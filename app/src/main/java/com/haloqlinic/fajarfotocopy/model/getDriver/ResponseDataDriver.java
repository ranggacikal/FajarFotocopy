package com.haloqlinic.fajarfotocopy.model.getDriver;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataDriver{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataDriver")
	private List<DataDriverItem> dataDriver;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataDriver(List<DataDriverItem> dataDriver){
		this.dataDriver = dataDriver;
	}

	public List<DataDriverItem> getDataDriver(){
		return dataDriver;
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
			"ResponseDataDriver{" + 
			"pesan = '" + pesan + '\'' + 
			",dataDriver = '" + dataDriver + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}