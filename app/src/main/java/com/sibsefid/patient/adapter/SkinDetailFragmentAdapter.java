package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PatientSkinDetailModel;

import java.util.List;


public class SkinDetailFragmentAdapter extends RecyclerView.Adapter<SkinDetailFragmentAdapter.ViewHolder> {


    private List<PatientSkinDetailModel.DataBean> pSkinDetails;

    public SkinDetailFragmentAdapter(List<PatientSkinDetailModel.DataBean> items) {
        pSkinDetails = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ent_detail_fragment_adapter_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = pSkinDetails.get(position);
        holder.mSerialNo.setText(position + 1 + "");
        holder.mSubject.setText(holder.mItem.getSubject());
        holder.mUploadedDate.setText(holder.mItem.getCreated_date());
        // holder.mImage.setImageResource();


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return pSkinDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSerialNo;
        public final TextView mSubject;
        public final TextView mUploadedDate;
        public final ImageView mImage;
        public PatientSkinDetailModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSerialNo = (TextView) view.findViewById(R.id.mSerialNo);
            mSubject = (TextView) view.findViewById(R.id.mSubject);
            mUploadedDate = (TextView) view.findViewById(R.id.mUploadedDate);
            mImage = (ImageView) view.findViewById(R.id.mImage);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSerialNo.getText() + "'";
        }
    }
}
