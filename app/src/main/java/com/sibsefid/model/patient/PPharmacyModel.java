package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 1/10/16.
 */
public class PPharmacyModel {

    /**
     * success : true
     * Data : [{"StateNamePharmacy":"","CountrynamePharmacy":"","State_pharmacy":"","country_pharmacy":"","pharmacy_name":"","address1_pharmacy":"","city_pharmacy":"","postcode_pharmacy":"","phone_pharmacy":""}]
     */

    private boolean success;
    /**
     * StateNamePharmacy :
     * CountrynamePharmacy :
     * State_pharmacy :
     * country_pharmacy :
     * pharmacy_name :
     * address1_pharmacy :
     * city_pharmacy :
     * postcode_pharmacy :
     * phone_pharmacy :
     */

    @SerializedName("Data")
    private ArrayList<AddressBean> addressbean;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AddressBean> getAddressbean() {
        return addressbean;
    }

    public void setAddressbean(ArrayList<AddressBean> addressbean) {
        this.addressbean = addressbean;
    }

    public static class AddressBean {
        private String StateNamePharmacy;
        private String CountrynamePharmacy;
        private String State_pharmacy;
        private String country_pharmacy;
        private String pharmacy_name;
        private String address1_pharmacy;
        private String city_pharmacy;
        private String postcode_pharmacy;
        private String phone_pharmacy;

        public String getStateNamePharmacy() {
            return StateNamePharmacy;
        }

        public void setStateNamePharmacy(String StateNamePharmacy) {
            this.StateNamePharmacy = StateNamePharmacy;
        }

        public String getCountrynamePharmacy() {
            return CountrynamePharmacy;
        }

        public void setCountrynamePharmacy(String CountrynamePharmacy) {
            this.CountrynamePharmacy = CountrynamePharmacy;
        }

        public String getState_pharmacy() {
            return State_pharmacy;
        }

        public void setState_pharmacy(String State_pharmacy) {
            this.State_pharmacy = State_pharmacy;
        }

        public String getCountry_pharmacy() {
            return country_pharmacy;
        }

        public void setCountry_pharmacy(String country_pharmacy) {
            this.country_pharmacy = country_pharmacy;
        }

        public String getPharmacy_name() {
            return pharmacy_name;
        }

        public void setPharmacy_name(String pharmacy_name) {
            this.pharmacy_name = pharmacy_name;
        }

        public String getAddress1_pharmacy() {
            return address1_pharmacy;
        }

        public void setAddress1_pharmacy(String address1_pharmacy) {
            this.address1_pharmacy = address1_pharmacy;
        }

        public String getCity_pharmacy() {
            return city_pharmacy;
        }

        public void setCity_pharmacy(String city_pharmacy) {
            this.city_pharmacy = city_pharmacy;
        }

        public String getPostcode_pharmacy() {
            return postcode_pharmacy;
        }

        public void setPostcode_pharmacy(String postcode_pharmacy) {
            this.postcode_pharmacy = postcode_pharmacy;
        }

        public String getPhone_pharmacy() {
            return phone_pharmacy;
        }

        public void setPhone_pharmacy(String phone_pharmacy) {
            this.phone_pharmacy = phone_pharmacy;
        }
    }
}
