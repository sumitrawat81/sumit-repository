package com.sibsefid.model.doctor;

/**
 * Created by ubuntu on 3/10/16.
 */
public class SetMyAvailibilityModel {
    String fromHours;
    String toHours;
    String fromMinutes;
    String toMinutes;
    String fromMeridane;
    String toMeridane;
    String fromDate;
    String toDate;
    String AppointmentId;
    String availabledays;
    int id;
    String timeZone;
    String timeZoneDiff;

    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        AppointmentId = appointmentId;
    }

    public String getAvailabledays() {
        return availabledays;
    }

    public void setAvailabledays(String availabledays) {
        this.availabledays = availabledays;
    }

    public String getTimeZoneDiff() {
        return timeZoneDiff;
    }

    public void setTimeZoneDiff(String timeZoneDiff) {
        this.timeZoneDiff = timeZoneDiff;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromMeridane() {
        return fromMeridane;
    }

    public void setFromMeridane(String fromMeridane) {
        this.fromMeridane = fromMeridane;
    }

    public String getToMeridane() {
        return toMeridane;
    }

    public void setToMeridane(String toMeridane) {
        this.toMeridane = toMeridane;
    }

    public String getToMinutes() {
        return toMinutes;
    }

    public void setToMinutes(String toMinutes) {
        this.toMinutes = toMinutes;
    }

    public String getFromHours() {
        return fromHours;
    }

    public void setFromHours(String fromHours) {
        this.fromHours = fromHours;
    }

    public String getToHours() {
        return toHours;
    }

    public void setToHours(String toHours) {
        this.toHours = toHours;
    }

    public String getFromMinutes() {
        return fromMinutes;
    }

    public void setFromMinutes(String fromMinutes) {
        this.fromMinutes = fromMinutes;
    }
}
