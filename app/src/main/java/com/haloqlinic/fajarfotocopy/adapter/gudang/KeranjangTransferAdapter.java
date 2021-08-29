package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.HasilPencarianTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.KeranjangTransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.detailTransferBarang.DetailStatusTransferItem;
import com.haloqlinic.fajarfotocopy.model.hapusBarangTransfer.ResponseHapusBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.hapusTransferCancel.ResponseHapusTransferCancel;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangTransferAdapter extends RecyclerView.Adapter<KeranjangTransferAdapter.KeranjangTransferViewHolder> {

    Context context;
    List<DetailStatusTransferItem> dataTransferBarang;
    KeranjangTransferBarangGudangActivity keranjangTransferBarangGudangActivity;

    public KeranjangTransferAdapter(Context context, List<DetailStatusTransferItem> dataTransferBarang,
                                    KeranjangTransferBarangGudangActivity keranjangTransferBarangGudangActivity) {
        this.context = context;
        this.dataTransferBarang = dataTransferBarang;
        this.keranjangTransferBarangGudangActivity = keranjangTransferBarangGudangActivity;
    }

    @NonNull
    @Override
    public KeranjangTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_keranjang_transfer, parent, false);
        return new KeranjangTransferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangTransferViewHolder holder, int position) {

        holder.txtNama.setText(dataTransferBarang.get(position).getNamaBarang());
        holder.txtJumlah.setText(dataTransferBarang.get(position).getJumlah());
        holder.txtJumlahPack.setText(dataTransferBarang.get(position).getJumlahPack());

        PushDownAnim.setPushDownAnimTo(holder.imgHapus)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)
                                .setTitle("Hapus Barang?")
                                .setMessage("Apakah anda yakin ingin menghapus barang ini?")
                                .setCancelable(false)
                                .setPositiveButton("Hapus", new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        hapusBarang(dataTransferBarang.get(position).getIdTransferBarang());
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

    }

    private void hapusBarang(String idTransferBarang) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menghapus data transfer barang");
        progressDialog.show();

        ConfigRetrofit.service.hapusBarangTransfer(idTransferBarang)
                .enqueue(new Callback<ResponseHapusBarangTransfer>() {
            @Override
            public void onResponse(Call<ResponseHapusBarangTransfer> call, Response<ResponseHapusBarangTransfer> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    progressDialog.dismiss();

                    if (status==1){

                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                        keranjangTransferBarangGudangActivity.loadKeranjangTransfer();
                        notifyDataSetChanged();


                    }else{
                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Error, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusBarangTransfer> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataTransferBarang.size();
    }

    public class KeranjangTransferViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtJumlah, txtJumlahPack;
        LinearLayout imgHapus;

        public KeranjangTransferViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.text_item_nama_keranjang_transfer);
            txtJumlah = itemView.findViewById(R.id.text_item_jumlah_keranjang_transfer);
            txtJumlahPack = itemView.findViewById(R.id.text_item_jumlah_pack_transfer);
            imgHapus = itemView.findViewById(R.id.img_hapus_barang_transfer);
        }
    }
}
