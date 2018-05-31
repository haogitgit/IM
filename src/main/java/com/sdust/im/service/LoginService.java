package com.sdust.im.service;

import com.sdust.im.common.ServerResponse;
import com.sdust.im.domin.dao.User;
import com.sdust.im.mapper.LoginMapper;
import com.sdust.im.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author luhao
 * @date 2018/5/11 17:24
 *
 */
@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 登陆service
     * @param user
     * @return
     */
    public ServerResponse login(User user){
        //获取用户账号密码
        String accountId = user.getAccountId();
        String password = user.getPassword();
        password = IDUtils.getMD5(password);
        //根据账号查询用户信息
        User userResult = loginMapper.findUserByAccountId(accountId);
        //返回结果
        if(password.equals(userResult.getPassword())){
            return ServerResponse.createBySuccess(userResult);
        }
        return ServerResponse.createByErrorMessage("账号或密码错误！");
    }


    /**
     * 注册service
     * @param user
     * @return
     */
    public ServerResponse register(User user){
        //生成账号
        String accountId = getAccountId();
        if (null != accountId && !"".equals(accountId)){
            user.setAccountId(accountId);
            String password = IDUtils.getMD5(user.getPassword());
            user.setPassword(password);
            //保存user
            try {
                loginMapper.saveUser(user);
                return ServerResponse.createBySuccess(user);
            }catch (Exception e){
                return ServerResponse.createByError();
            }

        }else{
            return ServerResponse.createByError();
        }

    }

    /**
     * 获取accountId
     * @return
     */
    private synchronized String getAccountId(){
        String _accountId = null;
        try {
            //查询数据库中的账号最大值
            String maxAccountId = loginMapper.findMaxAccountId();
            if(null == maxAccountId || "".equals(maxAccountId)){
                maxAccountId = "10000";
            }
            //账号加一
            int accountId= Integer.valueOf(maxAccountId);
            logger.info("获取当前账号为【{}】", accountId);
            _accountId = String.valueOf(++accountId);
        } catch (NumberFormatException e) {
            logger.error("注册获取账号出错，错误信息【{}】", e.getStackTrace());
        }
        return _accountId;
    }
}
