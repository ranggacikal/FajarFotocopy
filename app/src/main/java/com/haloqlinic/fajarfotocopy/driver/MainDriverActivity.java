package com.haloqlinic.fajarfotocopy.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.driver.fragmentdriver.HomeDriverFragment;
import com.haloqlinic.fajarfotocopy.driver.fragmentdriver.ProfileDriverFragment;
import com.haloqlinic.fajarfotocopy.driver.fragmentdriver.RiwayatDriverFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.InformasiGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.ProfileFragment;


public class MainDriverActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeDriverFragment homeDriverFragment;
    private RiwayatDriverFragment riwayatDriverFragment;
    private ProfileDriverFragment profileDriverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);

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
                replaceFragment(homeDriverFragment);
                break;
            case 1 :
                replaceFragment(riwayatDriverFragment);
                break;
            case 2 :
                replaceFragment(profileDriverFragment);
                break;

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home_driver, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupTabLayout() {
        homeDriverFragment = new HomeDriverFragment();
        riwayatDriverFragment = new RiwayatDriverFragment();
        profileDriverFragment = new ProfileDriverFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat").setIcon(R.drawable.informasi_ic));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_profile));
    }

    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_driver);
    }
}