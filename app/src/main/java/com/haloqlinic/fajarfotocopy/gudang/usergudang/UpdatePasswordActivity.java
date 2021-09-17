package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPengirimanBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityUpdatePasswordBinding;
import com.haloqlinic.fajarfotocopy.model.updatePassword.ResponsePassword;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    private ActivityUpdatePasswordBinding binding;

    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_user = getIntent().getStringExtra("id_user");

        showHidePass1(binding.imgHidePassword1);
        showHidePass2(binding.imgHidePassword2);

        binding.edtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordCheck1 = binding.edtPassword1.getText().toString();
                String passwordCheck2 = binding.edtPassword2.getText().toString();


                if (passwordCheck2.equals(passwordCheck1)){
                    binding.txtPasswordSesuai.setVisibility(View.VISIBLE);
                    binding.txtPasswordTidakSesuai.setVisibility(View.GONE);
                }else{
                    binding.txtPasswordTidakSesuai.setVisibility(View.VISIBLE);
                    binding.txtPasswordSesuai.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnUpdatePassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ubahPassword();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackUpdatePassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }

    private void ubahPassword() {


        String password1 = binding.edtPassword1.getText().toString();
        String password2 = binding.edtPassword2.getText().toString();

        if (password1.equals("")){
            binding.edtPassword1.setError("Password Tidak boleh kosong");
            binding.edtPassword1.requestFocus();
            return;
        }

        if (password2.equals("")){
            binding.edtPassword2.setError("Ulangi Password Tidak boleh kosong");
            binding.edtPassword2.requestFocus();
            return;
        }

        if(!password2.equals(password1)){
            Toast.makeText(UpdatePasswordActivity.this, "Password Tidak Sama",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(UpdatePasswordActivity.this);
        progressDialog.setMessage("Mengubah Password");
        progressDialog.show();

        Log.d("paramUpdatePassword", "id_user: "+id_user);
        Log.d("paramUpdatePassword", "password: "+password2);

        ConfigRetrofit.service.updatePassword(id_user, password2).enqueue(new Callback<ResponsePassword>() {
            @Override
            public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(UpdatePasswordActivity.this,
                                "Update Password Berhasil", Toast.LENGTH_SHORT).show();
                        finish();

                    }else{
                        Toast.makeText(UpdatePasswordActivity.this, "Update Password Gagal, silahkan coba lagi",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(UpdatePasswordActivity.this, "Terjadi Kesalahan diserver",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePassword> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdatePasswordActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showHidePass1(View view) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()== R.id.img_hide_password1){

                    if(binding.edtPassword1.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        binding.edtPassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        binding.edtPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

    private void showHidePass2(View view) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.img_hide_password2){

                    if(binding.edtPassword2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        binding.edtPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        binding.edtPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }
}