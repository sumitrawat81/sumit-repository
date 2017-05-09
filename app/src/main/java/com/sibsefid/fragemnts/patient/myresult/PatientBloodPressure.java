package com.sibsefid.fragemnts.patient.myresult;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.GetPatientBloodPressureModel;
import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientBloodPressure extends Fragment {

    //  private RecyclerView listMember;
    private WebView web_disply;
    private View mProgressView;

    private PatientActivity activity = null;
    private UserLoginDetails.LoginDetails details;
    private List<GetPatientBloodPressureModel.DataBean> pBloodPressureDetails;


    Callback<GetPatientBloodPressureModel> detailsCallback = new Callback<GetPatientBloodPressureModel>() {
        @Override
        public void onResponse(Call<GetPatientBloodPressureModel> call, Response<GetPatientBloodPressureModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            pBloodPressureDetails = response.body().getData();

                            updateUi();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<GetPatientBloodPressureModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    public PatientBloodPressure() {
        // Required empty public constructor
    }

    private void updateUi() {

        // listMember.setAdapter(new PatientBloodPressureListAdapter(pBloodPressureDetails));
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

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_blood_pressure, container, false);

        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            // RestAdapter.getAdapter().GetPatientBloodPressure(details.getUser_seq()).enqueue(detailsCallback);
        }

        return view;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;
            try {
                shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            } catch (Exception e) {

            }

            web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
            web_disply.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
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
            web_disply.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void initiateView(View view) {


        mProgressView = view.findViewById(R.id.progress);
        web_disply = (WebView) view.findViewById(R.id.web_disply);
       /* DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        String height = String.valueOf(displaymetrics.heightPixels/2);
        String width = String.valueOf(displaymetrics.widthPixels/2);
*/

        web_disply.getSettings().setJavaScriptEnabled(true);
        String url = ApiUrls.BASE_URL_WebView+"viewblood_pressure.aspx?PatientId=" + details.getUser_seq();
        web_disply.loadUrl(url);
        web_disply.setWebViewClient(new WebViewClient() {
                                        @Override
                                        public void onPageFinished(WebView view, String url) {
                                            super.onPageFinished(view, url);

                                            showProgress(false);
                                        }
                                    }
        );
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


}