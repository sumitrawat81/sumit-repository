package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientSkinDetailModel {


    /**
     * success : true
     * Data : [{"skin_photo_id":"16","patient_id":"99","skin_photo_original_name":"Desert.jpg","skin_photo_converted_name":"45d3d06a_16ac_427b_84b9_65a6f52c166d.jpg","created_date":"4/8/2015 12:07:44 PM","subject":"asd","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/Desert.jpg"},{"skin_photo_id":"15","patient_id":"99","skin_photo_original_name":"41fa14e8_00c2_439e_a4ba_524d5a1706f3.png","skin_photo_converted_name":"41fa14e8_00c2_439e_a4ba_524d5a1706f3.png","created_date":"4/8/2015 10:48:57 AM","subject":"dfgdfdfg","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/41fa14e8_00c2_439e_a4ba_524d5a1706f3.png"},{"skin_photo_id":"14","patient_id":"99","skin_photo_original_name":"0c5f8a80_23eb_47b7_9829_97b8262ccde2.png","skin_photo_converted_name":"0c5f8a80_23eb_47b7_9829_97b8262ccde2.png","created_date":"4/8/2015 10:48:55 AM","subject":"dfgdfgdf","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/0c5f8a80_23eb_47b7_9829_97b8262ccde2.png"},{"skin_photo_id":"13","patient_id":"99","skin_photo_original_name":"42516113_97b0_42c8_9bf6_548b6168b6ad.png","skin_photo_converted_name":"42516113_97b0_42c8_9bf6_548b6168b6ad.png","created_date":"4/8/2015 10:48:52 AM","subject":"dfgdf","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/42516113_97b0_42c8_9bf6_548b6168b6ad.png"},{"skin_photo_id":"12","patient_id":"99","skin_photo_original_name":"379c5c9c_95a3_47b0_b486_34edde4f30d8.png","skin_photo_converted_name":"379c5c9c_95a3_47b0_b486_34edde4f30d8.png","created_date":"4/7/2015 11:26:40 AM","subject":"sdsad","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/379c5c9c_95a3_47b0_b486_34edde4f30d8.png"},{"skin_photo_id":"11","patient_id":"99","skin_photo_original_name":"3fec3831_e797_4d4b_90a7_cf3236b4fdd7.png","skin_photo_converted_name":"3fec3831_e797_4d4b_90a7_cf3236b4fdd7.png","created_date":"3/21/2015 10:40:56 AM","subject":"dszf","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/3fec3831_e797_4d4b_90a7_cf3236b4fdd7.png"},{"skin_photo_id":"10","patient_id":"99","skin_photo_original_name":"54f218a3_61f1_46e8_bc7f_2522efa765cb.png","skin_photo_converted_name":"54f218a3_61f1_46e8_bc7f_2522efa765cb.png","created_date":"3/21/2015 10:40:32 AM","subject":"dszf","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/54f218a3_61f1_46e8_bc7f_2522efa765cb.png"},{"skin_photo_id":"9","patient_id":"99","skin_photo_original_name":"80efec2b_10ec_4481_b9a9_6cb7d602d772.png","skin_photo_converted_name":"80efec2b_10ec_4481_b9a9_6cb7d602d772.png","created_date":"3/21/2015 10:40:26 AM","subject":"dszf","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/80efec2b_10ec_4481_b9a9_6cb7d602d772.png"},{"skin_photo_id":"8","patient_id":"99","skin_photo_original_name":"b74f0c1e_72da_4324_ab13_f6bbc39f50d3.png","skin_photo_converted_name":"b74f0c1e_72da_4324_ab13_f6bbc39f50d3.png","created_date":"3/21/2015 10:38:34 AM","subject":"12jj","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/b74f0c1e_72da_4324_ab13_f6bbc39f50d3.png"},{"skin_photo_id":"7","patient_id":"99","skin_photo_original_name":"27974475_baa8_4653_b05f_4a5bfa8ae0fd.png","skin_photo_converted_name":"27974475_baa8_4653_b05f_4a5bfa8ae0fd.png","created_date":"3/21/2015 9:14:26 AM","subject":"trtrte","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/27974475_baa8_4653_b05f_4a5bfa8ae0fd.png"},{"skin_photo_id":"6","patient_id":"99","skin_photo_original_name":"email.png","skin_photo_converted_name":"98e7cfc6_b8f9_4da3_a1b3_84740e0163d2.png","created_date":"1/17/2015 7:10:17 AM","subject":"Skin api testing","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/email.png"},{"skin_photo_id":"5","patient_id":"99","skin_photo_original_name":"Chrysanthemum.jpg","skin_photo_converted_name":"c6d8ee3e_fd26_4651_9b6c_7f467e72bef5.jpg","created_date":"12/29/2014 5:54:22 AM","subject":"My First Subject","filename":"https://74.208.103.212:128//UploadImage/CheckSkin/Chrysanthemum.jpg"}]
     */

    private boolean success;
    /**
     * skin_photo_id : 16
     * patient_id : 99
     * skin_photo_original_name : Desert.jpg
     * skin_photo_converted_name : 45d3d06a_16ac_427b_84b9_65a6f52c166d.jpg
     * created_date : 4/8/2015 12:07:44 PM
     * subject : asd
     * filename : https://74.208.103.212:128//UploadImage/CheckSkin/Desert.jpg
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
        private String skin_photo_id;
        private String patient_id;
        private String skin_photo_original_name;
        private String skin_photo_converted_name;
        private String created_date;
        private String subject;
        private String filename;

        public String getSkin_photo_id() {
            return skin_photo_id;
        }

        public void setSkin_photo_id(String skin_photo_id) {
            this.skin_photo_id = skin_photo_id;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getSkin_photo_original_name() {
            return skin_photo_original_name;
        }

        public void setSkin_photo_original_name(String skin_photo_original_name) {
            this.skin_photo_original_name = skin_photo_original_name;
        }

        public String getSkin_photo_converted_name() {
            return skin_photo_converted_name;
        }

        public void setSkin_photo_converted_name(String skin_photo_converted_name) {
            this.skin_photo_converted_name = skin_photo_converted_name;
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
