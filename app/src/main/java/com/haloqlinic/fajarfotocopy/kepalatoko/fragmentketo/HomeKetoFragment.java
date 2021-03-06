package com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.MainKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.listpengirimanketo.ListPengirimanKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.pengeluaranketo.PengeluaranKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo.ReportTransaksiKetoActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.reporttransaksiketo.TransaksiKetoActivity;
import com.haloqlinic.fajarfotocopy.model.sumTransaksiBulan.ResponseSumTransaksiBulan;
import com.haloqlinic.fajarfotocopy.model.sumTransaksiHari.ResponseSumTransaksiHari;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualan.ResponseTambahStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeKetoFragment extends Fragment {


    public HomeKetoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SharedPreferencedConfig preferencedConfig;
    TextView txtNama, txtTanggal, txtNamaToko, txtTotalHarian, txtTotalBulanan;
    Button btnKeluar;
    ImageView imageView;


    ProgressDialog progressDialog;

    private Calendar calendar, calendarHari, calendarBulan;
    private SimpleDateFormat dateFormat, dateFormatHari, dateFormatBulan;
    private String date, hari, bulan;
    LinearLayout linearKasirKeto, linearReportTransaksiKeto, linearMintaBarangKeto, linearListPengiriman, linearPengeluaranKeto ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_keto, container, false);

        imageView = rootView.findViewById(R.id.imgprofile);
        txtNama = rootView.findViewById(R.id.text_nama_home_keto);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_keto);
        txtNamaToko = rootView.findViewById(R.id.text_nama_toko_keto);
        linearKasirKeto = rootView.findViewById(R.id.linear_kasir_keto);
        linearReportTransaksiKeto = rootView.findViewById(R.id.linear_report_transaksi_keto);
        linearMintaBarangKeto = rootView.findViewById(R.id.linear_minta_barang_keto);
        linearListPengiriman = rootView.findViewById(R.id.linear_list_pengiriman);
        linearPengeluaranKeto = rootView.findViewById(R.id.linear_pengeluaran_keto);
        btnKeluar = rootView.findViewById(R.id.btn_keluar_keto);
        txtTotalHarian = rootView.findViewById(R.id.text_total_penjualan_harian_keto);
        txtTotalBulanan = rootView.findViewById(R.id.text_total_penjualan_bulanan_keto);

        preferencedConfig = new SharedPreferencedConfig(getActivity());


        txtNama.setText(preferencedConfig.getPreferenceNama());
        txtNamaToko.setText(preferencedConfig.getPreferenceNamaToko());
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());

        txtTanggal.setText(date);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        date = dateFormat.format(calendar.getTime());

        calendarHari = Calendar.getInstance();
        dateFormatHari = new SimpleDateFormat("dd MMMM yyyy");

        hari = dateFormatHari.format(calendarHari.getTime());

        calendarBulan = Calendar.getInstance();
        dateFormatBulan = new SimpleDateFormat("MMMM yyyy");

        bulan = dateFormatBulan.format(calendarBulan.getTime());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data Penjualan");
        progressDialog.show();

        PushDownAnim.setPushDownAnimTo(linearKasirKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahStatusPenjualan();
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearReportTransaksiKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TransaksiKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearMintaBarangKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TambahBarangKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearListPengiriman)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ListPengirimanKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearPengeluaranKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), PengeluaranKetoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(btnKeluar)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialAlertDialogBuilder(getActivity())
                                .setTitle("Keluar Akun?")
                                .setMessage("Anda yakin ingin keluar dari akun ini?")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        keluarAkun();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }
                });

        loadHari();
        loadBulan();

        Log.d("checkTokenLocal", "Home: " + preferencedConfig.getPreferenceTokenFcm());

        return rootView;

    }

    private void loadBulan() {

        Log.d("loadBulan", "loadBulan: "+bulan);
        Log.d("loadBulan", "id_outlet: "+preferencedConfig.getPreferenceIdOutlet());

        ConfigRetrofit.service.sumTransaksiBulan(bulan, preferencedConfig.getPreferenceIdOutlet())
                .enqueue(new Callback<ResponseSumTransaksiBulan>() {
                    @Override
                    public void onResponse(Call<ResponseSumTransaksiBulan> call, Response<ResponseSumTransaksiBulan> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                String totalBulanStr = response.body()
                                        .getSumTransaksiByBulan().get(0).getTotal();

                                Log.d("totalBulanStr", "onResponse: "+totalBulanStr);

                                int totalBulan = 0;

                                if (totalBulanStr!=null){

                                    totalBulan = Integer.parseInt(totalBulanStr);
                                }
                                txtTotalBulanan.setText("Rp" + NumberFormat.getInstance()
                                        .format(totalBulan));
                            }else{
                                Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Terjadi kesalahan diserver",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSumTransaksiBulan> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error Koneksi", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadHari() {

        Log.d("cekParamLoadhari", "hari: "+hari);
        Log.d("cekParamLoadhari", "id_outler: "+preferencedConfig.getPreferenceIdOutlet());


        ConfigRetrofit.service.sumTransaksiHari(hari, preferencedConfig.getPreferenceIdOutlet())
                .enqueue(new Callback<ResponseSumTransaksiHari>() {
                    @Override
                    public void onResponse(Call<ResponseSumTransaksiHari> call, Response<ResponseSumTransaksiHari> response) {
                        if (response.isSuccessful()){

                            int status = response.body().getStatus();

                            if (status==1){

                                String totalHariStr = response.body().getSumTransaksiByHari()
                                        .get(0).getTotal();
                                int totalHari = 0;

                                if (totalHariStr!=null) {
                                    totalHari = Integer.parseInt(totalHariStr);
                                }

                                txtTotalHarian.setText("Rp" + NumberFormat.getInstance().format(totalHari));

                            }else{
                                Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getActivity(), "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSumTransaksiHari> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error Koneksi", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void tambahStatusPenjualan() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_status_penjualan = "SPN"+randomId;
        String status_penjualan = "pending";

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());
        String tanggal = date;
        String id_outlet = preferencedConfig.getPreferenceIdOutlet();


                    ConfigRetrofit.service.tambahStatusPenjualan(id_status_penjualan, status_penjualan, tanggal, id_outlet)
                            .enqueue(new Callback<ResponseTambahStatusPenjualan>() {
                                @Override
                                public void onResponse(Call<ResponseTambahStatusPenjualan> call, Response<ResponseTambahStatusPenjualan> response) {
                                    if(response.isSuccessful()){
                                        int status = response.body().getStatus();
                                        if(status == 1){
                                            Intent intent = new Intent(getActivity(), TransaksiKasirActivity.class);
                                            intent.putExtra("namaActivity", "HomeKeto");
//                                            intent.putExtra("id_status_penjualan", id_status_penjualan);

                                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_STATUS_PENJUALAN, id_status_penjualan);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }else {
                                            Toast.makeText(getContext(), "Gagal Membuat Penjualan", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(getContext(), "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseTambahStatusPenjualan> call, Throwable t) {
                                    Toast.makeText(getContext(), "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
            }




    private void keluarAkun() {

        Toast.makeText(getActivity(), "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}