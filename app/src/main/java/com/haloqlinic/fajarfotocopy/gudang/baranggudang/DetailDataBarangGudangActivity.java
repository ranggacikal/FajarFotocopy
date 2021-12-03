package com.haloqlinic.fajarfotocopy.gudang.baranggudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDetailDataBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.model.ResponseHapusBarang;
import com.haloqlinic.fajarfotocopy.model.editDataBarang.ResponseEditBarang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailDataBarangGudangActivity extends AppCompatActivity {

    private ActivityDetailDataBarangGudangBinding binding;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    String id_kategori;
    String number_of_pack;
    int jumlah_pcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDataBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_kategori = getIntent().getStringExtra("id_kategori");
        String id_barang = getIntent().getStringExtra("id_barang");
        String nama_barang = getIntent().getStringExtra("nama_barang");
        String stock = getIntent().getStringExtra("stock");
        String harga_modal_gudang = getIntent().getStringExtra("harga_modal_gudang");
        String harga_modal_toko = getIntent().getStringExtra("harga_modal_toko");
        String harga_jual_toko = getIntent().getStringExtra("harga_jual_toko");
        String harga_modal_gudang_pack = getIntent().getStringExtra("harga_modal_gudang_pack");
        String harga_modal_toko_pack = getIntent().getStringExtra("harga_modal_toko_pack");
        String harga_jual_toko_pack = getIntent().getStringExtra("harga_jual_toko_pack");
        String asal_barang = getIntent().getStringExtra("asal_barang");
        String jumlah_pack = getIntent().getStringExtra("jumlah_pack");
//        String diskon = getIntent().getStringExtra("diskon");
//        String diskon_pack = getIntent().getStringExtra("diskon_pack");
        String image = getIntent().getStringExtra("image");
        String number_of_pack_intent = getIntent().getStringExtra("number_of_pack");

        binding.edtNamaBarangGudang.setText(nama_barang);
        binding.edtStockBarangPcsGudang.setText(stock);
        binding.edtStockBarangPackGudang.setText(jumlah_pack);
        binding.edtHargaModalGudangEdit.setText(harga_modal_gudang);
        binding.edtHargaModalTokoEdit.setText(harga_modal_toko);
        binding.edtHargaJualTokoEdit.setText(harga_jual_toko);
        binding.edtHargaModalGudangPackEdit.setText(harga_modal_gudang_pack);
        binding.edtHargaModalTokoPackEdit.setText(harga_modal_toko_pack);
        binding.edtHargaJualTokoPackEdit.setText(harga_jual_toko_pack);
        binding.edtAsalBarangGudang.setText(asal_barang);
//        binding.edtDiskonBarangPackGudang.setText(diskon);
//        binding.edtDiskonBarangPcsGudang.setText(diskon_pack);
        binding.edtKodeBarcodeBarangGudang.setText(id_barang);
        binding.edtStockNumberOfPackGudang.setText(number_of_pack_intent);
        binding.edtStockBarangPcsGudang.setEnabled(false);

        binding.edtStockBarangPackGudang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String jml_pack = binding.edtStockBarangPackGudang.getText().toString();
                number_of_pack = binding.edtStockNumberOfPackGudang.getText().toString();

                if (jml_pack.equals("")){
                    binding.edtStockBarangPcsGudang.setText("0");
                }else {
                    jumlah_pcs = Integer.parseInt(jml_pack) * Integer.parseInt(number_of_pack);
                    binding.edtStockBarangPcsGudang.setText(String.valueOf(jumlah_pcs));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtStockNumberOfPackGudang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String jml_pack = binding.edtStockBarangPackGudang.getText().toString();
                number_of_pack = binding.edtStockNumberOfPackGudang.getText().toString();

                if (number_of_pack.equals("")){
                    binding.edtStockBarangPcsGudang.setText("0");
                }else {
                    jumlah_pcs = Integer.parseInt(jml_pack) * Integer.parseInt(number_of_pack);
                    binding.edtStockBarangPcsGudang.setText(String.valueOf(jumlah_pcs));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Glide.with(DetailDataBarangGudangActivity.this)
                .load(image)
                .error(R.drawable.icon_img_error)
                .into(binding.imageDetailBarangGudang);

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
                        tampilDialogHapus();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imageDetailBarangGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackDetailBarangStockGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }

    private void tampilDialogHapus() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Hapus Data Barang?")
                .setMessage("Apakah anda yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hapusBarang();
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

    private void hapusBarang() {

        String id_barang = getIntent().getStringExtra("id_barang");

        ProgressDialog progressDialog = new ProgressDialog(DetailDataBarangGudangActivity.this);
        progressDialog.setMessage("Menghapus Data");
        progressDialog.show();

        ConfigRetrofit.service.hapusBarang(id_barang).enqueue(new Callback<ResponseHapusBarang>() {
            @Override
            public void onResponse(Call<ResponseHapusBarang> call, Response<ResponseHapusBarang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                     int status = response.body().getStatus();

                     if (status == 1){

                         Toast.makeText(DetailDataBarangGudangActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                         finish();
                     }else{
                         Toast.makeText(DetailDataBarangGudangActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                     }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailDataBarangGudangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusBarang> call, Throwable t) {
                Toast.makeText(DetailDataBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihGambar() {

        ImagePicker.with(DetailDataBarangGudangActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Log.d("requestCodeImg", "onActivityResult: " + requestCode);

            Uri uri1 = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.imageDetailBarangGudang.setImageBitmap(bitmap);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailDataBarangGudangActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void tampilDialogEdit() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Edit Data Barang?")
                .setMessage("Apakah anda yakin ingin mengubah data ini?")
                .setCancelable(false)
                .setPositiveButton("Ubah", R.drawable.ic_edit, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        editBarang();
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

    private void editBarang() {

        String id_barang = getIntent().getStringExtra("id_barang");
        String nama_barang = binding.edtNamaBarangGudang.getText().toString();
        String stock = binding.edtStockBarangPcsGudang.getText().toString();
        String harga_modal_gudang = binding.edtHargaModalGudangEdit.getText().toString();
        String harga_modal_toko = binding.edtHargaModalTokoEdit.getText().toString();
        String harga_jual_toko = binding.edtHargaJualTokoEdit.getText().toString();
        String harga_modal_gudang_pack = binding.edtHargaModalGudangPackEdit.getText().toString();
        String harga_modal_toko_pack = binding.edtHargaModalTokoPackEdit.getText().toString();
        String harga_jual_toko_pack = binding.edtHargaJualTokoPackEdit.getText().toString();
        String asal_barang = binding.edtAsalBarangGudang.getText().toString();
        String jumlah_pack = binding.edtStockBarangPackGudang.getText().toString();
        String diskon = binding.edtDiskonBarangPcsGudang.getText().toString();
        String diskon_pack = binding.edtDiskonBarangPackGudang.getText().toString();
        number_of_pack = binding.edtStockNumberOfPackGudang.getText().toString();
        String image_barang = "";

        if (nama_barang.isEmpty()){
            binding.edtNamaBarangGudang.setError("Field tidak boleh kosong");
            binding.edtNamaBarangGudang.requestFocus();
            return;
        }

        if (stock.isEmpty()){
            binding.edtStockBarangPcsGudang.setError("Field tidak boleh kosong");
            binding.edtStockBarangPcsGudang.requestFocus();
            return;
        }

        if (harga_modal_gudang.isEmpty()){
            binding.edtHargaModalGudangEdit.setError("Field tidak boleh kosong");
            binding.edtHargaModalGudangEdit.requestFocus();
            return;
        }

        if (harga_modal_toko.isEmpty()){
            binding.edtHargaModalTokoEdit.setError("Field tidak boleh kosong");
            binding.edtHargaModalTokoEdit.requestFocus();
            return;
        }

        if (harga_jual_toko.isEmpty()){
            binding.edtHargaJualTokoEdit.setError("Field tidak boleh kosong");
            binding.edtHargaJualTokoEdit.requestFocus();
            return;
        }

        if (harga_modal_gudang_pack.isEmpty()){
            binding.edtHargaModalGudangPackEdit.setError("Field tidak boleh kosong");
            binding.edtHargaModalGudangPackEdit.requestFocus();
            return;
        }

        if (harga_modal_toko_pack.isEmpty()){
            binding.edtHargaModalTokoPackEdit.setError("Field tidak boleh kosong");
            binding.edtHargaModalGudangPackEdit.requestFocus();
            return;
        }

        if (harga_jual_toko_pack.isEmpty()){
            binding.edtHargaJualTokoPackEdit.setError("Field tidak boleh kosong");
            binding.edtHargaJualTokoPackEdit.requestFocus();
            return;
        }

        if (asal_barang.isEmpty()){
            binding.edtAsalBarangGudang.setError("Field tidak boleh kosong");
            binding.edtAsalBarangGudang.requestFocus();
            return;
        }

        if (jumlah_pack.isEmpty()){
            binding.edtStockBarangPackGudang.setError("Field tidak boleh kosong");
            binding.edtStockBarangPackGudang.requestFocus();
            return;
        }

        if (number_of_pack.isEmpty()){
            binding.edtStockNumberOfPackGudang.setError("Field Tidak Boleh Kosong");
            binding.edtStockNumberOfPackGudang.requestFocus();
            return;
        }

//        if (diskon.isEmpty()){
//            binding.edtDiskonBarangPcsGudang.setError("Field tidak boleh kosong");
//            binding.edtDiskonBarangPcsGudang.requestFocus();
//            return;
//        }
//
//        if (diskon_pack.isEmpty()){
//            binding.edtDiskonBarangPcsGudang.setError("Field tidak boleh kosong");
//            binding.edtDiskonBarangPackGudang.requestFocus();
//            return;
//        }

        if (bitmap == null){
            image_barang = getIntent().getStringExtra("image");
        }else{
            image_barang = imageToString();
        }

        ProgressDialog progressDialog = new ProgressDialog(DetailDataBarangGudangActivity.this);
        progressDialog.setMessage("Mengedit Data");
        progressDialog.show();

        ConfigRetrofit.service.editBarang(id_barang, nama_barang, stock, harga_modal_gudang, harga_modal_toko,
                harga_jual_toko, harga_modal_gudang_pack, harga_modal_toko_pack, harga_jual_toko_pack, asal_barang,
                jumlah_pack, number_of_pack, image_barang, id_kategori).enqueue(new Callback<ResponseEditBarang>() {
            @Override
            public void onResponse(Call<ResponseEditBarang> call, Response<ResponseEditBarang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                     int status = response.body().getStatus();

                     if (status==1){

                         Toast.makeText(DetailDataBarangGudangActivity.this, "Edit Data Berhasil", Toast.LENGTH_SHORT).show();
                         finish();

                     }else{
                         Toast.makeText(DetailDataBarangGudangActivity.this, "Edit Data Gagal", Toast.LENGTH_SHORT).show();
                     }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailDataBarangGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditBarang> call, Throwable t) {
                Toast.makeText(DetailDataBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}