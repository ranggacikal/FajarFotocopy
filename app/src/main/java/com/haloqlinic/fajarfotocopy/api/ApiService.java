package com.haloqlinic.fajarfotocopy.api;

import com.haloqlinic.fajarfotocopy.model.ResponseHapusBarang;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariToko.ResponseCariToko;
import com.haloqlinic.fajarfotocopy.model.dataKategori.ResponseDataKategori;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.dataUser.ResponseDataUser;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.ResponseDataUserByLevel;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevelOutlet.ResponseDataUserByOutletLevel;
import com.haloqlinic.fajarfotocopy.model.dataUserByNama.ResponseDataUserByNama;
import com.haloqlinic.fajarfotocopy.model.dataUserByToko.ResponseDataUserByOutlet;
import com.haloqlinic.fajarfotocopy.model.editBarangToko.ResponseEditBarangToko;
import com.haloqlinic.fajarfotocopy.model.editDataBarang.ResponseEditBarang;
import com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman.ResponseLastIdStatusPengiriamn;
import com.haloqlinic.fajarfotocopy.model.hapusBarangToko.ResponseHapusBarangToko;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.ResponseListPengiriman;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.ResponseDataStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;
import com.haloqlinic.fajarfotocopy.model.register.ResponseRegister;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.ResponseSearchStockTokoGudang;
import com.haloqlinic.fajarfotocopy.model.stockToko.ResponseDataStockToko;
import com.haloqlinic.fajarfotocopy.model.tambahBarang.ResponseTambahBarang;
import com.haloqlinic.fajarfotocopy.model.tambahKategori.ResponseTambahKategori;
import com.haloqlinic.fajarfotocopy.model.tambahOutlet.ResponseTambahOutlet;
import com.haloqlinic.fajarfotocopy.model.tambahPengiriman.ResponseTambahPengiriman;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;

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
                                                @Field("harga_jual_pack") String harga_jual_pack,
                                                @Field("stock") String stock,
                                                @Field("jumlah_pack") String jumlah_pack,
                                                @Field("diskon") String diskon,
                                                @Field("diskon_pack") String diskon_pack,
                                                @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("hapusBarangToko")
    Call<ResponseHapusBarangToko> hapusBarangToko(@Field("id_barang_outlet") String id_barang_outlet);

    @FormUrlEncoded
    @POST("searchToko")
    Call<ResponseCariToko> cariToko(@Field("nama_outlet") String nama_outlet);

    @FormUrlEncoded
    @POST("tambahBarang")
    Call<ResponseTambahBarang> tambahBarang(@Field("id_barang") String id_barang,
                                            @Field("nama_barang") String nama_barang,
                                            @Field("stock") String stock,
                                            @Field("harga_modal_gudang") String harga_modal_gudang,
                                            @Field("harga_modal_toko") String harga_modal_toko,
                                            @Field("harga_jual_toko") String harga_jual_toko,
                                            @Field("harga_modal_gudang_pack") String harga_modal_gudang_pack,
                                            @Field("harga_modal_toko_pack") String harga_modal_toko_pack,
                                            @Field("harga_jual_toko_pack") String harga_jual_toko_pack,
                                            @Field("asal_barang") String asal_barang,
                                            @Field("jumlah_pack") String jumlah_pack,
                                            @Field("diskon") String diskon,
                                            @Field("diskon_pack") String diskon_pack,
                                            @Field("image_barang") String image_barang,
                                            @Field("id_kategori_barang") String id_kategori_barang);

    @FormUrlEncoded
    @POST("searchBarangByNama")
    Call<ResponseCariBarangByNama> cariBarang(@Field("nama_barang") String nama_barang);

    @FormUrlEncoded
    @POST("editBarang")
    Call<ResponseEditBarang> editBarang(@Field("id_barang") String id_barang,
                                        @Field("nama_barang") String nama_barang,
                                        @Field("stock") String stock,
                                        @Field("harga_modal_gudang") String harga_modal_gudang,
                                        @Field("harga_modal_toko") String harga_modal_toko,
                                        @Field("harga_jual_toko") String harga_jual_toko,
                                        @Field("harga_modal_gudang_pack") String harga_modal_gudang_pack,
                                        @Field("harga_modal_toko_pack") String harga_modal_toko_pack,
                                        @Field("harga_jual_toko_pack") String harga_jual_toko_pack,
                                        @Field("asal_barang") String asal_barang,
                                        @Field("jumlah_pack") String jumlah_pack,
                                        @Field("diskon") String diskon,
                                        @Field("diskon_pack") String diskon_pack,
                                        @Field("image_barang") String image_barang);

    @FormUrlEncoded
    @POST("hapusBarang")
    Call<ResponseHapusBarang> hapusBarang(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("searchBarangById")
    Call<ResponseCariBarangById> cariBarangById(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("tambahStatusPengiriman")
    Call<ResponseTambahStatusPengiriman> tambahStatusPengiriman(@Field("status_pengiriman") String status,
                                                                @Field("tanggal_pengiriman") String tanggal,
                                                                @Field("id_outlet") String id_outlet);

    @GET("getIdStatusPengiriman")
    Call<ResponseLastIdStatusPengiriamn> lastIdStatusPengiriman();

    @FormUrlEncoded
    @POST("tambahPengiriman")
    Call<ResponseTambahPengiriman> tambahPengiriman(@Field("id_pengiriman") String id_pengiriman,
                                                    @Field("id_barang") String id_barang,
                                                    @Field("jumlah") String jumlah,
                                                    @Field("id_outlet") String id_outlet,
                                                    @Field("id_status_pengiriman") String id_status_pengiriman);

    @FormUrlEncoded
    @POST("getListPengiriman")
    Call<ResponseListPengiriman> listPengiriman(@Field("id_status_pengiriman") String id,
                                                @Field("tanggal_status_pengiriman") String tanggal);

    @GET("listStatusPengiriman")
    Call<ResponseDataStatusPengiriman> statusPengiriman();

    @FormUrlEncoded
    @POST("register")
    Call<ResponseRegister> register(@Field("id_user") String id_user,
                                    @Field("nama_lengkap") String nama_lengkap,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("level") String level,
                                    @Field("id_outlet") String id_outlet,
                                    @Field("foto") String foto);

    @GET("getDataUser")
    Call<ResponseDataUser> dataUser();

    @FormUrlEncoded
    @POST("getDataUserByNama")
    Call<ResponseDataUserByNama> dataUserByNama(@Field("nama_lengkap") String nama_lengkap);

    @FormUrlEncoded
    @POST("getDataUserByLevel")
    Call<ResponseDataUserByLevel> dataUserByLevel(@Field("level") String level);

    @FormUrlEncoded
    @POST("getDataUserByOutlet")
    Call<ResponseDataUserByOutlet> dataUserByOutlet(@Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("getDataUserByOutletAndLevel")
    Call<ResponseDataUserByOutletLevel> dataUserByOutletLevel(@Field("id_outlet") String id_outlet,
                                                              @Field("level") String level);

    @FormUrlEncoded
    @POST("tambahKategori")
    Call<ResponseTambahKategori> tambahKategori(@Field("nama_kategori") String nama_kategori);

    @GET("getKategori")
    Call<ResponseDataKategori> dataKategori();

}
