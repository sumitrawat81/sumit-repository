package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.AvailabilityAdapterSpinner;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.DAvalilibilityModel;
import com.sibsefid.model.doctor.GetTimeZoneModel;
import com.sibsefid.model.doctor.SetAvailabilityResponseModel;
import com.sibsefid.model.doctor.SetMyAvailibilityModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MultiSpinner;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/9/16.
 */
public class DoctorAvailabilityFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, MultiSpinner.MultiSpinnerListener {

    public static final String TAG = "DoctorAvailabilityFragment";
    String fromWhere;
    ArrayAdapter<String> timeZoneAdapter;
    String fromDate;
    String timeZone;
    String fromHours = "";
    String fromMinutes = "";
    String fromAmPm = "AM";
    String toHours = "";
    String toMinutes = "";
    String toAmPm = "AM";
    String toDate;
    String spinnerText = "Select Week Days";
    DAvalilibilityModel.DAvMessageBean dAvMessageArrayList;
    private Button mButton_ViewDoctorAvailability = null;
    private Button button_AddAvailability = null;
    private DoctorActivity activity = null;
    private TextView mDateChooser;
    private Spinner mHourChooser;
    AdapterView.OnItemSelectedListener mHourChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mHourChooser.getSelectedItem().toString();
            if (getActivity().getResources().getString(R.string.hours).equalsIgnoreCase(textHint)) {
                return;
            } else {
                fromHours = mHourChooser.getSelectedItem().toString();
            }
            //  Toast.makeText(getActivity(), mHourChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MultiSpinner mWeekChooser;
    private View mDetailsForm;
    private View mProgressView;
    Callback<SetAvailabilityResponseModel> updateProfileListner = new Callback<SetAvailabilityResponseModel>() {
        @Override
        public void onResponse(Call<SetAvailabilityResponseModel> call, Response<SetAvailabilityResponseModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
updateUI();

                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<SetAvailabilityResponseModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };

    private void updateUI() {

         fromHours = "";
         fromMinutes = "";
         fromAmPm = "AM";
         toHours = "";
         toMinutes = "";
         toAmPm = "AM";

        mDateChooser.setText("");
        mToDateChooser.setText("");


        mMinuteChooser.setHint(getActivity().getResources().getString(R.string.minutes));
        mToHourChooser.setHint(getActivity().getResources().getString(R.string.hours));
        mToMinuteChooser.setHint(getActivity().getResources().getString(R.string.minutes));
    }

    private List<String> mTimeZone_Title = new ArrayList<>();
    private List<String> mTimeZone_Title_ID = null;
    private UserLoginDetails.LoginDetails details;
    private ArrayList<GetTimeZoneModel.DataBean> titleTimeZoneList = new ArrayList<>();
    private String time_zone_difference = "";
    private MaterialSpinner mMinuteChooser;
    AdapterView.OnItemSelectedListener mMinuteChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mMinuteChooser.getSelectedItem().toString();
            if (getActivity().getResources().getString(R.string.minutes).equalsIgnoreCase(textHint)) {
                return;
            } else {
                fromMinutes = mMinuteChooser.getSelectedItem().toString();
            }
            //Toast.makeText(getActivity(), mMinuteChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mTimeChooser;
    AdapterView.OnItemSelectedListener mTimeChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mTimeChooser.getSelectedItem().toString();
            /*if (getActivity().getResources().getString(R.string.am).equalsIgnoreCase(textHint))
                return;
            else*/
            fromAmPm = mTimeChooser.getSelectedItem().toString();
            // Toast.makeText(getActivity(), mTimeChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mTimeZoneChooser;
    Callback<GetTimeZoneModel> detailsCallback = new Callback<GetTimeZoneModel>() {
        @Override
        public void onResponse(Call<GetTimeZoneModel> call, Response<GetTimeZoneModel> response) {
            if (getActivity() != null) {
                // showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            titleTimeZoneList = response.body().getData();
                            mTimeZone_Title.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mTimeZone_Title.add(response.body().getData().get(i).getValue());
                                mTimeZone_Title_ID.add(response.body().getData().get(i).getItem());
                            }
                            timeZoneAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mTimeZone_Title);
                            timeZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mTimeZoneChooser.setAdapter(timeZoneAdapter);
                            if (fromWhere.equalsIgnoreCase("ViewDoctorAvailability")) {
                                for (int i = 0; i < mTimeZone_Title.size(); i++) {
                                    if (mTimeZone_Title.get(i).equalsIgnoreCase(dAvMessageArrayList.getTimezone())) {
                                        mTimeZoneChooser.setSelection(i + 1);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<GetTimeZoneModel> call, Throwable t) {
            if (getActivity() != null) {
                // showProgress(false);
            }
        }
    };
    AdapterView.OnItemSelectedListener mTimeZoneChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mTimeZoneChooser.getSelectedItem().toString();
            if (getActivity().getResources().getString(R.string.select_a_time_zone).equalsIgnoreCase(textHint)) {
                return;
            } else
                timeZone = mTimeZoneChooser.getSelectedItem().toString();
            time_zone_difference = mTimeZone_Title_ID.get(mTimeZoneChooser.getSelectedItemPosition());
            // Toast.makeText(getActivity(), mTimeChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private TextView mToDateChooser;
    private MaterialSpinner mToHourChooser;
    AdapterView.OnItemSelectedListener mToHourChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mToHourChooser.getSelectedItem().toString();
            if (getActivity().getResources().getString(R.string.hours).equalsIgnoreCase(textHint))
                return;
            else
                toHours = mToHourChooser.getSelectedItem().toString();
            // Toast.makeText(getActivity(), mToHourChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mToMinuteChooser;
    AdapterView.OnItemSelectedListener mToMinuteChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mToMinuteChooser.getSelectedItem().toString();
            if (getActivity().getResources().getString(R.string.minutes).equalsIgnoreCase(textHint))
                return;
            else
                toMinutes = mToMinuteChooser.getSelectedItem().toString();
            //  Toast.makeText(getActivity(), mToMinuteChooser.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mToTimeChooser;
    AdapterView.OnItemSelectedListener mToTimeChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mToTimeChooser.getSelectedItem().toString();
            toAmPm = mToTimeChooser.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private String[] Am_PM_ITEMS = new String[2];
    private String[] MINUTES_ITEMS = null;
    private String[] HOURS_ITEMS = null;
    private String[] Week_ITEMS = null;
    private String[] TIME_ZONE_ITEMS = new String[13];
    private ArrayList<String> week_days_items = new ArrayList<>();
    private String[] week_full_items = null;
    private int DATE_CHOOSER_TYPE = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.docotor_avaiability));

        activity.forwardShowImg();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;
        View view = inflater.inflate(R.layout.fragment_doctor_availability, container, false);

        initComponents(view);
        details = MyPrefs.getLoginDetails(getActivity());
        Log.e("UserId=", details.getUser_seq() + "");
        if (Utils.isDeviceOnline(getActivity())) {
            // showProgress(true);
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().GetTimeZone(selLangToSend).enqueue(detailsCallback);
        }
        return view;
    }

    private void initComponents(View view) {
        mButton_ViewDoctorAvailability = (Button) view.findViewById(R.id.button_DoctorAvailability);
        button_AddAvailability = (Button) view.findViewById(R.id.button_AddAvailability);
        mDateChooser = (TextView) view.findViewById(R.id.mDateChooser);
        mHourChooser = (Spinner) view.findViewById(R.id.mHourChooser);
        mMinuteChooser = (MaterialSpinner) view.findViewById(R.id.mMinuteChooser);
        mTimeChooser = (MaterialSpinner) view.findViewById(R.id.mTimeChooser);
        mTimeZoneChooser = (MaterialSpinner) view.findViewById(R.id.mTimeZoneChooser);
        mToDateChooser = (TextView) view.findViewById(R.id.mToDateChooser);
        mToHourChooser = (MaterialSpinner) view.findViewById(R.id.mToHourChooser);
        mToMinuteChooser = (MaterialSpinner) view.findViewById(R.id.mToMinuteChooser);
        mToTimeChooser = (MaterialSpinner) view.findViewById(R.id.mToTimeChooser);
        mWeekChooser = (MultiSpinner) view.findViewById(R.id.mWeekChooser);
        // mToTimeZoneChooser = (MaterialSpinner) view.findViewById(R.id.mToTimeZoneChooser);
        mDetailsForm = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);
        mTimeZone_Title = new ArrayList<>();
        mTimeZone_Title_ID = new ArrayList<>();
        fromWhere = getArguments().getString("from");
        initiateSpinner();

        button_AddAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAvailability();
            }
        });

    }

    private void initlizeValues() {

        mDateChooser.setText(dAvMessageArrayList.getFromdate());
        mButton_ViewDoctorAvailability.setVisibility(View.GONE);

        for (int i = 0; i < HOURS_ITEMS.length; i++) {
            if (HOURS_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getFromHr().trim())) {
                mHourChooser.setSelection(i + 1);
            }
        }

        for (int i = 0; i < MINUTES_ITEMS.length; i++) {
            if (MINUTES_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getFromMin())) {
                mMinuteChooser.setSelection(i + 1);
            }
        }
        for (int i = 0; i < Am_PM_ITEMS.length; i++) {
            if (Am_PM_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getFromMeridiane())) {
                mTimeChooser.setSelection(i + 1);
            }
        }


        mToDateChooser.setText(dAvMessageArrayList.getTodate());

        for (int i = 0; i < HOURS_ITEMS.length; i++) {
            if (HOURS_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getToHr())) {
                mToHourChooser.setSelection(i + 1);
            }
        }

        for (int i = 0; i < MINUTES_ITEMS.length; i++) {
            if (MINUTES_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getToMin())) {
                mToMinuteChooser.setSelection(i + 1);
            }
        }
        for (int i = 0; i < Am_PM_ITEMS.length; i++) {
            if (Am_PM_ITEMS[i].equalsIgnoreCase(dAvMessageArrayList.getToMeridiane())) {
                mToTimeChooser.setSelection(i + 1);
            }
        }

    }

    private void addAvailability() {

        fromDate = mDateChooser.getText().toString().trim();
        toDate = mToDateChooser.getText().toString().trim();

        if (TextUtils.isEmpty(fromDate)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_from_date), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(fromHours)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please__choose_from_hours), Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(fromMinutes)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_from_minutes), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(fromAmPm)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please__choose_from_time), Toast.LENGTH_SHORT).show();

        }  else if (TextUtils.isEmpty(time_zone_difference)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please__choose_timezone), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(toDate)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please__choose_to_date), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(toHours)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_to_hours), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(toMinutes)) {

            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_to_minutes), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(toAmPm)) {


            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_to_time), Toast.LENGTH_SHORT).show();

        } else if (spinnerText.equals("Select Week Days")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_choose_weed), Toast.LENGTH_SHORT).show();
        }  else {

            SetMyAvailibilityModel myAvail = new SetMyAvailibilityModel();
            myAvail.setId(Integer.valueOf(details.getUser_seq()));
            myAvail.setFromDate(fromDate);
            myAvail.setFromHours(fromHours);
            myAvail.setFromMinutes(fromMinutes);
            myAvail.setFromMeridane(fromAmPm);
            myAvail.setToDate(toDate);
            myAvail.setToHours(toHours);
            myAvail.setToMinutes(toMinutes);
            myAvail.setToMeridane(toAmPm);
            myAvail.setTimeZone(timeZone);
            myAvail.setTimeZoneDiff(time_zone_difference);
            myAvail.setAvailabledays(spinnerText);
            if (fromWhere.equalsIgnoreCase("ViewDoctorAvailability")) {
                myAvail.setAppointmentId(dAvMessageArrayList.getAvailableId());
            } else
                myAvail.setAppointmentId("");

            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().addDoctorAvailability(
                        myAvail.getFromDate(),
                        myAvail.getFromHours(),
                        myAvail.getFromMinutes(),
                        myAvail.getFromMeridane(),
                        myAvail.getToDate(),
                        myAvail.getToHours(),
                        myAvail.getToMinutes(),
                        myAvail.getToMeridane(),
                        myAvail.getId(),
                        myAvail.getTimeZone(),
                        myAvail.getTimeZoneDiff(),
                        myAvail.getAvailabledays(), myAvail.getAppointmentId(),langOpted).enqueue(updateProfileListner);

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
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


    public void initiateSpinner() {


        MINUTES_ITEMS = getActivity().getResources().getStringArray(R.array.minutes_array);
        HOURS_ITEMS = getActivity().getResources().getStringArray(R.array.hoursarray);
        Week_ITEMS = getActivity().getResources().getStringArray(R.array.week);
        week_full_items = getActivity().getResources().getStringArray(R.array.week_full);
        Am_PM_ITEMS[0] = getActivity().getResources().getString(R.string.am);
        Am_PM_ITEMS[1] = getActivity().getResources().getString(R.string.pm);

        ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Am_PM_ITEMS);
        AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> HoursAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, HOURS_ITEMS);
        HoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> minutesArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MINUTES_ITEMS);
        minutesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mHourChooser.setAdapter(new AvailabilityAdapterSpinner(getActivity(), 0, HOURS_ITEMS));
       // mHourChooser.setAdapter(HoursAdapter);
        mMinuteChooser.setAdapter(minutesArrayAdapter);
        mTimeChooser.setAdapter(AMPMAdapter);
        //mTimeZoneChooser.setAdapter(timeZoneAdapter);

        mToHourChooser.setAdapter(HoursAdapter);
        mToMinuteChooser.setAdapter(minutesArrayAdapter);
        mToTimeChooser.setAdapter(AMPMAdapter);


        for (int i = 0; i < Week_ITEMS.length; i++) {
            week_days_items.add(i, Week_ITEMS[i]);
        }

        mHourChooser.setOnItemSelectedListener(mHourChooserListner);
        mMinuteChooser.setOnItemSelectedListener(mMinuteChooserListner);
        mTimeChooser.setOnItemSelectedListener(mTimeChooserListner);
        mTimeZoneChooser.setOnItemSelectedListener(mTimeZoneChooserListner);

        mToHourChooser.setOnItemSelectedListener(mToHourChooserListner);
        mToMinuteChooser.setOnItemSelectedListener(mToMinuteChooserListner);
        mToTimeChooser.setOnItemSelectedListener(mToTimeChooserListner);
        // mToTimeZoneChooser.setOnItemSelectedListener(mToTimeZoneChooserListner);
        mWeekChooser.setItems(week_days_items, getString(R.string.select_week_days), this);


        if (fromWhere.equalsIgnoreCase("ViewDoctorAvailability")) {
            dAvMessageArrayList = (DAvalilibilityModel.DAvMessageBean) getArguments().getSerializable("availability_list");

            initlizeValues();
        } else {
            mButton_ViewDoctorAvailability.setVisibility(View.VISIBLE);
        }

        mDateChooser.setOnClickListener(this);
        mToDateChooser.setOnClickListener(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton_ViewDoctorAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity != null) {
                    Utils.callFragmentForAddDoctor(activity, new ViewDoctorAvailabilityFragment(), ViewDoctorAvailabilityFragment.TAG);
                }
            }
        });
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }

    @Override
    public void onClick(View view) {
        if (mDateChooser == view) {
            DATE_CHOOSER_TYPE = 1;
            datePickerOpen();
        } else if (mToDateChooser == view) {
            DATE_CHOOSER_TYPE = 2;
            datePickerOpen();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        if (DATE_CHOOSER_TYPE == 1) {
            String day = dayOfMonth + "";
            String month = monthOfYear + "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            }
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            }
            mDateChooser.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        } else if (DATE_CHOOSER_TYPE == 2) {
            String day = dayOfMonth + "";
            String month = monthOfYear + "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            }
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            }
            mToDateChooser.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        }
    }

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                DoctorAvailabilityFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setMinDate(now);
        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(getResources().getString(R.string.date_dailog_title));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        StringBuffer spinnerBuffer = new StringBuffer();

        boolean someUnselected = false;
        for (int i = 0; i < selected.length; i++) {

            if (selected[i] == true) {
                spinnerBuffer.append(week_full_items[i]);
                spinnerBuffer.append(", ");
                someUnselected = true;
            }

        }
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2) {

                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
            } else {
                spinnerText = "Select Week Days";
            }
        } else {
            spinnerText = "Select Week Days";
        }
        //  Toast.makeText(getActivity(), spinnerText, Toast.LENGTH_SHORT).show();
    }
}
