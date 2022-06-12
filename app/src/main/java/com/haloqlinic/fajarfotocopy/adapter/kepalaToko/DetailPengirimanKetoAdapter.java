package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import android.annotation.SuppressLint;
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
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.editBarangOutlet.ResponseEditBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.editPengiriman.ResponseEditPengiriman;
import com.haloqlinic.fajarfotocopy.model.getIdBarangOutlet.IdBarangOutletItem;
import com.haloqlinic.fajarfotocopy.model.getIdBarangOutlet.ResponseGetIdBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.hapusPengiriman.ResponseHapusPengiriman;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.GetListPengirimanItem;
import com.haloqlinic.fajarfotocopy.model.stockByIdBarang.GetStockByIdBarangItem;
import com.haloqlinic.fajarfotocopy.model.stockByIdBarang.ResponseStockByIdBarang;
import com.haloqlinic.fajarfotocopy.model.tambahBarangOutlet.ResponseTambahBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.updateStockPengiriman.ResponseUpdateStockPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
            hargaPack, diskon, diskonPack, id_status_pengiriman, status_barang, number_of_pack;
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
    public void onBindViewHolder(@NonNull @NotNull DetailPengirimanKetoViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        id_barang = listPengiriman.get(position).getIdBarang();
        img_barang = listPengiriman.get(position).getImageBarang();
//        id_outlet = listPengiriman.get(position).getIdOutlet();
        jumlah_pcs = listPengiriman.get(position).getJumlah();
        jumlah_pack = listPengiriman.get(position).getJumlahPack();
//        id_pengiriman = listPengiriman.get(position).getIdPengiriman();
//        id_status_pengiriman = listPengiriman.get(position).getIdStatusPengiriman();
        status_barang = listPengiriman.get(position).getStatusBarang();
//
//        hargaPcs = listPengiriman.get(position).getHargaJualToko();
//        hargaPack = listPengiriman.get(position).getHargaJualTokoPack();


        Glide.with(context)
                .load(img_barang)
                .error(R.drawable.ic_gift)
                .into(holder.imgBarang);

        holder.linearTextTolakTerima.setVisibility(View.VISIBLE);

        if (!detailPengirimanKetoActivity.status_pengiriman.equals("pengiriman selesai")) {
            holder.linearBtnTolakTerima.setVisibility(View.GONE);

        } else if (status_barang.equals("diterima")) {

            holder.linearBtnTolakTerima.setVisibility(View.GONE);
            holder.linearTextTolakTerima.setVisibility(View.VISIBLE);
            holder.txtDiterima.setVisibility(View.VISIBLE);
            holder.txtDitolak.setVisibility(View.GONE);

        } else if (status_barang.equals("ditolak")) {

            holder.linearBtnTolakTerima.setVisibility(View.GONE);
            holder.linearTextTolakTerima.setVisibility(View.VISIBLE);
            holder.txtDitolak.setVisibility(View.VISIBLE);
            holder.txtDiterima.setVisibility(View.GONE);

        } else if (status_barang.equals("pending")) {
            holder.linearBtnTolakTerima.setVisibility(View.VISIBLE);
            holder.linearTextTolakTerima.setVisibility(View.GONE);
        }

        holder.txtNamaBarang.setText(listPengiriman.get(position).getNamaBarang());
        holder.txtJumlahPcs.setText(jumlah_pcs);
        holder.txtJumlahPack.setText(jumlah_pack);

        PushDownAnim.setPushDownAnimTo(holder.btnTerima)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        id_barang = listPengiriman.get(position).getIdBarang();


                        id_outlet = listPengiriman.get(position).getIdOutlet();
                        jumlah_pcs = listPengiriman.get(position).getJumlah();
                        jumlah_pack = listPengiriman.get(position).getJumlahPack();
                        id_pengiriman = listPengiriman.get(position).getIdPengiriman();
                        id_status_pengiriman = listPengiriman.get(position).getIdStatusPengiriman();
                        status_barang = listPengiriman.get(position).getStatusBarang();
                        number_of_pack = listPengiriman.get(position).getNumberOfPack();
                        hargaPcs = listPengiriman.get(position).getHargaJualToko();
                        hargaPack = listPengiriman.get(position).getHargaJualTokoPack();
                        Log.d("cekNumberOfpack", "clickTerima: " + number_of_pack);
                        tampilDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(holder.btnTolak)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_barang = listPengiriman.get(position).getIdBarang();
                        id_outlet = listPengiriman.get(position).getIdOutlet();
                        jumlah_pcs = listPengiriman.get(position).getJumlah();
                        jumlah_pack = listPengiriman.get(position).getJumlahPack();
                        id_pengiriman = listPengiriman.get(position).getIdPengiriman();
                        id_status_pengiriman = listPengiriman.get(position).getIdStatusPengiriman();
                        status_barang = listPengiriman.get(position).getStatusBarang();
                        hargaPcs = listPengiriman.get(position).getHargaJualToko();
                        hargaPack = listPengiriman.get(position).getHargaJualTokoPack();
                        tampilDialogTolak();
                    }
                });

    }

    private void tampilDialogTolak() {
        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_tolak_barang);
        dialog.setCancelable(false);
        final TextView txtTolakBarang = dialog.findViewById(R.id.text_dialog_tolak_barang);
        final TextView txtCancel = dialog.findViewById(R.id.text_dialog_cancel_tolak_barang_outlet);

        dialog.show();

        txtTolakBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)
                        .setTitle("Tolak barang?")
                        .setMessage("Apakah anda yakin ingin menolak barang ini?")
                        .setCancelable(false)
                        .setPositiveButton("Tolak", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
//                                hargaPcs = edthargaPcs.getText().toString();
//                                    hargaPack = edtHargaPack.getText().toString();
//                                diskon = edtDiskonPcs.getText().toString();
//                                diskonPack = edtDiskonPack.getText().toString();
//
//                                if (hargaPcs.isEmpty() || hargaPack.isEmpty() || diskon.isEmpty() || diskonPack.isEmpty()){
//                                    Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }

                                tolakBarang();
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

    private void tolakBarang() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);

        String id_barang_outlet = "BO" + randomId;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Tolak Barang");
        progressDialog.show();

        ConfigRetrofit.service.editPengiriman(id_pengiriman, id_barang, jumlah_pcs, jumlah_pack,
                id_outlet, id_status_pengiriman, "ditolak")
                .enqueue(new Callback<ResponseEditPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseEditPengiriman> call, Response<ResponseEditPengiriman> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            String pesan = response.body().getPesan();

                            if (status == 1) {
                                updateStockTolak(id_barang, jumlah_pcs, jumlah_pack);
                                Log.d("editHapusPengiriman", "onResponse: " + "berhasil");
                                Toast.makeText(context, "Berhasil menolak barang", Toast.LENGTH_SHORT).show();
                                detailPengirimanKetoActivity.loadDetailPengiriman();
                                dialog.dismiss();
                                notifyDataSetChanged();
                            } else {

                                Toast.makeText(context, "Gagal menolak barang", Toast.LENGTH_SHORT).show();
                                Log.d("editHapusPengiriman", "onResponse: " + "Gagal");
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditPengiriman> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void updateStockTolak(String id_barang, String jumlah_pcs, String jumlah_pack) {

        ConfigRetrofit.service.cariBarangById(id_barang).enqueue(new Callback<ResponseCariBarangById>() {
            @Override
            public void onResponse(Call<ResponseCariBarangById> call, Response<ResponseCariBarangById> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();
                    String stockAwal = "", packAwal = "";

                    if (status == 1) {

                        List<SearchBarangByIdItem> dataCari = response.body().getSearchBarangById();
                        for (int i = 0; i < dataCari.size(); i++) {
                            stockAwal = dataCari.get(i).getStock();
                            packAwal = dataCari.get(i).getJumlahPack();
                        }

                        Log.d("cekLoopingStock", "stockAwal: " + stockAwal);
                        Log.d("cekLoopingStock", "packAwal: " + packAwal);

                        tambahStockAwal(id_barang, jumlah_pcs, jumlah_pack, stockAwal, packAwal);

                    } else {
                        Toast.makeText(context, "Gagal Get Detail Barang", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Terjadi Kesalahan ketika get detail barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariBarangById> call, Throwable t) {
                Toast.makeText(context, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahStockAwal(String id_barang, String jumlah_pcs, String jumlah_pack,
                                 String stockAwal, String packAwal) {

        Log.d("cekDataTolak", "jumlahPcs: " + jumlah_pcs);
        Log.d("cekDataTolak", "jumlahPack: " + jumlah_pack);
        Log.d("cekDataTolak", "stockAwal: " + stockAwal);
        Log.d("cekDataTolak", "packAwal: " + packAwal);

        int stockUpdate = Integer.parseInt(stockAwal) + Integer.parseInt(jumlah_pcs);
        int packUpdate = Integer.parseInt(packAwal) + Integer.parseInt(jumlah_pack);

        Log.d("cekDataTolak", "stockUpdate: " + stockUpdate);
        Log.d("cekDataTolak", "packUpdate: " + packUpdate);

        ConfigRetrofit.service.updateStockPengiriman(id_barang, String.valueOf(stockUpdate), String.valueOf(packUpdate))
                .enqueue(new Callback<ResponseUpdateStockPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseUpdateStockPengiriman> call, Response<ResponseUpdateStockPengiriman> response) {
                        if (response.isSuccessful()) {
                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(context, "Berhasil Update data ke gudang",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Gagal Update data ke gudang", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Terjadi kesalahan saat update data ke gudang",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdateStockPengiriman> call, Throwable t) {
                        Toast.makeText(context, "Koneksi error saat update data ke gudang",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void tampilDialog() {

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_terima_barang);
        dialog.setCancelable(false);

//        final EditText edthargaPcs = dialog.findViewById(R.id.edt_dialog_harga_pcs);
//        final EditText edtHargaPack = dialog.findViewById(R.id.edt_dialog_harga_pack);
//        final EditText edtDiskonPcs = dialog.findViewById(R.id.edt_dialog_diskon);
//        final EditText edtDiskonPack = dialog.findViewById(R.id.edt_dialog_diskon_pack);
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
//                                hargaPcs = edthargaPcs.getText().toString();
//                                    hargaPack = edtHargaPack.getText().toString();
//                                diskon = edtDiskonPcs.getText().toString();
//                                diskonPack = edtDiskonPack.getText().toString();
//
//                                if (hargaPcs.isEmpty() || hargaPack.isEmpty() || diskon.isEmpty() || diskonPack.isEmpty()){
//                                    Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }

//                                validasiStock();
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

    private void validasiStock() {

        ConfigRetrofit.service.stockIdBarang(id_barang).enqueue(new Callback<ResponseStockByIdBarang>() {
            @Override
            public void onResponse(Call<ResponseStockByIdBarang> call, Response<ResponseStockByIdBarang> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<GetStockByIdBarangItem> dataBarang = response.body().getGetStockByIdBarang();

                        Log.d("cekStockStr", "onResponse: " + dataBarang);

                        String stockStr = "";

                        for (int a = 0; a < dataBarang.size(); a++) {

                            stockStr = dataBarang.get(a).getStock();
                        }

                        Log.d("cekStockStr", "onResponse: " + stockStr);

                        if (Integer.parseInt(stockStr) == 0) {

                            Toast.makeText(context, "Stock barang di gudang sudah habis, " +
                                    "silahkan hubungi karyawan/kepala gudang", Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(jumlah_pcs) <= Integer.parseInt(stockStr)) {

                            terimaBarang();
                        } else {
                            Toast.makeText(context, "Stock barang di gudang sudah habis, " +
                                    "silahkan hubungi karyawan/kepala gudang", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Barang tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Terjadi kesalahan dalam validasi stock ke gudang",
                            Toast.LENGTH_SHORT).show();
                    Log.d("TraceError", "validasi: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseStockByIdBarang> call, Throwable t) {
                Toast.makeText(context, "Koneksi validasi stock ke gudang error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void terimaBarang() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);

        String id_barang_outlet = "BO" + randomId;

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menerima Barang");
        progressDialog.show();

        ConfigRetrofit.service.tambahBarangOutlet(id_barang_outlet, id_barang, hargaPcs, hargaPack,
                jumlah_pcs, jumlah_pack, number_of_pack, "0", "0", id_outlet)
                .enqueue(new Callback<ResponseTambahBarangOutlet>() {
                    @Override
                    public void onResponse(Call<ResponseTambahBarangOutlet> call, Response<ResponseTambahBarangOutlet> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();

                            int status = response.body().getStatus();
                            String pesan = response.body().getPesan();

                            if (pesan.equals("Value is null")) {
                                dialog.dismiss();
                                Toast.makeText(context, "Gagal Terima barang, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                            } else if (pesan.equals("barang Sudah Terdaftar")) {
                                dialog.dismiss();
                                getIdBarangOutlet();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, "Berhasil Menerima Data", Toast.LENGTH_SHORT).show();
//                                hapusPengiriman();
                                editPengiriman();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                            Log.d("TraceError", "tambahBarangOutlet: " + response.errorBody());
                            Log.d("TraceError", "numberOfPack: " + number_of_pack);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahBarangOutlet> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getIdBarangOutlet() {

        ProgressDialog progressGetId = new ProgressDialog(context);
        progressGetId.setMessage("memuat id barang outlet");
        progressGetId.show();

        ConfigRetrofit.service.getIdBarangOutlet(id_outlet, id_barang).enqueue(new Callback<ResponseGetIdBarangOutlet>() {
            @Override
            public void onResponse(Call<ResponseGetIdBarangOutlet> call, Response<ResponseGetIdBarangOutlet> response) {
                if (response.isSuccessful()) {
                    progressGetId.dismiss();
                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<IdBarangOutletItem> dataBarang = response.body().getIdBarangOutlet();
                        String id_barang_outlet = "";
                        String stock_pcs = "";
                        String stock_pack = "";
                        String number_of_pack_outlet = "";
                        for (int a = 0; a < dataBarang.size(); a++) {

                            id_barang_outlet = dataBarang.get(a).getIdBarangOutlet();
                            stock_pcs = dataBarang.get(a).getStock();
                            stock_pack = dataBarang.get(a).getJumlahPack();
                            number_of_pack_outlet = dataBarang.get(a).getNumberOfPack();

                        }


                        Log.d("cekNumberOfpack", "forGetId: " + number_of_pack_outlet);

                        editStock(id_barang_outlet, stock_pcs, stock_pack, number_of_pack_outlet);

                    } else {
                        progressGetId.dismiss();
                        Toast.makeText(context, "Gagal get ID", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressGetId.dismiss();
                    Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetIdBarangOutlet> call, Throwable t) {
                progressGetId.dismiss();
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void editStock(String id_barang_outlet_edit, String stock_pcs, String stock_pack, String number_of_pack_outlet) {

//        ProgressDialog progressEdit = new ProgressDialog(context);
//        progressEdit.setMessage("Edit Stock");
//        progressEdit.show();

        int jumlah_stock_pcs = Integer.parseInt(stock_pcs) + Integer.parseInt(jumlah_pcs);
        int jumlah_stock_pack = Integer.parseInt(stock_pack) + Integer.parseInt(jumlah_pack);
        int number_of_stock_update = 0;

        Log.d("cekNumberOfpack", "editStock: outlet: " + number_of_pack_outlet + " fromList: " + number_of_pack);
        if (number_of_pack.equals("0")) {
            Toast.makeText(context, "Number Of Pack = 0", Toast.LENGTH_SHORT).show();
            return;
        }
        number_of_stock_update = 0 + Integer.parseInt(number_of_pack);
        Log.d("cekNumberOfPackUpdate", "editStock: " + number_of_stock_update);

        ConfigRetrofit.service.editBarangOutlet(id_barang_outlet_edit, id_barang, hargaPcs, hargaPack,
                String.valueOf(jumlah_stock_pcs), String.valueOf(jumlah_stock_pack), diskon, diskonPack,
                id_outlet, String.valueOf(number_of_stock_update)).enqueue(new Callback<ResponseEditBarangOutlet>() {
            @Override
            public void onResponse(Call<ResponseEditBarangOutlet> call, Response<ResponseEditBarangOutlet> response) {
                if (response.isSuccessful()) {

//                            progressEdit.dismiss();

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (pesan.equals("Value is null")) {
                        Toast.makeText(context, "Gagal Terima barang, silahkan coba lagi",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(context, "Berhasil Tambah Stock", Toast.LENGTH_SHORT).show();
                        editPengiriman();

                    }

                } else {
//                            progressEdit.dismiss();
                    Toast.makeText(context, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditBarangOutlet> call, Throwable t) {
//                        progressEdit.dismiss();
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void editPengiriman() {

//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Edit data di pengiriman");
//        progressDialog.show();

        ConfigRetrofit.service.editPengiriman(id_pengiriman, id_barang, jumlah_pcs, jumlah_pack,
                id_outlet, id_status_pengiriman, "diterima")
                .enqueue(new Callback<ResponseEditPengiriman>() {
                    @Override
                    public void onResponse(Call<ResponseEditPengiriman> call, Response<ResponseEditPengiriman> response) {
                        if (response.isSuccessful()) {
//                            progressDialog.dismiss();
                            int status = response.body().getStatus();
                            if (status == 1) {
                                Log.d("editHapusPengiriman", "onResponse: " + "berhasil");
                                detailPengirimanKetoActivity.loadDetailPengiriman();
                            } else {
                                Log.d("editHapusPengiriman", "onResponse: " + "Gagal");
                            }

                        } else {
//                            progressDialog.dismiss();
                            Log.d("editHapusPengiriman", "onResponse: " + "Gagal From server");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEditPengiriman> call, Throwable t) {
//                        progressDialog.dismiss();
                        Log.d("editHapusPengiriman", "onResponse: " + t.getMessage());
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
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {
                        Log.d("statusHapusPengiriman", "onResponse: " + "berhasil");
                        detailPengirimanKetoActivity.loadDetailPengiriman();
                    } else {
                        Log.d("statusHapusPengiriman", "onResponse: " + "Gagal");
                    }

                } else {
                    progressDialog.dismiss();
                    Log.d("statusHapusPengiriman", "onResponse: " + "Error Server");
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPengiriman> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("statusHapusPengiriman", "onResponse: " + t.getMessage());
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
