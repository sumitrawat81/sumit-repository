package com.sibsefid.patient.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.GetPatientWaistModel;

import java.util.List;


public class WaistCircumferenceAdapter extends RecyclerView.Adapter<WaistCircumferenceAdapter.ViewHolder> {


    private List<GetPatientWaistModel.DataBean> pFamilyMemberDetails;

    public WaistCircumferenceAdapter(List<GetPatientWaistModel.DataBean> items) {
        pFamilyMemberDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.temperature_detail_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pFamilyMemberDetails.get(position);
        holder.mMeasuredDate.setText(holder.mItem.getDate());
        holder.mTempValue.setText(holder.mItem.getWaist());


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
        public final TextView mTempValue;


        public GetPatientWaistModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMeasuredDate = (TextView) view.findViewById(R.id.mMeasuredDate);
            mTempValue = (TextView) view.findViewById(R.id.mTempValue);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeasuredDate.getText() + "'";
        }
    }
}
