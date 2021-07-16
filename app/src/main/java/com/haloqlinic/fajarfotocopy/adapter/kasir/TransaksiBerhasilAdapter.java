package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.DetailStatusPenjualanItem;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class TransaksiBerhasilAdapter extends RecyclerView.Adapter<TransaksiBerhasilAdapter.
        TransaksiBerhasilViewHolder> {

    Context context;
    List<DetailStatusPenjualanItem> dataDetail;

    public TransaksiBerhasilAdapter(Context context, List<DetailStatusPenjualanItem> dataDetail) {
        this.context = context;
        this.dataDetail = dataDetail;
    }

    @NonNull
    @NotNull
    @Override
    public TransaksiBerhasilViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cetak_bukti, parent,false);
        return new TransaksiBerhasilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransaksiBerhasilViewHolder holder, int position) {

        int total = Integer.parseInt(dataDetail.get(position).getTotal());
        int harga =  total / Integer.parseInt(dataDetail.get(position).getJumlahBarang());

        holder.txtIdPenjualan.setText(dataDetail.get(position).getIdPenjualan());
        holder.txtQty.setText(dataDetail.get(position).getJumlahBarang());
        holder.txtTotal.setText("Rp" + NumberFormat.getInstance().format(total));
        holder.txtNamaBarang.setText(dataDetail.get(position).getNamaBarang());

    }

    @Override
    public int getItemCount() {
        return dataDetail.size();
    }

    public class TransaksiBerhasilViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdPenjualan, txtQty, txtNamaBarang,txtTotal;

        public TransaksiBerhasilViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txtIdPenjualan = itemView.findViewById(R.id.text_item_id_penjualan_cetak_bukti);
            txtQty = itemView.findViewById(R.id.text_item_qty_cetak_bukti);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_barang_cetak_bukti);
            txtTotal = itemView.findViewById(R.id.text_item_total_cetak_bukti);
        }
    }
}
