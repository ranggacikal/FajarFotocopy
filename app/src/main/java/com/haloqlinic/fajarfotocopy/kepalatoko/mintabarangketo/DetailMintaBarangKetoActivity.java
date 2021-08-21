package com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailMintaBarangKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPembayaranKasirBinding;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.PembayaranKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiBerhasilActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualan.ResponseUpdateStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMintaBarangKetoActivity extends AppCompatActivity {

    private ActivityDetailMintaBarangKetoBinding binding;

    private SharedPreferencedConfig preferencedConfig;
    String tanggal;
    String status_permintaan_barang;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private Bitmap bitmap;
    String from_keto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailMintaBarangKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        from_keto = getIntent().getStringExtra("from_keto");

        preferencedConfig = new SharedPreferencedConfig(this);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        tanggal = date;

        status_permintaan_barang = getIntent().getStringExtra("status_permintaan_barang");


        PushDownAnim.setPushDownAnimTo(binding.linearBackListPermintaanBarangKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(DetailMintaBarangKetoActivity.this, TambahBarangKetoActivity.class));
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnListPermintaanBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit();
                    }
                });

        loadDataPermintaanBarang();

    }

    private void submit() {


    }

    private void loadDataPermintaanBarang() {
    }
}