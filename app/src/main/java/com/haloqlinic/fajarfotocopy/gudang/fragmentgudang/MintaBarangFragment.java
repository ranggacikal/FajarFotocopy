package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.FragmentMintaBarangBinding;

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
}