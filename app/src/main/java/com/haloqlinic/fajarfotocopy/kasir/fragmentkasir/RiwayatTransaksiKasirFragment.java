package com.haloqlinic.fajarfotocopy.kasir.fragmentkasir;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haloqlinic.fajarfotocopy.R;

public class RiwayatTransaksiKasirFragment extends Fragment {


    public RiwayatTransaksiKasirFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat_transaksi_kasir, container, false);
    }
}