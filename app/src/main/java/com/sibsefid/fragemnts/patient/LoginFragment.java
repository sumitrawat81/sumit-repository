package com.sibsefid.fragemnts.patient;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.ClickToGetBnft;
import com.sibsefid.DoctorActivity;
import com.sibsefid.DoctorSignUpActivity;
import com.sibsefid.LoginActivity;
import com.sibsefid.PatientActivity;
import com.sibsefid.PatientSignUpActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.services.AppService;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";

    private static final int REQUEST_READ_CONTACTS = 0;

    private EditText mEmailView;

    private EditText mPasswordView;

    private TextView clickToBenfit;

    private View mProgressView;

    private View mLoginFormView;

    private CheckBox mRememberBtn;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    Callback<UserLoginDetails> loginModelCallback = new Callback<UserLoginDetails>() {
        @Override
        public void onResponse(Call<UserLoginDetails> call, Response<UserLoginDetails> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    try {
                        //  Log.e(TAG, "onResponse: " + response.raw().body().string()+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getLogindetails() != null && response.body().getLogindetails().size() > 0) {
                            int LOGINTYPE = MyPrefs.getUser(getActivity());
                            String email = mEmailView.getText().toString();
                            String password = mPasswordView.getText().toString();
                            if (LOGINTYPE == 1) {

                                MyPrefs.saveUserMsgCountl(getActivity(), "0");
                                MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
                                response.body().getLogindetails().get(0).setType(1);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                                if (mRememberBtn.isChecked())
                                    saveLoginDetailsInMyPre(email, password);
                                else saveLoginDetailsInMyPre("", "");

                                if (AppService.sInstance != null) {
                                    AppService.loginWithInIt();
                                } else {
                                    Utils.isMyServiceRunning(getActivity(), AppService.class);
                                }
                                sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = sharedPref.edit();
                                editor.putString("appointmentIdTimeOver", null);
                                editor.putLong("appointmentTimeOverTime", 0);
                                editor.commit();
                                startActivity(new Intent(getActivity(), PatientActivity.class));

                            } else if (LOGINTYPE == 2) {

                                response.body().getLogindetails().get(0).setType(2);
                                MyPrefs.saveLoginDetails(getActivity(), response.body().getLogindetails().get(0));
                                MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
                                if (mRememberBtn.isChecked())
                                    saveLoginDetailsInMyPre(email, password);
                                else saveLoginDetailsInMyPre("", "");

                                if (AppService.sInstance != null) {
                                    AppService.loginWithInIt();
                                } else {
                                    Utils.isMyServiceRunning(getActivity(), AppService.class);
                                }
                                sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = sharedPref.edit();
                                editor.putLong("appointmentTimeOverTime", 0);
                                editor.commit();
                                startActivity(new Intent(getActivity(), DoctorActivity.class));
                            }

                            getActivity().finish();

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
    private LoginActivity loginActivity;

    public LoginFragment() {

    }

    public void saveLoginDetailsInMyPre(String email, String password) {
        MyPrefs.saveUserEmail(getActivity(), email);
        MyPrefs.saveUserPassword(getActivity(), password);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_login, container, false);
        initiateView(view);
        return view;
    }

    private void initiateView(View view) {

        mEmailView = (EditText) view.findViewById(R.id.email);
        mPasswordView = (EditText) view.findViewById(R.id.password);
        mRememberBtn = (CheckBox) view.findViewById(R.id.mRememberBtn);
        clickToBenfit = (TextView) view.findViewById(R.id.clickToBenfit);

        String strEmail = MyPrefs.getUserEmail(getActivity());
        String strPassword = MyPrefs.getUserPassword(getActivity());
        if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)) {
            mEmailView.setText(strEmail);
            mPasswordView.setText(strPassword);
            mRememberBtn.setChecked(true);
            mEmailView.setSelection(strEmail.length());
        }

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        TextView mForgotPasswordBtn = (TextView) view.findViewById(R.id.mForgotPasswordBtn);
        mForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.callFragmentForAdd(loginActivity, new ForgotPasswordFragment(), ForgotPasswordFragment.TAG);
            }
        });

        TextView mSignUpBtn = (TextView) view.findViewById(R.id.mSignUpBtn);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int LOGINTYPE = MyPrefs.getUser(getActivity());
                if (LOGINTYPE == 1) {
                    startActivity(new Intent(getActivity(), PatientSignUpActivity.class));
                } else if (LOGINTYPE == 2) {
                    startActivity(new Intent(getActivity(), DoctorSignUpActivity.class));
                }

            }
        });

        clickToBenfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.callFragmentForAdd(loginActivity, new ClickToGetBnft(), ClickToGetBnft.TAG);
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        mEmailView.setFocusable(true);
        mEmailView.  requestFocus();

    }


    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Utils.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                int LOGINTYPE = MyPrefs.getUser(getActivity());
                String type = "";
                if (LOGINTYPE == 1) {
                    type = "Patient";
                } else if (LOGINTYPE == 2) {
                    type = "Doctor";
                }
                String deviceToken = MyPrefs.getUserDeviceToken(getActivity());
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().userLogin(email, type, password, "Android", deviceToken,langOpted).enqueue(loginModelCallback);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


}
