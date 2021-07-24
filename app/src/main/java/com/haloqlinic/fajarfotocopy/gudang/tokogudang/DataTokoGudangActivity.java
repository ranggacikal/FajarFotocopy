package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataTokoAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailBarangStockGudangBinding;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataTokoGudangActivity extends AppCompatActivity {

    private ActivityDataTokoGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loadDataToko();

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    private void loadDataToko() {

        ProgressDialog progressDialog = new ProgressDialog(DataTokoGudangActivity.this);
        progressDialog.setMessage("Memuat data toko");
        progressDialog.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataTokoItem> dataTokoItems = response.body().getDataToko();
                        DataTokoAdapter adapter = new DataTokoAdapter(DataTokoGudangActivity.this, dataTokoItems);
                        binding.recyclerDataToko.setHasFixedSize(true);
                        binding.recyclerDataToko.setLayoutManager(new LinearLayoutManager(DataTokoGudangActivity.this));
                        binding.recyclerDataToko.setAdapter(adapter);

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(DataTokoGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DataTokoGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataTokoGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}