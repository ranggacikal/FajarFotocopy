package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataMintaBarangAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.FragmentMintaBarangBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.mintabarangketo.TambahBarangKetoActivity;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.DataPermintaanBarangItem;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.ResponseDataPermintaanBarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MintaBarangFragment extends Fragment {

    private FragmentMintaBarangBinding binding;


    public MintaBarangFragment() {
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
        binding = FragmentMintaBarangBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvMintaBarangGudang.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),
                2, GridLayoutManager.VERTICAL, false);
        binding.rvMintaBarangGudang.setLayoutManager(manager);

        binding.swipeRefreshMinta.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMintaBarang();
                binding.swipeRefreshMinta.setRefreshing(false);
            }
        });

        loadMintaBarang();
    }

    private void loadMintaBarang() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data Permintaan Barang");
        progressDialog.show();

        ConfigRetrofit.service.dataPermintaanBarang().enqueue(new Callback<ResponseDataPermintaanBarang>() {
            @Override
            public void onResponse(Call<ResponseDataPermintaanBarang> call, Response<ResponseDataPermintaanBarang> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        binding.rvMintaBarangGudang.setVisibility(View.VISIBLE);
                        List<DataPermintaanBarangItem> dataPermintaan = response.body().getDataPermintaanBarang();
                        DataMintaBarangAdapter adapter = new DataMintaBarangAdapter(getActivity(), dataPermintaan);
                        binding.rvMintaBarangGudang.setAdapter(adapter);

                    }else{
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        binding.rvMintaBarangGudang.setVisibility(View.GONE);
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Response Dari server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPermintaanBarang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Koneksi Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadMintaBarang();
    }
}