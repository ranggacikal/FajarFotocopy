package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.StatusPengirimanBulanAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.StatusPengirimanTanggalAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityListStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityLoginBinding;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByBulan.GetStatusPengirimanByBulanItem;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByBulan.ResponseStatusPengirimanByBulan;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByTanggal.GetStatusPengirimanByTanggalItem;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByTanggal.ResponseStatusPengirimanByTanggal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStatusPengirimanActivity extends AppCompatActivity {

    private ActivityListStatusPengirimanBinding binding;

    public String pilihan, bulan_tahun, tanggal;

    ProgressDialog progressDialog;

    public ListStatusPengirimanActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListStatusPengirimanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        pilihan = getIntent().getStringExtra("pilihan");
        bulan_tahun = getIntent().getStringExtra("bulan_tahun");
        tanggal = getIntent().getStringExtra("tanggal");

        binding.rvListStatusPengiriman.setHasFixedSize(true);
        binding.rvListStatusPengiriman.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data Transaksi Pengiriman");
        progressDialog.show();

        if (pilihan.equals("Bulan")){
            loadBulan();
        }else{
            loadHari();
        }

        binding.linearBackListStatusPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadHari() {

        ConfigRetrofit.service.statusPengirimanByTanggal(tanggal)
                .enqueue(new Callback<ResponseStatusPengirimanByTanggal>() {
                    @Override
                    public void onResponse(Call<ResponseStatusPengirimanByTanggal> call, Response<ResponseStatusPengirimanByTanggal> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                List<GetStatusPengirimanByTanggalItem> pengirimanTanggal =
                                        response.body().getGetStatusPengirimanByTanggal();
                                StatusPengirimanTanggalAdapter adapterTanggal = new StatusPengirimanTanggalAdapter(
                                        ListStatusPengirimanActivity.this,
                                        pengirimanTanggal,
                                        ListStatusPengirimanActivity.this
                                );
                                binding.rvListStatusPengiriman.setAdapter(adapterTanggal);

                            }else{
                                Toast.makeText(ListStatusPengirimanActivity.this,
                                        "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ListStatusPengirimanActivity.this,
                                    "Response Error From Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusPengirimanByTanggal> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ListStatusPengirimanActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadBulan() {
        Log.d("cekBulanList", "loadBulan: "+bulan_tahun);
        ConfigRetrofit.service.statusPengirimanByBulan(bulan_tahun)
                .enqueue(new Callback<ResponseStatusPengirimanByBulan>() {
                    @Override
                    public void onResponse(Call<ResponseStatusPengirimanByBulan> call, Response<ResponseStatusPengirimanByBulan> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                List<GetStatusPengirimanByBulanItem> dataPengirimanBulan =
                                        response.body().getGetStatusPengirimanByBulan();
                                StatusPengirimanBulanAdapter adapterBulan = new StatusPengirimanBulanAdapter(
                                        ListStatusPengirimanActivity.this,
                                        dataPengirimanBulan,
                                        ListStatusPengirimanActivity.this
                                );
                                binding.rvListStatusPengiriman.setAdapter(adapterBulan);

                            }else{
                                Toast.makeText(ListStatusPengirimanActivity.this,
                                        "Data Pengiriman Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ListStatusPengirimanActivity.this,
                                    "Response Error From Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusPengirimanByBulan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ListStatusPengirimanActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pilihan.equals("Bulan")){
            loadBulan();
        }else{
            loadHari();
        }
    }
}