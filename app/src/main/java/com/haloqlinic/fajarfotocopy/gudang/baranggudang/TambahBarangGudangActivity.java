package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.formatNumber.NumberTextWatcher;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.TambahUserGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataKategori.DataKategoriItem;
import com.haloqlinic.fajarfotocopy.model.dataKategori.ResponseDataKategori;
import com.haloqlinic.fajarfotocopy.model.tambahBarang.ResponseTambahBarang;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahBarangGudangActivity extends AppCompatActivity {

    private ActivityTambahBarangGudangBinding binding;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    List<DataKategoriItem> dataKategoriItems;

    String id_kategori;

    String jumlah_pack, number_of_pack;
    int jumlah_pcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.edtTambahHargaModalGudang.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaModalGudang));
        binding.edtTambahHargaModalToko.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaModalToko));
        binding.edtTambahHargaJualToko.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaJualToko));
        binding.edtTambahHargaModalGudangPack.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaModalGudangPack));
        binding.edtTambahHargaModalTokoPack.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaModalTokoPack));
        binding.edtTambahHargaJualTokoPack.addTextChangedListener(new NumberTextWatcher(binding.edtTambahHargaJualTokoPack));


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


        binding.tambahStockBarangPcsGudang.setEnabled(false);
        binding.tambahNumberOfPackBarangGudang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String jml_pack = binding.tambahStockBarangPackGudang.getText().toString();
                number_of_pack = binding.tambahNumberOfPackBarangGudang.getText().toString();
                Log.d("cekTextChanged", "numberOfPack: "+number_of_pack);
                Log.d("cekTextChanged", "jml_pack: "+jml_pack);

                if (number_of_pack.equals("")){
                    binding.tambahStockBarangPcsGudang.setText("0");
                }else {

                    jumlah_pcs = Integer.parseInt(jml_pack) * Integer.parseInt(number_of_pack);
                    binding.tambahStockBarangPcsGudang.setText(String.valueOf(jumlah_pcs));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        initSpinnerKategori();

        binding.spinnerKategoriAddBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kategori = dataKategoriItems.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahKategoriBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TambahBarangGudangActivity.this, TambahKategoriActivity.class));
                    }
                });

    }

    private void initSpinnerKategori() {

        ProgressDialog progressDialog = new ProgressDialog(TambahBarangGudangActivity.this);
        progressDialog.setMessage("Memuat Data Kategori");
        progressDialog.show();

        ConfigRetrofit.service.dataKategori().enqueue(new Callback<ResponseDataKategori>() {
            @Override
            public void onResponse(Call<ResponseDataKategori> call, Response<ResponseDataKategori> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataKategoriItems = response.body().getDataKategori();
                        List<String> listSpinnerKategori = new ArrayList<String>();
                        for (int i = 0; i < dataKategoriItems.size(); i++) {

                            listSpinnerKategori.add(dataKategoriItems.get(i).getNamaKategori());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(TambahBarangGudangActivity.this,
                                R.layout.spinner_item, listSpinnerKategori);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerKategoriAddBarang.setAdapter(adapterToko);

                    } else {
                        Toast.makeText(TambahBarangGudangActivity.this, "Data Kategori Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TambahBarangGudangActivity.this, "Gagal memuat data kategori", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKategori> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahBarangGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihGambar() {

        ImagePicker.with(TambahBarangGudangActivity.this)
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

                    binding.edtTambahIdBarangGudang.setText(intentResult.getContents());
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
                    binding.imageTambaBarangGudang.setImageBitmap(bitmap);

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahBarangGudangActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
                break;

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
        String harga_modal_gudang_int = binding.edtTambahHargaModalGudang.getText().toString();
        String harga_modal_gudang = harga_modal_gudang_int.replace(".", "").replace(",", "");
        String harga_modal_toko_int = binding.edtTambahHargaModalToko.getText().toString();
        String harga_modal_toko = harga_modal_toko_int.replace(".", "").replace(",", "");;
        String harga_jual_toko_int = binding.edtTambahHargaJualToko.getText().toString();
        String harga_jual_toko = harga_jual_toko_int.replace(".", "").replace(",", "");;
        String harga_modal_gudang_pack_int = binding.edtTambahHargaModalGudangPack.getText().toString();
        String harga_modal_gudang_pack = harga_modal_gudang_pack_int.replace(".", "").replace(",", "");;
        String harga_modal_toko_pack_int = binding.edtTambahHargaModalTokoPack.getText().toString();
        String harga_modal_toko_pack = harga_modal_toko_pack_int.replace(".", "").replace(",", "");;
        String harga_jual_toko_pack_int = binding.edtTambahHargaJualTokoPack.getText().toString();
        String harga_jual_toko_pack = harga_jual_toko_pack_int.replace(".", "").replace(",", "");;
        String asal_barang = binding.edtTambahAsalBarangGudang.getText().toString();
        String jumlah_pack = binding.tambahStockBarangPackGudang.getText().toString();
//        String diskon = binding.edtTambahDiskonBarangGudang.getText().toString();
//        String diskon_pack = binding.edtTambahDiskonpackBarangGudang.getText().toString();
        String image_barang = imageToString();

        if (id_barang.isEmpty()) {
            binding.edtTambahIdBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahIdBarangGudang.requestFocus();
            return;
        }

        if (nama_barang.isEmpty()) {
            binding.edtTambahNamaBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahNamaBarangGudang.requestFocus();
            return;
        }

        if (stock.equals("0")) {
            binding.tambahStockBarangPcsGudang.setError("Silahkan masukan jumlah satuan dalam " +
                    "satu pack");
            binding.tambahStockBarangPcsGudang.requestFocus();
            return;
        }

        if (harga_modal_gudang.isEmpty()) {
            binding.edtTambahHargaModalGudang.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalGudang.requestFocus();
            return;
        }

        if (harga_modal_toko.isEmpty()) {
            binding.edtTambahHargaModalToko.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalToko.requestFocus();
            return;
        }

        if (harga_jual_toko.isEmpty()) {
            binding.edtTambahHargaJualToko.setError("Field tidak boleh kosong");
            binding.edtTambahHargaJualToko.requestFocus();
            return;
        }

        if (harga_modal_gudang_pack.isEmpty()) {
            binding.edtTambahHargaModalGudangPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalGudangPack.requestFocus();
            return;
        }

        if (harga_modal_toko_pack.isEmpty()) {
            binding.edtTambahHargaModalTokoPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaModalTokoPack.requestFocus();
            return;
        }

        if (harga_jual_toko_pack.isEmpty()) {
            binding.edtTambahHargaJualTokoPack.setError("Field tidak boleh kosong");
            binding.edtTambahHargaJualTokoPack.requestFocus();
            return;
        }

        if (asal_barang.isEmpty()) {
            binding.edtTambahAsalBarangGudang.setError("Field tidak boleh kosong");
            binding.edtTambahAsalBarangGudang.requestFocus();
            return;
        }

        if (jumlah_pack.isEmpty()) {
            binding.tambahStockBarangPackGudang.setError("Field tidak boleh kosong");
            binding.tambahStockBarangPackGudang.requestFocus();
            return;
        }

        if(number_of_pack.isEmpty()){
            binding.tambahNumberOfPackBarangGudang.setError("Satuan dalam satu pack " +
                    "tidak boleh kosong");
            binding.tambahNumberOfPackBarangGudang.requestFocus();
            return;
        }

//        if (diskon.isEmpty()){
//            binding.edtTambahDiskonBarangGudang.setError("Field tidak boleh kosong");
//            binding.edtTambahDiskonBarangGudang.requestFocus();
//            return;
//        }
//
//        if (diskon_pack.isEmpty()){
//            binding.edtTambahDiskonpackBarangGudang.setError("Field tidak boleh kosong");
//            binding.edtTambahDiskonpackBarangGudang.requestFocus();
//            return;
//        }

        ProgressDialog progressDialog = new ProgressDialog(TambahBarangGudangActivity.this);
        progressDialog.setMessage("Menambahkan data barang, mohon menunggu sampai data berhasil bertambah ...");
        progressDialog.show();

        ConfigRetrofit.service.tambahBarang(id_barang, nama_barang, stock, harga_modal_gudang, harga_modal_toko,
                harga_jual_toko, harga_modal_gudang_pack, harga_modal_toko_pack, harga_jual_toko_pack, asal_barang,
                jumlah_pack, number_of_pack, image_barang, id_kategori).enqueue(new Callback<ResponseTambahBarang>() {
            @Override
            public void onResponse(Call<ResponseTambahBarang> call, Response<ResponseTambahBarang> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status == 1) {

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
                        binding.tambahNumberOfPackBarangGudang.setText("");
                        binding.imageTambaBarangGudang.setImageResource(R.drawable.ic_profile);
                    } else {
                        Toast.makeText(TambahBarangGudangActivity.this, "Gagal Tambah Data, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TambahBarangGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahBarang> call, Throwable t) {
                Toast.makeText(TambahBarangGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initSpinnerKategori();
    }
}