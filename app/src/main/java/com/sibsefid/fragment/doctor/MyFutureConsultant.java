package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.FrgChatWindow;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.MyFutureConsultantAdapter;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.DPastConsultModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.model.quickbolx_model.CustomQB_UserModel;
import com.sibsefid.quickbloxchat.CallingMainActivity;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 7/9/16.
 */
public class MyFutureConsultant extends BaseFragment implements MyFutureConsultantAdapter.OnItemClickListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    public static final String TAG = "My Future Consultant";
    int positionClicked;
    private RecyclerView mRecyclerView_myFutureConsultant = null;
    private LinearLayout lin;
    private DoctorActivity activity;
    private UserLoginDetails.LoginDetails details;
    private ArrayList<DPastConsultModel.DPastConsults> consultsArrayList = new ArrayList<>();
    private MyFutureConsultantAdapter myAdapter;
    String currentStatusSelected;
    private View mProgressView;
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
    Callback<BookingSummeryModelFromServer> setDoctorCallback = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        CreateAudioVideoChat(positionClicked);
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
    AdapterView.OnItemSelectedListener mStatusSpinerListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String currentItem = spinner_Status.getSelectedItem().toString();
            currentStatusSelected =mListStatus.get(i);
            updateAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private List<String> mListStatus = null;
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
                    MyFutureConsultant.this,
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
        //activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_feture_consults));
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

        View view = inflater.inflate(R.layout.fragment_myfutureconsultant, container, false);
        initComponents(view);
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            TimeZone tz = TimeZone.getDefault();
            String current_Time_Zone = (TimeZone.getTimeZone(tz.getID()).getDisplayName(false, TimeZone.SHORT));
            try {
                current_Time_Zone = Utils.getTimeZoneDifference();
            } catch (Exception e) {

            }

            Log.d("Time zone", "=" + current_Time_Zone);
            if (Utils.isDeviceOnline(getActivity())) {
                String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getDFutureCosults(details.getUser_seq(), current_Time_Zone,selLangToSend).enqueue(detailsCallback);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }
        return view;

    }

    private void initComponents(View view) {

        mDFromDateSelector = (TextView) view.findViewById(R.id.mDFromDateSelector);
        mDToDateSelector = (TextView) view.findViewById(R.id.mDToDateSelector);
        spinner_Status = (Spinner) view.findViewById(R.id.spinner_Status);
        mDFilterBtn = (Button) view.findViewById(R.id.mDFilter);
        mProgressView = view.findViewById(R.id.progress);

        myAdapter = new MyFutureConsultantAdapter(consultsArrayList, MyFutureConsultant.this);
        mRecyclerView_myFutureConsultant = (RecyclerView) view.findViewById(R.id.recycle_view);
        lin = (LinearLayout) view.findViewById(R.id.lin);
        mRecyclerView_myFutureConsultant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView_myFutureConsultant.setAdapter(myAdapter);
        mListStatus = new ArrayList<>();
        mListStatus.add(getResources().getString(R.string.defaults));
        mListStatus.add(getResources().getString(R.string.all));
        spinner_Status.setAdapter(new SpinnerAdapter(activity, 0, mListStatus));
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

        ArrayList<DPastConsultModel.DPastConsults> filterConsultsArrayLists = new ArrayList<>();
        String dateFrom = mDFromDateSelector.getText().toString().trim();
        String dateTo = mDToDateSelector.getText().toString().trim();
        if(TextUtils.isEmpty(dateFrom)){

            Toast.makeText(getActivity(),"Please enter the from date",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(dateTo)){
            Toast.makeText(getActivity(),"Please enter the to date",Toast.LENGTH_SHORT).show();
        }else {
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
                if (appointmentDate.after(fromDate) && appointmentDate.before(toDate)) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                } else if (appointmentDate.equals(fromDate)) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                } else if (appointmentDate.equals(toDate)) {
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

            lin.setVisibility(show ? View.GONE : View.VISIBLE);
            lin.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lin.setVisibility(show ? View.GONE : View.VISIBLE);
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
            lin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.mChatType:
                positionClicked = position;
                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().setDoctorCall(consultsArrayList.get(position).getAppointid(),selLangToSend).enqueue(setDoctorCallback);
                } else {
                    showProgress(false);
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void CreateAudioVideoChat(int pos) {
        if (Utils.isDeviceOnline(getActivity(), true)) {
            DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);

            if (!dPastConsults.getAppointmentMode().toLowerCase().equalsIgnoreCase("chat")) {
                String qbConferenceType = null;
                if (dPastConsults.getAppointmentMode().toLowerCase().equalsIgnoreCase("audio")) {
                    qbConferenceType = "audio";
                } else {
                    qbConferenceType = "video";
                }

                ArrayList<CustomQB_UserModel> opponents = new ArrayList<CustomQB_UserModel>();
                CustomQB_UserModel info = new CustomQB_UserModel();
                info.setName(dPastConsults.getPatientName());
                info.setId(Integer.parseInt(dPastConsults.getQuickBloxId()));
                info.setEmail(dPastConsults.getEmail());
                opponents.add(info);
                Log.e("optId=", opponents.get(0).getId() + "");
                Map<String, String> userInfo = new HashMap<>();
                userInfo.put("any_custom_data", "some data");
                userInfo.put("opponent_id", opponents.get(0).getId() + "");
                userInfo.put("opponent_email", opponents.get(0).getEmail() + "");
                Intent calling = new Intent(getActivity(), CallingMainActivity.class);
                calling.putExtra("qbConferenceType", qbConferenceType);
                calling.putExtra("calling", "s_calling");
                calling.putExtra("appointment_id", dPastConsults.getAppointid());
                calling.putExtra("patient_id", dPastConsults.getPatientsId());
                calling.putExtra("opponents", opponents);
                startActivity(calling);


            } else {
                Utils.callFragmentForAddDoctor(activity, new FrgChatWindow().newInstance(Integer.parseInt(dPastConsults.getQuickBloxId()), dPastConsults.getPatientName(), dPastConsults.getEmail()), FrgChatWindow.TAG);
            }


        }
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
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }

}

