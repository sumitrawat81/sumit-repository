package com.sibsefid.patient.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.FaqModel;

import java.util.ArrayList;


public class PFaqAdapter extends RecyclerView.Adapter<PFaqAdapter.ViewHolder> {

    private ArrayList<FaqModel.FaqList> mValues;
    private String[] mFaqArray = null;


    public PFaqAdapter(ArrayList<FaqModel.FaqList> mValues, String[] mFaqArray) {
        mValues = mValues;

        this.mFaqArray = mFaqArray;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_faq, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //   holder.mIdView.setText(mValues.get(position).id);
        holder.mFaqName.setText(mFaqArray[position]);

        if (position == 0) {
            holder.mFaqName.setTextColor(holder.mFaqName.getContext().getResources().getColor(R.color.colorAccent));
        } else {
            //  holder.mFaqName.setCompoundDrawables(null,null,null,null);
            holder.mFaqName.setTextColor(holder.mFaqName.getContext().getResources().getColor(R.color.gray));
            holder.mFaqName.setCompoundDrawables(null,
                    null,
                    ContextCompat.getDrawable(holder.mFaqName.getContext(), R.mipmap.faq_arrow),
                    null);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFaqArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFaqName;
        public FaqModel.FaqList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFaqName = (TextView) view.findViewById(R.id.mFaqName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFaqName.getText() + "'";
        }
    }
}
