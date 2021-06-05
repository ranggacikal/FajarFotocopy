package com.haloqlinic.fajarfotocopy.model.cariBarangById;

import com.google.gson.annotations.SerializedName;

public class SearchBarangByIdItem{

	@SerializedName("asal_barang")
	private String asalBarang;

	@SerializedName("harga_modal_gudang")
	private String hargaModalGudang;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("id_kategori_barang")
	private String idKategoriBarang;

	@SerializedName("harga_modal_gudang_pack")
	private String hargaModalGudangPack;

	@SerializedName("harga_modal_toko")
	private String hargaModalToko;

	@SerializedName("harga_modal_toko_pack")
	private String hargaModalTokoPack;

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("harga_jual_toko_pack")
	private String hargaJualTokoPack;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("stock")
	private String stock;

	@SerializedName("harga_jual_toko")
	private String hargaJualToko;

	@SerializedName("diskon")
	private String diskon;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setAsalBarang(String asalBarang){
		this.asalBarang = asalBarang;
	}

	public String getAsalBarang(){
		return asalBarang;
	}

	public void setHargaModalGudang(String hargaModalGudang){
		this.hargaModalGudang = hargaModalGudang;
	}

	public String getHargaModalGudang(){
		return hargaModalGudang;
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

	public void setHargaModalGudangPack(String hargaModalGudangPack){
		this.hargaModalGudangPack = hargaModalGudangPack;
	}

	public String getHargaModalGudangPack(){
		return hargaModalGudangPack;
	}

	public void setHargaModalToko(String hargaModalToko){
		this.hargaModalToko = hargaModalToko;
	}

	public String getHargaModalToko(){
		return hargaModalToko;
	}

	public void setHargaModalTokoPack(String hargaModalTokoPack){
		this.hargaModalTokoPack = hargaModalTokoPack;
	}

	public String getHargaModalTokoPack(){
		return hargaModalTokoPack;
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

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
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
			"SearchBarangByIdItem{" + 
			"asal_barang = '" + asalBarang + '\'' + 
			",harga_modal_gudang = '" + hargaModalGudang + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",id_kategori_barang = '" + idKategoriBarang + '\'' + 
			",harga_modal_gudang_pack = '" + hargaModalGudangPack + '\'' + 
			",harga_modal_toko = '" + hargaModalToko + '\'' + 
			",harga_modal_toko_pack = '" + hargaModalTokoPack + '\'' + 
			",diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",harga_jual_toko_pack = '" + hargaJualTokoPack + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",stock = '" + stock + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",diskon = '" + diskon + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}