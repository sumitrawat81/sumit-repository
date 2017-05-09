package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientEcgDetailModel {


    /**
     * success : true
     * Data : [{"ecg_file_id":"10","patient_id":"99","ecg_file_original_name":"Better.png","ecg_file_converted_name":"f28f695d_d3dc_406a_a124_e93459d3351e.png","created_date":"7/20/2016 1:31:07 PM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/Better.png"},{"ecg_file_id":"9","patient_id":"99","ecg_file_original_name":"adminpanel.pdf","ecg_file_converted_name":"JVBERi0xLjUKJeLjz9MKMSAwIG9iago8PCAKICAgL1R5cGUgL0NhdGFsb2cKICAgL1BhZ2VzIDIg MCBSCiAgIC9QYWdlTGF5b3V","created_date":"1/17/2015 6:58:40 AM","subject":"Ecg Api Testing","filename":"https://74.208.103.212:128//UploadImage/ECG/adminpanel.pdf"},{"ecg_file_id":"8","patient_id":"99","ecg_file_original_name":"23-12-2014 01-21-MOC updates doc_23-12-14.docx","ecg_file_converted_name":"51a9e656_3e4d_40ee_a370_f94f23f7cda1.docx","created_date":"12/25/2014 7:56:35 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/23-12-2014 01-21-MOC updates doc_23-12-14.docx"},{"ecg_file_id":"7","patient_id":"99","ecg_file_original_name":"23-12-2014 01-21-MOC updates doc_23-12-14.docx","ecg_file_converted_name":"0db55447_e90e_48b1_b0ba_51e5cfa8c7c1.docx","created_date":"12/25/2014 7:54:40 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/23-12-2014 01-21-MOC updates doc_23-12-14.docx"},{"ecg_file_id":"6","patient_id":"99","ecg_file_original_name":"23-12-2014 01-21-MOC updates doc_23-12-14.docx","ecg_file_converted_name":"59ae108f_a42d_4f40_8316_5c645e22c14e.docx","created_date":"12/25/2014 7:53:37 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/23-12-2014 01-21-MOC updates doc_23-12-14.docx"},{"ecg_file_id":"5","patient_id":"99","ecg_file_original_name":"30-08-2014 12-23-MOC issues 29-8.docx","ecg_file_converted_name":"7f8e7462_1f3b_43c9_a719_3fec6bec484a.docx","created_date":"9/1/2014 11:17:54 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/30-08-2014 12-23-MOC issues 29-8.docx"},{"ecg_file_id":"4","patient_id":"99","ecg_file_original_name":"01-08-2014 01-27-error-chhavi-01-08-2014.docx","ecg_file_converted_name":"48024cff_6e7f_4410_bf3f_aa3a70b3d6e9.docx","created_date":"9/1/2014 11:17:42 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/01-08-2014 01-27-error-chhavi-01-08-2014.docx"},{"ecg_file_id":"3","patient_id":"99","ecg_file_original_name":"VC Module ---7 august.docx","ecg_file_converted_name":"c621951e_a46f_47d1_8e78_bdce5982055f.docx","created_date":"9/1/2014 11:17:32 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/VC Module ---7 august.docx"},{"ecg_file_id":"2","patient_id":"99","ecg_file_original_name":"ec_quickstartguide.pdf","ecg_file_converted_name":"f4a3e865_c6e7_499c_8277_63f4170b0e76.pdf","created_date":"9/1/2014 11:17:20 AM","subject":"test","filename":"https://74.208.103.212:128//UploadImage/ECG/ec_quickstartguide.pdf"}]
     */

    private boolean success;
    /**
     * ecg_file_id : 10
     * patient_id : 99
     * ecg_file_original_name : Better.png
     * ecg_file_converted_name : f28f695d_d3dc_406a_a124_e93459d3351e.png
     * created_date : 7/20/2016 1:31:07 PM
     * subject : test
     * filename : https://74.208.103.212:128//UploadImage/ECG/Better.png
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
        private String ecg_file_id;
        private String patient_id;
        private String ecg_file_original_name;
        private String ecg_file_converted_name;
        private String created_date;
        private String subject;
        private String filename;

        public String getEcg_file_id() {
            return ecg_file_id;
        }

        public void setEcg_file_id(String ecg_file_id) {
            this.ecg_file_id = ecg_file_id;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getEcg_file_original_name() {
            return ecg_file_original_name;
        }

        public void setEcg_file_original_name(String ecg_file_original_name) {
            this.ecg_file_original_name = ecg_file_original_name;
        }

        public String getEcg_file_converted_name() {
            return ecg_file_converted_name;
        }

        public void setEcg_file_converted_name(String ecg_file_converted_name) {
            this.ecg_file_converted_name = ecg_file_converted_name;
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
