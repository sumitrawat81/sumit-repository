package com.sibsefid;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.ChangePassword;
import com.sibsefid.fragment.doctor.HomeFragment;
import com.sibsefid.model.doctor.AppointmentStatusModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.TitleModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.quickbloxchat.chat.PrivateChatImpl;
import com.sibsefid.services.AppService;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 3/9/16.
 */
public class DoctorActivity extends AppCompatActivity implements OnDataNotify, View.OnClickListener {


    public Handler mHandler;

    Callback<AppointmentStatusModel> pMessageModelCallback = new Callback<AppointmentStatusModel>() {
        @Override
        public void onResponse(Call<AppointmentStatusModel> call, Response<AppointmentStatusModel> response) {
            if (DoctorActivity.this != null) {
                //showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        mHandler.sendEmptyMessage(1);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<AppointmentStatusModel> call, Throwable t) {

        }
    };

    private void logoutFunction() {

        Utils.dismissProDialog();
        Utils.logout(DoctorActivity.this, 0);
        if (QBChatService.isInitialized()) {
            try {
                AppService.chatService = null;
                QBRTCClient.getInstance(DoctorActivity.this).destroy();
                AppService.rtcClient = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent servc = new Intent(DoctorActivity.this, AppService.class);
        stopService(servc);
        AppService.chatService = null;
        AppService.rtcClient = null;
        PrivateChatImpl.chat = null;
        PrivateChatImpl.chatManager = null;
        AppService.currentSession = null;
        FrgChatWindow.chatCallBack = null;
        AppService.sInstance = null;
        Intent i = new Intent(DoctorActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private TextView mTitle;
    private View mMenuBtn1;
    private ImageView mBackBtn, mLogo;
    private ArrayList<TitleModel.TitleBean> titleBeenArrayList = new ArrayList<>();
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    Callback<TitleModel> titleModelCallback = new Callback<TitleModel>() {
        @Override
        public void onResponse(Call<TitleModel> call, Response<TitleModel> response) {
            if (DoctorActivity.this != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getTitle() != null && response.body().getTitle().size() > 0) {
                        if (response.body().getTitle() != null) {
                            titleBeenArrayList = response.body().getTitle();
                            mList_Title.clear();
                            for (int i = 0; i < response.body().getTitle().size(); i++) {
                                mList_Title.add(response.body().getTitle().get(i).getDetail_code_nm());
                                mList_Title_ID.add(response.body().getTitle().get(i).getDetail_code());
                            }

                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<TitleModel> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private QBChatMessage qbChatMessage;
    private TextView msgQBText;
    private UserLoginDetails.LoginDetails details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (Utils.getuserSeletedLanague(this) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(this);
        } else {

            Utils.forceLTRIfSupported(this);
        }*/

        AppService.onDataNotify = this;
        this.setContentView(R.layout.activity_doctor_home);

        String selLang=MyPrefs.getUserSelectedLanguage(DoctorActivity.this);
        Utils.chagesLocatiolization(DoctorActivity.this,selLang);

        details = MyPrefs.getLoginDetails(DoctorActivity.this);
        mTitle = (TextView) findViewById(R.id.mTitle);
        mBackBtn = (ImageView) findViewById(R.id.mBackBtn);
        mLogo = (ImageView) findViewById(R.id.mLogo);
        msgQBText = (TextView) findViewById(R.id.msgText);
        mBackBtn.setVisibility(View.GONE);
        msgQBText.setOnClickListener(this);

        if (Utils.isDeviceOnline(DoctorActivity.this)) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(DoctorActivity.this);
            mList_Title = new ArrayList<>();
            mList_Title_ID = new ArrayList<>();
            RestAdapter.getAdapter().getDTitleList(selLangToSend).enqueue(titleModelCallback);
        }

if(details!=null) {
    if (details.getIsClinic().equalsIgnoreCase("true")) {

        if (!details.getClinicPhoto().isEmpty()) {
            mLogo.setVisibility(View.VISIBLE);
            mTitle.setVisibility(View.GONE);
            try {
                Picasso.with(DoctorActivity.this)
                        .load(details.getClinicPhoto())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(mLogo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mLogo.setVisibility(View.GONE);
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(details.getClinicName());
        }
    }
}

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((getSupportFragmentManager().getBackStackEntryCount() - 1) <= 0) {

                    backHide();
                }
                onBackPressed();
            }
        });



        Utils.callFragmentReplaceDoctor(this, new HomeFragment(), HomeFragment.TAG);

        ImageView mMenuBtn = (ImageView) findViewById(R.id.mMenuBtn);
        mMenuBtn.setVisibility(View.VISIBLE);
        mMenuBtn1 = findViewById(R.id.mMenuBtn);
        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(mMenuBtn1);

            }
        });

        backHide();

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){

                    AppService.rtcClient.getInstance(DoctorActivity.this).destroy();

                    AppService.chatService.getInstance().logout(new QBEntityCallback() {
                        @Override
                        public void onSuccess(Object o, Bundle bundle) {
                            logoutFunction();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            e.printStackTrace();
                            logoutFunction();
                            Utils.dismissProDialog();
                        }


                    });
                }
            }
        };
    }

    public void backShow() {
        mBackBtn.setVisibility(View.VISIBLE);
    }

    public void backHide() {
        if (details.getIsClinic().equalsIgnoreCase("true")) {
            if (!details.getClinicPhoto().isEmpty()) {
                mLogo.setVisibility(View.VISIBLE);
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(details.getClinicName());
            } else {
                mLogo.setVisibility(View.GONE);
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(details.getClinicName());
            }
        }
        mTitle.setVisibility(View.GONE);
        mLogo.setVisibility(View.VISIBLE);
        mBackBtn.setVisibility(View.GONE);
    }

    public void forwardShowImg() {

        mTitle.setVisibility(View.VISIBLE);
        mLogo.setVisibility(View.GONE);
    }

    public boolean isNetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void setToast(String mMessage) {
        Toast.makeText(this, "" + mMessage, Toast.LENGTH_SHORT).show();
    }

    public void setLog(String mTag, String mMessage) {
        //Log.e(mTag, mMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_patient, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            if ((getSupportFragmentManager().getBackStackEntryCount() - 1) <= 0) {
                backHide();
            }
            onBackPressed();
        }
        if (item.getItemId() == R.id.action_settings) {
            showPopup(findViewById(R.id.action_settings));
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopup(View anchor) {
        final View popupView = LayoutInflater.from(this).inflate(R.layout.doctor_menu_dialog, null);

        TextView mChangePassword = (TextView) popupView.findViewById(R.id.mChangePassword);
        TextView mLogoutBtn = (TextView) popupView.findViewById(R.id.mLogoutBtn);


        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(popupView);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor);


        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Utils.callFragmentForAddDoctor(DoctorActivity.this, new ChangePassword(), ChangePassword.TAG);
            }
        });
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
               /* AppService.chatService.logout(new QBEntityCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, Bundle bundle) {

                    }

                    @Override
                    public void onError(QBResponseException list) {

                    }
                });*/
                if (Utils.isDeviceOnline(DoctorActivity.this)) {
                    // showProgress();
                    // String timeZone=Utils.getTimeZoneDifference();
                    Utils.showProDialog(DoctorActivity.this,"please wait...");
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(DoctorActivity.this);
                    RestAdapter.getAdapter().logoutUserFromApp(details.getUser_seq(),langOpted).enqueue(pMessageModelCallback);
                } else {
                    Toast.makeText(DoctorActivity.this, DoctorActivity.this.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }

            }
        });

        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public TextView getmTitle() {
        return mTitle;
    }

    public List<String> getmList_Title() {
        return mList_Title;
    }

    public List<String> getmList_Title_ID() {
        return mList_Title_ID;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show, final View recyclerView, final View mProgressView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public TextView getMsgQBText() {
        return msgQBText;
    }


    @Override
    public void dataNotify(Object result, final Object status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QBChatMessage qbChatMessage1 = (QBChatMessage) status;
                if (qbChatMessage1 == null) {
                    qbChatMessage = null;
                    return;
                }

                RegisterResponseModel rModel = new RegisterResponseModel();
                rModel.setMsgCount(MyPrefs.getUserMsgCount(DoctorActivity.this));
                EventBus.getDefault().post(rModel);

                qbChatMessage = qbChatMessage1;
                if (DoctorActivity.this != null) {
                    getMsgQBText().setVisibility(View.VISIBLE);
                    if (status instanceof QBChatMessage) {
                        getMsgQBText().setText(qbChatMessage.getBody());
                    }

                    new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            getMsgQBText().setVisibility(View.GONE);
                        }
                    }.start();


                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (msgQBText == v) {
            getMsgQBText().setVisibility(View.GONE);
            if (qbChatMessage == null) {
                return;
            }
            Utils.callFragmentForAddDoctor(DoctorActivity.this, new FrgChatWindow().newInstance((qbChatMessage.getSenderId()), qbChatMessage.getBody(), ""), FrgChatWindow.TAG);
        }
    }


}
