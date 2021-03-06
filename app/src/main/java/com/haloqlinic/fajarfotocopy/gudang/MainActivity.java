package com.haloqlinic.fajarfotocopy.gudang;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.haloqlinic.fajarfotocopy.MyService;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.HomeFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.KirimBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.MintaBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.ProfileFragment;
import com.haloqlinic.fajarfotocopy.model.editFirebaseToken.ResponseEditFirebaseToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

enum WindowSizeClass { COMPACT, MEDIUM, EXPANDED }
 
public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    private HomeFragment homeFragment;
    private KirimBarangFragment kirimBarangFragment;
    private ProfileFragment profileFragment;
    private MintaBarangFragment mintaBarangFragment;


    private SharedPreferencedConfig preferencedConfig;

    String token = "";

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

        Log.d("cekTokenMain", "onCreate: " + preferencedConfig.getPreferenceTokenFcm());

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
                        Toast.makeText(MainActivity.this, "berhasil get Token", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tambahTokenFirebase(String id_user, String token) {


        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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

                                Toast.makeText(MainActivity.this, "Token Berhasil Ditambah", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this, "Token Gagal ditambah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditFirebaseToken> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
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

        switch (position) {
            case 0:
                replaceFragment(homeFragment);
                break;
            case 1:
                replaceFragment(kirimBarangFragment);
                break;
            case 2:
                replaceFragment(mintaBarangFragment);
                break;
            case 3:
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
        profileFragment = new ProfileFragment();
        kirimBarangFragment = new KirimBarangFragment();
        mintaBarangFragment = new MintaBarangFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_home_non_active));
        tabLayout.addTab(tabLayout.newTab().setText("Kirim").setIcon(R.drawable.ic_kirim_non_active));
        tabLayout.addTab(tabLayout.newTab().setText("Minta").setIcon(R.drawable.ic_minta_non_active));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_user_non_active));

    }

    private void getAllWidgets() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_gudang);
    }


}