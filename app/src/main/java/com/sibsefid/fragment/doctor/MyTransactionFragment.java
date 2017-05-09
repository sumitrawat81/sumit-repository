package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.MyTransactionAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.MyTransactionModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ubuntu on 7/9/16.
 */
public class MyTransactionFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "MyTransactionFragment";
    public TextView noDataText;
    private RecyclerView mRecyclerView_MyTransaction = null;
    private UserLoginDetails.LoginDetails details;
    private DoctorActivity activity;
    private ProgressBar progress;
    private LinearLayout linear_l1;
    private TextView mDFromDateSelector;
    private TextView mDToDateSelector;
    private Button mDFilter;
    private String fromDate = "";
    private String toDate = "";
    private ArrayList<MyTransactionModel.DataBean> my_transactionDetail;
    Callback<MyTransactionModel> detailsCallback = new Callback<MyTransactionModel>() {
        @Override
        public void onResponse(Call<MyTransactionModel> call, Response<MyTransactionModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                my_transactionDetail.clear();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            my_transactionDetail = response.body().getData();

                            updateUi();
                        }
                    } else {
                        noDataText.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<MyTransactionModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private int DIALOG_TYPE = 0; // 1 for from date and 2 for to date
    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (mDFromDateSelector == view) {
                DIALOG_TYPE = 1;
            } else if (mDToDateSelector == view) {
                DIALOG_TYPE = 2;
            }
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    MyTransactionFragment.this,
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

    private void updateUi() {

        noDataText.setVisibility(View.GONE);
        mRecyclerView_MyTransaction.setAdapter(new MyTransactionAdapter(my_transactionDetail));


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_trasation));
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

        View view = inflater.inflate(R.layout.fragment_mytransaction, container, false);
        /* initialize components By This Method */
        initComponents(view);
        details = MyPrefs.getLoginDetails(getActivity());
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String timezone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().GetDoctorMyTransaction(details.getUser_seq(), timezone, fromDate, toDate,langOpted).enqueue(detailsCallback);

        }
        return view;
    }

    private void initComponents(View view) {
        mRecyclerView_MyTransaction = (RecyclerView) view.findViewById(R.id.recycle_MyTransaction);
        mRecyclerView_MyTransaction.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        noDataText = (TextView) view.findViewById(R.id.noDataText);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        linear_l1 = (LinearLayout) view.findViewById(R.id.linear_l1);
        mDFilter = (Button) view.findViewById(R.id.mDFilter);
        mDFromDateSelector = (TextView) view.findViewById(R.id.mDFromDateSelector);
        mDToDateSelector = (TextView) view.findViewById(R.id.mDToDateSelector);
        my_transactionDetail = new ArrayList<>();
        mDFromDateSelector.setOnClickListener(dateOnClickListener);
        mDToDateSelector.setOnClickListener(dateOnClickListener);
        mDFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isDeviceOnline(getActivity())) {

                    String timezone = Utils.getTimeZoneDifference();
                    if (mDFromDateSelector.getText().toString().trim().length() > 0 && mDToDateSelector.getText().toString().trim().length() > 0) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().GetDoctorMyTransaction(details.getUser_seq(), timezone, mDFromDateSelector.getText().toString().trim(), mDToDateSelector.getText().toString().trim(),langOpted).enqueue(detailsCallback);
                    } else {
                        Toast.makeText(getActivity(), "Please Select The Date", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
            linear_l1.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
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
            linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
            mDFromDateSelector.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        } else if (DIALOG_TYPE == 2) {

            String day = dayOfMonth + "";
            String month = monthOfYear + "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            }
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            }
            mDToDateSelector.setText(day.trim() + "/" + (month.trim()) + "/" + year);
        }
    }
}
