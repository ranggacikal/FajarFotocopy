package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStockAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStockIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.SearchCekStockGudangAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCekStockBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.HasilPencarianTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.searchBarangGudang.ResponseSearchBarangGudang;
import com.haloqlinic.fajarfotocopy.model.searchBarangGudang.SearchBarangGudangItem;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekStockBarangGudangActivity extends AppCompatActivity {

    private ActivityCekStockBarangGudangBinding binding;

    public String id_barcode = "";
    public String nama = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCekStockBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackCekStockBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerCekStockBarangGudamg.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(CekStockBarangGudangActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerCekStockBarangGudamg.setLayoutManager(manager);

        binding.searchviewCekstockgudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.recyclerCekStockBarangGudamg.setVisibility(View.GONE);
                binding.searchviewCekstockgudang.setQueryHint("Cari Barang");
                binding.searchviewCekstockgudang.setIconified(false);
            }
        });

        binding.searchviewCekstockgudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                nama = newText;
                loadData(nama);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeCekStockBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.recyclerCekStockBarangGudamg.setVisibility(View.GONE);
                        ScanOptions options = new ScanOptions();
                        options.setOrientationLocked(false);
                        barcodeLauncher.launch(options);
                    }
                });

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(CekStockBarangGudangActivity.this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
                } else {
                    loadData(result.getContents());
                }
            });

    public void loadData(String keyword) {

        if (keyword.equals("")){
            binding.recyclerCekStockBarangGudamg.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.searchBarangGudang(keyword).enqueue(new Callback<ResponseSearchBarangGudang>() {
                @Override
                public void onResponse(Call<ResponseSearchBarangGudang> call, Response<ResponseSearchBarangGudang> response) {
                    if (response.isSuccessful()){
                        binding.recyclerCekStockBarangGudamg.setVisibility(View.VISIBLE);
                        int status = response.body().getStatus();
                        if (status==1){
                            List<SearchBarangGudangItem> dataBarang = response.body().getSearchBarangGudang();
                            SearchCekStockGudangAdapter adapter = new SearchCekStockGudangAdapter(CekStockBarangGudangActivity.this,
                                    dataBarang, CekStockBarangGudangActivity.this);
                            binding.recyclerCekStockBarangGudamg.setAdapter(adapter);
                        }else{
                            Toast.makeText(CekStockBarangGudangActivity.this, "Data Kesini",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CekStockBarangGudangActivity.this, "Response Gagal",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseSearchBarangGudang> call, Throwable t) {
                    Toast.makeText(CekStockBarangGudangActivity.this, "Error: "+t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}