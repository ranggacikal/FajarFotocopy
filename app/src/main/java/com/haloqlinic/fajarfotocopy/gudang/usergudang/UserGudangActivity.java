package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.haloqlinic.fajarfotocopy.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class UserGudangActivity extends AppCompatActivity {

    LinearLayout linearTambahUserGudang,linearDataUserGudang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gudang);

        linearTambahUserGudang = findViewById(R.id.linear_tambah_user_gudang);
        linearDataUserGudang = findViewById(R.id.linear_data_user_gudang);

        PushDownAnim.setPushDownAnimTo(linearTambahUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(UserGudangActivity.this, TambahUserGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearDataUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(UserGudangActivity.this, DataUserGudangActivity.class));
                    }
                });
    }
}