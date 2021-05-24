package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.BarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.TokoGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SharedPreferencedConfig preferencedConfig;
    TextView txtNama, txtTanggal;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    CardView cardToko, cardBarang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        txtNama = rootView.findViewById(R.id.text_nama_home_gudang);
        txtTanggal = rootView.findViewById(R.id.text_tanggal_home_gudang);
        cardToko = rootView.findViewById(R.id.card_outlet_gudang);
        cardBarang = rootView.findViewById(R.id.card_kelola_barang_gudang);

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
                        startActivity(new Intent(getActivity(), TokoGudangActivity.class));
                    }
                });


        PushDownAnim.setPushDownAnimTo(cardBarang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), BarangGudangActivity.class));
                    }
                });

        return rootView;
    }
}