package com.sibsefid.fragemnts.patient;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.CountrySpinnerAdapter;
import com.sibsefid.doctor.adapter.SpecialitySpinnerAdapter;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.doctor.adapter.StateSpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.SpecialityBean;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.DoctorListModel;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.patient.adapter.PatientSeeDoctorListAdapter;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PSeeDoctorList extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String TAG = "PSeeDoctorList";
    private static PSeeDoctorList fragment;
    private GridView mGridView;
    private LinearLayout btnFilter;
    private ProgressBar mProgress;
    private MaterialSpinner mCountryChooser;
    private MaterialSpinner mStateChooser;
    private MaterialSpinner mPrefferredTm;
    private MaterialSpinner mSpacialChooser;
    private MaterialSpinner mLanguageChooser;
    private MaterialSpinner mGenderChooser;
    private TextView mDateChooser;
    private FrameLayout navigationview;
    private DrawerLayout mDrawerLayout;
    private EditText mProviderName;
    private TextView filterBtn;
    private Button mChooseFirstAvailableBtn;
    private PatientActivity activity;
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
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
    private ArrayList<DoctorListModel.DataBean.DoctorListBean> doctorArrayList = new ArrayList<>();
    private ArrayList<DoctorListModel.DataBean.AvailablityListBean> doctorAvailabilityArrayList = new ArrayList<>();
    private ArrayList<String> preTimeArray = new ArrayList<>();
    private ArrayList<String> genderArray = new ArrayList<>();
    private ArrayList<String> languageArray = new ArrayList<>();
    private ArrayList<SpecialityBean.Speciality> arraySpeciality;
    private DoctorListModel.DataBean.DoctorListBean selectedDoctor;
    private PatientSeeDoctorListAdapter mAdapter;
    Callback<DoctorListModel> doctorListener = new Callback<DoctorListModel>() {
        @Override
        public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
            if (getActivity() != null) {
                activity.showProgress(false, mGridView, mProgress);
                if (response.isSuccessful()) {
                    doctorArrayList.clear();
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().getDoctorList() != null && response.body().getData().getDoctorList().size() > 0) {
                        doctorArrayList = response.body().getData().getDoctorList();
                        doctorAvailabilityArrayList = response.body().getData().getAvailablityList();
                        /*for(int i=0;i<doctorAvailabilityArrayList.size();i++){
                            doctorAvailabilityArrayList.get(i).setDateTimeF(Utils.convertDateTimeToProperFmt(  doctorAvailabilityArrayList.get(i).getDateTimeF()));
                            doctorAvailabilityArrayList.get(i).setDateTimeT(Utils.convertDateTimeToProperFmt(  doctorAvailabilityArrayList.get(i).getDateTimeT()));
                        }*/
                        mAdapter.setDoctorAvailabilityArrayList(doctorAvailabilityArrayList);
                        mAdapter.setDoctorList(doctorArrayList);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<DoctorListModel> call, Throwable t) {
            if (getActivity() != null) {
                activity.showProgress(false, mGridView, mProgress);
            }

        }
    };
    private String strCountry, strState, strSpecialist, strGender, strlanguage, strPrefTime, strName;
    private String strGenderValue = "";

    public static PSeeDoctorList newInstance() {

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        fragment = this;
        activity = (PatientActivity) getActivity();
        activity.forwardShowImg();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.see_doctor));
        String[] stringsReff = getActivity().getResources().getStringArray(R.array.prefferred_time);
        String[] stringsLanguage = getActivity().getResources().getStringArray(R.array.language_array);
        preTimeArray = new ArrayList<String>(Arrays.asList(stringsReff));
        languageArray = new ArrayList<String>(Arrays.asList(stringsLanguage));
        countryArrayList = MyPrefs.getCountryList(getActivity());
        arraySpeciality = activity.getArraySpeciality();
        genderArray.add("Male");
        genderArray.add("Female");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_patient_see_doctor_list, container, false);
        initiateView(view);
        setCountryArrayAdapter();
        setPrefferredTimeArrayAdapter();
        setSpecilistArrayAdapter();
        setGenderArrayAdapter();
        setLanguageArrayAdapter();
        doctorListCall();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(navigationview);
            }
        });

        return view;
    }

    private void doctorListCall() {
        if (Utils.isDeviceOnline(getActivity())) {
            activity.showProgress(true, mGridView, mProgress);
            String timezone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getDoctorList(strCountry, strState, timezone, strGenderValue, strSpecialist, mDateChooser.getText().toString(), strPrefTime, strlanguage, mProviderName.getText().toString(),langOpted).enqueue(doctorListener);
        }
    }

    private void filterApply() {

        mAdapter.setDoctorList(doctorArrayList);

        if (mAdapter.getDoctorList() != null && mAdapter.getDoctorList().size() > 0) {

            ArrayList<DoctorListModel.DataBean.DoctorListBean> filterArrayLists = new ArrayList<>();
            ArrayList<DoctorListModel.DataBean.DoctorListBean> currentArrayList = mAdapter.getDoctorList();

            if (strCountry.length() == 0 && strState.length() == 0 && strSpecialist.length() == 0) {
                mAdapter.setDoctorList(doctorArrayList);
                mAdapter.notifyDataSetChanged();
            } else {
                for (int i = 0; i < currentArrayList.size(); i++) {
                    if (!TextUtils.isEmpty(strCountry)) {
                        if (currentArrayList.get(i).getCountry().equalsIgnoreCase(strCountry)) {
                            if (!TextUtils.isEmpty(strState)) {
                                if (!TextUtils.isEmpty(strSpecialist)) {
                                    if (currentArrayList.get(i).getState().equalsIgnoreCase(strState)) {
                                        if (currentArrayList.get(i).getSpeciality().equalsIgnoreCase(strSpecialist)) {
                                            filterArrayLists.add(currentArrayList.get(i));
                                        }
                                    }
                                } else {
                                    if (currentArrayList.get(i).getState().equalsIgnoreCase(strState)) {
                                        filterArrayLists.add(currentArrayList.get(i));
                                    }
                                }
                            } else {
                                if (!TextUtils.isEmpty(strSpecialist)) {
                                    if (currentArrayList.get(i).getCountry().equalsIgnoreCase(strCountry)) {
                                        if (currentArrayList.get(i).getSpeciality().equalsIgnoreCase(strSpecialist)) {
                                            filterArrayLists.add(currentArrayList.get(i));
                                        }
                                    }
                                } else {
                                    if (currentArrayList.get(i).getCountry().equalsIgnoreCase(strCountry)) {
                                        filterArrayLists.add(currentArrayList.get(i));
                                    }
                                }
                            }
                        }
                    } else {
                        if (currentArrayList.get(i).getSpeciality().equalsIgnoreCase(strSpecialist)) {
                            filterArrayLists.add(currentArrayList.get(i));
                        }
                    }
                }
                mAdapter.setDoctorList(filterArrayLists);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    private void initiateView(View view) {

        mGridView = (GridView) view.findViewById(R.id.mGridView);
        btnFilter = (LinearLayout) view.findViewById(R.id.btnFilter);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        mCountryChooser = (MaterialSpinner) view.findViewById(R.id.mCountryChooser);
        mStateChooser = (MaterialSpinner) view.findViewById(R.id.mStateChooser);
        mPrefferredTm = (MaterialSpinner) view.findViewById(R.id.mPrefferredTm);
        mSpacialChooser = (MaterialSpinner) view.findViewById(R.id.mSpacialChooser);
        mLanguageChooser = (MaterialSpinner) view.findViewById(R.id.mLanguageChooser);
        mGenderChooser = (MaterialSpinner) view.findViewById(R.id.mGenderChooser);
        mDateChooser = (TextView) view.findViewById(R.id.mDateChooser);
        filterBtn = (TextView) view.findViewById(R.id.filterBtn);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.mDrawerLayout);
        navigationview = (FrameLayout) view.findViewById(R.id.navigationview);
        mProviderName = (EditText) view.findViewById(R.id.mProviderName);
        mChooseFirstAvailableBtn=(Button)view.findViewById(R.id.mChooseFirstAvailableBtn);
        mDateChooser.setOnClickListener(this);
        filterBtn.setOnClickListener(this);
        mChooseFirstAvailableBtn.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        mGridView.setNumColumns(3);
        mAdapter = new PatientSeeDoctorListAdapter(getActivity(), doctorArrayList);
        mAdapter.setOnClickListener(this);
        mGridView.setAdapter(mAdapter);

        setOnItemListenerAll();


    }

    private void setOnItemListenerAll() {


        mGenderChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strGender = "";
                if (position < 0) {
                    filterApply();
                    return;
                }
                strGender = genderArray.get(position);
                if (strGender.equalsIgnoreCase("Male")) {
                    strGenderValue = "1";
                } else if (strGender.equalsIgnoreCase("Female")) {
                    strGenderValue = "2";
                }
                filterApply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpacialChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSpecialist = "";
                if (position < 0) {
                    filterApply();
                    return;
                }
                strSpecialist = arraySpeciality.get(position).getSpecid();
                filterApply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mCountryChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetAdapter();
                strCountry = "";
                if (position < 0) {
                    filterApply();
                    return;
                }
                if (Utils.isDeviceOnline(getActivity())) {
                    strCountry = countryArrayList.get(position).getDetail_code();
                    String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().getStateList(countryArrayList.get(position).getDetail_code(),selLang).enqueue(stateListCallback);
                }
                filterApply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mStateChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strState = "";
                if (position < 0) {
                    filterApply();
                    return;
                }
                strState = citiesArrayList.get(position).getDetail_code();
                filterApply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mLanguageChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  resetAdapter();
                strlanguage = "";
                if (position < 0) {

                } else
                    strlanguage = languageArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mPrefferredTm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  resetAdapter();
                strPrefTime = "";
                if (position < 0) {

                } else
                    strPrefTime = preTimeArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void resetAdapter() {
        citiesArrayList = new ArrayList<StateList.City>();
        setStateSpinnerAdapter();
        // mAdapter.setDoctorList(doctorArrayList);
        // mAdapter.notifyDataSetChanged();
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

    private void setPrefferredTimeArrayAdapter() {
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, preTimeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrefferredTm.setAdapter(adapter);
    }

    private void setGenderArrayAdapter() {
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, genderArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderChooser.setAdapter(adapter);
    }

    private void setLanguageArrayAdapter() {
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, languageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLanguageChooser.setAdapter(adapter);
    }

    private void setSpecilistArrayAdapter() {
        if (arraySpeciality == null) {
            arraySpeciality = new ArrayList<>();
        }
        SpecialitySpinnerAdapter adapter = new SpecialitySpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arraySpeciality);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpacialChooser.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {


        int position = 0;


        switch (view.getId()) {

            case R.id.clickBtn:
                position = (Integer) view.getTag();
                PDoctorDetails pDoctorDetails = new PDoctorDetails();
                Bundle bundle = new Bundle();
                bundle.putString("id", doctorArrayList.get(position).getDoctorId());
                bundle.putSerializable("details", doctorArrayList.get(position));
                bundle.putSerializable("availabilitydetails", doctorAvailabilityArrayList);
                pDoctorDetails.setArguments(bundle);
                Utils.callFragmentForAddPatient(activity, pDoctorDetails, PDoctorDetails.TAG);
                break;

            case R.id.mScheduleBtn:
                position = (Integer) view.getTag();
                selectedDoctor = doctorArrayList.get(position);
                selectedDoctor.setAvailabe(false);
                Utils.callFragmentForAddPatient(activity, new PScheduleFragment(), PScheduleFragment.TAG);
                break;

            case R.id.mVisitNowBtn:
                position = (Integer) view.getTag();
                selectedDoctor = doctorArrayList.get(position);
                selectedDoctor.setAvailabe(true);
                Utils.callFragmentForAddPatient(activity, new PScheduleFragment(), PScheduleFragment.TAG);
                break;

            case R.id.mDateChooser:
                datePickerOpen();
                break;
            case R.id.btnFilter:
                mDrawerLayout.openDrawer(navigationview);
                break;

            case R.id.filterBtn:
                mDrawerLayout.closeDrawer(navigationview);
                doctorListCall();
                break;

            case R.id.mChooseFirstAvailableBtn:
                Utils.callFragmentForAddPatient(activity, new DoctorOnCall(), DoctorOnCall.TAG);
                break;


        }

    }

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PSeeDoctorList.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(getResources().getString(R.string.date_dailog_title));
        dpd.setMinDate(now);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String day = dayOfMonth + "";
        String month = monthOfYear + 1 + "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        }
        if (monthOfYear < 10) {
            month = "0" + month;
        }
        mDateChooser.setText((month) + "/" + day + "/" + year);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }

    @Subscribe
    public void onEvent(ArrayList<SpecialityBean.Speciality> arraySpeciality) {
        if (getActivity() != null) {
            this.arraySpeciality = arraySpeciality;
            setSpecilistArrayAdapter();
            Log.d("arraySpeciality----", "yes");
        }

    }

    public DoctorListModel.DataBean.DoctorListBean getSelectedDoctor() {
        return selectedDoctor;
    }


}
