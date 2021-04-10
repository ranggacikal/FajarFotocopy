package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.fajarfotocopy.gudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.InformasiGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.KeranjangGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.KirimBarangFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeFragment homeFragment;
    private InformasiGudangFragment informasiGudangFragment;
    private KeranjangGudangFragment keranjangGudangFragment;
    private KirimBarangFragment kirimBarangFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case 1 :
                replaceFragment(informasiGudangFragment);
                break;
            case 2 :
                replaceFragment(kirimBarangFragment);
                break;
            case 3 :
                replaceFragment(keranjangGudangFragment);
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
        informasiGudangFragment = new InformasiGudangFragment();
        keranjangGudangFragment = new KeranjangGudangFragment();
        kirimBarangFragment = new KirimBarangFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setText("Informasi").setIcon(R.drawable.ic_informasi));
        tabLayout.addTab(tabLayout.newTab().setText("Kirim Barang").setIcon(R.drawable.ic_kirim));
        tabLayout.addTab(tabLayout.newTab().setText("Keranjang").setIcon(R.drawable.ic_keranjang));

    }

    private void getAllWidgets() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_gudang);
    }


}