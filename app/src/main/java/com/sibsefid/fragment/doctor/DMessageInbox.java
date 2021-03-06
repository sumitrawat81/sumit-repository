package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.doctor.adapter.DMessageFragmentAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PMessageModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/9/16.
 */
public class DMessageInbox extends BaseFragment {

    //private RecyclerView mRecyclerView = null;
    // private List<String> mList_Message = null;

    private RecyclerView mRecyclerView;
    private int mColumnCount = 1;

    private View mProgressView;
    private UserLoginDetails.LoginDetails details;
    private DMessageFragmentAdapter adapter;

    private ArrayList<PMessageModel.PMessageListBean> messageListBeen = new ArrayList<>();
    Callback<PMessageModel> pMessageModelCallback = new Callback<PMessageModel>() {
        @Override
        public void onResponse(Call<PMessageModel> call, Response<PMessageModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getPmessagelistbean() != null && response.body().getPmessagelistbean().size() > 0) {
                        if (response.body().getPmessagelistbean() != null) {
                            messageListBeen = response.body().getPmessagelistbean();
                            adapter.setmValues(messageListBeen);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PMessageModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initComponents(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getPMessageList(details.getUser_seq(),selLangToSend).enqueue(pMessageModelCallback);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void initComponents(View view) {
        mProgressView = view.findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_message);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DMessageFragmentAdapter(messageListBeen);
        mRecyclerView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }
}
