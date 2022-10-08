package com.haloqlinic.fajarfotocopy.adapter.mintabarang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kirimBarang.CariKirimBarangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.supplier.StatusSupplierBulanAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ItemDataMintaBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ItemMintaBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ItemReportStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.gudang.mintabarang.DataMintaBarangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet.DataBarangItem;
import com.haloqlinic.fajarfotocopy.model.hapusBarangPermintaan.ResponseHapusBarangPermintaan;
import com.haloqlinic.fajarfotocopy.model.hapusDataMintaBarang.ResponseHapusDataMintaBarang;
import com.haloqlinic.fajarfotocopy.model.tambahPengiriman.ResponseTambahPengiriman;
import com.haloqlinic.fajarfotocopy.model.updateStockPengiriman.ResponseUpdateStockPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MintaBarangAdapter extends RecyclerView.Adapter<MintaBarangAdapter.MintaBarangViewHolder>{

    Context context;
    List<DataBarangItem> dataBarangItems;
    DataMintaBarangActivity dataMintaBarangActivity;
    String id_barang, number_of_pack, id_minta_barang;
    Dialog dialogDataKosong, dialog;
    String stockPcs = "", stockPack = "", numberOfPack = "";

    public MintaBarangAdapter(Context context, List<DataBarangItem> dataBarangItems,
                              DataMintaBarangActivity dataMintaBarangActivity) {
        this.context = context;
        this.dataBarangItems = dataBarangItems;
        this.dataMintaBarangActivity = dataMintaBarangActivity;
    }

    @NonNull
    @Override
    public MintaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataMintaBarangBinding bindingItem = ItemDataMintaBarangBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MintaBarangViewHolder(bindingItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MintaBarangViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String imageLink = dataBarangItems.get(position).getImageBarang();
        Glide.with(context)
                .load(imageLink)
                .into(holder.binding.imgItemDataMintaBarang);

        holder.binding.textItemNamaBarangDataMintaBarang.setText(
                dataBarangItems.get(position).getNamaBarang()
        );

        PushDownAnim.setPushDownAnimTo(holder.binding.btnTambahPengirimanDataMintaBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_barang = dataBarangItems.get(position).getIdBarang();
                        id_minta_barang = dataBarangItems.get(position).getIdMintaBarang();
                        cekDataBarang(id_barang);
//                        int stockPack = Integer.parseInt(dataCariBarang.get(position).getJumlahPack());
//                        number_of_pack = Integer.parseInt(dataCariBarang.get(position).getNumberOfPack());

//                        if (number_of_pack==0) {
//                            Toast.makeText(context, "Jumlah barang dalam pack adalah 0, silahkan edit " +
//                                    "data terlebih dahulu", Toast.LENGTH_LONG).show();
//                        }else if (stockPcs==0 || stockPcs<0) {
//
//                            tampilDialogKosong();
//
//                        }else {
//                            tampilDialog(stockPack, stockPcs, number_of_pack);
//                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.binding.btnHapusPengirimanDataMintaBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id_minta_barang = dataBarangItems.get(position).getIdMintaBarang();
                        hapusBarangPermintaan(id_minta_barang);
                    }
                });
    }

    private void hapusBarangPermintaan(String id_minta_barang) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menghapus Data");
        progressDialog.show();

        ConfigRetrofit.service.hapusBarangPermintaan(id_minta_barang)
                .enqueue(new Callback<ResponseHapusBarangPermintaan>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseHapusBarangPermintaan> call, Response<ResponseHapusBarangPermintaan> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            int status = response.body().getStatus();
                            if (status == 1){
                                Toast.makeText(context, "Hapus Data Berhasil",
                                        Toast.LENGTH_SHORT).show();
                                dataMintaBarangActivity.getDataMintaBarang(dataMintaBarangActivity.id_outlet);
                            } else {
                                Toast.makeText(context,
                                        "Hapus Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context,
                                    "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseHapusBarangPermintaan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context,
                                "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cekDataBarang(String id_barang) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cek Stock Barang");
        progressDialog.show();

        ConfigRetrofit.service.cariBarangById(id_barang).enqueue(new Callback<ResponseCariBarangById>() {
            @Override
            public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status==1){
                        List<SearchBarangByIdItem> dataBarang = response.body().getSearchBarangById();
                        for (int a = 0; a<dataBarang.size(); a++){
                            stockPcs = dataBarang.get(a).getStock();
                            stockPack = dataBarang.get(a).getJumlahPack();
                            numberOfPack = dataBarang.get(a).getNumberOfPack();
                        }
                        if (Integer.parseInt(stockPcs)<1 || Integer.parseInt(stockPack) < 1){
                            tampilDialogKosong();
                        }else{
                            tampilDialog(Integer.parseInt(stockPack), Integer.parseInt(stockPcs),
                                    Integer.parseInt(numberOfPack));
                        }
                    }else{
                        Toast.makeText(context, "Gagal Mengambil Data Barang",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tampilDialogKosong() {

        dialogDataKosong = new Dialog(context);

        dialogDataKosong.setContentView(R.layout.dialog_data_kosong);
        dialogDataKosong.setCancelable(false);

        final TextView txtTutup = dialogDataKosong.findViewById(R.id.text_tutup_dialog_data_kosong);

        dialogDataKosong.show();

        txtTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDataKosong.dismiss();
            }
        });


    }

    private void tampilDialog(int stockPack, int stockPcs, int number_of_pack) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_qty_kirim_barang);
        dialog.setCancelable(false);

        final EditText edtQty = dialog.findViewById(R.id.edt_dialog_qty_pcs);
        final EditText edtPack = dialog.findViewById(R.id.edt_dialog_qty_pack);
        final TextView txtTambahBarang = dialog.findViewById(R.id.text_dialog_tambah_barang);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tambah_barang);

        edtQty.setEnabled(false);

        edtPack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String jumlah_pack = edtPack.getText().toString();

                if (jumlah_pack.equals("")){
                    edtQty.setText("");
                }else{
                    int total_qty = Integer.parseInt(jumlah_pack) * number_of_pack;
                    edtQty.setText(String.valueOf(total_qty));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

        txtTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = edtQty.getText().toString();
                String pack = edtPack.getText().toString();
                tambahPengiriman(qty, pack, stockPcs, stockPack);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void tambahPengiriman(String qty, String pack, int stockPcs, int stockPack) {

        String id_toko = dataMintaBarangActivity.id_outlet;
        String id_status = dataMintaBarangActivity.id_status_pengiriman;

        if (Integer.parseInt(qty) > stockPcs) {
            Toast.makeText(context, "Stock Pcs Saat Ini Hanya Berjumlah "+stockPcs,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(pack) > stockPack) {
            Toast.makeText(context, "Stock Pack Saat Ini Hanya Berjumlah "+stockPack,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menambahkan Barang Ke Pengiriman barang");
        progressDialog.show();

        Log.d("cekNumberOPPengiriman", "tambahPengiriman: "+number_of_pack);

        ConfigRetrofit.service.tambahPengiriman(id_barang, qty, pack, String.valueOf(number_of_pack),
                id_toko, id_status, "pending")
                .enqueue(new Callback<ResponseTambahPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseTambahPengiriman> call, Response<ResponseTambahPengiriman> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil Menambahkan data", Toast.LENGTH_SHORT).show();
                                kurangiStock(id_barang, qty, pack, stockPack, stockPcs);
//                                String id_minta_barang = Mint.id_minta_barang;
                                if (id_minta_barang!=null) {
                                    hapusMintaBarang(id_minta_barang);
                                }
                                dialog.dismiss();
//                                CariKirimBarangAdapter adapter = new CariKirimBarangAdapter(context,
//                                        dataCariBarang, kirimBarangGudangActivity);
//                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {
                            progressDialog.dismiss();
                            Log.d("checkDataPengiriman", "id_barang: " + id_barang);
                            Log.d("checkDataPengiriman", "qty: " + qty);
                            Log.d("checkDataPengiriman", "pack: " + pack);
                            Log.d("checkDataPengiriman", "id_toko: " + id_toko);
                            Log.d("checkDataPengiriman", "id_status: " + id_status);
                            Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void hapusMintaBarang(String id_minta_barang) {

        ConfigRetrofit.service.hapusDataMintaBarang(id_minta_barang).enqueue(new Callback<ResponseHapusDataMintaBarang>() {
            @Override
            public void onResponse(Call<ResponseHapusDataMintaBarang> call, Response<ResponseHapusDataMintaBarang> response) {
                if (response.isSuccessful()){
                    int status = response.body().getStatus();

                    if (status==1){
                        dataMintaBarangActivity.getDataMintaBarang(dataMintaBarangActivity.id_outlet);
                    }else{
                        Toast.makeText(context, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Response Hapus Data Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusDataMintaBarang> call, Throwable t) {
                Toast.makeText(context, "Periksa Jaringan anda", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void kurangiStock(String id_barang, String qty, String pack, int stockPack, int stockPcs) {

        int updateStock = stockPcs - Integer.parseInt(qty) ;
        int updatePack =  stockPack - Integer.parseInt(pack);

        ConfigRetrofit.service.updateStockPengiriman(id_barang, String.valueOf(updateStock), String.valueOf(updatePack))
                .enqueue(new Callback<ResponseUpdateStockPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStockPengiriman> call, Response<ResponseUpdateStockPengiriman> response) {
                        if (response.isSuccessful()) {

                            int status = response.body().getStatus();
                            if (status == 1) {
                                Log.d("SuksesUpdateStock", "onResponse: Update Stock Berhasil");
                            } else {
                                Toast.makeText(context, "Gagal update stock", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Terjadi kesalahan saat update stock", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStockPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataBarangItems.size();
    }

    public class MintaBarangViewHolder extends RecyclerView.ViewHolder {

        private final ItemDataMintaBarangBinding binding;
        public MintaBarangViewHolder(@NonNull ItemDataMintaBarangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
