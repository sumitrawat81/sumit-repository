package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 25/10/16.
 */
public class SetWeightMannullyModel {

    /**
     * Success : true
     * Message : Weight List
     * Data : {"Weight":[{"weight_seq":"1029","patient_user_seq":"50","measure_eat_code":"C009002","weight_value":"150","height_value":"1","standard_height_value":"1","standard_weight_min_value":"150","standard_weight_max_value":"150","BMI_value":"150","standard_BMI_min_value":"150","standard_BMI_max_value":"150","memo":"","input_user_seq":"5","input_dt":"10/25/2016 7:15:01 AM","fat_value":"","standard_fat_min_value":"","standard_fat_max_value":"","weight_unit":"kg","SMS_seq":"","height_unit":"","measure_day":"10/26/2016 12:00:00 AM","HasRam":"True"}]}
     */

    private boolean Success;
    private String Message;
    private DataBean Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * weight_seq : 1029
         * patient_user_seq : 50
         * measure_eat_code : C009002
         * weight_value : 150
         * height_value : 1
         * standard_height_value : 1
         * standard_weight_min_value : 150
         * standard_weight_max_value : 150
         * BMI_value : 150
         * standard_BMI_min_value : 150
         * standard_BMI_max_value : 150
         * memo :
         * input_user_seq : 5
         * input_dt : 10/25/2016 7:15:01 AM
         * fat_value :
         * standard_fat_min_value :
         * standard_fat_max_value :
         * weight_unit : kg
         * SMS_seq :
         * height_unit :
         * measure_day : 10/26/2016 12:00:00 AM
         * HasRam : True
         */

        private List<WeightBean> Weight;

        public List<WeightBean> getWeight() {
            return Weight;
        }

        public void setWeight(List<WeightBean> Weight) {
            this.Weight = Weight;
        }

        public static class WeightBean {
            private String weight_seq;
            private String patient_user_seq;
            private String measure_eat_code;
            private String weight_value;
            private String height_value;
            private String standard_height_value;
            private String standard_weight_min_value;
            private String standard_weight_max_value;
            private String BMI_value;
            private String standard_BMI_min_value;
            private String standard_BMI_max_value;
            private String memo;
            private String input_user_seq;
            private String input_dt;
            private String fat_value;
            private String standard_fat_min_value;
            private String standard_fat_max_value;
            private String weight_unit;
            private String SMS_seq;
            private String height_unit;
            private String measure_day;
            private String HasRam;

            public String getWeight_seq() {
                return weight_seq;
            }

            public void setWeight_seq(String weight_seq) {
                this.weight_seq = weight_seq;
            }

            public String getPatient_user_seq() {
                return patient_user_seq;
            }

            public void setPatient_user_seq(String patient_user_seq) {
                this.patient_user_seq = patient_user_seq;
            }

            public String getMeasure_eat_code() {
                return measure_eat_code;
            }

            public void setMeasure_eat_code(String measure_eat_code) {
                this.measure_eat_code = measure_eat_code;
            }

            public String getWeight_value() {
                return weight_value;
            }

            public void setWeight_value(String weight_value) {
                this.weight_value = weight_value;
            }

            public String getHeight_value() {
                return height_value;
            }

            public void setHeight_value(String height_value) {
                this.height_value = height_value;
            }

            public String getStandard_height_value() {
                return standard_height_value;
            }

            public void setStandard_height_value(String standard_height_value) {
                this.standard_height_value = standard_height_value;
            }

            public String getStandard_weight_min_value() {
                return standard_weight_min_value;
            }

            public void setStandard_weight_min_value(String standard_weight_min_value) {
                this.standard_weight_min_value = standard_weight_min_value;
            }

            public String getStandard_weight_max_value() {
                return standard_weight_max_value;
            }

            public void setStandard_weight_max_value(String standard_weight_max_value) {
                this.standard_weight_max_value = standard_weight_max_value;
            }

            public String getBMI_value() {
                return BMI_value;
            }

            public void setBMI_value(String BMI_value) {
                this.BMI_value = BMI_value;
            }

            public String getStandard_BMI_min_value() {
                return standard_BMI_min_value;
            }

            public void setStandard_BMI_min_value(String standard_BMI_min_value) {
                this.standard_BMI_min_value = standard_BMI_min_value;
            }

            public String getStandard_BMI_max_value() {
                return standard_BMI_max_value;
            }

            public void setStandard_BMI_max_value(String standard_BMI_max_value) {
                this.standard_BMI_max_value = standard_BMI_max_value;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getInput_user_seq() {
                return input_user_seq;
            }

            public void setInput_user_seq(String input_user_seq) {
                this.input_user_seq = input_user_seq;
            }

            public String getInput_dt() {
                return input_dt;
            }

            public void setInput_dt(String input_dt) {
                this.input_dt = input_dt;
            }

            public String getFat_value() {
                return fat_value;
            }

            public void setFat_value(String fat_value) {
                this.fat_value = fat_value;
            }

            public String getStandard_fat_min_value() {
                return standard_fat_min_value;
            }

            public void setStandard_fat_min_value(String standard_fat_min_value) {
                this.standard_fat_min_value = standard_fat_min_value;
            }

            public String getStandard_fat_max_value() {
                return standard_fat_max_value;
            }

            public void setStandard_fat_max_value(String standard_fat_max_value) {
                this.standard_fat_max_value = standard_fat_max_value;
            }

            public String getWeight_unit() {
                return weight_unit;
            }

            public void setWeight_unit(String weight_unit) {
                this.weight_unit = weight_unit;
            }

            public String getSMS_seq() {
                return SMS_seq;
            }

            public void setSMS_seq(String SMS_seq) {
                this.SMS_seq = SMS_seq;
            }

            public String getHeight_unit() {
                return height_unit;
            }

            public void setHeight_unit(String height_unit) {
                this.height_unit = height_unit;
            }

            public String getMeasure_day() {
                return measure_day;
            }

            public void setMeasure_day(String measure_day) {
                this.measure_day = measure_day;
            }

            public String getHasRam() {
                return HasRam;
            }

            public void setHasRam(String HasRam) {
                this.HasRam = HasRam;
            }
        }
    }
}
