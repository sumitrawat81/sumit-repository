package com.sibsefid.doctor.adapter;

/**
 * Created by ubuntu on 6/9/16.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.e911md.services.AllergiesSaveCallBack;
import com.sibsefid.fragemnts.patient.myhistory.PMyHistory;
import com.sibsefid.model.patient.PMyHistoryModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.patient.adapter.YearIntSpinnerAdapter;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by ubuntu on 6/9/16.
 */
public class PMyHistoryAdapter extends RecyclerView.Adapter<PMyHistoryAdapter.MyViewHolder> {

    private static final int VIEW_TYPE_ALLERGIES = 0;
    private static final int VIEW_TYPE_MEDICAL_HISTORY = 1;
    private static final int VIEW_TYPE_POST_MEDICAL_HISTORY = 2;
    private static final int VIEW_TYPE_SMOKING = 3;
    private static final int VIEW_TYPE_ALCOHOL = 4;
    private static final int VIEW_TYPE_FAMILY = 5;
    private static final int VIEW_TYPE_WAIT_SIZE = 6;
    private static final int VIEW_TYPE_HEIGHT_SIZE = 7;
    private static final int VIEW_TYPE_OTHER_MEDICAL = 8;
    private PMyHistoryModel pMyHistoryList = null;
    private Activity mContext = null;
    private LayoutInflater layoutInflater;
    private PMyHistory myHistory;
    private View.OnClickListener onClickListener;
    private AllergiesSaveCallBack.OnAllergiesSaveCallBackListener allergiesSaveCallBackListener;

    private ArrayList<PMyHistoryModel.DataBean.AllergiesBean> allergiesArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.MedicationBean> medicationArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.SmokingBean> smokeArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.AlcoholBean> alcoholArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.FamilyBean> familyArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.WaistBean> waistArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.HeightBean> heightArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.PastMedicalSurgicalBean> pastMedicalArrayList = new ArrayList<>();
    private ArrayList<PMyHistoryModel.DataBean.OtherMedicalRecordBean> otherMedicalArrayList = new ArrayList<>();

    private ArrayList<Integer> yearArrayList = new ArrayList<>();


    public PMyHistoryAdapter(Activity mContext, PMyHistoryModel mList_Status,
                             AllergiesSaveCallBack.OnAllergiesSaveCallBackListener allergiesSaveCallBackListener, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.pMyHistoryList = mList_Status;
        this.onClickListener = onClickListener;
        layoutInflater = mContext.getLayoutInflater();
        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getAllergies() != null) {
            allergiesArrayList = mList_Status.getData().getAllergies();

        }
        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getMedication() != null)
            medicationArrayList = mList_Status.getData().getMedication();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getSmoking() != null)
            smokeArrayList = mList_Status.getData().getSmoking();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getAlcohol() != null)
            alcoholArrayList = mList_Status.getData().getAlcohol();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getFamily() != null)
            familyArrayList = mList_Status.getData().getFamily();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getWaist() != null)
            waistArrayList = mList_Status.getData().getWaist();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getHeight() != null)
            heightArrayList = mList_Status.getData().getHeight();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getPastMedicalSurgical() != null)
            pastMedicalArrayList = mList_Status.getData().getPastMedicalSurgical();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getOtherMedicalRecord() != null)
            otherMedicalArrayList = mList_Status.getData().getOtherMedicalRecord();

        for (int i = 1980; i < 2016; i++) {
            yearArrayList.add(i);
        }

        myHistory = PMyHistory.getInstance();
        this.allergiesSaveCallBackListener = allergiesSaveCallBackListener;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position == VIEW_TYPE_ALLERGIES) {
            setAllergiesData(holder, position);
        }
        if (position == VIEW_TYPE_MEDICAL_HISTORY) {
            setMedicalHistoryData(holder, position);
        }
        if (position == VIEW_TYPE_SMOKING) {
            setSmokeData(holder, position);
        }
        if (position == VIEW_TYPE_ALCOHOL) {
            setAlcoholData(holder, position);
        }
        if (position == VIEW_TYPE_FAMILY) {
            setFamilyHistoryData(holder, position);
        }
        if (position == VIEW_TYPE_WAIT_SIZE) {
            setWaistData(holder, position);
        }
        if (position == VIEW_TYPE_HEIGHT_SIZE) {
            setHeightData(holder, position);
        } if (position == VIEW_TYPE_OTHER_MEDICAL) {
            setOtherMedicalHistory(holder, position);
        }
        if (position == VIEW_TYPE_POST_MEDICAL_HISTORY) {
            setPastMedicalSargonData(holder, position);
        }

    }


    private void setPastMedicalSargonData(final MyViewHolder holder, final int position) {

        holder.mPMSHparent_layout.removeAllViews();


        holder.mPMSHAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(holder.mPMSHOtherEdt.getText().toString())) {

                    PMyHistoryModel.DataBean.PastMedicalSurgicalBean bean = new PMyHistoryModel.DataBean.PastMedicalSurgicalBean();
                    bean.setNamecondition(holder.mPMSHOtherEdt.getText().toString());
                    bean.setAdded(false);
                    bean.setNamecondition(holder.mPMSHOtherEdt.getText().toString());
                    bean.setLoading(true);
                    bean.setPatientid(myHistory.getDetails().getUser_seq());


                    pastMedicalArrayList.add(bean);
                    notifyItemChanged(holder.getAdapterPosition());

                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setName(bean.getNamecondition());
                    pMyHistoryParamsModel.setPatientid(bean.getPatientid());
                    pMyHistoryParamsModel.setAdpterPosition(pastMedicalArrayList.size() - 1);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setType(ApiConstant.PAST_MEDICAL_HISTORY_TYPE);
                    pMyHistoryParamsModel.setID((bean.getSrno()));
                    pMyHistoryParamsModel.setCheck(false);
                    new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                    setPMSHIItem(pastMedicalArrayList.size() - 1, bean);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });


        for (int i = 0; i < pastMedicalArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_past_medical_surgical, null);
            ImageView mPMSHEditBtn = (ImageView) view.findViewById(R.id.mPMSHEditBtn);
            CheckBox mPMSHCheckBox = (CheckBox) view.findViewById(R.id.mPMSHCheckBox);
            MaterialSpinner mPMSHYearChooser = (MaterialSpinner) view.findViewById(R.id.mPMSHYearChooser);
            ProgressBar mPMSHProgress = (ProgressBar) view.findViewById(R.id.mPMSHProgress);

            mPMSHEditBtn.setTag(i);
            mPMSHEditBtn.setTag(R.id.mFMHIEditBtn, view);

            TextView mPMSHName = (TextView) view.findViewById(R.id.mPMSHName);


            YearIntSpinnerAdapter adapterYear = new YearIntSpinnerAdapter(mContext, android.R.layout.simple_dropdown_item_1line, yearArrayList);
            adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mPMSHYearChooser.setAdapter(adapterYear);

            if ((!TextUtils.isEmpty(pastMedicalArrayList.get(i).getYear()))) {
                for (int j = 0; j < yearArrayList.size(); j++) {
                    int yearv = Integer.valueOf(pastMedicalArrayList.get(i).getYear().trim());
                    int yearSe = yearArrayList.get(j);
                    if (yearv == yearSe) {
                        mPMSHYearChooser.setSelection(j + 1);
                        break;
                    }
                }
            }
            mPMSHYearChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (pastMedicalArrayList.get(i).isLoading()) {
                mPMSHProgress.setVisibility(View.VISIBLE);
                mPMSHEditBtn.setVisibility(View.GONE);
            } else {
                mPMSHProgress.setVisibility(View.GONE);
                mPMSHEditBtn.setVisibility(View.VISIBLE);
            }


            mPMSHName.setText(pastMedicalArrayList.get(i).getNamecondition());
            if (!TextUtils.isEmpty(pastMedicalArrayList.get(i).getIsChecked())) {
                if (pastMedicalArrayList.get(i).getIsChecked().toString().toLowerCase().equalsIgnoreCase("true")) {
                    mPMSHCheckBox.setChecked(true);
                } else {
                    mPMSHCheckBox.setChecked(false);
                }
            }

            mPMSHEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mFMHIEditBtn);
                        CheckBox checkBox = (CheckBox) view1.findViewById(R.id.mPMSHCheckBox);
                        MaterialSpinner mPMSHYearChooser = (MaterialSpinner) view1.findViewById(R.id.mPMSHYearChooser);
                        PMyHistoryModel.DataBean.PastMedicalSurgicalBean bean = getPMSHItem(pos);
                        bean.setNamecondition(((TextView) (view1.findViewById(R.id.mPMSHName))).getText().toString());
                        if (checkBox.isChecked()) {
                            bean.setIsChecked("True");
                        } else {
                            bean.setIsChecked("False");
                        }
                        String year = "-1";
                        int spinSelectedPos = mPMSHYearChooser.getSelectedItemPosition();
                        if (spinSelectedPos > 0) {
                            year = yearArrayList.get(spinSelectedPos - 1).toString();
                        }
                        Log.d("year---", spinSelectedPos + "---------" + year);
                        bean.setYear(year);
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getPMSHItem(pos).getSrno())) {
                            bean.setSrno("0");

                        } else {
                            bean.setSrno(getPMSHItem(pos).getSrno());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getNamecondition());
                        pMyHistoryParamsModel.setPatientid(bean.getPatientid());
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setType(ApiConstant.PAST_MEDICAL_HISTORY_TYPE);
                        pMyHistoryParamsModel.setID((bean.getSrno()));
                        pMyHistoryParamsModel.setYearstarted((bean.getYear()));
                        pMyHistoryParamsModel.setCheck(((CheckBox) (view1.findViewById(R.id.mPMSHCheckBox))).isChecked());
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setPMSHIItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mPMSHparent_layout.addView(view);
        }


    }


    private void setOtherMedicalHistory(final MyViewHolder holder, final int position) {

        holder.mBrowseFile.setOnClickListener(onClickListener);

        holder.mUploadBtn.setOnClickListener(onClickListener);

        holder.mOMHistryparent_layout.removeAllViews();
        if(otherMedicalArrayList.size()>0) {
            for (int i = 0; i < otherMedicalArrayList.size(); i++) {
                View view = layoutInflater.inflate(R.layout.list_more_medical_histry_row, null);

                TextView mOtherFileSub = (TextView) view.findViewById(R.id.subjectOther);
                TextView fileNAmeOther = (TextView) view.findViewById(R.id.fileNAmeOther);
                TextView uploadedFileDate = (TextView) view.findViewById(R.id.uploadedFileDate);
                ImageView actionOther = (ImageView) view.findViewById(R.id.actionOther);
                mOtherFileSub.setText(otherMedicalArrayList.get(i).getSubject());
                fileNAmeOther.setText(otherMedicalArrayList.get(i).getOriginal_filename());
                uploadedFileDate.setText(otherMedicalArrayList.get(i).getUpload_date());
                actionOther.setTag(i);

                actionOther.setOnClickListener(onClickListener);

                holder.mOMHistryparent_layout.addView(view);
            }
            holder.headLayout.setVisibility(View.VISIBLE);
        }else{
            holder.headLayout.setVisibility(View.GONE);
        }

    }




    private void setHeightData(final MyViewHolder holder, final int position) {

        holder.mHGTparent_layout.removeAllViews();
        if (heightArrayList.size() <= 0) {

            holder.mHGTYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mHGTNoCheckBox.setVisibility(View.VISIBLE);
            holder.mHGTYesNoCheckBox.setChecked(false);
            holder.mHGTNoCheckBox.setChecked(true);
            holder.mHGTAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mHGTYesNoCheckBox.setVisibility(View.GONE);
            holder.mHGTNoCheckBox.setVisibility(View.GONE);
            holder.mHGTAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mHGTAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.HeightBean bean = new PMyHistoryModel.DataBean.HeightBean();
                bean.setAdded(true);
                heightArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mHGTYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.HeightBean bean = new PMyHistoryModel.DataBean.HeightBean();
                bean.setAdded(true);
                heightArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        for (int i = 0; i < heightArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_height, null);
            ImageView mWSTDeleteBtn = (ImageView) view.findViewById(R.id.mHGTDeleteBtn);
            ImageView mWSTEditBtn = (ImageView) view.findViewById(R.id.mHGTEditBtn);
            ProgressBar mSMKProgress = (ProgressBar) view.findViewById(R.id.mHGTProgress);
            mWSTDeleteBtn.setTag(i);
            mWSTEditBtn.setTag(i);
            mWSTEditBtn.setTag(R.id.mHGTEditBtn, view);

            EditText mSMKHowMany = (EditText) view.findViewById(R.id.mHGTHowMany);


            if (heightArrayList.get(i).isLoading()) {
                mSMKProgress.setVisibility(View.VISIBLE);
                mWSTEditBtn.setVisibility(View.GONE);
                mWSTDeleteBtn.setVisibility(View.GONE);
            } else {
                mSMKProgress.setVisibility(View.GONE);
                mWSTEditBtn.setVisibility(View.VISIBLE);
                mWSTDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (heightArrayList.get(i).isAdded()) {
                mSMKHowMany.setEnabled(true);

            } else {
                mSMKHowMany.setEnabled(false);
                mSMKHowMany.setText(heightArrayList.get(i).getHeight());

            }


            mWSTDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.HEIGHT_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(7);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(heightArrayList.get(pos).getId());
                    if (!heightArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.HeightBean bean = getHGTItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setHGTItem(pos, bean);
                    } else {
                        heightArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });

            mWSTEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mHGTEditBtn);
                        PMyHistoryModel.DataBean.HeightBean bean = new PMyHistoryModel.DataBean.HeightBean();
                        bean.setHeight(((EditText) (view1.findViewById(R.id.mHGTHowMany))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientId(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getHGTItem(pos).getPatientId())) {
                            bean.setId("0");

                        } else {
                            bean.setId(getHGTItem(pos).getPatientId());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getHeight());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientId()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setType(ApiConstant.HEIGHT_TYPE);
                        pMyHistoryParamsModel.setID((bean.getId()));

                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setHGTItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mHGTparent_layout.addView(view);
        }


    }


    private void setWaistData(final MyViewHolder holder, final int position) {

        holder.mWSTparent_layout.removeAllViews();
        if (waistArrayList.size() <= 0) {

            holder.mWSTYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mWSTNoCheckBox.setVisibility(View.VISIBLE);
            holder.mWSTYesNoCheckBox.setChecked(false);
            holder.mWSTNoCheckBox.setChecked(false);
            holder.mWSTAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mWSTYesNoCheckBox.setVisibility(View.GONE);
            holder.mWSTNoCheckBox.setVisibility(View.GONE);
            holder.mWSTAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mWSTAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.WaistBean bean = new PMyHistoryModel.DataBean.WaistBean();
                bean.setAdded(true);
                waistArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mWSTYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.WaistBean bean = new PMyHistoryModel.DataBean.WaistBean();
                bean.setAdded(true);
                waistArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        for (int i = 0; i < waistArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_waist, null);
            ImageView mWSTDeleteBtn = (ImageView) view.findViewById(R.id.mWSTDeleteBtn);
            ImageView mWSTEditBtn = (ImageView) view.findViewById(R.id.mWSTEditBtn);
            ProgressBar mSMKProgress = (ProgressBar) view.findViewById(R.id.mWSTProgress);
            mWSTDeleteBtn.setTag(i);
            mWSTEditBtn.setTag(i);
            mWSTEditBtn.setTag(R.id.mWSTEditBtn, view);

            EditText mSMKHowMany = (EditText) view.findViewById(R.id.mWSTHowMany);


            if (waistArrayList.get(i).isLoading()) {
                mSMKProgress.setVisibility(View.VISIBLE);
                mWSTEditBtn.setVisibility(View.GONE);
                mWSTDeleteBtn.setVisibility(View.GONE);
            } else {
                mSMKProgress.setVisibility(View.GONE);
                mWSTEditBtn.setVisibility(View.VISIBLE);
                mWSTDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (waistArrayList.get(i).isAdded()) {
                mSMKHowMany.setEnabled(true);

            } else {
                mSMKHowMany.setEnabled(false);
                mSMKHowMany.setText(waistArrayList.get(i).getWaist());

            }


            mWSTDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.WAIST_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(6);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(waistArrayList.get(pos).getId());
                    if (!waistArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.WaistBean bean = getWSTItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setWSTItem(pos, bean);
                    } else {
                        waistArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mWSTEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mWSTEditBtn);
                        PMyHistoryModel.DataBean.WaistBean bean = new PMyHistoryModel.DataBean.WaistBean();
                        bean.setWaist(((EditText) (view1.findViewById(R.id.mWSTHowMany))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getWSTItem(pos).getPatientId())) {
                            bean.setId("0");

                        } else {
                            bean.setId(getWSTItem(pos).getPatientId());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getWaist());

                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setType(ApiConstant.WAIST_TYPE);
                        pMyHistoryParamsModel.setID((bean.getId()));

                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setWSTItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mWSTparent_layout.addView(view);
        }


    }


    private void setFamilyHistoryData(final MyViewHolder holder, final int position) {

        holder.mFMHIparent_layout.removeAllViews();
        if (familyArrayList.size() <= 0) {

            holder.mFMHIYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mFMHINoCheckBox.setVisibility(View.VISIBLE);
            holder.mFMHIYesNoCheckBox.setChecked(false);
            holder.mFMHINoCheckBox.setChecked(true);
            holder.mFMHIQue.setVisibility(View.VISIBLE);
            holder.mFMHIAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mFMHIYesNoCheckBox.setVisibility(View.GONE);
            holder.mFMHINoCheckBox.setVisibility(View.GONE);
            holder.mFMHIQue.setVisibility(View.GONE);
            holder.mFMHIAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mFMHIAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.FamilyBean bean = new PMyHistoryModel.DataBean.FamilyBean();
                bean.setAdded(true);
                familyArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mFMHIYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.FamilyBean bean = new PMyHistoryModel.DataBean.FamilyBean();
                bean.setAdded(true);
                familyArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        for (int i = 0; i < familyArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_familyhistory, null);
            ImageView mFMHIDeleteBtn = (ImageView) view.findViewById(R.id.mFMHIDeleteBtn);
            ImageView mFMHIEditBtn = (ImageView) view.findViewById(R.id.mFMHIEditBtn);
            ProgressBar mSMKProgress = (ProgressBar) view.findViewById(R.id.mFMHIProgress);
            mFMHIDeleteBtn.setTag(i);
            mFMHIEditBtn.setTag(i);
            mFMHIEditBtn.setTag(R.id.mFMHIEditBtn, view);

            EditText mSMKHowMany = (EditText) view.findViewById(R.id.mFMHIHowMany);
            EditText mSMKEnterYear = (EditText) view.findViewById(R.id.mFMHIEnterYear);


            if (familyArrayList.get(i).isLoading()) {
                mSMKProgress.setVisibility(View.VISIBLE);
                mFMHIEditBtn.setVisibility(View.GONE);
                mFMHIDeleteBtn.setVisibility(View.GONE);
            } else {
                mSMKProgress.setVisibility(View.GONE);
                mFMHIEditBtn.setVisibility(View.VISIBLE);
                mFMHIDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (familyArrayList.get(i).isAdded()) {
            } else {
                mSMKHowMany.setText(familyArrayList.get(i).getNamecondition());
                mSMKEnterYear.setText(familyArrayList.get(i).getWhofamily());
            }


            mFMHIDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.WAIST_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(5);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(familyArrayList.get(pos).getSrno());
                    if (!familyArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.FamilyBean bean = getFMHIItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setFMHIItem(pos, bean);
                    } else {
                        familyArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mFMHIEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mFMHIEditBtn);
                        PMyHistoryModel.DataBean.FamilyBean bean = new PMyHistoryModel.DataBean.FamilyBean();
                        bean.setNamecondition(((EditText) (view1.findViewById(R.id.mFMHIHowMany))).getText().toString());
                        bean.setWhofamily(((EditText) (view1.findViewById(R.id.mFMHIEnterYear))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getFMHIItem(pos).getSrno())) {
                            bean.setSrno("0");

                        } else {
                            bean.setSrno(getFMHIItem(pos).getSrno());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getNamecondition());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setWhat_happen(bean.getWhofamily());
                        pMyHistoryParamsModel.setType(ApiConstant.FAMILY_TYPE);
                        pMyHistoryParamsModel.setID((bean.getSrno()));

                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setFMHIItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mFMHIparent_layout.addView(view);
        }


    }


    private void setAlcoholData(final MyViewHolder holder, final int position) {

        holder.mALCparent_layout.removeAllViews();
        if (alcoholArrayList.size() <= 0) {

            holder.mALCYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mALCNoCheckBox.setVisibility(View.VISIBLE);
            holder.mALCYesNoCheckBox.setChecked(false);
            holder.mALCNoCheckBox.setChecked(true);
            holder.mALCQue.setVisibility(View.VISIBLE);
            holder.mALCAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mALCYesNoCheckBox.setVisibility(View.GONE);
            holder.mALCNoCheckBox.setVisibility(View.GONE);
            holder.mALCQue.setVisibility(View.GONE);
            holder.mALCAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mALCAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.AlcoholBean bean = new PMyHistoryModel.DataBean.AlcoholBean();
                bean.setAdded(true);
                alcoholArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mALCYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.AlcoholBean bean = new PMyHistoryModel.DataBean.AlcoholBean();
                bean.setAdded(true);
                alcoholArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        for (int i = 0; i < alcoholArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_alcohol, null);
            ImageView mALCDeleteBtn = (ImageView) view.findViewById(R.id.mALCDeleteBtn);
            ImageView mALCEditBtn = (ImageView) view.findViewById(R.id.mALCEditBtn);
            ProgressBar mSMKProgress = (ProgressBar) view.findViewById(R.id.mALCProgress);
            mALCDeleteBtn.setTag(i);
            mALCEditBtn.setTag(i);
            mALCEditBtn.setTag(R.id.mALCEditBtn, view);

            EditText mSMKHowMany = (EditText) view.findViewById(R.id.mALCHowMany);
            EditText mSMKEnterYear = (EditText) view.findViewById(R.id.mALCEnterYear);


            if (alcoholArrayList.get(i).isLoading()) {
                mSMKProgress.setVisibility(View.VISIBLE);
                mALCEditBtn.setVisibility(View.GONE);
                mALCDeleteBtn.setVisibility(View.GONE);
            } else {
                mSMKProgress.setVisibility(View.GONE);
                mALCEditBtn.setVisibility(View.VISIBLE);
                mALCDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (alcoholArrayList.get(i).isAdded()) {
            } else {
                mSMKHowMany.setText(alcoholArrayList.get(i).getAlcohal_noofdays());
                mSMKEnterYear.setText(alcoholArrayList.get(i).getNo_of_std_drinkday());
            }


            mALCDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.ALCOHOL_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(4);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(alcoholArrayList.get(pos).getSrno());
                    if (!alcoholArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.AlcoholBean bean = getALCItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setALCItem(pos, bean);
                    } else {
                        alcoholArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mALCEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mALCEditBtn);
                        PMyHistoryModel.DataBean.AlcoholBean bean = new PMyHistoryModel.DataBean.AlcoholBean();
                        bean.setAlcohal_noofdays(((EditText) (view1.findViewById(R.id.mALCHowMany))).getText().toString());
                        bean.setNo_of_std_drinkday(((EditText) (view1.findViewById(R.id.mALCEnterYear))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getALCItem(pos).getSrno())) {
                            bean.setSrno("0");

                        } else {
                            bean.setSrno(getALCItem(pos).getSrno());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getAlcohal_noofdays());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setWhat_happen(bean.getNo_of_std_drinkday());
                        pMyHistoryParamsModel.setType(ApiConstant.ALCOHOL_TYPE);
                        pMyHistoryParamsModel.setID((bean.getSrno()));

                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setALCItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mALCparent_layout.addView(view);
        }


    }


    private void setSmokeData(final MyViewHolder holder, final int position) {

        holder.mSMKparent_layout.removeAllViews();
        if (smokeArrayList.size() <= 0) {

            holder.mSMKYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mSMKNoCheckBox.setVisibility(View.VISIBLE);
            holder.mSMKYesNoCheckBox.setChecked(false);
            holder.mSMKNoCheckBox.setChecked(true);
            holder.mSMKQue.setVisibility(View.VISIBLE);
            holder.mSMKAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mSMKYesNoCheckBox.setVisibility(View.GONE);
            holder.mSMKNoCheckBox.setVisibility(View.GONE);
            holder.mSMKQue.setVisibility(View.GONE);
            holder.mSMKAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mSMKAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.SmokingBean bean = new PMyHistoryModel.DataBean.SmokingBean();
                bean.setAdded(true);
                smokeArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mSMKYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.SmokingBean bean = new PMyHistoryModel.DataBean.SmokingBean();
                bean.setAdded(true);
                smokeArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        for (int i = 0; i < smokeArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_smoking, null);
            ImageView mSMKDeleteBtn = (ImageView) view.findViewById(R.id.mSMKDeleteBtn);
            ImageView mSMKEditBtn = (ImageView) view.findViewById(R.id.mSMKEditBtn);
            ProgressBar mSMKProgress = (ProgressBar) view.findViewById(R.id.mSMKProgress);
            mSMKDeleteBtn.setTag(i);
            mSMKEditBtn.setTag(i);
            mSMKEditBtn.setTag(R.id.mALGEditBtn, view);

            EditText mSMKHowMany = (EditText) view.findViewById(R.id.mSMKHowMany);
            EditText mSMKEnterYear = (EditText) view.findViewById(R.id.mSMKEnterYear);


            if (smokeArrayList.get(i).isLoading()) {
                mSMKProgress.setVisibility(View.VISIBLE);
                mSMKEditBtn.setVisibility(View.GONE);
                mSMKDeleteBtn.setVisibility(View.GONE);
            } else {
                mSMKProgress.setVisibility(View.GONE);
                mSMKEditBtn.setVisibility(View.VISIBLE);
                mSMKDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (smokeArrayList.get(i).isAdded()) {
            } else {

                mSMKHowMany.setText(smokeArrayList.get(i).getHowmanyeachday());
                mSMKEnterYear.setText(smokeArrayList.get(i).getYearstarted());
            }


            mSMKDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.SMOKING_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(3);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(smokeArrayList.get(pos).getSrno());
                    if (!smokeArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.SmokingBean bean = getSMKItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setSMKItem(pos, bean);
                    } else {
                        smokeArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mSMKEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mALGEditBtn);
                        PMyHistoryModel.DataBean.SmokingBean bean = new PMyHistoryModel.DataBean.SmokingBean();
                        bean.setHowmanyeachday(((EditText) (view1.findViewById(R.id.mSMKHowMany))).getText().toString());
                        bean.setYearstarted(((EditText) (view1.findViewById(R.id.mSMKEnterYear))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        if (TextUtils.isEmpty(getSMKItem(pos).getSrno())) {
                            bean.setSrno("0");

                        } else {
                            bean.setSrno(getSMKItem(pos).getSrno());
                        }

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setHowmanyeachday(bean.getHowmanyeachday());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setYearstarted(bean.getYearstarted());
                        pMyHistoryParamsModel.setType(ApiConstant.SMOKING_TYPE);
                        pMyHistoryParamsModel.setID((bean.getSrno()));


                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setSMKItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mSMKparent_layout.addView(view);
        }


    }


    private void setMedicalHistoryData(final MyViewHolder holder, final int position) {
        holder.mMDHISparent_layout.removeAllViews();
        if (medicationArrayList.size() <= 0) {

            holder.mMDHISYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mMDHISNoCheckBox.setVisibility(View.VISIBLE);
            holder.mMDHISYesNoCheckBox.setChecked(false);
            holder.mMDHISNoCheckBox.setChecked(true);
            holder.mmMDHISYesNoCheckBoxQue.setVisibility(View.VISIBLE);
            holder.mMDHISAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mMDHISYesNoCheckBox.setVisibility(View.GONE);
            holder.mMDHISNoCheckBox.setVisibility(View.GONE);
            holder.mmMDHISYesNoCheckBoxQue.setVisibility(View.GONE);
            holder.mMDHISAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mMDHISAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.MedicationBean bean = new PMyHistoryModel.DataBean.MedicationBean();
                bean.setAdded(true);
                medicationArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mMDHISYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.MedicationBean bean = new PMyHistoryModel.DataBean.MedicationBean();
                bean.setAdded(true);
                medicationArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        for (int i = 0; i < medicationArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_medical_history, null);
            ImageView mMSHIGDeleteBtn = (ImageView) view.findViewById(R.id.mMSHIGDeleteBtn);
            ImageView mMSHIEditBtn = (ImageView) view.findViewById(R.id.mMSHIEditBtn);
            TextView mMSHIRefill = (TextView) view.findViewById(R.id.mMSHIRefill);
            ProgressBar mMSHIProgress = (ProgressBar) view.findViewById(R.id.mMSHIProgress);
            mMSHIGDeleteBtn.setTag(i);
            mMSHIEditBtn.setTag(i);
            mMSHIRefill.setTag(i);
            mMSHIEditBtn.setTag(R.id.mMSHIEditBtn, view);

            EditText mMSHIName = (EditText) view.findViewById(R.id.mMSHIName);
            EditText mMSHIStrength = (EditText) view.findViewById(R.id.mMSHIStrength);

            EditText mMSHIHowManyTime = (EditText) view.findViewById(R.id.mMSHIHowManyTime);
            EditText mMSHIYearStarted = (EditText) view.findViewById(R.id.mMSHIYearStarted);


            if (medicationArrayList.get(i).isLoading()) {
                mMSHIProgress.setVisibility(View.VISIBLE);
                mMSHIEditBtn.setVisibility(View.GONE);
                mMSHIGDeleteBtn.setVisibility(View.GONE);
            } else {
                mMSHIProgress.setVisibility(View.GONE);
                mMSHIEditBtn.setVisibility(View.VISIBLE);
                mMSHIGDeleteBtn.setVisibility(View.VISIBLE);
            }


            mMSHIName.setText(medicationArrayList.get(i).getName());
            mMSHIStrength.setText(medicationArrayList.get(i).getStrenght());
            mMSHIHowManyTime.setText(medicationArrayList.get(i).getDose());
            mMSHIYearStarted.setText(medicationArrayList.get(i).getYearStarted());


            mMSHIGDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.ALLERGIES_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(9);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(medicationArrayList.get(pos).getSrno());
                    if (!medicationArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.MedicationBean bean = getMDHIItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setMDHIItem(pos, bean);
                    } else {
                        medicationArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());

                    //  medicationArrayList.remove(pos);
                    // notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mMSHIEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mMSHIEditBtn);
                        PMyHistoryModel.DataBean.MedicationBean bean = new PMyHistoryModel.DataBean.MedicationBean();
                        bean.setName(((EditText) (view1.findViewById(R.id.mMSHIName))).getText().toString());
                        bean.setStrenght(((EditText) (view1.findViewById(R.id.mMSHIStrength))).getText().toString());
                        bean.setDose(((EditText) (view1.findViewById(R.id.mMSHIHowManyTime))).getText().toString());
                        bean.setYearStarted(((EditText) (view1.findViewById(R.id.mMSHIYearStarted))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        if (TextUtils.isEmpty(getMDHIItem(pos).getSrno())) {
                            bean.setSrno("0");
                        } else {
                            bean.setSrno(getMDHIItem(pos).getSrno());
                        }
                        bean.setPatientid(myHistory.getDetails().getUser_seq());


                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getName());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setType(ApiConstant.MEDICAL_HISTORY_TYPE);
                        pMyHistoryParamsModel.setHowmanyeachday(bean.getStrenght());
                        pMyHistoryParamsModel.setWhat_happen(bean.getDose());
                        pMyHistoryParamsModel.setYearstarted(bean.getYearStarted());
                        pMyHistoryParamsModel.setID(bean.getSrno());


                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setMDHIItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            mMSHIRefill.setOnClickListener(onClickListener);
           /* mMSHIRefill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(mContext,"Posito"+position,Toast.LENGTH_SHORT).show();
                  *//*  if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mMSHIRefill);
                        PMyHistoryModel.DataBean.MedicationBean bean = new PMyHistoryModel.DataBean.MedicationBean();
                        bean.setName(((EditText) (view1.findViewById(R.id.mMSHIName))).getText().toString());
                        bean.setStrenght(((EditText) (view1.findViewById(R.id.mMSHIStrength))).getText().toString());
                        bean.setDose(((EditText) (view1.findViewById(R.id.mMSHIHowManyTime))).getText().toString());
                        bean.setYearStarted(((EditText) (view1.findViewById(R.id.mMSHIYearStarted))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        if (TextUtils.isEmpty(getMDHIItem(pos).getSrno())) {
                            bean.setSrno("0");
                        } else {
                            bean.setSrno(getMDHIItem(pos).getSrno());
                        }
                        bean.setPatientid(myHistory.getDetails().getUser_seq());


                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        pMyHistoryParamsModel.setName(bean.getName());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setType(ApiConstant.MEDICAL_HISTORY_TYPE);
                        pMyHistoryParamsModel.setHowmanyeachday(bean.getStrenght());
                        pMyHistoryParamsModel.setWhat_happen(bean.getDose());
                        pMyHistoryParamsModel.setYearstarted(bean.getYearStarted());
                        pMyHistoryParamsModel.setID(bean.getSrno());


                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setMDHIItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }
*//*
                }
            });*/

            holder.mMDHISparent_layout.addView(view);
        }
    }


    private void setAllergiesData(final MyViewHolder holder, final int position) {

        holder.parent_layout.removeAllViews();
        if (allergiesArrayList.size() <= 0) {

            holder.mALGYesNoCheckBox.setVisibility(View.VISIBLE);
            holder.mALGNoCheckBox.setVisibility(View.VISIBLE);
            holder.mALGYesNoCheckBox.setChecked(false);
            holder.mALGNoCheckBox.setChecked(true);
            holder.mALGQue.setVisibility(View.VISIBLE);
            holder.mALGAddRowBtn.setVisibility(View.GONE);


        } else {
            holder.mALGYesNoCheckBox.setVisibility(View.GONE);
            holder.mALGNoCheckBox.setVisibility(View.GONE);
            holder.mALGQue.setVisibility(View.GONE);
            holder.mALGAddRowBtn.setVisibility(View.VISIBLE);

        }

        holder.mALGAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PMyHistoryModel.DataBean.AllergiesBean bean = new PMyHistoryModel.DataBean.AllergiesBean();
                bean.setAdded(true);
                allergiesArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.mALGYesNoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PMyHistoryModel.DataBean.AllergiesBean bean = new PMyHistoryModel.DataBean.AllergiesBean();
                bean.setAdded(true);
                allergiesArrayList.add(bean);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        for (int i = 0; i < allergiesArrayList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.sub_row_allergies, null);
            ImageView mALGDeleteBtn = (ImageView) view.findViewById(R.id.mALGDeleteBtn);
            ImageView mALGEditBtn = (ImageView) view.findViewById(R.id.mALGEditBtn);
            ProgressBar mALGProgress = (ProgressBar) view.findViewById(R.id.mALGProgress);
            mALGDeleteBtn.setTag(i);
            mALGEditBtn.setTag(i);
            mALGEditBtn.setTag(R.id.mALGEditBtn, view);

            EditText mName = (EditText) view.findViewById(R.id.mName);
            EditText mWhatHappen = (EditText) view.findViewById(R.id.mWhatHappen);


            if (allergiesArrayList.get(i).isLoading()) {
                mALGProgress.setVisibility(View.VISIBLE);
                mALGEditBtn.setVisibility(View.GONE);
                mALGDeleteBtn.setVisibility(View.GONE);
            } else {
                mALGProgress.setVisibility(View.GONE);
                mALGEditBtn.setVisibility(View.VISIBLE);
                mALGDeleteBtn.setVisibility(View.VISIBLE);
            }

            if (!allergiesArrayList.get(i).isAdded()) {
                mName.setText(allergiesArrayList.get(i).getName());
                mWhatHappen.setText(allergiesArrayList.get(i).getWhat_happen());
            }


            mALGDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                    pMyHistoryParamsModel.setType(ApiConstant.ALLERGIES_DELETE_TYPE);
                    pMyHistoryParamsModel.setDeleted_item_id_type(1);
                    pMyHistoryParamsModel.setAdpterPosition(pos);
                    pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                    pMyHistoryParamsModel.setID(allergiesArrayList.get(pos).getSrno());
                    if (!allergiesArrayList.get(pos).isAdded()) {
                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        PMyHistoryModel.DataBean.AllergiesBean bean = getALGItem(pos);
                        bean.setDeletedLoading(true);
                        bean.setLoading(true);
                        setALGItem(pos, bean);
                    } else {
                        allergiesArrayList.remove(pos);
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
            mALGEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Utils.isDeviceOnline(mContext)) {
                        int pos = (Integer) view.getTag();
                        View view1 = (View) view.getTag(R.id.mALGEditBtn);
                        PMyHistoryModel.DataBean.AllergiesBean bean = new PMyHistoryModel.DataBean.AllergiesBean();
                        bean.setName(((EditText) (view1.findViewById(R.id.mName))).getText().toString());
                        bean.setWhat_happen(((EditText) (view1.findViewById(R.id.mWhatHappen))).getText().toString());
                        bean.setLoading(true);
                        bean.setAdded(false);
                        bean.setPatientid(myHistory.getDetails().getUser_seq());

                        PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();
                        if (TextUtils.isEmpty(getALGItem(pos).getSrno())) {
                            bean.setSrno("0");
                            pMyHistoryParamsModel.setType(ApiConstant.ALLERGIES_TYPE);
                        } else {
                            bean.setSrno(getALGItem(pos).getSrno());
                            pMyHistoryParamsModel.setType(ApiConstant.ALLERGIES_UPDATE_TYPE);
                        }
                        pMyHistoryParamsModel.setName(bean.getName());
                        pMyHistoryParamsModel.setPatientid((bean.getPatientid()));
                        pMyHistoryParamsModel.setAdpterPosition(pos);
                        pMyHistoryParamsModel.setRowPosition(holder.getAdapterPosition());
                        pMyHistoryParamsModel.setWhat_happen(bean.getWhat_happen());
                        pMyHistoryParamsModel.setID(bean.getSrno());


                        new AllergiesSaveCallBack(allergiesSaveCallBackListener).connect(pMyHistoryParamsModel);
                        setALGItem(pos, bean);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.parent_layout.addView(view);
        }


    }

    @Override
    public PMyHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {

            case VIEW_TYPE_ALLERGIES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allergies, parent, false);
                break;
            case VIEW_TYPE_MEDICAL_HISTORY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medical_history, parent, false);
                break;
            case VIEW_TYPE_POST_MEDICAL_HISTORY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_past_medical_history, parent, false);

                break;
            case VIEW_TYPE_SMOKING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_smoking, parent, false);
                break;
            case VIEW_TYPE_ALCOHOL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_alcohol, parent, false);

                break;
            case VIEW_TYPE_FAMILY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_family_medical_history, parent, false);
                break;
            case VIEW_TYPE_WAIT_SIZE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_waist_size, parent, false);
                break;
            case VIEW_TYPE_HEIGHT_SIZE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_height_size, parent, false);
                break;
            case VIEW_TYPE_OTHER_MEDICAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_other_medical_history, parent, false);
                break;

        }

        return new MyViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ALLERGIES;
        } else if (position == 1) {
            return VIEW_TYPE_MEDICAL_HISTORY;
        } else if (position == 2) {
            return VIEW_TYPE_POST_MEDICAL_HISTORY;
        } else if (position == 3) {
            return VIEW_TYPE_SMOKING;
        } else if (position == 4) {
            return VIEW_TYPE_ALCOHOL;
        } else if (position == 5) {
            return VIEW_TYPE_FAMILY;
        } else if (position == 6) {
            return VIEW_TYPE_WAIT_SIZE;
        } else if(position==7){
            return VIEW_TYPE_HEIGHT_SIZE;
        }else{
            return VIEW_TYPE_OTHER_MEDICAL;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public PMyHistoryModel getmList_Status() {
        return pMyHistoryList;
    }

    public void setmList_Status(PMyHistoryModel mList_Status) {
       /* if (mList_Status != null)
            allergiesArrayList = mList_Status.getData().getAllergies();*/
        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getAllergies() != null)
            allergiesArrayList = mList_Status.getData().getAllergies();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getMedication() != null)
            medicationArrayList = mList_Status.getData().getMedication();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getSmoking() != null)
            smokeArrayList = mList_Status.getData().getSmoking();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getAlcohol() != null)
            alcoholArrayList = mList_Status.getData().getAlcohol();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getFamily() != null)
            familyArrayList = mList_Status.getData().getFamily();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getWaist() != null)
            waistArrayList = mList_Status.getData().getWaist();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getHeight() != null)
            heightArrayList = mList_Status.getData().getHeight();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getPastMedicalSurgical() != null)
            pastMedicalArrayList = mList_Status.getData().getPastMedicalSurgical();

        if (mList_Status != null && mList_Status.getData() != null && mList_Status.getData().getOtherMedicalRecord() != null)
            otherMedicalArrayList = mList_Status.getData().getOtherMedicalRecord();

        this.pMyHistoryList = mList_Status;
    }

    public PMyHistoryModel.DataBean.AllergiesBean getALGItem(int position) {

        return allergiesArrayList.get(position);
    }

    public void setALGItem(int pos, PMyHistoryModel.DataBean.AllergiesBean bean) {

        allergiesArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.MedicationBean getMDHIItem(int position) {

        return medicationArrayList.get(position);
    }

    public void setMDHIItem(int pos, PMyHistoryModel.DataBean.MedicationBean bean) {

        medicationArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.SmokingBean getSMKItem(int position) {

        return smokeArrayList.get(position);
    }

    public void setSMKItem(int pos, PMyHistoryModel.DataBean.SmokingBean bean) {

        smokeArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.AlcoholBean getALCItem(int position) {

        return alcoholArrayList.get(position);
    }

    public void setALCItem(int pos, PMyHistoryModel.DataBean.AlcoholBean bean) {

        alcoholArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.WaistBean getWSTItem(int position) {

        return waistArrayList.get(position);
    }

    public void setWSTItem(int pos, PMyHistoryModel.DataBean.WaistBean bean) {

        waistArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.HeightBean getHGTItem(int position) {

        return heightArrayList.get(position);
    }

    public void setHGTItem(int pos, PMyHistoryModel.DataBean.HeightBean bean) {

        heightArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.FamilyBean getFMHIItem(int position) {

        return familyArrayList.get(position);
    }

    public void setFMHIItem(int pos, PMyHistoryModel.DataBean.FamilyBean bean) {

        familyArrayList.set(pos, bean);
    }

    public PMyHistoryModel.DataBean.PastMedicalSurgicalBean getPMSHItem(int position) {

        return pastMedicalArrayList.get(position);
    }

    public void setPMSHIItem(int pos, PMyHistoryModel.DataBean.PastMedicalSurgicalBean bean) {

        pastMedicalArrayList.set(pos, bean);
    }

    public ArrayList<PMyHistoryModel.DataBean.AllergiesBean> getAllergiesArrayList() {
        return allergiesArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.MedicationBean> getMedicationArrayList() {
        return medicationArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.SmokingBean> getSmokeArrayList() {
        return smokeArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.AlcoholBean> getAlcoholArrayList() {
        return alcoholArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.FamilyBean> getFamilyArrayList() {
        return familyArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.WaistBean> getWaistArrayList() {
        return waistArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.HeightBean> getHeightArrayList() {
        return heightArrayList;
    }

    public ArrayList<PMyHistoryModel.DataBean.PastMedicalSurgicalBean> getPastMedicalArrayList() {
        return pastMedicalArrayList;
    }

    public ArrayList<Integer> getYearArrayList() {
        return yearArrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout parent_layout;
        private RadioButton mALGYesNoCheckBox;
        private RadioButton mALGNoCheckBox;
        private ImageView mALGAddRowBtn;
        private TextView mALGQue;

        private LinearLayout mMDHISparent_layout;
        private RadioButton mMDHISYesNoCheckBox;
        private RadioButton mMDHISNoCheckBox;
        private ImageView mMDHISAddRowBtn;
        private TextView mmMDHISYesNoCheckBoxQue;

        private LinearLayout mSMKparent_layout;
        private RadioButton mSMKYesNoCheckBox;
        private RadioButton mSMKNoCheckBox;
        private ImageView mSMKAddRowBtn;
        private TextView mSMKQue;


        private LinearLayout mALCparent_layout;
        private RadioButton mALCYesNoCheckBox;
        private RadioButton mALCNoCheckBox;
        private ImageView mALCAddRowBtn;
        private TextView mALCQue;

        private LinearLayout mFMHIparent_layout;
        private RadioButton mFMHIYesNoCheckBox;
        private RadioButton mFMHINoCheckBox;
        private ImageView mFMHIAddRowBtn;
        private TextView mFMHIQue;

        private LinearLayout mWSTparent_layout;
        private RadioButton mWSTYesNoCheckBox;
        private RadioButton mWSTNoCheckBox;
        private ImageView mWSTAddRowBtn;


        private LinearLayout mHGTparent_layout;
        private RadioButton mHGTYesNoCheckBox;
        private RadioButton mHGTNoCheckBox;
        private ImageView mHGTAddRowBtn;


        private LinearLayout mPMSHparent_layout;
        private LinearLayout mOMHistryparent_layout;
        private EditText mPMSHOtherEdt;
        private ImageView mPMSHAddRowBtn;

        private static EditText mSubject;
        private static TextView mBrowseFile;
        private static ProgressBar progress;
        private Button mUploadBtn;
        private LinearLayout headLayout;

        public MyViewHolder(View view, int viewType) {
            super(view);
            switch (viewType) {
                case VIEW_TYPE_ALLERGIES:
                    parent_layout = (LinearLayout) view.findViewById(R.id.parent_layout);
                    mALGYesNoCheckBox = (RadioButton) view.findViewById(R.id.mALGYesNoCheckBox);
                    mALGNoCheckBox = (RadioButton) view.findViewById(R.id.mALGNoCheckBox);
                    mALGQue = (TextView) view.findViewById(R.id.mALGQue);
                    mALGAddRowBtn = (ImageView) view.findViewById(R.id.mALGAddRowBtn);
                    break;

                case VIEW_TYPE_MEDICAL_HISTORY:
                    mMDHISparent_layout = (LinearLayout) view.findViewById(R.id.mMDHISparent_layout);
                    mMDHISYesNoCheckBox = (RadioButton) view.findViewById(R.id.mMDHISYesNoCheckBox);
                    mMDHISNoCheckBox = (RadioButton) view.findViewById(R.id.mMDHISNoCheckBox);
                    mmMDHISYesNoCheckBoxQue = (TextView) view.findViewById(R.id.mmMDHISYesNoCheckBoxQue);
                    mMDHISAddRowBtn = (ImageView) view.findViewById(R.id.mMDHISAddRowBtn);
                    break;

                case VIEW_TYPE_SMOKING:
                    mSMKparent_layout = (LinearLayout) view.findViewById(R.id.mSMKparent_layout);
                    mSMKYesNoCheckBox = (RadioButton) view.findViewById(R.id.mSMKYesNoCheckBox);
                    mSMKNoCheckBox = (RadioButton) view.findViewById(R.id.mSMKNoCheckBox);
                    mSMKQue = (TextView) view.findViewById(R.id.mSMKQue);
                    mSMKAddRowBtn = (ImageView) view.findViewById(R.id.mSMKAddRowBtn);
                    break;

                case VIEW_TYPE_ALCOHOL:
                    mALCparent_layout = (LinearLayout) view.findViewById(R.id.mALCparent_layout);
                    mALCYesNoCheckBox = (RadioButton) view.findViewById(R.id.mALCYesNoCheckBox);
                    mALCNoCheckBox = (RadioButton) view.findViewById(R.id.mALCNoCheckBox);
                    mALCQue = (TextView) view.findViewById(R.id.mALCQue);
                    mALCAddRowBtn = (ImageView) view.findViewById(R.id.mALCAddRowBtn);
                    break;

                case VIEW_TYPE_FAMILY:
                    mFMHIparent_layout = (LinearLayout) view.findViewById(R.id.mFMHIparent_layout);
                    mFMHIYesNoCheckBox = (RadioButton) view.findViewById(R.id.mFMHIYesNoCheckBox);
                    mFMHINoCheckBox = (RadioButton) view.findViewById(R.id.mFMHINoCheckBox);
                    mFMHIQue = (TextView) view.findViewById(R.id.mFMHIQue);
                    mFMHIAddRowBtn = (ImageView) view.findViewById(R.id.mFMHIAddRowBtn);
                    break;

                case VIEW_TYPE_WAIT_SIZE:
                    mWSTparent_layout = (LinearLayout) view.findViewById(R.id.mWSTparent_layout);
                    mWSTYesNoCheckBox = (RadioButton) view.findViewById(R.id.mWSTYesNoCheckBox);
                    mWSTNoCheckBox = (RadioButton) view.findViewById(R.id.mWSTNoCheckBox);
                    mWSTAddRowBtn = (ImageView) view.findViewById(R.id.mWSTAddRowBtn);
                    break;


                case VIEW_TYPE_HEIGHT_SIZE:
                    mHGTparent_layout = (LinearLayout) view.findViewById(R.id.mHGTparent_layout);
                    mHGTYesNoCheckBox = (RadioButton) view.findViewById(R.id.mHGTYesNoCheckBox);
                    mHGTNoCheckBox = (RadioButton) view.findViewById(R.id.mHGTNoCheckBox);
                    mHGTAddRowBtn = (ImageView) view.findViewById(R.id.mHGTAddRowBtn);

                    break;

                case VIEW_TYPE_POST_MEDICAL_HISTORY:
                    mPMSHparent_layout = (LinearLayout) view.findViewById(R.id.mPMSHparent_layout);

                    mPMSHOtherEdt = (EditText) view.findViewById(R.id.mPMSHOtherEdt);
                    mPMSHAddRowBtn = (ImageView) view.findViewById(R.id.mPMSHAddRowBtn);
                    break;

                case VIEW_TYPE_OTHER_MEDICAL:
                    mOMHistryparent_layout = (LinearLayout) view.findViewById(R.id.mOMHistryparent_layout);
                    mSubject=(EditText)view.findViewById(R.id.mSubject);
                    mBrowseFile=(TextView) view.findViewById(R.id.mBrowseFile);
                    mUploadBtn=(Button) view.findViewById(R.id.mUploadBtn);
                    progress=(ProgressBar)view.findViewById(R.id.progress);
                    headLayout=(LinearLayout)view.findViewById(R.id.headLayout);
                    break;

            }


        }
    }
    public void setBrowserImagePath(String path){
        MyViewHolder.mBrowseFile.setText(path);

    }
    public void setSubject(){
         MyViewHolder.mSubject.setText("");
    }
    public void setUpload(){
         MyViewHolder.mBrowseFile.setText("");
    }
    public String getSubject(){
        return MyViewHolder.mSubject.getText().toString().trim();
    }

    public String getBrowseImg(){
        return MyViewHolder.mBrowseFile.getText().toString().trim();
    }

    public void showProgress(boolean is_true){

        if(is_true){
            MyViewHolder.progress.setVisibility(View.VISIBLE);
        }else{
            MyViewHolder.progress.setVisibility(View.GONE);
        }
    }

}