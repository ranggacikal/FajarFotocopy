package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.editPackBarang.ResponseEditPackBarang;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariBarangIdPenjualanAdapter extends RecyclerView.Adapter<
        CariBarangIdPenjualanAdapter.CariBarangIdPenjualanViewHolder> {

    Context context;
    List<SearchBarangByIdItem> dataBarang;
    SupplierGudangActivity supplierGudangActivity;

    String number;
    int total, edit_pack, jumlah_qty;

    public CariBarangIdPenjualanAdapter(Context context, List<SearchBarangByIdItem> dataBarang, SupplierGudangActivity supplierGudangActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.supplierGudangActivity = supplierGudangActivity;
    }

    @NonNull
    @Override
    public CariBarangIdPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariBarangIdPenjualanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CariBarangIdPenjualanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int hargaPcs = Integer.parseInt(dataBarang.get(position).getHargaModalToko());
        int hargaPack = Integer.parseInt(dataBarang.get(position).getHargaModalTokoPack());

        holder.txtNama.setText(dataBarang.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.edtJumlahPack.setVisibility(View.VISIBLE);
        holder.edtJumlahPack.setEnabled(false);

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = holder.numberPicker.getNumber();
                int number_of_pack = Integer.parseInt(dataBarang.get(position).getNumberOfPack());
                int stock_db = Integer.parseInt(dataBarang.get(position).getStock());
                int jumlah_kurang = 0;
                int stock = Integer.parseInt(dataBarang.get(position).getStock());
                if (number.equals("0")){
                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("1");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }else{
                    jumlah_qty = Integer.parseInt(number) * number_of_pack;
                    holder.edtJumlahPack.setText(String.valueOf(jumlah_qty));
                    total = jumlah_qty * Integer.parseInt(dataBarang.get(position).getHargaModalToko());
                    jumlah_kurang = stock_db - jumlah_qty;
                    edit_pack = jumlah_kurang/number_of_pack;
                    Log.d("testTotal", "number: "+number+" harga: "+dataBarang.get(position).getHargaModalToko()+" total: "+total);
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

        PushDownAnim.setPushDownAnimTo(holder.btnTambahPesanan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (number!=null) {

                            if (number.equals("0")) {
                                Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        String jumlah_pack = holder.edtJumlahPack.getText().toString();

                        Log.d("cekJumlahPackSupplier", "onClick: "+jumlah_pack);

                        String id_status_penjualan = supplierGudangActivity.id_status_penjualan_gudang;
                        String id_barang = dataBarang.get(position).getIdBarang();
                        tambahPenjualanGudang(id_status_penjualan, id_barang, number);

                    }
                });
    }

    private void tambahPenjualanGudang(String id_status_penjualan, String id_barang, String jumlah_pack) {
        Log.d("cekJumlahPackSupplier", "onClick: "+jumlah_pack);

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPenjualanGudang(id_barang, number, jumlah_pack, String.valueOf(total),
                supplierGudangActivity.nama_user, id_status_penjualan)
                .enqueue(new Callback<ResponseTambahPenjualan>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPenjualan> call, Response<ResponseTambahPenjualan> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(context, "Berhasil Menambahkan Barang",
                                        Toast.LENGTH_SHORT).show();
                                editPack(id_barang);

                            }else{
                                Toast.makeText(context, "Gagal Menambahkan, Silahkan coba lagi",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPenjualan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void editPack(String id_barang) {

        ConfigRetrofit.service.editPackBarang(id_barang, String.valueOf(edit_pack))
                .enqueue(new Callback<ResponseEditPackBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditPackBarang> call, Response<ResponseEditPackBarang> response) {
                        if (response.isSuccessful()){

                            int status = response.body().getStatus();

                            if (status==1){
                                Toast.makeText(context, "Berhasil Edit Pack", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Gagal Edit Pack", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(context, "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditPackBarang> call, Throwable t) {
                        Toast.makeText(context, "koneksi error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class CariBarangIdPenjualanViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan;
        EditText edtJumlahPack;
        public CariBarangIdPenjualanViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            numberPicker = itemView.findViewById(R.id.elegant_nb_item_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            edtJumlahPack = itemView.findViewById(R.id.edt_jumlah_pcs_item_barang);
        }
    }
}
