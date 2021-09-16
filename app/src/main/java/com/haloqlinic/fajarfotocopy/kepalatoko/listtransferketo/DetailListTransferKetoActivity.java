package com.haloqlinic.fajarfotocopy.kepalatoko.listtransferketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailListTransferKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityNotifikasiGudangBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class DetailListTransferKetoActivity extends AppCompatActivity {

    private ActivityDetailListTransferKetoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailListTransferKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailListTransferKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });    }
}