package com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko;

import com.google.gson.annotations.SerializedName;

public class ListPengirimanByOutletItem{

	@SerializedName("driver_id")
	private String driverId;

	@SerializedName("id_status_pengiriman")
	private String idStatusPengiriman;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("tanggal_pengiriman")
	private String tanggalPengiriman;

	public void setDriverId(String driverId){
		this.driverId = driverId;
	}

	public String getDriverId(){
		return driverId;
	}

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

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
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
			"driver_id = '" + driverId + '\'' + 
			",id_status_pengiriman = '" + idStatusPengiriman + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",tanggal_pengiriman = '" + tanggalPengiriman + '\'' + 
			"}";
		}
}