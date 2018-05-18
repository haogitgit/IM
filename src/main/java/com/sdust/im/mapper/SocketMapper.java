package com.sdust.im.mapper;
/*
 * @author luhao
 * @date 2018/5/17 13:28
 *
 */

import com.sdust.im.domin.dao.ClientInfo;

public interface SocketMapper {

    /**
     * 保存客户端信息
     * @param clientInfo
     */
    void saveClientInfo(ClientInfo clientInfo);

    /**
     * 根据clientid查询客户端信息
     * @param clientid
     * @return
     */
    ClientInfo findClientInfoByClientid(String clientid);

}
