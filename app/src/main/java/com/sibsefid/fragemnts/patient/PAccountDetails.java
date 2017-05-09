package com.sibsefid.fragemnts.patient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.RegisterModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PAccountDetails extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static PAccountDetails detailsContext;
    String strNuCodeId;
    private MaterialSpinner spinner_Title, spinner_Country, spinner_State, spinner_Day, spinner_Year, spinner_Month;
    private Spinner mSpinner_Mobile;
    private View mDetailsForm;
    private View mProgressView;
    Callback<UserLoginDetails> updateProfileListner = new Callback<UserLoginDetails>() {
        @Override
        public void onResponse(Call<UserLoginDetails> call, Response<UserLoginDetails> response) {
            if (getActivity() != null) {
                mUpdateBtn.setEnabled(true);
                if (response.isSuccessful()) {
                    try {
                        //  Log.e(TAG, "onResponse: " + response.raw().body().string()+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if (response.body().isSuccess() && response.body().getLogindetails() != null && response.body().getLogindetails().size() > 0) {

                            response.body().getLogindetails().get(0).setType(1);
                            MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));

                        } else if ((!response.body().isSuccess())) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UserLoginDetails> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mSelectCity;
    private EditText mAddress_1;
    private EditText mAddress_2;
    private EditText medicare_no;
    private EditText mPhoneNumber;
    private EditText mEmergencyPhoneNumber;
    private Button mUpdateBtn;
    private RadioGroup mRadioGroupGender;
    private RadioButton mRadioMale, mRadioFemale;
    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private HashMap<String, String> hashMapState = new HashMap<>();
    private boolean state_flag = true;
    private int gender;
    private List<String> mList_Mobile = null;
    private List<String> mList_MobileId = null;
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    private List<String> mList_Day = null;
    private List<String> mList_Month = null;
    private List<String> mList_Year = null;
    private List<String> mList_Country = null;
    private List<String> mList_State = null;
    private UserLoginDetails.LoginDetails dProfileDetails;
    private UserLoginDetails.LoginDetails details;
    Callback<UserLoginDetails> detailsCallback = new Callback<UserLoginDetails>() {
        @Override
        public void onResponse(Call<UserLoginDetails> call, Response<UserLoginDetails> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    try {
                        //  Log.e(TAG, "onResponse: " + response.raw().body().string()+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {

                        //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if (response.body().isSuccess() && response.body().getLogindetails() != null && response.body().getLogindetails().size() > 0) {

                            response.body().getLogindetails().get(0).setType(1);
                            MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                            updateUi();

                        } else if ((!response.body().isSuccess())) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UserLoginDetails> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private PatientActivity activity = null;
    Callback<StateList> stateListCallback = new Callback<StateList>() {
        @Override
        public void onResponse(Call<StateList> call, Response<StateList> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                        if (response.body().getCity() != null) {
                            mList_State.clear();
                            for (int i = 0; i < response.body().getCity().size(); i++) {
                                mList_State.add(response.body().getCity().get(i).getDetail_code_nm());
                                hashMapState.put(response.body().getCity().get(i).getDetail_code_nm(), response.body().getCity().get(i).getDetail_code());
                            }
                            spinner_State.setAdapter(new SpinnerAdapter(activity, 0, mList_State));
                            if (dProfileDetails != null) {

                                    String strStateName = dProfileDetails.getState_nm().trim();
                                    if (!TextUtils.isEmpty(strStateName)) {
                                        for (int i = 0; i < mList_State.size(); i++) {
                                            if (mList_State.get(i).equalsIgnoreCase(strStateName)) {
                                                spinner_State.setSelection(i);
                                                spinner_State.setTag(response.body().getCity().get(i).getDetail_code());
                                                state_flag = false;
                                                break;
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<StateList> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    public static PAccountDetails getInStnace() {

        return detailsContext;
    }

    private void updateUi() {

        if (dProfileDetails != null) {
            String mName = dProfileDetails.getCountry();
            if (!TextUtils.isEmpty(mName)) {
                showProgress(true);
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mName.trim()),selLang).enqueue(stateListCallback);
            }
            if (dProfileDetails.getDobnew().length() > 0) {
                String dobArray[] = dProfileDetails.getDobnew().split("/");
                String dbb_Day = dobArray[0];
                String dbb_Month = dobArray[1];
                String dbb_Year = dobArray[2];
                Log.e("dob detail", dbb_Month + "/" + dbb_Day + "/" + dbb_Year);
                int dayPull = Integer.valueOf(dbb_Day.trim());
                int monthPull = Integer.valueOf(dbb_Month.trim());
                int yearPull = Integer.valueOf(dbb_Year.trim());
                if (dobArray != null && dobArray.length > 0) {
                    try {
                        for (int i = 0; i < mList_Day.size(); i++) {
                            if (Integer.valueOf(mList_Day.get(i)) == Integer.valueOf(dbb_Day)) {
                                spinner_Day.setSelection(i, true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                       spinner_Month.setSelection(monthPull - 1, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {

                        for (int i = 0; i < mList_Year.size(); i++) {
                            if (mList_Year.get(i).trim().equals(dbb_Year)) {
                                spinner_Year.setSelection(i, true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mFirstName.setText(dProfileDetails.getFamily_name());
            mLastName.setText(dProfileDetails.getGiven_name());
            mEmail.setText(dProfileDetails.getEmail());
            mSelectCity.setText(dProfileDetails.getCity());
            mAddress_1.setText(dProfileDetails.getAddress_1());
            mAddress_2.setText(dProfileDetails.getAddress_2());
            mPhoneNumber.setText(dProfileDetails.getPhone());
            mEmergencyPhoneNumber.setText(dProfileDetails.getPhone());
            medicare_no.setText(dProfileDetails.getMedicareNumber());
            String strCountryName = dProfileDetails.getCountry_nm().trim();
            String strStateName = dProfileDetails.getState_nm().trim();
            strNuCodeId = dProfileDetails.getCode().trim();
            String strTitleName = dProfileDetails.getTitle_nm().trim();
            if (!TextUtils.isEmpty(strCountryName)) {
                for (int i = 0; i < mList_Country.size(); i++) {
                    if (mList_Country.get(i).toLowerCase().equalsIgnoreCase(strCountryName.toLowerCase())) {
                        spinner_Country.setSelection(i);
                        break;
                    }
                }
            }
            if (!TextUtils.isEmpty(strStateName)) {
                for (int i = 0; i < mList_State.size(); i++) {
                    if (mList_State.get(i).toLowerCase().equalsIgnoreCase(strStateName.toLowerCase())) {
                        spinner_State.setSelection(i);
                        break;
                    }
                }
            }
            if (!TextUtils.isEmpty(dProfileDetails.getSex())) {
                int sex = Integer.valueOf(dProfileDetails.getSex().trim());
                if (sex == 1) {
                    mRadioMale.setChecked(true);
                } else if (sex == 2) {
                    mRadioFemale.setChecked(true);
                } else {
                    mRadioMale.setChecked(true);
                }
            } else {
                mRadioMale.setChecked(true);
            }

            if (!TextUtils.isEmpty(dProfileDetails.getTitle_nm())) {

                for (int i = 0; i < mList_Title.size(); i++) {
                    if (mList_Title.get(i).toLowerCase().equalsIgnoreCase(strTitleName.toLowerCase())) {
                        spinner_Title.setSelection(i+1);
                        break;
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mDetailsForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.myprofile));

        details = MyPrefs.getLoginDetails(getActivity());
        dProfileDetails=details;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        detailsContext = this;
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_patient_account_details, container, false);
        EventBus.getDefault().register(this);
        initComponents(view);
        updateUi();
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getUserProfile(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }
        return view;
    }

    /* initialize Components Here*/
    public void initComponents(View view) {

        mFirstName = (EditText) view.findViewById(R.id.mFirstName);
        mLastName = (EditText) view.findViewById(R.id.mLastName);
        mEmail = (EditText) view.findViewById(R.id.mEmail);
        mSelectCity = (EditText) view.findViewById(R.id.mSelectCity);
        mAddress_1 = (EditText) view.findViewById(R.id.mAddress_1);
        mAddress_2 = (EditText) view.findViewById(R.id.mAddress_2);
        mPhoneNumber = (EditText) view.findViewById(R.id.mPhoneNumber);
        mEmergencyPhoneNumber = (EditText) view.findViewById(R.id.mEmergencyPhoneNumber);
        medicare_no = (EditText) view.findViewById(R.id.medicare_no);
        spinner_Title = (MaterialSpinner) view.findViewById(R.id.spinner_Title);
        spinner_Day = (MaterialSpinner) view.findViewById(R.id.spiner_Day);
        spinner_Month = (MaterialSpinner) view.findViewById(R.id.spiner_Month);
        spinner_Year = (MaterialSpinner) view.findViewById(R.id.spiner_Year);
        spinner_Country = (MaterialSpinner) view.findViewById(R.id.spinner_Country);
        spinner_State = (MaterialSpinner) view.findViewById(R.id.spinner_State);
        mSpinner_Mobile = (Spinner) view.findViewById(R.id.spinner_MobileNumber);
        mRadioGroupGender = (RadioGroup) view.findViewById(R.id.mRadioGroupGender);
        mRadioMale = (RadioButton) view.findViewById(R.id.mRadioMale);
        mRadioFemale = (RadioButton) view.findViewById(R.id.mRadioFemale);
        mDetailsForm = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);
        mUpdateBtn = (Button) view.findViewById(R.id.mUpdateBtn);
        mUpdateBtn.setOnClickListener(this);

        countryArrayList = MyPrefs.getCountryList(getActivity());
        setSpinnerAdapter();

        mRadioGroupGender.setOnCheckedChangeListener(this);
    }

    private void setSpinnerAdapter() {

        mList_Title = new ArrayList<>();
        mList_Title_ID = new ArrayList<>();
        mList_Day = new ArrayList<>();
        mList_Month = new ArrayList<>();
        mList_Year = new ArrayList<>();
        mList_Country = new ArrayList<>();
        mList_State = new ArrayList<>();

        for (int j = 1; j < 31; j++) {
            if (j < 10) {
                mList_Day.add("0" + j);
            } else {
                mList_Day.add(j + "");
            }
        }
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = getActivity().getResources().getStringArray(R.array.months);
        for (int i = 0; i < monthNames.length; i++) {
            mList_Month.add(monthNames[i]);
        }
        for (int j = 1970; j < 2017; j++) {
            mList_Year.add(j + "");
        }

        if (countryArrayList != null) {
            for (int i = 0; i < countryArrayList.size(); i++) {
                mList_Country.add(countryArrayList.get(i).getDetail_code_nm());
                hashMapCountry.put(countryArrayList.get(i).getDetail_code_nm(), countryArrayList.get(i).getDetail_code());
            }
        }

        mList_Title = activity.getmList_Title();
        mList_Title_ID = activity.getmList_Title_ID();
        if (mList_Title == null) {
            mList_Title = new ArrayList<>();
        }

        spinner_Title.setAdapter(new SpinnerAdapter(activity, 0, mList_Title));
        spinner_Day.setAdapter(new SpinnerAdapter(activity, 0, mList_Day));
        spinner_Month.setAdapter(new SpinnerAdapter(activity, 0, mList_Month));
        spinner_Year.setAdapter(new SpinnerAdapter(activity, 0, mList_Year));

        spinner_Country.setAdapter(new SpinnerAdapter(activity, 0, mList_Country));

        spinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_Year.setTag(spinner_Year.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = position + 1;
                if (position < 10) {
                    spinner_Month.setTag("0" + position);
                } else
                    spinner_Month.setTag(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_Day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              /*  if(position<10){
                    spinner_Day.setTag("0"+spinner_Day.getSelectedItem().toString());
                }else{*/
                try {
                    spinner_Day.setTag(spinner_Day.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_Title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    spinner_Title.setTag("title");
                } else {
                    spinner_Title.setTag(mList_Title_ID.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_Country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mList_State = new ArrayList<String>();
                spinner_State.setAdapter(new SpinnerAdapter(activity, 0, mList_State));
                spinner_Country.setTag(hashMapCountry.get(spinner_Country.getSelectedItem().toString()));
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(spinner_Country.getSelectedItem().toString().trim()),selLang).enqueue(stateListCallback);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  String mName = mList_Country.get(position);
                spinner_Country.setTag(hashMapCountry.get(spinner_Country.getSelectedItem().toString()));
                spinner_State.setTag(hashMapState.get(spinner_State.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Mobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSpinner_Mobile.setTag(mList_MobileId.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                mList_Mobile = (List<String>) Utils.loadJSONFromAssetCountry(getActivity())[0];
                mList_MobileId = (List<String>) Utils.loadJSONFromAssetCountry(getActivity())[1];
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mSpinner_Mobile.setAdapter(new SpinnerAdapter(activity, 0, mList_Mobile));

                            if (!TextUtils.isEmpty(details.getCode())) {

                                for (int i = 0; i < mList_MobileId.size(); i++) {
                                    if (mList_MobileId.get(i).equalsIgnoreCase(strNuCodeId)) {
                                        mSpinner_Mobile.setSelection(i);
                                        break;
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mUpdateBtn:
                updateProfileData();
                break;
        }
    }

    private void updateProfileData() {

        String strmFirstName = mFirstName.getText().toString().trim();
        String strmLastName = mLastName.getText().toString().trim();
        String strmEmail = mEmail.getText().toString().trim();
        String strmSelectCity = mSelectCity.getText().toString().trim();
        String strmAddress_1 = mAddress_1.getText().toString().trim();
        String strmAddress_2 = mAddress_2.getText().toString().trim();
        String strmPhoneNumber = mPhoneNumber.getText().toString().trim();
        String strmMedicareNumber = medicare_no.getText().toString().trim();
        String strmEmergencyPhoneNumber = mEmergencyPhoneNumber.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(strmFirstName) || !Utils.validateLName(strmFirstName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        } else if (TextUtils.isEmpty(strmLastName) || !Utils.validateLName(strmLastName)) {
            mLastName.setError(getString(R.string.error_invalid_last_name));
            focusView = mLastName;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strmEmail) || !Utils.isEmailValid(strmEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        else if (TextUtils.isEmpty(strmMedicareNumber) || !isNumber(strmMedicareNumber)) {
            medicare_no.setError(getString(R.string.error_invalid_medicare_no));
            focusView = medicare_no;
            cancel = true;
        }else if (strmSelectCity.isEmpty()) {
            mSelectCity.setError(getString(R.string.error_invalid_city));
            focusView = mSelectCity;
            cancel = true;
        } else if (TextUtils.isEmpty(strmAddress_1)) {
            mAddress_1.setError(getString(R.string.error_invalid_address));
            focusView = mAddress_1;
            cancel = true;
        } else if (TextUtils.isEmpty(strmAddress_2)) {
            mAddress_2.setError(getString(R.string.error_invalid_address));
            focusView = mAddress_2;
            cancel = true;
        } else if (TextUtils.isEmpty(strmPhoneNumber) || !isNumber(strmPhoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mPhoneNumber;
            cancel = true;
        } else if (!TextUtils.isEmpty(strmEmergencyPhoneNumber) && !isNumber(strmPhoneNumber)) {
            mEmergencyPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mEmergencyPhoneNumber;
            cancel = true;
        } if (cancel) {
            focusView.requestFocus();
        } else {
            if(spinner_Title.getTag().equals("00")){
                Toast.makeText(getActivity(),"Select the Title !!",Toast.LENGTH_SHORT).show();
            }else {
                RegisterModel model = new RegisterModel();

                model.setFirstName(strmFirstName);
                model.setLastName(strmLastName);
                model.setCountry(Integer.valueOf((String) spinner_Country.getTag()));
                model.setPassword(details.getUser_password());
                model.setPhone(strmPhoneNumber);
                model.setMobilecode(Integer.valueOf((String) mSpinner_Mobile.getTag()));
                if(spinner_State.getTag()!=null){
                    model.setState(Integer.valueOf((String) spinner_State.getTag()));
                }

                model.setCity(strmSelectCity);
                model.setMedicareNumber(strmMedicareNumber);

                model.setGender_int(gender);
                model.setAddress1(strmAddress_1);
                model.setAddress2(strmAddress_2);

                model.setDob(spinner_Month.getTag() + "/" + spinner_Day.getTag() + "/" + spinner_Year.getTag());
                if(String.valueOf(spinner_Title.getTag()).equalsIgnoreCase("title")){
                    Toast.makeText(getActivity(),"Please select the title",Toast.LENGTH_SHORT).show();
                    return;
                }
                model.setTitle(String.valueOf(spinner_Title.getTag()));
                model.setPatientId(details.getUser_seq());

                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    mUpdateBtn.setEnabled(false);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().updateUserForPatient(model.getFirstName(), model.getLastName(), model.getPassword(),
                            model.getCity(), model.getPhone(), model.getMobilecode(), model.getMedicareNumber(),
                            model.getState(), model.getCountry(), model.getTitle(), model.getPatientId(), model.getAddress1(),
                            model.getAddress2(), model.getGender_int(), model.getDob(),langOpted).enqueue(updateProfileListner);

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isName(String name) {
        return name.length() > 4;
    }
    private boolean isNumber(String name) {
        return name.length() >=10;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.mRadioMale:
                //  gender = getActivity().getResources().getString(R.string.male);
                gender = 1;
                break;
            case R.id.mRadioFemale:
                // gender = getActivity().getResources().getString(R.string.female);
                gender = 2;
                break;

            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onEvent(Object[] objects) {

        if (getActivity() != null) {
            mList_Title = (List<String>) objects[0];
            mList_Title_ID = (List<String>) objects[1];
        }
    }

    public UserLoginDetails.LoginDetails getdProfileDetails() {
        return details;
    }

}
