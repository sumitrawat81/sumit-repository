package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PatientFamiliyMembersModel;

import java.util.List;


public class PFamilyMemberAddedAdapter extends RecyclerView.Adapter<PFamilyMemberAddedAdapter.ViewHolder> {


    private List<PatientFamiliyMembersModel.DataBean> pFamilyMemberDetails;

    public PFamilyMemberAddedAdapter(List<PatientFamiliyMembersModel.DataBean> items) {
        pFamilyMemberDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fanily_member_added_adapter_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pFamilyMemberDetails.get(position);
        holder.mName.setText(holder.mItem.getName());
        holder.mEmail.setText(holder.mItem.getEmail());
        holder.mRelations.setText(holder.mItem.getRelation());
        if (holder.mItem.getGender().equals("1")) {
            holder.mGender.setText("Male");

        } else if (holder.mItem.getGender().equals("2")) {
            holder.mGender.setText("Female");
        }

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
        public final TextView mName;
        public final TextView mEmail;
        public final TextView mRelations;
        public final TextView mGender;
        public PatientFamiliyMembersModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.mName);
            mEmail = (TextView) view.findViewById(R.id.mEmail);
            mRelations = (TextView) view.findViewById(R.id.mRelations);
            mGender = (TextView) view.findViewById(R.id.mGender);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
