package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangIdPenjualanAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangPenjualanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivitySupplierGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.TambahBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanByIdStatus.ResponseHapusPenjualanGudangByIdStatus;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualanGudang.ResponseHapusStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class SupplierGudangActivity extends AppCompatActivity {

    private ActivitySupplierGudangBinding binding;

    public String id_status_penjualan_gudang, nama_user;

    boolean searchId = false;
    boolean searchName = false;

    Dialog dialog;
    String textId = "";
    String textName = "";
//    Button btnBatal;
//    TextView textKembali;
    Context context;
    Button btnBatal;
    TextView txtKembali;

    private SharedPreferencedConfig preferencedConfig;

    public SupplierGudangActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        nama_user = preferencedConfig.getPreferenceNama();
//        btnBatal = view.findViewById(R.id.btn_batal);
//        textKembali = view.findViewById(R.id.text_kembali);
        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");


        binding.searchviewSupplierBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewSupplierBarcode.setQueryHint("ID Barang");
                binding.searchviewSupplierBarcode.setIconified(false);
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeSupplierGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                SupplierGudangActivity.this
                        );

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

        binding.searchviewSupplierBarcode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCariId) {
                loadDataById(textCariId);
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
                        Log.d("cekSupplier", "id: " + id_status_penjualan_gudang);
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
//                        dialog = new Dialog(context);
//                        dialog.setContentView(R.layout.dialog_batal_transaksi);
//                        txtKembali = dialog.findViewById(R.id.text_kembali);
//                        btnBatal = dialog.findViewById(R.id.btn_batal);
//
//                        dialog.show();
//
//                        txtKembali.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        btnBatal.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                hapusStatusPenjualanBarang();
//                                dialog.dismiss();
//                            }
//                        });


                    }
                });
    }

//    private void tampilDialog() {
//
//        dialog = new Dialog(context);
//        dialog.setContentView(R.layout.dialog_batal_transaksi);
//        txtKembali = dialog.findViewById(R.id.text_kembali);
//        btnBatal = dialog.findViewById(R.id.btn_batal);
//
//        txtKembali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnBatal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hapusStatusPenjualanBarang();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//
//    }


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(SupplierGudangActivity.this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
                } else {
                    loadDataById(result.getContents());
                }
            });

    private void loadDataById(String id_barcode) {
        if (id_barcode.equals("")) {
            binding.recyclerBarangGudangScanner.setVisibility(View.GONE);
            binding.recyclerBarangGudang.setVisibility(View.GONE);
            binding.txtDataKosongSupplier.setVisibility(View.GONE);
        } else {
            ProgressDialog progressDialog = new ProgressDialog(SupplierGudangActivity.this);
            progressDialog.setMessage("Mencari Data");
            progressDialog.show();

            ConfigRetrofit.service.cariBarangById(id_barcode).enqueue(new Callback<ResponseCariBarangById>() {
                @Override
                public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();

                        int status = response.body().getStatus();

                        if (status == 1) {
                            binding.recyclerBarangGudangScanner.setVisibility(View.VISIBLE);
                            binding.txtDataKosongSupplier.setVisibility(View.GONE);

                            List<SearchBarangByIdItem> dataBarangScanner = response.body().getSearchBarangById();
                            CariBarangIdPenjualanAdapter adapterId = new CariBarangIdPenjualanAdapter(
                                    SupplierGudangActivity.this, dataBarangScanner,
                                    SupplierGudangActivity.this
                            );
                            binding.recyclerBarangGudangScanner.setHasFixedSize(true);
                            GridLayoutManager manager = new GridLayoutManager(SupplierGudangActivity.this,
                                    2, GridLayoutManager.VERTICAL, false);
                            binding.recyclerBarangGudangScanner.setLayoutManager(manager);
                            binding.recyclerBarangGudangScanner.setAdapter(adapterId);
                            binding.recyclerBarangGudangScanner.setVisibility(View.VISIBLE);
                            binding.recyclerBarangGudang.setVisibility(View.GONE);

                        } else {
                            binding.txtDataKosongSupplier.setText("Data pencarian dengan nama" +
                                    " '" + id_barcode + "' " + "tidak ditemukan atau\ntidak ada " +
                                    "dalam data toko ini");
                            binding.txtDataKosongSupplier.setVisibility(View.VISIBLE);
                            binding.recyclerBarangGudangScanner.setVisibility(View.GONE);
                        }

                    } else {
                        binding.recyclerBarangGudangScanner.setVisibility(View.GONE);
                        binding.txtDataKosongSupplier.setVisibility(View.GONE);
                        Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                    binding.recyclerBarangGudangScanner.setVisibility(View.GONE);
                    Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(
//                requestCode, resultCode, data
//        );
//
//        if (intentResult.getContents() != null) {
//
//            loadDataById(intentResult.getContents());
//
//        } else {
//            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void searchBarangNama(String query) {

        if (query.equals("")) {
            binding.recyclerBarangGudang.setVisibility(View.GONE);
            binding.txtDataKosongSupplier.setVisibility(View.GONE);
        } else {

            ConfigRetrofit.service.cariBarang(query).enqueue(new Callback<ResponseCariBarangByNama>() {
                @Override
                public void onResponse(Call<ResponseCariBarangByNama> call, Response<ResponseCariBarangByNama> response) {
                    if (response.isSuccessful()) {

                        int status = response.body().getStatus();

                        if (status == 1) {
                            binding.recyclerBarangGudang.setVisibility(View.VISIBLE);
                            binding.txtDataKosongSupplier.setVisibility(View.GONE);

                            List<SearchBarangByNamaItem> dataBarang = response.body().getSearchBarangByNama();
                            CariBarangPenjualanAdapter adapter = new CariBarangPenjualanAdapter(SupplierGudangActivity.this,
                                    dataBarang, SupplierGudangActivity.this);
                            binding.recyclerBarangGudang.setHasFixedSize(true);
                            GridLayoutManager manager = new GridLayoutManager(SupplierGudangActivity.this,
                                    2, GridLayoutManager.VERTICAL, false);
                            binding.recyclerBarangGudang.setLayoutManager(manager);
                            binding.recyclerBarangGudang.setAdapter(adapter);

                        } else {
                            binding.txtDataKosongSupplier.setText("Data pencarian dengan nama" +
                                    " '"+query+"' "+"tidak ditemukan atau\ntidak ada " +
                                    "dalam data toko ini");
                            binding.txtDataKosongSupplier.setVisibility(View.VISIBLE);
                            binding.recyclerBarangGudang.setVisibility(View.GONE);
                        }

                    } else {
                        binding.recyclerBarangGudang.setVisibility(View.GONE);
                        binding.txtDataKosongSupplier.setVisibility(View.GONE);
                        Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariBarangByNama> call, Throwable t) {
                    binding.recyclerBarangGudang.setVisibility(View.GONE);
                    Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                }
            });

        }

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
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(SupplierGudangActivity.this, "Berhasil menghapus semua data",
                                Toast.LENGTH_SHORT).show();
                        hapusStatusPenjualanBarang();
                    } else {
                        Toast.makeText(SupplierGudangActivity.this, "Gagal hapus semua data",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
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
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        finish();

                    } else {
                        Toast.makeText(SupplierGudangActivity.this, "Gagal Menyelesaikan transaksi",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SupplierGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusPenjualanGudang> call, Throwable t) {
                Toast.makeText(SupplierGudangActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (searchId = true) {
            loadDataById(textId);
        } else if (searchName = true) {
            searchBarangNama(textName);
        }

    }
}