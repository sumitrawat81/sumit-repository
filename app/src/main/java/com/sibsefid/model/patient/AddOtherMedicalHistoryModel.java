package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 21/12/16.
 */
public class AddOtherMedicalHistoryModel {


    /**
     * success : true
     * Message : [{"msg":"Data Updated Successfully"}]
     */

    private boolean success;
    /**
     * msg : Data Updated Successfully
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
