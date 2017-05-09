package com.sibsefid.model.doctor;

import java.util.List;

/**
 * Created by root on 17/9/16.
 */
public class ProfileUpdateModel {


    /**
     * success : true
     * Message : [{"msg":"Doctor Updated Successfully"}]
     */

    private boolean success;
    /**
     * msg : Doctor Updated Successfully
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
