package com.sibsefid;

import android.util.Log;

import com.sibsefid.services.AppService;
import com.sibsefid.util.MyPrefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ubuntu on 18/11/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        AppService.deviceToken = refreshedToken;
        MyPrefs.saveUserDeviceToken(getApplicationContext(), refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        MyPrefs.saveUserDeviceToken(getApplicationContext(), token);
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}