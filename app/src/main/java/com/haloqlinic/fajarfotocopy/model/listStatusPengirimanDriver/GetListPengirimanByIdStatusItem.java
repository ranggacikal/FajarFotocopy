package com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver;

import com.google.gson.annotations.SerializedName;

public class GetListPengirimanByIdStatusItem{

	@SerializedName("id_status_pengiriman")
	private String idStatusPengiriman;

	@SerializedName("id_pengiriman")
	private String idPengiriman;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("harga_jual_toko_pack")
	private String hargaJualTokoPack;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("status_barang")
	private String statusBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("harga_jual_toko")
	private String hargaJualToko;

	@SerializedName("tanggal_pengiriman")
	private String tanggalPengiriman;

	public void setIdStatusPengiriman(String idStatusPengiriman){
		this.idStatusPengiriman = idStatusPengiriman;
	}

	public String getIdStatusPengiriman(){
		return idStatusPengiriman;
	}

	public void setIdPengiriman(String idPengiriman){
		this.idPengiriman = idPengiriman;
	}

	public String getIdPengiriman(){
		return idPengiriman;
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

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setStatusPengiriman(String statusPengiriman){
		this.statusPengiriman = statusPengiriman;
	}

	public String getStatusPengiriman(){
		return statusPengiriman;
	}

	public void setHargaJualTokoPack(String hargaJualTokoPack){
		this.hargaJualTokoPack = hargaJualTokoPack;
	}

	public String getHargaJualTokoPack(){
		return hargaJualTokoPack;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setStatusBarang(String statusBarang){
		this.statusBarang = statusBarang;
	}

	public String getStatusBarang(){
		return statusBarang;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setHargaJualToko(String hargaJualToko){
		this.hargaJualToko = hargaJualToko;
	}

	public String getHargaJualToko(){
		return hargaJualToko;
	}

	public void setTanggalPengiriman(String tanggalPengiriman){
		this.tanggalPengiriman = tanggalPengiriman;
	}

	public String getTanggalPengiriman(){
		return tanggalPengiriman;
	}

	@Override
 	public String toString(){
		return 
			"GetListPengirimanByIdStatusItem{" + 
			"id_status_pengiriman = '" + idStatusPengiriman + '\'' + 
			",id_pengiriman = '" + idPengiriman + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",harga_jual_toko_pack = '" + hargaJualTokoPack + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",status_barang = '" + statusBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",tanggal_pengiriman = '" + tanggalPengiriman + '\'' + 
			"}";
		}
}