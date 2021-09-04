package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.BarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.notifikasigudang.NotifikasiGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ReportGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.suppliergudang.SupplierGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.TransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.UserGudangActivity;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualanGudang.ResponseTambahStatusPenjualanGudang;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SharedPreferencedConfig preferencedConfig;
    TextView txtNama, txtTanggal;
    Button btnKeluar;
    ImageView imageView;


    private Calendar calendar, calendarStatusPenjualanGudang;
    private SimpleDateFormat dateFormat, dateFormatStatusPenjualanGudang;
    private String date;
    CardView cardToko, cardBarang, cardUser, cardTransferBarang, cardSupplier, cardReport;
    ImageView imgNotif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = rootView.findViewById(R.id.imgprofile);
        txtNama = rootView.findViewById(R.id.text_nama_home_gudang);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_gudang);
        cardToko = rootView.findViewById(R.id.card_outlet_gudang);
        cardBarang = rootView.findViewById(R.id.card_kelola_barang_gudang);
        cardUser = rootView.findViewById(R.id.card_user_gudang);
        btnKeluar = rootView.findViewById(R.id.btn_keluar_gudang);
        cardTransferBarang = rootView.findViewById(R.id.card_transfer_barang_gudang);
        cardSupplier = rootView.findViewById(R.id.card_supplier_gudang);
        cardReport = rootView.findViewById(R.id.card_report_gudang);
        imgNotif = rootView.findViewById(R.id.img_notif);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        String level = preferencedConfig.getPreferenceLevel();
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);


        Log.d("checkTokenLocal", "Home: "+preferencedConfig.getPreferenceTokenFcm());

        if (level.equals("Karyawan Gudang")){
            cardToko.setVisibility(View.GONE);
            cardUser.setVisibility(View.GONE);
        }

        txtNama.setText(preferencedConfig.getPreferenceNama());

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());

        txtTanggal.setText(date);

        PushDownAnim.setPushDownAnimTo(cardToko)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TokoGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahStatusPenjualanGudang();
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardUser)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), UserGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardTransferBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TransferBarangGudangActivity.class));
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


        PushDownAnim.setPushDownAnimTo(cardBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), BarangGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardReport)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ReportGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(imgNotif)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), NotifikasiGudangActivity.class));
                    }
                });

        return rootView;
    }

    private void tambahStatusPenjualanGudang() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Menambahkan Status Penjualan Gudang");
        progressDialog.show();

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String randomId = String.format("%06d", number);
        String id_status_penjualan_gudang = "SPG"+randomId;

        calendarStatusPenjualanGudang = Calendar.getInstance();
        dateFormatStatusPenjualanGudang = new SimpleDateFormat("dd MMMM yyyy");

        String dateStatusPenjulanGudang = dateFormatStatusPenjualanGudang.format(
                calendarStatusPenjualanGudang.getTime());

        ConfigRetrofit.service.tambahStatusPenjualanGudang(id_status_penjualan_gudang, dateStatusPenjulanGudang,
                "pending", "", "", "", "", "")
                .enqueue(new Callback<ResponseTambahStatusPenjualanGudang>() {
                    @Override
                    public void onResponse(Call<ResponseTambahStatusPenjualanGudang> call, Response<ResponseTambahStatusPenjualanGudang> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            int status = response.body().getStatus();

                            if (status==1){

                                Intent intent = new Intent(getContext(), SupplierGudangActivity.class);
                                intent.putExtra("id_status_penjualan_gudang", id_status_penjualan_gudang);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getContext(), "Gagal Menambahkan penjualan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Response dari server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahStatusPenjualanGudang> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Koneksi Error", Toast.LENGTH_SHORT).show();
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