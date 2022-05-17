package com.haloqlinic.fajarfotocopy.kasir;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityKasirMainBinding;

public class KasirMainActivity extends AppCompatActivity {

    private ActivityKasirMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKasirMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_kasir, R.id.navigation_riwayat_kasir, R.id.navigation_profile_kasir)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_kasir_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}