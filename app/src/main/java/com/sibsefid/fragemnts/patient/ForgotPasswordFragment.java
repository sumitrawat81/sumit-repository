package com.sibsefid.fragemnts.patient;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.patient.ForgotPasswordModel;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "ForgotPasswordFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

private AutoCompleteTextView email;
    private Button email_sign_in_button;
    private ScrollView login_form;
    private String mParam1;
    private String mParam2;
    private ProgressBar login_progress;

    Callback<ForgotPasswordModel> loginModelCallback = new Callback<ForgotPasswordModel>() {
        @Override
        public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
            if (getActivity() != null) {
                showProgress(false);

                if (response.isSuccessful()) {

                /*  if ((!response.body().isSuccess())) {

                        }

*/
                    String msg=response.body().getMessage().get(0).getMsg().toString();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<ForgotPasswordModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
            }

        }
    };



    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        View view=inflater.inflate(R.layout.fragment_forgot_password,container,false);

        email=(AutoCompleteTextView)view.findViewById(R.id.email);
        email_sign_in_button=(Button)view.findViewById(R.id.email_sign_in_button);
        login_progress=(ProgressBar)view.findViewById(R.id.login_progress);
        login_form=(ScrollView)view.findViewById(R.id.login_form);

        email_sign_in_button.setOnClickListener(this);
        return view;

    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.email_sign_in_button:

                boolean cancel = false;
                View focusView = null;

                String strEmail=email.getText().toString().trim();
                if (TextUtils.isEmpty(strEmail)) {
                    email.setError(getString(R.string.error_invalid_email));
                    focusView = email;
                    cancel = true;
                } else if (!isEmailValid(strEmail)) {
                    email.setError(getString(R.string.error_invalid_email));
                    focusView = email;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {


                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().forgotenPassword(strEmail,langOpted).enqueue(loginModelCallback);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            login_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            login_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
