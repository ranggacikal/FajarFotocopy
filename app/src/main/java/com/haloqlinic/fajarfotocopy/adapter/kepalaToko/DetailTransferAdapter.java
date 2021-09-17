package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ItemDetailListTransferKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ItemListTransferKetoBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.listtransferketo.DetailListTransferKetoActivity;
import com.haloqlinic.fajarfotocopy.model.cekBarangToko.CekBarangTokoItem;
import com.haloqlinic.fajarfotocopy.model.cekBarangToko.ResponseCekBarangToko;
import com.haloqlinic.fajarfotocopy.model.editStatusTransferBarang.ResponseEditStatusTransferBarang;
import com.haloqlinic.fajarfotocopy.model.editStockBarangOutlet.ResponseEditStockBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.listTransfer.ListTransferBarangItem;
import com.haloqlinic.fajarfotocopy.model.tambahBarangOutlet.ResponseTambahBarangOutlet;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransferAdapter extends RecyclerView.Adapter<DetailTransferAdapter.DetailTransferViewHolder> {

    Context context;
    List<ListTransferBarangItem> dataTransfer;
    DetailListTransferKetoActivity detailListTransferKetoActivity;

    ProgressDialog progressDialog;

    String id_transfer_barang_post ="";

    public DetailTransferAdapter(Context context, List<ListTransferBarangItem> dataTransfer, DetailListTransferKetoActivity detailListTransferKetoActivity) {
        this.context = context;
        this.dataTransfer = dataTransfer;
        this.detailListTransferKetoActivity = detailListTransferKetoActivity;
    }

    @NonNull
    @Override
    public DetailTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailTransferViewHolder(ItemDetailListTransferKetoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransferViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String img = dataTransfer.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .into(holder.binding.imgBarangPengirimanKeto);

        holder.binding.textNamaBarangDetailListTransferKeto.setText(dataTransfer.get(position).getNamaBarang());
        holder.binding.textJumlahPcsBarangDetailListTransferKeto.setText(dataTransfer.get(position).getJumlah());
        holder.binding.textJumlahPackBarangDetailListTransferKeto.setText(dataTransfer.get(position).getJumlahPack());

        String status = dataTransfer.get(position).getStatusBarang();

        if (status.equals("diterima")){
            holder.binding.linearTextTolakTerimaDetailListTransferKeto.setVisibility(View.VISIBLE);
            holder.binding.linearTolakTerimaDetailListTransferKeto.setVisibility(View.GONE);
            holder.binding.textDetailListTransferDiterimaKeto.setVisibility(View.VISIBLE);
            holder.binding.textDetailListTransferDitolakKeto.setVisibility(View.GONE);
        }else if (status.equals("ditolak")){
            holder.binding.linearTextTolakTerimaDetailListTransferKeto.setVisibility(View.VISIBLE);
            holder.binding.linearTolakTerimaDetailListTransferKeto.setVisibility(View.GONE);
            holder.binding.textDetailListTransferDiterimaKeto.setVisibility(View.GONE);
            holder.binding.textDetailListTransferDitolakKeto.setVisibility(View.VISIBLE);
        }

        PushDownAnim.setPushDownAnimTo(holder.binding.btnTerimaBarangDetailListTransferKeto)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_barang = dataTransfer.get(position).getIdBarang();
                        String id_transfer_barang = dataTransfer.get(position).getIdTransferBarang();
                        id_transfer_barang_post = dataTransfer.get(position).getIdTransferBarang();
                        String harga_jual = dataTransfer.get(position).getHargaJual();
                        String harga_jual_pack = dataTransfer.get(position).getHargaJualPack();
                        String stock_transfer = dataTransfer.get(position).getJumlah();
                        String jumlah_pack_transfer = dataTransfer.get(position).getJumlahPack();
                        String diskon = dataTransfer.get(position).getDiskon();
                        String diskon_pack = dataTransfer.get(position).getDiskonPack();
                        String id_outlet = detailListTransferKetoActivity.id_outlet;

                        Log.d("cekIdTransferBarang", "updateStatus: "+id_transfer_barang);

                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Menambahkan Stock");
                        progressDialog.show();

                        cekStock(id_barang, id_outlet, stock_transfer, jumlah_pack_transfer, id_transfer_barang);
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.binding.btnTolakBarangDetailListTransferKeto)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateStatusTolak();
                    }
                });
    }

    private void updateStatusTolak() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menolak barang");
        progressDialog.show();

        ConfigRetrofit.service.editStatusTransferBarang(id_transfer_barang_post, "ditolak")
                .enqueue(new Callback<ResponseEditStatusTransferBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusTransferBarang> call, Response<ResponseEditStatusTransferBarang> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(context, "Data berhasil ditolak",
                                        Toast.LENGTH_SHORT).show();
                                detailListTransferKetoActivity.loadData();

                            }else{
                                Toast.makeText(context,
                                        "Data gagal ditolak", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan di server",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusTransferBarang> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Koneksi Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void cekStock(String id_barang, String id_outlet, String stock_transfer, String jumlah_pack_transfer, String id_transfer_barang) {

        ConfigRetrofit.service.cekBarangToko(id_barang, id_outlet).enqueue(new Callback<ResponseCekBarangToko>() {
            @Override
            public void onResponse(Call<ResponseCekBarangToko> call, Response<ResponseCekBarangToko> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<CekBarangTokoItem> dataBarang = response.body().getCekBarangToko();

                        String stock_db = "", jumlah_pack_db = "", id_transfer_barang = "";

                        for (int a = 0; a<dataBarang.size(); a++){

                            stock_db = dataBarang.get(a).getStock();
                            jumlah_pack_db = dataBarang.get(a).getJumlahPack();

                        }

                        editStockBarangOutlet(id_barang, id_outlet, stock_transfer, stock_db,
                                jumlah_pack_transfer, jumlah_pack_db, id_transfer_barang);

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(context, "Data Barang Belum ada di database",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Terjadi kesalahan di server",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCekBarangToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editStockBarangOutlet(String id_barang, String id_outlet, String stock_transfer,
                                       String stock_db, String jumlah_pack_transfer,
                                       String jumlah_pack_db, String id_transfer_barang) {

        int total_stock = Integer.parseInt(stock_db) + Integer.parseInt(stock_transfer);
        int total_jumlah_pack = Integer.parseInt(jumlah_pack_db) + Integer.parseInt(jumlah_pack_transfer);

        ConfigRetrofit.service.editStockBarangOutlet(id_barang, id_outlet, String.valueOf(total_stock),
                String.valueOf(total_jumlah_pack)).enqueue(new Callback<ResponseEditStockBarangOutlet>() {
            @Override
            public void onResponse(Call<ResponseEditStockBarangOutlet> call, Response<ResponseEditStockBarangOutlet> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status ==1){
                        Toast.makeText(context, "Data Berhasil ditambah", Toast.LENGTH_SHORT).show();
                        updateStatus(id_transfer_barang);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(context, "Gagal, silahkan coba lagi",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Terjadi kesalahan di server",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditStockBarangOutlet> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateStatus(String id_transfer_barang) {

        Log.d("cekIdPost", "updateStatus: "+id_transfer_barang_post);

        ConfigRetrofit.service.editStatusTransferBarang(id_transfer_barang_post, "diterima")
                .enqueue(new Callback<ResponseEditStatusTransferBarang>() {
                    @Override
                    public void onResponse(Call<ResponseEditStatusTransferBarang> call, Response<ResponseEditStatusTransferBarang> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                Toast.makeText(context, "Berhasil Ubah status",
                                        Toast.LENGTH_SHORT).show();
                                detailListTransferKetoActivity.loadData();


                            }else{
                                Toast.makeText(context,
                                        "Gagal ubah status", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan di server",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStatusTransferBarang> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataTransfer.size();
    }

    public class DetailTransferViewHolder extends RecyclerView.ViewHolder {
        private ItemDetailListTransferKetoBinding binding;
        public DetailTransferViewHolder(@NonNull ItemDetailListTransferKetoBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
