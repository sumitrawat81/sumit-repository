package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.e911md.services.SetPHistoryFamilyCallApi;
import com.sibsefid.model.patient.PLifeStyleModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.util.ApiConstant;

import java.util.ArrayList;


public class PMyLifeStyleAdapter extends RecyclerView.Adapter<PMyLifeStyleAdapter.ViewHolder> {

    private ArrayList<PLifeStyleModel.PLifeStyleBean> mValues;
    private SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener saveCallBackListener;


    public PMyLifeStyleAdapter(ArrayList<PLifeStyleModel.PLifeStyleBean> items, SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener saveCallBackListener) {
        mValues = items;
        this.saveCallBackListener = saveCallBackListener;

    }


    public PLifeStyleModel.PLifeStyleBean getFMHIItem(int pos) {

        return mValues.get(pos);

    }

    public void setFMHIItem(int pos, PLifeStyleModel.PLifeStyleBean bean) {
        mValues.set(pos, bean);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_patient_lifestyle, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mYesRadio.setTag(position);
        holder.mNoRadio.setTag(position);
        holder.mQuestion.setText(holder.mItem.getQus());
        if (holder.mItem.getPatientInInt() == 1) {
            holder.mYesRadio.setChecked(true);
        } else if (holder.mItem.getPatientInInt() == 0) {
            holder.mNoRadio.setChecked(true);
        }

        if (holder.mItem.isLoading()) {
            holder.progress_check.setVisibility(View.VISIBLE);
        } else {
            holder.progress_check.setVisibility(View.GONE);
        }


        holder.mYesRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton radioButton = (RadioButton) v;
                int pos = (Integer) v.getTag();
                PLifeStyleModel.PLifeStyleBean bean = mValues.get(pos);
                if (radioButton.isChecked()) {
                    bean.setPatientans("1");
                    PMyHistoryParamsModel model = new PMyHistoryParamsModel();
                    model.setPatientid(com.sibsefid.fragemnts.patient.myhistory.PMyLifeStyle.ID);
                    model.setAdpterPosition(holder.getAdapterPosition());
                    model.setRowPosition(holder.getAdapterPosition());
                    model.setID(bean.getId());
                    model.setType(ApiConstant.LIFE_STYLE_TYPE);
                    model.setDeleted_item_id_type(Integer.parseInt(bean.getPatientans()));
                    new SetPHistoryFamilyCallApi(saveCallBackListener).connect(model);
                    bean.setLoading(true);
                    setFMHIItem(pos, bean);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });

        holder.mNoRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton) v;

                int pos = (Integer) v.getTag();
                PLifeStyleModel.PLifeStyleBean bean = mValues.get(pos);
                if (radioButton.isChecked()) {
                    bean.setPatientans("0");
                    PMyHistoryParamsModel model = new PMyHistoryParamsModel();
                    model.setPatientid(com.sibsefid.fragemnts.patient.myhistory.PMyLifeStyle.ID);
                    model.setAdpterPosition(holder.getAdapterPosition());
                    model.setRowPosition(holder.getAdapterPosition());
                    model.setID(bean.getId());
                    model.setType(ApiConstant.LIFE_STYLE_TYPE);
                    model.setDeleted_item_id_type(Integer.parseInt(bean.getPatientans()));
                    new SetPHistoryFamilyCallApi(saveCallBackListener).connect(model);
                    bean.setLoading(true);
                    setFMHIItem(pos, bean);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<PLifeStyleModel.PLifeStyleBean> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<PLifeStyleModel.PLifeStyleBean> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mQuestion;
        public final RadioButton mYesRadio;
        public final RadioButton mNoRadio;
        public final ProgressBar progress_check;

        public PLifeStyleModel.PLifeStyleBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mQuestion = (TextView) view.findViewById(R.id.mQuestion);
            mYesRadio = (RadioButton) view.findViewById(R.id.mYesRadio);
            mNoRadio = (RadioButton) view.findViewById(R.id.mNoRadio);
            progress_check = (ProgressBar) view.findViewById(R.id.progress_check);

        }

    }
}
