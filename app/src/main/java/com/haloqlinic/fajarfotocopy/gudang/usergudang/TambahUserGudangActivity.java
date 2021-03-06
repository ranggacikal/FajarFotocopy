package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahUserGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.TambahBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.register.ResponseRegister;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahUserGudangActivity extends AppCompatActivity {

    private ActivityTambahUserGudangBinding binding;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    List<DataTokoItem> dataToko;
    String id_toko, level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initSpinnerToko();

        PushDownAnim.setPushDownAnimTo(binding.linearBackTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imageTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihImage();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahUser();
                    }
                });

        binding.spinnerTambahPilihTokoUserGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_toko = dataToko.get(position).getIdOutlet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> levelList = new ArrayList<>();
        levelList.add("Kepala Gudang");
        levelList.add("Karyawan Gudang");
        levelList.add("Kepala Toko");
        levelList.add("Karyawan Toko");
        levelList.add("Driver");


        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(TambahUserGudangActivity.this, R.layout.spinner_item, levelList);
        binding.spinnerTambahLevelUserGudang.setAdapter(adapterLevel);

        binding.spinnerTambahLevelUserGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = levelList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initSpinnerToko() {

        ProgressDialog progressDialog = new ProgressDialog(TambahUserGudangActivity.this);
        progressDialog.setMessage("Memuat Data Toko");
        progressDialog.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerToko = new ArrayList<String>();
                        for (int i = 0; i < dataToko.size(); i++) {

                            listSpinnerToko.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(TambahUserGudangActivity.this,
                                R.layout.spinner_item, listSpinnerToko);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerTambahPilihTokoUserGudang.setAdapter(adapterToko);

                    }else{

                        Toast.makeText(TambahUserGudangActivity.this, "Gagal memuat data toko", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahUserGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahUserGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahUser() {
        String nama_user = binding.edtTambahNamaUserGudang.getText().toString();
        String username = binding.edtTambahUsernameUserGudang.getText().toString();
        String password = binding.edtTambahPasswordUserGudang.getText().toString();
        String image = imageToString();

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_user = "U"+randomId;

        if (nama_user.isEmpty()){
            binding.edtTambahNamaUserGudang.setError("Nama Lengkap Tidak Boleh Kosong!");
            binding.edtTambahUsernameUserGudang.requestFocus();
            return;
        }

        if (username.isEmpty()){
            binding.edtTambahUsernameUserGudang.setError("Username Tidak Boleh Kosong!");
            binding.edtTambahUsernameUserGudang.requestFocus();
            return;
        }

        if (password.isEmpty()){
            binding.edtTambahPasswordUserGudang.setError("Password Tidak Boleh Kosong!");
            binding.edtTambahPasswordUserGudang.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(TambahUserGudangActivity.this);
        progressDialog.setMessage("Menambahkan User");
        progressDialog.show();

        ConfigRetrofit.service.register(id_user, nama_user, username, password, level, id_toko, image).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){
                        Toast.makeText(TambahUserGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        binding.edtTambahNamaUserGudang.setText("");
                        binding.edtTambahUsernameUserGudang.setText("");
                        binding.edtTambahPasswordUserGudang.setText("");
                        binding.imageTambahUserGudang.setImageResource(R.drawable.ic_profile);
                    }else{
                        Toast.makeText(TambahUserGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahUserGudangActivity.this, "Terjadi Kesalahan Di Server...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahUserGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihImage() {
        ImagePicker.with(TambahUserGudangActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (49374):
                IntentResult intentResult = IntentIntegrator.parseActivityResult(
                        requestCode, resultCode, data
                );

                if (intentResult.getContents() != null) {

                    binding.edtTambahUsernameUserGudang.setText(intentResult.getContents());
                    Log.d("requestCodeScan", "onActivityResult: " + requestCode);

                }
                break;
            case (2404):
                if (resultCode == RESULT_OK) {

                    Log.d("requestCodeImg", "onActivityResult: " + requestCode);

                    Uri uri1 = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imageTambahUserGudang.setImageBitmap(bitmap);

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahUserGudangActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}