package com.sibsefid.fragemnts.patient.myhistory;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.SetPHistoryFamilyCallApi;
import com.sibsefid.model.QickBloxModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PFamilyHistoryModel;
import com.sibsefid.model.patient.RelationshipType;
import com.sibsefid.patient.adapter.PMyFamilyHistoryAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMyFamilyHistory extends Fragment implements View.OnClickListener, SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener {


    private RecyclerView recyclerView;
    private int mColumnCount = 1;
    private View mProgressView;
    private UserLoginDetails.LoginDetails details;
    private PMyFamilyHistoryAdapter adapter;
    private ProgressBar progress_add;
    private Button mAddBrn;
    Callback<RegisterResponseModel> addResponse = new Callback<RegisterResponseModel>() {
        @Override
        public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
            if (getActivity() != null) {
                progress_add.setVisibility(View.GONE);
                mAddBrn.setVisibility(View.VISIBLE);
                mRelationEdt.setText("");
                if (response.isSuccessful()) {

                    showProgress(true);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter()
                            .getPFamilyHistory(Integer.valueOf(details.getUser_seq()),langOpted)
                            .enqueue(myHisModelCallback);
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                        Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
            if (getActivity() != null) {
                progress_add.setVisibility(View.GONE);
                mAddBrn.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        }
    };
    private EditText mRelationEdt;
    private ArrayList<PFamilyHistoryModel.PFamilyHistoryBean> pFamilyHistoryArrayList = new ArrayList<>();
    Callback<PFamilyHistoryModel> myHisModelCallback = new Callback<PFamilyHistoryModel>() {
        @Override
        public void onResponse(Call<PFamilyHistoryModel> call, Response<PFamilyHistoryModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getPFamilyHistoryBean() != null) {
                        pFamilyHistoryArrayList = (response.body().getPFamilyHistoryBean());
                        adapter.setmValues(pFamilyHistoryArrayList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PFamilyHistoryModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private ArrayList<RelationshipType.DataBean> relationArrayLis = new ArrayList<>();
    Callback<RelationshipType> relationPModelCallback = new Callback<RelationshipType>() {
        @Override
        public void onResponse(Call<RelationshipType> call, Response<RelationshipType> response) {
            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            relationArrayLis = response.body().getData();
                            adapter.setRelationarrayList(relationArrayLis);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<RelationshipType> call, Throwable t) {
            if (getActivity() != null) {
                t.printStackTrace();
            }
        }
    };

    public PMyFamilyHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        //EventBus.getDefault().register(this);
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

        View view = inflater.inflate(R.layout.fragment_pmy_family_history, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            if (Utils.isDeviceOnline(getActivity())) {
                RestAdapter.getAdapter().getPRelationList(selLangToSend).enqueue(relationPModelCallback);
            }

            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter()
                    .getPFamilyHistory(Integer.valueOf(details.getUser_seq()),langOpted)
                    .enqueue(myHisModelCallback);
        }


        return view;
    }

    private void initiateView(View view) {

        mProgressView = view.findViewById(R.id.progress);
        progress_add = (ProgressBar) view.findViewById(R.id.progress_add);
        mAddBrn = (Button) view.findViewById(R.id.mAddBrn);
        mRelationEdt = (EditText) view.findViewById(R.id.mRelationEdt);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        adapter = new PMyFamilyHistoryAdapter(pFamilyHistoryArrayList, getActivity(), this);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);

        mAddBrn.setOnClickListener(this);

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
    public void onClick(View v) {
        if (mAddBrn == v) {
            if (mRelationEdt.getText().toString().length() > 0) {
                if (Utils.isDeviceOnline(getActivity())) {
                    progress_add.setVisibility(View.VISIBLE);
                    mAddBrn.setVisibility(View.INVISIBLE);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().addPatientHistoryOnTab(details.getUser_seq(), -1, false, mRelationEdt.getText().toString().trim(),langOpted).enqueue(addResponse);
                }
            }
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

    @Subscribe
    public void onEvent(QickBloxModel event) {
        Toast.makeText(getActivity(), event.getId(), Toast.LENGTH_SHORT).show();
        Log.d("event-----------", event.getId());

    }
}