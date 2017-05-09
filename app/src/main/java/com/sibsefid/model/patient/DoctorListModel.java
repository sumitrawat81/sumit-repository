package com.sibsefid.model.patient;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 30/9/16.
 */
public class DoctorListModel {
    /**
     * Success : true
     * Data : {"DoctorList":[{"countryName":"","StateName":"Parwan","DoctorId":"497","CurrentDate":"9/30/2016 4:24:00 AM","state":"27","country":"","LanguageSpoken":"Hindi, English","sex":"","speciality":"2","email":"guri@mailinator.com","name":"Guri Deep","title":"Dr.","user_photo":"","specaility":"Family Practice","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"","StateName":"Samangan","DoctorId":"458","CurrentDate":"9/30/2016 4:24:00 AM","state":"28","country":"","LanguageSpoken":"English","sex":"","speciality":"1","email":"ayushi1@brsoftech.com","name":"ayushi mittal","title":"Dr.","user_photo":"","specaility":"Internal Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"","StateName":"Samangan","DoctorId":"495","CurrentDate":"9/30/2016 4:24:00 AM","state":"28","country":"","LanguageSpoken":"English, Hindi","sex":"","speciality":"10","email":"kidney@mailinator.com","name":"12345 12345","title":"Dr.","user_photo":"","specaility":"kidney specialist","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"","StateName":"Samangan","DoctorId":"496","CurrentDate":"9/30/2016 4:24:00 AM","state":"28","country":"","LanguageSpoken":"English, Hindi","sex":"","speciality":"9","email":"manu@mailinator.com","name":"Manu Kaur","title":"Dr.","user_photo":"","specaility":"cancer specialist","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Andaman and Nicobar Islands","DoctorId":"483","CurrentDate":"9/30/2016 4:24:00 AM","state":"1260","country":"80","LanguageSpoken":"xdfghxfghg","sex":"1","speciality":"1","email":"akki9@gmail.com","name":"sdfsfs dfgdhfh","title":"Mr.","user_photo":"","specaility":"Internal Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Delhi","DoctorId":"490","CurrentDate":"9/30/2016 4:24:00 AM","state":"1268","country":"80","LanguageSpoken":"zxccv zxccv","sex":"","speciality":"2","email":"anki4@gmail.com","name":"sdfasfdsdf asdfSDF","title":"Prof.","user_photo":"","specaility":"Family Practice","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Delhi","DoctorId":"516","CurrentDate":"9/30/2016 4:24:00 AM","state":"1268","country":"80","LanguageSpoken":"asdfSDF","sex":"2","speciality":"9","email":"akash@gmail.com","name":"qwe sdfasfdsdf","title":"A. Prof.","user_photo":"","specaility":"cancer specialist","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Rajasthan","DoctorId":"50","CurrentDate":"9/30/2016 4:24:00 AM","state":"1286","country":"80","LanguageSpoken":"engliash","sex":"2","speciality":"1","email":"neeraj_doc@gmail.com","name":"neeraj sharma","title":"Mr.","user_photo":"https://74.208.103.212:128/crop-636105363680929685-300.jpg","specaility":"Internal Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Rajasthan","DoctorId":"417","CurrentDate":"9/30/2016 4:24:00 AM","state":"1286","country":"80","LanguageSpoken":"Hindi, English","sex":"1","speciality":"2","email":"ramswaroop.jat@brsoftech.org","name":"ram jat","title":"Mr.","user_photo":"","specaility":"Family Practice","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"India","StateName":"Rajasthan","DoctorId":"498","CurrentDate":"9/30/2016 4:24:00 AM","state":"1286","country":"80","LanguageSpoken":"Hindi, English","sex":"2","speciality":"4","email":"kirat@mailinator.com","name":"kirat kaur","title":"Ms.","user_photo":"https://74.208.103.212:128/crop-636098589604969644-iags.jpeg","specaility":"Emergency Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"India Standard Time","AvailablityFrom":"9/23/2016 3:30:00 AM","AvailablityTo":"9/30/2016 10:30:00 AM","TodayAvailablity":"9/30/2016 4:00:00 PM","VisitNow":"0","RangeToday":"09/30/2016 09:00"},{"countryName":"India","StateName":"Rajasthan","DoctorId":"508","CurrentDate":"9/30/2016 4:24:00 AM","state":"1286","country":"80","LanguageSpoken":"English, Pushto","sex":"1","speciality":"10","email":"yt@mailinator.com","name":"Yash Thakur","title":"Mr.","user_photo":"","specaility":"kidney specialist","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"United States","StateName":"Maryland","DoctorId":"514","CurrentDate":"9/30/2016 4:24:00 AM","state":"3406","country":"192","LanguageSpoken":"French, English","sex":"2","speciality":"3","email":"syanwouo@gmail.com","name":"SANDRINE TANKEU","title":"Dr.","user_photo":"","specaility":"Family Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""},{"countryName":"United States","StateName":"Massachusetts","DoctorId":"421","CurrentDate":"9/30/2016 4:24:00 AM","state":"3407","country":"192","LanguageSpoken":"English","sex":"1","speciality":"1","email":"tchazouj@yahoo.com","name":"Tchazou JM","title":"Mr.","user_photo":"","specaility":"Internal Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"Eastern Standard Time","AvailablityFrom":"9/12/2016 2:00:00 AM","AvailablityTo":"10/1/2016 2:45:00 AM","TodayAvailablity":"","VisitNow":"0","RangeToday":"09/30/2016 22:00"},{"countryName":"United States","StateName":"Texas","DoctorId":"501","CurrentDate":"9/30/2016 4:24:00 AM","state":"3429","country":"192","LanguageSpoken":"English","sex":"1","speciality":"1","email":"evangankeu@yahoo.com","name":"EVARISTE NGANKEU","title":"Mr.","user_photo":"","specaility":"Internal Medicine","CurrentDateTime":"9/30/2016 4:24:00 AM","TimeZone":"","AvailablityFrom":"","AvailablityTo":"","TodayAvailablity":"","VisitNow":"0","RangeToday":""}],"AvailablityList":[{"AvailableId":"53","FromDate":"9/12/2016 2:00:00 AM","Todate":"10/1/2016 2:45:00 AM","FromDate1":"9/11/2016 10:00:00 PM","Todate1":"9/30/2016 10:45:00 PM","TimeZone":"Eastern Standard Time","DoctorId":"421","Status":"Active","AvailableDays":"Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday","dateF":"09/12/2016","dateT":"10/01/2016","DateTimeF":"09/30/2016 02:00","DateTimeT":"09/30/2016 02:45"},{"AvailableId":"60","FromDate":"9/23/2016 3:30:00 AM","Todate":"9/30/2016 10:30:00 AM","FromDate1":"9/23/2016 9:00:00 AM","Todate1":"9/30/2016 4:00:00 PM","TimeZone":"India Standard Time","DoctorId":"498","Status":"Active","AvailableDays":"Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday","dateF":"09/23/2016","dateT":"09/30/2016","DateTimeF":"09/30/2016 03:30","DateTimeT":"09/30/2016 10:30"},{"AvailableId":"63","FromDate":"10/1/2016 3:00:00 PM","Todate":"10/8/2016 2:30:00 PM","FromDate1":"10/1/2016 8:30:00 PM","Todate1":"10/8/2016 8:00:00 PM","TimeZone":"India Standard Time","DoctorId":"498","Status":"Active","AvailableDays":"Friday","dateF":"10/01/2016","dateT":"10/08/2016","DateTimeF":"09/30/2016 15:00","DateTimeT":"09/30/2016 14:30"}]}
     */

    private boolean Success;
    private String Message;
    private DataBean Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

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
        /**
         * countryName :
         * StateName : Parwan
         * DoctorId : 497
         * CurrentDate : 9/30/2016 4:24:00 AM
         * state : 27
         * country :
         * LanguageSpoken : Hindi, English
         * sex :
         * speciality : 2
         * email : guri@mailinator.com
         * name : Guri Deep
         * title : Dr.
         * user_photo :
         * specaility : Family Practice
         * CurrentDateTime : 9/30/2016 4:24:00 AM
         * TimeZone :
         * AvailablityFrom :
         * AvailablityTo :
         * TodayAvailablity :
         * VisitNow : 0
         * RangeToday :
         */

        private ArrayList<DoctorListBean> DoctorList;
        /**
         * AvailableId : 53
         * FromDate : 9/12/2016 2:00:00 AM
         * Todate : 10/1/2016 2:45:00 AM
         * FromDate1 : 9/11/2016 10:00:00 PM
         * Todate1 : 9/30/2016 10:45:00 PM
         * TimeZone : Eastern Standard Time
         * DoctorId : 421
         * Status : Active
         * AvailableDays : Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
         * dateF : 09/12/2016
         * dateT : 10/01/2016
         * DateTimeF : 09/30/2016 02:00
         * DateTimeT : 09/30/2016 02:45
         */

        private ArrayList<AvailablityListBean> AvailablityList;

        public ArrayList<DoctorListBean> getDoctorList() {
            return DoctorList;
        }

        public void setDoctorList(ArrayList<DoctorListBean> DoctorList) {
            this.DoctorList = DoctorList;
        }

        public ArrayList<AvailablityListBean> getAvailablityList() {
            return AvailablityList;
        }

        public void setAvailablityList(ArrayList<AvailablityListBean> AvailablityList) {
            this.AvailablityList = AvailablityList;
        }

        public static class DoctorListBean implements Serializable {
            private String countryName;
            private String StateName;
            private String DoctorId;
            private String CurrentDate;
            private String state;
            private int state_int;
            private String country;
            private int country_int;
            private String LanguageSpoken;
            private String sex;
            private String speciality;
            private int speciality_int;
            private String email;
            private String name;
            private String title;
            private String user_photo;
            private String specaility;
            private String CurrentDateTime;
            private String TimeZone;
            private String AvailablityFrom;
            private String AvailablityTo;
            private String TodayAvailablity;
            private String VisitNow;
            private String RangeToday;

            private String address_1;
            private String address_2;
            private String city;
            private String AboutMe;
            private String qualifications;
            private boolean isAvailabe;
            private String FeeClient;
            private String FeePercent;
            private String FeeAdmin;
            private String FeeDoctor;

            public boolean isAvailabe() {
                return isAvailabe;
            }

            public void setAvailabe(boolean availabe) {
                isAvailabe = availabe;
            }

            public String getFeeClient() {
                return FeeClient;
            }

            public void setFeeClient(String feeClient) {
                FeeClient = feeClient;
            }

            public String getFeePercent() {
                return FeePercent;
            }

            public void setFeePercent(String feePercent) {
                FeePercent = feePercent;
            }

            public String getFeeAdmin() {
                return FeeAdmin;
            }

            public void setFeeAdmin(String feeAdmin) {
                FeeAdmin = feeAdmin;
            }

            public String getFeeDoctor() {
                return FeeDoctor;
            }

            public void setFeeDoctor(String feeDoctor) {
                FeeDoctor = feeDoctor;
            }

            public int getState_int() {
                return state_int;
            }

            public void setState_int(int state_int) {
                this.state_int = state_int;
            }

            public int getCountry_int() {
                return country_int;
            }

            public void setCountry_int(int country_int) {
                this.country_int = country_int;
            }

            public int getSpeciality_int() {
                return speciality_int;
            }

            public void setSpeciality_int(int speciality_int) {
                this.speciality_int = speciality_int;
            }

            public String getAddress_1() {
                return address_1;
            }

            public void setAddress_1(String address_1) {
                this.address_1 = address_1;
            }

            public String getAddress_2() {
                return address_2;
            }

            public void setAddress_2(String address_2) {
                this.address_2 = address_2;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAboutMe() {
                return AboutMe;
            }

            public void setAboutMe(String aboutMe) {
                AboutMe = aboutMe;
            }

            public String getQualifications() {
                return qualifications;
            }

            public void setQualifications(String qualifications) {
                this.qualifications = qualifications;
            }

            public int geSpeciality_codeint() {

                if (TextUtils.isEmpty(speciality)) {
                    speciality = "0";
                }
                speciality_int = Integer.parseInt(speciality);
                return speciality_int;
            }

            public int geState_codeint() {

                if (TextUtils.isEmpty(state)) {
                    state = "0";
                }
                state_int = Integer.parseInt(state);
                return state_int;
            }


            public int geCountry_codeint() {

                if (TextUtils.isEmpty(country)) {
                    country = "0";
                }
                country_int = Integer.parseInt(country);
                return country_int;
            }

            public String getCountryName() {
                return countryName;
            }

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }

            public String getStateName() {
                return StateName;
            }

            public void setStateName(String StateName) {
                this.StateName = StateName;
            }

            public String getDoctorId() {
                return DoctorId;
            }

            public void setDoctorId(String DoctorId) {
                this.DoctorId = DoctorId;
            }

            public String getCurrentDate() {
                return CurrentDate;
            }

            public void setCurrentDate(String CurrentDate) {
                this.CurrentDate = CurrentDate;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLanguageSpoken() {
                return LanguageSpoken;
            }

            public void setLanguageSpoken(String LanguageSpoken) {
                this.LanguageSpoken = LanguageSpoken;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSpeciality() {
                return speciality;
            }

            public void setSpeciality(String speciality) {
                this.speciality = speciality;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUser_photo() {
                return user_photo;
            }

            public void setUser_photo(String user_photo) {
                this.user_photo = user_photo;
            }

            public String getSpecaility() {
                return specaility;
            }

            public void setSpecaility(String specaility) {
                this.specaility = specaility;
            }

            public String getCurrentDateTime() {
                return CurrentDateTime;
            }

            public void setCurrentDateTime(String CurrentDateTime) {
                this.CurrentDateTime = CurrentDateTime;
            }

            public String getTimeZone() {
                return TimeZone;
            }

            public void setTimeZone(String TimeZone) {
                this.TimeZone = TimeZone;
            }

            public String getAvailablityFrom() {
                return AvailablityFrom;
            }

            public void setAvailablityFrom(String AvailablityFrom) {
                this.AvailablityFrom = AvailablityFrom;
            }

            public String getAvailablityTo() {
                return AvailablityTo;
            }

            public void setAvailablityTo(String AvailablityTo) {
                this.AvailablityTo = AvailablityTo;
            }

            public String getTodayAvailablity() {
                return TodayAvailablity;
            }

            public void setTodayAvailablity(String TodayAvailablity) {
                this.TodayAvailablity = TodayAvailablity;
            }

            public String getVisitNow() {
                return VisitNow;
            }

            public void setVisitNow(String VisitNow) {
                this.VisitNow = VisitNow;
            }

            public String getRangeToday() {
                return RangeToday;
            }

            public void setRangeToday(String RangeToday) {
                this.RangeToday = RangeToday;
            }
        }

        public static class AvailablityListBean {
            private String AvailableId;
            private String FromDate;
            private String Todate;
            private String FromDate1;
            private String Todate1;
            private String TimeZone;
            private String DoctorId;
            private String Status;
            private String AvailableDays;
            private String dateF;
            private String dateT;
            private String DateTimeF;
            private String DateTimeT;

            public String getAvailableId() {
                return AvailableId;
            }

            public void setAvailableId(String AvailableId) {
                this.AvailableId = AvailableId;
            }

            public String getFromDate() {
                return FromDate;
            }

            public void setFromDate(String FromDate) {
                this.FromDate = FromDate;
            }

            public String getTodate() {
                return Todate;
            }

            public void setTodate(String Todate) {
                this.Todate = Todate;
            }

            public String getFromDate1() {
                return FromDate1;
            }

            public void setFromDate1(String FromDate1) {
                this.FromDate1 = FromDate1;
            }

            public String getTodate1() {
                return Todate1;
            }

            public void setTodate1(String Todate1) {
                this.Todate1 = Todate1;
            }

            public String getTimeZone() {
                return TimeZone;
            }

            public void setTimeZone(String TimeZone) {
                this.TimeZone = TimeZone;
            }

            public String getDoctorId() {
                return DoctorId;
            }

            public void setDoctorId(String DoctorId) {
                this.DoctorId = DoctorId;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getAvailableDays() {
                return AvailableDays;
            }

            public void setAvailableDays(String AvailableDays) {
                this.AvailableDays = AvailableDays;
            }

            public String getDateF() {
                return dateF;
            }

            public void setDateF(String dateF) {
                this.dateF = dateF;
            }

            public String getDateT() {
                return dateT;
            }

            public void setDateT(String dateT) {
                this.dateT = dateT;
            }

            public String getDateTimeF() {
                return DateTimeF;
            }

            public void setDateTimeF(String DateTimeF) {
                this.DateTimeF = DateTimeF;
            }

            public String getDateTimeT() {
                return DateTimeT;
            }

            public void setDateTimeT(String DateTimeT) {
                this.DateTimeT = DateTimeT;
            }
        }
    }
}
