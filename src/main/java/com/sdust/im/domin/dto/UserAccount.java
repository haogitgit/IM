package com.sdust.im.domin.dto;
/*
 * @author luhao
 * @date 2018/5/24 10:03
 *
 */

public class UserAccount {

    private String userAccountId;

    private String contactAccountId;

    private String remark;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getContactAccountId() {
        return contactAccountId;
    }

    public void setContactAccountId(String contactAccountId) {
        this.contactAccountId = contactAccountId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
