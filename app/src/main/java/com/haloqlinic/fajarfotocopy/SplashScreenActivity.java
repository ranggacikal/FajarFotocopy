package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.HomeKetoActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferencedConfig = new SharedPreferencedConfig(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferencedConfig.getPreferenceIsLogin()){
                    String level = preferencedConfig.getPreferenceLevel();

                    if (level.equals("gudang")){
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }else if (level.equals("kepala toko")){
                        startActivity(new Intent(SplashScreenActivity.this, HomeKetoActivity.class));
                        finish();
                    }
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}