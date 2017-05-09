package com.sibsefid.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.LoginActivity;
import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.model.doctor.NewCountryBean;
import com.sibsefid.model.patient.TimeZoneModel;
import com.sibsefid.quickbloxchat.CallingMainActivity;
import com.sibsefid.quickbloxchat.utils.QBConstants;
import com.sibsefid.services.AppService;
import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.FlipAnimation;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.LogLevel;
import com.quickblox.core.QBSettings;
import com.quickblox.core.ServiceZone;
import com.quickblox.videochat.webrtc.QBRTCConfig;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by root on 3/9/16.
 */
public class Utils {

    private static final long DURATION = 500;
    public static String message;
    private static ProgressDialog progressDialog;
    private static Dialog confirmation;
    public static final String UTC_TIMEZONE = "UTC";
    public static final String COMINGDATEFORMATFROMSERVER = "yyyy-MM-dd HH:mm:ssz";
    public static final String SHOW_TIMEFORMATES = "HH:mm:ssz";
    public static String landSend="en";
    public static void callFragmentReplace(LoginActivity activity, Fragment fragment, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.login_container, fragment, TAG);
            transaction.commit();
        }
    }


    public static void callFragmentForAdd(LoginActivity activity, Fragment fragment, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.login_container, fragment, TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public static void callFragmentReplacePatient(PatientActivity activity, Fragment fragment, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.patient_container, fragment, TAG);
            transaction.commit();
        }
    }


    public static void callFragmentForAddPatient(PatientActivity activity, Fragment fragment, String TAG) {

        activity.backShow();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.patient_container, fragment, TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {


            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            FragmentTransaction transaction2 = manager.beginTransaction();
            transaction2.add(R.id.patient_container, fragment, TAG);
            transaction2.addToBackStack(null);
            transaction2.commit();
        }
    }


    public static void callFragmentReplaceDoctor(DoctorActivity activity, Fragment fragment, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_ReplaceBy, fragment, TAG);
            transaction.commit();
        }
    }


    public static void callFragmentForAddDoctor(DoctorActivity activity, Fragment fragment, String TAG) {

        activity.backShow();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frame_ReplaceBy, fragment, TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {


            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            FragmentTransaction transaction2 = manager.beginTransaction();
            transaction2.add(R.id.frame_ReplaceBy, fragment, TAG);
            transaction2.addToBackStack(null);
            transaction2.commit();
        }
    }

    public static void callFragmentForAddDoctorCallingMain(CallingMainActivity activity, Fragment fragment, String TAG) {

        // activity.backShow();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.calling_container, fragment, TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {


            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            FragmentTransaction transaction2 = manager.beginTransaction();
            transaction2.add(R.id.calling_container, fragment, TAG);
            transaction2.addToBackStack(null);
            transaction2.commit();
        }
    }

    public static void callFragmentReplaceQb(CallingMainActivity activity, Fragment fragment, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.calling_container, fragment, TAG);
            transaction.commit();
        }
    }

    public static void logout(Activity activity, int ID) {
        MyPrefs.saveUser(activity, ID);
        MyPrefs.saveLoginDetails(activity, null);
    }


    public static boolean isDeviceOnline(Context context) {
        boolean isConnectionAvail = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnectionAvail;
    }


    public static Animation annimationFragment(int transit, boolean enter, int nextAnim) {
        Animation animation = enter ? FlipAnimation.create(FlipAnimation.RIGHT, enter, DURATION) :
                CubeAnimation.create(CubeAnimation.RIGHT, enter, DURATION).fading(1.0f, 0.3f);
        return animation;
    }


    public static Object[] loadJSONFromAssetCountry(Activity activity) {
        String json = null;
        Object[] mobArray = new Object[2];
        NewCountryBean countryLists = new NewCountryBean();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListCodeId = new ArrayList<>();
        try {
            InputStream is = activity.getAssets().open("country_code_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            countryLists = gson.fromJson(json.toString(), NewCountryBean.class);
            for (int i = 0; i < countryLists.getCountrylist().size(); i++) {
                String code = countryLists.getCountrylist().get(i).getPhonecode().toString().replace("[", "").replace("]", "");
                // arrayList.add("+" + code + " " + countryLists.getCountrylist().get(i).getNicename());
                arrayList.add(countryLists.getCountrylist().get(i).getPhonecode1());
                arrayListCodeId.add(countryLists.getCountrylist().get(i).getId());

            }
            mobArray[0] = arrayList;
            mobArray[1] = arrayListCodeId;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return mobArray;

    }


    public static ArrayList<TimeZoneModel.TimeZoneBean> timeZoneObject(Activity activity) {
        String json = null;
        ArrayList<TimeZoneModel.TimeZoneBean> mobArray = new ArrayList<>();
        TimeZoneModel zoneModel = new TimeZoneModel();

        try {
            InputStream is = activity.getAssets().open("time_zone.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            zoneModel = gson.fromJson(json.toString(), TimeZoneModel.class);
            mobArray = zoneModel.getTimezonebean();


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return mobArray;

    }

    public static String getTimeZoneDifference() {
        TimeZone tz = TimeZone.getDefault();
        //String current_Time_Zone = (TimeZone.getTimeZone(tz.getID()).getDisplayName(false, TimeZone.SHORT));
        String current_Time_Zone;
        try {

            Calendar mCalendar = new GregorianCalendar();
            TimeZone mTimeZone = mCalendar.getTimeZone();
            int mGMTOffset = mTimeZone.getRawOffset();
            current_Time_Zone = String.format("%s%02d:%02d",
                    mGMTOffset < 0 ? "-" : "+",
                    Math.abs(mGMTOffset) / 3600000,
                    Math.abs(mGMTOffset) / 60000 % 60);

            // current_Time_Zone = current_Time_Zone.split("GMT")[1];
        } catch (Exception e) {
            e.printStackTrace();
            current_Time_Zone = "+05:30";
        }
        Log.d("Time zone", "=" + current_Time_Zone);
        return current_Time_Zone;
    }

    public static void showCustomToast(String msg, Context context) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }


    public static void isMyServiceRunning(Context ctx, Class<?> serviceClass) {

        if (AppService.sInstance == null)

        {
            Intent i = new Intent(ctx, serviceClass);
            i.putExtra("KEY1", "Value to be used by the service");
            ctx.startService(i);
        } else {
            Log.e("NotInStartComandServc", "NotInStartCommand");
            initlization(ctx);
        }


    }

   /* 1. Enable debugging mode in your app and record FULL logs from the start of application till the issue occurs:

            qbSettings.setLogLevel(LogLevel.DEBUG);
    QBChatService.setDebugEnabled(true);
    QBRTCConfig.setDebugEnabled(true);

    The above lines should be included before creating QB session. Logs can be attached as .txt file

    2. Your WebRTC and Android SDK versions.*/

    public static void initlization(Context ctx) {
        QBSettings.getInstance().init(ctx, QBConstants.APP_ID, QBConstants.AUTH_KEY, QBConstants.AUTH_SECRET);
        QBSettings.getInstance().setEndpoints(QBConstants.API_DOAMIN, QBConstants.CHAT_DOMAIN, ServiceZone.DEVELOPMENT);
        QBSettings.getInstance().setZone(ServiceZone.DEVELOPMENT);
        QBSettings.getInstance().setLogLevel(LogLevel.DEBUG);
        QBChatService.setDebugEnabled(true);
        QBRTCConfig.setDebugEnabled(true);

    }

    public static boolean isDeviceOnline(Context ctx, boolean showErroDialg) {
        boolean isDeviceOnLine = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx
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
        if (!isDeviceOnLine && showErroDialg) {
            Toast.makeText(ctx, "No Internt Connection", Toast.LENGTH_SHORT).show();
        }
        return isDeviceOnLine;
    }

    public static void hideKeyBoardMethod(Context con, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Dialog showProDialog(final Activity act, String msg) {
        try {
            message = msg;
            Handler refresh = new Handler(Looper.getMainLooper());
            refresh.post(new Runnable() {
                public void run() {
                    if (act != null) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            try{
                                progressDialog.dismiss();
                            }catch (Exception e){
                                Log.e("error in dialog dismiss",e.toString());
                            }

                        }
                        progressDialog = new ProgressDialog(act);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminateDrawable(act.getResources().getDrawable(R.drawable.pbar));
                        if (message == null) {
                            message = act.getString(R.string.wait);
                        }
                        progressDialog.setMessage(message);
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        try {
                            progressDialog.show();
                        }
                        catch (Exception e){
                            Log.e("progress dismiss error",e.toString());
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }

    public static Dialog dismissProDialog() {
        try {

            if (progressDialog != null) {
                progressDialog.hide();
            }
            progressDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }


    public static Dialog showOkDialog(Activity act, View.OnClickListener leftClick, String message) {

        if (act != null) {
            try {
                if (confirmation != null && confirmation.isShowing()) {
                    confirmation.hide();
                }
                confirmation = new Dialog(act,
                        android.R.style.Theme_Translucent_NoTitleBar);
                confirmation.setContentView(R.layout.show_ok_dialog);
                TextView txtDescription_auth, txtOk_auth;
                txtOk_auth = (TextView) confirmation.findViewById(R.id.txtOk_auth);
                txtDescription_auth = (TextView) confirmation.findViewById(R.id.txtDescription_auth);

                txtDescription_auth.setText(message);
                if (leftClick != null) {
                    txtOk_auth.setOnClickListener(leftClick);
                } else {
                    txtOk_auth.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            confirmation.hide();
                            confirmation = null;
                        }
                    });
                    confirmation.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            confirmation = null;
                        }
                    });

                    confirmation.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                            confirmation = null;
                        }
                    });
                }
                confirmation.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return confirmation;
        } else {
            return null;
        }
    }


    public static Object[] loadJSONFromAsset(Activity activity) {
        String json = null;
        Object[] mobArray = new Object[2];
        NewCountryBean countryLists = new NewCountryBean();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListCodeId = new ArrayList<>();
        try {
            InputStream is = activity.getAssets().open("country_code_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            countryLists = gson.fromJson(json.toString(), NewCountryBean.class);
            for (int i = 0; i < countryLists.getCountrylist().size(); i++) {
                arrayList.add(countryLists.getCountrylist().get(i).getPhonecode1());
                arrayListCodeId.add(countryLists.getCountrylist().get(i).getId());

            }
            mobArray[0] = arrayList;
            mobArray[1] = arrayListCodeId;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return mobArray;

    }

    public static void CustomToast(Context context, View v) {

        LayoutInflater li = LayoutInflater.from(context);
        View layout = li.inflate(R.layout.comming_soon_custom_toast,
                (ViewGroup) v.findViewById(R.id.custom_toast));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(context.getResources().getString(R.string.cooming_soon));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    public static String compareDatesByDateMethods(String format, Date oldDate, Date newDate) {
        //how to check if two dates are equals in java
        String detail = null;
        DateFormat df = new SimpleDateFormat(format);

        if (oldDate.equals(newDate)) {
            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
            return detail = df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other";
        }

        //checking if date1 comes before date2
        if (oldDate.before(newDate)) {
            System.out.println(df.format(oldDate) + " comes before " + df.format(newDate));
            return detail = df.format(oldDate) + " comes before " + df.format(newDate);
        }

        //checking if date1 comes after date2
        if (oldDate.after(newDate)) {
            System.out.println(df.format(oldDate) + " comes after " + df.format(newDate));
            return detail = df.format(oldDate) + " comes after " + df.format(newDate);
        }
        return detail;
    }

    public static boolean checkCurrentDate(Date startDate) {
        boolean res = false;
        Date currentDate = null;

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        String date =day + "/"+month+"/" + year;
        DateFormat df = new SimpleDateFormat(ExtraConstants.Date_Format2);
        try {
            currentDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (currentDate.equals(startDate)) {

            res = true;
            return res;
        }

        return res;

    }

    public static long getTimeDifferenceInMilliSec(String date,String time){
        long timeDifference=0;
        String []dateArray=date.split("/");
        String []timeArray=time.split(":");
        Calendar cal=Calendar.getInstance();
        Date commingDate=   convertDateTimeToDateObj(date+" "+time,ExtraConstants.COMINGDATEFORMATFROMSERVER2,ExtraConstants.COMINGDATEFORMATFROMSERVER1);
//        int month=Integer.valueOf(dateArray[1])-1;
//        cal.set(Integer.valueOf(dateArray[0]),month,Integer.valueOf(dateArray[2]),Integer.valueOf(timeArray[0]),Integer.valueOf(timeArray[1]),Integer.valueOf(timeArray[2]));
        timeDifference=commingDate.getTime();
        timeDifference=timeDifference-getCurrentTimeInUTCInMilliSeconds1();

        return timeDifference;
    }
    public static boolean checkDate(String format, Date startDate, Date endDate) {
        boolean res = false;
        Date currentDate = null;

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = month + 1 + "/" + day + "/" + year;
        DateFormat df = new SimpleDateFormat(ExtraConstants.Date_Format);
        try {
            currentDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (checkDateBefore(format, currentDate, endDate) && checkDateAfter(format, currentDate, startDate)) {
            res = true;
            return res;

        }

        return res;

    }


    public static boolean checkDateBefore(String format, Date current, Date end) {
        boolean value = false;
        DateFormat df = new SimpleDateFormat(format);
        if (current.before(end)) {
            System.out.println(df.format(current) + " comes before " + df.format(end));
            value = true;
            return value;
        } else if (current.equals(end)) {
            System.out.println(df.format(current) + " comes before " + df.format(end));
            value = true;
            return value;
        }
        return value;
    }

    public static boolean checkDateEquals(String format, Date current, Date start, Date end) {
        boolean value = false;
        DateFormat df = new SimpleDateFormat(format);
        if (current.equals(start)) {
            System.out.println(df.format(current) + " comes before " + df.format(start));
            value = true;
            return value;
        } else if (current.equals(end)) {
            System.out.println(df.format(current) + " comes before " + df.format(end));
            value = true;
            return value;
        }
        return value;
    }

    public static boolean checkDateAfter(String format, Date current, Date start) {
        boolean value = false;
        DateFormat df = new SimpleDateFormat(format);
        if (current.after(start)) {
            System.out.println(df.format(current) + " comes before " + df.format(start));
            value = true;
            return value;
        } else if (current.equals(start)) {
            System.out.println(df.format(current) + " comes before " + df.format(start));
            value = true;
            return value;
        }
        return value;
    }


    public static String convertDateTimeToDate(String utcTime, String commingDformat, String format) {
        String convertedDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(commingDformat.trim(), Locale.ENGLISH);
            long time = df.parse(utcTime).getTime();
            Date date = new Date();
            date.setTime(time);
            convertedDate = df.format(date);
            if (format != null && !format.isEmpty()) {
                convertedDate = new SimpleDateFormat(format).format(date);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getCurrentDate() {

        Date currentDate = null;

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = month + 1 + "/" + day + "/" + year;
        DateFormat df = new SimpleDateFormat(ExtraConstants.Date_Format);
        try {
            currentDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }



    public static boolean validateLName(String lName)
    {
        boolean result=true;
        if (checkStringsContainsOnlySpecialChar(lName)) {
            //Utils.showToast(act, msg);
            result=false;
        }
        else if (isNumeric(lName)) {
            // Utils.showToast(act,msg);
            result=false;
        }

        return result;
    }

    public static boolean checkStringsContainsOnlySpecialChar(String inputString) {
        boolean found = false;
        try {
            String splChrs = "/^[!@#$%^&*()_+\\-=\\[\\]{};:\"\\\\|,.<>\\/?]*$/";
            found = inputString.matches("[" + splChrs + "]+");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }
    public static boolean isNumeric(String str) {
        try {
            long d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(CharSequence email) {
        boolean b = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

        return b;
    }

    // validate state
    public static boolean validateState( String state )
    {
        return state.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" ) ;
    } // end method validateState

    // validate city
    public static boolean validateCity( String city )
    {
        return city.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
    } //

    public static Long getCurrentTimeInUTCInMilliSeconds1() {
        long timeInMillSeconds = 0;
        DateFormat df = new SimpleDateFormat(ExtraConstants.COMINGDATEFORMATFROMSERVER1);
        df.setTimeZone(TimeZone.getDefault());
        String utc_time = df.format(new Date());
        try {
            Date date1 = df.parse(utc_time);
            timeInMillSeconds = date1.getTime() ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeInMillSeconds;
    }

    public static Date convertDateTimeToDateObj (String utcTime, String commingDformat, String format) {

        String convertedDate = null;
        Date convertedDateTime = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(commingDformat);
            long time = df.parse(utcTime).getTime();
            Date date = new Date();
            date.setTime(time);
            convertedDate = df.format(date);
            if (format != null && !format.isEmpty()) {
                convertedDate = new SimpleDateFormat(format).format(date);
                long time1 = new SimpleDateFormat(format).parse(convertedDate).getTime();
                convertedDateTime = new Date();
                convertedDateTime.setTime(time1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDateTime;
    }

    public static String convertDateTimeToProperFmt(String dateTimeF) {
        String date_time=null;
        String[] arr=dateTimeF.split(" ");
        String date=arr[0];
        String time=arr[1];
        String[] dateArray=date.split("/");
        String[] timeArray=time.split(":");

        NumberFormat nf=new DecimalFormat("00");
        date_time=nf.format(Integer.valueOf(dateArray[0]))+"/"+nf.format(Integer.valueOf(dateArray[1]))+"/"+nf.format(Integer.valueOf(dateArray[2]))+" "+nf.format(Integer.valueOf(timeArray[0]))+":"+nf.format(Integer.valueOf(timeArray[1]))+":"+nf.format(Integer.valueOf(timeArray[2]))+" "+arr[2];
        Log.e("dateTime=",date_time);
        return date_time;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void forceRTLIfSupported(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.getWindow().getDecorView().
                    setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void forceLTRIfSupported(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.getWindow().getDecorView().
                    setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    public static String getuserSeletedLanague(Context context) {
        String lanage_code = "en";
        lanage_code=MyPrefs.getUserSelectedLanguage(context);

        return lanage_code;
    }

    /*chanages language */
    public static void chagesLocatiolization (Activity activity, String language) {


        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = activity.getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());


    }


/*get local langaugae*/

    public static String userLanguage() {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("fa")) {
            return "fa";
        }
        /*else if (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) {
            return "fr";
        }*/
        else {
            return "en";
        }
    }
    public static String getuserSeletedLanagueForRequestSend(Context context) {
        String lanage_code = "en";
        lanage_code=MyPrefs.getUserSelectedLanguage(context);
        if(lanage_code.equalsIgnoreCase("fa")){
            lanage_code="far";
        }
        landSend=lanage_code;
        return lanage_code;
    }
}
