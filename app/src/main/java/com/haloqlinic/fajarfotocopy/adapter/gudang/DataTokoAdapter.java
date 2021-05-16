package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataTokoAdapter extends RecyclerView.Adapter<DataTokoAdapter.DataTokoViewHolder> {

    Context context;
    List<DataTokoItem> dataTokoItems;

    public DataTokoAdapter(Context context, List<DataTokoItem> dataTokoItems) {
        this.context = context;
        this.dataTokoItems = dataTokoItems;
    }

    @NonNull
    @NotNull
    @Override
    public DataTokoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_toko, parent, false);
        return new DataTokoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataTokoViewHolder holder, int position) {

        holder.txtNamaToko.setText(dataTokoItems.get(position).getNamaOutlet());
        holder.txtKota.setText(dataTokoItems.get(position).getKota());

    }

    @Override
    public int getItemCount() {
        return dataTokoItems.size();
    }

    public class DataTokoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaToko, txtKota;

        public DataTokoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaToko = itemView.findViewById(R.id.text_item_nama_data_toko);
            txtKota = itemView.findViewById(R.id.text_item_kota_data_toko);
        }
    }
}
