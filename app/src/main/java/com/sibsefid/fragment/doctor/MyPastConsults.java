package com.sibsefid.fragment.doctor;

/**
 * Created by ubuntu on 7/9/16.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.AppointmentStatusSpAd;
import com.sibsefid.doctor.adapter.MyPastConsultsAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.AppointmentStatusModel;
import com.sibsefid.model.doctor.DPastConsultModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.ApiUrls;
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
 * Created by ubuntu on 7/9/16.
 */
public class MyPastConsults extends BaseFragment implements
        DatePickerDialog.OnDateSetListener, View.OnClickListener {

    public static final String TAG = "My Past Consultant";
    private RecyclerView mRecyclerView_myPastConsults = null;
    private DoctorActivity activity;
    String currentStatusSelected;
    private UserLoginDetails.LoginDetails details;
    private View mProgressView;
    Callback<BookingSummeryModelFromServer> saveCummunicationNote = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                    } else {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<BookingSummeryModelFromServer> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private ArrayList<DPastConsultModel.DPastConsults> consultsArrayList = new ArrayList<>();
    private MyPastConsultsAdapter myAdapter;
    Callback<DPastConsultModel> detailsCallback = new Callback<DPastConsultModel>() {
        @Override
        public void onResponse(Call<DPastConsultModel> call, Response<DPastConsultModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDpastcosults() != null && response.body().getDpastcosults().size() > 0) {
                        if (response.body().getDpastcosults() != null) {
                            consultsArrayList = response.body().getDpastcosults();
                            updateAdapter();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<DPastConsultModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private ArrayList<AppointmentStatusModel.DataBean.TableBean> mListAppointmentStatus = new ArrayList<>();
    private TextView mPatientName;
    private TextView mEpisodeDate;
    private TextView mPhysicianName;
    private EditText mCommunicationNote;
    private TextView mSignature;
    private TextView mSignatureDate;
    private TextView mSignatureDate1;
    private Button mSaveBtn;
    private Button mCancelBtn;
    private TextView mDFromDateSelector;
    private TextView mDToDateSelector;
    View.OnClickListener filterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filterArrayList();
        }
    };
    private Button mDFilterBtn;
    private Spinner spinner_Status;
    Callback<AppointmentStatusModel> getAppointmentStatusCassBack = new Callback<AppointmentStatusModel>() {
        @Override
        public void onResponse(Call<AppointmentStatusModel> call, Response<AppointmentStatusModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().getTable() != null && response.body().getData().getTable().size() > 0) {
                        mListAppointmentStatus = new ArrayList<>();
                        mListAppointmentStatus = response.body().getData().getTable();
                        AppointmentStatusSpAd appointmentStatusSpAd = new AppointmentStatusSpAd(getActivity(), android.R.layout.simple_dropdown_item_1line, mListAppointmentStatus);
                        appointmentStatusSpAd.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinner_Status.setAdapter(appointmentStatusSpAd);

                    }
                }
            }
        }

        @Override
        public void onFailure(Call<AppointmentStatusModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    AdapterView.OnItemSelectedListener mStatusSpinerListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            currentStatusSelected =mListAppointmentStatus.get(i).getStatus();
            updateAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
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
                    MyPastConsults.this,
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        /*activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_past_consults));
        activity.forwardShowImg();*/
        details = MyPrefs.getLoginDetails(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypastconsults, container, false);
        initComponents(view);
        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            showProgress(true);
            String current_Time_Zone = Utils.getTimeZoneDifference();

            RestAdapter.getAdapter().getDPastCosults(details.getUser_seq(), current_Time_Zone,selLangToSend).enqueue(detailsCallback);
            RestAdapter.getAdapter().acceptsAppointmentStatusList(selLangToSend).enqueue(getAppointmentStatusCassBack);
        }

        return view;
    }

    private void initComponents(View view) {
        mDFromDateSelector = (TextView) view.findViewById(R.id.mDFromDateSelector);
        mDToDateSelector = (TextView) view.findViewById(R.id.mDToDateSelector);
        spinner_Status = (Spinner) view.findViewById(R.id.spinner_Status);
        mDFilterBtn = (Button) view.findViewById(R.id.mDFilter);
        mProgressView = view.findViewById(R.id.progress);
        mRecyclerView_myPastConsults = (RecyclerView) view.findViewById(R.id.recycle_MyPastConsultant);
        myAdapter = new MyPastConsultsAdapter(consultsArrayList);
        myAdapter.setOnClickListener(this);
        mRecyclerView_myPastConsults.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView_myPastConsults.setAdapter(myAdapter);

        mListAppointmentStatus = new ArrayList<>();
        AppointmentStatusSpAd appointmentStatusSpAd = new AppointmentStatusSpAd(getActivity(), android.R.layout.simple_dropdown_item_1line, mListAppointmentStatus);
        appointmentStatusSpAd.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_Status.setAdapter(appointmentStatusSpAd);

        mDFromDateSelector.setOnClickListener(dateOnClickListener);
        mDToDateSelector.setOnClickListener(dateOnClickListener);
        mDFilterBtn.setOnClickListener(filterClick);
        spinner_Status.setOnItemSelectedListener(mStatusSpinerListner);
    }

    private void updateAdapter() {
        myAdapter.setmList_MyFutureConsultant(consultsArrayList);
        myAdapter.notifyDataSetChanged();
    }

    private void filterArrayList() {
        String dateFrom = mDFromDateSelector.getText().toString().trim();
        String dateTo = mDToDateSelector.getText().toString().trim();

        if(TextUtils.isEmpty(dateFrom)){

            Toast.makeText(getActivity(),"Please enter the from date",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(dateTo)){
            Toast.makeText(getActivity(),"Please enter the to date",Toast.LENGTH_SHORT).show();
        }else {
            ArrayList<DPastConsultModel.DPastConsults> filterConsultsArrayLists = new ArrayList<>();

            Date fromDate = null;
            Date toDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                fromDate = dateFormat.parse(dateFrom);
                toDate = dateFormat.parse(dateTo);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            if(fromDate.after(toDate)){
                Toast.makeText(getActivity(),"from date should not be greater than to date!!",Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < consultsArrayList.size(); i++) {
                String appointmentDateStr = consultsArrayList.get(i).getDate().toString().trim();
                Date appointmentDate = null;
                try {
                    appointmentDate = dateFormat.parse(appointmentDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
                if (appointmentDate.after(fromDate) && appointmentDate.before(toDate) && consultsArrayList.get(i).getStatus().equalsIgnoreCase(currentStatusSelected)) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                } else if (appointmentDate.equals(fromDate) && consultsArrayList.get(i).getStatus().equalsIgnoreCase(currentStatusSelected)) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                } else if (appointmentDate.equals(toDate) && consultsArrayList.get(i).getStatus().equalsIgnoreCase(currentStatusSelected)) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                }
                myAdapter.setmList_MyFutureConsultant(filterConsultsArrayLists);
                myAdapter.notifyDataSetChanged();
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView_myPastConsults.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView_myPastConsults.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView_myPastConsults.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRecyclerView_myPastConsults.setVisibility(show ? View.GONE : View.VISIBLE);
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
        String day=String.valueOf(dayOfMonth);
        String month=String.valueOf(monthOfYear);

        if(dayOfMonth<10){
            day="0"+dayOfMonth;
        }
        if(monthOfYear<10){
            month="0"+monthOfYear;
        }
        if (DIALOG_TYPE == 1) {
            mDFromDateSelector.setText(day + "/" + (month) + "/" + year);
        } else if (DIALOG_TYPE == 2) {
            mDToDateSelector.setText(day + "/" + (month) + "/" + year);
        } else if (DIALOG_TYPE == 3) {
            mSignatureDate.setText(month + "/" + (day) + "/" + year);
        } else if (DIALOG_TYPE == 4) {
            mSignatureDate1.setText(month + "/" + (day) + "/" + year);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_CommnunicationNote:
                int pos = (int) v.getTag();
                showCustomDialog(pos);
                break;
            case R.id.button_PhysicanOrder:
                int poss = (int) v.getTag();
                showCustomDialog1(poss);
                break;

            case R.id.button_ClinicalDashboard:
                int clinical_pos = (int) v.getTag();
                String url;
                String timezone = Utils.getTimeZoneDifference();
                url = ApiUrls.BASE_URL_WebView+"ClinicalDashBoardDoctor.aspx?appId=" + consultsArrayList.get(clinical_pos).getAppointid() + "&doctorId=" + details.getUser_seq()
                        + "&patId=" + consultsArrayList.get(clinical_pos).getPatientsId() + "&TimeZone=" + timezone;
                if (activity != null) {
                    Utils.callFragmentForAddDoctor(activity, new DoctorClinicalDashboard().newInstance(url), DoctorClinicalDashboard.TAG);
                }

                break;
        }

    }

    public void showCustomDialog(int pos) {
        final DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_communication_note);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        mPatientName = (TextView) dialog.findViewById(R.id.mPatientName);
        mEpisodeDate = (TextView) dialog.findViewById(R.id.mEpisodeDate);
        mPhysicianName = (TextView) dialog.findViewById(R.id.mPhysicianName);
        mCommunicationNote = (EditText) dialog.findViewById(R.id.mCommunicationNote);
        mSignature = (EditText) dialog.findViewById(R.id.mSignature);
        mSignatureDate = (TextView) dialog.findViewById(R.id.mSignatureDate);
        mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);
        mCancelBtn = (Button) dialog.findViewById(R.id.mCancelBtn);
        mPatientName.setText(dPastConsults.getPatientName());
        mSignature.setText(dPastConsults.getPatientName());
        mEpisodeDate.setText(dPastConsults.getDate());
        mPhysicianName.setText(details.getTitle_nm() + " " + details.getFamily_name() + " " + details.getGiven_name());
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isDeviceOnline(getActivity())) {

                    if (TextUtils.isEmpty(mCommunicationNote.getText().toString())){
                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_communication_fields), getActivity());
                    }
                    else if(TextUtils.isEmpty(mSignatureDate.getText().toString())) {
                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_signature_date), getActivity());
                    }
                        else{
                        showProgress(true);
                        dialog.dismiss();
                        String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().setCommunicationNote(dPastConsults.getAppointid(), details.getUser_seq(),
                                dPastConsults.getPatientsId(), mCommunicationNote.getText().toString().trim(), mSignatureDate.getText().toString().trim(),selLangToSend).enqueue(saveCummunicationNote);
                    }
                }else {
                    Utils.showCustomToast(getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), getActivity());
                }

            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mSignatureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG_TYPE = 3;
                datePickerOpen();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showCustomDialog1(int pos) {
        final DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_physician_order);

        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        Button mSaveBtn1 = (Button) dialog.findViewById(R.id.mSaveBtn);
        Button mCancelBtn1 = (Button) dialog.findViewById(R.id.mCancelBtn);


        TextView mPatientName1 = (TextView) dialog.findViewById(R.id.mPatientName);
        LinearLayout botton_layout = (LinearLayout) dialog.findViewById(R.id.botton_layout);

        TextView mEpisodeDate1 = (TextView) dialog.findViewById(R.id.mEpisodeDate);
        TextView mOrderDate = (TextView) dialog.findViewById(R.id.mOrderDate);
        TextView mPhysicianName1 = (TextView) dialog.findViewById(R.id.mPhysicianName);
        final EditText mCommunicationNote1 = (EditText) dialog.findViewById(R.id.mEdtSummery);
        TextView mSignature1 = (TextView) dialog.findViewById(R.id.mSignature);
        mSignatureDate1 = (TextView) dialog.findViewById(R.id.mSignatureDate);

        botton_layout.setVisibility(View.VISIBLE);


        mPatientName1.setText(dPastConsults.getPatientName());
        mSignature1.setText(dPastConsults.getPatientName());
        mEpisodeDate1.setText(dPastConsults.getDate());
        mOrderDate.setText(dPastConsults.getDate());
        mPhysicianName1.setText(details.getTitle_nm() + " " + details.getFamily_name() + " " + details.getGiven_name());

        mSaveBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isDeviceOnline(getActivity())) {
                    if (TextUtils.isEmpty(mCommunicationNote1.getText().toString())){

                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_communication_fields), getActivity());
                    }
                    else if (TextUtils.isEmpty(mSignatureDate1.getText().toString())) {
                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_signature_date), getActivity());
                    }else{
                        dialog.dismiss();
                        showProgress(true);
                        String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().SetPhysicianOrder(dPastConsults.getAppointid(), details.getUser_seq(),
                                dPastConsults.getPatientsId(), mCommunicationNote1.getText().toString().trim(), mSignatureDate1.getText().toString().trim(),selLangToSend).enqueue(saveCummunicationNote);
                    }
                }else{
                    Utils.showCustomToast(getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), getActivity());
                }

            }
        });

        mSignatureDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIALOG_TYPE = 4;
                datePickerOpen();
            }
        });
        mCancelBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MyPastConsults.this,
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


}
