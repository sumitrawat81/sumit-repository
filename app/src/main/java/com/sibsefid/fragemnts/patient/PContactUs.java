package com.sibsefid.fragemnts.patient;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PContactUs extends Fragment implements View.OnClickListener {

    public static final String TAG = "PContactUs";

    private PatientActivity activity;

    private EditText mName;

    private EditText mEmail;

    private EditText mMobile;

    private EditText mSubject;

    private EditText mMessage;

    private Button mSendMsgBtn;

    private View mProgressView;

    private ScrollView mScrollView;
    Callback<RegisterResponseModel> modelCallback = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                        Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        mName.setText("");
                        mEmail.setText("");
                        mMobile.setText("");
                        mSubject.setText("");
                        mMessage.setText("");
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(),"Failed !!", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        }
    };

    public PContactUs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.contact_us));
        activity.forwardShowImg();
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

        View view = inflater.inflate(R.layout.fragment_pcontact_us, container, false);
        initiateUi(view);
        return view;
    }

    private void initiateUi(View view) {

        mName = (EditText) view.findViewById(R.id.mName);
        mEmail = (EditText) view.findViewById(R.id.mEmail);
        mMobile = (EditText) view.findViewById(R.id.mMobile);
        mSubject = (EditText) view.findViewById(R.id.mSubject);
        mMessage = (EditText) view.findViewById(R.id.mMessage);
        mSendMsgBtn = (Button) view.findViewById(R.id.mSendMsgBtn);

        mProgressView = view.findViewById(R.id.progress);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);
        mSendMsgBtn.setOnClickListener(this);


    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isName(String name) {
        return name.length() > 4;
    }

    private void sendContactUs() {


        mName.setError(null);
        mEmail.setError(null);
        mMobile.setError(null);
        mSubject.setError(null);
        mMessage.setError(null);

        String strName = mName.getText().toString();
        String strEmail = mEmail.getText().toString();
        String strPhoneNumber = mMobile.getText().toString();
        String strmSubject = mSubject.getText().toString();
        String strmMessage = mMessage.getText().toString();


        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(strName) && !isName(strName)) {
            mName.setError(getString(R.string.error_invalid_first_name));
            focusView = mName;
            cancel = true;
        } else if (TextUtils.isEmpty(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        } else if (!Utils.isEmailValid(strEmail)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        } else if (TextUtils.isEmpty(strPhoneNumber)) {
            mName.setError(getString(R.string.error_invalid_phone_number));
            focusView = mName;
            cancel = true;
        } else if (TextUtils.isEmpty(strmMessage)) {
            mMessage.setError(getString(R.string.error_invalid_message));
            focusView = mMessage;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().contactUs(strEmail, strPhoneNumber, strmSubject, strmMessage, strName,selLangToSend).enqueue(modelCallback);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            mScrollView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }


    @Override
    public void onClick(View view) {
        if (mSendMsgBtn == view) {
            sendContactUs();
        }
    }
}
