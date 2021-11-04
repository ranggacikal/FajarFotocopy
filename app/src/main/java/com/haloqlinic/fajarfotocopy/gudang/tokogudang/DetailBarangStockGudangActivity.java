package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailBarangStockGudangBinding;
import com.haloqlinic.fajarfotocopy.model.editBarangToko.ResponseEditBarangToko;
import com.haloqlinic.fajarfotocopy.model.hapusBarangToko.ResponseHapusBarangToko;
import com.thekhaeng.pushdownanim.PushDownAnim;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailBarangStockGudangActivity extends AppCompatActivity {

    private ActivityDetailBarangStockGudangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBarangStockGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailBarangStockGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.txtNamaTokoDetailBarangGudang.setText(getIntent().getStringExtra("nama_toko"));
        binding.txtNamaBarangGudang.setText(getIntent().getStringExtra("nama_barang"));
        binding.edtStockBarangPcsGudang.setText(getIntent().getStringExtra("jumlah_pcs"));
        binding.edtStockBarangPackGudang.setText(getIntent().getStringExtra("jumlah_pack"));
        binding.edtHargaJualDetailBarangPcsGudang.setText(getIntent().getStringExtra("harga_jual"));
        binding.edtHargaJualDetailBarangPackGudang.setText(getIntent().getStringExtra("harga_jual_pack"));
        binding.edtDiskonBarangPcsGudang.setText(getIntent().getStringExtra("diskon"));
        binding.edtDiskonBarangPackGudang.setText(getIntent().getStringExtra("diskon_pack"));

        PushDownAnim.setPushDownAnimTo(binding.btnEditBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialogEdit();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnHapusBarangStockGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });

    }

    private void tampilDialogEdit() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Edit barang?")
                .setMessage("Apakah anda yakin ingin mengubah data ini?")
                .setCancelable(false)
                .setPositiveButton("Ubah", R.drawable.ic_edit, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        editDetailBarang();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void tampilDialog() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Hapus barang?")
                .setMessage("Apakah anda yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hapusData();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void hapusData() {

        String id_barang_outlet = getIntent().getStringExtra("id_barang_outlet");

        ProgressDialog progressDialogHapus = new ProgressDialog(DetailBarangStockGudangActivity.this);
        progressDialogHapus.setMessage("Hapus Barang");
        progressDialogHapus.show();

        ConfigRetrofit.service.hapusBarangToko(id_barang_outlet).enqueue(new Callback<ResponseHapusBarangToko>() {
            @Override
            public void onResponse(Call<ResponseHapusBarangToko> call, Response<ResponseHapusBarangToko> response) {
                if (response.isSuccessful()){
                    progressDialogHapus.dismiss();
                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(DetailBarangStockGudangActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(DetailBarangStockGudangActivity.this, "Gagal hapus data", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialogHapus.dismiss();
                    Toast.makeText(DetailBarangStockGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusBarangToko> call, Throwable t) {
                progressDialogHapus.dismiss();
                Toast.makeText(DetailBarangStockGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editDetailBarang() {

        String id_barang_outlet = getIntent().getStringExtra("id_barang_outlet");
        String id_barang = getIntent().getStringExtra("id_barang");
        String harga_jual = binding.edtHargaJualDetailBarangPcsGudang.getText().toString();
        String harga_jual_pack = binding.edtHargaJualDetailBarangPackGudang.getText().toString();
        String stock = binding.edtStockBarangPcsGudang.getText().toString();
        String stock_pack = binding.edtStockBarangPackGudang.getText().toString();
        String id_outlet = getIntent().getStringExtra("id_outlet");

        if (harga_jual.isEmpty()){
            binding.edtHargaJualDetailBarangPcsGudang.setError("Field tidak boleh kosong");
            binding.edtHargaJualDetailBarangPcsGudang.requestFocus();
            return;
        }

        if (harga_jual_pack.isEmpty()){
            binding.edtHargaJualDetailBarangPackGudang.setError("Field tidak boleh kosong");
            binding.edtHargaJualDetailBarangPackGudang.requestFocus();
            return;
        }

        if (stock.isEmpty()){
            binding.edtStockBarangPcsGudang.setError("Field tidak boleh kosong");
            binding.edtStockBarangPcsGudang.requestFocus();
            return;
        }

        if (stock_pack.isEmpty()){
            binding.edtStockBarangPackGudang.setError("Field tidak boleh kosong");
            binding.edtStockBarangPackGudang.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(DetailBarangStockGudangActivity.this);
        progressDialog.setMessage("Memproses Edit Data");
        progressDialog.show();

        ConfigRetrofit.service.editBarangToko(id_barang_outlet, id_barang, harga_jual, harga_jual_pack, stock,
                stock_pack, "0", "0", id_outlet).enqueue(new Callback<ResponseEditBarangToko>() {
            @Override
            public void onResponse(Call<ResponseEditBarangToko> call, Response<ResponseEditBarangToko> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        progressDialog.dismiss();
                        Toast.makeText(DetailBarangStockGudangActivity.this, "Berhasil edit data", Toast.LENGTH_SHORT).show();
                        finish();

                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(DetailBarangStockGudangActivity.this, "Gagal edit data", Toast.LENGTH_SHORT).show();


                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailBarangStockGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditBarangToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailBarangStockGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}