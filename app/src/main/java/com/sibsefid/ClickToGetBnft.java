package com.sibsefid;

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

import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.Utils;

/**
 * Created by ubuntu on 9/11/16.
 */
public class ClickToGetBnft extends Fragment {
    public static final String TAG = "PatientClinicalDashboardFragment";
    String url;
    private View mProgressView;
    private WebView web_disply;

    public ClickToGetBnft() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        View view = inflater.inflate(R.layout.click_to_get_benefit, container, false);

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

        web_disply.getSettings().setJavaScriptEnabled(true);
        //url="https://e911md.com/webView/blood_glucose.aspx?PatientId=99";
        url = ApiUrls.BASE_URL_WebView+"organizations.aspx";
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
