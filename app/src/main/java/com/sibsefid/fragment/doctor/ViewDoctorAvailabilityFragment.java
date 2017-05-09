package com.sibsefid.fragment.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.ViewDoctorAvailabilityAdapter;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.DAvalilibilityModel;
import com.sibsefid.model.doctor.SetAvailabilityResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 7/9/16.
 */
public class ViewDoctorAvailabilityFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "ViewDoctorAvailabilityFragment";
    private RecyclerView mRecyclerView = null;
    private ViewDoctorAvailabilityAdapter adapter;
    private ArrayList<DAvalilibilityModel.DAvMessageBean> dAvMessageArrayList = new ArrayList<>();
    private UserLoginDetails.LoginDetails details;
    private View mProgressView;
    TextView noAvailibityText;
    Callback<DAvalilibilityModel> pMessageModelCallback = new Callback<DAvalilibilityModel>() {
        @Override
        public void onResponse(Call<DAvalilibilityModel> call, Response<DAvalilibilityModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        if (response.body().getData() != null) {
                            dAvMessageArrayList = response.body().getData();
                            if(dAvMessageArrayList.size()>0){
                                noAvailibityText.setVisibility(View.GONE);
                            }else{
                                noAvailibityText.setVisibility(View.VISIBLE);
                            }

                            adapter.setmList_ViewDoctor(dAvMessageArrayList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else{
                        noAvailibityText.setVisibility(View.VISIBLE);
                    }

                }

            }
        }

        @Override
        public void onFailure(Call<DAvalilibilityModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private DoctorActivity activity = null;
    //private Button mDeleteBtn;
    private int delete_edit_position;
    Callback<SetAvailabilityResponseModel> deleteAvaCallback = new Callback<SetAvailabilityResponseModel>() {
        @Override
        public void onResponse(Call<SetAvailabilityResponseModel> call, Response<SetAvailabilityResponseModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null) {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());

                        dAvMessageArrayList.remove(delete_edit_position);

                        adapter.notifyDataSetChanged();

                    } else {
                        Utils.showCustomToast(response.body().getMessage(), getActivity());
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<SetAvailabilityResponseModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = MyPrefs.getLoginDetails(getActivity());
        activity = (DoctorActivity) getActivity();
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

        View view = inflater.inflate(R.layout.fragment_view_doctor_availability, container, false);
        initializeComponents(view);
        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String timeZone = Utils.getTimeZoneDifference();
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getDAvailability(details.getUser_seq(), timeZone,langOpted).enqueue(pMessageModelCallback);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void initializeComponents(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_Doctor_Availability);
        //  mDeleteBtn = (Button) view.findViewById(R.id.mDeleteBtn);
        mProgressView = view.findViewById(R.id.progress);
        noAvailibityText=(TextView)view.findViewById(R.id.noAvailibityText);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ViewDoctorAvailabilityAdapter(dAvMessageArrayList);
        adapter.setOnClickListener(this);
        mRecyclerView.setAdapter(adapter);
        // mDeleteBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mDeleteBtn:
                delete_edit_position = (Integer) v.getTag();
                if (dAvMessageArrayList.get(delete_edit_position).getAppointId().isEmpty()) {
                    deleteAvailabilty(delete_edit_position);
                } else {
                    Utils.showCustomToast(getActivity().getResources().getString(R.string.not_editable), getActivity());

                }

                break;
            case R.id.mEditBtn:
                delete_edit_position = (Integer) v.getTag();
                if (dAvMessageArrayList.get(delete_edit_position).getAppointId().isEmpty()) {


                    DoctorAvailabilityFragment fragment = new DoctorAvailabilityFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "ViewDoctorAvailability");
                    bundle.putSerializable("availability_list", dAvMessageArrayList.get(delete_edit_position));
                    fragment.setArguments(bundle);
                    Utils.callFragmentForAddDoctor(activity, fragment, DoctorAvailabilityFragment.TAG);
                } else {
                    Utils.showCustomToast(getActivity().getResources().getString(R.string.not_editable), getActivity());

                }

                break;
        }


    }

    private void deleteAvailabilty(int position) {

        String ID = dAvMessageArrayList.get(position).getAvailableId();

        if (!TextUtils.isEmpty(ID)) {
            if (Utils.isDeviceOnline(getActivity())) {
                showProgress(true);
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().deleteDAvai(ID,langOpted).enqueue(deleteAvaCallback);
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
            }
        }


    }

}
