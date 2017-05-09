package com.sibsefid.fragemnts.patient.myresult;

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

import com.sibsefid.PatientActivity;
import com.sibsefid.PatientSignUpActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.PatientSummeryDetailModel;
import com.sibsefid.patient.adapter.PatientSummeryAdapter;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientSummery extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;

    private int mColumnCount = 1;
    private View mProgressView;
    private PatientActivity activity = null;
    private UserLoginDetails.LoginDetails details;
    private PatientSummeryDetailModel.DataBean pSummryDetails = new PatientSummeryDetailModel.DataBean();
    private PatientSummeryAdapter adapter;
    Callback<PatientSummeryDetailModel> detailsCallback = new Callback<PatientSummeryDetailModel>() {
        @Override
        public void onResponse(Call<PatientSummeryDetailModel> call, Response<PatientSummeryDetailModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null) {
                        if (response.body().getData() != null) {
                            pSummryDetails = response.body().getData();
                            updateUi();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PatientSummeryDetailModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private TextView textImgVisible, txtImgInvisible;

    public PatientSummery() {
        // Required empty public constructor
    }

    private void updateUi() {

        if (adapter == null) {
            adapter = new PatientSummeryAdapter(pSummryDetails);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {

            adapter.setmValues(pSummryDetails);
            adapter.notifyDataSetChanged();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;
            try {
                shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            } catch (Exception e) {

            }
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
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


        View view = inflater.inflate(R.layout.fragment_patient_summery, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().GetPatientSummery(details.getUser_seq(),langOpted).enqueue(detailsCallback);
        }

        return view;
    }

    private void initiateView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgressView = view.findViewById(R.id.progress);
        txtImgInvisible = (TextView) view.findViewById(R.id.txtImgInvisible);
        textImgVisible = (TextView) view.findViewById(R.id.textImgVisible);


        textImgVisible.setOnClickListener(this);
        txtImgInvisible.setOnClickListener(this);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        String langOpted=Utils.getuserSeletedLanagueForRequestSend(activity);
        switch (v.getId()) {

            case R.id.textImgVisible:

                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    RestAdapter.getAdapter().ShowMyTrackSummery(details.getUser_seq(), "true",langOpted).enqueue(detailsCallback);
                }

                break;

            case R.id.txtImgInvisible:
                if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    RestAdapter.getAdapter().ShowMyTrackSummery(details.getUser_seq(), "false",langOpted).enqueue(detailsCallback);
                }
                break;

            case R.id.eye_img:

                int Pos = (Integer) v.getTag();
                // Toast.makeText(getActivity(),Pos+":- Positon cliecked",Toast.LENGTH_SHORT).show();
                PatientSummeryDetailModel.DataBean pSummryDetail = new PatientSummeryDetailModel.DataBean();
                pSummryDetail = pSummryDetails;


                if (Pos == 0) {

                    if (pSummryDetail.getBloogSugar().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getBloogSugar().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getBloogSugar().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Gulose_YN",
                                pSummryDetail.getBloogSugar().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }

                } else if (Pos == 1) {

                    if (pSummryDetail.getBloodPressure().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getBloodPressure().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getBloodPressure().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Result_Pressure_YN",
                                pSummryDetail.getBloodPressure().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }

                } else if (Pos == 2) {

                    if (pSummryDetail.getTemprature().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getTemprature().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getTemprature().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Temprature_YN",
                                pSummryDetail.getTemprature().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 3) {

                    if (pSummryDetail.getOxygen().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getOxygen().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getOxygen().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Oxygen_YN",
                                pSummryDetail.getOxygen().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }

                } else if (Pos == 4) {

                    if (pSummryDetail.getWeight().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getWeight().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getWeight().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Weight_YN",
                                pSummryDetail.getWeight().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 5) {

                    if (pSummryDetail.getBMI().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getBMI().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getBMI().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Bmi_YN",
                                pSummryDetail.getBMI().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 6) {

                    if (pSummryDetail.getHeight().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getHeight().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getHeight().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Height_YN",
                                pSummryDetail.getHeight().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 7) {

                    if (pSummryDetail.getWaist().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getWaist().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getWaist().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Ent_YN",
                                pSummryDetail.getWaist().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 8) {

                    if (pSummryDetail.getECG().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getECG().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getECG().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "ECG_YN",
                                pSummryDetail.getECG().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 9) {

                    if (pSummryDetail.getENT().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getENT().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getENT().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Ent_YN",
                                pSummryDetail.getENT().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                } else if (Pos == 10) {

                    if (pSummryDetail.getSKIN().get(0).getIsShow().equalsIgnoreCase("True")) {
                        pSummryDetail.getSKIN().get(0).setIsShow("False");
                    } else
                        pSummryDetail.getSKIN().get(0).setIsShow("True");

                    if (Utils.isDeviceOnline(getActivity())) {
                        showProgress(true);
                        RestAdapter.getAdapter().ShowMyOneTrackers_URL(details.getUser_seq(), "Skin_YN",
                                pSummryDetail.getSKIN().get(0).getIsShow(),langOpted
                        ).enqueue(detailsCallback);
                    }


                }


               /* if (Utils.isDeviceOnline(getActivity())) {
                    showProgress(true);
                    RestAdapter.getAdapter().ShowAllMyTrackers(details.getUser_seq(),pSummryDetail.getBloodPressure().get(0).getIsShow(),
                            pSummryDetail.getBloogSugar().get(0).getIsShow(),
                            pSummryDetail.getOxygen().get(0).getIsShow(),
                            pSummryDetail.getWeight().get(0).getIsShow(),
                            pSummryDetail.getECG().get(0).getIsShow(),
                            pSummryDetail.getTemprature().get(0).getIsShow(),
                            pSummryDetail.getHeight().get(0).getIsShow(),
                            pSummryDetail.getENT().get(0).getIsShow(),pSummryDetail.getBMI().get(0).getIsShow(),
                            pSummryDetail.getSKIN().get(0).getIsShow(),
                            "True","True", pSummryDetail.getWaist().get(0).getIsShow(),"True").enqueue(detailsCallback);
                }*/
                break;
        }
    }
}
