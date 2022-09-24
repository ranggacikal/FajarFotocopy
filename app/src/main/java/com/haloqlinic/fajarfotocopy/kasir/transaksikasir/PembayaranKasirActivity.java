package com.haloqlinic.fajarfotocopy.kasir.transaksikasir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.dialog.DialogValidateAdapter;
import com.haloqlinic.fajarfotocopy.adapter.kasir.PembayaranAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPembayaranKasirBinding;
import com.haloqlinic.fajarfotocopy.model.dataBarangOutletList.DataBarangOutletListItem;
import com.haloqlinic.fajarfotocopy.model.dataBarangOutletList.ResponseBarangOutletList;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanBarang.ResponseEditStatusPenjualanBarang;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.BarangPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.ResponseDataBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusBarangPenjualan.ResponseHapusBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualan.ResponseHapusPenjualan;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualan.ResponseUpdateStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.validateBarang.DataValidateBarangItem;
import com.haloqlinic.fajarfotocopy.model.validateBarang.ResponseValidateBarang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class PembayaranKasirActivity extends AppCompatActivity {

    private ActivityPembayaranKasirBinding binding;
    String id_status_penjualan;
    String tanggal, tanggal2;
    String nama_kasir, metode_bayar;
    String value = "";
    int discount;
    int total;

    int totalSeluruh = 0;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    private Bitmap bitmap;
    String from_keto;
    ArrayList<String> idBarangPenjualan = new ArrayList<>();
    ArrayList<String> jumlahPackPenjualan = new ArrayList<>();
    ArrayList<String> jumlahPcsPenjualan = new ArrayList<>();
    ArrayList<String> idOutletPenjualan = new ArrayList<>();
    List<BarangPenjualanItem> dataBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPembayaranKasirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        from_keto = getIntent().getStringExtra("from_keto");

        preferencedConfig = new SharedPreferencedConfig(this);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        tanggal = date;

        id_status_penjualan = getIntent().getStringExtra("id_status_penjualan");
        Log.d("getIdStatusPenjualan", "onCreate: " + id_status_penjualan);

        binding.edtDiskonPembayaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                value = binding.edtDiskonPembayaran.getText().toString();

                if (value.equals("")) {
                    totalSeluruh = total - 0;
                } else {
                    totalSeluruh = total - Integer.parseInt(value);
                }

                Log.d("checkTotalSeluruh", "onTextChanged: " + "Rp." + totalSeluruh);
                binding.textTotalBayarPembayaran.setText("Rp" + NumberFormat.getInstance().format(totalSeluruh));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.spinnerPembayaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metode_bayar = binding.spinnerPembayaran.getSelectedItem().toString();
                Log.d("cekMetodeBayar", "onItemSelected: " + metode_bayar);

                if (metode_bayar.equals("Cash")) {
                    binding.linearBuktiTfPembayaran.setVisibility(View.GONE);
                } else {
                    binding.linearBuktiTfPembayaran.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        PushDownAnim.setPushDownAnimTo(binding.linearBackPembayaranKasir)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PembayaranKasirActivity.this, TransaksiKasirActivity.class));
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imgBuktiTfPembayaran)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihGambar();
                    }
                });

        binding.idTransaksiPembayaran.setText(id_status_penjualan);
        binding.textTanggalPembayaran.setText(tanggal);
        binding.textNamaKasirPembayaran.setText(preferencedConfig.getPreferenceNama());

        binding.rvListBarangPembayaran.setHasFixedSize(true);
        binding.rvListBarangPembayaran.setLayoutManager(new LinearLayoutManager(PembayaranKasirActivity.this));

        PushDownAnim.setPushDownAnimTo(binding.btnBayarPembayaran)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog progressDialog = new ProgressDialog(PembayaranKasirActivity.this);
                        progressDialog.setMessage("memproses pembayaran");
                        progressDialog.show();
                        validateDataBarang(progressDialog);
                    }
                });

        slideHapus();

        loadDataPembayaran();

    }

    private void slideHapus() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( 0,
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
                        String id_penjualan = dataBarang.get(position).getIdPenjualan();
                        hapusPenjualan(id_penjualan);
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY,
                        actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.parseColor("#e60026"))
                        .addSwipeLeftActionIcon(R.drawable.ic_trash)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvListBarangPembayaran);
    }

    private void hapusPenjualan(String id_penjualan) {

        ConfigRetrofit.service.hapusBarangPenjualan(id_penjualan).enqueue(new Callback<ResponseHapusBarangPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusBarangPenjualan> call, Response<ResponseHapusBarangPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(PembayaranKasirActivity.this,
                                "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                        loadDataPembayaran();

                    } else {
                        Toast.makeText(PembayaranKasirActivity.this,
                                "Hapus Data Gagal", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(PembayaranKasirActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusBarangPenjualan> call, Throwable t) {
                Toast.makeText(PembayaranKasirActivity.this,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateDataBarang(ProgressDialog progressDialog) {
        ConfigRetrofit.service.validateBarang(idBarangPenjualan, idOutletPenjualan, jumlahPcsPenjualan,
                jumlahPackPenjualan).enqueue(new Callback<ResponseValidateBarang>() {
            @Override
            public void onResponse(Call<ResponseValidateBarang> call, Response<ResponseValidateBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();
                    if (status == 1) {
                        bayar(progressDialog);
                    } else {
                        progressDialog.dismiss();
                        List<DataValidateBarangItem> dataValidateBarang = response.body().getDataValidateBarang();
                        showDialogValidateBarang(dataValidateBarang);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(PembayaranKasirActivity.this,
                            "Response Gagal, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValidateBarang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PembayaranKasirActivity.this,
                        "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                Log.d("errorMessageFail", "onFailure: "+t.getMessage());
            }
        });
    }

    private void showDialogValidateBarang(List<DataValidateBarangItem> dataValidateBarang) {

        Dialog dialogValidate = new Dialog(PembayaranKasirActivity.this);
        dialogValidate.setContentView(R.layout.dialog_validate_barang);
        dialogValidate.setCancelable(false);
        dialogValidate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogValidate.show();
        Button btnClose = dialogValidate.findViewById(R.id.btn_close_dialog_validate_barang);
        loadDataValidateBarang(dialogValidate, dataValidateBarang);
        PushDownAnim.setPushDownAnimTo(btnClose)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogValidate.dismiss();
                    }
                });

    }

    private void loadDataValidateBarang(Dialog dialogValidate, List<DataValidateBarangItem> dataValidateBarang) {

        ProgressDialog progressDialogValidate = new ProgressDialog(PembayaranKasirActivity.this);
        progressDialogValidate.setMessage("Memuat Data");
        progressDialogValidate.show();

        ArrayList<String> idBarangOutletList = new ArrayList<>();
        ArrayList<String> idOutletList = new ArrayList<>();

        for (int a = 0; a < dataValidateBarang.size(); a++) {
            idBarangOutletList.add(dataValidateBarang.get(a).getIdBarang());
            idOutletList.add(dataValidateBarang.get(a).getIdOutlet());
        }

        ConfigRetrofit.service.barangOutletList(idBarangOutletList, idOutletList)
                .enqueue(new Callback<ResponseBarangOutletList>() {
                    @Override
                    public void onResponse(Call<ResponseBarangOutletList> call, Response<ResponseBarangOutletList> response) {
                        if (response.isSuccessful()) {

                            boolean isSukses = response.body().isSukses();
                            if (isSukses){
                                progressDialogValidate.dismiss();
                                RecyclerView rvValidateDialog = dialogValidate.findViewById(R.id.rv_dialog_validate_barang);
                                rvValidateDialog.setHasFixedSize(true);
                                rvValidateDialog.setLayoutManager(new LinearLayoutManager(PembayaranKasirActivity.this));
                                List<DataBarangOutletListItem> dataBarang = response.body().getDataBarangOutletList();
                                DialogValidateAdapter adapter = new DialogValidateAdapter(PembayaranKasirActivity.this, dataBarang);
                                rvValidateDialog.setAdapter(adapter);
                            } else {
                                progressDialogValidate.dismiss();
                                Toast.makeText(PembayaranKasirActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialogValidate.dismiss();
                            Toast.makeText(PembayaranKasirActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBarangOutletList> call, Throwable t) {
                        progressDialogValidate.dismiss();
                        Toast.makeText(PembayaranKasirActivity.this, "Cek koneksi Internet", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void pilihGambar() {

        ImagePicker.with(PembayaranKasirActivity.this)
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
            binding.imgBuktiTfPembayaran.setImageBitmap(bitmap);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void bayar(ProgressDialog progressDialog) {

        String postTotal = "";
        String postDiskon = "";

        if (totalSeluruh == 0) {
            postTotal = String.valueOf(total);
        } else {
            postTotal = String.valueOf(totalSeluruh);
        }
        Log.d("totalSeluruh", "total: " + totalSeluruh);


        if (value.equals("")) {
            postDiskon = "0";
        } else {
            postDiskon = value;
        }

        Log.d("total", "total: " + value);

        ConfigRetrofit.service.updateStatusPenjualan(id_status_penjualan, tanggal2,
                "selesai", preferencedConfig.getPreferenceIdOutlet(), metode_bayar,
                postTotal, postDiskon, imageToString())
                .enqueue(new Callback<ResponseUpdateStatusPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStatusPenjualan> call, Response<ResponseUpdateStatusPenjualan> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            if (status == 1) {
                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Pembayaran Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PembayaranKasirActivity.this, TransaksiBerhasilActivity.class);
                                intent.putExtra("id_status_penjualan", id_status_penjualan);
                                if (from_keto != null) {
                                    intent.putExtra("from_keto", from_keto);
                                }
                                editStatusPenjualanBarang();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Pembayaran Gagal", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PembayaranKasirActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStatusPenjualan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PembayaranKasirActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void editStatusPenjualanBarang() {

        ConfigRetrofit.service.editStatusPenjualanBarang(id_status_penjualan, "selesai")
                .enqueue(new Callback<ResponseEditStatusPenjualanBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusPenjualanBarang> call,
                                           Response<ResponseEditStatusPenjualanBarang> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();

                            if (status == 1) {

                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Berhasil Set Data Pembayaran", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PembayaranKasirActivity.this,
                                        "Gagal Set Data Pembayaran", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(PembayaranKasirActivity.this,
                                    "Response ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusPenjualanBarang> call, Throwable t) {
                        Toast.makeText(PembayaranKasirActivity.this,
                                "Koneksi error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadDataPembayaran() {

        ProgressDialog progressDialog = new ProgressDialog(PembayaranKasirActivity.this);
        progressDialog.setMessage("memuat data");
        progressDialog.show();

        ConfigRetrofit.service.dataBarangPenjualan(id_status_penjualan).enqueue(new Callback<ResponseDataBarangPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDataBarangPenjualan> call, Response<ResponseDataBarangPenjualan> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    int status = response.body().getStatus();
                    ArrayList<Integer> listTotal = new ArrayList<>();

                    listTotal.clear();

                    if (status == 1) {


                        dataBarang = response.body().getBarangPenjualan();
                        PembayaranAdapter adapter = new PembayaranAdapter(PembayaranKasirActivity.this,
                                dataBarang);

                        for (int i = 0; i < dataBarang.size(); i++) {
                            listTotal.add(Integer.parseInt(dataBarang.get(i).getTotal()));
                            tanggal2 = dataBarang.get(i).getTanggalPenjualan();
                            idBarangPenjualan.add(dataBarang.get(i).getIdBarang());
                            jumlahPackPenjualan.add(dataBarang.get(i).getJumlahPack());
                            jumlahPcsPenjualan.add(dataBarang.get(i).getJumlahBarang());
                            idOutletPenjualan.add(dataBarang.get(i).getIdOutlet());
                        }

                        total = 0;
                        for (int a = 0; a < listTotal.size(); a++) {

                            total += listTotal.get(a);

                        }

                        binding.rvListBarangPembayaran.setAdapter(adapter);
                        binding.textTotalBayarPembayaran.setText("Rp" + NumberFormat.getInstance().format(total));

                    } else {
                        Toast.makeText(PembayaranKasirActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(PembayaranKasirActivity.this, "Gagal Memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarangPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PembayaranKasirActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PembayaranKasirActivity.this, TransaksiKasirActivity.class);
        intent.putExtra("namaActivity", from_keto);
        startActivity(intent);
    }
}