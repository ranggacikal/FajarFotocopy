package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.DetailBarangStockGudangActivity;
import com.haloqlinic.fajarfotocopy.model.stockToko.GetStockByTokoItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CekStokTokoGudangAdapter extends RecyclerView.Adapter<CekStokTokoGudangAdapter.CekStokTokoGudangViewHolder> {

    Context context;
    List<GetStockByTokoItem> dataStockToko;

    public CekStokTokoGudangAdapter(Context context, List<GetStockByTokoItem> dataStockToko) {
        this.context = context;
        this.dataStockToko = dataStockToko;
    }

    @NonNull
    @NotNull
    @Override
    public CekStokTokoGudangViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cek_stok_toko_gudang, parent, false);
        return new CekStokTokoGudangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CekStokTokoGudangViewHolder holder, int position) {

        String urlImage = dataStockToko.get(position).getImageBarang();

        int modalGudang = Integer.parseInt(dataStockToko.get(position).getHargaModalGudang());
        int modalToko = Integer.parseInt(dataStockToko.get(position).getHargaModalToko());
        int jualToko = Integer.parseInt(dataStockToko.get(position).getHargaJualToko());

        Glide.with(context)
                .load(urlImage)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgCekStok);

        holder.txtNama.setText(dataStockToko.get(position).getNamaBarang());
        holder.txtModalGudang.setText("Rp" + NumberFormat.getInstance().format(modalGudang));
        holder.txtModalToko.setText("Rp" + NumberFormat.getInstance().format(modalToko));
        holder.txtJualToko.setText("Rp" + NumberFormat.getInstance().format(jualToko));
        holder.txtStock.setText(dataStockToko.get(position).getStock());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailBarangStockGudangActivity.class);
                        intent.putExtra("id_barang_outlet", dataStockToko.get(position).getIdBarangOutlet());
                        intent.putExtra("id_barang", dataStockToko.get(position).getIdBarang());
                        intent.putExtra("id_outlet", dataStockToko.get(position).getIdOutlet());
                        intent.putExtra("nama_toko", dataStockToko.get(position).getNamaOutlet());
                        intent.putExtra("nama_barang", dataStockToko.get(position).getNamaBarang());
                        intent.putExtra("jumlah_pcs", dataStockToko.get(position).getStock());
                        intent.putExtra("jumlah_pack", dataStockToko.get(position).getJumlahPack());
                        intent.putExtra("harga_jual", dataStockToko.get(position).getHargaJual());
                        intent.putExtra("harga_jual_pack", dataStockToko.get(position).getHargaJualPack());
                        intent.putExtra("diskon", dataStockToko.get(position).getDiskon());
                        intent.putExtra("diskon_pack", dataStockToko.get(position).getDiskonPack());
                        context.startActivity(intent);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return dataStockToko.size();
    }

    public class CekStokTokoGudangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCekStok;
        TextView txtNama, txtModalGudang, txtModalToko, txtJualToko, txtStock;

        public CekStokTokoGudangViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgCekStok = itemView.findViewById(R.id.img_item_cek_stok_toko_gudang);
            txtNama = itemView.findViewById(R.id.text_item_nama_cek_stok_toko_gudang);
            txtModalGudang = itemView.findViewById(R.id.text_item_harga_modal_gudang_cek_stok_toko_gudang);
            txtModalToko = itemView.findViewById(R.id.text_item_harga_modal_toko_cek_stok_toko_gudang);
            txtJualToko = itemView.findViewById(R.id.text_item_harga_jual_toko_cek_stok_toko_gudang);
            txtStock = itemView.findViewById(R.id.text_item_stock_cek_stok_toko_gudang);
        }
    }
}
