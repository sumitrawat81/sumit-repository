package com.sibsefid.fragemnts.patient.tracker;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.PMedicalDevices;
import com.sibsefid.fragemnts.patient.myresult.MyResultFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.AddManulyBloodPressureModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PCheckBloodPressure extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private LinearLayout watch_video_layout;
    private LinearLayout add_result_layout;
    private LinearLayout see_previous_result;
    private LinearLayout enquiry;
    private EditText mSystolicValue;
    private EditText mDiastolicValue;
    private EditText mPulseValue;
    private TextView mTime;
    private TextView mDate;
    private Button mSaveBtn;
    private PatientActivity activity;
    private Spinner mTimeChooser;
    private String[] Am_PM_ITEMS = new String[2];
    private String fromAmPm;
    private ProgressBar progress;
    private UserLoginDetails.LoginDetails details;
    private Dialog dialog;


    Callback<AddManulyBloodPressureModel> detailsCallback = new Callback<AddManulyBloodPressureModel>() {
        @Override
        public void onResponse(Call<AddManulyBloodPressureModel> call, Response<AddManulyBloodPressureModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {

                }
                dialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<AddManulyBloodPressureModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                dialog.dismiss();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.p_check_blood_pressure, container, false);

        initilzeView(view);
        return view;
    }

    private void initilzeView(View view) {

        watch_video_layout = (LinearLayout) view.findViewById(R.id.watch_video_layout);
        add_result_layout = (LinearLayout) view.findViewById(R.id.add_result_layout);
        see_previous_result = (LinearLayout) view.findViewById(R.id.see_previous_result);
        enquiry = (LinearLayout) view.findViewById(R.id.enquiry);

        Am_PM_ITEMS[0] = getActivity().getResources().getString(R.string.am);
        Am_PM_ITEMS[1] = getActivity().getResources().getString(R.string.pm);

        watch_video_layout.setOnClickListener(this);
        add_result_layout.setOnClickListener(this);
        see_previous_result.setOnClickListener(this);
        enquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.watch_video_layout:
                Utils.CustomToast(getActivity(), v);
                break;

            case R.id.add_result_layout:
                showCustomDialog();
                break;

            case R.id.see_previous_result:
                Utils.callFragmentForAddPatient(activity, new MyResultFragment(), MyResultFragment.TAG);

                break;

            case R.id.enquiry:
                Utils.callFragmentForAddPatient(activity, new PMedicalDevices(), PMedicalDevices.TAG);
                break;
        }
    }

    public void showCustomDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.add_bloodpressure_result_dialog);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        mSystolicValue = (EditText) dialog.findViewById(R.id.mSystolicValue);
        mDiastolicValue = (EditText) dialog.findViewById(R.id.mDiastolicValue);
        mPulseValue = (EditText) dialog.findViewById(R.id.mPulseValue);
        mTime = (TextView) dialog.findViewById(R.id.mTime);
        mTimeChooser = (Spinner) dialog.findViewById(R.id.mTimeGmt);
        mDate = (TextView) dialog.findViewById(R.id.mDate);
        progress = (ProgressBar) dialog.findViewById(R.id.progress);
        mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);
        ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Am_PM_ITEMS);
        AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeChooser.setAdapter(AMPMAdapter);
        AdapterView.OnItemSelectedListener mTimeChooserListner = null;
        mTimeChooser.setOnItemSelectedListener(mTimeChooserListner);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerOpen();
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timePickerOpen();
            }
        });

        mTimeChooserListner = new AdapterView.OnItemSelectedListener() {
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
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isDeviceOnline(getActivity())) {
                    if (!TextUtils.isEmpty(mSystolicValue.getText().toString().trim()) && (!TextUtils.isEmpty(mDate.getText().toString())
                            && (!TextUtils.isEmpty(mTime.getText().toString())) && (!TextUtils.isEmpty(mDiastolicValue.getText().toString()))
                            && (!TextUtils.isEmpty(mPulseValue.getText().toString())))) {
                        showProgress(true);
                        String dt = mDate.getText().toString().trim() + " " + mTime.getText().toString().trim() + " " + mTimeChooser.getSelectedItem().toString();
                        Log.e("dateTime=", dt);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().SetBloodPressureManually(details.getUser_seq(),
                                mSystolicValue.getText().toString().trim(), mDiastolicValue.getText().toString().trim(), mPulseValue.getText().toString().trim(), mDate.getText().toString().trim() + " " + mTime.getText().toString().trim() + " " + mTimeChooser.getSelectedItem().toString(),langOpted).enqueue(detailsCallback);
                    } else {

                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_all_fields), getActivity());
                    }
                } else {

                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void timePickerOpen() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                PCheckBloodPressure.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.enableMinutes(true);
        tpd.setAccentColor(Color.parseColor("#9C27B0"));

        tpd.setTitle("Select the Time");


        //  tpd.setTimeInterval(2, 5, 10);

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PCheckBloodPressure.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setMaxDate(now);
        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(getResources().getString(R.string.date_dailog_title));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;

        String day = dayOfMonth + "";
        String month = monthOfYear + "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        }
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        }
        mDate.setText((month.trim() + "/" + day.trim() + "/" + year));

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


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
            //listMember.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            mTimeChooser.setSelection(1);
        } else {
            mTimeChooser.setSelection(0);
        }
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString;
        mTime.setText(time);
    }
}
