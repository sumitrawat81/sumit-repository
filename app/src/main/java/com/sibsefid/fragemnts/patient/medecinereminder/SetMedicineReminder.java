package com.sibsefid.fragemnts.patient.medecinereminder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragment.doctor.BaseFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.GetMedicinesList;
import com.sibsefid.model.patient.SetMedicineReminderModel;
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
 * Created by ubuntu on 3/9/16.
 */
public class SetMedicineReminder extends BaseFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener, MultiSpinner.MultiSpinnerListener {

    public static final String TAG = "MyProfileFragment";
    public static List<GetMedicinesList.DataBean.MedicineListBean> titleBeenArrayList = new ArrayList<>();
    public static List<String> mList_Title = null;
    public static List<String> mList_Title_ID = null;
    String spinnerText = "Select Week Days";
    private UserLoginDetails.LoginDetails details;
    private Context mContext = null;
    private PatientActivity activity = null;
    private Button mSaveBtn;
    private TextView mStartDate;
    private TextView mEndDate;
    // private MultiSpinner mWeekChooser;
    private MaterialSpinner mHourChooser;
    AdapterView.OnItemSelectedListener mHourChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mHourChooser.getSelectedItem().toString();
            Log.d(TAG, "mHourChooserListner: " + textHint);
            if (getActivity().getResources().getString(R.string.hours).equalsIgnoreCase(textHint))
                return;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mMinuteChooser;
    AdapterView.OnItemSelectedListener mMinuteChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mMinuteChooser.getSelectedItem().toString();
            Log.d(TAG, "mMinuteChooserListner: " + textHint);
            if (getActivity().getResources().getString(R.string.minutes).equalsIgnoreCase(textHint))
                return;

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


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mMedicineChooser;
    Callback<GetMedicinesList> composeToPModelCallback = new Callback<GetMedicinesList>() {
        @Override
        public void onResponse(Call<GetMedicinesList> call, Response<GetMedicinesList> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().getMedicineList().size() > 0) {
                        if (response.body().getData() != null) {
                            titleBeenArrayList = response.body().getData().getMedicineList();
                            mList_Title.clear();
                            for (int i = 0; i < titleBeenArrayList.size(); i++) {
                                mList_Title.add(titleBeenArrayList.get(i).getMedicinName());
                                mList_Title_ID.add(titleBeenArrayList.get(i).getSrno());
                            }
                            mMedicineChooser.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_Title));
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<GetMedicinesList> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private EditText mDrugDose;
    private EditText mAbout;
    private ScrollView details_form;
    private int DIALOG_TYPE = 0;
    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (mStartDate == view) {
                DIALOG_TYPE = 1;
            } else if (mEndDate == view) {
                DIALOG_TYPE = 2;
            }
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    SetMedicineReminder.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(false);
            dpd.vibrate(true);
            dpd.dismissOnPause(true);
            dpd.showYearPickerFirst(false);
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
            dpd.setTitle(getActivity().getResources().getString(R.string.date_dailog_title));
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    };
    private ArrayList<String> week_days_items = new ArrayList<>();
    private String[] Week_ITEMS = null;
    private String[] week_full_items = null;
    private ProgressBar progress;
    Callback<SetMedicineReminderModel> updateProfileListner = new Callback<SetMedicineReminderModel>() {
        @Override
        public void onResponse(Call<SetMedicineReminderModel> call, Response<SetMedicineReminderModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


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
        public void onFailure(Call<SetMedicineReminderModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private String[] Am_PM_ITEMS = new String[2];
    private String[] MINUTES_ITEMS = null;
    private String[] HOURS_ITEMS = null;
    private CheckBox sunCheck, monCheck, tueCheck, wedCheck, thusCheck, friCheck, satCheck;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
      //  activity.getmTitle().setText(getActivity().getResources().getString(R.string.myprofile));
        activity.forwardShowImg();
        details = MyPrefs.getLoginDetails(getActivity());
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

        View view = inflater.inflate(R.layout.set_medicine_reminder, container, false);

        initComponents(view);

        if (Utils.isDeviceOnline(getActivity())) {
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getListMedicinePatien(details.getUser_seq(),langOpted).enqueue(composeToPModelCallback);
        }
        return view;
    }


    public void initComponents(View view) {

        mSaveBtn = (Button) view.findViewById(R.id.mSaveBtn);
        mStartDate = (TextView) view.findViewById(R.id.mStartDate);
        mEndDate = (TextView) view.findViewById(R.id.mEndDate);
        // mWeekChooser = (MultiSpinner) view.findViewById(R.id.mWeekChooser);
        mStartDate.setOnClickListener(dateOnClickListener);
        mEndDate.setOnClickListener(dateOnClickListener);
        mHourChooser = (MaterialSpinner) view.findViewById(R.id.mHourChooser);
        mMinuteChooser = (MaterialSpinner) view.findViewById(R.id.mMinuteChooser);
        mTimeChooser = (MaterialSpinner) view.findViewById(R.id.mTimeChooser);
        mMedicineChooser = (MaterialSpinner) view.findViewById(R.id.mMedicineChooser);
        mDrugDose = (EditText) view.findViewById(R.id.mDrugDose);
        mAbout = (EditText) view.findViewById(R.id.mAbout);
        sunCheck = (CheckBox) view.findViewById(R.id.sunCheck);
        monCheck = (CheckBox) view.findViewById(R.id.monCheck);
        tueCheck = (CheckBox) view.findViewById(R.id.tueCheck);
        wedCheck = (CheckBox) view.findViewById(R.id.wedCheck);
        thusCheck = (CheckBox) view.findViewById(R.id.thusCheck);
        friCheck = (CheckBox) view.findViewById(R.id.friCheck);
        satCheck = (CheckBox) view.findViewById(R.id.satCheck);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        details_form = (ScrollView) view.findViewById(R.id.details_form);
        Week_ITEMS = getActivity().getResources().getStringArray(R.array.week);
        mList_Title = new ArrayList<>();
        mList_Title_ID = new ArrayList<>();
        setSpinnerAdapter();

        mSaveBtn.setOnClickListener(this);
    }

    private void setSpinnerAdapter() {
        week_full_items = getActivity().getResources().getStringArray(R.array.week_full);
        for (int i = 0; i < Week_ITEMS.length; i++) {
            week_days_items.add(i, Week_ITEMS[i]);
        }
        // mWeekChooser.setItems(week_days_items, getActivity().getString(R.string.select_week_days),this);


        MINUTES_ITEMS = getActivity().getResources().getStringArray(R.array.minutes_array);
        HOURS_ITEMS = getActivity().getResources().getStringArray(R.array.hoursarray);
        Am_PM_ITEMS[0] = getActivity().getResources().getString(R.string.am);
        Am_PM_ITEMS[1] = getActivity().getResources().getString(R.string.pm);

        ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Am_PM_ITEMS);
        AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> HoursAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, HOURS_ITEMS);
        HoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> minutesArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MINUTES_ITEMS);
        minutesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mHourChooser.setAdapter(HoursAdapter);
        mMinuteChooser.setAdapter(minutesArrayAdapter);
        mTimeChooser.setAdapter(AMPMAdapter);
        mTimeChooser.setSelection(0);
        mHourChooser.setOnItemSelectedListener(mHourChooserListner);
        mMinuteChooser.setOnItemSelectedListener(mMinuteChooserListner);
        mTimeChooser.setOnItemSelectedListener(mTimeChooserListner);


        mMedicineChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    mMedicineChooser.setTag(mList_Title_ID.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void updateUi() {


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            details_form.setVisibility(show ? View.GONE : View.VISIBLE);
            details_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    details_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            details_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.mSaveBtn:
                submitMessage();

                break;

        }


    }

    private void submitMessage() {
        boolean cancel = false;


        String strSelectedDoId = (String) mMedicineChooser.getTag();
        int hoursPos = mHourChooser.getSelectedItemPosition();
        int minutesPos = mMinuteChooser.getSelectedItemPosition();
        int amPos = mTimeChooser.getSelectedItemPosition();

        String strDate = mStartDate.getText().toString().trim();
        String endDate = mEndDate.getText().toString().trim();
        String doseValue = mDrugDose.getText().toString().trim();
        String note = mAbout.getText().toString().trim();


        ArrayList<String> categorylist = new ArrayList<>();
        if (sunCheck.isChecked()) {

            categorylist.add("Sunday");
        }
        if (monCheck.isChecked()) {

            categorylist.add("Monday");
        }
        if (tueCheck.isChecked()) {

            categorylist.add("Tuesday");
        }
        if (wedCheck.isChecked()) {

            categorylist.add("Wednesday");
        }
        if (thusCheck.isChecked()) {

            categorylist.add("Thusday");
        }
        if (friCheck.isChecked()) {

            categorylist.add("Friday");
        }
        if (satCheck.isChecked()) {

            categorylist.add("Saturday");
        }
        String weekdays = "";

        for (int j = 0; j < categorylist.size(); j++) {

            if (weekdays.isEmpty()) {

                weekdays = categorylist.get(j);
            } else {

                weekdays = weekdays + "," + categorylist.get(j);
            }

        }
        Log.e("WeekDaysList", weekdays);

        if (TextUtils.isEmpty(strSelectedDoId)) {
            cancel = true;
        } else if (TextUtils.isEmpty(strDate)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_date), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(endDate)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_date), Toast.LENGTH_SHORT).show();
        } else if (hoursPos <= 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_hours), Toast.LENGTH_SHORT).show();
        } else if (minutesPos <= 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_minute), Toast.LENGTH_SHORT).show();
        } else if (weekdays.trim().length() <= 0) {
            Toast.makeText(getActivity(), "Please select the weekdays", Toast.LENGTH_SHORT).show();
        } else {
            String selectedHr = HOURS_ITEMS[mHourChooser.getSelectedItemPosition() - 1];
            String selectedMin = MINUTES_ITEMS[mMinuteChooser.getSelectedItemPosition() - 1];
            String selectedAmPm = MINUTES_ITEMS[mMinuteChooser.getSelectedItemPosition()];
            String time = selectedHr + ":" + selectedMin + " " + mTimeChooser.getSelectedItem().toString();
            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().setPatientMedicine(
                        strDate, endDate, weekdays, strSelectedDoId, mList_Title.get(mMedicineChooser.getSelectedItemPosition() - 1), doseValue, time, note, details.getUser_seq(),langOpted).enqueue(updateProfileListner);


            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        if (DIALOG_TYPE == 1) {
            String day = dayOfMonth + "";
            String month = monthOfYear + "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            }
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            }
            mStartDate.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        } else if (DIALOG_TYPE == 2) {

            String day = dayOfMonth + "";
            String month = monthOfYear + "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            }
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            }
            mEndDate.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        }
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
