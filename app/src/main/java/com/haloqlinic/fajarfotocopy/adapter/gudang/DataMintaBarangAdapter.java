package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.fragmentgudang.MintaBarangFragment;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.KirimBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.gudang.mintabarang.DataMintaBarangActivity;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataPermintaanBarangItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.DataDriverItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.ResponseDataDriver;
import com.haloqlinic.fajarfotocopy.model.hapusMintaBarangByOutlet.ResponseHapusMintaBarangByOutlet;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMintaBarangAdapter extends RecyclerView.Adapter<DataMintaBarangAdapter.DataMintaBarangViewHolder> {

    Context context;
    List<DataPermintaanBarangItem> dataPermintaan;
    List<DataBarangItem> dataBarangMinta;
    MintaBarangFragment mintaBarangFragment;

    List<DataDriverItem> dataDriverItems;

    Boolean isOpen = true;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String tanggal, id_driver, id_toko;

    public DataMintaBarangAdapter(Context context, List<DataPermintaanBarangItem> dataPermintaan,
                                  List<DataBarangItem> dataBarangMinta,
                                  MintaBarangFragment mintaBarangFragment) {
        this.context = context;
        this.dataPermintaan = dataPermintaan;
        this.dataBarangMinta = dataBarangMinta;
        this.mintaBarangFragment = mintaBarangFragment;
    }

    @NonNull
    @Override
    public DataMintaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dari_minta_barang_gudang,
                parent, false);
        return new DataMintaBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataMintaBarangViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtNamaOutlet.setText(dataPermintaan.get(position).getNamaOutlet());

        dataBarangMinta = dataPermintaan.get(position).getDataBarang();

        holder.rvDataBarangMinta.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(context,
                2, GridLayoutManager.VERTICAL, false);
        holder.rvDataBarangMinta.setLayoutManager(manager);
        DataListMintaBarangAdapter adapter = new DataListMintaBarangAdapter(context, dataBarangMinta);
        holder.rvDataBarangMinta.setAdapter(adapter);
        ClickedNested clickedNested = new ClickedNested();
        clickedNested.setOpen(false);

        initSpinner(holder.spinnerPilihDriver);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        tanggal = df.format(c);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_toko = dataPermintaan.get(position).getIdOutlet();
                Log.d("cekIdToko", "onClick: " + id_toko);

                if (!clickedNested.isOpen()) {
                    clickedNested.setOpen(true);
                } else {
                    clickedNested.setOpen(false);
                }

                if (clickedNested.isOpen()) {
                    holder.rvDataBarangMinta.setVisibility(View.VISIBLE);
                    holder.btnCreatePengiriman.setVisibility(View.VISIBLE);
                    holder.txtPilihDriver.setVisibility(View.VISIBLE);
                    holder.spinnerPilihDriver.setVisibility(View.VISIBLE);
                    holder.imgDropDown.setVisibility(View.GONE);
                    holder.imgDropTop.setVisibility(View.VISIBLE);
                } else {
                    holder.rvDataBarangMinta.setVisibility(View.GONE);
                    holder.btnCreatePengiriman.setVisibility(View.GONE);
                    holder.txtPilihDriver.setVisibility(View.GONE);
                    holder.spinnerPilihDriver.setVisibility(View.GONE);
                    holder.imgDropDown.setVisibility(View.VISIBLE);
                    holder.imgDropTop.setVisibility(View.GONE);
                }
                Log.d("cekIsOpen", "onClick: " + clickedNested.isOpen());
            }
        });

        holder.spinnerPilihDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_driver = dataDriverItems.get(position).getIdUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.btnCreatePengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahStatusPengiriman();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)
                        .setTitle("Hapus Data ?")
                        .setMessage("Apakah anda yakin ingin menghapus data minta barang di toko ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                hapusDataMintaBarang(dataPermintaan.get(position).getIdOutlet());
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

    private void hapusDataMintaBarang(String idOutlet) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menghapus Data");
        progressDialog.show();

        ConfigRetrofit.service.hapusMintaBarangByOutlet(idOutlet)
                .enqueue(new Callback<ResponseHapusMintaBarangByOutlet>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseHapusMintaBarangByOutlet> call, Response<ResponseHapusMintaBarangByOutlet> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            int status = response.body().getStatus();
                            if (status == 1) {
                                Toast.makeText(context,
                                        "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                                mintaBarangFragment.loadMintaBarang();
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
                    public void onFailure(Call<ResponseHapusMintaBarangByOutlet> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context,
                                "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initSpinner(Spinner spinnerDriver) {

        ConfigRetrofit.service.getDriver().enqueue(new Callback<ResponseDataDriver>() {
            @Override
            public void onResponse(Call<ResponseDataDriver> call, Response<ResponseDataDriver> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataDriverItems = response.body().getDataDriver();
                        List<String> listSpinnerDriver = new ArrayList<String>();
                        for (int i = 0; i < dataDriverItems.size(); i++) {

                            listSpinnerDriver.add(dataDriverItems.get(i).getNamaLengkap());

                        }

                        ArrayAdapter<String> adapterDriver = new ArrayAdapter<String>(context,
                                R.layout.spinner_item, listSpinnerDriver);

                        adapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDriver.setAdapter(adapterDriver);

                    } else {
                        Toast.makeText(context,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context,
                            "Terjadi Kesalahan Di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataDriver> call, Throwable t) {
                Toast.makeText(context,
                        "Koneksi Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahStatusPengiriman() {

        String status_pengiriman = "pending";

        ProgressDialog progressStatus = new ProgressDialog(context);
        progressStatus.setMessage("Membuat pengiriman barang");
        progressStatus.show();

        Log.d("testDataMintaBarang", "tanggal: " + tanggal);
        Log.d("testDataMintaBarang", "id_toko: " + id_toko);
        Log.d("testDataMintaBarang", "id_driver: " + id_driver);

        ConfigRetrofit.service.tambahStatusPengiriman(status_pengiriman, tanggal, id_toko, id_driver).enqueue(new Callback<ResponseTambahStatusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseTambahStatusPengiriman> call, Response<ResponseTambahStatusPengiriman> response) {
                if (response.isSuccessful()) {
                    progressStatus.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Intent intent = new Intent(context, DataMintaBarangActivity.class);
                        intent.putExtra("id_toko", id_toko);
                        intent.putExtra("id_driver", id_driver);
                        intent.putExtra("tanggal", tanggal);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Gagal membuat pengiriman", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressStatus.dismiss();
                    Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahStatusPengiriman> call, Throwable t) {
                progressStatus.dismiss();
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataPermintaan.size();
    }

    public class DataMintaBarangViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaOutlet, txtPilihDriver;
        RecyclerView rvDataBarangMinta;
        ImageView imgDropDown, imgDropTop, imgDelete;
        Button btnCreatePengiriman;
        Spinner spinnerPilihDriver;

        public DataMintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaOutlet = itemView.findViewById(R.id.text_item_nama_outlet_minta_barang_gudang);
            rvDataBarangMinta = itemView.findViewById(R.id.rv_data_barang_minta_barang_gudang);
            imgDropDown = itemView.findViewById(R.id.imgPanahBawah);
            imgDropTop = itemView.findViewById(R.id.imgPanahAtas);
            btnCreatePengiriman = itemView.findViewById(R.id.btn_create_pengiriman_minta_barang);
            spinnerPilihDriver = itemView.findViewById(R.id.spinner_pilih_driver_minta_barang_gudang);
            txtPilihDriver = itemView.findViewById(R.id.tv_pilih_driver);
            imgDelete = itemView.findViewById(R.id.img_hapus_minta_barang_gudang);
        }
    }
}
