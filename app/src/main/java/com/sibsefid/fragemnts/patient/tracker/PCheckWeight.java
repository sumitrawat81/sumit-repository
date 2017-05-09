package com.sibsefid.fragemnts.patient.tracker;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.PMedicalDevices;
import com.sibsefid.fragemnts.patient.myresult.MyResultFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.SetWeightMannullyModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PCheckWeight extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private LinearLayout watch_video_layout;
    private LinearLayout add_result_layout;
    private LinearLayout see_previous_result;
    private LinearLayout enquiry;
    private PatientActivity activity;
    private EditText mWeight;
    private TextView mDate;
    private Button mSaveBtn;
    private ProgressBar progress;
    private UserLoginDetails.LoginDetails details;

    private Dialog dialog;


    Callback<SetWeightMannullyModel> detailsCallback = new Callback<SetWeightMannullyModel>() {
        @Override
        public void onResponse(Call<SetWeightMannullyModel> call, Response<SetWeightMannullyModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {

                }
                dialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<SetWeightMannullyModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                dialog.dismiss();
            }
        }
    };


    public PCheckWeight() {
        // Required empty public constructor
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pcheck_weight, container, false);
        initilzeView(view);

        return view;
    }

    private void initilzeView(View view) {

        watch_video_layout = (LinearLayout) view.findViewById(R.id.watch_video_layout);
        add_result_layout = (LinearLayout) view.findViewById(R.id.add_result_layout);
        see_previous_result = (LinearLayout) view.findViewById(R.id.see_previous_result);
        enquiry = (LinearLayout) view.findViewById(R.id.enquiry);

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
        dialog.setContentView(R.layout.weight_add_resulut_dialog);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        mWeight = (EditText) dialog.findViewById(R.id.mWeight);
        progress = (ProgressBar) dialog.findViewById(R.id.progress);
        mDate = (TextView) dialog.findViewById(R.id.mDate);

        mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerOpen();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isDeviceOnline(getActivity())) {
                    if (!TextUtils.isEmpty(mWeight.getText().toString().trim()) && (!TextUtils.isEmpty(mDate.getText().toString()))) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().setWeightMannully(details.getUser_seq(),
                                mWeight.getText().toString().trim(), mDate.getText().toString().trim(),langOpted).enqueue(detailsCallback);
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

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PCheckWeight.this,
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
}
