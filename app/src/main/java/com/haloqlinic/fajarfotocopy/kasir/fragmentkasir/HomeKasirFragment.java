package com.haloqlinic.fajarfotocopy.kasir.fragmentkasir;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.haloqlinic.fajarfotocopy.kasir.transaksikasir.TransaksiKasirActivity;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualan.ResponseTambahStatusPenjualan;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeKasirFragment extends Fragment {



    public HomeKasirFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private SharedPreferencedConfig preferencedConfig;
    TextView txtNama, txtTanggal;
    Button btnKeluar;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    CardView cardToko;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_kasir, container, false);

        txtNama = rootView.findViewById(R.id.text_nama_home_kasir);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_kasir);
        cardToko = rootView.findViewById(R.id.card_transaksi_kasir);

        btnKeluar = rootView.findViewById(R.id.btn_keluar_kasir);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

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
                        tambahStatusPenjualan();
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
        return rootView;


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
                                startActivity(new Intent(getActivity(), TransaksiKasirActivity.class));
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