package com.haloqlinic.fajarfotocopy;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityEditProfileBinding;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.DetailDataUserGudangActivity;
import com.haloqlinic.fajarfotocopy.model.editImageUser.ResponseEditImageUser;
import com.haloqlinic.fajarfotocopy.model.editProfile.ResponseEditProfile;
import com.haloqlinic.fajarfotocopy.model.editUser.ResponseEditUser;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    private SharedPreferencedConfig preferencedConfig;
    EditText editUsername, editPassword;
    ImageView imageView;

    String id_user, username, password;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        editUsername = rootView.findViewById(R.id.edt_username);
        editPassword = rootView.findViewById(R.id.edt_password);
        imageView = rootView.findViewById(R.id.img_profile);

        editPassword.setEnabled(false);

        preferencedConfig = new SharedPreferencedConfig(getApplicationContext());

        editUsername.setText(preferencedConfig.getPreferenceUsername());
        Glide.with(getApplicationContext()).load(preferencedConfig.getPreferenceImg()).into(imageView);

        PushDownAnim.setPushDownAnimTo(binding.imgProfile)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnSimpan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editUser();
                    }
                });


    }

    private void pilihGambar() {

        ImagePicker.with(EditProfileActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                binding.imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void editImage() {

        String image = imageToString();

        ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Mengubah foto profile");
        progressDialog.show();

        ConfigRetrofit.service.editImageUser(id_user, image).enqueue(new Callback<ResponseEditImageUser>() {
            @Override
            public void onResponse(Call<ResponseEditImageUser> call, Response<ResponseEditImageUser> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(EditProfileActivity.this,
                                "Berhasil Update Gambar", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(EditProfileActivity.this,
                                "Gagal Update Gambar", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditImageUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editUser() {

        String username_edit;

        username_edit = binding.edtUsername.getText().toString();


        if (username_edit.isEmpty()) {
            binding.edtUsername.setError("username Tidak Boleh Kosong");
            binding.edtUsername.requestFocus();
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Mengedit User");
        progressDialog.show();

        ConfigRetrofit.service.editProfile(id_user, username_edit)
                .enqueue(new Callback<ResponseEditProfile>() {
                    @Override
                    public void onResponse(Call<ResponseEditProfile> call, Response<ResponseEditProfile> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {
                                if (bitmap != null) {
                                    editImage();
                                }
                                finish();
                            } else {
                                Toast.makeText(EditProfileActivity.this,
                                        "Gagal Edit User", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this,
                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditProfile> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Koneksi Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

}