package com.sibsefid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.doctor.adapter.CountrySpinnerAdapter;
import com.sibsefid.doctor.adapter.SpecialitySpinnerAdapter;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.doctor.adapter.StateSpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.RetrofitUtils;
import com.sibsefid.model.doctor.RegisterModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.SpecialityBean;
import com.sibsefid.model.doctor.TitleModel;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.KeyValues;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorSignUpActivity extends AppCompatActivity implements Callback<StateList> {

    private static final String TAG = "DoctorSignUpActivity";
    private static final int REQUEST_CODE_DOC = 1;

    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Callback<RegisterResponseModel> registerResponseModelCall = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {

            if (DoctorSignUpActivity.this != null) {
                if (response.isSuccessful()) {
                    try {
                        //Log.e(TAG, "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(DoctorSignUpActivity.this, response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(DoctorSignUpActivity.this, response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DoctorSignUpActivity.this, getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> response, Throwable t) {
            if (DoctorSignUpActivity.this != null) {
                showProgress(false);
                Toast.makeText(DoctorSignUpActivity.this, getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private ImageView mLogo;
    private String docFilePath = "";
    private TextView mTitle;
    private ImageView mBackBtn;
    private int LOGINTYPE = 0;
    private AutoCompleteTextView mFirstName;
    private AutoCompleteTextView mLastName;
    private MaterialSpinner mSelectCountry;
    private MaterialSpinner mSelectDesignation;
    private MaterialSpinner mSelectSpeciality;
    private AutoCompleteTextView mEnterPracticeYear;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;
    private AutoCompleteTextView mPhoneNumber;
    private AutoCompleteTextView mNPINumber;
    private MaterialSpinner mSelectState;
    private AutoCompleteTextView mSelectCity;
    private AutoCompleteTextView mLicenceNumber;
    private AutoCompleteTextView mSpokenLanguage;
    private EditText mConsultFee;
    private TextView mAttacheFile;
    private String[] COUNTRY_NAME = null;
    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private ArrayList<TitleModel.TitleBean> titleBeenArrayList = new ArrayList<>();
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    Callback<TitleModel> titleModelCallback = new Callback<TitleModel>() {
        @Override
        public void onResponse(Call<TitleModel> call, Response<TitleModel> response) {
            if (DoctorSignUpActivity.this != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getTitle() != null && response.body().getTitle().size() > 0) {
                        if (response.body().getTitle() != null) {
                            titleBeenArrayList = response.body().getTitle();
                            mList_Title.clear();
                            for (int i = 0; i < response.body().getTitle().size(); i++) {
                                mList_Title.add(response.body().getTitle().get(i).getDetail_code_nm());
                                mList_Title_ID.add(response.body().getTitle().get(i).getDetail_code());
                            }
                            udpdateTitle();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<TitleModel> call, Throwable t) {
            t.printStackTrace();
        }
    };
    // private String[] STATE_NAME = null;
    // private HashMap<String, String> hashMapState = new HashMap<>();
    private ArrayList<StateList.City> stateArrayList = new ArrayList<>();
    //  private String[] SPECIALIST_NAME = null;
    private ArrayList<SpecialityBean.Speciality> arraySpeciality = new ArrayList();
    //private HashMap<String, String> hashMapSpeciality = new HashMap<>();
    Callback<SpecialityBean> specialityBeanCallback = new Callback<SpecialityBean>() {
        @Override
        public void onResponse(Call<SpecialityBean> call, Response<SpecialityBean> response) {
            if (DoctorSignUpActivity.this != null) {
                if (response.isSuccessful()) {
                    try {
                        final String responseString = new String(response.raw().body().string());
                        //Log.e(TAG, "onResponse: " + responseString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response.body().isSuccess() && response.body().getSpeciality() != null && response.body().getSpeciality().size() > 0) {
                        // SPECIALIST_NAME = new String[response.body().getSpeciality().size()];
                        arraySpeciality = response.body().getSpeciality();

                        SpecialitySpinnerAdapter adapter = new SpecialitySpinnerAdapter(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, arraySpeciality);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSelectSpeciality.setAdapter(adapter);


                    }
                }
            }
        }

        @Override
        public void onFailure(Call<SpecialityBean> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private ArrayList<StateList.City> specialityArrayList = new ArrayList<>();

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(".");

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static String getExtension(String filePath) {
        File f = new File(filePath);
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(".");

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private void udpdateTitle() {
        mSelectDesignation.setAdapter(new SpinnerAdapter(DoctorSignUpActivity.this, 0, mList_Title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LOGINTYPE = MyPrefs.getUser(this);

        setContentView(R.layout.signup_doctor);

        View view = getLayoutInflater().inflate(R.layout.tool_bar, null);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        getSupportActionBar().setCustomView(view, params);

        countryArrayList = MyPrefs.getCountryList(this);

        if (countryArrayList != null) {
            COUNTRY_NAME = new String[countryArrayList.size()];
            for (int i = 0; i < countryArrayList.size(); i++) {
                COUNTRY_NAME[i] = countryArrayList.get(i).getDetail_code_nm();
                hashMapCountry.put(countryArrayList.get(i).getDetail_code_nm(), countryArrayList.get(i).getDetail_code());
            }
        }

        initiateUi();


        if (Utils.isDeviceOnline(DoctorSignUpActivity.this)) {
            String selLang=Utils.getuserSeletedLanagueForRequestSend(DoctorSignUpActivity.this);
            RestAdapter.getAdapter().getGetSpeciality(selLang).enqueue(specialityBeanCallback);
            RestAdapter.getAdapter().getDTitleList(selLang).enqueue(titleModelCallback);
        }

    }

    private void initiateUi() {


        mPasswordView = (EditText) findViewById(R.id.mPassword);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        mFirstName = (AutoCompleteTextView) findViewById(R.id.mFirstName);
        mLastName = (AutoCompleteTextView) findViewById(R.id.mLastName);
        mSelectCountry = (MaterialSpinner) findViewById(R.id.mSelectCountry);
        mSelectSpeciality = (MaterialSpinner) findViewById(R.id.mSelectSpeciality);
        mSelectDesignation = (MaterialSpinner) findViewById(R.id.mSelectDesignation);
        mEnterPracticeYear = (AutoCompleteTextView) findViewById(R.id.mEnterPracticeYear);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mConfirmPassword = (EditText) findViewById(R.id.mConfirmPassword);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPhoneNumber = (AutoCompleteTextView) findViewById(R.id.mPhoneNumber);
        mNPINumber = (AutoCompleteTextView) findViewById(R.id.mNPINumber);
        mSelectState = (MaterialSpinner) findViewById(R.id.mSelectState);
        mSelectCity = (AutoCompleteTextView) findViewById(R.id.mSelectCity);
        mLicenceNumber = (AutoCompleteTextView) findViewById(R.id.mLicenceNumber);
        mSpokenLanguage = (AutoCompleteTextView) findViewById(R.id.mSpokenLanguage);
        mConsultFee = (EditText) findViewById(R.id.mConsultFee);
        mAttacheFile = (TextView) findViewById(R.id.mAttacheFile);

        mAttacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(DoctorSignUpActivity.this, "File attachment functionality is remaining!", Toast.LENGTH_SHORT).show();
                getDocument();
            }
        });

        /*if (countryArrayList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, COUNTRY_NAME);
            mSelectCountry.setAdapter(adapter);
        }*/

        mList_Title = new ArrayList<>();
        mList_Title_ID = new ArrayList<>();

        CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, countryArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectCountry.setAdapter(adapter);
        for (int i = 0; i < countryArrayList.size(); i++) {
            if (countryArrayList.get(i).getDetail_code_nm().equalsIgnoreCase("United States")) {
                mSelectCountry.setSelection(i + 1);

            }
        }


        mSelectState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectState.setTag(null);
                if (i < 0)
                    return;
                mSelectState.setTag(stateArrayList.get(i).getDetail_code());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       /* mSelectState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b) {
                    if (!TextUtils.isEmpty(mSelectState.getText().toString())) {
                        String IDGetFromHash = hashMapState.get(mSelectState.getText().toString().trim());
                        String ID = (String) mSelectState.getTag();
                        if (ID != null && ID.length() > 0 && IDGetFromHash != null) {
                            if (IDGetFromHash.equalsIgnoreCase(ID)) {
                                return;
                            } else {
                                showErrorOnEdt(mSelectState, getString(R.string.error_invalid_state_name));
                            }
                        } else {
                            showErrorOnEdt(mSelectState, getString(R.string.error_invalid_state_name));
                        }
                    }
                }

            }
        });*/


        mSelectSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectSpeciality.setTag(null);
                if (i < 0)
                    return;
                mSelectSpeciality.setTag(arraySpeciality.get(i).getSpecid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSelectDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectDesignation.setTag(mList_Title_ID.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSelectCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectCountry.setTag(null);
                if (i < 0)
                    return;
                mSelectCountry.setTag(countryArrayList.get(i).getDetail_code());
                stateArrayList = new ArrayList<>();
                StateSpinnerAdapter adapter = new StateSpinnerAdapter(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSelectState.setAdapter(adapter);
                String selLang=Utils.getuserSeletedLanagueForRequestSend(DoctorSignUpActivity.this);
                RestAdapter.getAdapter().getStateList(countryArrayList.get(i).getDetail_code(),selLang).enqueue(DoctorSignUpActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        ImageView mBackBtn = (ImageView) findViewById(R.id.mBackBtn);
        mLogo = (ImageView) findViewById(R.id.mLogo);
        mTitle = (TextView) findViewById(R.id.mTitle);
        mLogo.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.GONE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void showErrorOnEdt(AutoCompleteTextView autoCompleteTextView, String error) {
        autoCompleteTextView.setError(error);
        //autoCompleteTextView.requestFocus();
        autoCompleteTextView.setText("");
    }

    private void attemptLogin() {


        mFirstName.setError(null);
        mLastName.setError(null);
        mSelectCountry.setError(null);
        mSelectSpeciality.setError(null);
        mEnterPracticeYear.setError(null);
        mPassword.setError(null);
        mConfirmPassword.setError(null);
        mEmail.setError(null);
        mPhoneNumber.setError(null);
        mNPINumber.setError(null);
        mSelectState.setError(null);
        mSelectCity.setError(null);
        mLicenceNumber.setError(null);
        mSpokenLanguage.setError(null);
        mConsultFee.setError(null);
        mAttacheFile.setError(null);

        String strName = mFirstName.getText().toString();
        String strLastName = mLastName.getText().toString();
        String strSelectCountry = (String) mSelectCountry.getTag();
        //String strSelectSpeciality = mSelectSpeciality.getSelectedItem().toString();
        String strSelectSpeciality = (String) mSelectSpeciality.getTag();
        String strSelectTitle = (String) mSelectDesignation.getTag();
        String strEnterPracticeYear = mEnterPracticeYear.getText().toString();
        String strPassword = mPassword.getText().toString();
        String strConfirmPassword = mConfirmPassword.getText().toString();
        String strEmail = mEmail.getText().toString();
        String strPhoneNumber = mPhoneNumber.getText().toString();
        String strNPINumber = mNPINumber.getText().toString();
        // String strSelectState = mSelectState.getText().toString();
        String strSelectState = (String) mSelectState.getTag();
        String strCity = mSelectCity.getText().toString();
        String strLicenceNumber = mLicenceNumber.getText().toString();
        String strSpokenLanguage = mSpokenLanguage.getText().toString();
        String strmConsultFee = mConsultFee.getText().toString();
        String strAttachFile = mAttacheFile.getText().toString();


        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(strName) && !isName(strName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        }
        else if (!Utils.validateLName(strName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name_not_text));
            focusView = mFirstName;
            cancel = true;
        }else if (TextUtils.isEmpty(strLastName) && !isName(strLastName)) {
            mLastName.setError(getString(R.string.error_invalid_last_name));
            focusView = mLastName;
            cancel = true;
        }
        else if (!Utils.validateLName(strLastName)) {
            mLastName.setError(getString(R.string.error_invalid_first_name_not_text));
            focusView = mLastName;
            cancel = true;
        }else if (TextUtils.isEmpty(strSelectCountry)) {
            mSelectCountry.setError(getString(R.string.error_invalid_country));
            focusView = mSelectCountry;
            cancel = true;
            Toast.makeText(DoctorSignUpActivity.this, getString(R.string.error_invalid_country), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strSelectSpeciality)) {
            mSelectSpeciality.setError(getString(R.string.error_invalid_speciality));
            focusView = mSelectSpeciality;
            cancel = true;
            Toast.makeText(DoctorSignUpActivity.this, getString(R.string.error_invalid_speciality), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strSelectTitle)) {
            mSelectDesignation.setError(getString(R.string.error_invalid_speciality));
            focusView = mSelectDesignation;
            cancel = true;
            Toast.makeText(DoctorSignUpActivity.this, getString(R.string.error_invalid_speciality), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        } else if (!Utils.isEmailValid(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strPhoneNumber) || !isNumber(strPhoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mPhoneNumber;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strPassword) || !isPasswordValid(strPassword)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(strPassword) || !isPasswordValid(strPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(strConfirmPassword) && !isPasswordValid(strConfirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_invalid_confirm_password));
            focusView = mConfirmPassword;
            cancel = true;
        } else if (!strPassword.equalsIgnoreCase(strConfirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_invalid_both_pass));
            focusView = mConfirmPassword;
            cancel = true;
        }   else if (!TextUtils.isEmpty(strNPINumber) && !isName(strNPINumber)) {
            mNPINumber.setError(getString(R.string.error_invalid_npi_number));
            focusView = mNPINumber;
            cancel = true;
        } else if (TextUtils.isEmpty(strSelectState)) {
            mSelectState.setError(getString(R.string.error_invalid_state_name));
            focusView = mSelectState;
            cancel = true;
            Toast.makeText(DoctorSignUpActivity.this, getString(R.string.error_invalid_state_name), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strCity)) {
            mSelectCity.setError(getString(R.string.error_invalid_city));
            focusView = mSelectCity;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strEnterPracticeYear) || isValidYear(strEnterPracticeYear)) {
            mEnterPracticeYear.setError(getString(R.string.error_invalid_pracitice));
            focusView = mEnterPracticeYear;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strLicenceNumber) || !isName(strLicenceNumber)) {
            mLicenceNumber.setError(getString(R.string.error_invalid_licence_number));
            focusView = mLicenceNumber;
            cancel = true;
        } else if (TextUtils.isEmpty(strSpokenLanguage) || !isName(strSpokenLanguage)) {
            mSpokenLanguage.setError(getString(R.string.error_invalid_spoken_language));
            focusView = mSpokenLanguage;
            cancel = true;
        } /*else if (TextUtils.isEmpty(strAttachFile)) {
            mAttacheFile.setError(getString(R.string.error_attachment));
            focusView = mAttacheFile;
            cancel = true;
        }*/
        if (cancel) {
            focusView.requestFocus();
        } else {

            RegisterModel model = new RegisterModel();
            model.setFirstName(strName);
            model.setLastName(strLastName);
            model.setCountry(Integer.valueOf((String) mSelectCountry.getTag()));
            model.setSpeciality(Integer.valueOf((String) mSelectSpeciality.getTag()));
            model.setPyear(strEnterPracticeYear);
            model.setPassword(strPassword);
            model.setPassword(strConfirmPassword);
            model.setEmailId(strEmail);
            model.setPhone(strPhoneNumber);
            model.setNPINumber(strNPINumber);
            model.setState(Integer.valueOf((String) mSelectState.getTag()));
            model.setCity(strCity);
            model.setTitle(String.valueOf(strSelectTitle));
            model.setLicenseNumber(strLicenceNumber);
            model.setSpokenLanguage(strSpokenLanguage);
            if (TextUtils.isEmpty(strmConsultFee)) {
                model.setConsultFee("");
            } else {
                model.setConsultFee(strmConsultFee);
            }
            if(strAttachFile.isEmpty()){
                model.setDoc_resume("");
            }else{
                model.setDoc_resume(strAttachFile);
            }



            //model.setA(strAttachFile);


            if (Utils.isDeviceOnline(DoctorSignUpActivity.this)) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(DoctorSignUpActivity.this);
                if(model.getDoc_resume().isEmpty() ||  model.getDoc_resume()==null || model.getDoc_resume().equals("")){

                    RestAdapter.getAdapter().createUserForDoctor(model.getFirstName(), model.getLastName(), model.getEmailId(), model.getPassword(),
                            model.getCity(), model.getPhone(), model.getNPINumber(), model.getLicenseNumber(), model.getPyear(), model.getSpokenLanguage(),
                            model.getState(), model.getCountry(), String.valueOf(model.getTitle()), model.getSpeciality(),model.getConsultFee(),langOpted).enqueue(registerResponseModelCall);

                }else {
                    HashMap<String, String> requestValuePairsMap = new HashMap<>();
                    requestValuePairsMap.put(KeyValues.ARG_D_FirstName, model.getFirstName());
                    requestValuePairsMap.put(KeyValues.ARG_D_LastName, model.getLastName());
                    requestValuePairsMap.put(KeyValues.ARG_D_EmailId, model.getEmailId());
                    requestValuePairsMap.put(KeyValues.ARG_D_Password, model.getPassword());
                    requestValuePairsMap.put(KeyValues.ARG_D_City, model.getCity());
                    requestValuePairsMap.put(KeyValues.ARG_D_Phone, model.getPhone());
                    requestValuePairsMap.put(KeyValues.ARG_D_NPINumber, model.getNPINumber());
                    requestValuePairsMap.put(KeyValues.ARG_D_LicenseNumber, model.getLicenseNumber());
                    requestValuePairsMap.put(KeyValues.ARG_D_Pyear, model.getPyear());
                    requestValuePairsMap.put(KeyValues.ARG_D_SpokenLanguage, model.getSpokenLanguage());
                    requestValuePairsMap.put(KeyValues.ARG_D_State, String.valueOf(model.getState()));
                    requestValuePairsMap.put(KeyValues.ARG_D_Country, String.valueOf(model.getCountry()));
                    requestValuePairsMap.put(KeyValues.ARG_D_Title, String.valueOf(model.getTitle()));
                    requestValuePairsMap.put(KeyValues.ARG_ConsultFee, String.valueOf(model.getConsultFee()));
                    requestValuePairsMap.put(KeyValues.ARG_D_Speciality, String.valueOf(model.getSpeciality()));
                    requestValuePairsMap.put(KeyValues.ARG_P_LANG, langOpted);
                    if (TextUtils.equals(getExtension(model.getDoc_resume()), "pdf")) {
                        RestAdapter.getAdapter()
                                .createUserForDoctor1(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_PDF))
                                .enqueue(registerResponseModelCall);
                    } else if (TextUtils.equals(getExtension(model.getDoc_resume()), "doc")) {
                        RestAdapter.getAdapter()
                                .createUserForDoctor1(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_DOC))
                                .enqueue(registerResponseModelCall);
                    } else {
                        Toast.makeText(getApplicationContext(), "File format incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Toast.makeText(DoctorSignUpActivity.this, getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private boolean isName(String name) {
        return name.length() >4;
    }
    private boolean isValidYear(String name) {
        return name.length() <4;
    }
    private boolean isNumber(String name) {
        return name.length() >=10;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResponse(Call<StateList> call, Response<StateList> response) {
        if (response.isSuccessful()) {
            try {
                //Log.e(TAG, "onResponse: " + response.raw().body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                stateArrayList = response.body().getCity();
                StateSpinnerAdapter adapter = new StateSpinnerAdapter(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSelectState.setAdapter(adapter);

            } else {
                stateArrayList = new ArrayList<>();
                StateSpinnerAdapter adapter = new StateSpinnerAdapter(DoctorSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSelectState.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onFailure(Call<StateList> call, Throwable t) {
        t.printStackTrace();
    }

// get file path

    private void getDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }

    @Override
    protected void onActivityResult(int req, int result, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);
        if (result == RESULT_OK) {
            Uri fileuri = data.getData();
            docFilePath = getFileNameByUri(this, fileuri);
            mAttacheFile.setText(docFilePath);
        }
    }

    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }
}