package com.sibsefid.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.doctor.AppointmentStatusModel;

import java.util.ArrayList;

/**
 * Created by ubuntu on 5/9/16.
 */
public class AppointmentStatusSpAd extends ArrayAdapter<AppointmentStatusModel.DataBean.TableBean> {

    private Context mContext = null;
    private ArrayList<AppointmentStatusModel.DataBean.TableBean> mList = null;

    public AppointmentStatusSpAd(Context context, int resource, ArrayList<AppointmentStatusModel.DataBean.TableBean> mList) {
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
            textView.setText(mList.get(position).getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }
}
