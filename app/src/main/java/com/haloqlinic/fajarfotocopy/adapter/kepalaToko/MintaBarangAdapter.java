package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.MintaBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class MintaBarangAdapter extends RecyclerView.Adapter<MintaBarangAdapter.MintaBarangViewHolder> {

    Context context;
    List<SearchBarangByNamaItem> dataBarang;
    TambahBarangKetoActivity tambahBarangKetoActivity;
    Dialog dialog;


    public MintaBarangAdapter(Context context, List<SearchBarangByNamaItem> dataBarang, TambahBarangKetoActivity tambahBarangKetoActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.tambahBarangKetoActivity = tambahBarangKetoActivity;
    }


    @NonNull
    @Override
    public MintaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minta_barang, parent, false);
        return new MintaBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MintaBarangViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String img = dataBarang.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgMintaBarang);

        holder.txtNamaBarang.setText(dataBarang.get(position).getNamaBarang());
        holder.txtStockPcs.setText(dataBarang.get(position).getStock());
        holder.txtPack.setText(dataBarang.get(position).getJumlahPack());

        PushDownAnim.setPushDownAnimTo(holder.txtTambah)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_barang = dataBarang.get(position).getIdBarang();
                        String stock = dataBarang.get(position).getStock();
                        String jumlah_pack = dataBarang.get(position).getJumlahPack();
                    }
                });

    }




    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class MintaBarangViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMintaBarang;
        TextView txtNamaBarang, txtStockPcs, txtPack, txtTambah;

        public MintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMintaBarang = itemView.findViewById(R.id.img_item_minta_barang);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_minta_barang);
            txtStockPcs = itemView.findViewById(R.id.text_item_stock_pcs_minta_barang);
            txtPack = itemView.findViewById(R.id.text_item_stock_pack_minta_barang);
            txtTambah = itemView.findViewById(R.id.text_item_edit_cek_stock);
        }
    }
}
