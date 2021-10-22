package com.haloqlinic.fajarfotocopy.driver.fragmentdriver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.driver.PengirimanSelesaiAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman.RiwayatPengirimanSupplierActivity;
import com.haloqlinic.fajarfotocopy.driver.riwayatPengiriman.RiwayatPengirimanTokoActivity;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.ResponsePengirimanSelesai;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.StatusPengirimanSelesaiItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatDriverFragment extends Fragment {

    public RiwayatDriverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rvSelesai;

    private SharedPreferencedConfig preferencedConfig;

    CardView cardPengirimanKeToko, cardPengirimanKeSupplier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat_driver, container, false);

        cardPengirimanKeToko = view.findViewById(R.id.card_pengiriman_ke_toko);
        cardPengirimanKeSupplier = view.findViewById(R.id.card_pengiriman_ke_supplier);

        PushDownAnim.setPushDownAnimTo(cardPengirimanKeToko)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),
                                RiwayatPengirimanTokoActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardPengirimanKeSupplier)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),
                                RiwayatPengirimanSupplierActivity.class));
                    }
                });

        return view;
    }
}