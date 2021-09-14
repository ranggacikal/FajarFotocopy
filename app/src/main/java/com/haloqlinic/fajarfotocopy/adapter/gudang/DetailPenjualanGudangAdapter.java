package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.detailPenjualanGudang.DetailPenjualanGudangItem;

import java.text.NumberFormat;
import java.util.List;

public class DetailPenjualanGudangAdapter extends RecyclerView.Adapter<DetailPenjualanGudangAdapter.DetailPenjualanGudangViewHolder> {

    Context context;
    List<DetailPenjualanGudangItem> dataPenjualan;

    public DetailPenjualanGudangAdapter(Context context, List<DetailPenjualanGudangItem> dataPenjualan) {
        this.context = context;
        this.dataPenjualan = dataPenjualan;
    }

    @NonNull
    @Override
    public DetailPenjualanGudangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_status_supplier, parent, false);
        return new DetailPenjualanGudangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjualanGudangViewHolder holder, int position) {
        int total = Integer.parseInt(dataPenjualan.get(position).getTotal());

        holder.txtId.setText(dataPenjualan.get(position).getIdPenjualanGudang());
        holder.txtNama.setText(dataPenjualan.get(position).getNamaBarang());
        holder.txtQty.setText(dataPenjualan.get(position).getJumlahBarang());
        holder.txtTotal.setText("Rp" + NumberFormat.getInstance().format(total));
    }

    @Override
    public int getItemCount() {
        return dataPenjualan.size();
    }

    public class DetailPenjualanGudangViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtNama, txtQty, txtTotal;

        public DetailPenjualanGudangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_item_id_detail_supplier);
            txtNama = itemView.findViewById(R.id.txt_item_nama_barang_detail_supplier);
            txtQty = itemView.findViewById(R.id.txt_item_qty_detail_supplier);
            txtTotal = itemView.findViewById(R.id.txt_item_total_detail_supplier);
        }
    }
}
