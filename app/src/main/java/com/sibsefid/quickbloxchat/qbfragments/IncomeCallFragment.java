package com.sibsefid.quickbloxchat.qbfragments;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.quickbloxchat.CallingMainActivity;
import com.sibsefid.quickbloxchat.definitions.Consts;
import com.sibsefid.quickbloxchat.utils.RingtonePlayer;
import com.sibsefid.services.AppService;
import com.sibsefid.util.Utils;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCSessionDescription;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * QuickBlox team
 */
public class IncomeCallFragment extends Fragment implements Serializable, View.OnClickListener {

    private static final String TAG = IncomeCallFragment.class.getSimpleName();
    private static final long CLICK_DELAY = TimeUnit.SECONDS.toMillis(2);
    public boolean isAccepted;
    String fullName;
    private TextView incVideoCall;
    private TextView incAudioCall;
    private TextView callerName;
    private TextView otherIncUsers;
    private LinearLayout relEndCall;
    private LinearLayout relRecCall;
    private ArrayList<Integer> opponents;
    private List<QBUser> opponentsFromCall = new ArrayList<>();
    private QBRTCSessionDescription sessionDescription;
    private Vibrator vibrator;
    private QBRTCTypes.QBConferenceType conferenceType;
    private int qbConferenceType;
    private View view;
    private long lastCliclTime = 0l;
    private RingtonePlayer ringtonePlayer;
    private AppService myApp;
    private View toolBarView;
    private TextView txtTileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        myApp = AppService.getInstance();
        isAccepted = false;
        if (getArguments() != null) {
            opponents = getArguments().getIntegerArrayList("opponents");
            sessionDescription = (QBRTCSessionDescription) getArguments().getSerializable("sessionDescription");
            qbConferenceType = getArguments().getInt(Consts.CONFERENCE_TYPE);
            conferenceType =
                    qbConferenceType == 1 ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO :
                            QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
            Log.d(TAG, conferenceType.toString() + "From onCreateView()");
        }
        if (savedInstanceState == null) {
            view = inflater.inflate(R.layout.fragment_income_call, container, false);
            try {
                initUI(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setDisplayedTypeCall(conferenceType);
            initButtonsListener();
        }
        ringtonePlayer = new RingtonePlayer(getActivity());
        //toolBarView = ((ActLandingScreen) getActivity()).setToolBarLayout(R.layout.header_with_title);
        initToolBarView();
        return view;
    }

    private void initToolBarView() {
        //txtTileName = (TextView) toolBarView.findViewById(R.id.txtTileName);
        //txtTileName.setText("");
        /*((ActLandingScreen) getActivity()).closeDrawer();
				((ActLandingScreen) getActivity()).disableSlideBar(false);*/
        callerName.post(new Runnable() {
            @Override
            public void run() {
                Utils.hideKeyBoardMethod(getActivity(), callerName);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        startCallNotification();
    }

    private void initButtonsListener() {
        relEndCall.setOnClickListener(this);
        relRecCall.setOnClickListener(this);
    }

    private void initUI(View view) {
        incAudioCall = (TextView) view.findViewById(R.id.incAudioCall);
        incVideoCall = (TextView) view.findViewById(R.id.incVideoCall);

        callerName = (TextView) view.findViewById(R.id.callerName);
        try {
            callerName.setText(getCallerName(myApp.getCurrentSession()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        otherIncUsers = (TextView) view.findViewById(R.id.otherIncUsers);
        otherIncUsers.setText(getOtherIncUsersNames(opponents));
        relEndCall = (LinearLayout) view.findViewById(R.id.relEndCall);
        relRecCall = (LinearLayout) view.findViewById(R.id.relRecCall);
    }

    private String getCallerName(QBRTCSession session) {
        fullName = new String();
        int i = session.getCallerID();
        Log.e("callerId=", i + "");
        QBUsers.getUser(i, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                fullName = qbUser.getFullName().toString();
                Log.e("callerNme TakenOut=", fullName);
                //CommonFunction.callerName=s;
                callerName.setText(fullName);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("error in getting user", e.toString());
            }
        });


        return fullName;
    }


    private void enableButtons(boolean enable) {
        relRecCall.setEnabled(enable);
        relEndCall.setEnabled(enable);
    }

    public void startCallNotification() {

        ringtonePlayer.play(false);

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        long[] vibrationCycle = {0, 1000, 1000};
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(vibrationCycle, 1);
        }

    }

    private void stopCallNotification() {
        if (ringtonePlayer != null) {
            ringtonePlayer.stop();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private String getOtherIncUsersNames(ArrayList<Integer> opponents) {
        StringBuffer s = new StringBuffer("");
        opponents.remove(QBChatService.getInstance().getUser().getId());
        for (Integer i : opponents) {
            for (QBUser usr : opponentsFromCall) {
                if (usr.getId().equals(i)) {
                    if (opponents.indexOf(i) == (opponents.size() - 1)) {
                        s.append(usr.getFullName() + " ");
                        break;
                    } else {
                        s.append(usr.getFullName() + ", ");
                    }
                }
            }
        }
        return s.toString();
    }


    private void setDisplayedTypeCall(QBRTCTypes.QBConferenceType conferenceType) {
        if (conferenceType == QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO) {
            incVideoCall.setVisibility(View.VISIBLE);
            incAudioCall.setVisibility(View.INVISIBLE);
        } else if (conferenceType == QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO) {
            incVideoCall.setVisibility(View.INVISIBLE);
            incAudioCall.setVisibility(View.VISIBLE);
        }
    }

    public void onStop() {
        stopCallNotification();
        super.onStop();
        Log.d(TAG, "onDestroy() from IncomeCallFragment");
		/*if(!ApplicationLifecycleHandler.isInBackground){
			System.exit(0);
		}*/
    }

    @Override
    public void onClick(View v) {
        if ((SystemClock.uptimeMillis() - lastCliclTime) < CLICK_DELAY) {
            return;
        }
        lastCliclTime = SystemClock.uptimeMillis();
        switch (v.getId()) {
            case R.id.relEndCall:
                reject();
                break;
            case R.id.relRecCall:
                accept();
                break;
            default:
                break;
        }
    }

    private void accept() {
        isAccepted = true;
        relRecCall.setClickable(false);
        stopCallNotification();
        ((CallingMainActivity) getActivity())
                .addConversationFragmentReceiveCall(fullName);
    }

    private void reject() {
        isAccepted = false;
        relEndCall.setClickable(false);
        Log.d(TAG, "Call is rejected");
        stopCallNotification();
        myApp.rejectCurrentSession();
		/*int count=getActivity().getSupportFragmentManager().getBackStackEntryCount();
		Log.e("frgCount", "frgCount value: " + count);
		if(count>2)
		getActivity().onBackPressed();
		else
		{
			Intent mainSecreen=new Intent(getActivity(),ActLandingScreen.class);
			mainSecreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mainSecreen);
		}
*/

    }
}
