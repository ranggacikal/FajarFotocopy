package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.HasilPencarianTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
import com.haloqlinic.fajarfotocopy.model.tambahTransferBarang.ResponseTambahTransferBarang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariTransferBarangAdapter extends RecyclerView.Adapter<CariTransferBarangAdapter.CariTransferBarangViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> dataBarang;
    HasilPencarianTransferBarangGudangActivity hasilActivity;

    String number ="";
    String jumlah_pack = "";
    String id_barang_outlet_pengirim = "";
    String id_barang = "";

    public CariTransferBarangAdapter(Context context, List<SearchBarangOutletByNamaItem> dataBarang,
                                     HasilPencarianTransferBarangGudangActivity hasilActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.hasilActivity = hasilActivity;
    }

    @NonNull
    @NotNull
    @Override
    public CariTransferBarangViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariTransferBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariTransferBarangViewHolder holder, int position) {

        int hargaPcs = Integer.parseInt(dataBarang.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(dataBarang.get(position).getHargaJualPack());
        id_barang_outlet_pengirim = dataBarang.get(position).getIdBarangOutlet();
        id_barang = dataBarang.get(position).getIdBarang();


        String img = dataBarang.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .into(holder.imgBarang);

        holder.txtNama.setText(dataBarang.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));

        holder.numberPicker.setNumber("1");

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = holder.numberPicker.getNumber();
                int stock = Integer.parseInt(dataBarang.get(position).getStock());
                if (number.equals("0")){
                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("1");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }

            }
        });

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

        holder.btnTambahPesanan.setVisibility(View.GONE);
        holder.btnTambahBarang.setVisibility(View.VISIBLE);
        holder.edtJumlahPack.setVisibility(View.VISIBLE);

        PushDownAnim.setPushDownAnimTo(holder.btnTambahBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumlah_pack = holder.edtJumlahPack.getText().toString();

                        if (jumlah_pack.isEmpty()){

                            holder.edtJumlahPack.setError("Jumlah Pack Tidak boleh kosong");
                            holder.edtJumlahPack.requestFocus();
                            return;
                        }

                        tambahPesanan(jumlah_pack);
                    }
                });
    }

    private void tambahPesanan(String jumlah_pack) {


        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_transfer_barang = "TB"+randomId;

        String id_status_transfer = hasilActivity.id_status_transfer;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("menambahkan barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahTransferBarang(id_transfer_barang, id_barang_outlet_pengirim, id_barang,
                String.valueOf(number), jumlah_pack, id_status_transfer, "pending")
                .enqueue(new Callback<ResponseTambahTransferBarang>() {
                    @Override
                    public void onResponse(Call<ResponseTambahTransferBarang> call, Response<ResponseTambahTransferBarang> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            String pesan = response.body().getPesan();

                            if (status==1){

                                Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                                hasilActivity.getCount();

                            }else{
                                Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Gagal Menambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahTransferBarang> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class CariTransferBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan, btnTambahBarang;
        EditText edtJumlahPack;

        public CariTransferBarangViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            numberPicker = itemView.findViewById(R.id.elegant_nb_item_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            btnTambahBarang = itemView.findViewById(R.id.btn_tambah_barang_transfer);
            edtJumlahPack = itemView.findViewById(R.id.edt_item_jumlah_pack_transfer);
        }
    }
}