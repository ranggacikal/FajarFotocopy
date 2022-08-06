package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kasir.TransaksiBerhasilAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailBarangStockGudangBinding;
import com.haloqlinic.fajarfotocopy.kasir.KasirMainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.KetoMainActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.DetailStatusPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.ResponseDetailStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CetakBuktiKasirActivity extends AppCompatActivity {

    private ActivityCetakBuktiKasirBinding binding;

    int diskon = 0;
    int total = 0;

    String id_status_penjualan = "";

    String from_keto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCetakBuktiKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");
        from_keto = getIntent().getStringExtra("from_keto");

        binding.recyclerCetakBukti.setHasFixedSize(true);
        binding.recyclerCetakBukti.setLayoutManager(new LinearLayoutManager(CetakBuktiKasirActivity.this));

        PushDownAnim.setPushDownAnimTo(binding.btnBackHomeCetakBukti)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("from_keto", "onClick: "+from_keto);

                        if (from_keto!=null){
                            if (from_keto.equals("HomeKeto")){
                                Intent intent = new Intent(CetakBuktiKasirActivity.this, KetoMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent2 = new Intent(CetakBuktiKasirActivity.this, KasirMainActivity.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent2);
                                finish();
                            }
                        }else {

                            Intent intent3 = new Intent(CetakBuktiKasirActivity.this, KasirMainActivity.class);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent3);
                            finish();

                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnCetakBuktiPenjualan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CetakBuktiKasirActivity.this, InvoiceKasirActivity.class);
                        intent.putExtra("id_status_penjualan", id_status_penjualan);
                        startActivity(intent);
                    }
                });

        loadData();

    }

    private void createPdf() {



    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(CetakBuktiKasirActivity.this);
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
                        TransaksiBerhasilAdapter adapter = new TransaksiBerhasilAdapter(CetakBuktiKasirActivity.this, dataDetail);
                        binding.recyclerCetakBukti.setAdapter(adapter);

                    }else{
                        Toast.makeText(CetakBuktiKasirActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(CetakBuktiKasirActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailStatusPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CetakBuktiKasirActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}