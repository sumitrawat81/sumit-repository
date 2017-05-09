package com.sibsefid.rememberthedate.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sibsefid.R;
import com.sibsefid.rememberthedate.model.UserProfile;
import com.sibsefid.rememberthedate.storage.UserProfileStorage;
import com.zendesk.util.StringUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class HelpFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public HelpFragment() {
        // Intentionally empty
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HelpFragment newInstance(int sectionNumber) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final Context ctx = getActivity().getApplicationContext();


        rootView.findViewById(R.id.fragment_main_btn_chat).setOnClickListener(new AuthOnClickWrapper(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(ctx, CreateProfileActivity.class));
               /* PreChatForm build = new PreChatForm.Builder()
                        .name(PreChatForm.Field.REQUIRED)
                        .email(PreChatForm.Field.REQUIRED)
                      *//*  .phoneNumber(PreChatForm.Field.OPTIONAL)
                        .department(PreChatForm.Field.OPTIONAL)
                        .message(PreChatForm.Field.OPTIONAL)
                       *//*
                        .build();

                ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                        .preChatForm(build)
                        .department("The date");

                ZopimChatActivity.startActivity(getActivity(), department);*/
            }
        }, ctx));

        return rootView;
    }

    class AuthOnClickWrapper implements View.OnClickListener {

        private View.OnClickListener mOnClickListener;
        private UserProfileStorage mUserProfileStorage;
        private Context mContext;

        public AuthOnClickWrapper(View.OnClickListener onClickListener, Context context) {
            this.mOnClickListener = onClickListener;
            this.mUserProfileStorage = new UserProfileStorage(context);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            final UserProfile profile = mUserProfileStorage.getProfile();

            if (StringUtils.hasLength(profile.getEmail())) {
                mOnClickListener.onClick(v);
            } else {
                // showDialog();
                startActivity(new Intent(mContext, CreateProfileActivity.class));
            }
        }

       /* private void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_auth_title)
                    .setPositiveButton(R.string.dialog_auth_positive_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(mContext, CreateProfileActivity.class));
                        }
                    })
                    .setNegativeButton(R.string.dialog_auth_negative_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Intentionally empty
                        }
                    });
            builder.create().show();
        }*/
    }
}