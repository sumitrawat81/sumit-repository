package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 25/10/16.
 */
public class AddMunnalTemperatureModel {


    /**
     * Success : true
     * Message : Temperature save successfully.
     * Data : {"Temperature":[{"bodytemp_seq":"32","patient_user_seq":"50","bodytemp_value":"50.5","standard_bodytemp_min_value":"","standard_bodytemp_max_value":"","memo":"","input_user_seq":"","input_dt":"10/25/2016 10:01:47 AM","temp_unit":"","bodytemp_unit":"","SMS_seq":"","measure_day":"10/26/2016 8:02:00 PM","HasRam":"True"}]}
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
         * bodytemp_seq : 32
         * patient_user_seq : 50
         * bodytemp_value : 50.5
         * standard_bodytemp_min_value :
         * standard_bodytemp_max_value :
         * memo :
         * input_user_seq :
         * input_dt : 10/25/2016 10:01:47 AM
         * temp_unit :
         * bodytemp_unit :
         * SMS_seq :
         * measure_day : 10/26/2016 8:02:00 PM
         * HasRam : True
         */

        private List<TemperatureBean> Temperature;

        public List<TemperatureBean> getTemperature() {
            return Temperature;
        }

        public void setTemperature(List<TemperatureBean> Temperature) {
            this.Temperature = Temperature;
        }

        public static class TemperatureBean {
            private String bodytemp_seq;
            private String patient_user_seq;
            private String bodytemp_value;
            private String standard_bodytemp_min_value;
            private String standard_bodytemp_max_value;
            private String memo;
            private String input_user_seq;
            private String input_dt;
            private String temp_unit;
            private String bodytemp_unit;
            private String SMS_seq;
            private String measure_day;
            private String HasRam;

            public String getBodytemp_seq() {
                return bodytemp_seq;
            }

            public void setBodytemp_seq(String bodytemp_seq) {
                this.bodytemp_seq = bodytemp_seq;
            }

            public String getPatient_user_seq() {
                return patient_user_seq;
            }

            public void setPatient_user_seq(String patient_user_seq) {
                this.patient_user_seq = patient_user_seq;
            }

            public String getBodytemp_value() {
                return bodytemp_value;
            }

            public void setBodytemp_value(String bodytemp_value) {
                this.bodytemp_value = bodytemp_value;
            }

            public String getStandard_bodytemp_min_value() {
                return standard_bodytemp_min_value;
            }

            public void setStandard_bodytemp_min_value(String standard_bodytemp_min_value) {
                this.standard_bodytemp_min_value = standard_bodytemp_min_value;
            }

            public String getStandard_bodytemp_max_value() {
                return standard_bodytemp_max_value;
            }

            public void setStandard_bodytemp_max_value(String standard_bodytemp_max_value) {
                this.standard_bodytemp_max_value = standard_bodytemp_max_value;
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

            public String getTemp_unit() {
                return temp_unit;
            }

            public void setTemp_unit(String temp_unit) {
                this.temp_unit = temp_unit;
            }

            public String getBodytemp_unit() {
                return bodytemp_unit;
            }

            public void setBodytemp_unit(String bodytemp_unit) {
                this.bodytemp_unit = bodytemp_unit;
            }

            public String getSMS_seq() {
                return SMS_seq;
            }

            public void setSMS_seq(String SMS_seq) {
                this.SMS_seq = SMS_seq;
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
