package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataTokoAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataUserAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.DataTokoGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataUser.DataUserItem;
import com.haloqlinic.fajarfotocopy.model.dataUser.ResponseDataUser;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataUserGudangActivity extends AppCompatActivity {

    private ActivityDataUserGudangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        loadDataUser();


    }

    private void loadDataUser() {
        ProgressDialog progressDialog = new ProgressDialog(DataUserGudangActivity.this);
        progressDialog.setMessage("Memuat Data User...");
        progressDialog.show();

        ConfigRetrofit.service.dataUser().enqueue(new Callback<ResponseDataUser>() {
            @Override
            public void onResponse(Call<ResponseDataUser> call, Response<ResponseDataUser> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataUserItem> dataUserItems = response.body().getDataUser();
                        DataUserAdapter adapter = new DataUserAdapter(DataUserGudangActivity.this, dataUserItems);
                        binding.recyclerDataUserGudang.setHasFixedSize(true);
                        binding.recyclerDataUserGudang.setLayoutManager(new LinearLayoutManager(DataUserGudangActivity.this));
                        binding.recyclerDataUserGudang.setAdapter(adapter);

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(DataUserGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DataUserGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUser> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(DataUserGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}