package com.sibsefid.fragemnts.patient;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.RetrofitUtils;
import com.sibsefid.fragemnts.patient.medecinereminder.MedicineReminder;
import com.sibsefid.fragemnts.patient.myappointment.PMyAppointment;
import com.sibsefid.fragemnts.patient.myhistory.PMyHistoryMain;
import com.sibsefid.fragemnts.patient.mymessage.PMyMessageMain;
import com.sibsefid.fragemnts.patient.myresult.MyResultFragment;
import com.sibsefid.fragemnts.patient.syyptom.checker.PSymptomCheckerMain;
import com.sibsefid.fragemnts.patient.tracker.PTracker;
import com.sibsefid.interfaces.NotificationReceiverInterface;
import com.sibsefid.model.doctor.DAppointmentListModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.UpdateProfilePictureModel;
import com.sibsefid.patient.adapter.HomePatientGridViewAdapter;
import com.sibsefid.util.KeyValues;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class PHomeProfileFragment extends Fragment implements View.OnClickListener, NotificationReceiverInterface {


    public static final String TAG = "PHomeProfileFragment";
    private GridView mGridView;
    private PatientActivity patientActivity;
    private ImageView mProfilePicture;
    private TextView mAppointmentRemainingTime;
    // private ImageView mProfileImageView;
    private TextView mUserName;
    private UserLoginDetails.LoginDetails details;
    private View progress;
    long timeInMillisecond;
    String langOpted;
    private ArrayList<DAppointmentListModel.DAppointmentBean> mDAppointmentList = new ArrayList<>();

    Callback<DAppointmentListModel> getAppointment = new Callback<DAppointmentListModel>() {
        @Override
        public void onResponse(Call<DAppointmentListModel> call, Response<DAppointmentListModel> response) {
            if (getActivity() != null) {

                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDAppointmentBean() != null && response.body().getDAppointmentBean().size() > 0) {
                        mDAppointmentList = response.body().getDAppointmentBean();
                        String date=mDAppointmentList.get(0).getDate();
                        String time=mDAppointmentList.get(0).getTime();
                       UpdateTheRemainingTime(date,time);

                    }
                    else{
                        mAppointmentRemainingTime.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<DAppointmentListModel> call, Throwable t) {
            if (getActivity() != null) {

            }
        }
    };
    private Runnable notification;

    private void UpdateTheRemainingTime(String date, String time) {

        timeInMillisecond=Utils.getTimeDifferenceInMilliSec(date,time);
        primaryTimeProgressUpdater();
            mAppointmentRemainingTime.setVisibility(View.VISIBLE);


    }

    private void primaryTimeProgressUpdater () {

              notification = new Runnable() {
                public void run() {
                    if(timeInMillisecond>0) {
                        primaryTimeProgressUpdater();
                        getAudioLengthInString();
                    }
                }
            };

        mAppointmentRemainingTime.postDelayed(notification,1000);
    }
    private String getAudioLengthInString () {

        long seconds = timeInMillisecond / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        hours=hours%24;
        minutes=minutes%60;
        seconds=seconds%60;

     //   hours % 24 + ":" + minutes % 60 + ":" + seconds % 60

      /*  long seconds = (timeInMillisecond / 1000) ;
        long days = TimeUnit.MILLISECONDS.toDays(timeInMillisecond);;
        long hours =seconds % (60 *60*24);
        long minutes = hours % (60 );
        seconds = minutes % (60 );*/
        Log.d("tag", "minutes and seconds value is: " + minutes + ":" + seconds);
        NumberFormat f = new DecimalFormat("00");
        String remainingTime="Remaning Time:";
        if(days==1){
            remainingTime =remainingTime+" "+ f.format(days) + "day" +" "+ f.format(hours)+ ":" +f.format(minutes) + ":" + f.format(seconds);
        }else if(days>1){
            remainingTime =remainingTime+" "+ f.format(days) + "days" +" "+ f.format(hours)+ ":" + f.format(minutes) + ":" + f.format(seconds);
        }else{
            remainingTime =remainingTime+" "+ f.format(hours)+":"+ f.format(minutes) + ":" + f.format(seconds);
        }

        mAppointmentRemainingTime.setText(remainingTime);
        timeInMillisecond-=1000;
        return remainingTime;
    }


    Callback<UpdateProfilePictureModel> updateProfileListner = new Callback<UpdateProfilePictureModel>() {
        @Override
        public void onResponse(Call<UpdateProfilePictureModel> call, Response<UpdateProfilePictureModel> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    try {
                        Log.e(TAG, "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    patientActivity.showProgress(false, mGridView, progress);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            details.setPhoto(response.body().getData().getUserImage().get(0).getUser_photo());
                            MyPrefs.saveLoginDetails(getActivity(), details);
                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateProfilePictureModel> response, Throwable t) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, mGridView, progress);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private int RESULT_LOAD_IMAGE = 1;
    private int CAMERA_REQUEST = 1888;
    private Uri mCapturedImageURI = null;
    private Bitmap finalBitmap = null;
    private String imagePath;
    private TextView txtMsgCount;
    private TextView notification_count_txt;
    private ImageView mUpdateImageBtn;
    private LinearLayout dail_layou;

    public static PHomeProfileFragment newInstance() {
        PHomeProfileFragment fragment = new PHomeProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientActivity = (PatientActivity) getActivity();
        langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        EventBus.getDefault().register(this);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_patient_profile, container, false);
        details = MyPrefs.getLoginDetails(getActivity());
        initiateView(view);

        mUserName.setText(details.getFamily_name() + " " + details.getGiven_name());

        try {
            Picasso.with(getActivity())
                    .load(details.getPhoto())
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(mProfilePicture);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Utils.isDeviceOnline(getActivity())) {

            String current_Time_Zone = Utils.getTimeZoneDifference();
            RestAdapter.getAdapter().getPAppointmentList(details.getUser_seq(), current_Time_Zone,langOpted).enqueue(getAppointment);
        }


        return view;
    }

    private void initiateView(View view) {

        mProfilePicture = (ImageView) view.findViewById(R.id.mProfilePicture);
        mUserName = (TextView) view.findViewById(R.id.mUserName);
        mGridView = (GridView) view.findViewById(R.id.mGridView);
        progress = view.findViewById(R.id.progress);
        txtMsgCount = (TextView) view.findViewById(R.id.txtMsgCount);
        mAppointmentRemainingTime = (TextView) view.findViewById(R.id.mAppointmentRemainingTime);
        notification_count_txt = (TextView) view.findViewById(R.id.notification_count_txt);
        mUpdateImageBtn = (ImageView) view.findViewById(R.id.mUpdateImageBtn);
        dail_layou = (LinearLayout) view.findViewById(R.id.dail_layou);

        mGridView.setNumColumns(3);

        HomePatientGridViewAdapter mAdapter = new HomePatientGridViewAdapter(getActivity());
        mAdapter.setOnClickListener(this);
        mGridView.setAdapter(mAdapter);

        txtMsgCount.setText(MyPrefs.getUserMsgCount(getActivity()).trim());
        notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()).trim());

        mUpdateImageBtn.setOnClickListener(this);
        dail_layou.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int position = 0;


        switch (view.getId()) {

            case R.id.clickBtn:
                position = (Integer) view.getTag();

                switch (position) {
                    case 0:
                        Utils.callFragmentForAddPatient(patientActivity, new PDetailsFragment(), PDetailsFragment.TAG);
                        break;

                    case 1:
                        Utils.callFragmentForAddPatient(patientActivity, new PMyHistoryMain(), PMyHistoryMain.TAG);
                        break;

                    case 2:
                        Utils.callFragmentForAddPatient(patientActivity, new PMyMessageMain(), PMyMessageMain.TAG);
                        break;

                    case 3:
                        Utils.callFragmentForAddPatient(patientActivity, new PSeeDoctorList(), PSeeDoctorList.TAG);
                        break;
                    case 4:
                        Utils.callFragmentForAddPatient(patientActivity, new PMyAppointment(), PMyAppointment.TAG);
                        break;

                    case 5:
                        Utils.callFragmentForAddPatient(patientActivity, new PMyBilling(), PMyBilling.TAG);
                        break;

                    case 6:
                        Utils.callFragmentForAddPatient(patientActivity, new MyResultFragment(), MyResultFragment.TAG);
                        break;

                    case 7:
                        Utils.callFragmentForAddPatient(patientActivity, new PTracker(), PTracker.TAG);
                        break;

                    case 8:
                        Utils.callFragmentForAddPatient(patientActivity, new PSymptomCheckerMain(), PSymptomCheckerMain.TAG);
                        break;

                    case 9:
                        Utils.callFragmentForAddPatient(patientActivity, new MedicineReminder(), MedicineReminder.TAG);
                        break;

                }
                break;

            case R.id.mUpdateImageBtn:
                select_Image();
                break;

            case R.id.dail_layou:
                showPopup(dail_layou);
                break;


        }

    }

    private void showPopup(View anchor) {
        final View popupView = LayoutInflater.from(patientActivity).inflate(R.layout.dial_emergency_dialog, null);

        TextView mCallBtn = (TextView) popupView.findViewById(R.id.mCallBtn);
        TextView mMessageBtn = (TextView) popupView.findViewById(R.id.mMessageBtn);


        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(popupView);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor);


        mCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                startActivity(callIntent);
            }
        });
        mMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address", "911");
                sendIntent.putExtra("sms_body", "e911MD");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void select_Image() {

        final CharSequence[] items1 = {getActivity().getResources().getString(R.string.user_camera), getActivity().getResources().getString(R.string.choose_exsting)};
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle(getActivity().getResources().getString(R.string.choose_option));

        builder1.setItems(items1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals(getActivity().getResources().getString(R.string.choose_exsting))) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else if (items1[item].equals(getActivity().getResources().getString(R.string.user_camera))) {
                    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                    if (isSDPresent) {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, getActivity().getResources().getString(R.string.app_name));
                        mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_sd_card), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE
                && resultCode == RESULT_OK && null != data) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                mProfilePicture.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Utils.isDeviceOnline(getActivity())) {
                patientActivity.showProgress(true, mGridView, progress);



                HashMap<String, String> requestValuePairsMap = new HashMap<>();
                requestValuePairsMap.put("PatientId", details.getUser_seq());
                requestValuePairsMap.put("LanType",langOpted);

                RestAdapter.getAdapter()
                        .addSetPatientPic(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                RetrofitUtils.createFilePart(KeyValues.ARG_P_Image,
                                        imagePath,
                                        RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                        .enqueue(updateProfileListner);


            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(column_index);
                cursor.close();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                mProfilePicture.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (Utils.isDeviceOnline(getActivity())) {

                patientActivity.showProgress(true, mGridView, progress);

                HashMap<String, String> requestValuePairsMap = new HashMap<>();
                requestValuePairsMap.put("PatientId", details.getUser_seq());
                requestValuePairsMap.put("LanType",langOpted);

                RestAdapter.getAdapter().addSetPatientPic(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                        RetrofitUtils.createFilePart(KeyValues.ARG_P_Image,
                                imagePath,
                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                        .enqueue(updateProfileListner);


            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Subscribe
    public void onEvent(RegisterResponseModel objects) {

        if (getActivity() != null) {
            txtMsgCount.setText(objects.getMsgCount());
        }

    }

    @Override
    public void onResume() {
        PMyMessageMain.notificationReceiverInterface = this;
        txtMsgCount.setText(MyPrefs.getUserMsgCount(getActivity()).trim());
        super.onResume();
    }


    @Override
    public void getNotification() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()));

            }
        });


    }

    @Subscribe
    public void onEvent(UserLoginDetails.LoginDetails model) {

        if (getActivity() != null) {
            details = MyPrefs.getLoginDetails(getActivity());
            mUserName.setText(details.getFamily_name() + " " + details.getGiven_name());

            try {
                Picasso.with(getActivity())
                        .load(details.getPhoto())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(mProfilePicture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onEvent(RemoteMessage remoteMessage) {

        if (getActivity() != null) {
            int count = Integer.valueOf(MyPrefs.getUserNotificationCount(getActivity()).trim());
            count = count + 1;
            MyPrefs.saveUserNotiicationCountl(getActivity(), String.valueOf(count));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notification_count_txt.setText(MyPrefs.getUserNotificationCount(getActivity()));

                }
            });

        }

    }

}
