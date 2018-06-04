package com.sdust.im.service;
/*
 * @author luhao
 * @date 2018/5/31 12:05
 *
 */

import com.sdust.im.common.ServerResponse;
import com.sdust.im.domin.dao.ClientInfo;
import com.sdust.im.domin.dao.User;
import com.sdust.im.mapper.ChatMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private UserService userService;

    /**
     * 获得本账户好友的在线状态
     * @param accountId
     * @return
     */
    public Map<String, Boolean> getOwnContactOnlineState(String accountId){
        List<ClientInfo> ownContactOnlineState = null;
        Map<String, Boolean> resultMap = new HashMap<>();
        try {
            ownContactOnlineState = chatMapper.getOwnContactOnlineState(accountId);
            for (ClientInfo clientInfo : ownContactOnlineState) {
                resultMap.put(clientInfo.getClientid(), "0".equals(clientInfo.getConnected().toString())?false:true);
            }
            logger.info("用户【{}】好友登陆状态为【{}】", accountId, resultMap);
        } catch (Exception e) {
            logger.error("查询用户【{}】好友登陆状态出错，错误信息【{}】", accountId, e.getStackTrace());
        }
        return resultMap;
    }

    /**
     * 获得本账户好友的好友的在线状态
     * @param accountId
     * @return
     */
    public Map<String, Map<String, Boolean>> getContactContactOnlineState(String accountId){
        //查询本账户所有在线好友
        ServerResponse allContact = userService.findOnlineContact(accountId);
        List<User> userList = (List<User>)allContact.getData();
        Map<String, Map<String, Boolean>> resultMap = new HashMap<>();
        //遍历好友列表获取好友的在线状态
        for (User user: userList) {
            String userAccountId = user.getAccountId();
            Map<String, Boolean> ownContactOnlineState = getOwnContactOnlineState(userAccountId);
            resultMap.put(userAccountId, ownContactOnlineState);
        }
        return resultMap;
    }

}
