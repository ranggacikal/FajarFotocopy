package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kasir.model.DataPenjualan;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.KeranjangSupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.DataUserGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.PembayaranKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.editJumlahPackOutlet.ResponseEditJumlahPackOutlet;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;
import com.mcdev.quantitizerlibrary.QuantitizerListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariBarangOutletAdapter extends RecyclerView.Adapter<CariBarangOutletAdapter.CariBarangOutletViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> cariBarangOutlet;
    TransaksiKasirActivity transaksiKasirActivity;

    Dialog dialog;


    private String[] levelItem = {"Pcs", "Pack"};

    public CariBarangOutletAdapter(Context context, List<SearchBarangOutletByNamaItem> cariBarangOutlet, TransaksiKasirActivity transaksiKasirActivity) {
        this.context = context;
        this.cariBarangOutlet = cariBarangOutlet;
        this.transaksiKasirActivity = transaksiKasirActivity;
    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    int total;
    int number, jumlahPcs, hasil_max, hasil_max_pcs;
    int jumlah_pack_penjualan = 0;
    @NonNull
    @NotNull
    @Override
    public CariBarangOutletViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariBarangOutletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariBarangOutletViewHolder holder, @SuppressLint("RecyclerView") int position) {


        String hargaPcsStr = cariBarangOutlet.get(position).getHargaJual();
        String hargaPackStr = cariBarangOutlet.get(position).getHargaJualPack();

        int hargaPcs, hargaPack;

        if (hargaPcsStr.equals("null")){
            hargaPcs = 0;
        }else{
            hargaPcs = Integer.parseInt(hargaPcsStr);
        }

        if (hargaPackStr.equals("null")){
            hargaPack = 0;
        }else{
            hargaPack = Integer.parseInt(hargaPackStr);
        }

        holder.txtNama.setText(cariBarangOutlet.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.lblJumlahPack.setVisibility(View.GONE);
        holder.btnTambahPesanan.setEnabled(false);

        holder.btnTambahPesanan.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_of_pack = cariBarangOutlet.get(position).getNumberOfPack();
                String stock = cariBarangOutlet.get(position).getStock();
                transaksiKasirActivity.loadDataPembayaran();
                Log.d("cekStatus", "onCheckedChanged: "+transaksiKasirActivity.status);
                int status = transaksiKasirActivity.status;

//                int kurangi_stock = Integer.parseInt(stock) - Integer.parseInt(number);
//                int jumlah_pack_sisa = kurangi_stock / Integer.parseInt(number_of_pack);

                String id_barang_outlet = cariBarangOutlet.get(position).getIdBarangOutlet();
                String id_status_penjualan = transaksiKasirActivity.id_status_penjualan;
                String id_barang = cariBarangOutlet.get(position).getIdBarang();
                String stockPack = cariBarangOutlet.get(position).getJumlahPack();
                String numberOfPack = cariBarangOutlet.get(position).getNumberOfPack();
                String hargaJual = cariBarangOutlet.get(position).getHargaJual();
                String hargaJualPack = cariBarangOutlet.get(position).getHargaJualPack();

                tampilDialogPilihanSatuan(id_barang_outlet, id_status_penjualan, id_barang, stock,
                        stockPack, numberOfPack, hargaJual, hargaJualPack, status);

//                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
//                        String.valueOf(jumlah_pack_sisa));
            }
        });

    }

    private void tampilDialogPilihanSatuan(String id_barang_outlet, String id_status_penjualan,
                                           String id_barang, String stock, String stockPack,
                                           String numberOfPack, String hargaJual, String hargaJualPack,
                                           int status) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_kasir);
        dialog.setCancelable(false);

        final RadioGroup rgPilihan = dialog.findViewById(R.id.rg_pilihan_satuan);
        final LinearLayout llQtyDialog = dialog.findViewById(R.id.ll_qty_dialog_kasir);
        final HorizontalQuantitizer numberPicker = dialog.findViewById(R.id.number_picker_dialog_kasir);
        final TextView edtTotalHarga = dialog.findViewById(R.id.edt_pcs_dialog_kasir);
        final TextView btnTambah = dialog.findViewById(R.id.text_tambah_barang_dialog_kasi);
        final TextView lblTextKosong = dialog.findViewById(R.id.lbl_stock_kosong_dialog_kasir);
        final TextView btnCancel = dialog.findViewById(R.id.text_cancel_dialog_kasir);

        dialog.show();

        rgPilihan.clearCheck();

        rgPilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                final RadioButton rb_satuan = dialog.findViewById(checkedId);

                if (rb_satuan.getText().equals("Pcs")) {
                    if (Integer.parseInt(stock) == 0 || Integer.parseInt(stock) < 0) {
                        llQtyDialog.setVisibility(View.VISIBLE);
                        numberPicker.setVisibility(View.GONE);
                        edtTotalHarga.setVisibility(View.GONE);
                        lblTextKosong.setVisibility(View.VISIBLE);
                        Log.d("cekIfPosition", "onItemSelected: TRUE");
                    } else {
                        llQtyDialog.setVisibility(View.VISIBLE);
                        lblTextKosong.setVisibility(View.GONE);
                        numberPicker.setVisibility(View.VISIBLE);
                        edtTotalHarga.setVisibility(View.VISIBLE);
                        numberPicker.setValue(0);
                        edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(0));

                        String id_barang_penjualan = "";
                        int jumlah_pcs_penjualan = 0;
                        List<String> dataIdBarang = new ArrayList<>();
                        List<String> dataJumlahBarang = new ArrayList<>();
                        DataPenjualan[] arrayIdBarang;
                        Boolean isOutOfStockPcs = false;
                        int stockPackDb = Integer.parseInt(stockPack);
                        int stockPackSisa = 0;
                        jumlah_pack_penjualan = 0;

                        if (transaksiKasirActivity.barangPenjualan != null) {

                            arrayIdBarang = new DataPenjualan[transaksiKasirActivity.barangPenjualan.size()];

                            for (int a = 0; a < transaksiKasirActivity.barangPenjualan.size(); a++) {
                                id_barang_penjualan = transaksiKasirActivity.barangPenjualan.get(a)
                                        .getIdBarangOutlet();

                                if (id_barang_outlet.equals(id_barang_penjualan)) {
                                    jumlah_pcs_penjualan += Integer.parseInt(
                                            transaksiKasirActivity.barangPenjualan.get(a)
                                                    .getJumlahBarang());
                                    jumlah_pack_penjualan += Integer.parseInt(
                                            transaksiKasirActivity.barangPenjualan.get(a)
                                            .getJumlahPack()
                                    );
                                    if (jumlah_pcs_penjualan == Integer.parseInt(stock)) {
                                        isOutOfStockPcs = true;
                                    }
                                }
                            }
                            Log.d("cekStockPackSisaTest", "onCheckedChanged: "+stockPackSisa);
                            hasil_max_pcs = Integer.parseInt(stock) - jumlah_pcs_penjualan;

                        }


                        if (isOutOfStockPcs) {
                            numberPicker.setVisibility(View.GONE);
                            lblTextKosong.setVisibility(View.VISIBLE);
                            edtTotalHarga.setVisibility(View.GONE);
                            btnTambah.setVisibility(View.GONE);
                        }

                        Log.d("cekIfPosition", "onItemSelected: FALSE");
                        setQTy("Pcs", numberPicker, edtTotalHarga, btnTambah, id_barang_outlet,
                                id_status_penjualan, id_barang, stock, stockPack,
                                numberOfPack, hargaJual);
                    }
                } else {
                    Log.d("cekStockPack", "onCheckedChanged: " + stockPack);
                    if (Integer.parseInt(stockPack) == 0 || Integer.parseInt(stockPack) < 0) {
                        llQtyDialog.setVisibility(View.VISIBLE);
                        numberPicker.setVisibility(View.GONE);
                        edtTotalHarga.setVisibility(View.GONE);
                        lblTextKosong.setVisibility(View.VISIBLE);

                    } else {
                        llQtyDialog.setVisibility(View.VISIBLE);
                        numberPicker.setVisibility(View.VISIBLE);
                        edtTotalHarga.setVisibility(View.VISIBLE);
                        numberPicker.setValue(0);
                        edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(0));
                        lblTextKosong.setVisibility(View.GONE);

                        String id_barang_penjualan = "";
                        int jumlah_pack_penjualan = 0;
                        List<String> dataIdBarang = new ArrayList<>();
                        List<String> dataJumlahBarang = new ArrayList<>();
                        DataPenjualan[] arrayIdBarang;
                        Boolean isOutOfStock = false;

                        if (transaksiKasirActivity.barangPenjualan != null) {

                            arrayIdBarang = new DataPenjualan[transaksiKasirActivity.barangPenjualan.size()];

                            for (int a = 0; a < transaksiKasirActivity.barangPenjualan.size(); a++) {
                                id_barang_penjualan = transaksiKasirActivity.barangPenjualan.get(a)
                                        .getIdBarangOutlet();

                                if (id_barang_outlet.equals(id_barang_penjualan)) {
                                    jumlah_pack_penjualan += Integer.parseInt(
                                            transaksiKasirActivity.barangPenjualan.get(a)
                                            .getJumlahBarang());
                                    int jumlah_pcs_data = Integer.parseInt(stockPack) *
                                            Integer.parseInt(numberOfPack);
                                    if (jumlah_pack_penjualan == jumlah_pcs_data) {
                                        isOutOfStock = true;
                                    }
                                }
                            }

                            int hasil_bagi_pack = jumlah_pack_penjualan /
                                    Integer.parseInt(numberOfPack);
                            Log.d("cekSum", "stockPack: "+stockPack);
                            Log.d("cekSum", "hasilBagi: "+hasil_bagi_pack);
                            hasil_max = Integer.parseInt(stockPack) - hasil_bagi_pack;
                            Log.d("cekSum", "onCheckedChanged: "+jumlah_pack_penjualan);
                            Log.d("cekSum", "hasil_max: "+hasil_max);

                        }

                        Log.d("cekDataForPembayaran", "barang: " + id_barang_penjualan);
                        Log.d("cekDataForPembayaran", "barang: " + jumlah_pack_penjualan);
                        if (dataIdBarang != null) {
                            Log.d("cekDataForPembayaran", "listId: " + dataIdBarang.toString());
                        }


                        if (isOutOfStock) {
                            numberPicker.setVisibility(View.GONE);
                            lblTextKosong.setVisibility(View.VISIBLE);
                            edtTotalHarga.setVisibility(View.GONE);
                            btnTambah.setVisibility(View.GONE);
                        }

                        setQTyPack("Pack", numberPicker, edtTotalHarga, btnTambah, id_barang_outlet,
                                id_status_penjualan, id_barang, stock, stockPack, numberOfPack, hargaJualPack);
                    }
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setQTyPack(String jenis_satuan, HorizontalQuantitizer numberPicker, TextView edtTotalHarga,
                            TextView btnTambah, String id_barang_outlet, String id_status_penjualan,
                            String id_barang, String stock, String stockPack, String numberOfPack,
                            String hargaJualPack) {

        numberPicker.setQuantitizerListener(new QuantitizerListener() {
            @Override
            public void onIncrease() {
                if (transaksiKasirActivity.barangPenjualan != null){
                    numberPicker.setMaxValue(hasil_max);
                }else {
                    numberPicker.setMaxValue(Integer.parseInt(stockPack));
                }
                jumlahPcs = numberPicker.getValue();

                if (jumlahPcs == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                } else if (jumlahPcs > numberPicker.getMaxValue()) {
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(jumlahPcs);
                    btnTambah.setEnabled(false);
                } else {
                    if (hargaJualPack.equals("null")){
                        Toast.makeText(context, "Harga Pack 0, Silahkan lakukan penjualan pcs",
                                Toast.LENGTH_SHORT).show();
                        numberPicker.setValue(0);
                    }else {
                        total = jumlahPcs * Integer.parseInt(hargaJualPack);
                        number = jumlahPcs * Integer.parseInt(numberOfPack);
                        edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                        btnTambah.setEnabled(true);
                    }
                }
            }

            @Override
            public void onDecrease() {

                jumlahPcs = numberPicker.getValue();

                if (jumlahPcs == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    total = jumlahPcs * Integer.parseInt(hargaJualPack);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(false);
                    numberPicker.setValue(0);
                } else {
                    total = jumlahPcs * Integer.parseInt(hargaJualPack);
                    number = jumlahPcs * Integer.parseInt(numberOfPack);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(true);
                }

            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (jumlahPcs == 0) {
                    Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int kurangi_stock = Integer.parseInt(stock) - number;
                int jumlah_pack_sisa = jumlahPcs;

                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
                        String.valueOf(jumlah_pack_sisa), jenis_satuan);
            }
        });

    }

    private void setQTy(String jenis_satuan, HorizontalQuantitizer numberPicker, TextView edtTotalHarga,
                        TextView btnTambah, String id_barang_outlet, String id_status_penjualan,
                        String id_barang, String stock, String stockPack, String numberOfPack,
                        String hargaJual) {

        numberPicker.setQuantitizerListener(new QuantitizerListener() {
            @Override
            public void onIncrease() {
                if (transaksiKasirActivity.barangPenjualan != null){
                    numberPicker.setMaxValue(hasil_max_pcs);
                }else {
                    numberPicker.setMaxValue(Integer.parseInt(stock));
                }
                number = numberPicker.getValue();

                if (number == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                } else if (number > numberPicker.getMaxValue()) {
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(number);
                    btnTambah.setEnabled(false);
                } else {
                    total = number * Integer.parseInt(hargaJual);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(true);
                }
            }

            @Override
            public void onDecrease() {

                number = numberPicker.getValue();

                if (number == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    total = number * Integer.parseInt(hargaJual);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(false);
                    numberPicker.setValue(0);
                } else {
                    total = number * Integer.parseInt(hargaJual);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(true);
                }

            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number == 0) {
                    Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int kurangi_stock = Integer.parseInt(stock) - number;
                int jumlah_pack_sisa = 0;
                int jumlah_hasil_bagi = kurangi_stock / Integer.parseInt(numberOfPack);
                Log.d("cekJumlahPackSisa", "jumlahHasilBagi: "+jumlah_hasil_bagi);

                if (Integer.parseInt(stockPack) == jumlah_hasil_bagi){
                    jumlah_pack_sisa = 0;
                    
                }else{
                    int sisa_stock = Integer.parseInt(stockPack) - jumlah_hasil_bagi;
                    jumlah_pack_sisa = sisa_stock;
                }

                Log.d("cekJumlahPackSisa", "jumlahPackSisa: "+jumlah_pack_sisa);
                Log.d("cekJumlahPackSisa", "stockPack: "+stockPack);

//                if (kurangi_stock % Integer.parseInt(numberOfPack) == 0) {
////                    jumlah_hasil_bagi = kurangi_stock / Integer.parseInt(numberOfPack);
//                    jumlah_pack_sisa = number / Integer.parseInt(numberOfPack);
//                }else{
//                    jumlah_pack_sisa = 0;
//                }

                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
                        String.valueOf(jumlah_pack_sisa), jenis_satuan);


            }
        });

    }

    private void tambahPenjualan(String id_barang_outlet, String id_status_penjualan,
                                 String id_barang, String jumlah_pack_sisa, String jenis_satuan) {

        preferencedConfig = new SharedPreferencedConfig(context);
        transaksiKasirActivity.dataBarangoutlet.add(id_barang_outlet);
        for (int a = 0; a < transaksiKasirActivity.dataBarangoutlet.size(); a++) {
            Log.d("cekForData", "tambahPenjualan: " + transaksiKasirActivity.dataBarangoutlet.toString());
        }


        Random rnd = new Random();
        int numberRnd = rnd.nextInt(999999);

        String randomId = String.format("%06d", numberRnd);
        String id_penjualan = "FEG" + randomId;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        String tanggal = date;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPenjualan(id_penjualan, id_barang_outlet, id_barang,
                String.valueOf(number), jumlah_pack_sisa, String.valueOf(total), tanggal,
                preferencedConfig.getPreferenceNama(), id_status_penjualan, jenis_satuan, "pending")
                .enqueue(new Callback<ResponseTambahPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPenjualan> call, Response<ResponseTambahPenjualan> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil menambah barang", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                transaksiKasirActivity.loadDataPembayaran();
                                transaksiKasirActivity.loadSearchBarangKasir(transaksiKasirActivity.cariBarang);
//                                editJumlahPack(jumlah_pack_sisa, id_barang_outlet);
                            } else {
                                Toast.makeText(context, "Gagal Menambah barang", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Log.d("paramPenjualan", "idPenjualan: " + id_penjualan);
                            Log.d("paramPenjualan", "idBarangOutlet: " + id_barang_outlet);
                            Log.d("paramPenjualan", "number: " + number);
                            Log.d("paramPenjualan", "total: " + total);
                            Log.d("paramPenjualan", "tanggal: " + tanggal);
                            Log.d("paramPenjualan", "nama: " + preferencedConfig.getPreferenceNama());
                            Log.d("paramPenjualan", "idStatusPenjualan: " + id_status_penjualan);
                            Log.d("paramPenjualan", "ERROR: " + response.message());
                            Toast.makeText(context, "Gagal Saat menambahkan barang", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPenjualan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void editJumlahPack(String jumlah_pack_sisa, String id_barang_outlet) {


        ConfigRetrofit.service.editPackOutlet(id_barang_outlet, jumlah_pack_sisa)
                .enqueue(new Callback<ResponseEditJumlahPackOutlet>() {
                    @Override
                    public void onResponse(Call<ResponseEditJumlahPackOutlet> call, Response<ResponseEditJumlahPackOutlet> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();

                            if (status == 1) {

                                Toast.makeText(context, "Berhasil Edit Pack", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Gagal Edit Pack", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Terjadi Kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditJumlahPackOutlet> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return cariBarangOutlet.size();
    }

    public class CariBarangOutletViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack, lblJumlahPack;
        Button btnTambahPesanan;

        public CariBarangOutletViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            lblJumlahPack = itemView.findViewById(R.id.lbl_jumlah_pack);
        }
    }

    private void scaleImage(ImageView view) {
        Drawable drawing = view.getDrawable();
        if (drawing == null) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int xBounding = ((View) view.getParent()).getWidth();//EXPECTED WIDTH
        int yBounding = ((View) view.getParent()).getHeight();//EXPECTED HEIGHT

        float xScale = ((float) xBounding) / width;
        float yScale = ((float) yBounding) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(xScale, yScale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();
        BitmapDrawable result = new BitmapDrawable(context.getResources(), scaledBitmap);

        view.setImageDrawable(result);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
