package com.haloqlinic.fajarfotocopy.adapter.gudang;

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
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataPermintaanBarangItem;

import java.util.List;

public class DataListMintaBarangAdapter extends RecyclerView
        .Adapter<DataListMintaBarangAdapter.DataListMintaBarangViewHolder> {

    Context context;
    List<DataBarangItem> dataBarangMinta;

    public DataListMintaBarangAdapter(Context context, List<DataBarangItem> dataBarangMinta) {
        this.context = context;
        this.dataBarangMinta = dataBarangMinta;
    }

    @NonNull
    @Override
    public DataListMintaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang_minta,
                parent, false);
        return new DataListMintaBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataListMintaBarangViewHolder holder, int position) {
        String link_image = dataBarangMinta.get(position).getImageBarang();
        Glide.with(context)
                .load(link_image)
                .into(holder.imgListMinta);
        holder.txtListNama.setText(dataBarangMinta.get(position).getNamaBarang());
    }

    @Override
    public int getItemCount() {
        return dataBarangMinta.size();
    }

    public class DataListMintaBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgListMinta;
        TextView txtListNama;

        public DataListMintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgListMinta = itemView.findViewById(R.id.img_list_minta_barang);
            txtListNama = itemView.findViewById(R.id.tv_list_nama_minta_barang);
        }
    }
}
