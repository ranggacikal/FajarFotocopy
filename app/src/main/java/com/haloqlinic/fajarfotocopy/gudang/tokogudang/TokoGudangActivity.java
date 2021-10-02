package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.BarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.TambahBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTokoGudangBinding;

import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TokoGudangActivity extends AppCompatActivity {

    private ActivityTokoGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearCekStockGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TokoGudangActivity.this, CekStockTokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearTambahTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TokoGudangActivity.this, TambahTokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearDataTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TokoGudangActivity.this, DataTokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearReportTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TokoGudangActivity.this, ReportAllTokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }


}