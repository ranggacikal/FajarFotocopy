package com.haloqlinic.fajarfotocopy.gudang.informasigudang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ActivityDataPengirimanBarangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahInformasiGudangBinding;
import com.haloqlinic.fajarfotocopy.databinding.ActivityTambahStatusPengirimanBinding;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.TambahUserGudangActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahInformasiGudangActivity extends AppCompatActivity {

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    private ActivityTambahInformasiGudangBinding binding;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String tanggal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahInformasiGudangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy");

        PushDownAnim.setPushDownAnimTo(binding.linearBackTambahInformasiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTanggalTambahInformasiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imageTambahInformasiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihImage();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahInformasiGudang)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahInformasi();
                    }
                });

    }

    private void tambahInformasi() {
        String image = imageToString();


    }

    private void pilihImage() {
        ImagePicker.with(TambahInformasiGudangActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (49374):
                IntentResult intentResult = IntentIntegrator.parseActivityResult(
                        requestCode, resultCode, data
                );

                if (intentResult.getContents() != null) {

                    binding.edtJudulInformasi.setText(intentResult.getContents());
                    Log.d("requestCodeScan", "onActivityResult: " + requestCode);

                }
                break;
            case (2404):
                if (resultCode == RESULT_OK) {

                    Log.d("requestCodeImg", "onActivityResult: " + requestCode);

                    Uri uri1 = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imageTambahInformasiGudang.setImageBitmap(bitmap);

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahInformasiGudangActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }


    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggal = dateFormatter.format(newDate.getTime());
                binding.textTanggalInformasiGudang.setText(tanggal);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}