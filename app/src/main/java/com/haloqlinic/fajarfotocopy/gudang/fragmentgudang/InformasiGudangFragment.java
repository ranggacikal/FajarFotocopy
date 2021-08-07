package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.informasigudang.DataInformasiGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.informasigudang.TambahInformasiGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class InformasiGudangFragment extends Fragment {

    CardView cardTambahInformasi, cardDataInformasi;



    public InformasiGudangFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_informasi_gudang, container, false);

        cardTambahInformasi = rootView.findViewById(R.id.card_tambah_informasi);
        cardDataInformasi = rootView.findViewById(R.id.card_data_informasi);

        PushDownAnim.setPushDownAnimTo(cardTambahInformasi)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TambahInformasiGudangActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardDataInformasi)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), DataInformasiGudangActivity.class));
                    }
                });

        return rootView;
    }
}