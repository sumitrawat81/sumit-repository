package com.sibsefid.patient.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibsefid.R;

/**
 * Created by root on 3/9/16.
 */
public class HomePatientGridViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Integer[] mImageArray = {R.mipmap.my_detail, R.mipmap.health, R.mipmap.my_message,
            R.mipmap.doctor, R.mipmap.calendar_home, R.mipmap.billing,
            R.mipmap.result, R.mipmap.trackers, R.mipmap.symptoms, R.mipmap.reminder};


    private String[] nameArray = null;
    private View.OnClickListener onClickListener;


    public HomePatientGridViewAdapter(Activity activity) {
        layoutInflater = activity.getLayoutInflater();
        nameArray = activity.getResources().getStringArray(R.array.namearray);

    }


    @Override
    public int getCount() {
        return mImageArray.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_homepage, parent, false);
            viewHolder = new ViewHolder(convertView);


            convertView.setTag(R.layout.row_homepage, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.row_homepage);
        }

        viewHolder.clickBtn.setTag(i);
        viewHolder.mImageView.setImageResource(mImageArray[i]);
        viewHolder.mName.setText(nameArray[i]);

        viewHolder.clickBtn.setOnClickListener(onClickListener);


        return convertView;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private static class ViewHolder {

        private TextView mName;
        private ImageView mImageView;
        private LinearLayout clickBtn;

        public ViewHolder(View view) {

            mName = (TextView) view.findViewById(R.id.mName);
            mImageView = (ImageView) view.findViewById(R.id.mImageView);
            clickBtn = (LinearLayout) view.findViewById(R.id.clickBtn);


        }
    }
}
