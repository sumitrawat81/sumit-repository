package com.sibsefid.model.doctor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 28/9/16.
 */
public class DAvalilibilityModel implements Serializable {


    /**
     * success : true
     * Data : [{"AvailableId":"39","Fromdate":"12/08/2016","Todate":"13/08/2016","FromTime":"11:00PM","Totime":"2:30AM","timezone":"Eastern Standard Time"},{"AvailableId":"40","Fromdate":"13/08/2016","Todate":"14/08/2016","FromTime":"11:00AM","Totime":"2:15AM","timezone":"Eastern Standard Time"},{"AvailableId":"41","Fromdate":"17/08/2016","Todate":"17/08/2016","FromTime":"5:00AM","Totime":"12:30PM","timezone":"India Standard Time"},{"AvailableId":"45","Fromdate":"26/08/2016","Todate":"26/08/2016","FromTime":"1:45PM","Totime":"3:30PM","timezone":"India Standard Time"},{"AvailableId":"46","Fromdate":"01/09/2016","Todate":"01/09/2016","FromTime":"1:30PM","Totime":"5:00PM","timezone":"India Standard Time"},{"AvailableId":"47","Fromdate":"02/09/2016","Todate":"02/09/2016","FromTime":"5:30AM","Totime":"1:30PM","timezone":"India Standard Time"},{"AvailableId":"48","Fromdate":"03/09/2016","Todate":"03/09/2016","FromTime":"6:45AM","Totime":"2:00PM","timezone":"India Standard Time"},{"AvailableId":"49","Fromdate":"05/09/2016","Todate":"05/09/2016","FromTime":"6:00AM","Totime":"1:30PM","timezone":"India Standard Time"},{"AvailableId":"51","Fromdate":"07/09/2016","Todate":"07/09/2016","FromTime":"11:15AM","Totime":"1:45PM","timezone":"India Standard Time"},{"AvailableId":"55","Fromdate":"12/09/2016","Todate":"12/09/2016","FromTime":"1:30PM","Totime":"2:30PM","timezone":"India Standard Time"},{"AvailableId":"56","Fromdate":"19/09/2016","Todate":"19/09/2016","FromTime":"5:30AM","Totime":"2:30PM","timezone":"India Standard Time"},{"AvailableId":"71","Fromdate":"27/09/2016","Todate":"27/09/2016","FromTime":"7:00AM","Totime":"6:00PM","timezone":"India Standard Time"}]
     */

    private boolean success;
    /**
     * AvailableId : 39
     * Fromdate : 12/08/2016
     * Todate : 13/08/2016
     * FromTime : 11:00PM
     * Totime : 2:30AM
     * timezone : Eastern Standard Time
     */

    private ArrayList<DAvMessageBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DAvMessageBean> getData() {
        return Data;
    }

    public void setData(ArrayList<DAvMessageBean> Data) {
        this.Data = Data;
    }

    public static class DAvMessageBean implements Serializable {
        private String AvailableId;
        private String Fromdate;
        private String Todate;
        private String FromTime;
        private String Totime;
        private String timezone;
        private String fromHr;
        private String fromMin;
        private String fromMeridiane;
        private String toHr;
        private String toMin;
        private String toMeridiane;
        private String AppointId;
        private boolean isCheck = false;

        public String getFromHr() {
            return fromHr;
        }

        public void setFromHr(String fromHr) {
            this.fromHr = fromHr;
        }

        public String getFromMin() {
            return fromMin;
        }

        public void setFromMin(String fromMin) {
            this.fromMin = fromMin;
        }

        public String getFromMeridiane() {
            return fromMeridiane;
        }

        public void setFromMeridiane(String fromMeridiane) {
            this.fromMeridiane = fromMeridiane;
        }

        public String getToMin() {
            return toMin;
        }

        public void setToMin(String toMin) {
            this.toMin = toMin;
        }

        public String getToHr() {
            return toHr;
        }

        public void setToHr(String toHr) {
            this.toHr = toHr;
        }

        public String getToMeridiane() {
            return toMeridiane;
        }

        public void setToMeridiane(String toMeridiane) {
            this.toMeridiane = toMeridiane;
        }

        public String getAppointId() {
            return AppointId;
        }

        public void setAppointId(String appointId) {
            AppointId = appointId;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getAvailableId() {
            return AvailableId;
        }

        public void setAvailableId(String AvailableId) {
            this.AvailableId = AvailableId;
        }

        public String getFromdate() {
            return Fromdate;
        }

        public void setFromdate(String Fromdate) {
            this.Fromdate = Fromdate;
        }

        public String getTodate() {
            return Todate;
        }

        public void setTodate(String Todate) {
            this.Todate = Todate;
        }

        public String getFromTime() {
            return FromTime;
        }

        public void setFromTime(String FromTime) {
            this.FromTime = FromTime;
        }

        public String getTotime() {
            return Totime;
        }

        public void setTotime(String Totime) {
            this.Totime = Totime;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }
}
