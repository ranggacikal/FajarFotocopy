package com.haloqlinic.fajarfotocopy.model.statusPengirimanByIdUser;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPengirimanByIdUser{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getStatusPengirimanByIdUser")
	private List<GetStatusPengirimanByIdUserItem> getStatusPengirimanByIdUser;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetStatusPengirimanByIdUser(List<GetStatusPengirimanByIdUserItem> getStatusPengirimanByIdUser){
		this.getStatusPengirimanByIdUser = getStatusPengirimanByIdUser;
	}

	public List<GetStatusPengirimanByIdUserItem> getGetStatusPengirimanByIdUser(){
		return getStatusPengirimanByIdUser;
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
			"ResponseStatusPengirimanByIdUser{" + 
			"pesan = '" + pesan + '\'' + 
			",getStatusPengirimanByIdUser = '" + getStatusPengirimanByIdUser + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}