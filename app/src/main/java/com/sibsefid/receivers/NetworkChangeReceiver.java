package com.sibsefid.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sibsefid.quickbloxchat.interfaces.NetworkConnected;
import com.sibsefid.services.AppService;
import com.sibsefid.util.ExtraConstants;
import com.sibsefid.util.NetworkUtil;
import com.sibsefid.util.Utils;


public class NetworkChangeReceiver extends BroadcastReceiver {
    public static NetworkConnected networkConnected;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        if (!status.equals(ExtraConstants.NO_INTERNET_CONNECTION)) {
            if (AppService.getInstance() != null) {
                AppService.loginWithInIt();
                if (networkConnected != null)
                    networkConnected.networkConnected(true);
            } else {
                Utils.isMyServiceRunning(context, AppService.class);
            }

        }
    }
}
