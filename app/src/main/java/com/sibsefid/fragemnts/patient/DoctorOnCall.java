package com.sibsefid.fragemnts.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.fragemnts.patient.bookappointmentDoctorCall.PMainBookAppDocCall;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.model.patient.BookAppointmentPostModel;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;



/**
 * Created by ubuntu on 20/12/16.
 */
public class DoctorOnCall extends Fragment implements View.OnClickListener{

    private TextView mDateChooser;

    private TextView mName;
    private TextView mProvider;
    private TextView mTime;
    private TextView mPhoneNumber;
    private Button CancelBtn;
    private Button mBookAppointment;
    private View progress;
    private View mParentLayout;
    private UserLoginDetails.LoginDetails details;
    // private DoctorListModel.DataBean.DoctorListBean selectedDoctor;
    private PatientActivity patientActivity;
    private BookAppointmentPostModel postModel = new BookAppointmentPostModel();

    public static final String TAG = "DoctorOnCall";
    private static DoctorOnCall fragment;


    public static DoctorOnCall getInstance() {

        return fragment;
    }

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

        fragment = this;
        View view = inflater.inflate(R.layout.dr_on_call, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mDateChooser = (TextView) view.findViewById(R.id.mDateChooser);


        CancelBtn = (Button) view.findViewById(R.id.CancelBtn);
        mBookAppointment = (Button) view.findViewById(R.id.mBookAppointment);
        mName = (TextView) view.findViewById(R.id.mName);
        mProvider = (TextView) view.findViewById(R.id.mProvider);
        mTime = (TextView) view.findViewById(R.id.mTime);
        mPhoneNumber = (TextView) view.findViewById(R.id.mPhoneNumber);
        progress = view.findViewById(R.id.progress);
        mParentLayout = view.findViewById(R.id.mParentLayout);


        CancelBtn.setOnClickListener(this);
        mBookAppointment.setOnClickListener(this);
        mDateChooser.setOnClickListener(this);

        mName.setText(details.getFamily_name());

        mProvider.setText("Choose First Available");
        mPhoneNumber.setText(details.getPhone().toString());

        mDateChooser.setText(Utils.getCurrentDate());
        Calendar c = Calendar.getInstance () ;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String time = sdf.format(c.getTime());
        mTime.setText(time);

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.mBookAppointment:
                saveAppointment();
                break;
        }
    }



    private void saveAppointment() {

        String strDate = mDateChooser.getText().toString().trim();

        String time=mTime.getText().toString().trim();
        String provider="Choose First Available";
        String strPhone=mPhoneNumber.getText().toString().trim();
        String chatType = "Phone Consultation";

        if (TextUtils.isEmpty(strPhone) || !isPhoneNumber(strPhone)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_phone_number), Toast.LENGTH_SHORT).show();
        } else {
            postModel = new BookAppointmentPostModel();
           // postModel.setChat_type(chatType);
            postModel.setDate(strDate);
          //  postModel.setDoctor_id(details.getUser_id());
            postModel.setTime("time");
            postModel.setPatientPhone(strPhone);

            PMainBookAppDocCall fragment=new PMainBookAppDocCall();

            Utils.callFragmentForAddPatient(patientActivity,fragment, DoctorOnCall.TAG);
        }

    }

    private boolean isPhoneNumber(String strPhone) {
        boolean isTrue=false;
        if(strPhone.length()==10){
            isTrue=true;
        }
        return isTrue;
    }


    public BookAppointmentPostModel getPostModel() {
        return postModel;
    }


}
