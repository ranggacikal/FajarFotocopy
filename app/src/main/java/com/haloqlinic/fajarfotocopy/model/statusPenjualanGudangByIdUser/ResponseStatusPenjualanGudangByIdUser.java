package com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangByIdUser;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPenjualanGudangByIdUser{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPenjualanGudangByIdUser")
	private List<StatusPenjualanGudangByIdUserItem> statusPenjualanGudangByIdUser;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPenjualanGudangByIdUser(List<StatusPenjualanGudangByIdUserItem> statusPenjualanGudangByIdUser){
		this.statusPenjualanGudangByIdUser = statusPenjualanGudangByIdUser;
	}

	public List<StatusPenjualanGudangByIdUserItem> getStatusPenjualanGudangByIdUser(){
		return statusPenjualanGudangByIdUser;
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
			"ResponseStatusPenjualanGudangByIdUser{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPenjualanGudangByIdUser = '" + statusPenjualanGudangByIdUser + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}