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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.AccountDetailModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/9/16.
 */
public class AccountDetailFragment extends BaseFragment {

    public static final String TAG = "AccountDetailFragment";
    private DoctorActivity activity;
    private AccountDetailModel.DAccountDetails dAccountDetails = null;

    private EditText mDBankName;
    private EditText mDAccountName;
    private EditText mDBSD;
    private EditText mDAccountNumber;
    private EditText mDPaypalId;

    private View mDetailsForm;
    private View mProgressView;
    Callback<AccountDetailModel> detailsCallback = new Callback<AccountDetailModel>() {
        @Override
        public void onResponse(Call<AccountDetailModel> call, Response<AccountDetailModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDaccountdetails() != null && response.body().getDaccountdetails().size() > 0) {
                        if (response.body().getDaccountdetails() != null) {
                            dAccountDetails = response.body().getDaccountdetails().get(0);
                            updateui();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<AccountDetailModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    Callback<AccountDetailModel> saveAccountDetails = new Callback<AccountDetailModel>() {
        @Override
        public void onResponse(Call<AccountDetailModel> call, Response<AccountDetailModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDaccountdetails() != null && response.body().getDaccountdetails().size() > 0) {
                        if (response.body().getDaccountdetails() != null) {
                            dAccountDetails = response.body().getDaccountdetails().get(0);
                            updateui();
                        }
                    }
                    if(response.body().getMessage().get(0).getMsg()!=null) {
                        Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<AccountDetailModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private Button mDSubmit;
    private UserLoginDetails.LoginDetails details;
    View.OnClickListener listenerMSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            updateAccountDetails();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_account));
        activity.forwardShowImg();
        details = MyPrefs.getLoginDetails(getActivity());
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
        View view = inflater.inflate(R.layout.fragment_account_details, container, false);
        initComponents(view);
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getDAccountDetails(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }
        return view;
    }

    public void initComponents(View view) {
        mDetailsForm = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);
        mDBankName = (EditText) view.findViewById(R.id.mDBankName);
        mDAccountName = (EditText) view.findViewById(R.id.mDAccountName);
        mDBSD = (EditText) view.findViewById(R.id.mDBSD);
        mDAccountNumber = (EditText) view.findViewById(R.id.mDAccountNumber);
        mDPaypalId = (EditText) view.findViewById(R.id.mDPaypalId);
        mDSubmit = (Button) view.findViewById(R.id.mDSubmit);
        mDSubmit.setOnClickListener(listenerMSubmit);
    }

    private void updateui() {
        mDBankName.setText(dAccountDetails.getBankName());
        mDAccountName.setText(dAccountDetails.getAccountType());
        mDBSD.setText(dAccountDetails.getSrNo());
        mDAccountNumber.setText(dAccountDetails.getAcNumber());
        mDPaypalId.setText(dAccountDetails.getPaypalEmail());
    }

    private void updateAccountDetails() {
        String strDoctorId = details.getUser_seq().toString().trim();

        String strBankName = mDBankName.getText().toString().trim();
        String strAccountType = mDAccountName.getText().toString().trim();
        String strDBSD = mDBSD.getText().toString().trim();
        String strAccountNumber = mDAccountNumber.getText().toString().trim();
        String strPayPalEmail = mDPaypalId.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(strBankName) || !Utils.validateLName(strBankName)) {
            mDBankName.setError(getString(R.string.error_invalid_first_name));
            focusView = mDBankName;
            cancel = true;
        }  else if (TextUtils.isEmpty(strAccountType) || !Utils.validateLName(strAccountType)) {
            mDAccountName.setError(getString(R.string.error_invalid_first_name));
            focusView = mDAccountName;
            cancel = true;
        } else if (TextUtils.isEmpty(strAccountNumber) || !isPhoneNumber(strAccountNumber)) {
            mDAccountNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mDAccountNumber;
            cancel = true;
        }else if (!TextUtils.isEmpty(strPayPalEmail) && !Utils.isEmailValid(strPayPalEmail)) {
            mDPaypalId.setError(getString(R.string.error_invalid_email));
            focusView = mDPaypalId;
            cancel = true;
        } if (cancel) {
            focusView.requestFocus();
        } else {
            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().updateAccountDetails(strDoctorId, strBankName,
                        strAccountType, strDBSD, strAccountNumber, strPayPalEmail,langOpted).enqueue(saveAccountDetails);
            }
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }
    private boolean isPhoneNumber(String name) {
        return name.length() >=10;
    }
}
