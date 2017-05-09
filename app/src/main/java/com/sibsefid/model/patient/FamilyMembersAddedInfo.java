package com.sibsefid.model.patient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.patient.adapter.PFamilyMemberAddedAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 22/9/16.
 */
public class FamilyMembersAddedInfo extends Fragment {

    public static final String TAG = "FamilyMembersAddedInfo";
    private PatientActivity activity = null;
    private UserLoginDetails.LoginDetails details;
    private List<PatientFamiliyMembersModel.DataBean> pFamilyMemberDetails;
    private RecyclerView recyclerView;
    private TextView noDataFoundText;
    // private View mDetailsForm;
    private View mProgressView;

    Callback<PatientFamiliyMembersModel> detailsCallback = new Callback<PatientFamiliyMembersModel>() {
        @Override
        public void onResponse(Call<PatientFamiliyMembersModel> call, Response<PatientFamiliyMembersModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    noDataFoundText.setVisibility(View.GONE);
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            pFamilyMemberDetails = response.body().getData();

                            updateUi();
                        }

                    }else{
                        noDataFoundText.setVisibility(View.VISIBLE);
                }
                }
            }
        }

        @Override
        public void onFailure(Call<PatientFamiliyMembersModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };


    public FamilyMembersAddedInfo() {

    }

    private void updateUi() {

            recyclerView.setAdapter(new PFamilyMemberAddedAdapter(pFamilyMemberDetails));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        details = MyPrefs.getLoginDetails(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.family_member_added_info, container, false);

        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().Get_Patient_FamilyMembers(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }


        return view;
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
    }

    private void initiateView(View view) {

        // listMember = view.findViewById(R.id.details_form);
        mProgressView = view.findViewById(R.id.progress);

        recyclerView = (RecyclerView) view.findViewById(R.id.listMember);
        noDataFoundText=(TextView)view.findViewById(R.id.noDataFoundText);


    }
}
