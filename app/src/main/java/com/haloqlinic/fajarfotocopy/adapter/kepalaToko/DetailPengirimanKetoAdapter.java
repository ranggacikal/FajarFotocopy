package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.DetailPengirimanKetoActivity;
import com.haloqlinic.fajarfotocopy.model.editPengiriman.ResponseEditPengiriman;
import com.haloqlinic.fajarfotocopy.model.hapusPengiriman.ResponseHapusPengiriman;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.tambahBarangOutlet.ResponseTambahBarangOutlet;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailPengirimanKetoAdapter extends RecyclerView.Adapter<DetailPengirimanKetoAdapter.DetailPengirimanKetoViewHolder> {

    Context context;
    List<GetListPengirimanItem> listPengiriman;
    DetailPengirimanKetoActivity detailPengirimanKetoActivity;

    String id_pengiriman, id_barang, id_outlet, jumlah_pcs, jumlah_pack, img_barang, hargaPcs,
            hargaPack, diskon, diskonPack, id_status_pengiriman, status_barang;
    Dialog dialog;

    public DetailPengirimanKetoAdapter(Context context, List<GetListPengirimanItem> listPengiriman, DetailPengirimanKetoActivity detailPengirimanKetoActivity) {
        this.context = context;
        this.listPengiriman = listPengiriman;
        this.detailPengirimanKetoActivity = detailPengirimanKetoActivity;
    }

    @NonNull
    @NotNull
    @Override
    public DetailPengirimanKetoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_pengiriman_keto, parent, false);
        return new DetailPengirimanKetoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetailPengirimanKetoViewHolder holder, int position) {

        id_barang = listPengiriman.get(position).getIdBarang();
        img_barang = listPengiriman.get(position).getImageBarang();
        id_outlet = listPengiriman.get(position).getIdOutlet();
        jumlah_pcs = listPengiriman.get(position).getJumlah();
        jumlah_pack = listPengiriman.get(position).getJumlahPack();
        id_pengiriman = listPengiriman.get(position).getIdPengiriman();
        id_status_pengiriman = listPengiriman.get(position).getIdStatusPengiriman();
        status_barang = listPengiriman.get(position).getStatusBarang();

        Glide.with(context)
                .load(img_barang)
                .error(R.drawable.ic_gift)
                .into(holder.imgBarang);

        holder.linearTextTolakTerima.setVisibility(View.GONE);

        if (status_barang.equals("diterima")){

            holder.linearBtnTolakTerima.setVisibility(View.GONE);
            holder.linearTextTolakTerima.setVisibility(View.VISIBLE);
            holder.txtDiterima.setVisibility(View.VISIBLE);


        }else if (status_barang.equals("ditolak")){

            holder.linearBtnTolakTerima.setVisibility(View.GONE);
            holder.linearTextTolakTerima.setVisibility(View.VISIBLE);
            holder.txtDitolak.setVisibility(View.VISIBLE);

        }

        holder.txtNamaBarang.setText(listPengiriman.get(position).getNamaBarang());
        holder.txtJumlahPcs.setText(jumlah_pcs);
        holder.txtJumlahPack.setText(jumlah_pack);

        PushDownAnim.setPushDownAnimTo(holder.btnTerima)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.btnTolak)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Tolak", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void tampilDialog() {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_tambah_barang_outlet);
        dialog.setCancelable(false);

        final EditText edthargaPcs = dialog.findViewById(R.id.edt_dialog_harga_pcs);
        final EditText edtHargaPack = dialog.findViewById(R.id.edt_dialog_harga_pack);
        final EditText edtDiskonPcs = dialog.findViewById(R.id.edt_dialog_diskon);
        final EditText edtDiskonPack = dialog.findViewById(R.id.edt_dialog_diskon_pack);
        final TextView txtTerimaBarang = dialog.findViewById(R.id.text_dialog_terima_barang);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tambah_barang_outlet);

        dialog.show();

        txtTerimaBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)
                        .setTitle("Terima barang?")
                        .setMessage("Apakah anda yakin ingin menerima barang ini?")
                        .setCancelable(false)
                        .setPositiveButton("Terima", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                hargaPcs = edthargaPcs.getText().toString();
                                hargaPack = edtHargaPack.getText().toString();
                                diskon = edtDiskonPcs.getText().toString();
                                diskonPack = edtDiskonPack.getText().toString();

                                if (hargaPcs.isEmpty() || hargaPack.isEmpty() || diskon.isEmpty() || diskonPack.isEmpty()){
                                    Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                terimaBarang();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Batal", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mBottomSheetDialog.show();

            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void terimaBarang() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);

        String id_barang_outlet = "BO"+randomId;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menerima Barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahBarangOutlet(id_barang_outlet, id_barang, hargaPcs, hargaPack,
                jumlah_pcs, jumlah_pack, diskon, diskonPack, id_outlet)
                .enqueue(new Callback<ResponseTambahBarangOutlet>() {
                    @Override
                    public void onResponse(Call<ResponseTambahBarangOutlet> call, Response<ResponseTambahBarangOutlet> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){
                                dialog.dismiss();
                                Toast.makeText(context, "Berhasil Menerima Data", Toast.LENGTH_SHORT).show();
//                                hapusPengiriman();
                                editPengiriman();
                            }else{
                                Toast.makeText(context, "Gagal Menambah Data", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahBarangOutlet> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editPengiriman() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Edit data di pengiriman");
        progressDialog.show();

        ConfigRetrofit.service.editPengiriman(id_pengiriman, id_barang, jumlah_pcs, jumlah_pack,
                id_outlet, id_status_pengiriman, "diterima")
                .enqueue(new Callback<ResponseEditPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseEditPengiriman> call, Response<ResponseEditPengiriman> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            int status = response.body().getStatus();
                            if (status==1){
                                Log.d("editHapusPengiriman", "onResponse: "+"berhasil");
                                detailPengirimanKetoActivity.loadDetailPengiriman();
                            }else{
                                Log.d("editHapusPengiriman", "onResponse: "+"Gagal");
                            }

                        }else{
                            progressDialog.dismiss();
                            Log.d("editHapusPengiriman", "onResponse: "+"Gagal From server");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditPengiriman> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("editHapusPengiriman", "onResponse: "+t.getMessage());
                    }
                });

    }

    private void hapusPengiriman() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menghapus data di pengiriman");
        progressDialog.show();

        ConfigRetrofit.service.hapusPengiriman(id_pengiriman).enqueue(new Callback<ResponseHapusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseHapusPengiriman> call, Response<ResponseHapusPengiriman> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){
                        Log.d("statusHapusPengiriman", "onResponse: "+"berhasil");
                        detailPengirimanKetoActivity.loadDetailPengiriman();
                    }else{
                        Log.d("statusHapusPengiriman", "onResponse: "+"Gagal");
                    }

                }else{
                    progressDialog.dismiss();
                    Log.d("statusHapusPengiriman", "onResponse: "+"Error Server");
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPengiriman> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("statusHapusPengiriman", "onResponse: "+t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPengiriman.size();
    }

    public class DetailPengirimanKetoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtJumlahPcs, txtJumlahPack, txtNamaBarang, txtDiterima, txtDitolak;
        Button btnTerima, btnTolak;
        LinearLayout linearBtnTolakTerima, linearTextTolakTerima;

        public DetailPengirimanKetoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_barang_pengiriman_keto);
            txtNamaBarang = itemView.findViewById(R.id.text_nama_barang_pengiriman_keto);
            txtJumlahPcs = itemView.findViewById(R.id.text_jumlah_pcs_barang_pengiriman_keto);
            txtJumlahPack = itemView.findViewById(R.id.text_jumlah_pack_barang_pengiriman_keto);
            btnTerima = itemView.findViewById(R.id.btn_terima_barang_pengiriman_keto);
            btnTolak = itemView.findViewById(R.id.btn_tolak_barang_pengiriman_keto);
            linearBtnTolakTerima = itemView.findViewById(R.id.linear_tolak_terima_pengiriman);
            linearTextTolakTerima = itemView.findViewById(R.id.linear_text_tolak_terima_pengiriman);
            txtDiterima = itemView.findViewById(R.id.text_pengiriman_diterima);
            txtDitolak = itemView.findViewById(R.id.text_pengiriman_ditolak);
        }
    }
}
