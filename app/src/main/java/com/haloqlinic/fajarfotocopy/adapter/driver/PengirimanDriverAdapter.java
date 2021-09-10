package com.haloqlinic.fajarfotocopy.adapter.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver.GetListPengirimanByIdStatusItem;

import java.util.List;

public class PengirimanDriverAdapter extends RecyclerView.Adapter<PengirimanDriverAdapter.PengirimanDriverViewHolder> {

    Context context;
    List<GetListPengirimanByIdStatusItem> dataStatus;

    public PengirimanDriverAdapter(Context context, List<GetListPengirimanByIdStatusItem> dataStatus) {
        this.context = context;
        this.dataStatus = dataStatus;
    }

    @NonNull
    @Override
    public PengirimanDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_driver, parent, false);
        return new PengirimanDriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengirimanDriverViewHolder holder, int position) {

        String link_image = dataStatus.get(position).getImageBarang();

        Glide.with(context)
                .load(link_image)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataStatus.get(position).getNamaBarang());
        holder.txtJumlah.setText(dataStatus.get(position).getJumlah());

    }

    @Override
    public int getItemCount() {
        return dataStatus.size();
    }

    public class PengirimanDriverViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtJumlah;

        public PengirimanDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_detail_driver);
            txtNama = itemView.findViewById(R.id.text_item_nama_detail_driver);
            txtJumlah = itemView.findViewById(R.id.text_item_jumlah_detail_driver);
        }
    }
}
