package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class GetPatientBloodGlucoseModel {


    /**
     * success : true
     * Data : [{"Date":"September 21, 2016  10:22AM","MeasureDateTime1":"10:22:00","MeasureDate":"21/09/2016","MeasureDateTime":"10:22AM","bldsgr_valueRam":"350 (RAM)","bldsgr_value":"350","bldsgr_value1":"350","detail_code_nm":"","memo":"After Lunch","NewDateTime":"21/09/2016\\n10:22AM","HasRam":" (RAM)","measure_day":"9/21/2016 10:22:00 AM"},{"Date":"February 19, 2015  2:00PM","MeasureDateTime1":"14:00:00","MeasureDate":"19/02/2015","MeasureDateTime":" 2:00PM","bldsgr_valueRam":"400 (RAM)","bldsgr_value":"400","bldsgr_value1":"400","detail_code_nm":"","memo":"After Breakfast","NewDateTime":"19/02/2015\\n 2:00PM","HasRam":" (RAM)","measure_day":"2/19/2015 2:00:00 PM"},{"Date":"February 19, 2015  1:00PM","MeasureDateTime1":"13:00:00","MeasureDate":"19/02/2015","MeasureDateTime":" 1:00PM","bldsgr_valueRam":"400 (RAM)","bldsgr_value":"400","bldsgr_value1":"400","detail_code_nm":"","memo":"Before Breakfast","NewDateTime":"19/02/2015\\n 1:00PM","HasRam":" (RAM)","measure_day":"2/19/2015 1:00:00 PM"},{"Date":"February 12, 2015  1:00AM","MeasureDateTime1":"01:00:00","MeasureDate":"12/02/2015","MeasureDateTime":" 1:00AM","bldsgr_valueRam":"400 (RAM)","bldsgr_value":"400","bldsgr_value1":"400","detail_code_nm":"","memo":"Before Breakfast","NewDateTime":"12/02/2015\\n 1:00AM","HasRam":" (RAM)","measure_day":"2/12/2015 1:00:00 AM"},{"Date":"June 04, 2014  2:42PM","MeasureDateTime1":"14:42:00","MeasureDate":"04/06/2014","MeasureDateTime":" 2:42PM","bldsgr_valueRam":"165 (RAM)","bldsgr_value":"165","bldsgr_value1":"165","detail_code_nm":"","memo":"After Dinner","NewDateTime":"04/06/2014\\n 2:42PM","HasRam":" (RAM)","measure_day":"6/4/2014 2:42:00 PM"},{"Date":"April 07, 2014  7:14AM","MeasureDateTime1":"07:14:36.5730000","MeasureDate":"07/04/2014","MeasureDateTime":" 7:14AM","bldsgr_valueRam":"120","bldsgr_value":"120","bldsgr_value1":"120","detail_code_nm":"Before-Meal","memo":"Before Dinner","NewDateTime":"07/04/2014\\n 7:14AM","HasRam":"","measure_day":"4/7/2014 7:14:36 AM"},{"Date":"March 30, 2014  8:24AM","MeasureDateTime1":"08:24:36.5730000","MeasureDate":"30/03/2014","MeasureDateTime":" 8:24AM","bldsgr_valueRam":"160","bldsgr_value":"160","bldsgr_value1":"160","detail_code_nm":"Before-Meal","memo":"Before Dinner","NewDateTime":"30/03/2014\\n 8:24AM","HasRam":"","measure_day":"3/30/2014 8:24:36 AM"},{"Date":"March 25, 2014  11:14AM","MeasureDateTime1":"11:14:36.5730000","MeasureDate":"25/03/2014","MeasureDateTime":"11:14AM","bldsgr_valueRam":"170","bldsgr_value":"170","bldsgr_value1":"170","detail_code_nm":"Before-Meal","memo":"Before Lunch","NewDateTime":"25/03/2014\\n11:14AM","HasRam":"","measure_day":"3/25/2014 11:14:36 AM"},{"Date":"March 22, 2014  11:14AM","MeasureDateTime1":"11:14:36.5730000","MeasureDate":"22/03/2014","MeasureDateTime":"11:14AM","bldsgr_valueRam":"160","bldsgr_value":"160","bldsgr_value1":"160","detail_code_nm":"Before-Meal","memo":"Before Lunch","NewDateTime":"22/03/2014\\n11:14AM","HasRam":"","measure_day":"3/22/2014 11:14:36 AM"},{"Date":"February 20, 2014  6:14AM","MeasureDateTime1":"06:14:36.5730000","MeasureDate":"20/02/2014","MeasureDateTime":" 6:14AM","bldsgr_valueRam":"220","bldsgr_value":"220","bldsgr_value1":"220","detail_code_nm":"Before-Meal","memo":"Before Bed","NewDateTime":"20/02/2014\\n 6:14AM","HasRam":"","measure_day":"2/20/2014 6:14:36 AM"},{"Date":"February 15, 2014  5:14AM","MeasureDateTime1":"05:14:36.5730000","MeasureDate":"15/02/2014","MeasureDateTime":" 5:14AM","bldsgr_valueRam":"85","bldsgr_value":"85","bldsgr_value1":"85","detail_code_nm":"Before-Meal","memo":"Before Bed","NewDateTime":"15/02/2014\\n 5:14AM","HasRam":"","measure_day":"2/15/2014 5:14:36 AM"},{"Date":"February 10, 2014  11:14AM","MeasureDateTime1":"11:14:36.5730000","MeasureDate":"10/02/2014","MeasureDateTime":"11:14AM","bldsgr_valueRam":"180","bldsgr_value":"180","bldsgr_value1":"180","detail_code_nm":"Before-Meal","memo":"After Breakfast","NewDateTime":"10/02/2014\\n11:14AM","HasRam":"","measure_day":"2/10/2014 11:14:36 AM"},{"Date":"January 25, 2014  10:14AM","MeasureDateTime1":"10:14:36.5730000","MeasureDate":"25/01/2014","MeasureDateTime":"10:14AM","bldsgr_valueRam":"154 (RAM)","bldsgr_value":"154","bldsgr_value1":"154","detail_code_nm":"Before-Meal","memo":"Before Breakfast","NewDateTime":"25/01/2014\\n10:14AM","HasRam":" (RAM)","measure_day":"1/25/2014 10:14:36 AM"},{"Date":"January 05, 2014  1:14AM","MeasureDateTime1":"01:14:36.5730000","MeasureDate":"05/01/2014","MeasureDateTime":" 1:14AM","bldsgr_valueRam":"165 (RAM)","bldsgr_value":"165","bldsgr_value1":"165","detail_code_nm":"Before-Meal","memo":"After Lunch","NewDateTime":"05/01/2014\\n 1:14AM","HasRam":" (RAM)","measure_day":"1/5/2014 1:14:36 AM"}]
     */

    private boolean success;
    /**
     * Date : September 21, 2016  10:22AM
     * MeasureDateTime1 : 10:22:00
     * MeasureDate : 21/09/2016
     * MeasureDateTime : 10:22AM
     * bldsgr_valueRam : 350 (RAM)
     * bldsgr_value : 350
     * bldsgr_value1 : 350
     * detail_code_nm :
     * memo : After Lunch
     * NewDateTime : 21/09/2016\n10:22AM
     * HasRam :  (RAM)
     * measure_day : 9/21/2016 10:22:00 AM
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
        private String MeasureDateTime1;
        private String MeasureDate;
        private String MeasureDateTime;
        private String bldsgr_valueRam;
        private String bldsgr_value;
        private String bldsgr_value1;
        private String detail_code_nm;
        private String memo;
        private String NewDateTime;
        private String HasRam;
        private String measure_day;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getMeasureDateTime1() {
            return MeasureDateTime1;
        }

        public void setMeasureDateTime1(String MeasureDateTime1) {
            this.MeasureDateTime1 = MeasureDateTime1;
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

        public String getBldsgr_valueRam() {
            return bldsgr_valueRam;
        }

        public void setBldsgr_valueRam(String bldsgr_valueRam) {
            this.bldsgr_valueRam = bldsgr_valueRam;
        }

        public String getBldsgr_value() {
            return bldsgr_value;
        }

        public void setBldsgr_value(String bldsgr_value) {
            this.bldsgr_value = bldsgr_value;
        }

        public String getBldsgr_value1() {
            return bldsgr_value1;
        }

        public void setBldsgr_value1(String bldsgr_value1) {
            this.bldsgr_value1 = bldsgr_value1;
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

        public String getNewDateTime() {
            return NewDateTime;
        }

        public void setNewDateTime(String NewDateTime) {
            this.NewDateTime = NewDateTime;
        }

        public String getHasRam() {
            return HasRam;
        }

        public void setHasRam(String HasRam) {
            this.HasRam = HasRam;
        }

        public String getMeasure_day() {
            return measure_day;
        }

        public void setMeasure_day(String measure_day) {
            this.measure_day = measure_day;
        }
    }
}
