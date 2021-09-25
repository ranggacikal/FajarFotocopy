package com.haloqlinic.fajarfotocopy.adapter.driver;

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
import com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus.ListPenjualanGudangByIdStatusItem;

import java.util.List;

public class PengirimanPenjualanGudangAdapter extends RecyclerView.Adapter<PengirimanPenjualanGudangAdapter
        .PengirimanPenjualanGudangViewHolder> {

    Context context;
    List<ListPenjualanGudangByIdStatusItem> dataPenjualan;

    public PengirimanPenjualanGudangAdapter(Context context, List<ListPenjualanGudangByIdStatusItem> dataPenjualan) {
        this.context = context;
        this.dataPenjualan = dataPenjualan;
    }

    @NonNull
    @Override
    public PengirimanPenjualanGudangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_driver, parent, false);
        return new PengirimanPenjualanGudangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengirimanPenjualanGudangViewHolder holder, int position) {

        String link_image = dataPenjualan.get(position).getImageBarang();

        Glide.with(context)
                .load(link_image)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataPenjualan.get(position).getNamaBarang());
        holder.txtJumlah.setText(dataPenjualan.get(position).getJumlahBarang());

    }

    @Override
    public int getItemCount() {
        return dataPenjualan.size();
    }

    public class PengirimanPenjualanGudangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtJumlah;

        public PengirimanPenjualanGudangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_detail_driver);
            txtNama = itemView.findViewById(R.id.text_item_nama_detail_driver);
            txtJumlah = itemView.findViewById(R.id.text_item_jumlah_detail_driver);
        }
    }
}
