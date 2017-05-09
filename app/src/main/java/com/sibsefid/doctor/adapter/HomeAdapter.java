package com.sibsefid.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.HomeGridModel;

import java.util.List;

/**
 * Created by ubuntu on 3/9/16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<HomeGridModel> mList_HomeGridModel = null;
    private Context mContext;

    public HomeAdapter(Context mContext, List<HomeGridModel> mList_HomeGridModel) {
        this.mContext = mContext;
        this.mList_HomeGridModel = mList_HomeGridModel;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView_Name.setText("" + mList_HomeGridModel.get(position).getName());
        holder.mImageView.setImageResource(mList_HomeGridModel.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mList_HomeGridModel.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from((parent.getContext()).getApplicationContext()).inflate(R.layout.item_home_grid, parent, false);
        return new MyViewHolder(mView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView_Name;

        public MyViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.imageView_HomeImage);
            mTextView_Name = (TextView) view.findViewById(R.id.textView_HomeGridName);
        }
    }
}
