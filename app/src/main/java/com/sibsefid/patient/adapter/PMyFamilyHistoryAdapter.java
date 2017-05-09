package com.sibsefid.patient.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.e911md.services.SetPHistoryFamilyCallApi;
import com.sibsefid.model.patient.PFamilyHistoryModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.model.patient.RelationshipType;
import com.sibsefid.util.ApiConstant;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class PMyFamilyHistoryAdapter extends RecyclerView.Adapter<PMyFamilyHistoryAdapter.ViewHolder> {

    private ArrayList<PFamilyHistoryModel.PFamilyHistoryBean> mValues;

    private ArrayList<RelationshipType.DataBean> relationarrayList = new ArrayList<>();

    private RelationSpinnerAdapter spinnerAdapter = null;

    private View.OnClickListener onClickListener;

    private SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener saveCallBackListener;

    private Activity activity;

    public PMyFamilyHistoryAdapter(ArrayList<PFamilyHistoryModel.PFamilyHistoryBean> items, Activity activity, SetPHistoryFamilyCallApi.OnFamilyHiSaveCallBackListener saveCallBackListener) {
        mValues = items;
        this.activity = activity;
        spinnerAdapter = new RelationSpinnerAdapter(activity, android.R.layout.simple_dropdown_item_1line, relationarrayList);
        this.saveCallBackListener = saveCallBackListener;


    }

    public PFamilyHistoryModel.PFamilyHistoryBean getFMHIItem(int pos) {

        return mValues.get(pos);

    }

    public void setFMHIItem(int pos, PFamilyHistoryModel.PFamilyHistoryBean bean) {
        mValues.set(pos, bean);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pmy_family_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = mValues.get(position);
        holder.mPMSHName.setText(holder.mItem.getFamilyhistory());

        holder.mPMSHCheckBox.setTag(position);


        holder.mRelationChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (holder.progress_check.getVisibility() == View.GONE) {
                    if (position < 0)
                        return;
                /*      int pos = position;
                    PFamilyHistoryModel.PFamilyHistoryBean bean = mValues.get(pos);
                    PMyHistoryParamsModel model = new PMyHistoryParamsModel();
                    bean.setRelationId(relationarrayList.get(pos).getDetail_code());
                    model.setName(bean.getFamilyhistory());
                    model.setPatientid(bean.getPatientid());
                    model.setAdpterPosition(holder.getAdapterPosition());
                    model.setRowPosition(holder.getAdapterPosition());
                    model.setID((bean.getRelationId()));
                    new SetPHistoryFamilyCallApi(saveCallBackListener).connect(model);
                    bean.setLoading(true);
                    setFMHIItem(pos, bean);
                    notifyItemChanged(holder.getAdapterPosition());*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerAdapter = null;
        spinnerAdapter = new RelationSpinnerAdapter(activity, android.R.layout.simple_dropdown_item_1line, relationarrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.mRelationChooser.setAdapter(spinnerAdapter);
        holder.mRelationChooser.setSelection(mValues.get(position).getPostition());
        holder.mRelationChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                mValues.get(position).setPostition(position1+1);
              //  RelationshipType.DataBean abc=relationarrayList.get(position1);
                if(position1==-1){


                }else {
                    mValues.get(position).setRelationId(relationarrayList.get(position1).getDetail_code());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int relationId = Integer.parseInt(holder.mItem.getRelationId());
        if (relationId > 0) {
            for (int i = 0; i < relationarrayList.size(); i++) {
                int selectedid = Integer.parseInt(relationarrayList.get(i).getDetail_code());
                if (relationId == selectedid) {
                    holder.mRelationChooser.setSelection(i);
                    break;
                }
            }
        }


        if (holder.mItem.getValue().toLowerCase().equalsIgnoreCase("false")) {
            holder.mPMSHCheckBox.setChecked(false);
        } else if (holder.mItem.getValue().toLowerCase().equalsIgnoreCase("true")) {
            holder.mPMSHCheckBox.setChecked(true);
        }

        if (holder.mItem.isLoading()) {
            holder.progress_check.setVisibility(View.VISIBLE);
        } else {
            holder.progress_check.setVisibility(View.GONE);
        }


        holder.mPMSHCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.progress_check.getVisibility() == View.GONE) {
                    CheckBox checkBox = (CheckBox) v;
                    int pos = (Integer) v.getTag();
                    PFamilyHistoryModel.PFamilyHistoryBean bean = mValues.get(pos);
                    PMyHistoryParamsModel model = new PMyHistoryParamsModel();
                    model.setName(bean.getFamilyhistory());
                    model.setPatientid(bean.getPatientid());
                    model.setAdpterPosition(holder.getAdapterPosition());
                    model.setRowPosition(holder.getAdapterPosition());
                    model.setID((bean.getRelationId()));
                    model.setCheck(checkBox.isChecked());
                    model.setType(ApiConstant.FAMILY_HISTORY_TYPE);
                    new SetPHistoryFamilyCallApi(saveCallBackListener).connect(model);
                    bean.setLoading(true);
                    if (checkBox.isChecked()) {
                        bean.setValue("true");
                    } else {
                        bean.setValue("false");
                    }
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

    public ArrayList<PFamilyHistoryModel.PFamilyHistoryBean> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<PFamilyHistoryModel.PFamilyHistoryBean> mValues) {
        this.mValues = mValues;
    }

    public ArrayList<RelationshipType.DataBean> getRelationarrayList() {
        return relationarrayList;
    }

    public void setRelationarrayList(ArrayList<RelationshipType.DataBean> relationarrayList) {
        this.relationarrayList = relationarrayList;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPMSHName;
        public final CheckBox mPMSHCheckBox;
        public final ProgressBar progress_check;
        public PFamilyHistoryModel.PFamilyHistoryBean mItem;
        private MaterialSpinner mRelationChooser;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPMSHName = (TextView) view.findViewById(R.id.mPMSHName);
            mPMSHCheckBox = (CheckBox) view.findViewById(R.id.mPMSHCheckBox);
            progress_check = (ProgressBar) view.findViewById(R.id.progress_check);
            mRelationChooser = (MaterialSpinner) view.findViewById(R.id.mRelationChooser);

        }

    }
}
