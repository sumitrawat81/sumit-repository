package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 25/10/16.
 */
public class AddMannullyBloodOxygenModel {


    /**
     * Success : true
     * Message : Blood oxygen save successfully.
     * Data : {"BloodOxygen":[{"oxy_seq":"64","patient_user_seq":"50","oxy_value":"50.5","oxy_pulse_value":"0","input_user_seq":"50","input_dt":"10/25/2016 9:28:33 AM","measure_day":"10/26/2016 8:02:00 PM","HasRam":"True"}]}
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
         * oxy_seq : 64
         * patient_user_seq : 50
         * oxy_value : 50.5
         * oxy_pulse_value : 0
         * input_user_seq : 50
         * input_dt : 10/25/2016 9:28:33 AM
         * measure_day : 10/26/2016 8:02:00 PM
         * HasRam : True
         */

        private List<BloodOxygenBean> BloodOxygen;

        public List<BloodOxygenBean> getBloodOxygen() {
            return BloodOxygen;
        }

        public void setBloodOxygen(List<BloodOxygenBean> BloodOxygen) {
            this.BloodOxygen = BloodOxygen;
        }

        public static class BloodOxygenBean {
            private String oxy_seq;
            private String patient_user_seq;
            private String oxy_value;
            private String oxy_pulse_value;
            private String input_user_seq;
            private String input_dt;
            private String measure_day;
            private String HasRam;

            public String getOxy_seq() {
                return oxy_seq;
            }

            public void setOxy_seq(String oxy_seq) {
                this.oxy_seq = oxy_seq;
            }

            public String getPatient_user_seq() {
                return patient_user_seq;
            }

            public void setPatient_user_seq(String patient_user_seq) {
                this.patient_user_seq = patient_user_seq;
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
