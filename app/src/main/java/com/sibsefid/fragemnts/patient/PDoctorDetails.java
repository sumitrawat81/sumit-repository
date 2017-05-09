package com.sibsefid.fragemnts.patient;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.doctor.UserProfileDetails;
import com.sibsefid.model.patient.DoctorListModel;
import com.sibsefid.model.patient.UpdateProfilePictureModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PDoctorDetails extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String TAG = "PDoctorDetails";

    private View mProgressView;
    private TextView mName;
    private TextView mAddress;
    private TextView mAvailabilityTime;
    private TextView mAboutUs;
    private TextView mQualification;
    private TextView mState;
    private TextView language;
    private TextView mAvailabilityNote;
    private ImageView mProfileImage;
    private View mScrollView;
    private Button requestAppointmentBtn;

    private LinearLayout requestAppointmentLayout;
    private UserLoginDetails.LoginDetails details;
    private PatientActivity activity = null;
    ProgressBar  pop_progress;
    Dialog dialog;
    private TextView mDFromDateSelector;
    private TextView mtermsCoundition;
    private MaterialSpinner mscheduleTime;
    private EditText mContactNumber;
    private EditText mreasonForAppointment;
    private EditText mAdditionalComment;
    private CheckBox mIAgreeChck;
    private RadioButton mChatCheckBox;
    private RadioButton mAudioCheckBox;
    private RadioButton mVideoCheckBox;

    private String[] appointmentTimings = new String[3];

    private String DOCTOR_ID = "";
    private DoctorListModel.DataBean.DoctorListBean selectedDoctorDetails;
    private ArrayList<DoctorListModel.DataBean.AvailablityListBean> doctorAvailabilityArrayList;
    Callback<UserProfileDetails> detailsCallback = new Callback<UserProfileDetails>() {
        @Override
        public void onResponse(Call<UserProfileDetails> call, Response<UserProfileDetails> response) {
            if (getActivity() != null) {
                activity.showProgress(false, mScrollView, mProgressView);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDProfileDetails() != null && response.body().getDProfileDetails().size() > 0) {
                        if (response.body().getDProfileDetails() != null) {
                            //  dProfileDetails = response.body().getDProfileDetails().get(0);
                            updateUi();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UserProfileDetails> call, Throwable t) {
            if (getActivity() != null) {
                activity.showProgress(false, mScrollView, mProgressView);
            }
        }
    };

    Callback<UpdateProfilePictureModel> modelCallback = new Callback<UpdateProfilePictureModel>() {
        @Override
        public void onResponse(Call<UpdateProfilePictureModel> call, Response<UpdateProfilePictureModel> response) {
            if (getActivity() != null) {
                pop_progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getMessage() != null && response.body().getMessage().length() > 0) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateProfilePictureModel> call, Throwable t) {
            if (getActivity() != null) {
                pop_progress.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PDoctorDetails.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(false);
            dpd.vibrate(true);
            dpd.dismissOnPause(true);
            dpd.showYearPickerFirst(false);
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
            dpd.setTitle(getActivity().getResources().getString(R.string.date_dailog_title));
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    };
    AdapterView.OnItemSelectedListener mTimeChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mscheduleTime.getSelectedItem().toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
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

        Bundle bundle = getArguments();
        DOCTOR_ID = bundle.getString("id");
        selectedDoctorDetails = (DoctorListModel.DataBean.DoctorListBean) bundle.getSerializable("details");
        doctorAvailabilityArrayList = (ArrayList<DoctorListModel.DataBean.AvailablityListBean>) bundle.getSerializable("availabilitydetails");
        View view = inflater.inflate(R.layout.fragment_pdoctor_details, container, false);
        initComponents(view);

        if (Utils.isDeviceOnline(getActivity())) {
            // activity.showProgress(true, mScrollView, mProgressView);
            // RestAdapter.getAdapter().getUserProfile(DOCTOR_ID).enqueue(detailsCallback);
        }
        return view;
    }

    public void initComponents(View view) {

        mProgressView = view.findViewById(R.id.progress);
        mScrollView = view.findViewById(R.id.mScrollView);
        mName = (TextView) view.findViewById(R.id.mName);
        mAddress = (TextView) view.findViewById(R.id.mAddress);
        mAvailabilityTime = (TextView) view.findViewById(R.id.mAvailabilityTime);
        mAboutUs = (TextView) view.findViewById(R.id.mAboutUs);
        mQualification = (TextView) view.findViewById(R.id.mQualification);
        mState = (TextView) view.findViewById(R.id.mState);
        language = (TextView) view.findViewById(R.id.language);
        mAvailabilityNote = (TextView) view.findViewById(R.id.mAvailabilityNote);
        mProfileImage = (ImageView) view.findViewById(R.id.mProfileImage);
        requestAppointmentBtn=(Button)view.findViewById(R.id.requestAppointmentBtn);
        requestAppointmentLayout=(LinearLayout)view.findViewById(R.id.requestAppointmentLayout);
        requestAppointmentBtn.setOnClickListener(this);

        appointmentTimings[0]="Morning";
        appointmentTimings[1]="After Noon";
        appointmentTimings[2]="Evening";

        updateUi();


    }

    private void updateUi() {

        if (selectedDoctorDetails != null) {
            String address = "";
            if (!TextUtils.isEmpty(selectedDoctorDetails.getAddress_1())) {
                address = selectedDoctorDetails.getAddress_1() + " " + selectedDoctorDetails.getAddress_2();
            }
            if (!TextUtils.isEmpty(selectedDoctorDetails.getCity())) {
                if (address.length() > 0) {
                    address = address + ", " + selectedDoctorDetails.getCity();
                } else {
                    address = selectedDoctorDetails.getCity();
                }
            }
            if (!TextUtils.isEmpty(selectedDoctorDetails.getStateName())) {
                if (address.length() > 0) {
                    address = address + ", " + selectedDoctorDetails.getStateName();
                } else {
                    address = selectedDoctorDetails.getStateName();
                }
            }
            if (!TextUtils.isEmpty(selectedDoctorDetails.getCountryName())) {
                if (address.length() > 0) {
                    address = address + ", " + selectedDoctorDetails.getCountryName();
                } else {
                    address = selectedDoctorDetails.getCountryName();
                }

            }

            mName.setText(selectedDoctorDetails.getTitle() + " " + selectedDoctorDetails.getName() + "\n" + "Price:" + selectedDoctorDetails.getFeeClient() + "$");
            mAddress.setText(address);
            String availableDT = "";
            for (int i = 0; i < doctorAvailabilityArrayList.size(); i++) {
                if (doctorAvailabilityArrayList.get(i).getDoctorId().equals(DOCTOR_ID)) {
                    availableDT = availableDT + doctorAvailabilityArrayList.get(i).getDateTimeF() + " to " + doctorAvailabilityArrayList.get(i).getDateTimeT() + "\n";

                }
            }
            if(availableDT.length()>0){
                mAvailabilityTime.setText(availableDT);
                requestAppointmentLayout.setVisibility(View.GONE);
            }else{
                mAvailabilityTime.setText("Not Available");
                requestAppointmentLayout.setVisibility(View.VISIBLE);
                mAvailabilityNote.setText(selectedDoctorDetails.getTitle() + " " +selectedDoctorDetails.getName()+" "+getResources().getString(R.string.availability_note));
            }

            mAboutUs.setText(selectedDoctorDetails.getAboutMe());
            mQualification.setText(selectedDoctorDetails.getQualifications());
            mState.setText(selectedDoctorDetails.getStateName() + ", " + selectedDoctorDetails.getCountryName());
            language.setText(selectedDoctorDetails.getLanguageSpoken());
            try {
                Picasso.with(getActivity())
                        .load(selectedDoctorDetails.getUser_photo())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .into(mProfileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.requestAppointmentBtn:
                showCustomDialog();
                break;
        }

    }


    private void showCustomDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_request_appointment);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);


        mDFromDateSelector = (TextView) dialog.findViewById(R.id.mDFromDateSelector);
        mtermsCoundition = (TextView) dialog.findViewById(R.id.mtermsCoundition);
        mscheduleTime = (MaterialSpinner) dialog.findViewById(R.id.mscheduleTime);
        mContactNumber = (EditText) dialog.findViewById(R.id.mContactNumber);
        mreasonForAppointment = (EditText) dialog.findViewById(R.id.mreasonForAppointment);
        mAdditionalComment = (EditText) dialog.findViewById(R.id.mAdditionalComment);
        mChatCheckBox = (RadioButton) dialog.findViewById(R.id.mChatCheckBox);
        mAudioCheckBox = (RadioButton) dialog.findViewById(R.id.mAudioCheckBox);
        mVideoCheckBox = (RadioButton) dialog.findViewById(R.id.mVideoCheckBox);
        mIAgreeChck=(CheckBox)dialog.findViewById(R.id.mIAgreeChck);

        pop_progress = (ProgressBar) dialog.findViewById(R.id.pop_progress);

        Button mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, appointmentTimings);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mscheduleTime.setAdapter(timeAdapter);
        mscheduleTime.setSelection(0);

        mChatCheckBox.setChecked(true);
        mDFromDateSelector.setOnClickListener(dateOnClickListener);
        mscheduleTime.setOnItemSelectedListener(mTimeChooserListner);
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strContact = mContactNumber.getText().toString();
                String strReason = mreasonForAppointment.getText().toString();
                String strSelectedDate = mDFromDateSelector.getText().toString();
                String strAdditionalComment = mAdditionalComment.getText().toString();
                String strAppointmentTimings = appointmentTimings[mscheduleTime.getSelectedItemPosition()];
                String strSenderId = details.getUser_seq();
                String chatType = "";
                if (mChatCheckBox.isChecked()) {
                    chatType = "Chat";
                } else if (mAudioCheckBox.isChecked()) {
                    chatType = "Audio";
                } else if (mVideoCheckBox.isChecked()) {
                    chatType = "Video";
                }

                if (TextUtils.isEmpty(strSelectedDate)) {
                    Toast.makeText(getActivity(), "Please fill the Date", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(strContact)) {
                    Toast.makeText(getActivity(), "Please fill the Contact No.", Toast.LENGTH_SHORT).show();
                }else if (strContact.length()<10) {
                    Toast.makeText(getActivity(), "Please fill the Valid Contact No.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(strReason)) {
                    Toast.makeText(getActivity(), "Please fill the Reason", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strAdditionalComment)) {
                    Toast.makeText(getActivity(), "Please fill the Additional Comment", Toast.LENGTH_SHORT).show();
                } else if(!mIAgreeChck.isChecked()) {
                    Toast.makeText(getActivity(), "Please Accept Terms & Counditions", Toast.LENGTH_SHORT).show();
                }else
                {

                    if (Utils.isDeviceOnline(getActivity())) {
                        String langOpted=Utils.getuserSeletedLanagueForRequestSend(getActivity());
                        pop_progress.setVisibility(View.VISIBLE);
                        RestAdapter.getAdapter().appointmentRequest(strSelectedDate,appointmentTimings[mscheduleTime.getSelectedItemPosition()],strContact,strReason,strAdditionalComment,chatType,selectedDoctorDetails.getName(),selectedDoctorDetails.getEmail(),details.getFamily_name(),details.getEmail(),langOpted).enqueue(modelCallback);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String day=String.valueOf(dayOfMonth);
        String month=String.valueOf(monthOfYear);

        if(dayOfMonth<10){
            day="0"+dayOfMonth;
        }
        if(monthOfYear<10){
            month="0"+monthOfYear;
        }
        mDFromDateSelector.setText(day + "/" + (month) + "/" + year);
    }
}
