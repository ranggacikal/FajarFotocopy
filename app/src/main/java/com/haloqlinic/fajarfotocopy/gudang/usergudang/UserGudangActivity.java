package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityUserGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.CekStockTokoGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class UserGudangActivity extends AppCompatActivity {

    private ActivityUserGudangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(UserGudangActivity.this, TambahUserGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearDataUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(UserGudangActivity.this, DataUserGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


    }
}