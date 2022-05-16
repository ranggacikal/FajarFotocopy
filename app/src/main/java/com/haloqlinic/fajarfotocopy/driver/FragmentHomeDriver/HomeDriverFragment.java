package com.haloqlinic.fajarfotocopy.driver.FragmentHomeDriver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.driver.pengirimandriver.PengirimanDriverActivity;
import com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman.PilihTanggalRiwayatDriverActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ReportGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.reportgudang.ReportPenjualanGudangActivity;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.NoSuchElementException;

public class HomeDriverFragment extends Fragment {


    private SharedPreferencedConfig preferencedConfig;
    TextView txtNama, txtTanggal;
    ImageView imageView;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    CardView cardPengirimanTokoDriver, cardPengirimanSupplierDriver;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_driver, container, false);

        txtNama = rootView.findViewById(R.id.text_nama_home_driver);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_driver);
        cardPengirimanTokoDriver = rootView.findViewById(R.id.card_pengiriman_toko_keto_driver);
        cardPengirimanSupplierDriver = rootView.findViewById(R.id.card_pengiriman_supplier_driver);
        imageView = rootView.findViewById(R.id.img_profile_home_driver);


        preferencedConfig = new SharedPreferencedConfig(getActivity());

        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);
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

//                        Intent intent = new Intent(getActivity(), PilihTanggalRiwayatDriverActivity.class);
//                        intent.putExtra("jenisPengiriman", "toko");
//                        intent.putExtra("fromReportDriver", "pengirimanToko");
//                        startActivity(intent);


                    }
                });
        PushDownAnim.setPushDownAnimTo(cardPengirimanSupplierDriver)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent2 = new Intent(getActivity(), PilihTanggalRiwayatDriverActivity.class);
//                        intent2.putExtra("jenisPengiriman", "supplier");
//                        intent2.putExtra("fromReportDriver", "pengirimanSupplier");
//                        startActivity(intent2);
                    }
                });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}