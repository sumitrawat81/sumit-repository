package com.sibsefid.doctor.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by root on 16/9/16.
 */
public class SpinnerAdapterNew extends ArrayAdapter<String> {

    public SpinnerAdapterNew(Context context, int textViewResourceId) {
        super(context, textViewResourceId);


    }

    @Override
    public int getCount() {

        int count = super.getCount();

        return count > 0 ? count - 1 : count;


    }


}