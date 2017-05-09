package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientHeartRateModel {


    /**
     * success : true
     * Data : [{"Date":"March 25, 2014 1:32AM","MeasureDate":"25/03/2014","MeasureDateTime":" 1:32AM","Systolic_value":"140","Diastolic_value":"60","Pulse_value":"120"},{"Date":"March 30, 2014 9:32AM","MeasureDate":"30/03/2014","MeasureDateTime":" 9:32AM","Systolic_value":"140","Diastolic_value":"60","Pulse_value":"70"},{"Date":"April 05, 2014 11:32AM","MeasureDate":"05/04/2014","MeasureDateTime":"11:32AM","Systolic_value":"140","Diastolic_value":"60","Pulse_value":"40"},{"Date":"April 05, 2014 12:32PM","MeasureDate":"05/04/2014","MeasureDateTime":"12:32PM","Systolic_value":"120","Diastolic_value":"40","Pulse_value":"120"},{"Date":"May 22, 2014 10:24PM","MeasureDate":"22/05/2014","MeasureDateTime":"10:24PM","Systolic_value":"88","Diastolic_value":"88","Pulse_value":"88"},{"Date":"May 31, 2014 10:24AM","MeasureDate":"31/05/2014","MeasureDateTime":"10:24AM","Systolic_value":"55","Diastolic_value":"55","Pulse_value":"55"},{"Date":"May 31, 2014 10:24PM","MeasureDate":"31/05/2014","MeasureDateTime":"10:24PM","Systolic_value":"120","Diastolic_value":"175","Pulse_value":"55"},{"Date":"October 16, 2014 12:31PM","MeasureDate":"16/10/2014","MeasureDateTime":"12:31PM","Systolic_value":"9","Diastolic_value":"10","Pulse_value":"11"},{"Date":"October 16, 2014 1:00PM","MeasureDate":"16/10/2014","MeasureDateTime":" 1:00PM","Systolic_value":"12","Diastolic_value":"13","Pulse_value":"14"},{"Date":"November 07, 2014 11:00AM","MeasureDate":"07/11/2014","MeasureDateTime":"11:00AM","Systolic_value":"13.2","Diastolic_value":"13.2","Pulse_value":"13.2"},{"Date":"November 07, 2014 11:01AM","MeasureDate":"07/11/2014","MeasureDateTime":"11:01AM","Systolic_value":"13.2","Diastolic_value":"13.2","Pulse_value":"13.2"},{"Date":"December 12, 2014 1:00AM","MeasureDate":"12/12/2014","MeasureDateTime":" 1:00AM","Systolic_value":"10","Diastolic_value":"10","Pulse_value":"10"},{"Date":"December 12, 2014 2:00AM","MeasureDate":"12/12/2014","MeasureDateTime":" 2:00AM","Systolic_value":"10","Diastolic_value":"10","Pulse_value":"10"},{"Date":"December 12, 2014 2:00PM","MeasureDate":"12/12/2014","MeasureDateTime":" 2:00PM","Systolic_value":"10","Diastolic_value":"10","Pulse_value":"10"},{"Date":"February 19, 2015 1:00AM","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00AM","Systolic_value":"100","Diastolic_value":"190","Pulse_value":"122"},{"Date":"February 19, 2015 1:00PM","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00PM","Systolic_value":"300","Diastolic_value":"400","Pulse_value":"100"},{"Date":"September 08, 2016 5:00PM","MeasureDate":"08/09/2016","MeasureDateTime":" 5:00PM","Systolic_value":"212","Diastolic_value":"123","Pulse_value":"321"},{"Date":"September 12, 2016 2:20PM","MeasureDate":"12/09/2016","MeasureDateTime":" 2:20PM","Systolic_value":"12","Diastolic_value":"121","Pulse_value":"21"},{"Date":"September 21, 2016 10:10AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:10AM","Systolic_value":"45","Diastolic_value":"90","Pulse_value":"80"}]
     */

    private boolean success;
    /**
     * Date : March 25, 2014 1:32AM
     * MeasureDate : 25/03/2014
     * MeasureDateTime :  1:32AM
     * Systolic_value : 140
     * Diastolic_value : 60
     * Pulse_value : 120
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
        private String Date;
        private String MeasureDate;
        private String MeasureDateTime;
        private String Systolic_value;
        private String Diastolic_value;
        private String Pulse_value;

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
    }
}
