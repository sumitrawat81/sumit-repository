package com.sibsefid.patient.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.DoctorListModel;
import com.sibsefid.util.ExtraConstants;
import com.sibsefid.util.Utils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 3/9/16.
 */
public class PatientSeeDoctorListAdapter extends BaseAdapter {

    private boolean isSchedule=false;
    boolean response = false;
    private LayoutInflater layoutInflater;

    private View.OnClickListener onClickListener;

    private ArrayList<DoctorListModel.DataBean.DoctorListBean> doctorList;
    private ArrayList<DoctorListModel.DataBean.AvailablityListBean> doctorAvailabilityArrayList;

    public PatientSeeDoctorListAdapter(Activity activity, ArrayList<DoctorListModel.DataBean.DoctorListBean> doctorList) {
        layoutInflater = activity.getLayoutInflater();
        this.doctorList = doctorList;

    }

    @Override
    public int getCount() {
        return doctorList.size();
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
            convertView = layoutInflater.inflate(R.layout.row_patient_see_doctorlist, parent, false);
            viewHolder = new ViewHolder(convertView);


            convertView.setTag(R.layout.row_homepage, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.row_homepage);
        }
        viewHolder.clickBtn.setTag(i);
        viewHolder.mScheduleBtn.setTag(i);
        viewHolder.mVisitNowBtn.setTag(i);
        viewHolder.mScheduleBtn.setOnClickListener(onClickListener);
        viewHolder.mVisitNowBtn.setOnClickListener(onClickListener);
        viewHolder.clickBtn.setOnClickListener(onClickListener);

        DoctorListModel.DataBean.DoctorListBean bean = doctorList.get(i);

        if (doctorAvailabilityArrayList != null && doctorAvailabilityArrayList.size() > 0) {
            isSchedule=false;
            response=false;
            for (int j = 0; j < doctorAvailabilityArrayList.size(); j++) {
                if (doctorAvailabilityArrayList.get(j).getDoctorId().equalsIgnoreCase(bean.getDoctorId())) {

                    isSchedule=true;
                    // viewHolder.mVisitNowBtn.setVisibility(View.VISIBLE);
                    DateFormat df = new SimpleDateFormat(ExtraConstants.Date_Format);


                 try {

                      String dateFrom = Utils.convertDateTimeToDate(doctorAvailabilityArrayList.get(j).getDateTimeF(), ExtraConstants.COMINGDATEFORMATFROMSERVER1, ExtraConstants.Date_Format);
                        String dateTo = Utils.convertDateTimeToDate(doctorAvailabilityArrayList.get(j).getDateTimeT(), ExtraConstants.COMINGDATEFORMATFROMSERVER1, ExtraConstants.Date_Format);
                        response = Utils.checkDate(ExtraConstants.Date_Format, df.parse(dateFrom), df.parse(dateTo));
                        //  response = Utils.compareDatesByDateMethods(ExtraConstants.Date_Format,df.parse("19/11/2016"),df.parse("02/01/2017"));

                  } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.e("response", String.valueOf(response));

                }/* else {
                    viewHolder.mScheduleBtn.setVisibility(View.INVISIBLE);
                     viewHolder.mVisitNowBtn.setVisibility(View.INVISIBLE);
                }*/
            }
            if(isSchedule){
                viewHolder.mScheduleBtn.setVisibility(View.VISIBLE);
            }else{
                viewHolder.mScheduleBtn.setVisibility(View.INVISIBLE);
            }

                if (response) {
                   // int vis = setVisibilityVisit(doctorAvailabilityArrayList.get(j));
                    Calendar c = Calendar.getInstance () ;
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                    String time = sdf.format(c.getTime());
                    // System.out.println("============="+Datetime);

                    viewHolder.mVisitNowBtn.setVisibility(View.VISIBLE);
                    viewHolder.mDoctorTime.setVisibility(View.VISIBLE);
                    viewHolder.mDoctorTime.setText("Available Today"+","+time);

                }else{
                    viewHolder.mVisitNowBtn.setVisibility(View.INVISIBLE);
                }


        } else {
            viewHolder.mScheduleBtn.setVisibility(View.INVISIBLE);
            viewHolder.mDoctorTime.setVisibility(View.INVISIBLE);
            //viewHolder.mVisitNowBtn.setVisibility(View.GONE);
        }
        viewHolder.mDoctorName.setText(bean.getName());
        viewHolder.mName.setText(bean.getSpecaility());
        if (!bean.getUser_photo().isEmpty()) {
            try {
                Picasso.with(viewHolder.mImageView.getContext())
                        .load(bean.getUser_photo())
                        .placeholder(R.mipmap.profile_img_infonew)   // optional
                        .error(R.mipmap.profile_img_infonew)    // optional
                        .into(viewHolder.mImageView);
            } catch (Exception e) {
                e.printStackTrace();
                viewHolder.mImageView.setImageResource(R.mipmap.profile_img_infonew);
            }
        }


        return convertView;
    }

    private int setVisibilityVisit(DoctorListModel.DataBean.AvailablityListBean availablityListBean) {

        int visivle = View.GONE;
        try {
            long currentDateTimeInMil = System.currentTimeMillis();
            SimpleDateFormat df = new SimpleDateFormat(ExtraConstants.COMINGDATEFORMATFROMSERVER1);
            long time = df.parse(availablityListBean.getDateTimeT()).getTime();
            if (time - currentDateTimeInMil > 0) {
                visivle = View.VISIBLE;
            }

        } catch (Exception e) {

        }


        return visivle;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ArrayList<DoctorListModel.DataBean.DoctorListBean> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ArrayList<DoctorListModel.DataBean.DoctorListBean> doctorList) {
        this.doctorList = doctorList;
    }

    public ArrayList<DoctorListModel.DataBean.AvailablityListBean> getDoctorAvailabilityArrayList() {
        return doctorAvailabilityArrayList;
    }

    public void setDoctorAvailabilityArrayList(ArrayList<DoctorListModel.DataBean.AvailablityListBean> doctorAvailabilityArrayList) {
        this.doctorAvailabilityArrayList = doctorAvailabilityArrayList;
    }

    private static class ViewHolder {

        private TextView mDoctorName;
        private TextView mName;
        private TextView mDoctorTime;
        private ImageView mImageView;
        private Button mScheduleBtn;
        private Button mVisitNowBtn;
        private LinearLayout clickBtn;

        public ViewHolder(View view) {

            mDoctorName = (TextView) view.findViewById(R.id.mDoctorName);
            mName = (TextView) view.findViewById(R.id.mName);
            mDoctorTime = (TextView) view.findViewById(R.id.mDoctorTime);
            mImageView = (ImageView) view.findViewById(R.id.mImageView);
            mScheduleBtn = (Button) view.findViewById(R.id.mScheduleBtn);
            mVisitNowBtn = (Button) view.findViewById(R.id.mVisitNowBtn);
            clickBtn = (LinearLayout) view.findViewById(R.id.clickBtn);


        }
    }
}
