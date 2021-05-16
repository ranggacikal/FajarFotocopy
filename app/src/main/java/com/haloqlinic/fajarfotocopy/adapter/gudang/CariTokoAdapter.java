package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.cariToko.DataCariTokoItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CariTokoAdapter extends RecyclerView.Adapter<CariTokoAdapter.CariTokoViewHolder> {

    Context context;
    List<DataCariTokoItem> dataCariToko;

    public CariTokoAdapter(Context context, List<DataCariTokoItem> dataCariToko) {
        this.context = context;
        this.dataCariToko = dataCariToko;
    }

    @NonNull
    @NotNull
    @Override
    public CariTokoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_toko, parent, false);
        return new CariTokoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariTokoViewHolder holder, int position) {

        holder.txtNamaToko.setText(dataCariToko.get(position).getNamaOutlet());
        holder.txtKota.setText(dataCariToko.get(position).getKota());

    }

    @Override
    public int getItemCount() {
        return dataCariToko.size();
    }

    public class CariTokoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaToko, txtKota;

        public CariTokoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaToko = itemView.findViewById(R.id.text_item_nama_data_toko);
            txtKota = itemView.findViewById(R.id.text_item_kota_data_toko);
        }
    }
}
