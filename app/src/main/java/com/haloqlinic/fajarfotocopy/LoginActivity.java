package com.haloqlinic.fajarfotocopy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityLoginBinding;
import com.haloqlinic.fajarfotocopy.driver.DriverMainActivity;
import com.haloqlinic.fajarfotocopy.driver.MainDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.fragmentkasir.HomeKasirFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;
import com.haloqlinic.fajarfotocopy.model.editFirebaseToken.ResponseEditFirebaseToken;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import java.sql.Driver;

public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding binding;


    EditText edtUsername, edtPassword;
    Button btnLogin;

    String token;
    String tokenLocal = "";

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        preferencedConfig = new SharedPreferencedConfig(this);

        edtUsername = findViewById(R.id.edt_username_login_gudang);
        edtPassword = findViewById(R.id.edt_password_login_gudang);
        btnLogin = findViewById(R.id.btn_login_gudang);
        showHidePass(binding.imgHidePassword);


        PushDownAnim.setPushDownAnimTo(binding.btnLoginGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();
                    }
                });


    }

    private void login() {

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (username.isEmpty()) {
            edtUsername.setError("Username Tidak Boleh Kosong");
            edtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Password Tidak Boleh Kosong");
            edtPassword.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Login..");
        progressDialog.show();

        ConfigRetrofit.service.login(username, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();


                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("statusGetToken",
                                                "Fetching FCM registration token failed",
                                                task.getException());
                                        return;
                                    }

                                    token = task.getResult();

                                    Log.d("statusGetToken", token);
                                    Toast.makeText(LoginActivity.this, "berhasil get Token",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });






                    if (status == 1) {
                        progressDialog.dismiss();


                        String nama = response.body().getDataLogin().getNamaLengkap();
                        String username = response.body().getDataLogin().getUsername();
                        String level = response.body().getDataLogin().getLevel();
                        String id_outlet = response.body().getDataLogin().getIdOutlet();
                        String img = response.body().getDataLogin().getFoto();
                        String nama_toko = response.body().getDataLogin().getNamaOutlet();
                        String id_user = response.body().getDataLogin().getIdUser();

                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_USER, id_user);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA, nama);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA_TOKO, nama_toko);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_USERNAME, username);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_LEVEL, level);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_OUTLET, id_outlet);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMG, img);
                        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);

                        if (level.equals("Kepala Gudang") || level.equals("Karyawan Gudang")) {
                            tambahTokenFirebase(id_user, token);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else if (level.equals("Kepala Toko")) {
                            tambahTokenFirebase(id_user, token);
                            startActivity(new Intent(LoginActivity.this, MainKetoActivity.class));
                            finish();
                        } else if (level.equals("Karyawan Toko")) {
                            tambahTokenFirebase(id_user, token);
                            startActivity(new Intent(LoginActivity.this, MainKasirActivity.class));
                            finish();
                        } else if (level.equals("Driver")) {
                            tambahTokenFirebase(id_user, token);
                            startActivity(new Intent(LoginActivity.this, DriverMainActivity.class));
                            finish();
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahTokenFirebase(String id_user, String token) {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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

                                Toast.makeText(LoginActivity.this, "Token Berhasil Ditambah", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "Token Gagal ditambah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditFirebaseToken> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), new IntentFilter("MyData"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("testBroadcastToken", "onReceive: "+intent.getExtras().getString("token"));

        }
    };

    private void showHidePass(View view) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()== R.id.img_hide_password){

                    if(binding.edtPasswordLoginGudang.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.ic_password_visibile);
                        //Show Password
                        binding.edtPasswordLoginGudang.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.ic_password_invisibile);

                        //Hide Password
                        binding.edtPasswordLoginGudang.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }
}