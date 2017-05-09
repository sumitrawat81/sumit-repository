package com.sibsefid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sibsefid.fragment.doctor.BaseFragment;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.quickbloxchat.chat.PrivateChatImpl;
import com.sibsefid.quickbloxchat.interfaces.NetworkConnected;
import com.sibsefid.receivers.NetworkChangeReceiver;
import com.sibsefid.services.AppService;
import com.sibsefid.util.ExtraConstants;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * Created by ubuntu on 31/5/16.
 */
public class FrgChatWindow extends BaseFragment implements View.OnClickListener,
        OnDataNotify, NetworkConnected, SeekBar.OnSeekBarChangeListener {


    public static final String TAG = "FrgChatWindow";
    public static Handler chatCallBack;
    public static boolean show_emojicons = false;
    public static Timer timer;
    static RelativeLayout emojicons;
    private static QBDialog qbDialog;
    private static int QB_ID = 0;
    private static String QB_NAME = "";
    private static String QB_EMAIL = "";
    private static String AppointmentID = "";
    final Handler handler = new Handler();
    private final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";
    private final String ROOM_JID = "room_jid";
    public View toolbar;
    public long timeInMilliSeconds;
    PatientActivity activity;
    DoctorActivity activity_doctor;
    String timeInHMS;
    TimerTask timerTask;
    private View mView;
    private ImageView smile_btn;
    private ListView lvChatW;
    private EditText emojiconEditText;
    private ImageView imgSendMsg;
    private EditText mTitleTimer;
    private String userName;
    private boolean grouChat = false;
    private ArrayList<QBChatMessage> messageList = new ArrayList<>();
    private FrgChatWAdapter mAdapter;
    private QBRTCTypes.QBConferenceType qbConferenceType = null;
    private AppService myapp;
    private QBDialog qbGroupDialog;
    private ArrayList<Integer> opptIdList;
    private FrgChatWAdapter.ViewHolderChatW viewHolderChatW;
    private Runnable notification;
    private UserLoginDetails.LoginDetails details;

    public static int dp(float value) {
        return (int) Math.ceil(1 * value);
    }


    public static FrgChatWindow newInstance(int param1, String param2, String param3) {
        FrgChatWindow fragment = new FrgChatWindow();
        Bundle args = new Bundle();
        args.putInt("QB_ID", param1);
        args.putString(QB_NAME, param2);
        args.putString(QB_EMAIL, param3);

        // args.putString(QB_OPPONENT_PICL,param4);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            QB_ID = getArguments().getInt("QB_ID");
            QB_NAME = getArguments().getString(QB_NAME);
            QB_EMAIL = getArguments().getString(QB_EMAIL);
            AppointmentID = getArguments().getString(AppointmentID);
            // QB_OPPONENT_PICL = getArguments().getString(QB_OPPONENT_PICL);
        }
        details = MyPrefs.getLoginDetails(getActivity());
        int LOGINTYPE = MyPrefs.getUser(getActivity());
        if (LOGINTYPE == 1) {
            activity = (PatientActivity) getActivity();
        } else {
            activity_doctor = (DoctorActivity) getActivity();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frg_chat_window, container, false);
        myapp = AppService.getInstance();
        mAdapter = null;
        init();
        NetworkChangeReceiver.networkConnected = this;
        return mView;
    }

    private void displayData() {
        if (mAdapter == null) {
            mAdapter = new FrgChatWAdapter(getActivity(), messageList, this);
            lvChatW.setAdapter(mAdapter);
        } else {
            mAdapter.messageList = messageList;
            mAdapter.notifyDataSetChanged();
        }
        scrollMyListViewToBottom();

    }


    @Override
    public void onStop() {
        AppService.msgCounter = 0;
        MyPrefs.saveUserMsgCountl(getActivity(), AppService.msgCounter + "");
        super.onStop();

    }


    public void init() {


        messageList = new ArrayList<>();
        lvChatW = (ListView) mView.findViewById(R.id.lvChatW);
        smile_btn = (ImageView) mView.findViewById(R.id.smile_btn);
        imgSendMsg = (ImageView) mView.findViewById(R.id.imgSendMsg);
        emojicons = (RelativeLayout) mView.findViewById(R.id.emojicons);
        emojiconEditText = (EditText) mView.findViewById(R.id.emojiconEditText);
        mTitleTimer = (EditText) mView.findViewById(R.id.mTitleTimer);
        smile_btn.setOnClickListener(this);
        imgSendMsg.setOnClickListener(this);

        mTitleTimer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTitleTimer.getText().toString().trim().equalsIgnoreCase("10:00")) {
                    int LOGINTYPE = MyPrefs.getUser(getActivity());
                    if (LOGINTYPE == 1) {

                        MyPrefs.saveUserTimeOut(getActivity(), 0L);
                        mTitleTimer.setText("0");
                        stoptimertask();
                        activity.onBackPressed();

                    } else {

                        MyPrefs.saveUserTimeOut(getActivity(), 0L);

                        stoptimertask();
                        createChatDialogTimerOver();
                        Intent i = new Intent(getActivity(), ThankyouActivity.class);
                        startActivity(i);
                        activity_doctor.onBackPressed();
                       /**/
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        opptIdList = new ArrayList<Integer>();
        userName = QB_NAME;
        details = MyPrefs.getLoginDetails(getActivity());
        AppService.privateChat = PrivateChatImpl.getInstance(QB_ID);
        AppService.privateChat.onDataNotify = FrgChatWindow.this;
        opptIdList.add(QB_ID);


        if (Utils.isDeviceOnline(getActivity(), true)) {
            connectToFriend(getActivity(), opptIdList, userName, grouChat);
        }

        emojiconEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imgSendMsg.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AppService.msgCounter = 0;
        MyPrefs.saveUserMsgCountl(getActivity(), AppService.msgCounter + "");

    }

    private void createChatDialogTimerOver() {
        sendChatMessage("Chat_Disconnected_Time_Over");
    }


    private boolean isLoginUser() {
        boolean b = false;
        if (viewHolderChatW.frgChatWModel.getSenderId().equals(Integer.valueOf(details.getQuickBloxId()))) {
            b = true;
        }
        return b;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.imgSendMsg:
                sendMessage();
                break;
        }
    }


    private void sendMessage() {
        if (Utils.isDeviceOnline(getActivity(), true)) {
            String chatMsg = emojiconEditText.getText().toString().trim();
            if (!chatMsg.isEmpty()) {

                sendChatMessage(chatMsg);
            } else {
                Toast.makeText(getActivity(), "Msg is empty", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void connectToFriend(final Activity act, ArrayList<Integer> optId, String optEmail, boolean groupChat) {
        if (AppService.chatService != null) {
            Utils.showProDialog(getActivity(), null);
            final QBDialog dialogToCreate = new QBDialog();
            dialogToCreate.setName(optEmail);

            AppService.chatService.getPrivateChatManager().createDialog(optId.get(0), new QBEntityCallback<QBDialog>() {
                @Override
                public void onSuccess(QBDialog dialog, Bundle args) {
                    qbDialog = dialog;
                    loadChatHistory();
                }

                @Override
                public void onError(final QBResponseException errors) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showOkDialog(act, null, errors.getMessage());
                            Utils.dismissProDialog();
                        }
                    });

                }
            });


        }
    }


    private void loadChatHistory() {
        QBRequestGetBuilder customObjectRequestBuilder = new QBRequestGetBuilder();
        customObjectRequestBuilder.setLimit(100);
        customObjectRequestBuilder.sortDesc("date_sent");
        QBChatService.getDialogMessages(qbDialog, customObjectRequestBuilder, new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args) {
                messageList.addAll(messages);
                if (messageList != null && messageList.size() > 0) {
                    for (int i = 0; i < messageList.size(); i++) {
                        if (!messageList.get(i).getSenderId().equals(details.getQuickBloxId())) {

                        }
                        messageList.get(i).setProperty(ExtraConstants.SEEKPROGRESS, "" + 0);
                    }
                }
                Collections.reverse(messageList);
                displayData();
                Utils.dismissProDialog();
                timeInMilliSeconds = MyPrefs.getUserTimeOut(getActivity());
                startTimer(timeInMilliSeconds);
            }

            @Override
            public void onError(final QBResponseException errors) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showOkDialog(getActivity(), null, errors.getMessage());
                        Utils.dismissProDialog();
                        timeInMilliSeconds = MyPrefs.getUserTimeOut(getActivity());
                        startTimer(timeInMilliSeconds);
                    }
                });

            }
        });

    }


    private void sendChatMessage(String messageText) {
        AppService.privateChat.onDataNotify = this;
        QBChatMessage chatMessage = new QBChatMessage();
        if (!messageText.isEmpty())
            chatMessage.setBody(messageText);
        // chatMessage.setMarkable(true);
        chatMessage.setDialogId(qbDialog.getDialogId());
        chatMessage.setId(details.getQuickBloxId());
        chatMessage.setRecipientId(QB_ID);
        chatMessage.setSenderId(Integer.parseInt(details.getQuickBloxId()));
        chatMessage.setProperty(PROPERTY_SAVE_TO_HISTORY, "1");
        chatMessage.setDateSent(new Date().getTime() / 1000);
        if (PrivateChatImpl.chat == null) {
            AppService.privateChat = PrivateChatImpl.getInstance(QB_ID);

            AppService.privateChat.onDataNotify = this;
        }
        AppService.privateChat.sendMessage(chatMessage);
        emojiconEditText.setText("");
    }


    @Override
    public void dataNotify(Object result, final Object status) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (status instanceof QBChatMessage) {
                        QBChatMessage qbChatMessage = (QBChatMessage) status;
                        boolean b = false;
                        if ((opptIdList.contains(qbChatMessage.getSenderId()) && qbChatMessage.getRecipientId().equals(Integer.valueOf(details.getQuickBloxId()))) || (qbChatMessage.getSenderId().equals(Integer.valueOf(details.getQuickBloxId())))) {
                            b = true;
                        }
                        if (b) {
                            messageList.add(qbChatMessage);
                            displayData();

                        }
                    }
                }

            });
        }
    }

    private void scrollMyListViewToBottom() {
        try {
            lvChatW.post(new Runnable() {
                @Override
                public void run() {
                    if (lvChatW != null && lvChatW.getAdapter() != null)
                        lvChatW.setSelection(lvChatW.getAdapter().getCount() - 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void networkConnected(boolean isConnected) {
        if (isConnected) {

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
        AppService.msgCounter = 0;
        MyPrefs.saveUserMsgCountl(getActivity(), AppService.msgCounter + "");
        RegisterResponseModel rModel = new RegisterResponseModel();
        rModel.setMsgCount(MyPrefs.getUserMsgCount(getActivity()));
        EventBus.getDefault().post(rModel);
        AppService.privateChat.onDataNotify = null;
    }

    public void startTimer(Long timeInMilliSeconds) {
        if (timer == null) {
            this.timeInMilliSeconds = timeInMilliSeconds;

            timer = new Timer();
            initializeTimerTask();
            timer.schedule(timerTask, 0, 1000); //
        }
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }

    public void initializeTimerTask() {


        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        if (TimeUnit.MILLISECONDS.toHours(timeInMilliSeconds) <= 0) {
                            timeInHMS = String.format("%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(timeInMilliSeconds) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMilliSeconds)), // The change is in this line
                                    TimeUnit.MILLISECONDS.toSeconds(timeInMilliSeconds) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMilliSeconds)));
                        } else {
                            timeInHMS = String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(timeInMilliSeconds),
                                    TimeUnit.MILLISECONDS.toMinutes(timeInMilliSeconds) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMilliSeconds)), // The change is in this line
                                    TimeUnit.MILLISECONDS.toSeconds(timeInMilliSeconds) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMilliSeconds)));
                        }
                        mTitleTimer.setText(timeInHMS);
                        if (getActivity() != null) {
                            MyPrefs.saveUserTimeOut(getActivity(), timeInMilliSeconds);
                        }
                        timeInMilliSeconds += 1000;
                    }
                });
            }
        };
    }

}