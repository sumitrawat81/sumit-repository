package com.sibsefid.model.doctor;

import java.util.ArrayList;

/**
 * Created by ubuntu on 6/10/16.
 */
public class DoctorPreviousTicketsModel {


    /**
     * success : true
     * Data : [{"user_seq":"13","summary":"yoooo","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1013"},{"user_seq":"12","summary":"this is testing","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1012"},{"user_seq":"11","summary":"big probdfdfdf","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1011"},{"user_seq":"10","summary":"big prob","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1010"},{"user_seq":"9","summary":"big prob","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1009"},{"user_seq":"8","summary":"big p","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1008"},{"user_seq":"7","summary":"i have a very big problem of short term memory loss.","summarydate":"5 October 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1007"},{"user_seq":"5","summary":"this is my new problem dated 9 august 2016 11 AM","summarydate":"9 August 2016","ReplayByAdmin":"reply given by admin on dated 10 august 12.01 PM","field_code":"D0001","IsProblemSolved":"True","ticketNo":"1005"},{"user_seq":"2","summary":"new problem ","summarydate":"29 June 2016","ReplayByAdmin":"Waiting For Reply","field_code":"D0001","IsProblemSolved":"","ticketNo":"1002"},{"user_seq":"1","summary":"hello this problem is entered at 29 june","summarydate":"29 June 2016","ReplayByAdmin":"reply by admin dated 30 june","field_code":"D0001","IsProblemSolved":"True","ticketNo":"1001"}]
     */

    private boolean success;
    /**
     * user_seq : 13
     * summary : yoooo
     * summarydate : 5 October 2016
     * ReplayByAdmin : Waiting For Reply
     * field_code : D0001
     * IsProblemSolved :
     * ticketNo : 1013
     */

    private ArrayList<DataBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DataBean> getData() {
        return Data;
    }

    public void setData(ArrayList<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String user_seq;
        private String summary;
        private String summarydate;
        private String ReplayByAdmin;
        private String field_code;
        private String IsProblemSolved;
        private String ticketNo;

        public String getUser_seq() {
            return user_seq;
        }

        public void setUser_seq(String user_seq) {
            this.user_seq = user_seq;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSummarydate() {
            return summarydate;
        }

        public void setSummarydate(String summarydate) {
            this.summarydate = summarydate;
        }

        public String getReplayByAdmin() {
            return ReplayByAdmin;
        }

        public void setReplayByAdmin(String ReplayByAdmin) {
            this.ReplayByAdmin = ReplayByAdmin;
        }

        public String getField_code() {
            return field_code;
        }

        public void setField_code(String field_code) {
            this.field_code = field_code;
        }

        public String getIsProblemSolved() {
            return IsProblemSolved;
        }

        public void setIsProblemSolved(String IsProblemSolved) {
            this.IsProblemSolved = IsProblemSolved;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }
    }
}
