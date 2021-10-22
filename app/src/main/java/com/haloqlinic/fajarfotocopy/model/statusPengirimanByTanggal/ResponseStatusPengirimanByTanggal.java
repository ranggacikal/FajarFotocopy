package com.haloqlinic.fajarfotocopy.model.statusPengirimanByTanggal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPengirimanByTanggal{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getStatusPengirimanByTanggal")
	private List<GetStatusPengirimanByTanggalItem> getStatusPengirimanByTanggal;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetStatusPengirimanByTanggal(List<GetStatusPengirimanByTanggalItem> getStatusPengirimanByTanggal){
		this.getStatusPengirimanByTanggal = getStatusPengirimanByTanggal;
	}

	public List<GetStatusPengirimanByTanggalItem> getGetStatusPengirimanByTanggal(){
		return getStatusPengirimanByTanggal;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseStatusPengirimanByTanggal{" + 
			"pesan = '" + pesan + '\'' + 
			",getStatusPengirimanByTanggal = '" + getStatusPengirimanByTanggal + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}