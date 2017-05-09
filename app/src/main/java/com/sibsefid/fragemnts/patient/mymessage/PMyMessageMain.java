package com.sibsefid.fragemnts.patient.mymessage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.interfaces.NotificationReceiverInterface;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMyMessageMain extends Fragment {

    public static final String TAG = "PMyMessageMain";
    public static NotificationReceiverInterface notificationReceiverInterface;
    private TabLayout tabLayout = null;
    private ViewPager mPager;
    private PatientActivity activity;

    public PMyMessageMain() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_message));
        activity.forwardShowImg();
        MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_pmy_message_main, container, false);
        initiateView(view);
        setupViewPager(mPager);

        return view;
    }

    private void initiateView(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mPager = (ViewPager) view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.appointment)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.inbox)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sent)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.compose_msg)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PAppointmentMsg(), getResources().getString(R.string.appointment));
        adapter.addFragment(new PMessageInboxMsg(), getResources().getString(R.string.inbox));
        adapter.addFragment(new PMessageSend(), getResources().getString(R.string.sent));
        adapter.addFragment(new PComposeMessageMsg(), getResources().getString(R.string.compose_msg));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyPrefs.saveUserNotiicationCountl(getActivity(), "0");
        notificationReceiverInterface.getNotification();
        // activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}