package com.sibsefid.model.patient;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 23/9/16.
 */
public class PLifeStyleModel {


    /**
     * success : true
     * Data : [{"id":"1","Qus":"Do you get at least 7 hours of sleep daily?","patientans":"1"},{"id":"2","Qus":"Do you exercise?","patientans":"0"},{"id":"3","Qus":"Do you get yearly flu shots?","patientans":"0"},{"id":"4","Qus":"Do you use recreational drugs?","patientans":"0"},{"id":"5","Qus":"Do you work around hazardous or toxic chemicals?","patientans":"1"},{"id":"6","Qus":"Are you or have you ever been disabled?","patientans":"0"}]
     */

    private boolean success;
    /**
     * id : 1
     * Qus : Do you get at least 7 hours of sleep daily?
     * patientans : 1
     */

    @SerializedName("Data")
    private ArrayList<PLifeStyleBean> plifestylebean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PLifeStyleBean> getPlifestylebean() {
        return plifestylebean;
    }

    public void setPlifestylebean(ArrayList<PLifeStyleBean> plifestylebean) {
        this.plifestylebean = plifestylebean;
    }

    public static class PLifeStyleBean {
        boolean isLoading;
        private String id;
        private String Qus;
        private String patientans;

        public boolean isLoading() {
            return isLoading;
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPatientInInt() {
            if (TextUtils.isEmpty(patientans))
                patientans = "0";

            return Integer.valueOf(patientans);
        }

        public String getQus() {
            return Qus;
        }

        public void setQus(String Qus) {
            this.Qus = Qus;
        }

        public String getPatientans() {
            return patientans;
        }

        public void setPatientans(String patientans) {
            this.patientans = patientans;
        }
    }
}
