package com.sibsefid.fragemnts.patient.bookappointmentDoctorCall;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.fragemnts.patient.DoctorOnCall;
import com.sibsefid.util.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ubuntu on 20/12/16.
 */
public class ReasonToVisitDrCall extends Fragment implements View.OnClickListener {

    private static final String TAG = "PReasonForVisit";
    private EditText mReasonEdt;
    private EditText mCommentEdt;
    private Button mBrowse;
    private Button mBackBtn;
    private Button mContinueBtn;
    private ImageView browsImg;
    private DoctorOnCall scheduleFragment;
    private String filePath;
    private int RESULT_LOAD_IMAGE = 1;
    private int CAMERA_REQUEST = 1888;
    private Uri mCapturedImageURI = null;
    private String imagePath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleFragment = DoctorOnCall.getInstance();
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

        View view = inflater.inflate(R.layout.fragment_presion_for_visit, container, false);
        initiateView(view);
        return view;
    }

    private void initiateView(View view) {


        mCommentEdt = (EditText) view.findViewById(R.id.mCommentEdt);
        mReasonEdt = (EditText) view.findViewById(R.id.mReasonEdt);
        mBackBtn = (Button) view.findViewById(R.id.mBackBtn);
        mContinueBtn = (Button) view.findViewById(R.id.mContinueBtn);
        mBrowse = (Button) view.findViewById(R.id.mBrowse);
        browsImg = (ImageView) view.findViewById(R.id.browsImg);
        mBackBtn.setOnClickListener(this);
        mContinueBtn.setOnClickListener(this);
        mBrowse.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBackBtn:
                getActivity().onBackPressed();
                PMainBookAppDocCall.getInstance().getmPager().setCurrentItem(0);
                break;

            case R.id.mContinueBtn:
                continueClick();
                break;
            case R.id.mBrowse:
                select_Image();
                break;
        }
    }


    private void continueClick() {

        String strReason = mReasonEdt.getText().toString().toLowerCase();
        String strComment = mCommentEdt.getText().toString().toLowerCase();

        if (TextUtils.isEmpty(strReason)) {

        } else if (TextUtils.isEmpty(strComment)) {

        } else {
            scheduleFragment.getPostModel().setReason(strReason);
            scheduleFragment.getPostModel().setComment(strComment);
            scheduleFragment.getPostModel().setImage_uri(imagePath);
            PMainBookAppDocCall.getInstance().getmPager().setCurrentItem(1);
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                browsImg.setImageBitmap(bitmap);

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
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                browsImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}