package com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ReportGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ReportPenjualanGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class TransaksiKetoActivity extends AppCompatActivity {
    private ActivityTransaksiKetoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackTransaksiKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearTransaksiKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TransaksiKetoActivity.this, ReportTransaksiKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearPengeluaranKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TransaksiKetoActivity.this, ReportPengeluaranKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearReportBarangKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TransaksiKetoActivity.this, ReportBarangKetoActivity.class));
                    }
                });


    }
}