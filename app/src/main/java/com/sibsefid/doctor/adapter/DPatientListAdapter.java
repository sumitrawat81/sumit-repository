package com.sibsefid.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.MyPatientsModel;

import java.util.ArrayList;


public class DPatientListAdapter extends RecyclerView.Adapter<DPatientListAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private ArrayList<MyPatientsModel.DataBean> dMyPatientsDetail;


    public DPatientListAdapter(ArrayList<MyPatientsModel.DataBean> items) {
        dMyPatientsDetail = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dpatient_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = dMyPatientsDetail.get(position);
        holder.patientNameText.setText(holder.mItem.getPatientname());

    }

    @Override
    public int getItemCount() {
        return dMyPatientsDetail.size();
        //return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView patientNameText;

        public MyPatientsModel.DataBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            patientNameText = (TextView) view.findViewById(R.id.patientNameText);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + patientNameText.getText() + "'";
        }
    }
}
