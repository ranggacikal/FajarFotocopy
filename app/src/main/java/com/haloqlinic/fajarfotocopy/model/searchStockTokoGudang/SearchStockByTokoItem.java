package com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang;

import com.google.gson.annotations.SerializedName;

public class SearchStockByTokoItem{

	@SerializedName("harga_modal_gudang")
	private String hargaModalGudang;

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("harga_modal_toko")
	private String hargaModalToko;

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

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

	public void setHargaModalGudang(String hargaModalGudang){
		this.hargaModalGudang = hargaModalGudang;
	}

	public String getHargaModalGudang(){
		return hargaModalGudang;
	}

	public void setHargaJualPack(String hargaJualPack){
		this.hargaJualPack = hargaJualPack;
	}

	public String getHargaJualPack(){
		return hargaJualPack;
	}

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
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

	public void setHargaModalToko(String hargaModalToko){
		this.hargaModalToko = hargaModalToko;
	}

	public String getHargaModalToko(){
		return hargaModalToko;
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

	@Override
 	public String toString(){
		return 
			"SearchStockByTokoItem{" + 
			"harga_modal_gudang = '" + hargaModalGudang + '\'' + 
			",harga_jual_pack = '" + hargaJualPack + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",harga_modal_toko = '" + hargaModalToko + '\'' + 
			",diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",number_of_pack = '" + numberOfPack + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",stock = '" + stock + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",diskon = '" + diskon + '\'' + 
			"}";
		}
}