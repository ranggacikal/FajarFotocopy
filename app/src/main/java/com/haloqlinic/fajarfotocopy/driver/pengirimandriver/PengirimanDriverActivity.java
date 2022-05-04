package com.haloqlinic.fajarfotocopy.driver.pengirimandriver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.driver.StatusPengirimanDriverAdapter;
import com.haloqlinic.fajarfotocopy.adapter.driver.StatusPenjualanGudangAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPengirimanDriverBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.GetStatusPengirimanByIdUserItem;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.ResponseStatusPengirimanByIdUser;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangByIdUser.ResponseStatusPenjualanGudangByIdUser;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangByIdUser.StatusPenjualanGudangByIdUserItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengirimanDriverActivity extends AppCompatActivity {
    private ActivityPengirimanDriverBinding binding;


    private SharedPreferencedConfig preferencedConfig;

    String jenis_pengiriman = "";
    String dateIntent, monthIntent, pilihanIntent, fromReportDriver, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengirimanDriverBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        jenis_pengiriman = getIntent().getStringExtra("jenisPengiriman");
        dateIntent = getIntent().getStringExtra("tanggal");
        monthIntent = getIntent().getStringExtra("bulan_tahun");
        pilihanIntent = getIntent().getStringExtra("pilihan");
        fromReportDriver = getIntent().getStringExtra("fromReportDriver");

        if (pilihanIntent.equals("Hari")){
            tanggal = dateIntent;
        }else{
            tanggal = monthIntent;
        }


        preferencedConfig = new SharedPreferencedConfig(PengirimanDriverActivity.this);

        Log.d("cekIdDriver", "loadDataSupplier: "+preferencedConfig.getPreferenceIdUser());

        PushDownAnim.setPushDownAnimTo(binding.linearBackPengirimanDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        if (jenis_pengiriman.equals("toko")) {

            binding.recyclerDataPengirimanSupplierDriver.setVisibility(View.GONE);
            binding.recyclerDataPengirimanDriver.setVisibility(View.VISIBLE);

            binding.recyclerDataPengirimanDriver.setHasFixedSize(true);
            binding.recyclerDataPengirimanDriver.setLayoutManager(new LinearLayoutManager(PengirimanDriverActivity.this));

            loadDataToko();

        }else{
            binding.recyclerDataPengirimanSupplierDriver.setVisibility(View.VISIBLE);
            binding.recyclerDataPengirimanDriver.setVisibility(View.GONE);

            binding.recyclerDataPengirimanSupplierDriver.setHasFixedSize(true);
            binding.recyclerDataPengirimanSupplierDriver.setLayoutManager(new LinearLayoutManager(PengirimanDriverActivity.this));
            loadDataSupplier();
        }
        


    }


    private void loadDataSupplier(){

        ProgressDialog progressDialog = new ProgressDialog(PengirimanDriverActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.statusPenjualanGudangByIdUser(preferencedConfig.getPreferenceIdUser(), tanggal)
                .enqueue(new Callback<ResponseStatusPenjualanGudangByIdUser>() {
                    @Override
                    public void onResponse(Call<ResponseStatusPenjualanGudangByIdUser> call, Response<ResponseStatusPenjualanGudangByIdUser> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(PengirimanDriverActivity.this, "Berhasil Ambil Data", Toast.LENGTH_SHORT).show();

                                List<StatusPenjualanGudangByIdUserItem> dataPenjualan = response.body().getStatusPenjualanGudangByIdUser();
                                StatusPenjualanGudangAdapter adapter = new StatusPenjualanGudangAdapter(PengirimanDriverActivity.this, dataPenjualan);
                                binding.recyclerDataPengirimanSupplierDriver.setAdapter(adapter);

                            }else{
                                Toast.makeText(PengirimanDriverActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(PengirimanDriverActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusPenjualanGudangByIdUser> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PengirimanDriverActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void loadDataToko() {

        ProgressDialog progressDialog = new ProgressDialog(PengirimanDriverActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        Log.d("cekIdUser", "loadDataToko: "+preferencedConfig.getPreferenceIdUser());

        ConfigRetrofit.service.statusPengirimanByIdUser(preferencedConfig.getPreferenceIdUser(), tanggal)
                .enqueue(new Callback<ResponseStatusPengirimanByIdUser>() {
                    @Override
                    public void onResponse(Call<ResponseStatusPengirimanByIdUser> call, Response<ResponseStatusPengirimanByIdUser> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status==1){

                                List<GetStatusPengirimanByIdUserItem> dataStatusPengiriman = response.body()
                                        .getGetStatusPengirimanByIdUser();
                                StatusPengirimanDriverAdapter adapter = new StatusPengirimanDriverAdapter
                                        (PengirimanDriverActivity.this, dataStatusPengiriman);
                                binding.recyclerDataPengirimanDriver.setAdapter(adapter);

                            }else{
                                Toast.makeText(PengirimanDriverActivity.this, "Data Kosong",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(PengirimanDriverActivity.this, "Terjadi Kesalahan di server",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusPengirimanByIdUser> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PengirimanDriverActivity.this, "Koneksi Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (jenis_pengiriman.equals("toko")){
            loadDataToko();
        }else{
            loadDataSupplier();
        }

    }
}