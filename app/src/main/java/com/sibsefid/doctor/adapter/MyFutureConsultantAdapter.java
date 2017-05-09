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
public class MyFutureConsultantAdapter extends RecyclerView.Adapter<MyFutureConsultantAdapter.MyViewHolder> {

    public OnItemClickListener listener = null;
    private ArrayList<DPastConsultModel.DPastConsults> mList_MyFutureConsultant = null;


    public MyFutureConsultantAdapter(ArrayList<DPastConsultModel.DPastConsults> mList_MyFutureConsultant, OnItemClickListener listener) {
        this.listener = listener;
        this.mList_MyFutureConsultant = mList_MyFutureConsultant;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myfuture_consultant, null);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DPastConsultModel.DPastConsults pastConsults = mList_MyFutureConsultant.get(position);

        holder.mDDoctorname.setText(pastConsults.getPatientName());
        holder.mDStatus.setText(pastConsults.getStatus());
        holder.mReason.setText(pastConsults.getReason());
        holder.mDAudio.setText(pastConsults.getAppointmentMode());
        holder.mChatType.setText(pastConsults.getAppointmentMode());
        holder.mDAppointmentDate.setText(holder.mDDoctorname.getResources().getString(R.string.on) + " " + pastConsults.getDate());
        holder.mDAppointmentTime.setText(holder.mDDoctorname.getResources().getString(R.string.at) + " " + pastConsults.getTime() + " ");

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

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button mChatType = null;
        private TextView mDDoctorname;
        private TextView mReason;
        private TextView mDStatus;
        private TextView mDAudio;
        private TextView mDAppointmentDate;
        private TextView mDAppointmentTime;

        public MyViewHolder(View view) {
            super(view);

            mDDoctorname = (TextView) view.findViewById(R.id.mDDoctorname);
            mDStatus = (TextView) view.findViewById(R.id.mDStatus);
            mReason = (TextView) view.findViewById(R.id.mReason);
            mDAudio = (TextView) view.findViewById(R.id.mDAudio);
            mDAppointmentDate = (TextView) view.findViewById(R.id.mDAppointmentDate);
            mDAppointmentTime = (TextView) view.findViewById(R.id.mDAppointmentTime);

            mChatType = (Button) view.findViewById(R.id.mChatType);

            mChatType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition(), v);
                    }
                }
            });

        }
    }
}
