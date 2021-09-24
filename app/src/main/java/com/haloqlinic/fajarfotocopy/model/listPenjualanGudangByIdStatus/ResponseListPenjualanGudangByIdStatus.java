package com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListPenjualanGudangByIdStatus{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("listPenjualanGudangByIdStatus")
	private List<ListPenjualanGudangByIdStatusItem> listPenjualanGudangByIdStatus;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setListPenjualanGudangByIdStatus(List<ListPenjualanGudangByIdStatusItem> listPenjualanGudangByIdStatus){
		this.listPenjualanGudangByIdStatus = listPenjualanGudangByIdStatus;
	}

	public List<ListPenjualanGudangByIdStatusItem> getListPenjualanGudangByIdStatus(){
		return listPenjualanGudangByIdStatus;
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
			"ResponseListPenjualanGudangByIdStatus{" + 
			"pesan = '" + pesan + '\'' + 
			",listPenjualanGudangByIdStatus = '" + listPenjualanGudangByIdStatus + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}