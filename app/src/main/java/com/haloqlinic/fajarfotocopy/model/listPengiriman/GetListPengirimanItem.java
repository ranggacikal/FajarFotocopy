package com.haloqlinic.fajarfotocopy.model.listPengiriman;

import com.google.gson.annotations.SerializedName;

public class GetListPengirimanItem{

	@SerializedName("id_status_pengiriman")
	private String idStatusPengiriman;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("id_pengiriman")
	private String idPengiriman;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("tanggal_pengiriman")
	private String tanggalPengiriman;

	public void setIdStatusPengiriman(String idStatusPengiriman){
		this.idStatusPengiriman = idStatusPengiriman;
	}

	public String getIdStatusPengiriman(){
		return idStatusPengiriman;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setIdPengiriman(String idPengiriman){
		this.idPengiriman = idPengiriman;
	}

	public String getIdPengiriman(){
		return idPengiriman;
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

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
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
			"GetListPengirimanItem{" + 
			"id_status_pengiriman = '" + idStatusPengiriman + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id_pengiriman = '" + idPengiriman + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",tanggal_pengiriman = '" + tanggalPengiriman + '\'' + 
			"}";
		}
}