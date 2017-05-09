package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 9/12/16.
 */
public class PMessageSentModel {


    /**
     * success : true
     * Data : [{"msgId":"154","receiverId":"520","senderid":"515","Msg":"sdf sdfss dfd sf","subject":"test","MsgType":"","Date":"11/22/2016 6:22:19 PM","sender":"sam  rawat","receiver":"harman  12345"},{"msgId":"152","receiverId":"557","senderid":"515","Msg":"ghjghkjghkjhkjhkjhkj","subject":"kljlkjkjl","MsgType":"","Date":"11/22/2016 6:18:54 PM","sender":"sam  rawat","receiver":"san  rawat"},{"msgId":"151","receiverId":"557","senderid":"515","Msg":"ghjghkjghkjhkjhkjhkj","subject":"kljlkjkjl","MsgType":"","Date":"11/22/2016 6:18:52 PM","sender":"sam  rawat","receiver":"san  rawat"},{"msgId":"150","receiverId":"557","senderid":"515","Msg":"ghjghkjghkjhkjhkjhkj","subject":"kljlkjkjl","MsgType":"","Date":"11/22/2016 6:18:49 PM","sender":"sam  rawat","receiver":"san  rawat"},{"msgId":"148","receiverId":"557","senderid":"515","Msg":"ghjghkjghkjhkjhkjhkj","subject":"kljlkjkjl","MsgType":"","Date":"11/22/2016 6:12:06 PM","sender":"sam  rawat","receiver":"san  rawat"},{"msgId":"147","receiverId":"557","senderid":"515","Msg":"ghjghkjghkjhkjhkjhkj","subject":"kljlkjkjl","MsgType":"","Date":"11/22/2016 6:12:00 PM","sender":"sam  rawat","receiver":"san  rawat"},{"msgId":"145","receiverId":"520","senderid":"515","Msg":"testdf","subject":"yes this is OK ","MsgType":"","Date":"11/22/2016 3:18:17 PM","sender":"sam  rawat","receiver":"harman  12345"}]
     */

    private boolean success;
    /**
     * msgId : 154
     * receiverId : 520
     * senderid : 515
     * Msg : sdf sdfss dfd sf
     * subject : test
     * MsgType :
     * Date : 11/22/2016 6:22:19 PM
     * sender : sam  rawat
     * receiver : harman  12345
     */

    private List<DataBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String msgId;
        private String receiverId;
        private String senderid;
        private String Msg;
        private String subject;
        private String MsgType;
        private String Date;
        private String sender;
        private String receiver;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMsgType() {
            return MsgType;
        }

        public void setMsgType(String MsgType) {
            this.MsgType = MsgType;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }
    }
}
