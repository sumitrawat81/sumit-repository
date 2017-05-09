package com.sibsefid.patient.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.FaqModel;

import java.util.ArrayList;

/**
 * Created by root on 19/9/16.
 */
public class FaqExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<FaqModel.FaqList> faqLists = new ArrayList<>();
    private Activity activity;
    private LayoutInflater inflater;
    private int chageItem = -1;

    public FaqExpandableAdapter(ArrayList<FaqModel.FaqList> faqLists, Activity activity) {
        this.faqLists = faqLists;
        this.activity = activity;
        inflater = activity.getLayoutInflater();

    }

    @Override
    public int getGroupCount() {
        return faqLists.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {


        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        View view1 = inflater.inflate(R.layout.row_faq, null, false);
        TextView mFaqName = (TextView) view1.findViewById(R.id.mFaqName);
        mFaqName.setText(Html.fromHtml(faqLists.get(i).getQuestion()));

        if (faqLists.get(i).isExpand()) {
            mFaqName.setTextColor(mFaqName.getResources().getColor(R.color.red));
            mFaqName.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotate));

        } else {
            mFaqName.setTextColor(mFaqName.getResources().getColor(R.color.colorPrimary));
        }

        return view1;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View view1 = inflater.inflate(R.layout.xpandable_child_rowitem, null, false);
        TextView textview = (TextView) view1.findViewById(R.id.textview_spinner_Row);
        textview.setText(Html.fromHtml(faqLists.get(i).getAnswer()));

        return view1;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public ArrayList<FaqModel.FaqList> getFaqLists() {
        return faqLists;
    }

    public void setFaqLists(ArrayList<FaqModel.FaqList> faqLists) {
        this.faqLists = faqLists;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        faqLists.get(groupPosition).setExpand(true);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        faqLists.get(groupPosition).setExpand(false);
    }
}
