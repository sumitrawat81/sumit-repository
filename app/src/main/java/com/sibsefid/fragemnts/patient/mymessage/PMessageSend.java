
package com.sibsefid.fragemnts.patient.mymessage;


        import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.annotation.TargetApi;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.sibsefid.R;
        import com.sibsefid.e911md.services.RestAdapter;
        import com.sibsefid.model.doctor.UserLoginDetails;
        import com.sibsefid.model.patient.PMessageSentModel;
        import com.sibsefid.patient.adapter.PMessageSentMsgAdapter;
        import com.sibsefid.util.MyPrefs;
        import com.sibsefid.util.Utils;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PMessageSend extends Fragment {

    private RecyclerView recyclerView;
    private int mColumnCount = 1;
    private TextView txtNoValue;
    private View mProgressView;

    private List<PMessageSentModel.
                DataBean> messageListBeen = new ArrayList<>();

    private PMessageSentMsgAdapter messageMsgAdapter;
    Callback<PMessageSentModel> pMessageModelCallback = new Callback<PMessageSentModel>() {
        @Override
        public void onResponse(Call<PMessageSentModel> call, Response<PMessageSentModel> response) {
            if (getActivity() != null) {
                showProgress(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                        txtNoValue.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (response.body().getData() != null) {
                            messageListBeen = response.body().getData();
                            messageMsgAdapter.setmValues(messageListBeen);
                            messageMsgAdapter.notifyDataSetChanged();
                        }
                    } else {
                        txtNoValue.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<PMessageSentModel> call, Throwable t) {
            if (getActivity() != null) {
                showProgress(false);
            }
        }
    };
    private UserLoginDetails.LoginDetails details;
    private String current_Time_Zone;

    public PMessageSend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        details = MyPrefs.getLoginDetails(getActivity());
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

        View view = inflater.inflate(R.layout.fragment_pmessage_msg, container, false);
        initiateView(view);

        if (Utils.isDeviceOnline(getActivity())) {
            showProgress(true);
            try {
                current_Time_Zone = Utils.getTimeZoneDifference();
            } catch (Exception e) {

            }
            String selLangToSend=Utils.getuserSeletedLanagueForRequestSend(getActivity());
            RestAdapter.getAdapter().PGet_SentMessageList(details.getUser_seq(),current_Time_Zone,selLangToSend).enqueue(pMessageModelCallback);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_check_your_internet_connection_error), Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void initiateView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgressView = view.findViewById(R.id.progress);
        txtNoValue = (TextView) view.findViewById(R.id.txtNoValue);

        messageMsgAdapter = new PMessageSentMsgAdapter(messageListBeen);
        recyclerView.setAdapter(messageMsgAdapter);


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return Utils.annimationFragment(transit, enter, nextAnim);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
