package com.sibsefid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_PHONE_STATE = 2;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 3;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4;
    private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 5;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


        CheckPermissions();
    }

    public void CheckPermissions() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_PHONE_STATE);

        } else if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

        } else if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_AUDIO);


        } else if (ContextCompat.checkSelfPermission(SplashActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);


        } else if (ContextCompat.checkSelfPermission(SplashActivity.this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);


        } else if (ContextCompat.checkSelfPermission(SplashActivity.this,
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);


        } else {
            goToForward();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();
                } else {
                    SplashActivity.this.finish();
                }
            }
            break;

            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();

                } else {
                    SplashActivity.this.finish();
                }
            }

            break;

            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();

                } else {
                    SplashActivity.this.finish();
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_GET_ACCOUNTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();

                } else {
                    SplashActivity.this.finish();
                }

            }
            break;
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();
                } else {
                    SplashActivity.this.finish();
                }
            }
            break;

            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();
                } else {
                    SplashActivity.this.finish();
                }
            }
            break;

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissions();
                } else {
                    SplashActivity.this.finish();
                }
            }
            break;

        }
    }


    private void goToForward() {
      //  if (Utils.getuserSeletedLanague(this).equalsIgnoreCase("") ||Utils.getuserSeletedLanague(this) == null) {

            String lanuage_code = Utils.userLanguage();
            MyPrefs.saveUserSelectedLanguage(this,lanuage_code);

            Utils.chagesLocatiolization(this, lanuage_code);
            Log.d("lanuage code", lanuage_code);

      /*  } else {

            String lanuage_code = Utils.getuserSeletedLanague(this);
//Config.setDefaultLanguage(lanuage_code);
            Log.d("lanuage_set_langaue", lanuage_code);
            Utils.chagesLocatiolization(this, lanuage_code);

        }*/



        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    UserLoginDetails.LoginDetails details = MyPrefs.getLoginDetails(SplashActivity.this);
                    if (details == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    } else if (details.getType() == 1) {
                        startActivity(new Intent(SplashActivity.this, PatientActivity.class));
                        finish();
                    } else if (details.getType() == 2) {
                        startActivity(new Intent(SplashActivity.this, DoctorActivity.class));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }


}
