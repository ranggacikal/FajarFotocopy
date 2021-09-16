package com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.KeranjangTransferAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCekStockBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKeranjangTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.model.detailTransferBarang.DetailStatusTransferItem;
import com.haloqlinic.fajarfotocopy.model.detailTransferBarang.ResponseDetailTransferBarang;
import com.haloqlinic.fajarfotocopy.model.editStatusBarangTransfer.ResponseEditStatusBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.editStatusTransfer.ResponseEditStatusTransfer;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangTransferBarangGudangActivity extends AppCompatActivity {

    private ActivityKeranjangTransferBarangGudangBinding binding;
    public String id_status_transfer;

    public KeranjangTransferBarangGudangActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeranjangTransferBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_transfer = getIntent().getStringExtra("id_status_transfer");

        binding.rvListKeranjangTransfer.setHasFixedSize(true);
        binding.rvListKeranjangTransfer.setLayoutManager(new LinearLayoutManager(KeranjangTransferBarangGudangActivity.this));

        loadKeranjangTransfer();

        PushDownAnim.setPushDownAnimTo(binding.btnCheckoutTransfer)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            editStatusTransfer();
                    }
                });

    }



    private void editStatusTransfer() {


        ProgressDialog progresStatusTransfer = new ProgressDialog(KeranjangTransferBarangGudangActivity.this);
        progresStatusTransfer.setMessage("Memproses Checkout Anda ...");
        progresStatusTransfer.show();


        ConfigRetrofit.service.editStatusTransfer(id_status_transfer, "dalam proses")
                .enqueue(new Callback<ResponseEditStatusTransfer>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusTransfer> call, Response<ResponseEditStatusTransfer> response) {
                        if (response.isSuccessful()){
                            progresStatusTransfer.dismiss();

                            int status = response.body().getStatus();
                            if (status == 1){
                                editStatusBarang();
                                editDataBarangToko();
                            }else{
                                Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                        "Gagal Check Out.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progresStatusTransfer.dismiss();
                            Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                    "Gagal mengambil data dari server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusTransfer> call, Throwable t) {
                        progresStatusTransfer.dismiss();
                        Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void editDataBarangToko() {



    }

    private void editStatusBarang() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangTransferBarangGudangActivity.this);
        progressDialog.setMessage("Merubah Status Transfer");
        progressDialog.show();

        ConfigRetrofit.service.editStatusBarangTransfer(id_status_transfer, "dalam proses")
                .enqueue(new Callback<ResponseEditStatusBarangTransfer>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusBarangTransfer> call, Response<ResponseEditStatusBarangTransfer> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                        "Berhasil Checkout", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KeranjangTransferBarangGudangActivity.this,
                                        TransferBarangGudangActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("from_activity", "keranjang_transfer_gudang");
                                startActivity(intent);
                            }else{
                                Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                        "Gagal Mengubah Status", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                    "Response Dari Server Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusBarangTransfer> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void loadKeranjangTransfer() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangTransferBarangGudangActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.detailTransferBarang(id_status_transfer).enqueue(new Callback<ResponseDetailTransferBarang>() {
            @Override
            public void onResponse(Call<ResponseDetailTransferBarang> call, Response<ResponseDetailTransferBarang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status==1) {

                        List<DetailStatusTransferItem> detailTransfer = response.body().getDetailStatusTransfer();
                        KeranjangTransferAdapter adapter = new KeranjangTransferAdapter(KeranjangTransferBarangGudangActivity.this,
                                detailTransfer, KeranjangTransferBarangGudangActivity.this);
                        binding.rvListKeranjangTransfer.setAdapter(adapter);
                    }else{
                        Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                            "Gagal Mengambil Data Dari Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailTransferBarang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KeranjangTransferBarangGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}