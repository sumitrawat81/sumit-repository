package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientBMIDetailModel {


    /**
     * success : true
     * Data : [{"Date":"September 21, 2016  10:26AM","MeasureDate":"21/09/2016","MeasureDateTime":"10:26AM","BMI_value":"40"},{"Date":"September 19, 2016  12:00AM","MeasureDate":"19/09/2016","MeasureDateTime":"12:00AM","BMI_value":"10.82"},{"Date":"September 12, 2016  2:15PM","MeasureDate":"12/09/2016","MeasureDateTime":" 2:15PM","BMI_value":"16.07"},{"Date":"July 18, 2016  12:00AM","MeasureDate":"18/07/2016","MeasureDateTime":"12:00AM","BMI_value":"4.94"},{"Date":"May 28, 2015  3:39PM","MeasureDate":"28/05/2015","MeasureDateTime":" 3:39PM","BMI_value":"2.47"},{"Date":"March 09, 2015  2:16PM","MeasureDate":"09/03/2015","MeasureDateTime":" 2:16PM","BMI_value":"49.38"},{"Date":"March 09, 2015  2:07PM","MeasureDate":"09/03/2015","MeasureDateTime":" 2:07PM","BMI_value":"49.38"},{"Date":"February 19, 2015  1:00AM","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00AM","BMI_value":"69444.44"},{"Date":"June 24, 2014  3:02PM","MeasureDate":"24/06/2014","MeasureDateTime":" 3:02PM","BMI_value":"0.03"},{"Date":"June 24, 2014  3:01PM","MeasureDate":"24/06/2014","MeasureDateTime":" 3:01PM","BMI_value":"0.1"},{"Date":"June 04, 2014  2:52PM","MeasureDate":"04/06/2014","MeasureDateTime":" 2:52PM","BMI_value":"15"}]
     */

    private boolean success;
    /**
     * Date : September 21, 2016  10:26AM
     * MeasureDate : 21/09/2016
     * MeasureDateTime : 10:26AM
     * BMI_value : 40
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
        private String BMI_value;

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
}
