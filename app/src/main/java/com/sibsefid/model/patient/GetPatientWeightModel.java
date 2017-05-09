package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 23/9/16.
 */
public class GetPatientWeightModel {


    /**
     * success : true
     * Data : [{"Date":"September 21, 2016  10:26AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:26AM","weight_value":"90"},{"Date":"September 19, 2016  12:00AM","MeasureDate":"19/09/2016","MeasureDateTime":"12:00AM","weight_value":"25"},{"Date":"September 12, 2016  2:15PM","MeasureDate":"12/09/2016","MeasureDateTime":" 2:15PM","weight_value":"58"},{"Date":"July 18, 2016  12:00AM","MeasureDate":"18/07/2016","MeasureDateTime":"12:00AM","weight_value":"100"},{"Date":"May 28, 2015  3:39PM","MeasureDate":"28/05/2015","MeasureDateTime":" 3:39PM","weight_value":"50"},{"Date":"March 09, 2015  2:16PM","MeasureDate":"09/03/2015","MeasureDateTime":" 2:16PM","weight_value":"1000"},{"Date":"March 09, 2015  2:07PM","MeasureDate":"09/03/2015","MeasureDateTime":" 2:07PM","weight_value":"1000"},{"Date":"February 19, 2015  1:00AM","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00AM","weight_value":"1000"},{"Date":"April 05, 2014  11:32AM","MeasureDate":"05/04/2014","MeasureDateTime":"11:32AM","weight_value":"65"},{"Date":"April 05, 2014  11:32AM","MeasureDate":"05/04/2014","MeasureDateTime":"11:32AM","weight_value":"75"},{"Date":"March 06, 2014  11:32AM","MeasureDate":"06/03/2014","MeasureDateTime":"11:32AM","weight_value":"85"}]
     */

    private boolean success;
    /**
     * Date : September 21, 2016  10:26AM
     * MeasureDate : 21/09/2016
     * MeasureDateTime : 10:26AM
     * weight_value : 90
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
        private String weight_value;

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
}
