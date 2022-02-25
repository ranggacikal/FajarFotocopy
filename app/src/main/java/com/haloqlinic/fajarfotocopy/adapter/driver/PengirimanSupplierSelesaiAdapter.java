package com.haloqlinic.fajarfotocopy.adapter.driver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.driver.DetailDriverActivity;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangSelesai.StatusPenjualanGudangSelesaiItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class PengirimanSupplierSelesaiAdapter extends RecyclerView.Adapter<PengirimanSupplierSelesaiAdapter.PengirimanSelesaiSupplierViewHolder> {

    Context context;
    List<StatusPenjualanGudangSelesaiItem> dataSelesai;

    public PengirimanSupplierSelesaiAdapter(Context context, List<StatusPenjualanGudangSelesaiItem> dataSelesai) {
        this.context = context;
        this.dataSelesai = dataSelesai;
    }

    @NonNull
    @Override
    public PengirimanSelesaiSupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_driver, parent, false);
        return new PengirimanSelesaiSupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengirimanSelesaiSupplierViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtTanggal.setText(dataSelesai.get(position).getTanggalPenjualan());
        holder.txtPenerima.setText(dataSelesai.get(position).getAlamatTujuan());
        holder.txtStatus.setText(dataSelesai.get(position).getStatusPengiriman());

        PushDownAnim.setPushDownAnimTo(holder.btnDetail)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_status_penjualan_gudang = dataSelesai.get(position).getIdStatusPenjualanGudang();
                        Intent intent = new Intent(context, DetailDriverActivity.class);
                        intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                        intent.putExtra("status_pengiriman", dataSelesai.get(position).getStatusPengiriman());
                        intent.putExtra("jenis_pengiriman", "supplier");
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataSelesai.size();
    }

    public class PengirimanSelesaiSupplierViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtPenerima, txtStatus;
        Button btnDetail;

        public PengirimanSelesaiSupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txt_tanggal_driver);
            txtPenerima = itemView.findViewById(R.id.txt_penerima_driver);
            btnDetail = itemView.findViewById(R.id.btn_detail_driver);
            txtStatus = itemView.findViewById(R.id.txt_status_driver);
        }
    }
}
