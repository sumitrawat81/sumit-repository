package com.sibsefid.fragemnts.patient.medecinereminder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.SetMedicineReminderModel;
import com.sibsefid.patient.adapter.ReminderListAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 25/11/16.
 */
public class ReminderListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    int pos;
    private RecyclerView reminder_list;
PatientActivity patientActivity;


    private UserLoginDetails.LoginDetails details;
    private TextView noDataText;
    private ProgressBar progress;
    private ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> myReminderListArray;
    private ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> myReminderListArrayClone;
    private ReminderListAdapter adapter;
    Callback<SetMedicineReminderModel> updateProfileListner = new Callback<SetMedicineReminderModel>() {
        @Override
        public void onResponse(Call<SetMedicineReminderModel> call, Response<SetMedicineReminderModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    // noDataText.setVisibility(View.GONE);
                    showProgress(false);
                    // reminder_list.setVisibility(View.VISIBLE);
                    if (response.body().isSuccess() && response.body().getData().getReminderList() != null && response.body().getData().getReminderList().size() > 0) {
                        if (response.body().getData().getReminderList() != null) {

                            myReminderListArray = new ArrayList<>();

                            myReminderListArray = (ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean>) response.body().getData().getReminderList();

                            myReminderListArrayClone= (ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean>) myReminderListArray.clone();
                            updateUi();
                        }
                    } else {
                        reminder_list.setVisibility(View.GONE);
                        noDataText.setVisibility(View.VISIBLE);

                    }
                }

            }
        }

        @Override
        public void onFailure(Call<SetMedicineReminderModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };

    Callback<SetMedicineReminderModel> updateReminderList = new Callback<SetMedicineReminderModel>() {
        @Override
        public void onResponse(Call<SetMedicineReminderModel> call, Response<SetMedicineReminderModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {
                    // noDataText.setVisibility(View.GONE);
                    showProgress(false);
                    // reminder_list.setVisibility(View.VISIBLE);
                    if (response.body().isSuccess()) {
                        myReminderListArray.get(pos).setIsTaken("true");
                        adapter.notifyDataSetChanged();
                    } else {


                    }
                }

            }
        }

        @Override
        public void onFailure(Call<SetMedicineReminderModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };

    private void updateUi() {
        reminder_list.setVisibility(View.VISIBLE);
        adapter = new ReminderListAdapter(getActivity(),myReminderListArray,this);
        adapter.setOnClickListener(this);
        reminder_list.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            reminder_list.setVisibility(show ? View.GONE : View.VISIBLE);
            reminder_list.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    reminder_list.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);

                }
            });
        } else {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            reminder_list.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        patientActivity=(PatientActivity)getActivity();
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

        View view = inflater.inflate(R.layout.reminder_list, container, false);

        init(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getReminderList(
                    details.getUser_seq(),langOpted).enqueue(updateProfileListner);


        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }
        return view;

    }

    private void init(View view) {


        reminder_list = (RecyclerView) view.findViewById(R.id.reminder_list);
        reminder_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        noDataText = (TextView) view.findViewById(R.id.noDataText);
        progress = (ProgressBar) view.findViewById(R.id.progress);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mDFilter:

              String medicineId=  adapter. myFilterViewHolder.mMedicineChooser.getTag().toString();
                if(medicineId.equalsIgnoreCase(getResources().getString(R.string.select_medicine))){
                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        RestAdapter.getAdapter().getReminderList(
                                details.getUser_seq(),langOpted).enqueue(updateProfileListner);


                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    myReminderListArray.clear();
                    for (int i = 0; i < myReminderListArrayClone.size(); i++) {
                        if (medicineId.equalsIgnoreCase(myReminderListArrayClone.get(i).getMedicineName())) {
                            myReminderListArray.add(myReminderListArrayClone.get(i));

                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                break;

            case R.id.takenBtn:
                pos = (int) v.getTag();


                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().TakenMedicinePatientList(
                            myReminderListArray.get(pos).getReminderId(),langOpted).enqueue(updateReminderList);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            if(position==-1){
                adapter. myFilterViewHolder.  mMedicineChooser.setTag(getResources().getString(R.string.select_medicine));
            }else
                adapter. myFilterViewHolder.  mMedicineChooser.setTag(SetMedicineReminder.mList_Title.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
