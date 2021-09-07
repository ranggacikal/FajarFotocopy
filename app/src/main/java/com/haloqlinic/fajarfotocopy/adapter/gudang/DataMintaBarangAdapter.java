package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.KirimBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
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

    List<DataDriverItem> dataDriverItems;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date, id_driver;

    public DataMintaBarangAdapter(Context context, List<DataPermintaanBarangItem> dataPermintaan) {
        this.context = context;
        this.dataPermintaan = dataPermintaan;
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

        String url_image = dataPermintaan.get(position).getImageBarang();
        String nama_barang = dataPermintaan.get(position).getNamaBarang();
        String nama_outlet = dataPermintaan.get(position).getNamaOutlet();

        initSpinner(holder.spinnerDriver);

        holder.txtNamaBarang.setText(nama_barang);
        holder.txtNamaOutlet.setText(nama_outlet);

        Glide.with(context)
                .load(url_image)
                .into(holder.imgBarang);

        holder.spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_driver = dataDriverItems.get(position).getIdUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(holder.btnTambah)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahStatusPengiriman(dataPermintaan.get(position).getIdOutlet(), nama_barang,
                                dataPermintaan.get(position).getIdMintaBarang());
                    }
                });

    }

    private void initSpinner(Spinner spinnerDriver) {

        ConfigRetrofit.service.getDriver().enqueue(new Callback<ResponseDataDriver>() {
            @Override
            public void onResponse(Call<ResponseDataDriver> call, Response<ResponseDataDriver> response) {
                if (response.isSuccessful()){
                    int status = response.body().getStatus();

                    if (status==1){

                        dataDriverItems = response.body().getDataDriver();
                        List<String> listSpinnerDriver = new ArrayList<String>();
                        for (int i = 0; i < dataDriverItems.size(); i++) {

                            listSpinnerDriver.add(dataDriverItems.get(i).getNamaLengkap());

                        }

                        ArrayAdapter<String> adapterDriver = new ArrayAdapter<String>(context,
                                R.layout.spinner_item, listSpinnerDriver);

                        adapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDriver.setAdapter(adapterDriver);

                    }else{
                        Toast.makeText(context,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
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
                if (response.isSuccessful()){
                    progressStatus.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Intent intent = new Intent(context, KirimBarangGudangActivity.class);
                        intent.putExtra("id_toko", idOutlet);
                        intent.putExtra("tanggal", date);
                        intent.putExtra("fromActivity", "mintaBarang");
                        intent.putExtra("nama_barang", nama_barang);
                        intent.putExtra("id_minta_barang",idMintaBarang);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context, "Gagal membuat pengiriman", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressStatus.dismiss();
                    Toast.makeText(context, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahStatusPengiriman> call, Throwable t) {
                progressStatus.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataPermintaan.size();
    }

    public class DataMintaBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNamaBarang, txtNamaOutlet;
        Button btnTambah;
        Spinner spinnerDriver;

        public DataMintaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.img_item_dari_minta_barang_gudang);
            txtNamaBarang = itemView.findViewById(R.id.text_item_dari_nama_minta_barang_gudang);
            txtNamaOutlet = itemView.findViewById(R.id.text_item_dari_outlet_minta_barang_gudang);
            btnTambah = itemView.findViewById(R.id.btn_tambah_dari_minta_barang_keto);
            spinnerDriver = itemView.findViewById(R.id.spinner_pilih_driver_dari_minta_barang_keto);
        }
    }
}
