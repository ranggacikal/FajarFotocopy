package com.haloqlinic.fajarfotocopy.gudang.tokogudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPertokoGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTransaksiKasirBinding;
import com.haloqlinic.fajarfotocopy.model.editOutlet.ResponseEditOutlet;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPertokoGudangActivity extends AppCompatActivity {

    private ActivityDataPertokoGudangBinding binding;

    String id_outlet, nama_outlet, kota, persentase, gaji, jumlah_anggota, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataPertokoGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_outlet = getIntent().getStringExtra("id_outlet");
        nama_outlet = getIntent().getStringExtra("nama_outlet");
        kota = getIntent().getStringExtra("kota");
        persentase = getIntent().getStringExtra("persentase");
        jumlah_anggota = getIntent().getStringExtra("jumlah_anggota");
        alamat = getIntent().getStringExtra("alamat");

        binding.edtNamaTokoEditOutlet.setText(nama_outlet);
        binding.edtKotaEditOutlet.setText(kota);
        binding.edtPersentaseEditOutlet.setText(persentase);
        binding.edtJumlahAnggotaEditOutlet.setText(jumlah_anggota);
        binding.edtAlamatEditOutlet.setText(alamat);

        PushDownAnim.setPushDownAnimTo(binding.btnEditDataTokoGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editToko();
                    }
                });
    }

    private void editToko() {

        String nama_edit = binding.edtNamaTokoEditOutlet.getText().toString();
        String kota_edit = binding.edtKotaEditOutlet.getText().toString();
        String presentase_edit = binding.edtPersentaseEditOutlet.getText().toString();
        String anggot_edit = binding.edtPersentaseEditOutlet.getText().toString();
        String alamat_edit = binding.edtAlamatEditOutlet.getText().toString();

        if (nama_edit.isEmpty()){
            binding.edtNamaTokoEditOutlet.setError("Nama Toko Tidak boleh kosong");
            binding.edtNamaTokoEditOutlet.requestFocus();
            return;
        }

        if (kota_edit.isEmpty()){
            binding.edtKotaEditOutlet.setError("Kota Tidak boleh kosong");
            binding.edtKotaEditOutlet.requestFocus();
            return;
        }

        if (presentase_edit.isEmpty()){
            binding.edtPersentaseEditOutlet.setError("Persentase Tidak boleh kosong");
            binding.edtPersentaseEditOutlet.requestFocus();
            return;
        }


        if (anggot_edit.isEmpty()){
            binding.edtJumlahAnggotaEditOutlet.setError("Anggota Tidak boleh kosong");
            binding.edtJumlahAnggotaEditOutlet.requestFocus();
            return;
        }

        if (alamat_edit.isEmpty()){
            binding.edtAlamatEditOutlet.setError("Alamat Tidak boleh kosong");
            binding.edtAlamatEditOutlet.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(DataPertokoGudangActivity.this);
        progressDialog.setMessage("Edit Data Toko");
        progressDialog.show();

        ConfigRetrofit.service.editOutlet(id_outlet, nama_edit, kota_edit, presentase_edit,
                anggot_edit, alamat_edit).enqueue(new Callback<ResponseEditOutlet>() {
            @Override
            public void onResponse(Call<ResponseEditOutlet> call, Response<ResponseEditOutlet> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status==1){

                        finish();
                        Toast.makeText(DataPertokoGudangActivity.this,
                                "Berhasil Edit Data", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(DataPertokoGudangActivity.this,
                                "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DataPertokoGudangActivity.this, "Response Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditOutlet> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataPertokoGudangActivity.this, "Error Koneksi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}