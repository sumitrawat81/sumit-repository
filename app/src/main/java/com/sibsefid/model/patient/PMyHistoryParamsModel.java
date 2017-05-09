package com.sibsefid.model.patient;

import android.text.TextUtils;

/**
 * Created by root on 22/9/16.
 */
public class PMyHistoryParamsModel {


    String ID;
    int IDInt;
    String what_happen;
    String Name;
    int adpterPosition;
    int rowPosition;
    int type;
    int deleted_item_id_type = 0;

    String patientid;
    int patientidInt;
    boolean isCheck = false;
    String howmanyeachday, yearstarted;

    String patientemail;
    String action;
    String doctorname;
    String appointmentid;

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getIDInt() {

        if (TextUtils.isEmpty(ID)) {
            IDInt = 0;
        } else {
            IDInt = Integer.valueOf(ID);
        }
        return IDInt;
    }

    public void setIDInt(int IDInt) {
        this.IDInt = IDInt;
    }

    public String getHowmanyeachday() {
        return howmanyeachday;
    }

    public void setHowmanyeachday(String howmanyeachday) {
        this.howmanyeachday = howmanyeachday;
    }

    public String getYearstarted() {
        return yearstarted;
    }

    public void setYearstarted(String yearstarted) {
        this.yearstarted = yearstarted;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public int getPatientidInt() {

        if (TextUtils.isEmpty(patientid)) {
            patientidInt = 0;
        } else {
            patientidInt = Integer.valueOf(patientid);
        }
        return patientidInt;
    }

    public void setPatientidInt(int patientidInt) {
        this.patientidInt = patientidInt;
    }

    public int getDeleted_item_id_type() {
        return deleted_item_id_type;
    }

    public void setDeleted_item_id_type(int deleted_item_id_type) {
        this.deleted_item_id_type = deleted_item_id_type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWhat_happen() {
        return what_happen;
    }

    public void setWhat_happen(String what_happen) {
        this.what_happen = what_happen;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAdpterPosition() {
        return adpterPosition;
    }

    public void setAdpterPosition(int adpterPosition) {
        this.adpterPosition = adpterPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }
}
