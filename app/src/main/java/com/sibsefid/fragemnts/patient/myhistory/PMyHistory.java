package com.sibsefid.fragemnts.patient.myhistory;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.AllergiesSaveCallBack;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMyHistory extends Fragment implements AllergiesSaveCallBack.OnAllergiesSaveCallBackListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private static PMyHistory myHistory;
    PatientActivity activity;
    TextView mDFromDateSelector;
    TextView mMedicineName;
    EditText mMedicineQty;
    EditText mComment;
    Dialog dialog;
    private int RESULT_LOAD_IMAGE = 1;
    private int CAMERA_REQUEST = 1888;
    //private Uri mCapturedImageURI = null;
    private String imagePath;
    private View mProgressView;
    private WebView web_disply;

    private WebSettings webSettings;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG ="MyHistroty";

    int delPos;
    private UserLoginDetails.LoginDetails details;

   /* Callback<AddOtherMedicalHistoryModel> detailsCallback = new Callback<AddOtherMedicalHistoryModel>() {
        @Override
        public void onResponse(Call<AddOtherMedicalHistoryModel> call, Response<AddOtherMedicalHistoryModel> response) {
            if (getActivity() != null) {
                //showProgress(false);
                adapter.showProgress(false);
                if (response.isSuccessful()) {
                    adapter.setSubject();
                    adapter.setUpload();
                    RestAdapter.getAdapter().getPMyHistory(details.getUser_seq()).enqueue(myHisModelCallback);
                }
              //  dialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<AddOtherMedicalHistoryModel> call, Throwable t) {
            if (getActivity() != null) {
                adapter.showProgress(false);
//                dialog.dismiss();
            }
        }
    };

    Callback<AddOtherMedicalHistoryModel> detailsCallbackForDel = new Callback<AddOtherMedicalHistoryModel>() {
        @Override
        public void onResponse(Call<AddOtherMedicalHistoryModel> call, Response<AddOtherMedicalHistoryModel> response) {
            if (getActivity() != null) {

                adapter.showProgress(false);
                if (response.isSuccessful()) {
                    pMyHistoryArrayList.getData().getOtherMedicalRecord().remove(delPos);
                    adapter.setmList_Status(pMyHistoryArrayList);
                    adapter.notifyDataSetChanged();
                }

                Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<AddOtherMedicalHistoryModel> call, Throwable t) {
            if (getActivity() != null) {

            }
        }
    };

    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PMyHistory.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(false);
            dpd.vibrate(true);
            dpd.dismissOnPause(true);
            dpd.showYearPickerFirst(false);
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
            dpd.setTitle(getActivity().getResources().getString(R.string.date_dailog_title));
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    };
    private RecyclerView recyclerView;
    private CheckBox allow_my_treating;
    private View mProgressView;
    Callback<BookingSummeryModelFromServer> updateAllowCallback = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                mProgressView.setVisibility(View.GONE);
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null) {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                    } else {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<BookingSummeryModelFromServer> call, Throwable t) {
            if (getActivity() != null) {
                mProgressView.setVisibility(View.GONE);
                if (allow_my_treating.isChecked()) {
                    allow_my_treating.setChecked(false);
                } else {
                    allow_my_treating.setChecked(true);
                }
                showProgress(false);
            }
        }
    };
    private ProgressBar pop_progress;
    Callback<UpdateProfilePictureModel> modelCallback = new Callback<UpdateProfilePictureModel>() {
        @Override
        public void onResponse(Call<UpdateProfilePictureModel> call, Response<UpdateProfilePictureModel> response) {
            if (getActivity() != null) {
                pop_progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        //   dialog.dismiss();
                        // {"Success":true,"Message":"Sent successfully refill Medicine to pharmacy."}
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateProfilePictureModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                pop_progress.setVisibility(View.GONE);
            }
        }
    };

    private PMyHistoryModel pMyHistoryArrayList = new PMyHistoryModel();
    private PMyHistoryAdapter adapter;
    Callback<PMyHistoryModel> myHisModelCallback = new Callback<PMyHistoryModel>() {
        @Override
        public void onResponse(Call<PMyHistoryModel> call, Response<PMyHistoryModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null) {
                        pMyHistoryArrayList = (response.body());
                        adapter.setmList_Status(pMyHistoryArrayList);
                        adapter.notifyDataSetChanged();
                        if (pMyHistoryArrayList.getData().getAllowPhysicians() != null) {
                            if (pMyHistoryArrayList.getData().getAllowPhysicians().get(0).getIsAllowPhysicians().toString().toLowerCase().equalsIgnoreCase("true")) {
                                allow_my_treating.setChecked(true);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PMyHistoryModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
*/
    public PMyHistory() {
        // Required empty public constructor
    }

    public static PMyHistory getInstance() {

        return myHistory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        activity = (PatientActivity) getActivity();

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

        View view = inflater.inflate(R.layout.fragment_pmy_history, container, false);
        myHistory = this;
        initiateView(view);

      /*  if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            RestAdapter.getAdapter().getPMyHistory(details.getUser_seq()).enqueue(myHisModelCallback);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }
*/
        return view;
    }

    private void initiateView(View view) {
       /* mProgressView = view.findViewById(R.id.progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.mRecycleView);
        allow_my_treating = (CheckBox) view.findViewById(R.id.allow_my_treating);
        adapter = new PMyHistoryAdapter(getActivity(), pMyHistoryArrayList, this, this);
        recyclerView.setAdapter(adapter);
        allow_my_treating.setOnClickListener(this);*/

        mProgressView = view.findViewById(R.id.progress);
        web_disply = (WebView) view.findViewById(R.id.web_disply);
        webSettings = web_disply.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        web_disply.setWebViewClient(new Client());
        web_disply.setWebChromeClient(new ChromeClient());
        if (Build.VERSION.SDK_INT >= 19) {
            web_disply.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
            web_disply.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        String url = ApiUrls.BASE_URL_WebView+"patient_history.aspx?userid="+details.getUser_seq();
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);

        }
        web_disply.loadUrl(url);
        web_disply.setWebViewClient(new WebViewClient() {
                                        @Override
                                        public void onPageFinished(WebView view, String url) {
                                            super.onPageFinished(view, url);
                                            showProgress(false);
                                        }
                                    }
        );
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }
    public class ChromeClient extends WebChromeClient {
        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Unable to create Image File", ex);
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }
            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
            return true;
        }
        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard
            File imageStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES)
                    , "AndroidExampleFolder");
            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }
            // Create camera captured image file path and name
            File file = new File(
                    imageStorageDir + File.separator + "IMG_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");
            mCapturedImageURI = Uri.fromFile(file);
            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            // Create file chooser intent
            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                    , new Parcelable[] { captureIntent });
            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        }
        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }
        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture) {
            openFileChooser(uploadMsg, acceptType);
        }
    }
    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_disply.canGoBack()) {
            web_disply.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }*/
    public class Client extends WebViewClient {
        ProgressDialog progressDialog;
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // If url contains mailto link then open Mail Intent
            if (url.contains("mailto:")) {
                // Could be cleverer and use a regex
                //Open links in new browser
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                // Here we can open new activity
                return true;
            }else {
                // Stay within this webview and load url
                view.loadUrl(url);
                return true;
            }
        }
           //Show loader on url load
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               // Then show progress  Dialog
               // in standard case YourActivity.this
               if (progressDialog == null) {
                   progressDialog = new ProgressDialog(getActivity());
                   progressDialog.setMessage("Loading...");
                   progressDialog.show();
               }
           }
        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            try {
                // Close progressDialog
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
  /*  @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/

    public UserLoginDetails.LoginDetails getDetails() {
        return details;
    }

    @Override
    public void onAllergiesSaveCallBackSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition) {
      /*  if (rowPosition == 0) {
            if (adapter.getALGItem(adpterPosition).isDeletedLoading()) {
                adapter.getAllergiesArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getALGItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 1) {
            if (adapter.getMDHIItem(adpterPosition).isDeletedLoading()) {
                adapter.getMedicationArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getMDHIItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 2) {
            if (adapter.getPMSHItem(adpterPosition).isDeletedLoading()) {
                adapter.getPastMedicalArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getPMSHItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 3) {
            if (adapter.getSMKItem(adpterPosition).isDeletedLoading()) {
                adapter.getSmokeArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getSMKItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 4) {
            if (adapter.getALCItem(adpterPosition).isDeletedLoading()) {
                adapter.getAlcoholArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getALCItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 5) {
            if (adapter.getFMHIItem(adpterPosition).isDeletedLoading()) {
                adapter.getFamilyArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getFMHIItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 6) {
            if (adapter.getWSTItem(adpterPosition).isDeletedLoading()) {
                adapter.getWaistArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getWSTItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        } else if (rowPosition == 7) {
            if (adapter.getHGTItem(adpterPosition).isDeletedLoading()) {
                adapter.getHeightArrayList().remove(adpterPosition);
                adapter.notifyItemChanged(rowPosition);
            } else {
                adapter.getHGTItem(adpterPosition).setLoading(false);
                adapter.notifyItemChanged(rowPosition);
            }
        }*/


    }

    @Override
    public void onAllergiesSaveCallBackFailure(RegisterResponseModel data, int adpterPosition, int rowPosition) {
     /*   if (rowPosition == 0) {
            adapter.getALGItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 1) {
            adapter.getMDHIItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 2) {
            adapter.getPMSHItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 3) {
            adapter.getSMKItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 4) {
            adapter.getALCItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 5) {
            adapter.getFMHIItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 6) {
            adapter.getWSTItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        } else if (rowPosition == 7) {
            adapter.getHGTItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);
        }*/

    }

    @Override
    public void onAllergiesSaveCallBackException(String message, int adpterPosition, int rowPosition) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            /*case R.id.allow_my_treating:
                updateIsAllow();
                break;

            case R.id.mMSHIRefill:
                int pos = (Integer) v.getTag();
                showCustomDialog(pos);
                // Toast.makeText(getActivity(),pMyHistoryArrayList.getData().getMedication().get(pos).getStrenght(),Toast.LENGTH_SHORT).show();
                break;

            case R.id.mBrowseFile:

                select_Image();
                break;

            case R.id.mUploadBtn:
                String strSub=adapter.getSubject();
                String strImgPath=adapter.getBrowseImg();
                if (TextUtils.isEmpty(strSub)) {
                    Toast.makeText(getActivity(), "Enter the Subject", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(strImgPath)){
                    Toast.makeText(getActivity(), "upload image", Toast.LENGTH_SHORT).show();
                }else{
                    if (Utils.isDeviceOnline(getActivity())) {
                        //showProgress(true);
                        adapter.showProgress(true);
                       // pop_progress.setVisibility(View.VISIBLE);
                        HashMap<String, String> requestValuePairsMap = new HashMap<>();
                        requestValuePairsMap.put(KeyValues.ARG_patientid, details.getUser_seq());
                        requestValuePairsMap.put(KeyValues.ARG_Ds_Subject, strSub);
                      //  requestValuePairsMap.put(KeyValues.ARG_file, strImgPath);


                        RestAdapter.getAdapter()
                                .addOtherMedicalHistory(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_file,
                                                strImgPath,
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                                .enqueue(detailsCallback);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.actionOther:
                 delPos=(Integer)v.getTag();
                //Toast.makeText(getActivity(),"Position Clicked="+delPos,Toast.LENGTH_SHORT).show();
                if (Utils.isDeviceOnline(getActivity())) {

                    RestAdapter.getAdapter().Delete_OtherMedicalHistory(
                            pMyHistoryArrayList.getData().getOtherMedicalRecord().get(delPos).getOther_medical_record_id()).enqueue(detailsCallbackForDel);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }
                break;*/
        }
    }

   /* private void select_Image() {

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
    }*/

   /* private void showCustomDialog(int pos) {
        // DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_refill_medicine);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);

        mMedicineName = (TextView) dialog.findViewById(R.id.mMedicineName);
        mDFromDateSelector = (TextView) dialog.findViewById(R.id.mDFromDateSelector);
        pop_progress = (ProgressBar) dialog.findViewById(R.id.pop_progress);


        mMedicineQty = (EditText) dialog.findViewById(R.id.mMedicineQty);
        mComment = (EditText) dialog.findViewById(R.id.mComment);


        Button mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);


        mMedicineName.setText(pMyHistoryArrayList.getData().getMedication().get(pos).getName());

        mDFromDateSelector.setOnClickListener(dateOnClickListener);

        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strQty = mMedicineQty.getText().toString();
                String strComment = mComment.getText().toString();
                String strSelectedDate = mDFromDateSelector.getText().toString();
                String strSenderId = details.getUser_seq();

                if (TextUtils.isEmpty(strQty)) {
                    Toast.makeText(getActivity(), "Please fill the Quantity", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strComment)) {
                    Toast.makeText(getActivity(), "Please fill the Comment", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strSelectedDate)) {
                    Toast.makeText(getActivity(), "Please fill the Date", Toast.LENGTH_SHORT).show();
                } else {

                    if (Utils.isDeviceOnline(getActivity())) {

                        pop_progress.setVisibility(View.VISIBLE);
                        RestAdapter.getAdapter().refillMedicine(strSenderId, mMedicineName.getText().toString(), strQty, strSelectedDate, strComment).enqueue(modelCallback);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        dialog.show();
    }
*/
    /*private void updateIsAllow() {
        if (Utils.isDeviceOnline(getActivity())) {
            mProgressView.setVisibility(View.VISIBLE);
            RestAdapter.getAdapter().updateAllowPhysicians(details.getUser_seq(), String.valueOf(allow_my_treating.isChecked())).enqueue(updateAllowCallback);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }

    }*/

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        String day=String.valueOf(dayOfMonth);
        String month=String.valueOf(monthOfYear);

        if(dayOfMonth<10){
            day="0"+dayOfMonth;
        }
        if(monthOfYear<10){
            month="0"+monthOfYear;
        }
        mDFromDateSelector.setText(day + "/" + (month) + "/" + year);

    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       *//* if (requestCode == RESULT_LOAD_IMAGE
                && resultCode == RESULT_OK && null != data) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();
                adapter.setBrowserImagePath(imagePath);

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
                adapter.setBrowserImagePath(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }*//*
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }
                Uri result = null;
                try {
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;
            try {
                shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            } catch (Exception e) {

            }

            web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
            web_disply.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
