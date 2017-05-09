package com.sibsefid.fragemnts.patient.mymessage;


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
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PMessageModel;
import com.sibsefid.patient.adapter.PMessageMsgAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMessageInboxMsg extends Fragment {

    private RecyclerView recyclerView;
    private int mColumnCount = 1;
    private TextView txtNoValue;
    private View mProgressView;

    private ArrayList<PMessageModel.PMessageListBean> messageListBeen = new ArrayList<>();

    private PMessageMsgAdapter messageMsgAdapter;
    Callback<PMessageModel> pMessageModelCallback = new Callback<PMessageModel>() {
        @Override
        public void onResponse(Call<PMessageModel> call, Response<PMessageModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getPmessagelistbean() != null && response.body().getPmessagelistbean().size() > 0) {
                        txtNoValue.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (response.body().getPmessagelistbean() != null) {
                            messageListBeen = response.body().getPmessagelistbean();
                            messageMsgAdapter.setmValues(messageListBeen);
                            messageMsgAdapter.notifyDataSetChanged();
                        }
                    } else {
                        txtNoValue.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
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
    private UserLoginDetails.LoginDetails details;

    public PMessageInboxMsg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        View view = inflater.inflate(R.layout.fragment_pmessage_msg, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getPMessageList(details.getUser_seq(),selLangToSend).enqueue(pMessageModelCallback);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void initiateView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgressView = view.findViewById(R.id.progress);
        txtNoValue = (TextView) view.findViewById(R.id.txtNoValue);

        messageMsgAdapter = new PMessageMsgAdapter(messageListBeen);
        recyclerView.setAdapter(messageMsgAdapter);


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


}
