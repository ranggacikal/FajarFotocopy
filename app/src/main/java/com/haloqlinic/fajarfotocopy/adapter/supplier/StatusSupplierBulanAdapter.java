package com.haloqlinic.fajarfotocopy.adapter.supplier;

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
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.WebViewReportPengirimanActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ListTransaksiSupplierActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.WebViewReportPenjualanGudangActivity;
import com.haloqlinic.fajarfotocopy.model.statusSupplierBulan.StatusPenjualanGudangByBulanItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class StatusSupplierBulanAdapter extends RecyclerView.Adapter<StatusSupplierBulanAdapter.MyViewHolder> {

    Context context;
    List<StatusPenjualanGudangByBulanItem> dataPenjualan;
    ListTransaksiSupplierActivity listTransaksiSupplierActivity;

    public StatusSupplierBulanAdapter(Context context,
                                      List<StatusPenjualanGudangByBulanItem> dataPenjualan,
                                      ListTransaksiSupplierActivity listTransaksiSupplierActivity) {
        this.context = context;
        this.dataPenjualan = dataPenjualan;
        this.listTransaksiSupplierActivity = listTransaksiSupplierActivity;
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
                .setText(dataPenjualan.get(position).getTanggalPenjualan());
        holder.binding.textIdStatusPengirimanItemReport
                .setText(dataPenjualan.get(position).getIdStatusPenjualanGudang());
        holder.binding.textStatusPengirimanItemReport
                .setText(dataPenjualan.get(position).getStatusPengiriman());
        holder.binding.textNamaOutletItemReport
                .setText(dataPenjualan.get(position).getAlamatTujuan());
        holder.binding.textIdOutletItemReport.setVisibility(View.GONE);
        holder.binding.textNamaDriverItemReport
                .setText(dataPenjualan.get(position).getNamaLengkap());
        holder.binding.textIdDriverItemReport
                .setText(dataPenjualan.get(position).getDriverId());

        PushDownAnim.setPushDownAnimTo(holder.binding.btnLihatInvoiceItemReport)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewReportPenjualanGudangActivity.class);
                        intent.putExtra("bulan_tahun", listTransaksiSupplierActivity.bulan_tahun);
                        intent.putExtra("id_status_penjualan_gudang",
                                dataPenjualan.get(position).getIdStatusPenjualanGudang());
                        intent.putExtra("pilihan", "Bulan");
                        context.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataPenjualan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemReportStatusPengirimanBinding binding;
        public MyViewHolder(@NonNull ItemReportStatusPengirimanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
