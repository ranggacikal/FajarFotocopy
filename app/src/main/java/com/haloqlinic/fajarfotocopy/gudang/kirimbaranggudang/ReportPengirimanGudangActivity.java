package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.ReportPengirimanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPengirimanBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.DataStatusPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.ResponseDataStatusPengiriman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPengirimanGudangActivity extends AppCompatActivity {

    private ActivityReportPengirimanGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportPengirimanGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvReportPengiriman.setHasFixedSize(true);
        binding.rvReportPengiriman.setLayoutManager(new LinearLayoutManager(ReportPengirimanGudangActivity.this));

        loadRecycler();

    }

    private void loadRecycler() {

        ProgressDialog progressDialog = new ProgressDialog(ReportPengirimanGudangActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.statusPengiriman().enqueue(new Callback<ResponseDataStatusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseDataStatusPengiriman> call, Response<ResponseDataStatusPengiriman> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    List<DataStatusPengirimanItem> dataPengiriman = response.body().getDataStatusPengiriman();
                    ReportPengirimanAdapter adapter = new ReportPengirimanAdapter(ReportPengirimanGudangActivity.this, dataPengiriman);
                    binding.rvReportPengiriman.setAdapter(adapter);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(ReportPengirimanGudangActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataStatusPengiriman> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ReportPengirimanGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}