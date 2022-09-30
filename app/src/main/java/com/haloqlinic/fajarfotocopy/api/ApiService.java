package com.haloqlinic.fajarfotocopy.api;

import com.haloqlinic.fajarfotocopy.model.ResponseDataBarang;
import com.haloqlinic.fajarfotocopy.model.ResponseHapusBarang;
import com.haloqlinic.fajarfotocopy.model.ResponsePenjualanKaryawanToko;
import com.haloqlinic.fajarfotocopy.model.dataBarangOutletList.ResponseBarangOutletList;
import com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet.ResponseMintaBarangByOutlet;
import com.haloqlinic.fajarfotocopy.model.editJumlahPackOutlet.ResponseEditJumlahPackOutlet;
import com.haloqlinic.fajarfotocopy.model.editPackBarang.ResponseEditPackBarang;
import com.haloqlinic.fajarfotocopy.model.editProfile.ResponseEditProfile;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanBarang.ResponseEditStatusPenjualanBarang;
import com.haloqlinic.fajarfotocopy.model.getKategoriById.ResponseKategoriById;
import com.haloqlinic.fajarfotocopy.model.hapusBarangPenjualan.ResponseHapusBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusDataMintaBarang.ResponseHapusDataMintaBarang;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualan.ResponseHapusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPengiriman.ResponseHapusStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.insertReportToko.ResponseInsertReportToko;
import com.haloqlinic.fajarfotocopy.model.statusSupplierBulan.ResponseStatusSupplierByBulan;
import com.haloqlinic.fajarfotocopy.model.cariBarangById.ResponseCariBarangById;
import com.haloqlinic.fajarfotocopy.model.cariBarangByNama.ResponseCariBarangByNama;
import com.haloqlinic.fajarfotocopy.model.cariToko.ResponseCariToko;
import com.haloqlinic.fajarfotocopy.model.cekBarangToko.ResponseCekBarangToko;
import com.haloqlinic.fajarfotocopy.model.countBarangTransfer.ResponseCountBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.dataKategori.ResponseDataKategori;
import com.haloqlinic.fajarfotocopy.model.dataKategoriDesc.ResponseDataKategoriDesc;
import com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang.ResponseDataPermintaanBarang;
import com.haloqlinic.fajarfotocopy.model.dataToko.ResponseDataToko;
import com.haloqlinic.fajarfotocopy.model.dataUser.ResponseDataUser;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.ResponseDataUserByLevel;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevelOutlet.ResponseDataUserByOutletLevel;
import com.haloqlinic.fajarfotocopy.model.dataUserByNama.ResponseDataUserByNama;
import com.haloqlinic.fajarfotocopy.model.dataUserByToko.ResponseDataUserByOutlet;
import com.haloqlinic.fajarfotocopy.model.detailPenjualanGudang.ResponseDetailPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan.ResponseDetailStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.detailTransferBarang.ResponseDetailTransferBarang;
import com.haloqlinic.fajarfotocopy.model.editBarangOutlet.ResponseEditBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.editBarangToko.ResponseEditBarangToko;
import com.haloqlinic.fajarfotocopy.model.editDataBarang.ResponseEditBarang;
import com.haloqlinic.fajarfotocopy.model.editFirebaseToken.ResponseEditFirebaseToken;
import com.haloqlinic.fajarfotocopy.model.editImageUser.ResponseEditImageUser;
import com.haloqlinic.fajarfotocopy.model.editKategori.ResponseEditKategori;
import com.haloqlinic.fajarfotocopy.model.editOutlet.ResponseEditOutlet;
import com.haloqlinic.fajarfotocopy.model.editPengiriman.ResponseEditPengiriman;
import com.haloqlinic.fajarfotocopy.model.editStatusBarangTransfer.ResponseEditStatusBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.editStatusPenjualanGudang.ResponseEditStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.editStatusTransfer.ResponseEditStatusTransfer;
import com.haloqlinic.fajarfotocopy.model.editStatusTransferBarang.ResponseEditStatusTransferBarang;
import com.haloqlinic.fajarfotocopy.model.editStock.ResponseEditStock;
import com.haloqlinic.fajarfotocopy.model.editStockBarangOutlet.ResponseEditStockBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.editUser.ResponseEditUser;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualan.ResponseDataBarangPenjualan;
import com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang.ResponseBarangPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.getDriver.ResponseDataDriver;
import com.haloqlinic.fajarfotocopy.model.getIdBarangOutlet.ResponseGetIdBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan.ResponseGetIdStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman.ResponseLastIdStatusPengiriamn;
import com.haloqlinic.fajarfotocopy.model.hapusBarangToko.ResponseHapusBarangToko;
import com.haloqlinic.fajarfotocopy.model.hapusBarangTransfer.ResponseHapusBarangTransfer;
import com.haloqlinic.fajarfotocopy.model.hapusKategori.ResponseHapusKategori;
import com.haloqlinic.fajarfotocopy.model.hapusMintaBarang.ResponseHapusMintaBarang;
import com.haloqlinic.fajarfotocopy.model.hapusOutlet.ResponseHapusOutlet;
import com.haloqlinic.fajarfotocopy.model.hapusPengiriman.ResponseHapusPengiriman;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanByIdStatus.ResponseHapusPenjualanGudangByIdStatus;
import com.haloqlinic.fajarfotocopy.model.hapusPenjualanGudang.ResponseHapusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualan.ResponseHapusStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.hapusStatusPenjualanGudang.ResponseHapusStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.hapusStatusTransfer.ResponseHapusStatusTransfer;
import com.haloqlinic.fajarfotocopy.model.hapusTransferCancel.ResponseHapusTransferCancel;
import com.haloqlinic.fajarfotocopy.model.hapusUser.ResponseHapusUser;
import com.haloqlinic.fajarfotocopy.model.listPengiriman.ResponseListPengiriman;
import com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus.ResponseListPenjualanGudangByIdStatus;
import com.haloqlinic.fajarfotocopy.model.listStatusPengiriman.ResponseDataStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver.ResponsePengirimanByIdUser;
import com.haloqlinic.fajarfotocopy.model.listStatusTransfer.ResponseListStatusTransfer;
import com.haloqlinic.fajarfotocopy.model.listTransfer.ResponseListTransfer;
import com.haloqlinic.fajarfotocopy.model.login.ResponseLogin;
import com.haloqlinic.fajarfotocopy.model.mintaBarang.ResponseMintaBarang;
import com.haloqlinic.fajarfotocopy.model.pengirimanSelesai.ResponsePengirimanSelesai;
import com.haloqlinic.fajarfotocopy.model.register.ResponseRegister;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletById.ResponseBarangOutletById;
import com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama.ResponseBarangOutletByNama;
import com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang.ResponseSearchStockTokoGudang;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByBulan.ResponseStatusPengirimanByBulan;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser.ResponseStatusPengirimanByIdUser;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByTanggal.ResponseStatusPengirimanByTanggal;
import com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko.ResponseStatusPengirimanByToko;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanByBulan.ResponseStatusPenjualanByBulan;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari.ResponseStatusPenjualanByHari;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangByIdUser.ResponseStatusPenjualanGudangByIdUser;
import com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangSelesai.ResponseStatusPenjualanGudangSelesai;
import com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal.ResponseStatusSupplierTanggal;
import com.haloqlinic.fajarfotocopy.model.statusTransferByBulan.ResponseStatusTransferByBulan;
import com.haloqlinic.fajarfotocopy.model.stockByIdBarang.ResponseStockByIdBarang;
import com.haloqlinic.fajarfotocopy.model.stockToko.ResponseDataStockToko;
import com.haloqlinic.fajarfotocopy.model.sumPenjualanGudang.ResponseSumPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.sumTransaksiBulan.ResponseSumTransaksiBulan;
import com.haloqlinic.fajarfotocopy.model.sumTransaksiHari.ResponseSumTransaksiHari;
import com.haloqlinic.fajarfotocopy.model.tambahBarang.ResponseTambahBarang;
import com.haloqlinic.fajarfotocopy.model.tambahBarangOutlet.ResponseTambahBarangOutlet;
import com.haloqlinic.fajarfotocopy.model.tambahKategori.ResponseTambahKategori;
import com.haloqlinic.fajarfotocopy.model.tambahOutlet.ResponseTambahOutlet;
import com.haloqlinic.fajarfotocopy.model.tambahPengeluaranOutlet.ResponseTambahPengeluaranOutlet;
import com.haloqlinic.fajarfotocopy.model.tambahPengiriman.ResponseTambahPengiriman;
import com.haloqlinic.fajarfotocopy.model.tambahPenjualan.ResponseTambahPenjualan;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPengiriman.ResponseTambahStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualan.ResponseTambahStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.tambahStatusPenjualanGudang.ResponseTambahStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.tambahStatusTransfer.ResponseTambahStatusTransfer;
import com.haloqlinic.fajarfotocopy.model.tambahTransferBarang.ResponseTambahTransferBarang;
import com.haloqlinic.fajarfotocopy.model.transaksiByBulan.ResponseTransaksiByBulan;
import com.haloqlinic.fajarfotocopy.model.transaksiByHari.ResponseTransaksiByHari;
import com.haloqlinic.fajarfotocopy.model.updatePassword.ResponsePassword;
import com.haloqlinic.fajarfotocopy.model.updateStatusPengiriman.ResponseUpdateStatusPengiriman;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualan.ResponseUpdateStatusPenjualan;
import com.haloqlinic.fajarfotocopy.model.updateStatusPenjualanGudang.ResponseUpdateStatusPenjualanGudang;
import com.haloqlinic.fajarfotocopy.model.updateStockPengiriman.ResponseUpdateStockPengiriman;
import com.haloqlinic.fajarfotocopy.model.validateBarang.ResponseValidateBarang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login(@Field("username") String username,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("tambahOutlet")
    Call<ResponseTambahOutlet> tambahOutlet(@Field("id_outlet") String id_outlet,
                                            @Field("nama_outlet") String nama_outlet,
                                            @Field("kota") String kota,
                                            @Field("persentase") String persentase,
                                            @Field("jumlah_anggota") String jumlah_anggota,
                                            @Field("alamat") String alamat);

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
                                                @Field("number_of_pack") String number_of_pack,
                                                @Field("diskon") String diskon);

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
                                            @Field("number_of_pack") String number_of_pack,
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
                                        @Field("number_of_pack") String number_of_pack,
                                        @Field("image_link") String image_link,
                                        @Field("image_barang") String image_barang,
                                        @Field("id_kategori_barang") String id_kategori);

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
                                                                @Field("id_outlet") String id_outlet,
                                                                @Field("driver_id") String driver_id);

    @GET("getIdStatusPengiriman")
    Call<ResponseLastIdStatusPengiriamn> lastIdStatusPengiriman();

    @FormUrlEncoded
    @POST("tambahPengiriman")
    Call<ResponseTambahPengiriman> tambahPengiriman(@Field("id_barang") String id_barang,
                                                    @Field("jumlah") String jumlah,
                                                    @Field("jumlah_pack") String jumlah_pack,
                                                    @Field("number_of_pack") String number_of_pack,
                                                    @Field("id_outlet") String id_outlet,
                                                    @Field("id_status_pengiriman") String id_status_pengiriman,
                                                    @Field("status_barang") String status_barang);

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

    @GET("getKategoriDesc")
    Call<ResponseDataKategoriDesc> dataKategoriDesc();

    @FormUrlEncoded
    @POST("hapusKategori")
    Call<ResponseHapusKategori> hapusKategori(@Field("id_kategori") String id_kategori);

    @FormUrlEncoded
    @POST("editKategori")
    Call<ResponseEditKategori> editKtegori(@Field("id_kategori") String id_kategori,
                                           @Field("nama_kategori") String nama_kategori);

    @FormUrlEncoded
    @POST("listStatusPengirimanByToko")
    Call<ResponseStatusPengirimanByToko> statusPengirimanByToko(@Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("tambahBarangOutlet")
    Call<ResponseTambahBarangOutlet> tambahBarangOutlet(@Field("id_barang_outlet") String id_barang_outlet,
                                                        @Field("id_barang") String id_barang,
                                                        @Field("harga_jual") String harga_jual,
                                                        @Field("harga_jual_pack") String harga_jual_pack,
                                                        @Field("stock") String stock,
                                                        @Field("jumlah_pack") String jumlah_pack,
                                                        @Field("number_of_pack") String number_of_pack,
                                                        @Field("diskon") String diskon,
                                                        @Field("diskon_pack") String diskon_pack,
                                                        @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("hapusPengiriman")
    Call<ResponseHapusPengiriman> hapusPengiriman(@Field("id_pengiriman") String id_pengiriman);

    @FormUrlEncoded
    @POST("editPengiriman")
    Call<ResponseEditPengiriman> editPengiriman(@Field("id_pengiriman") String id_pengiriman,
                                                @Field("id_barang") String id_barang,
                                                @Field("jumlah") String jumlah,
                                                @Field("jumlah_pack") String jumlah_pack,
                                                @Field("id_outlet") String id_outlet,
                                                @Field("id_status_pengiriman") String id_status_pengiriman,
                                                @Field("status_barang") String status_barang);

    @FormUrlEncoded
    @POST("tambahStatusPenjualan")
    Call<ResponseTambahStatusPenjualan> tambahStatusPenjualan(@Field("id_status_penjualan") String id_status_penjualan,
                                                              @Field("status_penjualan") String status_penjualan,
                                                              @Field("tanggal_penjualan") String tanggal_penjualan,
                                                              @Field("id_outlet") String id_outlet);

    @GET("getIdStatusPenjualan")
    Call<ResponseGetIdStatusPenjualan> getIdStatusPenjualan();

    @FormUrlEncoded
    @POST("hapusStatusPenjualan")
    Call<ResponseHapusStatusPenjualan> hapusStatusPenjualan(@Field("id_status_penjualan") String id_status_penjualan);

    @FormUrlEncoded
    @POST("searchBarangOutletByNama")
    Call<ResponseBarangOutletByNama> barangOutletByNama(@Field("nama_barang") String nama_barang,
                                                        @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("searchBarangOutletById")
    Call<ResponseBarangOutletById> barangOutletById(@Field("id_barang") String id_barang,
                                                    @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("tambahPenjualan")
    Call<ResponseTambahPenjualan> tambahPenjualan(@Field("id_penjualan") String id_penjualan,
                                                  @Field("id_barang_outlet") String id_barang_outlet,
                                                  @Field("id_barang") String id_barang,
                                                  @Field("jumlah_barang") String jumlah_barang,
                                                  @Field("jumlah_pack") String jumlah_pack,
                                                  @Field("total") String total,
                                                  @Field("tanggal_penjualan") String tanggal_penjualan,
                                                  @Field("nama_kasir") String nama_kasir,
                                                  @Field("id_status_penjualan") String id_status_penjualan,
                                                  @Field("jenis_satuan") String jenis_satuan,
                                                  @Field("status_penjualan_barang") String status_penjualan_barang);

    @FormUrlEncoded
    @POST("getBarangPenjualan")
    Call<ResponseDataBarangPenjualan> dataBarangPenjualan(@Field("id_status_penjualan") String id_status_penjualan);

    @FormUrlEncoded
    @POST("editStatusPembayaran")
    Call<ResponseUpdateStatusPenjualan> updateStatusPenjualan(@Field("id_status_penjualan") String id_status_penjualan,
                                                              @Field("tanggal_penjualan") String tanggal_penjualan,
                                                              @Field("status_penjualan") String status_penjualan,
                                                              @Field("id_outlet") String id_outlet,
                                                              @Field("metode_bayar") String metode_bayar,
                                                              @Field("total_harga") String total_harga,
                                                              @Field("jumlah_diskon") String jumlah_diskon,
                                                              @Field("image_pembayaran") String image_pembayaran);

    @FormUrlEncoded
    @POST("getTransaksiByBulan")
    Call<ResponseTransaksiByBulan> transaksiByBulan(@Field("bulan") String bulan,
                                                    @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("getTransaksiByHari")
    Call<ResponseTransaksiByHari> transaksiByHari(@Field("hari") String hari,
                                                  @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("getStatusPenjualanByBulan")
    Call<ResponseStatusPenjualanByBulan> statusPenjualanByBulan(@Field("bulan") String bulan);

    @FormUrlEncoded
    @POST("getStatusPenjualanByHari")
    Call<ResponseStatusPenjualanByHari> statusPenjualanByHari(@Field("hari") String hari);

    @FormUrlEncoded
    @POST("detailStatusPenjualan")
    Call<ResponseDetailStatusPenjualan> detailStatusPenjualan(@Field("id_status_penjualan") String id_status_penjualan);

    @FormUrlEncoded
    @POST("getIdBarangOutlet")
    Call<ResponseGetIdBarangOutlet> getIdBarangOutlet(@Field("id_outlet") String id_outlet,
                                                      @Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("editBarangOutlet")
    Call<ResponseEditBarangOutlet> editBarangOutlet(@Field("id_barang_outlet") String id_barang_outlet,
                                                    @Field("id_barang") String id_barang,
                                                    @Field("harga_jual") String harga_jual,
                                                    @Field("harga_jual_pack") String harga_jual_pack,
                                                    @Field("stock") String stock,
                                                    @Field("jumlah_pack") String jumlah_pack,
                                                    @Field("diskon") String diskon,
                                                    @Field("diskon_pack") String diskon_pack,
                                                    @Field("id_outlet") String id_outlet,
                                                    @Field("number_of_pack") String number_of_pack);

    @FormUrlEncoded
    @POST("tambahStatusTransfer")
    Call<ResponseTambahStatusTransfer> tambahStatusTransfer(@Field("id_status_transfer") String id_status_transfer,
                                                            @Field("tanggal_transfer") String tanggal_transfer,
                                                            @Field("id_outlet_pengirim") String id_outlet_pengirim,
                                                            @Field("id_outlet_penerima") String id_outlet_penerima,
                                                            @Field("outlet_pengirim") String outlet_pengirim,
                                                            @Field("outlet_penerima") String outlet_penerima,
                                                            @Field("status_transfer") String status_transfer);

    @FormUrlEncoded
    @POST("tambahTransferBarang")
    Call<ResponseTambahTransferBarang> tambahTransferBarang(@Field("id_transfer_barang") String id_transfer_barang,
                                                            @Field("id_barang_outlet_pengirim") String id_barang_outlet_pengirim,
                                                            @Field("id_barang") String id_barang,
                                                            @Field("jumlah") String jumlah,
                                                            @Field("jumlah_pack") String jumlah_pack,
                                                            @Field("id_status_transfer") String id_status_transfer,
                                                            @Field("status_barang") String status_barang);

    @FormUrlEncoded
    @POST("getStatusTransferByBulan")
    Call<ResponseStatusTransferByBulan> statusTransferByBulan(@Field("bulan") String bulan);

    @FormUrlEncoded
    @POST("detailTransferBarang")
    Call<ResponseDetailTransferBarang> detailTransferBarang(@Field("id_status_transfer") String id_status_transfer);

    @FormUrlEncoded
    @POST("hapusStatusTransfer")
    Call<ResponseHapusStatusTransfer> hapusStatusTransfer(@Field("id_status_transfer") String id_status_transfer);

    @FormUrlEncoded
    @POST("getCount")
    Call<ResponseCountBarangTransfer> countTransferBarang(@Field("id_status_transfer") String id_status_transfer);

    @FormUrlEncoded
    @POST("hapusTransferBarang")
    Call<ResponseHapusTransferCancel> hapusTransferCancel(@Field("id_status_transfer") String id_status_transfer);


    @FormUrlEncoded
    @POST("editStock")
    Call<ResponseEditStock> editStock(@Field("id_barang") String id_barang,
                                      @Field("stock") String stock,
                                      @Field("jumlah_pack") String jumlah_pack);

    @FormUrlEncoded
    @POST("tambahPermintaanBarang")
    Call<ResponseMintaBarang> mintaBarang(@Field("status_permintaan_barang") String status_permintaan_barang,
                                          @Field("tanggal_permintaan_barang") String tanggal_permintaan_barang,
                                          @Field("id_barang") String id_barang,
                                          @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("hapusBarangTransfer")
    Call<ResponseHapusBarangTransfer> hapusBarangTransfer(@Field("id_transfer_barang") String id_transfer_barang);

    @FormUrlEncoded
    @POST("editStatusTransferBarang")
    Call<ResponseEditStatusTransfer> editStatusTransfer(@Field("id_status_transfer") String id_status_transfer,
                                                        @Field("status_transfer") String status_transfer);

    @FormUrlEncoded
    @POST("editBarangTransfer")
    Call<ResponseEditStatusBarangTransfer> editStatusBarangTransfer(@Field("id_status_transfer") String id_status_transfer,
                                                                    @Field("status_barang") String status_transfer);

    @FormUrlEncoded
    @POST("tambahStatusPenjualanGudang")
    Call<ResponseTambahStatusPenjualanGudang> tambahStatusPenjualanGudang(
            @Field("id_status_penjualan_gudang") String id_status_penjualan_gudang,
            @Field("tanggal_penjualan") String tanggal_penjualan,
            @Field("status_penjualan") String status_penjualan,
            @Field("metode_bayar") String metode_bayar,
            @Field("total_harga") String total_harga,
            @Field("jumlah_bayar") String jumlah_bayar,
            @Field("jumlah_kurang") String jumlah_kurang,
            @Field("image_bukti_tf") String image_bukti_tf
    );

    @FormUrlEncoded
    @POST("tambahPenjualanGudang")
    Call<ResponseTambahPenjualan> tambahPenjualanGudang(
            @Field("id_barang") String id_barang,
            @Field("jumlah_barang") String jumlah_barang,
            @Field("jumlah_pack") String jumlah_pack,
            @Field("total") String total,
            @Field("nama_user") String nama_user,
            @Field("id_status_penjualan_gudang") String id_status_penjualan_gudang
    );

    @FormUrlEncoded
    @POST("hapusStatusPenjualanGudang")
    Call<ResponseHapusStatusPenjualanGudang> hapusStatusPenjualanGudang(
            @Field("id_status_penjualan_gudang") String id_status_penjualan_gudang
    );

    @FormUrlEncoded
    @POST("getBarangPenjualanGudang")
    Call<ResponseBarangPenjualanGudang> barangPenjualanGudang(@Field("id_status_penjualan_gudang") String id_status_penjualan_gudang);

    @FormUrlEncoded
    @POST("hapusBarangPenjualanGudang")
    Call<ResponseHapusPenjualanGudang> hapusPenjualanGudang(@Field("id_penjualan_gudang") String id_penjualan_gudang);

    @FormUrlEncoded
    @POST("getSumPenjualanGudang")
    Call<ResponseSumPenjualanGudang> sumTotalPenjualanGudang(@Field("id_status_penjualan_gudang") String id_status_pejualan_gudang);

    @FormUrlEncoded
    @POST("hapusBarangPenjualanGudangByIdStatus")
    Call<ResponseHapusPenjualanGudangByIdStatus> hapusPenjualanIdStatus(@Field("id_status_penjualan_gudang") String id_status_penjualan_gudang);

    @FormUrlEncoded
    @POST("editStatusPenjualanGudang")
    Call<ResponseEditStatusPenjualanGudang> editStatusPenjualanGudang(

            @Field("id_status_penjualan_gudang") String id_status_penjualan_gudang,
            @Field("status_penjualan") String status_penjualan,
            @Field("metode_bayar") String metode_bayar,
            @Field("total_harga") String total_harga,
            @Field("jumlah_bayar") String jumlah_bayar,
            @Field("jumlah_kurang") String jumlah_kurang,
            @Field("image_bukti_tf") String image_bukti_tf,
            @Field("pilihan_kirim") String pilihan_kirim,
            @Field("driver_id") String driver_id,
            @Field("alamat_tujuan") String alamat_tujuan,
            @Field("status_pengiriman") String status_pengiriman

    );

    @FormUrlEncoded
    @POST("editDataUser")
    Call<ResponseEditUser> editUser(@Field("id_user") String id_user,
                                    @Field("nama_lengkap") String nama_lengkap,
                                    @Field("username") String username,
                                    @Field("level") String level,
                                    @Field("id_outlet") String id_outlet);


    @FormUrlEncoded
    @POST("editDataImageUser")
    Call<ResponseEditImageUser> editImageUser(@Field("id_user") String id_user,
                                              @Field("foto") String foto);

    @FormUrlEncoded
    @POST("hapusUser")
    Call<ResponseHapusUser> hapusUser(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("editOutlet")
    Call<ResponseEditOutlet> editOutlet(@Field("id_outlet") String id_outlet,
                                        @Field("nama_outlet") String nama_outlet,
                                        @Field("kota") String kota,
                                        @Field("persentase") String persentase,
                                        @Field("jumlah_anggota") String jumlah_anggota,
                                        @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("editPassword")
    Call<ResponsePassword> updatePassword(@Field("id_user") String id_user,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("hapusOutlet")
    Call<ResponseHapusOutlet> hapusOutlet(@Field("id_outlet") String id_outlet);

    @GET("getPermintaanBarang")
    Call<ResponseDataPermintaanBarang> dataPermintaanBarang();

    @FormUrlEncoded
    @POST("hapusPermintaanBarang")
    Call<ResponseHapusMintaBarang> hapusMintaBarang(@Field("id_minta_barang") String hapus_minta_barang);

    @GET("getUserDriver")
    Call<ResponseDataDriver> getDriver();

    @FormUrlEncoded
    @POST("getStatusPengirimanByIdUser")
    Call<ResponseStatusPengirimanByIdUser> statusPengirimanByIdUser(@Field("id_user") String id_user,
                                                                    @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("getListPengirimanByIdStatus")
    Call<ResponsePengirimanByIdUser> pengirimanByIdUser(@Field("id_status_pengiriman") String id_status_pengiriman);

    @FormUrlEncoded
    @POST("updateStatusPengiriman")
    Call<ResponseUpdateStatusPengiriman> updateStatusPengiriman(@Field("id_status_pengiriman") String id_status_pengiriman,
                                                                @Field("status_pengiriman") String status_pengiriman);

    @FormUrlEncoded
    @POST("getStatusPengirimanSelesai")
    Call<ResponsePengirimanSelesai> pengirimanSelesai(@Field("id_user") String id_user,
                                                      @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("detailPenjualanGudang")
    Call<ResponseDetailPenjualanGudang> detailPenjualanGudang(@Field("id_status_penjualan_gudang") String id_status_penjualan_gudang);

    @FormUrlEncoded
    @POST("listStatusTransfer")
    Call<ResponseListStatusTransfer> listStatusTransfer(@Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("getListTransfer")
    Call<ResponseListTransfer> listTransfer(@Field("id_status_transfer") String id_status_transfer);

    @FormUrlEncoded
    @POST("cekBarangToko")
    Call<ResponseCekBarangToko> cekBarangToko(@Field("id_barang") String id_barang,
                                              @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("editStockBarangOutlet")
    Call<ResponseEditStockBarangOutlet> editStockBarangOutlet(@Field("id_barang") String id_barang,
                                                              @Field("id_outlet") String id_outlet,
                                                              @Field("stock") String stock,
                                                              @Field("jumlah_pack") String jumlah_pack);

    @FormUrlEncoded
    @POST("editStatusTransferBarang")
    Call<ResponseEditStatusTransferBarang> editStatusTransferBarang(@Field("id_transfer_barang") String id_transfer_barang,
                                                                    @Field("status_barang") String status_barang);

    @GET("sumTransaksiByBulan")
    Call<ResponseSumTransaksiBulan> sumTransaksiBulan(@Query("bulan") String bulan,
                                                      @Query("id_outlet") String id_outlet);

    @GET("sumTransaksiByHari")
    Call<ResponseSumTransaksiHari> sumTransaksiHari(@Query("hari") String hari,
                                                    @Query("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("getStatusPenjualanGudangByIdUser")
    Call<ResponseStatusPenjualanGudangByIdUser> statusPenjualanGudangByIdUser(@Field("id_user") String id_user,
                                                                              @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("getListPenjualanGudangByIdStatus")
    Call<ResponseListPenjualanGudangByIdStatus> penjualanGudangByIdStatus(@Field("id_status_penjualan_gudang")
                                                                                  String id_status_penjualan_gudang);

    @FormUrlEncoded
    @POST("updateStatusPenjualanGudang")
    Call<ResponseUpdateStatusPenjualanGudang> updateStatusPenjualanGudang(@Field("id_status_penjualan_gudang") String id_status_penjualan_gudang,
                                                                          @Field("status_pengiriman") String status_pengiriman);

    @FormUrlEncoded
    @POST("tambahPengeluaranOutlet")
    Call<ResponseTambahPengeluaranOutlet> tambahPengeluaranOutlet(@Field("jumlah_pengeluaran") String jumlah_pengeluaran,
                                                                  @Field("keterangan") String keterangan,
                                                                  @Field("tanggal_pengeluaran") String tanggal_pengeluaran,
                                                                  @Field("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("editFirebaseToken")
    Call<ResponseEditFirebaseToken> editFirebaseToken(@Field("id_user") String id_user,
                                                      @Field("firebase_token") String firebase_token);

    @GET("getDataBarang")
    Call<ResponseDataBarang> dataBarang();

    @FormUrlEncoded
    @POST("getSumPenjualanKaryawanToko")
    Call<ResponsePenjualanKaryawanToko> penjualanKaryawanToko(@Field("nama_kasir") String nama_kasir,
                                                              @Field("hari") String hari);

    @FormUrlEncoded
    @POST("getStockByIdBarang")
    Call<ResponseStockByIdBarang> stockIdBarang(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("getStatusPenjualanGudangSelesai")
    Call<ResponseStatusPenjualanGudangSelesai> statusPenjualanGudanSelesai(@Field("id_user") String id_user,
                                                                           @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("getStatusPengirimanByBulan")
    Call<ResponseStatusPengirimanByBulan> statusPengirimanByBulan(@Field("bulan") String bulan);

    @FormUrlEncoded
    @POST("getStatusPengirimanByTanggal")
    Call<ResponseStatusPengirimanByTanggal> statusPengirimanByTanggal(@Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("getStatusPenjualanGudangByBulan")
    Call<ResponseStatusSupplierByBulan> statusSupplierByBulan(@Field("bulan") String bulan);

    @FormUrlEncoded
    @POST("getStatusPenjualanGudangByTanggal")
    Call<ResponseStatusSupplierTanggal> statusSupplierByTanggal(@Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("editPackBarangOutlet")
    Call<ResponseEditJumlahPackOutlet> editPackOutlet(@Field("id_barang_outlet") String id_barang_outlet,
                                                      @Field("jumlah_pack") String jumlah_pack);

    @FormUrlEncoded
    @POST("editPackBarang")
    Call<ResponseEditPackBarang> editPackBarang(@Field("id_barang") String id_barang,
                                                @Field("jumlah_pack") String jumlah_pack);

    @FormUrlEncoded
    @POST("updateStockForPengiriman")
    Call<ResponseUpdateStockPengiriman> updateStockPengiriman(@Field("id_barang") String id_barang,
                                                              @Field("stock") String stock,
                                                              @Field("jumlah_pack") String jumlah_pack);

    @FormUrlEncoded
    @POST("editStatusPenjualanBarang")
    Call<ResponseEditStatusPenjualanBarang> editStatusPenjualanBarang(@Field("id_status_penjualan") String id_status_penjualan,
                                                                      @Field("status_penjualan_barang") String status_penjualan_barang);

    @FormUrlEncoded
    @POST("hapusStatusPengiriman")
    Call<ResponseHapusStatusPengiriman> hapusStatusPengiriman(@Field("id_status_pengiriman") String id_status_pengiriman);

    @GET("getPermintaanBarangByIdOutlet")
    Call<ResponseMintaBarangByOutlet> mintaBarangByOutlet(@Query("id_outlet") String id_outlet);

    @FormUrlEncoded
    @POST("hapusMintaBarang")
    Call<ResponseHapusDataMintaBarang> hapusDataMintaBarang(@Field("id_minta_barang") String id_minta_barang);

    @FormUrlEncoded
    @POST("getKategoriById")
    Call<ResponseKategoriById> dataKategoriById(@Field("id_kategori") String id_kategori);

    @FormUrlEncoded
    @POST("editProfile")
    Call<ResponseEditProfile> editProfile(@Field("id_user") String id_user,
                                          @Field("username") String username);

    @FormUrlEncoded
    @POST("validateBarangList")
    Call<ResponseValidateBarang> validateBarang(@Field("id_barang[]") ArrayList<String> id_barang,
                                                @Field("id_outlet[]") ArrayList<String> id_outlet,
                                                @Field("jumlah_barang[]") ArrayList<String> jumlah_barang,
                                                @Field("jumlah_pack[]") ArrayList<String> jumlah_pack);

    @FormUrlEncoded
    @POST("getIdBarangOutletList")
    Call<ResponseBarangOutletList> barangOutletList(@Field("id_barang[]") ArrayList<String> id_barang,
                                                    @Field("id_outlet[]") ArrayList<String> id_outlet);

    @FormUrlEncoded
    @POST("hapusPenjualan")
    Call<ResponseHapusPenjualan> hapusPenjualan(@Field("id_status_penjualan") String id_status_penjualan);

    @FormUrlEncoded
    @POST("hapusBarangPenjualan")
    Call<ResponseHapusBarangPenjualan> hapusBarangPenjualan(@Field("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("tambahBarangReportOutletList")
    Call<ResponseInsertReportToko> insertReportToko(@Field("nama_outlet[]") ArrayList<String> nama_outlet,
                                                    @Field("nama_barang[]") ArrayList<String> nama_barang,
                                                    @Field("jumlah_barang[]") ArrayList<String> jumlah_barang,
                                                    @Field("jumlah_pack[]") ArrayList<String> jumlah_pack,
                                                    @Field("harga_pcs[]") ArrayList<String> harga_pcs,
                                                    @Field("harga_pack[]") ArrayList<String> harga_pack,
                                                    @Field("total[]") ArrayList<String> total,
                                                    @Field("metode_pembayaran[]") ArrayList<String> metode_pembayaran,
                                                    @Field("jenis_satuan[]") ArrayList<String> jenis_satuan,
                                                    @Field("tanggal_penjualan[]") ArrayList<String> tanggal_penjualan,
                                                    @Field("nama_kasir[]") ArrayList<String> nama_kasir,
                                                    @Field("id_status_penjualan[]") ArrayList<String> id_status_penjualan,
                                                    @Field("status_penjualan_barang[]") ArrayList<String> status_penjualan_barang);

}