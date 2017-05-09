package com.sibsefid.fragemnts.patient;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.ProfileUpdateModel;
import com.sibsefid.model.doctor.RegisterModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.FamilyMembersAddedInfo;
import com.sibsefid.model.patient.RelationshipType;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyMembers extends Fragment implements View.OnClickListener {


    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmailId;
    private EditText mAddress1;
    private EditText mCity;
    private EditText mZip;
    private EditText mPhone;

    private MaterialSpinner mCountryChooser;
    private MaterialSpinner mStateChooser;
    private MaterialSpinner mDays;
    private MaterialSpinner mMonth;
    private MaterialSpinner mYear;
    private MaterialSpinner mGender;
    private MaterialSpinner mRelationship;

    private CheckBox addressCompairater_chkr;

    private TextView txtShowMembers;

    private Button mUpdateBtn;
    private View mDetailsForm;
    private View mProgressView;
    Callback<ProfileUpdateModel> updateProfileListner = new Callback<ProfileUpdateModel>() {
        @Override
        public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {

            if (getActivity() != null) {
                mUpdateBtn.setEnabled(true);
                if (response.isSuccessful()) {
                  /*  try {
                        Log.e("TAG", "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();

                            mFirstName.setText("");
                            mLastName.setText("");
                            mEmailId.setText("");
                            mAddress1.setText("");
                            mCity.setText("");
                            mZip.setText("");
                            mPhone.setText("");


                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ProfileUpdateModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private PatientActivity activity = null;
    private UserLoginDetails.LoginDetails details;
    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private HashMap<String, String> hashMapState = new HashMap<>();
    private List<String> mList_Day = null;
    private List<String> mList_Month = null;
    private List<String> mList_Year = null;
    private List<String> mList_Country = null;
    private List<String> mList_State = null;
    private boolean state_flag = true;
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
                            mStateChooser.setAdapter(new SpinnerAdapter(activity, 0, mList_State));
                            UserLoginDetails.LoginDetails dProfileDetails = PAccountDetails.getInStnace().getdProfileDetails();
                            if (dProfileDetails != null) {
                                if (state_flag) {
                                    String strStateName = dProfileDetails.getState_nm().trim();
                                    if (!TextUtils.isEmpty(strStateName)) {
                                        for (int i = 0; i < mList_State.size(); i++) {
                                            if (mList_State.get(i).equalsIgnoreCase(strStateName)) {
                                                mStateChooser.setSelection(i);
                                                mStateChooser.setTag(response.body().getCity().get(i).getDetail_code());
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
        }

        @Override
        public void onFailure(Call<StateList> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }

        }
    };
    private int gender = 1;
    private List<String> mList_RelationName = null;
    private List<String> mList_Relation_ID = null;
    private List<RelationshipType.DataBean> relationBeenArrayList = new ArrayList<>();
    Callback<RelationshipType> relationPModelCallback = new Callback<RelationshipType>() {
        @Override
        public void onResponse(Call<RelationshipType> call, Response<RelationshipType> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            relationBeenArrayList = response.body().getData();
                            mList_RelationName.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mList_RelationName.add(response.body().getData().get(i).getDetail_code_nm());
                                mList_Relation_ID.add(response.body().getData().get(i).getDetail_code());
                            }

                            updateUi();

                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RelationshipType> call, Throwable t) {
            t.printStackTrace();
        }
    };


    public FamilyMembers() {
        // Required empty public constructor
    }

    private void updateUi() {
        mRelationship.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_RelationName));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        details = MyPrefs.getLoginDetails(getActivity());
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_family_members, container, false);

        initiateView(view);
        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            mList_RelationName = new ArrayList<>();
            mList_Relation_ID = new ArrayList<>();
            RestAdapter.getAdapter().getPRelationList(selLangToSend).enqueue(relationPModelCallback);
        }
        return view;
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

    private void initiateView(View view) {

        mFirstName = (EditText) view.findViewById(R.id.mFirstName);
        mLastName = (EditText) view.findViewById(R.id.mLastName);
        mEmailId = (EditText) view.findViewById(R.id.mEmailId);
        mAddress1 = (EditText) view.findViewById(R.id.mAddress1);
        mCity = (EditText) view.findViewById(R.id.mCity);
        mZip = (EditText) view.findViewById(R.id.mZip);
        mPhone = (EditText) view.findViewById(R.id.mPhone);

        txtShowMembers = (TextView) view.findViewById(R.id.txtShowMembers);

        mCountryChooser = (MaterialSpinner) view.findViewById(R.id.mCountryChooser);
        mStateChooser = (MaterialSpinner) view.findViewById(R.id.mStateChooser);
        mDays = (MaterialSpinner) view.findViewById(R.id.mDays);
        mMonth = (MaterialSpinner) view.findViewById(R.id.mMonth);
        mYear = (MaterialSpinner) view.findViewById(R.id.mYear);
        mGender = (MaterialSpinner) view.findViewById(R.id.mGender);
        mRelationship = (MaterialSpinner) view.findViewById(R.id.mRelationship);
        mDetailsForm = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);
        mUpdateBtn = (Button) view.findViewById(R.id.mUpdateBtn);
        addressCompairater_chkr = (CheckBox) view.findViewById(R.id.addressCompairater_chkr);

        countryArrayList = MyPrefs.getCountryList(getActivity());

        txtShowMembers.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        addressCompairater_chkr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserLoginDetails.LoginDetails dProfileDetails = PAccountDetails.getInStnace().getdProfileDetails();
                    mAddress1.setText(dProfileDetails.getAddress_1());
                    mCity.setText(dProfileDetails.getCity());
                    String strCountryName = dProfileDetails.getCountry_nm().trim();
                    if (!TextUtils.isEmpty(strCountryName)) {
                        for (int i = 0; i < mList_Country.size(); i++) {
                            if (mList_Country.get(i).toLowerCase().equalsIgnoreCase(strCountryName.toLowerCase())) {
                                mCountryChooser.setSelection(i);
                                break;
                            }
                        }
                    }
                } else {
                    mAddress1.setText("");
                }
            }
        });
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {

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

        mDays.setAdapter(new SpinnerAdapter(activity, 0, mList_Day));
        mMonth.setAdapter(new SpinnerAdapter(activity, 0, mList_Month));
        mYear.setAdapter(new SpinnerAdapter(activity, 0, mList_Year));

        mCountryChooser.setAdapter(new SpinnerAdapter(activity, 0, mList_Country));

        ArrayAdapter<String> adapter_gendr = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, getActivity().getResources().getStringArray(R.array.gender));
        adapter_gendr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGender.setAdapter(adapter_gendr);

        mYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYear.setTag(mYear.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = position + 1;
                if (position < 10) {
                    mMonth.setTag("0" + position);
                } else
                    mMonth.setTag(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    mDays.setTag(mDays.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mCountryChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountryChooser.setTag(hashMapCountry.get(mCountryChooser.getSelectedItem().toString()));
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mCountryChooser.getSelectedItem().toString()),selLang).enqueue(stateListCallback);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mStateChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountryChooser.setTag(hashMapCountry.get(mCountryChooser.getSelectedItem().toString()));
                mStateChooser.setTag(hashMapState.get(mStateChooser.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = 1;
                } else if (position == 1) {
                    gender = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRelationship.setTag(mList_Relation_ID.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mUpdateBtn:

                addMembers();
                break;

            case R.id.txtShowMembers:
                Utils.callFragmentForAddPatient(activity, new FamilyMembersAddedInfo(), FamilyMembersAddedInfo.TAG);
                break;
        }
    }

    private void addMembers() {
        boolean cancel = false;
        View focusView = null;
        String strFirstName = mFirstName.getText().toString().trim();
        String strLastName = mLastName.getText().toString().trim();
        String strEmail = mEmailId.getText().toString().trim();
        String strAddress1 = mAddress1.getText().toString().trim();
        String strCity = mCity.getText().toString().trim();
        String strZip = mZip.getText().toString().trim();
        String strPhone = mPhone.getText().toString().trim();
        if (TextUtils.isEmpty(strFirstName) || !Utils.validateLName(strFirstName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        } else if (TextUtils.isEmpty(strLastName) || !Utils.validateLName(strLastName)) {
            mLastName.setError(getString(R.string.error_invalid_first_name));
            focusView = mLastName;
            cancel = true;
        } else if (TextUtils.isEmpty(strEmail) || !Utils.isEmailValid(strEmail)) {
            mEmailId.setError(getString(R.string.error_invalid_email));
            focusView = mEmailId;
            cancel = true;
        }  else if (TextUtils.isEmpty(strCity) || !Utils.validateCity(strCity)) {
            mCity.setError(getString(R.string.error_invalid_city));
            focusView = mCity;
            cancel = true;
        } if (cancel) {
            focusView.requestFocus();
        }
        else {

            RegisterModel model = new RegisterModel();

            model.setFirstName(strFirstName);
            model.setLastName(strLastName);
            model.setEmailId(strEmail);
            model.setCity(strCity);
            model.setState(Integer.valueOf((String) mStateChooser.getTag()));
            model.setPostCode(strZip);
            model.setPhone(strPhone);
            model.setDob(mMonth.getTag() + "/" + mDays.getTag() + "/" + mYear.getTag());
            model.setGender_int(gender);
            model.setAddress1(strAddress1);
            model.setPatientId(details.getUser_seq());
            model.setRelation(Integer.valueOf((String) mRelationship.getTag()));


            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                mUpdateBtn.setEnabled(false);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().addPatientFamilyMembers(
                        model.getFirstName(),
                        model.getLastName(),
                        model.getCity(),
                        model.getPhone(),
                        model.getState(),
                        model.getPostCode(),
                        model.getPatientId(),
                        model.getAddress1(),
                        model.getGender_int(),
                        model.getDob(),
                        model.getRelation(),
                        model.getEmailId(),langOpted).enqueue(updateProfileListner);


            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
