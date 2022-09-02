package com.haloqlinic.fajarfotocopy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    private static ConfigRetrofit mInstance;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient getLogging(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/")
            .client(getLogging())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    public static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://dev-api.fajar-fotocopy.com/backend_fotocopy/index.php/API_fotocopy/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();

    public static ApiService service = retrofit.create(ApiService.class);

}
