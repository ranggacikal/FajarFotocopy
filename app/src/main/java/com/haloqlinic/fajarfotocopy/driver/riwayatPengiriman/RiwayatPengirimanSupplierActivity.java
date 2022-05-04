package com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.driver.PengirimanSupplierSelesaiAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityRiwayatPengirimanSupplierBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityRiwayatPengirimanTokoBinding;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangSelesai.ResponseStatusPenjualanGudangSelesai;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangSelesai.StatusPenjualanGudangSelesaiItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class RiwayatPengirimanSupplierActivity extends AppCompatActivity {

    private ActivityRiwayatPengirimanSupplierBinding binding;
    private SharedPreferencedConfig preferencedConfig;
    String dateIntent, monthIntent, pilihanIntent, fromReportDriver, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiwayatPengirimanSupplierBinding.inflate(
                getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dateIntent = getIntent().getStringExtra("tanggal");
        monthIntent = getIntent().getStringExtra("bulan_tahun");
        pilihanIntent = getIntent().getStringExtra("pilihan");
        fromReportDriver = getIntent().getStringExtra("fromReportDriver");

        if (pilihanIntent.equals("Hari")){
            tanggal = dateIntent;
        }else{
            tanggal = monthIntent;
        }

        preferencedConfig = new SharedPreferencedConfig(this);
        binding.rvPengirimanKeSupplier.setHasFixedSize(true);
        binding.rvPengirimanKeSupplier.setLayoutManager(new LinearLayoutManager(
                this));

        PushDownAnim.setPushDownAnimTo(binding.linearBackRiwayatDriverSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


        loadData();

    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.statusPenjualanGudanSelesai(
                preferencedConfig.getPreferenceIdUser(), tanggal
        ).enqueue(new Callback<ResponseStatusPenjualanGudangSelesai>() {
            @Override
            public void onResponse(
                    Call<ResponseStatusPenjualanGudangSelesai> call,
                    Response<ResponseStatusPenjualanGudangSelesai> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<StatusPenjualanGudangSelesaiItem> dataSelesai =
                                response.body()
                                        .getStatusPenjualanGudangSelesai();

                        PengirimanSupplierSelesaiAdapter adapter = new
                                PengirimanSupplierSelesaiAdapter(
                                RiwayatPengirimanSupplierActivity.this,
                                dataSelesai
                        );
                        binding.rvPengirimanKeSupplier.setAdapter(adapter);

                    } else {
                        Toast.makeText(
                                RiwayatPengirimanSupplierActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RiwayatPengirimanSupplierActivity.this,
                            "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(
                    Call<ResponseStatusPenjualanGudangSelesai> call,
                    Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(RiwayatPengirimanSupplierActivity.this,
                        "Koneksi error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}