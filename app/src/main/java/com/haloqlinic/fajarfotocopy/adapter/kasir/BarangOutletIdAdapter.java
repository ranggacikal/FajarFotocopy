package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.app.ProgressDialog;
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
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.SearchBarangOutletByIdItem;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class BarangOutletIdAdapter extends RecyclerView.Adapter<BarangOutletIdAdapter.BarangOutletIdViewHolder> {

    Context context;
    List<SearchBarangOutletByIdItem> dataCari;
    TransaksiKasirActivity transaksiKasirActivity;

    public BarangOutletIdAdapter(Context context, List<SearchBarangOutletByIdItem> dataCari, TransaksiKasirActivity transaksiKasirActivity) {
        this.context = context;
        this.dataCari = dataCari;
        this.transaksiKasirActivity = transaksiKasirActivity;
    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    int total;
    String number;

    @NonNull
    @NotNull
    @Override
    public BarangOutletIdViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new BarangOutletIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BarangOutletIdViewHolder holder, int position) {

        int hargaPcs = Integer.parseInt(dataCari.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(dataCari.get(position).getHargaJualPack());

        holder.txtNama.setText(dataCari.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = holder.numberPicker.getNumber();
                int stock = Integer.parseInt(dataCari.get(position).getStock());
                if (number.equals("0")){
                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("1");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }else{
                    total = Integer.parseInt(number) * Integer.parseInt(dataCari.get(position).getHargaJual());
                    Log.d("testTotal", "number: "+number+" harga: "+dataCari.get(position).getHargaJual()+" total: "+total);
                }

            }
        });

        PushDownAnim.setPushDownAnimTo(holder.btnTambahPesanan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_barang_outlet = dataCari.get(position).getIdBarangOutlet();
                        String id_barang = dataCari.get(position).getIdBarang();
                        String id_status_penjualan = transaksiKasirActivity.id_status_penjualan;
                        tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang);

                    }
                });

    }

    private void tambahPenjualan(String id_barang_outlet, String id_status_penjualan, String id_barang) {

        preferencedConfig = new SharedPreferencedConfig(context);

        Random rnd = new Random();
        int numberRnd = rnd.nextInt(999999);

        String randomId = String.format("%06d", numberRnd);
        String id_penjualan = "FEG"+randomId;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        String tanggal = date;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahPenjualan(id_penjualan, id_barang_outlet, id_barang, number, String.valueOf(total),
                tanggal, preferencedConfig.getPreferenceNama(), id_status_penjualan).enqueue(new Callback<ResponseTambahPenjualan>() {
            @Override
            public void onResponse(Call<ResponseTambahPenjualan> call, Response<ResponseTambahPenjualan> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1){
                        Toast.makeText(context, "Berhasil menambah barang", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Gagal Menambah barang", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Gagal Saat menambahkan barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahPenjualan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataCari.size();
    }

    public class BarangOutletIdViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan;

        public BarangOutletIdViewHolder(@NonNull @NotNull View itemView) {
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
