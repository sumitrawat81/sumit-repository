package com.sibsefid.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 3/10/16.
 */
public class DAppointmentListModel {

    /**
     * success : true
     * Data : [{"appointId":"129","patientsId":"99","status":"","Date":"03/10/2016","time":"5:21PM","AppointDate":"04/10/2016","AppointTime":"7:32PM","name":"Mr. Rajkumar Sharma","doctorname":"Mr. Neeraj Sharma","patient_contact":"911412763903","patient_Email":"neeraj@brsoftech.com","IsReadByDoctor":"","DoctorUnreadAppointment":"1","TransactionNumber":"PAY-89A495391M710371LK7ZEM2I","doctorId":"50"}]
     */

    private boolean success;
    /**
     * appointId : 129
     * patientsId : 99
     * status :
     * Date : 03/10/2016
     * time : 5:21PM
     * AppointDate : 04/10/2016
     * AppointTime : 7:32PM
     * name : Mr. Rajkumar Sharma
     * doctorname : Mr. Neeraj Sharma
     * patient_contact : 911412763903
     * patient_Email : neeraj@brsoftech.com
     * IsReadByDoctor :
     * DoctorUnreadAppointment : 1
     * TransactionNumber : PAY-89A495391M710371LK7ZEM2I
     * doctorId : 50
     */

    @SerializedName("Data")
    private ArrayList<DAppointmentBean> DAppointmentBean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DAppointmentBean> getDAppointmentBean() {
        return DAppointmentBean;
    }

    public void setDAppointmentBean(ArrayList<DAppointmentBean> DAppointmentBean) {
        this.DAppointmentBean = DAppointmentBean;
    }

    public static class DAppointmentBean {
        private String appointId;
        private String patientsId;
        private String status;
        private String Date;
        private String time;
        private String AppointDate;
        private String AppointTime;
        private String name;
        private String doctorname;
        private String patient_contact;
        private String patient_Email;
        private String IsReadByDoctor;
        private String DoctorUnreadAppointment;
        private String TransactionNumber;
        private String doctorId;
        private String appointmentmode;
        private boolean isLoading;

        @SerializedName("Reason")
        private String PReason;
        @SerializedName("AppointmentMode")
        private String PAppointmentMode;
        @SerializedName("DoctorName")
        private String PDoctorName;
        @SerializedName("email")
        private String Pemail;
        @SerializedName("Status")
        private String PStatus;
        @SerializedName("appointid")
        private String Pappointid;

        public String getPReason() {
            return PReason;
        }

        public void setPReason(String PReason) {
            this.PReason = PReason;
        }

        public String getPAppointmentMode() {
            return PAppointmentMode;
        }

        public void setPAppointmentMode(String PAppointmentMode) {
            this.PAppointmentMode = PAppointmentMode;
        }

        public String getAppointmentmode() {
            return appointmentmode;
        }

        public void setAppointmentmode(String appointmentmode) {
            this.appointmentmode = appointmentmode;
        }

        public String getPDoctorName() {
            return PDoctorName;
        }

        public void setPDoctorName(String PDoctorName) {
            this.PDoctorName = PDoctorName;
        }

        public String getPemail() {
            return Pemail;
        }

        public void setPemail(String pemail) {
            Pemail = pemail;
        }

        public String getPStatus() {
            return PStatus;
        }

        public void setPStatus(String PStatus) {
            this.PStatus = PStatus;
        }

        public String getPappointid() {
            return Pappointid;
        }

        public void setPappointid(String pappointid) {
            Pappointid = pappointid;
        }

        public boolean isLoading() {
            return isLoading;
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }

        public String getPatientsId() {
            return patientsId;
        }

        public void setPatientsId(String patientsId) {
            this.patientsId = patientsId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getAppointDate() {
            return AppointDate;
        }

        public void setAppointDate(String AppointDate) {
            this.AppointDate = AppointDate;
        }

        public String getAppointTime() {
            return AppointTime;
        }

        public void setAppointTime(String AppointTime) {
            this.AppointTime = AppointTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDoctorname() {
            return doctorname;
        }

        public void setDoctorname(String doctorname) {
            this.doctorname = doctorname;
        }

        public String getPatient_contact() {
            return patient_contact;
        }

        public void setPatient_contact(String patient_contact) {
            this.patient_contact = patient_contact;
        }

        public String getPatient_Email() {
            return patient_Email;
        }

        public void setPatient_Email(String patient_Email) {
            this.patient_Email = patient_Email;
        }

        public String getIsReadByDoctor() {
            return IsReadByDoctor;
        }

        public void setIsReadByDoctor(String IsReadByDoctor) {
            this.IsReadByDoctor = IsReadByDoctor;
        }

        public String getDoctorUnreadAppointment() {
            return DoctorUnreadAppointment;
        }

        public void setDoctorUnreadAppointment(String DoctorUnreadAppointment) {
            this.DoctorUnreadAppointment = DoctorUnreadAppointment;
        }

        public String getTransactionNumber() {
            return TransactionNumber;
        }

        public void setTransactionNumber(String TransactionNumber) {
            this.TransactionNumber = TransactionNumber;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }
    }
}
