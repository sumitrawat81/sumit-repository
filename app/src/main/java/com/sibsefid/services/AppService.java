package com.sibsefid.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.sibsefid.OnDataNotify;
import com.sibsefid.R;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.quickbolx_model.CustomQB_UserModel;
import com.sibsefid.quickbloxchat.CallingMainActivity;
import com.sibsefid.quickbloxchat.chat.PrivateChatImpl;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.RingtonePlayer;
import com.sibsefid.util.Utils;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBSignaling;
import com.quickblox.chat.QBWebRTCSignaling;
import com.quickblox.chat.listeners.QBVideoChatSignalingManagerListener;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCConfig;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.quickblox.videochat.webrtc.QBSignalingSpec;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionConnectionCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSignalingCallback;
import com.quickblox.videochat.webrtc.exception.QBRTCException;
import com.quickblox.videochat.webrtc.exception.QBRTCSignalException;

import org.webrtc.VideoCapturerAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ubuntu on 27/8/16.
 */
public class AppService extends Service implements QBRTCClientSessionCallbacks, QBRTCSessionConnectionCallbacks, QBRTCSignalingCallback {

    // public static GroupChatImpl groupChat;
    public static final String INCOME_CALL_FRAGMENT = "income_call_fragment";
    public static final String CONVERSATION_CALL_FRAGMENT = "conversation_call_fragment";
    public static final String CALLER_NAME = "caller_name";
    public static final String SESSION_ID = "sessionID";
    public static final String START_CONVERSATION_REASON = "start_conversation_reason";
    public static AppService sInstance = null;
    public static boolean isInApplication;
    public static String deviceToken = "";
    public static QBRTCClient rtcClient = null;
    public static QBChatService chatService;
    public static PrivateChatImpl privateChat;
    public static Handler RecentupdateHandler;
    public static OnDataNotify onDataNotify;
    public static int msgCounter = 0;
    private static boolean activityVisible;
    /**
     * QB variables
     */

    private static UserLoginDetails.LoginDetails details;
    public boolean isInCommingCall;
    int LOGINTYPE;
    public static QBRTCSession currentSession;
    private AppService.QBRTCSessionUserCallback sessionUserCallback;
    private RingtonePlayer ringtonePlayer;
    private QBRTCTypes.QBConferenceType qbConferenceType = null;

    public static void loginWithInIt() {
        if (Utils.isDeviceOnline(sInstance)) {

            Utils.initlization(sInstance);
            if (details != null && details.getQuickBloxId() != null) {
                try {
                    AppService.quickBoxLogin(details.getEmail(), details.getEmail(), details.getQuickblogpassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            sInstance = null;
        }

    }

    public static void quickBoxLogin(final String email1, final String userName, final String password) {
        if (userName != null && !userName.isEmpty()) {
            final QBUser usercreate = new QBUser(userName, password);
            usercreate.setEmail(email1);
            usercreate.setId(Integer.valueOf(details.getQuickBloxId()));
            chatService = QBChatService.getInstance();

            chatService.setDefaultPacketReplyTimeout(150000);
            QBAuth.createSession(usercreate, new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession session, Bundle bundle) {
                    Log.e("loginQuickblox", "onSuccess QuickBloxUser");

                    Log.e("loginQuickbloxId", session.getUserId() + "");


                    QBUsers.signIn(usercreate, new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(final QBUser user, Bundle params) {
                            Log.e("SuccessSignin", "Signin Success");
                        }

                        @Override
                        public void onError(QBResponseException errors) {
                            Toast.makeText(sInstance, "error in connection", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


                @Override
                public void onError(QBResponseException arg0) {
                    // TODO Auto-generated method stub
                    Log.e("qbError", arg0.toString());
                    // Audio & Video chat Handlear
                   /* if (ActLandingScreen.callBackApplication != null) {
                        Message msg = ActLandingScreen.callBackApplication.obtainMessage();
                        ActLandingScreen.callBackApplication.sendMessage(msg);
                    }*/
                }


            });

            loginForChat(usercreate);
        }
    }

    private static void loginForChat(QBUser usercreate) {
        if (chatService.isLoggedIn()) {
            initQBRTCClient();
            Log.e("ChatAlreadyLogin", "onSuccess login to chat with quickBlox");
         /*   if (ActLandingScreen.callBackApplication != null) {
                Message msg = ActLandingScreen.callBackApplication.obtainMessage();
                ActLandingScreen.callBackApplication.sendMessage(msg);
            } else if (FrgChatWindow.chatCallBack != null) {
                Message msg = FrgChatWindow.chatCallBack.obtainMessage();
                Bundle b = new Bundle();
                b.putString("message", "message");
                msg.setData(b);
                FrgChatWindow.chatCallBack.sendMessage(msg);
            }*/
        } else {
            chatService.login(usercreate, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser result, Bundle params) {
                    Log.e("ChatLogin", "onSuccess login to chat with quickBlox");
                    initQBRTCClient();

                    AppService.privateChat = PrivateChatImpl.getInstance(0);

                }

                @Override
                public void onError(QBResponseException arg0) {
                    // TODO Auto-generated method stub

                    Log.e("error in chatService", arg0.toString());

                    initQBRTCClient();
                    AppService.privateChat = PrivateChatImpl.getInstance(0);
                }

            });
        }

    }

    private static void initQBRTCClient() {
        if (rtcClient == null) {
            try {
                rtcClient = QBRTCClient.getInstance(sInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AppService.chatService.getVideoChatWebRTCSignalingManager().addSignalingManagerListener(new QBVideoChatSignalingManagerListener() {
                @Override
                public void signalingCreated(QBSignaling qbSignaling, boolean createdLocally) {
                    if (!createdLocally) {
                        rtcClient.addSignaling((QBWebRTCSignaling) qbSignaling);
                        Log.e("rtcSignaling", "insideRTC");
                    }
                }
            });
            rtcClient.setCameraErrorHendler(new VideoCapturerAndroid.CameraErrorHandler() {
                @Override
                public void onCameraError(final String s) {
                    Log.e("rtc onCameraError", "onCameraError");
                }
            });
            QBRTCConfig.setMaxOpponentsCount(6);
            QBRTCConfig.setDisconnectTime(20);
            QBRTCConfig.setAnswerTimeInterval(60l);
            QBRTCConfig.setDebugEnabled(true);
            rtcClient.addSessionCallbacksListener(sInstance);
            rtcClient.prepareToProcessCalls();
        }

    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public static synchronized AppService getInstance() {
        if (sInstance == null) {
            sInstance = new AppService();
        }
        return sInstance;
    }

    public static ArrayList<Integer> getOpponentsIds(List<QBUser> opponents) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (QBUser user : opponents) {
            ids.add(user.getId());
        }
        return ids;
    }

    public static ArrayList<Integer> getOpponentsCustomIds(List<CustomQB_UserModel> opponents) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (CustomQB_UserModel user : opponents) {
            ids.add(user.getId());
        }
        return ids;
    }

    /**
     * Check network is avalable or not
     * Created by Ramnivas Singh on 04-08-2015
     *
     * @return
     */
    public static boolean isDeviceOnline() {
        boolean isDeviceOnLine = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) sInstance
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                isDeviceOnLine = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isDeviceOnLine = false;
        }

        return isDeviceOnLine;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        details = MyPrefs.getLoginDetails(this);
        LOGINTYPE = MyPrefs.getUser(this);
        qbBasickInitialization();
        Log.e("onCreate Service", "onCreate");

    }

    public void qbBasickInitialization() {
        if (sInstance == null) {
            sInstance = this;
            loginWithInIt();
        } else {
            loginWithInIt();
        }

    }

    @Override
    public void onDestroy() {
        sInstance = null;
        Log.e("Service DestroyCall", "Service DestroyCall");
        super.onDestroy();

    }

    @Override
    public void onReceiveNewSession(QBRTCSession session) {

        isInCommingCall = true;
        Log.e("callRecived", "new call Recived");
        CallingMainActivity.session = session;

        if (isInApplication) {

            Intent calling = new Intent(this, CallingMainActivity.class);
            calling.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            calling.putExtra("calling", "r_calling");

            startActivity(calling);
        } else {

            Intent calling = new Intent(this, CallingMainActivity.class);
            calling.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            calling.putExtra("calling", "r_calling");

            startActivity(calling);
        }


    }

    public void setIsInCommingCall(boolean isInCommingCall) {
        this.isInCommingCall = isInCommingCall;
    }

    @Override
    public void onUserNotAnswer(QBRTCSession session, Integer userID) {
        if (!session.equals(getCurrentSession())) {
            return;
        }
        if (sessionUserCallback != null) {
            sessionUserCallback.onUserNotAnswer(session, userID);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ringtonePlayer.stop();
            }
        }).start();
        //getApplicationContext().start();
    }

    public QBRTCSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(QBRTCSession currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public void onCallRejectByUser(QBRTCSession session, Integer userID, Map<String, String> userInfo) {

        if (!session.equals(getCurrentSession())) {
            return;
        }
        if (sessionUserCallback != null) {
            /*	DataHolder_Returns.opponent_user_Data.setId(Integer.parseInt(userInfo.get("opponent_id")));
            DataHolder_Returns.opponent_user_Data.setFullName(userInfo.get("opponent_email"));*/
            sessionUserCallback.onCallRejectByUser(session, userID, userInfo);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ringtonePlayer.stop();
            }
        }).start();

    }

    @Override
    public void onCallAcceptByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo) {
        if (!session.equals(getCurrentSession())) {
            return;
        }
        if (sessionUserCallback != null) {

			/*	DataHolder_Returns.opponent_user_Data.setId(Integer.parseInt(userInfo.get("opponent_id")));
            DataHolder_Returns.opponent_user_Data.setFullName(userInfo.get("opponent_email"));*/

            sessionUserCallback.onCallAcceptByUser(session, userId, userInfo);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ringtonePlayer.stop();
            }
        }).start();
    }

    @Override
    public void onReceiveHangUpFromUser(QBRTCSession session, Integer userID) {

    }

    @Override
    public void onUserNoActions(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onSessionClosed(final QBRTCSession session) {
        CallingMainActivity.session = session;
        if (CallingMainActivity.SessionCloseCallBack != null) {
            Message msg = CallingMainActivity.SessionCloseCallBack.obtainMessage();
            Bundle b = new Bundle();
            b.putString("session_id", session.getSessionID());
            msg.setData(b);
            CallingMainActivity.SessionCloseCallBack.sendMessage(msg);
        }
    }

    @Override
    public void onSessionStartClose(final QBRTCSession session) {

        session.removeSessionnCallbacksListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*ConversationFragment fragment =ConversationFragment.newInstance();
                if (fragment != null && session.equals(getCurrentSession())) {
                    fragment.actionButtonsEnabled(false);
                    // Back_Container.SessionCloseCallBack
                }*/
            }
        });
    }

    @Override
    public void onStartConnectToUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onConnectedToUser(QBRTCSession qbrtcSession, Integer integer) {
        forbidenCloseByWifiState();


    }

    private void forbidenCloseByWifiState() {
        //closeByWifiStateAllow = false;
    }

    @Override
    public void onConnectionClosedForUser(QBRTCSession qbrtcSession, Integer integer) {
        Log.e("userDisconnected", "userDisconnected");

    }

    @Override
    public void onDisconnectedFromUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onDisconnectedTimeoutFromUser(QBRTCSession qbrtcSession, Integer integer) {

    }

    @Override
    public void onConnectionFailedWithUser(QBRTCSession qbrtcSession, Integer integer) {
        Log.e("userDisconnected", "userDisconnected");
    }

    @Override
    public void onError(QBRTCSession qbrtcSession, QBRTCException e) {

    }

    @Override
    public void onSuccessSendingPacket(QBSignalingSpec.QBSignalCMD qbSignalCMD, Integer integer) {

    }

    @Override
    public void onErrorSendingPacket(QBSignalingSpec.QBSignalCMD qbSignalCMD, Integer integer, QBRTCSignalException e) {

    }

    public void callUser(List<QBUser> opponents,
                         QBRTCTypes.QBConferenceType qbConferenceType,
                         Map<String, String> userInfo) {

        if (rtcClient != null) {
            if (opponents != null) {
                QBRTCSession newSessionWithOpponents = rtcClient.createNewSessionWithOpponents(
                        getOpponentsIds(opponents), qbConferenceType);
                Log.d("Crash", "addConversationFragmentStartCall. Set session " + newSessionWithOpponents);

                initCurrentSession(newSessionWithOpponents);

                userInfo.put("any_custom_data", "some data");
                userInfo.put("userName", "avatar_reference");

                ringtonePlayer = new RingtonePlayer(this, R.raw.beep);
                ringtonePlayer.play(true);

            } else {
                Log.e("check opponents", "opponents null");
            }
        } else {
            Log.e("check rtcClient", "rtcClient null");
        }

    }

    public QBRTCClient getRtcClient() {
        return rtcClient;
    }

    public RingtonePlayer getRingtonePlayer() {
        return ringtonePlayer;
    }

    public void setRingtonePlayer(RingtonePlayer ringtonePlayer) {
        this.ringtonePlayer = ringtonePlayer;
    }

    public void addVideoTrackCallbacksListener(QBRTCClientVideoTracksCallbacks videoTracksCallbacks) {
        if (currentSession != null) {
            currentSession.addVideoTrackCallbacksListener(videoTracksCallbacks);
        }
    }

    public void addTCClientConnectionCallback(QBRTCSessionConnectionCallbacks clientConnectionCallbacks) {
        if (currentSession != null) {
            currentSession.addSessionCallbacksListener(clientConnectionCallbacks);
        }
    }

    public void addRTCSessionUserCallback(AppService.QBRTCSessionUserCallback sessionUserCallback) {
        sessionUserCallback = sessionUserCallback;
    }

    public void removeRTCClientConnectionCallback(QBRTCSessionConnectionCallbacks clientConnectionCallbacks) {
        if (currentSession != null) {
            currentSession.removeSessionnCallbacksListener(clientConnectionCallbacks);
        }
    }

    public void removeRTCSessionUserCallback(AppService.QBRTCSessionUserCallback sessionUserCallback) {
        sessionUserCallback = null;
    }

    public void hangUpCurrentSession() {
        if (ringtonePlayer != null) {
            ringtonePlayer.stop();
        }
        if (getCurrentSession() != null) {
            getCurrentSession().hangUp(new HashMap<String, String>());
        }
    }

    public void rejectCurrentSession() {
        Log.e("1 Call disconnect ", "1 call disconnect ");
        if (getCurrentSession() != null) {
            Log.e("Call disconnect ", "call disconnect ");
            getCurrentSession().rejectCall(new HashMap<String, String>());
        }
    }

    public void addOpponentsFragment() {

    }

    public void releaseCurrentSession() {
        this.currentSession.removeSessionnCallbacksListener(this);
        this.currentSession.removeSignalingCallback(this);
        this.currentSession = null;

    }

    public void initCurrentSession(QBRTCSession sesion) {
        this.currentSession = sesion;
        this.currentSession.addSessionCallbacksListener(this);
        this.currentSession.addSignalingCallback(this);
    }

    public enum StartConversetionReason {
        INCOME_CALL_FOR_ACCEPTION,
        OUTCOME_CALL_MADE
    }

    public interface QBRTCSessionUserCallback {
        void onUserNotAnswer(QBRTCSession session, Integer userId);

        void onCallRejectByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo);

        void onCallAcceptByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo);

        void onReceiveHangUpFromUser(QBRTCSession session, Integer userId);
    }

}
