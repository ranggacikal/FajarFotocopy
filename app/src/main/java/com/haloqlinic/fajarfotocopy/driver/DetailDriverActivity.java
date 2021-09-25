package com.haloqlinic.fajarfotocopy.driver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.driver.PengirimanDriverAdapter;
import com.haloqlinic.fajarfotocopy.adapter.driver.PengirimanPenjualanGudangAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataInformasiGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailDriverBinding;
import com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus.ListPenjualanGudangByIdStatusItem;
import com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus.ResponseListPenjualanGudangByIdStatus;
import com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver.GetListPengirimanByIdStatusItem;
import com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver.ResponsePengirimanByIdUser;
import com.haloqlinic.fajarfotocopy.model.updateStatusPengiriman.ResponseUpdateStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualanGudang.ResponseUpdateStatusPenjualanGudang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDriverActivity extends AppCompatActivity {

    private ActivityDetailDriverBinding binding;

    String id_status_pengiriman, id_status_penjualan_gudang;
    String status_pengiriman, status_pengiriman_intent;
    String jenis_pengiriman = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDriverBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_pengiriman = getIntent().getStringExtra("id_status_pengiriman");
        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");
        status_pengiriman_intent = getIntent().getStringExtra("status_pengiriman");
        jenis_pengiriman = getIntent().getStringExtra("jenis_pengiriman");

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnSuratJalan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (jenis_pengiriman.equals("toko")) {
                            Intent intent = new Intent(DetailDriverActivity.this, WebViewSuratJalanActivity.class);
                            intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                            startActivity(intent);
                        }else{
                            Toast.makeText(DetailDriverActivity.this, "Surat jalan Supplier masih dalam tahap pembuatan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Log.d("StatusPengirimanIntent", "onCreate: "+status_pengiriman_intent);

        if (status_pengiriman_intent.equals("dalam pengiriman")){
            binding.btnDalamPengirimanDriver.setVisibility(View.GONE);
        }else if (status_pengiriman_intent.equals("pengiriman selesai")){
            binding.btnDalamPengirimanDriver.setVisibility(View.GONE);
            binding.btnSelesaiDriver.setVisibility(View.GONE);
        }

        PushDownAnim.setPushDownAnimTo(binding.btnDalamPengirimanDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_pengiriman = "dalam pengiriman";

                        if (jenis_pengiriman.equals("toko")) {
                            updateStatusPengiriman();
                        }else{
                            updateStatusPengirimanSupplier();
                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnSelesaiDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status_pengiriman = "pengiriman selesai";
                        if (jenis_pengiriman.equals("toko")) {
                            updateStatusPengiriman();
                        }else{
                            updateStatusPengirimanSupplier();
                        }
                    }
                });

        if (jenis_pengiriman.equals("toko")) {

            binding.recyclerDetailPengirimanSupplierDriver.setVisibility(View.GONE);
            binding.recyclerDetailPengirimanDriver.setVisibility(View.VISIBLE);

            binding.recyclerDetailPengirimanDriver.setHasFixedSize(true);
            binding.recyclerDetailPengirimanDriver.setLayoutManager(new LinearLayoutManager(DetailDriverActivity.this));

            loadData();

        }else{
            binding.recyclerDetailPengirimanSupplierDriver.setVisibility(View.VISIBLE);
            binding.recyclerDetailPengirimanDriver.setVisibility(View.GONE);

            binding.recyclerDetailPengirimanSupplierDriver.setHasFixedSize(true);
            binding.recyclerDetailPengirimanSupplierDriver.setLayoutManager(new
                    LinearLayoutManager(DetailDriverActivity.this));

            loadDataSupplier();
        }
    }

    private void updateStatusPengirimanSupplier() {

        ProgressDialog progressDialogUpdate = new ProgressDialog(DetailDriverActivity.this);
        progressDialogUpdate.setMessage("Mengupdate Data");
        progressDialogUpdate.show();

        ConfigRetrofit.service.updateStatusPenjualanGudang(id_status_penjualan_gudang, status_pengiriman)
                .enqueue(new Callback<ResponseUpdateStatusPenjualanGudang>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStatusPenjualanGudang> call, Response<ResponseUpdateStatusPenjualanGudang> response) {
                        if (response.isSuccessful()){

                            progressDialogUpdate.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(DetailDriverActivity.this,
                                        "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                Toast.makeText(DetailDriverActivity.this, "Gagal Update Data",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialogUpdate.dismiss();
                            Toast.makeText(DetailDriverActivity.this, "Terjadi Kesalahan di server",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStatusPenjualanGudang> call, Throwable t) {
                        progressDialogUpdate.dismiss();
                        Toast.makeText(DetailDriverActivity.this, "Koneksi Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadDataSupplier() {

        ProgressDialog progressDialog = new ProgressDialog(DetailDriverActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.penjualanGudangByIdStatus(id_status_penjualan_gudang).enqueue(new Callback<ResponseListPenjualanGudangByIdStatus>() {
            @Override
            public void onResponse(Call<ResponseListPenjualanGudangByIdStatus> call, Response<ResponseListPenjualanGudangByIdStatus> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1) {

                        List<ListPenjualanGudangByIdStatusItem> dataPenjualan = response.body().getListPenjualanGudangByIdStatus();
                        PengirimanPenjualanGudangAdapter adapter = new PengirimanPenjualanGudangAdapter(DetailDriverActivity.this,
                                dataPenjualan);
                        binding.recyclerDetailPengirimanSupplierDriver.setAdapter(adapter);

                    }else{
                        Toast.makeText(DetailDriverActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailDriverActivity.this, "Terjadi Kesalahan Di server",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListPenjualanGudangByIdStatus> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDriverActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateStatusPengiriman() {

        ProgressDialog progressDialogUpdate = new ProgressDialog(DetailDriverActivity.this);
        progressDialogUpdate.setMessage("Mengupdate Data");
        progressDialogUpdate.show();

        ConfigRetrofit.service.updateStatusPengiriman(id_status_pengiriman, status_pengiriman)
                .enqueue(new Callback<ResponseUpdateStatusPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStatusPengiriman> call, Response<ResponseUpdateStatusPengiriman> response) {
                        if (response.isSuccessful()){
                            progressDialogUpdate.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(DetailDriverActivity.this,
                                        "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                Toast.makeText(DetailDriverActivity.this, "Gagal update data",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialogUpdate.dismiss();
                            Toast.makeText(DetailDriverActivity.this, "Terjadi Kesalahan diserver",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStatusPengiriman> call, Throwable t) {
                        progressDialogUpdate.dismiss();
                        Toast.makeText(DetailDriverActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(DetailDriverActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.pengirimanByIdUser(id_status_pengiriman).enqueue(new Callback<ResponsePengirimanByIdUser>() {
            @Override
            public void onResponse(Call<ResponsePengirimanByIdUser> call, Response<ResponsePengirimanByIdUser> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){
                        List<GetListPengirimanByIdStatusItem> dataPengiriman = response.body().getGetListPengirimanByIdStatus();
                        PengirimanDriverAdapter adapter = new PengirimanDriverAdapter(DetailDriverActivity.this, dataPengiriman);
                        binding.recyclerDetailPengirimanDriver.setAdapter(adapter);
                    }else{
                        Toast.makeText(DetailDriverActivity.this, "Data Kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailDriverActivity.this, "Terjadi kesalahan diserver",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengirimanByIdUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailDriverActivity.this, "Error Koneksi",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}