package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 25/10/16.
 */
public class AddManulyBloodPressureModel {


    /**
     * Success : true
     * Message : Blood pressure save successfully.
     * Data : {"BloodPressure":[{"bldprsr_seq":"39","measure_day":"10/26/2016 8:02:00 PM","patient_user_seq":"50","systolic_value":"50.5","diastolic_value":"54","pulse_value":"22","HasRam":"True"}]}
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
         * bldprsr_seq : 39
         * measure_day : 10/26/2016 8:02:00 PM
         * patient_user_seq : 50
         * systolic_value : 50.5
         * diastolic_value : 54
         * pulse_value : 22
         * HasRam : True
         */

        private List<BloodPressureBean> BloodPressure;

        public List<BloodPressureBean> getBloodPressure() {
            return BloodPressure;
        }

        public void setBloodPressure(List<BloodPressureBean> BloodPressure) {
            this.BloodPressure = BloodPressure;
        }

        public static class BloodPressureBean {
            private String bldprsr_seq;
            private String measure_day;
            private String patient_user_seq;
            private String systolic_value;
            private String diastolic_value;
            private String pulse_value;
            private String HasRam;

            public String getBldprsr_seq() {
                return bldprsr_seq;
            }

            public void setBldprsr_seq(String bldprsr_seq) {
                this.bldprsr_seq = bldprsr_seq;
            }

            public String getMeasure_day() {
                return measure_day;
            }

            public void setMeasure_day(String measure_day) {
                this.measure_day = measure_day;
            }

            public String getPatient_user_seq() {
                return patient_user_seq;
            }

            public void setPatient_user_seq(String patient_user_seq) {
                this.patient_user_seq = patient_user_seq;
            }

            public String getSystolic_value() {
                return systolic_value;
            }

            public void setSystolic_value(String systolic_value) {
                this.systolic_value = systolic_value;
            }

            public String getDiastolic_value() {
                return diastolic_value;
            }

            public void setDiastolic_value(String diastolic_value) {
                this.diastolic_value = diastolic_value;
            }

            public String getPulse_value() {
                return pulse_value;
            }

            public void setPulse_value(String pulse_value) {
                this.pulse_value = pulse_value;
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
