package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 19/9/16.
 */
public class PMessageModel {

    /**
     * success : true
     * Data : [{"msgId":"25","doctorId":"50","message":" from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join ","createdDate":"7/9/2016 12:45:15 AM","updateDate":"","Seen":"False","deleted":"False","IsMessageReadByPatient":"","IsMessageReadByDoctor":"","subject":" from the comfort of your office or home, you should join ","senderid":"99","AppointId":"0","MsgType":"","IsMessage":"False","SignatureDate":"7/11/2016 8:11:39 AM","date":"09/07/2016","sender":"Neeraj  Arora","receiver":"neeraj  sharma"},{"msgId":"33","doctorId":"50","message":"hello doctor how are you -- date 19 sep 2016","createdDate":"9/18/2016 10:45:03 PM","updateDate":"","Seen":"False","deleted":"False","IsMessageReadByPatient":"","IsMessageReadByDoctor":"","subject":"hello docotor --- date 19 sep 2016","senderid":"99","AppointId":"0","MsgType":"","IsMessage":"False","SignatureDate":"9/18/2016 10:45:03 PM","date":"18/09/2016","sender":"Neeraj  Arora","receiver":"neeraj  sharma"}]
     */

    private boolean success;
    /**
     * msgId : 25
     * doctorId : 50
     * message :  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join  from the comfort of your office or home, you should join
     * createdDate : 7/9/2016 12:45:15 AM
     * updateDate :
     * Seen : False
     * deleted : False
     * IsMessageReadByPatient :
     * IsMessageReadByDoctor :
     * subject :  from the comfort of your office or home, you should join
     * senderid : 99
     * AppointId : 0
     * MsgType :
     * IsMessage : False
     * SignatureDate : 7/11/2016 8:11:39 AM
     * date : 09/07/2016
     * sender : Neeraj  Arora
     * receiver : neeraj  sharma
     */

    @SerializedName("Data")
    private ArrayList<PMessageListBean> pmessagelistbean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PMessageListBean> getPmessagelistbean() {
        return pmessagelistbean;
    }

    public void setPmessagelistbean(ArrayList<PMessageListBean> pmessagelistbean) {
        this.pmessagelistbean = pmessagelistbean;
    }

    public static class PMessageListBean {
        private String msgId;
        private String doctorId;
        private String message;
        private String createdDate;
        private String updateDate;
        private String Seen;
        private String deleted;
        private String IsMessageReadByPatient;
        private String IsMessageReadByDoctor;
        private String subject;
        private String senderid;
        private String AppointId;
        private String MsgType;
        private String IsMessage;
        private String SignatureDate;
        private String date;
        private String sender;
        private String receiver;
        private String senderimg;
        private String receiverimg;
        private String image;

        public String getReceiverimg() {
            return receiverimg;
        }

        public void setReceiverimg(String receiverimg) {
            this.receiverimg = receiverimg;
        }

        public String getSenderimg() {
            return senderimg;
        }

        public void setSenderimg(String senderimg) {
            this.senderimg = senderimg;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getSeen() {
            return Seen;
        }

        public void setSeen(String Seen) {
            this.Seen = Seen;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getIsMessageReadByPatient() {
            return IsMessageReadByPatient;
        }

        public void setIsMessageReadByPatient(String IsMessageReadByPatient) {
            this.IsMessageReadByPatient = IsMessageReadByPatient;
        }

        public String getIsMessageReadByDoctor() {
            return IsMessageReadByDoctor;
        }

        public void setIsMessageReadByDoctor(String IsMessageReadByDoctor) {
            this.IsMessageReadByDoctor = IsMessageReadByDoctor;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public String getAppointId() {
            return AppointId;
        }

        public void setAppointId(String AppointId) {
            this.AppointId = AppointId;
        }

        public String getMsgType() {
            return MsgType;
        }

        public void setMsgType(String MsgType) {
            this.MsgType = MsgType;
        }

        public String getIsMessage() {
            return IsMessage;
        }

        public void setIsMessage(String IsMessage) {
            this.IsMessage = IsMessage;
        }

        public String getSignatureDate() {
            return SignatureDate;
        }

        public void setSignatureDate(String SignatureDate) {
            this.SignatureDate = SignatureDate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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
