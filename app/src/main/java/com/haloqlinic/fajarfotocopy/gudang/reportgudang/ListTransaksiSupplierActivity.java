package com.haloqlinic.fajarfotocopy.gudang.reportgudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.supplier.StatusSupplierBulanAdapter;
import com.haloqlinic.fajarfotocopy.adapter.supplier.StatusSupplierTanggalAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityListStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityListTransaksiSupplierBinding;
import com.haloqlinic.fajarfotocopy.model.statusSupplierBulan.ResponseStatusSupplierByBulan;
import com.haloqlinic.fajarfotocopy.model.statusSupplierBulan.StatusPenjualanGudangByBulanItem;
import com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal.ResponseStatusSupplierTanggal;
import com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal.StatusPenjualanGudangByTanggalItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTransaksiSupplierActivity extends AppCompatActivity {

    private ActivityListTransaksiSupplierBinding binding;

    public String pilihan, bulan_tahun, tanggal;

    ProgressDialog progressDialog;

    public ListTransaksiSupplierActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListTransaksiSupplierBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        pilihan = getIntent().getStringExtra("pilihan");
        bulan_tahun = getIntent().getStringExtra("bulan_tahun");
        tanggal = getIntent().getStringExtra("tanggal");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        binding.linearBackListStatusSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvListStatusSupplier.setHasFixedSize(true);
        binding.rvListStatusSupplier.setLayoutManager(new LinearLayoutManager(this));

        if (pilihan.equals("Hari")){
            loadDataHari();
        }else{
            loadData();
        }
    }

    private void loadDataHari() {

        ConfigRetrofit.service.statusSupplierByTanggal(tanggal)
                .enqueue(new Callback<ResponseStatusSupplierTanggal>() {
                    @Override
                    public void onResponse(Call<ResponseStatusSupplierTanggal> call, Response<ResponseStatusSupplierTanggal> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if(status==1){

                                List<StatusPenjualanGudangByTanggalItem> dataPenjualan
                                        = response.body().getStatusPenjualanGudangByTanggal();
                                StatusSupplierTanggalAdapter adapterTanggal = new
                                        StatusSupplierTanggalAdapter(
                                        ListTransaksiSupplierActivity.this,
                                        ListTransaksiSupplierActivity.this,
                                        dataPenjualan);
                                binding.rvListStatusSupplier.setAdapter(adapterTanggal);

                            }else{
                                Toast.makeText(ListTransaksiSupplierActivity.this,
                                        "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ListTransaksiSupplierActivity.this,
                                    "Response Error From Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusSupplierTanggal> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ListTransaksiSupplierActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadData() {

        ConfigRetrofit.service.statusSupplierByBulan(bulan_tahun)
                .enqueue(new Callback<ResponseStatusSupplierByBulan>() {
                    @Override
                    public void onResponse(Call<ResponseStatusSupplierByBulan> call,
                                           Response<ResponseStatusSupplierByBulan> response) {

                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if(status==1){

                                List<StatusPenjualanGudangByBulanItem> dataPenjualan
                                        = response.body().getStatusPenjualanGudangByBulan();
                                StatusSupplierBulanAdapter adapterBulan = new
                                        StatusSupplierBulanAdapter(
                                                ListTransaksiSupplierActivity.this,
                                                        dataPenjualan,
                                        ListTransaksiSupplierActivity.this);
                                binding.rvListStatusSupplier.setAdapter(adapterBulan);

                            }else{
                                Toast.makeText(ListTransaksiSupplierActivity.this,
                                        "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ListTransaksiSupplierActivity.this,
                                    "Response Error From Server", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseStatusSupplierByBulan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ListTransaksiSupplierActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}