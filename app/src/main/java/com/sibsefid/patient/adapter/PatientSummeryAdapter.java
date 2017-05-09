package com.sibsefid.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.R;
import com.sibsefid.model.patient.PatientSummeryDetailModel;
import com.sibsefid.patient.adapter.dummy.DummyContent.DummyItem;


public class PatientSummeryAdapter extends RecyclerView.Adapter<PatientSummeryAdapter.ViewHolder> {

    private PatientSummeryDetailModel.DataBean mValues;
    private View.OnClickListener clickListener;

    public PatientSummeryAdapter(PatientSummeryDetailModel.DataBean items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_row_summery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mSummeryMg_2.setText("");

        if (position == 0) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.blood_glucose));

            if (mValues.getBloogSugar().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getBloogSugar().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }
            holder.mSummeryDate.setText(mValues.getBloogSugar().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getBloogSugar().get(0).getBldsgr_value() + " mmol/l");

        } else if (position == 1) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.blood_pressure));
            if (mValues.getBloodPressure().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getBloodPressure().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }
            holder.mSummeryDate.setText(mValues.getBloodPressure().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getBloodPressure().get(0).getSystolic_value() + "/" + mValues.getBloodPressure().get(0).getDiastolic_value() + " mmHg");
            holder.mSummeryMg_2.setText(mValues.getBloodPressure().get(0).getPulse_value() + " bpm");
        } else if (position == 2) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.temperature));
            if (mValues.getTemprature().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getTemprature().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }
            holder.mSummeryDate.setText(mValues.getTemprature().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getTemprature().get(0).getBodytemp_value() + " °c");

        } else if (position == 3) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.blood_oxygen));
            if (mValues.getOxygen().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getOxygen().get(0).getIsShow().equalsIgnoreCase("True")) {

                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }
            holder.mSummeryDate.setText(mValues.getOxygen().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getOxygen().get(0).getOxy_value() + " %");
            holder.mSummeryMg_2.setText(mValues.getOxygen().get(0).getOxy_pulse_value() + " bpm");
        } else if (position == 4) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.weight));
            if (mValues.getWeight().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getWeight().get(0).getIsShow().equalsIgnoreCase("True")) {

                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }

            holder.mSummeryDate.setText(mValues.getWeight().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getWeight().get(0).getWeight_value() + " kg");
        } else if (position == 5) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.bmi));
            if (mValues.getBMI().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }

            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getBMI().get(0).getIsShow().equalsIgnoreCase("True")) {

                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }

            holder.mSummeryDate.setText(mValues.getBMI().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getBMI().get(0).getBMI_value() + " kg/m2");
        } else if (position == 6) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.height));
            if (mValues.getHeight().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");

                return;
            } else {
                holder.eye_img.setVisibility(View.VISIBLE);
                holder.mSummeryDate.setVisibility(View.VISIBLE);
                if (mValues.getHeight().get(0).getIsShow().equalsIgnoreCase("True")) {
                    holder.eye_img.setImageResource(R.mipmap.eye);
                } else {
                    holder.eye_img.setImageResource(R.mipmap.eye_red);
                }

                holder.mSummeryDate.setText(mValues.getHeight().get(0).getDate());
                holder.mSummeryMg_1.setText(mValues.getHeight().get(0).getHeight() + " cm");
            }

        } else if (position == 7) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.waist));
            if (mValues.getWaist().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            } else {
                holder.eye_img.setVisibility(View.VISIBLE);
                holder.mSummeryDate.setVisibility(View.VISIBLE);
                holder.mSummeryMg_1.setVisibility(View.VISIBLE);
                if (mValues.getWaist().get(0).getIsShow().equalsIgnoreCase("True")) {
                    holder.eye_img.setImageResource(R.mipmap.eye);
                } else {
                    holder.eye_img.setImageResource(R.mipmap.eye_red);
                }

                holder.mSummeryDate.setText(mValues.getWaist().get(0).getDate());
                holder.mSummeryMg_1.setText(mValues.getWaist().get(0).getWaist() + " cm");
            }
        } else if (position == 8) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.ecg));
            if (mValues.getECG().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getECG().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }

            holder.mSummeryDate.setText(mValues.getECG().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getECG().get(0).getSubject() + "");
        } else if (position == 9) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.ent));
            if (mValues.getENT().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getENT().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }

            holder.mSummeryDate.setText(mValues.getENT().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getENT().get(0).getSubject() + "");
        } else if (position == 10) {
            holder.mSummeryMTitle.setText(holder.mSummeryDate.getResources().getString(R.string.skin));
            if (mValues.getSKIN().size() == 0) {
                holder.eye_img.setVisibility(View.GONE);
                holder.mSummeryDate.setText("no value");
                holder.mSummeryMg_1.setText("no value");
                return;
            }
            holder.eye_img.setVisibility(View.VISIBLE);
            if (mValues.getSKIN().get(0).getIsShow().equalsIgnoreCase("True")) {
                holder.eye_img.setImageResource(R.mipmap.eye);
            } else {
                holder.eye_img.setImageResource(R.mipmap.eye_red);
            }

            holder.mSummeryDate.setText(mValues.getSKIN().get(0).getDate());
            holder.mSummeryMg_1.setText(mValues.getSKIN().get(0).getSubject() + "");
        }

        holder.eye_img.setTag(position);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public PatientSummeryDetailModel.DataBean getmValues() {
        return mValues;
    }

    public void setmValues(PatientSummeryDetailModel.DataBean mValues) {
        this.mValues = mValues;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSummeryMg_1;
        public final TextView mSummeryMg_2;
        public final TextView mSummeryDate;
        public final TextView mSummeryMTitle;
        public final ImageView eye_img;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSummeryMg_1 = (TextView) view.findViewById(R.id.mSummeryMg_1);
            mSummeryMg_2 = (TextView) view.findViewById(R.id.mSummeryMg_2);
            mSummeryDate = (TextView) view.findViewById(R.id.mSummeryDate);
            mSummeryMTitle = (TextView) view.findViewById(R.id.mSummeryMTitle);
            eye_img = (ImageView) view.findViewById(R.id.eye_img);

            eye_img.setOnClickListener(clickListener);
        }


    }
}