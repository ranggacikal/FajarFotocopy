package com.haloqlinic.fajarfotocopy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityLoginBinding;
import com.haloqlinic.fajarfotocopy.driver.MainDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.fragmentkasir.HomeKasirFragment;
import com.haloqlinic.fajarfotocopy.kepalatoko.HomeKetoActivity;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;


    EditText edtUsername, edtPassword;
    Button btnLogin;

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

        if (username.isEmpty()){
            edtUsername.setError("Username Tidak Boleh Kosong");
            edtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()){
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

                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){
                        progressDialog.dismiss();

                        String id_user = response.body().getDataLogin().getIdUser();
                        String nama = response.body().getDataLogin().getNamaLengkap();
                        String username = response.body().getDataLogin().getUsername();
                        String level = response.body().getDataLogin().getLevel();
                        String id_outlet = response.body().getDataLogin().getIdOutlet();
                        String img = response.body().getDataLogin().getFoto();

                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_USER, id_user);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA, nama);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_USERNAME, username);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_LEVEL, level);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_OUTLET, id_outlet);
                        preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMG, img);
                        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);

                        if (level.equals("Kepala Gudang") || level.equals("Karyawan Gudang")){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            getToken();
                            finish();
                        }else if (level.equals("Kepala Toko")){
                            startActivity(new Intent(LoginActivity.this, HomeKetoActivity.class));
                            getToken();
                            finish();
                        }else if (level.equals("Karyawan Toko")){
                            startActivity(new Intent(LoginActivity.this, MainKasirActivity.class));
                            getToken();
                            finish();
                        } else if (level.equals("Driver")){
                            startActivity(new Intent(LoginActivity.this, MainDriverActivity.class));
                            getToken();
                            finish();
                        }

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("statusGetToken", token);
                        Toast.makeText(LoginActivity.this, "berhasil get Token", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}