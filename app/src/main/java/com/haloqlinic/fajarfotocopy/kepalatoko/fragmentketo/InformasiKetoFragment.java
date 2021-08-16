package com.haloqlinic.fajarfotocopy.kepalatoko.fragmentketo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haloqlinic.fajarfotocopy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformasiKetoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformasiKetoFragment extends Fragment {


    public InformasiKetoFragment() {
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
        return inflater.inflate(R.layout.fragment_informasi_keto, container, false);
    }
}