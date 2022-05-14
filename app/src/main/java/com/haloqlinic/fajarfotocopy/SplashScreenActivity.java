package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.driver.DriverMainActivity;
import com.haloqlinic.fajarfotocopy.driver.MainDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;
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
                    finish();

                    if (level.equals("Kepala Gudang") || level.equals("Karyawan Gudang")){
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }else if (level.equals("Kepala Toko")){
                        startActivity(new Intent(SplashScreenActivity.this, MainKetoActivity.class));
                        finish();
                    }else if (level.equals("Karyawan Toko")){
                        startActivity(new Intent(SplashScreenActivity.this, MainKasirActivity.class));
                        finish();
                    }else if (level.equals("Driver")){
                        startActivity(new Intent(SplashScreenActivity.this, DriverMainActivity.class));
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