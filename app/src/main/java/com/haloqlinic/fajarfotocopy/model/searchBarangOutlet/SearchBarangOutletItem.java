package com.haloqlinic.fajarfotocopy.model.searchBarangOutlet;

import com.google.gson.annotations.SerializedName;

public class SearchBarangOutletItem{

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("id_kategori_barang")
	private String idKategoriBarang;

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("harga_jual_toko_pack")
	private String hargaJualTokoPack;

	@SerializedName("number_of_pack")
	private String numberOfPack;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("stock")
	private String stock;

	@SerializedName("harga_jual_toko")
	private String hargaJualToko;

	@SerializedName("id_barang_outlet")
	private String idBarangOutlet;

	@SerializedName("diskon")
	private String diskon;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setHargaJualPack(String hargaJualPack){
		this.hargaJualPack = hargaJualPack;
	}

	public String getHargaJualPack(){
		return hargaJualPack;
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

	public void setIdKategoriBarang(String idKategoriBarang){
		this.idKategoriBarang = idKategoriBarang;
	}

	public String getIdKategoriBarang(){
		return idKategoriBarang;
	}

	public void setDiskonPack(String diskonPack){
		this.diskonPack = diskonPack;
	}

	public String getDiskonPack(){
		return diskonPack;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setHargaJualTokoPack(String hargaJualTokoPack){
		this.hargaJualTokoPack = hargaJualTokoPack;
	}

	public String getHargaJualTokoPack(){
		return hargaJualTokoPack;
	}

	public void setNumberOfPack(String numberOfPack){
		this.numberOfPack = numberOfPack;
	}

	public String getNumberOfPack(){
		return numberOfPack;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	public void setStock(String stock){
		this.stock = stock;
	}

	public String getStock(){
		return stock;
	}

	public void setHargaJualToko(String hargaJualToko){
		this.hargaJualToko = hargaJualToko;
	}

	public String getHargaJualToko(){
		return hargaJualToko;
	}

	public void setIdBarangOutlet(String idBarangOutlet){
		this.idBarangOutlet = idBarangOutlet;
	}

	public String getIdBarangOutlet(){
		return idBarangOutlet;
	}

	public void setDiskon(String diskon){
		this.diskon = diskon;
	}

	public String getDiskon(){
		return diskon;
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
			"SearchBarangOutletItem{" + 
			"harga_jual_pack = '" + hargaJualPack + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",id_kategori_barang = '" + idKategoriBarang + '\'' + 
			",diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",harga_jual_toko_pack = '" + hargaJualTokoPack + '\'' + 
			",number_of_pack = '" + numberOfPack + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",stock = '" + stock + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",diskon = '" + diskon + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}