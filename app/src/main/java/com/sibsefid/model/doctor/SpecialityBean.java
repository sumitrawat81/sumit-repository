package com.sibsefid.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 13/9/16.
 */
public class SpecialityBean {


    /**
     * success : true
     * Data : [{"specid":"1","specaility":"Internal Medicine","IsDelete":"False"},{"specid":"2","specaility":"Family Practice","IsDelete":"False"},{"specid":"3","specaility":"Family Medicine","IsDelete":"False"},{"specid":"4","specaility":"Emergency Medicine","IsDelete":"False"},{"specid":"5","specaility":"Pediatrics","IsDelete":"False"},{"specid":"6","specaility":"Approved Clinical Supervisor","IsDelete":"False"},{"specid":"7","specaility":"Counselor","IsDelete":"False"},{"specid":"9","specaility":"cancer specialist","IsDelete":"False"},{"specid":"10","specaility":"kidney specialist","IsDelete":"False"}]
     */

    private boolean success;
    /**
     * specid : 1
     * specaility : Internal Medicine
     * IsDelete : False
     */

    @SerializedName("Data")
    private ArrayList<Speciality> speciality;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Speciality> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(ArrayList<Speciality> speciality) {
        this.speciality = speciality;
    }

    public static class Speciality {
        private String specid;
        private String specaility;
        private String IsDelete;

        public String getSpecid() {
            return specid;
        }

        public void setSpecid(String specid) {
            this.specid = specid;
        }

        public String getSpecaility() {
            return specaility;
        }

        public void setSpecaility(String specaility) {
            this.specaility = specaility;
        }

        public String getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(String IsDelete) {
            this.IsDelete = IsDelete;
        }
    }
}
