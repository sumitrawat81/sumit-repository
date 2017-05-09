package com.sibsefid.patient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.e911md.services.SaveRequestedData;
import com.sibsefid.model.doctor.DAppointmentListModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.util.ApiConstant;

import java.util.ArrayList;

/**
 * Created by ubuntu on 6/9/16.
 */
public class PFutureAppointmentAdapter extends RecyclerView.Adapter<PFutureAppointmentAdapter.MyViewHolder> {

    private ArrayList<DAppointmentListModel.DAppointmentBean> mDAppointmentList = null;
    private Context mContext = null;
    private SaveRequestedData.OnSaveRequestedDataCallBackListener dataCallBackListener;


    public PFutureAppointmentAdapter(Context mContext, ArrayList<DAppointmentListModel.DAppointmentBean> mList_Status, SaveRequestedData.OnSaveRequestedDataCallBackListener dataCallBackListener) {
        this.mContext = mContext;
        this.mDAppointmentList = mList_Status;
        this.dataCallBackListener = dataCallBackListener;
    }

    public DAppointmentListModel.DAppointmentBean getBeanItem(int position) {
        return mDAppointmentList.get(position);
    }

    public void setBeanItem(int pos, DAppointmentListModel.DAppointmentBean bean) {

        mDAppointmentList.set(pos, bean);
    }

    public void removeItem(int pos) {

        mDAppointmentList.remove(pos);
    }


    @Override
    public void onBindViewHolder(PFutureAppointmentAdapter.MyViewHolder holder, final int position) {

        holder.mAcceptsBtn.setTag(position);
        holder.mItem = mDAppointmentList.get(position);
        holder.mName.setText(holder.mItem.getPDoctorName());

        holder.mDate.setText(mContext.getResources().getString(R.string.on) + " " + holder.mItem.getDate());
        holder.mAtTime.setText(mContext.getResources().getString(R.string.at) + " " + holder.mItem.getTime() + " ");
        holder.mChatType.setText(holder.mItem.getPAppointmentMode());
        holder.mStatus.setText(holder.mItem.getPStatus());
        holder.mReason.setText(holder.mItem.getPReason());

        if (holder.mItem.isLoading()) {
            holder.mProgress.setVisibility(View.VISIBLE);
        } else {
            holder.mProgress.setVisibility(View.GONE);
        }

        holder.mAcceptsBtn.setVisibility(View.GONE);
        holder.mRejectBtn.setVisibility(View.GONE);
        holder.mRejectBtn.setTag(position);
        holder.mRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                DAppointmentListModel.DAppointmentBean bean = mDAppointmentList.get(pos);
                bean.setLoading(true);
                PMyHistoryParamsModel mItem = new PMyHistoryParamsModel();
                mItem.setType(ApiConstant.DOCTOR_ACCEPTS_APPOINTMENT_TYPE);
                mItem.setAppointmentid(bean.getPappointid());
                mItem.setAction("6");
                mItem.setDoctorname(bean.getPDoctorName());
                mItem.setPatientemail(bean.getPemail());
                mItem.setAdpterPosition(pos);
                mItem.setRowPosition(pos);
                new SaveRequestedData(dataCallBackListener).connect(mItem);
                setBeanItem(pos, bean);
                notifyItemChanged(pos);

            }
        });

    }

    @Override
    public PFutureAppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mymessage_appointment, null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {

        if (mDAppointmentList == null)
            mDAppointmentList = new ArrayList<>();

        return mDAppointmentList.size();
    }

    public ArrayList<DAppointmentListModel.DAppointmentBean> getmDAppointmentList() {
        return mDAppointmentList;
    }

    public void setmDAppointmentList(ArrayList<DAppointmentListModel.DAppointmentBean> mDAppointmentList) {
        this.mDAppointmentList = mDAppointmentList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mChatType;
        public TextView mName;
        public TextView mStatus;
        public TextView mDate;
        public TextView mAtTime;
        public TextView mReason;
        public Button mAcceptsBtn;
        public Button mRejectBtn;
        public View mProgress;

        public DAppointmentListModel.DAppointmentBean mItem;

        public MyViewHolder(View view) {
            super(view);

            mChatType = (TextView) view.findViewById(R.id.mChatType);
            mDate = (TextView) view.findViewById(R.id.mDate);
            mStatus = (TextView) view.findViewById(R.id.mStatus);
            mName = (TextView) view.findViewById(R.id.mName);
            mAtTime = (TextView) view.findViewById(R.id.mAtTime);
            mReason = (TextView) view.findViewById(R.id.mReason);
            mAcceptsBtn = (Button) view.findViewById(R.id.mAcceptsBtn);
            mRejectBtn = (Button) view.findViewById(R.id.mRejectBtn);
            mProgress = view.findViewById(R.id.progress);
        }
    }


}
