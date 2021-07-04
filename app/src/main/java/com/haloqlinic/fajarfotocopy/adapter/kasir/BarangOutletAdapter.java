package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class BarangOutletAdapter extends RecyclerView.Adapter<BarangOutletAdapter.BarangOutletViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> cariBarangOutlet;

    public BarangOutletAdapter(Context context, List<SearchBarangOutletByNamaItem> cariBarangOutlet) {
        this.context = context;
        this.cariBarangOutlet = cariBarangOutlet;
    }

    @NonNull
    @NotNull
    @Override
    public BarangOutletViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new BarangOutletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BarangOutletViewHolder holder, int position) {

        int hargaPcs = Integer.parseInt(cariBarangOutlet.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(cariBarangOutlet.get(position).getHargaJualPack());

        holder.txtNama.setText(cariBarangOutlet.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = holder.numberPicker.getNumber();
                int stock = Integer.parseInt(cariBarangOutlet.get(position).getStock());
                if (number.equals("0")){
                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("1");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }else{
                    int total = Integer.parseInt(number) * Integer.parseInt(cariBarangOutlet.get(position).getHargaJual());
                    Log.d("testTotal", "number: "+number+" harga: "+cariBarangOutlet.get(position).getHargaJual()+" total: "+total);
                }

            }
        });

        PushDownAnim.setPushDownAnimTo(holder.btnTambahPesanan)
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
        return cariBarangOutlet.size();
    }

    public class BarangOutletViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan;

        public BarangOutletViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            numberPicker = itemView.findViewById(R.id.elegant_nb_item_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
        }
    }
}
