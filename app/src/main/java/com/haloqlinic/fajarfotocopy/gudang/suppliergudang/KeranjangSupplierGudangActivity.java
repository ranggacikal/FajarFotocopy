package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.KeranjangSupplierAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKeranjangSupplierGudangBinding;
import com.haloqlinic.fajarfotocopy.formatNumber.NumberTextWatcher;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.PembayaranKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiBerhasilActivity;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanGudang.ResponseEditStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.BarangPenjualanGudangItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.ResponseBarangPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanGudang.ResponseHapusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.sumPenjualanGudang.ResponseSumPenjualanGudang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangSupplierGudangActivity extends AppCompatActivity {

    private ActivityKeranjangSupplierGudangBinding binding;
    String id_status_penjualan_gudang;

    KeranjangSupplierAdapter adapter;

    int total, jumlahBayar, kurang, kembalian, jumlahKurang;

    private Bitmap bitmap;

    List<BarangPenjualanGudangItem> dataBarang;

    String metode_bayar;

    private String[] metodeBayarItem = {"Cash", "Debit"};
    private String[] jenisBayarItem = {"Lunas", "Tempo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeranjangSupplierGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");

        binding.rvKeranjangSupplier.setHasFixedSize(true);
        binding.rvKeranjangSupplier.setLayoutManager(new LinearLayoutManager(KeranjangSupplierGudangActivity.this));

        binding.edtJumlahBayarSupplier.addTextChangedListener(new NumberTextWatcher(binding.edtJumlahBayarSupplier));

        loadDataKeranjang();
        loadSumTotal();

        ArrayAdapter<String> adapterMetode = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                R.layout.spinner_item, metodeBayarItem);
        adapterMetode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMetodeBayarSupplier.setAdapter(adapterMetode);

        ArrayAdapter<String> adapterJenis = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                R.layout.spinner_item, jenisBayarItem);
        adapterMetode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerJenisBayarSupplier.setAdapter(adapterJenis);

        binding.spinnerMetodeBayarSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metode_bayar = binding.spinnerMetodeBayarSupplier.getSelectedItem().toString();

                if (metode_bayar.equals("debit") || metode_bayar.equals("Debit")) {
                    binding.imgBuktiTfSupplier.setVisibility(View.VISIBLE);
                } else {
                    binding.imgBuktiTfSupplier.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerJenisBayarSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String jenis_bayar = binding.spinnerJenisBayarSupplier.getSelectedItem().toString();

                if (jenis_bayar.equals("Tempo")) {
                    binding.relativeTotalKurang.setVisibility(View.VISIBLE);
                } else {
                    binding.relativeTotalKurang.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                switch (direction) {

                    case ItemTouchHelper.LEFT:
                        String id_penjualan_gudang = dataBarang.get(position).getIdPenjualanGudang();
                        hapusPenjualanGudang(id_penjualan_gudang);
                        break;

                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.parseColor("#e60026"))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_white)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvKeranjangSupplier);

        PushDownAnim.setPushDownAnimTo(binding.btnHitungKeranjangSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jumlahBayarStr = binding.edtJumlahBayarSupplier.getText().toString();
                        String jumlahReplace = jumlahBayarStr.replace(".", "").replace(",", "");
                        jumlahBayar = Integer.parseInt(jumlahReplace);
                        kembalian = jumlahBayar - total;
                        jumlahKurang = total - jumlahBayar;

                        binding.txtKembalianSupplier.setText("Rp" + NumberFormat.getInstance().format(kembalian));
                        binding.txtTotalKurang.setText("Rp" + NumberFormat.getInstance().format(jumlahKurang));
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imgBuktiTfSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnKeranjangSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bayar();
                    }
                });

    }

    private void bayar() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        progressDialog.setMessage("Melakukan Checkout");
        progressDialog.show();

        String image;

        if (metode_bayar.equals("debit")||metode_bayar.equals("Debit")){
            image = imageToString();
        }else{
            image = "";
        }

        ConfigRetrofit.service.editStatusPenjualanGudang(id_status_penjualan_gudang, "Selesai",
                metode_bayar, String.valueOf(total), String.valueOf(jumlahBayar), String.valueOf(jumlahKurang),
                image).enqueue(new Callback<ResponseEditStatusPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseEditStatusPenjualanGudang> call, Response<ResponseEditStatusPenjualanGudang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Intent intent = new Intent(KeranjangSupplierGudangActivity.this, TransaksiBerhasilActivity.class);
                        intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                        startActivity(intent);

                    }else{
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Gagal, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditStatusPenjualanGudang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihGambar() {

        ImagePicker.with(KeranjangSupplierGudangActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Log.d("requestCodeImg", "onActivityResult: " + requestCode);

            Uri uri1 = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.imgBuktiTfSupplier.setImageBitmap(bitmap);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void loadSumTotal() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        progressDialog.setMessage("Memperbarui Total Harga");
        progressDialog.show();

        ConfigRetrofit.service.sumTotalPenjualanGudang(id_status_penjualan_gudang).enqueue(new Callback<ResponseSumPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseSumPenjualanGudang> call, Response<ResponseSumPenjualanGudang> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {
                        total = Integer.parseInt(response.body().getDataSumPenjualanGudang().get(0).getTotal());
                        binding.txtTotalSupplier.setText("Rp" + NumberFormat.getInstance().format(total));
                    } else {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Gagal memperbarui Total", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSumPenjualanGudang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hapusPenjualanGudang(String id_penjualan_gudang) {

        ConfigRetrofit.service.hapusPenjualanGudang(id_penjualan_gudang).enqueue(new Callback<ResponseHapusPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseHapusPenjualanGudang> call, Response<ResponseHapusPenjualanGudang> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                        loadDataKeranjang();

                    } else {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Hapus Data Gagal", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPenjualanGudang> call, Throwable t) {
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadDataKeranjang() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.barangPenjualanGudang(id_status_penjualan_gudang).enqueue(new Callback<ResponseBarangPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseBarangPenjualanGudang> call, Response<ResponseBarangPenjualanGudang> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataBarang = response.body().getBarangPenjualanGudang();
                        adapter = new KeranjangSupplierAdapter(KeranjangSupplierGudangActivity.this, dataBarang);
                        binding.rvKeranjangSupplier.setAdapter(adapter);

                    } else {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Anda Belum memilih barang", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBarangPenjualanGudang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}