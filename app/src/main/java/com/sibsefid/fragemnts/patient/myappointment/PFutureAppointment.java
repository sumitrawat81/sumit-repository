package com.sibsefid.fragemnts.patient.myappointment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.SaveRequestedData;
import com.sibsefid.model.doctor.DAppointmentListModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.patient.adapter.PFutureAppointmentAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PFutureAppointment extends Fragment implements SaveRequestedData.OnSaveRequestedDataCallBackListener {


    private RecyclerView mRecyclerView;
    private View mProgress;
    private PFutureAppointmentAdapter adapter;
    private UserLoginDetails.LoginDetails details;
    private PatientActivity activity;
    private ArrayList<DAppointmentListModel.DAppointmentBean> mDAppointmentList = new ArrayList<>();
    Callback<DAppointmentListModel> getAppointment = new Callback<DAppointmentListModel>() {
        @Override
        public void onResponse(Call<DAppointmentListModel> call, Response<DAppointmentListModel> response) {
            if (getActivity() != null) {
                activity.showProgress(false, mRecyclerView, mProgress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDAppointmentBean() != null && response.body().getDAppointmentBean().size() > 0) {
                        mDAppointmentList = response.body().getDAppointmentBean();
                        adapter.setmDAppointmentList(mDAppointmentList);

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<DAppointmentListModel> call, Throwable t) {
            if (getActivity() != null) {
                activity.showProgress(false, mRecyclerView, mProgress);
            }
        }
    };
    private SaveRequestedData.OnSaveRequestedDataCallBackListener dataCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        activity = (PatientActivity) getActivity();
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

        dataCallBackListener = this;
        View view = inflater.inflate(R.layout.fragment_pfuture_appointment, container, false);
        initiateView(view);


        if (Utils.isDeviceOnline(getActivity())) {
            activity.showProgress(true, mRecyclerView, mProgress);
            String current_Time_Zone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getPAppointmentList(details.getUser_seq(), current_Time_Zone,langOpted).enqueue(getAppointment);
        }

        return view;
    }

    private void initiateView(View view) {


        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgress = view.findViewById(R.id.progress);
        adapter = new PFutureAppointmentAdapter(getActivity(), mDAppointmentList, dataCallBackListener);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(new PPastAdapter(DummyContent.ITEMS));


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onnSaveRequestedDataSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition) {

        if (getActivity() != null) {
            if (data != null && data.isSuccess()) {
                adapter.notifyItemChanged(adpterPosition);
                adapter.removeItem(adpterPosition);
                Utils.showCustomToast(getActivity().getResources().getString(R.string.your_request_successfully_saved), getActivity());
            } else if (data != null && data.getMessage() != null && data.getMessage().size() > 0) {
                adapter.getBeanItem(adpterPosition).setLoading(false);
                Utils.showCustomToast(data.getMessage().get(0).getMsg(), getActivity());
            }
        }

    }

    @Override
    public void onnSaveRequestedDataFailure(RegisterResponseModel data, int adpterPosition, int rowPosition) {
        if (getActivity() != null) {
            adapter.getBeanItem(adpterPosition).setLoading(false);
            adapter.notifyItemChanged(rowPosition);

        }
    }

    @Override
    public void onnSaveRequestedDatakException(String message, int adpterPosition, int rowPosition) {

    }
}
