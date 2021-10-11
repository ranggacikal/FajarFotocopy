package com.haloqlinic.fajarfotocopy.gudang.usergudang;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.TambahBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.TransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.editImageUser.ResponseEditImageUser;
import com.haloqlinic.fajarfotocopy.model.editUser.ResponseEditUser;
import com.haloqlinic.fajarfotocopy.model.hapusUser.ResponseHapusUser;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDataUserGudangActivity extends AppCompatActivity {

    private ActivityDetailDataUserGudangBinding binding;

    String id_user, nama_lengkap, username, password, level, id_outlet, nama_outlet, foto;

    String id_outlet_edt, id_outlet_spinner, level_edt, level_spinner;

    boolean gantiLevel, gantiIdOutlet;

    private String[] levelItem = {"Kepala Gudang", "Karyawan Gudang", "Kepala Toko", "Karyawan Toko", "Driver"};

    List<DataTokoItem> dataToko;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDataUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_user = getIntent().getStringExtra("id_user");
        nama_lengkap = getIntent().getStringExtra("nama_user");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        level = getIntent().getStringExtra("level");
        id_outlet = getIntent().getStringExtra("id_outlet");
        nama_outlet = getIntent().getStringExtra("nama_outlet");
        foto = getIntent().getStringExtra("foto");

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(DetailDataUserGudangActivity.this,
                android.R.layout.simple_spinner_item, levelItem);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDetailLevelUserGudang.setAdapter(adapterSpinner);

        initSpinnerToko();

        PushDownAnim.setPushDownAnimTo(binding.linearBackUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnGantiLevelDetailUser)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gantiLevel = true;
                        binding.spinnerDetailLevelUserGudang.setVisibility(View.VISIBLE);
                        binding.relativeDetailGantiLevelUser.setVisibility(View.GONE);
                        binding.edtDetailLevelUserGudang.setVisibility(View.GONE);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnGantiTokoDetailUser)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gantiIdOutlet = true;
                        binding.spinnerDetailPilihTokoUserGudang.setVisibility(View.VISIBLE);
                        binding.relativeDetailGantiTokoUser.setVisibility(View.GONE);
                        binding.edtDetailTokoUserGudang.setVisibility(View.GONE);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnDetailUpdatePasswordUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailDataUserGudangActivity.this, UpdatePasswordActivity.class);
                        intent.putExtra("id_user", id_user);
                        startActivity(intent);
                    }
                });

        binding.spinnerDetailLevelUserGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                level_spinner = binding.spinnerDetailLevelUserGudang.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerDetailPilihTokoUserGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_outlet_spinner = dataToko.get(position).getIdOutlet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.edtDetailNamaUserGudang.setText(nama_lengkap);
        binding.edtDetailUsernameUserGudang.setText(username);
        binding.edtDetailTokoUserGudang.setText(nama_outlet);
        binding.edtDetailLevelUserGudang.setText(level);

        Glide.with(DetailDataUserGudangActivity.this)
                .load(foto)
                .into(binding.imageTambahUserGudang);

        PushDownAnim.setPushDownAnimTo(binding.btnDetailEditUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editUser();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imageTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnDetailHapusUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hapusUser();
                    }
                });

    }

    private void hapusUser() {

        BottomSheetMaterialDialog mBottomSheetDialog =
                new BottomSheetMaterialDialog.Builder(DetailDataUserGudangActivity.this)
                        .setTitle("Hapus Data User?")
                        .setMessage("Apakah anda yakin ingin menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                ProgressDialog progressHapus = new ProgressDialog(DetailDataUserGudangActivity.this);
                                progressHapus.setMessage("Hapus Data");
                                progressHapus.show();

                                ConfigRetrofit.service.hapusUser(id_user).enqueue(new Callback<ResponseHapusUser>() {
                                    @Override
                                    public void onResponse(Call<ResponseHapusUser> call, Response<ResponseHapusUser> response) {
                                        if (response.isSuccessful()){

                                            progressHapus.dismiss();

                                            int status = response.body().getStatus();

                                            if (status==1){

                                                dialogInterface.dismiss();

                                                finish();
                                                Toast.makeText(DetailDataUserGudangActivity.this, "Hapus Data Berhasil",
                                                        Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(DetailDataUserGudangActivity.this,
                                                        "Hapus Gagal", Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            progressHapus.dismiss();
                                            Toast.makeText(DetailDataUserGudangActivity.this,
                                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseHapusUser> call, Throwable t) {
                                        progressHapus.dismiss();
                                        Toast.makeText(DetailDataUserGudangActivity.this, "Koneksi Error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void pilihGambar() {

        ImagePicker.with(DetailDataUserGudangActivity.this)
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
                binding.imageTambahUserGudang.setImageBitmap(bitmap);
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

        ProgressDialog progressDialog = new ProgressDialog(DetailDataUserGudangActivity.this);
        progressDialog.setMessage("Mengubah foto profile");
        progressDialog.show();

        ConfigRetrofit.service.editImageUser(id_user, image).enqueue(new Callback<ResponseEditImageUser>() {
            @Override
            public void onResponse(Call<ResponseEditImageUser> call, Response<ResponseEditImageUser> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(DetailDataUserGudangActivity.this,
                                "Berhasil Update Gambar", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DetailDataUserGudangActivity.this,
                                "Gagal Update Gambar", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailDataUserGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditImageUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDataUserGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editUser() {

        String id_outlet_edit, level_edit, nama_edit, username_edit;

        nama_edit = binding.edtDetailNamaUserGudang.getText().toString();
        username_edit = binding.edtDetailUsernameUserGudang.getText().toString();

        if (nama_edit.isEmpty()) {
            binding.edtDetailNamaUserGudang.setError("Nama Tidak Boleh Kosong");
            binding.edtDetailNamaUserGudang.requestFocus();
            return;
        }

        if (username_edit.isEmpty()) {
            binding.edtDetailUsernameUserGudang.setError("username Tidak Boleh Kosong");
            binding.edtDetailUsernameUserGudang.requestFocus();
            return;
        }

        if (gantiLevel == true) {
            level_edit = level_spinner;
        } else {
            level_edit = level;
        }

        if (gantiIdOutlet == true) {
            id_outlet_edit = id_outlet_spinner;
        } else {
            id_outlet_edit = id_outlet;
        }

        ProgressDialog progressDialog = new ProgressDialog(DetailDataUserGudangActivity.this);
        progressDialog.setMessage("Mengedit User");
        progressDialog.show();

        ConfigRetrofit.service.editUser(id_user, nama_edit, username_edit, level_edit, id_outlet_edit)
                .enqueue(new Callback<ResponseEditUser>() {
                    @Override
                    public void onResponse(Call<ResponseEditUser> call, Response<ResponseEditUser> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {
                                if (bitmap!=null){
                                    editImage();
                                }
                                finish();
                            } else {
                                Toast.makeText(DetailDataUserGudangActivity.this,
                                        "Gagal Edit User", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(DetailDataUserGudangActivity.this,
                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditUser> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailDataUserGudangActivity.this, "Koneksi Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initSpinnerToko() {

        ProgressDialog progressDialog = new ProgressDialog(DetailDataUserGudangActivity.this);
        progressDialog.setMessage("Memuat Data Toko");
        progressDialog.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {
                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerToko = new ArrayList<String>();
                        for (int i = 0; i < dataToko.size(); i++) {

                            listSpinnerToko.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(DetailDataUserGudangActivity.this,
                                R.layout.spinner_item, listSpinnerToko);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerDetailPilihTokoUserGudang.setAdapter(adapterToko);
                    } else {
                        Toast.makeText(DetailDataUserGudangActivity.this, "Data Toko Kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DetailDataUserGudangActivity.this, "Response Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDataUserGudangActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}