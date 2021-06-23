package com.haloqlinic.fajarfotocopy.gudang.fragmentgudang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;

import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private SharedPreferencedConfig preferencedConfig;
    TextView txtUsernameProfile, txtLevelProfile;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        txtUsernameProfile = rootView.findViewById(R.id.text_username_profile_gudang);
        txtLevelProfile = rootView.findViewById(R.id.text_level_profile_gudang);
        imageView = rootView.findViewById(R.id.img_profile);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        txtUsernameProfile.setText(preferencedConfig.getPreferenceUsername());
        txtLevelProfile.setText(preferencedConfig.getPreferenceLevel());
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);

        return rootView;


    }
}