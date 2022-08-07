package com.haloqlinic.fajarfotocopy.kepalatoko.listtransferketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.DetailTransferAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailListTransferKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityNotifikasiGudangBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.DetailPengirimanKetoActivity;
import com.haloqlinic.fajarfotocopy.model.listTransfer.ListTransferBarangItem;
import com.haloqlinic.fajarfotocopy.model.listTransfer.ResponseListTransfer;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListTransferKetoActivity extends AppCompatActivity {

    private ActivityDetailListTransferKetoBinding binding;
    public String id_status_transfer, id_outlet, tanggal;

    public DetailListTransferKetoActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailListTransferKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_transfer = getIntent().getStringExtra("id_status_transfer");
        id_outlet = getIntent().getStringExtra("id_outlet");
        tanggal = getIntent().getStringExtra("tanggal_transfer");

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailListTransferKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerDetailListTransferKeto.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(DetailListTransferKetoActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerDetailListTransferKeto.setLayoutManager(manager);

        loadData();
    }

    public void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(DetailListTransferKetoActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        Log.d("cekIdStatus", "loadData: "+id_status_transfer);

        ConfigRetrofit.service.listTransfer(id_status_transfer).enqueue(new Callback<ResponseListTransfer>() {
            @Override
            public void onResponse(Call<ResponseListTransfer> call, Response<ResponseListTransfer> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<ListTransferBarangItem> dataTransfer = response.body().getListTransferBarang();
                        DetailTransferAdapter adapter = new DetailTransferAdapter(DetailListTransferKetoActivity.this,
                                dataTransfer, DetailListTransferKetoActivity.this);
                        binding.recyclerDetailListTransferKeto.setAdapter(adapter);

                    }else{
                        Toast.makeText(DetailListTransferKetoActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailListTransferKetoActivity.this,
                            "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListTransfer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailListTransferKetoActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}