package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kasir.BarangOutletIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kasir.CariBarangOutletAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahKategoriBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKasirBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.HomeKetoActivity;
import com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan.ResponseGetIdStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualan.ResponseHapusStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.ResponseBarangOutletById;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.ResponseBarangOutletByNama;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TransaksiKasirActivity extends AppCompatActivity {

    public String id_status_penjualan;

    private ActivityTransaksiKasirBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    String nameActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackTransaksiKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(TransaksiKasirActivity.this)
                                .setTitle("Batal Transaksi?")
                                .setMessage("Apakah anda yakin ingin membatalkan transaksi ini?")
                                .setCancelable(false)
                                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {

                                        TransaksiKasirActivity.super.onBackPressed();

                                        hapusStatusPenjualan();
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
                });


        preferencedConfig = new SharedPreferencedConfig(this);
        binding.recyclerBarangOutlet.setHasFixedSize(true);
        binding.recyclerBarangOutlet.setLayoutManager(new LinearLayoutManager(TransaksiKasirActivity.this));
        binding.recyclerBarangOutlet.setVisibility(View.VISIBLE);

        binding.recyclerBarangOutletBarcode.setHasFixedSize(true);
        binding.recyclerBarangOutletBarcode.setLayoutManager(new LinearLayoutManager(TransaksiKasirActivity.this));

        binding.searchviewBarangOutletBarcode.setQueryHint("ID Barang");
        binding.searchviewBarangOutletBarcode.setIconified(false);

        nameActivity = getIntent().getStringExtra("namaActivity");
        id_status_penjualan = preferencedConfig.getPreferenceIdStatusPenjualan();

        PushDownAnim.setPushDownAnimTo(binding.btnJasaKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.btnJasaKasir.setBackgroundResource(R.drawable.btn_black);
                        binding.btnJasaKasir.setTextColor(Color.parseColor("#FFFFFF"));
                        binding.btnBarangKasir.setBackgroundResource(R.drawable.btn_merah);
                        binding.btnBarangKasir.setTextColor(Color.parseColor("#000000"));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnBarangKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.btnJasaKasir.setBackgroundResource(R.drawable.btn_merah);
                        binding.btnJasaKasir.setTextColor(Color.parseColor("#000000"));
                        binding.btnBarangKasir.setBackgroundResource(R.drawable.btn_black);
                        binding.btnBarangKasir.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.searchviewSupplierKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.searchviewSupplierKasir.setQueryHint("Masukan Nama Barang");
                        binding.searchviewSupplierKasir.setIconified(false);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeSupplierKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                TransaksiKasirActivity.this
                        );

                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                    }
                });

        binding.searchviewSupplierKasir.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCari) {
                loadSearchBarangKasir(textCari);
                return true;
            }
        });

        binding.searchviewBarangOutletBarcode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCariId) {
                loadSearchBarangById(textCariId);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnChekoutKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TransaksiKasirActivity.this, PembayaranKasirActivity.class);
                        intent.putExtra("id_status_penjualan", id_status_penjualan);
                        startActivity(intent);
                        finish();

                    }
                });

//        getStatusPenjualan();
    }

    private void loadSearchBarangById(String textCariId) {

        if (textCariId.equals("")){
            binding.recyclerBarangOutlet.setVisibility(View.GONE);
            binding.searchviewBarangOutletBarcode.setVisibility(View.GONE);
            binding.searchviewSupplierKasir.setVisibility(View.VISIBLE);
            binding.btnCariDenganNamaBarangOutlet.setVisibility(View.GONE);
        }else{

            binding.recyclerBarangOutletBarcode.setVisibility(View.VISIBLE);
            ConfigRetrofit.service.barangOutletById(textCariId, preferencedConfig.getPreferenceIdOutlet())
                    .enqueue(new Callback<ResponseBarangOutletById>() {
                        @Override
                        public void onResponse(Call<ResponseBarangOutletById> call, Response<ResponseBarangOutletById> response) {
                            if (response.isSuccessful()){

                                List<SearchBarangOutletByIdItem> dataCariId = response.body().getSearchBarangOutletById();

                                if (dataCariId==null){
                                    Toast.makeText(TransaksiKasirActivity.this, "Data barang kosong",
                                            Toast.LENGTH_SHORT).show();
                                }

                                BarangOutletIdAdapter adapterId = new BarangOutletIdAdapter(TransaksiKasirActivity.this, dataCariId, TransaksiKasirActivity.this);
                                binding.recyclerBarangOutletBarcode.setAdapter(adapterId);

                            }else{
                                Toast.makeText(TransaksiKasirActivity.this, "Gagal Load Barang", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBarangOutletById> call, Throwable t) {
                            Toast.makeText(TransaksiKasirActivity.this, "Error: "+t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
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

            binding.searchviewSupplierKasir.setVisibility(View.GONE);
            binding.btnCariDenganNamaBarangOutlet.setVisibility(View.VISIBLE);
            binding.searchviewBarangOutletBarcode.setVisibility(View.VISIBLE);
            binding.searchviewBarangOutletBarcode.setQuery(intentResult.getContents(), false);
            binding.recyclerBarangOutlet.setVisibility(View.GONE);
            binding.recyclerBarangOutletBarcode.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSearchBarangKasir(String textCari) {

        if (textCari.equals("")){
            binding.recyclerBarangOutlet.setVisibility(View.GONE);
        }else {
            binding.recyclerBarangOutletBarcode.setVisibility(View.GONE);
            binding.recyclerBarangOutlet.setVisibility(View.VISIBLE);
            ConfigRetrofit.service.barangOutletByNama(textCari, preferencedConfig.getPreferenceIdOutlet())
                    .enqueue(new Callback<ResponseBarangOutletByNama>() {
                        @Override
                        public void onResponse(Call<ResponseBarangOutletByNama> call, Response<ResponseBarangOutletByNama> response) {
                            if (response.isSuccessful()) {

                                int status = response.body().getStatus();

                                if (status==1) {

                                    List<SearchBarangOutletByNamaItem> dataCari = response.body().getSearchBarangOutletByNama();

                                    CariBarangOutletAdapter adapterNama = new CariBarangOutletAdapter(TransaksiKasirActivity.this, dataCari, TransaksiKasirActivity.this);
                                    binding.recyclerBarangOutlet.setAdapter(adapterNama);
                                }else{
                                    Toast.makeText(TransaksiKasirActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(TransaksiKasirActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBarangOutletByNama> call, Throwable t) {
                            Toast.makeText(TransaksiKasirActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }

    }

//    private void getStatusPenjualan() {
//        ConfigRetrofit.service.getIdStatusPenjualan().enqueue(new Callback<ResponseGetIdStatusPenjualan>() {
//            @Override
//            public void onResponse(Call<ResponseGetIdStatusPenjualan> call, Response<ResponseGetIdStatusPenjualan> response) {
//                if (response.isSuccessful()) {
//                    int status = response.body().getStatus();
//                    if (status == 1) {
//                        id_status_penjualan = response.body().getDataIdStatusPenjualan().getIdStatusPenjualan();
//
//                    } else {
//                        Toast.makeText(TransaksiKasirActivity.this, "Gagal Mengambil Id Status Penjualan", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseGetIdStatusPenjualan> call, Throwable t) {
//                Toast.makeText(TransaksiKasirActivity.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    @Override
    public void onBackPressed() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(TransaksiKasirActivity.this)
                .setTitle("Batal Transaksi?")
                .setMessage("Apakah anda yakin ingin membatalkan transaksi ini?")
                .setCancelable(false)
                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        TransaksiKasirActivity.super.onBackPressed();

                        hapusStatusPenjualan();
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


    private void hapusStatusPenjualan() {
        ProgressDialog progressDialog = new ProgressDialog(TransaksiKasirActivity.this);
        progressDialog.setMessage("Membatalkan Transaksi");
        progressDialog.show();
        ConfigRetrofit.service.hapusStatusPenjualan(id_status_penjualan).enqueue(new Callback<ResponseHapusStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusStatusPenjualan> call, Response<ResponseHapusStatusPenjualan> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int status = response.body().getStatus();
                    if (status == 1) {

                        if (nameActivity!=null) {

                            if (!nameActivity.equals("")) {

                                if (nameActivity.equals("HomeKeto")) {
                                    startActivity(new Intent(TransaksiKasirActivity.this, HomeKetoActivity.class));
                                    finish();
                                } else if (nameActivity.equals("HomeKasir")) {
                                    startActivity(new Intent(TransaksiKasirActivity.this, MainKasirActivity.class));
                                    finish();
                                }

                            }

                        }

                        finish();
                    } else {
                        Toast.makeText(TransaksiKasirActivity.this, "Gagal Membatalkan Transaksi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}