package com.haloqlinic.fajarfotocopy.driver.FragmentRiwayatDriver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.FragmentRiwayatDriverBinding;
import com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman.PilihTanggalRiwayatDriverActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class RiwayatDriverFragment extends Fragment {


    CardView cardPengirimanKeToko, cardPengirimanKeSupplier;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_riwayat_driver, container, false);

        cardPengirimanKeToko = rootView.findViewById(R.id.card_pengiriman_ke_toko);
        cardPengirimanKeSupplier = rootView.findViewById(R.id.card_pengiriman_ke_supplier);

        PushDownAnim.setPushDownAnimTo(cardPengirimanKeToko)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentReportToko = new Intent(getActivity(),
                                PilihTanggalRiwayatDriverActivity.class);
                        intentReportToko.putExtra("fromReportDriver", "toko");
                        startActivity(intentReportToko);
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardPengirimanKeSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentReportSupplier = new Intent(getActivity(),
                                PilihTanggalRiwayatDriverActivity.class);
                        intentReportSupplier.putExtra("fromReportDriver", "supplier");
                        startActivity(intentReportSupplier);
                    }
                });

        return rootView;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}