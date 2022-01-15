package com.haloqlinic.fajarfotocopy.adapter.gudang;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.HasilPencarianTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.model.tambahTransferBarang.ResponseTambahTransferBarang;
import com.mcdev.quantitizerlibrary.AnimationStyle;
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer;
import com.mcdev.quantitizerlibrary.QuantitizerListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariTransferBarangAdapter extends RecyclerView.Adapter<CariTransferBarangAdapter.CariTransferBarangViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> dataBarang;
    HasilPencarianTransferBarangGudangActivity hasilActivity;

    int number = 0;
    String jumlah_pack = "";
    String id_barang_outlet_pengirim = "";
    String id_barang = "";
    String value = "";

    Dialog dialog, dialogDataKosong;

    public CariTransferBarangAdapter(Context context, List<SearchBarangOutletByNamaItem> dataBarang,
                                     HasilPencarianTransferBarangGudangActivity hasilActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.hasilActivity = hasilActivity;
    }

    @NonNull
    @NotNull
    @Override
    public CariTransferBarangViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariTransferBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariTransferBarangViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int hargaPcs = Integer.parseInt(dataBarang.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(dataBarang.get(position).getHargaJualPack());
        id_barang_outlet_pengirim = dataBarang.get(position).getIdBarangOutlet();
        id_barang = dataBarang.get(position).getIdBarang();


        String img = dataBarang.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataBarang.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.lblJumlahPack.setVisibility(View.GONE);
        holder.edtJumlahPack.setVisibility(View.GONE);

//        holder.numberPicker.setNumber("1");

//        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                number = holder.numberPicker.getNumber();
//                int stock = Integer.parseInt(dataBarang.get(position).getStock());
//                if (Integer.parseInt(number) > stock ){
//                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
//                    holder.numberPicker.setNumber(String.valueOf(stock));
//                }
//
//            }
//        });

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_barang_outlet_pengirim = dataBarang.get(position).getIdBarangOutlet();
                        id_barang = dataBarang.get(position).getIdBarang();
                        int stock = Integer.parseInt(dataBarang.get(position).getStock());
                        int number_of_pack = Integer.parseInt(dataBarang.get(position).getNumberOfPack());

                        if (stock == 0 || stock < 0) {
                            tampilDialogKosong();
                        } else {
                            tampilDialog(id_barang_outlet_pengirim, id_barang, stock, number_of_pack);
                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.btnTambahBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumlah_pack = holder.edtJumlahPack.getText().toString();

                        if (jumlah_pack.isEmpty()) {

                            holder.edtJumlahPack.setError("Jumlah Pack Tidak boleh kosong");
                            holder.edtJumlahPack.requestFocus();
                            return;
                        }

                        tambahPesanan(id_barang_outlet_pengirim, id_barang, String.valueOf(number), jumlah_pack);
                    }
                });
    }

    private void tampilDialog(String id_barang_outlet_pengirim, String id_barang, int stock, int number_of_pack) {

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

        tambahPenjualanFromDialog(id_barang_outlet_pengirim, id_barang, numberPicker, edtJumlahPcs, txtTambah,
                txtCancel, number_of_pack, stock);

    }

    private void tambahPenjualanFromDialog(String id_barang_outlet_pengirim, String id_barang,
                                           HorizontalQuantitizer numberPicker, EditText edtJumlahPcs,
                                           TextView txtTambah, TextView txtCancel, int number_of_pack, int stock) {

        numberPicker.setTextAnimationStyle(AnimationStyle.FALL_IN);
        numberPicker.setQuantitizerListener(new QuantitizerListener() {
            @Override
            public void onIncrease() {
                value = String.valueOf(numberPicker.getValue());
                edtJumlahPcs.setEnabled(false);
                if (number_of_pack == 0) {
                    Toast.makeText(context, "Jumlah satuan dalam pack barang ini 0," +
                                    " Silahkan edit data kembali",
                            Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                }
                if (number > stock) {
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    numberPicker.setEnabled(false);
                    numberPicker.setMaxValue(stock);
                    edtJumlahPcs.setText(String.valueOf(number));
                }
                number = Integer.parseInt(value) * number_of_pack;
                edtJumlahPcs.setText(String.valueOf(number));
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
                    edtJumlahPcs.setText("Jumlah Pack");
                } else {
                    number = Integer.parseInt(value) * number_of_pack;
                    edtJumlahPcs.setText(String.valueOf(number));
                    Toast.makeText(context, "Jumlah satuan dalam pack barang ini 0," +
                                    " Silahkan edit data kembali",
                            Toast.LENGTH_SHORT).show();
                    numberPicker.setValue(0);
                }
            }
        });

        txtTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahPesanan(id_barang_outlet_pengirim, id_barang, String.valueOf(number), value);
            }
        });

    }

    private void tampilDialogKosong() {

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

    private void tambahPesanan(String id_barang_outlet_pengirim, String id_barang, String qty, String jumlah_pack) {


        Random rnd = new Random();
        int numberRandom = rnd.nextInt(999999);

        String randomId = String.format("%06d", numberRandom);
        String id_transfer_barang = "TB" + randomId;

        String id_status_transfer = hasilActivity.id_status_transfer;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang ...");
        progressDialog.show();

        ConfigRetrofit.service.tambahTransferBarang(id_transfer_barang, id_barang_outlet_pengirim, id_barang,
                String.valueOf(qty), jumlah_pack, id_status_transfer, "pending")
                .enqueue(new Callback<ResponseTambahTransferBarang>() {
                    @Override
                    public void onResponse(Call<ResponseTambahTransferBarang> call, Response<ResponseTambahTransferBarang> response) {
                        if (response.isSuccessful()) {

                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            String pesan = response.body().getPesan();

                            if (status == 1) {

                                Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                                hasilActivity.getCount();
                                dialog.dismiss();

                            } else {
                                Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Menambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahTransferBarang> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("paramTambahTransfer", "id_status_transfer: " + id_status_transfer);
                        Log.d("paramTambahTransfer", "id_transfer_barang: " + id_transfer_barang);
                        Log.d("paramTambahTransfer", "id_barang_outlet_pengirim: " + CariTransferBarangAdapter.this.id_barang_outlet_pengirim);
                        Log.d("paramTambahTransfer", "id_barang: " + CariTransferBarangAdapter.this.id_barang);
                        Log.d("paramTambahTransfer", "number: " + number);
                        Log.d("paramTambahTransfer", "jumlah_pack: " + jumlah_pack);
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class CariTransferBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack, lblJumlahPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan, btnTambahBarang;
        EditText edtJumlahPack;

        public CariTransferBarangViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            numberPicker = itemView.findViewById(R.id.elegant_nb_item_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            btnTambahBarang = itemView.findViewById(R.id.btn_tambah_barang_transfer);
            edtJumlahPack = itemView.findViewById(R.id.edt_item_jumlah_pack_transfer);
            lblJumlahPack = itemView.findViewById(R.id.lbl_jumlah_pack);
        }
    }
}
