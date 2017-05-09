package com.sibsefid.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.AppointmentAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.SaveRequestedData;
import com.sibsefid.model.doctor.DAppointmentListModel;
import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/9/16.
 */
public class AppointmentFragment extends BaseFragment implements SaveRequestedData.OnSaveRequestedDataCallBackListener {


    private Spinner mSpinner_Status = null;
    private List<String> mList_Status = null;
    private ArrayList<DAppointmentListModel.DAppointmentBean> mDAppointmentList = null;
    private DoctorActivity mDoctorActivity = null;
    private RecyclerView mRecyclerView = null;
    private AppointmentAdapter adapter = null;
    private View mProgress;
    Callback<DAppointmentListModel> getAppointment = new Callback<DAppointmentListModel>() {
        @Override
        public void onResponse(Call<DAppointmentListModel> call, Response<DAppointmentListModel> response) {
            if (getActivity() != null) {
                mDoctorActivity.showProgress(false, mRecyclerView, mProgress);
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
                mDoctorActivity.showProgress(false, mRecyclerView, mProgress);
            }
        }
    };
    private UserLoginDetails.LoginDetails loginDetails;
    private SaveRequestedData.OnSaveRequestedDataCallBackListener dataCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoctorActivity = (DoctorActivity) getActivity();
        loginDetails = MyPrefs.getLoginDetails(getActivity());
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
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        dataCallBackListener = this;
        init(view);
        if (Utils.isDeviceOnline(getActivity())) {
            mDoctorActivity.showProgress(true, mRecyclerView, mProgress);
            String current_Time_Zone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getDAppointmentList(loginDetails.getUser_seq(), current_Time_Zone,langOpted).enqueue(getAppointment);
        }
        return view;
    }

    public void init(View view) {

        mDoctorActivity = (DoctorActivity) getActivity();
        mSpinner_Status = (Spinner) view.findViewById(R.id.spinner_Status);
        mList_Status = new ArrayList<>();
        mList_Status.add("All");
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(mDoctorActivity, android.R.layout.simple_spinner_dropdown_item, mList_Status);
        mSpinner_Status.setAdapter(mArrayAdapter);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_appointment);
        mProgress = view.findViewById(R.id.progress);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mDoctorActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AppointmentAdapter(mDoctorActivity, mDAppointmentList, dataCallBackListener);
        mRecyclerView.setAdapter(adapter);


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
