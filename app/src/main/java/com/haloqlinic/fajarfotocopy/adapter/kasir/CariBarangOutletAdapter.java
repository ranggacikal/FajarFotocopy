package com.haloqlinic.fajarfotocopy.adapter.kasir;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.KeranjangSupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.PembayaranKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.editJumlahPackOutlet.ResponseEditJumlahPackOutlet;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.SearchBarangOutletByNamaItem;
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

public class CariBarangOutletAdapter extends RecyclerView.Adapter<CariBarangOutletAdapter.CariBarangOutletViewHolder> {

    Context context;
    List<SearchBarangOutletByNamaItem> cariBarangOutlet;
    TransaksiKasirActivity transaksiKasirActivity;

    public CariBarangOutletAdapter(Context context, List<SearchBarangOutletByNamaItem> cariBarangOutlet, TransaksiKasirActivity transaksiKasirActivity) {
        this.context = context;
        this.cariBarangOutlet = cariBarangOutlet;
        this.transaksiKasirActivity = transaksiKasirActivity;
    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    int total;
    String number;
    public String id_status_penjualan_outlet;


    @NonNull
    @NotNull
    @Override
    public CariBarangOutletViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_outlet, parent, false);
        return new CariBarangOutletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariBarangOutletViewHolder holder, @SuppressLint("RecyclerView") int position) {


        int hargaPcs = Integer.parseInt(cariBarangOutlet.get(position).getHargaJual());
        int hargaPack = Integer.parseInt(cariBarangOutlet.get(position).getHargaJualPack());

        holder.txtNama.setText(cariBarangOutlet.get(position).getNamaBarang());
        holder.txtHargaPcs.setText("Rp" + NumberFormat.getInstance().format(hargaPcs));
        holder.txtHargaPack.setText("Rp" + NumberFormat.getInstance().format(hargaPack));
        holder.lblJumlahPack.setVisibility(View.GONE);
        holder.btnTambahPesanan.setEnabled(false);

        holder.numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = holder.numberPicker.getNumber();
                int stock = Integer.parseInt(cariBarangOutlet.get(position).getStock());
                int jumlah_satuan_pack = Integer.parseInt(cariBarangOutlet.get(position).getNumberOfPack());

                if (jumlah_satuan_pack == 0 || jumlah_satuan_pack<0){
                    Toast.makeText(context, "Jumlah satuan dalam pack di data ini adalah 0, " +
                                    "silahkan hubungi bagian gudang untuk edit Data ini",
                            Toast.LENGTH_LONG).show();
                    holder.numberPicker.setNumber("0");
                }else if (number.equals("0")){
//                    Toast.makeText(context, "Tidak Boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber("0");
                }else if (Integer.parseInt(number) > stock ){
                    Toast.makeText(context, "Stock Tidak mencukupi untuk quantity ini", Toast.LENGTH_SHORT).show();
                    holder.numberPicker.setNumber(String.valueOf(stock));
                }else{
                    total = Integer.parseInt(number) * Integer.parseInt(cariBarangOutlet
                            .get(position).getHargaJual());
                    holder.btnTambahPesanan.setEnabled(true);
                    Log.d("testTotal", "number: "+number+" harga: "+cariBarangOutlet
                            .get(position).getHargaJual()+" total: "+total);
                }

            }
        });

//        PushDownAnim.setPushDownAnimTo(holder.itemView)
//                .setScale(MODE_SCALE, 0.89f)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//                    }
//                });

        PushDownAnim.setPushDownAnimTo(holder.btnTambahPesanan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (number!=null) {

                            if (number.equals("0")) {
                                Toast.makeText(context, "Jumlah Barang Harus Lebih dari 0",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        String number_of_pack = cariBarangOutlet.get(position).getNumberOfPack();
                        String stock = cariBarangOutlet.get(position).getStock();

                        int kurangi_stock = Integer.parseInt(stock) - Integer.parseInt(number);
                        int jumlah_pack_sisa = kurangi_stock / Integer.parseInt(number_of_pack);

                        String id_barang_outlet = cariBarangOutlet.get(position).getIdBarangOutlet();
                        String id_status_penjualan = transaksiKasirActivity.id_status_penjualan;
                        String id_barang = cariBarangOutlet.get(position).getIdBarang();

                        tambahPenjualan(id_barang_outlet, id_status_penjualan, id_barang,
                                String.valueOf(jumlah_pack_sisa));

                    }
                });

    }



    private void tambahPenjualan(String id_barang_outlet, String id_status_penjualan,
                                 String id_barang, String jumlah_pack_sisa) {

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
                        editJumlahPack(jumlah_pack_sisa, id_barang_outlet);
                    }else{
                        Toast.makeText(context, "Gagal Menambah barang", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Log.d("paramPenjualan", "idPenjualan: "+id_penjualan);
                    Log.d("paramPenjualan", "idBarangOutlet: "+id_barang_outlet);
                    Log.d("paramPenjualan", "number: "+number);
                    Log.d("paramPenjualan", "total: "+total);
                    Log.d("paramPenjualan", "tanggal: "+tanggal);
                    Log.d("paramPenjualan", "nama: "+preferencedConfig.getPreferenceNama());
                    Log.d("paramPenjualan", "idStatusPenjualan: "+id_status_penjualan);
                    Log.d("paramPenjualan", "ERROR: "+response.message());
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

    private void editJumlahPack(String jumlah_pack_sisa, String id_barang_outlet) {


        ConfigRetrofit.service.editPackOutlet(id_barang_outlet, jumlah_pack_sisa)
                .enqueue(new Callback<ResponseEditJumlahPackOutlet>() {
                    @Override
                    public void onResponse(Call<ResponseEditJumlahPackOutlet> call, Response<ResponseEditJumlahPackOutlet> response) {
                        if (response.isSuccessful()){

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(context, "Berhasil Edit Pack", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context, "Gagal Edit Pack", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(context, "Terjadi Kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditJumlahPackOutlet> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return cariBarangOutlet.size();
    }

    public class CariBarangOutletViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtHargaPcs, txtHargaPack, lblJumlahPack;
        ElegantNumberButton numberPicker;
        Button btnTambahPesanan;

        public CariBarangOutletViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_barang_outlet);
            txtNama = itemView.findViewById(R.id.text_item_nama_barang_outlet);
            txtHargaPcs = itemView.findViewById(R.id.text_item_harga_pcs_barang_outlet);
            txtHargaPack = itemView.findViewById(R.id.text_item_harga_pack_barang_outlet);
            btnTambahPesanan = itemView.findViewById(R.id.btn_tambah_pesanan_barang_outlet);
            lblJumlahPack = itemView.findViewById(R.id.lbl_jumlah_pack);
        }
    }
}
