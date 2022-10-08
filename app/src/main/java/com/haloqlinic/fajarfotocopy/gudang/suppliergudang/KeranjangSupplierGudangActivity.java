package com.haloqlinic.fajarfotocopy.gudang.suppliergudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.dialog.DialogValidateAdapter;
import com.haloqlinic.fajarfotocopy.adapter.dialog.DialogValidateGudangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.KeranjangSupplierAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCetakBuktiKasirBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKeranjangSupplierGudangBinding;
import com.haloqlinic.fajarfotocopy.formatNumber.NumberTextWatcher;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.PembayaranKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiBerhasilActivity;
import com.haloqlinic.fajarfotocopy.model.dataBarangOutletList.DataBarangOutletListItem;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanGudang.ResponseEditStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.BarangPenjualanItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.BarangPenjualanGudangItem;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.ResponseBarangPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.getDriver.DataDriverItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.ResponseDataDriver;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanGudang.ResponseHapusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.listBarangGudangValidate.DataBarangGudangListItem;
import com.haloqlinic.fajarfotocopy.model.listBarangGudangValidate.ResponseListBarangGudangValidate;
import com.haloqlinic.fajarfotocopy.model.reportPenjualanGudang.ResponseReportPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.sumPenjualanGudang.ResponseSumPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualanBarangGudang.ResponseUpdateStatusPenjualanBarangGudang;
import com.haloqlinic.fajarfotocopy.model.validateBarangListGudang.DataValidateBarangGudangItem;
import com.haloqlinic.fajarfotocopy.model.validateBarangListGudang.ResponseValidateBarangGudang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    List<DataDriverItem> dataDriverItems;

    String metode_bayar, metode_pengambilan, id_driver;

    private String[] metodeBayarItem = {"Cash", "Debit"};
    private String[] jenisBayarItem = {"Lunas", "Tempo"};
    private String[] metodePengambilanItem = {"Ambil Ditempat", "Dikirim Kurir"};

    SharedPreferencedConfig preferencedConfig;

    ArrayList<String> idBarangPenjualan = new ArrayList<>();
    ArrayList<String> jumlahPackPenjualan = new ArrayList<>();
    ArrayList<String> jumlahPcsPenjualan = new ArrayList<>();
    ArrayList<String> idOutletPenjualan = new ArrayList<>();
    ArrayList<String> namaOutlet = new ArrayList<>();
    ArrayList<String> namaBarang = new ArrayList<>();
    ArrayList<String> hargaPcsPenjualan = new ArrayList<>();
    ArrayList<String> hargaPackPenjualan = new ArrayList<>();
    ArrayList<String> totalPenjualan = new ArrayList<>();
    ArrayList<String> jenisSatuan = new ArrayList<>();
    ArrayList<String> tanggalPenjualan = new ArrayList<>();
    ArrayList<String> namaKasirPenjualan = new ArrayList<>();
    ArrayList<String> idStatusPenjualan = new ArrayList<>();
    ArrayList<String> statusPenjualan = new ArrayList<>();
    ArrayList<String> idKasir = new ArrayList<>();
    ArrayList<String> jumlahKurangList = new ArrayList<>();
    ArrayList<String> metodeBayar = new ArrayList<>();
    ArrayList<String> alamatTujuan = new ArrayList<>();
    ArrayList<String> statusPengiriman = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeranjangSupplierGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_status_penjualan_gudang = getIntent().getStringExtra("id_status_penjualan_gudang");
        preferencedConfig = new SharedPreferencedConfig(this);

        binding.rvKeranjangSupplier.setHasFixedSize(true);
        binding.rvKeranjangSupplier.setLayoutManager(new LinearLayoutManager(KeranjangSupplierGudangActivity.this));

        binding.edtJumlahBayarSupplier.addTextChangedListener(new NumberTextWatcher(binding.edtJumlahBayarSupplier));

        loadDataKeranjang();

        ArrayAdapter<String> adapterMetode = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                R.layout.spinner_item, metodeBayarItem);
        adapterMetode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMetodeBayarSupplier.setAdapter(adapterMetode);

        ArrayAdapter<String> adapterJenis = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                R.layout.spinner_item, jenisBayarItem);
        adapterMetode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerJenisBayarSupplier.setAdapter(adapterJenis);

        ArrayAdapter<String> adapterPengambilan = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                R.layout.spinner_item, metodePengambilanItem);
        adapterPengambilan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMetodeKirimSupplier.setAdapter(adapterPengambilan);

        PushDownAnim.setPushDownAnimTo(binding.linearBackKeranjangSupplierGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.spinnerMetodeKirimSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metode_pengambilan = binding.spinnerMetodeKirimSupplier.getSelectedItem().toString();

                if (metode_pengambilan.equals("Dikirim Kurir")) {
                    binding.linearLayoutKurir.setVisibility(View.VISIBLE);
                    initSpinnerPilihDriver();
                } else {
                    binding.linearLayoutKurir.setVisibility(View.GONE);
                    id_driver = preferencedConfig.getPreferenceIdUser();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                    binding.relativeKembalianSupplier.setVisibility(View.GONE);

                } else {
                    binding.relativeTotalKurang.setVisibility(View.GONE);
                    binding.relativeKembalianSupplier.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerDriverSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_driver = dataDriverItems.get(i).getIdUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        itemTouchHelper.attachToRecyclerView(binding.rvKeranjangSupplier);

        PushDownAnim.setPushDownAnimTo(binding.btnHitungKeranjangSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jumlahBayarStr = binding.edtJumlahBayarSupplier.getText().toString();
                        String jumlahReplace = jumlahBayarStr.replace(".", "")
                                .replace(",", "");
                        if (jumlahBayarStr.isEmpty()) {
                            binding.edtJumlahBayarSupplier.setError("Jumlah bayar tidak boleh kosong");
                            return;
                        }
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
                        ProgressDialog progressDialog = new ProgressDialog(KeranjangSupplierGudangActivity.this);
                        progressDialog.setMessage("memproses pembayaran");
                        progressDialog.show();
                        validateDataBarang(progressDialog);
                    }
                });

    }

    private void validateDataBarang(ProgressDialog progressDialog) {

        ConfigRetrofit.service
                .validateBarangListGudang(idBarangPenjualan, jumlahPcsPenjualan, jumlahPackPenjualan)
                .enqueue(new Callback<ResponseValidateBarangGudang>() {
                    @Override
                    public void onResponse(Call<ResponseValidateBarangGudang> call, Response<ResponseValidateBarangGudang> response) {
                        if (response.isSuccessful()) {
                            int status = response.body().getStatus();
                            if (status == 1) {
                                bayar(progressDialog);
                            } else {
                                progressDialog.dismiss();
                                List<DataValidateBarangGudangItem> dataValidateBarangGudang =
                                        response.body().getDataValidateBarangGudang();
                                showDialogValidateBarang(dataValidateBarangGudang);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseValidateBarangGudang> call, Throwable t) {

                    }
                });
    }

    private void showDialogValidateBarang(List<DataValidateBarangGudangItem> dataValidateBarangGudang) {
        Dialog dialogValidate = new Dialog(KeranjangSupplierGudangActivity.this);
        dialogValidate.setContentView(R.layout.dialog_validate_barang);
        dialogValidate.setCancelable(false);
        dialogValidate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogValidate.show();
        Button btnClose = dialogValidate.findViewById(R.id.btn_close_dialog_validate_barang);
        loadDataValidateBarangGudang(dialogValidate, dataValidateBarangGudang);
        PushDownAnim.setPushDownAnimTo(btnClose)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogValidate.dismiss();
                    }
                });
    }

    private void loadDataValidateBarangGudang(Dialog dialogValidate,
                                              List<DataValidateBarangGudangItem> dataValidateBarangGudang) {
        ProgressDialog progressDialogValidate = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        progressDialogValidate.setMessage("Memuat Data");
        progressDialogValidate.show();

        ArrayList<String> idBarangList = new ArrayList<>();

        for (int a = 0; a < dataValidateBarangGudang.size(); a++) {
            idBarangList.add(dataValidateBarangGudang.get(a).getIdBarang());
        }

        ConfigRetrofit.service.listBarangGudangValidate(idBarangList)
                .enqueue(new Callback<ResponseListBarangGudangValidate>() {
                    @Override
                    public void onResponse(Call<ResponseListBarangGudangValidate> call, Response<ResponseListBarangGudangValidate> response) {
                        if (response.isSuccessful()) {
                            progressDialogValidate.dismiss();
                            boolean isSukses = response.body().isSukses();
                            if (isSukses) {
                                RecyclerView rvValidateDialog = dialogValidate.findViewById(R.id.rv_dialog_validate_barang);
                                rvValidateDialog.setHasFixedSize(true);
                                rvValidateDialog.setLayoutManager(new LinearLayoutManager(KeranjangSupplierGudangActivity.this));
                                List<DataBarangGudangListItem> dataBarang = response.body().getDataBarangGudangList();
                                DialogValidateGudangAdapter adapter = new DialogValidateGudangAdapter(KeranjangSupplierGudangActivity.this, dataBarang);
                                rvValidateDialog.setAdapter(adapter);
                            } else {
                                Toast.makeText(KeranjangSupplierGudangActivity.this,
                                        "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialogValidate.dismiss();
                            Toast.makeText(KeranjangSupplierGudangActivity.this,
                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListBarangGudangValidate> call, Throwable t) {
                        progressDialogValidate.dismiss();
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void initSpinnerPilihDriver() {

        ProgressDialog progressDialog = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        progressDialog.setMessage("Memuat Data Driver");
        progressDialog.show();

        ConfigRetrofit.service.getDriver().enqueue(new Callback<ResponseDataDriver>() {
            @Override
            public void onResponse(Call<ResponseDataDriver> call, Response<ResponseDataDriver> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataDriverItems = response.body().getDataDriver();
                        List<String> listSpinnerDriver = new ArrayList<String>();
                        for (int i = 0; i < dataDriverItems.size(); i++) {

                            listSpinnerDriver.add(dataDriverItems.get(i).getNamaLengkap());

                        }

                        ArrayAdapter<String> adapterDriver = new ArrayAdapter<String>(KeranjangSupplierGudangActivity.this,
                                R.layout.spinner_item, listSpinnerDriver);

                        adapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerDriverSupplier.setAdapter(adapterDriver);

                    } else {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Terjadi Kesalahan Di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataDriver> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Koneksi Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void bayar(ProgressDialog progressDialog) {

        String alamat = "";

        if (metode_pengambilan.equals("Dikirim Kurir")) {
            alamat = binding.edtAlamatSupplier.getText().toString();

            if (alamat.isEmpty()) {

                binding.edtAlamatSupplier.setError("Alamat Tujuan tidak boleh kosong");
                binding.edtAlamatSupplier.requestFocus();
                return;

            }

        }

        String image;

        if (metode_bayar.equals("debit") || metode_bayar.equals("Debit")) {
            image = imageToString();
        } else {
            image = "";
        }

        ConfigRetrofit.service.editStatusPenjualanGudang(id_status_penjualan_gudang, "Selesai",
                metode_bayar, String.valueOf(total), String.valueOf(jumlahBayar), String.valueOf(jumlahKurang),
                image, metode_pengambilan, id_driver, alamat, "pending")
                .enqueue(new Callback<ResponseEditStatusPenjualanGudang>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusPenjualanGudang> call, Response<ResponseEditStatusPenjualanGudang> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {
                                updatePenjualanBarangGudang(id_status_penjualan_gudang);

                            } else {
                                Toast.makeText(KeranjangSupplierGudangActivity.this,
                                        "Gagal, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                            }

                        } else {
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

    private void updatePenjualanBarangGudang(String id_status_penjualan_gudang) {
        ConfigRetrofit.service
                .updateStatusBarangGudang(id_status_penjualan_gudang, "Selesai")
                .enqueue(new Callback<ResponseUpdateStatusPenjualanBarangGudang>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStatusPenjualanBarangGudang> call, Response<ResponseUpdateStatusPenjualanBarangGudang> response) {
                        if (response.isSuccessful()) {
                            int status = response.body().getStatus();
                            if (status == 1) {
                                insertReportGudang();
                            } else {
                                Toast.makeText(KeranjangSupplierGudangActivity.this,
                                        "Gagal update data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(KeranjangSupplierGudangActivity.this,
                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStatusPenjualanBarangGudang> call, Throwable t) {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertReportGudang() {
        ProgressDialog pdReport = new ProgressDialog(KeranjangSupplierGudangActivity.this);
        pdReport.setMessage("Menambahkan Data Report");
        pdReport.show();
        ConfigRetrofit.service.insertReportPenjualanGudang(namaBarang, jumlahPcsPenjualan,
                jumlahPackPenjualan, hargaPcsPenjualan, jumlahKurangList, totalPenjualan,
                tanggalPenjualan, metodeBayar, namaKasirPenjualan, alamatTujuan, idKasir, statusPengiriman,
                idStatusPenjualan, statusPenjualan).enqueue(new Callback<ResponseReportPenjualanGudang>() {
            @Override
            public void onResponse(Call<ResponseReportPenjualanGudang> call, Response<ResponseReportPenjualanGudang> response) {
                if (response.isSuccessful()) {
                    pdReport.dismiss();
                    boolean isSukses = response.body().isSukses();
                    if (isSukses) {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Berhasil Menambahkan Report", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KeranjangSupplierGudangActivity.this, TransaksiBerhasilGudangActivity.class);
                        intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(KeranjangSupplierGudangActivity.this,
                                "Gagal Menambahkan report", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pdReport.dismiss();
                    Toast.makeText(KeranjangSupplierGudangActivity.this,
                            "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseReportPenjualanGudang> call, Throwable t) {
                pdReport.dismiss();
                Toast.makeText(KeranjangSupplierGudangActivity.this,
                        "Periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
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
                        for (int i = 0; i < dataBarang.size(); i++) {
                            idBarangPenjualan.add(dataBarang.get(i).getIdBarang());
                            jumlahPackPenjualan.add(dataBarang.get(i).getJumlahPack());
                            jumlahPcsPenjualan.add(dataBarang.get(i).getJumlahBarang());
                            namaOutlet.add(preferencedConfig.getPreferenceNamaToko());
                            namaBarang.add(dataBarang.get(i).getNamaBarang());
                            hargaPcsPenjualan.add(dataBarang.get(i).getHargaModalToko());
                            totalPenjualan.add(dataBarang.get(i).getTotal());
                            idStatusPenjualan.add(dataBarang.get(i).getIdStatusPenjualanGudang());
                            statusPenjualan.add(dataBarang.get(i).getStatusPenjualan());
                            tanggalPenjualan.add(dataBarang.get(i).getTanggalPenjualan());
                            jumlahKurangList.add(dataBarang.get(i).getJumlahKurang());
                            metodeBayar.add(binding.spinnerMetodeBayarSupplier.getSelectedItem().toString());
                            namaKasirPenjualan.add(preferencedConfig.getPreferenceNama());
                            if (dataBarang.get(i).getDriverId() == null) {
                                idKasir.add(preferencedConfig.getPreferenceIdUser());
                            } else {
                                idKasir.add(dataBarang.get(i).getDriverId());
                            }
                            if (dataBarang.get(i).getAlamatTujuan() == null) {
                                alamatTujuan.add("Ambil Ditempat");
                            } else {
                                alamatTujuan.add(dataBarang.get(i).getAlamatTujuan());
                            }
                            if (dataBarang.get(i).getStatusPengiriman() == null) {
                                statusPengiriman.add("Ambil Ditempat");
                            } else {
                                statusPengiriman.add(dataBarang.get(i).getStatusPengiriman());
                            }
                        }
                        adapter = new KeranjangSupplierAdapter(KeranjangSupplierGudangActivity.this, dataBarang);
                        binding.rvKeranjangSupplier.setAdapter(adapter);
                        loadSumTotal();

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