package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.haloqlinic.fajarfotocopy.R;

public class TokoGudangActivity extends AppCompatActivity {

    LinearLayout linearCekStock, linearTambahOutlet, linearDataOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_gudang);

        linearCekStock = findViewById(R.id.linear_cek_stock_gudang);
        linearTambahOutlet = findViewById(R.id.linear_tambah_toko_gudang);
        linearDataOutlet = findViewById(R.id.linear_data_toko_gudang);

        linearCekStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TokoGudangActivity.this, CekStockTokoGudangActivity.class));
            }
        });

        linearTambahOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TokoGudangActivity.this, TambahTokoGudangActivity.class));
            }
        });

        linearDataOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TokoGudangActivity.this, DataTokoGudangActivity.class));
            }
        });
    }
}