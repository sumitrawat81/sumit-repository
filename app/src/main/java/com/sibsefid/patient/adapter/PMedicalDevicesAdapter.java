package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.MedicalDevicesBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PMedicalDevicesAdapter extends RecyclerView.Adapter<PMedicalDevicesAdapter.ViewHolder> {

    private ArrayList<MedicalDevicesBean.MedicalDeviceList> mValues;


    public PMedicalDevicesAdapter(ArrayList<MedicalDevicesBean.MedicalDeviceList> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pmedical_devices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mProductName.setText(holder.mItem.getTitle());
        holder.mProductDes.setText(holder.mItem.getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        try {
            Picasso.with(holder.mProductImage.getContext())
                    .load(holder.mItem.getDeviceimage())
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .into(holder.mProductImage);
        } catch (Exception e) {
            e.printStackTrace();
            holder.mProductImage.setImageResource(R.mipmap.profile_img_infonew);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<MedicalDevicesBean.MedicalDeviceList> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<MedicalDevicesBean.MedicalDeviceList> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductName;
        public final TextView mProductDes;
        public final ImageView mProductImage;
        public MedicalDevicesBean.MedicalDeviceList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductName = (TextView) view.findViewById(R.id.mProductName);
            mProductDes = (TextView) view.findViewById(R.id.mProductDes);
            mProductImage = (ImageView) view.findViewById(R.id.mProductImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mProductDes.getText() + "'";
        }
    }
}
