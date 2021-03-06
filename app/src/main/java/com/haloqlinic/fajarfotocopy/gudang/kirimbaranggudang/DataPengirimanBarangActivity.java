package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.DataPengirimanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPengirimanBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKirimBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.ResponseListPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataPengirimanBarangActivity extends AppCompatActivity {

    private ActivityDataPengirimanBarangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataPengirimanBarangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        loadDataPengiriman();
    }

    private void loadDataPengiriman() {

        String id_status = getIntent().getStringExtra("id_status_pengiriman");
        String tanggal = getIntent().getStringExtra("tanggal_status_transaksi");

        ConfigRetrofit.service.listPengiriman(id_status, tanggal).enqueue(new Callback<ResponseListPengiriman>() {
            @Override
            public void onResponse(Call<ResponseListPengiriman> call, Response<ResponseListPengiriman> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    List<GetListPengirimanItem> dataPengiriman = response.body().getGetListPengiriman();

                    if (dataPengiriman != null) {

                        String nama_toko = response.body().getGetListPengiriman().get(0).getNamaOutlet();
                        String tanggalPengiriman = response.body().getGetListPengiriman().get(0).getTanggalPengiriman();

                        if (tanggal != null || nama_toko != null){

                            binding.textNamaTokoDataPengiriman.setText(nama_toko);
                            binding.textTanggalDataPengiriman.setText(tanggalPengiriman);

                        }

                    }else{
                        binding.textTanggalDataPengiriman.setText("null");
                        binding.textNamaTokoDataPengiriman.setText("null");
                    }



                    if (status==1){

                        DataPengirimanAdapter adapter = new DataPengirimanAdapter(DataPengirimanBarangActivity.this, dataPengiriman);
                        binding.rvDataPengirimanBarang.setHasFixedSize(true);
                        binding.rvDataPengirimanBarang.setLayoutManager(new LinearLayoutManager(DataPengirimanBarangActivity.this));
                        binding.rvDataPengirimanBarang.setAdapter(adapter);

                    }else{
                        Toast.makeText(DataPengirimanBarangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(DataPengirimanBarangActivity.this, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListPengiriman> call, Throwable t) {
                Toast.makeText(DataPengirimanBarangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}