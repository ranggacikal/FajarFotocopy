package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.CekStockBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.KirimBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataPermintaanBarangItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.DataDriverItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.ResponseDataDriver;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMintaBarangAdapter extends RecyclerView.Adapter<DataMintaBarangAdapter.DataMintaBarangViewHolder> {

    Context context;
    List<DataPermintaanBarangItem> dataPermintaan;
    List<DataBarangItem> dataBarangMinta;

    List<DataDriverItem> dataDriverItems;

    Boolean isOpen = true;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date, id_driver;

    public DataMintaBarangAdapter(Context context, List<DataPermintaanBarangItem> dataPermintaan,
                                  List<DataBarangItem> dataBarangMinta) {
        this.context = context;
        this.dataPermintaan = dataPermintaan;
        this.dataBarangMinta = dataBarangMinta;
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
        holder.rvDataBarangMinta.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(context,
                2, GridLayoutManager.VERTICAL, false);
        holder.rvDataBarangMinta.setLayoutManager(manager);
        DataListMintaBarangAdapter adapter = new DataListMintaBarangAdapter(context, dataBarangMinta);
        holder.rvDataBarangMinta.setAdapter(adapter);

        ClickedNested clickedNested = new ClickedNested();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!clickedNested.isOpen()){
                    clickedNested.setOpen(true);
                }else{
                    clickedNested.setOpen(false);
                }

                Log.d("cekIsOpen", "onClick: " + clickedNested.isOpen());
                if (clickedNested.isOpen()) {
                    holder.rvDataBarangMinta.setVisibility(View.VISIBLE);
                    holder.imgDropDown.setVisibility(View.GONE);
                    holder.imgDropTop.setVisibility(View.VISIBLE);
                    clickedNested.setOpen(false);
                } else {
                    holder.rvDataBarangMinta.setVisibility(View.GONE);
                    holder.imgDropDown.setVisibility(View.VISIBLE);
                    holder.imgDropTop.setVisibility(View.GONE);
                    clickedNested.setOpen(true);
                }
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

    private void tambahStatusPengiriman(String idOutlet, String nama_barang, String idMintaBarang) {

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        date = dateFormat.format(calendar.getTime());

        String status_pengiriman = "pending";

        ProgressDialog progressStatus = new ProgressDialog(context);
        progressStatus.setMessage("Membuat pengiriman barang");
        progressStatus.show();

        ConfigRetrofit.service.tambahStatusPengiriman(status_pengiriman, date, idOutlet, id_driver).enqueue(new Callback<ResponseTambahStatusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseTambahStatusPengiriman> call, Response<ResponseTambahStatusPengiriman> response) {
                if (response.isSuccessful()) {
                    progressStatus.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Intent intent = new Intent(context, KirimBarangGudangActivity.class);
                        intent.putExtra("id_toko", idOutlet);
                        intent.putExtra("tanggal", date);
                        intent.putExtra("fromActivity", "mintaBarang");
                        intent.putExtra("nama_barang", nama_barang);
                        intent.putExtra("id_minta_barang", idMintaBarang);
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

        TextView txtNamaOutlet;
        RecyclerView rvDataBarangMinta;
        ImageView imgDropDown, imgDropTop;

        public DataMintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaOutlet = itemView.findViewById(R.id.text_item_nama_outlet_minta_barang_gudang);
            rvDataBarangMinta = itemView.findViewById(R.id.rv_data_barang_minta_barang_gudang);
            imgDropDown = itemView.findViewById(R.id.imgPanahBawah);
            imgDropTop = itemView.findViewById(R.id.imgPanahAtas);
        }
    }
}
