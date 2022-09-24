package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.BarangPenjualanItem;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PembayaranAdapter extends RecyclerView.Adapter<PembayaranAdapter.PembayaranViewHolder> {

    Context context;
    List<BarangPenjualanItem> dataBarang;

    public PembayaranAdapter(Context context, List<BarangPenjualanItem> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @NotNull
    @Override
    public PembayaranViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pembayaran, parent, false);
        return new PembayaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PembayaranViewHolder holder, int position) {

        String satuan = dataBarang.get(position).getJenisSatuan();
        int total_harga = Integer.parseInt(dataBarang.get(position).getTotal());
        int jumlah;

        int harga_barang;

        holder.txtNamaProduk.setText(dataBarang.get(position).getNamaBarang());

        if (satuan.equals("Pack")){
            holder.txtQty.setText(dataBarang.get(position).getJumlahPack());
            holder.txtSatuan.setText("Pack");
            jumlah = Integer.parseInt(dataBarang.get(position).getJumlahPack());
            harga_barang = total_harga / jumlah;
        }else{
            holder.txtQty.setText(dataBarang.get(position).getJumlahBarang());
            holder.txtSatuan.setText("Pcs");
            jumlah = Integer.parseInt(dataBarang.get(position).getJumlahBarang());
            harga_barang = total_harga / jumlah;
        }
        holder.txtHarga.setText("Rp" + NumberFormat.getInstance().format(harga_barang));
        holder.txtTotal.setText("Rp" + NumberFormat.getInstance().format(total_harga));


    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class PembayaranViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaProduk, txtQty, txtHarga, txtTotal, txtSatuan;

        public PembayaranViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaProduk = itemView.findViewById(R.id.text_item_nama_pembayaran);
            txtQty = itemView.findViewById(R.id.text_item_qty_pembayaran);
            txtHarga = itemView.findViewById(R.id.text_item_harga_pembayaran);
            txtSatuan = itemView.findViewById(R.id.text_item_satuan_pembayaran);
            txtTotal = itemView.findViewById(R.id.text_item_total_pembayaran);
        }
    }
}
