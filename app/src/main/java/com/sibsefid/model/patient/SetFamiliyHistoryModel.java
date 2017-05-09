package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ubuntu on 17/12/16.
 */
public class SetFamiliyHistoryModel {
    private boolean success;
    /**
     * id : 1
     * familyhistory : Asthma
     * patientid : 99
     * value : False
     * RelationId : 0
     */

    @SerializedName("Data")
    private ArrayList<PFamilyHistoryBean> PFamilyHistoryBean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PFamilyHistoryBean> getPFamilyHistoryBean() {
        return PFamilyHistoryBean;
    }

    public void setPFamilyHistoryBean(ArrayList<PFamilyHistoryBean> PFamilyHistoryBean) {
        this.PFamilyHistoryBean = PFamilyHistoryBean;
    }

    public static class PFamilyHistoryBean {
        boolean isLoading;
        private String id;
        private String familyhistory;
        private String patientid;

        public int getPostition() {
            return postition;
        }

        public void setPostition(int postition) {
            this.postition = postition;
        }

        private String value;
        private String RelationId;
        private int postition=0;

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

        public String getFamilyhistory() {
            return familyhistory;
        }

        public void setFamilyhistory(String familyhistory) {
            this.familyhistory = familyhistory;
        }

        public String getPatientid() {
            return patientid;
        }

        public void setPatientid(String patientid) {
            this.patientid = patientid;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRelationId() {
            return RelationId;
        }

        public void setRelationId(String RelationId) {
            this.RelationId = RelationId;
        }
    }
}
