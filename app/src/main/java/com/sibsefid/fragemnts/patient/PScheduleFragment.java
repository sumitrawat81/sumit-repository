package com.sibsefid.fragemnts.patient;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.fragemnts.patient.bookappointment.PMainBookAppointment;
import com.sibsefid.model.patient.BookAppointmentPostModel;
import com.sibsefid.model.patient.DoctorListModel;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class PScheduleFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    public static final String TAG = "PScheduleFragment";
    private static PScheduleFragment fragment;
    private MaterialSpinner mHourChooser;
    AdapterView.OnItemSelectedListener mHourChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mHourChooser.getSelectedItem().toString();
            Log.d(TAG, "mHourChooserListner: " + textHint);
            if (getActivity().getResources().getString(R.string.hours).equalsIgnoreCase(textHint))
                return;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mMinuteChooser;
    AdapterView.OnItemSelectedListener mMinuteChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mMinuteChooser.getSelectedItem().toString();
            Log.d(TAG, "mMinuteChooserListner: " + textHint);
            if (getActivity().getResources().getString(R.string.minutes).equalsIgnoreCase(textHint))
                return;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private MaterialSpinner mTimeChooser;
    AdapterView.OnItemSelectedListener mTimeZoneChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mTimeChooser.getSelectedItem().toString();
            Log.d(TAG, "mTimeZoneChooserListner: " + textHint);
            if (getActivity().getResources().getString(R.string.select_a_time_zone).equalsIgnoreCase(textHint))
                return;

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener mTimeChooserListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String textHint = mTimeChooser.getSelectedItem().toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private TextView mDateChooser;
    private RadioButton mChatCheckBox;
    private RadioButton mAudioCheckBox;
    private RadioButton mVideoCheckBox;
    private TextView mName;
    private TextView mProvider;
    private Button CancelBtn;
    private Button mBookAppointment;
    private View progress;
    private View mParentLayout;
    private String[] Am_PM_ITEMS = new String[2];
    private String[] MINUTES_ITEMS = null;
    private String[] HOURS_ITEMS = null;
    private String[] TIME_ZONE_ITEMS = new String[13];
    private int DATE_CHOOSER_TYPE = 0;
    private DoctorListModel.DataBean.DoctorListBean selectedDoctor;
    private PatientActivity patientActivity;
    private BookAppointmentPostModel postModel = new BookAppointmentPostModel();

    public static PScheduleFragment getInstance() {

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientActivity = (PatientActivity) getActivity();
        selectedDoctor = PSeeDoctorList.newInstance().getSelectedDoctor();
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
        View view = inflater.inflate(R.layout.fragment_pschedule, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mDateChooser = (TextView) view.findViewById(R.id.mDateChooser);
        mHourChooser = (MaterialSpinner) view.findViewById(R.id.mHourChooser);
        mMinuteChooser = (MaterialSpinner) view.findViewById(R.id.mMinuteChooser);
        mTimeChooser = (MaterialSpinner) view.findViewById(R.id.mTimeChooser);
        mChatCheckBox = (RadioButton) view.findViewById(R.id.mChatCheckBox);
        mAudioCheckBox = (RadioButton) view.findViewById(R.id.mAudioCheckBox);
        mVideoCheckBox = (RadioButton) view.findViewById(R.id.mVideoCheckBox);
        CancelBtn = (Button) view.findViewById(R.id.CancelBtn);
        mBookAppointment = (Button) view.findViewById(R.id.mBookAppointment);
        mName = (TextView) view.findViewById(R.id.mName);
        mProvider = (TextView) view.findViewById(R.id.mProvider);
        progress = view.findViewById(R.id.progress);
        mParentLayout = view.findViewById(R.id.mParentLayout);


        MINUTES_ITEMS = getActivity().getResources().getStringArray(R.array.minutes_array);
        HOURS_ITEMS = getActivity().getResources().getStringArray(R.array.hoursarray);
        Am_PM_ITEMS[0] = getActivity().getResources().getString(R.string.am);
        Am_PM_ITEMS[1] = getActivity().getResources().getString(R.string.pm);

        ArrayAdapter<String> AMPMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Am_PM_ITEMS);
        AMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> HoursAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, HOURS_ITEMS);
        HoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> minutesArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MINUTES_ITEMS);
        minutesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mHourChooser.setAdapter(HoursAdapter);
        mMinuteChooser.setAdapter(minutesArrayAdapter);
        mTimeChooser.setAdapter(AMPMAdapter);
        mTimeChooser.setSelection(0);
        mHourChooser.setOnItemSelectedListener(mHourChooserListner);
        mMinuteChooser.setOnItemSelectedListener(mMinuteChooserListner);
        mTimeChooser.setOnItemSelectedListener(mTimeChooserListner);

        CancelBtn.setOnClickListener(this);
        mBookAppointment.setOnClickListener(this);
        mDateChooser.setOnClickListener(this);

        mName.setText(selectedDoctor.getName());
        mProvider.setText(selectedDoctor.getSpecaility());
        if (selectedDoctor.isAvailabe()) {
            mDateChooser.setText(Utils.getCurrentDate());
            mDateChooser.setEnabled(false);
        } else {

        }

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View view) {
        if (mDateChooser == view) {
            DATE_CHOOSER_TYPE = 1;
            datePickerOpen();
        } else if (view == mBookAppointment) {
            saveAppointment();
        }
    }

    private void datePickerOpen() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PScheduleFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(getResources().getString(R.string.date_dailog_title));
        dpd.setMinDate(now);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (DATE_CHOOSER_TYPE == 1) {
            int daMoNew = monthOfYear + 1;
            String month = daMoNew + "";
            String day = dayOfMonth + "";
            if (daMoNew < 10) {
                month = "0" + month + "";
            }
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth + "";
            }


            mDateChooser.setText(month + "/" + (day) + "/" + year);
        }
    }

    private void saveAppointment() {

        String strDate = mDateChooser.getText().toString().trim();
        int hoursPos = mHourChooser.getSelectedItemPosition();
        int minutesPos = mMinuteChooser.getSelectedItemPosition();
        int amPos = mTimeChooser.getSelectedItemPosition();
        String chatType = "";
        if (mChatCheckBox.isChecked()) {
            chatType = "Chat";
        } else if (mAudioCheckBox.isChecked()) {
            chatType = "Audio";
        } else if (mVideoCheckBox.isChecked()) {
            chatType = "Video";
        }

        if (TextUtils.isEmpty(strDate)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_date), Toast.LENGTH_SHORT).show();
        } else if (hoursPos <= 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_hours), Toast.LENGTH_SHORT).show();
        } else if (minutesPos <= 0) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_minute), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(chatType)) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_invalid_chat_type), Toast.LENGTH_SHORT).show();
        } else {
            postModel = new BookAppointmentPostModel();
            postModel.setChat_type(chatType);
            postModel.setDate(strDate);
            postModel.setDoctor_id(selectedDoctor.getDoctorId());
            postModel.setHours(HOURS_ITEMS[mHourChooser.getSelectedItemPosition() - 1]);
            postModel.setMinutes(MINUTES_ITEMS[mMinuteChooser.getSelectedItemPosition() - 1]);
            postModel.setTime(Am_PM_ITEMS[mTimeChooser.getSelectedItemPosition()]);

            PMainBookAppointment fragment=new PMainBookAppointment();

            Utils.callFragmentForAddPatient(patientActivity,fragment, PMainBookAppointment.TAG);
         //   Utils.callFragmentForAddPatient(patientActivity, new PMainBookAppointment(), PMainBookAppointment.TAG);
        }

    }


    public BookAppointmentPostModel getPostModel() {
        return postModel;
    }


}
