package com.w8er.android.debug;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.w8er.android.R;
import com.w8er.android.ui.activities.NavBarActivity;
import com.w8er.android.utils.Constants;

//Need to be removed!
public class SetUpIpActivity extends AppCompatActivity {

    private EditText mIp;
    private SharedPreferences wmbPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_ip);
        initViews();
        getSaveIp();
    }

    private void initViews() {
        mIp = findViewById(R.id.eIp);
        Button mStart = findViewById(R.id.start_button);
        mStart.setOnClickListener(view -> startButton());

    }

    public void startButton() {

        String ip = mIp.getText().toString().trim();

        if (ip.isEmpty()) {

            String msg = "IP should not be empty.";
            Toast toast = Toast.makeText(new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        Constants.BASE_URL = "http://" + ip + ":3000/";

        SharedPreferences.Editor editor = wmbPreference.edit();
        editor.putString("ip", ip);
        editor.apply();

        Intent intent = new Intent(this, NavBarActivity.class);

        startActivity(intent);
        finish();
    }

    private void getSaveIp() {
        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String ip = wmbPreference.getString("ip", "");
        mIp.setText(ip);
    }

}
