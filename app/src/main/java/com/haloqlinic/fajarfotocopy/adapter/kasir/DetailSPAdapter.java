package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.content.Context;
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

public class DetailSPAdapter extends RecyclerView.Adapter<DetailSPAdapter.DetailSPViewHolder> {

    Context context;
    List<DetailStatusPenjualanItem> detailStatusPenjualanItems;

    public DetailSPAdapter(Context context, List<DetailStatusPenjualanItem> detailStatusPenjualanItems) {
        this.context = context;
        this.detailStatusPenjualanItems = detailStatusPenjualanItems;
    }

    @NonNull
    @NotNull
    @Override
    public DetailSPViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_status_penjualan, parent, false);
        return new DetailSPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetailSPViewHolder holder, int position) {


        holder.txtIdStatus.setText(detailStatusPenjualanItems.get(position).getIdPenjualan());
        holder.txtNamaBarang.setText(detailStatusPenjualanItems.get(position).getNamaBarang());
        holder.txtQty.setText(detailStatusPenjualanItems.get(position).getJumlahBarang());
        int total = Integer.parseInt(detailStatusPenjualanItems.get(position).getTotal());

        holder.txtTotal.setText("Rp" + NumberFormat.getInstance().format(total));

    }

    @Override
    public int getItemCount() {
        return detailStatusPenjualanItems.size();
    }

    public class DetailSPViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdStatus, txtNamaBarang, txtQty, txtTotal;

        public DetailSPViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtIdStatus = itemView.findViewById(R.id.text_item_id_detail_penjualan);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_barang_detail_penjualan);
            txtQty = itemView.findViewById(R.id.text_item_qty_detail_penjualan);
            txtTotal = itemView.findViewById(R.id.text_item_total_detail_penjualan);
        }
    }
}
