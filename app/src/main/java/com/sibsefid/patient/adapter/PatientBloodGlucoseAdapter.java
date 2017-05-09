package com.sibsefid.patient.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.GetPatientBloodGlucoseModel;

import java.util.List;


public class PatientBloodGlucoseAdapter extends RecyclerView.Adapter<PatientBloodGlucoseAdapter.ViewHolder> {


    private List<GetPatientBloodGlucoseModel.DataBean> pGlusoceDetails;

    public PatientBloodGlucoseAdapter(List<GetPatientBloodGlucoseModel.DataBean> items) {
        pGlusoceDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_blood_glucose_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pGlusoceDetails.get(position);
        holder.mMeasuredDate.setText(holder.mItem.getDate());
        holder.mglucose.setText(holder.mItem.getBldsgr_value());
        holder.mMeals.setText(holder.mItem.getMemo());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return pGlusoceDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMeasuredDate;
        public final TextView mglucose;
        public final TextView mMeals;

        public GetPatientBloodGlucoseModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMeasuredDate = (TextView) view.findViewById(R.id.mMeasuredDate);
            mglucose = (TextView) view.findViewById(R.id.mglucose);
            mMeals = (TextView) view.findViewById(R.id.mMeals);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMeasuredDate.getText() + "'";
        }
    }
}
