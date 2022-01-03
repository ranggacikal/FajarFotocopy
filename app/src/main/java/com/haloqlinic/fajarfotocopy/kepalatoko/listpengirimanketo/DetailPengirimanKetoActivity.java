package com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.DetailPengirimanKetoAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailPengirimanKetoBinding;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.ResponseListPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailPengirimanKetoActivity extends AppCompatActivity {

    private ActivityDetailPengirimanKetoBinding binding;
    public String id_status, tanggal, status_pengiriman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPengirimanKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailListPengirimanKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


        id_status = getIntent().getStringExtra("id_status_pengiriman");
        tanggal = getIntent().getStringExtra("tanggal");

        Log.d("cekDataPengiriman", "status: "+id_status);
        Log.d("cekDataPengiriman", "tanggal: "+tanggal);
        status_pengiriman = getIntent().getStringExtra("status_pengiriman");

//        Log.d("cekStatusPengirimanIntent", "onCreate: "+status_pengiriman);

        binding.recyclerDetailPengirimanKeto.setHasFixedSize(true);
        binding.recyclerDetailPengirimanKeto.setLayoutManager(new LinearLayoutManager(DetailPengirimanKetoActivity.this));

        loadDetailPengiriman();

    }

    public void loadDetailPengiriman() {

        ProgressDialog progressDialog = new ProgressDialog(DetailPengirimanKetoActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.listPengiriman(id_status, tanggal).enqueue(new Callback<ResponseListPengiriman>() {
            @Override
            public void onResponse(Call<ResponseListPengiriman> call, Response<ResponseListPengiriman> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<GetListPengirimanItem> listPengiriman = response.body().getGetListPengiriman();
                        DetailPengirimanKetoAdapter adapter = new DetailPengirimanKetoAdapter(DetailPengirimanKetoActivity.this,
                                listPengiriman, DetailPengirimanKetoActivity.this);
                        binding.recyclerDetailPengirimanKeto.setAdapter(adapter);

                    }else{
                        Toast.makeText(DetailPengirimanKetoActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailPengirimanKetoActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListPengiriman> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailPengirimanKetoActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}