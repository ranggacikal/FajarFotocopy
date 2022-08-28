package com.haloqlinic.fajarfotocopy.model.dataBarangOutletList;

import com.google.gson.annotations.SerializedName;

public class DataBarangOutletListItem{

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

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

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("id_barang_outlet")
	private String idBarangOutlet;

	@SerializedName("diskon")
	private String diskon;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

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

	public void setHargaJualPack(String hargaJualPack){
		this.hargaJualPack = hargaJualPack;
	}

	public String getHargaJualPack(){
		return hargaJualPack;
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

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
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

	public void setJumlahPack(String jumlahPack){
		this.jumlahPack = jumlahPack;
	}

	public String getJumlahPack(){
		return jumlahPack;
	}

	@Override
 	public String toString(){
		return 
			"DataBarangOutletListItem{" + 
			"diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",harga_jual_pack = '" + hargaJualPack + '\'' + 
			",number_of_pack = '" + numberOfPack + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",stock = '" + stock + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",diskon = '" + diskon + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			"}";
		}
}