package com.sibsefid.patient.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.GetPatientBloodPressureModel;

import java.util.List;


public class PatientBloodPressureListAdapter extends RecyclerView.Adapter<PatientBloodPressureListAdapter.ViewHolder> {


    private List<GetPatientBloodPressureModel.DataBean> pBloodPressureDetails;

    public PatientBloodPressureListAdapter(List<GetPatientBloodPressureModel.DataBean> items) {
        pBloodPressureDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_bloodpressure_list_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pBloodPressureDetails.get(position);
        holder.mMeasuredDate.setText(holder.mItem.getDate());
        holder.mSystolicValue.setText(holder.mItem.getSystolic_value());
        holder.mDiastolicValue.setText(holder.mItem.getDiastolic_value());
        holder.mPulse.setText(holder.mItem.getPulse_value());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return pBloodPressureDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMeasuredDate;
        public final TextView mSystolicValue;
        public final TextView mDiastolicValue;
        public final TextView mPulse;
        public GetPatientBloodPressureModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMeasuredDate = (TextView) view.findViewById(R.id.mMeasuredDate);
            mSystolicValue = (TextView) view.findViewById(R.id.mSystolicValue);
            mDiastolicValue = (TextView) view.findViewById(R.id.mDiastolicValue);
            mPulse = (TextView) view.findViewById(R.id.mPulse);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeasuredDate.getText() + "'";
        }
    }
}
