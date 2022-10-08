package com.haloqlinic.fajarfotocopy.adapter.gudang;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.SearchBarangByNamaItem;
import com.haloqlinic.fajarfotocopy.model.editStock.ResponseEditStock;
import com.haloqlinic.fajarfotocopy.model.searchBarangGudang.SearchBarangGudangItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCekStockGudangAdapter extends RecyclerView.Adapter<SearchCekStockGudangAdapter.SearchCekStockGudangViewHolder> {
    Context context;
    List<SearchBarangGudangItem> dataBarang;
    CekStockBarangGudangActivity cekStockBarangGudangActivity;

    Dialog dialog;

    public SearchCekStockGudangAdapter(Context context, List<SearchBarangGudangItem> dataBarang, CekStockBarangGudangActivity cekStockBarangGudangActivity) {
        this.context = context;
        this.dataBarang = dataBarang;
        this.cekStockBarangGudangActivity = cekStockBarangGudangActivity;
    }

    @NonNull
    @Override
    public SearchCekStockGudangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cek_stock, parent, false);
        return new SearchCekStockGudangViewHolder(view);    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SearchCekStockGudangViewHolder holder, int position) {
        String img = dataBarang.get(position).getImageBarang();

        Glide.with(context)
                .load(img)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgCekStock);

        holder.txtNamaBarang.setText(dataBarang.get(position).getNamaBarang());
        holder.txtStockPcs.setText(dataBarang.get(position).getStock());
        holder.txtPack.setText(dataBarang.get(position).getJumlahPack());

        PushDownAnim.setPushDownAnimTo(holder.txtEdit)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_barang = dataBarang.get(position).getIdBarang();
                        String stock = dataBarang.get(position).getStock();
                        String jumlah_pack = dataBarang.get(position).getJumlahPack();
                        String number_of_pack = dataBarang.get(position).getNumberOfPack();
                        tampilDialog(id_barang, stock, jumlah_pack, number_of_pack);
                    }
                });
    }

    private void tampilDialog(String id_barang, String stock, String jumlah_pack, String number_of_pack) {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_stock_barang);
        dialog.setCancelable(false);

        final EditText edtPcs = dialog.findViewById(R.id.edt_dialog_pcs_edit_stock);
        final EditText edtPack = dialog.findViewById(R.id.edt_dialog_pack_edit_stock);
        final TextView txtEdit = dialog.findViewById(R.id.text_dialog_edit_stock);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_edit_stock);

        edtPcs.setEnabled(false);

        edtPack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String jumlah_pack = edtPack.getText().toString();

                Log.d("cekDataEditStock", "jumlah_pack: "+jumlah_pack);
                Log.d("cekDataEditStock", "number_of_pack: "+number_of_pack);

                if (jumlah_pack.equals("")){
                    edtPcs.setText("0");
                }else{
                    int total_satuan = Integer.parseInt(number_of_pack) * Integer.parseInt(jumlah_pack);

                    Log.d("cekDataEditStock", "total: "+total_satuan);
                    edtPcs.setText(String.valueOf(total_satuan));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dialog.show();

        PushDownAnim.setPushDownAnimTo(txtEdit)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pcs = edtPcs.getText().toString();
                        String pack = edtPack.getText().toString();

                        if (pcs.isEmpty()){
                            edtPcs.setError("Jumlah pcs tidak boleh kosong");
                            edtPcs.requestFocus();
                            return;
                        }

                        if (pack.isEmpty()){
                            edtPack.setError("Jumlah pack tidak boleh kosong");
                            edtPack.requestFocus();
                            return;
                        }

                        int jumlahPcs = Integer.parseInt(pcs) + Integer.parseInt(stock);
                        int jumlahPack = Integer.parseInt(pack) + Integer.parseInt(jumlah_pack);

                        editStock(id_barang, jumlahPcs, jumlahPack);
                    }
                });

        PushDownAnim.setPushDownAnimTo(txtCancel)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

    }

    private void editStock(String id_barang, int jumlahPcs, int jumlahPack) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Edit Stock");
        progressDialog.show();

        ConfigRetrofit.service.editStock(id_barang, String.valueOf(jumlahPcs), String.valueOf(jumlahPack))
                .enqueue(new Callback<ResponseEditStock>() {
                    @Override
                    public void onResponse(Call<ResponseEditStock> call, Response<ResponseEditStock> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){
                                Toast.makeText(context, "Berhasil Edit Stock", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                cekStockBarangGudangActivity.loadData(cekStockBarangGudangActivity.nama);
                            }else{
                                Toast.makeText(context, "Gagal Edit, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditStock> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }

    public class SearchCekStockGudangViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCekStock;
        TextView txtNamaBarang, txtStockPcs, txtPack, txtEdit;
        public SearchCekStockGudangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCekStock = itemView.findViewById(R.id.img_item_cek_stock);
            txtNamaBarang = itemView.findViewById(R.id.text_item_nama_cek_stock);
            txtStockPcs = itemView.findViewById(R.id.text_item_stock_pcs_cek_stock);
            txtPack = itemView.findViewById(R.id.text_item_stock_pack_cek_stock);
            txtEdit = itemView.findViewById(R.id.text_item_edit_cek_stock);
        }
    }
}
