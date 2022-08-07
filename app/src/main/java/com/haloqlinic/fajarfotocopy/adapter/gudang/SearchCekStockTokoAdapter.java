package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.annotation.SuppressLint;
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
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.SearchStockByTokoItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class SearchCekStockTokoAdapter extends RecyclerView.Adapter<SearchCekStockTokoAdapter.SearchCekStokTokoViewHolder> {

    Context context;
    List<SearchStockByTokoItem> dataSearchStock;

    public SearchCekStockTokoAdapter(Context context, List<SearchStockByTokoItem> dataSearchStock) {
        this.context = context;
        this.dataSearchStock = dataSearchStock;
    }

    @NonNull
    @NotNull
    @Override
    public SearchCekStokTokoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cek_stok_toko_gudang, parent, false);
        return new SearchCekStokTokoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchCekStokTokoViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        String urlImage = dataSearchStock.get(position).getImageBarang();

        int modalGudang = Integer.parseInt(dataSearchStock.get(position).getHargaModalGudang());
        int modalToko = Integer.parseInt(dataSearchStock.get(position).getHargaModalToko());
        int jualToko = Integer.parseInt(dataSearchStock.get(position).getHargaJualToko());

        Glide.with(context)
                .load(urlImage)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgCekStok);

        holder.txtNama.setText(dataSearchStock.get(position).getNamaBarang());
        holder.txtModalGudang.setText("Rp" + NumberFormat.getInstance().format(modalGudang));
        holder.txtModalToko.setText("Rp" + NumberFormat.getInstance().format(modalToko));
        holder.txtJualToko.setText("Rp" + NumberFormat.getInstance().format(jualToko));
        holder.txtStock.setText(dataSearchStock.get(position).getStock());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailBarangStockGudangActivity.class);
                        intent.putExtra("id_barang_outlet", dataSearchStock.get(position).getIdBarangOutlet());
                        intent.putExtra("id_barang", dataSearchStock.get(position).getIdBarang());
                        intent.putExtra("id_outlet", dataSearchStock.get(position).getIdOutlet());
                        intent.putExtra("nama_toko", dataSearchStock.get(position).getNamaOutlet());
                        intent.putExtra("nama_barang", dataSearchStock.get(position).getNamaBarang());
                        intent.putExtra("jumlah_pcs", dataSearchStock.get(position).getStock());
                        intent.putExtra("jumlah_pack", dataSearchStock.get(position).getJumlahPack());
                        intent.putExtra("harga_jual", dataSearchStock.get(position).getHargaJualToko());
                        intent.putExtra("harga_jual_pack", dataSearchStock.get(position).getHargaJualPack());
                        intent.putExtra("diskon", dataSearchStock.get(position).getDiskon());
                        intent.putExtra("diskon_pack", dataSearchStock.get(position).getDiskonPack());
                        intent.putExtra("number_of_pack", dataSearchStock.get(position).getNumberOfPack());
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataSearchStock.size();
    }

    public class SearchCekStokTokoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCekStok;
        TextView txtNama, txtModalGudang, txtModalToko, txtJualToko, txtStock;

        public SearchCekStokTokoViewHolder(@NonNull @NotNull View itemView) {
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
