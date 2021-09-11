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
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.StatusPengirimanSelesaiItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class PengirimanSelesaiAdapter extends RecyclerView.Adapter<PengirimanSelesaiAdapter.PengirimanSelesaiViewHolder> {

    Context context;
    List<StatusPengirimanSelesaiItem> dataSelesai;

    public PengirimanSelesaiAdapter(Context context, List<StatusPengirimanSelesaiItem> dataSelesai) {
        this.context = context;
        this.dataSelesai = dataSelesai;
    }

    @NonNull
    @Override
    public PengirimanSelesaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_driver, parent, false);
        return new PengirimanSelesaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengirimanSelesaiViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtTanggal.setText(dataSelesai.get(position).getTanggalPengiriman());
        holder.txtPenerima.setText(dataSelesai.get(position).getNamaOutlet());
        holder.txtStatus.setText(dataSelesai.get(position).getStatusPengiriman());

        PushDownAnim.setPushDownAnimTo(holder.btnDetail)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_status_pengiriman = dataSelesai.get(position).getIdStatusPengiriman();
                        Intent intent = new Intent(context, DetailDriverActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                        intent.putExtra("status_pengiriman", dataSelesai.get(position).getStatusPengiriman());
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataSelesai.size();
    }

    public class PengirimanSelesaiViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtPenerima, txtStatus;
        Button btnDetail;
        public PengirimanSelesaiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txt_tanggal_driver);
            txtPenerima = itemView.findViewById(R.id.txt_penerima_driver);
            btnDetail = itemView.findViewById(R.id.btn_detail_driver);
            txtStatus = itemView.findViewById(R.id.txt_status_driver);
        }
    }
}
