package com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan;

import com.google.gson.annotations.SerializedName;

public class DataIdStatusPenjualan{

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("id_status_penjualan")
	private String idStatusPenjualan;

	@SerializedName("id_outlet")
	private String idOutlet;

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setIdStatusPenjualan(String idStatusPenjualan){
		this.idStatusPenjualan = idStatusPenjualan;
	}

	public String getIdStatusPenjualan(){
		return idStatusPenjualan;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	@Override
 	public String toString(){
		return 
			"DataIdStatusPenjualan{" + 
			"tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",id_status_penjualan = '" + idStatusPenjualan + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			"}";
		}
}