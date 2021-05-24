package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.model.tambahBarang.ResponseTambahBarang;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahBarangGudangActivity extends AppCompatActivity {

    private ActivityTambahBarangGudangBinding binding;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeTambahStockpcsGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                TambahBarangGudangActivity.this
                        );

                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahBarang();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imageTambaBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackTambahBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }

    private void pilihGambar() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                binding.imageTambaBarangGudang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            IntentResult intentResult = IntentIntegrator.parseActivityResult(
                    requestCode, resultCode, data
            );

            if (intentResult.getContents() != null) {

                binding.edtTambahIdBarangGudang.setText(intentResult.getContents());

            } else {
                Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void tambahBarang() {

        String id_barang = binding.edtTambahIdBarangGudang.getText().toString();
        String nama_barang = binding.edtTambahNamaBarangGudang.getText().toString();
        String stock = binding.tambahStockBarangPcsGudang.getText().toString();
        String harga_modal_gudang = binding.edtTambahHargaModalGudang.getText().toString();
        String harga_modal_toko = binding.edtTambahHargaModalToko.getText().toString();
        String harga_jual_toko = binding.edtTambahHargaJualToko.getText().toString();
        String harga_modal_gudang_pack = binding.edtTambahHargaModalGudangPack.getText().toString();
        String harga_modal_toko_pack = binding.edtTambahHargaModalTokoPack.getText().toString();
        String harga_jual_toko_pack = binding.edtTambahHargaJualTokoPack.getText().toString();
        String asal_barang = binding.edtTambahAsalBarangGudang.getText().toString();
        String jumlah_pack = binding.tambahStockBarangPackGudang.getText().toString();
        String diskon = binding.edtTambahDiskonBarangGudang.getText().toString();
        String diskon_pack = binding.edtTambahDiskonpackBarangGudang.getText().toString();
        String image_barang = imageToString();

        if (id_barang.isEmpty()){
            binding.edtTambahIdBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahIdBarangGudang.requestFocus();
            return;
        }

        if (nama_barang.isEmpty()){
            binding.edtTambahNamaBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahNamaBarangGudang.requestFocus();
            return;
        }

        if (stock.isEmpty()){
            binding.tambahStockBarangPcsGudang.setError("Field tidak boleh kosong");
            binding.tambahStockBarangPcsGudang.requestFocus();
            return;
        }

        if (harga_modal_gudang.isEmpty()){
            binding.edtTambahHargaModalGudang.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalGudang.requestFocus();
            return;
        }

        if (harga_modal_toko.isEmpty()){
            binding.edtTambahHargaModalToko.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalToko.requestFocus();
            return;
        }

        if (harga_jual_toko.isEmpty()){
            binding.edtTambahHargaJualToko.setError("Field tidak boleh kosong");
            binding.edtTambahHargaJualToko.requestFocus();
            return;
        }

        if (harga_modal_gudang_pack.isEmpty()){
            binding.edtTambahHargaModalGudangPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalGudangPack.requestFocus();
            return;
        }

        if (harga_modal_toko_pack.isEmpty()){
            binding.edtTambahHargaModalTokoPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalTokoPack.requestFocus();
            return;
        }

        if (harga_jual_toko_pack.isEmpty()){
            binding.edtTambahHargaJualTokoPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaJualTokoPack.requestFocus();
            return;
        }

        if (asal_barang.isEmpty()){
            binding.edtTambahAsalBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahAsalBarangGudang.requestFocus();
            return;
        }

        if (jumlah_pack.isEmpty()){
            binding.tambahStockBarangPackGudang.setError("Field tidak boleh kosong");
            binding.tambahStockBarangPackGudang.requestFocus();
            return;
        }

        if (diskon.isEmpty()){
            binding.edtTambahDiskonBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahDiskonBarangGudang.requestFocus();
            return;
        }

        if (diskon_pack.isEmpty()){
            binding.edtTambahDiskonpackBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahDiskonpackBarangGudang.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(TambahBarangGudangActivity.this);
        progressDialog.setMessage("Menambahkan data barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahBarang(id_barang, nama_barang, stock, harga_modal_gudang, harga_modal_toko,
                harga_jual_toko, harga_modal_gudang_pack, harga_modal_toko_pack, harga_jual_toko_pack, asal_barang,
                jumlah_pack, diskon, diskon_pack, image_barang).enqueue(new Callback<ResponseTambahBarang>() {
            @Override
            public void onResponse(Call<ResponseTambahBarang> call, Response<ResponseTambahBarang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(TambahBarangGudangActivity.this, "Berhasil tambah data", Toast.LENGTH_SHORT).show();
                        binding.edtTambahIdBarangGudang.setText("");
                        binding.edtTambahNamaBarangGudang.setText("");
                        binding.tambahStockBarangPcsGudang.setText("");
                        binding.tambahStockBarangPackGudang.setText("");
                        binding.edtTambahHargaModalGudang.setText("");
                        binding.edtTambahHargaModalToko.setText("");
                        binding.edtTambahHargaJualToko.setText("");
                        binding.edtTambahHargaModalGudangPack.setText("");
                        binding.edtTambahHargaModalTokoPack.setText("");
                        binding.edtTambahHargaJualTokoPack.setText("");
                        binding.edtTambahAsalBarangGudang.setText("");
                        binding.edtTambahDiskonBarangGudang.setText("");
                        binding.edtTambahDiskonpackBarangGudang.setText("");
                        binding.imageTambaBarangGudang.setImageResource(R.drawable.ic_profile);
                    }else{
                        Toast.makeText(TambahBarangGudangActivity.this, "Gagal Tambah Data, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahBarangGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahBarang> call, Throwable t) {
                Toast.makeText(TambahBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}