package com.sibsefid.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.sibsefid.DoctorActivity;
import com.sibsefid.R;
import com.sibsefid.doctor.adapter.MyMessageTabLayoutAdapter;
import com.sibsefid.interfaces.NotificationReceiverInterface;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

/**
 * Created by ubuntu on 5/9/16.
 */
public class MyMessageFragment extends BaseFragment {

    public static final String TAG = "MyMessageFragment";
    public static NotificationReceiverInterface notificationReceiverInterface;
    private View view;
    private MyMessageTabLayoutAdapter mMyMessageTabLayoutAdapter = null;
    private DoctorActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_appointment));
        activity.forwardShowImg();
        MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        view = inflater.inflate(R.layout.fragment_mymessage, container, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        mMyMessageTabLayoutAdapter = new MyMessageTabLayoutAdapter(getChildFragmentManager());

        mMyMessageTabLayoutAdapter.addFragment(new AppointmentFragment(), getResources().getString(R.string.appointment));
        mMyMessageTabLayoutAdapter.addFragment(new DMessageInbox(), getResources().getString(R.string.inbox));
        mMyMessageTabLayoutAdapter.addFragment(new DMessageSent(), getResources().getString(R.string.sent));
        mMyMessageTabLayoutAdapter.addFragment(new ComposeMessageFragment(), getResources().getString(R.string.compose_msg));
        viewPager.setAdapter(mMyMessageTabLayoutAdapter);
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getmTitle().setText(ApiConstant.LAST_TITLE);
        MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
        notificationReceiverInterface.getNotification();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }
}
