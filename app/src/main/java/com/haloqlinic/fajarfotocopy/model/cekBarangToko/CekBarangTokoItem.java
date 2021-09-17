package com.haloqlinic.fajarfotocopy.model.cekBarangToko;

import com.google.gson.annotations.SerializedName;

public class CekBarangTokoItem{

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("stock")
	private String stock;

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

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
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
			"CekBarangTokoItem{" + 
			"diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",harga_jual_pack = '" + hargaJualPack + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",stock = '" + stock + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",diskon = '" + diskon + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			"}";
		}
}