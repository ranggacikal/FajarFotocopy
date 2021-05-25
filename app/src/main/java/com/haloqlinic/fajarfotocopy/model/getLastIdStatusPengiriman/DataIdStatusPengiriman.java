package com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman;

import com.google.gson.annotations.SerializedName;

public class DataIdStatusPengiriman{

	@SerializedName("id_status_pengiriman")
	private String idStatusPengiriman;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("tanggal_pengiriman")
	private String tanggalPengiriman;

	public void setIdStatusPengiriman(String idStatusPengiriman){
		this.idStatusPengiriman = idStatusPengiriman;
	}

	public String getIdStatusPengiriman(){
		return idStatusPengiriman;
	}

	public void setStatusPengiriman(String statusPengiriman){
		this.statusPengiriman = statusPengiriman;
	}

	public String getStatusPengiriman(){
		return statusPengiriman;
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
			"DataIdStatusPengiriman{" + 
			"id_status_pengiriman = '" + idStatusPengiriman + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",tanggal_pengiriman = '" + tanggalPengiriman + '\'' + 
			"}";
		}
}