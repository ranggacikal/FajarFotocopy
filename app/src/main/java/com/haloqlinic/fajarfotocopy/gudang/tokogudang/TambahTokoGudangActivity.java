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
import com.haloqlinic.fajarfotocopy.formatNumber.NumberTextWatcher;
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
        edtKota = findViewById(R.id.edt_kota_tambah_outlet);
        btnSimpan = findViewById(R.id.btn_simpan_tambah_outlet);

        binding.edtGajiTambahOutlet.addTextChangedListener(new NumberTextWatcher(binding.edtGajiTambahOutlet));

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
        String kota = binding.edtKotaTambahOutlet.getText().toString();
        String persentase = binding.edtPersentaseTambahOutlet.getText().toString();
        String gajiStr = binding.edtGajiTambahOutlet.getText().toString();
        String gaji = gajiStr.replace(".", "").replace(",", "");
        String jumlah_anggota = binding.edtJumlahAnggotaTambahOutlet.getText().toString();
        String alamat = binding.edtAlamatTambahOutlet.getText().toString();

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

        if (kota.isEmpty()){
            binding.edtKotaTambahOutlet.setError("Kota Tidak boleh Kosong");
            binding.edtKotaTambahOutlet.requestFocus();
            return;
        }

        if (persentase.isEmpty()){
            binding.edtPersentaseTambahOutlet.setError("Persentase Tidak boleh Kosong");
            binding.edtPersentaseTambahOutlet.requestFocus();
            return;
        }

        if (gaji.isEmpty()){
            binding.edtGajiTambahOutlet.setError("Gaji Tidak boleh Kosong");
            binding.edtGajiTambahOutlet.requestFocus();
            return;
        }

        if (jumlah_anggota.isEmpty()){
            binding.edtJumlahAnggotaTambahOutlet.setError("Jumlah Anggota Tidak boleh Kosong");
            binding.edtJumlahAnggotaTambahOutlet.requestFocus();
            return;
        }

        if (alamat.isEmpty()){
            binding.edtAlamatTambahOutlet.setError("Alamat Tidak boleh Kosong");
            binding.edtAlamatTambahOutlet.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(TambahTokoGudangActivity.this);
        progressDialog.setMessage("Menambahkan outlet");
        progressDialog.show();

        ConfigRetrofit.service.tambahOutlet(id, nama, kota, persentase, gaji, jumlah_anggota, alamat).enqueue(new Callback<ResponseTambahOutlet>() {
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
                        binding.edtKotaTambahOutlet.setText("");
                        binding.edtPersentaseTambahOutlet.setText("");
                        binding.edtGajiTambahOutlet.setText("");
                        binding.edtJumlahAnggotaTambahOutlet.setText("");
                        binding.edtAlamatTambahOutlet.setText("");
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