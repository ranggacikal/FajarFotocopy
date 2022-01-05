package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.CariKirimBarangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.CariKirimBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKirimBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman.ResponseLastIdStatusPengiriamn;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class KirimBarangGudangActivity extends AppCompatActivity {

    private ActivityKirimBarangGudangBinding binding;
    boolean searchId = false;
    boolean searchName = false;

    String textId = "";
    String textName = "";

    public String id_toko, id_status_pengiriman, fromActivity, nama_barang, id_minta_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKirimBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getLastIdStatusPengiriman();

        id_toko = getIntent().getStringExtra("id_toko");
        fromActivity = getIntent().getStringExtra("fromActivity");
        nama_barang = getIntent().getStringExtra("nama_barang");
        id_minta_barang = getIntent().getStringExtra("id_minta_barang");

        PushDownAnim.setPushDownAnimTo(binding.linearBackKirimGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeKirimBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                KirimBarangGudangActivity.this
                        );

                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                    }
                });

        if (fromActivity!=null){
            if (fromActivity.equals("mintaBarang")){

                if (nama_barang!=null){
                    binding.searchviewKirimBarangGudang.setIconified(false);
                    binding.searchviewKirimBarangGudang.setQuery(nama_barang, true);
//                    binding.searchviewKirimBarangGudang.setQueryHint("Cari Nama Barang");
                    loadDataCari(nama_barang);
                }

            }
        }else {

            binding.searchviewKirimBarangGudang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.searchviewKirimBarangGudang.setIconified(false);
                    binding.searchviewKirimBarangGudang.setQueryHint("Cari Nama Barang");

                }
            });
        }

        binding.searchviewKirimBarangGudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                newText = "Sampoerna";

                loadDataCari(newText);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnListPengrimanBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(KirimBarangGudangActivity.this, DataPengirimanBarangActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                        intent.putExtra("tanggal_status_pengiriman", getIntent().getStringExtra("tanggal"));
                        startActivity(intent);
                    }
                });

    }

    private void getLastIdStatusPengiriman() {

        ConfigRetrofit.service.lastIdStatusPengiriman().enqueue(new Callback<ResponseLastIdStatusPengiriamn>() {
            @Override
            public void onResponse(Call<ResponseLastIdStatusPengiriamn> call, Response<ResponseLastIdStatusPengiriamn> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        id_status_pengiriman = response.body().getDataIdStatusPengiriman().getIdStatusPengiriman();

                    }else{
                        Log.e("lastIdStatusPengiriman", "onResponse: Data Kosong");
                    }

                }else{
                    Log.e("lastIdStatusPengiriman", "onResponse: Terjadi kesalahan" );
                }
            }

            @Override
            public void onFailure(Call<ResponseLastIdStatusPengiriamn> call, Throwable t) {
                Log.d("lastIdStatusPengiriman", "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {

            loadSearchById(intentResult.getContents());

        } else {
            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
        }
    }


    public void loadDataCari(String newText) {

        if (newText.equals("")){
            binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
            binding.txtDataKosongKirimBarangGudang.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.cariBarang(newText).enqueue(new Callback<ResponseCariBarangByNama>() {
                @Override
                public void onResponse(Call<ResponseCariBarangByNama> call, Response<ResponseCariBarangByNama> response) {
                    if (response.isSuccessful()) {

                        int status = response.body().getStatus();
                        List<SearchBarangByNamaItem> dataBarang = response.body().getSearchBarangByNama();

                        if (status == 1) {

                            binding.rvSearchKirimBarangGudang.setVisibility(View.VISIBLE);
                            binding.txtDataKosongKirimBarangGudang.setVisibility(View.GONE);
                            CariKirimBarangAdapter adapter = new CariKirimBarangAdapter(KirimBarangGudangActivity.this, dataBarang, KirimBarangGudangActivity.this);
                            binding.rvSearchKirimBarangGudang.setHasFixedSize(true);
                            binding.rvSearchKirimBarangGudang.setLayoutManager(new LinearLayoutManager(KirimBarangGudangActivity.this));
                            binding.rvSearchKirimBarangGudang.setAdapter(adapter);



                        } else {
                            binding.txtDataKosongKirimBarangGudang.setVisibility(View.VISIBLE);
                            binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(KirimBarangGudangActivity.this, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                        binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangByNama> call, Throwable t) {
                    Toast.makeText(KirimBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                }
            });

        }

    }

    private void loadSearchById(String newText) {
        ProgressDialog progressDialogBarang = new ProgressDialog(KirimBarangGudangActivity.this);
        progressDialogBarang.setMessage("Mencari data barang");
        progressDialogBarang.show();

        if (newText.equals("")){
            progressDialogBarang.dismiss();
            binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
            binding.txtDataKosongKirimBarangGudang.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.cariBarangById(newText).enqueue(new Callback<ResponseCariBarangById>() {
                @Override
                public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                    if (response.isSuccessful()) {

                        progressDialogBarang.dismiss();

                        int status = response.body().getStatus();
                        List<SearchBarangByIdItem> dataBarang = response.body().getSearchBarangById();

                        if (status == 1) {

                            binding.rvSearchKirimBarangGudang.setVisibility(View.VISIBLE);
                            binding.txtDataKosongKirimBarangGudang.setVisibility(View.GONE);
                            CariKirimBarangIdAdapter adapter = new CariKirimBarangIdAdapter(KirimBarangGudangActivity.this, dataBarang, KirimBarangGudangActivity.this);
                            binding.rvSearchKirimBarangGudang.setHasFixedSize(true);
                            binding.rvSearchKirimBarangGudang.setLayoutManager(new LinearLayoutManager(KirimBarangGudangActivity.this));
                            binding.rvSearchKirimBarangGudang.setAdapter(adapter);



                        } else {
                            binding.txtDataKosongKirimBarangGudang.setText("Data pencarian dengan nama" +
                                    " '"+newText+"' "+"tidak ditemukan atau\ntidak ada " +
                                    "dalam data toko ini");
                            binding.txtDataKosongKirimBarangGudang.setVisibility(View.VISIBLE);
                            binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                        }

                    } else {
                        progressDialogBarang.dismiss();
                        Toast.makeText(KirimBarangGudangActivity.this, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                        binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                    progressDialogBarang.dismiss();
                    Toast.makeText(KirimBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.rvSearchKirimBarangGudang.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (searchId = true){
            loadSearchById(textId);
        }else if (searchName = true){
            loadDataCari(textName);
        }

    }


}