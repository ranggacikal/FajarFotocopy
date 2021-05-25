package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.DataPengirimanBarangActivity;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.DataStatusPengirimanItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ReportPengirimanAdapter extends RecyclerView.Adapter<ReportPengirimanAdapter.ReportPengirimanViewHolder> {

    Context context;
    List<DataStatusPengirimanItem> dataStatusPengiriman;

    public ReportPengirimanAdapter(Context context, List<DataStatusPengirimanItem> dataStatusPengiriman) {
        this.context = context;
        this.dataStatusPengiriman = dataStatusPengiriman;
    }

    @NonNull
    @NotNull
    @Override
    public ReportPengirimanViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_pengiriman, parent, false);
        return new ReportPengirimanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReportPengirimanViewHolder holder, int position) {

        String id_status = dataStatusPengiriman.get(position).getIdStatusPengiriman();
        String tanggal = dataStatusPengiriman.get(position).getTanggalPengiriman();
        String nama_toko = dataStatusPengiriman.get(position).getNamaOutlet();
        String status = dataStatusPengiriman.get(position).getStatusPengiriman();

        holder.txtIdStatus.setText(id_status);
        holder.txtNamaToko.setText(nama_toko);
        holder.txtTanggal.setText(tanggal);
        holder.txtStatus.setText(status);

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DataPengirimanBarangActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status);
                        intent.putExtra("tanggal_status_pengiriman", tanggal);
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataStatusPengiriman.size();
    }

    public class ReportPengirimanViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdStatus, txtTanggal, txtNamaToko, txtStatus;

        public ReportPengirimanViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtIdStatus = itemView.findViewById(R.id.text_id_status_report_pengiriman);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_report_pengiriman);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_report_pengiriman);
            txtStatus = itemView.findViewById(R.id.text_status_report_pengiriman);
        }
    }
}
