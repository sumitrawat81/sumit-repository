package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientEntDetailModel {


    /**
     * success : true
     * Data : [{"throat_photo_id":"7","patient_id":"99","throat_photo_original_name":"094fe112_15a6_4182_be66_19e8b62963da.png","throat_photo_converted_name":"094fe112_15a6_4182_be66_19e8b62963da.png","created_date":"4/7/2015 12:20:42 PM","subject":"dsf","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/094fe112_15a6_4182_be66_19e8b62963da.png"},{"throat_photo_id":"6","patient_id":"99","throat_photo_original_name":"7cc719fe_21ca_420e_ae2b_61d713b672e4.png","throat_photo_converted_name":"7cc719fe_21ca_420e_ae2b_61d713b672e4.png","created_date":"3/21/2015 1:54:08 PM","subject":"dsfds","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/7cc719fe_21ca_420e_ae2b_61d713b672e4.png"},{"throat_photo_id":"5","patient_id":"99","throat_photo_original_name":"0a6dfa70_c0f7_4bbd_9837_b2c82bf9fdbb.png","throat_photo_converted_name":"0a6dfa70_c0f7_4bbd_9837_b2c82bf9fdbb.png","created_date":"3/21/2015 10:37:52 AM","subject":"hi","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/0a6dfa70_c0f7_4bbd_9837_b2c82bf9fdbb.png"},{"throat_photo_id":"4","patient_id":"99","throat_photo_original_name":"2798de03_12a6_4ef4_94bb_6354fe79e4ca.png","throat_photo_converted_name":"2798de03_12a6_4ef4_94bb_6354fe79e4ca.png","created_date":"3/21/2015 10:35:08 AM","subject":"151","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/2798de03_12a6_4ef4_94bb_6354fe79e4ca.png"},{"throat_photo_id":"3","patient_id":"99","throat_photo_original_name":"a730d48d_212e_465b_a669_90eeb496accb.png","throat_photo_converted_name":"a730d48d_212e_465b_a669_90eeb496accb.png","created_date":"3/21/2015 9:15:58 AM","subject":"12121","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/a730d48d_212e_465b_a669_90eeb496accb.png"},{"throat_photo_id":"2","patient_id":"99","throat_photo_original_name":"6f54eb8f_fe2d_4145_a8bb_c583058f9122.png","throat_photo_converted_name":"6f54eb8f_fe2d_4145_a8bb_c583058f9122.png","created_date":"3/21/2015 9:15:30 AM","subject":"12121","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/6f54eb8f_fe2d_4145_a8bb_c583058f9122.png"},{"throat_photo_id":"1","patient_id":"99","throat_photo_original_name":"2f4ecc8c_5411_47b6_89eb_769c73cc9c26.png","throat_photo_converted_name":"2f4ecc8c_5411_47b6_89eb_769c73cc9c26.png","created_date":"3/21/2015 9:15:13 AM","subject":"12121","filename":"https://74.208.103.212:128//UploadImage/CheckThroat/2f4ecc8c_5411_47b6_89eb_769c73cc9c26.png"}]
     */

    private boolean success;
    /**
     * throat_photo_id : 7
     * patient_id : 99
     * throat_photo_original_name : 094fe112_15a6_4182_be66_19e8b62963da.png
     * throat_photo_converted_name : 094fe112_15a6_4182_be66_19e8b62963da.png
     * created_date : 4/7/2015 12:20:42 PM
     * subject : dsf
     * filename : https://74.208.103.212:128//UploadImage/CheckThroat/094fe112_15a6_4182_be66_19e8b62963da.png
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
        private String throat_photo_id;
        private String patient_id;
        private String throat_photo_original_name;
        private String throat_photo_converted_name;
        private String created_date;
        private String subject;
        private String filename;

        public String getThroat_photo_id() {
            return throat_photo_id;
        }

        public void setThroat_photo_id(String throat_photo_id) {
            this.throat_photo_id = throat_photo_id;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getThroat_photo_original_name() {
            return throat_photo_original_name;
        }

        public void setThroat_photo_original_name(String throat_photo_original_name) {
            this.throat_photo_original_name = throat_photo_original_name;
        }

        public String getThroat_photo_converted_name() {
            return throat_photo_converted_name;
        }

        public void setThroat_photo_converted_name(String throat_photo_converted_name) {
            this.throat_photo_converted_name = throat_photo_converted_name;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}
