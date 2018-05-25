package com.sdust.im.mapper;
/*
 * @author luhao
 * @date 2018/5/20 20:35
 *
 */

import com.sdust.im.domin.dao.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 根据账户id获取联系人列表
     * @param accountId
     * @return
     */
    List<User> findAllContact(String accountId);

    /**
     * 搜索联系人
     * @param accountId
     * @return
     */
    User searchContact(String accountId);

    /**
     * 添加联系人
     * @param userAccountId
     * @param contactAccountId
     */
    void addContact(@Param("userAccountId") String userAccountId,
        @Param("contactAccountId")String contactAccountId);

    /**
     * 删除联系人
     * @param userAccountId
     * @param contactAccountId
     */
    void deleteContact(@Param("userAccountId") String userAccountId,
        @Param("contactAccountId")String contactAccountId);
}
