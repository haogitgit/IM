package com.sdust.im.domin.dto;
/*
 * @author luhao
 * @date 2018/5/17 13:14
 *
 */

public class MessageInfo {
    //源客户端id
    private String sourceClientId;
    //目标客户端id
    private String targetClientId;
    //消息类型
    private String msgType;
    //消息内容
    private String msgContent;

    private String date;

    public String getSourceClientId() {
        return sourceClientId;
    }
    public void setSourceClientId(String sourceClientId) {
        this.sourceClientId = sourceClientId;
    }
    public String getTargetClientId() {
        return targetClientId;
    }
    public void setTargetClientId(String targetClientId) {
        this.targetClientId = targetClientId;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getMsgContent() {
        return msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
