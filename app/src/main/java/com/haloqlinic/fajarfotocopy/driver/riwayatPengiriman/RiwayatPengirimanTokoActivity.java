package com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.driver.PengirimanSelesaiAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityRiwayatPengirimanTokoBinding;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.ResponsePengirimanSelesai;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.StatusPengirimanSelesaiItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPengirimanTokoActivity extends AppCompatActivity {

    private ActivityRiwayatPengirimanTokoBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiwayatPengirimanTokoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);
        binding.rvPengirimanKeToko.setHasFixedSize(true);
        binding.rvPengirimanKeToko.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }


    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.pengirimanSelesai(preferencedConfig.getPreferenceIdUser()).enqueue(new Callback<ResponsePengirimanSelesai>() {
            @Override
            public void onResponse(Call<ResponsePengirimanSelesai> call, Response<ResponsePengirimanSelesai> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<StatusPengirimanSelesaiItem> dataSelesai = response.body().getStatusPengirimanSelesai();
                        PengirimanSelesaiAdapter adapter = new PengirimanSelesaiAdapter(RiwayatPengirimanTokoActivity.this, dataSelesai);
                        binding.rvPengirimanKeToko.setAdapter(adapter);

                    } else {
                        Toast.makeText(RiwayatPengirimanTokoActivity.this,
                                "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RiwayatPengirimanTokoActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengirimanSelesai> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RiwayatPengirimanTokoActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}