package com.sibsefid.model.doctor;

import java.util.ArrayList;

/**
 * Created by ubuntu on 4/10/16.
 */
public class MyTransactionModel {


    /**
     * success : true
     * Data : [{"AppointmentMode":"Video","appointId":"98","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"27/09/2016","time":"00:00:00","appointdate":"9/27/2016 1:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"26","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"ayushi mittal","Date":"05/09/2016","time":"00:00:00","appointdate":"9/5/2016 5:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"25","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"ayushi mittal","Date":"05/09/2016","time":"00:00:00","appointdate":"9/5/2016 4:50:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"24","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"jean marc","Date":"03/09/2016","time":"00:00:00","appointdate":"9/3/2016 6:30:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"23","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"ayushi mittal","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 6:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"22","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","Status":"Completed","DocterName":"neeraj sharma","Patients":"ayushi mittal","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 6:30:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"20","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 6:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"17","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"ayushi mittal","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 1:10:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"11","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"01/09/2016","time":"00:00:00","appointdate":"9/1/2016 7:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"AppointmentMode":"Video","appointId":"1","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"09/08/2016","time":"00:00:00","appointdate":"8/9/2016 10:30:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"}]
     */

    private boolean success;
    /**
     * AppointmentMode : Video
     * appointId : 98
     * fees : 0
     * PaymentStatusId : 1
     * PaymentBy : Credit Card Paypal
     * Status : Completed
     * DocterName : neeraj sharma
     * Patients : RAjKumar Sharma
     * Date : 27/09/2016
     * time : 00:00:00
     * appointdate : 9/27/2016 1:00:00 PM
     * doctorfees : 45.00
     * paymentstatus : paid
     * statusid : 2
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
        private String AppointmentMode;
        private String appointId;
        private String fees;
        private String PaymentStatusId;
        private String PaymentBy;
        private String Status;
        private String DocterName;
        private String Patients;
        private String Date;
        private String time;
        private String appointdate;
        private String doctorfees;
        private String paymentstatus;
        private String statusid;

        public String getAppointmentMode() {
            return AppointmentMode;
        }

        public void setAppointmentMode(String AppointmentMode) {
            this.AppointmentMode = AppointmentMode;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }

        public String getFees() {
            return fees;
        }

        public void setFees(String fees) {
            this.fees = fees;
        }

        public String getPaymentStatusId() {
            return PaymentStatusId;
        }

        public void setPaymentStatusId(String PaymentStatusId) {
            this.PaymentStatusId = PaymentStatusId;
        }

        public String getPaymentBy() {
            return PaymentBy;
        }

        public void setPaymentBy(String PaymentBy) {
            this.PaymentBy = PaymentBy;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getDocterName() {
            return DocterName;
        }

        public void setDocterName(String DocterName) {
            this.DocterName = DocterName;
        }

        public String getPatients() {
            return Patients;
        }

        public void setPatients(String Patients) {
            this.Patients = Patients;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAppointdate() {
            return appointdate;
        }

        public void setAppointdate(String appointdate) {
            this.appointdate = appointdate;
        }

        public String getDoctorfees() {
            return doctorfees;
        }

        public void setDoctorfees(String doctorfees) {
            this.doctorfees = doctorfees;
        }

        public String getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(String paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

        public String getStatusid() {
            return statusid;
        }

        public void setStatusid(String statusid) {
            this.statusid = statusid;
        }
    }
}
