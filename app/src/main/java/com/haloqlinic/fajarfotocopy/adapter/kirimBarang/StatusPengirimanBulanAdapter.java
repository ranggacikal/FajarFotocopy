package com.haloqlinic.fajarfotocopy.adapter.kirimBarang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.databinding.ItemReportStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.ListStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.WebViewReportPengirimanActivity;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByBulan.GetStatusPengirimanByBulanItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class StatusPengirimanBulanAdapter extends
        RecyclerView.Adapter<StatusPengirimanBulanAdapter.MyViewHolder> {

    Context context;
    List<GetStatusPengirimanByBulanItem> statusPengirimanList;
    ListStatusPengirimanActivity listStatusPengirimanActivity;

    public StatusPengirimanBulanAdapter(Context context,
                                        List<GetStatusPengirimanByBulanItem> statusPengirimanList,
                                        ListStatusPengirimanActivity listStatusPengirimanActivity) {
        this.context = context;
        this.statusPengirimanList = statusPengirimanList;
        this.listStatusPengirimanActivity = listStatusPengirimanActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReportStatusPengirimanBinding bindingItem = ItemReportStatusPengirimanBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyViewHolder(bindingItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.textTanggalPengirimanItemReport
                .setText(statusPengirimanList.get(position).getTanggalPengiriman());
        holder.binding.textIdStatusPengirimanItemReport
                .setText(statusPengirimanList.get(position).getIdStatusPengiriman());
        holder.binding.textStatusPengirimanItemReport
                .setText(statusPengirimanList.get(position).getStatusPengiriman());
        holder.binding.textNamaOutletItemReport
                .setText(statusPengirimanList.get(position).getNamaOutlet());
        holder.binding.textIdOutletItemReport
                .setText(statusPengirimanList.get(position).getIdOutlet());
        holder.binding.textNamaDriverItemReport
                .setText(statusPengirimanList.get(position).getNamaLengkap());
        holder.binding.textIdDriverItemReport
                .setText(statusPengirimanList.get(position).getDriverId());

        PushDownAnim.setPushDownAnimTo(holder.binding.btnLihatInvoiceItemReport)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewReportPengirimanActivity.class);
                        intent.putExtra("bulan_tahun", listStatusPengirimanActivity.bulan_tahun);
                        intent.putExtra("id_status_pengiriman",
                                statusPengirimanList.get(position).getIdStatusPengiriman());
                        intent.putExtra("pilihan", "Bulan");
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return statusPengirimanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemReportStatusPengirimanBinding binding;

        public MyViewHolder(@NonNull ItemReportStatusPengirimanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
