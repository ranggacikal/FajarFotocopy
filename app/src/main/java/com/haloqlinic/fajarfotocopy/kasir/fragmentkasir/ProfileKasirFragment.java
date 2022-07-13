package com.haloqlinic.fajarfotocopy.kasir.fragmentkasir;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.EditProfileActivity;
import com.haloqlinic.fajarfotocopy.LoginActivity;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.SharedPreference.SharedPreferencedConfig;
import com.thekhaeng.pushdownanim.PushDownAnim;


public class ProfileKasirFragment extends Fragment {
    private SharedPreferencedConfig preferencedConfig;
    TextView txtUsernameProfile, txtLevelProfile, textKembaliKasir;
    CardView cardLogout, cardEditProfile, cardBantuan, cardRating;
    Button btnKeluarKasir;
    Dialog dialogLogout;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_kasir, container, false);

        cardLogout = rootView.findViewById(R.id.card_logout_kasir);
        cardEditProfile = rootView.findViewById(R.id.card_edit_profile_kasir);
        cardBantuan = rootView.findViewById(R.id.card_bantuan_kasir);
        cardRating = rootView.findViewById(R.id.card_rating_kasir);
        txtUsernameProfile = rootView.findViewById(R.id.text_username_profile_kasir);
        txtLevelProfile = rootView.findViewById(R.id.text_level_profile_kasir);
        imageView = rootView.findViewById(R.id.img_profile_kasir);
        btnKeluarKasir = rootView.findViewById(R.id.btn_keluar);
        textKembaliKasir = rootView.findViewById(R.id.text_kembali);
        preferencedConfig = new SharedPreferencedConfig(getActivity());
        Glide.with(getActivity()).load(preferencedConfig.getPreferenceImg()).into(imageView);

        PushDownAnim.setPushDownAnimTo(cardLogout)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogout = new Dialog(getActivity());

                        dialogLogout.setContentView(R.layout.dialog_logout);
                        dialogLogout.setCancelable(false);

                        TextView txtKembali = dialogLogout.findViewById(R.id.text_kembali);
                        Button btnKeluar = dialogLogout.findViewById(R.id.btn_keluar);

                        dialogLogout.show();

                        txtKembali.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogLogout.dismiss();
                            }
                        });

                        btnKeluar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                keluarAkun();
                            }
                        });
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardEditProfile)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), EditProfileActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardRating)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://play.google.com/store/apps/details?id=com.haloqlinic.fajarfotocopy";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardBantuan)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://wa.me/6281317726882?text=Bang%20fajar,%20saya%20butuh%20bantuan.";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
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