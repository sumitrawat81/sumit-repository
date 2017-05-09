package com.sibsefid.fragemnts.patient.mymessage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.GetPatientAppointmentModel;
import com.sibsefid.patient.adapter.PAppointmentMsgAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PAppointmentMsg extends Fragment {


    RecyclerView recyclerView;
    private int mColumnCount = 1;
    private ArrayList<GetPatientAppointmentModel.GetPAppointmentBean> appointmentArrayList = new ArrayList<>();
    private PatientActivity patientActivity;
    private UserLoginDetails.LoginDetails details;
    private PAppointmentMsgAdapter adapter;
    private View mProgress;
    private TextView txtNoValue;
    Callback<GetPatientAppointmentModel> getAppointmentCallBack = new Callback<GetPatientAppointmentModel>() {
        @Override
        public void onResponse(Call<GetPatientAppointmentModel> call, Response<GetPatientAppointmentModel> response) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getGetPAppointmentBean() != null && response.body().getGetPAppointmentBean().size() > 0) {
                        txtNoValue.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        appointmentArrayList = new ArrayList<>();
                        appointmentArrayList = response.body().getGetPAppointmentBean();
                        adapter.setmValues(appointmentArrayList);
                        adapter.notifyDataSetChanged();
                    } else {
                        txtNoValue.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<GetPatientAppointmentModel> call, Throwable t) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientActivity = (PatientActivity) getActivity();
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

        View view = inflater.inflate(R.layout.fragment_pappointment_msg, container, false);
        initiateView(view);
        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            patientActivity.showProgress(true, recyclerView, mProgress);
            String current_Time_Zone = Utils.getTimeZoneDifference();
            RestAdapter.getAdapter().getPAllAppointment(details.getUser_seq(), current_Time_Zone,selLangToSend).enqueue(getAppointmentCallBack);

        }
        return view;
    }

    private void initiateView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgress = view.findViewById(R.id.progress);
        txtNoValue = (TextView) view.findViewById(R.id.txtNoValue);
        adapter = new PAppointmentMsgAdapter(appointmentArrayList);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

}

