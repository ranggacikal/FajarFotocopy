package com.haloqlinic.fajarfotocopy.gudang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.fajarfotocopy.Constants;
import com.haloqlinic.fajarfotocopy.MyNotificationManager;
import com.haloqlinic.fajarfotocopy.MyService;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.InformasiGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.KirimBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.MintaBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeFragment homeFragment;
//    private InformasiGudangFragment informasiGudangFragment;
    private KirimBarangFragment kirimBarangFragment;
    private ProfileFragment profileFragment;
    private MintaBarangFragment mintaBarangFragment;


    private SharedPreferencedConfig preferencedConfig;

    private Handler handler = new Handler();

    // Define the code block to be executed
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startService(new Intent(MainActivity.this, MyService.class));

            // Repeat every 2 seconds
            handler.postDelayed(runnable, 300000);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.post(runnable);

        preferencedConfig = new SharedPreferencedConfig(MainActivity.this);

        Log.d("cekTokenMain", "onCreate: "+preferencedConfig.getPreferenceTokenFcm());

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
//            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
////
////        /*
////         * Displaying a notification locally
////         */
//        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");

        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }

    private void bindWidgetsWithAnEvent() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void setCurrentTabFragment(int position) {

        switch (position)
        {
            case 0 :
                replaceFragment(homeFragment);
                break;
//            case 1 :
//                replaceFragment(informasiGudangFragment);
//                break;
            case 1 :
                replaceFragment(kirimBarangFragment);
                break;
            case 2 :
                replaceFragment(mintaBarangFragment);
                break;
            case 3 :
                replaceFragment(profileFragment);
                break;
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home_gudang, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupTabLayout() {

        homeFragment = new HomeFragment();
//        informasiGudangFragment = new InformasiGudangFragment();
        profileFragment = new ProfileFragment();
        kirimBarangFragment = new KirimBarangFragment();
        mintaBarangFragment = new MintaBarangFragment();


        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
//        tabLayout.addTab(tabLayout.newTab().setText("Informasi").setIcon(R.drawable.informasi_ic));
        tabLayout.addTab(tabLayout.newTab().setText("Kirim Barang").setIcon(R.drawable.ic_history_baru));
        tabLayout.addTab(tabLayout.newTab().setText("Minta Barang").setIcon(R.drawable.ic_minta_barang_gudang));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_profile));

    }

    private void getAllWidgets() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_gudang);
    }


}