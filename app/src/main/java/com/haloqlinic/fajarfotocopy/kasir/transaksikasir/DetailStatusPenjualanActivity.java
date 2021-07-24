package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kasir.DetailSPAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailPengirimanKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailStatusPenjualanBinding;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.DetailStatusPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.ResponseDetailStatusPenjualan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStatusPenjualanActivity extends AppCompatActivity {

    private ActivityDetailStatusPenjualanBinding binding;

    String id_status_penjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStatusPenjualanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");
        
        binding.recyclerDetailStatusPenjualan.setHasFixedSize(true);
        binding.recyclerDetailStatusPenjualan.setLayoutManager(new LinearLayoutManager(DetailStatusPenjualanActivity.this));
        
        loadData();
        
    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(DetailStatusPenjualanActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.detailStatusPenjualan(id_status_penjualan).enqueue(new Callback<ResponseDetailStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDetailStatusPenjualan> call, Response<ResponseDetailStatusPenjualan> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DetailStatusPenjualanItem> dataDetail = response.body().getDetailStatusPenjualan();
                        DetailSPAdapter adapter = new DetailSPAdapter(DetailStatusPenjualanActivity.this, dataDetail);
                        binding.recyclerDetailStatusPenjualan.setAdapter(adapter);

                    }else{
                        Toast.makeText(DetailStatusPenjualanActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailStatusPenjualanActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailStatusPenjualan> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(DetailStatusPenjualanActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}