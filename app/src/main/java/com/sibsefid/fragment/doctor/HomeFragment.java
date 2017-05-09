package com.sibsefid.fragment.doctor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.HomeAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.myappointment.PMyAppointment;
import com.sibsefid.interfaces.NotificationReceiverInterface;
import com.sibsefid.model.doctor.DPastConsultModel;
import com.sibsefid.model.doctor.HomeGridModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.RecyclerItemClickListener;
import com.sibsefid.util.Utils;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 8/9/16.
 */

public class HomeFragment extends BaseFragment implements NotificationReceiverInterface {

    public static final String TAG = "HomeFragment";

    /*Component Prototype Declaration Here*/
    private DoctorActivity mDoctorActivity = null;
    private RecyclerView mRecyclerView_HomeGrid = null;
    private RecyclerView.LayoutManager mRecyclerLayoutManager_GridLayout = null;
    private ImageView mProfileImageView;
    private TextView mDoctorName;
    private TextView notification_count_txt;
    private TextView txtMsgCount;
    private TextView mAppointmentRemainingTime;
    private HomeAdapter mHomeAdapter = null;
    private List<HomeGridModel> mList_HomeGridModels = null;
    private String Name[] = null;
    private ArrayList<DPastConsultModel.DPastConsults> consultsArrayList = new ArrayList<>();
    private UserLoginDetails.LoginDetails details;
    private int Image[] = {R.mipmap.doctor, R.mipmap.account, R.mipmap.calendar_home, R.mipmap.my_detail, R.mipmap.my_message, R.mipmap.my_room, R.mipmap.past, R.mipmap.transction, R.mipmap.report, R.mipmap.profile_icon, R.mipmap.billing};

    long timeInMillisecond;
    private Runnable notification;

    Callback<DPastConsultModel> detailsCallback = new Callback<DPastConsultModel>() {
        @Override
        public void onResponse(Call<DPastConsultModel> call, Response<DPastConsultModel> response) {
            if (getActivity() != null) {

                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDpastcosults() != null && response.body().getDpastcosults().size() > 0) {
                        if (response.body().getDpastcosults() != null) {
                            consultsArrayList = response.body().getDpastcosults();
                            String date=consultsArrayList.get(0).getDate();
                            String time=consultsArrayList.get(0).getTime();
                            UpdateTheRemainingTime(date,time);
                        }
                    }
                    else{
                        mAppointmentRemainingTime.setVisibility(View.GONE);
                    }
                }
                else{
                  //  mAppointmentRemainingTime.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onFailure(Call<DPastConsultModel> call, Throwable t) {
            if (getActivity() != null) {

            }
        }
    };

    private void UpdateTheRemainingTime(String date, String time) {
        timeInMillisecond=Utils.getTimeDifferenceInMilliSec(date,time);
        primaryTimeProgressUpdater();
        mAppointmentRemainingTime.setVisibility(View.VISIBLE);

    }

    private void primaryTimeProgressUpdater () {

        notification = new Runnable() {
            public void run() {
                if(timeInMillisecond>0) {
                    primaryTimeProgressUpdater();
                    getAudioLengthInString();
                }
            }
        };

        mAppointmentRemainingTime.postDelayed(notification,1000);
    }

    private String getAudioLengthInString () {

        long seconds = timeInMillisecond / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        hours=hours%24;
        minutes=minutes%60;
        seconds=seconds%60;

        Log.d("tag", "minutes and seconds value is: " + minutes + ":" + seconds);
        NumberFormat f = new DecimalFormat("00");
        String remainingTime="Remaning Time:";
        if(days==1){
            remainingTime =remainingTime+" "+ f.format(days) + "day" +" "+ f.format(hours)+ ":" +f.format(minutes) + ":" + f.format(seconds);
        }else if(days>1){
            remainingTime =remainingTime+" "+ f.format(days) + "days" +" "+ f.format(hours)+ ":" + f.format(minutes) + ":" + f.format(seconds);
        }else{
            remainingTime =remainingTime+" "+ f.format(hours)+":"+ f.format(minutes) + ":" + f.format(seconds);
        }

        mAppointmentRemainingTime.setText(remainingTime);
        timeInMillisecond-=1000;
        return remainingTime;
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        details = MyPrefs.getLoginDetails(getActivity());
        EventBus.getDefault().register(this);

        String Name2[] = {getActivity().getResources().getString(R.string.my_profile), getResources().getString(R.string.my_account),
                getResources().getString(R.string.my_availability), getResources().getString(R.string.my_patient),
                getResources().getString(R.string.my_message), getResources().getString(R.string.check_my_room),
                getResources().getString(R.string.my_appointment),
                getResources().getString(R.string.my_trasation), getResources().getString(R.string.report_a_problem),
                getResources().getString(R.string.my_profile_would_apear), getResources().getString(R.string.my_personal_note)};
        Name = Name2;

        initializeComponents(view);
        mDoctorName.setText(details.getTitle_nm() + " " + details.getFamily_name() + " " + details.getGiven_name());
        if (details.getPhoto().length() > 0) {
            try {
                Picasso.with(getActivity())
                        .load(details.getPhoto())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .into(mProfileImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Utils.isDeviceOnline(getActivity())) {

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


    /* Components Initialization Here */
    public void initializeComponents(View view) {

        mList_HomeGridModels = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            HomeGridModel mHomeGridModel = new HomeGridModel();
            mHomeGridModel.setName(Name[i]);
            mHomeGridModel.setImage(Image[i]);
            mList_HomeGridModels.add(mHomeGridModel);
        }
        mDoctorActivity = (DoctorActivity) getActivity();

        mProfileImageView = (ImageView) view.findViewById(R.id.mProfileImageView);
        txtMsgCount = (TextView) view.findViewById(R.id.txtMsgCount);
        mAppointmentRemainingTime = (TextView) view.findViewById(R.id.mAppointmentRemainingTime);
        notification_count_txt = (TextView) view.findViewById(R.id.notification_count_txt);
        mDoctorName = (TextView) view.findViewById(R.id.mDoctorName);
        mRecyclerView_HomeGrid = (RecyclerView) view.findViewById(R.id.recycler_home_grid);
        mRecyclerLayoutManager_GridLayout = new GridLayoutManager(getActivity(), 3);
        mHomeAdapter = new HomeAdapter(mDoctorActivity, mList_HomeGridModels);
        mRecyclerView_HomeGrid.setLayoutManager(mRecyclerLayoutManager_GridLayout);
        mRecyclerView_HomeGrid.setAdapter(mHomeAdapter);

        txtMsgCount.setText(MyPrefs.getUserMsgCount(getActivity()).trim());
        notification_count_txt.setText(MyPrefs.getUserNotificationCount(mDoctorActivity));

        mRecyclerView_HomeGrid.addOnItemTouchListener(new RecyclerItemClickListener(mDoctorActivity, mRecyclerView_HomeGrid, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new MyProfileFragment(), MyProfileFragment.TAG);
                        break;
                    case 1:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new AccountDetailFragment(), AccountDetailFragment.TAG);
                        break;
                    case 2:
                        Bundle b = new Bundle();
                        b.putString("from", "HomeFragment");
                        Fragment fragment = new DoctorAvailabilityFragment();
                        fragment.setArguments(b);
                        Utils.callFragmentForAddDoctor(mDoctorActivity, fragment, DoctorAvailabilityFragment.TAG);
                        break;

                    case 3:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new MyPatientsFragment(), MyPatientsFragment.TAG);
                        break;

                    case 4:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new MyMessageFragment(), MyMessageFragment.TAG);
                        break;
                    case 6:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new DoctorMyAppointments(), PMyAppointment.TAG);
                        break;

                    case 7:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new MyTransactionFragment(), MyTransactionFragment.TAG);
                        break;
                    case 8:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new ReportProblemFragment(), ReportProblemFragment.TAG);
                        break;
                    case 9:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new DoctorProfileFragment(), DoctorProfileFragment.TAG);
                        break;
                    case 10:
                        Utils.callFragmentForAddDoctor(mDoctorActivity, new MyPersonalNoteFragment(), MyPersonalNoteFragment.TAG);
                        break;
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }


    @Override
    public void onResume() {

        MyMessageFragment.notificationReceiverInterface = this;
        txtMsgCount.setText(MyPrefs.getUserMsgCount(getActivity()).trim());
        notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()).trim());
        super.onResume();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Subscribe
    public void onEvent(RegisterResponseModel objects) {

        if (getActivity() != null) {
            txtMsgCount.setText(objects.getMsgCount());
        }

    }

    @Subscribe
    public void onEvent(UserLoginDetails.LoginDetails model) {

        if (getActivity() != null) {
            details = MyPrefs.getLoginDetails(getActivity());
            mDoctorName.setText(details.getTitle_nm() + " " + details.getFamily_name() + " " + details.getGiven_name());

            try {
                Picasso.with(getActivity())
                        .load(details.getPhoto())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .into(mProfileImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void getNotification() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()));

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onEvent(RemoteMessage remoteMessage) {

        if (getActivity() != null) {
            int count = Integer.valueOf(MyPrefs.getUserNotificationCount(getActivity()).trim());
            count = count + 1;
            MyPrefs.saveUserNotiicationCountl(getActivity(), String.valueOf(count));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()));

                }
            });

        }

    }

}
