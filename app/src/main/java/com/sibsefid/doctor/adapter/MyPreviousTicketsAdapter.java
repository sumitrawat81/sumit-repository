package com.sibsefid.doctor.adapter;


/**
 * Created by ubuntu on 6/9/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.DoctorPreviousTicketsModel;

import java.util.ArrayList;


/**
 * Created by ubuntu on 6/9/16.
 */
public class MyPreviousTicketsAdapter extends RecyclerView.Adapter<MyPreviousTicketsAdapter.MyViewHolder> {

    //private List<String> mList_Status = null;
    ArrayList<DoctorPreviousTicketsModel.DataBean> personalNoteDetails;
    private Context mContext = null;
    private int pos = -1;
    private View.OnClickListener clickListener;

    public MyPreviousTicketsAdapter(Context mContext, ArrayList<DoctorPreviousTicketsModel.DataBean> personalNoteDetails) {
        this.mContext = mContext;
        //this.mList_Status = mList_Status;
        this.personalNoteDetails = personalNoteDetails;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mItem = personalNoteDetails.get(position);
        holder.ticketDate.setText(holder.mItem.getSummarydate());
        holder.ticketId.setText(holder.mItem.getTicketNo());
        holder.problemTxt.setText(holder.mItem.getSummary());
        holder.replyText.setText(holder.mItem.getReplayByAdmin());


        if (pos == position && (!holder.mCheckBox.isChecked())) {
            holder.expandableLay.setVisibility(View.VISIBLE);
            holder.mCheckBox.setChecked(true);
        } else {
            holder.expandableLay.setVisibility(View.GONE);
            holder.mCheckBox.setChecked(false);
        }


        holder.mRelClick.setTag(position);
        holder.deleteTicket.setTag(position);

    }

    @Override
    public MyPreviousTicketsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_previous_tickets_adapter_row, null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return personalNoteDetails.size();
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView ticketDate;
        public final TextView ticketId;
        public final TextView problemTxt;
        public final TextView replyText;
        public final ImageView deleteTicket;
        public final LinearLayout expandableLay;
        public final CheckBox mCheckBox;
        public final RelativeLayout mRelClick;
        public DoctorPreviousTicketsModel.DataBean mItem;

        public MyViewHolder(View view) {
            super(view);
            ticketDate = (TextView) view.findViewById(R.id.ticketDate);
            ticketId = (TextView) view.findViewById(R.id.ticketId);
            problemTxt = (TextView) view.findViewById(R.id.problemTxt);
            replyText = (TextView) view.findViewById(R.id.replyText);
            deleteTicket = (ImageView) view.findViewById(R.id.deleteTicket);
            mCheckBox = (CheckBox) view.findViewById(R.id.mCheckBox);
            mRelClick = (RelativeLayout) view.findViewById(R.id.mRelClick);
            deleteTicket.setOnClickListener(clickListener);
            mRelClick.setOnClickListener(clickListener);
            expandableLay = (LinearLayout) view.findViewById(R.id.expandableLay);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + ticketDate.getText() + "'";
        }
    }
}