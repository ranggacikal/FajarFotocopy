package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahKategoriBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKasirBinding;
import com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan.ResponseGetIdStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualan.ResponseHapusStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import androidx.appcompat.app.AppCompatActivity;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TransaksiKasirActivity extends AppCompatActivity {

    String id_status_penjualan;

    private ActivityTransaksiKasirBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.btnJasaKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.btnJasaKasir.setBackgroundResource(R.drawable.btn_black);
                        binding.btnJasaKasir.setTextColor(Color.parseColor("#FFFFFF"));
                        binding.btnBarangKasir.setBackgroundResource(R.drawable.btn_merah);
                        binding.btnBarangKasir.setTextColor(Color.parseColor("#000000"));
                        binding.linearJasaKasir.setVisibility(View.VISIBLE);
                        binding.linearBarangKasir.setVisibility(View.GONE);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnBarangKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.btnJasaKasir.setBackgroundResource(R.drawable.btn_merah);
                        binding.btnJasaKasir.setTextColor(Color.parseColor("#000000"));
                        binding.btnBarangKasir.setBackgroundResource(R.drawable.btn_black);
                        binding.btnBarangKasir.setTextColor(Color.parseColor("#FFFFFF"));
                        binding.linearJasaKasir.setVisibility(View.GONE);
                        binding.linearBarangKasir.setVisibility(View.VISIBLE);
                    }
                });



        getStatusPenjualan();
    }

    private void getStatusPenjualan() {
        ConfigRetrofit.service.getIdStatusPenjualan().enqueue(new Callback<ResponseGetIdStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseGetIdStatusPenjualan> call, Response<ResponseGetIdStatusPenjualan> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();
                    if (status == 1) {
                        id_status_penjualan = response.body().getDataIdStatusPenjualan().getIdStatusPenjualan();

                    } else {
                        Toast.makeText(TransaksiKasirActivity.this, "Gagal Mengambil Id Status Penjualan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetIdStatusPenjualan> call, Throwable t) {
                Toast.makeText(TransaksiKasirActivity.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(TransaksiKasirActivity.this)
                .setTitle("Batal Transaksi?")
                .setMessage("Apakah anda yakin ingin membatalkan transaksi ini?")
                .setCancelable(false)
                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        TransaksiKasirActivity.super.onBackPressed();

                        hapusStatusPenjualan();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Tidak", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }


    private void hapusStatusPenjualan() {
        ProgressDialog progressDialog = new ProgressDialog(TransaksiKasirActivity.this);
        progressDialog.setMessage("Membatalkan Transaksi");
        progressDialog.show();
        ConfigRetrofit.service.hapusStatusPenjualan(id_status_penjualan).enqueue(new Callback<ResponseHapusStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusStatusPenjualan> call, Response<ResponseHapusStatusPenjualan> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int status = response.body().getStatus();
                    if (status == 1) {
                        finish();
                    } else {
                        Toast.makeText(TransaksiKasirActivity.this, "Gagal Membatalkan Transaksi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}