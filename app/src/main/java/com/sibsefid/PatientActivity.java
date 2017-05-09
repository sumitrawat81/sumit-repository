package com.sibsefid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.ChangePassword;
import com.sibsefid.fragemnts.patient.PBlogFragment;
import com.sibsefid.fragemnts.patient.PContactUs;
import com.sibsefid.fragemnts.patient.PFAQFragment;
import com.sibsefid.fragemnts.patient.PHomeProfileFragment;
import com.sibsefid.fragemnts.patient.PMedicalDevices;
import com.sibsefid.model.doctor.AppointmentStatusModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.SpecialityBean;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PatientTitleModel;
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
import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.DeviceInfo;
import com.zendesk.sdk.model.MemoryInformation;
import com.zendesk.sdk.model.access.JwtIdentity;
import com.zendesk.sdk.model.request.CustomField;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.util.FileUtils;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.EmailTranscript;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientActivity extends AppCompatActivity implements OnDataNotify, View.OnClickListener {


    private static final String TAG = "PatientActivity";
    private static final long TICKET_FORM_ID = 62599l;
    private static final long TICKET_FIELD_APP_VERSION = 24328555l;
    private static final long TICKET_FIELD_OS_VERSION = 24273979l;
    private static final long TICKET_FIELD_DEVICE_MODEL = 24273989l;
    private static final long TICKET_FIELD_DEVICE_MEMORY = 24273999;
    private static final long TICKET_FIELD_DEVICE_FREE_SPACE = 24274009l;
    private static final long TICKET_FIELD_DEVICE_BATTERY_LEVEL = 24274019l;
    public static Handler SessionTimeCloseCallBack;
    public Handler mHandler;
    String selLang;
    View mMenuBtn1;
    Callback<AppointmentStatusModel> pMessageModelCallback = new Callback<AppointmentStatusModel>() {
        @Override
        public void onResponse(Call<AppointmentStatusModel> call, Response<AppointmentStatusModel> response) {
            if (PatientActivity.this != null) {
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

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
    }*/

    private TextView mTitle;
    private ImageView mBackBtn, mLogo;
    private TextView msgQBText;
    private Button customSupportBtn;
    private UserLoginDetails.LoginDetails details;
    private ArrayList<PatientTitleModel.DataBean> titleBeenArrayList = new ArrayList<>();
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    Callback<PatientTitleModel> titlePModelCallback = new Callback<PatientTitleModel>() {
        @Override
        public void onResponse(Call<PatientTitleModel> call, Response<PatientTitleModel> response) {
            if (PatientActivity.this != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            titleBeenArrayList = response.body().getData();
                            mList_Title.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mList_Title.add(response.body().getData().get(i).getDetail_code_nm());
                                mList_Title_ID.add(response.body().getData().get(i).getDetail_code());
                                Object[] objects = new Object[2];
                                objects[0] = mTitle;
                                objects[1] = mList_Title_ID;
                                EventBus.getDefault().post(objects);
                            }

                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PatientTitleModel> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private ArrayList<SpecialityBean.Speciality> arraySpeciality;
    Callback<SpecialityBean> specialityBeanCallback = new Callback<SpecialityBean>() {
        @Override
        public void onResponse(Call<SpecialityBean> call, Response<SpecialityBean> response) {
            if (PatientActivity.this != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getSpeciality() != null && response.body().getSpeciality().size() > 0) {
                        arraySpeciality = response.body().getSpeciality();
                        EventBus.getDefault().post(arraySpeciality);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<SpecialityBean> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private QBChatMessage qbChatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if (Utils.getuserSeletedLanague(this) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(this);
        } else {

            Utils.forceLTRIfSupported(this);
        }*/

        AppService.onDataNotify = this;
        setContentView(R.layout.activity_patient);
        sessionClosecallback();

        selLang=MyPrefs.getUserSelectedLanguage(PatientActivity.this);
        Utils.chagesLocatiolization(PatientActivity.this,selLang);
        details = MyPrefs.getLoginDetails(PatientActivity.this);
        mTitle = (TextView) findViewById(R.id.mTitle);
        customSupportBtn = (Button) findViewById(R.id.customSupportBtn);
        mBackBtn = (ImageView) findViewById(R.id.mBackBtn);
        mLogo = (ImageView) findViewById(R.id.mLogo);
        msgQBText = (TextView) findViewById(R.id.msgText);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((getSupportFragmentManager().getBackStackEntryCount() - 1) <= 0) {
                    backHide();
                }
                onBackPressed();
            }
        });

        try {
            if (details.getIsClinic().equalsIgnoreCase("true")) {

                if (!details.getClinicPhoto().isEmpty()) {
                    mLogo.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.GONE);
                    try {
                        Picasso.with(PatientActivity.this)
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
        }catch (Exception e){

        }

        ImageView mMenuBtn = (ImageView) findViewById(R.id.mMenuBtn);
        mMenuBtn.setVisibility(View.VISIBLE);
        mMenuBtn1 = findViewById(R.id.mMenuBtn);
        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(mMenuBtn1);

            }
        });

        Utils.callFragmentReplacePatient(this, new PHomeProfileFragment(), PHomeProfileFragment.TAG);
        backHide();

        if (Utils.isDeviceOnline(PatientActivity.this)) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(PatientActivity.this);
            mList_Title = new ArrayList<>();
            mList_Title_ID = new ArrayList<>();
            RestAdapter.getAdapter().getPTitleList(selLangToSend).enqueue(titlePModelCallback);


        }
        if (Utils.isDeviceOnline(PatientActivity.this)) {

            String selLang=Utils.getuserSeletedLanagueForRequestSend(PatientActivity.this);
            RestAdapter.getAdapter().getGetSpeciality(selLang).enqueue(specialityBeanCallback);
        }
        msgQBText.setOnClickListener(this);
        customSupportBtn.setOnClickListener(this);
        initialiseZopimSdk();

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){

                    AppService.rtcClient.getInstance(PatientActivity.this).destroy();

                    AppService.chatService.getInstance().logout(new QBEntityCallback() {
                        @Override
                        public void onSuccess(Object o, Bundle bundle) {
                            logoutFunction();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            e.printStackTrace();
                            Utils.dismissProDialog();
                            logoutFunction();
                        }


                    });
                }
            }
        };
    }

    private void logoutFunction() {

        Utils.dismissProDialog();
        Utils.logout(PatientActivity.this, 0);
        if (QBChatService.isInitialized()) {
            try {
                AppService.chatService = null;
                QBRTCClient.getInstance(PatientActivity.this).destroy();
                AppService.rtcClient = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent servc = new Intent(PatientActivity.this, AppService.class);
        stopService(servc);

        AppService.chatService = null;
        AppService.rtcClient = null;
        PrivateChatImpl.chat = null;
        PrivateChatImpl.chatManager = null;
        AppService.currentSession = null;
        FrgChatWindow.chatCallBack = null;
        AppService.privateChat = null;
        AppService.sInstance = null;
        Intent i = new Intent(PatientActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void initialiseZopimSdk() {


        Logger.i("Identity", "Setting identity");
        ZendeskConfig.INSTANCE.setIdentity(new JwtIdentity(details.getEmail()));

        // Init Zopim Visitor info
        final VisitorInfo.Builder build = new VisitorInfo.Builder()
                .email(details.getEmail());


        build.name(details.getMyDoctorName());

        ZopimChat.setVisitorInfo(build.build());


        ZendeskConfig.INSTANCE.setTicketFormId(TICKET_FORM_ID);
        ZendeskConfig.INSTANCE.setCustomFields(getCustomFields());
        ZopimChat.init(getString(R.string.zopim_account_id)).emailTranscript(EmailTranscript.DISABLED).build();

    }

    private void sessionClosecallback() {
        SessionTimeCloseCallBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int frgCount = getSupportFragmentManager().getBackStackEntryCount();
                        if (frgCount > 0) {
                            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.patient_container);
                            if (frag instanceof FrgChatWindow) {
                                ((FrgChatWindow) frag).stoptimertask();
                                onBackPressed();
                            }
                        }
                        MyPrefs.saveUserTimeOut(PatientActivity.this, 0L);
                        Intent thank = new Intent(PatientActivity.this, ThankyouActivity.class);
                        startActivity(thank);
                    }
                });

            }
        };

    }

    public void getSpecilistList() {
        EventBus.getDefault().post(arraySpeciality);
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

        mBackBtn.setVisibility(View.GONE);
        mTitle.setVisibility(View.GONE);
        mLogo.setVisibility(View.VISIBLE);
    }

    public void forwardShowImg() {

        mTitle.setVisibility(View.VISIBLE);
        mLogo.setVisibility(View.GONE);

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
        final View popupView = LayoutInflater.from(this).inflate(R.layout.menu_dialog, null);

        TextView mBlogBtn = (TextView) popupView.findViewById(R.id.mBlogBtn);
        TextView mMedicalDevice = (TextView) popupView.findViewById(R.id.mMedicalDevice);
        TextView mFaqBtn = (TextView) popupView.findViewById(R.id.mFaqBtn);
        TextView mContactUsBtn = (TextView) popupView.findViewById(R.id.mContactUsBtn);
        TextView mChangePassword = (TextView) popupView.findViewById(R.id.mChangePassword);
        TextView mLogoutBtn = (TextView) popupView.findViewById(R.id.mLogoutBtn);


        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(popupView);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor);

        mBlogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.callFragmentForAddPatient(PatientActivity.this, new PBlogFragment(), PBlogFragment.TAG);
                popupWindow.dismiss();
            }
        });

        mMedicalDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.callFragmentForAddPatient(PatientActivity.this, new PMedicalDevices(), PMedicalDevices.TAG);
                popupWindow.dismiss();
            }
        });

        mFaqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.callFragmentForAddPatient(PatientActivity.this, new PFAQFragment(), PFAQFragment.TAG);
                popupWindow.dismiss();
            }
        });
        mContactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.callFragmentForAddPatient(PatientActivity.this, new PContactUs(), PContactUs.TAG);
                popupWindow.dismiss();
            }
        });
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Utils.callFragmentForAddPatient(PatientActivity.this, new ChangePassword(), ChangePassword.TAG);
            }
        });
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (Utils.isDeviceOnline(PatientActivity.this)) {
                    // showProgress();
                    // String timeZone=Utils.getTimeZoneDifference();
                    Utils.showProDialog(PatientActivity.this,"please wait...");
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(PatientActivity.this);
                    RestAdapter.getAdapter().logoutUserFromApp(details.getUser_seq(),langOpted).enqueue(pMessageModelCallback);
                } else {
                    Toast.makeText(PatientActivity.this, PatientActivity.this.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
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

    public List<String> getmList_Title_ID() {
        return mList_Title_ID;
    }

    public List<String> getmList_Title() {
        return mList_Title;
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

    public ArrayList<SpecialityBean.Speciality> getArraySpeciality() {
        return arraySpeciality;
    }

    public TextView getMsgQBText() {
        return msgQBText;
    }

    public void setMsgQBText(TextView msgQBText) {
        this.msgQBText = msgQBText;
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
                //  AppService.msgCounter= AppService.msgCounter+1;
                RegisterResponseModel rModel = new RegisterResponseModel();
                rModel.setMsgCount(MyPrefs.getUserMsgCount(PatientActivity.this));
                EventBus.getDefault().post(rModel);
                qbChatMessage = qbChatMessage1;
                if (PatientActivity.this != null) {
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

        switch (v.getId()) {
            case R.id.customSupportBtn:

               /* Intent zop=new Intent(PatientActivity.this, MainActivity.class);
                startActivity(zop);*/

                Logger.i("Identity", "Setting identity");
                Toast.makeText(this,"It is under working process",Toast.LENGTH_SHORT).show();
         /*       ZendeskConfig.INSTANCE.setIdentity(new JwtIdentity(details.getEmail()));

                // Init Zopim Visitor info
                final VisitorInfo.Builder build = new VisitorInfo.Builder()
                        .email(details.getEmail());

                if (StringUtils.hasLength(details.getFamily_name())) {
                    build.name(details.getFamily_name());
                }


                ZopimChat.setVisitorInfo(build.build());


                PreChatForm buildz = new PreChatForm.Builder()
                        .name(PreChatForm.Field.REQUIRED)
                        .email(PreChatForm.Field.REQUIRED)

                        .build();

                ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                        .preChatForm(buildz)
                        .department("The date");

                ZopimChatActivity.startActivity(PatientActivity.this, department);*/


                break;

            case R.id.msgText:
                getMsgQBText().setVisibility(View.GONE);
                if (qbChatMessage == null) {
                    return;
                }
                Utils.callFragmentForAddPatient(PatientActivity.this, new FrgChatWindow().newInstance((qbChatMessage.getSenderId()), qbChatMessage.getBody(), ""), FrgChatWindow.TAG);

                break;
        }

    }


    private List<CustomField> getCustomFields() {
        final DeviceInfo deviceInfo = new DeviceInfo(this);
        final MemoryInformation memoryInformation = new MemoryInformation(this);

        final String appVersion = String.format(
                Locale.US,
                "version_%s",
                com.zopim.android.sdk.api.BuildConfig.VERSION_NAME
        );

        final String osVersion = String.format(
                Locale.US,
                "Android %s, Version %s",
                deviceInfo.getVersionName(), deviceInfo.getVersionCode()
        );

        final String deviceModel = String.format(
                Locale.US,
                "%s, %s, %s",
                deviceInfo.getModelName(), deviceInfo.getModelDeviceName(), deviceInfo.getModelManufacturer()
        );

        final StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        final long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        final String freeSpace = FileUtils.humanReadableFileSize(bytesAvailable);

        // final String batteryLevel = String.format(Locale.US, "%.1f %s", getBatteryLevel(), "%");

        final List<CustomField> customFields = new ArrayList<>();
        customFields.add(new CustomField(TICKET_FIELD_APP_VERSION, appVersion));
        customFields.add(new CustomField(TICKET_FIELD_OS_VERSION, osVersion));
        customFields.add(new CustomField(TICKET_FIELD_DEVICE_MODEL, deviceModel));
        customFields.add(new CustomField(TICKET_FIELD_DEVICE_MEMORY, memoryInformation.formatMemoryUsage()));
        customFields.add(new CustomField(TICKET_FIELD_DEVICE_FREE_SPACE, freeSpace));


        return customFields;
    }
}
