package com.haloqlinic.fajarfotocopy.driver.FragmentProfileDriver;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ProfileDriverFragment extends Fragment {


    private SharedPreferencedConfig preferencedConfig;
    TextView txtUsernameProfile, txtLevelProfile, textKembaliDriver;
    ImageView imageView;
    Dialog dialogLogout;
    Context context;
    CardView cardLogout;
    Button btnKeluarDriver;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_driver, container, false);


        txtUsernameProfile = rootView.findViewById(R.id.text_username_profile_driver);
        txtLevelProfile = rootView.findViewById(R.id.text_level_profile_driver);
        imageView = rootView.findViewById(R.id.img_profile_driver);
        cardLogout = rootView.findViewById(R.id.card_logout);
        btnKeluarDriver = rootView.findViewById(R.id.btn_keluar_driver);
        textKembaliDriver = rootView.findViewById(R.id.text_kembali_driver);


        preferencedConfig = new SharedPreferencedConfig(getActivity());
        txtUsernameProfile.setText(preferencedConfig.getPreferenceUsername());
        txtLevelProfile.setText(preferencedConfig.getPreferenceLevel());
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);



        PushDownAnim.setPushDownAnimTo(cardLogout)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            new MaterialAlertDialogBuilder(getActivity(),R.style.RoundShapeTheme)
                                    .setTitle("Keluar Akun?")
                                    .setMessage("Anda yakin ingin keluar dari akun ini?")

                                    .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            keluarAkun();
                                        }
                                    })
                                    .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();
                    }
                });


        return rootView;
    }




    private void keluarAkun() {

        Toast.makeText(getActivity(), "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();

    }


}