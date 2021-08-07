package com.haloqlinic.fajarfotocopy.gudang.informasigudang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataInformasiGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPengirimanBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahInformasiGudangBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataInformasiGudangActivity extends AppCompatActivity {

    private ActivityDataInformasiGudangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInformasiGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataInformasiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });    }
}