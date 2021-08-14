package com.haloqlinic.fajarfotocopy.driver.fragmentdriver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import com.haloqlinic.fajarfotocopy.driver.DetailDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

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
    CardView cardHomeDriver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_driver, container, false);

        txtNama = rootView.findViewById(R.id.text_nama_home_driver);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_driver);
        btnKeluar = rootView.findViewById(R.id.btn_keluar_driver);
        preferencedConfig = new SharedPreferencedConfig(getActivity());

        txtNama.setText(preferencedConfig.getPreferenceNama());

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        date = dateFormat.format(calendar.getTime());

        txtTanggal.setText(date);

       


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
}