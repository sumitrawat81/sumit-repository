package com.sibsefid.fragemnts.patient.myhistory;


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
import android.view.animation.Animation;

import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.SetPHistoryFamilyCallApi;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PLifeStyleModel;
import com.sibsefid.patient.adapter.PMyLifeStyleAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMyLifeStyle extends Fragment implements SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener {


    public static String ID = "0";
    private RecyclerView recyclerView;
    private View mProgressView;
    private int mColumnCount = 1;
    private ArrayList<PLifeStyleModel.PLifeStyleBean> pLifeStyleArraList = new ArrayList<>();
    private PMyLifeStyleAdapter adapter;
    Callback<PLifeStyleModel> myHisModelCallback = new Callback<PLifeStyleModel>() {
        @Override
        public void onResponse(Call<PLifeStyleModel> call, Response<PLifeStyleModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getPlifestylebean() != null) {
                        pLifeStyleArraList = (response.body().getPlifestylebean());
                        adapter.setmValues(pLifeStyleArraList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PLifeStyleModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private UserLoginDetails.LoginDetails details;


    public PMyLifeStyle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        ID = (details.getUser_seq());
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

        View view = inflater.inflate(R.layout.fragment_pmy_life_style, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter()
                    .getPLifeStyle(Integer.valueOf(details.getUser_seq()),langOpted)
                    .enqueue(myHisModelCallback);
        }

        return view;
    }

    private void initiateView(View view) {

        mProgressView = view.findViewById(R.id.progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        adapter = new PMyLifeStyleAdapter(pLifeStyleArraList, this);
        recyclerView.setAdapter(adapter);


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
    public void onAllergiesSaveCallBackSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition) {
        if (getActivity() != null && data != null && data.isSuccess()) {
            adapter.getmValues().get(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(adpterPosition);
        }
    }

    @Override
    public void onAllergiesSaveCallBackFailure(RegisterResponseModel data, int adpterPosition, int rowPosition) {
        if (getActivity() != null && data != null) {
            adapter.getmValues().get(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(adpterPosition);
        }
    }

    @Override
    public void onAllergiesSaveCallBackException(String message, int adpterPosition, int rowPosition) {

    }
}
