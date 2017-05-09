package com.sibsefid.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.StateList;

import java.util.List;

/**
 * Created by ubuntu on 12/4/17.
 */
public class SpinnerStateAdapter extends ArrayAdapter<StateList.City> {

    private Context mContext = null;
    private List<StateList.City> mList = null;

    public SpinnerStateAdapter(Context context, int resource, List<StateList.City> mList) {
        super(context,resource,mList);

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
            textView.setText(mList.get(position).getDetail_code_nm());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }
}
