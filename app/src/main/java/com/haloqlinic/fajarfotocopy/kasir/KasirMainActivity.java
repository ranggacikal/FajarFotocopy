package com.haloqlinic.fajarfotocopy.kasir;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDriverMainBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKasirMainBinding;
import com.haloqlinic.fajarfotocopy.driver.DriverMainActivity;
import com.haloqlinic.fajarfotocopy.model.editFirebaseToken.ResponseEditFirebaseToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KasirMainActivity extends AppCompatActivity {

    String token = "";

    private SharedPreferencedConfig preferencedConfig;
    private ActivityKasirMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKasirMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferencedConfig = new SharedPreferencedConfig(KasirMainActivity.this);

        getToken();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_kasir, R.id.navigation_riwayat_kasir, R.id.navigation_profile_kasir)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_kasir_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

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
                        Toast.makeText(KasirMainActivity.this, "berhasil get Token", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tambahTokenFirebase(String id_user, String token) {


        ProgressDialog progressDialog = new ProgressDialog(KasirMainActivity.this);
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

                                Toast.makeText(KasirMainActivity.this, "Token Berhasil Ditambah", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(KasirMainActivity.this, "Token Gagal ditambah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(KasirMainActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditFirebaseToken> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(KasirMainActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}