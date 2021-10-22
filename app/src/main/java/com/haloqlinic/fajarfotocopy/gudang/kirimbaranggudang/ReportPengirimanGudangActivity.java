package com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ReportPengirimanGudangActivity extends AppCompatActivity {

    private ActivityReportPengirimanGudangBinding binding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportPengirimanGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportPengirimanGudang)
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
        binding.spinnerPilihHariBulanPengiriman.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihBulanReportPengiriman.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihTahunReportPengiriman.setAdapter(adapterTahun);

        binding.spinnerPilihHariBulanPengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihan = binding.spinnerPilihHariBulanPengiriman.getSelectedItem().toString();

                if (pilihan.equals("Hari")) {
                    binding.linearTanggalReportPengiriman.setVisibility(View.VISIBLE);
                    binding.linearPilihBulanTahunReportPengiriman.setVisibility(View.GONE);
                } else {
                    binding.linearTanggalReportPengiriman.setVisibility(View.GONE);
                    binding.linearPilihBulanTahunReportPengiriman.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihBulanReportPengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = binding.spinnerPilihBulanReportPengiriman.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTahunReportPengiriman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = binding.spinnerPilihTahunReportPengiriman.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalReportPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnLihatLaporanPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lihatLaporan();
                    }
                });

    }

    private void lihatLaporan() {

        Intent intent = new Intent(ReportPengirimanGudangActivity.this, ListStatusPengirimanActivity.class);
        intent.putExtra("bulan_tahun", bulan + " " + tahun);
        intent.putExtra("tanggal", date);
        intent.putExtra("pilihan", pilihan);
        startActivity(intent);

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(ReportPengirimanGudangActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date = dateFormatter.format(newDate.getTime());
                binding.textTanggalReportPengiriman.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

//    private void loadRecycler() {
//
//        ProgressDialog progressDialog = new ProgressDialog(ReportPengirimanGudangActivity.this);
//        progressDialog.setMessage("Memuat Data");
//        progressDialog.show();
//
//        ConfigRetrofit.service.statusPengiriman().enqueue(new Callback<ResponseDataStatusPengiriman>() {
//            @Override
//            public void onResponse(Call<ResponseDataStatusPengiriman> call, Response<ResponseDataStatusPengiriman> response) {
//                if (response.isSuccessful()){
//
//                    progressDialog.dismiss();
//                    List<DataStatusPengirimanItem> dataPengiriman = response.body().getDataStatusPengiriman();
//                    ReportPengirimanAdapter adapter = new ReportPengirimanAdapter(ReportPengirimanGudangActivity.this, dataPengiriman);
//                    binding.rvReportPengiriman.setAdapter(adapter);
//
//                }else{
//                    progressDialog.dismiss();
//                    Toast.makeText(ReportPengirimanGudangActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDataStatusPengiriman> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(ReportPengirimanGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}