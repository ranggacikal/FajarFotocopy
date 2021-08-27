package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityCekStockTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahTokoGudangBinding;
import com.haloqlinic.fajarfotocopy.model.tambahOutlet.ResponseTambahOutlet;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahTokoGudangActivity extends AppCompatActivity {

    private ActivityTambahTokoGudangBinding binding;


    EditText edtId, edtNama, edtProvinsi, edtKota, edtKecamatan, edtKelurahan, edtKodePos;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahTokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PushDownAnim.setPushDownAnimTo(binding.linearBackTambahTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        edtId = findViewById(R.id.edt_id_tambah_outlet);
        edtNama = findViewById(R.id.edt_nama_tambah_outlet);
        edtProvinsi = findViewById(R.id.edt_provinsi_tambah_outlet);
        edtKota = findViewById(R.id.edt_kota_tambah_outlet);
        edtKecamatan = findViewById(R.id.edt_kecamatan_tambah_outlet);
        edtKelurahan = findViewById(R.id.edt_kelurahan_tambah_outlet);
        edtKodePos = findViewById(R.id.edt_kodepos_tambah_outlet);
        btnSimpan = findViewById(R.id.btn_simpan_tambah_outlet);



        PushDownAnim.setPushDownAnimTo(binding.btnSimpanTambahOutlet)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        simpanTambahOutlet();
                    }
                });
    }

    private void simpanTambahOutlet() {

        String id = edtId.getText().toString();
        String nama = edtNama.getText().toString();
        String provinsi = edtProvinsi.getText().toString();
        String kota = edtKota.getText().toString();
        String kecamatan = edtKecamatan.getText().toString();
        String kelurahan = edtKelurahan.getText().toString();
        String kodePos = edtKodePos.getText().toString();

        if (id.isEmpty()){
            edtId.setError("Id Tidak boleh Kosong");
            edtId.requestFocus();
            return;
        }

        if (nama.isEmpty()){
            edtNama.setError("Nama Tidak boleh Kosong");
            edtNama.requestFocus();
            return;
        }

        if (provinsi.isEmpty()){
            edtProvinsi.setError("Provinsi Tidak boleh Kosong");
            edtProvinsi.requestFocus();
            return;
        }

        if (kota.isEmpty()){
            edtKota.setError("Kota Tidak boleh Kosong");
            edtKota.requestFocus();
            return;
        }

        if (kecamatan.isEmpty()){
            edtKecamatan.setError("Kecamatan Tidak boleh Kosong");
            edtKecamatan.requestFocus();
            return;
        }

        if (kelurahan.isEmpty()){
            edtKelurahan.setError("Kelurahan Tidak boleh Kosong");
            edtKelurahan.requestFocus();
            return;
        }

        if (kodePos.isEmpty()){
            edtKodePos.setError("Kdoe pos Tidak boleh Kosong");
            edtKodePos.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(TambahTokoGudangActivity.this);
        progressDialog.setMessage("Menambahkan outlet");
        progressDialog.show();

        ConfigRetrofit.service.tambahOutlet(id, nama, provinsi, kota, kecamatan, kelurahan, kodePos).enqueue(new Callback<ResponseTambahOutlet>() {
            @Override
            public void onResponse(Call<ResponseTambahOutlet> call, Response<ResponseTambahOutlet> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){
                        progressDialog.dismiss();
                        Toast.makeText(TambahTokoGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        edtId.setText("");
                        edtNama.setText("");
                        edtProvinsi.setText("");
                        edtKota.setText("");
                        edtKecamatan.setText("");
                        edtKelurahan.setText("");
                        edtKodePos.setText("");
                        edtId.requestFocus();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(TambahTokoGudangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TambahTokoGudangActivity.this, "Terjadi Kesalahan Di Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahOutlet> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahTokoGudangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}