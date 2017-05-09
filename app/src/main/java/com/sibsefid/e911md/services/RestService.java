package com.sibsefid.e911md.services;

import com.sibsefid.model.doctor.AccountDetailModel;
import com.sibsefid.model.doctor.AppointmentStatusModel;
import com.sibsefid.model.doctor.DAppointmentListModel;
import com.sibsefid.model.doctor.DAvalilibilityModel;
import com.sibsefid.model.doctor.DPastConsultModel;
import com.sibsefid.model.doctor.DoctorPreviousTicketsModel;
import com.sibsefid.model.doctor.GetPersonalNotesModel;
import com.sibsefid.model.doctor.GetTimeZoneModel;
import com.sibsefid.model.doctor.MyPatientsModel;
import com.sibsefid.model.doctor.MyTransactionModel;
import com.sibsefid.model.doctor.ProfileUpdateModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.SendMobileVerifyCodeModel;
import com.sibsefid.model.doctor.SetAvailabilityResponseModel;
import com.sibsefid.model.doctor.SpecialityBean;
import com.sibsefid.model.doctor.TitleModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.doctor.VerifyMobileCodeModel;
import com.sibsefid.model.patient.AddMannullyBloodOxygenModel;
import com.sibsefid.model.patient.AddManualEcgModel;
import com.sibsefid.model.patient.AddManulyBloodPressureModel;
import com.sibsefid.model.patient.AddMunnalTemperatureModel;
import com.sibsefid.model.patient.AddOtherMedicalHistoryModel;
import com.sibsefid.model.patient.BillingInformationModel;
import com.sibsefid.model.patient.BlogListModel;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.model.patient.ComposeMsgTo;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.DoctorListModel;
import com.sibsefid.model.patient.FaqModel;
import com.sibsefid.model.patient.ForgotPasswordModel;
import com.sibsefid.model.patient.GetBloodOxygenModel;
import com.sibsefid.model.patient.GetMedicinesList;
import com.sibsefid.model.patient.GetPatientAppointmentModel;
import com.sibsefid.model.patient.GetPatientBloodGlucoseModel;
import com.sibsefid.model.patient.GetPatientBloodPressureModel;
import com.sibsefid.model.patient.GetPatientWaistModel;
import com.sibsefid.model.patient.GetPatientWeightModel;
import com.sibsefid.model.patient.MedicalDevicesBean;
import com.sibsefid.model.patient.PFamilyHistoryModel;
import com.sibsefid.model.patient.PLifeStyleModel;
import com.sibsefid.model.patient.PMessageModel;
import com.sibsefid.model.patient.PMessageSentModel;
import com.sibsefid.model.patient.PMyBillingHistoryModel;
import com.sibsefid.model.patient.PMyHistoryModel;
import com.sibsefid.model.patient.PPharmacyModel;
import com.sibsefid.model.patient.PatientBMIDetailModel;
import com.sibsefid.model.patient.PatientEcgDetailModel;
import com.sibsefid.model.patient.PatientEntDetailModel;
import com.sibsefid.model.patient.PatientFamiliyMembersModel;
import com.sibsefid.model.patient.PatientHeartRateModel;
import com.sibsefid.model.patient.PatientHeightModel;
import com.sibsefid.model.patient.PatientSkinDetailModel;
import com.sibsefid.model.patient.PatientSummeryDetailModel;
import com.sibsefid.model.patient.PatientTemperatureDetailModel;
import com.sibsefid.model.patient.PatientTitleModel;
import com.sibsefid.model.patient.RelationshipType;
import com.sibsefid.model.patient.SetMedicineReminderModel;
import com.sibsefid.model.patient.SetWeightMannullyModel;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.model.patient.UpdateProfilePictureModel;
import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.KeyValues;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by root on 12/9/16.
 */
public interface RestService {


    @GET(ApiUrls.GET_COUNTRY_LIST_URL)
    Call<CountryBean> getCountryList();


    @FormUrlEncoded
    @POST(ApiUrls.GET_COUNTRY_LIST_URL)
    Call<CountryBean> getCountryList(@Field(KeyValues.ARG_P_LANG) String lang);



    @GET(ApiUrls.GET_GetSpeciality_URL)
    Call<SpecialityBean> getGetSpeciality();

    @FormUrlEncoded
    @POST(ApiUrls.GET_GetSpeciality_URL)
    Call<SpecialityBean> getGetSpeciality(@Field(KeyValues.ARG_P_LANG) String lang);

    @FormUrlEncoded
    @POST(ApiUrls.GET_STATE_LIST_URL)
    Call<StateList> getStateList(@Field(KeyValues.ARG_D_REGISTER_COUNTRY_ID) String countryID,@Field(KeyValues.ARG_P_LANG) String lang);


    @FormUrlEncoded
    @POST(ApiUrls.DOCTOR_REGISTER_URL)
    Call<RegisterResponseModel> createUserForDoctor(@Field(KeyValues.ARG_D_FirstName) String FirstName,
                                                    @Field(KeyValues.ARG_D_LastName) String LastName,
                                                    @Field(KeyValues.ARG_D_EmailId) String EmailId,
                                                    @Field(KeyValues.ARG_D_Password) String Password,
                                                    @Field(KeyValues.ARG_D_City) String City,
                                                    @Field(KeyValues.ARG_D_Phone) String Phone,
                                                    @Field(KeyValues.ARG_D_NPINumber) String NPINumber,
                                                    @Field(KeyValues.ARG_D_LicenseNumber) String LicenseNumber,
                                                    @Field(KeyValues.ARG_D_Pyear) String Pyear,
                                                    @Field(KeyValues.ARG_D_SpokenLanguage) String SpokenLanguage,
                                                    @Field(KeyValues.ARG_D_State) int State,
                                                    @Field(KeyValues.ARG_D_Country) int Country,
                                                    @Field(KeyValues.ARG_D_Title) String Title,
                                                    @Field(KeyValues.ARG_D_Speciality) int Speciality,
                                                    @Field(KeyValues.ARG_ConsultFee) String consult_fee,
                                                    @Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.USER_LOGIN_URL)
    Call<UserLoginDetails> userLogin(@Field(KeyValues.ARG_D_UserName) String FirstName,
                                     @Field(KeyValues.ARG_D_Type) String LastName,
                                     @Field(KeyValues.ARG_D_Password) String Password,
                                     @Field(KeyValues.ARG_D_DEVICE_Type) String DeviceType,
                                     @Field(KeyValues.ARG_D_DEVICE_Token) String DeviceToken,
                                     @Field(KeyValues.ARG_P_LANG) String language);

    @GET(ApiUrls.GET_DR_TITLE_URL)
    Call<TitleModel> getDTitleList();


    @FormUrlEncoded
    @POST(ApiUrls.GET_DR_TITLE_URL)
    Call<TitleModel> getDTitleList(@Field(KeyValues.ARG_P_LANG) String lang);


    @FormUrlEncoded
    @POST(ApiUrls.GET_PROFILE_URL)
    Call<UserLoginDetails> getUserProfile(@Field(KeyValues.ARG_D_USER_ID) String userID,
                                          @Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GET_Doctor_MyAccountDetails_URL)
    Call<AccountDetailModel> getDAccountDetails(@Field(KeyValues.ARG_D_USER_ID) String countryID
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GET_Set_Doctor_MyAccount_URL)
    Call<AccountDetailModel> updateAccountDetails(@Field(KeyValues.ARG_D_doctorId) String doctorId,
                                                  @Field(KeyValues.ARG_D_BankName) String BankName,
                                                  @Field(KeyValues.ARG_D_AccountType) String AccountType,
                                                  @Field(KeyValues.ARG_D_BSB) String BSB,
                                                  @Field(KeyValues.ARG_D_AccountNumber) String AccountNumber,
                                                  @Field(KeyValues.ARG_D_PayPalEmail) String PayPalEmail
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GET_GetDoctorPastConsult_URL)
    Call<DPastConsultModel> getDPastCosults(@Field(KeyValues.ARG_D_DoctorId) String DoctorId,
                                            @Field(KeyValues.ARG_D_Timezone) String Timezone
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatientPastConsult_URL)
    Call<DPastConsultModel> getPPastCosults(@Field(KeyValues.ARG_P_patientId) String DoctorId,
                                            @Field(KeyValues.ARG_timezone) String Timezone
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Patient_Appointment_URL)
    Call<GetPatientAppointmentModel> getPAllAppointment(@Field(KeyValues.ARG_UserId) String DoctorId,
                                                        @Field(KeyValues.ARG_timezonedifference) String Timezone
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GET_GetDoctorFutureConsult_URL)
    Call<DPastConsultModel> getDFutureCosults(@Field(KeyValues.ARG_D_DoctorId) String DoctorId,
                                              @Field(KeyValues.ARG_D_Timezone) String Timezone
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.PATIENT_REGISTER_URL)
    Call<RegisterResponseModel> createUserForPatient(@Field(KeyValues.ARG_D_FirstName) String FirstName,
                                                     @Field(KeyValues.ARG_D_LastName) String LastName,
                                                     @Field(KeyValues.ARG_D_EmialId) String EmailId,
                                                     @Field(KeyValues.ARG_D_Password) String Password,
                                                     @Field(KeyValues.ARG_D_City) String City,
                                                     @Field(KeyValues.ARG_D_Phone) String Phone,
                                                     @Field(KeyValues.ARG_D_State) int State,
                                                     @Field(KeyValues.ARG_D_Country) int Country,
                                                     @Field(KeyValues.ARG_D_DOB) String DOB,
                                                     @Field(KeyValues.ARG_D_PostCode) String PostCode,
                                                     @Field(KeyValues.ARG_D_gender) String gender,
                                                     @Field(KeyValues.ARG_D_Address1) String Address1,
                                                     @Field(KeyValues.ARG_D_Address2) String Address2
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GET_UpdateDoctorProfile_URL)
    Call<ProfileUpdateModel> updateUserForDoctor(@Field(KeyValues.ARG_D_FirstName) String FirstName,
                                                 @Field(KeyValues.ARG_D_LastName) String LastName,
                                                 @Field(KeyValues.ARG_D_Password) String Password,
                                                 @Field(KeyValues.ARG_D_City) String City,
                                                 @Field(KeyValues.ARG_D_Phone) String Phone,
                                                 @Field(KeyValues.ARG_D_NPINumber) String NPINumber,
                                                 @Field(KeyValues.ARG_D_LicenseNumber) String LicenseNumber,
                                                 @Field(KeyValues.ARG_D_Pyear) String Pyear,
                                                 @Field(KeyValues.ARG_D_SpokenLanguage) String SpokenLanguage,
                                                 @Field(KeyValues.ARG_D_State) int State,
                                                 @Field(KeyValues.ARG_D_Country) int Country,
                                                 @Field(KeyValues.ARG_D_Title) int Title,
                                                 @Field(KeyValues.ARG_D_Speciality) int Speciality,
                                                 @Field(KeyValues.ARG_D_DOCTOR_ID_UPDATE) String doctorId,
                                                 @Field(KeyValues.ARG_D_Address11) String address1,
                                                 @Field(KeyValues.ARG_D_Address21) String address2,
                                                 @Field(KeyValues.ARG_D_POST_CODE) String postalcode,
                                                 @Field(KeyValues.ARG_D_gender) int gender,
                                                 @Field(KeyValues.ARG_D_DOB) String DOB,
                                                 @Field(KeyValues.ARG_D_Qualification) String Qualification,
                                                 @Field(KeyValues.ARG_D_AboutMe) String AboutMe,
                                                 @Field(KeyValues.ARG_D_Image) String Image,
                                                 @Field(KeyValues.ARG_D_Mobilecode) int Mobilecode
           );


    @GET(ApiUrls.GET_Get_MedicalDevicesList_URL)
    Call<MedicalDevicesBean> getMedicalDevices();

    @FormUrlEncoded
    @POST(ApiUrls.GET_Get_MedicalDevicesList_URL)
    Call<MedicalDevicesBean> getMedicalDevices(@Field(KeyValues.ARG_P_LANG) String lang);


    @GET(ApiUrls.GET_Get_BlogManagement_URL)
    Call<BlogListModel> getBlogList();

    @FormUrlEncoded
    @POST(ApiUrls.GET_Get_BlogManagement_URL)
    Call<BlogListModel> getBlogList(@Field(KeyValues.ARG_P_LANG) String lang);



    @GET(ApiUrls.GET_Get_Get_FAQManagement_URL)
    Call<FaqModel> getFaqList();

    @FormUrlEncoded
    @POST(ApiUrls.GET_Get_Get_FAQManagement_URL)
    Call<FaqModel> getFaqList(@Field(KeyValues.ARG_P_LANG) String lang);


    @FormUrlEncoded
    @POST(ApiUrls.GET_MyMessage_URL)
    Call<PMessageModel> getPMessageList(@Field(KeyValues.ARG_D_UserId) String countryID
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_SentMessage_URL)
    Call<PMessageSentModel> PGet_SentMessageList(@Field(KeyValues.ARG_D_UserId) String id,
                                                 @Field(KeyValues.ARG_timezonedifference) String Timezone
            ,@Field(KeyValues.ARG_P_LANG) String language);

    /* Url :--- https://e911md.com/service.asmx/Get_SentMessage<br />Keys:----
             1. UserId (Logged In user Id) Required Number
     2. timezonedifference - Required(Time Zone Difference from Utc)*/
    @FormUrlEncoded
    @POST(ApiUrls.Send_MyMessage_URL)
    Call<RegisterResponseModel> sendMessgae(@Field(KeyValues.ARG_D_receiverid) String receiverid,
                                            @Field(KeyValues.ARG_D_senderid) String senderid,
                                            @Field(KeyValues.ARG_D_subject) String subject,
                                            @Field(KeyValues.ARG_Ds_message) String message
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GET_Contact_Us_URL)
    Call<RegisterResponseModel> contactUs(@Field(KeyValues.ARG_D_Email) String ARG_D_Email,
                                          @Field(KeyValues.ARG_D_ContactUsPhone) String ARG_D_ContactUsPhone,
                                          @Field(KeyValues.ARG_Ds_Subject) String ARG_Ds_Subject,
                                          @Field(KeyValues.ARG_Ds_Message) String ARG_Ds_Message,
                                          @Field(KeyValues.ARG_D_Name) String ARG_D_Name
            ,@Field(KeyValues.ARG_P_LANG) String language);


    //************************** Patient *************************************************************************************
    @GET(ApiUrls.GET_Patient_TITLE_URL)
    Call<PatientTitleModel> getPTitleList();

    @FormUrlEncoded
    @POST(ApiUrls.GET_Patient_TITLE_URL)
    Call<PatientTitleModel> getPTitleList(@Field(KeyValues.ARG_P_LANG) String lang);

    @FormUrlEncoded
    @POST(ApiUrls.GET_UpdatePatientProfile_URL)
    Call<UserLoginDetails> updateUserForPatient(@Field(KeyValues.ARG_D_FirstName) String FirstName,
                                                @Field(KeyValues.ARG_D_LastName) String LastName,
                                                @Field(KeyValues.ARG_D_Password) String Password,
                                                @Field(KeyValues.ARG_D_City) String City,
                                                @Field(KeyValues.ARG_D_Phone) String Phone,
                                                @Field(KeyValues.ARG_D_Mobilecode) int Mobilecode,
                                                @Field(KeyValues.ARG_P_patientMedicareNumber) String ARG_P_patientMedicareNumber,
                                                @Field(KeyValues.ARG_D_State) int State,
                                                @Field(KeyValues.ARG_D_Country) int Country,
                                                @Field(KeyValues.ARG_D_Title) String Title,
                                                @Field(KeyValues.ARG_P_patientId) String patientId,
                                                @Field(KeyValues.ARG_D_Address11) String address1,
                                                @Field(KeyValues.ARG_D_Address21) String address2,
                                                @Field(KeyValues.ARG_D_gender) int gender,
                                                @Field(KeyValues.ARG_D_DOB) String DOB
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.GET_PatientMyHistory_URL)
    Call<PMyHistoryModel> getPMyHistory(@Field(KeyValues.ARG_P_patientId_1) String patientId
           );

    @FormUrlEncoded
    @POST(ApiUrls.GET_Save_Allergies_URL)
    Call<RegisterResponseModel> saveAllergies(@Field(KeyValues.ARG_patientid) String patientId,
                                              @Field(KeyValues.ARG_ID) String ID,
                                              @Field(KeyValues.ARG_what_happen) String what_happen,
                                              @Field(KeyValues.ARG_name) String Name
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_Smoking_URL)
    Call<RegisterResponseModel> saveSmoking(@Field(KeyValues.ARG_patientid) int patientId,
                                            @Field(KeyValues.ARG_ID) int ID,
                                            @Field(KeyValues.ARG_yearstarted) String what_happen,
                                            @Field(KeyValues.ARG_howmanyeachday) String Name
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Past_Medical_history_URL)
    Call<RegisterResponseModel> savePastMedicalHistory(@Field(KeyValues.ARG_patientid) int patientId,
                                                       @Field(KeyValues.ARG_ID) int ID,
                                                       @Field(KeyValues.ARG_Ischecked) boolean Ischecked,
                                                       @Field(KeyValues.ARG_yearstarted) String yearstarted,
                                                       @Field(KeyValues.ARG_Namecondition) String Namecondition
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_ALcohal_URL)
    Call<RegisterResponseModel> saveAlcohol(@Field(KeyValues.ARG_patientid) int patientId,
                                            @Field(KeyValues.ARG_ID) int ID,
                                            @Field(KeyValues.ARG_noofstddrink_day) String noofstddrink_day,
                                            @Field(KeyValues.ARG_Alcohalnoofdays) String Alcohalnoofdays
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_FamilyHistory_URL)
    Call<RegisterResponseModel> saveFamilyHistory(@Field(KeyValues.ARG_patientid) int patientId,
                                                  @Field(KeyValues.ARG_ID) int ID,
                                                  @Field(KeyValues.ARG_Namecondition) String noofstddrink_day,
                                                  @Field(KeyValues.ARG_Whofamily) String Alcohalnoofdays
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Save_Update_Medical_History)
    Call<RegisterResponseModel> saveMedicalHistory(@Field(KeyValues.ARG_Patientid) int patientId,
                                                   @Field(KeyValues.ARG_ID) int ID,
                                                   @Field(KeyValues.ARG_Name) String Name,
                                                   @Field(KeyValues.ARG_Yearstarted) String Alcohalnoofdays,
                                                   @Field(KeyValues.ARG_Dose) String Dose,
                                                   @Field(KeyValues.ARG_Strength) String Strength
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_Waist_URL)
    Call<RegisterResponseModel> saveWaist(@Field(KeyValues.ARG_patientid) int patientId,
                                          @Field(KeyValues.ARG_ID) int ID,
                                          @Field(KeyValues.ARG_waist) String waist
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_Height_URL)
    Call<RegisterResponseModel> saveHeight(@Field(KeyValues.ARG_Patientid) int patientId,
                                           @Field(KeyValues.ARG_ID) int ID,
                                           @Field(KeyValues.ARG_Height) String Height
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Save_Update_Height_URL)
    Call<RegisterResponseModel> saveMidicalHi(@Field(KeyValues.ARG_Patientid) int patientId,
                                              @Field(KeyValues.ARG_ID) int ID,
                                              @Field(KeyValues.ARG_Height) String Height
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GET_Delete_History_URL)
    Call<RegisterResponseModel> deleteMyHistoryRow(@Field(KeyValues.ARG_D_Type) int patientId,
                                                   @Field(KeyValues.ARG_ID) int ID
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Patient_LifeStyle_URL)
    Call<PLifeStyleModel> getPLifeStyle(@Field(KeyValues.ARG_P_patientId) int patientId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Patient_History_familyHistory_URL)
    Call<PFamilyHistoryModel> getPFamilyHistory(@Field(KeyValues.ARG_P_patientId) int patientId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Set_Patient_Billing_Information_URL)
    Call<ProfileUpdateModel> updatePatient_Billing_Information(@Field(KeyValues.ARG_P_cardNAme) String cardName,
                                                               @Field(KeyValues.ARG_P_cardNo) String cardNo,
                                                               @Field(KeyValues.ARG_P_cvv) String cvv,
                                                               @Field(KeyValues.ARG_P_expireMonth) String expireMonth,
                                                               @Field(KeyValues.ARG_P_expireYear) String expireYear,
                                                               @Field(KeyValues.ARG_P_Address1) String address1,
                                                               @Field(KeyValues.ARG_P_Address2) String address2,
                                                               @Field(KeyValues.ARG_P_City) String city,
                                                               @Field(KeyValues.ARG_P_State) int state,
                                                               @Field(KeyValues.ARG_P_patient_id) int userId,
                                                               @Field(KeyValues.ARG_P_IsInsurence) String isInsurance,
                                                               @Field(KeyValues.ARG_P_InsurenceCompny) String insuranceCompany,
                                                               @Field(KeyValues.ARG_P_InsurenceNo) String insuranceNo,
                                                               @Field(KeyValues.ARG_P_ZipCode) String zip,
                                                               @Field(KeyValues.ARG_country) int country
            ,@Field(KeyValues.ARG_P_LANG) String language

    );


    @FormUrlEncoded
    @POST(ApiUrls.Get_Patient_Billing_Information_URL)
    Call<BillingInformationModel> getBillingInfo(@Field(KeyValues.ARG_D_USER_ID) String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_FamilyMembers_URL)
    Call<PatientFamiliyMembersModel> Get_Patient_FamilyMembers(@Field(KeyValues.ARG_D_USER_ID) String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @GET(ApiUrls.GET_RelationNames_URL)
    Call<RelationshipType> getPRelationList();

    @FormUrlEncoded
    @POST(ApiUrls.GET_RelationNames_URL)
    Call<RelationshipType> getPRelationList(@Field(KeyValues.ARG_P_LANG) String lang);


    @FormUrlEncoded
    @POST(ApiUrls.Add_Patient_FamilyMembers_URL)
    Call<ProfileUpdateModel> addPatientFamilyMembers(@Field("FirstName") String FirstName,
                                                     @Field("LastName") String LastName,
                                                     @Field("City") String City,
                                                     @Field("Phone") String Phone,
                                                     @Field("State") int State,
                                                     @Field("PostalCode") String postal,
                                                     @Field("PatientID") String patientId,
                                                     @Field("Address") String address1,
                                                     @Field("Gender") int gender,
                                                     @Field("DOB") String DOB,
                                                     @Field("Relation") int relation,
                                                     @Field("Email") String email
            ,@Field(KeyValues.ARG_P_LANG) String language
    );


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Weight_URL)
    Call<GetPatientWeightModel> GetPatient_Weight(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Waist_URL)
    Call<GetPatientWaistModel> GetPatientWaist(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Get_Doctor_Patient_ALL_URL)
    Call<ComposeMsgTo> getComposerMsgTo(@Field(KeyValues.ARG_P_Type) String type,
                                        @Field(KeyValues.ARG_User_Id) String userId
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.ListMedicinePatien_URL)
    Call<GetMedicinesList> getListMedicinePatien(@Field(KeyValues.ARG_PatientId) String type
            ,@Field(KeyValues.ARG_P_LANG) String language
    );


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_BloodPressure_URL)
    Call<GetPatientBloodPressureModel> GetPatientBloodPressure(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_BloodGlussoe_URL)
    Call<GetPatientBloodGlucoseModel> GetPatientBloodGlucose(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_BloodOxygen_URL)
    Call<GetBloodOxygenModel> GetPatientBloodOxygen(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_BMI_URL)
    Call<PatientBMIDetailModel> GetPatientBMI(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_ECG_URL)
    Call<PatientEcgDetailModel> GetPatientECG(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_ENT_URL)
    Call<PatientEntDetailModel> GetPatientENT(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Skin_URL)
    Call<PatientSkinDetailModel> GetPatientSkinDetail(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_HeartRate_URL)
    Call<PatientHeartRateModel> GetPatientHeartRate(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Height_URL)
    Call<PatientHeightModel> GetPatientHeight(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Temperature_URL)
    Call<PatientTemperatureDetailModel> GetPatientTemperature(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatient_Summry_URL)
    Call<PatientSummeryDetailModel> GetPatientSummery(@Field("patientid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Set_Patient_History_familyHistory_URL)
    Call<RegisterResponseModel> addPatientHistoryOnTab(@Field(KeyValues.ARG_P_patientId) String patientId,
                                                       @Field(KeyValues.ARG_Relation) int Relation,
                                                       @Field(KeyValues.ARG_Ischecked) boolean Ischecked,
                                                       @Field(KeyValues.ARG_Familyhistory) String Familyhistory
            ,@Field(KeyValues.ARG_P_LANG) String language);
    @FormUrlEncoded
    @POST(ApiUrls.Set_Patient_History_familyHistory_URL_NEW)
    Call<PFamilyHistoryModel> addPatientHistoryOnTabNew(@Field(KeyValues.ARG_P_patientId) String patientId,
                                                        @Field(KeyValues.ARG_Relation) int Relation,
                                                        @Field(KeyValues.ARG_Ischecked) boolean Ischecked,
                                                        @Field(KeyValues.ARG_Familyhistory) String Familyhistory
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Set_Patient_LifeStyle_URL)
    Call<RegisterResponseModel> setLifeStyleOnTab(@Field(KeyValues.ARG_P_patientId) String patientId,
                                                  @Field(KeyValues.ARG_QuestionId) int Relation,
                                                  @Field(KeyValues.ARG_Ischecked) int Ischecked
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetDoctorAvailability_URL)
    Call<DAvalilibilityModel> getDAvailability(@Field(KeyValues.ARG_DoctorId) String DoctorId,
                                               @Field(KeyValues.ARG_timezone) String Timezonedifference
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Doctor_List_URL)
    Call<DoctorListModel> getDoctorList(@Field(KeyValues.ARG_countryName) String country,
                                        @Field(KeyValues.ARG_stateName) String state,
                                        @Field(KeyValues.ARG_Timezone) String Timezone,
                                        @Field(KeyValues.ARG_sex) String sex,
                                        @Field(KeyValues.ARG_speciality) String speciality,
                                        @Field(KeyValues.ARG_AppointmentDate) String AppointmentDate,
                                        @Field(KeyValues.ARG_PreferredTime) String PreferredTime,
                                        @Field(KeyValues.ARG_LanguageSpoken) String LanguageSpoken,
                                        @Field(KeyValues.ARG_name) String name
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.Get_PatientPharmacy_URL)
    Call<PPharmacyModel> getPPharmacy(@Field(KeyValues.ARG_D_USER_ID) String country
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Change_Patient_Pharmacy_URL)
    Call<PPharmacyModel> changePharmacy(@Field(KeyValues.ARG_address) String address,
                                        @Field(KeyValues.ARG_country) String country,
                                        @Field(KeyValues.ARG_state) String state,
                                        @Field(KeyValues.ARG_city) String city,
                                        @Field(KeyValues.ARG_phone) String phone,
                                        @Field(KeyValues.ARG_postcode) String postcode,
                                        @Field(KeyValues.ARG_name) String name,
                                        @Field(KeyValues.ARG_patientid) String patientid
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.Change_Patient_Pharmacy_URL)
    Call<PPharmacyModel> setAvailability(@Field(KeyValues.ARG_DoctorId) String DoctorId,
                                         @Field(KeyValues.ARG_FromDate) String FromDate,
                                         @Field(KeyValues.ARG_FromHour) String FromHour,
                                         @Field(KeyValues.ARG_FromMinute) String FromMinute,
                                         @Field(KeyValues.ARG_FromTime) String FromTime,
                                         @Field(KeyValues.ARG_ToDate) String ToDate,
                                         @Field(KeyValues.ARG_ToHour) String ToHour,
                                         @Field(KeyValues.ARG_ToMinute) String ToMinute,
                                         @Field(KeyValues.ARG_ToTime) String ToTime,
                                         @Field(KeyValues.ARG_Timezone) String Timezone,
                                         @Field(KeyValues.ARG_TimeZoneDifference) String TimeZoneDifference
            ,@Field(KeyValues.ARG_P_LANG) String language
    );


    @FormUrlEncoded
    @POST(ApiUrls.BookAppointment_URL)
    Call<BookingSummeryModelFromServer> bookingAppointment(@Field("patientsId") String PatientID,
                                                           @Field(KeyValues.ARG_DoctorId) String DoctorId,
                                                           @Field(KeyValues.ARG_Reason) String Reason,
                                                           @Field(KeyValues.ARG_Comment) String Comment,
                                                           @Field(KeyValues.ARG_AppointMode) String AppointMode,
                                                           @Field(KeyValues.ARG_BookingDateTime) String BookingDateTime,
                                                           @Field(KeyValues.ARG_timeZone) String timeZone,
                                                           @Field(KeyValues.ARG_File) String File
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.BookAppointmentDoctorCall_URL)
    Call<BookingSummeryModelFromServer> bookingAppointmentDoctorForCall(@Field("PatientsId") String PatientID,

                                                                        @Field(KeyValues.ARG_Reason) String Reason,
                                                                        @Field(KeyValues.ARG_Comment) String Comment,
                                                                        @Field(KeyValues.ARG_PatientPhone) String patientPhone,

                                                                        @Field(KeyValues.ARG_timeZone) String timeZone,
                                                                        @Field(KeyValues.ARG_File) String File
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.BookingPayment_URL)
    Call<BookingSummeryModelFromServer> bookingPayment(@Field(KeyValues.ARG_PatientID) String PatientID,
                                                       @Field(KeyValues.ARG_BookingID) String BookingID,
                                                       @Field(KeyValues.ARG_TransactionNumber) String TransactionNumber,
                                                       @Field(KeyValues.ARG_TransactionType) String TransactionType,
                                                       @Field(KeyValues.ARG_timeZone) String timeZone
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Doctor_Appointment_URL)
    Call<DAppointmentListModel> getDAppointmentList(@Field(KeyValues.ARG_D_UserId) String UserId,
                                                    @Field(KeyValues.ARG_Timezonedifference) String Timezonedifference
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.GetPatientFutureConsult_URL)
    Call<DAppointmentListModel> getPAppointmentList(@Field(KeyValues.ARG_P_patientId) String UserId,
                                                    @Field(KeyValues.ARG_timezone) String Timezonedifference
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.set_appointment_approved_reject_URL)
    Call<RegisterResponseModel> acceptsAppointment(@Field(KeyValues.ARG_appointmentid) String appointmentid,
                                                   @Field(KeyValues.ARG_action) String action,
                                                   @Field(KeyValues.ARG_patientemail) String patientemail,
                                                   @Field(KeyValues.ARG_doctorname) String doctorname
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @GET(ApiUrls.getAppointmentStatus_URL)
    Call<AppointmentStatusModel> acceptsAppointmentStatusList();

    @FormUrlEncoded
    @POST(ApiUrls.getAppointmentStatus_URL)
    Call<AppointmentStatusModel> acceptsAppointmentStatusList(@Field(KeyValues.ARG_P_LANG) String lang);

    @FormUrlEncoded
    @POST(ApiUrls.DeleteAvailability_URL)
    Call<SetAvailabilityResponseModel> deleteDAvai(@Field(KeyValues.ARG_AvailableId) String AvailableId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Add_Doctor_Availability_URL)
    Call<SetAvailabilityResponseModel> addDoctorAvailability(@Field("FromDate") String FromDate,
                                                             @Field("FromHour") String FromHour,
                                                             @Field("FromMinute") String FromMinute,
                                                             @Field("FromTime") String FromTime,
                                                             @Field("ToDate") String ToDate,
                                                             @Field("ToHour") String ToHour,
                                                             @Field("ToMinute") String ToMinute,
                                                             @Field("ToTime") String ToTime,
                                                             @Field("DoctorId") int DoctorId,
                                                             @Field("Timezone") String Timezone,
                                                             @Field("TimeZoneDifference") String TimeZoneDifference,
                                                             @Field("availabledays") String weedDay,
                                                             @Field("AvailableId") String availableId
            ,@Field(KeyValues.ARG_P_LANG) String language

    );

    @GET(ApiUrls.GetTimeZone_URL)
    Call<GetTimeZoneModel> GetTimeZone();

    @FormUrlEncoded
    @POST(ApiUrls.GetTimeZone_URL)
    Call<GetTimeZoneModel> GetTimeZone(@Field(KeyValues.ARG_P_LANG) String lang);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Doctor_Mypatients_URL)
    Call<MyPatientsModel> GetDoctorMypatients(@Field("doctorid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Get_Doctor_MyTransaction_URL)
    Call<MyTransactionModel> GetDoctorMyTransaction(@Field("doctorid") String userId,
                                                    @Field("timezonedifference") String timezonedifference,
                                                    @Field("fromDate") String from_date,
                                                    @Field("toDate") String to_date
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Get_MyPersonalNotes_URL)
    Call<GetPersonalNotesModel> GetMyPersonalNotes(@Field("doctorid") String userId,
                                                   @Field("timezonedifference") String timezonedifference
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Delete_MyPersonalNotes_URL)
    Call<ProfileUpdateModel> DeleteMyPersonalNotes(@Field("ticketid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.AddDoctorProblem_URL)
    Call<ProfileUpdateModel> AddDoctorProblems(@Field("doctorid") String userId,
                                               @Field("problem") String dProblem
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.GetDoctorProblem_URL)
    Call<DoctorPreviousTicketsModel> GetDoctorProblems(@Field("doctorid") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.Delete_Doctor_Ticket_URL)
    Call<ProfileUpdateModel> Delete_Doctor_Ticket(@Field("ticketid") String ticketId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.SetCommunicationNote_URL)
    Call<BookingSummeryModelFromServer> setCommunicationNote(@Field(KeyValues.ARG_AppointId) String appointmentid,
                                                             @Field(KeyValues.ARG_DoctorId) String DoctorId,
                                                             @Field(KeyValues.ARG_PatientId) String patientemail,
                                                             @Field(KeyValues.ARG_Message) String doctorname,
                                                             @Field(KeyValues.ARG_SignDate) String SignDate
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.SetPhysicianOrder_URL)
    Call<BookingSummeryModelFromServer> SetPhysicianOrder(@Field(KeyValues.ARG_AppointId) String appointmentid,
                                                          @Field(KeyValues.ARG_DoctorId) String DoctorId,
                                                          @Field(KeyValues.ARG_PatientId) String patientemail,
                                                          @Field(KeyValues.ARG_Message) String doctorname,
                                                          @Field(KeyValues.ARG_SignDate) String SignDate
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @Multipart
    @POST(ApiUrls.GET_UpdateDoctorProfile_URL)
    Call<UserLoginDetails> addRegularUser(@PartMap() Map<String, RequestBody> partMap
    );

    @Multipart
    @POST(ApiUrls.GET_UpdateDoctorProfile_URL)
    Call<UserLoginDetails> addRegularUserWithProfileImg(@PartMap() Map<String, RequestBody> partMap,
                                                        @Part MultipartBody.Part imageFile);

    @Multipart
    @POST(ApiUrls.GET_UpdateDoctorProfile_URL)
    Call<UserLoginDetails> addRegularUserWithResumeImage(@PartMap() Map<String, RequestBody> partMap,
                                                         @Part MultipartBody.Part imagePic,
                                                         @Part MultipartBody.Part imageresume);

    @Multipart
    @POST(ApiUrls.GET_UpdateDoctorProfile_URL)
    Call<UserLoginDetails> addRegularUserWithResSign(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part imageFile,
                                                     @Part MultipartBody.Part imageSign,
                                                     @Part MultipartBody.Part imageresume);


    @FormUrlEncoded
    @POST(ApiUrls.updateUserPassword_URL)
    Call<BookingSummeryModelFromServer> changePassword(@Field(KeyValues.ARG_Patientid) String Patientid,
                                                       @Field(KeyValues.ARG_userPassword) String userPassword
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.setAllowPhysicians_URL)
    Call<BookingSummeryModelFromServer> updateAllowPhysicians(@Field(KeyValues.ARG_Patientid) String Patientid,
                                                              @Field(KeyValues.ARG_IsAllowPhysicians) String IsAllowPhysicians
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.Get_Patient_Billing_History_URL)
    Call<PMyBillingHistoryModel> getPatientBilingHistory(@Field(KeyValues.ARG_Patientid) String Patientid,
                                                         @Field(KeyValues.ARG_timezonedifference) String timezonedifference
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @Multipart
    @POST(ApiUrls.SetPatientPic_URL)
    Call<UpdateProfilePictureModel> addSetPatientPic(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part imageFile);

    @FormUrlEncoded
    @POST(ApiUrls.SetDoctorCall_URL)
    Call<BookingSummeryModelFromServer> setDoctorCall(@Field(KeyValues.ARG_appointId) String appointId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.ShowMyTrackers_URL)
    Call<PatientSummeryDetailModel> ShowMyTrackSummery(@Field("PatientId") String patientId,
                                                       @Field("TrackAll") String TrackAll
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.ShowAllMyTrackers_URL)
    Call<PatientSummeryDetailModel> ShowAllMyTrackers(@Field("PatientId") String userId,
                                                      @Field("Result_Pressure_YN") String result_pressure,
                                                      @Field("Gulose_YN") String Gulose_YN,
                                                      @Field("Oxygen_YN") String Oxygen_YN,
                                                      @Field("Weight_YN") String Weight_YN,
                                                      @Field("ECG_YN") String ECG_YN,
                                                      @Field("Temprature_YN") String Temprature_YN,
                                                      @Field("Height_YN") String Height_YN,
                                                      @Field("Ent_YN") String Ent_YN,
                                                      @Field("Bmi_YN") String Bmi_YN,
                                                      @Field("Skin_YN") String Skin_YN,
                                                      @Field("HeartRate_YN") String HeartRate_YN,
                                                      @Field("SpiroMetry_YN") String SpiroMetry_YN,
                                                      @Field("Motion_YN") String Motion_YN,
                                                      @Field("ResultAll_YN") String ResultAll_YN
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.ShowMyOneTrackers_URL)
    Call<PatientSummeryDetailModel> ShowMyOneTrackers_URL(@Field("PatientId") String userId,
                                                          @Field("Type") String result_pressure,
                                                          @Field("IsTrue") String ResultAll_YN
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @Multipart
    @POST(ApiUrls.DOCTOR_REGISTER_URL)
    Call<RegisterResponseModel> createUserForDoctor1(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part resumeFile);


    @FormUrlEncoded
    @POST(ApiUrls.SetWeightMannully_URL)
    Call<SetWeightMannullyModel> setWeightMannully(@Field(KeyValues.ARG_PatientID) String patientid,
                                                   @Field(KeyValues.ARG_P_Weight) String patientd,
                                                   @Field(KeyValues.ARG_P_MeasureDateTime) String measuredDate
            ,@Field(KeyValues.ARG_P_LANG) String language );


    @FormUrlEncoded
    @POST(ApiUrls.SetBloodOxygenManually_URL)
    Call<AddMannullyBloodOxygenModel> SetBloodOxygenManually(@Field(KeyValues.ARG_PatientID) String patientid,
                                                             @Field(KeyValues.ARG_P_OxygenValue) String oxygenValue,
                                                             @Field(KeyValues.ARG_P_MeasureDateTime) String measuredDate
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.SetTemperatureManually_URL)
    Call<AddMunnalTemperatureModel> SetTemperatureManually(@Field(KeyValues.ARG_PatientID) String patientid,
                                                           @Field(KeyValues.ARG_P_TemperatureValue) String temperatureValue,
                                                           @Field(KeyValues.ARG_P_MeasureDateTime) String measuredDate
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @FormUrlEncoded
    @POST(ApiUrls.SetBloodPressureManually_URL)
    Call<AddManulyBloodPressureModel> SetBloodPressureManually(@Field(KeyValues.ARG_PatientID) String patientid,
                                                               @Field(KeyValues.ARG_P_SystolicValue) String systValue,
                                                               @Field(KeyValues.ARG_P_DiastolicValue) String diasDate,
                                                               @Field(KeyValues.ARG_P_PulseValue) String pulsDate,
                                                               @Field(KeyValues.ARG_P_MeasureDateTime) String measuredDate
            ,@Field(KeyValues.ARG_P_LANG) String language
    );

    @Multipart
    @POST(ApiUrls.SetECGManually_URL)
    Call<AddManualEcgModel> addEcgManuly(@PartMap() Map<String, RequestBody> partMap,
                                         @Part MultipartBody.Part imageFile);

    @Multipart
    @POST(ApiUrls.SetOTHERMEDICAL_URL)
    Call<AddOtherMedicalHistoryModel> addOtherMedicalHistory(@PartMap() Map<String, RequestBody> partMap,
                                                             @Part MultipartBody.Part imageFile);

    @Multipart
    @POST(ApiUrls.SetEntManually_URL)
    Call<AddManualEcgModel> addEntManuly(@PartMap() Map<String, RequestBody> partMap,
                                         @Part MultipartBody.Part imageFile);

    @Multipart
    @POST(ApiUrls.SetSkinManually_URL)
    Call<AddManualEcgModel> addSetSkinManually(@PartMap() Map<String, RequestBody> partMap,
                                               @Part MultipartBody.Part imageFile);


    @Multipart
    @POST(ApiUrls.BookAppointment_URL)
    Call<BookingSummeryModelFromServer> bookingAppointmentWithFile(@PartMap() Map<String, RequestBody> partMap,
                                                                   @Part MultipartBody.Part imageFile);

    @FormUrlEncoded
    @POST(ApiUrls.LogOutUser_URL)
    Call<AppointmentStatusModel> logoutUserFromApp(@Field(KeyValues.ARG_UserId) String DoctorId
            ,@Field(KeyValues.ARG_P_LANG) String language);


    @FormUrlEncoded
    @POST(ApiUrls.AddReminderMedicine_URL)
    Call<SetMedicineReminderModel> setPatientMedicine(@Field("StartDate") String FromDate,
                                                      @Field("EndDate") String FromHour,
                                                      @Field("Weekdays") String FromMinute,
                                                      @Field("MedicineId") String FromTime,
                                                      @Field("MedicineName") String ToDate,
                                                      @Field("Dosase") String ToHour,
                                                      @Field("TimeTaken") String ToMinute,
                                                      @Field("NotesOfUse") String ToTime,
                                                      @Field("PatientId") String PatientId
            ,@Field(KeyValues.ARG_P_LANG) String language

    );

    @FormUrlEncoded
    @POST(ApiUrls.ListReminderMedicine_URL)
    Call<SetMedicineReminderModel> getReminderList(@Field("PatientId") String PatientId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.TakenMedicinePatient_URL)
    Call<SetMedicineReminderModel> TakenMedicinePatientList(@Field("ReminderId") String ReminderId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    //
    // https://e911md.com/service.asmx/TakenMedicinePatient<br />
    @FormUrlEncoded
    @POST(ApiUrls.RefillMedicine_URL)
    Call<UpdateProfilePictureModel> refillMedicine(@Field(KeyValues.ARG_UserId) String senderId,
                                                   @Field(KeyValues.ARG_MedicineName) String mName,
                                                   @Field(KeyValues.ARG_Qty) String qty,
                                                   @Field(KeyValues.ARG_Date) String date,
                                                   @Field(KeyValues.ARG_comment) String comment
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.RequestAppointment_URL)
    Call<UpdateProfilePictureModel> appointmentRequest(@Field(KeyValues.ARG_Date) String appDate,
                                                       @Field(KeyValues.ARG_P_IdealTime) String idleTime,
                                                       @Field(KeyValues.ARG_P_ContactNo) String ContactNo,
                                                       @Field(KeyValues.ARG_P_Reason) String reasons,
                                                       @Field(KeyValues.ARG_Comment) String comment,
                                                       @Field(KeyValues.ARG_P_ChatType) String ChatType,
                                                       @Field(KeyValues.ARG_P_DoctorName) String doctorName,
                                                       @Field(KeyValues.ARG_P_DoctorEmail) String doctorEmail,
                                                       @Field(KeyValues.ARG_P_PatientName) String patientName,
                                                       @Field(KeyValues.ARG_P_PatientEmail) String patientEmail
            ,@Field(KeyValues.ARG_P_LANG) String language);



    @FormUrlEncoded
    @POST(ApiUrls.Forgot_Password_URL)
    Call<ForgotPasswordModel> forgotenPassword(@Field(KeyValues.ARG_D_EmialId) String email
            ,@Field(KeyValues.ARG_P_LANG) String language                 );

    @FormUrlEncoded
    @POST(ApiUrls.Delete_OtherMedicalHistory_URL)
    Call<AddOtherMedicalHistoryModel> Delete_OtherMedicalHistory(@Field(KeyValues.ARG_HistoryId) String OthersId
            ,@Field(KeyValues.ARG_P_LANG) String language );

    @FormUrlEncoded
    @POST(ApiUrls.SendVerifyCode_URL)
    Call<SendMobileVerifyCodeModel> sendCodeUserMobile(@Field("UserId") String userId
            ,@Field(KeyValues.ARG_P_LANG) String language);

    @FormUrlEncoded
    @POST(ApiUrls.VerifyMobile_URL)
    Call<VerifyMobileCodeModel> VerifyUserMobile(@Field("UserId") String userId, @Field("VerifyCode") String verifyCode
            ,@Field(KeyValues.ARG_P_LANG) String language);
}

