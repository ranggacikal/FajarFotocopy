package com.haloqlinic.fajarfotocopy.gudang.usergudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CariBarangIdAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStokTokoGudangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataTokoAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataUserAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.DataUserLevelAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.SearchCekStockTokoAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.SearchDataUserAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataUserGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityReportPengirimanGudangBinding;
import com.haloqlinic.fajarfotocopy.gudang.baranggudang.DataBarangGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.CekStockTokoGudangActivity;
import com.haloqlinic.fajarfotocopy.gudang.tokogudang.DataTokoGudangActivity;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.SearchBarangByIdItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.dataUser.DataUserItem;
import com.haloqlinic.fajarfotocopy.model.dataUser.ResponseDataUser;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.DataUserByLevelItem;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.ResponseDataUserByLevel;
import com.haloqlinic.fajarfotocopy.model.dataUserByNama.DataUserByNamaItem;
import com.haloqlinic.fajarfotocopy.model.dataUserByNama.ResponseDataUserByNama;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.SearchStockByTokoItem;
import com.haloqlinic.fajarfotocopy.model.stockToko.GetStockByTokoItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DataUserGudangActivity extends AppCompatActivity {

    private ActivityDataUserGudangBinding binding;

    LinearLayout linearDataUserGudang;
    RecyclerView rvDataUserGudang;
    Spinner spinnerDataLevelUserGudang;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    SearchView searchDataUser;


    String nama_lengkap;
    String search_user;

    List<DataUserItem> dataUser;

    private String[] levelItem = {"Kepala Gudang","Karyawan Gudang","Kepala Toko","Karyawan Toko","Driver"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataUserGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackDataUserGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        linearDataUserGudang = findViewById(R.id.linear_recycler_data_user_gudang);
        rvDataUserGudang = findViewById(R.id.recycler_data_user_gudang);
        spinnerDataLevelUserGudang = findViewById(R.id.spinner_data_level_user_gudang);
        progressBar = findViewById(R.id.progressBar_data_user_gudang);
        searchDataUser = findViewById(R.id.search_data_user_gudang);

        progressDialog = new ProgressDialog(DataUserGudangActivity.this);
        progressDialog.setMessage("Memuat Data User...");
        progressDialog.show();

        searchDataUser.setQueryHint("Cari User");
        searchDataUser.setIconified(false);

        searchDataUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search_user = newText;
                loadDataSearch(newText);
                return true;
            }
        });

        rvDataUserGudang.setHasFixedSize(true);
        rvDataUserGudang.setLayoutManager(new LinearLayoutManager(DataUserGudangActivity.this));

        initSpinner();


        spinnerDataLevelUserGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String level = spinnerDataLevelUserGudang.getSelectedItem().toString();

                loadDataUser(level);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadDataSearch(String newText) {
        ConfigRetrofit.service.dataUserByNama(newText).enqueue(new Callback<ResponseDataUserByNama>() {
            @Override
            public void onResponse(Call<ResponseDataUserByNama> call, Response<ResponseDataUserByNama> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    progressBar.setVisibility(View.GONE);
                    linearDataUserGudang.setVisibility(View.VISIBLE);

                    int status = response.body().getStatus();

                    if (status==1) {

                        List<DataUserByNamaItem> dataSearch = response.body().getDataUserByNama();
                        SearchDataUserAdapter adapterSearch = new SearchDataUserAdapter(DataUserGudangActivity.this, dataSearch);
                        rvDataUserGudang.setAdapter(adapterSearch);

                    }else{
                        linearDataUserGudang.setVisibility(View.GONE);
                        Toast.makeText(DataUserGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                    linearDataUserGudang.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(DataUserGudangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUserByNama> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                linearDataUserGudang.setVisibility(View.GONE);
                Toast.makeText(DataUserGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void loadDataUser(String level) {

        ConfigRetrofit.service.dataUserByLevel(level).enqueue(new Callback<ResponseDataUserByLevel>() {
            @Override
            public void onResponse(Call<ResponseDataUserByLevel> call, Response<ResponseDataUserByLevel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    progressBar.setVisibility(View.GONE);
                    linearDataUserGudang.setVisibility(View.VISIBLE);

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataUserByLevelItem> dataUser = response.body().getDataUserByLevel();

                        DataUserLevelAdapter adapter = new DataUserLevelAdapter(DataUserGudangActivity.this, dataUser);
                        rvDataUserGudang.setAdapter(adapter);

                    }else{
                        Toast.makeText(DataUserGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        linearDataUserGudang.setVisibility(View.GONE);  }

                }else{
                    progressBar.setVisibility(View.GONE);
                    linearDataUserGudang.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(DataUserGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUserByLevel> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                linearDataUserGudang.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(DataUserGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void initSpinner() {

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(DataUserGudangActivity.this,
                android.R.layout.simple_spinner_item, levelItem);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDataLevelUserGudang.setAdapter(adapterSpinner);

    }

    private void cariData() {



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataUser(nama_lengkap);
        loadDataSearch(search_user);
    }
}