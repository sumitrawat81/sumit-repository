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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.ProfileUpdateModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BillingInformationModel;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingInformation extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private PatientActivity activity = null;
    private EditText name_on_card;
    private EditText card_no;
    private EditText cvv_no;
    private EditText billing_address1;
    private EditText billing_address2;
    private EditText mCity;
    private EditText mZipCode;
    private EditText insuranceCompnyName;
    private EditText insuranceNo;

    private MaterialSpinner mMonthChooser;
    private MaterialSpinner mYearChooser;
    private MaterialSpinner mCountryChooser;
    private MaterialSpinner mStateChooser;

    private CheckBox addressCompairater_chkr;
    private RadioGroup radioIsInsurence;
    private RadioButton radioYEs;
    private RadioButton radioNo;
    private UserLoginDetails.LoginDetails details;
    private Button mUpdateBtn;
    private LinearLayout txtInsuranceNameLL;
    private LinearLayout txtInsuranceNumberLL;

    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private HashMap<String, String> hashMapState = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private List<String> mList_Country = null;
    private List<String> mList_State = null;

    private List<String> mList_Month = null;
    private List<String> mList_Year = null;

    private View mDetailsForm;
    private View mProgressView;
    Callback<ProfileUpdateModel> updateProfileListner = new Callback<ProfileUpdateModel>() {
        @Override
        public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {

            if (getActivity() != null) {
                mUpdateBtn.setEnabled(true);
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();

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
    private boolean isInsurance = false;
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
                            if(dBillingDetails!=null && dBillingDetails.getCountry_nm()!=null && !dBillingDetails.getCountry_nm().equals("")){
                                String strState =dBillingDetails.getState_nm().trim();
                                if (strState != null) {
                                    if (state_flag) {


                                        for (int i = 0; i < mList_State.size(); i++) {
                                            if (mList_State.get(i).equalsIgnoreCase(strState)) {
                                                mStateChooser.setSelection(i);

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
    private BillingInformationModel.DataBean dBillingDetails;
    Callback<BillingInformationModel> detailsCallback = new Callback<BillingInformationModel>() {
        @Override
        public void onResponse(Call<BillingInformationModel> call, Response<BillingInformationModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            dBillingDetails = response.body().getData().get(0);
                            updateUi();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<BillingInformationModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    public BillingInformation() {
        // Required empty public constructor
    }

    private void updateUi() {


        if (dBillingDetails != null) {

           /* String mName = dBillingDetails.getCountry();
            if (!TextUtils.isEmpty(mName)) {
                showProgress(true);
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mName.trim())).enqueue(stateListCallback);
            }*/


            name_on_card.setText(dBillingDetails.getNameOnCard());
            card_no.setText(dBillingDetails.getCardNo());
            cvv_no.setText(dBillingDetails.getCVV());
            billing_address1.setText(dBillingDetails.getBillingAddress1());
            billing_address2.setText(dBillingDetails.getBillingAddress2());
            mCity.setText(dBillingDetails.getCity());
            mZipCode.setText(dBillingDetails.getZip());
            insuranceCompnyName.setText(dBillingDetails.getInsuranceCompany());
            insuranceNo.setText(dBillingDetails.getInsuranceNo());
            int monthPull = Integer.valueOf(dBillingDetails.getExpirationMonth());

            String strCountryName = dBillingDetails.getCountry_nm();
            String strStateName = details.getState_nm().trim();

            if (!TextUtils.isEmpty(strCountryName)) {
                for (int i = 0; i < mList_Country.size(); i++) {
                    if (mList_Country.get(i).toLowerCase().equalsIgnoreCase(strCountryName.toLowerCase())) {
                        mCountryChooser.setSelection(i);
                        break;
                    }
                }
            }
          /*  if (!TextUtils.isEmpty(strStateName)) {
                for (int i = 0; i < mList_State.size(); i++) {
                    if (mList_State.get(i).toLowerCase().equalsIgnoreCase(strStateName.toLowerCase())) {
                        mStateChooser.setSelection(i);
                        break;
                    }
                }
            }*/

            if (dBillingDetails.getIsInsurance().equalsIgnoreCase("true")) {
                radioYEs.setChecked(true);

            } else {
                radioNo.setChecked(true);
            }

            try {


                mMonthChooser.setSelection(monthPull - 1, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (int i = 0; i < mList_Year.size(); i++) {
                    if (mList_Year.get(i).equals(dBillingDetails.getExpirationYear())) {
                        mYearChooser.setSelection(i, true);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

           /* String strState =dBillingDetails.getState_nm().trim();
            if (strState != null) {
                if (state_flag) {


                        for (int i = 0; i < mList_State.size(); i++) {
                            if (mList_State.get(i).equalsIgnoreCase(strStateName)) {
                                mStateChooser.setSelection(i);

                                state_flag = false;
                                break;
                            }

                    }
                }
            }*/
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_billing_information, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getBillingInfo(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }

        return view;
    }

    private void setSpinnerAdapter() {

        mList_Month = new ArrayList<>();
        mList_Year = new ArrayList<>();

        mList_Country = new ArrayList<>();
        mList_State = new ArrayList<>();

        String[] monthNames = getActivity().getResources().getStringArray(R.array.months);
        for (int i = 0; i < monthNames.length; i++) {
            mList_Month.add(monthNames[i]);
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int j = year; j < year+20; j++) {
            mList_Year.add(j + "");
        }

        if (countryArrayList != null) {
            for (int i = 0; i < countryArrayList.size(); i++) {
                mList_Country.add(countryArrayList.get(i).getDetail_code_nm());
                hashMapCountry.put(countryArrayList.get(i).getDetail_code_nm(), countryArrayList.get(i).getDetail_code());
            }
        }
        mMonthChooser.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_Month));
        mYearChooser.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_Year));

        mCountryChooser.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_Country));

    }

    private void initiateView(View view) {

        name_on_card = (EditText) view.findViewById(R.id.name_on_card);
        card_no = (EditText) view.findViewById(R.id.card_no);
        cvv_no = (EditText) view.findViewById(R.id.cvv_no);
        billing_address1 = (EditText) view.findViewById(R.id.billing_address1);
        billing_address2 = (EditText) view.findViewById(R.id.billing_address2);
        mCity = (EditText) view.findViewById(R.id.mCity);
        mZipCode = (EditText) view.findViewById(R.id.mZipCode);
        insuranceCompnyName = (EditText) view.findViewById(R.id.insuranceCompnyName);
        insuranceNo = (EditText) view.findViewById(R.id.insuranceNo);

        mMonthChooser = (MaterialSpinner) view.findViewById(R.id.mMonthChooser);
        mYearChooser = (MaterialSpinner) view.findViewById(R.id.mYearChooser);
        mCountryChooser = (MaterialSpinner) view.findViewById(R.id.mCountryChooser);
        mStateChooser = (MaterialSpinner) view.findViewById(R.id.mStateChooser);

        addressCompairater_chkr = (CheckBox) view.findViewById(R.id.addressCompairater_chkr);
        radioIsInsurence = (RadioGroup) view.findViewById(R.id.radioIsInsurence);
        radioYEs = (RadioButton) view.findViewById(R.id.radioYEs);
        radioNo = (RadioButton) view.findViewById(R.id.radioNo);

        mDetailsForm = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);


        mUpdateBtn = (Button) view.findViewById(R.id.mUpdateBtn);
        txtInsuranceNameLL = (LinearLayout) view.findViewById(R.id.txtInsuranceNameLL);
        txtInsuranceNumberLL = (LinearLayout) view.findViewById(R.id.txtInsuranceNumberLL);

        countryArrayList = MyPrefs.getCountryList(getActivity());
        setSpinnerAdapter();

        mUpdateBtn.setOnClickListener(this);
        radioIsInsurence.setOnCheckedChangeListener(this);

        radioYEs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtInsuranceNameLL.setVisibility(View.VISIBLE);
                    txtInsuranceNumberLL.setVisibility(View.VISIBLE);
                } else {
                    txtInsuranceNameLL.setVisibility(View.GONE);
                    txtInsuranceNumberLL.setVisibility(View.GONE);
                }
            }
        });


        mCountryChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              /* if(dBillingDetails!=null && dBillingDetails.getCountry_nm()!=null && !dBillingDetails.getCountry_nm().equals(""))
               {*/
                mList_State = new ArrayList<String>();

                mStateChooser.setAdapter(new SpinnerAdapter(activity, 0, mList_State));
                mCountryChooser.setTag(hashMapCountry.get(mCountryChooser.getSelectedItem().toString()));
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mCountryChooser.getSelectedItem().toString().trim()),selLang).enqueue(stateListCallback);
                //  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mStateChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  String mName = mList_Country.get(position);
                mCountryChooser.setTag(hashMapCountry.get(mCountryChooser.getSelectedItem().toString()));
                mStateChooser.setTag(hashMapState.get(mStateChooser.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mYearChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYearChooser.setTag(mYearChooser.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mMonthChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = position + 1;
                if (position < 10) {
                    mMonthChooser.setTag("0" + position);
                } else
                    mMonthChooser.setTag(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        addressCompairater_chkr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserLoginDetails.LoginDetails details=PAccountDetails.getInStnace().getdProfileDetails();;
                    // UserProfileDetails.DProfileDetails dProfileDetails =
                    billing_address1.setText(details.getAddress_1().toString());
                    billing_address2.setText(details.getAddress_2().toString());
                    mCity.setText(details.getCity());
                    mZipCode.setText(details.getPostcode());
                    String strCountryName = details.getCountry_nm().trim();
                    if (!TextUtils.isEmpty(strCountryName)) {
                        for (int i = 0; i < mList_Country.size(); i++) {
                            if (mList_Country.get(i).toLowerCase().equalsIgnoreCase(strCountryName.toLowerCase())) {
                                mCountryChooser.setSelection(i);
                                break;
                            }
                        }
                    }
                } else {
                    billing_address1.setText("");
                    billing_address2.setText("");
                }
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

                setBillingInfo();
                break;
        }
    }

    private void setBillingInfo() {

        String str_card_name = name_on_card.getText().toString().trim();
        String str_card_no = card_no.getText().toString().trim();
        String str_cvv_no = cvv_no.getText().toString().trim();
        String str_billing_add1 = billing_address1.getText().toString().trim();
        String str_billing_add2 = billing_address2.getText().toString().trim();
        String str_city = mCity.getText().toString().trim();
        String str_zip = mZipCode.getText().toString().trim();
        String str_incCompyName = insuranceCompnyName.getText().toString().trim();
        String str_incCompyNo = insuranceNo.getText().toString().trim();
        UserLoginDetails.LoginDetails details = MyPrefs.getLoginDetails(getActivity());
        String strSenderId = details.getUser_seq();
        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(str_card_name) || !Utils.validateLName(str_card_name)) {
            name_on_card.setError(getString(R.string.error_invalid_first_name));
            focusView = name_on_card;
            cancel = true;
        } else if (TextUtils.isEmpty(str_card_no) || !Utils.isNumeric(str_card_no)) {
            card_no.setError(getString(R.string.error_invalid_last_name));
            focusView = card_no;
            cancel = true;
        } else if (TextUtils.isEmpty(str_cvv_no) && !isvalidCvv(str_cvv_no)) {
            cvv_no.setError(getString(R.string.error_invalid_city));
            focusView = cvv_no;
            cancel = true;
        } else if (TextUtils.isEmpty(str_billing_add1)) {
            billing_address1.setError(getString(R.string.error_invalid_address));
            focusView = billing_address1;
            cancel = true;
        } else if (TextUtils.isEmpty(str_city) || !Utils.validateCity(str_city)) {
            mCity.setError(getString(R.string.error_invalid_city));
            focusView = mCity;
            cancel = true;
        }else if(radioYEs.isChecked()) {
            if (TextUtils.isEmpty(str_incCompyName)){
                insuranceCompnyName.setError(getString(R.string.error_field_required));
                focusView = insuranceCompnyName;
                cancel = true;
            }else if (TextUtils.isEmpty(str_incCompyNo)){
                insuranceNo.setError(getString(R.string.error_field_required));
                focusView = insuranceNo;
                cancel = true;

            }
            if (cancel) {
                focusView.requestFocus();
            } else {

                BillingInformationModel.DataBean billingModel = new BillingInformationModel.DataBean();
                billingModel.setBillingAddress1(str_billing_add1);
                billingModel.setBillingAddress2(str_billing_add2);
                billingModel.setCardNo(str_card_no);
                billingModel.setNameOnCard(str_card_name);
                billingModel.setCity(str_city);
                billingModel.setCVV(str_cvv_no);
                billingModel.setExpirationMonth(mMonthChooser.getTag().toString());
                billingModel.setExpirationYear(mYearChooser.getTag().toString());
                billingModel.setUserId(Integer.valueOf(strSenderId));
                billingModel.setInsuranceCompany(str_incCompyName);
                billingModel.setInsuranceNo(str_incCompyNo);
                billingModel.setZip(str_zip);
                billingModel.setCountry(Integer.valueOf((String) mCountryChooser.getTag()));
                if (isInsurance) {
                    billingModel.setIsInsurance("true");
                } else {
                    billingModel.setIsInsurance("false");
                }

                billingModel.setStateId(Integer.valueOf((String) mStateChooser.getTag()));

                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    mUpdateBtn.setEnabled(false);
                    RestAdapter.getAdapter().updatePatient_Billing_Information(billingModel.getNameOnCard(), billingModel.getCardNo(),
                            billingModel.getCVV(), billingModel.getExpirationMonth(), billingModel.getExpirationYear(),
                            billingModel.getBillingAddress1(), billingModel.getBillingAddress2(), billingModel.getCity(),
                            billingModel.getStateId(), billingModel.getUserId(), billingModel.getIsInsurance(),
                            billingModel.getInsuranceCompany(), billingModel.getInsuranceNo(),billingModel.getZip(),billingModel.getCountry(),langOpted).enqueue(updateProfileListner);


                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }

            }
        }
        else
        {
            BillingInformationModel.DataBean billingModel = new BillingInformationModel.DataBean();
            billingModel.setBillingAddress1(str_billing_add1);
            billingModel.setBillingAddress2(str_billing_add2);
            billingModel.setCardNo(str_card_no);
            billingModel.setNameOnCard(str_card_name);
            billingModel.setCity(str_city);
            billingModel.setCVV(str_cvv_no);
            billingModel.setExpirationMonth(mMonthChooser.getTag().toString());
            billingModel.setExpirationYear(mYearChooser.getTag().toString());
            billingModel.setUserId(Integer.valueOf(strSenderId));
            billingModel.setInsuranceCompany(str_incCompyName);
            billingModel.setInsuranceNo(str_incCompyNo);
            billingModel.setZip(str_zip);
            billingModel.setCountry(Integer.valueOf((String) mCountryChooser.getTag()));
            if (isInsurance) {
                billingModel.setIsInsurance("true");
            } else {
                billingModel.setIsInsurance("false");
            }
            if(mStateChooser.getTag()!=null){
                billingModel.setStateId(Integer.valueOf((String) mStateChooser.getTag()));
            }


            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().updatePatient_Billing_Information(billingModel.getNameOnCard(), billingModel.getCardNo(),
                        billingModel.getCVV(), billingModel.getExpirationMonth(), billingModel.getExpirationYear(),
                        billingModel.getBillingAddress1(), billingModel.getBillingAddress2(), billingModel.getCity(),
                        billingModel.getStateId(), billingModel.getUserId(), billingModel.getIsInsurance(),
                        billingModel.getInsuranceCompany(), billingModel.getInsuranceNo(),billingModel.getZip(),billingModel.getCountry(),langOpted).enqueue(updateProfileListner);


            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isName(String str_card_no) {
        return str_card_no.length() > 10;
    }

    private boolean isvalidCvv(String cvv) {
        return cvv.length() == 3;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioYEs:

                isInsurance = true;
                break;
            case R.id.radioNo:

                isInsurance = false;
                break;

            default:
                break;

        }
    }
}
