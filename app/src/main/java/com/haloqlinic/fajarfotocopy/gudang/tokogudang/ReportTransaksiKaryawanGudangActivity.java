package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengeluaranTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportTransaksiKaryawanGudangBinding;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ReportTransaksiKaryawanGudangActivity extends AppCompatActivity {

    private ActivityReportTransaksiKaryawanGudangBinding binding;

    private String[] pilihanItem = {"Hari", "Bulan"};
    private String[] pilihanLevel = {"Kepala Toko", "Karyawan Toko"};
    private String[] bulanItem = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};
    private String[] tahunItem = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
            "2029", "2030"};

    String bulan, tahun;
    String pilihLevel;
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
        binding = ActivityReportTransaksiKaryawanGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportTransaksiKaryawanGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy");

//        //init spinner pilihan
//        ArrayAdapter<String> adapterPilihan = new ArrayAdapter<String>(this,
//                R.layout.spinner_item, pilihanItem);
//        adapterPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spinnerPilihHariBulanReportTransaksiKaryawanGudang.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihBulanTahunReportTransaksiKaryawanGudang.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihTahunReportTransaksiKaryawanGudang.setAdapter(adapterTahun);



        //init spinner karyawan
        ArrayAdapter<String> adapterKaryawan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterKaryawan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihKaryawanReportTransaksiKaryawanGudang.setAdapter(adapterTahun);

        initSpinnerToko();

        binding.spinnerPilihOutletReportTransaksiKaryawanGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_outlet = dataToko.get(i).getIdOutlet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        binding.spinnerPilihLevelTokoKaryawanGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                pilihLevel = binding.spinnerPilihLevelTokoKaryawanGudang.getSelectedItem().toString();
//
//                if (pilihan.equals("Kepala Toko")) {
//                    binding.linearTanggalReportTransaksiKaryawanGudang.setVisibility(View.VISIBLE);
//                    binding.linearPilihBulanTahunReportTransaksiKaryawanGudang.setVisibility(View.GONE);
//                } else {
//                    binding.linearTanggalReportTransaksiKaryawanGudang.setVisibility(View.GONE);
//                    binding.linearPilihBulanTahunReportTransaksiKaryawanGudang.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        binding.spinnerPilihHariBulanReportTransaksiKaryawanGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                pilihan = binding.spinnerPilihHariBulanReportTransaksiKaryawanGudang.getSelectedItem().toString();
//
//                if (pilihan.equals("Hari")) {
//                    binding.linearTanggalReportTransaksiKaryawanGudang.setVisibility(View.VISIBLE);
//                    binding.linearPilihBulanTahunReportTransaksiKaryawanGudang.setVisibility(View.GONE);
//                } else {
//                    binding.linearTanggalReportTransaksiKaryawanGudang.setVisibility(View.GONE);
//                    binding.linearPilihBulanTahunReportTransaksiKaryawanGudang.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        binding.spinnerPilihBulanTahunReportTransaksiKaryawanGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = binding.spinnerPilihBulanTahunReportTransaksiKaryawanGudang.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTahunReportTransaksiKaryawanGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = binding.spinnerPilihTahunReportTransaksiKaryawanGudang.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinnerToko() {
    }
}