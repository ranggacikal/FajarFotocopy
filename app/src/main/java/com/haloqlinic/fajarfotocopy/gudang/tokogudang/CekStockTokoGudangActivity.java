package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.haloqlinic.fajarfotocopy.adapter.gudang.CekStokTokoGudangAdapter;
import com.haloqlinic.fajarfotocopy.adapter.gudang.SearchCekStockTokoAdapter;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCekStockTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.ResponseSearchStockTokoGudang;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.SearchStockByTokoItem;
import com.haloqlinic.fajarfotocopy.model.stockToko.GetStockByTokoItem;
import com.haloqlinic.fajarfotocopy.model.stockToko.ResponseDataStockToko;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CekStockTokoGudangActivity extends AppCompatActivity {

    private ActivityCekStockTokoGudangBinding binding;


    LinearLayout linearCekStockGudang;
    RecyclerView rvCekStockGudang;
    Spinner spinnerCekStockToko;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    ImageView imgBack;
    SearchView searchCekStock;

    String id_outlet;
    String search_barang;

    List<DataTokoItem> dataToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCekStockTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackCekStockTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


        linearCekStockGudang = findViewById(R.id.linear_recycler_cek_stok_gudang);
        rvCekStockGudang = findViewById(R.id.recycler_cek_stock_tokogudang);
        spinnerCekStockToko = findViewById(R.id.spinner_cekstocktokogudang);
        progressBar = findViewById(R.id.progressBar_cek_stok_toko_gudang);
        imgBack = findViewById(R.id.img_back_cek_stock_toko_gudang);
        searchCekStock = findViewById(R.id.search_cek_stok_toko_gudang);



        progressDialog = new ProgressDialog(CekStockTokoGudangActivity.this);
        progressDialog.setMessage("Memuat data outlet");
        progressDialog.show();

        searchCekStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCekStock.setQueryHint("Cari Barang");
                searchCekStock.setIconified(false);
            }
        });

        searchCekStock.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search_barang = newText;
                loadDataSearch(newText);
                return true;
            }
        });

        rvCekStockGudang.setHasFixedSize(true);
        rvCekStockGudang.setLayoutManager(new LinearLayoutManager(CekStockTokoGudangActivity.this));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initSpinner();

        spinnerCekStockToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nama_toko = dataToko.get(position).getNamaOutlet();
                id_outlet = dataToko.get(position).getIdOutlet();
                searchCekStock.setVisibility(View.VISIBLE);

//                loadDataStock(id_outlet);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadDataSearch(String newText) {

        if (newText.equals("")){
            rvCekStockGudang.setVisibility(View.GONE);
            binding.txtDataKosong.setVisibility(View.GONE);
        }else {

            ConfigRetrofit.service.searchStokTokoGudang(id_outlet, newText).enqueue(new Callback<ResponseSearchStockTokoGudang>() {
                @Override
                public void onResponse(Call<ResponseSearchStockTokoGudang> call, Response<ResponseSearchStockTokoGudang> response) {
                    if (response.isSuccessful()) {

                        progressBar.setVisibility(View.GONE);
                        linearCekStockGudang.setVisibility(View.VISIBLE);
                        rvCekStockGudang.setVisibility(View.VISIBLE);

                        int status = response.body().getStatus();

                        if (status == 1) {

                            List<SearchStockByTokoItem> dataSearch = response.body().getSearchStockByToko();
                            SearchCekStockTokoAdapter adapterSearch = new SearchCekStockTokoAdapter(CekStockTokoGudangActivity.this, dataSearch);
                            rvCekStockGudang.setAdapter(adapterSearch);
                            binding.txtDataKosong.setVisibility(View.GONE);

                        } else {
                            rvCekStockGudang.setVisibility(View.GONE);
                            binding.txtDataKosong.setVisibility(View.VISIBLE);
                        }

                    } else {
                        progressBar.setVisibility(View.GONE);
                        linearCekStockGudang.setVisibility(View.GONE);
                        Toast.makeText(CekStockTokoGudangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseSearchStockTokoGudang> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    linearCekStockGudang.setVisibility(View.GONE);
                    Toast.makeText(CekStockTokoGudangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void loadDataStock(String id_outlet) {

        ConfigRetrofit.service.dataStockToko(id_outlet).enqueue(new Callback<ResponseDataStockToko>() {
            @Override
            public void onResponse(Call<ResponseDataStockToko> call, Response<ResponseDataStockToko> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    linearCekStockGudang.setVisibility(View.VISIBLE);

                    int status = response.body().getStatus();

                    if (status==1){

                        List<GetStockByTokoItem> dataStock = response.body().getGetStockByToko();

                        CekStokTokoGudangAdapter adapter = new CekStokTokoGudangAdapter(CekStockTokoGudangActivity.this, dataStock);
                        rvCekStockGudang.setAdapter(adapter);

                    }else{
                        Toast.makeText(CekStockTokoGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                        linearCekStockGudang.setVisibility(View.GONE);
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                    linearCekStockGudang.setVisibility(View.GONE);
                    Toast.makeText(CekStockTokoGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataStockToko> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                linearCekStockGudang.setVisibility(View.GONE);
                Toast.makeText(CekStockTokoGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initSpinner() {

        ConfigRetrofit.service.dataToko().enqueue(new Callback<ResponseDataToko>() {
            @Override
            public void onResponse(Call<ResponseDataToko> call, Response<ResponseDataToko> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        dataToko = response.body().getDataToko();
                        List<String> listSpinnerToko = new ArrayList<String>();

                        for (int i = 0; i<dataToko.size(); i++){

                            listSpinnerToko.add(dataToko.get(i).getNamaOutlet());

                        }

                        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(CekStockTokoGudangActivity.this,
                                android.R.layout.simple_spinner_item, listSpinnerToko);
                        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCekStockToko.setAdapter(adapterSpinner);

                    }else{
                        Toast.makeText(CekStockTokoGudangActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(CekStockTokoGudangActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataToko> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CekStockTokoGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (search_barang!=null){
            loadDataSearch(search_barang);
        }
    }
}