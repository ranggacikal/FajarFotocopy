package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataBarangGudangActivity extends AppCompatActivity {

    private ActivityDataBarangGudangBinding binding;
    boolean searchId = false;
    boolean searchName = false;

    String textId = "";
    String textName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.searchviewDatabaranggudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewDatabaranggudang.setQueryHint("Cari Nama Barang");
                binding.searchviewDatabaranggudang.setIconified(false);
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeDataBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                DataBarangGudangActivity.this
                        );

                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                    }
                });

        binding.searchviewDatabaranggudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                textName = newText;
                loadDataCari(textName);
                searchName = true;
                return true;
            }
        });
    }

    private void loadSearchById(String newText) {

        if (newText.equals("")){
            binding.recyclerDataBarangGudamgScanner.setVisibility(View.GONE);
            binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
            binding.textKosongDataBarang.setVisibility(View.GONE);
        }else {

            ProgressDialog progressDialog = new ProgressDialog(DataBarangGudangActivity.this);
            progressDialog.setMessage("Mencari Data");
            progressDialog.show();

            ConfigRetrofit.service.cariBarangById(newText).enqueue(new Callback<ResponseCariBarangById>() {
                @Override
                public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                    if (response.isSuccessful()) {

                        progressDialog.dismiss();
                        List<SearchBarangByIdItem> dataBarangScanner = response.body().getSearchBarangById();
                        CariBarangIdAdapter adapterScanner = new CariBarangIdAdapter(DataBarangGudangActivity.this, dataBarangScanner);
                        binding.recyclerDataBarangGudamgScanner.setHasFixedSize(true);
                        GridLayoutManager manager = new GridLayoutManager(DataBarangGudangActivity.this,
                                2, GridLayoutManager.VERTICAL, false);
                        binding.recyclerDataBarangGudamgScanner.setLayoutManager(manager);
                        binding.recyclerDataBarangGudamgScanner.setAdapter(adapterScanner);
                        binding.recyclerDataBarangGudamgScanner.setVisibility(View.VISIBLE);
                        binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
                        binding.textKosongDataBarang.setVisibility(View.GONE);

                    } else {
                        progressDialog.dismiss();
                        binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
                        binding.textKosongDataBarang.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                    progressDialog.dismiss();
                    binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
                    Toast.makeText(DataBarangGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {

            loadSearchById(intentResult.getContents());

        } else {
            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataCari(String newText) {

        if (newText.equals("")){
            binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
            binding.textKosongDataBarang.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.cariBarang(newText).enqueue(new Callback<ResponseCariBarangByNama>() {
                @Override
                public void onResponse(Call<ResponseCariBarangByNama> call, Response<ResponseCariBarangByNama> response) {
                    if (response.isSuccessful()) {

                        int status = response.body().getStatus();

                        if (status == 1) {

                            binding.textKosongDataBarang.setVisibility(View.GONE);
                            binding.recyclerDataBarangGudamgScanner.setVisibility(View.GONE);
                            binding.recyclerDataBarangGudamg.setVisibility(View.VISIBLE);
                            List<SearchBarangByNamaItem> dataBarang = response.body().getSearchBarangByNama();
                            CariBarangAdapter adapter = new CariBarangAdapter(DataBarangGudangActivity.this, dataBarang);
                            binding.recyclerDataBarangGudamg.setHasFixedSize(true);
                            GridLayoutManager manager = new GridLayoutManager(DataBarangGudangActivity.this,
                                    2, GridLayoutManager.VERTICAL, false);
                            binding.recyclerDataBarangGudamg.setLayoutManager(manager);
                            binding.recyclerDataBarangGudamg.setAdapter(adapter);

                        } else {
                            binding.recyclerDataBarangGudamg.setVisibility(View.GONE);
                            binding.textKosongDataBarang.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(DataBarangGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangByNama> call, Throwable t) {
                    Toast.makeText(DataBarangGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (searchId = true){
            loadSearchById(textId);
        }else if (searchName = true){
            loadDataCari(textName);
        }

    }
}