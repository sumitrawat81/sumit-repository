package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 23/9/16.
 */
public class GetPatientTemperatureModel {


    /**
     * success : true
     * Data : [{"Date":"April 05, 2014  7:14AM","bodytemp_value":"24.6","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"36.4","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"35.9","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"33.5","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"34.6","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"36.9","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"36.9","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"April 05, 2014  7:14AM","bodytemp_value":"36.9","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM"},{"Date":"May 30, 2014  5:35PM","bodytemp_value":"0","MeasureDate":"30/05/2014","MeasureDateTime":" 5:35PM"},{"Date":"May 30, 2014  5:35PM","bodytemp_value":"0","MeasureDate":"30/05/2014","MeasureDateTime":" 5:35PM"},{"Date":"May 31, 2014  11:00AM","bodytemp_value":"37.5","MeasureDate":"31/05/2014","MeasureDateTime":"11:00AM"},{"Date":"February 19, 2015  1:00AM","bodytemp_value":"350","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00AM"}]
     */

    private boolean success;
    /**
     * Date : April 05, 2014  7:14AM
     * bodytemp_value : 24.6
     * MeasureDate : 05/04/2014
     * MeasureDateTime :  7:14AM
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
        private String bodytemp_value;
        private String MeasureDate;
        private String MeasureDateTime;

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
}
