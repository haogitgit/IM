package com.sdust.im.service;

import com.sdust.im.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/*
 * @author luhao
 * @date 2018/5/11 17:24
 *
 */
@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @CacheEvict(value = "temp")
    public int login(int temp){
        loginMapper.login(temp);
        return 11;
    }

}
