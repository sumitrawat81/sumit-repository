package com.sibsefid.fragemnts.patient;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends Fragment implements View.OnClickListener {


    public static final String TAG = "ChangePassword";
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private Button mSubmitBtn;
    private View mProgress;
    private UserLoginDetails.LoginDetails loginDetails;
    Callback<BookingSummeryModelFromServer> updatePassword = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                mProgress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                        String strNewPassword = new_password.getText().toString().trim();
                        loginDetails.setUser_password(strNewPassword);
                        MyPrefs.saveLoginDetails(getActivity(), loginDetails);
                        getActivity().onBackPressed();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<BookingSummeryModelFromServer> call, Throwable t) {
            if (getActivity() != null) {
                mProgress.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDetails = MyPrefs.getLoginDetails(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initiateView(view);
        return view;
    }

    private void initiateView(View v) {

        old_password = (EditText) v.findViewById(R.id.old_password);
        new_password = (EditText) v.findViewById(R.id.new_password);
        confirm_password = (EditText) v.findViewById(R.id.confirm_password);
        mSubmitBtn = (Button) v.findViewById(R.id.mSubmitBtn);
        mProgress = v.findViewById(R.id.progress);
        mSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mSubmitBtn:
                savePassword();
                break;

        }

    }

    private void savePassword() {

        String strOldPassword = old_password.getText().toString().trim();
        String strNewPassword = new_password.getText().toString().trim();
        String strConfirmPassword = confirm_password.getText().toString().trim();

        if (TextUtils.isEmpty(strOldPassword)) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_password_please_old), getActivity());
        } else if (TextUtils.isEmpty(strNewPassword)) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_password_please_old), getActivity());
        } else if (TextUtils.isEmpty(strConfirmPassword)) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_password_please_old), getActivity());
        } else if (!strNewPassword.equalsIgnoreCase(strConfirmPassword)) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_both_pass), getActivity());
        } else if (!strOldPassword.equalsIgnoreCase(loginDetails.getUser_password())) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_both_pass_old), getActivity());
        } else if (strNewPassword.length() < 8) {
            Utils.showCustomToast(getActivity().getResources().getString(R.string.error_invalid_password), getActivity());
        } else {
            mProgress.setVisibility(View.VISIBLE);
            if (Utils.isDeviceOnline(getActivity())) {
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().changePassword(loginDetails.getUser_seq(), strNewPassword,langOpted).enqueue(updatePassword);
            }
        }
    }


}
