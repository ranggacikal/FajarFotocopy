package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailBarangStockGudangBinding;

public class DataTokoGudangActivity extends AppCompatActivity {

    private ActivityDataTokoGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}