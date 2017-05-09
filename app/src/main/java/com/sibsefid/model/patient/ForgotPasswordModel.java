package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 14/12/16.
 */
public class ForgotPasswordModel {


    /**
     * success : false
     * Message : [{"msg":"User Email Id not Found"}]
     */

    private boolean success;
    /**
     * msg : User Email Id not Found
     */

    private List<MessageBean> Message;

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

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
