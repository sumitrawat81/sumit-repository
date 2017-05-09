package com.sibsefid.quickbloxchat.qbfragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sibsefid.R;
import com.sibsefid.fragment.doctor.DoctorCallingClinicalDashboard;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.quickbloxchat.CallingMainActivity;
import com.sibsefid.quickbloxchat.adapters.OpponentsFromCallAdapter;
import com.sibsefid.quickbloxchat.chat.PrivateChatImpl;
import com.sibsefid.quickbloxchat.definitions.Consts;
import com.sibsefid.quickbloxchat.utils.CameraUtils;
import com.sibsefid.quickbloxchat.view.RTCGLVideoView;
import com.sibsefid.services.AppService;
import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBMediaStreamManager;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionConnectionCallbacks;
import com.quickblox.videochat.webrtc.exception.QBRTCException;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

import org.webrtc.VideoRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * QuickBlox team
 */
public class ConversationFragment extends Fragment implements Serializable, QBRTCClientVideoTracksCallbacks, QBRTCSessionConnectionCallbacks, AppService.QBRTCSessionUserCallback, OpponentsFromCallAdapter.OnAdapterEventListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String CALLER_NAME = "caller_name";
    public static final String SESSION_ID = "sessionID";
    public static final String START_CONVERSATION_REASON = "start_conversation_reason";

    private static final int DEFAULT_ROWS_COUNT = 2;
    private static final int DEFAULT_COLS_COUNT = 3;
    private static final long TOGGLE_CAMERA_DELAY = 1000;
    private static final long LOCAL_TRACk_INITIALIZE_DELAY = 500;
    public static LinearLayout llEndCall;
    public static Timer timer;
    final Handler handler = new Handler();
    public long timeInMilliSeconds;
    int oponentId;
    String timeInHMS;
    TimerTask timerTask;
    private String TAG = ConversationFragment.class.getSimpleName();
    private ArrayList<QBUser> opponents;
    private int qbConferenceType;
    private int startReason;
    private String sessionID;
    private MediaPlayer ringtone;
    private ToggleButton cameraToggle;
    private ToggleButton switchCameraToggle;
    private ToggleButton dynamicToggleVideoCall;
    private ToggleButton micToggleVideoCall;
    private View myCameraOff;
    private TextView incUserName;
    private TextView showClinicalDashboard;
    private View view;
    private Map<String, String> userInfo;
    private boolean isVideoEnabled = false;
    private boolean isAudioEnabled = true;
    private List<QBUser> allUsers = new ArrayList<>();
    private LinearLayout actionVideoButtonsLayout;
    private String callerName;
    private String patientId;
    private String appointmentId;
    private boolean isMessageProcessed;
    private RTCGLVideoView localVideoView;
    private IntentFilter intentFilter;
    private AudioStreamReceiver audioStreamReceiver;
    private CameraState cameraState = CameraState.NONE;
    private RecyclerView recyclerView;
    private SparseArray<OpponentsFromCallAdapter.ViewHolder> opponentViewHolders;
    private boolean isPeerToPeerCall;
    private QBRTCVideoTrack localVideoTrack;
    private Handler mainHandler;
    private AppService myApp;
    private View toolBarView;
    private TextView txtTileName;
    private UserLoginDetails.LoginDetails details;
    private LinearLayout llController;
    private LinearLayout llmike;
    private LinearLayout llSpeaker;
    private LinearLayout llCamera;
    private ToggleButton speakerToggle;
    private FrameLayout localClinicalView;
    private CallingMainActivity activity;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static ConversationFragment newInstance(List<QBUser> opponents, String callerName,
                                                   QBRTCTypes.QBConferenceType qbConferenceType,
                                                   AppService.StartConversetionReason reason,
                                                   String sesionnId, String patientId, String appointmentId) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Consts.CONFERENCE_TYPE, qbConferenceType.getValue());
        bundle.putString(CALLER_NAME, callerName);
        bundle.putString("PatientId", patientId);
        bundle.putString("AppointmentId", appointmentId);
        bundle.putSerializable(Consts.OPPONENTS, (Serializable) opponents);
       /* if (userInfo != null) {
            for (String key : userInfo.keySet()) {
                bundle.putString("UserInfo:" + key, userInfo.get(key));
            }
        }*/
        bundle.putInt(START_CONVERSATION_REASON, reason.ordinal());
        if (sesionnId != null) {
            bundle.putString(SESSION_ID, sesionnId);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private void startOutBeep() {
        ringtone = MediaPlayer.create(getActivity(), R.raw.beep);
        ringtone.setLooping(true);
        ringtone.start();
    }

    public void stopOutBeep() {
        if (ringtone != null) {
            try {
                ringtone.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ringtone.release();
            ringtone = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        view = inflater.inflate(R.layout.fragment_conversation, container, false);
        Log.d(TAG, "Fragment. Thread id: " + Thread.currentThread().getId());
        myApp = AppService.getInstance();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPref.edit();
        if (getArguments() != null) {
            opponents = (ArrayList<QBUser>) getArguments().getSerializable(Consts.OPPONENTS);
            qbConferenceType = getArguments().getInt(Consts.CONFERENCE_TYPE);
            startReason = getArguments().getInt(AppService.START_CONVERSATION_REASON);
            sessionID = getArguments().getString(AppService.SESSION_ID);
            callerName = getArguments().getString(AppService.CALLER_NAME);
            patientId = getArguments().getString("PatientId");
            appointmentId = getArguments().getString("AppointmentId");
            oponentId = opponents.get(0).getId();
            isPeerToPeerCall = opponents.size() == 1;
            isVideoEnabled = (qbConferenceType ==
                    QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO.getValue());
            Log.e(TAG, "CALLER_NAME: " + callerName);
            Log.e(TAG, "opponents: " + opponents.toString());
        }
        initViews(view);
        initButtonsListener();
        initSessionListener();
        setUpUiByCallType(qbConferenceType);
        mainHandler = new FragmentLifeCycleHandler();
        //toolBarView = ((ActLandingScreen) getActivity()).setToolBarLayout(R.layout.header_with_title);
        // initToolBarView();
        return view;
    }


    private void initSessionListener() {
        myApp.addVideoTrackCallbacksListener(this);
    }

    private void setUpUiByCallType(int qbConferenceType) {
        if (!isVideoEnabled) {
            cameraToggle.setVisibility(View.INVISIBLE);
            llCamera.setVisibility(View.GONE);
            if (switchCameraToggle != null) {
                switchCameraToggle.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void actionButtonsEnabled(boolean enability) {
        cameraToggle.setEnabled(enability);
        speakerToggle.setEnabled(enability);
        micToggleVideoCall.setEnabled(enability);
        dynamicToggleVideoCall.setEnabled(enability);
        // inactivate toggle buttons
        cameraToggle.setActivated(enability);
        speakerToggle.setActivated(enability);
        micToggleVideoCall.setActivated(enability);
        dynamicToggleVideoCall.setActivated(enability);
        if (switchCameraToggle != null) {
            switchCameraToggle.setEnabled(enability);
            switchCameraToggle.setActivated(enability);
        }
    }


    @Override
    public void onStart() {
        getActivity().registerReceiver(audioStreamReceiver, intentFilter);
        super.onStart();
        QBRTCSession session = myApp.getCurrentSession();
        if (!isMessageProcessed && session != null) {
            if (startReason == AppService.StartConversetionReason.INCOME_CALL_FOR_ACCEPTION.ordinal()) {
                session.acceptCall(session.getUserInfo());
                //stopOutBeep();
            } else {
                session.startCall(session.getUserInfo());
                //startOutBeep();
            }
            isMessageProcessed = true;
        }
        myApp.addTCClientConnectionCallback(this);
        myApp.addRTCSessionUserCallback(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() from " + TAG);
        super.onCreate(savedInstanceState);
        intentFilter = new IntentFilter();
        //  intentFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);
        intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);

        audioStreamReceiver = new AudioStreamReceiver();
        activity = (CallingMainActivity) getActivity();
        // ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();

        details = MyPrefs.getLoginDetails(getActivity());
    }

    private void initViews(View view) {

        opponentViewHolders = new SparseArray<>(opponents.size());
        recyclerView = (RecyclerView) view.findViewById(R.id.grid_opponents);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.dimen.grid_item_divider));
        recyclerView.setHasFixedSize(false);
        final int columnsCount = defineColumnsCount();
        final int rowsCount = defineRowCount();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnsCount));
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setGrid(columnsCount, rowsCount);
                recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        cameraToggle = (ToggleButton) view.findViewById(R.id.cameraToggle);
        speakerToggle = (ToggleButton) view.findViewById(R.id.speakerToggle);
        llController = (LinearLayout) view.findViewById(R.id.llController);
        llmike = (LinearLayout) view.findViewById(R.id.llmike);
        llCamera = (LinearLayout) view.findViewById(R.id.llCamera);
        llSpeaker = (LinearLayout) view.findViewById(R.id.llSpeaker);
        if (!isPeerToPeerCall) {
            initLocalViewUI(view);
        }
        dynamicToggleVideoCall = (ToggleButton) view.findViewById(R.id.dynamicToggleVideoCall);
        micToggleVideoCall = (ToggleButton) view.findViewById(R.id.micToggleVideoCall);
        actionVideoButtonsLayout = (LinearLayout) view.findViewById(R.id.element_set_video_buttons);
        llEndCall = (LinearLayout) view.findViewById(R.id.llEndCall);
        incUserName = (TextView) view.findViewById(R.id.incUserName);
        localClinicalView = (FrameLayout) view.findViewById(R.id.localClinicalView);
        showClinicalDashboard = (TextView) view.findViewById(R.id.showClinicalDashboard);
        incUserName.setText(callerName);

        actionButtonsEnabled(false);

        int LOGINTYPE = MyPrefs.getUser(getActivity());
        if (LOGINTYPE == 1) {
            localClinicalView.setVisibility(View.GONE);
        } else if (LOGINTYPE == 2) {
            localClinicalView.setVisibility(View.VISIBLE);
        }

        llmike.setOnClickListener(this);
        llCamera.setOnClickListener(this);
        llSpeaker.setOnClickListener(this);
        showClinicalDashboard.setOnClickListener(this);
        speakerToggle.setOnCheckedChangeListener(this);
        micToggleVideoCall.setOnCheckedChangeListener(this);


    }


    private void setGrid(int columnsCount, int rowsCount) {
        int gridWidth = recyclerView.getMeasuredWidth();
        float itemMargin = getResources().getDimension(R.dimen.grid_item_divider);
        int cellSize = defineMinSize(gridWidth, recyclerView.getMeasuredHeight(),
                columnsCount, rowsCount, itemMargin);
        Log.i(TAG, "onGlobalLayout : cellSize=" + cellSize);
        //int cellSize=recyclerView.getHeight();

        OpponentsFromCallAdapter opponentsAdapter = new OpponentsFromCallAdapter(getActivity(), opponents, cellSize,
                cellSize, gridWidth, columnsCount, (int) itemMargin,
                isVideoEnabled);
        opponentsAdapter.setAdapterListener(ConversationFragment.this);
        recyclerView.setAdapter(opponentsAdapter);
    }

    private int defineMinSize(int measuredWidth, int measuredHeight, int columnsCount, int rowsCount, float padding) {
        int cellWidth = measuredWidth / columnsCount - (int) (padding * 2);
        int cellHeight = measuredHeight / rowsCount - (int) (padding * 2);
        return Math.min(cellWidth, cellHeight);
    }

    private int defineRowCount() {
        int result = DEFAULT_ROWS_COUNT;
        int opponentsCount = opponents.size();
        if (opponentsCount < 3) {
            result = opponentsCount;
        }
        return result;

    }

    private int defineColumnsCount() {
        int result = DEFAULT_COLS_COUNT;
        int opponentsCount = opponents.size();
        if (opponentsCount == 1 || opponentsCount == 2) {
            result = 1;
        } else if (opponentsCount == 4) {
            result = 2;
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        // If user changed camera state few times and last state was CameraState.ENABLED_FROM_USER // Жень, глянь здесь, смысл в том, что мы здесь включаем камеру, если юзер ее не выключал
        // than we turn on cam, else we nothing change
        if (cameraState != CameraState.DISABLED_FROM_USER
                && isVideoEnabled) {
            toggleCamera(true);
        }
    }

    @Override
    public void onPause() {
        // If camera state is CameraState.ENABLED_FROM_USER or CameraState.NONE
        // than we turn off cam
        if (cameraState != CameraState.DISABLED_FROM_USER && isVideoEnabled) {
            toggleCamera(false);
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(audioStreamReceiver);
        //stopOutBeep();
        myApp.removeRTCClientConnectionCallback(this);
        myApp.removeRTCSessionUserCallback(this);

    }

    private void initSwitchCameraButton(View view) {
        switchCameraToggle = (ToggleButton) view.findViewById(R.id.switchCameraToggle);
        switchCameraToggle.setOnClickListener(getCameraSwitchListener());
        switchCameraToggle.setActivated(false);
        switchCameraToggle.setVisibility(isVideoEnabled ?
                View.INVISIBLE : View.VISIBLE);
    }

    private void initButtonsListener() {
        cameraToggle.setOnCheckedChangeListener(this);

        dynamicToggleVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myApp.getCurrentSession() != null) {
                    Log.d(TAG, "Dynamic switched!");
                    myApp.getCurrentSession().switchAudioOutput();
                }

            }
        });

        llEndCall.setOnClickListener(this);
      /*  llEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButtonsEnabled(false);
                llEndCall.setEnabled(false);
                stoptimertask(v);
                //stopOutBeep();
                Log.d(TAG, "Call is stopped");
                myApp.hangUpCurrentSession();

                createChatDialog();
                llEndCall.setEnabled(false);
                llEndCall.setActivated(false);

            }
        });*/
    }


    private void createChatDialogTimerOver() {

        QBPrivateChatManager privateChatManager = QBChatService.getInstance().getPrivateChatManager();
        privateChatManager.createDialog(oponentId, new QBEntityCallback<QBDialog>() {
            @Override
            public void onSuccess(QBDialog dialog, Bundle args) {

                QBChatMessage chatMessage = new QBChatMessage();

                chatMessage.setBody("Chat_Disconnected_Form");

                chatMessage.setDialogId(dialog.getDialogId());
                chatMessage.setId(details.getQuickBloxId());
                chatMessage.setRecipientId(oponentId);
                chatMessage.setSenderId(Integer.parseInt(details.getQuickBloxId()));

                chatMessage.setDateSent(new Date().getTime() / 1000);
                if (PrivateChatImpl.chat == null) {
                    AppService.privateChat = PrivateChatImpl.getInstance(oponentId);

                }

                QBChatMessage chatMessage2 = new QBChatMessage();
                chatMessage2.setBody("Chat_Disconnected_Time_Over");
                chatMessage2.setDialogId(dialog.getDialogId());
                chatMessage2.setId(details.getQuickBloxId());
                chatMessage2.setRecipientId(oponentId);
                chatMessage2.setSenderId(Integer.parseInt(details.getQuickBloxId()));
                chatMessage.setDateSent(new Date().getTime() / 1000);

                AppService.privateChat.sendMessage(chatMessage);
                AppService.privateChat.sendMessage(chatMessage2);

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.e("QBResponseException", errors + "");
            }
        });
    }

    private void createChatDialog() {

        QBPrivateChatManager privateChatManager = QBChatService.getInstance().getPrivateChatManager();
        privateChatManager.createDialog(oponentId, new QBEntityCallback<QBDialog>() {
            @Override
            public void onSuccess(QBDialog dialog, Bundle args) {

                QBChatMessage chatMessage = new QBChatMessage();

                chatMessage.setBody("Chat_Disconnected_Form");

                chatMessage.setDialogId(dialog.getDialogId());
                chatMessage.setId(details.getQuickBloxId());
                chatMessage.setRecipientId(oponentId);
                chatMessage.setSenderId(Integer.parseInt(details.getQuickBloxId()));

                chatMessage.setDateSent(new Date().getTime() / 1000);
                if (PrivateChatImpl.chat == null) {
                    AppService.privateChat = PrivateChatImpl.getInstance(oponentId);

                }
                AppService.privateChat.sendMessage(chatMessage);

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.e("QBResponseException", errors + "");
            }
        });
    }


    private View.OnClickListener getCameraSwitchListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QBRTCSession currentSession;


                currentSession = myApp.getCurrentSession();
                if (currentSession == null) {
                    return;
                }
                final QBMediaStreamManager mediaStreamManager = currentSession.getMediaStreamManager();
                if (mediaStreamManager == null) {
                    return;
                }
                boolean cameraSwitched = mediaStreamManager.switchCameraInput(new Runnable() {
                    @Override
                    public void run() {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                toggleCamerainternal(mediaStreamManager);
                            }
                        });
                    }
                });


            }

        };
    }

    private void toggleCamerainternal(QBMediaStreamManager mediaStreamManager) {
        toggleCameraOnUiThread(false);
        int currentCameraId = mediaStreamManager.getCurrentCameraId();
        Log.d(TAG, "Camera was switched!");
        RTCGLVideoView.RendererConfig config = new RTCGLVideoView.RendererConfig();
        config.mirror = CameraUtils.isCameraFront(currentCameraId);
        localVideoView.updateRenderer(isPeerToPeerCall ? RTCGLVideoView.RendererSurface.SECOND :
                RTCGLVideoView.RendererSurface.MAIN, config);
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toggleCameraOnUiThread(true);
            }
        }, TOGGLE_CAMERA_DELAY);
    }

    private void toggleCameraOnUiThread(final boolean toggle) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleCamera(toggle);
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        mainHandler.post(runnable);
    }

    private void toggleCamera(boolean isNeedEnableCam) {
        QBRTCSession currentSession;
        currentSession = myApp.getCurrentSession();
        if (currentSession != null && currentSession.getMediaStreamManager() != null) {
            currentSession.getMediaStreamManager().setVideoEnabled(isNeedEnableCam);
            if (myCameraOff != null) {
                myCameraOff.setVisibility(isNeedEnableCam ? View.VISIBLE : View.INVISIBLE);
            }
            if (switchCameraToggle != null) {
                switchCameraToggle.setVisibility(isNeedEnableCam ? View.INVISIBLE : View.VISIBLE);
            }
        }


    }

    @Override
    public void onLocalVideoTrackReceive(QBRTCSession qbrtcSession, final QBRTCVideoTrack videoTrack) {
        Log.d(TAG, "onLocalVideoTrackReceive() run");

        if (localVideoView != null) {
            fillVideoView(localVideoView, videoTrack, !isPeerToPeerCall);
        } else {
            //localVideoView hasn't been inflated yet. Will set track while OnBindLastViewHolder
            localVideoTrack = videoTrack;
        }
    }

    @Override
    public void onRemoteVideoTrackReceive(QBRTCSession session, QBRTCVideoTrack videoTrack, Integer userID) {
        Log.d(TAG, "onRemoteVideoTrackReceive for opponent= " + userID);
        OpponentsFromCallAdapter.ViewHolder itemHolder = getViewHolderForOpponent(userID);
        if (itemHolder == null) {
            return;
        }
        RTCGLVideoView remoteVideoView = itemHolder.getOpponentView();
        if (remoteVideoView != null) {
            fillVideoView(remoteVideoView, videoTrack);
        }
    }

    //last opponent view is bind
    @Override
    public void OnBindLastViewHolder(OpponentsFromCallAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "OnBindLastViewHolder position=" + position);
        if (!isVideoEnabled) {
            return;
        }
        if (isPeerToPeerCall) {
            localVideoView = holder.getOpponentView();
            initLocalViewUI(holder.itemView);
        } else {
            //on group call we postpone initialization of localVideoView due to set it on Gui renderer.
            // Refer to RTCGlVIew
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    localVideoView = (RTCGLVideoView) ((ViewStub) getView().findViewById(R.id.localViewStub)).inflate();
                    if (localVideoTrack != null) {
                        fillVideoView(localVideoView, localVideoTrack, !isPeerToPeerCall);
                    }
                }
            }, LOCAL_TRACk_INITIALIZE_DELAY);
        }
    }

    private void initLocalViewUI(View localView) {
        initSwitchCameraButton(localView);
        myCameraOff = localView.findViewById(R.id.cameraOff);
    }

    private OpponentsFromCallAdapter.ViewHolder getViewHolderForOpponent(Integer userID) {
        OpponentsFromCallAdapter.ViewHolder holder = opponentViewHolders.get(userID);
        if (holder == null) {
            holder = findHolder(userID);
            if (holder != null) {
                opponentViewHolders.append(userID, holder);
            }
        }
        return holder;
    }

    private OpponentsFromCallAdapter.ViewHolder findHolder(Integer userID) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            OpponentsFromCallAdapter.ViewHolder childViewHolder = (OpponentsFromCallAdapter.ViewHolder) recyclerView.getChildViewHolder(childView);
            Log.d(TAG, "getViewForOpponent holder user id is : " + childViewHolder.getUserId());
            if (userID.equals(childViewHolder.getUserId())) {
                return childViewHolder;
            }
        }
        return null;
    }

    private void fillVideoView(RTCGLVideoView videoView, QBRTCVideoTrack videoTrack, boolean remoteRenderer) {
        videoTrack.addRenderer(new VideoRenderer(remoteRenderer ?
                videoView.obtainVideoRenderer(RTCGLVideoView.RendererSurface.MAIN) :
                videoView.obtainVideoRenderer(RTCGLVideoView.RendererSurface.SECOND)));
        Log.d(TAG, (remoteRenderer ? "remote" : "local") + " Track is rendering");
    }

    private void fillVideoView(RTCGLVideoView videoView, QBRTCVideoTrack videoTrack) {
        fillVideoView(videoView, videoTrack, true);
    }

    private void setStatusForOpponent(int userId, final String status) {
        final OpponentsFromCallAdapter.ViewHolder holder = getViewHolderForOpponent(userId);
        if (holder == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.setStatus(status);
            }
        });


    }

    @Override
    public void onStartConnectToUser(QBRTCSession qbrtcSession, Integer userId) {
        setStatusForOpponent(userId, getString(R.string.checking));
    }

    @Override
    public void onConnectedToUser(QBRTCSession qbrtcSession, final Integer userId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                actionButtonsEnabled(true);
                setStatusForOpponent(userId, getString(R.string.connected));
                String appointmentIdTimeOver = sharedPref.getString("appointmentIdTimeOver", null);

                timeInMilliSeconds = (sharedPref.getLong("appointmentTimeOverTime", 0));

                if (appointmentIdTimeOver != null) {
                    if (appointmentIdTimeOver.equalsIgnoreCase(appointmentId)) {
                        startTimer(timeInMilliSeconds);
                    } else {
                        startTimer(0L);
                    }
                } else {
                    startTimer(0L);
                }

                //  if(timeInMilliSeconds>0){

                //}


            }
        });

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
                        CallingMainActivity.mTitle.setText(timeInHMS);
                        editor.putLong("appointmentTimeOverTime", timeInMilliSeconds);
                        editor.commit();
                        timeInMilliSeconds += 1000;
                    }
                });
            }
        };
    }

    @Override
    public void onConnectionClosedForUser(QBRTCSession qbrtcSession, Integer integer) {
        setStatusForOpponent(integer, getString(R.string.closed));
    }

    @Override
    public void onDisconnectedFromUser(QBRTCSession qbrtcSession, Integer integer) {
        setStatusForOpponent(integer, getString(R.string.disconnected));
    }

    @Override
    public void onDisconnectedTimeoutFromUser(QBRTCSession qbrtcSession, Integer integer) {
        setStatusForOpponent(integer, getString(R.string.time_out));
    }

    @Override
    public void onConnectionFailedWithUser(QBRTCSession qbrtcSession, Integer integer) {
        setStatusForOpponent(integer, getString(R.string.failed));
    }

    @Override
    public void onError(QBRTCSession qbrtcSession, QBRTCException e) {

    }

    @Override
    public void onUserNotAnswer(QBRTCSession session, Integer userId) {
        setStatusForOpponent(userId, getString(R.string.noAnswer));
    }

    @Override
    public void onCallRejectByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo) {
        setStatusForOpponent(userId, getString(R.string.rejected));
    }

    @Override
    public void onCallAcceptByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo) {
        setStatusForOpponent(userId, getString(R.string.accepted));
    }

    @Override
    public void onReceiveHangUpFromUser(QBRTCSession session, Integer userId) {
        setStatusForOpponent(userId, getString(R.string.hungUp));
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.llSpeaker) {
            onCheckedChanged(speakerToggle, speakerToggle.isChecked());
        } else if (vId == R.id.llCamera) {
            onCheckedChanged(cameraToggle, cameraToggle.isChecked());
        } else if (vId == R.id.llmike) {
            onCheckedChanged(micToggleVideoCall, micToggleVideoCall.isChecked());
        } else if (vId == R.id.llEndCall) {
            actionButtonsEnabled(false);
            llEndCall.setEnabled(false);

            editor.commit();
            stoptimertask();
            //stopOutBeep();
            Log.d(TAG, "Call is stopped");
            myApp.hangUpCurrentSession();

            createChatDialog();
            llEndCall.setEnabled(false);
            llEndCall.setActivated(false);
        } else if (vId == R.id.showClinicalDashboard) {
            String url;
            String timezone = Utils.getTimeZoneDifference();
            url = ApiUrls.BASE_URL_WebView+"ClinicalDashBoard.aspx?appId=" + appointmentId + "&docId=" + details.getUser_seq()
                    + "&patientId=" + patientId + "&TimeZone=" + timezone;
            if (activity != null) {
                Utils.callFragmentForAddDoctorCallingMain(activity, new DoctorCallingClinicalDashboard().newInstance(url), DoctorCallingClinicalDashboard.TAG);
            }
        }


    }

    public void endCallingTimeEnds() {
        actionButtonsEnabled(false);
        llEndCall.setEnabled(false);
        stoptimertask();
        //stopOutBeep();
        Log.d(TAG, "Call is stopped");
        myApp.hangUpCurrentSession();

        createChatDialogTimerOver();
        llEndCall.setEnabled(false);
        llEndCall.setActivated(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int vId = buttonView.getId();
        if (vId == R.id.cameraToggle) {
            cameraState = isChecked ? CameraState.ENABLED_FROM_USER : CameraState.DISABLED_FROM_USER;
            toggleCamera(isChecked);
        } else if (vId == R.id.speakerToggle) {
            if (myApp.getCurrentSession() != null && myApp.getCurrentSession().getMediaStreamManager() != null) {
                ((AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE)).setSpeakerphoneOn(isChecked);
            } else {
                speakerToggle.setChecked(speakerToggle.isChecked() ? false : true);
            }
        } else if (vId == R.id.micToggleVideoCall) {
            if (myApp.getCurrentSession() != null && myApp.getCurrentSession().getMediaStreamManager() != null) {
                myApp.getCurrentSession().setAudioEnabled(isChecked);
            } else {
                micToggleVideoCall.setChecked(micToggleVideoCall.isChecked() ? false : true);
            }

        }
    }

    private enum CameraState {
        NONE,
        DISABLED_FROM_USER,
        ENABLED_FROM_USER
    }

    private class AudioStreamReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

			/*if (intent.getAction().equals(AudioManager.ACTION_HEADSET_PLUG)) {
                Log.d(TAG, "ACTION_HEADSET_PLUG " + intent.getIntExtra("state", -1));
            } else*/
            if (intent.getAction().equals(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED)) {
                Log.d(TAG, "ACTION_SCO_AUDIO_STATE_UPDATED " + intent.getIntExtra("EXTRA_SCO_AUDIO_STATE", -2));
            }

            dynamicToggleVideoCall.setChecked(intent.getIntExtra("state", -1) == 1);

        }
    }

    class FragmentLifeCycleHandler extends Handler {

        @Override
        public void dispatchMessage(Message msg) {
            if (isAdded() && getActivity() != null) {
                super.dispatchMessage(msg);
            } else {
                Log.d(TAG, "Fragment under destroying");
            }
        }
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public DividerItemDecoration(Context context, int dimensionDivider) {
            this.space = context.getResources().getDimensionPixelSize(dimensionDivider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(space, space, space, space);
        }

    }
}


