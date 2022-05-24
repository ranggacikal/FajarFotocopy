package com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityPilihTanggalRiwayatDriverBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.driver.pengirimandriver.PengirimanDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.ListStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.ReportPengirimanGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PilihTanggalRiwayatDriverActivity extends AppCompatActivity {

    ActivityPilihTanggalRiwayatDriverBinding binding;
    private String[] pilihanItem = {"Hari", "Bulan"};
    private String[] bulanItem = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};
    private String[] tahunItem = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
            "2029", "2030"};

    String bulan, tahun;
    String pilihan;
    String fromReportDriver, jenisPengiriman;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihTanggalRiwayatDriverBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fromReportDriver = getIntent().getStringExtra("fromReportDriver");
        jenisPengiriman = getIntent().getStringExtra("jenisPengiriman");

        PushDownAnim.setPushDownAnimTo(binding.linearBackReportDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        //init spinner pilihan
        ArrayAdapter<String> adapterPilihan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, pilihanItem);
        adapterPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihHariBulanReportDriver.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihBulanReportDriver.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPilihTahunReportDriver.setAdapter(adapterTahun);

        binding.spinnerPilihHariBulanReportDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihan = binding.spinnerPilihHariBulanReportDriver.getSelectedItem().toString();

                if (pilihan.equals("Hari")) {
                    binding.linearTanggalReportDriver.setVisibility(View.VISIBLE);
                    binding.linearPilihBulanTahunReportDriver.setVisibility(View.GONE);
                } else {
                    binding.linearTanggalReportDriver.setVisibility(View.GONE);
                    binding.linearPilihBulanTahunReportDriver.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihBulanReportDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = binding.spinnerPilihBulanReportDriver.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPilihTahunReportDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = binding.spinnerPilihTahunReportDriver.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalReportDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnLihatReportDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lihatLaporan();
                    }
                });
    }

    private void lihatLaporan() {

        if (fromReportDriver.equals("toko")) {
            Intent intent = new Intent(this, RiwayatPengirimanTokoActivity.class);
            intent.putExtra("bulan_tahun", bulan + " " + tahun);
            intent.putExtra("tanggal", date);
            intent.putExtra("pilihan", pilihan);
            intent.putExtra("fromReportDriver", fromReportDriver);
            startActivity(intent);
        }else if (fromReportDriver.equals("supplier")){
            Intent intent = new Intent(this, RiwayatPengirimanSupplierActivity.class);
            intent.putExtra("bulan_tahun", bulan + " " + tahun);
            intent.putExtra("tanggal", date);
            intent.putExtra("pilihan", pilihan);
            intent.putExtra("fromReportDriver", fromReportDriver);
            startActivity(intent);
        }else if (fromReportDriver.equals("pengirimanToko") || fromReportDriver.equals("pengirimanSupplier")){
            Intent intent = new Intent(this, PengirimanDriverActivity.class);
            intent.putExtra("bulan_tahun", bulan + " " + tahun);
            intent.putExtra("tanggal", date);
            intent.putExtra("pilihan", pilihan);
            intent.putExtra("jenisPengiriman", jenisPengiriman);
            intent.putExtra("fromReportDriver", fromReportDriver);
            startActivity(intent);
        }

    }

    private void showDateDialog() {

        String strDateTime = "01-03-2020";
//        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy");
//        Date date1 = inputFormatter.parse(strDateTime);
//        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
//        String formatted = outputFormatter.format(date1);
//        Log.d("formatBulan", "showDateDialog: "+formatted);

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date date1 = newDate.getTime();
                Log.d("newDate", "onDateSet: "+date1);
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
//                Log.d("formatBulan", "showDateDialog: "+formatted);
//                date = dateFormatter.format(newDate.getTime());
                binding.textTanggalReportDriver.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }


}