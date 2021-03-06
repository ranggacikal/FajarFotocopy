package com.haloqlinic.fajarfotocopy.adapter.supplier;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.databinding.ItemReportStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.WebViewReportPengirimanActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.LihatBuktiBayarActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ListTransaksiSupplierActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.WebViewReportPenjualanGudangActivity;
import com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal.StatusPenjualanGudangByTanggalItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class StatusSupplierTanggalAdapter extends RecyclerView.Adapter<StatusSupplierTanggalAdapter.
        MyViewHolder> {

    Context context;
    ListTransaksiSupplierActivity listTransaksiSupplierActivity;
    List<StatusPenjualanGudangByTanggalItem> dataPenjualan;

    public StatusSupplierTanggalAdapter(Context context,
                                        ListTransaksiSupplierActivity listTransaksiSupplierActivity,
                                        List<StatusPenjualanGudangByTanggalItem> dataPenjualan) {
        this.context = context;
        this.listTransaksiSupplierActivity = listTransaksiSupplierActivity;
        this.dataPenjualan = dataPenjualan;
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

        String memtode_bayar = dataPenjualan.get(position).getMetodeBayar();

        if (memtode_bayar.equals("Debit")){
            holder.binding.btnLihatBuktiBayarItemReport.setVisibility(View.VISIBLE);
        }else{
            holder.binding.btnLihatBuktiBayarItemReport.setVisibility(View.GONE);
        }

        PushDownAnim.setPushDownAnimTo(holder.binding.btnLihatBuktiBayarItemReport)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LihatBuktiBayarActivity.class);
                        intent.putExtra("image_bukti", dataPenjualan.get(position).getImageBuktiTf());
                        Log.d("cekImageBukti", "onClick: "+dataPenjualan.get(position).getImageBuktiTf());
                        context.startActivity(intent);
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.binding.btnLihatInvoiceItemReport)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewReportPenjualanGudangActivity.class);
                        intent.putExtra("tanggal", listTransaksiSupplierActivity.tanggal);
                        intent.putExtra("id_status_penjualan_gudang",
                                dataPenjualan.get(position).getIdStatusPenjualanGudang());
                        intent.putExtra("pilihan", "Hari");
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

        public MyViewHolder(ItemReportStatusPengirimanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
