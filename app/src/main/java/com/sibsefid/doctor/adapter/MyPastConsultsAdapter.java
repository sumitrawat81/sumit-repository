package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.DPastConsultModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 7/9/16.
 */
public class MyPastConsultsAdapter extends RecyclerView.Adapter<MyPastConsultsAdapter.MyViewHolder> {

    private ArrayList<DPastConsultModel.DPastConsults> mList_MyFutureConsultant = null;
    private View.OnClickListener onClickListener;

    public MyPastConsultsAdapter(ArrayList<DPastConsultModel.DPastConsults> mList_MyFutureConsultant) {
        this.mList_MyFutureConsultant = mList_MyFutureConsultant;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypast_consults, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DPastConsultModel.DPastConsults pastConsults = mList_MyFutureConsultant.get(position);

        holder.mDDoctorname.setText(pastConsults.getPatientName());
        holder.mReason.setText(pastConsults.getReason());
        holder.mDStatus.setText(pastConsults.getStatus());
        holder.mDAudio.setText(pastConsults.getAppointmentMode());
        holder.mDAppointmentDate.setText(holder.mDDoctorname.getResources().getString(R.string.on) + " " + pastConsults.getDate());
        holder.mDAppointmentTime.setText(holder.mDDoctorname.getResources().getString(R.string.at) + " " + pastConsults.getTime() + " ");
        holder.button_CommnunicationNote.setOnClickListener(onClickListener);
        holder.button_PhysicanOrder.setOnClickListener(onClickListener);
        holder.button_PhysicanOrder.setTag(position);
        holder.button_ClinicalDashboard.setOnClickListener(onClickListener);
        holder.button_ClinicalDashboard.setTag(position);
        holder.button_CommnunicationNote.setTag(position);


    }

    @Override
    public int getItemCount() {
        return mList_MyFutureConsultant.size();
    }

    public ArrayList<DPastConsultModel.DPastConsults> getmList_MyFutureConsultant() {
        return mList_MyFutureConsultant;
    }

    public void setmList_MyFutureConsultant(ArrayList<DPastConsultModel.DPastConsults> mList_MyFutureConsultant) {
        this.mList_MyFutureConsultant = mList_MyFutureConsultant;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mDDoctorname;
        private TextView mReason;
        private TextView mDStatus;
        private TextView mDAudio;
        private TextView mDAppointmentDate;
        private TextView mDAppointmentTime;

        private Button button_CommnunicationNote;
        private Button button_PhysicanOrder;
        private Button button_ClinicalDashboard;


        public MyViewHolder(View view) {
            super(view);

            mDDoctorname = (TextView) view.findViewById(R.id.mDDoctorname);
            mDStatus = (TextView) view.findViewById(R.id.mDStatus);
            mDAudio = (TextView) view.findViewById(R.id.mDAudio);
            mReason = (TextView) view.findViewById(R.id.mReason);
            mDAppointmentDate = (TextView) view.findViewById(R.id.mDAppointmentDate);
            mDAppointmentTime = (TextView) view.findViewById(R.id.mDAppointmentTime);
            button_CommnunicationNote = (Button) view.findViewById(R.id.button_CommnunicationNote);
            button_PhysicanOrder = (Button) view.findViewById(R.id.button_PhysicanOrder);
            button_ClinicalDashboard = (Button) view.findViewById(R.id.button_ClinicalDashboard);

        }
    }
}
