package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.RetrofitUtils;
import com.sibsefid.model.doctor.RegisterModel;
import com.sibsefid.model.doctor.SendMobileVerifyCodeModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.doctor.VerifyMobileCodeModel;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.model.patient.StateList;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.KeyValues;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ubuntu on 3/9/16.
 */
public class MyProfileFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "MyProfileFragment";
    private static final int REQUEST_CODE_DOC = 3;
    private Context mContext = null;
    private DoctorActivity activity = null;
    private SpinnerAdapter mSPiSpinnerAdapter = null;
    private Spinner mSpinner_Title = null, mSpinner_Day, mSpinner_Month, mSpinner_Year, mSpinner_Gender, mSpinner_Country, mSpinner_State, mSpinner_Mobile;
    private TextView mTextView = null;
    private ImageView mProfileImage;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mNPINumber;
    private EditText mSelectCity;
    private EditText mAddress_1;
    private EditText mAddress_2;
    private EditText mZipCode;
    private ImageView mUpdateImageBtn;
    private EditText mPhoneNumber;
    private EditText mVerifyCode;
    private EditText mQualification;
    private EditText mSpokenLanguage;
    private TextView mSignature;
    private TextView mResume;
    private EditText mAbout;
    private EditText mConsultFee;
    private Button mSaveBtn;
    private Button verifyButton;
    private Button msendCodeButton;
    private TextView veryfyOrNot;
    private ScrollView mDetailsForm;
    private View mProgressView;
    private UserLoginDetails.LoginDetails dProfileDetails;
    private UserLoginDetails.LoginDetails details;

    private String imagePath = "";
    private String signatureImagePath = "";
    private String docFilePath = "";

    public static String getExtension(String filePath) {
        File f = new File(filePath);
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(".");

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.myprofile));
        activity.forwardShowImg();
        details = MyPrefs.getLoginDetails(getActivity());
        dProfileDetails=details;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        EventBus.getDefault().register(this);
        initComponents(view);
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getUserProfile(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }
        updateUi();
        return view;
    }
    Callback<UserLoginDetails> updateProfileListner = new Callback<UserLoginDetails>() {
        @Override
        public void onResponse(Call<UserLoginDetails> call, Response<UserLoginDetails> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    try {
                        Log.e(TAG, "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);

                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getLogindetails() != null && response.body().getLogindetails().size() > 0) {
                            int LOGINTYPE = MyPrefs.getUser(getActivity());

                            if (LOGINTYPE == 1) {

                                MyPrefs.saveUserMsgCountl(getActivity(), "0");
                                response.body().getLogindetails().get(0).setType(1);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));

                            } else if (LOGINTYPE == 2) {

                                response.body().getLogindetails().get(0).setType(2);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                                UserLoginDetails.LoginDetails model = new UserLoginDetails.LoginDetails();
                                model = response.body().getLogindetails().get(0);
                                EventBus.getDefault().post(model);

                            }
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else if ((!response.body().isSuccess())) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UserLoginDetails> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
            }

        }
    };
    private int RESULT_LOAD_IMAGE_Profile = 1;
    private int CAMERA_REQUEST_Profile = 1888;
    private int RESULT_LOAD_IMAGE_Signature = 2;
    private int CAMERA_REQUEST_Signature = 1999;
    private Uri mCapturedImageURI = null;
    private Bitmap finalBitmap = null;
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    private List<String> mList_Day = null;
    private List<String> mList_Month = null;
    private List<String> mList_Year = null;
    private List<String> mList_Country = null;
    private List<String> mList_State = null;
    private List<String> mList_Mobile = null;
    private List<String> mList_MobileId = null;
    private List<String> mList_Gender = null;
    private HashMap<String, String> hashMapCountry = new HashMap<>();
    private ArrayList<CountryBean.DataBean> countryArrayList = new ArrayList<>();
    private HashMap<String, String> hashMapState = new HashMap<>();
    private boolean state_flag = true;

    Callback<StateList> stateListCallback = new Callback<StateList>() {
        @Override
        public void onResponse(Call<StateList> call, Response<StateList> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getCity() != null && response.body().getCity().size() > 0) {
                        if (response.body().getCity() != null) {
                            mList_State.clear();
                            for (int i = 0; i < response.body().getCity().size(); i++) {
                                mList_State.add(response.body().getCity().get(i).getDetail_code_nm());
                                hashMapState.put(response.body().getCity().get(i).getDetail_code_nm(), response.body().getCity().get(i).getDetail_code());
                            }
                            mSpinner_State.setAdapter(new SpinnerAdapter(activity, 0, mList_State));
                            if (dProfileDetails != null) {
                                if (state_flag) {
                                    String strStateName = dProfileDetails.getState_nm().trim();
                                    if (!TextUtils.isEmpty(strStateName)) {
                                        for (int i = 0; i < mList_State.size(); i++) {
                                            if (mList_State.get(i).equalsIgnoreCase(strStateName)) {
                                                mSpinner_State.setSelection(i);
                                                mSpinner_State.setTag(response.body().getCity().get(i).getDetail_code());
                                                state_flag = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<StateList> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }

        }
    };
    Callback<UserLoginDetails> detailsCallback = new Callback<UserLoginDetails>() {
        @Override
        public void onResponse(Call<UserLoginDetails> call, Response<UserLoginDetails> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    try {
                        Log.e(TAG, "onResponse: " + response.raw().body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getLogindetails() != null && response.body().getLogindetails().size() > 0) {
                            int LOGINTYPE = MyPrefs.getUser(getActivity());

                            if (LOGINTYPE == 1) {

                                MyPrefs.saveUserMsgCountl(getActivity(), "0");
                                response.body().getLogindetails().get(0).setType(1);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                               /* UserLoginDetails.LoginDetails model=new UserLoginDetails.LoginDetails();
                                model=response.body().getLogindetails().get(0);
                                EventBus.getDefault().post(model);*/
                                updateUi();

                            } else if (LOGINTYPE == 2) {

                                response.body().getLogindetails().get(0).setType(2);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                                UserLoginDetails.LoginDetails model = new UserLoginDetails.LoginDetails();
                                model = response.body().getLogindetails().get(0);
                                EventBus.getDefault().post(model);

                            }
                            updateUi();
                            //  Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else if ((!response.body().isSuccess())) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UserLoginDetails> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    public void initComponents(View view) {
        mDetailsForm = (ScrollView)view.findViewById(R.id.details_form_scroll);
        mProgressView = view.findViewById(R.id.progress);
        mProfileImage = (ImageView) view.findViewById(R.id.mProfileImage);
        mFirstName = (EditText) view.findViewById(R.id.mFirstName);
        mLastName = (EditText) view.findViewById(R.id.mLastName);
        mEmail = (EditText) view.findViewById(R.id.mEmail);
        mNPINumber = (EditText) view.findViewById(R.id.mNPINumber);
        mSelectCity = (EditText) view.findViewById(R.id.mSelectCity);
        mAddress_1 = (EditText) view.findViewById(R.id.mAddress_1);
        mAddress_2 = (EditText) view.findViewById(R.id.mAddress_2);
        mZipCode = (EditText) view.findViewById(R.id.mZipCode);
        mPhoneNumber = (EditText) view.findViewById(R.id.mPhoneNumber);
        mVerifyCode = (EditText) view.findViewById(R.id.mVerifyCode);
        mQualification = (EditText) view.findViewById(R.id.mQualification);
        mSpokenLanguage = (EditText) view.findViewById(R.id.mSpokenLanguage);
        mSignature = (TextView) view.findViewById(R.id.mSignature);
        mResume = (TextView) view.findViewById(R.id.mResume);
        mAbout = (EditText) view.findViewById(R.id.mAbout);
        mConsultFee = (EditText) view.findViewById(R.id.mConsultFee);
        mUpdateImageBtn = (ImageView) view.findViewById(R.id.mUpdateImageBtn);

        mSpinner_Title = (Spinner) view.findViewById(R.id.spinner_Title);
        mSpinner_Day = (Spinner) view.findViewById(R.id.spinner_Day);
        mSpinner_Month = (Spinner) view.findViewById(R.id.spinner_Month);
        mSpinner_Year = (Spinner) view.findViewById(R.id.spinner_Year);
        mSpinner_Gender = (Spinner) view.findViewById(R.id.spinner_Gender);
        mSpinner_Country = (Spinner) view.findViewById(R.id.spinner_Country);
        mSpinner_State = (Spinner) view.findViewById(R.id.spinner_State);
        mSpinner_Mobile = (Spinner) view.findViewById(R.id.spinner_MobileNumber);
        mSaveBtn = (Button) view.findViewById(R.id.mSaveBtn);
        veryfyOrNot = (TextView) view.findViewById(R.id.veryfyOrNot);
        verifyButton = (Button) view.findViewById(R.id.verifyButton);
        msendCodeButton = (Button) view.findViewById(R.id.msendCodeButton);

        mSaveBtn.setOnClickListener(this);
        verifyButton.setOnClickListener(this);
        msendCodeButton.setOnClickListener(this);
        mUpdateImageBtn.setOnClickListener(this);
        mResume.setOnClickListener(this);
        mSignature.setOnClickListener(this);
        countryArrayList = MyPrefs.getCountryList(getActivity());
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {

        mList_Title = new ArrayList<>();
        mList_Title_ID = new ArrayList<>();
        mList_Day = new ArrayList<>();
        mList_Month = new ArrayList<>();
        mList_Year = new ArrayList<>();
        mList_Gender = new ArrayList<>();
        mList_Country = new ArrayList<>();
        mList_State = new ArrayList<>();
        mList_Mobile = new ArrayList<>();

        for (int j = 1; j < 31; j++) {
            mList_Day.add(j + "");
        }
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = symbols.getMonths();
        for (int i = 0; i < monthNames.length; i++) {
            mList_Month.add(monthNames[i]);
        }
        for (int j = 1970; j < 2017; j++) {
            mList_Year.add(j + "");
        }

        if (countryArrayList != null) {
            for (int i = 0; i < countryArrayList.size(); i++) {
                mList_Country.add(countryArrayList.get(i).getDetail_code_nm());
                hashMapCountry.put(countryArrayList.get(i).getDetail_code_nm(), countryArrayList.get(i).getDetail_code());
            }
        }

        mList_Gender.add(getActivity().getResources().getString(R.string.male));
        mList_Gender.add(getActivity().getResources().getString(R.string.female));

        mList_Title = activity.getmList_Title();
        mList_Title_ID = activity.getmList_Title_ID();
        if (mList_Title == null)
            mList_Title = new ArrayList<>();

        mSpinner_Title.setAdapter(new SpinnerAdapter(activity, 0, mList_Title));
        mSpinner_Day.setAdapter(new SpinnerAdapter(activity, 0, mList_Day));
        mSpinner_Month.setAdapter(new SpinnerAdapter(activity, 0, mList_Month));
        mSpinner_Year.setAdapter(new SpinnerAdapter(activity, 0, mList_Year));
        mSpinner_Gender.setAdapter(new SpinnerAdapter(activity, 0, mList_Gender));
        mSpinner_Country.setAdapter(new SpinnerAdapter(activity, 0, mList_Country));


        mSpinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sex = mSpinner_Gender.getSelectedItem().toString();
                if (sex.equalsIgnoreCase(getActivity().getResources().getString(R.string.male))) {
                    mSpinner_Gender.setTag(1);
                } else if (sex.equalsIgnoreCase(getActivity().getResources().getString(R.string.female))) {
                    mSpinner_Gender.setTag(2);
                } else {
                    mSpinner_Gender.setTag(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinner_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinner_Year.setTag(mSpinner_Year.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinner_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinner_Month.setTag((position + 1) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinner_Day.setTag(mSpinner_Day.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinner_Title.setTag(mList_Title_ID.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinner_Country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mName = mList_Country.get(position);
                mSpinner_Country.setTag(hashMapCountry.get(mName));
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mName.trim()),selLang).enqueue(stateListCallback);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinner_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mName = mList_Country.get(position);
                mSpinner_State.setTag(hashMapState.get(mSpinner_State.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mSpinner_Mobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSpinner_Mobile.setTag(mList_MobileId.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {


                mList_Mobile = (List<String>) Utils.loadJSONFromAsset(getActivity())[0];
                mList_MobileId = (List<String>) Utils.loadJSONFromAsset(getActivity())[1];
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mSpinner_Mobile.setAdapter(new SpinnerAdapter(activity, 0, mList_Mobile));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void updateUi() {

        if (dProfileDetails != null) {
            String mName = dProfileDetails.getCountry();
            if (!TextUtils.isEmpty(mName)) {
                showProgress(true);
                String selLang=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().getStateList(hashMapCountry.get(mName.trim()),selLang).enqueue(stateListCallback);
            }
            if (dProfileDetails.getDob().length() > 0) {
                String dobArray[] = dProfileDetails.getDob().split("/");
                String dbb_Month = dobArray[0];
                String dbb_Day = dobArray[1];
                String dbb_Year = dobArray[2];

                if (dobArray != null && dobArray.length > 0) {
                    for (int i = 0; i < mList_Day.size(); i++) {
                        if (Integer.valueOf(mList_Day.get(i)) == Integer.valueOf(dbb_Day)) {
                            mSpinner_Day.setSelection(i);
                        }
                    }
                    for (int i = 0; i < mList_Month.size(); i++) {
                        if ((i + 1) == Integer.valueOf(dbb_Month)) {
                            mSpinner_Month.setSelection(i);
                        }
                    }
                    String year = dbb_Year.split(" ")[0];

                    if (mList_Year.contains(year.trim())) {
                        mSpinner_Year.setSelection(mList_Year.indexOf(year.trim()));
                    }
                }
            }

            mFirstName.setText(dProfileDetails.getFamily_name());
            mLastName.setText(dProfileDetails.getGiven_name());
            mEmail.setText(dProfileDetails.getEmail());
            mNPINumber.setText(dProfileDetails.getNPI_number());
            mSelectCity.setText(dProfileDetails.getCity());
            mAddress_1.setText(dProfileDetails.getAddress_1());
            mAddress_2.setText(dProfileDetails.getAddress_2());
            mZipCode.setText(dProfileDetails.getPostcode());
            mPhoneNumber.setText(dProfileDetails.getPhone());
            mVerifyCode.setText(dProfileDetails.getVerifiedCode());
            mQualification.setText(dProfileDetails.getQualifications());
            mSpokenLanguage.setText(dProfileDetails.getLanguageSpoken());

           /* if(dProfileDetails.getSignature()!=null){
                mSignature.setText(dProfileDetails.getSignature());
            }
            if(dProfileDetails.getDoc_resume()!=null){
                mResume.setText(dProfileDetails.getDoc_resume());
            }*/

            mAbout.setText(dProfileDetails.getAboutMe());
            mConsultFee.setText(dProfileDetails.getFeeClient());
            if(dProfileDetails.getIsMobileVerified().equalsIgnoreCase("True"))
            {
                veryfyOrNot.setText("Verified");
                veryfyOrNot.setTextColor(getActivity().getResources().getColor(R.color.q_green));

            }

            String strCountryName = dProfileDetails.getCountry_nm().trim();
            String strNuCodeId = dProfileDetails.getCode().trim();
            String strTitleName = dProfileDetails.getTitle_nm().trim();

            if (!TextUtils.isEmpty(strCountryName)) {
                for (int i = 0; i < mList_Country.size(); i++) {
                    if (mList_Country.get(i).toLowerCase().equalsIgnoreCase(strCountryName.toLowerCase())) {
                        mSpinner_Country.setSelection(i);
                        break;
                    }
                }
            }


            if (!TextUtils.isEmpty(dProfileDetails.getSex())) {
                int sex = Integer.valueOf(dProfileDetails.getSex().trim());
                if (sex == 1) {
                    mSpinner_Gender.setSelection(0);
                } else if (sex == 2) {
                    mSpinner_Gender.setSelection(1);
                } else {
                    mSpinner_Gender.setSelection(0);
                }
            }


            if (!TextUtils.isEmpty(dProfileDetails.getTitle_nm())) {

                for (int i = 0; i < mList_Title.size(); i++) {
                    if (mList_Title.get(i).toLowerCase().equalsIgnoreCase(strTitleName.toLowerCase())) {
                        mSpinner_Title.setSelection(i);
                        break;
                    }
                }
            }


            if (!TextUtils.isEmpty(dProfileDetails.getCode())) {

                for (int i = 0; i < mList_MobileId.size(); i++) {
                    if (mList_MobileId.get(i).equalsIgnoreCase(strNuCodeId)) {
                        try {
                            mSpinner_Mobile.setSelection(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            if (!TextUtils.isEmpty(dProfileDetails.getAddress_1())) {

                mAddress_1.setText(dProfileDetails.getAddress_1());
            }
            if (!TextUtils.isEmpty(dProfileDetails.getAddress_2())) {

                mAddress_1.setText(dProfileDetails.getAddress_2());
            }

            if (!TextUtils.isEmpty(dProfileDetails.getPostcode())) {

                mZipCode.setText(dProfileDetails.getPostcode());
            }

            try {
                Picasso.with(getActivity())
                        .load(dProfileDetails.getPhoto())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .into(mProfileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mDetailsForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
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
           mDetailsForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


    Callback<VerifyMobileCodeModel> verifyMobileListner = new Callback<VerifyMobileCodeModel>() {
        @Override
        public void onResponse(Call<VerifyMobileCodeModel> call, Response<VerifyMobileCodeModel> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            veryfyOrNot.setText("Verified");
                            veryfyOrNot.setTextColor(getActivity().getResources().getColor(R.color.q_green));
                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null ) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<VerifyMobileCodeModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), "Failed To Verify !!", Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };

    Callback<SendMobileVerifyCodeModel> sendCodeMobileListner = new Callback<SendMobileVerifyCodeModel>() {
        @Override
        public void onResponse(Call<SendMobileVerifyCodeModel> call, Response<SendMobileVerifyCodeModel> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getData().getTable1()!=null) {

                            String verifyCode=response.body().getData().getTable1().get(0).getVerifiedCode();
                            mVerifyCode.setText(verifyCode);
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<SendMobileVerifyCodeModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), "Failed To Verify !!", Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mUpdateImageBtn:
                select_Image("profile");
                break;
            case R.id.mSaveBtn:
                updateProfileData();
                break;

            case R.id.mResume:
                getDocument();
                break;

            case R.id.mSignature:
                select_Image("signature");
                break;

            case R.id.msendCodeButton:

                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().sendCodeUserMobile(details.getUser_seq(),selLangToSend).enqueue(sendCodeMobileListner);

                }else{
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.verifyButton:
                String verifiedCode=mVerifyCode.getText().toString().trim();
                if(TextUtils.isEmpty(verifiedCode)){
                    Toast.makeText(getActivity(),"your phone no. is not verified!!",Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().VerifyUserMobile(details.getUser_seq(),verifiedCode,selLangToSend).enqueue(verifyMobileListner);

                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }

    private void getDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }

    private void updateProfileData() {

        String strmFirstName = mFirstName.getText().toString().trim();
        String strmLastName = mLastName.getText().toString().trim();
        String strmEmail = mEmail.getText().toString().trim();
        String strmNPINumber = mNPINumber.getText().toString().trim();
        String strmSelectCity = mSelectCity.getText().toString().trim();

        String strmAddress_1 = mAddress_1.getText().toString().trim();
        String strmAddress_2 = mAddress_2.getText().toString().trim();
        String strmZipCode = mZipCode.getText().toString().trim();
        String strmPhoneNumber = mPhoneNumber.getText().toString().trim();
        String strmQualification = mQualification.getText().toString().trim();

        String strmSpokenLanguage = mSpokenLanguage.getText().toString().trim();
        String strmSignature = mSignature.getText().toString().trim();
        String strmResume = mResume.getText().toString().trim();
        String strmAbout = mAbout.getText().toString().trim();
        String strmConsultFee = mConsultFee.getText().toString();


        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(strmFirstName) && !isName(strmFirstName)) {
            mFirstName.setError(getString(R.string.error_invalid_first_name));
            focusView = mFirstName;
            cancel = true;
        } else if (TextUtils.isEmpty(strmLastName) && !isName(strmLastName)) {
            mLastName.setError(getString(R.string.error_invalid_last_name));
            focusView = mLastName;
            cancel = true;
        } else if (TextUtils.isEmpty(strmPhoneNumber) && !isName(strmPhoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mPhoneNumber;
            cancel = true;
        } else if (TextUtils.isEmpty(strmNPINumber) || !isName(strmNPINumber)) {
            mNPINumber.setError(getString(R.string.error_invalid_npi_number));
            focusView = mNPINumber;
            cancel = true;
        } else if (TextUtils.isEmpty(strmSpokenLanguage) && !isName(strmSpokenLanguage)) {
            mSpokenLanguage.setError(getString(R.string.error_invalid_spoken_language));
            focusView = mSpokenLanguage;
            cancel = true;
        } else if (TextUtils.isEmpty(strmAddress_1)) {
            mAddress_1.setError(getString(R.string.error_invalid_address));
            focusView = mAddress_1;
            cancel = true;
        } else if (TextUtils.isEmpty(strmAddress_2)) {
            mAddress_2.setError(getString(R.string.error_invalid_address));
            focusView = mAddress_2;
            cancel = true;
        } else if (TextUtils.isEmpty(strmZipCode) || !isZip(strmZipCode)) {
            mZipCode.setError(getString(R.string.error_invalid_zip));
            focusView = mZipCode;
            cancel = true;
        } else if (TextUtils.isEmpty(strmQualification) ) {
            mQualification.setError(getString(R.string.error_invalid_qualification));
            focusView = mQualification;
            cancel = true;
        }else if (TextUtils.isEmpty(strmConsultFee)) {
            mConsultFee.setError(getString(R.string.error_invalid_fee));
            focusView = mConsultFee;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            RegisterModel model = new RegisterModel();


            String specilistItemId = details.getSpeciality();
            String liceNo = details.getOther_licenses();
            String dobStr = mSpinner_Month.getTag() + "/" + (mSpinner_Day.getTag() + "/" + mSpinner_Year.getTag());
            if (TextUtils.isEmpty(specilistItemId))
                specilistItemId = "0";

            if (TextUtils.isEmpty(liceNo))
                liceNo = "4550";

            model.setFirstName(strmFirstName);
            model.setLastName(strmLastName);
            model.setCountry(Integer.valueOf((String) mSpinner_Country.getTag()));
            model.setSpeciality(Integer.valueOf(specilistItemId));
            model.setPyear(details.getPratice_years());
            model.setPassword(details.getUser_password());
            model.setPhone(strmPhoneNumber);
            model.setNPINumber(strmNPINumber);
            model.setState(Integer.valueOf((String) mSpinner_State.getTag()));
            model.setCity(strmSelectCity);
            model.setLicenseNumber(liceNo);
            model.setSpokenLanguage(strmSpokenLanguage);

            model.setGender_int(Integer.valueOf((Integer) mSpinner_Gender.getTag()));
            model.setAddress1(strmAddress_1);
            model.setAddress2(strmAddress_2);
            model.setAboutMe(strmAbout);
            model.setQualification(strmQualification);

            model.setMobilecode(Integer.valueOf((String) mSpinner_Mobile.getTag()));
            model.setDob(dobStr);
            model.setPostCode(strmZipCode);
            model.setTitle(String.valueOf(mSpinner_Title.getTag()));
            model.setDoctorId(details.getUser_seq());
            model.setDoc_resume(strmResume);
            model.setDoc_Sign(mSignature.getText().toString());
            model.setConsultFee(strmConsultFee);
            model.setImage(imagePath);


            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);

                HashMap<String, String> requestValuePairsMap = new HashMap<>();
                requestValuePairsMap.put(KeyValues.ARG_D_FirstName, model.getFirstName());
                requestValuePairsMap.put(KeyValues.ARG_D_LastName, model.getLastName());
                requestValuePairsMap.put(KeyValues.ARG_D_Password, model.getPassword());
                requestValuePairsMap.put(KeyValues.ARG_D_City, model.getCity());
                requestValuePairsMap.put(KeyValues.ARG_D_Phone, model.getPhone());
                requestValuePairsMap.put(KeyValues.ARG_D_NPINumber, model.getNPINumber());
                requestValuePairsMap.put(KeyValues.ARG_D_LicenseNumber, model.getLicenseNumber());
                requestValuePairsMap.put(KeyValues.ARG_D_Pyear, model.getPyear());
                requestValuePairsMap.put(KeyValues.ARG_D_SpokenLanguage, model.getSpokenLanguage());
                requestValuePairsMap.put(KeyValues.ARG_D_State, String.valueOf(model.getState()));
                requestValuePairsMap.put(KeyValues.ARG_D_Country, String.valueOf(model.getCountry()));
                requestValuePairsMap.put(KeyValues.ARG_D_Title, String.valueOf(model.getTitle()));
                requestValuePairsMap.put(KeyValues.ARG_D_Speciality, String.valueOf(model.getSpeciality()));
                requestValuePairsMap.put(KeyValues.ARG_D_DOCTOR_ID_UPDATE, model.getDoctorId());
                requestValuePairsMap.put(KeyValues.ARG_D_Address11, model.getAddress1());
                requestValuePairsMap.put(KeyValues.ARG_D_Address21, model.getAddress2());
                requestValuePairsMap.put(KeyValues.ARG_D_POST_CODE, model.getPostCode());
                requestValuePairsMap.put(KeyValues.ARG_D_gender, String.valueOf(model.getGender_int()));
                requestValuePairsMap.put(KeyValues.ARG_D_DOB, model.getDob());
                requestValuePairsMap.put(KeyValues.ARG_D_Qualification, model.getQualification());
                requestValuePairsMap.put(KeyValues.ARG_D_AboutMe, model.getAboutMe());
                requestValuePairsMap.put(KeyValues.ARG_ConsultFee, model.getConsultFee());
                requestValuePairsMap.put(KeyValues.ARG_D_Mobilecode, String.valueOf(model.getMobilecode()));

                if (model.getDoc_resume().length() > 0 && model.getDoc_Sign().length() > 0 && model.getImage().length() > 0) {

                    if (TextUtils.equals(getExtension(model.getDoc_resume()), "pdf")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResSign(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                                model.getImage(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                                model.getDoc_Sign(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_PDF))
                                .enqueue(updateProfileListner);
                    } else if (TextUtils.equals(getExtension(model.getDoc_resume()), "doc")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResSign(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                                model.getImage(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                                model.getDoc_Sign(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_DOC))
                                .enqueue(updateProfileListner);
                    } else {
                        showProgress(false);
                        Toast.makeText(getActivity(), "Resume File Format is Incorrect!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (model.getDoc_resume().length() <= 0 && model.getDoc_Sign().length() <= 0 && model.getImage().length() > 0) {
                    RestAdapter.getAdapter()
                            .addRegularUserWithProfileImg(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                    RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                            model.getImage(),
                                            RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                            .enqueue(updateProfileListner);
                } else if (model.getImage().length() > 0 && model.getDoc_Sign().length() > 0 && model.getDoc_resume().length() <= 0) {

                    RestAdapter.getAdapter()
                            .addRegularUserWithResumeImage(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                    RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                            model.getImage(),
                                            RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                    RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                            model.getDoc_Sign(),
                                            RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                            .enqueue(updateProfileListner);
                } else if (model.getImage().length() > 0 && model.getDoc_Sign().length() <= 0 && model.getDoc_resume().length() > 0) {


                    if (TextUtils.equals(getExtension(model.getDoc_resume()), "pdf")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResumeImage(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                                model.getImage(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_PDF))
                                .enqueue(updateProfileListner);
                    } else if (TextUtils.equals(getExtension(model.getDoc_resume()), "doc")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResumeImage(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Image,
                                                model.getImage(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),

                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_DOC))
                                .enqueue(updateProfileListner);
                    } else {
                        showProgress(false);
                        Toast.makeText(getActivity(), "Resume File Format is Incorrect!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (model.getImage().length() <= 0 && model.getDoc_Sign().length() > 0 && model.getDoc_resume().length() > 0) {


                    if (TextUtils.equals(getExtension(model.getDoc_resume()), "pdf")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResumeImage(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                                model.getDoc_Sign(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_PDF))
                                .enqueue(updateProfileListner);
                    } else if (TextUtils.equals(getExtension(model.getDoc_resume()), "doc")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithResumeImage(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                                model.getDoc_Sign(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL),

                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_DOC))
                                .enqueue(updateProfileListner);
                    } else {
                        showProgress(false);
                        Toast.makeText(getActivity(), "Resume File Format is Incorrect!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (model.getImage().length() <= 0 && model.getDoc_Sign().length() <= 0 && model.getDoc_resume().length() > 0) {


                    if (TextUtils.equals(getExtension(model.getDoc_resume()), "pdf")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithProfileImg(RetrofitUtils.createMultipartRequest(requestValuePairsMap),

                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_PDF))
                                .enqueue(updateProfileListner);
                    } else if (TextUtils.equals(getExtension(model.getDoc_resume()), "doc")) {
                        RestAdapter.getAdapter()
                                .addRegularUserWithProfileImg(RetrofitUtils.createMultipartRequest(requestValuePairsMap),

                                        RetrofitUtils.createFilePart(KeyValues.ARG_D_Resume,
                                                model.getDoc_resume(),
                                                RetrofitUtils.MEDIA_TYPE_DOC))
                                .enqueue(updateProfileListner);
                    } else {
                        showProgress(false);
                        Toast.makeText(getActivity(), "Resume File Format is Incorrect!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (model.getImage().length() <= 0 && model.getDoc_Sign().length() > 0 && model.getDoc_resume().length() <= 0) {

                    RestAdapter.getAdapter()
                            .addRegularUserWithProfileImg(RetrofitUtils.createMultipartRequest(requestValuePairsMap),

                                    RetrofitUtils.createFilePart(KeyValues.ARG_D_Sign,
                                            model.getDoc_Sign(),
                                            RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                            .enqueue(updateProfileListner);
                } else if (model.getImage().length() <= 0 && model.getDoc_Sign().length() <= 0 && model.getDoc_resume().length() <= 0) {

                    RestAdapter.getAdapter()
                            .addRegularUser(RetrofitUtils.createMultipartRequest(requestValuePairsMap))
                            .enqueue(updateProfileListner);
                }
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void select_Image(final String from) {

        final CharSequence[] items1 = {getActivity().getResources().getString(R.string.user_camera), getActivity().getResources().getString(R.string.choose_exsting)};
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle(getActivity().getResources().getString(R.string.choose_option));

        builder1.setItems(items1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (from.equalsIgnoreCase("profile")) {
                    if (items1[item].equals(getActivity().getResources().getString(R.string.choose_exsting))) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE_Profile);
                    } else if (items1[item].equals(getActivity().getResources().getString(R.string.user_camera))) {
                        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                        if (isSDPresent) {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, getActivity().getResources().getString(R.string.app_name));
                            mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST_Profile);
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_sd_card), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (from.equalsIgnoreCase("signature")) {
                    if (items1[item].equals(getActivity().getResources().getString(R.string.choose_exsting))) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE_Signature);
                    } else if (items1[item].equals(getActivity().getResources().getString(R.string.user_camera))) {
                        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                        if (isSDPresent) {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, getActivity().getResources().getString(R.string.app_name));
                            mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST_Signature);
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_sd_card), Toast.LENGTH_SHORT).show();
                        }
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
        if (requestCode == RESULT_LOAD_IMAGE_Profile
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
                mProfileImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST_Profile && resultCode == RESULT_OK) {

            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(column_index);
                cursor.close();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                mProfileImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == RESULT_LOAD_IMAGE_Signature
                && resultCode == RESULT_OK && null != data) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                signatureImagePath = cursor.getString(columnIndex);
                cursor.close();
                mSignature.setText(signatureImagePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST_Signature && resultCode == RESULT_OK) {

            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndex(filePathColumn[0]);
                signatureImagePath = cursor.getString(column_index);
                cursor.close();
                mSignature.setText(signatureImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_DOC && resultCode == RESULT_OK) {

            Uri fileuri = data.getData();
            docFilePath = getFileNameByUri(getActivity(), fileuri);
            mResume.setText(docFilePath);
        }

    }

    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isName(String name) {
        return name.length() >=4;
    }
    private boolean isZip(String name) {
        return name.length() >=6;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onEvent(Object[] objects) {

        if (getActivity() != null) {
            mList_Title = (List<String>) objects[0];
            mList_Title_ID = (List<String>) objects[1];
        }

    }

}
