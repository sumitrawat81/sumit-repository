package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 19/9/16.
 */
public class MedicalDevicesBean {


    /**
     * success : true
     * Data : [{"Id":"18","Title":"Thermometer","Photo":"636084807987076335-Thermometer.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:26:38 AM","deviceimage":"https://74UploadImage/medical-devices/636084807987076335-Thermometer.jpg"},{"Id":"17","Title":"Stethoscope","Photo":"636084807808133407-Stethoscope1.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:26:20 AM","deviceimage":"https://74UploadImage/medical-devices/636084807808133407-Stethoscope1.jpg"},{"Id":"16","Title":"Pulse Oximeter","Photo":"636084807643064624-Pulse-Oximeter.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:26:04 AM","deviceimage":"https://74UploadImage/medical-devices/636084807643064624-Pulse-Oximeter.jpg"},{"Id":"15","Title":"Lancing device","Photo":"636084807436451896-Lancing-Device.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:25:43 AM","deviceimage":"https://74UploadImage/medical-devices/636084807436451896-Lancing-Device.jpg"},{"Id":"14","Title":"Glucometer","Photo":"636084807155497067-Glucometer.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:25:15 AM","deviceimage":"https://74UploadImage/medical-devices/636084807155497067-Glucometer.jpg"},{"Id":"13","Title":"Blood Pressure Monitor","Photo":"636084806928464679-Blood-Pressure-Monitor.jpg","Description":"","IsActive":"True","CreateDate":"9/3/2016 6:24:52 AM","deviceimage":"https://74UploadImage/medical-devices/636084806928464679-Blood-Pressure-Monitor.jpg"}]
     */

    private boolean success;
    /**
     * Id : 18
     * Title : Thermometer
     * Photo : 636084807987076335-Thermometer.jpg
     * Description :
     * IsActive : True
     * CreateDate : 9/3/2016 6:26:38 AM
     * deviceimage : https://74UploadImage/medical-devices/636084807987076335-Thermometer.jpg
     */

    @SerializedName("Data")
    private ArrayList<MedicalDeviceList> medicaldevicelist;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<MedicalDeviceList> getMedicaldevicelist() {
        return medicaldevicelist;
    }

    public void setMedicaldevicelist(ArrayList<MedicalDeviceList> medicaldevicelist) {
        this.medicaldevicelist = medicaldevicelist;
    }

    public static class MedicalDeviceList {
        private String Id;
        private String Title;
        private String Photo;
        private String Description;
        private String IsActive;
        private String CreateDate;
        private String deviceimage;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getIsActive() {
            return IsActive;
        }

        public void setIsActive(String IsActive) {
            this.IsActive = IsActive;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getDeviceimage() {
            return deviceimage;
        }

        public void setDeviceimage(String deviceimage) {
            this.deviceimage = deviceimage;
        }
    }
}
