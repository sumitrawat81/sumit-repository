package com.sibsefid.doctor.adapter;

/**
 * Created by ubuntu on 6/9/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.GetPersonalNotesModel;

import java.util.ArrayList;


/**
 * Created by ubuntu on 6/9/16.
 */
public class MyPersonalNoteAdapter extends RecyclerView.Adapter<MyPersonalNoteAdapter.MyViewHolder> {

    //private List<String> mList_Status = null;
    ArrayList<GetPersonalNotesModel.DataBean> personalNoteDetails;
    private Context mContext = null;


    private View.OnClickListener clickListener;

    public MyPersonalNoteAdapter(Context mContext, ArrayList<GetPersonalNotesModel.DataBean> personalNoteDetails) {
        this.mContext = mContext;
        //this.mList_Status = mList_Status;
        this.personalNoteDetails = personalNoteDetails;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mItem = personalNoteDetails.get(position);
        holder.patientNameText.setText(holder.mItem.getName());
        holder.appointmentDateText.setText(holder.mItem.getDate() + " " + holder.mItem.getTime());
        holder.statusText.setText(holder.mItem.getStatus());
        holder.noteOnText.setText(holder.mItem.getCreatedDate());
        holder.noteText.setText(holder.mItem.getMessage());

        holder.btnDelete.setTag(position);

    }

    @Override
    public MyPersonalNoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mypersonalnote, null);
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

        public final TextView patientNameText;
        public final TextView appointmentDateText;
        public final TextView statusText;
        public final TextView noteOnText;
        public final TextView noteText;
        public final Button btnDelete;
        public GetPersonalNotesModel.DataBean mItem;

        public MyViewHolder(View view) {
            super(view);
            patientNameText = (TextView) view.findViewById(R.id.patientNameText);
            appointmentDateText = (TextView) view.findViewById(R.id.appointmentDateText);
            statusText = (TextView) view.findViewById(R.id.statusText);
            noteOnText = (TextView) view.findViewById(R.id.noteOnText);
            noteText = (TextView) view.findViewById(R.id.noteText);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(clickListener);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + patientNameText.getText() + "'";
        }
    }
}