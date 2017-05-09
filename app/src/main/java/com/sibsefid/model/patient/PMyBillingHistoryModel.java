package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by linux on 15/10/16.
 */

public class PMyBillingHistoryModel {


    /**
     * success : true
     * Data : [{"appointId":"232","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"chat","Status":"Rejected","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"18/10/2016","time":"00:00:00","appointdate":"10/18/2016 3:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"6"},{"appointId":"231","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"chat","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"17/10/2016","time":"00:00:00","appointdate":"10/17/2016 3:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"230","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"chat","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"16/10/2016","time":"00:00:00","appointdate":"10/16/2016 3:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"228","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"chat","Status":"Rejected","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"16/10/2016","time":"00:00:00","appointdate":"10/16/2016 3:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"6"},{"appointId":"227","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"13/10/2016","time":"00:00:00","appointdate":"10/14/2016 12:22:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"226","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"13/10/2016","time":"00:00:00","appointdate":"10/14/2016 12:16:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"225","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"13/10/2016","time":"00:00:00","appointdate":"10/13/2016 8:24:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"211","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"10/10/2016","time":"00:00:00","appointdate":"10/10/2016 9:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"207","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"video","Status":"Pending","DocterName":"Tchazou JM","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/9/2016 12:35:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"206","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 11:45:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"205","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 11:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"204","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 10:15:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"198","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 8:43:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"197","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 7:31:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"201","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 6:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"200","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 6:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"196","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Rejected","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 6:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"6"},{"appointId":"195","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"08/10/2016","time":"00:00:00","appointdate":"10/8/2016 11:20:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"194","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"07/10/2016","time":"00:00:00","appointdate":"10/7/2016 6:39:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"193","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"07/10/2016","time":"00:00:00","appointdate":"10/7/2016 4:46:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"190","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"07/10/2016","time":"00:00:00","appointdate":"10/7/2016 4:01:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"163","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"06/10/2016","time":"00:00:00","appointdate":"10/6/2016 7:31:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"151","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Pending","DocterName":"kirat kaur","Patients":"RAjKumar Sharma","Date":"05/10/2016","time":"00:00:00","appointdate":"10/5/2016 7:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"162","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"05/10/2016","time":"00:00:00","appointdate":"10/5/2016 6:31:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"158","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"05/10/2016","time":"00:00:00","appointdate":"10/5/2016 6:31:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"129","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Chat","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"04/10/2016","time":"00:00:00","appointdate":"10/4/2016 7:32:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"160","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Audio","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"04/10/2016","time":"00:00:00","appointdate":"10/4/2016 7:31:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"161","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"04/10/2016","time":"00:00:00","appointdate":"10/4/2016 6:31:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"121","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Chat","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"03/10/2016","time":"00:00:00","appointdate":"10/3/2016 6:31:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"120","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Chat","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"02/10/2016","time":"00:00:00","appointdate":"10/2/2016 6:31:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"98","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"27/09/2016","time":"00:00:00","appointdate":"9/27/2016 1:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"97","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"","Status":"Pending","DocterName":"kirat kaur","Patients":"RAjKumar Sharma","Date":"27/09/2016","time":"00:00:00","appointdate":"9/27/2016 12:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"80","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"kirat kaur","Patients":"RAjKumar Sharma","Date":"20/09/2016","time":"00:00:00","appointdate":"9/20/2016 12:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"66","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"19/09/2016","time":"00:00:00","appointdate":"9/19/2016 11:50:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"64","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"Tchazou JM","Patients":"RAjKumar Sharma","Date":"17/09/2016","time":"00:00:00","appointdate":"9/17/2016 4:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"57","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"12/09/2016","time":"00:00:00","appointdate":"9/12/2016 7:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"45","fees":"0","PaymentStatusId":"1","PaymentBy":"Credit Card Paypal","AppointmentMode":"Video","Status":"Pending","DocterName":"Tchazou JM","Patients":"RAjKumar Sharma","Date":"12/09/2016","time":"00:00:00","appointdate":"9/12/2016 11:00:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"20","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 6:00:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"18","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 4:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"12","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"02/09/2016","time":"00:00:00","appointdate":"9/2/2016 11:10:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"11","fees":"0","PaymentStatusId":"1","PaymentBy":"Paypal","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"01/09/2016","time":"00:00:00","appointdate":"9/1/2016 7:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"},{"appointId":"10","fees":"0","PaymentStatusId":"1","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"26/08/2016","time":"00:00:00","appointdate":"8/26/2016 7:20:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"5","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Audio","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"17/08/2016","time":"00:00:00","appointdate":"8/17/2016 5:40:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"6","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"17/08/2016","time":"00:00:00","appointdate":"8/17/2016 4:50:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"4","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Pending","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"17/08/2016","time":"00:00:00","appointdate":"8/17/2016 10:50:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"3","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Pending","DocterName":"Tchazou JM","Patients":"RAjKumar Sharma","Date":"12/08/2016","time":"00:00:00","appointdate":"8/12/2016 7:10:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":""},{"appointId":"2","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Approved","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"10/08/2016","time":"00:00:00","appointdate":"8/10/2016 11:30:00 AM","doctorfees":"45.00","paymentstatus":"paid","statusid":"3"},{"appointId":"1","fees":"0","PaymentStatusId":"5","PaymentBy":"Voucher","AppointmentMode":"Video","Status":"Completed","DocterName":"neeraj sharma","Patients":"RAjKumar Sharma","Date":"09/08/2016","time":"00:00:00","appointdate":"8/9/2016 10:30:00 PM","doctorfees":"45.00","paymentstatus":"paid","statusid":"2"}]
     */

    private boolean success;
    /**
     * appointId : 232
     * fees : 0
     * PaymentStatusId : 1
     * PaymentBy : Paypal
     * AppointmentMode : chat
     * Status : Rejected
     * DocterName : neeraj sharma
     * Patients : RAjKumar Sharma
     * Date : 18/10/2016
     * time : 00:00:00
     * appointdate : 10/18/2016 3:32:00 PM
     * doctorfees : 45.00
     * paymentstatus : paid
     * statusid : 6
     */

    @SerializedName("Data")
    private ArrayList<PMyBillingBean> PMyBillingList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PMyBillingBean> getPMyBillingList() {
        return PMyBillingList;
    }

    public void setPMyBillingList(ArrayList<PMyBillingBean> PMyBillingList) {
        this.PMyBillingList = PMyBillingList;
    }

    public static class PMyBillingBean {
        private String appointId;
        private String fees;
        private String PaymentStatusId;
        private String PaymentBy;
        private String AppointmentMode;
        private String Status;
        private String DocterName;
        private String Patients;
        private String Date;
        private String time;
        private String appointdate;
        private String doctorfees;
        private String paymentstatus;
        private String statusid;
        private String Totalfees;

        public String getTotalfees() {
            return Totalfees;
        }

        public void setTotalfees(String totalfees) {
            Totalfees = totalfees;
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

        public String getAppointmentMode() {
            return AppointmentMode;
        }

        public void setAppointmentMode(String AppointmentMode) {
            this.AppointmentMode = AppointmentMode;
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
