package com.sibsefid.model.doctor;

import java.util.List;

/**
 * Created by root on 13/9/16.
 */
public class RegisterResponseModel {

    /**
     * success : true
     * Message : [{"msg":"Doctor Already Register With This Email Id"}]
     */

    private boolean success;
    private String msgCount;
    /**
     * msg : Doctor Already Register With This Email Id
     */

    private List<MessageBean> Message;

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<MessageBean> getMessage() {
        return Message;
    }

    public void setMessage(List<MessageBean> Message) {
        this.Message = Message;
    }

    public static class MessageBean {
        private String msg;
        private String ID;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


    }
}
