package com.sibsefid.fragemnts.patient.myappointment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.AppointmentStatusSpAd;
import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.PatientClinicalDashboard;
import com.sibsefid.model.doctor.AppointmentStatusModel;
import com.sibsefid.model.doctor.DPastConsultModel;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.patient.adapter.PPastAdapter;
import com.sibsefid.util.ApiUrls;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PPastAppointment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private RecyclerView recyclerView;
    private ArrayList<DPastConsultModel.DPastConsults> consultsArrayList = new ArrayList<>();
    private PatientActivity patientActivity;
    private View mProgress;
    private UserLoginDetails.LoginDetails details;
    private PPastAdapter pPastAdapter;
    Callback<DPastConsultModel> detailsCallback = new Callback<DPastConsultModel>() {
        @Override
        public void onResponse(Call<DPastConsultModel> call, Response<DPastConsultModel> response) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getDpastcosults() != null && response.body().getDpastcosults().size() > 0) {
                        if (response.body().getDpastcosults() != null) {
                            consultsArrayList = response.body().getDpastcosults();
                            updateAdapter();
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<DPastConsultModel> call, Throwable t) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
            }
        }
    };
    private TextView mDFromDateSelector;
    private TextView mDToDateSelector;
    private MaterialSpinner spinner_Status;
    AdapterView.OnItemSelectedListener mStatusSpinerListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String currentItem = spinner_Status.getSelectedItem().toString();
            updateAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private Button mDFilterBtn;
    private ArrayList<AppointmentStatusModel.DataBean.TableBean> mListAppointmentStatus = new ArrayList<>();
    View.OnClickListener filterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filterArrayList();
        }
    };
    Callback<AppointmentStatusModel> getAppointmentStatusCassBack = new Callback<AppointmentStatusModel>() {
        @Override
        public void onResponse(Call<AppointmentStatusModel> call, Response<AppointmentStatusModel> response) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().getTable() != null && response.body().getData().getTable().size() > 0) {
                        mListAppointmentStatus = new ArrayList<>();
                        mListAppointmentStatus = response.body().getData().getTable();
                        AppointmentStatusSpAd appointmentStatusSpAd = new AppointmentStatusSpAd(getActivity(), android.R.layout.simple_dropdown_item_1line, mListAppointmentStatus);
                        appointmentStatusSpAd.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinner_Status.setAdapter(appointmentStatusSpAd);


                    }
                }
            }
        }

        @Override
        public void onFailure(Call<AppointmentStatusModel> call, Throwable t) {
            if (getActivity() != null) {
                patientActivity.showProgress(false, recyclerView, mProgress);
            }
        }
    };
    private int DIALOG_TYPE = 0; // 1 for from date and 2 for to date
    View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (mDFromDateSelector == view) {
                DIALOG_TYPE = 1;
            } else if (mDToDateSelector == view) {
                DIALOG_TYPE = 2;
            }
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PPastAppointment.this,
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

    public PPastAppointment() {
        // Required empty public constructor
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

        View view = inflater.inflate(R.layout.fragment_ppast_appointment, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            patientActivity.showProgress(true, recyclerView, mProgress);
            String current_Time_Zone = Utils.getTimeZoneDifference();
            RestAdapter.getAdapter().getPPastCosults(details.getUser_seq(), current_Time_Zone,selLangToSend).enqueue(detailsCallback);
            RestAdapter.getAdapter().acceptsAppointmentStatusList(selLangToSend).enqueue(getAppointmentStatusCassBack);
        }

        return view;
    }

    private void initiateView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgress = view.findViewById(R.id.progress);

        mDFromDateSelector = (TextView) view.findViewById(R.id.mDFromDateSelector);
        mDToDateSelector = (TextView) view.findViewById(R.id.mDToDateSelector);
        mDFilterBtn = (Button) view.findViewById(R.id.mDFilter);
        spinner_Status = (MaterialSpinner) view.findViewById(R.id.spinner_Status);

        pPastAdapter = new PPastAdapter(consultsArrayList);
        pPastAdapter.setOnClickListener(this);
        recyclerView.setAdapter(pPastAdapter);

        mDFromDateSelector.setOnClickListener(dateOnClickListener);
        mDToDateSelector.setOnClickListener(dateOnClickListener);

        mDFilterBtn.setOnClickListener(filterClick);
        spinner_Status.setOnItemSelectedListener(mStatusSpinerListner);


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    private void updateAdapter() {
        pPastAdapter.setmValues(consultsArrayList);
        pPastAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_CommnunicationNote:
                int pos = (int) v.getTag();
                showCustomDialog(pos);
                break;
            case R.id.button_PhysicanOrder:
                int pos1 = (int) v.getTag();
                showCustomDialog1(pos1);
                break;

            case R.id.button_ClinicalDashboard:
                int clinical_pos = (int) v.getTag();
                String url;
                String timezone = Utils.getTimeZoneDifference();
                url = ApiUrls.BASE_URL_WebView+"ClinicalDashBoard.aspx?appId=" + consultsArrayList.get(clinical_pos).getAppointid() + "&docId=" + consultsArrayList.get(clinical_pos).getDoctorId()
                        + "&patientId=" + details.getUser_seq() + "&TimeZone=" + timezone;
                if (patientActivity != null) {
                    Utils.callFragmentForAddPatient(patientActivity, new PatientClinicalDashboard().newInstance(url), PatientClinicalDashboard.TAG);
                }

                break;
        }

    }

    public void showCustomDialog(int pos) {
        DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_communication_note);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);

        TextView mPatientName = (TextView) dialog.findViewById(R.id.mPatientName);
        TextView mEpisodeDate = (TextView) dialog.findViewById(R.id.mEpisodeDate);
        TextView mPhysicianName = (TextView) dialog.findViewById(R.id.mPhysicianName);

        EditText mCommunicationNote = (EditText) dialog.findViewById(R.id.mCommunicationNote);
        EditText mSignature = (EditText) dialog.findViewById(R.id.mSignature);
        TextView mSignatureDate = (TextView) dialog.findViewById(R.id.mSignatureDate);
        Button mCancelBtn = (Button) dialog.findViewById(R.id.mCancelBtn);
        Button mSaveBtn = (Button) dialog.findViewById(R.id.mSaveBtn);
        mSaveBtn.setVisibility(View.GONE);
        mCancelBtn.setVisibility(View.GONE);
        mPatientName.setText(details.getFamily_name() + " " + details.getGiven_name());
        mSignature.setText(dPastConsults.getDoctorName());
        mEpisodeDate.setText(dPastConsults.getDate());
        mPhysicianName.setText(dPastConsults.getDoctorName());

        mCommunicationNote.setEnabled(false);
        mSignature.setEnabled(false);
        mCommunicationNote.setText(dPastConsults.getCommunicationNote());
        mSignatureDate.setText(dPastConsults.getDate());

        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showCustomDialog1(int pos) {
        DPastConsultModel.DPastConsults dPastConsults = consultsArrayList.get(pos);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_physician_order);
        ImageView imageView_Close = (ImageView) dialog.findViewById(R.id.imageView_close);
        TextView mAddress = (TextView) dialog.findViewById(R.id.mAddress);
        TextView addresstext = (TextView) dialog.findViewById(R.id.addresstext);
        mAddress.setVisibility(View.GONE);
        addresstext.setVisibility(View.GONE);

        TextView mPatientName = (TextView) dialog.findViewById(R.id.mPatientName);
        TextView mPhysicianName = (TextView) dialog.findViewById(R.id.mPhysicianName);
        TextView mOrderDate = (TextView) dialog.findViewById(R.id.mOrderDate);
        TextView mEpisodeDate = (TextView) dialog.findViewById(R.id.mEpisodeDate);
        EditText mEdtSummery = (EditText) dialog.findViewById(R.id.mEdtSummery);

        TextView mSignature = (TextView) dialog.findViewById(R.id.mSignature);
        TextView mSignatureDate = (TextView) dialog.findViewById(R.id.mSignatureDate);

        mPatientName.setText(details.getFamily_name() + " " + details.getGiven_name());
        mSignature.setText(dPastConsults.getDoctorName());
        mEpisodeDate.setText(dPastConsults.getDate());
        mOrderDate.setText(dPastConsults.getDate());
        mPhysicianName.setText(dPastConsults.getDoctorName());
        mEdtSummery.setText(dPastConsults.getPhysicianorder());
        mSignatureDate.setText(dPastConsults.getDate());
        mEdtSummery.setEnabled(false);


        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void filterArrayList() {


        String dateFrom = mDFromDateSelector.getText().toString().trim();
        String dateTo = mDToDateSelector.getText().toString().trim();
        if(TextUtils.isEmpty(dateFrom)){

            Toast.makeText(getActivity(),"Please enter the from date",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(dateTo)){
            Toast.makeText(getActivity(),"Please enter the to date",Toast.LENGTH_SHORT).show();
        }else {
            ArrayList<DPastConsultModel.DPastConsults> filterConsultsArrayLists = new ArrayList<>();
            Date fromDate = null;
            Date toDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
            try {
                fromDate = dateFormat.parse(dateFrom);
                toDate = dateFormat.parse(dateTo);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(fromDate.after(toDate)){
                Toast.makeText(getActivity(),"from date should not be greater than to date!!",Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < consultsArrayList.size(); i++) {
                String appointmentDateStr = consultsArrayList.get(i).getDate().toString().trim();
                String currentItem = "";
                if (spinner_Status.getSelectedItemPosition() > 0) {
                    currentItem = mListAppointmentStatus.get(spinner_Status.getSelectedItemPosition() - 1).getStatus();
                }
                String steSp = consultsArrayList.get(i).getStatus().toString().trim();
                Date appointmentDate = null;
                try {
                    appointmentDate = dateFormat2.parse(appointmentDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }


                Log.d("appointmentDate---", appointmentDate.toString() + "---" + appointmentDateStr);
                Log.d("steSp---currentItem---", steSp.toString() + "-------" + currentItem.toString());

                if (fromDate != null && toDate != null && appointmentDate.after(fromDate) && appointmentDate.before(toDate)) {
                    if (!TextUtils.isEmpty(currentItem)) {
                        if (steSp.toLowerCase().equals(currentItem.toLowerCase()))
                            filterConsultsArrayLists.add(consultsArrayList.get(i));
                    } else {
                        filterConsultsArrayLists.add(consultsArrayList.get(i));
                    }

                } else if (fromDate != null && appointmentDate.equals(fromDate)) {
                    if (!TextUtils.isEmpty(currentItem)) {
                        if (steSp.toLowerCase().equals(currentItem.toLowerCase()))
                            filterConsultsArrayLists.add(consultsArrayList.get(i));
                    } else {
                        filterConsultsArrayLists.add(consultsArrayList.get(i));
                    }
                } else if (toDate != null && appointmentDate.equals(toDate)) {
                    if (!TextUtils.isEmpty(currentItem)) {
                        if (steSp.toLowerCase().equals(currentItem.toLowerCase()))
                            filterConsultsArrayLists.add(consultsArrayList.get(i));
                    } else {
                        filterConsultsArrayLists.add(consultsArrayList.get(i));
                    }
                } else if (steSp.toLowerCase().equals(currentItem.toLowerCase())) {
                    filterConsultsArrayLists.add(consultsArrayList.get(i));
                }

                if (fromDate == null && toDate == null && TextUtils.isEmpty(currentItem)) {
                    pPastAdapter.setmValues(consultsArrayList);
                    pPastAdapter.notifyDataSetChanged();
                } else {
                    pPastAdapter.setmValues(filterConsultsArrayLists);
                    pPastAdapter.notifyDataSetChanged();
                }

            }
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        String day=String.valueOf(dayOfMonth);
        String month=String.valueOf(monthOfYear);

        if(dayOfMonth<10){
            day="0"+dayOfMonth;
        }
        if(monthOfYear<10){
            month="0"+monthOfYear;
        }
        if (DIALOG_TYPE == 1) {
            mDFromDateSelector.setText(day + "/" + (month) + "/" + year);
        } else if (DIALOG_TYPE == 2) {
            mDToDateSelector.setText(day + "/" + (month) + "/" + year);
        }
    }
}
