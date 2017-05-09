package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 21/9/16.
 */
public class PatientFamiliyMembersModel {


    /**
     * success : true
     * Data : [{"id":"8","patientid":"485","first_name":"ram","last_name":"das","email":"ram@gmail.com","address":"bffdfs","country":"0","state":"99","city":"delhi","postalcode":"248100","phone":"999786691","dob":"8/3/1990 12:00:00 AM","gender":"1","relationship":"5","createdate":"9/21/2016 6:53:29 PM","name":"ram das","relation":"Ms.","gendername":"Male"}]
     */

    private boolean success;
    /**
     * id : 8
     * patientid : 485
     * first_name : ram
     * last_name : das
     * email : ram@gmail.com
     * address : bffdfs
     * country : 0
     * state : 99
     * city : delhi
     * postalcode : 248100
     * phone : 999786691
     * dob : 8/3/1990 12:00:00 AM
     * gender : 1
     * relationship : 5
     * createdate : 9/21/2016 6:53:29 PM
     * name : ram das
     * relation : Ms.
     * gendername : Male
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
        private String id;
        private String patientid;
        private String first_name;
        private String last_name;
        private String email;
        private String address;
        private String country;
        private String state;
        private String city;
        private String postalcode;
        private String phone;
        private String dob;
        private String gender;
        private String relationship;
        private String createdate;
        private String name;
        private String relation;
        private String gendername;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPatientid() {
            return patientid;
        }

        public void setPatientid(String patientid) {
            this.patientid = patientid;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getGendername() {
            return gendername;
        }

        public void setGendername(String gendername) {
            this.gendername = gendername;
        }
    }
}
