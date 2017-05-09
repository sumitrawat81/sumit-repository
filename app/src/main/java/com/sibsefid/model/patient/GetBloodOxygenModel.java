package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class GetBloodOxygenModel {


    /**
     * success : true
     * Data : [{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"83"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"71"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"83"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"78"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"84"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"82"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"97"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"98"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"101"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"99"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"101"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"88"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"94"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"73"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"98","oxy_pulse_value":"80"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"100","oxy_pulse_value":"58"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"81"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"76"},{"Date":"April 05, 2014  7:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 7:14AM","oxy_value":"99","oxy_pulse_value":"64"},{"Date":"April 05, 2014  8:14AM","MeasureDate":"05/04/2014","MeasureDateTime":" 8:14AM","oxy_value":"98","oxy_pulse_value":"84"},{"Date":"October 31, 2014  9:12AM","MeasureDate":"31/10/2014","MeasureDateTime":" 9:12AM","oxy_value":"12","oxy_pulse_value":"12"},{"Date":"November 06, 2014  11:00AM","MeasureDate":"06/11/2014","MeasureDateTime":"11:00AM","oxy_value":"12.2","oxy_pulse_value":"12.2"},{"Date":"November 07, 2014  11:00AM","MeasureDate":"07/11/2014","MeasureDateTime":"11:00AM","oxy_value":"14.2","oxy_pulse_value":"14.2"},{"Date":"February 19, 2015  1:00PM","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00PM","oxy_value":"500","oxy_pulse_value":"100"},{"Date":"July 27, 2016  11:19AM","MeasureDate":"27/07/2016","MeasureDateTime":"11:19AM","oxy_value":"50","oxy_pulse_value":"0"}]
     */

    private boolean success;
    /**
     * Date : April 05, 2014  7:14AM
     * MeasureDate : 05/04/2014
     * MeasureDateTime :  7:14AM
     * oxy_value : 99
     * oxy_pulse_value : 83
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
        private String oxy_value;
        private String oxy_pulse_value;

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
}
