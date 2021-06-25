package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan.ResponseGetIdStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualan.ResponseHapusStatusPenjualan;

import androidx.appcompat.app.AppCompatActivity;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiKasirActivity extends AppCompatActivity {

    String id_status_penjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_kasir);

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