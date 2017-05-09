package com.sibsefid.rememberthedate.ui;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.sibsefid.R;
import com.sibsefid.rememberthedate.model.UserProfile;
import com.sibsefid.rememberthedate.storage.UserProfileStorage;
import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.JwtIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.util.StringUtils;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.ArrayList;
import java.util.List;


public class CreateProfileActivity extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText phoneText, messageText;
    Spinner department;
    ArrayList<String> onlyDepartmentName_spinnerList = new ArrayList<String>();
    private UserProfileStorage mUserProfileStorage;
    private PlaceholderFragment mPlaceHolderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        onlyDepartmentName_spinnerList.add(0, "Choose a Department");
        onlyDepartmentName_spinnerList.add(1, "business enquiry");
        onlyDepartmentName_spinnerList.add(2, "consultation");
        mUserProfileStorage = new UserProfileStorage(this);
        final FragmentManager fm = getFragmentManager();
        PlaceholderFragment dataFragment = (PlaceholderFragment) fm.findFragmentByTag(PlaceholderFragment.TAG);

        if (dataFragment == null) {
            dataFragment = new PlaceholderFragment();
            fm.beginTransaction().add(R.id.container, dataFragment, PlaceholderFragment.TAG).commit();
            dataFragment.setCurrentBitmap(mUserProfileStorage.getProfile().getAvatar());
        }


        this.mPlaceHolderFragment = dataFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showStoredProfile();
    }

    private void showStoredProfile() {
        UserProfile userProfile = mUserProfileStorage.getProfile();

        ImageButton button = (ImageButton) this.findViewById(R.id.imageButton);
        EditText nameText = (EditText) this.findViewById(R.id.nameText);
        EditText emailText = (EditText) this.findViewById(R.id.emailText);
        EditText phoneText = (EditText) this.findViewById(R.id.phoneText);
        EditText messageText = (EditText) this.findViewById(R.id.messageText);
        Spinner department = (Spinner) this.findViewById(R.id.department);
        ArrayAdapter<String> department_Adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                onlyDepartmentName_spinnerList);
      /*  department_Adapter
                .setDropDownViewResource(R.layout.spinnerdrop_down);*/
        department.setAdapter(department_Adapter);
        department.setSelection(0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();
            }
        });

        if (mPlaceHolderFragment.getCurrentBitmap() != null) {
            button.setImageBitmap(mPlaceHolderFragment.getCurrentBitmap());
        }

        if (!StringUtils.hasLength(nameText.getText().toString())) {
            nameText.setText(userProfile.getName());
        }

        if (!StringUtils.hasLength(emailText.getText().toString())) {
            emailText.setText(userProfile.getEmail());
        }
        if (StringUtils.hasLength(userProfile.getmPhone().toString())) {
            phoneText.setText(userProfile.getmPhone());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1001) {
            Bitmap bitmap = null;

            if (data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (data.getExtras() != null && data.getExtras().get("data") instanceof Bitmap) {
                bitmap = (Bitmap) data.getExtras().get("data");

            }

            if (bitmap != null) {
                mPlaceHolderFragment.setCurrentBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                ((ImageButton) this.findViewById(R.id.imageButton)).setImageBitmap(mPlaceHolderFragment.getCurrentBitmap());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {

            nameText = (EditText) this.findViewById(R.id.nameText);
            emailText = (EditText) this.findViewById(R.id.emailText);
            phoneText = (EditText) this.findViewById(R.id.phoneText);
            department = (Spinner) this.findViewById(R.id.department);
            messageText = (EditText) this.findViewById(R.id.messageText);

            ArrayAdapter<String> department_Adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1,
                    onlyDepartmentName_spinnerList);
          /*  department_Adapter
                    .setDropDownViewResource(R.layout.spinnerdrop_down);*/
            department.setAdapter(department_Adapter);
            department.setSelection(0);

            String PhoneNumber = phoneText.getText().toString().trim();
            if (PhoneNumber.length() > 0) {
                PhoneNumber = PhoneNumber.trim();
            } else {
                PhoneNumber = "";
            }
            String email = emailText.getText().toString();

            if (StringUtils.hasLength(email)) {
                mUserProfileStorage.storeUserProfile(
                        nameText.getText().toString(),
                        email,
                        mPlaceHolderFragment.getCurrentBitmap(), PhoneNumber
                );


                final UserProfile profile = mUserProfileStorage.getProfile();
                if (StringUtils.hasLength(profile.getEmail())) {
                    Logger.i("Identity", "Setting identity");
                    ZendeskConfig.INSTANCE.setIdentity(new JwtIdentity(profile.getEmail()));

                    // Init Zopim Visitor info
                    final VisitorInfo.Builder build = new VisitorInfo.Builder()
                            .email(profile.getEmail());

                    if (StringUtils.hasLength(profile.getName())) {
                        build.name(profile.getName());
                    }

                    if (StringUtils.hasLength(profile.getmPhone())) {
                        build.name(profile.getmPhone());
                    }


                    ZopimChat.setVisitorInfo(build.build());
                }

                String depratment = department.getSelectedItem().toString();
                PreChatForm build = new PreChatForm.Builder()
                        .name(PreChatForm.Field.REQUIRED)
                        .email(PreChatForm.Field.REQUIRED)

                        .build();

                ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                        .preChatForm(build)
                        .department("The date");

                ZopimChatActivity.startActivity(CreateProfileActivity.this, department);
                CreateProfileActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openImageIntent() {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, 1001);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String TAG = "placeholder_fragment_create_profile";

        private Bitmap currentBitmap;

        public PlaceholderFragment() {
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_create_profile, container, false);
        }

        public Bitmap getCurrentBitmap() {
            return currentBitmap;
        }

        public void setCurrentBitmap(final Bitmap currentBitmap) {
            this.currentBitmap = currentBitmap;
        }
    }
}
