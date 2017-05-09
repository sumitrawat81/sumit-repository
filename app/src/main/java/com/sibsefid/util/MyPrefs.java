package com.sibsefid.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.CountryBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by root on 9/9/16.
 */
public class MyPrefs {

    public static final String MyPREFERENCES = "MyPrefs";

    public static void saveUser(Activity activity, int ID) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("ID", ID);
        editor.commit();
    }

    public static int getUser(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int user = myPre.getInt("ID", 0);
        return user;
    }

    public static void saveCountryList(Activity activity, ArrayList<CountryBean.DataBean> list) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String valueList = gson.toJson(list);
        editor.putString("country_list", valueList);
        editor.commit();
    }

    public static ArrayList<CountryBean.DataBean> getCountryList(Activity activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("country_list", null);

        Type type = new TypeToken<ArrayList<CountryBean.DataBean>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<CountryBean.DataBean> arrayList = gson.fromJson(user, type);
        return arrayList;
    }

    public static void saveLoginDetails(Activity activity, UserLoginDetails.LoginDetails list) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String valueList = gson.toJson(list);
        editor.putString("login_details", valueList);
        editor.commit();
    }

    public static UserLoginDetails.LoginDetails getLoginDetails(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("login_details", null);

        Type type = new TypeToken<UserLoginDetails.LoginDetails>() {
        }.getType();
        Gson gson = new Gson();
        UserLoginDetails.LoginDetails arrayList = gson.fromJson(user, type);
        return arrayList;
    }

    public static void saveUserEmail(Activity activity, String ID) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Email", ID);
        editor.commit();
    }

    public static void saveUserDeviceToken(Context activity, String token) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("DeviceToken", token);
        editor.commit();
    }

    public static String getUserDeviceToken(Activity activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("DeviceToken", "");
        return user;
    }

    public static String getUserEmail(Activity activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("Email", "");
        return user;
    }

    public static void saveUserPassword(Activity activity, String ID) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Password", ID);
        editor.commit();
    }

    public static String getUserPassword(Activity activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("Password", "");
        return user;
    }

    public static void saveUserMsgCountl(Context activity, String count) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("msgCount", count);
        editor.commit();
    }

    public static String getUserMsgCount(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("msgCount", "0");
        return user;
    }

    public static void saveUserNotiicationCountl(Context activity, String count) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("notificationCount", count);
        editor.commit();
    }

    public static String getUserNotificationCount(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = myPre.getString("notificationCount", "0");
        return user;
    }

    public static void saveUserTimeOut(Activity activity, Long time) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong("appointmentTimeOverTime", time);
        editor.commit();
    }

    public static Long getUserTimeOut(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Long userTime = myPre.getLong("appointmentTimeOverTime", 0);
        return userTime;
    }
    public static void saveUserSelectedLanguage(Activity activity, String lang) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("selectedLanguage", lang);
        editor.commit();
    }

    public static String getUserSelectedLanguage(Context activity) {
        SharedPreferences myPre = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String language = myPre.getString("selectedLanguage", "0");
        return language;
    }
}
