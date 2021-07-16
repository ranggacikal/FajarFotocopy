package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kasir.PembayaranAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityHomeKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPembayaranKasirBinding;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.BarangPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.ResponseDataBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualan.ResponseUpdateStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class PembayaranKasirActivity extends AppCompatActivity {

    private ActivityPembayaranKasirBinding binding;
    String id_status_penjualan;
    String tanggal, tanggal2;
    String nama_kasir, metode_bayar;
    String value = "";
    int discount;
    int total;

    int totalSeluruh = 0;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityPembayaranKasirBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

            preferencedConfig = new SharedPreferencedConfig(this);

            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("dd MMMM yyyy");

            date = dateFormat.format(calendar.getTime());
            tanggal = date;

            id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");

            binding.edtDiskonPembayaran.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    value = binding.edtDiskonPembayaran.getText().toString();

                    if (value.equals("")){
                        totalSeluruh = total - 0;
                    }else{
                        totalSeluruh = total - Integer.parseInt(value);
                    }

                    Log.d("checkTotalSeluruh", "onTextChanged: "+"Rp."+totalSeluruh);
                    binding.textTotalBayarPembayaran.setText("Rp" + NumberFormat.getInstance().format(totalSeluruh));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            metode_bayar = String.valueOf(binding.spinnerPembayaran.getSelectedItem());

            binding.idTransaksiPembayaran.setText(id_status_penjualan);
            binding.textTanggalPembayaran.setText(tanggal);
            binding.textNamaKasirPembayaran.setText(preferencedConfig.getPreferenceNama());

            binding.rvListBarangPembayaran.setHasFixedSize(true);
            binding.rvListBarangPembayaran.setLayoutManager(new LinearLayoutManager(PembayaranKasirActivity.this));

        PushDownAnim.setPushDownAnimTo(binding.btnBayarPembayaran)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bayar();
                    }
                });

            loadDataPembayaran();
    }

    private void bayar() {

        String postTotal = "";
        String postDiskon = "";

        if (totalSeluruh == 0){
            postTotal = String.valueOf(total);
        }else{
            postTotal = String.valueOf(totalSeluruh);
        }

        if (value.equals("")){
            postDiskon = "0";
        }else{
            postDiskon = value;
        }

        ProgressDialog progressDialog = new ProgressDialog(PembayaranKasirActivity.this);
        progressDialog.setMessage("memproses pembayaran");
        progressDialog.dismiss();

        ConfigRetrofit.service.updateStatusPenjualan(id_status_penjualan, tanggal2,
                "selesai", preferencedConfig.getPreferenceIdOutlet(), metode_bayar,
                postTotal, postDiskon)
                .enqueue(new Callback<ResponseUpdateStatusPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStatusPenjualan> call, Response<ResponseUpdateStatusPenjualan> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            if (status == 1){
                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Pembayaran Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PembayaranKasirActivity.this, TransaksiBerhasilActivity.class);
                                intent.putExtra("id_status_penjualan", id_status_penjualan);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Pembayaran Gagal", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(PembayaranKasirActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStatusPenjualan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PembayaranKasirActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadDataPembayaran() {

        ProgressDialog progressDialog = new ProgressDialog(PembayaranKasirActivity.this);
        progressDialog.setMessage("memuat data");
        progressDialog.show();

        ConfigRetrofit.service.dataBarangPenjualan(id_status_penjualan).enqueue(new Callback<ResponseDataBarangPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDataBarangPenjualan> call, Response<ResponseDataBarangPenjualan> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();
                    ArrayList<Integer> listTotal = new ArrayList<>();

                    listTotal.clear();

                    if (status == 1){


                        List<BarangPenjualanItem> dataBarang = response.body().getBarangPenjualan();
                        PembayaranAdapter adapter = new PembayaranAdapter(PembayaranKasirActivity.this,
                                dataBarang);

                        for (int i = 0; i<dataBarang.size(); i++){
                            listTotal.add(Integer.parseInt(dataBarang.get(i).getTotal()));
                            tanggal2 = dataBarang.get(i).getTanggalPenjualan();
                        }

                        total = 0;
                        for (int a = 0; a<listTotal.size(); a++){

                            total += listTotal.get(a);

                        }

                        binding.rvListBarangPembayaran.setAdapter(adapter);
                        binding.textTotalBayarPembayaran.setText("Rp" + NumberFormat.getInstance().format(total));

                    }else{
                        Toast.makeText(PembayaranKasirActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(PembayaranKasirActivity.this, "Gagal Memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarangPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PembayaranKasirActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}