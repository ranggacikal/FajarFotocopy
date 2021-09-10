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
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.GetStatusPengirimanByIdUserItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class StatusPengirimanDriverAdapter extends RecyclerView.Adapter<StatusPengirimanDriverAdapter.StatusPengirimanViewHolder> {

    Context context;
    List<GetStatusPengirimanByIdUserItem> dataStatus;

    public StatusPengirimanDriverAdapter(Context context, List<GetStatusPengirimanByIdUserItem> dataStatus) {
        this.context = context;
        this.dataStatus = dataStatus;
    }

    @NonNull
    @Override
    public StatusPengirimanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_driver, parent, false);
        return new StatusPengirimanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusPengirimanViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtTanggal.setText(dataStatus.get(position).getTanggalPengiriman());
        holder.txtPenerima.setText(dataStatus.get(position).getNamaOutlet());
        holder.txtStatus.setText(dataStatus.get(position).getStatusPengiriman());

        PushDownAnim.setPushDownAnimTo(holder.btnDetail)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_status_pengiriman = dataStatus.get(position).getIdStatusPengiriman();
                        Intent intent = new Intent(context, DetailDriverActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                        intent.putExtra("status_pengiriman", dataStatus.get(position).getStatusPengiriman());
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataStatus.size();
    }

    public class StatusPengirimanViewHolder extends RecyclerView.ViewHolder {

        TextView txtTanggal, txtPenerima, txtStatus;
        Button btnDetail;

        public StatusPengirimanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txt_tanggal_driver);
            txtPenerima = itemView.findViewById(R.id.txt_penerima_driver);
            btnDetail = itemView.findViewById(R.id.btn_detail_driver);
            txtStatus = itemView.findViewById(R.id.txt_status_driver);
        }
    }
}
