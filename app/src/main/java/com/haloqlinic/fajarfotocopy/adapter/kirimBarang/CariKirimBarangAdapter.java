package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
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

import org.jetbrains.annotations.NotNull;

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
                tampilDialog();
            }
        });

        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_barang = dataCariBarang.get(position).getIdBarang();
                tampilDialog();
            }
        });

    }

    private void tampilDialog() {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_kirim_barang);
        dialog.setCancelable(false);

        final EditText edtQty = dialog.findViewById(R.id.edt_dialog_qty);
        final EditText edtId = dialog.findViewById(R.id.edt_dialog_id_pengiriman);
        final TextView txtTambahBarang = dialog.findViewById(R.id.text_dialog_tambah_barang);

        dialog.show();

        txtTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = edtQty.getText().toString();
                String id_pengiriman = edtId.getText().toString();
                tambahPengiriman(qty, id_pengiriman);
            }
        });

    }

    private void tambahPengiriman(String qty, String id_pengiriman) {

        String id_toko = kirimBarangGudangActivity.id_toko;
        String id_status = kirimBarangGudangActivity.id_status_pengiriman;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang Ke Pengiriman barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPengiriman(id_pengiriman, id_barang, qty, id_toko, id_status)
                .enqueue(new Callback<ResponseTambahPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPengiriman> call, Response<ResponseTambahPengiriman> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status==1){
                                Toast.makeText(context, "Berhasil Menambahkan data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(context, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
