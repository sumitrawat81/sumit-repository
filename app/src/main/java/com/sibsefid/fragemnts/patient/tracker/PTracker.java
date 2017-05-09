package com.sibsefid.fragemnts.patient.tracker;


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

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PTracker extends Fragment {


    public static final String TAG = "PTracker";
    private TabLayout tabLayout = null;
    private ViewPager mPager;
    private PatientActivity activity;

    public PTracker() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.trackers));
        activity.forwardShowImg();
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_ptracker, container, false);
        initiateView(view);
        setupViewPager(mPager);

        return view;
    }

    private void initiateView(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mPager = (ViewPager) view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_weight)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_blood_oxigen)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_tempture)));

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_blood_pressure)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_ecg)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_ent)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.check_skin)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PCheckWeight(), getResources().getString(R.string.check_weight));
        adapter.addFragment(new PCheckBloodOxigen(), getResources().getString(R.string.check_blood_oxigen));
        adapter.addFragment(new PCheckTemperature(), getResources().getString(R.string.check_tempture));

        adapter.addFragment(new PCheckBloodPressure(), getResources().getString(R.string.check_blood_pressure));
        adapter.addFragment(new PCheckEcg(), getResources().getString(R.string.check_ecg));
        adapter.addFragment(new PCheckEnt(), getResources().getString(R.string.check_ent));
        adapter.addFragment(new PCheckSkin(), getResources().getString(R.string.check_skin));
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(7);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // activity.getmTitle().setText(ApiConstant.LAST_TITLE);
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