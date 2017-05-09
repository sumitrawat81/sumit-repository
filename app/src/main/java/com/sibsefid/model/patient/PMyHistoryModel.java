package com.sibsefid.model.patient;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/9/16.
 */
public class PMyHistoryModel {


    /**
     * Success : true
     * Data : {"Waist":[{"id":"15","Waist":"12","createDate":"9/12/2016 8:52:40 AM","patientId":"99","Unit":"cm","newdate":"12/09/2016"},{"id":"14","Waist":"201","createDate":"9/12/2016 8:52:04 AM","patientId":"99","Unit":"cm","newdate":"12/09/2016"},{"id":"13","Waist":"50","createDate":"9/8/2016 12:53:27 PM","patientId":"99","Unit":"cm","newdate":"08/09/2016"},{"id":"12","Waist":"36","createDate":"8/10/2016 6:19:51 AM","patientId":"99","Unit":"cm","newdate":"10/08/2016"},{"id":"10","Waist":"34","createDate":"2/19/2015 10:52:31 AM","patientId":"99","Unit":"cm","newdate":"19/02/2015"},{"id":"8","Waist":"11","createDate":"8/6/2014 8:34:42 AM","patientId":"99","Unit":"cm","newdate":"06/08/2014"},{"id":"7","Waist":"12","createDate":"8/6/2014 8:34:38 AM","patientId":"99","Unit":"cm","newdate":"06/08/2014"}],"Height":[{"id":"15","Waist":"","Height":"150","createDate":"9/21/2016 10:26:00 AM","patientId":"99","Unit":"cm"},{"id":"13","Waist":"","Height":"152","createDate":"9/12/2016 2:21:00 PM","patientId":"99","Unit":"cm"},{"id":"12","Waist":"","Height":"190","createDate":"9/12/2016 2:15:00 PM","patientId":"99","Unit":"cm"},{"id":"10","Waist":"","Height":"550","createDate":"8/10/2016 6:20:06 AM","patientId":"99","Unit":"cm"},{"id":"9","Waist":"","Height":"450","createDate":"2/19/2015 10:54:46 AM","patientId":"99","Unit":"cm"},{"id":"8","Waist":"","Height":"500","createDate":"2/19/2015 10:52:06 AM","patientId":"99","Unit":"cm"},{"id":"5","Waist":"","Height":"12","createDate":"8/5/2014 1:28:16 PM","patientId":"99","Unit":"cm"}],"Allergies":[{"srno":"75","patientid":"99","name":"Test","what_happen":"Fdsfdsfdsf","createdate":"9/21/2016 5:28:32 AM","type":"Allergies"}],"Medication":[{"srno":"1","name":"Cold And Flu Day And Night Relief Pe (your Pharmacy)","strength":"200 Mg","createdt":"8/2/2016 3:42:17 AM","type":"Medication","Dose":"100","YearStarted":"2014"},{"srno":"2","name":"Asprin","strength":"100mg","createdt":"8/10/2016 6:17:01 AM","type":"Medication","Dose":"100","YearStarted":"2014"},{"srno":"5","name":"Drug Delivery System","strength":"50","createdt":"9/5/2016 6:39:56 AM","type":"Medication","Dose":"3","YearStarted":"2014"}],"PastMedicalSurgical":[{"srno":"16","patientid":"99","namecondition":"Gall Bladder","year":"2015","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"10","patientid":"99","namecondition":"Cabg (bypass Surgery)","year":"2011","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"13","patientid":"99","namecondition":"Craniotomy","year":"2010","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"9","patientid":"99","namecondition":"Knee","year":"1996","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"15","patientid":"99","namecondition":"Ear Tubes","year":"1993","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"6","patientid":"99","namecondition":"Ankle","year":"1992","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"7","patientid":"99","namecondition":"Hip Replacement","year":"1989","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"17","patientid":"99","namecondition":"Wrist","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"18","patientid":"99","namecondition":"Head Or Neck","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"8","patientid":"99","namecondition":"Appendectomy","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"11","patientid":"99","namecondition":"Lung Resection","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"12","patientid":"99","namecondition":"Colon Resection","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"},{"srno":"14","patientid":"99","namecondition":"Plastic Surgery","year":"0","createdt":"7/7/2016 6:29:43 AM","type":"Surgical"}],"Smoking":[],"Alcohol":[],"Family":[{"srno":"13","patientid":"99","namecondition":"family history","whofamily":"who in family","createdt":"8/10/2016 1:26:26 AM","type":"Family"}],"OtherMedicalRecord":[{"other_medical_record_id":"2","patientid":"99","original_filename":"doctor1.jpg","convert_filename":"https://74.208.103.212:128/UploadImage/Patient/f493744d_91fb_4ad8_8cef_4d1b6e6602d8.jpg","upload_date":"8/10/2016 6:23:15 AM","upload_updated_date":"","subject":"yes i have other medical history"}]}
     */

    private boolean Success;
    private DataBean Data;


    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {


        private ArrayList<DataBean.OtherMedicalRecordBean> OtherMedicalRecord;
        /**
         * IsAllowPhysicians : False
         */

        private List<AllowPhysiciansBean> AllowPhysicians;
        /**
         * id : 15
         * Waist : 12
         * createDate : 9/12/2016 8:52:40 AM
         * patientId : 99
         * Unit : cm
         * newdate : 12/09/2016
         */

        private ArrayList<WaistBean> Waist;
        /**
         * id : 15
         * Waist :
         * Height : 150
         * createDate : 9/21/2016 10:26:00 AM
         * patientId : 99
         * Unit : cm
         */

        private ArrayList<HeightBean> Height;
        /**
         * srno : 75
         * patientid : 99
         * name : Test
         * what_happen : Fdsfdsfdsf
         * createdate : 9/21/2016 5:28:32 AM
         * type : Allergies
         */

        private ArrayList<AllergiesBean> Allergies;
        /**
         * srno : 1
         * name : Cold And Flu Day And Night Relief Pe (your Pharmacy)
         * strength : 200 Mg
         * createdt : 8/2/2016 3:42:17 AM
         * type : Medication
         * Dose : 100
         * YearStarted : 2014
         */

        private ArrayList<MedicationBean> Medication;
        /**
         * srno : 16
         * patientid : 99
         * namecondition : Gall Bladder
         * year : 2015
         * createdt : 7/7/2016 6:29:43 AM
         * type : Surgical
         */

        private ArrayList<PastMedicalSurgicalBean> PastMedicalSurgical;
        private ArrayList<SmokingBean> Smoking;
        private ArrayList<AlcoholBean> Alcohol;
        /**
         * srno : 13
         * patientid : 99
         * namecondition : family history
         * whofamily : who in family
         * createdt : 8/10/2016 1:26:26 AM
         * type : Family
         */

        private ArrayList<FamilyBean> Family;

        public List<AllowPhysiciansBean> getAllowPhysicians() {
            return AllowPhysicians;
        }

        public void setAllowPhysicians(List<AllowPhysiciansBean> allowPhysicians) {
            AllowPhysicians = allowPhysicians;
        }

        /**
         * other_medical_record_id : 2
         * patientid : 99
         * original_filename : doctor1.jpg
         * convert_filename : https://74.208.103.212:128/UploadImage/Patient/f493744d_91fb_4ad8_8cef_4d1b6e6602d8.jpg
         * upload_date : 8/10/2016 6:23:15 AM
         * upload_updated_date :
         * subject : yes i have other medical history
         */


        public ArrayList<WaistBean> getWaist() {
            return Waist;
        }

        public void setWaist(ArrayList<WaistBean> Waist) {
            this.Waist = Waist;
        }

        public ArrayList<HeightBean> getHeight() {
            return Height;
        }

        public void setHeight(ArrayList<HeightBean> Height) {
            this.Height = Height;
        }

        public ArrayList<AllergiesBean> getAllergies() {
            return Allergies;
        }

        public void setAllergies(ArrayList<AllergiesBean> Allergies) {
            this.Allergies = Allergies;
        }

        public ArrayList<MedicationBean> getMedication() {
            return Medication;
        }

        public void setMedication(ArrayList<MedicationBean> Medication) {
            this.Medication = Medication;
        }

        public ArrayList<PastMedicalSurgicalBean> getPastMedicalSurgical() {
            return PastMedicalSurgical;
        }

        public void setPastMedicalSurgical(ArrayList<PastMedicalSurgicalBean> PastMedicalSurgical) {
            this.PastMedicalSurgical = PastMedicalSurgical;
        }

        public ArrayList<SmokingBean> getSmoking() {
            return Smoking;
        }

        public void setSmoking(ArrayList<SmokingBean> Smoking) {
            this.Smoking = Smoking;
        }

        public ArrayList<AlcoholBean> getAlcohol() {
            return Alcohol;
        }

        public void setAlcohol(ArrayList<AlcoholBean> Alcohol) {
            this.Alcohol = Alcohol;
        }

        public ArrayList<FamilyBean> getFamily() {
            return Family;
        }

        public void setFamily(ArrayList<FamilyBean> Family) {
            this.Family = Family;
        }

        public ArrayList<OtherMedicalRecordBean> getOtherMedicalRecord() {
            return OtherMedicalRecord;
        }

        public void setOtherMedicalRecord(ArrayList<OtherMedicalRecordBean> OtherMedicalRecord) {
            this.OtherMedicalRecord = OtherMedicalRecord;
        }

        public static class SmokingBean {
            boolean isAdded;
            private String srno;
            private String patientid;
            private String howmanyeachday;
            private String yearstarted;
            private String createdt;
            private String type;
            private boolean isLoading = false;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
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

            public String getCreatedt() {
                return createdt;
            }

            public void setCreatedt(String createdt) {
                this.createdt = createdt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }


        public static class AlcoholBean {
            boolean isAdded;
            private String srno;
            private String patientid;
            private String alcohal_noofdays;

            private String no_of_std_drinkday;
            private String createdt;
            private String type;
            private boolean isLoading = false;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public String getAlcohal_noofdays() {
                return alcohal_noofdays;
            }

            public void setAlcohal_noofdays(String alcohal_noofdays) {
                this.alcohal_noofdays = alcohal_noofdays;
            }

            public String getNo_of_std_drinkday() {
                return no_of_std_drinkday;
            }

            public void setNo_of_std_drinkday(String no_of_std_drinkday) {
                this.no_of_std_drinkday = no_of_std_drinkday;
            }

            public String getCreatedt() {
                return createdt;
            }

            public void setCreatedt(String createdt) {
                this.createdt = createdt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class WaistBean {
            boolean isAdded;
            private String id;
            private String Waist;
            private String createDate;
            private String patientId;
            private String Unit;
            private String newdate;
            private boolean isLoading = false;
            private String patientid;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWaist() {
                return Waist;
            }

            public void setWaist(String Waist) {
                this.Waist = Waist;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getPatientId() {
                return patientId;
            }

            public void setPatientId(String patientId) {
                this.patientId = patientId;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public String getNewdate() {
                return newdate;
            }

            public void setNewdate(String newdate) {
                this.newdate = newdate;
            }
        }

        public static class HeightBean {
            boolean isAdded;
            private String id;
            private String Waist;
            private String Height;
            private String createDate;
            private String patientId;
            private String Unit;
            private boolean isLoading = false;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWaist() {
                return Waist;
            }

            public void setWaist(String Waist) {
                this.Waist = Waist;
            }

            public String getHeight() {
                return Height;
            }

            public void setHeight(String Height) {
                this.Height = Height;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getPatientId() {
                return patientId;
            }

            public void setPatientId(String patientId) {
                this.patientId = patientId;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }
        }

        public static class AllergiesBean {
            boolean isAdded;
            private String srno;
            private String patientid;
            private String name;
            private String what_happen;
            private String createdate;
            private String type;
            private boolean isLoading = false;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getWhat_happen() {
                return what_happen;
            }

            public void setWhat_happen(String what_happen) {
                this.what_happen = what_happen;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class MedicationBean {
            boolean isAdded;
            boolean isLoading = false;
            private String srno;
            private String name;
            private String strength;
            private String createdt;
            private String type;
            private String Dose;
            private String patientid;
            private String YearStarted;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public String getStrenght() {
                return strength;
            }

            public void setStrenght(String strenght) {
                this.strength = strenght;
            }

            public String getDose() {
                return Dose;
            }

            public void setDose(String dose) {
                this.Dose = dose;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }


            public String getCreatedt() {
                return createdt;
            }

            public void setCreatedt(String createdt) {
                this.createdt = createdt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }


            public String getYearStarted() {
                return YearStarted;
            }

            public void setYearStarted(String YearStarted) {
                this.YearStarted = YearStarted;
            }
        }

        public static class PastMedicalSurgicalBean {
            boolean isAdded;
            boolean isLoading = false;
            private String srno;
            private String patientid;
            private String namecondition;
            private String year;
            private String createdt;
            private String type;
            private String IsChecked;
            private boolean isDeletedLoading = false;

            public String getIsChecked() {
                return IsChecked;
            }

            public void setIsChecked(String isChecked) {
                IsChecked = isChecked;
            }

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public String getNamecondition() {
                return namecondition;
            }

            public void setNamecondition(String namecondition) {
                this.namecondition = namecondition;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getCreatedt() {
                return createdt;
            }

            public void setCreatedt(String createdt) {
                this.createdt = createdt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class FamilyBean {
            boolean isAdded;
            boolean isLoading = false;
            private String srno;
            private String patientid;
            private String namecondition;
            private String whofamily;
            private String createdt;
            private String type;
            private boolean isDeletedLoading = false;

            public boolean isDeletedLoading() {
                return isDeletedLoading;
            }

            public void setDeletedLoading(boolean deletedLoading) {
                isDeletedLoading = deletedLoading;
            }

            public boolean isLoading() {
                return isLoading;
            }

            public void setLoading(boolean loading) {
                isLoading = loading;
            }

            public boolean isAdded() {
                return isAdded;
            }

            public void setAdded(boolean added) {
                isAdded = added;
            }

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public String getNamecondition() {
                return namecondition;
            }

            public void setNamecondition(String namecondition) {
                this.namecondition = namecondition;
            }

            public String getWhofamily() {
                return whofamily;
            }

            public void setWhofamily(String whofamily) {
                this.whofamily = whofamily;
            }

            public String getCreatedt() {
                return createdt;
            }

            public void setCreatedt(String createdt) {
                this.createdt = createdt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class OtherMedicalRecordBean {
            private String other_medical_record_id;
            private String patientid;
            private String original_filename;
            private String convert_filename;
            private String upload_date;
            private String upload_updated_date;
            private String subject;

            public String getOther_medical_record_id() {
                return other_medical_record_id;
            }

            public void setOther_medical_record_id(String other_medical_record_id) {
                this.other_medical_record_id = other_medical_record_id;
            }

            public String getPatientid() {
                return patientid;
            }

            public void setPatientid(String patientid) {
                this.patientid = patientid;
            }

            public String getOriginal_filename() {
                return original_filename;
            }

            public void setOriginal_filename(String original_filename) {
                this.original_filename = original_filename;
            }

            public String getConvert_filename() {
                return convert_filename;
            }

            public void setConvert_filename(String convert_filename) {
                this.convert_filename = convert_filename;
            }

            public String getUpload_date() {
                return upload_date;
            }

            public void setUpload_date(String upload_date) {
                this.upload_date = upload_date;
            }

            public String getUpload_updated_date() {
                return upload_updated_date;
            }

            public void setUpload_updated_date(String upload_updated_date) {
                this.upload_updated_date = upload_updated_date;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }
        }
    }

    public static class AllowPhysiciansBean {
        private String IsAllowPhysicians;

        public String getIsAllowPhysicians() {
            return IsAllowPhysicians;
        }

        public void setIsAllowPhysicians(String IsAllowPhysicians) {
            this.IsAllowPhysicians = IsAllowPhysicians;
        }
    }

}
