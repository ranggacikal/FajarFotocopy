package com.haloqlinic.fajarfotocopy.gudang.mintabarang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.mintabarang.MintaBarangAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataMintaBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityUserGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet.DataBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet.DataPermintaanBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet.ResponseMintaBarangByOutlet;
import com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman.ResponseLastIdStatusPengiriamn;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.ResponseListPengiriman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMintaBarangActivity extends AppCompatActivity {

    ActivityDataMintaBarangBinding binding;
    public String id_outlet = "";
    public String id_status_pengiriman = "";

    private SharedPreferencedConfig preferencedConfig;
    String size = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataMintaBarangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        preferencedConfig = new SharedPreferencedConfig(this);
        setContentView(view);
        GridLayoutManager manager = new GridLayoutManager(DataMintaBarangActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.rvDataMintaBarang.setLayoutManager(manager);
        binding.rvDataMintaBarang.setHasFixedSize(true);
        id_outlet = getIntent().getStringExtra("id_toko");
        getDataMintaBarang(id_outlet);
        getLastIdStatusPengiriman();
    }

    private void getLastIdStatusPengiriman() {

        ConfigRetrofit.service.lastIdStatusPengiriman().enqueue(new Callback<ResponseLastIdStatusPengiriamn>() {
            @Override
            public void onResponse(Call<ResponseLastIdStatusPengiriamn> call, Response<ResponseLastIdStatusPengiriamn> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        id_status_pengiriman = response.body().getDataIdStatusPengiriman().getIdStatusPengiriman();
                        preferencedConfig.savePrefString(SharedPreferencedConfig
                                .PREFERENCE_LAST_ID_STATUS_PENGIRIMAN, id_status_pengiriman);
                        loadDataPengiriman();
                        Log.d("cekIdStatusPengiriman", "onResponse: "+id_status_pengiriman);
                    }else{
                        Log.e("lastIdStatusPengiriman", "onResponse: Data Kosong");
                    }

                }else{
                    Log.e("lastIdStatusPengiriman", "onResponse: Terjadi kesalahan" );
                }
            }

            @Override
            public void onFailure(Call<ResponseLastIdStatusPengiriamn> call, Throwable t) {
                Log.d("lastIdStatusPengiriman", "onFailure: "+t.getMessage());
            }
        });

    }

    public void loadDataPengiriman() {

        Log.d("cekPrefIdStatusPengiriman", "loadDataPengiriman: "+
                preferencedConfig.getPreferenceLastIdStatusPengiriman());

        ConfigRetrofit.service.listPengiriman(preferencedConfig.getPreferenceLastIdStatusPengiriman(),
                getIntent().getStringExtra("tanggal"))
                .enqueue(new Callback<ResponseListPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseListPengiriman> call, Response<ResponseListPengiriman> response) {
                        if (response.isSuccessful()){

                            int status = response.body().getStatus();

                            size = String.valueOf(status);

                            List<GetListPengirimanItem> dataPengiriman = response.body().getGetListPengiriman();

                            if (status==1){
                                Log.d("cekSize", "onResponse: "+size);
                                Log.d("cekSize", "onResponse: "+dataPengiriman.toString());
                            }else{
                                Log.d("LoadDataPengiriman", "onResponse: Status = "+status);
                            }

                        }else{
                            Log.d("LoadDataPengiriman", "onResponse: Response = False");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListPengiriman> call, Throwable t) {
                        Log.e("LoadDataPengiriman", "onFailure: "+t.getMessage() );
                    }
                });

    }

    public void getDataMintaBarang(String id_outlet) {

        ProgressDialog progressDialog = new ProgressDialog(DataMintaBarangActivity.this);
        progressDialog.setMessage("Memuat Data Barang");
        progressDialog.show();

        ConfigRetrofit.service.mintaBarangByOutlet(id_outlet).enqueue(new Callback<ResponseMintaBarangByOutlet>() {
            @Override
            public void onResponse(Call<ResponseMintaBarangByOutlet> call, Response<ResponseMintaBarangByOutlet> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if(status == 1){
                        List<DataPermintaanBarangItem> dataMintaBarang = response.body().getDataPermintaanBarang();
                        List<DataBarangItem> dataBarangItems = null;
                        String nama_toko = "";

                        for (int a = 0; a<dataMintaBarang.size(); a++){
                            dataBarangItems = dataMintaBarang.get(a).getDataBarang();
                            nama_toko = dataMintaBarang.get(a).getNamaOutlet();
                        }
                        binding.tvNamaTokoDataMintaBarang.setText(nama_toko);
                        MintaBarangAdapter adapter = new MintaBarangAdapter(DataMintaBarangActivity.this,
                                dataBarangItems, DataMintaBarangActivity.this);
                        binding.rvDataMintaBarang.setAdapter(adapter);
                    }else{
                        Toast.makeText(DataMintaBarangActivity.this, "Error Saat Mengambil Data",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DataMintaBarangActivity.this, "Terjadi kesalahan di jaringan",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMintaBarangByOutlet> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("cekFailure", "onFailure: "+t.getMessage());
                Toast.makeText(DataMintaBarangActivity.this, "Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
}