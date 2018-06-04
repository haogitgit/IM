package com.sdust.im.mapper;
/*
 * @author luhao
 * @date 2018/5/31 12:05
 *
 */

import com.sdust.im.domin.dao.ClientInfo;
import java.util.List;

public interface ChatMapper {
    /**
     * 获得本账户好友的在线状态
     * @param accountId
     * @return
     */
    List<ClientInfo> getOwnContactOnlineState(String accountId);
}
