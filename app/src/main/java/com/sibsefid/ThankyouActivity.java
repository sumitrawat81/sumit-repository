package com.sibsefid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ubuntu on 15/11/16.
 */
public class ThankyouActivity extends Activity implements View.OnClickListener {

    private Button mOkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_note_dialog);

        init();
    }

    private void init() {

        mOkBtn = (Button) findViewById(R.id.mOkBtn);

        mOkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mOkBtn:

                ThankyouActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
