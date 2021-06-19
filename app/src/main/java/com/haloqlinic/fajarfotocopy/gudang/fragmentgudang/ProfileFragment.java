package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private SharedPreferencedConfig preferencedConfig;
    TextView txtNamaProfile, txtUsernameProfile, txtLevelProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        txtNamaProfile = rootView.findViewById(R.id.text_nama_profile_gudang);
        txtUsernameProfile = rootView.findViewById(R.id.text_username_profile_gudang);
        txtLevelProfile = rootView.findViewById(R.id.text_level_profile_gudang);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        txtNamaProfile.setText(preferencedConfig.getPreferenceNama());
        txtUsernameProfile.setText(preferencedConfig.getPreferenceUsername());
        txtLevelProfile.setText(preferencedConfig.getPreferenceLevel());

        return rootView;



    }
}