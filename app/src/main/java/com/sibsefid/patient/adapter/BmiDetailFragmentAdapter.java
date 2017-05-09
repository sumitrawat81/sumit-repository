package com.sibsefid.patient.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PatientBMIDetailModel;

import java.util.List;


public class BmiDetailFragmentAdapter extends RecyclerView.Adapter<BmiDetailFragmentAdapter.ViewHolder> {


    private List<PatientBMIDetailModel.DataBean> pFamilyMemberDetails;

    public BmiDetailFragmentAdapter(List<PatientBMIDetailModel.DataBean> items) {
        pFamilyMemberDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bmi_detail_fragment_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pFamilyMemberDetails.get(position);
        holder.mMeasuredDate.setText(holder.mItem.getDate());
        holder.mBmiValue.setText(holder.mItem.getBMI_value());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return pFamilyMemberDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMeasuredDate;
        public final TextView mBmiValue;


        public PatientBMIDetailModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMeasuredDate = (TextView) view.findViewById(R.id.mMeasuredDate);
            mBmiValue = (TextView) view.findViewById(R.id.mBmiValue);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeasuredDate.getText() + "'";
        }
    }
}
