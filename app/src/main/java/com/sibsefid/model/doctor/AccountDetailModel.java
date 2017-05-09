package com.sibsefid.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14/9/16.
 */
public class AccountDetailModel {


    /**
     * success : true
     * Data : [{"SrNo":"6","DoctorId":"50","BankName":"ICICI Bank Ltd","AccountType":"Saving Account","BSB":"123456","AcNumber":"001204578","CreatedDate":"6/27/2016 8:02:56 AM","UpdateDate":"9/3/2016 7:14:33 AM","PaypalEmail":"paypal@gmail.com"}]
     */

    private boolean success;
    /**
     * SrNo : 6
     * DoctorId : 50
     * BankName : ICICI Bank Ltd
     * AccountType : Saving Account
     * BSB : 123456
     * AcNumber : 001204578
     * CreatedDate : 6/27/2016 8:02:56 AM
     * UpdateDate : 9/3/2016 7:14:33 AM
     * PaypalEmail : paypal@gmail.com
     */

    @SerializedName("Data")
    private ArrayList<DAccountDetails> daccountdetails;
    /**
     * msg : Doctor Account Has been Updated.
     */

    private List<MessageBean> Message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DAccountDetails> getDaccountdetails() {
        return daccountdetails;
    }

    public void setDaccountdetails(ArrayList<DAccountDetails> daccountdetails) {
        this.daccountdetails = daccountdetails;
    }

    public List<MessageBean> getMessage() {
        return Message;
    }

    public void setMessage(List<MessageBean> Message) {
        this.Message = Message;
    }

    public static class DAccountDetails {
        private String SrNo;
        private String DoctorId;
        private String BankName;
        private String AccountType;
        private String BSB;
        private String AcNumber;
        private String CreatedDate;
        private String UpdateDate;
        private String PaypalEmail;

        public String getSrNo() {
            return SrNo;
        }

        public void setSrNo(String SrNo) {
            this.SrNo = SrNo;
        }

        public String getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(String DoctorId) {
            this.DoctorId = DoctorId;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }

        public String getAccountType() {
            return AccountType;
        }

        public void setAccountType(String AccountType) {
            this.AccountType = AccountType;
        }

        public String getBSB() {
            return BSB;
        }

        public void setBSB(String BSB) {
            this.BSB = BSB;
        }

        public String getAcNumber() {
            return AcNumber;
        }

        public void setAcNumber(String AcNumber) {
            this.AcNumber = AcNumber;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String UpdateDate) {
            this.UpdateDate = UpdateDate;
        }

        public String getPaypalEmail() {
            return PaypalEmail;
        }

        public void setPaypalEmail(String PaypalEmail) {
            this.PaypalEmail = PaypalEmail;
        }
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
