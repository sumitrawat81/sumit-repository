package com.sibsefid.fragemnts.patient.tracker;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.RetrofitUtils;
import com.sibsefid.fragemnts.patient.PMedicalDevices;
import com.sibsefid.fragemnts.patient.myresult.MyResultFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.AddManualEcgModel;
import com.sibsefid.util.KeyValues;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PCheckEcg extends Fragment implements View.OnClickListener {

    private LinearLayout watch_video_layout;
    private LinearLayout add_result_layout;
    private LinearLayout see_previous_result;
    private LinearLayout enquiry;
    private PatientActivity activity;
    private TextView mBrowseFile;
    private EditText mSubject;
    private Button mSaveBtn;
    private ProgressBar progress;
    private UserLoginDetails.LoginDetails details;
    private Dialog dialog;
    Callback<AddManualEcgModel> detailsCallback = new Callback<AddManualEcgModel>() {
        @Override
        public void onResponse(Call<AddManualEcgModel> call, Response<AddManualEcgModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {

                }
                dialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<AddManualEcgModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                dialog.dismiss();
            }
        }
    };
    private int RESULT_LOAD_IMAGE = 1;
    private int CAMERA_REQUEST = 1888;
    private Uri mCapturedImageURI = null;
    private String imagePath;


    public PCheckEcg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        details = MyPrefs.getLoginDetails(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.p_check_ecg, container, false);

        initilzeView(view);

        return view;
    }

    private void initilzeView(View view) {

        watch_video_layout = (LinearLayout) view.findViewById(R.id.watch_video_layout);
        add_result_layout = (LinearLayout) view.findViewById(R.id.add_result_layout);
        see_previous_result = (LinearLayout) view.findViewById(R.id.see_previous_result);
        enquiry = (LinearLayout) view.findViewById(R.id.enquiry);

        watch_video_layout.setOnClickListener(this);
        add_result_layout.setOnClickListener(this);
        see_previous_result.setOnClickListener(this);
        enquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.watch_video_layout:
                Utils.CustomToast(getActivity(), v);
                break;

            case R.id.add_result_layout:
                showCustomDialog();
                break;

            case R.id.see_previous_result:
                Utils.callFragmentForAddPatient(activity, new MyResultFragment(), MyResultFragment.TAG);

                break;

            case R.id.enquiry:
                Utils.callFragmentForAddPatient(activity, new PMedicalDevices(), PMedicalDevices.TAG);
                break;
        }
    }

    public void showCustomDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.add_ecg_result_dialog);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        mSubject = (EditText) dialog.findViewById(R.id.mSubject);
        progress = (ProgressBar) dialog.findViewById(R.id.progress);
        mBrowseFile = (TextView) dialog.findViewById(R.id.mBrowseFile);

        mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);

        mBrowseFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                select_Image();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isDeviceOnline(getActivity())) {


                    if (!TextUtils.isEmpty(mSubject.getText().toString().trim()) && (!TextUtils.isEmpty(mBrowseFile.getText().toString()))) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        HashMap<String, String> requestValuePairsMap = new HashMap<>();
                        requestValuePairsMap.put(KeyValues.ARG_PatientID, details.getUser_seq());
                        requestValuePairsMap.put(KeyValues.ARG_Ds_Subject, mSubject.getText().toString().trim());
                        requestValuePairsMap.put(KeyValues.ARG_P_LANG, langOpted);


                        RestAdapter.getAdapter()
                                .addEcgManuly(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_P_ecgFile,
                                                mBrowseFile.getText().toString().trim(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                                .enqueue(detailsCallback);
                    } else {

                        Utils.showCustomToast(getActivity().getResources().getString(R.string.please_fill_all_fields), getActivity());
                    }
                } else {

                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            //listMember.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
                mBrowseFile.setText(imagePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(column_index);
                cursor.close();
                mBrowseFile.setText(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
