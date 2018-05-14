package com.sdust.im.mapper;
/*
 * @author luhao
 * @date 2018/5/11 17:25
 *
 */

import com.sdust.im.domin.dao.User;

public interface LoginMapper {

    void login(int temp);

    /**
     * 根据账号查询用户信息
     * @param accountId
     * @return
     */
    User findUserByAccountId(String accountId);

    /**
     * 查询最大的账号
     * @return
     */
    String findMaxAccountId();

    /**
     * 保存用户信息
     * @param user
     */
    void saveUser(User user);

}
