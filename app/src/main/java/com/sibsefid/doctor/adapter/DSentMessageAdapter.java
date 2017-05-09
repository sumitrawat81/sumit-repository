package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PMessageSentModel;

import java.util.List;

/**
 * Created by ubuntu on 13/12/16.
 */
public class DSentMessageAdapter extends RecyclerView.Adapter<DSentMessageAdapter.ViewHolder> {

    private List<PMessageSentModel.DataBean> mValues;


    public DSentMessageAdapter(List<PMessageSentModel.DataBean> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mymessage_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDoctorName.setText(mValues.get(position).getReceiver());
        holder.mSubject.setText(mValues.get(position).getSubject());
        holder.mMessage.setText(mValues.get(position).getMsg());
        holder.mWhenTxt.setText(mValues.get(position).getDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*try {
            Picasso.with(holder.mDoctorName.getContext())
                    .load(holder.mItem.get)
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .into(holder.mImageVeiw);
        } catch (Exception e) {
            e.printStackTrace();
            holder.mImageVeiw.setImageResource(R.mipmap.profile_img_infonew);

        }*/

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<PMessageSentModel.DataBean> getmValues() {
        return mValues;
    }

    public void setmValues(List<PMessageSentModel.DataBean> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDoctorName;
        public final TextView mSubject;
        public final TextView mMessage;
        public final TextView mWhenTxt;
        public final ImageView mImageVeiw;
        public PMessageSentModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDoctorName = (TextView) view.findViewById(R.id.mDoctorName);
            mSubject = (TextView) view.findViewById(R.id.mSubject);
            mMessage = (TextView) view.findViewById(R.id.mMessage);
            mWhenTxt = (TextView) view.findViewById(R.id.mWhenTxt);
            mImageVeiw = (ImageView) view.findViewById(R.id.mImageVeiw);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessage.getText() + "'";
        }
    }
}

