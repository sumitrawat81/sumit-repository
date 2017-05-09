package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.ComposeMsgTo;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/9/16.
 */
public class ComposeMessageFragment extends BaseFragment implements View.OnClickListener {


    UserLoginDetails.LoginDetails details;
    private View mProgressView;
    private Spinner mSelectDoctorSpinner;
    private EditText mSub;
    private EditText mContent;
    private Button mDSubmit;
    private List<ComposeMsgTo.DataBean> titleBeenArrayList = new ArrayList<>();
    private List<String> mList_Title = null;
    private List<String> mList_Title_ID = null;
    Callback<ComposeMsgTo> composeToPModelCallback = new Callback<ComposeMsgTo>() {
        @Override
        public void onResponse(Call<ComposeMsgTo> call, Response<ComposeMsgTo> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            titleBeenArrayList = response.body().getData();
                            mList_Title.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mList_Title.add(response.body().getData().get(i).getName());
                                mList_Title_ID.add(response.body().getData().get(i).getUser_seq());
                            }
                            mSelectDoctorSpinner.setAdapter(new SpinnerAdapter(getActivity(), 0, mList_Title));
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ComposeMsgTo> call, Throwable t) {
            t.printStackTrace();
        }
    };
    private ScrollView mScrollView;
    Callback<RegisterResponseModel> modelCallback = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                        Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        mSub.setText("");
                        mContent.setText("");
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = inflater.inflate(R.layout.fragment_mymessage_compose_message, container, false);
        initiateUi(view);
        details = MyPrefs.getLoginDetails(getActivity());
        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getComposerMsgTo("C0001", details.getUser_seq(),selLangToSend).enqueue(composeToPModelCallback);
        }
        return view;
    }

    public void initiateUi(View view) {

        mProgressView = view.findViewById(R.id.progress);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);

        mSelectDoctorSpinner = (Spinner) view.findViewById(R.id.mSelectDoctorSpinner);
        mSub = (EditText) view.findViewById(R.id.mSub);
        mContent = (EditText) view.findViewById(R.id.mContent);
        mDSubmit = (Button) view.findViewById(R.id.mDSubmit);
        mList_Title = new ArrayList<>();
        mList_Title_ID = new ArrayList<>();
        mSelectDoctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    mSelectDoctorSpinner.setTag(mList_Title_ID.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mDSubmit.setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mDSubmit:
                submitMessage();
                break;

        }
    }

    private void submitMessage() {

        String strSubject = mSub.getText().toString();
        String strContemt = mContent.getText().toString();
        String strSelectedDoId = (String) mSelectDoctorSpinner.getTag();
        UserLoginDetails.LoginDetails details = MyPrefs.getLoginDetails(getActivity());
        String strSenderId = details.getUser_seq();


        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(strSelectedDoId)) {
            cancel = true;
        } else if (TextUtils.isEmpty(strSubject)) {
            mSub.setError(getString(R.string.error_str_subject));
            focusView = mSub;
            cancel = true;
        } else if (TextUtils.isEmpty(strContemt)) {
            mContent.setError(getString(R.string.error_str_content));
            focusView = mContent;
            cancel = true;
        }


        if (cancel) {
            if (focusView != null) {
                try {
                    focusView.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().sendMessgae(strSelectedDoId, strSenderId, strSubject, strContemt,selLangToSend).enqueue(modelCallback);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
