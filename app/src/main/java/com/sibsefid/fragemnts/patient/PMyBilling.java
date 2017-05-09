package com.sibsefid.fragemnts.patient;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragment.doctor.BaseFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PMyBillingHistoryModel;
import com.sibsefid.patient.adapter.PMyBillingAdapter;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 13/10/16.
 */
public class PMyBilling extends BaseFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    public static final String TAG = "MyBillingFragment";
    public TextView noDataText;
    private RecyclerView mRecyclerView = null;
    private PatientActivity activity;
    private ProgressBar progress;
    private LinearLayout linear_l1;
    private TextView mDFromDateSelector;
    private TextView mDToDateSelector;
    private Button mDFilter;
    private String fromDate = "";
    private String toDate = "";
    private UserLoginDetails.LoginDetails details;
    private ArrayList<PMyBillingHistoryModel.PMyBillingBean> my_billingDetailList;
    private PMyBillingAdapter adapter;
    Callback<PMyBillingHistoryModel> myBillingCallback = new Callback<PMyBillingHistoryModel>() {
        @Override
        public void onResponse(Call<PMyBillingHistoryModel> call, Response<PMyBillingHistoryModel> response) {
            if (getActivity() != null) {

                activity.showProgress(false, mRecyclerView, progress);
                my_billingDetailList.clear();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getPMyBillingList() != null && response.body().getPMyBillingList().size() > 0) {
                        if (response.body().getPMyBillingList() != null) {
                            my_billingDetailList = response.body().getPMyBillingList();
                            updateUi();
                        }
                    } else {
                        noDataText.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PMyBillingHistoryModel> call, Throwable t) {
            if (getActivity() != null) {
                activity.showProgress(false, mRecyclerView, progress);
            }
        }
    };
    private View mPopupView;
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
                    PMyBilling.this,
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
        adapter.setpMyBillingArrayList(my_billingDetailList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_billing));
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

        View view = inflater.inflate(R.layout.p_my_billing, container, false);

        initComponents(view);
        details = MyPrefs.getLoginDetails(getActivity());
        if (Utils.isDeviceOnline(getActivity())) {
            activity.showProgress(true, mRecyclerView, progress);
            String timezone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getPatientBilingHistory(details.getUser_seq(), timezone,langOpted).enqueue(myBillingCallback);

        }
        return view;
    }

    private void initComponents(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_MyBills);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        noDataText = (TextView) view.findViewById(R.id.noDataText);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        linear_l1 = (LinearLayout) view.findViewById(R.id.linear_l1);
        mDFilter = (Button) view.findViewById(R.id.mDFilter);
        mDFromDateSelector = (TextView) view.findViewById(R.id.mDFromDateSelector);
        mDToDateSelector = (TextView) view.findViewById(R.id.mDToDateSelector);


        mPopupView = view.findViewById(R.id.mPopupView);


        my_billingDetailList = new ArrayList<>();
        mDFromDateSelector.setOnClickListener(dateOnClickListener);
        mDToDateSelector.setOnClickListener(dateOnClickListener);

        adapter = new PMyBillingAdapter(my_billingDetailList);
        adapter.setOnClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mDFilter.setOnClickListener(this);

    }

    private void filterArrayList() {

        ArrayList<PMyBillingHistoryModel.PMyBillingBean> filterConsultsArrayLists = new ArrayList<>();
        String dateFrom = mDFromDateSelector.getText().toString().trim();
        String dateTo = mDToDateSelector.getText().toString().trim();
        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fromDate = dateFormat.parse(dateFrom);
            toDate = dateFormat.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < my_billingDetailList.size(); i++) {
            String appointmentDateStr = my_billingDetailList.get(i).getDate().toString().trim();
            String currentItem = "";

            Date appointmentDate = null;
            try {
                appointmentDate = dateFormat2.parse(appointmentDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }


            Log.d("appointmentDate---", appointmentDate.toString() + "---" + appointmentDateStr);

            if (fromDate != null && toDate != null && appointmentDate.after(fromDate) && appointmentDate.before(toDate)) {

                filterConsultsArrayLists.add(my_billingDetailList.get(i));


            } else if (fromDate != null && appointmentDate.equals(fromDate)) {

                filterConsultsArrayLists.add(my_billingDetailList.get(i));

            } else if (toDate != null && appointmentDate.equals(toDate)) {

                filterConsultsArrayLists.add(my_billingDetailList.get(i));

            }

            if (fromDate == null && toDate == null && TextUtils.isEmpty(currentItem)) {
                adapter.setpMyBillingArrayList(my_billingDetailList);
                adapter.notifyDataSetChanged();
            } else {
                adapter.setpMyBillingArrayList(filterConsultsArrayLists);
                adapter.notifyDataSetChanged();
            }

        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mPrntRecipt:
                int pos = (int) v.getTag();
                showPopup(mPopupView, pos);

                break;
            case R.id.mDFilter:
                filterArrayList();
                break;
        }
    }


    private void showPopup(View anchor, int pos) {
        final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.recipt_dialog, null);


        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(popupView);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);

        PMyBillingHistoryModel.PMyBillingBean pMyBillingBean = my_billingDetailList.get(pos);

        TextView mStatus = (TextView) popupView.findViewById(R.id.mStatus);
        TextView mDate = (TextView) popupView.findViewById(R.id.mDate);
        TextView mTime = (TextView) popupView.findViewById(R.id.mTime);
        TextView mDName = (TextView) popupView.findViewById(R.id.mDName);
        TextView mFee = (TextView) popupView.findViewById(R.id.mDoctorFee);
        TextView mPaymentBy = (TextView) popupView.findViewById(R.id.mPaymentBy);

        mStatus.setText(getActivity().getResources().getString(R.string.appointment_status) + " " + pMyBillingBean.getStatus());
        mDate.setText(getActivity().getResources().getString(R.string.date_) + " " + pMyBillingBean.getAppointdate());
        mTime.setText(getActivity().getResources().getString(R.string.time_) + " " + pMyBillingBean.getTime());
        mDName.setText(getActivity().getResources().getString(R.string.doctor_name) + " " + pMyBillingBean.getDocterName());
        mFee.setText(getActivity().getResources().getString(R.string.fee) + " " + pMyBillingBean.getTotalfees());
        mPaymentBy.setText(getActivity().getResources().getString(R.string.payment_by) + " " + pMyBillingBean.getPaymentBy());


        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


}
