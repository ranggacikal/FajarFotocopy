package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiBerhasilBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiBerhasilGudangBinding;

public class TransaksiBerhasilGudangActivity extends AppCompatActivity {

    private ActivityTransaksiBerhasilGudangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiBerhasilGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}