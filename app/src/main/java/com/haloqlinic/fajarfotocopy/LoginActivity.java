package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencedConfig = new SharedPreferencedConfig(this);

        edtUsername = findViewById(R.id.edt_username_login_gudang);
        edtPassword = findViewById(R.id.edt_password_login_gudang);
        btnLogin = findViewById(R.id.btn_login_gudang);

        btnLogin.setOnClickListener(new View.OnClickListener() {
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
            edtUsername.setError("usernmae tidak boleh kosong");
            edtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword.setError("password tidak boleh kosong");
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

                        if (level.equals("gudang")){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Username/password salah", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Username/password salah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}