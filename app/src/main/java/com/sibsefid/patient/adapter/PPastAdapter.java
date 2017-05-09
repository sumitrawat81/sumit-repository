package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.DPastConsultModel;

import java.util.ArrayList;


public class PPastAdapter extends RecyclerView.Adapter<PPastAdapter.ViewHolder> {

    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_LIST = 1;
    private View.OnClickListener onClickListener;
    private ArrayList<DPastConsultModel.DPastConsults> mValues = null;

    public PPastAdapter(ArrayList<DPastConsultModel.DPastConsults> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = null;
      /*   switch (viewType) {

            case VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pmy_appointment_top, parent, false);

                break;
            case VIEW_TYPE_LIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pmy_appointment, parent, false);
                break;

        }*/
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_p_my_past_appointment, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);

//        if (position == 0) {
//            return;
//        }

        holder.mDName.setText(holder.mItem.getDoctorName());
        holder.mDAppointmentDate.setText(holder.mItem.getDate());
        holder.mDStatus.setText(holder.mItem.getStatus());
        holder.mChatType.setText(holder.mItem.getAppointmentMode());

        holder.button_CommnunicationNote.setOnClickListener(onClickListener);
        holder.button_CommnunicationNote.setTag(position);
        holder.button_PhysicanOrder.setOnClickListener(onClickListener);
        holder.button_PhysicanOrder.setTag(position);
        holder.button_ClinicalDashboard.setOnClickListener(onClickListener);
        holder.button_ClinicalDashboard.setTag(position);


        if (mValues.get(position).getCommunicationNote().length() > 0) {
            holder.button_CommnunicationNote.setVisibility(View.VISIBLE);
        } else {
            holder.button_CommnunicationNote.setVisibility(View.GONE);
        }
        if (mValues.get(position).getPhysicianorder().length() > 0) {
            holder.button_PhysicanOrder.setVisibility(View.VISIBLE);
        } else {
            holder.button_PhysicanOrder.setVisibility(View.GONE);
        }

    }

   /* @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_LIST;
        }
    }*/

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<DPastConsultModel.DPastConsults> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<DPastConsultModel.DPastConsults> mValues) {
        this.mValues = mValues;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDName;
        public final TextView mDAppointmentDate;
        public final TextView mDStatus;
        public final TextView mChatType;
        public DPastConsultModel.DPastConsults mItem;
        private Button button_CommnunicationNote;
        private Button button_PhysicanOrder;
        private Button button_ClinicalDashboard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDName = (TextView) view.findViewById(R.id.mDName);
            mDAppointmentDate = (TextView) view.findViewById(R.id.mDAppointmentDate);
            mDStatus = (TextView) view.findViewById(R.id.mDStatus);
            mChatType = (TextView) view.findViewById(R.id.mChatType);
            button_CommnunicationNote = (Button) view.findViewById(R.id.button_CommnunicationNote);
            button_PhysicanOrder = (Button) view.findViewById(R.id.button_PhysicanOrder);
            button_ClinicalDashboard = (Button) view.findViewById(R.id.button_ClinicalDashboard);
        }


    }
}
