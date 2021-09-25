package com.haloqlinic.fajarfotocopy.kepalatoko.pengeluaranketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKirimBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPengeluaranKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.formatNumber.NumberTextWatcher;
import com.haloqlinic.fajarfotocopy.model.tambahPengeluaranOutlet.ResponseTambahPengeluaranOutlet;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengeluaranKetoActivity extends AppCompatActivity {

    private ActivityPengeluaranKetoBinding binding;

    private Calendar calendarHari;
    private SimpleDateFormat dateFormatHari;


    private SharedPreferencedConfig preferencedConfig;

    String hari = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengeluaranKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        calendarHari = Calendar.getInstance();
        dateFormatHari = new SimpleDateFormat("dd MMMM yyyy");

        hari = dateFormatHari.format(calendarHari.getTime());


        binding.edtTambahPengeluaranKeto.addTextChangedListener(new NumberTextWatcher(binding.edtTambahPengeluaranKeto));

        PushDownAnim.setPushDownAnimTo(binding.linearBackPengeluaranKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnSubmitPengeluaranKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahPengeluaran();
                    }
                });
    }

    private void tambahPengeluaran() {

        String jumlah_pengeluaran_str = binding.edtTambahPengeluaranKeto.getText().toString();
        String jumlah_pengeluaran = jumlah_pengeluaran_str.replace(".", "")
                .replace(",", "");

        String keterangan = binding.edtKeteranganKeto.getText().toString();

        if (jumlah_pengeluaran.equals("")){

            binding.edtTambahPengeluaranKeto.setError("Jumlah pengeluaran tidak boleh kosong");
            binding.edtTambahPengeluaranKeto.requestFocus();
            return;

        }

        ProgressDialog progressDialog = new ProgressDialog(PengeluaranKetoActivity.this);
        progressDialog.setMessage("Menambahkan Data Pengeluaran");
        progressDialog.show();

        ConfigRetrofit.service.tambahPengeluaranOutlet(jumlah_pengeluaran, keterangan, hari,
                preferencedConfig.getPreferenceIdOutlet()).enqueue(new Callback<ResponseTambahPengeluaranOutlet>() {
            @Override
            public void onResponse(Call<ResponseTambahPengeluaranOutlet> call, Response<ResponseTambahPengeluaranOutlet> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(PengeluaranKetoActivity.this, "Berhasil Tambah Data",
                                Toast.LENGTH_SHORT).show();
                        binding.edtTambahPengeluaranKeto.setText("");
                        binding.edtKeteranganKeto.setText("");
                        binding.edtTambahPengeluaranKeto.requestFocus();

                    }else{
                        Toast.makeText(PengeluaranKetoActivity.this, "Gagal tambah data",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(PengeluaranKetoActivity.this, "Terjadi kesalahan di server",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahPengeluaranOutlet> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PengeluaranKetoActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}