package com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang;

import com.google.gson.annotations.SerializedName;

public class DataPermintaanBarangItem{

	@SerializedName("id_minta_barang")
	private String idMintaBarang;

	@SerializedName("status_permintaan_barang")
	private String statusPermintaanBarang;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("tanggal_permintaan")
	private String tanggalPermintaan;

	public void setIdMintaBarang(String idMintaBarang){
		this.idMintaBarang = idMintaBarang;
	}

	public String getIdMintaBarang(){
		return idMintaBarang;
	}

	public void setStatusPermintaanBarang(String statusPermintaanBarang){
		this.statusPermintaanBarang = statusPermintaanBarang;
	}

	public String getStatusPermintaanBarang(){
		return statusPermintaanBarang;
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

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
	}

	public void setTanggalPermintaan(String tanggalPermintaan){
		this.tanggalPermintaan = tanggalPermintaan;
	}

	public String getTanggalPermintaan(){
		return tanggalPermintaan;
	}

	@Override
 	public String toString(){
		return 
			"DataPermintaanBarangItem{" + 
			"id_minta_barang = '" + idMintaBarang + '\'' + 
			",status_permintaan_barang = '" + statusPermintaanBarang + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",tanggal_permintaan = '" + tanggalPermintaan + '\'' + 
			"}";
		}
}