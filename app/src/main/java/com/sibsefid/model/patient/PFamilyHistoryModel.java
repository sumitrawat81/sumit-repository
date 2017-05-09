package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 23/9/16.
 */
public class PFamilyHistoryModel {

    /**
     * success : true
     * Data : [{"id":"1","familyhistory":"Asthma","patientid":"99","value":"False","RelationId":"0"},{"id":"2","familyhistory":"Cancer","patientid":"99","value":"True","RelationId":"64"},{"id":"3","familyhistory":"Cystic fibrosis","patientid":"99","value":"True","RelationId":"63"},{"id":"4","familyhistory":"Diabetes","patientid":"99","value":"True","RelationId":"63"},{"id":"5","familyhistory":"Early/unexplained death","patientid":"99","value":"False","RelationId":"0"},{"id":"6","familyhistory":"Elevated cholestrol","patientid":"99","value":"False","RelationId":"0"},{"id":"7","familyhistory":"Heart disease","patientid":"99","value":"False","RelationId":"0"},{"id":"8","familyhistory":"High blood pressure","patientid":"99","value":"False","RelationId":"0"},{"id":"9","familyhistory":"Metabolic disease","patientid":"99","value":"False","RelationId":"0"},{"id":"10","familyhistory":"Seasonal allergies","patientid":"99","value":"False","RelationId":"0"},{"id":"11","familyhistory":"Sickle cell anemia","patientid":"99","value":"False","RelationId":"0"},{"id":"12","familyhistory":"Stroke","patientid":"99","value":"True","RelationId":"62"},{"id":"13","familyhistory":"Thyroid disease","patientid":"99","value":"False","RelationId":"0"},{"id":"106","familyhistory":"hxjbgdfhjg","patientid":"99","value":"True","RelationId":"65"}]
     */

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
