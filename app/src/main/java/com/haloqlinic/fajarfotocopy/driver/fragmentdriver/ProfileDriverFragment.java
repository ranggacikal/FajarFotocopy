package com.haloqlinic.fajarfotocopy.driver.fragmentdriver;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;


public class ProfileDriverFragment extends Fragment {



    public ProfileDriverFragment() {
        // Required empty public constructor
    }


    private SharedPreferencedConfig preferencedConfig;
    TextView txtUsernameProfile, txtLevelProfile;
    ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_driver, container, false);


        txtUsernameProfile = rootView.findViewById(R.id.text_username_profile_driver);
        txtLevelProfile = rootView.findViewById(R.id.text_level_profile_driver);
        imageView = rootView.findViewById(R.id.img_profile_driver);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        txtUsernameProfile.setText(preferencedConfig.getPreferenceUsername());
        txtLevelProfile.setText(preferencedConfig.getPreferenceLevel());
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);

        return rootView;    }
}
