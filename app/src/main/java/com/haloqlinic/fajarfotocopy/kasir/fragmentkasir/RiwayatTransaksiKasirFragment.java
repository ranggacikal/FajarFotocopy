package com.haloqlinic.fajarfotocopy.kasir.fragmentkasir;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.kasir.RiwayatKasirAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari.ResponseStatusPenjualanByHari;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari.StatusPenjualanByHariItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        loadData();



        return rootView;
    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.statusPenjualanByHari(date).enqueue(new Callback<ResponseStatusPenjualanByHari>() {
            @Override
            public void onResponse(Call<ResponseStatusPenjualanByHari> call, Response<ResponseStatusPenjualanByHari> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<StatusPenjualanByHariItem> dataRiwayat = response.body().getStatusPenjualanByHari();
                        RiwayatKasirAdapter adapter = new RiwayatKasirAdapter(getActivity(), dataRiwayat);
                        rvRiwayat.setAdapter(adapter);

                    }else{
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusPenjualanByHari> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}