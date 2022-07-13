    package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.LoadingActivity;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kasir.BarangOutletIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kasir.CariBarangOutletAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKasirBinding;
import com.haloqlinic.fajarfotocopy.kasir.KasirMainActivity;
import com.haloqlinic.fajarfotocopy.kasir.MainKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.KetoMainActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanBarang.ResponseEditStatusPenjualanBarang;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.BarangPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.ResponseDataBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualan.ResponseHapusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualan.ResponseHapusStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.ResponseBarangOutletById;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.ResponseBarangOutletByNama;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.thekhaeng.pushdownanim.PushDownAnim;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
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
    public String cariBarang = "";

    ProgressDialog progressDialog;
    public List<BarangPenjualanItem> barangPenjualan;
    public int status;

    public ArrayList<String> dataBarangoutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        progressDialog = new ProgressDialog(this);

        dataBarangoutlet = new ArrayList<>();

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

//                                        TransaksiKasirActivity.super.onBackPressed();
                                        Intent intent = new Intent(TransaksiKasirActivity.this, LoadingActivity.class);
                                        startActivity(intent);
                                        finish();

                                        editStatusPenjualanBarang();
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
        GridLayoutManager manager = new GridLayoutManager(TransaksiKasirActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerBarangOutlet.setLayoutManager(manager);
        binding.recyclerBarangOutlet.setVisibility(View.VISIBLE);

        binding.recyclerBarangOutletBarcode.setHasFixedSize(true);
        GridLayoutManager manager2 = new GridLayoutManager(TransaksiKasirActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.recyclerBarangOutlet.setLayoutManager(manager2);

        binding.searchviewBarangOutletBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewBarangOutletBarcode.setQueryHint("ID Barang");
                binding.searchviewBarangOutletBarcode.setIconified(false);
            }
        });

        nameActivity = getIntent().getStringExtra("namaActivity");
        id_status_penjualan = preferencedConfig.getPreferenceIdStatusPenjualan();


        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeSupplierKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        IntentIntegrator intentIntegrator = new IntentIntegrator(
//                                TransaksiKasirActivity.this
//                        );
//
//                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
//                        intentIntegrator.setBeepEnabled(true);
//                        intentIntegrator.setOrientationLocked(true);
//                        intentIntegrator.setCaptureActivity(Capture.class);
//                        intentIntegrator.initiateScan();
                        ScanOptions options = new ScanOptions();
                        options.setOrientationLocked(false);
                        barcodeLauncher.launch(options);
                    }
                });

        binding.searchviewSupplierKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewSupplierKasir.setQueryHint("Masukan Nama Barang");
                binding.searchviewSupplierKasir.setIconified(false);
            }
        });

        binding.searchviewSupplierKasir.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCari) {
                cariBarang = textCari;
                loadSearchBarangKasir(cariBarang);
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
                        Log.d("cekStatusPenjualan", "onClick: "+id_status_penjualan);
                        intent.putExtra("from_keto", nameActivity);
                        startActivity(intent);
                        finish();

                    }
                });

        loadDataPembayaran();

//        getStatusPenjualan();
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(TransaksiKasirActivity.this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
                } else {
                    binding.searchviewSupplierKasir.setVisibility(View.GONE);
                    binding.searchviewBarangOutletBarcode.setVisibility(View.VISIBLE);
                    binding.searchviewBarangOutletBarcode.setQuery(result.getContents(), false);
                    binding.recyclerBarangOutlet.setVisibility(View.GONE);
                    binding.recyclerBarangOutletBarcode.setVisibility(View.VISIBLE);
                }
            });

    public void loadDataPembayaran() {

        ProgressDialog progressDialog = new ProgressDialog(TransaksiKasirActivity.this);
        progressDialog.setMessage("memuat data pembayaran");
        progressDialog.show();

        ConfigRetrofit.service.dataBarangPenjualan(id_status_penjualan).enqueue(new Callback<ResponseDataBarangPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDataBarangPenjualan> call, Response<ResponseDataBarangPenjualan> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    status = response.body().getStatus();
                    ArrayList<Integer> listTotal = new ArrayList<>();

                    listTotal.clear();

                    if (status == 1){


                        barangPenjualan = response.body().getBarangPenjualan();

                        Toast.makeText(TransaksiKasirActivity.this, String.valueOf(barangPenjualan.size()), Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(TransaksiKasirActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TransaksiKasirActivity.this, "Gagal Memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarangPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TransaksiKasirActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSearchBarangById(String textCariId) {

        Log.d("cekBarcodeId", "loadSearchBarangById: "+textCariId);

        if (textCariId.equals("")){
            binding.recyclerBarangOutlet.setVisibility(View.GONE);
            binding.searchviewBarangOutletBarcode.setVisibility(View.GONE);
            binding.searchviewSupplierKasir.setVisibility(View.VISIBLE);
        }else{

            binding.recyclerBarangOutletBarcode.setVisibility(View.VISIBLE);
            ConfigRetrofit.service.barangOutletById(textCariId, preferencedConfig.getPreferenceIdOutlet())
                    .enqueue(new Callback<ResponseBarangOutletById>() {
                        @Override
                        public void onResponse(Call<ResponseBarangOutletById> call, Response<ResponseBarangOutletById> response) {
                            if (response.isSuccessful()){

                                List<SearchBarangOutletByIdItem> dataCariId = response.body().getSearchBarangOutletById();
                                Log.d("cekDataCariId", "onResponse: "+dataCariId);
                                if (dataCariId==null){
                                    Toast.makeText(TransaksiKasirActivity.this, "Data barang kosong",
                                            Toast.LENGTH_SHORT).show();
                                }else {

                                    BarangOutletIdAdapter adapterId = new BarangOutletIdAdapter(TransaksiKasirActivity.this, dataCariId, TransaksiKasirActivity.this);
                                    binding.recyclerBarangOutletBarcode.setAdapter(adapterId);
                                }

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(
//                requestCode, resultCode, data
//        );
//
//        if (intentResult.getContents() != null) {
//
//            binding.searchviewSupplierKasir.setVisibility(View.GONE);
//            binding.searchviewBarangOutletBarcode.setVisibility(View.VISIBLE);
//            binding.searchviewBarangOutletBarcode.setQuery(intentResult.getContents(), false);
//            binding.recyclerBarangOutlet.setVisibility(View.GONE);
//            binding.recyclerBarangOutletBarcode.setVisibility(View.VISIBLE);
//
//        } else {
//            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void loadSearchBarangKasir(String textCari) {

        if (textCari.equals("")){
            binding.recyclerBarangOutlet.setVisibility(View.GONE);
            binding.txtDataKosongKasir.setVisibility(View.GONE);
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
                                    binding.recyclerBarangOutlet.setVisibility(View.VISIBLE);

                                    List<SearchBarangOutletByNamaItem> dataCari = response.body().getSearchBarangOutletByNama();

                                    CariBarangOutletAdapter adapterNama = new CariBarangOutletAdapter(TransaksiKasirActivity.this, dataCari, TransaksiKasirActivity.this);
                                    binding.recyclerBarangOutlet.setAdapter(adapterNama);
                                    binding.txtDataKosongKasir.setVisibility(View.GONE);
                                }else{
                                    binding.txtDataKosongKasir.setVisibility(View.VISIBLE);
                                    binding.txtDataKosongKasir.setText("Data pencarian dengan nama" +
                                            " '"+textCari+"' "+"tidak ditemukan atau\ntidak ada " +
                                            "dalam data toko ini");
                                    binding.recyclerBarangOutlet.setVisibility(View.GONE);
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

//                        TransaksiKasirActivity.super.onBackPressed();
                        Intent intent = new Intent(TransaksiKasirActivity.this, LoadingActivity.class);
                        startActivity(intent);
                        finish();

                        editStatusPenjualanBarang();
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

    private void editStatusPenjualanBarang() {

        ConfigRetrofit.service.editStatusPenjualanBarang(id_status_penjualan, "cancel")
                .enqueue(new Callback<ResponseEditStatusPenjualanBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusPenjualanBarang> call,
                                           Response<ResponseEditStatusPenjualanBarang> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();

                            if (status == 1) {

                                Toast.makeText(TransaksiKasirActivity.this,
                                        "Berhasil batalkan transaksi", Toast.LENGTH_SHORT).show();
                                hapusPenjualan();

                            } else {
                                Toast.makeText(TransaksiKasirActivity.this,
                                        "Gagal batalkan transaksi", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(TransaksiKasirActivity.this,
                                    "Response ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusPenjualanBarang> call, Throwable t) {
                        Toast.makeText(TransaksiKasirActivity.this,
                                "Koneksi error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void hapusPenjualan(){

        ConfigRetrofit.service.hapusPenjualan(id_status_penjualan)
                .enqueue(new Callback<ResponseHapusPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseHapusPenjualan> call, Response<ResponseHapusPenjualan> response) {
                        if (response.isSuccessful()){

                            int status = response.body().getStatus();

                            if(status == 1){

                                hapusStatusPenjualan();

                            }else{
                                Toast.makeText(TransaksiKasirActivity.this,
                                        "Hapus Data Penjualan Gagal", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(TransaksiKasirActivity.this,
                                    "Response Hapus Penjualan Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseHapusPenjualan> call, Throwable t) {
                        Toast.makeText(TransaksiKasirActivity.this,
                                "Koneksi error On hapus penjualan", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void hapusStatusPenjualan() {
//        showProgressDialogWithTitle("Membatalkan Transaksi");
//        ProgressDialog progressDialog = new ProgressDialog(TransaksiKasirActivity.this);
//        progressDialog.setMessage("Membatalkan Transaksi");
//        progressDialog.show();
        ConfigRetrofit.service.hapusStatusPenjualan(id_status_penjualan).enqueue(new Callback<ResponseHapusStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusStatusPenjualan> call, Response<ResponseHapusStatusPenjualan> response) {
                
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
//                    hideProgressDialogWithTitle();
                    int status = response.body().getStatus();
                    if (status == 1) {

                        if (!nameActivity.equals("")){

                            if (nameActivity.equals("HomeKeto")){
                                Intent intent = new Intent(TransaksiKasirActivity.this, KetoMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else if (nameActivity.equals("HomeKasir")){
                                Intent intent = new Intent(TransaksiKasirActivity.this, KasirMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }

                        finish();
                    } else {
                        Toast.makeText(TransaksiKasirActivity.this, "Gagal Membatalkan Transaksi", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    progressDialog.dismiss();
//                    hideProgressDialogWithTitle();
                    Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusPenjualan> call, Throwable t) {
//                progressDialog.dismiss();
//                hideProgressDialogWithTitle();
                Toast.makeText(TransaksiKasirActivity.this, "Terjadi Kesalahan Di Server" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Method to show Progress bar
    private void showProgressDialogWithTitle(String substring) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setMessage(substring);
        progressDialog.show();
    }

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }
}