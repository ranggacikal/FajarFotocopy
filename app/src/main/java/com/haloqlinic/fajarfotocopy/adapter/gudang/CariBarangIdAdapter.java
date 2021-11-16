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
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DetailDataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariBarangIdAdapter extends RecyclerView.Adapter<CariBarangIdAdapter.CariBarangIdViewHolder> {

    Context context;
    List<SearchBarangByIdItem> dataBarang;

    public CariBarangIdAdapter(Context context, List<SearchBarangByIdItem> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @NotNull
    @Override
    public CariBarangIdViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang, parent, false);
        return new CariBarangIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariBarangIdViewHolder holder, int position) {

        String id_barang = dataBarang.get(position).getIdBarang();
        String nama_barang = dataBarang.get(position).getNamaBarang();
        String stock_pcs = dataBarang.get(position).getStock();
        String modal_gudang_pcs = dataBarang.get(position).getHargaModalGudang();
        String modal_toko = dataBarang.get(position).getHargaModalToko();
        String jual_toko = dataBarang.get(position).getHargaJualToko();
        String modal_gudang_pack = dataBarang.get(position).getHargaModalGudangPack();
        String modal_toko_pack = dataBarang.get(position).getHargaModalTokoPack();
        String jual_toko_pack = dataBarang.get(position).getHargaJualTokoPack();
        String asal_barang = dataBarang.get(position).getAsalBarang();
        String stock_pack = dataBarang.get(position).getJumlahPack();
        String diskon = dataBarang.get(position).getDiskon();
        String diskon_pack = dataBarang.get(position).getDiskonPack();
        String image = dataBarang.get(position).getImageBarang();
        String id_kategori = dataBarang.get(position).getIdKategoriBarang();
        String number_of_pack = dataBarang.get(position).getNumberOfPack();

        Glide.with(context)
                .load(image)
                .error(R.drawable.icon_img_error)
                .into(holder.imgBarang);

        holder.txtNamaBarang.setText(nama_barang);
        holder.txtModalGudangPcs.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(modal_gudang_pcs)));
        holder.txtModalGudangPack.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(modal_gudang_pack)));
        holder.txtStockPcs.setText(stock_pcs);
        holder.txtStockPack.setText(stock_pack);

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailDataBarangGudangActivity.class);
                        intent.putExtra("id_barang", id_barang);
                        intent.putExtra("nama_barang", nama_barang);
                        intent.putExtra("stock", stock_pcs);
                        intent.putExtra("harga_modal_gudang", modal_gudang_pcs);
                        intent.putExtra("harga_modal_toko", modal_toko);
                        intent.putExtra("harga_jual_toko", jual_toko);
                        intent.putExtra("harga_modal_gudang_pack", modal_gudang_pack);
                        intent.putExtra("harga_modal_toko_pack", modal_toko_pack);
                        intent.putExtra("harga_jual_toko_pack", jual_toko_pack);
                        intent.putExtra("asal_barang", asal_barang);
                        intent.putExtra("jumlah_pack", stock_pack);
                        intent.putExtra("diskon", diskon);
                        intent.putExtra("diskon_pack", diskon_pack);
                        intent.putExtra("number_of_pack", number_of_pack);
                        intent.putExtra("image", image);
                        intent.putExtra("id_kategori", id_kategori);
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class CariBarangIdViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaBarang, txtModalGudangPcs, txtModalGudangPack, txtStockPcs, txtStockPack;
        ImageView imgBarang;

        public CariBarangIdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_barang_gudang);
            txtModalGudangPcs = itemView.findViewById(R.id.text_item_harga_modal_pcs_barang_gudang);
            txtModalGudangPack = itemView.findViewById(R.id.text_item_harga_modal_pack_barang_gudang);
            txtStockPcs = itemView.findViewById(R.id.text_item_stock_pcs_barang_gudang);
            txtStockPack = itemView.findViewById(R.id.text_item_stock_pack_barang_gudang);
            imgBarang = itemView.findViewById(R.id.img_item_barang_gudang);
        }
    }
}
