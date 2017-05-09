package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PMessageModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 6/9/16.
 */
public class DMessageFragmentAdapter extends RecyclerView.Adapter<DMessageFragmentAdapter.ViewHolder> {

    private ArrayList<PMessageModel.PMessageListBean> mValues;


    public DMessageFragmentAdapter(ArrayList<PMessageModel.PMessageListBean> items) {
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
        holder.drText.setText(mValues.get(position).getSender());
        holder.mMessage.setText(mValues.get(position).getMessage());
        holder.mWhenTxt.setText(mValues.get(position).getDate());
        holder.mSubject.setText(mValues.get(position).getSubject());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       /* try {
            Picasso.with(holder.mImageVeiw.getContext())
                    .load(holder.mItem.getSenderimg())
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

    public ArrayList<PMessageModel.PMessageListBean> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<PMessageModel.PMessageListBean> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView drText;
        public final TextView mMessage;
        public final TextView mWhenTxt;
        public final TextView mSubject;
        public final ImageView mImageVeiw;
        public PMessageModel.PMessageListBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            drText = (TextView) view.findViewById(R.id.drText);
            mMessage = (TextView) view.findViewById(R.id.mMessage);
            mWhenTxt = (TextView) view.findViewById(R.id.mWhenTxt);
            mSubject = (TextView) view.findViewById(R.id.mSubject);
            mImageVeiw = (ImageView) view.findViewById(R.id.mImageVeiw);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessage.getText() + "'";
        }
    }
}