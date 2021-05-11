package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginSebagaiActivity extends AppCompatActivity {

    Button btnGudang, btnKasir, btnKepalaToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sebagai);

        btnGudang = findViewById(R.id.btn_loginsebagai_gudang);
        btnKasir = findViewById(R.id.btn_loginsebagai_kasir);
        btnKepalaToko = findViewById(R.id.btn_loginsebagai_keto);

        btnGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSebagaiActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSebagaiActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnKepalaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSebagaiActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}