package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.ProfileUpdateModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 6/9/16.
 */
public class ReportProblemFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "ReportProblemFragment";

    private Button btnAddProblem;
    private Button btnPreviewTicket;
    private EditText problemSummery;
    private DoctorActivity activity;
    private ProgressBar progress;
    private LinearLayout linear_l1;
    Callback<ProfileUpdateModel> updateProfileListner = new Callback<ProfileUpdateModel>() {
        @Override
        public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            problemSummery.setText("");
                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ProfileUpdateModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };
    private UserLoginDetails.LoginDetails details;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.report_a_problem));
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

        View view = inflater.inflate(R.layout.fragment_report_a_problem, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {

        btnAddProblem = (Button) view.findViewById(R.id.btnAddProblem);
        btnPreviewTicket = (Button) view.findViewById(R.id.btnPreviewTicket);
        problemSummery = (EditText) view.findViewById(R.id.problemSummery);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        linear_l1 = (LinearLayout) view.findViewById(R.id.linear_l1);
        details = MyPrefs.getLoginDetails(getActivity());
        btnAddProblem.setOnClickListener(this);
        btnPreviewTicket.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAddProblem:
                String dProblem = problemSummery.getText().toString().trim();
                if (dProblem.length() > 0) {
                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().AddDoctorProblems(details.getUser_seq(), dProblem,langOpted).enqueue(updateProfileListner);

                    }
                } else {
                    Toast.makeText(getActivity(), "Enter the problem", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnPreviewTicket:
                Utils.callFragmentForAddDoctor((DoctorActivity) getActivity(), new MyPreviousTickets(), ReportProblemFragment.TAG);

                break;
        }
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
}
