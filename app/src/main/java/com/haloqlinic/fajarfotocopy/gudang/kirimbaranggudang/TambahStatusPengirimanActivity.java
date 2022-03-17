package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.getDriver.DataDriverItem;
import com.haloqlinic.fajarfotocopy.model.getDriver.ResponseDataDriver;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahStatusPengirimanActivity extends AppCompatActivity {

    List<DataTokoItem> dataTokoItems;
    List<DataDriverItem> dataDriverItems;
    private ActivityTambahStatusPengirimanBinding binding;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String tanggal, id_toko, id_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahStatusPengirimanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initSpinner();
        initSpinnerDriver();
        dateFormatter = new SimpleDateFormat("dd MMMM yyyy");

        PushDownAnim.setPushDownAnimTo(binding.linearBackStatusPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalStatusPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        binding.spinnerPilihTokoStatusPengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_toko = dataTokoItems.get(position).getIdOutlet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihDriverStatusPengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_driver = dataDriverItems.get(position).getIdUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahStatusPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("cekTanggalPengiriman", "onClick: "+tanggal);
                        tambahStatusPengiriman();
                    }
                });
    }

    private void initSpinnerDriver() {

        ProgressDialog progressDialog = new ProgressDialog(TambahStatusPengirimanActivity.this);
        progressDialog.setMessage("Memuat Data Driver");
        progressDialog.show();

        ConfigRetrofit.service.getDriver().enqueue(new Callback<ResponseDataDriver>() {
            @Override
            public void onResponse(Call<ResponseDataDriver> call, Response<ResponseDataDriver> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status==1){

                        dataDriverItems = response.body().getDataDriver();
                        List<String> listSpinnerDriver = new ArrayList<String>();
                        for (int i = 0; i < dataDriverItems.size(); i++) {

                            listSpinnerDriver.add(dataDriverItems.get(i).getNamaLengkap());

                        }

                        ArrayAdapter<String> adapterDriver = new ArrayAdapter<String>(TambahStatusPengirimanActivity.this,
                                R.layout.spinner_item, listSpinnerDriver);

                        adapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerPilihDriverStatusPengiriman.setAdapter(adapterDriver);

                    }else{
                        Toast.makeText(TambahStatusPengirimanActivity.this,
                                "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahStatusPengirimanActivity.this,
                            "Terjadi Kesalahan Di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataDriver> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahStatusPengirimanActivity.this,
                        "Koneksi Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tambahStatusPengiriman() {

        String status_pengiriman = "pending";

        ProgressDialog progressStatus = new ProgressDialog(TambahStatusPengirimanActivity.this);
        progressStatus.setMessage("Membuat pengiriman barang");
        progressStatus.show();

        ConfigRetrofit.service.tambahStatusPengiriman(status_pengiriman, tanggal, id_toko, id_driver).enqueue(new Callback<ResponseTambahStatusPengiriman>() {
            @Override
            public void onResponse(Call<ResponseTambahStatusPengiriman> call, Response<ResponseTambahStatusPengiriman> response) {
                if (response.isSuccessful()){
                    progressStatus.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        Intent intent = new Intent(TambahStatusPengirimanActivity.this, KirimBarangGudangActivity.class);
                        intent.putExtra("id_toko", id_toko);
                        intent.putExtra("tanggal", tanggal);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TambahStatusPengirimanActivity.this, "Gagal membuat pengiriman", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressStatus.dismiss();
                    Toast.makeText(TambahStatusPengirimanActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahStatusPengiriman> call, Throwable t) {
                progressStatus.dismiss();
                Toast.makeText(TambahStatusPengirimanActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggal = dateFormatter.format(newDate.getTime());

                binding.textTanggalStatusPengiriman.setText(tanggal);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private void initSpinner() {

        ProgressDialog progressDialog = new ProgressDialog(TambahStatusPengirimanActivity.this);
        progressDialog.setMessage("Memuat Data Toko");
        progressDialog.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {

                        dataTokoItems = response.body().getDataToko();
                        List<String> listSpinnerToko = new ArrayList<String>();
                        for (int i = 0; i < dataTokoItems.size(); i++) {

                            listSpinnerToko.add(dataTokoItems.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(TambahStatusPengirimanActivity.this,
                                R.layout.spinner_item, listSpinnerToko);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerPilihTokoStatusPengiriman.setAdapter(adapterToko);

                    }else{

                        Toast.makeText(TambahStatusPengirimanActivity.this, "Gagal memuat data toko", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahStatusPengirimanActivity.this, "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahStatusPengirimanActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}