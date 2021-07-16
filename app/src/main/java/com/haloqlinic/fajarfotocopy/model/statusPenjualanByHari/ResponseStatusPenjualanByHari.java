package com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPenjualanByHari{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	@SerializedName("statusPenjualanByHari")
	private List<StatusPenjualanByHariItem> statusPenjualanByHari;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setStatusPenjualanByHari(List<StatusPenjualanByHariItem> statusPenjualanByHari){
		this.statusPenjualanByHari = statusPenjualanByHari;
	}

	public List<StatusPenjualanByHariItem> getStatusPenjualanByHari(){
		return statusPenjualanByHari;
	}

	@Override
 	public String toString(){
		return 
			"ResponseStatusPenjualanByHari{" + 
			"pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			",statusPenjualanByHari = '" + statusPenjualanByHari + '\'' + 
			"}";
		}
}