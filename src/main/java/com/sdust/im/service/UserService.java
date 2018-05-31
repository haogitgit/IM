package com.sdust.im.service;

import com.sdust.im.common.ServerResponse;
import com.sdust.im.domin.dao.User;
import com.sdust.im.domin.dto.UserAccount;
import com.sdust.im.mapper.UserMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author luhao
 * @date 2018/5/12 19:45
 *
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     *获取此账户所有联系人
     * @param accountId
     * @return
     */
    public ServerResponse findAllContact(String accountId){
        List<User> userList = null;
        try {
            userList = userMapper.findAllContact(accountId);
            logger.info("账号【{}】所有联系人【{}】", accountId, userList);
            for (User user: userList) {
                String remark = user.getRemark();
                if (null != remark && !"".equals(remark)){
                    user.setName(remark + "(" + user.getName() + ")");
                }
            }
            return ServerResponse.createBySuccess(userList);
        }catch (Exception e){
            logger.error("查询所有联系人错误，错误信息【{}】", e.getStackTrace());
            return  ServerResponse.createByError();
        }
    }

    /**
     * 搜索联系人并添加
     * @param userAccount
     * @return
     */
    @Transactional
    public ServerResponse searchContact(UserAccount userAccount) {
        String userAccountId = userAccount.getUserAccountId();
        String contactAccountId = userAccount.getContactAccountId();
        logger.info("账户id【{}】联系人id【{}】", userAccountId, contactAccountId);
        if (null != userAccountId && !"".equals(userAccountId) &&
            null != contactAccountId && !"".equals(contactAccountId)){
            try {
                if (userAccountId.equals(contactAccountId)){
                    return ServerResponse.createByErrorMessage("自己无法被加为好友！");
                }
                User contact = userMapper.searchContact(contactAccountId);
                if (null != contact){
                    //是否已经是好友
                    List<User> allContact = userMapper.findAllContact(userAccountId);
                    Set<String> contactSet = new HashSet<>();
                    for (User user : allContact) {
                        contactSet.add(user.getAccountId());
                    }
                    if (contactSet.contains(contactAccountId)){
                        return ServerResponse.createByErrorMessage("对方已经是你的好友！");
                    }else {
                        //双方互加为好友
                        userMapper.addContact(userAccountId, contactAccountId);
                        userMapper.addContact(contactAccountId, userAccountId);
                        return ServerResponse.createBySuccessMessage("添加好友成功！");
                    }
                }
                return ServerResponse.createByErrorMessage("用户不存在！");
            } catch (Exception e) {
                logger.error("添加联系人错误，错误信息【{}】", e.getStackTrace());
                return ServerResponse.createByErrorMessage("服务器错误！");
            }
        }else{
            return ServerResponse.createByErrorMessage("服务器错误！");
        }
    }

    /**
     * 删除联系人
     * @param userAccount
     * @return
     */
    @Transactional
    public ServerResponse deleteContact(UserAccount userAccount) {
        String userAccountId = userAccount.getUserAccountId();
        String contactAccountId = userAccount.getContactAccountId();
        logger.info("账户id【{}】联系人id【{}】", userAccountId, contactAccountId);
        if (null != userAccountId && !"".equals(userAccountId) &&
            null != contactAccountId && !"".equals(contactAccountId)){
            try {
                userMapper.deleteContact(userAccountId, contactAccountId);
                userMapper.deleteContact(contactAccountId, userAccountId);
                return ServerResponse.createBySuccessMessage("删除好友成功！");
            }catch (Exception e){
                logger.error("删除联系人错误，错误信息【{}】", e.getStackTrace());
                return ServerResponse.createByErrorMessage("服务器错误！");
            }
        }else {
            return ServerResponse.createByErrorMessage("服务器错误！");
        }
    }

    /**
     * 添加备注
     * @param userAccount
     * @return
     */
    public ServerResponse updateRemark(UserAccount userAccount){
        String userAccountId = userAccount.getUserAccountId();
        String contactAccountId = userAccount.getContactAccountId();
        logger.info("账户id【{}】联系人id【{}】", userAccountId, contactAccountId);
        if (null != userAccountId && !"".equals(userAccountId) &&
            null != contactAccountId && !"".equals(contactAccountId)){
            try {
                userMapper.updateRemark(userAccount);
                return ServerResponse.createBySuccess();
            }catch (Exception e){
                logger.error("添加备注错误，错误信息【{}】", e.getStackTrace());
                return ServerResponse.createByErrorMessage("服务器错误！");
            }
        }else {
            return ServerResponse.createByErrorMessage("服务器错误！");
        }
    }

    /**
     * 修改资料
     * @param user
     * @return
     */
    public ServerResponse updateInfo(User user){
        logger.info("本次修改用户【{}】", user);
        if (null != user){
            try {
                userMapper.updateInfo(user);
                return ServerResponse.createBySuccess(user);
            } catch (Exception e) {
                logger.error("修改资料错误，错误信息【{}】", e.getStackTrace());
                return ServerResponse.createByErrorMessage("服务器错误！");
            }

        }else {
            return ServerResponse.createByErrorMessage("服务器错误！");
        }
    }
}
