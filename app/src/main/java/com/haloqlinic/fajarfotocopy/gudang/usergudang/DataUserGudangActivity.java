package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;

public class DataUserGudangActivity extends AppCompatActivity {

    private ActivityDataUserGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}