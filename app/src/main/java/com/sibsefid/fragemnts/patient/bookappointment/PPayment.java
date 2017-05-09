package com.sibsefid.fragemnts.patient.bookappointment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.PayPalResponseModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookingSummeryModelFromServer;
import com.sibsefid.model.patient.PaymentPostModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.PayPalConfig;
import com.sibsefid.util.Utils;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PPayment extends Fragment implements View.OnClickListener {

    public static final String TAG = "PPayment";
    public static final int PAYPAL_REQUEST_CODE = 123;
    public static final int REQUEST_CODE = 111;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final String CONFIG_CLIENT_ID = PayPalConfig.PAYPAL_CLIENT_ID;
    private static PPayment pPayment;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    //Payment Amount
    private String paymentAmount;
    private RadioButton mPaypalCheckBox;
    private RadioButton mCardCheckBox;
    private RadioButton mVoucherCheckBox;
    private Button mBackBtn;
    private Button mContinueBtn;
    private LinearLayout mVoucherLayout;
    private LinearLayout mCardLayout;
    private EditText mVoucherCodeEdt;
    private TextView mDoctorFee;
    private PaymentPostModel paymentPostModel = new PaymentPostModel();
    private UserLoginDetails.LoginDetails loginDetails;
    private MaterialDialog dialog;
    Callback<BookingSummeryModelFromServer> PaymantCallback = new Callback<BookingSummeryModelFromServer>() {
        @Override
        public void onResponse(Call<BookingSummeryModelFromServer> call, Response<BookingSummeryModelFromServer> response) {
            if (getActivity() != null) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.appointment_success), Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
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
            }
        }
    };
    private PChoosePharmacy pharmacy;

    public static PPayment getInstance() {

        return pPayment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDetails = MyPrefs.getLoginDetails(getActivity());
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

        pPayment = this;
        View view = inflater.inflate(R.layout.fragment_ppayment, container, false);
        initiateView(view);


        return view;
    }

    private void initiateView(View view) {

        mBackBtn = (Button) view.findViewById(R.id.mBackBtn);
        mContinueBtn = (Button) view.findViewById(R.id.mContinueBtn);
        mPaypalCheckBox = (RadioButton) view.findViewById(R.id.mPaypalCheckBox);
        mCardCheckBox = (RadioButton) view.findViewById(R.id.mCardCheckBox);
        mVoucherCheckBox = (RadioButton) view.findViewById(R.id.mVoucherCheckBox);
        mVoucherLayout = (LinearLayout) view.findViewById(R.id.mVoucherLayout);
        mCardLayout = (LinearLayout) view.findViewById(R.id.mCardLayout);
        mVoucherCodeEdt = (EditText) view.findViewById(R.id.mVoucherCodeEdt);
        mDoctorFee = (TextView) view.findViewById(R.id.mDoctorFee);
        mBackBtn.setOnClickListener(this);
        mContinueBtn.setOnClickListener(this);
        mPaypalCheckBox.setOnClickListener(this);
        mCardCheckBox.setOnClickListener(this);
        mVoucherCheckBox.setOnClickListener(this);
        mContinueBtn.setText(getActivity().getResources().getString(R.string.pay_with_paypal));

    }

    public void setDectorFee() {
        if (mDoctorFee != null && getActivity() != null) {
            pharmacy = PChoosePharmacy.getInstance();
            paymentAmount = pharmacy.getBookingSummaryDetails().getFeeClient();
            mDoctorFee.setText(getActivity().getResources().getString(R.string.fee) + " " + paymentAmount + " USD ");
        }
    }


   /* BookingID==>your booking id
    PatientID==>your Patient id
    TransactionNumber==> if paypal then paypalid else voucher then vouchercode
    TransactionType==>Voucher ,Palpal, Credit Card Paypal{if this then send -- CardNumber,ExpirationMonth,ExpirationYear,NameCard,CVV}*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBackBtn:
                PMainBookAppointment.getInstance().getmPager().setCurrentItem(1);
                break;

            case R.id.mContinueBtn:
                pharmacy = PChoosePharmacy.getInstance();
                if (mPaypalCheckBox.isChecked()) {
                    getPayment();
                } else {
                    submitPaymentDetails("");
                }
                break;
            case R.id.mPaypalCheckBox:
                mVoucherLayout.setVisibility(View.GONE);
                mCardLayout.setVisibility(View.GONE);
                mCardCheckBox.setChecked(false);
                mVoucherCheckBox.setChecked(false);
                mContinueBtn.setText(getActivity().getResources().getString(R.string.pay_with_paypal));
                break;
            case R.id.mCardCheckBox:
                mVoucherLayout.setVisibility(View.GONE);
                mCardLayout.setVisibility(View.VISIBLE);
                mPaypalCheckBox.setChecked(false);
                mVoucherCheckBox.setChecked(false);
                mContinueBtn.setText(getActivity().getResources().getString(R.string.pay_with_credit_card));
                break;
            case R.id.mVoucherCheckBox:
                mVoucherLayout.setVisibility(View.VISIBLE);
                mCardLayout.setVisibility(View.GONE);
                mPaypalCheckBox.setChecked(false);
                mCardCheckBox.setChecked(false);
                mContinueBtn.setText(getActivity().getResources().getString(R.string.pay_with_voucher));
                break;
        }
    }

    private void submitPaymentDetails(String voucher_no) {

        String TransactionType = "";
        boolean isPayment = false;
        String current_Time_Zone = Utils.getTimeZoneDifference();
        if (mVoucherCheckBox.isChecked()) {
            if (!TextUtils.isEmpty(mVoucherCodeEdt.getText().toString().trim())) {
                TransactionType = getActivity().getResources().getString(R.string.voucher);
                voucher_no = mVoucherCodeEdt.getText().toString().trim();
                isPayment = true;
            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_enter_your_code), Toast.LENGTH_SHORT).show();
            }
        } else if (mPaypalCheckBox.isChecked() && voucher_no != null && voucher_no.length() > 0) {
            TransactionType = getActivity().getResources().getString(R.string.paypal);
            isPayment = true;
        }
        if (isPayment) {
            paymentPostModel.setTransactionType(TransactionType);
            paymentPostModel.setPatientID(loginDetails.getUser_seq());
            paymentPostModel.setBookingID(pharmacy.getBookingSummaryDetails().getId());
            if (Utils.isDeviceOnline(getActivity())) {
                paymentDialog();
                String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                RestAdapter.getAdapter().bookingPayment(paymentPostModel.getPatientID(), paymentPostModel.getBookingID(), voucher_no, paymentPostModel.getTransactionType(), current_Time_Zone,langOpted).enqueue(PaymantCallback);
            }
        }


    }

    private void paymentDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title)
                .progress(true, 0);
        dialog = builder.build();
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Gson gson = new Gson();
                        PayPalResponseModel response = gson.fromJson(paymentDetails.toString(), PayPalResponseModel.class);
                        submitPaymentDetails(response.getResponse().getId());
                        Log.i("paymentExample", paymentDetails);
                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }

    private void getPayment() {
        pharmacy = PChoosePharmacy.getInstance();
        paymentAmount = pharmacy.getBookingSummaryDetails().getFeeDoctor();
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Appointment book Fee", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }


}

