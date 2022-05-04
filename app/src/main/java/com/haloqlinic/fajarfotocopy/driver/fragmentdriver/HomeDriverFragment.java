package com.haloqlinic.fajarfotocopy.driver.fragmentdriver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.haloqlinic.fajarfotocopy.adapter.driver.StatusPengirimanDriverAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.driver.DetailDriverActivity;
import com.haloqlinic.fajarfotocopy.driver.pengirimandriver.PengirimanDriverActivity;
import com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman.PilihTanggalRiwayatDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.transferbaranggudang.TransferBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.GetStatusPengirimanByIdUserItem;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.ResponseStatusPengirimanByIdUser;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDriverFragment extends Fragment {



    public HomeDriverFragment() {
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
    CardView cardPengirimanTokoDriver, cardPengirimanSupplierDriver;
    RecyclerView rvStatusPengiriman;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_driver, container, false);

        txtNama = rootView.findViewById(R.id.text_nama_home_driver);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_driver);
        btnKeluar = rootView.findViewById(R.id.btn_keluar_driver);
        cardPengirimanTokoDriver = rootView.findViewById(R.id.card_pengiriman_toko_keto_driver);
        cardPengirimanSupplierDriver = rootView.findViewById(R.id.card_pengiriman_supplier_driver);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

//        rvStatusPengiriman.setHasFixedSize(true);
//        rvStatusPengiriman.setLayoutManager(new LinearLayoutManager(getActivity()));

//        loadDataStatusPengiriman();

        txtNama.setText(preferencedConfig.getPreferenceNama());

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        date = dateFormat.format(calendar.getTime());

        txtTanggal.setText(date);

        PushDownAnim.setPushDownAnimTo(cardPengirimanTokoDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), PilihTanggalRiwayatDriverActivity.class);
                        intent.putExtra("jenisPengiriman", "toko");
                        intent.putExtra("fromReportDriver", "pengirimanToko");
                        startActivity(intent);

                    }
                });
        PushDownAnim.setPushDownAnimTo(cardPengirimanSupplierDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(getActivity(), PilihTanggalRiwayatDriverActivity.class);
                        intent2.putExtra("jenisPengiriman", "supplier");
                        intent2.putExtra("fromReportDriver", "pengirimanSupplier");
                        startActivity(intent2);
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


    private void keluarAkun() {

        Toast.makeText(getActivity(), "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();

    }

    @Override
    public void onResume() {
        super.onResume();
//        loadDataStatusPengiriman();
    }
}