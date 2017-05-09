package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.MyPatientsModel;
import com.sibsefid.model.doctor.MyTransactionModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 7/9/16.
 */
public class MyTransactionAdapter extends RecyclerView.Adapter<MyTransactionAdapter.MyViewHolder> {

    //private List<String> mList_MyTransaction = null;
    private ArrayList<MyTransactionModel.DataBean> my_transactionDetail;

    public MyTransactionAdapter(ArrayList<MyTransactionModel.DataBean> my_transactionDetail) {
        this.my_transactionDetail = my_transactionDetail;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mytransaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.patientName.setText(my_transactionDetail.get(position).getPatients());
        holder.status.setText(my_transactionDetail.get(position).getPaymentstatus());
        holder.appointmentType.setText(my_transactionDetail.get(position).getAppointmentMode());
        holder.appointmentDate.setText(my_transactionDetail.get(position).getAppointdate());
    }


    /*@Override
    public MyTransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytransaction, null);

        return new MyViewHolder(view);
    }*/

    @Override
    public int getItemCount() {
        return my_transactionDetail.size();
        // return  5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView appointmentDate;
        public final TextView appointmentType;
        public final TextView status;
        public final TextView patientName;

        public MyPatientsModel.DataBean mItem;

        public MyViewHolder(View view) {
            super(view);

            patientName = (TextView) view.findViewById(R.id.patientName);
            status = (TextView) view.findViewById(R.id.status);
            appointmentType = (TextView) view.findViewById(R.id.appointmentType);
            appointmentDate = (TextView) view.findViewById(R.id.appointmentDate);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + patientName.getText() + "'";
        }
    }
}
