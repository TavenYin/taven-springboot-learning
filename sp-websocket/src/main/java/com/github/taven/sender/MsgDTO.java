package com.github.taven.sender;

import java.io.Serializable;

public class MsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer receiverId;

    private String msgBody;

    public MsgDTO() {
    }

    public MsgDTO(Integer receiverId, String msgBody) {
        this.receiverId = receiverId;
        this.msgBody = msgBody;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
