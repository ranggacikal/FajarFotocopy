package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DetailPenjualanGudangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kasir.TransaksiBerhasilAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiSupplierGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityInvoiceKetoBinding;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.CetakBuktiKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.InvoiceKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;
import com.haloqlinic.fajarfotocopy.model.detailPenjualanGudang.DetailPenjualanGudangItem;
import com.haloqlinic.fajarfotocopy.model.detailPenjualanGudang.ResponseDetailPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.DetailStatusPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.ResponseDetailStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakBuktiSupplierGudangActivity extends AppCompatActivity {

    private ActivityCetakBuktiSupplierGudangBinding binding;

    int diskon = 0;
    int total = 0;

    String id_status_penjualan = "";

    String from_gudang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCetakBuktiSupplierGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan_gudang");
        from_gudang = getIntent().getStringExtra("from_gudang");

        binding.recyclerCetakBuktiGudang.setHasFixedSize(true);
        binding.recyclerCetakBuktiGudang.setLayoutManager(new LinearLayoutManager(CetakBuktiSupplierGudangActivity.this));



        PushDownAnim.setPushDownAnimTo(binding.linearBackCetakBuktiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CetakBuktiSupplierGudangActivity.this, MainActivity.class));
                        finish();
                    }
                });


        PushDownAnim.setPushDownAnimTo(binding.btnCetakBuktiPenjualanGudang)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CetakBuktiSupplierGudangActivity.this, InvoiceSupplierActivity.class);
                        intent.putExtra("id_status_penjualan", id_status_penjualan);
                        startActivity(intent);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnBackHomeCetakBuktiGudang)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CetakBuktiSupplierGudangActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        loadData();

    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(CetakBuktiSupplierGudangActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.detailPenjualanGudang(id_status_penjualan).enqueue(new Callback<ResponseDetailPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseDetailPenjualanGudang> call, Response<ResponseDetailPenjualanGudang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    int status = response.body().getStatus();
                    if (status==1){

                        List<DetailPenjualanGudangItem> dataPenjualan = response.body().getDetailPenjualanGudang();
                        DetailPenjualanGudangAdapter adapter = new DetailPenjualanGudangAdapter(CetakBuktiSupplierGudangActivity.this,
                                dataPenjualan);
                        binding.recyclerCetakBuktiGudang.setAdapter(adapter);

                    }else{
                        Toast.makeText(CetakBuktiSupplierGudangActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(CetakBuktiSupplierGudangActivity.this,
                            "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPenjualanGudang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CetakBuktiSupplierGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}