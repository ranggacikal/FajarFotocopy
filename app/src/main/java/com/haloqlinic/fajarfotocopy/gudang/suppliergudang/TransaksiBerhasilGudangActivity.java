package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiBerhasilBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiBerhasilGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.CetakBuktiSupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.CetakBuktiKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiBerhasilActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class TransaksiBerhasilGudangActivity extends AppCompatActivity {

    private ActivityTransaksiBerhasilGudangBinding binding;

    String id_status_penjualan_gudang = "";

    String from_gudang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiBerhasilGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");
        from_gudang = getIntent().getStringExtra("from_gudang");
        PushDownAnim.setPushDownAnimTo(binding.btnTransaksiBerhasilGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TransaksiBerhasilGudangActivity.this, CetakBuktiSupplierGudangActivity.class);
                        intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                        if (from_gudang!=null){
                            intent.putExtra("from_gudang", from_gudang);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}