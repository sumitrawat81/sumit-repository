package com.sibsefid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.sibsefid.e911md.services.RestAdapter;
import com.sibsefid.fragemnts.patient.LoginTypeFragment;
import com.sibsefid.model.patient.CountryBean;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<CountryBean> {

    RestAdapter parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Utils.callFragmentReplace(this, new LoginTypeFragment(), LoginTypeFragment.TAG);
        String selLang=Utils.getuserSeletedLanagueForRequestSend(LoginActivity.this);
        RestAdapter.getAdapter().getCountryList(selLang).enqueue(this);

    }

    @Override
    public void onResponse(Call<CountryBean> call, Response<CountryBean> response) {
        if (LoginActivity.this != null) {
            if (response.body().isSuccess() && response.body().getData() != null && response.body().getData().size() > 0) {
                MyPrefs.saveCountryList(LoginActivity.this, response.body().getData());
            }
        }

    }

    @Override
    public void onFailure(Call<CountryBean> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_LANGUAGE_SWITCH && event.getRepeatCount() == 0) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}