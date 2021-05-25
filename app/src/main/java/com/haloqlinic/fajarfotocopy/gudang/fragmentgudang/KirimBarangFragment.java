package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.gudang.ReportPengirimanGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.KirimBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.kirimbaranggudang.TambahStatusPengirimanActivity;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Date;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class KirimBarangFragment extends Fragment {


    public KirimBarangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CardView cardKirimBarang, cardReport;

    SimpleDateFormat formatter;
    Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kirim_barang, container, false);

        cardKirimBarang = rootView.findViewById(R.id.card_kirim_barang);
        cardReport = rootView.findViewById(R.id.card_report_pengiriman);

        PushDownAnim.setPushDownAnimTo(cardKirimBarang)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TambahStatusPengirimanActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardReport)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ReportPengirimanGudangActivity.class));
                    }
                });

        return rootView;
    }
}