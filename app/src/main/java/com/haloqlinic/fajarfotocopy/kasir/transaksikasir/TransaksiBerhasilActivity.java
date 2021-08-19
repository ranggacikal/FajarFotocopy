package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiBerhasilBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TransaksiBerhasilActivity extends AppCompatActivity {

    private ActivityTransaksiBerhasilBinding binding;
    
    String id_status_penjualan = "";

    String from_keto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiBerhasilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");
        from_keto = getIntent().getStringExtra("from_keto");
        PushDownAnim.setPushDownAnimTo(binding.btnTransaksiBerhasil)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TransaksiBerhasilActivity.this, CetakBuktiKasirActivity.class);
                        intent.putExtra("id_status_penjualan", id_status_penjualan);
                        if (from_keto!=null){
                            intent.putExtra("from_keto", from_keto);
                        }
                        startActivity(intent);
                        finish();
                    }
                });


    }
}