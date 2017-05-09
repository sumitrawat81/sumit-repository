package com.sibsefid.fragemnts.patient;


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
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.patient.FaqModel;
import com.sibsefid.patient.adapter.FaqExpandableAdapter;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PFAQFragment extends Fragment {

    public static final String TAG = "PFAQFragment";

    private ExpandableListView recyclerView;
    private int mColumnCount = 1;
    private PatientActivity activity;
    private View mProgressView;

    private ArrayList<FaqModel.FaqList> faqLists = new ArrayList<>();
    private FaqExpandableAdapter mAdapter;
    Callback<FaqModel> blogListModelCallback = new Callback<FaqModel>() {
        @Override
        public void onResponse(Call<FaqModel> call, Response<FaqModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getFaqlist() != null && response.body().getFaqlist().size() > 0) {
                        if (response.body().getFaqlist() != null) {
                            faqLists = response.body().getFaqlist();
                            mAdapter.setFaqLists(faqLists);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<FaqModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };


    public PFAQFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.faq));
        activity.forwardShowImg();
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

        View view = inflater.inflate(R.layout.fragment_pfaq, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getFaqList(selLangToSend).enqueue(blogListModelCallback);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void initiateView(View view) {

        mProgressView = view.findViewById(R.id.progress);
        recyclerView = (ExpandableListView) view.findViewById(R.id.list);
        String[] mFaqArray = getActivity().getResources().getStringArray(R.array.faq_array);
        mAdapter = new FaqExpandableAdapter(faqLists, getActivity());
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }
}