package com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.PengirimanKetoAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityListPengirimanKetoBinding;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko.ListPengirimanByOutletItem;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko.ResponseStatusPengirimanByToko;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.haloqlinic.fajarfotocopy.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ListPengirimanKetoActivity extends AppCompatActivity {

    private ActivityListPengirimanKetoBinding binding;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPengirimanKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackListPengirimanKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        preferencedConfig = new SharedPreferencedConfig(this);

        binding.recyclerListStatusKeto.setHasFixedSize(true);
        binding.recyclerListStatusKeto.setLayoutManager(new LinearLayoutManager(ListPengirimanKetoActivity.this));

        loadRecycler();

    }

    private void loadRecycler() {

        ProgressDialog progressDialog = new ProgressDialog(ListPengirimanKetoActivity.this);
        progressDialog.setMessage("Memuat Data Pengiriman");
        progressDialog.show();

        ConfigRetrofit.service.statusPengirimanByToko(preferencedConfig.getPreferenceIdOutlet())
                .enqueue(new Callback<ResponseStatusPengirimanByToko>() {
                    @Override
                    public void onResponse(Call<ResponseStatusPengirimanByToko> call, Response<ResponseStatusPengirimanByToko> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                List<ListPengirimanByOutletItem> listPengiriman = response.body().getListPengirimanByOutlet();
                                PengirimanKetoAdapter adapter = new PengirimanKetoAdapter(ListPengirimanKetoActivity.this, listPengiriman);
                                binding.recyclerListStatusKeto.setAdapter(adapter);

                            }else{
                                Toast.makeText(ListPengirimanKetoActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ListPengirimanKetoActivity.this, "Terjadi kesalahn di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseStatusPengirimanByToko> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ListPengirimanKetoActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}