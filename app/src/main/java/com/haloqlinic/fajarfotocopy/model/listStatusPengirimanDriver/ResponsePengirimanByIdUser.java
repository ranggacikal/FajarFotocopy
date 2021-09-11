package com.haloqlinic.fajarfotocopy.model.listStatusPengirimanDriver;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePengirimanByIdUser{

	@SerializedName("getListPengirimanByIdStatus")
	private List<GetListPengirimanByIdStatusItem> getListPengirimanByIdStatus;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public void setGetListPengirimanByIdStatus(List<GetListPengirimanByIdStatusItem> getListPengirimanByIdStatus){
		this.getListPengirimanByIdStatus = getListPengirimanByIdStatus;
	}

	public List<GetListPengirimanByIdStatusItem> getGetListPengirimanByIdStatus(){
		return getListPengirimanByIdStatus;
	}

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

	@Override
 	public String toString(){
		return 
			"ResponsePengirimanByIdUser{" + 
			"getListPengirimanByIdStatus = '" + getListPengirimanByIdStatus + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}