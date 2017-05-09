package com.sibsefid.fragemnts.patient;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.sibsefid.LoginActivity;
import com.sibsefid.R;
import com.sibsefid.SpinnerLanguageAdapter;
import com.sibsefid.SplashActivity;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class LoginTypeFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    public static final String TAG = "LoginTypeFragment";
    public static final int LOCALESETTING = 1;
    String langCode;
    private Spinner spinner_language;
    private List<String> mList_language = null;
    LoginActivity mActivity;
    boolean isFirst=true;
    private ArrayMap<String,String> langList;
    public LoginTypeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        langCode=Utils.userLanguage();
        if (Utils.getuserSeletedLanague(getActivity()) .equalsIgnoreCase("fa")) {
            Utils.forceRTLIfSupported(getActivity());
        } else {

            Utils.forceLTRIfSupported(getActivity());
        }
        super.onCreateView (inflater, container, savedInstanceState) ;
        isFirst=true;

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_login_type, container, false);
        mActivity=(LoginActivity)getActivity();
        spinner_language=(Spinner)view.findViewById(R.id.spinner_language);
        setSpinnerAdapter();
        spinner_language.setOnItemSelectedListener(this);
        Button mPatientLoginBtn = (Button) view.findViewById(R.id.mPatientLoginBtn);

        mPatientLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPrefs.saveUser(getActivity(), 1);
                Utils.callFragmentForAdd(((LoginActivity) getActivity()), new LoginFragment(), LoginFragment.TAG);
            }
        });

        Button mProviderLoginBtn = (Button) view.findViewById(R.id.mProviderLoginBtn);
        mProviderLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyPrefs.saveUser(getActivity(), 2);
                Utils.callFragmentForAdd(((LoginActivity) getActivity()), new LoginFragment(), LoginFragment.TAG);

            }
        });


        return view;
    }
    private void setSpinnerAdapter() {

        mList_language = new ArrayList<>();


        String[] monthNames = getActivity().getResources().getStringArray(R.array.languages);
        for (int i = 0; i < monthNames.length; i++) {
            mList_language.add(monthNames[i]);
        }
        spinner_language.setAdapter(new SpinnerLanguageAdapter(getActivity(), 0, mList_language));

        String selLang=MyPrefs.getUserSelectedLanguage(mActivity);
        if(selLang.equalsIgnoreCase("en")){
            selLang="English";
            spinner_language.setSelection(0);
        }/*else if(selLang.equalsIgnoreCase("fr")){
            selLang="French";
            spinner_language.setSelection(1);
        }*/else if(selLang.equalsIgnoreCase("fa")){
            selLang="Persian";
            spinner_language.setSelection(1);
        }
       /* for(int i=0;i<mList_language.size();i++){
            if(mList_language.get(i).equalsIgnoreCase(selLang)){
                spinner_language.setSelection(i);
              //  Utils.chagesLocatiolization(mActivity,selLang);
            }
        }*/

    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String item = parent.getItemAtPosition(position).toString();
        if(position==0){

            langCode="en";
        }/*else if(position==1){
            langCode="fr";
        }*/
        else if(position==1){
            langCode="fa";
        }
        if(Utils.userLanguage().equalsIgnoreCase(langCode)){

            Utils.chagesLocatiolization(mActivity,langCode);
            MyPrefs.saveUserSelectedLanguage(mActivity,langCode);
            if(!isFirst){
                Intent splsh=new Intent(getActivity(), SplashActivity.class);
                splsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                getActivity().startActivity(splsh);

            }
            isFirst=false;
        }else{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
            startActivityForResult(intent,LOCALESETTING);
        }


        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(Utils.userLanguage().equalsIgnoreCase(langCode)){
                Utils.chagesLocatiolization(mActivity,langCode);
                MyPrefs.saveUserSelectedLanguage(mActivity,langCode);
                if(!isFirst){
                    Intent splsh=new Intent(getActivity(), SplashActivity.class);
                    splsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                    getActivity().startActivity(splsh);

                }
                isFirst=false;
            }

        }
    }
}
