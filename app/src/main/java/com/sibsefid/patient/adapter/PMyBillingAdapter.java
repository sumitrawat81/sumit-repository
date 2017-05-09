package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.MyPatientsModel;
import com.sibsefid.model.patient.PMyBillingHistoryModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 7/9/16.
 */
public class PMyBillingAdapter extends RecyclerView.Adapter<PMyBillingAdapter.MyViewHolder> {

    //private List<String> mList_MyTransaction = null;
    private ArrayList<PMyBillingHistoryModel.PMyBillingBean> pMyBillingArrayList;

    private View.OnClickListener onClickListener;

    public PMyBillingAdapter(ArrayList<PMyBillingHistoryModel.PMyBillingBean> my_transactionDetail) {
        this.pMyBillingArrayList = my_transactionDetail;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_p_mybilling, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mSrNo.setText(position + "");
        holder.mAppointmentDate.setText(pMyBillingArrayList.get(position).getDate());
        holder.mPrntRecipt.setText(pMyBillingArrayList.get(position).getAppointId());

        holder.mAppointmentDoctor.setText(pMyBillingArrayList.get(position).getDocterName());
        holder.mAppointmentTime.setText(pMyBillingArrayList.get(position).getTime());
        holder.mAppointmentMode.setText(pMyBillingArrayList.get(position).getAppointmentMode());
        holder.mPrntRecipt.setTag(position);
        holder.mPrntRecipt.setOnClickListener(onClickListener);
    }


    @Override
    public int getItemCount() {
        if (pMyBillingArrayList == null)
            pMyBillingArrayList = new ArrayList<>();
        return pMyBillingArrayList.size();
    }

    public ArrayList<PMyBillingHistoryModel.PMyBillingBean> getpMyBillingArrayList() {
        return pMyBillingArrayList;
    }

    public void setpMyBillingArrayList(ArrayList<PMyBillingHistoryModel.PMyBillingBean> pMyBillingArrayList) {
        this.pMyBillingArrayList = pMyBillingArrayList;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView mSrNo;
        public final TextView mAppointmentDate;
        public final TextView mPrntRecipt;
        public final TextView mAppointmentDoctor;
        public final TextView mAppointmentTime;
        public final TextView mAppointmentMode;


        public MyPatientsModel.DataBean mItem;

        public MyViewHolder(View view) {
            super(view);

            mSrNo = (TextView) view.findViewById(R.id.mSrNo);
            mAppointmentDate = (TextView) view.findViewById(R.id.mAppointmentDate);
            mPrntRecipt = (TextView) view.findViewById(R.id.mPrntRecipt);
            mAppointmentDoctor = (TextView) view.findViewById(R.id.mAppointmentDoctor);
            mAppointmentTime = (TextView) view.findViewById(R.id.mAppointmentTime);
            mAppointmentMode = (TextView) view.findViewById(R.id.mAppointmentMode);


        }

    }
}
