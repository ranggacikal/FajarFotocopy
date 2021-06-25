package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.haloqlinic.fajarfotocopy.R;

public class TransaksiKasirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_kasir);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}