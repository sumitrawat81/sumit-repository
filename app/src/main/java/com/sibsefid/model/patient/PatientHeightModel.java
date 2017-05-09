package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/9/16.
 */
public class PatientHeightModel {


    /**
     * success : true
     * Data : [{"Date":"September 23, 2016  4:38AM","MeasureDate":"23/09/2016","MeasureDateTime":" 4:38AM","Height":"121"},{"Date":"September 23, 2016  10:33AM","MeasureDate":"23/09/2016","MeasureDateTime":"10:33AM","Height":"100"},{"Date":"September 23, 2016  10:35AM","MeasureDate":"23/09/2016","MeasureDateTime":"10:35AM","Height":"99"},{"Date":"September 23, 2016  10:36AM","MeasureDate":"23/09/2016","MeasureDateTime":"10:36AM","Height":"121"}]
     */

    private boolean success;
    /**
     * Date : September 23, 2016  4:38AM
     * MeasureDate : 23/09/2016
     * MeasureDateTime :  4:38AM
     * Height : 121
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
        private String Height;

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

        public String getHeight() {
            return Height;
        }

        public void setHeight(String Height) {
            this.Height = Height;
        }
    }
}
