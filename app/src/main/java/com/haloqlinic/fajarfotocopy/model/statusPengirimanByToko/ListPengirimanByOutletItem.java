package com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko;

import com.google.gson.annotations.SerializedName;

public class ListPengirimanByOutletItem{

	@SerializedName("id_status_pengiriman")
	private String idStatusPengiriman;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("id_outlet")
	private String idOutlet;

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

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
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
			"ListPengirimanByOutletItem{" + 
			"id_status_pengiriman = '" + idStatusPengiriman + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",tanggal_pengiriman = '" + tanggalPengiriman + '\'' + 
			"}";
		}
}