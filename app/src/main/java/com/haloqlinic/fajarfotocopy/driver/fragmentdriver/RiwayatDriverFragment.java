package com.haloqlinic.fajarfotocopy.driver.fragmentdriver;

import android.app.ProgressDialog;
import android.os.Bundle;

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
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.ResponsePengirimanSelesai;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.StatusPengirimanSelesaiItem;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat_driver, container, false);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        rvSelesai = view.findViewById(R.id.rv_selesai_driver);
        rvSelesai.setHasFixedSize(true);
        rvSelesai.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();

        return view;
    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.pengirimanSelesai(preferencedConfig.getPreferenceIdUser()).enqueue(new Callback<ResponsePengirimanSelesai>() {
            @Override
            public void onResponse(Call<ResponsePengirimanSelesai> call, Response<ResponsePengirimanSelesai> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        List<StatusPengirimanSelesaiItem> dataSelesai = response.body().getStatusPengirimanSelesai();
                        PengirimanSelesaiAdapter adapter = new PengirimanSelesaiAdapter(getActivity(), dataSelesai);
                        rvSelesai.setAdapter(adapter);

                    }else{
                        Toast.makeText(getActivity(),
                                "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengirimanSelesai> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}