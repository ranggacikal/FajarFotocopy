package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

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
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataPengirimanAdapter extends RecyclerView.Adapter<DataPengirimanAdapter.DataPengirimanViewHolder> {

    Context context;
    List<GetListPengirimanItem> dataPengiriman;

    public DataPengirimanAdapter(Context context, List<GetListPengirimanItem> dataPengiriman) {
        this.context = context;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @NotNull
    @Override
    public DataPengirimanViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_pengiriman, parent, false);
        return new DataPengirimanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataPengirimanViewHolder holder, int position) {

        String nama_barang = dataPengiriman.get(position).getNamaBarang();
        String jumlah = dataPengiriman.get(position).getJumlah();
        String status = dataPengiriman.get(position).getStatusPengiriman();
        String img = dataPengiriman.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .error(R.drawable.icon_img_error)
                .into(holder.imgDataPengiriman);

        holder.txtNamaBarang.setText(nama_barang);
        holder.txtJumlah.setText(jumlah);
        holder.txtStatus.setText(status);

    }

    @Override
    public int getItemCount() {
        return dataPengiriman.size();
    }

    public class DataPengirimanViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDataPengiriman;
        TextView txtNamaBarang, txtJumlah, txtStatus;

        public DataPengirimanViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgDataPengiriman = itemView.findViewById(R.id.img_barang_data_pengiriman);
            txtNamaBarang = itemView.findViewById(R.id.text_nama_barang_data_pengiriman);
            txtJumlah = itemView.findViewById(R.id.text_jumlah_barang_data_pengiriman);
            txtStatus = itemView.findViewById(R.id.text_status_barang_data_pengiriman);
        }
    }
}
