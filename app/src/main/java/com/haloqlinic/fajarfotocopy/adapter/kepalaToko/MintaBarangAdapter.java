package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.mintaBarang.ResponseMintaBarang;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MintaBarangAdapter extends RecyclerView.Adapter<MintaBarangAdapter.MintaBarangViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> dataBarang;
    TambahBarangKetoActivity tambahBarangKetoActivity;
    Dialog dialog;
    SimpleDateFormat dateFormat;
    Calendar calendar;
    String id_barang = "";
    String id_barang_minta, id_outlet_minta;

    private SharedPreferencedConfig preferencedConfig;

    public MintaBarangAdapter(Context context, List<SearchBarangOutletByNamaItem> dataBarang,
                              TambahBarangKetoActivity tambahBarangKetoActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.tambahBarangKetoActivity = tambahBarangKetoActivity;
    }

    @NonNull
    @Override
    public MintaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minta_barang, parent, false);
        return new MintaBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MintaBarangViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String img = dataBarang.get(position).getImageBarang();

        preferencedConfig = new SharedPreferencedConfig(context);

        id_outlet_minta = preferencedConfig.getPreferenceIdOutlet();
        id_barang_minta = dataBarang.get(position).getIdBarang();

        Glide.with(context)
                .load(img)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgMintaBarang);

        holder.txtNamaBarang.setText(dataBarang.get(position).getNamaBarang());
        holder.txtStockPcs.setText(dataBarang.get(position).getStock());
        holder.txtPack.setText(dataBarang.get(position).getJumlahPack());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_barang = dataBarang.get(position).getNamaBarang();
                int stockPcs = Integer.parseInt(dataBarang.get(position).getStock());
                int stockPack = Integer.parseInt(dataBarang.get(position).getJumlahPack());
                tampilDialog(stockPack, stockPcs);
            }
        });

        PushDownAnim.setPushDownAnimTo(holder.btnTambah)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_barang = dataBarang.get(position).getIdBarang();
                        int stockPcs = Integer.parseInt(dataBarang.get(position).getStock());
                        int stockPack = Integer.parseInt(dataBarang.get(position).getJumlahPack());
                        tampilDialog(stockPack, stockPcs);
                    }
                });

    }

    private void tampilDialog(int stockPack, int stockPcs) {
        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_minta_barang);
        dialog.setCancelable(false);

        final TextView txtTambahBarang = dialog.findViewById(R.id.text_dialog_tambah_barang_keto);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tambah_barang_keto);

        dialog.show();

        txtTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahMintaBarang(stockPcs, stockPack);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void tambahMintaBarang(int stockPcs, int stockPack) {
        String nama_toko = tambahBarangKetoActivity.nama_toko;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        String date = dateFormat.format(calendar.getTime());

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang Ke Permintaan Barang");
        progressDialog.show();

        ConfigRetrofit.service.mintaBarang("Dalam Proses", date, id_barang,
                id_outlet_minta).enqueue(new Callback<ResponseMintaBarang>() {
            @Override
            public void onResponse(Call<ResponseMintaBarang> call, Response<ResponseMintaBarang> response) {
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
                    Log.d("checkDataPermintaan", "id_barang: " + id_barang);
                    Log.d("checkDataPermintaan", "nama_toko: " + nama_toko);
                    Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseMintaBarang> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class MintaBarangViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMintaBarang;
        TextView txtNamaBarang, txtStockPcs, txtPack;
        Button btnTambah;

        public MintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMintaBarang = itemView.findViewById(R.id.img_item_minta_barang);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_minta_barang);
            txtStockPcs = itemView.findViewById(R.id.text_item_stock_pcs_minta_barang);
            txtPack = itemView.findViewById(R.id.text_item_stock_pack_minta_barang);
            btnTambah = itemView.findViewById(R.id.btn_tambah_minta_barang_keto);
        }
    }
}
