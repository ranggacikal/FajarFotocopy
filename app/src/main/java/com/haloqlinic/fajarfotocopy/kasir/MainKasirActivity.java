package com.haloqlinic.fajarfotocopy.kasir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.fajarfotocopy.R;

import com.haloqlinic.fajarfotocopy.kasir.fragmentkasir.HomeKasirFragment;
import com.haloqlinic.fajarfotocopy.kasir.fragmentkasir.InformasiKasirFragment;
import com.haloqlinic.fajarfotocopy.kasir.fragmentkasir.RiwayatTransaksiKasirFragment;

public class MainKasirActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeKasirFragment homeKasirFragment;
    private InformasiKasirFragment informasiKasirFragment;
    private RiwayatTransaksiKasirFragment riwayatTransaksiKasirFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kasir);

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
                replaceFragment(homeKasirFragment);
                break;
            case 1 :
                replaceFragment(informasiKasirFragment);
                break;
            case 2 :
                replaceFragment(riwayatTransaksiKasirFragment);
                break;

        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home_kasir, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupTabLayout() {

        homeKasirFragment = new HomeKasirFragment();
        informasiKasirFragment = new InformasiKasirFragment();
        riwayatTransaksiKasirFragment = new RiwayatTransaksiKasirFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setText("Informasi").setIcon(R.drawable.informasi_ic));
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat").setIcon(R.drawable.ic_riwayat_transaksi));

    }

    private void getAllWidgets() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_kasir);
    }
}