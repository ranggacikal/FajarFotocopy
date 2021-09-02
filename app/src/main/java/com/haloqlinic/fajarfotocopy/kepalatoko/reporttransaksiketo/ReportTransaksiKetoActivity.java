package com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTransaksiKetoBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahInformasiGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKasirBinding;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.ReportPengirimanGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.report.WebViewReportPengirimanActivity;
import com.haloqlinic.fajarfotocopy.kasir.InvoiceLaporanKasirActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.InvoiceKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.kasirketo.InvoiceKetoActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportTransaksiKetoActivity extends AppCompatActivity {

    private ActivityReportTransaksiKetoBinding binding;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportTransaksiKetoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportTransaksiKeto)
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
        binding.spinnerPilihHariBulanKeto.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihBulanReportKeto.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihTahunReportKeto.setAdapter(adapterTahun);

        binding.spinnerPilihHariBulanKeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihan = binding.spinnerPilihHariBulanKeto.getSelectedItem().toString();

                if (pilihan.equals("Hari")) {
                    binding.linearTanggalReportKeto.setVisibility(View.VISIBLE);
                    binding.linearPilihBulanTahunReportKeto.setVisibility(View.GONE);
                } else {
                    binding.linearTanggalReportKeto.setVisibility(View.GONE);
                    binding.linearPilihBulanTahunReportKeto.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihBulanReportKeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = binding.spinnerPilihBulanReportKeto.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTahunReportKeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = binding.spinnerPilihTahunReportKeto.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalReportKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnLihatLaporanKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lihatLaporan();
                    }
                });


    }

    private void lihatLaporan() {

        Intent intent = new Intent(ReportTransaksiKetoActivity.this, InvoiceKetoActivity.class);
        intent.putExtra("bulan_tahun", bulan + " " + tahun);
        intent.putExtra("tanggal", date);
        intent.putExtra("pilihan", pilihan);
        startActivity(intent);

    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(ReportTransaksiKetoActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date = dateFormatter.format(newDate.getTime());
                binding.textTanggalReportKeto.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}