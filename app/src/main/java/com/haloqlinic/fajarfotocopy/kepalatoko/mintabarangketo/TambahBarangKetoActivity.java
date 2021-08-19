package com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

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
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStockAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStockIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.MintaBarangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.MintaBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahBarangKetoBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarangKetoActivity extends AppCompatActivity {

    private ActivityTambahBarangKetoBinding binding;
    public String id_barcode = "";
    public String nama = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahBarangKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackCariMintaBarangKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        binding.recyclerMintaBarangKeto.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(TambahBarangKetoActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerMintaBarangKeto.setLayoutManager(manager);

        binding.searchviewMintaBarangGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.recyclerMintaBarangKeto.setVisibility(View.GONE);
                binding.searchviewMintaBarangGudang.setQueryHint("Cari Barang");
                binding.searchviewMintaBarangGudang.setIconified(false);
            }
        });


        binding.searchviewMintaBarangGudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    private void loadData(String newText) {

        if (newText.equals("")){
            binding.recyclerMintaBarangKeto.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.cariBarang(newText).enqueue(new Callback<ResponseCariBarangByNama>() {
                @Override
                public void onResponse(Call<ResponseCariBarangByNama> call, Response<ResponseCariBarangByNama> response) {
                    if (response.isSuccessful()){
                        binding.recyclerMintaBarangKeto.setVisibility(View.VISIBLE);
                        int status = response.body().getStatus();
                        if (status==1){
                            List<SearchBarangByNamaItem> dataBarang = response.body().getSearchBarangByNama();
                            MintaBarangAdapter adapter = new MintaBarangAdapter(TambahBarangKetoActivity.this,
                                    dataBarang, TambahBarangKetoActivity.this);
                            binding.recyclerMintaBarangKeto.setAdapter(adapter);
                        }else{
                            Toast.makeText(TambahBarangKetoActivity.this, "Data Kosong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(TambahBarangKetoActivity.this, "Response Gagal",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangByNama> call, Throwable t) {
                    Toast.makeText(TambahBarangKetoActivity.this, "Error: "+t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void loadDataById(String newText) {


        ConfigRetrofit.service.cariBarangById(newText).enqueue(new Callback<ResponseCariBarangById>() {
            @Override
            public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                if (response.isSuccessful()){

                    binding.recyclerMintaBarangKeto.setVisibility(View.VISIBLE);

                    int status = response.body().getStatus();

                    if (status == 1){

                        List<SearchBarangByIdItem> dataBarang = response.body().getSearchBarangById();
                        MintaBarangIdAdapter adapterId = new MintaBarangIdAdapter(TambahBarangKetoActivity.this,
                                dataBarang, TambahBarangKetoActivity.this);
                        binding.recyclerMintaBarangKeto.setAdapter(adapterId);

                    }else{
                        Toast.makeText(TambahBarangKetoActivity.this, "Data Kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TambahBarangKetoActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                Toast.makeText(TambahBarangKetoActivity.this, "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {

            id_barcode = intentResult.getContents();
            loadDataById(id_barcode);
            Log.d("requestCodeScan", "onActivityResult: " + requestCode);

        }
    }
}