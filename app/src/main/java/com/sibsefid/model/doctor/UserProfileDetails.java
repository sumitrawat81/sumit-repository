package com.sibsefid.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 14/9/16.
 */
public class UserProfileDetails {


    /**
     * success : true
     * Message : Login Successfully
     * Data : [{"user_seq":"50","user_id":"","user_nm":"","field_code":"D0001","user_password":"Aa123456789","email":"neeraj_doc@gmail.com","input_dt":"10/21/2013 12:17:19 PM","modify_dt":"9/27/2016 7:23:20 AM","family_name":"neeraj","given_name":"sharma","middle_name":"","address_1":"geger rktmretre","address_2":"dfhdh[p fdh","city":"hailed","postcode":"302016","State":"1286","country":"80","State_pharmacy":"","country_pharmacy":"","title":"3","dob":"4/6/1995 12:00:00 AM","age":"","sex":"2","phone":"9509274996","pharmacy_name":"","address1_pharmacy":"","address2_pharmacy":"","city_pharmacy":"","postcode_pharmacy":"","phone_pharmacy":"","fax_pharmacy":"","user_photo":"crop-636105363680929685-300.jpg","clinic":"doctor-update-dcotor","user_profile":"fdsfdsf sfds dsfd s","geneal_comment":"","PaypalEmail":"","Consent_SMS":"True","Consent_Email":"True","Is_Approve":"True","Is_Delete":"False","Is_FullApprove":"False","doctorfees":"","LanguageSpoken":"engliash","RegNo":"AHPRA RegNo.","input_dt_Utc":"","Code":"9","qualifications":"BCA","Expressionofinterest":"","IsOnline":"True","MedicareNumber":"","ExpiryDate":"","ProviderNumber":"dsfds","RegisterByAdmin":"False","Signature":"","IpAddress":"117.247.233.61","Prescribed_No":"","RefrenceNumber":"","IsAcceptedTermsAndCondition":"","NPI_number":"654656765","other_licenses":"656546","pratice_years":"xzczxcxzc","speciality":"1","doc_resume":"","no_of_employee":"","firm_name":"","firm_contact_name":"","AboutMe":"dfdsf","UserRole":"","LastLogin":"9/27/2016 12:51:29 PM","CurrentLogin":"9/28/2016 10:16:50 AM","IsMobileVerified":"False","VerifiedCode":" ","IsAllowPhysicians":"False","quickblogpassword":"123456789","FeeClient":"70.00","FeePercent":"40.00","FeeAdmin":"28.00","FeeDoctor":"42.00","speciality_clinic":"","IsAddedbyClinic":"False","ClinicId":"0","QuickBloxId":"16570400","DateOfBirth":"06/04/1995","photo":"https://74.208.103.212:128/UploadImage/Provider/crop-636105363680929685-300.jpg","Signature_New":"","title_nm":"Mr.","State_nm":"Rajasthan","country_nm":"India"}]
     */

    private boolean success;
    private String Message;
    /**
     * user_seq : 50
     * user_id :
     * user_nm :
     * field_code : D0001
     * user_password : Aa123456789
     * email : neeraj_doc@gmail.com
     * input_dt : 10/21/2013 12:17:19 PM
     * modify_dt : 9/27/2016 7:23:20 AM
     * family_name : neeraj
     * given_name : sharma
     * middle_name :
     * address_1 : geger rktmretre
     * address_2 : dfhdh[p fdh
     * city : hailed
     * postcode : 302016
     * State : 1286
     * country : 80
     * State_pharmacy :
     * country_pharmacy :
     * title : 3
     * dob : 4/6/1995 12:00:00 AM
     * age :
     * sex : 2
     * phone : 9509274996
     * pharmacy_name :
     * address1_pharmacy :
     * address2_pharmacy :
     * city_pharmacy :
     * postcode_pharmacy :
     * phone_pharmacy :
     * fax_pharmacy :
     * user_photo : crop-636105363680929685-300.jpg
     * clinic : doctor-update-dcotor
     * user_profile : fdsfdsf sfds dsfd s
     * geneal_comment :
     * PaypalEmail :
     * Consent_SMS : True
     * Consent_Email : True
     * Is_Approve : True
     * Is_Delete : False
     * Is_FullApprove : False
     * doctorfees :
     * LanguageSpoken : engliash
     * RegNo : AHPRA RegNo.
     * input_dt_Utc :
     * Code : 9
     * qualifications : BCA
     * Expressionofinterest :
     * IsOnline : True
     * MedicareNumber :
     * ExpiryDate :
     * ProviderNumber : dsfds
     * RegisterByAdmin : False
     * Signature :
     * IpAddress : 117.247.233.61
     * Prescribed_No :
     * RefrenceNumber :
     * IsAcceptedTermsAndCondition :
     * NPI_number : 654656765
     * other_licenses : 656546
     * pratice_years : xzczxcxzc
     * speciality : 1
     * doc_resume :
     * no_of_employee :
     * firm_name :
     * firm_contact_name :
     * AboutMe : dfdsf
     * UserRole :
     * LastLogin : 9/27/2016 12:51:29 PM
     * CurrentLogin : 9/28/2016 10:16:50 AM
     * IsMobileVerified : False
     * VerifiedCode :
     * IsAllowPhysicians : False
     * quickblogpassword : 123456789
     * FeeClient : 70.00
     * FeePercent : 40.00
     * FeeAdmin : 28.00
     * FeeDoctor : 42.00
     * speciality_clinic :
     * IsAddedbyClinic : False
     * ClinicId : 0
     * QuickBloxId : 16570400
     * DateOfBirth : 06/04/1995
     * photo : https://74.208.103.212:128/UploadImage/Provider/crop-636105363680929685-300.jpg
     * Signature_New :
     * title_nm : Mr.
     * State_nm : Rajasthan
     * country_nm : India
     */

    @SerializedName("Data")
    private List<DProfileDetails> DProfileDetails;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DProfileDetails> getDProfileDetails() {
        return DProfileDetails;
    }

    public void setDProfileDetails(List<DProfileDetails> DProfileDetails) {
        this.DProfileDetails = DProfileDetails;
    }

    public static class DProfileDetails {
        private String user_seq;
        private String user_id;
        private String user_nm;
        private String field_code;
        private String user_password;
        private String email;
        private String input_dt;
        private String modify_dt;
        private String family_name;
        private String given_name;
        private String middle_name;
        private String address_1;
        private String address_2;
        private String city;
        private String postcode;
        private String State;
        private String country;
        private String State_pharmacy;
        private String country_pharmacy;
        private String title;
        private String dob;
        private String age;
        private String sex;
        private String phone;
        private String pharmacy_name;
        private String address1_pharmacy;
        private String address2_pharmacy;
        private String city_pharmacy;
        private String postcode_pharmacy;
        private String phone_pharmacy;
        private String fax_pharmacy;
        private String user_photo;
        private String clinic;
        private String user_profile;
        private String geneal_comment;
        private String PaypalEmail;
        private String Consent_SMS;
        private String Consent_Email;
        private String Is_Approve;
        private String Is_Delete;
        private String Is_FullApprove;
        private String doctorfees;
        private String LanguageSpoken;
        private String RegNo;
        private String input_dt_Utc;
        private String Code;
        private String qualifications;
        private String Expressionofinterest;
        private String IsOnline;
        private String MedicareNumber;
        private String ExpiryDate;
        private String ProviderNumber;
        private String RegisterByAdmin;
        private String Signature;
        private String IpAddress;
        private String Prescribed_No;
        private String RefrenceNumber;
        private String IsAcceptedTermsAndCondition;
        private String NPI_number;
        private String other_licenses;
        private String pratice_years;
        private String speciality;
        private String doc_resume;
        private String no_of_employee;
        private String firm_name;
        private String firm_contact_name;
        private String AboutMe;
        private String UserRole;
        private String LastLogin;
        private String CurrentLogin;
        private String IsMobileVerified;
        private String VerifiedCode;
        private String IsAllowPhysicians;
        private String quickblogpassword;
        private String FeeClient;
        private String FeePercent;
        private String FeeAdmin;
        private String FeeDoctor;
        private String speciality_clinic;
        private String IsAddedbyClinic;
        private String ClinicId;
        private String QuickBloxId;
        private String DateOfBirth;
        private String photo;
        private String Signature_New;
        private String title_nm;
        private String State_nm;
        private String country_nm;
        private String userPhoto;
        private String dobnew;
        private String ConsultFee;
        private int type;

        public String getConsultFee() {
            return ConsultFee;
        }

        public void setConsultFee(String consultFee) {
            ConsultFee = consultFee;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public String getDobnew() {
            return dobnew;
        }

        public void setDobnew(String dobnew) {
            this.dobnew = dobnew;
        }

        public String getUser_seq() {
            return user_seq;
        }

        public void setUser_seq(String user_seq) {
            this.user_seq = user_seq;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_nm() {
            return user_nm;
        }

        public void setUser_nm(String user_nm) {
            this.user_nm = user_nm;
        }

        public String getField_code() {
            return field_code;
        }

        public void setField_code(String field_code) {
            this.field_code = field_code;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getInput_dt() {
            return input_dt;
        }

        public void setInput_dt(String input_dt) {
            this.input_dt = input_dt;
        }

        public String getModify_dt() {
            return modify_dt;
        }

        public void setModify_dt(String modify_dt) {
            this.modify_dt = modify_dt;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getGiven_name() {
            return given_name;
        }

        public void setGiven_name(String given_name) {
            this.given_name = given_name;
        }

        public String getMiddle_name() {
            return middle_name;
        }

        public void setMiddle_name(String middle_name) {
            this.middle_name = middle_name;
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

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState_pharmacy() {
            return State_pharmacy;
        }

        public void setState_pharmacy(String State_pharmacy) {
            this.State_pharmacy = State_pharmacy;
        }

        public String getCountry_pharmacy() {
            return country_pharmacy;
        }

        public void setCountry_pharmacy(String country_pharmacy) {
            this.country_pharmacy = country_pharmacy;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPharmacy_name() {
            return pharmacy_name;
        }

        public void setPharmacy_name(String pharmacy_name) {
            this.pharmacy_name = pharmacy_name;
        }

        public String getAddress1_pharmacy() {
            return address1_pharmacy;
        }

        public void setAddress1_pharmacy(String address1_pharmacy) {
            this.address1_pharmacy = address1_pharmacy;
        }

        public String getAddress2_pharmacy() {
            return address2_pharmacy;
        }

        public void setAddress2_pharmacy(String address2_pharmacy) {
            this.address2_pharmacy = address2_pharmacy;
        }

        public String getCity_pharmacy() {
            return city_pharmacy;
        }

        public void setCity_pharmacy(String city_pharmacy) {
            this.city_pharmacy = city_pharmacy;
        }

        public String getPostcode_pharmacy() {
            return postcode_pharmacy;
        }

        public void setPostcode_pharmacy(String postcode_pharmacy) {
            this.postcode_pharmacy = postcode_pharmacy;
        }

        public String getPhone_pharmacy() {
            return phone_pharmacy;
        }

        public void setPhone_pharmacy(String phone_pharmacy) {
            this.phone_pharmacy = phone_pharmacy;
        }

        public String getFax_pharmacy() {
            return fax_pharmacy;
        }

        public void setFax_pharmacy(String fax_pharmacy) {
            this.fax_pharmacy = fax_pharmacy;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }

        public String getClinic() {
            return clinic;
        }

        public void setClinic(String clinic) {
            this.clinic = clinic;
        }

        public String getUser_profile() {
            return user_profile;
        }

        public void setUser_profile(String user_profile) {
            this.user_profile = user_profile;
        }

        public String getGeneal_comment() {
            return geneal_comment;
        }

        public void setGeneal_comment(String geneal_comment) {
            this.geneal_comment = geneal_comment;
        }

        public String getPaypalEmail() {
            return PaypalEmail;
        }

        public void setPaypalEmail(String PaypalEmail) {
            this.PaypalEmail = PaypalEmail;
        }

        public String getConsent_SMS() {
            return Consent_SMS;
        }

        public void setConsent_SMS(String Consent_SMS) {
            this.Consent_SMS = Consent_SMS;
        }

        public String getConsent_Email() {
            return Consent_Email;
        }

        public void setConsent_Email(String Consent_Email) {
            this.Consent_Email = Consent_Email;
        }

        public String getIs_Approve() {
            return Is_Approve;
        }

        public void setIs_Approve(String Is_Approve) {
            this.Is_Approve = Is_Approve;
        }

        public String getIs_Delete() {
            return Is_Delete;
        }

        public void setIs_Delete(String Is_Delete) {
            this.Is_Delete = Is_Delete;
        }

        public String getIs_FullApprove() {
            return Is_FullApprove;
        }

        public void setIs_FullApprove(String Is_FullApprove) {
            this.Is_FullApprove = Is_FullApprove;
        }

        public String getDoctorfees() {
            return doctorfees;
        }

        public void setDoctorfees(String doctorfees) {
            this.doctorfees = doctorfees;
        }

        public String getLanguageSpoken() {
            return LanguageSpoken;
        }

        public void setLanguageSpoken(String LanguageSpoken) {
            this.LanguageSpoken = LanguageSpoken;
        }

        public String getRegNo() {
            return RegNo;
        }

        public void setRegNo(String RegNo) {
            this.RegNo = RegNo;
        }

        public String getInput_dt_Utc() {
            return input_dt_Utc;
        }

        public void setInput_dt_Utc(String input_dt_Utc) {
            this.input_dt_Utc = input_dt_Utc;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getQualifications() {
            return qualifications;
        }

        public void setQualifications(String qualifications) {
            this.qualifications = qualifications;
        }

        public String getExpressionofinterest() {
            return Expressionofinterest;
        }

        public void setExpressionofinterest(String Expressionofinterest) {
            this.Expressionofinterest = Expressionofinterest;
        }

        public String getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(String IsOnline) {
            this.IsOnline = IsOnline;
        }

        public String getMedicareNumber() {
            return MedicareNumber;
        }

        public void setMedicareNumber(String MedicareNumber) {
            this.MedicareNumber = MedicareNumber;
        }

        public String getExpiryDate() {
            return ExpiryDate;
        }

        public void setExpiryDate(String ExpiryDate) {
            this.ExpiryDate = ExpiryDate;
        }

        public String getProviderNumber() {
            return ProviderNumber;
        }

        public void setProviderNumber(String ProviderNumber) {
            this.ProviderNumber = ProviderNumber;
        }

        public String getRegisterByAdmin() {
            return RegisterByAdmin;
        }

        public void setRegisterByAdmin(String RegisterByAdmin) {
            this.RegisterByAdmin = RegisterByAdmin;
        }

        public String getSignature() {
            return Signature;
        }

        public void setSignature(String Signature) {
            this.Signature = Signature;
        }

        public String getIpAddress() {
            return IpAddress;
        }

        public void setIpAddress(String IpAddress) {
            this.IpAddress = IpAddress;
        }

        public String getPrescribed_No() {
            return Prescribed_No;
        }

        public void setPrescribed_No(String Prescribed_No) {
            this.Prescribed_No = Prescribed_No;
        }

        public String getRefrenceNumber() {
            return RefrenceNumber;
        }

        public void setRefrenceNumber(String RefrenceNumber) {
            this.RefrenceNumber = RefrenceNumber;
        }

        public String getIsAcceptedTermsAndCondition() {
            return IsAcceptedTermsAndCondition;
        }

        public void setIsAcceptedTermsAndCondition(String IsAcceptedTermsAndCondition) {
            this.IsAcceptedTermsAndCondition = IsAcceptedTermsAndCondition;
        }

        public String getNPI_number() {
            return NPI_number;
        }

        public void setNPI_number(String NPI_number) {
            this.NPI_number = NPI_number;
        }

        public String getOther_licenses() {
            return other_licenses;
        }

        public void setOther_licenses(String other_licenses) {
            this.other_licenses = other_licenses;
        }

        public String getPratice_years() {
            return pratice_years;
        }

        public void setPratice_years(String pratice_years) {
            this.pratice_years = pratice_years;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getDoc_resume() {
            return doc_resume;
        }

        public void setDoc_resume(String doc_resume) {
            this.doc_resume = doc_resume;
        }

        public String getNo_of_employee() {
            return no_of_employee;
        }

        public void setNo_of_employee(String no_of_employee) {
            this.no_of_employee = no_of_employee;
        }

        public String getFirm_name() {
            return firm_name;
        }

        public void setFirm_name(String firm_name) {
            this.firm_name = firm_name;
        }

        public String getFirm_contact_name() {
            return firm_contact_name;
        }

        public void setFirm_contact_name(String firm_contact_name) {
            this.firm_contact_name = firm_contact_name;
        }

        public String getAboutMe() {
            return AboutMe;
        }

        public void setAboutMe(String AboutMe) {
            this.AboutMe = AboutMe;
        }

        public String getUserRole() {
            return UserRole;
        }

        public void setUserRole(String UserRole) {
            this.UserRole = UserRole;
        }

        public String getLastLogin() {
            return LastLogin;
        }

        public void setLastLogin(String LastLogin) {
            this.LastLogin = LastLogin;
        }

        public String getCurrentLogin() {
            return CurrentLogin;
        }

        public void setCurrentLogin(String CurrentLogin) {
            this.CurrentLogin = CurrentLogin;
        }

        public String getIsMobileVerified() {
            return IsMobileVerified;
        }

        public void setIsMobileVerified(String IsMobileVerified) {
            this.IsMobileVerified = IsMobileVerified;
        }

        public String getVerifiedCode() {
            return VerifiedCode;
        }

        public void setVerifiedCode(String VerifiedCode) {
            this.VerifiedCode = VerifiedCode;
        }

        public String getIsAllowPhysicians() {
            return IsAllowPhysicians;
        }

        public void setIsAllowPhysicians(String IsAllowPhysicians) {
            this.IsAllowPhysicians = IsAllowPhysicians;
        }

        public String getQuickblogpassword() {
            return quickblogpassword;
        }

        public void setQuickblogpassword(String quickblogpassword) {
            this.quickblogpassword = quickblogpassword;
        }

        public String getFeeClient() {
            return FeeClient;
        }

        public void setFeeClient(String FeeClient) {
            this.FeeClient = FeeClient;
        }

        public String getFeePercent() {
            return FeePercent;
        }

        public void setFeePercent(String FeePercent) {
            this.FeePercent = FeePercent;
        }

        public String getFeeAdmin() {
            return FeeAdmin;
        }

        public void setFeeAdmin(String FeeAdmin) {
            this.FeeAdmin = FeeAdmin;
        }

        public String getFeeDoctor() {
            return FeeDoctor;
        }

        public void setFeeDoctor(String FeeDoctor) {
            this.FeeDoctor = FeeDoctor;
        }

        public String getSpeciality_clinic() {
            return speciality_clinic;
        }

        public void setSpeciality_clinic(String speciality_clinic) {
            this.speciality_clinic = speciality_clinic;
        }

        public String getIsAddedbyClinic() {
            return IsAddedbyClinic;
        }

        public void setIsAddedbyClinic(String IsAddedbyClinic) {
            this.IsAddedbyClinic = IsAddedbyClinic;
        }

        public String getClinicId() {
            return ClinicId;
        }

        public void setClinicId(String ClinicId) {
            this.ClinicId = ClinicId;
        }

        public String getQuickBloxId() {
            return QuickBloxId;
        }

        public void setQuickBloxId(String QuickBloxId) {
            this.QuickBloxId = QuickBloxId;
        }

        public String getDateOfBirth() {
            return DateOfBirth;
        }

        public void setDateOfBirth(String DateOfBirth) {
            this.DateOfBirth = DateOfBirth;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getSignature_New() {
            return Signature_New;
        }

        public void setSignature_New(String Signature_New) {
            this.Signature_New = Signature_New;
        }

        public String getTitle_nm() {
            return title_nm;
        }

        public void setTitle_nm(String title_nm) {
            this.title_nm = title_nm;
        }

        public String getState_nm() {
            return State_nm;
        }

        public void setState_nm(String State_nm) {
            this.State_nm = State_nm;
        }

        public String getCountry_nm() {
            return country_nm;
        }

        public void setCountry_nm(String country_nm) {
            this.country_nm = country_nm;
        }
    }
}
