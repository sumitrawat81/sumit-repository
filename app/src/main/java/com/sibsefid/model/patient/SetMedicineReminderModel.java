package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/11/16.
 */
public class SetMedicineReminderModel {


    /**
     * Success : true
     * Message : Medicine reminder set.
     * Data : {"ReminderList":[{"ReminderId":"1","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Saturday","NotesOfUse":"test Notes Of Use","IsTaken":"True","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"11/19/2016 12:14:09 PM","patientID":"99","remindDate":"11/19/2016 12:00:00 AM","remindDates":"19/11/2016","takendatetime":"11/19/2016 8:00:00 AM"},{"ReminderId":"2","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Sunday","NotesOfUse":"test Notes Of Use","IsTaken":"True","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"11/19/2016 12:14:13 PM","patientID":"99","remindDate":"11/20/2016 12:00:00 AM","remindDates":"20/11/2016","takendatetime":"11/20/2016 8:00:00 AM"},{"ReminderId":"9","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"10:00 AM","DaysOfWeek":"Monday","NotesOfUse":"tetsasa","IsTaken":"False","createDate":"11/21/2016 10:07:43 AM","IsTakenDate":"","patientID":"99","remindDate":"11/21/2016 12:00:00 AM","remindDates":"21/11/2016","takendatetime":"11/21/2016 10:00:00 AM"},{"ReminderId":"3","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Tuesday","NotesOfUse":"test Notes Of Use","IsTaken":"True","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"11/19/2016 12:57:56 PM","patientID":"99","remindDate":"11/22/2016 12:00:00 AM","remindDates":"22/11/2016","takendatetime":"11/22/2016 8:00:00 AM"},{"ReminderId":"13","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 ml ","TimeTaken":"9:30 AM","DaysOfWeek":"Tuesday","NotesOfUse":"10:30 AM doses","IsTaken":"False","createDate":"11/22/2016 12:01:31 PM","IsTakenDate":"","patientID":"99","remindDate":"11/22/2016 12:00:00 AM","remindDates":"22/11/2016","takendatetime":"11/22/2016 9:30:00 AM"},{"ReminderId":"10","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"10:00 AM","DaysOfWeek":"Tuesday","NotesOfUse":"tetsasa","IsTaken":"False","createDate":"11/21/2016 10:07:43 AM","IsTakenDate":"","patientID":"99","remindDate":"11/22/2016 12:00:00 AM","remindDates":"22/11/2016","takendatetime":"11/22/2016 10:00:00 AM"},{"ReminderId":"11","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"10:00 AM","DaysOfWeek":"Wednesday","NotesOfUse":"tetsasa","IsTaken":"False","createDate":"11/21/2016 10:07:43 AM","IsTakenDate":"","patientID":"99","remindDate":"11/23/2016 12:00:00 AM","remindDates":"23/11/2016","takendatetime":"11/23/2016 10:00:00 AM"},{"ReminderId":"4","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Thursday","NotesOfUse":"test Notes Of Use","IsTaken":"True","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"11/19/2016 12:58:08 PM","patientID":"99","remindDate":"11/24/2016 12:00:00 AM","remindDates":"24/11/2016","takendatetime":"11/24/2016 8:00:00 AM"},{"ReminderId":"19","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"250mg","TimeTaken":"08:30 AM","DaysOfWeek":"Friday","NotesOfUse":"this is the testing","IsTaken":"False","createDate":"11/24/2016 11:29:41 AM","IsTakenDate":"","patientID":"99","remindDate":"11/25/2016 12:00:00 AM","remindDates":"25/11/2016","takendatetime":"11/25/2016 8:30:00 AM"},{"ReminderId":"14","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 ml ","TimeTaken":"9:30 AM","DaysOfWeek":"Friday","NotesOfUse":"10:30 AM doses","IsTaken":"False","createDate":"11/22/2016 12:01:31 PM","IsTakenDate":"","patientID":"99","remindDate":"11/25/2016 12:00:00 AM","remindDates":"25/11/2016","takendatetime":"11/25/2016 9:30:00 AM"},{"ReminderId":"5","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Saturday","NotesOfUse":"test Notes Of Use","IsTaken":"True","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"11/22/2016 12:03:14 PM","patientID":"99","remindDate":"11/26/2016 12:00:00 AM","remindDates":"26/11/2016","takendatetime":"11/26/2016 8:00:00 AM"},{"ReminderId":"15","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 ml ","TimeTaken":"9:30 AM","DaysOfWeek":"Saturday","NotesOfUse":"10:30 AM doses","IsTaken":"False","createDate":"11/22/2016 12:01:31 PM","IsTakenDate":"","patientID":"99","remindDate":"11/26/2016 12:00:00 AM","remindDates":"26/11/2016","takendatetime":"11/26/2016 9:30:00 AM"},{"ReminderId":"6","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Sunday","NotesOfUse":"test Notes Of Use","IsTaken":"False","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"","patientID":"99","remindDate":"11/27/2016 12:00:00 AM","remindDates":"27/11/2016","takendatetime":"11/27/2016 8:00:00 AM"},{"ReminderId":"16","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 ml ","TimeTaken":"9:30 AM","DaysOfWeek":"Sunday","NotesOfUse":"10:30 AM doses","IsTaken":"False","createDate":"11/22/2016 12:01:31 PM","IsTakenDate":"","patientID":"99","remindDate":"11/27/2016 12:00:00 AM","remindDates":"27/11/2016","takendatetime":"11/27/2016 9:30:00 AM"},{"ReminderId":"7","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 tab","TimeTaken":"08:00 AM","DaysOfWeek":"Tuesday","NotesOfUse":"test Notes Of Use","IsTaken":"False","createDate":"11/19/2016 12:13:36 PM","IsTakenDate":"","patientID":"99","remindDate":"11/29/2016 12:00:00 AM","remindDates":"29/11/2016","takendatetime":"11/29/2016 8:00:00 AM"},{"ReminderId":"17","MedicineID":"78","MedicineName":"Combiflom","DrugDosase":"1 ml ","TimeTaken":"9:30 AM","DaysOfWeek":"Tuesday","NotesOfUse":"10:30 AM doses","IsTaken":"False","createDate":"11/22/2016 12:01:31 PM","IsTakenDate":"","patientID":"99","remindDate":"11/29/2016 12:00:00 AM","remindDates":"29/11/2016","takendatetime":"11/29/2016 9:30:00 AM"}]}
     */

    private boolean Success;
    private String Message;
    private DataBean Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * ReminderId : 1
         * MedicineID : 78
         * MedicineName : Combiflom
         * DrugDosase : 1 tab
         * TimeTaken : 08:00 AM
         * DaysOfWeek : Saturday
         * NotesOfUse : test Notes Of Use
         * IsTaken : True
         * createDate : 11/19/2016 12:13:36 PM
         * IsTakenDate : 11/19/2016 12:14:09 PM
         * patientID : 99
         * remindDate : 11/19/2016 12:00:00 AM
         * remindDates : 19/11/2016
         * takendatetime : 11/19/2016 8:00:00 AM
         */

        private List<ReminderListBean> ReminderList;

        public List<ReminderListBean> getReminderList() {
            return ReminderList;
        }

        public void setReminderList(List<ReminderListBean> ReminderList) {
            this.ReminderList = ReminderList;
        }

        public static class ReminderListBean {
            private String ReminderId;
            private String MedicineID;
            private String MedicineName;
            private String DrugDosase;
            private String TimeTaken;
            private String DaysOfWeek;
            private String NotesOfUse;
            private String IsTaken;
            private String createDate;
            private String IsTakenDate;
            private String patientID;
            private String remindDate;
            private String remindDates;
            private String takendatetime;

            public String getReminderId() {
                return ReminderId;
            }

            public void setReminderId(String ReminderId) {
                this.ReminderId = ReminderId;
            }

            public String getMedicineID() {
                return MedicineID;
            }

            public void setMedicineID(String MedicineID) {
                this.MedicineID = MedicineID;
            }

            public String getMedicineName() {
                return MedicineName;
            }

            public void setMedicineName(String MedicineName) {
                this.MedicineName = MedicineName;
            }

            public String getDrugDosase() {
                return DrugDosase;
            }

            public void setDrugDosase(String DrugDosase) {
                this.DrugDosase = DrugDosase;
            }

            public String getTimeTaken() {
                return TimeTaken;
            }

            public void setTimeTaken(String TimeTaken) {
                this.TimeTaken = TimeTaken;
            }

            public String getDaysOfWeek() {
                return DaysOfWeek;
            }

            public void setDaysOfWeek(String DaysOfWeek) {
                this.DaysOfWeek = DaysOfWeek;
            }

            public String getNotesOfUse() {
                return NotesOfUse;
            }

            public void setNotesOfUse(String NotesOfUse) {
                this.NotesOfUse = NotesOfUse;
            }

            public String getIsTaken() {
                return IsTaken;
            }

            public void setIsTaken(String IsTaken) {
                this.IsTaken = IsTaken;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getIsTakenDate() {
                return IsTakenDate;
            }

            public void setIsTakenDate(String IsTakenDate) {
                this.IsTakenDate = IsTakenDate;
            }

            public String getPatientID() {
                return patientID;
            }

            public void setPatientID(String patientID) {
                this.patientID = patientID;
            }

            public String getRemindDate() {
                return remindDate;
            }

            public void setRemindDate(String remindDate) {
                this.remindDate = remindDate;
            }

            public String getRemindDates() {
                return remindDates;
            }

            public void setRemindDates(String remindDates) {
                this.remindDates = remindDates;
            }

            public String getTakendatetime() {
                return takendatetime;
            }

            public void setTakendatetime(String takendatetime) {
                this.takendatetime = takendatetime;
            }
        }
    }
}
