package com.haloqlinic.fajarfotocopy.model.stockToko;

import com.google.gson.annotations.SerializedName;

public class GetStockByTokoItem{

	@SerializedName("harga_modal_gudang")
	private String hargaModalGudang;

	@SerializedName("harga_modal_toko")
	private String hargaModalToko;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_outlet")
	private String namaOutlet;

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

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
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
			"GetStockByTokoItem{" + 
			"harga_modal_gudang = '" + hargaModalGudang + '\'' + 
			",harga_modal_toko = '" + hargaModalToko + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",stock = '" + stock + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",diskon = '" + diskon + '\'' + 
			"}";
		}
}