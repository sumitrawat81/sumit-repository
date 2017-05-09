package com.sibsefid.fragemnts.patient.bookappointment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.e911md.services.RetrofitUtils;
import com.sibsefid.fragemnts.patient.PScheduleFragment;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookAppointmentPostModel;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.model.patient.PPharmacyModel;
import com.sibsefid.util.KeyValues;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PChoosePharmacy extends Fragment implements View.OnClickListener {

    public static final String TAG = "PChoosePharmacy";
    private static PChoosePharmacy pharmacy;
    private TextView mAddress;
    private Button mChangePharmacy;
    private Button mBackBtn;
    private Button mContinueBtn;
    private View progress;
    private View mScrollView;
    private PScheduleFragment scheduleFragment;
    private String filePath;
    private PatientActivity mParentActivity;
    private UserLoginDetails.LoginDetails loginDetails;
    private PPharmacyModel.AddressBean pPharmacyModel;
    Callback<PPharmacyModel> patientPharmacyCallback = new Callback<PPharmacyModel>() {
        @Override
        public void onResponse(Call<PPharmacyModel> call, Response<PPharmacyModel> response) {
            if (getActivity() != null) {
                mParentActivity.showProgress(false, mScrollView, progress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getAddressbean() != null) {
                        pPharmacyModel = response.body().getAddressbean().get(0);
                        updateUi();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PPharmacyModel> call, Throwable t) {
            if (getActivity() != null) {
                mParentActivity.showProgress(false, mScrollView, progress);
            }
        }
    };
    private BookingSummeryModelFromServer.DataBean.BookingSummaryBean bookingSummaryDetails;
    private MaterialDialog dialog;
    Callback<BookingSummeryModelFromServer> bookAppointmentCallback = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                mParentActivity.showProgress(false, mScrollView, progress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().getBookingSummary() != null) {
                        bookingSummaryDetails = response.body().getData().getBookingSummary().get(0);
                        BookAppointmentPostModel postModel = scheduleFragment.getPostModel();
                        bookingSummaryDetails.setTransactionNumber(postModel.getChat_type());
                        PPayment.getInstance().setDectorFee();
                        PMainBookAppointment.getInstance().getmPager().setCurrentItem(2);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<BookingSummeryModelFromServer> call, Throwable t) {
            if (getActivity() != null) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                mParentActivity.showProgress(false, mScrollView, progress);
            }
        }
    };

    public static PChoosePharmacy getInstance() {
        return pharmacy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleFragment = PScheduleFragment.getInstance();
        mParentActivity = (PatientActivity) getActivity();
        loginDetails = MyPrefs.getLoginDetails(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = inflater.inflate(R.layout.fragment_pchoose_pharmacy, container, false);
        pharmacy = this;
        initiateView(view);
        if (Utils.isDeviceOnline(getActivity())) {
            String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().getPPharmacy(loginDetails.getUser_seq(),langOpted).enqueue(patientPharmacyCallback);
        }
        return view;
    }

    private void initiateView(View view) {
        mBackBtn = (Button) view.findViewById(R.id.mBackBtn);
        mContinueBtn = (Button) view.findViewById(R.id.mContinueBtn);
        mChangePharmacy = (Button) view.findViewById(R.id.mChangePharmacy);
        mAddress = (TextView) view.findViewById(R.id.mAddress);
        progress = view.findViewById(R.id.progress);
        mScrollView = view.findViewById(R.id.mScrollView);
        mBackBtn.setOnClickListener(this);
        mContinueBtn.setOnClickListener(this);
        mChangePharmacy.setOnClickListener(this);
    }

    public void updateUi() {
        if (pPharmacyModel != null) {
            mAddress.setText(pPharmacyModel.getPharmacy_name() + "\n" +
                    pPharmacyModel.getAddress1_pharmacy() + " " +
                    pPharmacyModel.getCity_pharmacy() + "\n" +
                    pPharmacyModel.getStateNamePharmacy() + ", " +
                    pPharmacyModel.getCountrynamePharmacy() + "\n" +
                    "Zip code: " + pPharmacyModel.getPostcode_pharmacy() + "\n" +
                    "Ph: " + pPharmacyModel.getPhone_pharmacy());


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBackBtn:
                PMainBookAppointment.getInstance().getmPager().setCurrentItem(0);
                break;
            case R.id.mChangePharmacy:
                Utils.callFragmentForAddPatient((PatientActivity) getActivity(), new PPharmacyChange(), PPharmacyChange.TAG);
                break;
            case R.id.mContinueBtn:


                if (Utils.isDeviceOnline(getActivity())) {
                    String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                    BookAppointmentPostModel postModel = scheduleFragment.getPostModel();
                    String[] dateold = postModel.getDate().split("/");
                    String dateNew = postModel.getDate() + " " + postModel.getHours() + ":" + postModel.getMinutes() + ":00" + " " + postModel.getTime();
                    String current_Time_Zone = Utils.getTimeZoneDifference();
                    paymentDialog();

                    HashMap<String, String> requestValuePairsMap = new HashMap<>();
                    requestValuePairsMap.put("patientsId", loginDetails.getUser_seq());

                    requestValuePairsMap.put(KeyValues.ARG_DoctorId, postModel.getDoctor_id());
                    requestValuePairsMap.put(KeyValues.ARG_Reason, postModel.getReason());
                    requestValuePairsMap.put(KeyValues.ARG_Comment, postModel.getComment());
                    requestValuePairsMap.put(KeyValues.ARG_AppointMode, postModel.getChat_type());
                    requestValuePairsMap.put(KeyValues.ARG_BookingDateTime, dateNew);
                    requestValuePairsMap.put(KeyValues.ARG_timeZone, current_Time_Zone.trim());
                    requestValuePairsMap.put(KeyValues.ARG_P_LANG, langOpted);

                    if (postModel.getImage_uri().length() > 0) {
                        RestAdapter.getAdapter()
                                .bookingAppointmentWithFile(RetrofitUtils.createMultipartRequest(requestValuePairsMap),
                                        RetrofitUtils.createFilePart(KeyValues.ARG_File,
                                                postModel.getImage_uri(),
                                                RetrofitUtils.MEDIA_TYPE_IMAGE_ALL))
                                .enqueue(bookAppointmentCallback);
                    } else {
                        RestAdapter.getAdapter().bookingAppointment(loginDetails.getUser_seq(), postModel.getDoctor_id(), postModel.getReason(), postModel.getComment(), postModel.getChat_type(), dateNew, current_Time_Zone, postModel.getImage_uri(),langOpted).enqueue(bookAppointmentCallback);
                    }

                } else {

                    Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    private void paymentDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title)
                .progress(true, 0);
        dialog = builder.build();
        dialog.show();

    }

    public BookingSummeryModelFromServer.DataBean.BookingSummaryBean getBookingSummaryDetails() {
        return bookingSummaryDetails;
    }


    public PPharmacyModel.AddressBean getpPharmacyModel() {
        return pPharmacyModel;
    }

    public void setpPharmacyModel(PPharmacyModel.AddressBean pPharmacyModel) {
        this.pPharmacyModel = pPharmacyModel;
    }

    public UserLoginDetails.LoginDetails getLoginDetails() {
        return loginDetails;
    }

}
