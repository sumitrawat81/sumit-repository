package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientSummeryDetailModel {


    /**
     * Success : true
     * Data : {"BloogSugar":[{"Date":"September 21, 2016  10:22AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:22AM","bldsgr_value":"350","detail_code_nm":"","memo":"After Lunch"}],"BloodPressure":[{"Date":"September 21, 2016 10:10AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:10AM","Systolic_value":"45","Diastolic_value":"90","Pulse_value":"80","NewDateTime":"21/09/2016\\n10:10AM"}],"Temprature":[{"Date":"February 19, 2015  1:00AM","bodytemp_value":"350","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00AM"}],"Oxygen":[{"Date":"July 27, 2016  11:19AM","MeasureDate":"27/07/2016","MeasureDateTime":"11:19AM","oxy_value":"50","oxy_pulse_value":"0"}],"Weight":[{"Date":"September 21, 2016  10:26AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:26AM","weight_value":"90"}],"BMI":[{"Date":"September 21, 2016  10:26AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:26AM","BMI_value":"40"}],"Height":[{"Date":"September 23, 2016  4:38AM","MeasureDate":"23/09/2016","MeasureDateTime":" 4:38AM","Height":"121"}],"Waist":[{"Date":"September 26, 2016  6:24AM","MeasureDate":"26/09/2016","MeasureDateTime":" 6:24AM","Waist":"12"}],"ECG":[{"Date":"July 20, 2016  1:31PM","MeasureDate":"20/07/2016","MeasureDateTime":" 1:31PM","ecg_file_id":"10","patient_id":"99","ecg_file_original_name":"Better.png","ecg_file_converted_name":"f28f695d_d3dc_406a_a124_e93459d3351e.png","created_date":"7/20/2016 1:31:07 PM","subject":"test","filename":"https://74.208.103.212:128/UploadImage/ECG/Better.png"}],"ENT":[{"Date":"April 07, 2015  12:20PM","MeasureDate":"07/04/2015","MeasureDateTime":"12:20PM","throat_photo_id":"7","patient_id":"99","throat_photo_original_name":"094fe112_15a6_4182_be66_19e8b62963da.png","throat_photo_converted_name":"094fe112_15a6_4182_be66_19e8b62963da.png","created_date":"4/7/2015 12:20:42 PM","subject":"dsf","filename":"https://74.208.103.212:128/UploadImage/CheckThroat/094fe112_15a6_4182_be66_19e8b62963da.png"}],"SKIN":[{"Date":"April 08, 2015  12:07PM","MeasureDate":"08/04/2015","MeasureDateTime":"12:07PM","skin_photo_id":"16","patient_id":"99","skin_photo_original_name":"Desert.jpg","skin_photo_converted_name":"45d3d06a_16ac_427b_84b9_65a6f52c166d.jpg","created_date":"4/8/2015 12:07:44 PM","subject":"asd","filename":"https://74.208.103.212:128/UploadImage/CheckSkin/Desert.jpg"}]}
     */

    private boolean Success;
    private DataBean Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Date : September 21, 2016  10:22AM
         * MeasureDate : 21/09/2016
         * MeasureDateTime : 10:22AM
         * bldsgr_value : 350
         * detail_code_nm :
         * memo : After Lunch
         */

        private List<BloogSugarBean> BloogSugar;
        /**
         * Date : September 21, 2016 10:10AM
         * MeasureDate : 21/09/2016
         * MeasureDateTime : 10:10AM
         * Systolic_value : 45
         * Diastolic_value : 90
         * Pulse_value : 80
         * NewDateTime : 21/09/2016\n10:10AM
         */

        private List<BloodPressureBean> BloodPressure;
        /**
         * Date : February 19, 2015  1:00AM
         * bodytemp_value : 350
         * MeasureDate : 19/02/2015
         * MeasureDateTime :  1:00AM
         */

        private List<TempratureBean> Temprature;
        /**
         * Date : July 27, 2016  11:19AM
         * MeasureDate : 27/07/2016
         * MeasureDateTime : 11:19AM
         * oxy_value : 50
         * oxy_pulse_value : 0
         */

        private List<OxygenBean> Oxygen;
        /**
         * Date : September 21, 2016  10:26AM
         * MeasureDate : 21/09/2016
         * MeasureDateTime : 10:26AM
         * weight_value : 90
         */

        private List<WeightBean> Weight;
        /**
         * Date : September 21, 2016  10:26AM
         * MeasureDate : 21/09/2016
         * MeasureDateTime : 10:26AM
         * BMI_value : 40
         */

        private List<BMIBean> BMI;
        /**
         * Date : September 23, 2016  4:38AM
         * MeasureDate : 23/09/2016
         * MeasureDateTime :  4:38AM
         * Height : 121
         */

        private List<HeightBean> Height;
        /**
         * Date : September 26, 2016  6:24AM
         * MeasureDate : 26/09/2016
         * MeasureDateTime :  6:24AM
         * Waist : 12
         */

        private List<WaistBean> Waist;
        /**
         * Date : July 20, 2016  1:31PM
         * MeasureDate : 20/07/2016
         * MeasureDateTime :  1:31PM
         * ecg_file_id : 10
         * patient_id : 99
         * ecg_file_original_name : Better.png
         * ecg_file_converted_name : f28f695d_d3dc_406a_a124_e93459d3351e.png
         * created_date : 7/20/2016 1:31:07 PM
         * subject : test
         * filename : https://74.208.103.212:128/UploadImage/ECG/Better.png
         */

        private List<ECGBean> ECG;
        /**
         * Date : April 07, 2015  12:20PM
         * MeasureDate : 07/04/2015
         * MeasureDateTime : 12:20PM
         * throat_photo_id : 7
         * patient_id : 99
         * throat_photo_original_name : 094fe112_15a6_4182_be66_19e8b62963da.png
         * throat_photo_converted_name : 094fe112_15a6_4182_be66_19e8b62963da.png
         * created_date : 4/7/2015 12:20:42 PM
         * subject : dsf
         * filename : https://74.208.103.212:128/UploadImage/CheckThroat/094fe112_15a6_4182_be66_19e8b62963da.png
         */

        private List<ENTBean> ENT;
        /**
         * Date : April 08, 2015  12:07PM
         * MeasureDate : 08/04/2015
         * MeasureDateTime : 12:07PM
         * skin_photo_id : 16
         * patient_id : 99
         * skin_photo_original_name : Desert.jpg
         * skin_photo_converted_name : 45d3d06a_16ac_427b_84b9_65a6f52c166d.jpg
         * created_date : 4/8/2015 12:07:44 PM
         * subject : asd
         * filename : https://74.208.103.212:128/UploadImage/CheckSkin/Desert.jpg
         */

        private List<SKINBean> SKIN;

        public List<BloogSugarBean> getBloogSugar() {
            return BloogSugar;
        }

        public void setBloogSugar(List<BloogSugarBean> BloogSugar) {
            this.BloogSugar = BloogSugar;
        }

        public List<BloodPressureBean> getBloodPressure() {
            return BloodPressure;
        }

        public void setBloodPressure(List<BloodPressureBean> BloodPressure) {
            this.BloodPressure = BloodPressure;
        }

        public List<TempratureBean> getTemprature() {
            return Temprature;
        }

        public void setTemprature(List<TempratureBean> Temprature) {
            this.Temprature = Temprature;
        }

        public List<OxygenBean> getOxygen() {
            return Oxygen;
        }

        public void setOxygen(List<OxygenBean> Oxygen) {
            this.Oxygen = Oxygen;
        }

        public List<WeightBean> getWeight() {
            return Weight;
        }

        public void setWeight(List<WeightBean> Weight) {
            this.Weight = Weight;
        }

        public List<BMIBean> getBMI() {
            return BMI;
        }

        public void setBMI(List<BMIBean> BMI) {
            this.BMI = BMI;
        }

        public List<HeightBean> getHeight() {
            return Height;
        }

        public void setHeight(List<HeightBean> Height) {
            this.Height = Height;
        }

        public List<WaistBean> getWaist() {
            return Waist;
        }

        public void setWaist(List<WaistBean> Waist) {
            this.Waist = Waist;
        }

        public List<ECGBean> getECG() {
            return ECG;
        }

        public void setECG(List<ECGBean> ECG) {
            this.ECG = ECG;
        }

        public List<ENTBean> getENT() {
            return ENT;
        }

        public void setENT(List<ENTBean> ENT) {
            this.ENT = ENT;
        }

        public List<SKINBean> getSKIN() {
            return SKIN;
        }

        public void setSKIN(List<SKINBean> SKIN) {
            this.SKIN = SKIN;
        }

        public static class BloogSugarBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String bldsgr_value;
            private String detail_code_nm;
            private String memo;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getBldsgr_value() {
                return bldsgr_value;
            }

            public void setBldsgr_value(String bldsgr_value) {
                this.bldsgr_value = bldsgr_value;
            }

            public String getDetail_code_nm() {
                return detail_code_nm;
            }

            public void setDetail_code_nm(String detail_code_nm) {
                this.detail_code_nm = detail_code_nm;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }
        }

        public static class BloodPressureBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String Systolic_value;
            private String Diastolic_value;
            private String Pulse_value;
            private String NewDateTime;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getSystolic_value() {
                return Systolic_value;
            }

            public void setSystolic_value(String Systolic_value) {
                this.Systolic_value = Systolic_value;
            }

            public String getDiastolic_value() {
                return Diastolic_value;
            }

            public void setDiastolic_value(String Diastolic_value) {
                this.Diastolic_value = Diastolic_value;
            }

            public String getPulse_value() {
                return Pulse_value;
            }

            public void setPulse_value(String Pulse_value) {
                this.Pulse_value = Pulse_value;
            }

            public String getNewDateTime() {
                return NewDateTime;
            }

            public void setNewDateTime(String NewDateTime) {
                this.NewDateTime = NewDateTime;
            }
        }

        public static class TempratureBean {
            private String Date;
            private String bodytemp_value;
            private String MeasureDate;
            private String MeasureDateTime;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getBodytemp_value() {
                return bodytemp_value;
            }

            public void setBodytemp_value(String bodytemp_value) {
                this.bodytemp_value = bodytemp_value;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }
        }

        public static class OxygenBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String oxy_value;
            private String oxy_pulse_value;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getOxy_value() {
                return oxy_value;
            }

            public void setOxy_value(String oxy_value) {
                this.oxy_value = oxy_value;
            }

            public String getOxy_pulse_value() {
                return oxy_pulse_value;
            }

            public void setOxy_pulse_value(String oxy_pulse_value) {
                this.oxy_pulse_value = oxy_pulse_value;
            }
        }

        public static class WeightBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String weight_value;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getWeight_value() {
                return weight_value;
            }

            public void setWeight_value(String weight_value) {
                this.weight_value = weight_value;
            }
        }

        public static class BMIBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String BMI_value;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getBMI_value() {
                return BMI_value;
            }

            public void setBMI_value(String BMI_value) {
                this.BMI_value = BMI_value;
            }
        }

        public static class HeightBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String Height;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getHeight() {
                return Height;
            }

            public void setHeight(String Height) {
                this.Height = Height;
            }
        }

        public static class WaistBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String Waist;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

            public String getWaist() {
                return Waist;
            }

            public void setWaist(String Waist) {
                this.Waist = Waist;
            }
        }

        public static class ECGBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String ecg_file_id;
            private String patient_id;
            private String ecg_file_original_name;
            private String ecg_file_converted_name;
            private String created_date;
            private String subject;
            private String filename;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

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

        public static class ENTBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String throat_photo_id;
            private String patient_id;
            private String throat_photo_original_name;
            private String throat_photo_converted_name;
            private String created_date;
            private String subject;
            private String filename;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

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

        public static class SKINBean {
            private String Date;
            private String MeasureDate;
            private String MeasureDateTime;
            private String skin_photo_id;
            private String patient_id;
            private String skin_photo_original_name;
            private String skin_photo_converted_name;
            private String created_date;
            private String subject;
            private String filename;
            private String isShow;

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getDate() {
                return Date;
            }

            public void setDate(String Date) {
                this.Date = Date;
            }

            public String getMeasureDate() {
                return MeasureDate;
            }

            public void setMeasureDate(String MeasureDate) {
                this.MeasureDate = MeasureDate;
            }

            public String getMeasureDateTime() {
                return MeasureDateTime;
            }

            public void setMeasureDateTime(String MeasureDateTime) {
                this.MeasureDateTime = MeasureDateTime;
            }

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
}
