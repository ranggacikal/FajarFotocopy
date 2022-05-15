package com.haloqlinic.fajarfotocopy.driver.FragmentRiwayatDriver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.haloqlinic.fajarfotocopy.databinding.FragmentRiwayatDriverBinding;

public class RiwayatDriverFragment extends Fragment {

private FragmentRiwayatDriverBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentRiwayatDriverBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}