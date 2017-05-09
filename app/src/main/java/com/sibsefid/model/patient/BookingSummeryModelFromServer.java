package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 1/10/16.
 */
public class BookingSummeryModelFromServer {


    /**
     * Success : true
     * Data : {"BookingSummary":[{"id":"104","FeeClient":"70.00","FeeDoctor":"42.00","FeeAdmin":"28.00"}]}
     */

    private boolean Success;
    private String Message;
    @SerializedName("message")
    private String message1;
    private DataBean Data;

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 104
         * FeeClient : 70.00
         * FeeDoctor : 42.00
         * FeeAdmin : 28.00
         */

        private List<BookingSummaryBean> BookingSummary;

        public List<BookingSummaryBean> getBookingSummary() {
            return BookingSummary;
        }

        public void setBookingSummary(List<BookingSummaryBean> BookingSummary) {
            this.BookingSummary = BookingSummary;
        }

        public static class BookingSummaryBean {
            private String id;
            private String FeeClient;
            private String FeeDoctor;
            private String FeeAdmin;
            private String TransactionNumber;

            public String getTransactionNumber() {
                return TransactionNumber;
            }

            public void setTransactionNumber(String transactionNumber) {
                TransactionNumber = transactionNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFeeClient() {
                return FeeClient;
            }

            public void setFeeClient(String FeeClient) {
                this.FeeClient = FeeClient;
            }

            public String getFeeDoctor() {
                return FeeDoctor;
            }

            public void setFeeDoctor(String FeeDoctor) {
                this.FeeDoctor = FeeDoctor;
            }

            public String getFeeAdmin() {
                return FeeAdmin;
            }

            public void setFeeAdmin(String FeeAdmin) {
                this.FeeAdmin = FeeAdmin;
            }
        }
    }
}
