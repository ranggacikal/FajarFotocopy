package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangIdPenjualanAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangPenjualanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivitySupplierGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.HasilPencarianTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanByIdStatus.ResponseHapusPenjualanGudangByIdStatus;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualanGudang.ResponseHapusStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierGudangActivity extends AppCompatActivity {

    private ActivitySupplierGudangBinding binding;

    public String id_status_penjualan_gudang, nama_user;

    private SharedPreferencedConfig preferencedConfig;

    public SupplierGudangActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        nama_user = preferencedConfig.getPreferenceNama();

        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");

        binding.btnBarcodeSupplierGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        SupplierGudangActivity.this
                );

                intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        binding.searchviewSupplierGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewSupplierGudang.setQueryHint("Masukan Nama Barang");
                binding.searchviewSupplierGudang.setIconified(false);
            }
        });

        binding.searchviewSupplierGudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBarangNama(newText);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnChekoutSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SupplierGudangActivity.this, KeranjangSupplierGudangActivity.class);
                        intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                        Log.d("cekSupplier", "id: "+id_status_penjualan_gudang);
                        startActivity(intent);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackSupplierGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetMaterialDialog mBottomSheetDialog =
                                new BottomSheetMaterialDialog.Builder(SupplierGudangActivity.this)
                                .setTitle("Keluar Dari Halaman Supplier?")
                                .setMessage("Apakah anda yakin ingin keluar dari halaman supplier?")
                                .setCancelable(false)
                                .setPositiveButton("Hapus", new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        hapusStatusPenjualanBarang();
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Batal", new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build();

                        // Show Dialog
                        mBottomSheetDialog.show();
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

            String id_barcode = intentResult.getContents();
            loadDataById(id_barcode);
            Log.d("requestCodeScan", "onActivityResult: " + requestCode);

        }
    }

    private void loadDataById(String id_barcode) {

        ConfigRetrofit.service.cariBarangById(id_barcode).enqueue(new Callback<ResponseCariBarangById>() {
            @Override
            public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<SearchBarangByIdItem> dataBarang = response.body().getSearchBarangById();
                        CariBarangIdPenjualanAdapter adapterId = new CariBarangIdPenjualanAdapter(
                                SupplierGudangActivity.this, dataBarang,
                                SupplierGudangActivity.this
                        );
                        binding.recyclerBarangGudang.setHasFixedSize(true);
                        GridLayoutManager manager = new GridLayoutManager(SupplierGudangActivity.this,
                                2, GridLayoutManager.VERTICAL, false);
                        binding.recyclerBarangGudang.setLayoutManager(manager);
                        binding.recyclerBarangGudang.setAdapter(adapterId);

                    }else{
                        Toast.makeText(SupplierGudangActivity.this, "Data Kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SupplierGudangActivity.this, "Response Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                Toast.makeText(SupplierGudangActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void searchBarangNama(String query) {

        ConfigRetrofit.service.cariBarang(query).enqueue(new Callback<ResponseCariBarangByNama>() {
            @Override
            public void onResponse(Call<ResponseCariBarangByNama> call, Response<ResponseCariBarangByNama> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<SearchBarangByNamaItem> dataBarang = response.body().getSearchBarangByNama();
                        CariBarangPenjualanAdapter adapter = new CariBarangPenjualanAdapter(SupplierGudangActivity.this,
                                dataBarang, SupplierGudangActivity.this);
                        binding.recyclerBarangGudang.setHasFixedSize(true);
                        GridLayoutManager manager = new GridLayoutManager(SupplierGudangActivity.this,
                                2, GridLayoutManager.VERTICAL, false);
                        binding.recyclerBarangGudang.setLayoutManager(manager);
                        binding.recyclerBarangGudang.setAdapter(adapter);

                    }else{
                        Toast.makeText(SupplierGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangByNama> call, Throwable t) {
                Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(SupplierGudangActivity.this)
                .setTitle("Keluar Dari Halaman Supplier?")
                .setMessage("Apakah anda yakin ingin keluar dari halaman supplier?")
                .setCancelable(false)
                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        SupplierGudangActivity.super.onBackPressed();
                        hapusBarangBarangByIdStatus();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Tidak", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();
    }

    private void hapusBarangBarangByIdStatus() {

        ConfigRetrofit.service.hapusPenjualanIdStatus(id_status_penjualan_gudang).enqueue(new Callback<ResponseHapusPenjualanGudangByIdStatus>() {
            @Override
            public void onResponse(Call<ResponseHapusPenjualanGudangByIdStatus> call, Response<ResponseHapusPenjualanGudangByIdStatus> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(SupplierGudangActivity.this, "Berhasil menghapus semua data",
                                Toast.LENGTH_SHORT).show();
                        hapusStatusPenjualanBarang();
                    }else{
                        Toast.makeText(SupplierGudangActivity.this, "Gagal hapus semua data",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPenjualanGudangByIdStatus> call, Throwable t) {
                Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hapusStatusPenjualanBarang() {

        ConfigRetrofit.service.hapusStatusPenjualanGudang(id_status_penjualan_gudang).enqueue(new Callback<ResponseHapusStatusPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseHapusStatusPenjualanGudang> call, Response<ResponseHapusStatusPenjualanGudang> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        finish();

                    }else{
                        Toast.makeText(SupplierGudangActivity.this, "Gagal Menyelesaikan transaksi",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusPenjualanGudang> call, Throwable t) {
                Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}