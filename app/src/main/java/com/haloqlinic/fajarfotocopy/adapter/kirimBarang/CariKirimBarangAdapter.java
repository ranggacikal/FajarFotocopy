package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
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
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.tambahPengiriman.ResponseTambahPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariKirimBarangAdapter extends RecyclerView.Adapter<CariKirimBarangAdapter.CariKirimBarangViewHolder> {

    Context context;
    List<SearchBarangByNamaItem> dataCariBarang;
    KirimBarangGudangActivity kirimBarangGudangActivity;

    String id_barang = "";
    Dialog dialog;

    public CariKirimBarangAdapter(Context context, List<SearchBarangByNamaItem> dataCariBarang, KirimBarangGudangActivity kirimBarangGudangActivity) {
        this.context = context;
        this.dataCariBarang = dataCariBarang;
        this.kirimBarangGudangActivity = kirimBarangGudangActivity;
    }

    @NonNull
    @NotNull
    @Override
    public CariKirimBarangViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cari_barang, parent, false);
        return new CariKirimBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariKirimBarangViewHolder holder, int position) {

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
                tampilDialog(stockPack, stockPcs);
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
                        tampilDialog(stockPack, stockPcs);
                    }
                });

    }

    private void tampilDialog(int stockPack, int stockPcs) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_kirim_barang);
        dialog.setCancelable(false);

        final EditText edtQty = dialog.findViewById(R.id.edt_dialog_qty_pcs);
        final EditText edtPack = dialog.findViewById(R.id.edt_dialog_qty_pack);
        final TextView txtTambahBarang = dialog.findViewById(R.id.text_dialog_tambah_barang);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tambah_barang);

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

        ConfigRetrofit.service.tambahPengiriman(id_barang, qty, pack, id_toko, id_status, "pending")
                .enqueue(new Callback<ResponseTambahPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPengiriman> call, Response<ResponseTambahPengiriman> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil Menambahkan data", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return dataCariBarang.size();
    }

    public class CariKirimBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCariBarang;
        TextView txtNamaBarang;
        Button btnTambah;

        public CariKirimBarangViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCariBarang = itemView.findViewById(R.id.img_item_cari_cari_barang);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_cari_barang);
            btnTambah = itemView.findViewById(R.id.btn_tambah_kirim_barang_gudang);
        }
    }
}
