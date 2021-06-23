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
import com.haloqlinic.fajarfotocopy.databinding.ActivityHomeKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahUserGudangBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.ListPengirimanKetoActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeKetoActivity extends AppCompatActivity {

    private ActivityHomeKetoBinding binding;
    private SharedPreferencedConfig preferencedConfig;

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
    }

    private void keluarAkun() {

        Toast.makeText(HomeKetoActivity.this, "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(HomeKetoActivity.this, LoginActivity.class));
        finish();

    }
}