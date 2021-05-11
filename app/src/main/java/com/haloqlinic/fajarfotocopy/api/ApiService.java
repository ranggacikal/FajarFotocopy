package com.haloqlinic.fajarfotocopy.api;

import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.editBarangToko.ResponseEditBarangToko;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.ResponseSearchStockTokoGudang;
import com.haloqlinic.fajarfotocopy.model.stockToko.ResponseDataStockToko;
import com.haloqlinic.fajarfotocopy.model.tambahOutlet.ResponseTambahOutlet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login (@Field("username") String username,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("tambahOutlet")
    Call<ResponseTambahOutlet> tambahOutlet(@Field("id_outlet") String id_outlet,
                                            @Field("nama_outlet") String nama_outlet,
                                            @Field("provinsi") String provinsi,
                                            @Field("kota") String kota,
                                            @Field("kecamatan") String kecamatan,
                                            @Field("kelurahan") String kelurahan,
                                            @Field("kode_pos") String kode_pos);

    @GET("getDataToko")
    Call<ResponseDataToko> dataToko();

    @FormUrlEncoded
    @POST("getStockByToko")
    Call<ResponseDataStockToko> dataStockToko(@Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("searchStockByToko")
    Call<ResponseSearchStockTokoGudang> searchStokTokoGudang(@Field("id_outlet") String id_outlet,
                                                             @Field("nama_barang") String nama_barang);

    @FormUrlEncoded
    @POST("editDataBarangOutlet")
    Call<ResponseEditBarangToko> editBarangToko(@Field("id_barang_outlet") String id_barang_outlet,
                                                @Field("id_barang") String id_barang,
                                                @Field("harga_jual") String harga_jual,
                                                @Field("stock") String stock,
                                                @Field("diskon") String diskon,
                                                @Field("id_outlet") String id_outlet);

}
