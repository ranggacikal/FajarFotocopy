package com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransferBarangGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.MainActivity;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.TambahUserGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.tambahStatusTransfer.ResponseTambahStatusTransfer;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TransferBarangGudangActivity extends AppCompatActivity {

    private ActivityTransferBarangGudangBinding binding;

    List<DataTokoItem> dataToko;

    String id_outlet_pengirim, nama_outlet_pengirim;
    String id_outlet_penerima, nama_outlet_penerima;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    String from_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransferBarangGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        from_activity = getIntent().getStringExtra("from_activity");

        initSpinnerTokoPengirim();
        initSpinnerTokoPenerima();

        binding.spinnerPilihTokoPengirimTransferGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_outlet_pengirim = dataToko.get(position).getIdOutlet();
                nama_outlet_pengirim = dataToko.get(position).getNamaOutlet();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTokoPenerimaTransferGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_outlet_penerima = dataToko.get(position).getIdOutlet();
                nama_outlet_penerima = dataToko.get(position).getNamaOutlet();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnTransferGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahStatusTransfer();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.linearBackTransferGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (from_activity!=null){
                            if (from_activity.equals("keranjang_transfer_gudang")){
                                startActivity(new Intent(TransferBarangGudangActivity.this,
                                        MainActivity.class));
                                finish();
                            }
                        }else {

                            finish();
                        }
                    }
                });

    }

    private void tambahStatusTransfer() {

        ProgressDialog progressDialogTambah = new ProgressDialog(TransferBarangGudangActivity.this);
        progressDialogTambah.setMessage("Menambhkan Status Transfer Barang");
        progressDialogTambah.show();

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_status_transfer = "STB"+randomId;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        Calendar newDate = Calendar.getInstance();
        Date date1 = newDate.getTime();
        Log.d("newDate", "onDateSet: " + date1);
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date1 = inputFormatter.parse(newDate.getTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormatter = new SimpleDateFormat(
                "dd MMMM yyyy", new Locale("id", "ID")
        );

        date = outputFormatter.format(date1);

        ConfigRetrofit.service.tambahStatusTransfer(id_status_transfer, date, id_outlet_pengirim,
                id_outlet_penerima, nama_outlet_pengirim, nama_outlet_penerima, "")
                .enqueue(new Callback<ResponseTambahStatusTransfer>() {
                    @Override
                    public void onResponse(Call<ResponseTambahStatusTransfer> call, Response<ResponseTambahStatusTransfer> response) {
                        if (response.isSuccessful()){

                            progressDialogTambah.dismiss();

                            if (response.body() != null){

                                int status = response.body().getStatus();
                                String message = response.body().getPesan();

                                if (status==1){

                                    Toast.makeText(TransferBarangGudangActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TransferBarangGudangActivity.this,
                                            HasilPencarianTransferBarangGudangActivity.class);
                                    intent.putExtra("id_outlet", id_outlet_pengirim);
                                    intent.putExtra("id_status_transfer", id_status_transfer);
                                    startActivity(intent);


                                }else{
                                    Toast.makeText(TransferBarangGudangActivity.this, message,
                                            Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(TransferBarangGudangActivity.this, "Response Null",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialogTambah.dismiss();
                            Toast.makeText(TransferBarangGudangActivity.this, "Response Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahStatusTransfer> call, Throwable t) {
                        progressDialogTambah.dismiss();
                        Toast.makeText(TransferBarangGudangActivity.this, "Error: "+t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initSpinnerTokoPenerima() {

        ProgressDialog progressDialogPenerima = new ProgressDialog(TransferBarangGudangActivity.this);
        progressDialogPenerima.setMessage("Memuat Data Toko");
        progressDialogPenerima.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){
                    progressDialogPenerima.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerTokoPenerima = new ArrayList<String>();
                        for (int i = 0; i < dataToko.size(); i++) {

                            listSpinnerTokoPenerima.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(TransferBarangGudangActivity.this,
                                R.layout.spinner_item, listSpinnerTokoPenerima);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerPilihTokoPenerimaTransferGudang.setAdapter(adapterToko);

                    }else{

                        Toast.makeText(TransferBarangGudangActivity.this, "Gagal memuat data toko", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    progressDialogPenerima.dismiss();
                    Toast.makeText(TransferBarangGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialogPenerima.dismiss();
                Toast.makeText(TransferBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerTokoPengirim() {

        ProgressDialog progressDialogPengirim = new ProgressDialog(TransferBarangGudangActivity.this);
        progressDialogPengirim.setMessage("Memuat Data Toko");
        progressDialogPengirim.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){
                    progressDialogPengirim.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerTokoPengirim = new ArrayList<String>();
                        for (int i = 0; i < dataToko.size(); i++) {

                            listSpinnerTokoPengirim.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(TransferBarangGudangActivity.this,
                                R.layout.spinner_item, listSpinnerTokoPengirim);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerPilihTokoPengirimTransferGudang.setAdapter(adapterToko);

                    }else{

                        Toast.makeText(TransferBarangGudangActivity.this, "Gagal memuat data toko", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    progressDialogPengirim.dismiss();
                    Toast.makeText(TransferBarangGudangActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialogPengirim.dismiss();
                Toast.makeText(TransferBarangGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (from_activity!=null){
            if (from_activity.equals("keranjang_transfer_gudang")){
                startActivity(new Intent(TransferBarangGudangActivity.this,
                        MainActivity.class));
                finish();
            }
        }else {

            finish();
        }
    }
}