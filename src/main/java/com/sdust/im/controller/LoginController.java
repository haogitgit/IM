package com.sdust.im.controller;

import com.sdust.im.common.ServerResponse;
import com.sdust.im.domin.dao.User;
import com.sdust.im.service.LoginService;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties.Redis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author luhao
 * @date 2018/5/11 17:16
 *
 */
@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @RequestMapping("login")
    public ServerResponse login(@RequestBody User user, HttpSession session, HttpServletResponse response){
        //判断此账号是否在另一设备登陆
        String sessionId = session.getId();
        String accountId = user.getAccountId();
        //获取redis中的sessionid
        String redisSessionId = (String)redisTemplate.opsForValue().get(accountId);
        if (null != redisSessionId){
            if (!sessionId.equals(redisSessionId)){
                return ServerResponse.createByErrorMessage("已在其他设备登陆，无法重复登陆！");
            }

        }
        String password = user.getPassword();
        logger.info("用户账号为【{}】，密码为【{}】",accountId , password);
        if(null != accountId && null != password){
            ServerResponse result = loginService.login(user);
            if (result.isSuccess()){
                session.setAttribute("isLogin", true);
                redisTemplate.opsForValue().set(accountId, sessionId);
            }else{
                Cookie cookie = new Cookie("JSESSIONID", "");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            return result;
        }
        return ServerResponse.createByErrorMessage("账号或密码错误！");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("register")
    public ServerResponse register(@RequestBody User user, HttpServletResponse response){

        if(null != user){
            String birth = user.getBirth();
            birth = birth.substring(0, birth.indexOf("T"));
            user.setBirth(birth);
            List<String> homeplace = user.getHomeplace();
            user.setProvince(homeplace.get(0));
            user.setCity(homeplace.get(1));
            user.setCounty(homeplace.get(2));
            ServerResponse result = loginService.register(user);
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return result;
        }else{
            return ServerResponse.createByErrorMessage("服务器错误");
        }
    }

    /**
     * 用户登出
     * @param id
     * @return
     */
    @RequestMapping("logout/{id}")
    public ServerResponse logout(@PathVariable(value = "id") String id, HttpSession session,
        HttpServletResponse response){
        logger.info("本次登出用户id【{}】", id);
        //将session中islogin置为false
        session.setAttribute("isLogin", false);
        //删除redis中的sessionid
        redisTemplate.delete(id);
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ServerResponse.createBySuccess();
    }

}
