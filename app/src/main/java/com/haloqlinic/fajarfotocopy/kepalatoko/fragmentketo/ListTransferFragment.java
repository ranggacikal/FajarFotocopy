package com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.fajarfotocopy.adapter.kepalaToko.StatusTransferAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.FragmentListTransferBinding;
import com.haloqlinic.fajarfotocopy.model.listStatusTransfer.ListStatusTransferItem;
import com.haloqlinic.fajarfotocopy.model.listStatusTransfer.ResponseListStatusTransfer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTransferFragment extends Fragment {


    public ListTransferFragment() {
        // Required empty public constructor
    }

    private FragmentListTransferBinding binding;
    private SharedPreferencedConfig preferencedConfig;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListTransferBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencedConfig = new SharedPreferencedConfig(getActivity());
        binding.recyclerListTransferKeto.setHasFixedSize(true);
        binding.recyclerListTransferKeto.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();
    }

    private void loadData() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.listStatusTransfer(preferencedConfig.getPreferenceIdOutlet())
                .enqueue(new Callback<ResponseListStatusTransfer>() {
                    @Override
                    public void onResponse(Call<ResponseListStatusTransfer> call, Response<ResponseListStatusTransfer> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();

                            int status = response.body().getStatus();

                            if (status==1){

                                List<ListStatusTransferItem> dataStatus = response.body().getListStatusTransfer();
                                StatusTransferAdapter adapter = new StatusTransferAdapter(getActivity(), dataStatus);
                                binding.recyclerListTransferKeto.setAdapter(adapter);

                            }else{
                                Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Terjadi kesalahan diserver", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListStatusTransfer> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Koneksi Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}