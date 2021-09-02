package com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang;

import com.google.gson.annotations.SerializedName;

public class BarangPenjualanGudangItem{

	@SerializedName("harga_modal_gudang")
	private String hargaModalGudang;

	@SerializedName("id_status_penjualan_gudang")
	private String idStatusPenjualanGudang;

	@SerializedName("id_penjualan_gudang")
	private String idPenjualanGudang;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("id_kategori_barang")
	private String idKategoriBarang;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("harga_modal_toko")
	private String hargaModalToko;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("jumlah_barang")
	private String jumlahBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setHargaModalGudang(String hargaModalGudang){
		this.hargaModalGudang = hargaModalGudang;
	}

	public String getHargaModalGudang(){
		return hargaModalGudang;
	}

	public void setIdStatusPenjualanGudang(String idStatusPenjualanGudang){
		this.idStatusPenjualanGudang = idStatusPenjualanGudang;
	}

	public String getIdStatusPenjualanGudang(){
		return idStatusPenjualanGudang;
	}

	public void setIdPenjualanGudang(String idPenjualanGudang){
		this.idPenjualanGudang = idPenjualanGudang;
	}

	public String getIdPenjualanGudang(){
		return idPenjualanGudang;
	}

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
	}

	public void setJumlahPack(String jumlahPack){
		this.jumlahPack = jumlahPack;
	}

	public String getJumlahPack(){
		return jumlahPack;
	}

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setIdKategoriBarang(String idKategoriBarang){
		this.idKategoriBarang = idKategoriBarang;
	}

	public String getIdKategoriBarang(){
		return idKategoriBarang;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setHargaModalToko(String hargaModalToko){
		this.hargaModalToko = hargaModalToko;
	}

	public String getHargaModalToko(){
		return hargaModalToko;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setJumlahBarang(String jumlahBarang){
		this.jumlahBarang = jumlahBarang;
	}

	public String getJumlahBarang(){
		return jumlahBarang;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setNamaUser(String namaUser){
		this.namaUser = namaUser;
	}

	public String getNamaUser(){
		return namaUser;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}

	@Override
 	public String toString(){
		return 
			"BarangPenjualanGudangItem{" + 
			"harga_modal_gudang = '" + hargaModalGudang + '\'' + 
			",id_status_penjualan_gudang = '" + idStatusPenjualanGudang + '\'' + 
			",id_penjualan_gudang = '" + idPenjualanGudang + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",id_kategori_barang = '" + idKategoriBarang + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",harga_modal_toko = '" + hargaModalToko + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",jumlah_barang = '" + jumlahBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_user = '" + namaUser + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}