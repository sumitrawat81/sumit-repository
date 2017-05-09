package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by linux on 8/10/16.
 */

public class GetPatientAppointmentModel {

    /**
     * success : true
     * Data : [{"appointmentmode":"Video","Reason":"What is the reason for your appointment? (ex: Sore Throat)  ","appointId":"196","status":"Rejected","statusid":"6","AppointDate":"08/10/2016","Date":"08/10/2016","time":"6:00PM","AppointTime":"10:53AM","Column1":"Mr. RAjKumar Sharma","doctorname":"neeraj sharma","email":"neeraj@brsoftech.com","IsReadByPatient":"","PatientUnreadAppointment":"0"},{"appointmentmode":"chat","Reason":"nbnm","appointId":"197","status":"Approved","statusid":"3","AppointDate":"08/10/2016","Date":"08/10/2016","time":"7:31PM","AppointTime":"11:49AM","Column1":"Mr. RAjKumar Sharma","doctorname":"neeraj sharma","email":"neeraj@brsoftech.com","IsReadByPatient":"","PatientUnreadAppointment":"0"}]
     */

    private boolean success;
    /**
     * appointmentmode : Video
     * Reason : What is the reason for your appointment? (ex: Sore Throat)
     * appointId : 196
     * status : Rejected
     * statusid : 6
     * AppointDate : 08/10/2016
     * Date : 08/10/2016
     * time : 6:00PM
     * AppointTime : 10:53AM
     * Column1 : Mr. RAjKumar Sharma
     * doctorname : neeraj sharma
     * email : neeraj@brsoftech.com
     * IsReadByPatient :
     * PatientUnreadAppointment : 0
     */

    @SerializedName("Data")
    private ArrayList<GetPAppointmentBean> GetPAppointmentBean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<GetPAppointmentBean> getGetPAppointmentBean() {
        return GetPAppointmentBean;
    }

    public void setGetPAppointmentBean(ArrayList<GetPAppointmentBean> GetPAppointmentBean) {
        this.GetPAppointmentBean = GetPAppointmentBean;
    }

    public static class GetPAppointmentBean {
        private String appointmentmode;
        private String Reason;
        private String appointId;
        private String status;
        private String statusid;
        private String AppointDate;
        private String Date;
        private String time;
        private String AppointTime;
        private String Column1;
        private String doctorname;
        private String email;
        private String IsReadByPatient;
        private String PatientUnreadAppointment;
        private String user_photo;
        private String subject;
        private String doctorimg;
        private String patientsimg;

        public String getPatientsimg() {
            return patientsimg;
        }

        public void setPatientsimg(String patientsimg) {
            this.patientsimg = patientsimg;
        }

        public String getDoctorimg() {
            return doctorimg;
        }

        public void setDoctorimg(String doctorimg) {
            this.doctorimg = doctorimg;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }

        public String getAppointmentmode() {
            return appointmentmode;
        }

        public void setAppointmentmode(String appointmentmode) {
            this.appointmentmode = appointmentmode;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusid() {
            return statusid;
        }

        public void setStatusid(String statusid) {
            this.statusid = statusid;
        }

        public String getAppointDate() {
            return AppointDate;
        }

        public void setAppointDate(String AppointDate) {
            this.AppointDate = AppointDate;
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

        public String getAppointTime() {
            return AppointTime;
        }

        public void setAppointTime(String AppointTime) {
            this.AppointTime = AppointTime;
        }

        public String getColumn1() {
            return Column1;
        }

        public void setColumn1(String Column1) {
            this.Column1 = Column1;
        }

        public String getDoctorname() {
            return doctorname;
        }

        public void setDoctorname(String doctorname) {
            this.doctorname = doctorname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIsReadByPatient() {
            return IsReadByPatient;
        }

        public void setIsReadByPatient(String IsReadByPatient) {
            this.IsReadByPatient = IsReadByPatient;
        }

        public String getPatientUnreadAppointment() {
            return PatientUnreadAppointment;
        }

        public void setPatientUnreadAppointment(String PatientUnreadAppointment) {
            this.PatientUnreadAppointment = PatientUnreadAppointment;
        }
    }
}
