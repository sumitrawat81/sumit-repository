package com.sibsefid;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.sibsefid.services.AppService;
import com.sibsefid.util.Utils;
import com.facebook.stetho.Stetho;

/**
 * Created by root on 12/9/16.
 */
public class MedicoApp extends Application {

    public static final String INCOME_CALL_FRAGMENT = "income_call_fragment";
    public static MedicoApp sInstance;
    public static DownloadManager mgr;

    public static synchronized MedicoApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);

        mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        sInstance = this;
        Utils.isMyServiceRunning(this, AppService.class);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplicationContext());
        MultiDex.install(getBaseContext());
        MultiDex.install(this);
    }

}
