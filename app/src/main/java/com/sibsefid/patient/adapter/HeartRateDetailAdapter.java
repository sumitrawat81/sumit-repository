package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PatientHeartRateModel;

import java.util.List;


public class HeartRateDetailAdapter extends RecyclerView.Adapter<HeartRateDetailAdapter.ViewHolder> {


    private List<PatientHeartRateModel.DataBean> pHeartRateDetails;

    public HeartRateDetailAdapter(List<PatientHeartRateModel.DataBean> items) {
        pHeartRateDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.heart_rate_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pHeartRateDetails.get(position);
        holder.mMeasuredDate.setText(holder.mItem.getDate());
        holder.mPulseValue.setText(holder.mItem.getPulse_value());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return pHeartRateDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMeasuredDate;
        public final TextView mPulseValue;


        public PatientHeartRateModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMeasuredDate = (TextView) view.findViewById(R.id.mMeasuredDate);
            mPulseValue = (TextView) view.findViewById(R.id.mPulseValue);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeasuredDate.getText() + "'";
        }
    }
}
