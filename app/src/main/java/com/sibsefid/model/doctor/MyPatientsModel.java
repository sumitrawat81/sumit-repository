package com.sibsefid.model.doctor;

import java.util.ArrayList;

/**
 * Created by ubuntu on 4/10/16.
 */
public class MyPatientsModel {


    /**
     * success : true
     * Data : [{"patientname":"Mr. jean marc ","patientId":"420","appointId":"96"},{"patientname":"Mr. RAjKumar Sharma ","patientId":"99","appointId":"98"},{"patientname":"Ms. ayushi mittal ","patientId":"457","appointId":"63"}]
     */

    private boolean success;
    /**
     * patientname : Mr. jean marc
     * patientId : 420
     * appointId : 96
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
        private String patientname;
        private String patientId;
        private String appointId;

        public String getPatientname() {
            return patientname;
        }

        public void setPatientname(String patientname) {
            this.patientname = patientname;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getAppointId() {
            return appointId;
        }

        public void setAppointId(String appointId) {
            this.appointId = appointId;
        }
    }
}
