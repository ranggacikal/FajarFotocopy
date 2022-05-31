package com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.haloqlinic.fajarfotocopy.kasir.InvoiceLaporanKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.WebViewReportMintaBarangKetoActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportPengirimanKetoFragment extends Fragment {


    public ReportPengirimanKetoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String date;

    TextView txtTanggal;
    Spinner spinnerPilihan, spinnerBulan, spinnerTahun;
    Button btnPilihTanggal, btnLihatLaporan;
    LinearLayout linearTanggal, linearBulanTahun;

    private String[] pilihanItem = {"Hari", "Bulan"};
    private String[] bulanItem = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"};
    private String[] tahunItem = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
            "2029", "2030"};

    String bulan, tahun;
    String pilihan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_pengiriman_keto, container, false);

        txtTanggal = rootView.findViewById(R.id.text_tanggal_report_minta_barang_keto);
        spinnerPilihan = rootView.findViewById(R.id.spinner_pilih_hari_bulan_minta_barang_keto);
        spinnerBulan = rootView.findViewById(R.id.spinner_pilih_bulan_report_minta_barang_keto);
        spinnerTahun = rootView.findViewById(R.id.spinner_pilih_tahun_report_minta_barang_keto);
        btnPilihTanggal = rootView.findViewById(R.id.btn_pilih_tanggal_report_minta_barang_keto);
        linearTanggal = rootView.findViewById(R.id.linear_tanggal_report_minta_barang_keto);
        linearBulanTahun = rootView.findViewById(R.id.linear_pilih_bulan_tahun_report_minta_barang_keto);
        btnLihatLaporan = rootView.findViewById(R.id.btn_lihat_laporan_minta_barang_keto);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy");

        //init spinner pilihan
        ArrayAdapter<String> adapterPilihan = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, pilihanItem);
        adapterPilihan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPilihan.setAdapter(adapterPilihan);

        //init spinner bulan
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, bulanItem);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulan.setAdapter(adapterBulan);

        //init spinner tahun
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, tahunItem);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(adapterTahun);

        spinnerPilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihan = spinnerPilihan.getSelectedItem().toString();

                if (pilihan.equals("Hari")) {
                    linearTanggal.setVisibility(View.VISIBLE);
                    linearBulanTahun.setVisibility(View.GONE);
                } else {
                    linearTanggal.setVisibility(View.GONE);
                    linearBulanTahun.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulan = spinnerBulan.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahun = spinnerTahun.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(btnPilihTanggal)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(btnLihatLaporan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lihatLaporan();
                    }
                });

        return rootView;
    }

    private void lihatLaporan() {


        Intent intent = new Intent(getActivity(), WebViewReportMintaBarangKetoActivity.class);
        intent.putExtra("bulan_tahun", bulan + " " + tahun);
        intent.putExtra("tanggal", date);
        intent.putExtra("pilihan", pilihan);
        startActivity(intent);

    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

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
                txtTanggal.setText(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}