package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.ReportPengirimanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.DataStatusPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.ResponseDataStatusPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ReportPengKirimanGudangActivity extends AppCompatActivity {

    private ActivityReportPengirimanGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportPengirimanGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvReportPengiriman.setHasFixedSize(true);
        binding.rvReportPengiriman.setLayoutManager(new LinearLayoutManager(ReportPengirimanGudangActivity.this));

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportPengirimanGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        loadStatusPengiriman();
    }

    private void loadStatusPengiriman() {

        ProgressDialog progressDialog = new ProgressDialog(ReportPengirimanGudangActivity.this);
        progressDialog.setMessage("Load Report Pengiriman");
        progressDialog.show();

        ConfigRetrofit.service.statusPengiriman().enqueue(new Callback<ResponseDataStatusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseDataStatusPengiriman> call, Response<ResponseDataStatusPengiriman> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){
                        List<DataStatusPengirimanItem> dataStatusPengiriman = response.body().getDataStatusPengiriman();
                        ReportPengirimanAdapter adapter = new ReportPengirimanAdapter(ReportPengirimanGudangActivity.this, dataStatusPengiriman);
                        binding.rvReportPengiriman.setAdapter(adapter);
                    }else{
                        Toast.makeText(ReportPengirimanGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(ReportPengirimanGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
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