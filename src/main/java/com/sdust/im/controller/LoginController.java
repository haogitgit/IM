package com.sdust.im.controller;

import com.sdust.im.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties.Redis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("login")
    public String login(){
        logger.info("日志测试 log info");
        logger.error("日志测试 log error");
        logger.debug("日志测试 log debug");
        redisTemplate.opsForValue().set("session", "hello");
        loginService.login(11);
        return "haha";
    }
}
