package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.BarangPenjualanGudangItem;

import java.text.NumberFormat;
import java.util.List;

public class KeranjangSupplierAdapter extends RecyclerView.Adapter<KeranjangSupplierAdapter.KeranjangSupplierViewHolder> {

    Context context;
    List<BarangPenjualanGudangItem> dataBarang;

    public KeranjangSupplierAdapter(Context context, List<BarangPenjualanGudangItem> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @Override
    public KeranjangSupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keranjang_supplier, parent, false);
        return new KeranjangSupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangSupplierViewHolder holder, int position) {

        holder.txtNamaBarang.setText(dataBarang.get(position).getNamaBarang());
        holder.txtQty.setText(dataBarang.get(position).getJumlahBarang());
        int harga = Integer.parseInt(dataBarang.get(position).getHargaModalGudang());
        int total = Integer.parseInt(dataBarang.get(position).getTotal());

        holder.txtHarga.setText("Rp"+ NumberFormat.getInstance().format(harga));
        holder.txtTotal.setText("Rp"+NumberFormat.getInstance().format(total));

    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class KeranjangSupplierViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaBarang, txtQty, txtHarga, txtTotal;

        public KeranjangSupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaBarang = itemView.findViewById(R.id.item_keranjang_supplier_nama);
            txtQty = itemView.findViewById(R.id.item_keranjang_supplier_qty);
            txtHarga = itemView.findViewById(R.id.item_keranjang_supplier_harga);
            txtTotal = itemView.findViewById(R.id.item_keranjang_supplier_total);
        }
    }
}
