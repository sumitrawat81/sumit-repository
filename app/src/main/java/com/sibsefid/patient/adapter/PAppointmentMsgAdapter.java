package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.GetPatientAppointmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PAppointmentMsgAdapter extends RecyclerView.Adapter<PAppointmentMsgAdapter.ViewHolder> {

    private ArrayList<GetPatientAppointmentModel.GetPAppointmentBean> mValues;


    public PAppointmentMsgAdapter(ArrayList<GetPatientAppointmentModel.GetPAppointmentBean> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_appointment_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mDName.setText(holder.mItem.getColumn1());
        holder.mEventTest.setText(holder.mItem.getSubject());
        holder.mWhenTxt.setText(holder.mItem.getAppointDate());
        holder.mMessage.setText(holder.mItem.getReason());

        try {
            Picasso.with(holder.mDName.getContext())
                    .load(holder.mItem.getDoctorimg())
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .into(holder.mImageVeiw);
        } catch (Exception e) {
            e.printStackTrace();
            holder.mImageVeiw.setImageResource(R.mipmap.profile_img_infonew);

        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<GetPatientAppointmentModel.GetPAppointmentBean> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<GetPatientAppointmentModel.GetPAppointmentBean> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDName;
        public final TextView mEventTest;
        public final TextView mWhenTxt;
        public final TextView mMessage;
        public final ImageView mImageVeiw;
        public GetPatientAppointmentModel.GetPAppointmentBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDName = (TextView) view.findViewById(R.id.mDName);
            mEventTest = (TextView) view.findViewById(R.id.mEventTest);
            mWhenTxt = (TextView) view.findViewById(R.id.mWhenTxt);
            mMessage = (TextView) view.findViewById(R.id.mMessage);
            mImageVeiw = (ImageView) view.findViewById(R.id.mImageVeiw);
        }

    }
}
