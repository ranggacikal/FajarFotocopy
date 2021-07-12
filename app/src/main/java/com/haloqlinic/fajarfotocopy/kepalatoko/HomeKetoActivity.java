package com.haloqlinic.fajarfotocopy.kepalatoko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityHomeKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahUserGudangBinding;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.ListPengirimanKetoActivity;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualan.ResponseTambahStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeKetoActivity extends AppCompatActivity {

    private ActivityHomeKetoBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        PushDownAnim.setPushDownAnimTo(binding.linearListPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeKetoActivity.this, ListPengirimanKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnKeluarKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialAlertDialogBuilder(HomeKetoActivity.this)
                                .setTitle("Keluar Akun?")
                                .setMessage("Anda yakin ingin keluar dari akun ini?")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        keluarAkun();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearKasirKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahStatusPenjualan();
                    }
                });

    }

    private void tambahStatusPenjualan() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_status_penjualan = "SPN"+randomId;
        String status_penjualan = "pending";

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        String tanggal = date;
        String id_outlet = preferencedConfig.getPreferenceIdOutlet();

        ConfigRetrofit.service.tambahStatusPenjualan(id_status_penjualan, status_penjualan, tanggal, id_outlet)
                .enqueue(new Callback<ResponseTambahStatusPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseTambahStatusPenjualan> call, Response<ResponseTambahStatusPenjualan> response) {
                        if(response.isSuccessful()){
                            int status = response.body().getStatus();
                            if(status == 1){
                                Intent intent = new Intent(HomeKetoActivity.this, TransaksiKasirActivity.class);
                                intent.putExtra("namaActivity", "HomeKeto");
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(HomeKetoActivity.this, "Gagal Membuat Penjualan", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(HomeKetoActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahStatusPenjualan> call, Throwable t) {
                        Toast.makeText(HomeKetoActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void keluarAkun() {

        Toast.makeText(HomeKetoActivity.this, "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(HomeKetoActivity.this, LoginActivity.class));
        finish();

    }
}