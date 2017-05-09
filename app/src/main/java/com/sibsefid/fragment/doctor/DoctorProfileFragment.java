package com.sibsefid.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by ubuntu on 6/9/16.
 */
public class DoctorProfileFragment extends BaseFragment {

    public static final String TAG = "DoctorProfileFragment";

    private UserLoginDetails.LoginDetails details;
    private DoctorActivity activity;
    private ImageView mProfileImageView;
    private TextView mQualification;
    private TextView mAddress;
    private TextView mAbout;
    private TextView mDoctorName;
    private TextView mDoctorPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.doctor_profile));
        activity.forwardShowImg();
        details = MyPrefs.getLoginDetails(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        initializeComponents(view);
        return view;
    }

    /* Components Initialization Here */
    public void initializeComponents(View view) {

        mProfileImageView = (ImageView) view.findViewById(R.id.mProfileImageView);
        mDoctorName = (TextView) view.findViewById(R.id.mDoctorName);
        mQualification = (TextView) view.findViewById(R.id.mQualification);
        mAddress = (TextView) view.findViewById(R.id.mAddress);
        mAbout = (TextView) view.findViewById(R.id.mAbout);
        mDoctorPrice = (TextView) view.findViewById(R.id.mDoctorPrice);

        mDoctorName.setText(details.getTitle_nm() + " " + details.getFamily_name() + " " + details.getGiven_name());
        mDoctorPrice.setText("Price:" + details.getFeeClient() + "$");
        mQualification.setText(details.getQualifications());
        mAddress.setText(details.getAddress_1() + ", " + details.getAddress_2() + "\n" + details.getCity() + " - " +
                details.getPostcode() + "\n" +
                details.getState_nm() + ", " + details.getCountry());
        mAbout.setText(details.getAboutMe());

        try {
            Picasso.with(getActivity())
                    .load(details.getPhoto())
                    .placeholder(R.mipmap.profile_img_infonew)   // optional
                    .error(R.mipmap.profile_img_infonew)    // optional
                    .into(mProfileImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }
}
