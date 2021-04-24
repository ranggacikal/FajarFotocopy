package com.haloqlinic.fajarfotocopy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.haloqlinic.fajarfotocopy.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username_login_gudang);
        edtPassword = findViewById(R.id.edt_password_login_gudang);
        btnLogin = findViewById(R.id.btn_login_gudang);

        login();
    }

    private void login() {

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

    }
}