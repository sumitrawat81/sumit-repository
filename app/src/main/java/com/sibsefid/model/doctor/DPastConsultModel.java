package com.sibsefid.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 14/9/16.
 */
public class DPastConsultModel {


    /**
     * success : true
     * Data : [{"newstatus":"","appointId":"57","patientsId":"99","status":"","Date":"12/09/2016","time":"7:20PM","AppointDate":"12/09/2016","AppointTime":"12:58PM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Completed","appointId":"26","patientsId":"457","status":"2","Date":"05/09/2016","time":"5:40PM","AppointDate":"05/09/2016","AppointTime":"11:19AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Completed","appointId":"25","patientsId":"457","status":"2","Date":"05/09/2016","time":"4:50PM","AppointDate":"05/09/2016","AppointTime":"10:30AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Completed","appointId":"24","patientsId":"420","status":"2","Date":"03/09/2016","time":"6:30PM","AppointDate":"03/09/2016","AppointTime":"12:19PM","name":"Mr. Jean Marc","doctorname":"Mr. Neeraj Arora","patient_contact":"17744201054","patient_Email":"tjmshop@gmail.com"},{"newstatus":"Completed","appointId":"23","patientsId":"457","status":"2","Date":"02/09/2016","time":"6:40PM","AppointDate":"02/09/2016","AppointTime":"12:26PM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Completed","appointId":"22","patientsId":"457","status":"2","Date":"02/09/2016","time":"6:30PM","AppointDate":"02/09/2016","AppointTime":"12:23PM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Approved","appointId":"21","patientsId":"457","status":"3","Date":"02/09/2016","time":"6:10PM","AppointDate":"02/09/2016","AppointTime":"12:06PM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Completed","appointId":"20","patientsId":"99","status":"2","Date":"02/09/2016","time":"6:00PM","AppointDate":"02/09/2016","AppointTime":"11:42AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"","appointId":"19","patientsId":"420","status":"","Date":"02/09/2016","time":"4:50PM","AppointDate":"02/09/2016","AppointTime":"10:43AM","name":"Mr. Jean Marc","doctorname":"Mr. Neeraj Arora","patient_contact":"17744201054","patient_Email":"tjmshop@gmail.com"},{"newstatus":"Approved","appointId":"18","patientsId":"99","status":"3","Date":"02/09/2016","time":"4:40PM","AppointDate":"02/09/2016","AppointTime":"10:34AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Completed","appointId":"17","patientsId":"457","status":"2","Date":"02/09/2016","time":"1:10PM","AppointDate":"02/09/2016","AppointTime":"6:24AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Approved","appointId":"15","patientsId":"457","status":"3","Date":"02/09/2016","time":"12:10PM","AppointDate":"02/09/2016","AppointTime":"6:00AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Approved","appointId":"14","patientsId":"457","status":"3","Date":"02/09/2016","time":"11:50AM","AppointDate":"02/09/2016","AppointTime":"5:43AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"Approved","appointId":"13","patientsId":"457","status":"3","Date":"02/09/2016","time":"11:40AM","AppointDate":"02/09/2016","AppointTime":"5:34AM","name":"Ms. Ayushi Mittal","doctorname":"Ms. Neeraj Arora","patient_contact":"918764377170","patient_Email":"ayushi@brsoftech.com"},{"newstatus":"","appointId":"12","patientsId":"99","status":"","Date":"02/09/2016","time":"11:10AM","AppointDate":"02/09/2016","AppointTime":"4:37AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Completed","appointId":"11","patientsId":"99","status":"2","Date":"01/09/2016","time":"7:20PM","AppointDate":"01/09/2016","AppointTime":"12:43PM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Approved","appointId":"10","patientsId":"99","status":"3","Date":"26/08/2016","time":"7:20PM","AppointDate":"26/08/2016","AppointTime":"2:07PM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"","appointId":"5","patientsId":"99","status":"","Date":"17/08/2016","time":"5:40PM","AppointDate":"17/08/2016","AppointTime":"10:37AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"","appointId":"6","patientsId":"99","status":"","Date":"17/08/2016","time":"4:50PM","AppointDate":"17/08/2016","AppointTime":"11:02AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"","appointId":"4","patientsId":"99","status":"","Date":"17/08/2016","time":"10:50AM","AppointDate":"17/08/2016","AppointTime":"5:47AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Approved","appointId":"2","patientsId":"99","status":"3","Date":"10/08/2016","time":"11:30AM","AppointDate":"10/08/2016","AppointTime":"6:05AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"},{"newstatus":"Completed","appointId":"1","patientsId":"99","status":"2","Date":"09/08/2016","time":"10:30PM","AppointDate":"09/08/2016","AppointTime":"7:11AM","name":"Mr. Neeraj Arora","doctorname":"Mr. Neeraj Arora","patient_contact":"919461460209","patient_Email":"neeraj@brsoftech.com"}]
     */

    private boolean success;
    /**
     * newstatus :
     * appointId : 57
     * patientsId : 99
     * status :
     * Date : 12/09/2016
     * time : 7:20PM
     * AppointDate : 12/09/2016
     * AppointTime : 12:58PM
     * name : Mr. Neeraj Arora
     * doctorname : Mr. Neeraj Arora
     * patient_contact : 919461460209
     * patient_Email : neeraj@brsoftech.com
     */

    @SerializedName("Data")
    private ArrayList<DPastConsults> dpastcosults;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DPastConsults> getDpastcosults() {
        return dpastcosults;
    }

    public void setDpastcosults(ArrayList<DPastConsults> dpastcosults) {
        this.dpastcosults = dpastcosults;
    }

    public static class DPastConsults {
        private String patientsId;
        private String date;
        private String time;
        private String PatientName;
        private String email;
        private String Status;
        private String appointid;
        private String Reason;
        private String AppointmentMode;

        private String family_name;
        private String QuickBloxId;
        private String user_photo;

        private String doctorId;
        private String DoctorName;
        private String CommunicationNote;
        private String Physicianorder;

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String doctorName) {
            DoctorName = doctorName;
        }

        public String getCommunicationNote() {
            return CommunicationNote;
        }

        public void setCommunicationNote(String communicationNote) {
            CommunicationNote = communicationNote;
        }

        public String getPhysicianorder() {
            return Physicianorder;
        }

        public void setPhysicianorder(String physicianorder) {
            Physicianorder = physicianorder;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getQuickBloxId() {
            return QuickBloxId;
        }

        public void setQuickBloxId(String quickBloxId) {
            QuickBloxId = quickBloxId;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }

        public String getAppointmentMode() {
            return AppointmentMode;
        }

        public void setAppointmentMode(String appointmentMode) {
            AppointmentMode = appointmentMode;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            Reason = reason;
        }

        public String getPatientsId() {
            return patientsId;
        }

        public void setPatientsId(String patientsId) {
            this.patientsId = patientsId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String patientName) {
            PatientName = patientName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getAppointid() {
            return appointid;
        }

        public void setAppointid(String appointid) {
            this.appointid = appointid;
        }
    }
}
