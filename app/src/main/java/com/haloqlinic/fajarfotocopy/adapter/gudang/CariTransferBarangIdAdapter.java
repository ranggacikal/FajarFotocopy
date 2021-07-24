package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariTransferBarangIdAdapter extends RecyclerView.Adapter<CariTransferBarangIdAdapter.CariTransferBarangIdViewHolder> {

    Context context;
    List<SearchBarangOutletByIdItem> dataBarangId;

    String number = "";

    public CariTransferBarangIdAdapter(Context context, List<SearchBarangOutletByIdItem> dataBarangId) {
        this.context = context;
        this.dataBarangId = dataBarangId;
    }

    @NonNull
    @NotNull
    @Override
    public CariTransferBarangIdViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariTransferBarangIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariTransferBarangIdViewHolder holder, int position) {
        int hargaPcs = Integer.parseInt(dataBarangId.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(dataBarangId.get(position).getHargaJualPack());

        String img = dataBarangId.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataBarangId.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));

        holder.numberPicker.setNumber("1");

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = holder.numberPicker.getNumber();
                int stock = Integer.parseInt(dataBarangId.get(position).getStock());
                if (number.equals("0")){
                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("1");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }

            }
        });

        holder.btnTambahPesanan.setVisibility(View.GONE);
        holder.btnTambahBarang.setVisibility(View.VISIBLE);


        PushDownAnim.setPushDownAnimTo(holder.btnTambahBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataBarangId.size();
    }

    public class CariTransferBarangIdViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan, btnTambahBarang;

        public CariTransferBarangIdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            numberPicker = itemView.findViewById(R.id.elegant_nb_item_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            btnTambahBarang = itemView.findViewById(R.id.btn_tambah_barang_transfer);
        }
    }
}
