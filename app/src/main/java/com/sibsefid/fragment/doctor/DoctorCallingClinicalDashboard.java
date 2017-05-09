package com.sibsefid.fragment.doctor;

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
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

/**
 * Created by ubuntu on 7/11/16.
 */
public class DoctorCallingClinicalDashboard extends Fragment {
    public static final String TAG = "DoctorClinicalDashboardFragment";
    String url;
    private View mProgressView;
    private WebView web_disply;
    //  private ImageView mBackBtn;
    // private DoctorActivity activity = null;
    private UserLoginDetails.LoginDetails details;

    public DoctorCallingClinicalDashboard() {
        // Required empty public constructor
    }


    public static DoctorCallingClinicalDashboard newInstance(String param1) {
        DoctorCallingClinicalDashboard fragment = new DoctorCallingClinicalDashboard();
        Bundle args = new Bundle();
        args.putString("CLINICAL_URL", param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity = (DoctorActivity) getActivity();
        details = MyPrefs.getLoginDetails(getActivity());
        if (getArguments() != null) {
            url = getArguments().getString("CLINICAL_URL");

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;
            try {
                shortAnimTime = getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime);
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
        View view = inflater.inflate(R.layout.dr_clinical_calling_dashboard, container, false);

        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);

        } else {
            Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void initiateView(View view) {

        mProgressView = view.findViewById(R.id.clinical_progress);
        web_disply = (WebView) view.findViewById(R.id.web_disply);
        //mBackBtn=(ImageView)view.findViewById(R.id.mBackBtn);

        web_disply.getSettings().setJavaScriptEnabled(true);
        // String url="https://e911md.com/webView/blood_glucose.aspx?PatientId="+details.getUser_seq();
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
