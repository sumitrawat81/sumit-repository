package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.DPatientListAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.MyPatientsModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 8/9/16.
 */
public class MyPatientsFragment extends BaseFragment {

    public static final String TAG = "MyPatientsFragment";
    private RecyclerView recyclerView;
    private DoctorActivity activity;
    private ProgressBar progress;
    private LinearLayout linear_l1;
    private UserLoginDetails.LoginDetails details;
    private ArrayList<MyPatientsModel.DataBean> dMyPatientsDetail;


    Callback<MyPatientsModel> detailsCallback = new Callback<MyPatientsModel>() {
        @Override
        public void onResponse(Call<MyPatientsModel> call, Response<MyPatientsModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            dMyPatientsDetail = response.body().getData();

                            updateUi();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<MyPatientsModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    private void updateUi() {
        recyclerView.setAdapter(new DPatientListAdapter(dMyPatientsDetail));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_patient_list));
        activity.forwardShowImg();
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

        View view = inflater.inflate(R.layout.fragment_mypatients_list, container, false);
        initiateView(view);
        details = MyPrefs.getLoginDetails(getActivity());
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().GetDoctorMypatients(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }
        return view;
    }

    private void initiateView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        linear_l1 = (LinearLayout) view.findViewById(R.id.linear_l1);
        //  recyclerView.setAdapter(new DPatientListAdapter(DummyContent.ITEMS));

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
            linear_l1.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            linear_l1.setVisibility(show ? View.GONE : View.VISIBLE);
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
}
