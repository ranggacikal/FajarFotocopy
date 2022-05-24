package com.haloqlinic.fajarfotocopy.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.InformasiGudangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.ProfileFragment;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.model.editFirebaseToken.ResponseEditFirebaseToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainDriverActivity extends AppCompatActivity {

    TabLayout tabLayout;


    String token = "";

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);

        preferencedConfig = new SharedPreferencedConfig(MainDriverActivity.this);

        getToken();

        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();

    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("statusGetToken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        tambahTokenFirebase(preferencedConfig.getPreferenceIdUser(), token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("statusGetToken", token);
                        Toast.makeText(MainDriverActivity.this, "berhasil get Token", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tambahTokenFirebase(String id_user, String token) {


        ProgressDialog progressDialog = new ProgressDialog(MainDriverActivity.this);
        progressDialog.setMessage("Generate Token");
        progressDialog.show();


        Log.d("cekParamToken", "onResponse: " + token);

        ConfigRetrofit.service.editFirebaseToken(id_user, token)
                .enqueue(new Callback<ResponseEditFirebaseToken>() {
                    @Override
                    public void onResponse(Call<ResponseEditFirebaseToken> call, Response<ResponseEditFirebaseToken> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {

                                Toast.makeText(MainDriverActivity.this, "Token Berhasil Ditambah", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainDriverActivity.this, "Token Gagal ditambah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainDriverActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditFirebaseToken> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainDriverActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

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


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home_driver, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupTabLayout() {


        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat").setIcon(R.drawable.ic_history_baru));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_profile));
    }

    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_driver);
    }
}