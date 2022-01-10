package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.editJumlahPackOutlet.ResponseEditJumlahPackOutlet;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;
import com.mcdev.quantitizerlibrary.QuantitizerListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class BarangOutletIdAdapter extends RecyclerView.Adapter<BarangOutletIdAdapter.BarangOutletIdViewHolder> {

    Context context;
    List<SearchBarangOutletByIdItem> dataCari;
    TransaksiKasirActivity transaksiKasirActivity;
    Dialog dialog;

    public BarangOutletIdAdapter(Context context, List<SearchBarangOutletByIdItem> dataCari, TransaksiKasirActivity transaksiKasirActivity) {
        this.context = context;
        this.dataCari = dataCari;
        this.transaksiKasirActivity = transaksiKasirActivity;
    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    int total, jumlahPcs;
    int number;

    @NonNull
    @NotNull
    @Override
    public BarangOutletIdViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new BarangOutletIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BarangOutletIdViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        int hargaPcs = Integer.parseInt(dataCari.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(dataCari.get(position).getHargaJualPack());

        holder.txtNama.setText(dataCari.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.lblJumlahPack.setVisibility(View.GONE);

        holder.btnTambahPesanan.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_of_pack = dataCari.get(position).getNumberOfPack();
                String stock = dataCari.get(position).getStock();

//                int kurangi_stock = Integer.parseInt(stock) - Integer.parseInt(number);
//                int jumlah_pack_sisa = kurangi_stock / Integer.parseInt(number_of_pack);

                String id_barang_outlet = dataCari.get(position).getIdBarangOutlet();
                String id_status_penjualan = transaksiKasirActivity.id_status_penjualan;
                String id_barang = dataCari.get(position).getIdBarang();
                String stockPack = dataCari.get(position).getJumlahPack();
                String numberOfPack = dataCari.get(position).getNumberOfPack();
                String hargaJual = dataCari.get(position).getHargaJual();
                String hargaJualPack = dataCari.get(position).getHargaJualPack();

                tampilDialogPilihanSatuan(id_barang_outlet, id_status_penjualan, id_barang, stock,
                        stockPack, numberOfPack, hargaJual, hargaJualPack);

//                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
//                        String.valueOf(jumlah_pack_sisa));
            }
        });
    }

    private void tampilDialogPilihanSatuan(String id_barang_outlet, String id_status_penjualan,
                                           String id_barang, String stock, String stockPack,
                                           String numberOfPack, String hargaJual, String hargaJualPack) {

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
                        Log.d("cekIfPosition", "onItemSelected: FALSE");
                        setQTy("Pcs", numberPicker, edtTotalHarga, btnTambah, id_barang_outlet,
                                id_status_penjualan, id_barang, stock, stockPack, numberOfPack, hargaJual);
                    }
                } else {
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
                numberPicker.setMaxValue(Integer.parseInt(stockPack));
                jumlahPcs = numberPicker.getValue();

                if (jumlahPcs == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                } else if (jumlahPcs > numberPicker.getMaxValue()) {
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(jumlahPcs);
                    btnTambah.setEnabled(false);
                } else {
                    total = jumlahPcs * Integer.parseInt(hargaJualPack);
                    number = jumlahPcs * Integer.parseInt(numberOfPack);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(true);
                }
            }

            @Override
            public void onDecrease() {

                number = numberPicker.getValue();

                if (number == 0) {
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    total = number * Integer.parseInt(hargaJualPack);
                    edtTotalHarga.setText("Rp" + NumberFormat.getInstance().format(total));
                    btnTambah.setEnabled(false);
                    numberPicker.setValue(0);
                } else {
                    total = number * Integer.parseInt(hargaJualPack);
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
                if (number == 0) {
                    Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

//                int kurangi_stock = Integer.parseInt(stock) - number;
                int jumlah_pack_sisa = jumlahPcs;

                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
                        String.valueOf(jumlah_pack_sisa), jenis_satuan);
            }
        });

    }

    private void setQTy(String jenis_satuan, HorizontalQuantitizer numberPicker, TextView edtTotalHarga,
                        TextView btnTambah, String id_barang_outlet, String id_status_penjualan,
                        String id_barang, String stock, String stockPack, String numberOfPack, String hargaJual) {

        numberPicker.setQuantitizerListener(new QuantitizerListener() {
            @Override
            public void onIncrease() {
                numberPicker.setMaxValue(Integer.parseInt(stock));
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
//
                if (number == 0) {
                    Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int kurangi_stock = Integer.parseInt(stock) - number;
                int jumlah_pack_sisa;

                if (kurangi_stock % Integer.parseInt(numberOfPack) == 0) {
                    jumlah_pack_sisa = kurangi_stock / Integer.parseInt(numberOfPack);
                }else{
                    jumlah_pack_sisa = 0;
                }

                tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
                        String.valueOf(jumlah_pack_sisa), jenis_satuan);
            }
        });

    }

    private void tambahPenjualan(String id_barang_outlet, String id_status_penjualan,
                                 String id_barang, String jumlah_pack_sisa, String jenis_satuan) {

        preferencedConfig = new SharedPreferencedConfig(context);
        transaksiKasirActivity.dataBarangoutlet.add(id_barang_outlet);
        for (int a = 0; a<transaksiKasirActivity.dataBarangoutlet.size(); a++){
            Log.d("cekForData", "tambahPenjualan: "+transaksiKasirActivity.dataBarangoutlet.toString());
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

    private void editJumlahPack(String jumlah_pack, String id_barang_outlet) {

        ConfigRetrofit.service.editPackOutlet(id_barang_outlet, jumlah_pack)
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
        return dataCari.size();
    }

    public class BarangOutletIdViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack, lblJumlahPack;
        Button btnTambahPesanan;

        public BarangOutletIdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            lblJumlahPack = itemView.findViewById(R.id.lbl_jumlah_pack);
        }
    }
}
