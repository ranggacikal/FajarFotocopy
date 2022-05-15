package com.haloqlinic.fajarfotocopy.driver.FragmentHomeDriver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.haloqlinic.fajarfotocopy.databinding.FragmentHomeDriverBinding;

public class HomeDriverFragment extends Fragment {

private FragmentHomeDriverBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


    binding = FragmentHomeDriverBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}