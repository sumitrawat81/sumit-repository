package com.sibsefid.model.doctor;

import java.util.ArrayList;

/**
 * Created by ubuntu on 4/10/16.
 */
public class GetPersonalNotesModel {


    /**
     * success : true
     * Data : [{"SrNo":"8","DoctorId":"50","CreatedDate":"9/5/2016 11:46:29 AM","Message":"hello ","UpdateDate":"1/1/1900 12:00:00 AM","appointid":"20","IsDelete":"False","CreatedDates":"9/5/2016 5:16:29 PM","CreatedDate1":"9/5/2016 5:16:29 PM","date":"Appointment on 02/09/2016","time":"at 6:00PM","status":"Status: Completed","name":"Patient: RAjKumar Sharma"},{"SrNo":"6","DoctorId":"50","CreatedDate":"8/10/2016 11:21:14 AM","Message":"Special consideration given to the doctor","UpdateDate":"1/1/1900 12:00:00 AM","appointid":"2","IsDelete":"False","CreatedDates":"8/10/2016 4:51:14 PM","CreatedDate1":"8/10/2016 4:51:14 PM","date":"Appointment on 10/08/2016","time":"at 11:30AM","status":"Status: Approved","name":"Patient: RAjKumar Sharma"},{"SrNo":"5","DoctorId":"50","CreatedDate":"8/10/2016 6:06:32 AM","Message":"this is personal note dated 10 august 11.35 AM","UpdateDate":"1/1/1900 12:00:00 AM","appointid":"2","IsDelete":"False","CreatedDates":"8/10/2016 11:36:32 AM","CreatedDate1":"8/10/2016 11:36:32 AM","date":"Appointment on 10/08/2016","time":"at 11:30AM","status":"Status: Approved","name":"Patient: RAjKumar Sharma"},{"SrNo":"4","DoctorId":"50","CreatedDate":"8/10/2016 5:10:58 AM","Message":"this is personal note dated 10 august 10.40 AM IST","UpdateDate":"1/1/1900 12:00:00 AM","appointid":"1","IsDelete":"False","CreatedDates":"8/10/2016 10:40:58 AM","CreatedDate1":"8/10/2016 10:40:58 AM","date":"Appointment on 09/08/2016","time":"at 10:30PM","status":"Status: Completed","name":"Patient: RAjKumar Sharma"}]
     */

    private boolean success;
    /**
     * SrNo : 8
     * DoctorId : 50
     * CreatedDate : 9/5/2016 11:46:29 AM
     * Message : hello
     * UpdateDate : 1/1/1900 12:00:00 AM
     * appointid : 20
     * IsDelete : False
     * CreatedDates : 9/5/2016 5:16:29 PM
     * CreatedDate1 : 9/5/2016 5:16:29 PM
     * date : Appointment on 02/09/2016
     * time : at 6:00PM
     * status : Status: Completed
     * name : Patient: RAjKumar Sharma
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
        private String SrNo;
        private String DoctorId;
        private String CreatedDate;
        private String Message;
        private String UpdateDate;
        private String appointid;
        private String IsDelete;
        private String CreatedDates;
        private String CreatedDate1;
        private String date;
        private String time;
        private String status;
        private String name;

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

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String UpdateDate) {
            this.UpdateDate = UpdateDate;
        }

        public String getAppointid() {
            return appointid;
        }

        public void setAppointid(String appointid) {
            this.appointid = appointid;
        }

        public String getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(String IsDelete) {
            this.IsDelete = IsDelete;
        }

        public String getCreatedDates() {
            return CreatedDates;
        }

        public void setCreatedDates(String CreatedDates) {
            this.CreatedDates = CreatedDates;
        }

        public String getCreatedDate1() {
            return CreatedDate1;
        }

        public void setCreatedDate1(String CreatedDate1) {
            this.CreatedDate1 = CreatedDate1;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
