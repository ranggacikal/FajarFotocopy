package com.haloqlinic.fajarfotocopy.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataUser")
	private List<DataUserItem> dataUser;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataUser(List<DataUserItem> dataUser){
		this.dataUser = dataUser;
	}

	public List<DataUserItem> getDataUser(){
		return dataUser;
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
			"ResponseDataBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataUser = '" + dataUser + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}