package com.haloqlinic.fajarfotocopy.kasir.fragmentkasir;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haloqlinic.fajarfotocopy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RiwayatTransaksiKasirFragment extends Fragment {


    public RiwayatTransaksiKasirFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    TextView txtTanggal;
    RecyclerView rvRiwayat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_riwayat_transaksi_kasir, container, false);

        txtTanggal = rootView.findViewById(R.id.txt_tanggal_riwayat_kasir);
        rvRiwayat = rootView.findViewById(R.id.recycler_riwayat_kasir);

        rvRiwayat.setHasFixedSize(true);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getActivity()));

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        date = dateFormat.format(calendar.getTime());

        txtTanggal.setText(date);



        return rootView;
    }
}