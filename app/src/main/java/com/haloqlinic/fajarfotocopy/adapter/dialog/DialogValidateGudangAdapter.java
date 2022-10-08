package com.haloqlinic.fajarfotocopy.adapter.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.listBarangGudangValidate.DataBarangGudangListItem;

import java.util.List;

public class DialogValidateGudangAdapter extends RecyclerView
        .Adapter<DialogValidateGudangAdapter.DialogValidateViewHolder> {

    Context context;
    List<DataBarangGudangListItem> dataBarangGudangList;

    public DialogValidateGudangAdapter(Context context, List<DataBarangGudangListItem> dataBarangGudangList) {
        this.context = context;
        this.dataBarangGudangList = dataBarangGudangList;
    }

    @NonNull
    @Override
    public DialogValidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_validate_barang,
                parent, false);
        return new DialogValidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogValidateViewHolder holder, int position) {
        String imgLink = dataBarangGudangList.get(position).getImageBarang();
        Glide.with(context)
                .load(imgLink)
                .error(R.drawable.logobaru)
                .into(holder.imgBarang);
        holder.txtNama.setText(dataBarangGudangList.get(position).getNamaBarang());
        holder.txtStock.setText(dataBarangGudangList.get(position).getStock());
        holder.txtPack.setText(dataBarangGudangList.get(position).getJumlahPack());
    }

    @Override
    public int getItemCount() {
        return dataBarangGudangList.size();
    }

    public class DialogValidateViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBarang;
        TextView txtNama, txtStock, txtPack;
        public DialogValidateViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.iv_item_validate_barang);
            txtNama = itemView.findViewById(R.id.tv_nama_barang_item_validate_barang);
            txtStock = itemView.findViewById(R.id.tv_value_stock_item_validate_barang);
            txtPack = itemView.findViewById(R.id.tv_value_pack_item_validate_barang);
        }
    }
}
