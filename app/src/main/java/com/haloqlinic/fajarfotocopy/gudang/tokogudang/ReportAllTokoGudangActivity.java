
package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportAllTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo.ReportPengeluaranKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo.ReportTransaksiKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo.TransaksiKetoActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ReportAllTokoGudangActivity extends AppCompatActivity {
    private ActivityReportAllTokoGudangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportAllTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportTransaksiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearTransaksiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ReportAllTokoGudangActivity.this, ReportTokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearPengeluaranGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ReportAllTokoGudangActivity.this, ReportPengeluaranTokoGudangActivity.class));
                    }
                });    }
}