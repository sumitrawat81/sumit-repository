package com.sibsefid.rememberthedate;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.sibsefid.R;
import com.zendesk.sdk.feedback.impl.BaseZendeskFeedbackConfiguration;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zopim.android.sdk.api.ZopimChat;

public class Global extends Application {

    private final static String LOG_TAG = Global.class.getSimpleName();

    /* Here is zopim details->

             1. e911md@gmail.com
     pass-> Lowell!!*/
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        if ("replace_me_chat_account_id".equals(getString(R.string.zopim_account_id))) {
            Log.w(LOG_TAG, "==============================================================================================================");
            Log.w(LOG_TAG, "Zopim chat is not connected to an account, if you wish to try chat please add your Zopim accountId to 'zd.xml'");
            Log.w(LOG_TAG, "==============================================================================================================");
        }

        ZendeskConfig.INSTANCE.init(this, getResources().getString(R.string.zd_url), getResources().getString(R.string.zd_appid), getResources().getString(R.string.zd_oauth));

        ZendeskConfig.INSTANCE.setContactConfiguration(new BaseZendeskFeedbackConfiguration() {
            @Override
            public String getRequestSubject() {
                return "Save The Date";
            }
        });

        ZopimChat.init(getString(R.string.zopim_account_id));
    }
}
