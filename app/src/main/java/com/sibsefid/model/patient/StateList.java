package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 12/9/16.
 */
public class StateList {


    /**
     * success : true
     * Data : [{"detail_code":"1","detail_code_nm":"Badakhshan"},{"detail_code":"2","detail_code_nm":"Badghis"},{"detail_code":"3","detail_code_nm":"Baghlan"},{"detail_code":"4","detail_code_nm":"Balkh"},{"detail_code":"5","detail_code_nm":"Bamian"},{"detail_code":"6","detail_code_nm":"Farah"},{"detail_code":"7","detail_code_nm":"Faryab"},{"detail_code":"8","detail_code_nm":"Ghazni"},{"detail_code":"9","detail_code_nm":"Ghowr"},{"detail_code":"10","detail_code_nm":"Helmand"},{"detail_code":"11","detail_code_nm":"Herat"},{"detail_code":"12","detail_code_nm":"Jowzjan"},{"detail_code":"13","detail_code_nm":"Kabul"},{"detail_code":"14","detail_code_nm":"Kandahar"},{"detail_code":"15","detail_code_nm":"Kapisa"},{"detail_code":"16","detail_code_nm":"Khost"},{"detail_code":"17","detail_code_nm":"Konar"},{"detail_code":"18","detail_code_nm":"Kondoz"},{"detail_code":"19","detail_code_nm":"Laghman"},{"detail_code":"20","detail_code_nm":"Lowgar"},{"detail_code":"21","detail_code_nm":"Nangrahar"},{"detail_code":"22","detail_code_nm":"Nimruz"},{"detail_code":"23","detail_code_nm":"Nurestan"},{"detail_code":"24","detail_code_nm":"Oruzgan"},{"detail_code":"25","detail_code_nm":"Paktia"},{"detail_code":"26","detail_code_nm":"Paktika"},{"detail_code":"27","detail_code_nm":"Parwan"},{"detail_code":"28","detail_code_nm":"Samangan"},{"detail_code":"29","detail_code_nm":"Sar-e Pol"},{"detail_code":"30","detail_code_nm":"Takhar"},{"detail_code":"31","detail_code_nm":"Wardak"},{"detail_code":"32","detail_code_nm":"Zabol"},{"detail_code":"6125","detail_code_nm":"Daykundi"},{"detail_code":"6126","detail_code_nm":"Panjshir"}]
     */

    private boolean success;
    /**
     * detail_code : 1
     * detail_code_nm : Badakhshan
     */

    @SerializedName("Data")
    private ArrayList<City> city;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<City> getCity() {
        return city;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    public static class City {
        private String detail_code;
        private String detail_code_nm;

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
    }
}
