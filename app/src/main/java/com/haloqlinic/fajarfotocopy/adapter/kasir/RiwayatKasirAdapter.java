package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.DetailStatusPenjualanActivity;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari.StatusPenjualanByHariItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class RiwayatKasirAdapter extends RecyclerView.Adapter<RiwayatKasirAdapter.RiwayatKasirViewHolder> {

    Context context;
    List<StatusPenjualanByHariItem> dataRiwayat;

    public RiwayatKasirAdapter(Context context, List<StatusPenjualanByHariItem> dataRiwayat) {
        this.context = context;
        this.dataRiwayat = dataRiwayat;
    }

    @NonNull
    @NotNull
    @Override
    public RiwayatKasirViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_kasir, parent, false);
        return new RiwayatKasirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RiwayatKasirViewHolder holder, int position) {

        holder.txtIdTransaksi.setText(dataRiwayat.get(position).getIdStatusPenjualan());
        holder.txtTanggal.setText(dataRiwayat.get(position).getTanggalPenjualan());
        holder.txtNamaOutlet.setText(dataRiwayat.get(position).getNamaOutlet());
        holder.txtStatus.setText(dataRiwayat.get(position).getStatusPenjualan());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailStatusPenjualanActivity.class);
                        intent.putExtra("id_status_penjualan", dataRiwayat.get(position).getIdStatusPenjualan());
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataRiwayat.size();
    }

    public class RiwayatKasirViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi,txtTanggal, txtNamaOutlet, txtStatus;

        public RiwayatKasirViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txtIdTransaksi = itemView.findViewById(R.id.text_item_id_penjualan_riwayat_kasir);
            txtNamaOutlet = itemView.findViewById(R.id.text_item_nama_outlet_riwayat_kasir);
            txtTanggal = itemView.findViewById(R.id.text_item_tanggal_riwayat_kasir);
            txtStatus = itemView.findViewById(R.id.text_item_status_riwayat_transaksi);

        }
    }
}
