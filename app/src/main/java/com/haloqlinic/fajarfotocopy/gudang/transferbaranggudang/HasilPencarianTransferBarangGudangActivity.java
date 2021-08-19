package com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariTransferBarangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariTransferBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityHasilPencarianTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.countBarangTransfer.ResponseCountBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.hapusStatusTransfer.ResponseHapusStatusTransfer;
import com.haloqlinic.fajarfotocopy.model.hapusTransferCancel.ResponseHapusTransferCancel;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.ResponseBarangOutletById;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.ResponseBarangOutletByNama;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.scan.Capture;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HasilPencarianTransferBarangGudangActivity extends AppCompatActivity {

    private ActivityHasilPencarianTransferBarangGudangBinding binding;

    String id_outlet = "";
    String id_barang = "";
    public String id_status_transfer = "";
    String count_keranjang = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHasilPencarianTransferBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_outlet = getIntent().getStringExtra("id_outlet");
        id_status_transfer = getIntent().getStringExtra("id_status_transfer");

        getCount();

        binding.rvTransferBarang.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(HasilPencarianTransferBarangGudangActivity.this,
                2, GridLayoutManager.VERTICAL, false);
        binding.rvTransferBarang.setLayoutManager(manager);

        PushDownAnim.setPushDownAnimTo(binding.linearBackHasilPencarianTransferGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(HasilPencarianTransferBarangGudangActivity.this)
                                .setTitle("Batal Transfer Barang?")
                                .setMessage("Apakah anda yakin ingin membatalkan transfer barang?")
                                .setCancelable(false)
                                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {

                                        hapusTransferBarangCancel();
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

        PushDownAnim.setPushDownAnimTo(binding.btnBarcodeTransferBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(
                                HasilPencarianTransferBarangGudangActivity.this
                        );

                        intentIntegrator.setPrompt("Tekan volume atas untuk menyalakan flash");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(Capture.class);
                        intentIntegrator.initiateScan();
                    }
                });

        binding.searchviewTransferbaranggudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchviewTransferbaranggudang.setQueryHint("Nama Barang");
                binding.searchviewTransferbaranggudang.setIconified(false);
            }
        });

        binding.searchviewTransferbaranggudang.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                cariBarang(newText);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnChekoutTransfer)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HasilPencarianTransferBarangGudangActivity.this,
                                KeranjangTransferBarangGudangActivity.class);
                        intent.putExtra("id_status_transfer", id_status_transfer);
                        startActivity(intent);
                    }
                });

    }

    public void getCount() {

        ProgressDialog progressDialog = new ProgressDialog(HasilPencarianTransferBarangGudangActivity.this);
        progressDialog.setMessage("Memuat Jumlah Barang");
        progressDialog.show();

        ConfigRetrofit.service.countTransferBarang(id_status_transfer).enqueue(new Callback<ResponseCountBarangTransfer>() {
            @Override
            public void onResponse(Call<ResponseCountBarangTransfer> call, Response<ResponseCountBarangTransfer> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        count_keranjang = String.valueOf(response.body().getCountBarangTransfer());
                        binding.transferTotalGudang.setText(count_keranjang);

                    }else{
                        binding.transferTotalGudang.setText("0");
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Gagal memuat Jumlah data",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCountBarangTransfer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {

            id_barang = intentResult.getContents();
            cariBarangById();

        } else {
            Toast.makeText(this, "Tidak ada barcode yg anda scan", Toast.LENGTH_SHORT).show();
        }
    }

    private void cariBarangById() {

        if (id_barang.equals("")){

            binding.rvTransferBarang.setVisibility(View.GONE);

        }else{
            ConfigRetrofit.service.barangOutletById(id_barang, id_outlet).enqueue(new Callback<ResponseBarangOutletById>() {
                @Override
                public void onResponse(Call<ResponseBarangOutletById> call, Response<ResponseBarangOutletById> response) {
                    if (response.isSuccessful()){

                        int status = response.body().getStatus();
                        String pesan = response.body().getPesan();

                        List<SearchBarangOutletByIdItem> dataId = response.body().getSearchBarangOutletById();

                        if (status==1){

                            CariTransferBarangIdAdapter adapterId = new CariTransferBarangIdAdapter(HasilPencarianTransferBarangGudangActivity.this,
                                    dataId);
                            binding.rvTransferBarang.setAdapter(adapterId);

                        }else{
                            Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "response gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBarangOutletById> call, Throwable t) {
                    Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(HasilPencarianTransferBarangGudangActivity.this)
                .setTitle("Batal Transfer Barang?")
                .setMessage("Apakah anda yakin ingin membatalkan transfer barang?")
                .setCancelable(false)
                .setPositiveButton("Iya", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        HasilPencarianTransferBarangGudangActivity.super.onBackPressed();

                        hapusTransferBarangCancel();
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

    private void hapusTransferBarangCancel() {

        ProgressDialog progressDialog = new ProgressDialog(HasilPencarianTransferBarangGudangActivity.this);
        progressDialog.setMessage("Menghapus data transfer barang");
        progressDialog.show();

        ConfigRetrofit.service.hapusTransferCancel(id_status_transfer).enqueue(new Callback<ResponseHapusTransferCancel>() {
            @Override
            public void onResponse(Call<ResponseHapusTransferCancel> call, Response<ResponseHapusTransferCancel> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    progressDialog.dismiss();

                    if (status==1){

                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        hapusStatusTransfer();

                    }else{
                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusTransferCancel> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void hapusStatusTransfer() {

        ProgressDialog progressDialog = new ProgressDialog(HasilPencarianTransferBarangGudangActivity.this);
        progressDialog.setMessage("Membatalkan Transfer barang");
        progressDialog.show();

        ConfigRetrofit.service.hapusStatusTransfer(id_status_transfer).enqueue(new Callback<ResponseHapusStatusTransfer>() {
            @Override
            public void onResponse(Call<ResponseHapusStatusTransfer> call, Response<ResponseHapusStatusTransfer> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){

                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        finish();

                    }else{
                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan
                                , Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusStatusTransfer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cariBarang(String newText) {

        if (newText.equals("")){
            binding.rvTransferBarang.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.barangOutletByNama(newText, id_outlet).enqueue(new Callback<ResponseBarangOutletByNama>() {
                @Override
                public void onResponse(Call<ResponseBarangOutletByNama> call, Response<ResponseBarangOutletByNama> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        binding.rvTransferBarang.setVisibility(View.VISIBLE);

                        List<SearchBarangOutletByNamaItem> dataBarang = response.body().getSearchBarangOutletByNama();
                        int status = response.body().getStatus();
                        String pesan = response.body().getPesan();

                        if (status == 1) {

                            CariTransferBarangAdapter adapter = new CariTransferBarangAdapter(HasilPencarianTransferBarangGudangActivity.this,
                                    dataBarang, HasilPencarianTransferBarangGudangActivity.this);
                            binding.rvTransferBarang.setAdapter(adapter);
                        } else {
                            Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Data Kosong/Response Dari Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBarangOutletByNama> call, Throwable t) {
                    Toast.makeText(HasilPencarianTransferBarangGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }



    }
}