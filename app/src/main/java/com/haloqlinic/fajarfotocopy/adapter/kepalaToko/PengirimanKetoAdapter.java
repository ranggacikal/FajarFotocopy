package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.DetailPengirimanKetoActivity;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko.ListPengirimanByOutletItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class PengirimanKetoAdapter extends RecyclerView.Adapter<PengirimanKetoAdapter.PengirimanKetoViewHolder> {

    Context context;
    List<ListPengirimanByOutletItem> dataPengiriman;

    public PengirimanKetoAdapter(Context context, List<ListPengirimanByOutletItem> dataPengiriman) {
        this.context = context;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @NotNull
    @Override
    public PengirimanKetoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pengiriman_keto, parent, false);
        return new PengirimanKetoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PengirimanKetoViewHolder holder, int position) {

        String id_status_pengiriman = dataPengiriman.get(position).getIdStatusPengiriman();
        String tanggal = dataPengiriman.get(position).getTanggalPengiriman();

        holder.txtTanggal.setText(tanggal);

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, DetailPengirimanKetoActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                        intent.putExtra("tanggal", tanggal);
                        Log.d("cekListPengiriman", "id: "+id_status_pengiriman);
                        Log.d("cekListPengiriman", "tanggal: "+tanggal);
                        context.startActivity(intent);

                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.btnDetail)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, DetailPengirimanKetoActivity.class);
                        intent.putExtra("id_status_pengiriman", id_status_pengiriman);
                        intent.putExtra("tanggal", tanggal);
                        context.startActivity(intent);

                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataPengiriman.size();
    }

    public class PengirimanKetoViewHolder extends RecyclerView.ViewHolder {

        TextView txtTanggal;
        Button btnDetail;

        public PengirimanKetoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_pengiriman_keto);
            btnDetail = itemView.findViewById(R.id.btn_detail_pengiriman_keto);
        }
    }
}
