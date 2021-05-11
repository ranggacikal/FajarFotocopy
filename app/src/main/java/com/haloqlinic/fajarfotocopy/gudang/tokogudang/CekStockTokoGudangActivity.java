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
import com.haloqlinic.fajarfotocopy.model.dataToko.DataTokoItem;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.ResponseSearchStockTokoGudang;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.SearchStockByTokoItem;
import com.haloqlinic.fajarfotocopy.model.stockToko.GetStockByTokoItem;
import com.haloqlinic.fajarfotocopy.model.stockToko.ResponseDataStockToko;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekStockTokoGudangActivity extends AppCompatActivity {

    LinearLayout linearCekStockGudang;
    RecyclerView rvCekStockGudang;
    Spinner spinnerCekStockToko;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    ImageView imgBack;
    SearchView searchCekStock;

    String id_outlet;

    List<DataTokoItem> dataToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_stock_toko_gudang);
        linearCekStockGudang = findViewById(R.id.linear_recycler_cek_stok_gudang);
        rvCekStockGudang = findViewById(R.id.recycler_cek_stock_tokogudang);
        spinnerCekStockToko = findViewById(R.id.spinner_cekstocktokogudang);
        progressBar = findViewById(R.id.progressBar_cek_stok_toko_gudang);
        imgBack = findViewById(R.id.img_back_cek_stock_toko_gudang);
        searchCekStock = findViewById(R.id.search_cek_stok_toko_gudang);

        progressDialog = new ProgressDialog(CekStockTokoGudangActivity.this);
        progressDialog.setMessage("Memuat data outlet");
        progressDialog.show();

        searchCekStock.setQueryHint("Cari Barang");
        searchCekStock.setIconified(false);

        searchCekStock.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

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

                loadDataStock(id_outlet);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadDataSearch(String newText) {

        ConfigRetrofit.service.searchStokTokoGudang(id_outlet, newText).enqueue(new Callback<ResponseSearchStockTokoGudang>() {
            @Override
            public void onResponse(Call<ResponseSearchStockTokoGudang> call, Response<ResponseSearchStockTokoGudang> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    linearCekStockGudang.setVisibility(View.VISIBLE);

                    int status = response.body().getStatus();

                    if (status==1) {

                        List<SearchStockByTokoItem> dataSearch = response.body().getSearchStockByToko();
                        SearchCekStockTokoAdapter adapterSearch = new SearchCekStockTokoAdapter(CekStockTokoGudangActivity.this, dataSearch);
                        rvCekStockGudang.setAdapter(adapterSearch);

                    }else{
                        progressBar.setVisibility(View.GONE);
                        linearCekStockGudang.setVisibility(View.GONE);
                        Toast.makeText(CekStockTokoGudangActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                    linearCekStockGudang.setVisibility(View.GONE);
                    Toast.makeText(CekStockTokoGudangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchStockTokoGudang> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                linearCekStockGudang.setVisibility(View.GONE);
                Toast.makeText(CekStockTokoGudangActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

    private void cariData() {



    }
}