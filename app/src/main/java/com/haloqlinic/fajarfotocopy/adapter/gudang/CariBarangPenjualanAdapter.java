package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.editPackBarang.ResponseEditPackBarang;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.mcdev.quantitizerlibrary.AnimationStyle;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;
import com.mcdev.quantitizerlibrary.QuantitizerListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariBarangPenjualanAdapter extends RecyclerView.Adapter<CariBarangPenjualanAdapter.CariBarangPenjualanViewHolder> {

    Context context;
    List<SearchBarangByNamaItem> dataBarang;
    SupplierGudangActivity supplierGudangActivity;

    String number, value;
    int total, jumlah_qty, edit_pack, count = 0;
    Dialog dialog, dialogDataKosong;

    public CariBarangPenjualanAdapter(Context context, List<SearchBarangByNamaItem> dataBarang, SupplierGudangActivity supplierGudangActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.supplierGudangActivity = supplierGudangActivity;
    }

    @NonNull
    @Override
    public CariBarangPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariBarangPenjualanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CariBarangPenjualanViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int hargaPcs = Integer.parseInt(dataBarang.get(position).getHargaModalToko());
        int hargaPack = Integer.parseInt(dataBarang.get(position).getHargaModalTokoPack());

        String image = dataBarang.get(position).getImageBarang();


        Glide.with(context)
                .load(image)
                .error(R.drawable.ic_gift)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataBarang.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.btnTambahPesanan.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockFromDb = dataBarang.get(position).getStock();
                Log.d("cekStockDb", "onClick: "+stockFromDb);
                if (Integer.parseInt(stockFromDb) == 0 || Integer.parseInt(stockFromDb) < 0){
                    tampilDialogDataKosong();
                }else {
                    int number_of_pack = Integer.parseInt(dataBarang.get(position).getNumberOfPack());
                    int stock_db = Integer.parseInt(dataBarang.get(position).getStock());
                    int hargaModalToko = Integer.parseInt(dataBarang.get(position).getHargaModalToko());
                    String id_status_penjualan = supplierGudangActivity.id_status_penjualan_gudang;
                    String id_barang = dataBarang.get(position).getIdBarang();
                    tampilDialog(number_of_pack, stock_db, hargaModalToko, id_status_penjualan, id_barang);
                }
            }
        });

//        PushDownAnim.setPushDownAnimTo(holder.btnTambahPesanan)
//                .setScale(MODE_SCALE, 0.89f)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (number != null) {
//
//                            if (number.equals("0")) {
//                                Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                        }
//                        String jumlah_pack = holder.edtJumlahPack.getText().toString();
//
//                        Log.d("cekJumlahPackSupplier", "onClick: " + jumlah_pack);
//
//                        String id_status_penjualan = supplierGudangActivity.id_status_penjualan_gudang;
//                        String id_barang = dataBarang.get(position).getIdBarang();
//                        tambahPenjualanGudang(id_status_penjualan, id_barang, number);
//
//                    }
//                });

    }

    private void tampilDialogDataKosong() {

        dialogDataKosong = new Dialog(context);

        dialogDataKosong.setContentView(R.layout.dialog_data_kosong);
        dialogDataKosong.setCancelable(false);

        final TextView txtTutup = dialogDataKosong.findViewById(R.id.text_tutup_dialog_data_kosong);

        dialogDataKosong.show();

        txtTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDataKosong.dismiss();
            }
        });

    }

    private void tampilDialog(int number_of_pack, int stock_db, int hargaModalToko, String id_status_penjualan, String id_barang) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_penjualan);
        dialog.setCancelable(false);

        final HorizontalQuantitizer numberPicker = dialog.findViewById(R.id.number_picker_dialog_qty_penjualan);
        final EditText edtJumlahPcs = dialog.findViewById(R.id.edt_pcs_dialog_qty_penjualan);
        final TextView txtTambah = dialog.findViewById(R.id.text_tambah_barang_dialog_penjualan_qty);
        final TextView txtCancel = dialog.findViewById(R.id.text_cancel_dialog_penjualan_qty);

        dialog.show();
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tambahPenjualanFromDialog(number_of_pack, stock_db, numberPicker, edtJumlahPcs, txtTambah,
                txtCancel, hargaModalToko, id_status_penjualan, id_barang);


    }

    private void tambahPenjualanFromDialog(int number_of_pack, int stock_db,
                                           HorizontalQuantitizer numberPicker, EditText edtJumlahPcs,
                                           TextView txtTambah, TextView txtCancel, int hargaModalToko,
                                           String id_status_penjualan, String id_barang) {

        numberPicker.setTextAnimationStyle(AnimationStyle.FALL_IN);
        numberPicker.setQuantitizerListener(new QuantitizerListener() {
            @Override
            public void onIncrease() {
                numberPicker.setMaxValue(stock_db);
                value = String.valueOf(numberPicker.getValue());
                Log.d("valueNP", "onIncrease: "+value);
                edtJumlahPcs.setEnabled(false);
                int jumlah_kurang = 0;
                if (Integer.parseInt(value) > stock_db) {
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(stock_db);
                } else {
                    jumlah_qty = Integer.parseInt(value) * number_of_pack;

                    edtJumlahPcs.setText(String.valueOf(jumlah_qty));
                    total = jumlah_qty * hargaModalToko;
                    jumlah_kurang = stock_db - jumlah_qty;
                    if (number_of_pack != 0) {
                        edit_pack = jumlah_kurang / number_of_pack;
                    } else {
                        Toast.makeText(context, "Jumlah satuan dalam pack barang ini 0," +
                                        " Silahkan edit data kembali",
                                Toast.LENGTH_SHORT).show();
                        numberPicker.setValue(0);

                    }
                }
            }

            @Override
            public void onDecrease() {
                edtJumlahPcs.setEnabled(false);
                value = String.valueOf(numberPicker.getValue());
                int jumlah_kurang = 0;
                if (Integer.parseInt(value) < 0) {
                    Toast.makeText(context, "Quantity tidak boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                } else if (value.equals("0")) {
                    edtJumlahPcs.setText("Jumlah Pcs");
                } else {
                    jumlah_qty = Integer.parseInt(value) * number_of_pack;
                    edtJumlahPcs.setText(String.valueOf(jumlah_qty));
                    total = jumlah_qty * hargaModalToko;
                    jumlah_kurang = stock_db - jumlah_qty;
                    if (number_of_pack != 0) {
                        edit_pack = jumlah_kurang / number_of_pack;
                    } else {
                        Toast.makeText(context, "Jumlah satuan dalam pack barang ini 0," +
                                        " Silahkan edit data kembali",
                                Toast.LENGTH_SHORT).show();
                        numberPicker.setValue(0);

                    }
                }
            }
        });

        txtTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Validasi stock barang saat ini");
                progressDialog.show();
                validateStock(id_barang, id_status_penjualan, progressDialog);
            }
        });

    }

    private void validateStock(String id_barang, String id_status_penjualan, ProgressDialog progressDialog) {

        ConfigRetrofit.service.cariBarangById(id_barang).enqueue(new Callback<ResponseCariBarangById>() {
            @Override
            public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    int jumlah_pack_validate = 0;
                    List<SearchBarangByIdItem> cariBarangId = response.body().getSearchBarangById();
                    for (int i = 0; i < cariBarangId.size(); i++) {
                        jumlah_pack_validate = Integer.parseInt(cariBarangId.get(i).getJumlahPack());
                    }
                    if (jumlah_pack_validate == 0 ||
                            jumlah_pack_validate < Integer.parseInt(value) ||
                            jumlah_pack_validate < 0) {
                        Toast.makeText(context, "Stock Barang Yang Anda Pilih Sudah Habis",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        tambahPenjualanGudang(id_status_penjualan, id_barang, String.valueOf(jumlah_qty), value);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Silahkan Periksa koneksi internet anda",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahPenjualanGudang(String id_status_penjualan, String id_barang, String jumlah_pack, String jml_pack) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPenjualanGudang(id_barang, String.valueOf(jumlah_qty), jml_pack,
                String.valueOf(total), supplierGudangActivity.nama_user, id_status_penjualan)
                .enqueue(new Callback<ResponseTambahPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPenjualan> call, Response<ResponseTambahPenjualan> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status == 1) {

                                Toast.makeText(context, "Berhasil Menambahkan Barang",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            } else {
                                Toast.makeText(context, "Gagal Menambahkan, Silahkan coba lagi",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPenjualan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void editPack(String id_barang) {

        ConfigRetrofit.service.editPackBarang(id_barang, String.valueOf(edit_pack))
                .enqueue(new Callback<ResponseEditPackBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditPackBarang> call, Response<ResponseEditPackBarang> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil Edit Pack", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Gagal Edit Pack", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditPackBarang> call, Throwable t) {
                        Toast.makeText(context, "koneksi error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class CariBarangPenjualanViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack, txtJmlPack, txtJumlahCount;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan;
        TextView edtJumlahPack;
        HorizontalQuantitizer selectNumber;
        RelativeLayout rlStockHabis;
        ImageView imgTambah, imgKurang;

        public CariBarangPenjualanViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            edtJumlahPack = itemView.findViewById(R.id.edt_jumlah_pcs_item_barang);
            rlStockHabis = itemView.findViewById(R.id.rl_stok_habis_supplier);

        }
    }
}
