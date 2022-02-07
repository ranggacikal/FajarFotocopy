package com.haloqlinic.fajarfotocopy.gudang.reportgudang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityLihatBuktiBayarBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportAllTokoGudangBinding;

public class LihatBuktiBayarActivity extends AppCompatActivity {

    String image_bukti = "";
    ActivityLihatBuktiBayarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLihatBuktiBayarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        image_bukti = getIntent().getStringExtra("image_bukti");

        Glide.with(LihatBuktiBayarActivity.this)
                .load(image_bukti)
                .error(R.drawable.icon_img_error)
                .into(binding.imgBuktiBayarLihat);
    }
}