package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.BlogListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PBlogAdapter extends RecyclerView.Adapter<PBlogAdapter.ViewHolder> {

    private ArrayList<BlogListModel.BlogList> mValues;
    private View.OnClickListener clickListener;

    public PBlogAdapter(ArrayList<BlogListModel.BlogList> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_blog, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.mProductName.setText(holder.mItem.getTitle());
        holder.mProductSummery.setText(holder.mItem.getSummery());
        holder.mProductDes.setText(Html.fromHtml(holder.mItem.getDescription()));
        holder.txtShowMoreLess.setTag(position);


        if (holder.mItem.isExpand()) {
            holder.mProductSummery.setVisibility(View.GONE);
            holder.mProductDes.setVisibility(View.VISIBLE);
            holder.txtShowMoreLess.setText("View Less");
        } else {
            holder.mProductSummery.setVisibility(View.VISIBLE);
            holder.mProductDes.setVisibility(View.GONE);
            holder.txtShowMoreLess.setText("View More");
        }


        holder.txtShowMoreLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                BlogListModel.BlogList mItem = mValues.get(pos);
                if (mItem.isExpand()) {
                    mItem.setExpand(false);
                } else {
                    mItem.setExpand(true);
                }
                mValues.set(pos, mItem);
                notifyItemChanged(pos);
            }
        });

        try {
            Picasso.with(holder.mProductImage.getContext())
                    .load(holder.mItem.getBlogimage())
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .into(holder.mProductImage);
        } catch (Exception e) {
            e.printStackTrace();
            holder.mProductImage.setImageResource(R.mipmap.profile_img_infonew);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<BlogListModel.BlogList> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<BlogListModel.BlogList> mValues) {
        this.mValues = mValues;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductName;
        public final TextView mProductSummery;
        public final TextView mProductDes;
        public final TextView txtShowMoreLess;
        public final ImageView mProductImage;

        public BlogListModel.BlogList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductName = (TextView) view.findViewById(R.id.mProductName);
            mProductSummery = (TextView) view.findViewById(R.id.mProductSummery);
            mProductDes = (TextView) view.findViewById(R.id.mProductDes);
            txtShowMoreLess = (TextView) view.findViewById(R.id.txtShowMoreLess);
            mProductImage = (ImageView) view.findViewById(R.id.mProductImage);
            //  txtShowMoreLess.setOnClickListener(clickListener);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mProductDes.getText() + "'";
        }
    }
}
