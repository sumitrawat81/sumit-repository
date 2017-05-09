package com.sibsefid.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sibsefid.R;

import java.util.List;

/**
 * Created by ubuntu on 5/9/16.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext = null;
    private List<String> mList = null;

    public SpinnerAdapter(Context context, int resource, List<String> mList) {
        super(context, resource, mList);
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /* custom method for get View */
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_row, null);
        TextView textView = (TextView) view.findViewById(R.id.textview_spinner_Row);
        try {
            textView.setText(mList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }
}
