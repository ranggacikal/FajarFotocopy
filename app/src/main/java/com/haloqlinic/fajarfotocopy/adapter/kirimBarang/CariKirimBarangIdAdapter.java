package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.KirimBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.hapusMintaBarang.ResponseHapusMintaBarang;
import com.haloqlinic.fajarfotocopy.model.tambahPengiriman.ResponseTambahPengiriman;
import com.haloqlinic.fajarfotocopy.model.updateStockPengiriman.ResponseUpdateStockPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariKirimBarangIdAdapter extends RecyclerView.Adapter<CariKirimBarangIdAdapter.CariKirimBarangIdViewHolder>  {

    Context context;
    List<SearchBarangByIdItem> dataCariBarang;
    KirimBarangGudangActivity kirimBarangGudangActivity;

    String id_barang = "";
    Dialog dialog, dialogDataKosong;
    int number_of_pack;

    public CariKirimBarangIdAdapter(Context context, List<SearchBarangByIdItem> dataCariBarang, KirimBarangGudangActivity kirimBarangGudangActivity) {
        this.context = context;
        this.dataCariBarang = dataCariBarang;
        this.kirimBarangGudangActivity = kirimBarangGudangActivity;
    }

    @NonNull
    @NotNull
    @Override
    public CariKirimBarangIdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cari_barang, parent, false);
        return new CariKirimBarangIdAdapter.CariKirimBarangIdViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull CariKirimBarangIdViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String img = dataCariBarang.get(position).getImageBarang();
        String nama_barang = dataCariBarang.get(position).getNamaBarang();


        Glide.with(context)
                .load(img)
                .error(R.drawable.icon_img_error)
                .into(holder.imgCariBarang);

        holder.txtNamaBarang.setText(nama_barang);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_barang = dataCariBarang.get(position).getIdBarang();
                int stockPcs = Integer.parseInt(dataCariBarang.get(position).getStock());
                int stockPack = Integer.parseInt(dataCariBarang.get(position).getJumlahPack());
                int number_of_pack = Integer.parseInt(dataCariBarang.get(position).getNumberOfPack());
                if (number_of_pack==0){
                    Toast.makeText(context, "Jumlah barang dalam pack adalah 0, silahkan edit " +
                            "data terlebih dahulu", Toast.LENGTH_LONG).show();
                }else if (stockPcs==0 || stockPcs<0) {

                    tampilDialogKosong();

                }else {
                    tampilDialog(stockPack, stockPcs, number_of_pack);
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(holder.btnTambah)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_barang = dataCariBarang.get(position).getIdBarang();
                        int stockPcs = Integer.parseInt(dataCariBarang.get(position).getStock());
                        int stockPack = Integer.parseInt(dataCariBarang.get(position).getJumlahPack());
                        int number_of_pack = Integer.parseInt(dataCariBarang.get(position).getNumberOfPack());
                        if (number_of_pack==0){
                            Toast.makeText(context, "Jumlah barang dalam pack adalah 0, silahkan edit " +
                                    "data terlebih dahulu", Toast.LENGTH_LONG).show();
                        }else if (stockPcs==0 || stockPcs<0) {

                            tampilDialogKosong();

                        }else {
                            tampilDialog(stockPack, stockPcs, number_of_pack);
                        }
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

    private void tampilDialog(int stockPack, int stockPcs, int number_of_pack) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_kirim_barang);
        dialog.setCancelable(false);

        final EditText edtQty = dialog.findViewById(R.id.edt_dialog_qty_pcs);
        final EditText edtPack = dialog.findViewById(R.id.edt_dialog_qty_pack);
        final TextView txtTambahBarang = dialog.findViewById(R.id.text_dialog_tambah_barang);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tambah_barang);

        edtQty.setEnabled(false);

        edtPack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String jumlah_pack = edtPack.getText().toString();

                if (jumlah_pack.equals("")){
                    edtQty.setText("");
                }else{
                    int total_qty = Integer.parseInt(jumlah_pack) * number_of_pack;
                    edtQty.setText(String.valueOf(total_qty));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

        txtTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = edtQty.getText().toString();
                String pack = edtPack.getText().toString();
                tambahPengiriman(qty, pack, stockPcs, stockPack);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void tambahPengiriman(String qty, String pack, int stockPcs, int stockPack) {

        String id_toko = kirimBarangGudangActivity.id_toko;
        String id_status = kirimBarangGudangActivity.id_status_pengiriman;

        if (Integer.parseInt(qty) > stockPcs) {
            Toast.makeText(context, "Jumlah stock tidak cukup dengan quantity yg anda masukan",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(pack) > stockPack) {
            Toast.makeText(context, "Jumlah stock tidak cukup dengan quantity yg anda masukan",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang Ke Pengiriman barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPengiriman(id_barang, qty, pack, String.valueOf(number_of_pack),
                id_toko, id_status, "pending")
                .enqueue(new Callback<ResponseTambahPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPengiriman> call, Response<ResponseTambahPengiriman> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil Menambahkan data", Toast.LENGTH_SHORT).show();
                                kurangiStock(id_barang, qty, pack, stockPack, stockPcs);
                                String id_minta_barang = kirimBarangGudangActivity.id_minta_barang;
                                if (id_minta_barang!=null) {
                                    hapusMintaBarang(id_minta_barang);
                                }
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {
                            progressDialog.dismiss();
                            Log.d("checkDataPengiriman", "id_barang: " + id_barang);
                            Log.d("checkDataPengiriman", "qty: " + qty);
                            Log.d("checkDataPengiriman", "pack: " + pack);
                            Log.d("checkDataPengiriman", "id_toko: " + id_toko);
                            Log.d("checkDataPengiriman", "id_status: " + id_status);
                            Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void kurangiStock(String id_barang, String qty, String pack, int stockPack, int stockPcs) {

        int updateStock = stockPcs - Integer.parseInt(qty);
        int updatePack = stockPack - Integer.parseInt(pack);

        ConfigRetrofit.service.updateStockPengiriman(id_barang, String.valueOf(updateStock), String.valueOf(updatePack))
                .enqueue(new Callback<ResponseUpdateStockPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStockPengiriman> call, Response<ResponseUpdateStockPengiriman> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();
                            if (status == 1) {
                                Log.d("SuksesUpdateStock", "onResponse: Update Stock Berhasil");
                            } else {
                                Toast.makeText(context, "Gagal update stock", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Terjadi kesalahan saat update stock", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStockPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void hapusMintaBarang(String id_minta_barang) {
        ConfigRetrofit.service.hapusMintaBarang(id_minta_barang).enqueue(new Callback<ResponseHapusMintaBarang>() {
            @Override
            public void onResponse(Call<ResponseHapusMintaBarang> call, Response<ResponseHapusMintaBarang> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(context, "Berhasil Update Minta Barang", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, "Gagal Update Minta Barang", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Response dari server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusMintaBarang> call, Throwable t) {
                Toast.makeText(context, "Koneksi Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataCariBarang.size();
    }

    public class CariKirimBarangIdViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCariBarang;
        TextView txtNamaBarang;
        Button btnTambah;

        public CariKirimBarangIdViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCariBarang = itemView.findViewById(R.id.img_item_cari_cari_barang);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_cari_barang);
            btnTambah = itemView.findViewById(R.id.btn_tambah_kirim_barang_gudang);
        }
    }
}
