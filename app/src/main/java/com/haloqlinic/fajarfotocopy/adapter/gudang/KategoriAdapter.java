package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.TambahKategoriActivity;
import com.haloqlinic.fajarfotocopy.model.dataKategori.DataKategoriItem;
import com.haloqlinic.fajarfotocopy.model.dataKategoriDesc.DataKategoriDescItem;
import com.haloqlinic.fajarfotocopy.model.editKategori.ResponseEditKategori;
import com.haloqlinic.fajarfotocopy.model.hapusKategori.ResponseHapusKategori;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {

    Context context;
    List<DataKategoriDescItem> dataKategori;
    TambahKategoriActivity tambahKategoriActivity;

    Dialog dialog;

    String nama_kategori, id_kategori;

    public KategoriAdapter(Context context, List<DataKategoriDescItem> dataKategori, TambahKategoriActivity tambahKategoriActivity) {
        this.context = context;
        this.dataKategori = dataKategori;
        this.tambahKategoriActivity = tambahKategoriActivity;
    }

    @NonNull
    @NotNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull KategoriViewHolder holder, int position) {

        holder.txtNamaKategori.setText(dataKategori.get(position).getNamaKategori());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nama_kategori = dataKategori.get(position).getNamaKategori();
                        id_kategori = dataKategori.get(position).getIdKategori();
                        tampilDialog();
                    }
                });


    }

    private void tampilDialog() {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_edit_hapus_kategori);
        dialog.setCancelable(false);

        final EditText edtEditKategori = dialog.findViewById(R.id.edt_dialog_nama_kategori);
        final TextView txtEdit = dialog.findViewById(R.id.text_dialog_edit_kategori);
        final TextView txtHapus = dialog.findViewById(R.id.text_dialog_hapus_kategori);

        edtEditKategori.setText(nama_kategori);

        dialog.show();

        PushDownAnim.setPushDownAnimTo(txtEdit)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editKategori(edtEditKategori, dialog);
                    }
                });

        PushDownAnim.setPushDownAnimTo(txtHapus)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hapusKategori(edtEditKategori, dialog);
                    }
                });

    }

    private void hapusKategori(EditText edtEditKategori, Dialog dialog) {

        ProgressDialog progressDialogHapus = new ProgressDialog(context);
        progressDialogHapus.setMessage("Hapus Data");
        progressDialogHapus.show();

        ConfigRetrofit.service.hapusKategori(id_kategori).enqueue(new Callback<ResponseHapusKategori>() {
            @Override
            public void onResponse(Call<ResponseHapusKategori> call, Response<ResponseHapusKategori> response) {
                if (response.isSuccessful()){

                    progressDialogHapus.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(context, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        tambahKategoriActivity.loadDataKategori();

                    }else{
                        Toast.makeText(context, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialogHapus.dismiss();
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusKategori> call, Throwable t) {
                progressDialogHapus.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editKategori(EditText edtEditKategori, Dialog dialog) {

        String nama_kategori_edit = edtEditKategori.getText().toString();

        if (nama_kategori_edit.isEmpty()){

            edtEditKategori.setError("Nama Kategori Tidak Boleh Kosong");
            edtEditKategori.requestFocus();
            return;

        }

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Edit Kategori");
        progressDialog.show();

        ConfigRetrofit.service.editKtegori(id_kategori, nama_kategori_edit).enqueue(new Callback<ResponseEditKategori>() {
            @Override
            public void onResponse(Call<ResponseEditKategori> call, Response<ResponseEditKategori> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(context, "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        tambahKategoriActivity.loadDataKategori();


                    }else {
                        Toast.makeText(context, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Terjadi Kesalahan Di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditKategori> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataKategori.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaKategori;

        public KategoriViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaKategori = itemView.findViewById(R.id.text_item_nama_kategori);
        }
    }
}
