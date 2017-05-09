package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 23/9/16.
 */
public class GetPatientWaistModel {


    /**
     * success : true
     * Data : [{"Date":"August 06, 2014  8:34AM","MeasureDate":"06/08/2014","MeasureDateTime":" 8:34AM","Waist":"12"},{"Date":"August 06, 2014  8:34AM","MeasureDate":"06/08/2014","MeasureDateTime":" 8:34AM","Waist":"11"},{"Date":"February 19, 2015  10:52AM","MeasureDate":"19/02/2015","MeasureDateTime":"10:52AM","Waist":"34"},{"Date":"August 10, 2016  6:19AM","MeasureDate":"10/08/2016","MeasureDateTime":" 6:19AM","Waist":"36"},{"Date":"September 08, 2016  12:53PM","MeasureDate":"08/09/2016","MeasureDateTime":"12:53PM","Waist":"50"},{"Date":"September 12, 2016  8:52AM","MeasureDate":"12/09/2016","MeasureDateTime":" 8:52AM","Waist":"201"},{"Date":"September 12, 2016  8:52AM","MeasureDate":"12/09/2016","MeasureDateTime":" 8:52AM","Waist":"12"},{"Date":"September 23, 2016  10:31AM","MeasureDate":"23/09/2016","MeasureDateTime":"10:31AM","Waist":"78"}]
     */

    private boolean success;
    /**
     * Date : August 06, 2014  8:34AM
     * MeasureDate : 06/08/2014
     * MeasureDateTime :  8:34AM
     * Waist : 12
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
        private String Waist;

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

        public String getWaist() {
            return Waist;
        }

        public void setWaist(String Waist) {
            this.Waist = Waist;
        }
    }
}
