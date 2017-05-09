package com.sibsefid.fragemnts.patient.bookappointment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.CountrySpinnerAdapter;
import com.sibsefid.doctor.adapter.StateSpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.PPharmacyModel;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PPharmacyChange extends Fragment implements View.OnClickListener {
    public static final String TAG = "PPharmacyChange";
    UserLoginDetails.LoginDetails loginDetails;
    PatientActivity activity;
    private Button mSaveBtn;
    private EditText mName;
    private EditText mAddress;
    private EditText mCity;
    private EditText mPhoneNumber;
    private EditText mZipCode;
    private MaterialSpinner mCountryChooser;
    private MaterialSpinner mStateChooser;
    private PPharmacyModel.AddressBean pPharmacyModel;
    private View mProgress;
    private PChoosePharmacy pharmacy;
    Callback<PPharmacyModel> patientPharmacyUpdateCallback = new Callback<PPharmacyModel>() {
        @Override
        public void onResponse(Call<PPharmacyModel> call, Response<PPharmacyModel> response) {
            if (getActivity() != null) {
                mProgress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        if (pharmacy != null) {
                            pharmacy.setpPharmacyModel(pPharmacyModel);
                            pharmacy.updateUi();
                        }
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.change_pharmacy_success), Toast.LENGTH_SHORT).show();
                        activity.onBackPressed();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PPharmacyModel> call, Throwable t) {
            if (getActivity() != null) {
                mProgress.setVisibility(View.GONE);
            }
        }
    };
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    Callback<PPharmacyModel> patientPharmacyCallback = new Callback<PPharmacyModel>() {
        @Override
        public void onResponse(Call<PPharmacyModel> call, Response<PPharmacyModel> response) {
            if (getActivity() != null) {

                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getAddressbean() != null) {
                        pPharmacyModel = response.body().getAddressbean().get(0);
                        updateUi();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PPharmacyModel> call, Throwable t) {
            if (getActivity() != null) {

            }
        }
    };
    private ArrayList<StateList.City> citiesArrayList = new ArrayList<>();
    Callback<StateList> stateListCallback = new Callback<StateList>() {
        @Override
        public void onResponse(Call<StateList> call, Response<StateList> response) {
            if (getActivity() != null) {

                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                        if (response.body().getCity() != null) {
                            citiesArrayList = response.body().getCity();
                            setStateSpinnerAdapter();
                            if (citiesArrayList != null && citiesArrayList.size() > 0) {
                                setStateSpinnerAdapter();
                                for (int i = 0; i < citiesArrayList.size(); i++) {
                                    if (pPharmacyModel.getState_pharmacy().equalsIgnoreCase(citiesArrayList.get(i).getDetail_code())) {
                                        mStateChooser.setSelection(i + 1);
                                        break;
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

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDetails = MyPrefs.getLoginDetails(getActivity());
        activity=(PatientActivity)getActivity();
        pharmacy = PChoosePharmacy.getInstance();
        if (pharmacy != null)
            pPharmacyModel = pharmacy.getpPharmacyModel();
        if (pPharmacyModel == null) {
            if (Utils.isDeviceOnline(getActivity())) {
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getPPharmacy(loginDetails.getUser_seq(),langOpted).enqueue(patientPharmacyCallback);
            }
        }
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

        View view = inflater.inflate(R.layout.fragment_ppharmacy_change, container, false);
        initiateView(view);
        countryArrayList = MyPrefs.getCountryList(getActivity());
        setCountryArrayAdapter();
        if (pPharmacyModel != null)
            updateUi();
        return view;
    }

    private void initiateView(View view) {
        mProgress = view.findViewById(R.id.progress);
        mName = (EditText) view.findViewById(R.id.mName);
        mAddress = (EditText) view.findViewById(R.id.mAddress);
        mCity = (EditText) view.findViewById(R.id.mCity);
        mPhoneNumber = (EditText) view.findViewById(R.id.mPhoneNumber);
        mZipCode = (EditText) view.findViewById(R.id.mZipCode);
        mCountryChooser = (MaterialSpinner) view.findViewById(R.id.mCountryChooser);
        mStateChooser = (MaterialSpinner) view.findViewById(R.id.mStateChooser);
        mSaveBtn = (Button) view.findViewById(R.id.mContinueBtn);
        mSaveBtn.setOnClickListener(this);
        setOnItemListenerAll();
    }

    private void updateUi() {
        mName.setText(pPharmacyModel.getPharmacy_name());
        mAddress.setText(pPharmacyModel.getAddress1_pharmacy());
        mCity.setText(pPharmacyModel.getCity_pharmacy());
        mPhoneNumber.setText(pPharmacyModel.getPhone_pharmacy());
        mZipCode.setText(pPharmacyModel.getPostcode_pharmacy());

        if (countryArrayList != null && countryArrayList.size() > 0) {
            for (int i = 0; i < countryArrayList.size(); i++) {
                if (countryArrayList.get(i).getDetail_code().equalsIgnoreCase(pPharmacyModel.getCountry_pharmacy())) {
                    mCountryChooser.setSelection(i + 1);
                    break;
                }
            }
        }


    }

    private void setOnItemListenerAll() {

        mCountryChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetAdapter();
                if (position < 0) {
                    return;
                }
                if (Utils.isDeviceOnline(getActivity())) {
                    String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().getStateList(countryArrayList.get(position).getDetail_code(),selLang).enqueue(stateListCallback);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mStateChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void resetAdapter() {
        citiesArrayList = new ArrayList<StateList.City>();
        setStateSpinnerAdapter();
    }

    private void setCountryArrayAdapter() {
        if (countryArrayList != null && countryArrayList.size() > 0) {
            CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, countryArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCountryChooser.setAdapter(adapter);
        }
    }

    private void setStateSpinnerAdapter() {
        StateSpinnerAdapter adapter = new StateSpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, citiesArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateChooser.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBackBtn:
                getActivity().onBackPressed();
                break;

            case R.id.mContinueBtn:
                if (Utils.isDeviceOnline(getActivity())) {

                    String strName = mName.getText().toString().trim();
                    String strmAddress = mAddress.getText().toString().trim();
                    String strmCity = mCity.getText().toString().trim();
                    String strmPhoneNumber = mPhoneNumber.getText().toString().trim();
                    String strmZipCode = mZipCode.getText().toString().trim();


                    if (TextUtils.isEmpty(strName)) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_first_name), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(strmAddress)) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_address), Toast.LENGTH_SHORT).show();
                    } else if (mCountryChooser.getSelectedItemPosition() <= 0) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_country), Toast.LENGTH_SHORT).show();
                    } else if (mStateChooser.getSelectedItemPosition() <= 0) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_state_name), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(strmCity) && !Utils.validateCity(strmCity)) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_city), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(strmPhoneNumber)) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_phone_number), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(strmZipCode)) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_zip), Toast.LENGTH_SHORT).show();
                    } else {
                        mProgress.setVisibility(View.VISIBLE);
                        String strCountryId = countryArrayList.get(mCountryChooser.getSelectedItemPosition() - 1).getDetail_code();
                        String strStateId = citiesArrayList.get(mStateChooser.getSelectedItemPosition() - 1).getDetail_code();

                        pPharmacyModel.setPhone_pharmacy(strmPhoneNumber);
                        pPharmacyModel.setPostcode_pharmacy(strmZipCode);
                        pPharmacyModel.setPharmacy_name(strName);
                        pPharmacyModel.setAddress1_pharmacy(strmAddress);
                        pPharmacyModel.setCity_pharmacy(strmCity);
                        pPharmacyModel.setCountry_pharmacy(strCountryId);
                        pPharmacyModel.setState_pharmacy(strStateId);
                        pPharmacyModel.setStateNamePharmacy(citiesArrayList.get(mStateChooser.getSelectedItemPosition() - 1).getDetail_code_nm());
                        pPharmacyModel.setCountrynamePharmacy(countryArrayList.get(mCountryChooser.getSelectedItemPosition() - 1).getDetail_code_nm());
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        if (pharmacy != null) {
                            RestAdapter.getAdapter().changePharmacy(strmAddress, strCountryId, strStateId, strmCity, strmPhoneNumber, strmZipCode, strName, pharmacy.getLoginDetails().getUser_seq(),langOpted).enqueue(patientPharmacyUpdateCallback);
                        } else {
                            RestAdapter.getAdapter().changePharmacy(strmAddress, strCountryId, strStateId, strmCity, strmPhoneNumber, strmZipCode, strName, loginDetails.getUser_seq(),langOpted).enqueue(patientPharmacyUpdateCallback);
                        }
                    }

                }
                break;
        }
    }
}
