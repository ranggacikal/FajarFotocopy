package com.haloqlinic.fajarfotocopy;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.haloqlinic.fajarfotocopy.api.ConfigRetrofit;
import com.haloqlinic.fajarfotocopy.model.ResponseDataBarang;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service{

    String statusStr = "";

    @Override
    public void onStart(Intent intent, int startId) {
        ConfigRetrofit.service.dataBarang().enqueue(new Callback<ResponseDataBarang>() {
            @Override
            public void onResponse(Call<ResponseDataBarang> call, Response<ResponseDataBarang> response) {
                if (response.isSuccessful()){

                    Log.d("serviceDataBarang", "statusResponse: Response Sukses");

                    int status = response.body().getStatus();

                    if (status==1){

                        statusStr = "Berhasil Get Data";

                        Log.d("serviceDataBarang", "status: Berhasil Get Data");

                    }else{
                        statusStr = "Gagal Get Data";
                        Log.d("serviceDataBarang", "status: Gagal Get Data");

                    }

                }else{

                    statusStr = "Response GAGAL";
                    Log.d("serviceDataBarang", "statusResponse: Response GAGAL");

                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarang> call, Throwable t) {

                statusStr = "Koneksi Error";
                Log.d("serviceDataBarang", "onFailure: Koneksi Error");
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
