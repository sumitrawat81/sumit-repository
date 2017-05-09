package com.sibsefid.model.patient;

import java.util.ArrayList;

/**
 * Created by ubuntu on 22/9/16.
 */
public class RelationshipType {


    /**
     * success : true
     * Data : [{"detail_code":"61","detail_code_nm":"Parent","master_code":"relation","input_dt":"7/1/2016 7:44:16 AM","modify_dt":"7/1/2016 7:44:16 AM"},{"detail_code":"62","detail_code_nm":"Grandparent","master_code":"relation","input_dt":"7/1/2016 7:44:16 AM","modify_dt":"7/1/2016 7:44:16 AM"},{"detail_code":"63","detail_code_nm":"Sibling","master_code":"relation","input_dt":"7/1/2016 7:44:16 AM","modify_dt":"7/1/2016 7:44:16 AM"},{"detail_code":"64","detail_code_nm":"Child","master_code":"relation","input_dt":"7/1/2016 7:44:16 AM","modify_dt":"7/1/2016 7:44:16 AM"},{"detail_code":"65","detail_code_nm":"Other","master_code":"relation","input_dt":"7/1/2016 7:44:16 AM","modify_dt":"7/1/2016 7:44:16 AM"}]
     */

    private boolean success;
    /**
     * detail_code : 61
     * detail_code_nm : Parent
     * master_code : relation
     * input_dt : 7/1/2016 7:44:16 AM
     * modify_dt : 7/1/2016 7:44:16 AM
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
        private String detail_code;
        private String detail_code_nm;
        private String master_code;
        private String input_dt;
        private String modify_dt;

        public String getDetail_code() {
            return detail_code;
        }

        public void setDetail_code(String detail_code) {
            this.detail_code = detail_code;
        }

        public String getDetail_code_nm() {
            return detail_code_nm;
        }

        public void setDetail_code_nm(String detail_code_nm) {
            this.detail_code_nm = detail_code_nm;
        }

        public String getMaster_code() {
            return master_code;
        }

        public void setMaster_code(String master_code) {
            this.master_code = master_code;
        }

        public String getInput_dt() {
            return input_dt;
        }

        public void setInput_dt(String input_dt) {
            this.input_dt = input_dt;
        }

        public String getModify_dt() {
            return modify_dt;
        }

        public void setModify_dt(String modify_dt) {
            this.modify_dt = modify_dt;
        }
    }
}
