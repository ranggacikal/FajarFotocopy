package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityInvoiceKetoBinding;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class CetakBuktiSupplierGudangActivity extends AppCompatActivity {

    private ActivityCetakBuktiKasirBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCetakBuktiKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.btnBackHomeCetakBukti)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CetakBuktiSupplierGudangActivity.this, MainActivity.class));
                        finish();
                    }
                });

    }
}