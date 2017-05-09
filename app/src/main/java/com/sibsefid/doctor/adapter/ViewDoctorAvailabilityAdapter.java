package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.DAvalilibilityModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 7/9/16.
 */
public class ViewDoctorAvailabilityAdapter extends RecyclerView.Adapter<ViewDoctorAvailabilityAdapter.MyViewHolder> {

    private ArrayList<DAvalilibilityModel.DAvMessageBean> mList_ViewDoctor = null;

    private View.OnClickListener onClickListener;


    public ViewDoctorAvailabilityAdapter(ArrayList<DAvalilibilityModel.DAvMessageBean> mList_ViewDoctor) {
        this.mList_ViewDoctor = mList_ViewDoctor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_doctor_availability, null);

        return new MyViewHolder(view);
    }

    public void RemoveItem() {


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mItem = mList_ViewDoctor.get(position);
        holder.mDFromDate.setText(holder.mItem.getFromdate());
        holder.mDToDate.setText(holder.mItem.getTodate());
        holder.mDFromTime.setText(holder.mItem.getFromTime());
        holder.mDToTime.setText(holder.mItem.getTotime());
        holder.mDTimeZone.setText(holder.mItem.getTimezone());
        // holder.mCheckBox.setTag(position);
        holder.mDeleteBtn.setTag(position);
        holder.mEditBtn.setTag(position);
        holder.mEditBtn.setOnClickListener(onClickListener);
        holder.mDeleteBtn.setOnClickListener(onClickListener);
       /* holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int pos = (int) v.getTag();
                if (checkBox.isChecked()) {
                    mList_ViewDoctor.get(pos).setCheck(true);
                } else {
                    mList_ViewDoctor.get(pos).setCheck(false);
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mList_ViewDoctor.size();
    }

    public ArrayList<DAvalilibilityModel.DAvMessageBean> getmList_ViewDoctor() {
        return mList_ViewDoctor;
    }

    public void setmList_ViewDoctor(ArrayList<DAvalilibilityModel.DAvMessageBean> mList_ViewDoctor) {
        this.mList_ViewDoctor = mList_ViewDoctor;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mDFromDate;
        private TextView mDToDate;
        private TextView mDFromTime;
        private TextView mDToTime;
        private TextView mDTimeZone;
        // private CheckBox mCheckBox;
        private Button mDeleteBtn;
        private Button mEditBtn;
        private DAvalilibilityModel.DAvMessageBean mItem;


        public MyViewHolder(View view) {
            super(view);

            mDFromDate = (TextView) view.findViewById(R.id.mDFromDate);
            mDToDate = (TextView) view.findViewById(R.id.mDToDate);
            mDFromTime = (TextView) view.findViewById(R.id.mDFromTime);
            mDToTime = (TextView) view.findViewById(R.id.mDToTime);
            mDTimeZone = (TextView) view.findViewById(R.id.mDTimeZone);
            //  mCheckBox=(CheckBox) view.findViewById(R.id.mCheckBox);
            mDeleteBtn = (Button) view.findViewById(R.id.mDeleteBtn);
            mEditBtn = (Button) view.findViewById(R.id.mEditBtn);
        }
    }
}
