package com.sibsefid.quickbloxchat;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.fragemnts.patient.PHomeProfileFragment;
import com.sibsefid.model.quickbolx_model.CustomQB_UserModel;
import com.sibsefid.quickbloxchat.definitions.Consts;
import com.sibsefid.quickbloxchat.holder.DataHolder_Returns;
import com.sibsefid.quickbloxchat.qbfragments.ConversationFragment;
import com.sibsefid.quickbloxchat.qbfragments.IncomeCallFragment;
import com.sibsefid.quickbloxchat.utils.QBConstants;
import com.sibsefid.quickbloxchat.utils.SettingsUtil;
import com.sibsefid.services.AppService;
import com.sibsefid.util.RingtonePlayer;
import com.sibsefid.util.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 29/9/16.
 */
public class CallingMainActivity extends AppCompatActivity {
    public static EditText mTitle;
    public static Handler SessionCloseCallBack;
    public static Handler newCallupdateHandler;
    public static QBRTCSession session;
    public Handler showIncomingCallWindowTaskHandler;
    public Runnable showIncomingCallWindowTask;
    ArrayList<CustomQB_UserModel> opponents;
    String callingType;
    String processType;
    String appointmentId;
    String patientId;
    QBRTCTypes.QBConferenceType qbConferenceType;
    ConversationFragment fragment;
    String output;
    long seconds;
    private View mMenuBtn1;
    private ImageView mBackBtn, mLogo;
    private AppService myApplication;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private RelativeLayout tollbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.getuserSeletedLanague(this) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(this);
        } else {

            Utils.forceLTRIfSupported(this);
        }

        setContentView(R.layout.calling_main_activity);

        myApplication = AppService.getInstance();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
        sessionClosecallback();
        setRecentupdateHandler();
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            callingType = bundle.getString("calling");


            if (callingType.equals("s_calling")) {

                processType = bundle.getString("qbConferenceType");
                appointmentId = bundle.getString("appointment_id");
                patientId = bundle.getString("patient_id");
                opponents = (ArrayList<CustomQB_UserModel>) getIntent().getSerializableExtra("opponents");
                Log.e("DataFOund", opponents.get(0).getId() + "");

                if (processType.equals("audio")) {
                    qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
                } else if (processType.equals("video")) {
                    qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;
                }

                if (myApplication.getRtcClient() != null) {
                    if (opponents != null) {
                        // dismissgilog();
                        ArrayList<QBUser> opp = new ArrayList<>();
                        QBUser qbuser = new QBUser();
                        qbuser.setId(opponents.
                                get(0).getId());
                        qbuser.setFullName(opponents.
                                get(0).getName());
                        qbuser.setEmail(opponents.
                                get(0).getEmail());
                        opp.add(qbuser);
                        QBRTCSession newSessionWithOpponents = myApplication.getRtcClient().createNewSessionWithOpponents(
                                AppService.getOpponentsCustomIds(opponents), qbConferenceType);
                        Log.d("Crash", "addConversationFragmentStartCall. Set session " + newSessionWithOpponents);
                        initCurrentSession(newSessionWithOpponents);

                        fragment = ConversationFragment.newInstance(opp, opponents.get(0).getEmail(),
                                qbConferenceType, AppService.StartConversetionReason.OUTCOME_CALL_MADE, myApplication.getCurrentSession().getSessionID(), patientId, appointmentId);
                        Utils.callFragmentForAddDoctorCallingMain(CallingMainActivity.this, fragment, PHomeProfileFragment.TAG);
                        //   FragmentExecuotr.addFragment(getSupportFragmentManager(), R.id.content_frame, fragment, QBConstants.CONVERSATION_CALL_FRAGMENT);
                        myApplication.setRingtonePlayer(new RingtonePlayer(CallingMainActivity.this, R.raw.beep));
                        myApplication.getRingtonePlayer().play(true);
                    } else {
                        Log.e("check opponents", "opponents null");
                    }
                } else {
                    Log.e("check rtcClient", "rtcClient null");
                    Toast.makeText(CallingMainActivity.this, "not able to connect!!", Toast.LENGTH_SHORT).show();
                }

            } else if (callingType.equals("r_calling")) {
               /* android.os.Message msg = CallingMainActivity.newCallupdateHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("session", session.toString());
                msg.setData(b);
                CallingMainActivity.newCallupdateHandler.sendMessage(msg);*/
                incommingCallingFromUser();
            }

        }
    }

    private void init() {
        mTitle = (EditText) findViewById(R.id.mTitle);
        mBackBtn = (ImageView) findViewById(R.id.mBackBtn);
        mLogo = (ImageView) findViewById(R.id.mLogo);
        tollbarLayout = (RelativeLayout) findViewById(R.id.tollbarLayout);
        mBackBtn.setVisibility(View.GONE);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((getSupportFragmentManager().getBackStackEntryCount() - 1) <= 0) {

                    backHide();
                }
                onBackPressed();
            }
        });

        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTitle.getText().toString().trim().equalsIgnoreCase("10:00")) {
                    editor.putString("appointmentIdTimeOver", null);
                    editor.putLong("appointmentTimeOverTime", 0);
                    myApplication.hangUpCurrentSession();
                    fragment.endCallingTimeEnds();
                    // call_thankyou_dialog();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backHide();
    }

    private void call_thankyou_dialog() {

        final Dialog dialog = new Dialog(CallingMainActivity.this);
        dialog.requestWindowFeature(CallingMainActivity.this.getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.thankyou_note_dialog);

        Button mOkBtn = (Button) dialog.findViewById(R.id.mOkBtn);


        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                fragment.endCallingTimeEnds();
            }
        });


        dialog.show();

    }

    private void setRecentupdateHandler() {
        // TODO Auto-generated method stub
        newCallupdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
    }


    private void incommingCallingFromUser() {
        try {
            Log.e("TAG", "Session " + session.getSessionID() + " are income");
            Log.e("TAG", "insideonReceivedNewSession " + session.getSessionID() + " are income");
        } catch (Exception e) {

        }
        if (myApplication.getCurrentSession() == null) {
            Log.d("TAG", "Start new session");
            initCurrentSession(session);
        }
        addIncomeCallFragment(session);
        myApplication.setIsInCommingCall(true);
        initIncommingCallTask();
    }

    public void initIncommingCallTask() {
        showIncomingCallWindowTaskHandler = new Handler(Looper.myLooper());
        showIncomingCallWindowTask = new Runnable() {
            @Override
            public void run() {
                IncomeCallFragment incomeCallFragment = (IncomeCallFragment) getSupportFragmentManager().findFragmentByTag(QBConstants.INCOME_CALL_FRAGMENT);
                if (incomeCallFragment == null) {
                    fragment = (ConversationFragment) getSupportFragmentManager().findFragmentByTag(QBConstants.CONVERSATION_CALL_FRAGMENT);
                    if (fragment != null) {
                        disableConversationFragmentButtons();
                        myApplication.getRingtonePlayer().stop();
                        myApplication.hangUpCurrentSession();
                    }
                } else {
                    myApplication.rejectCurrentSession();
                }
                Toast.makeText(CallingMainActivity.this, "Call was stopped by timer", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void disableConversationFragmentButtons() {
        fragment = (ConversationFragment) getSupportFragmentManager().findFragmentByTag(QBConstants.CONVERSATION_CALL_FRAGMENT);
        if (fragment != null) {
            fragment.actionButtonsEnabled(false);
        }
    }

    public void addIncomeCallFragment(QBRTCSession session) {
        Log.d("TAG", "QBRTCSession in addIncomeCallFragment is " + session);
        if (session != null) {
            ////////////
            if (CallingMainActivity.this != null) {
                Fragment fragment = new IncomeCallFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("sessionDescription", session.getSessionDescription());
                bundle.putIntegerArrayList("opponents", new ArrayList<Integer>(session.getOpponents()));
                bundle.putInt(Consts.CONFERENCE_TYPE, session.getConferenceType().getValue());
                Log.e("sessionDescription", session.getSessionDescription().toString());
                fragment.setArguments(bundle);
                Utils.callFragmentReplaceQb(CallingMainActivity.this, fragment, QBConstants.INCOME_CALL_FRAGMENT);

            }
            ///////////////

        }
    }

    private void sessionClosecallback() {
        SessionCloseCallBack = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            toolbar.setVisibility(View.VISIBLE);
                        if (session != null && session.equals(myApplication.getCurrentSession())) {
                            Log.d("TAG", "Stop session");

                            editor.putString("appointmentIdTimeOver", appointmentId);

                            editor.commit();
                            if (ConversationFragment.timer != null) {
                                ConversationFragment.timer.cancel();
                                ConversationFragment.timer = null;
                            }
                            myApplication.releaseCurrentSession();
                            // android.os.Process.killProcess(android.os.Process.myPid());
                            CallingMainActivity.this.finish();

                        }
                    }
                });

            }
        };

    }


    public void initCurrentSession(QBRTCSession sesion) {
        myApplication.setCurrentSession(sesion);
        myApplication.getCurrentSession().addSessionCallbacksListener(myApplication);
        myApplication.getCurrentSession().addSignalingCallback(myApplication);
    }


    public void addConversationFragmentReceiveCall(String fullName) {
        if (Utils.isDeviceOnline(this, true)) {
            QBRTCSession session = myApplication.getCurrentSession();
            if (myApplication.getCurrentSession() != null) {
                Integer myId = QBChatService.getInstance().getUser().getId();
                ArrayList<Integer> opponentsWithoutMe = new ArrayList<Integer>(session.getOpponents());
                opponentsWithoutMe.remove(new Integer(myId));
                opponentsWithoutMe.add(session.getCallerID());
                DataHolder_Returns.opponent_user_Data.setId(session.getCallerID());
                DataHolder_Returns.opponent_user_Data.setFullName(fullName);
                ArrayList<QBUser> result = new ArrayList<>();
                result.add(DataHolder_Returns.opponent_user_Data);
                List<QBUser> opponents = result;
                SettingsUtil.setSettingsStrategy(opponents, sharedPref, CallingMainActivity.this);
                fragment = ConversationFragment.newInstance(opponents,
                        DataHolder_Returns.opponent_user_Data.getEmail(),
                        session.getConferenceType(),
                        AppService.StartConversetionReason.INCOME_CALL_FOR_ACCEPTION, myApplication.getCurrentSession().getSessionID(), patientId, appointmentId);
                Utils.callFragmentForAddDoctorCallingMain(CallingMainActivity.this, fragment, QBConstants.CONVERSATION_CALL_FRAGMENT);

            }
        }
    }


    @Override
    public void onBackPressed() {
        int frgCount = getSupportFragmentManager().getBackStackEntryCount();
        if (frgCount > 0) {
            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.calling_container);
            if (frag instanceof ConversationFragment || frag instanceof IncomeCallFragment) {

            } else {
                getSupportFragmentManager().popBackStackImmediate();
                backHide();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void backShow() {
        mBackBtn.setVisibility(View.VISIBLE);
    }

    public void backHide() {

        mTitle.setVisibility(View.VISIBLE);
        mLogo.setVisibility(View.GONE);
        mBackBtn.setVisibility(View.GONE);
    }

    public void forwardShowImg() {

        tollbarLayout.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mLogo.setVisibility(View.GONE);
        mBackBtn.setVisibility(View.VISIBLE);

    }


    public String formatTime(long millis) {
        output = "";
        seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;

        if (hours < 10)
            hoursD = "0" + hours;

        //output = hoursD+" : "+minutesD + " : " + secondsD;
        output = minutesD + " : " + secondsD;

        return output;
    }

}
