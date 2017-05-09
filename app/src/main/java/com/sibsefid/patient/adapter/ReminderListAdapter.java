package com.sibsefid.patient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.doctor.adapter.SpinnerAdapter;
import com.sibsefid.fragemnts.patient.medecinereminder.SetMedicineReminder;
import com.sibsefid.model.patient.SetMedicineReminderModel;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by ubuntu on 7/9/16.
 */
public class ReminderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //private List<String> mList_MyTransaction = null;
    public ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> pReminderListArray;

    private View.OnClickListener onClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private Context context;
    public MyFilterViewHolder myFilterViewHolder;
    public ReminderListAdapter(Context context,ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> my_transactionDetail,AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.pReminderListArray = my_transactionDetail;
        this.onItemSelectedListener=onItemSelectedListener;
        this.context=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == TYPE_HEADER ) {

            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.filter_layout_reminder, parent, false);
            myFilterViewHolder= new MyFilterViewHolder(v);
            return  myFilterViewHolder ;
        }
        else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reminder_list_adapter, parent, false);
            return new MyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if(holder1 instanceof MyViewHolder) {
            MyViewHolder holder= (MyViewHolder) holder1;

            holder.mSrNo.setText(position + 1 + "");
            holder.mMedicineName.setText(pReminderListArray.get(position).getMedicineName());
            holder.mTimeTaken.setText(pReminderListArray.get(position).getTimeTaken());
            holder.mDrugDose.setText(pReminderListArray.get(position).getDrugDosase());
            holder.mTakenDay.setText(pReminderListArray.get(position).getIsTakenDate());
            holder.mTakenDate.setText(pReminderListArray.get(position).getRemindDate());

            if (pReminderListArray.get(position).getIsTaken().equalsIgnoreCase("true")) {
                holder.takenTick.setVisibility(View.VISIBLE);
                holder.takenBtn.setVisibility(View.GONE);
            } else {
                holder.takenTick.setVisibility(View.GONE);
                holder.takenBtn.setVisibility(View.VISIBLE);
            }
            holder.takenBtn.setTag(position);

            holder.takenBtn.setOnClickListener(onClickListener);
        }
        else {
            MyFilterViewHolder filterViewHolder= (MyFilterViewHolder) holder1;
            filterViewHolder. mDFilter.setOnClickListener(onClickListener);
            filterViewHolder. mMedicineChooser.setTag(context.getResources().getString(R.string.select_medicine));

            filterViewHolder.     mMedicineChooser.setAdapter(new SpinnerAdapter(context, 0, SetMedicineReminder.mList_Title));

            filterViewHolder.   mMedicineChooser.setOnItemSelectedListener(onItemSelectedListener);
        }

    }


    @Override
    public int getItemCount() {
        if (pReminderListArray == null)
            pReminderListArray = new ArrayList<>();
        return pReminderListArray.size();
    }

    public ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> getpMyBillingArrayList() {
        return pReminderListArray;
    }

    public void setpMyBillingArrayList(ArrayList<SetMedicineReminderModel.DataBean.ReminderListBean> pReminderListArray) {
        this.pReminderListArray = pReminderListArray;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView mSrNo;
        public final TextView mMedicineName;
        public final TextView mDrugDose;
        public final TextView mTimeTaken;
        public final TextView mTakenDay;
        public final TextView mTakenDate;
        public final Button takenBtn;
        public final ImageView takenTick;


        public MyViewHolder(View view) {
            super(view);

            mSrNo = (TextView) view.findViewById(R.id.mSrNo);
            mMedicineName = (TextView) view.findViewById(R.id.mMedicineName);
            mDrugDose = (TextView) view.findViewById(R.id.mDrugDose);
            mTimeTaken = (TextView) view.findViewById(R.id.mTimeTaken);
            mTakenDay = (TextView) view.findViewById(R.id.mTakenDay);
            mTakenDate = (TextView) view.findViewById(R.id.mTakenDate);
            takenBtn = (Button) view.findViewById(R.id.takenBtn);
            takenTick = (ImageView) view.findViewById(R.id.takenTick);


        }


    }
    @Override
    public int getItemViewType (int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }
    private boolean isPositionHeader (int position) {
        return position == 0;
    }
    public class MyFilterViewHolder extends RecyclerView.ViewHolder {
        private CheckBox sunCheck, monCheck, tueCheck, wedCheck, thusCheck, friCheck, satCheck;
        private Button mDFilter;
        public MaterialSpinner mMedicineChooser;
        public MyFilterViewHolder(View view) {
            super(view);
            sunCheck = (CheckBox) view.findViewById(R.id.sunCheck);
            monCheck = (CheckBox) view.findViewById(R.id.monCheck);
            tueCheck = (CheckBox) view.findViewById(R.id.tueCheck);
            wedCheck = (CheckBox) view.findViewById(R.id.wedCheck);
            thusCheck = (CheckBox) view.findViewById(R.id.thusCheck);
            friCheck = (CheckBox) view.findViewById(R.id.friCheck);
            satCheck = (CheckBox) view.findViewById(R.id.satCheck);
            mDFilter = (Button) view.findViewById(R.id.mDFilter);
            mMedicineChooser = (MaterialSpinner) view.findViewById(R.id.mMedicineChooser);
        }


    }
}
