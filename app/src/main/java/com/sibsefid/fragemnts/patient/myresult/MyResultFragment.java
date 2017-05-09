package com.sibsefid.fragemnts.patient.myresult;


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
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.CustomViewPager;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyResultFragment extends Fragment {

    public static final String TAG = "MyResultFragment";
    private TabLayout tabLayout = null;
    private CustomViewPager mPager;
    private PatientActivity activity;

    public MyResultFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.my_result));
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_result, container, false);
        initiateView(view);
        setupViewPager(mPager);

        return view;
    }

    private void initiateView(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mPager = (CustomViewPager) view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.summery)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.blood_pressure)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.blood_glucose)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.blood_oxygen)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.bmi)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.ecg)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.ent)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.report_skin)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.heart_rate)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.height)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.temperature)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.waist_circumference)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.weight)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mPager.setPagingEnabled(false);

    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PatientSummery(), getResources().getString(R.string.summery));
        adapter.addFragment(new PatientBloodPressure(), getResources().getString(R.string.blood_pressure));
        adapter.addFragment(new PatientBloodGlucose(), getResources().getString(R.string.blood_glucose));
        adapter.addFragment(new BloodOxygenFragment(), getResources().getString(R.string.blood_oxygen));
        adapter.addFragment(new BmiDetailFragment(), getResources().getString(R.string.bmi));
        adapter.addFragment(new EcgDetailFragment(), getResources().getString(R.string.ecg));
        adapter.addFragment(new EntDetailFragment(), getResources().getString(R.string.ent));
        adapter.addFragment(new SkinDetailFragment(), getResources().getString(R.string.report_skin));
        adapter.addFragment(new HeartRateDetailFragment(), getResources().getString(R.string.heart_rate));
        adapter.addFragment(new HeightDetailFragment(), getResources().getString(R.string.height));
        adapter.addFragment(new TemperatureDetailFragment(), getResources().getString(R.string.temperature));
        adapter.addFragment(new WaistCircumferenceFragment(), getResources().getString(R.string.waist_circumference));
        adapter.addFragment(new WeightDetailFragment(), getResources().getString(R.string.weight));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(13);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

