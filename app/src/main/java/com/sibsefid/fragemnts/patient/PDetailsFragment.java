package com.sibsefid.fragemnts.patient;


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
import com.sibsefid.fragemnts.patient.bookappointment.PPharmacyChange;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class PDetailsFragment extends Fragment {

    public static final String TAG = "PDetailsFragment";
    private TabLayout tabLayout = null;
    private ViewPager mPager;
    private PatientActivity activity;
    private String LAST_TITLE;

    public PDetailsFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.patient_details));
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_patient_details, container, false);
        initiateView(view);
        setupViewPager(mPager);

        return view;
    }

    private void initiateView(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mPager = (ViewPager) view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.account_details)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.billing_information)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.family_information)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pharmacy)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PAccountDetails(), getResources().getString(R.string.account_details));
        adapter.addFragment(new BillingInformation(), getResources().getString(R.string.billing_information));
        adapter.addFragment(new FamilyMembers(), getResources().getString(R.string.family_information));
        adapter.addFragment(new PPharmacyChange(), getResources().getString(R.string.pharmacy));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //activity.getmTitle().setText(LAST_TITLE);
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
