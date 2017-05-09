package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.MyPreviousTicketsAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.DoctorPreviousTicketsModel;
import com.sibsefid.model.doctor.ProfileUpdateModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 5/10/16.
 */
public class MyPreviousTickets extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "MyPersonalNoteFragment";

    private RecyclerView mRecyclerView_MyPersonalNote = null;
    private UserLoginDetails.LoginDetails details;
    private ProgressBar progress;
    private TextView noDataText;
    private DoctorActivity activity;
    private MyPreviousTicketsAdapter myPersonalNoteAdapter;
    private int deletePos;
    private int posClicked;
    private ArrayList<DoctorPreviousTicketsModel.DataBean> personalTicketDetails;

    Callback<DoctorPreviousTicketsModel> detailsCallback = new Callback<DoctorPreviousTicketsModel>() {
        @Override
        public void onResponse(Call<DoctorPreviousTicketsModel> call, Response<DoctorPreviousTicketsModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    noDataText.setVisibility(View.GONE);
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            personalTicketDetails = response.body().getData();

                            updateUi();
                        }
                    }
                    else{
                        noDataText.setVisibility(View.VISIBLE);
                    }
                }else{
                    noDataText.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onFailure(Call<DoctorPreviousTicketsModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    Callback<ProfileUpdateModel> deleteTicketListner = new Callback<ProfileUpdateModel>() {
        @Override
        public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {

            if (getActivity() != null) {
                if (response.isSuccessful()) {

                    showProgress(false);
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                            personalTicketDetails.remove(deletePos);
                            myPersonalNoteAdapter.setPos(-1);
                            myPersonalNoteAdapter.notifyDataSetChanged();


                        } else if ((!response.body().isSuccess()) && response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            Toast.makeText(getActivity(), response.body().getMessage().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ProfileUpdateModel> response, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
                Toast.makeText(getActivity(), getResources().getString(R.string.failed_updation), Toast.LENGTH_SHORT).show();
            }
            t.printStackTrace();

        }
    };


    private void updateUi() {
        myPersonalNoteAdapter = new MyPreviousTicketsAdapter(getActivity(), personalTicketDetails);
        myPersonalNoteAdapter.setClickListener(this);
        mRecyclerView_MyPersonalNote.setAdapter(myPersonalNoteAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_previous_ticket));
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

        View view = inflater.inflate(R.layout.my_previous_ticket, container, false);
        initComponents(view);
        details = MyPrefs.getLoginDetails(getActivity());
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().GetDoctorProblems(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }
        return view;
    }

    public void initComponents(View view) {

        progress = (ProgressBar) view.findViewById(R.id.progress);
        noDataText=(TextView)view.findViewById(R.id.noDataText);
        mRecyclerView_MyPersonalNote = (RecyclerView) view.findViewById(R.id.recycle_mytickets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView_MyPersonalNote.setLayoutManager(layoutManager);

    }


    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


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

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mRelClick:

                posClicked = (Integer) v.getTag();
                myPersonalNoteAdapter.setPos(posClicked);
                myPersonalNoteAdapter.notifyDataSetChanged();
                break;

            case R.id.deleteTicket:
                deletePos = (Integer) v.getTag();
                String ticketId = personalTicketDetails.get(deletePos).getTicketNo();
                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    RestAdapter.getAdapter().Delete_Doctor_Ticket(ticketId,langOpted).enqueue(deleteTicketListner);

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}