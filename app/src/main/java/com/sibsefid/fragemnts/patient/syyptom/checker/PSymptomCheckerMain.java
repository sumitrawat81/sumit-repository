package com.sibsefid.fragemnts.patient.syyptom.checker;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sibsefid.PatientActivity;
import com.sibsefid.R;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PSymptomCheckerMain extends Fragment {

    public static final String TAG = "PSymptomCheckerMain";

    private LinearLayout mDetailsBtn;

    private View mPopupView;

    private PatientActivity activity;

    public PSymptomCheckerMain() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PatientActivity) getActivity();
        ApiConstant.LAST_TITLE = activity.getmTitle().getText().toString();
        activity.getmTitle().setText(getActivity().getResources().getString(R.string.symptom_checker));
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

        View view = inflater.inflate(R.layout.fragment_psymptom_checker_main, container, false);

        initiateView(view);

        return view;
    }

    private void initiateView(View view) {

        mPopupView = view.findViewById(R.id.mPopupView);


        mDetailsBtn = (LinearLayout) view.findViewById(R.id.mDetailsBtn);
        mDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(mPopupView);
            }
        });

    }


    private void showPopup(View anchor) {
        final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fullbody_view, null);


        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(popupView);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);


        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //activity.getmTitle().setText(ApiConstant.LAST_TITLE);
    }
}
