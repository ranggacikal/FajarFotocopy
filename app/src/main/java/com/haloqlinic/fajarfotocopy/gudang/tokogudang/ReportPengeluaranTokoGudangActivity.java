package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

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
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengeluaranTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPengeluaranTokoGudangActivity extends AppCompatActivity {


    private ActivityReportPengeluaranTokoGudangBinding binding;

    private String[] pilihanItem = {"Hari", "Bulan"};
    private String[] bulanItem = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};
    private String[] tahunItem = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
            "2029", "2030"};

    String bulan, tahun;
    String pilihan;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String date;

    List<DataTokoItem> dataToko;
    String id_outlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportPengeluaranTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportPengeluaranTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy");

        //init spinner pilihan
        ArrayAdapter<String> adapterPilihan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, pilihanItem);
        adapterPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihHariBulanPengeluaranToko.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihBulanReportPengeluaranToko.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihTahunReportPengeluaranToko.setAdapter(adapterTahun);

        initSpinnerToko();

        binding.spinnerPilihOutletPengeluaranToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_outlet = dataToko.get(i).getIdOutlet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerPilihHariBulanPengeluaranToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihan = binding.spinnerPilihHariBulanPengeluaranToko.getSelectedItem().toString();

                if (pilihan.equals("Hari")) {
                    binding.linearTanggalReportPenjualanToko.setVisibility(View.VISIBLE);
                    binding.linearPilihBulanTahunReportPengeluaranToko.setVisibility(View.GONE);
                } else {
                    binding.linearTanggalReportPenjualanToko.setVisibility(View.GONE);
                    binding.linearPilihBulanTahunReportPengeluaranToko.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihBulanReportPengeluaranToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = binding.spinnerPilihBulanReportPengeluaranToko.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTahunReportPengeluaranToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = binding.spinnerPilihTahunReportPengeluaranToko.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalReportPengeluaranToko)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnLihatLaporanPengeluaranToko)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lihatLaporan();
                    }
                });


    }

    private void lihatLaporan() {
        Intent intent = new Intent(ReportPengeluaranTokoGudangActivity.this, WebViewReportPengeluaranTokoGudangActivity.class);
        intent.putExtra("bulan_tahun", bulan + " " + tahun);
        intent.putExtra("tanggal", date);
        intent.putExtra("pilihan", pilihan);
        intent.putExtra("id_outlet", id_outlet);
        startActivity(intent);
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(ReportPengeluaranTokoGudangActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

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
                binding.textTanggalReportPengeluaranToko.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private void initSpinnerToko() {
        ProgressDialog progressDialog = new ProgressDialog(ReportPengeluaranTokoGudangActivity.this);
        progressDialog.setMessage("Memuat Data Toko");
        progressDialog.show();

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1) {
                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerToko = new ArrayList<String>();
                        for (int i = 0; i < dataToko.size(); i++) {

                            listSpinnerToko.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterToko = new ArrayAdapter<String>(ReportPengeluaranTokoGudangActivity.this,
                                R.layout.spinner_item, listSpinnerToko);

                        adapterToko.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerPilihOutletPengeluaranToko.setAdapter(adapterToko);
                    } else {
                        Toast.makeText(ReportPengeluaranTokoGudangActivity.this, "Data Toko Kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ReportPengeluaranTokoGudangActivity.this, "Response Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ReportPengeluaranTokoGudangActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}