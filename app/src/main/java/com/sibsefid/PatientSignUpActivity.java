package com.sibsefid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.doctor.adapter.CountrySpinnerAdapter;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.doctor.adapter.StateSpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.RegisterModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 15/9/16.
 */
public class PatientSignUpActivity extends AppCompatActivity implements Callback<StateList>,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DoctorSignUpActivity";
    private ImageView mLogo;
    private TextView mTitle;
    private ImageView mBackBtn;
    private int LOGINTYPE = 0;
    private AutoCompleteTextView mFirstName;
    private AutoCompleteTextView mLastName;
    private MaterialSpinner mSelectCountry;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;
    private AutoCompleteTextView mPhoneNumber;
    private MaterialSpinner mSelectState;
    private AutoCompleteTextView mSelectCity;
    private AutoCompleteTextView mAddress_1;
    private AutoCompleteTextView mAddress_2;
    private AutoCompleteTextView mPostCode;
    private Spinner mSpinner_Gender;
    private TextView mDob;
    private String[] COUNTRY_NAME = null;
    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private String[] STATE_NAME = null;
    private HashMap<String, String> hashMapState = new HashMap<>();
    private ArrayList<StateList.City> stateArrayList = new ArrayList<>();
    private String[] SPECIALIST_NAME = null;
    private HashMap<String, String> hashMapSpeciality = new HashMap<>();
    private ArrayList<StateList.City> specialityArrayList = new ArrayList<>();
    private List<String> mList_Gender = null;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PatientSignUpActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(false);
            dpd.vibrate(true);
            dpd.dismissOnPause(true);
            dpd.setMaxDate(now);
            dpd.showYearPickerFirst(false);
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
            dpd.setTitle(getResources().getString(R.string.date_dailog_title));
            dpd.show(getFragmentManager(), "Datepickerdialog");
        }
    };

    Callback<RegisterResponseModel> registerResponseModelCall = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {

            if (PatientSignUpActivity.this != null) {
                if (response.isSuccessful()) {
                    try {
                        //Log.e(TAG, "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(PatientSignUpActivity.this, response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(PatientSignUpActivity.this, response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PatientSignUpActivity.this, getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> response, Throwable t) {
            showProgress(false);
            Toast.makeText(PatientSignUpActivity.this, getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
            t.printStackTrace();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LOGINTYPE = MyPrefs.getUser(this);

        setContentView(R.layout.activity_sign_up);

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
    }

    private void initiateUi() {

        mPasswordView = (EditText) findViewById(R.id.mPassword);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mDob = (TextView) findViewById(R.id.mDob);
        mFirstName = (AutoCompleteTextView) findViewById(R.id.mFirstName);
        mLastName = (AutoCompleteTextView) findViewById(R.id.mLastName);
        mSelectCountry = (MaterialSpinner) findViewById(R.id.mSelectCountry);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mConfirmPassword = (EditText) findViewById(R.id.mConfirmPassword);
        mEmail = (EditText) findViewById(R.id.mEmail);
        mPhoneNumber = (AutoCompleteTextView) findViewById(R.id.mPhoneNumber);
        mSelectState = (MaterialSpinner) findViewById(R.id.mSelectState);
        mSelectCity = (AutoCompleteTextView) findViewById(R.id.mSelectCity);
        mPostCode = (AutoCompleteTextView) findViewById(R.id.mPostCode);
        mAddress_1 = (AutoCompleteTextView) findViewById(R.id.mAddress_1);
        mAddress_2 = (AutoCompleteTextView) findViewById(R.id.mAddress_2);

        mList_Gender = new ArrayList<>();

        if (countryArrayList != null) {
           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, COUNTRY_NAME);
            mSelectCountry.setAdapter(adapter);*/
            CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, countryArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSelectCountry.setAdapter(adapter);
        }
        for (int i = 0; i < countryArrayList.size(); i++) {
            if (countryArrayList.get(i).getDetail_code_nm().equalsIgnoreCase("United States")) {
                mSelectCountry.setSelection(i + 1);

            }
        }

        mSelectCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectCountry.setTag(null);
                if (i < 0)
                    return;
                mSelectCountry.setTag(countryArrayList.get(i).getDetail_code());
                stateArrayList = new ArrayList<>();
                StateSpinnerAdapter adapter = new StateSpinnerAdapter(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSelectState.setAdapter(adapter);
                String selLang=Utils.getuserSeletedLanagueForRequestSend(PatientSignUpActivity.this);
                RestAdapter.getAdapter().getStateList(countryArrayList.get(i).getDetail_code(),selLang).enqueue(PatientSignUpActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSpinner_Gender = (Spinner) findViewById(R.id.mGender);
        mList_Gender.add(this.getResources().getString(R.string.male));
        mList_Gender.add(this.getResources().getString(R.string.female));
        mSpinner_Gender.setAdapter(new SpinnerAdapter(this, 0, mList_Gender));

        mSpinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sex = mSpinner_Gender.getSelectedItem().toString();
                if (sex.equalsIgnoreCase(PatientSignUpActivity.this.getResources().getString(R.string.male))) {
                    mSpinner_Gender.setTag("1");
                } else if (sex.equalsIgnoreCase(PatientSignUpActivity.this.getResources().getString(R.string.female))) {
                    mSpinner_Gender.setTag("2");
                } else {
                    mSpinner_Gender.setTag("1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        mDob.setOnClickListener(dateOnClickListener);

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
        mPassword.setError(null);
        mConfirmPassword.setError(null);
        mEmail.setError(null);
        mPhoneNumber.setError(null);
        mSelectState.setError(null);
        mSelectCity.setError(null);
        mAddress_1.setError(null);
        mAddress_2.setError(null);
        mPostCode.setError(null);
        mDob.setError(null);

        String strName = mFirstName.getText().toString();
        String strLastName = mLastName.getText().toString();
        String strSelectCountry = (String) mSelectCountry.getTag();
        String strEmail = mEmail.getText().toString();
        String strPhoneNumber = mPhoneNumber.getText().toString();
        String strPassword = mPassword.getText().toString();
        String strConfirmPassword = mConfirmPassword.getText().toString();
        String strAddress_1 = mAddress_1.getText().toString();
        String strAddress_2 = mAddress_2.getText().toString();
        String strSelectState = (String) mSelectState.getTag();
        String strCity = mSelectCity.getText().toString();
        String strPostCode = mPostCode.getText().toString();
        String strDob = mDob.getText().toString();
        //  String strGender = mSpinner_Gender.getSelectedItem().toString();
        //String strGender= mSpinner_Gender.getTag().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(strName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        }  else if (!Utils.validateLName(strName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strLastName)) {
            mLastName.setError(getString(R.string.error_invalid_last_name));
            focusView = mLastName;
            cancel = true;
        }  else if (!Utils.validateLName(strLastName)) {
            mLastName.setError(getString(R.string.error_invalid_last_name));
            focusView = mLastName;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strSelectCountry)) {
            mSelectCountry.setError(getString(R.string.error_invalid_country));
            focusView = mSelectCountry;
            cancel = true;
            Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_invalid_country), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }else if (!Utils.isEmailValid(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        } else if (TextUtils.isEmpty(strPhoneNumber) || !isPhoneNumber(strPhoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mPhoneNumber;
            cancel = true;
        } else if (TextUtils.isEmpty(strPassword) || !isPasswordValid(strPassword)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(strPassword) || !isPasswordValid(strPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(strConfirmPassword) || !isPasswordValid(strConfirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_invalid_confirm_password));
            focusView = mConfirmPassword;
            cancel = true;
        } else if (!strPassword.equalsIgnoreCase(strConfirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_invalid_both_pass));
            focusView = mConfirmPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(strAddress_1)) {
            mAddress_1.setError(getString(R.string.error_invalid_address));
            focusView = mAddress_1;
            cancel = true;
        } else if (TextUtils.isEmpty(strSelectState)) {
            mSelectState.setError(getString(R.string.error_invalid_state_name));
            focusView = mSelectState;
            cancel = true;
            Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_invalid_state_name), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strCity)) {
            mSelectCity.setError(getString(R.string.error_invalid_city));
            focusView = mSelectCity;
            cancel = true;
        } else if (TextUtils.isEmpty(strPostCode)) {
            mPostCode.setError(getString(R.string.error_invalid_post_code));
            focusView = mPostCode;
            cancel = true;
        } else if (TextUtils.isEmpty(strDob)) {
            mDob.setError(getString(R.string.error_invalid_dob));
            focusView = mDob;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {

            RegisterModel model = new RegisterModel();
            model.setFirstName(strName);
            model.setLastName(strLastName);
            model.setCountry(Integer.valueOf((String) mSelectCountry.getTag()));
            model.setPassword(strPassword);
            model.setPassword(strConfirmPassword);
            model.setEmailId(strEmail);
            model.setPhone(strPhoneNumber);
            model.setState(Integer.valueOf((String) mSelectState.getTag()));
            model.setCity(strCity);
            model.setDob(strDob);
            model.setPostCode(strPostCode);
            model.setAddress_1(strAddress_1);
            model.setAddress_2(strAddress_2);
            model.setGender(mSpinner_Gender.getTag().toString());
            model.setTitle("1");
            if (Utils.isDeviceOnline(PatientSignUpActivity.this)) {
                showProgress(true);
                String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(PatientSignUpActivity.this);
                RestAdapter.getAdapter().createUserForPatient(model.getFirstName(), model.getLastName(), model.getEmailId(), model.getPassword(),
                        model.getCity(), model.getPhone(),
                        model.getState(), model.getCountry(), model.getDob(), model.getPostCode(), model.getGender(), model.getAddress_1(), model.getAddress_2(),selLangToSend).enqueue(registerResponseModelCall);
            } else {
                Toast.makeText(PatientSignUpActivity.this, getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
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
        return name.length() > 4;
    }

    private boolean isPhoneNumber(String name) {
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
        if (PatientSignUpActivity.this != null) {
            if (response.isSuccessful()) {
                try {
                    //Log.e(TAG, "onResponse: " + response.raw().body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                    if (response.body().getCity() != null) {

                        if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                            stateArrayList = response.body().getCity();
                            StateSpinnerAdapter adapter = new StateSpinnerAdapter(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSelectState.setAdapter(adapter);

                        } else {
                            stateArrayList = new ArrayList<>();
                            StateSpinnerAdapter adapter = new StateSpinnerAdapter(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, stateArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSelectState.setAdapter(adapter);
                        }

                        /*  STATE_NAME = new String[response.body().getCity().size()];
                        for (int i = 0; i < response.body().getCity().size(); i++) {
                            STATE_NAME[i] = response.body().getCity().get(i).getDetail_code_nm();
                            hashMapState.put(response.body().getCity().get(i).getDetail_code_nm(), response.body().getCity().get(i).getDetail_code());
                        }
                        if (STATE_NAME.length > 0) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientSignUpActivity.this, android.R.layout.simple_dropdown_item_1line, STATE_NAME);
                            mSelectState.setAdapter(adapter);
                        }*/
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<StateList> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        mDob.setText(dayOfMonth + "/" + month + "/" + year);
    }
}


