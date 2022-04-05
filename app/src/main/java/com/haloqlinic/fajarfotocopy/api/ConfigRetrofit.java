package com.haloqlinic.fajarfotocopy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    private static ConfigRetrofit mInstance;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    public static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://dev-api.fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();

    public static ApiService service = retrofit.create(ApiService.class);

}
