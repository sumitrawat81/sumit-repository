package com.sibsefid;

/**
 * Created by ubuntu on 8/2/17.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;


import com.sibsefid.model.LanguageModule;

import java.util.ArrayList;

/**
 * Created by vishnu on 15/9/16.
 */
public class LangageAdpater extends BaseAdapter {


    public ArrayList<LanguageModule.DataBean> datalist;
    private Context _ctx;
    View.OnClickListener onClickListener;


    public LangageAdpater (Context _ctx,
                           ArrayList<LanguageModule.DataBean> datalist,
                           View.OnClickListener onClickListener) {
        this._ctx = _ctx;
// this.card_name=card_name;
        this.datalist = datalist;
        this.onClickListener = onClickListener;


    }



    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
// Inflate the view since it does not exist
            LayoutInflater mInflater = (LayoutInflater) _ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.data_list_langauge, parent, false);
            holder = new Holder();
            holder.txt_langauge_name = (TextView) convertView.findViewById(R.id.txt_langauge_name);


            convertView.setTag(holder);


// Create and save off the holder in the tag so we get quick access to inner fields
// This must be done for performance reasons

// convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txt_langauge_name.setText(datalist.get(position).getName());
        holder.txt_langauge_name.setOnClickListener(onClickListener);
        holder.txt_langauge_name.setTag(position);

/* holder.seleted_langauge.setChecked(datalist.get(position).is_seleted_card());*/

/*
holder.seleted_langauge.setOnClickListener(onClickListener);
holder.seleted_langauge.setTag(position);*/


        return convertView;
    }

    private static class Holder {
        public TextView txt_langauge_name;
        public RadioButton seleted_langauge;

    }
}
