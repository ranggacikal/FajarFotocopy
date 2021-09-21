package com.haloqlinic.fajarfotocopy.kepalatoko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.InformasiGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.KirimBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.ProfileFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo.HomeKetoFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo.InformasiKetoFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo.ListTransferFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo.ProfileKetoFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo.ReportPengirimanKetoFragment;

public class MainKetoActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeKetoFragment homeKetoFragment;
//    private InformasiKetoFragment informasiKetoFragment;
    private ReportPengirimanKetoFragment reportPengirimanKetoFragment;
    private ProfileKetoFragment profileKetoFragment;
    private ListTransferFragment listTransferFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_keto);

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
                replaceFragment(homeKetoFragment);
                break;
//            case 1 :
//                replaceFragment(informasiKetoFragment);
//                break;

            case 1 :
                replaceFragment(reportPengirimanKetoFragment);
                break;
            case 2 :
                replaceFragment(listTransferFragment);
                break;
            case 3 :
                replaceFragment(profileKetoFragment);
                break;
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home_keto, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


    private void setupTabLayout() {

        homeKetoFragment = new HomeKetoFragment();
//        informasiKetoFragment = new InformasiKetoFragment();
        profileKetoFragment = new ProfileKetoFragment();
        reportPengirimanKetoFragment = new ReportPengirimanKetoFragment();
        listTransferFragment = new ListTransferFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
//        tabLayout.addTab(tabLayout.newTab().setText("Informasi").setIcon(R.drawable.informasi_ic));
        tabLayout.addTab(tabLayout.newTab().setText("Report Barang").setIcon(R.drawable.ic_minta_barang_gudang));
        tabLayout.addTab(tabLayout.newTab().setText("List Transfer").setIcon(R.drawable.ic_list_transfer));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_profile));

    }

    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_keto);
    }
}