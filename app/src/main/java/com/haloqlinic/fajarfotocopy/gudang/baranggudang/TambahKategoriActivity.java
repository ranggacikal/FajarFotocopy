package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.KategoriAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahKategoriBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.model.dataKategori.DataKategoriItem;
import com.haloqlinic.fajarfotocopy.model.dataKategori.ResponseDataKategori;
import com.haloqlinic.fajarfotocopy.model.dataKategoriDesc.DataKategoriDescItem;
import com.haloqlinic.fajarfotocopy.model.dataKategoriDesc.ResponseDataKategoriDesc;
import com.haloqlinic.fajarfotocopy.model.tambahKategori.ResponseTambahKategori;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahKategoriActivity extends AppCompatActivity {

    private ActivityTambahKategoriBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahKategoriBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.btnTambahKategori)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         tambahKategori();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackTambahKategori)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerDataKategori.setHasFixedSize(true);
        binding.recyclerDataKategori.setLayoutManager(new LinearLayoutManager(TambahKategoriActivity.this));

        loadDataKategori();
    }

    public void loadDataKategori() {

        ProgressDialog progressDialog = new ProgressDialog(TambahKategoriActivity.this);
        progressDialog.setMessage("Memuat Data Kategori");
        progressDialog.show();

        ConfigRetrofit.service.dataKategoriDesc().enqueue(new Callback<ResponseDataKategoriDesc>() {
            @Override
            public void onResponse(Call<ResponseDataKategoriDesc> call, Response<ResponseDataKategoriDesc> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataKategoriDescItem> dataKategori = response.body().getDataKategoriDesc();
                        KategoriAdapter adapter = new KategoriAdapter(TambahKategoriActivity.this,
                                dataKategori, TambahKategoriActivity.this);
                        binding.recyclerDataKategori.setAdapter(adapter);

                    }else{
                        Toast.makeText(TambahKategoriActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahKategoriActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKategoriDesc> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahKategoriActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahKategori() {

        String nama_kategori = binding.edtTambahNamaKategori.getText().toString();

        if (nama_kategori.isEmpty()){

            binding.edtTambahNamaKategori.setError("Nama Kategori tidak boleh kosong");
            binding.edtTambahNamaKategori.requestFocus();

            return;

        }

        ProgressDialog progressDialog = new ProgressDialog(TambahKategoriActivity.this);
        progressDialog.setMessage("Menambah Kategori");
        progressDialog.show();

        ConfigRetrofit.service.tambahKategori(nama_kategori).enqueue(new Callback<ResponseTambahKategori>() {
            @Override
            public void onResponse(Call<ResponseTambahKategori> call, Response<ResponseTambahKategori> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(TambahKategoriActivity.this, "Berhasil Tambah Kategori", Toast.LENGTH_SHORT).show();
                        binding.edtTambahNamaKategori.setText("");
                        loadDataKategori();
                    }else{
                        Toast.makeText(TambahKategoriActivity.this, "Gagal Tambah Kategori", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TambahKategoriActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahKategori> call, Throwable t) {
                Toast.makeText(TambahKategoriActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}