package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.DataPertokoGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

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
    public void onBindViewHolder(@NonNull @NotNull DataTokoViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtNamaToko.setText(dataTokoItems.get(position).getNamaOutlet());
        holder.txtKota.setText(dataTokoItems.get(position).getKota());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DataPertokoGudangActivity.class);
                        intent.putExtra("id_outlet", dataTokoItems.get(position).getIdOutlet());
                        intent.putExtra("nama_outlet", dataTokoItems.get(position).getNamaOutlet());
                        intent.putExtra("kota", dataTokoItems.get(position).getKota());
                        intent.putExtra("persentase", dataTokoItems.get(position).getPersentase());
                        intent.putExtra("gaji", dataTokoItems.get(position).getGaji());
                        intent.putExtra("jumlah_anggota", dataTokoItems.get(position).getJumlahAnggota());
                        intent.putExtra("alamat", dataTokoItems.get(position).getAlamat());
                        context.startActivity(intent);
                    }
                });

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
