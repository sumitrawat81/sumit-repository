package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 21/9/16.
 */
public class BillingInformationModel {


    /**
     * success : true
     * Data : [{"Id":"3","UserId":"485","NameOnCard":"sumit singh","CardNo":"1234567898654567","CVV":"244","ExpirationMonth":"01","ExpirationYear":"2016","BillingAddress1":"dehradun","BillingAddress2":"uttarakhand","StateId":"99","City":"dehradun","Zip":"","IsInsurance":"False","InsuranceCompany":"","InsuranceNo":"","CreateDate":"9/21/2016 4:09:22 AM","IsActive":"True","IsDelete":"False"}]
     */

    private boolean success;
    /**
     * Id : 3
     * UserId : 485
     * NameOnCard : sumit singh
     * CardNo : 1234567898654567
     * CVV : 244
     * ExpirationMonth : 01
     * ExpirationYear : 2016
     * BillingAddress1 : dehradun
     * BillingAddress2 : uttarakhand
     * StateId : 99
     * City : dehradun
     * Zip :
     * IsInsurance : False
     * InsuranceCompany :
     * InsuranceNo :
     * CreateDate : 9/21/2016 4:09:22 AM
     * IsActive : True
     * IsDelete : False
     * State_nm : Rajasthan
     * country_nm : India
     */

    private List<DataBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String Id;
        private int UserId;
        private String NameOnCard;
        private String CardNo;
        private String CVV;
        private String ExpirationMonth;
        private String ExpirationYear;
        private String BillingAddress1;
        private String BillingAddress2;
        private int StateId;
        private String City;
        private String Zip;
        private String IsInsurance;
        private String InsuranceCompany;
        private String InsuranceNo;
        private String CreateDate;
        private String IsActive;
        private String IsDelete;
        private String country_nm;

        public int getCountry() {
            return Country;
        }

        public void setCountry(int country) {
            Country = country;
        }

        private int Country;

        public String getState_nm() {
            return State_nm;
        }

        public void setState_nm(String state_nm) {
            State_nm = state_nm;
        }

        public String getCountry_nm() {
            return country_nm;
        }

        public void setCountry_nm(String country_nm) {
            this.country_nm = country_nm;
        }

        private String State_nm;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getNameOnCard() {
            return NameOnCard;
        }

        public void setNameOnCard(String NameOnCard) {
            this.NameOnCard = NameOnCard;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getCVV() {
            return CVV;
        }

        public void setCVV(String CVV) {
            this.CVV = CVV;
        }

        public String getExpirationMonth() {
            return ExpirationMonth;
        }

        public void setExpirationMonth(String ExpirationMonth) {
            this.ExpirationMonth = ExpirationMonth;
        }

        public String getExpirationYear() {
            return ExpirationYear;
        }

        public void setExpirationYear(String ExpirationYear) {
            this.ExpirationYear = ExpirationYear;
        }

        public String getBillingAddress1() {
            return BillingAddress1;
        }

        public void setBillingAddress1(String BillingAddress1) {
            this.BillingAddress1 = BillingAddress1;
        }

        public String getBillingAddress2() {
            return BillingAddress2;
        }

        public void setBillingAddress2(String BillingAddress2) {
            this.BillingAddress2 = BillingAddress2;
        }

        public int getStateId() {
            return StateId;
        }

        public void setStateId(int StateId) {
            this.StateId = StateId;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getZip() {
            return Zip;
        }

        public void setZip(String Zip) {
            this.Zip = Zip;
        }

        public String getIsInsurance() {
            return IsInsurance;
        }

        public void setIsInsurance(String IsInsurance) {
            this.IsInsurance = IsInsurance;
        }

        public String getInsuranceCompany() {
            return InsuranceCompany;
        }

        public void setInsuranceCompany(String InsuranceCompany) {
            this.InsuranceCompany = InsuranceCompany;
        }

        public String getInsuranceNo() {
            return InsuranceNo;
        }

        public void setInsuranceNo(String InsuranceNo) {
            this.InsuranceNo = InsuranceNo;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getIsActive() {
            return IsActive;
        }

        public void setIsActive(String IsActive) {
            this.IsActive = IsActive;
        }

        public String getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(String IsDelete) {
            this.IsDelete = IsDelete;
        }
    }
}
