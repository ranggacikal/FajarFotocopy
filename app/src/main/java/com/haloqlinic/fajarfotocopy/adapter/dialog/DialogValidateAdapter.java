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
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataMintaBarangAdapter;
import com.haloqlinic.fajarfotocopy.model.dataBarangOutletList.DataBarangOutletListItem;

import java.util.List;

public class DialogValidateAdapter extends RecyclerView.Adapter<DialogValidateAdapter.DialogValidateViewHolder> {

    Context context;
    List<DataBarangOutletListItem> dataBarangValidate;

    public DialogValidateAdapter(Context context, List<DataBarangOutletListItem> dataBarangValidate) {
        this.context = context;
        this.dataBarangValidate = dataBarangValidate;
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
        String imgLink = dataBarangValidate.get(position).getImageBarang();
        Glide.with(context)
                .load(imgLink)
                .error(R.drawable.logobaru)
                .into(holder.imgBarang);
        holder.txtNama.setText(dataBarangValidate.get(position).getNamaBarang());
        holder.txtStock.setText(dataBarangValidate.get(position).getStock());
        holder.txtPack.setText(dataBarangValidate.get(position).getJumlahPack());
    }

    @Override
    public int getItemCount() {
        return dataBarangValidate.size();
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
