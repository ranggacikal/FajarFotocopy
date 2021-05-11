package com.haloqlinic.fajarfotocopy.model.dataToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataToko")
	private List<DataTokoItem> dataToko;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataToko(List<DataTokoItem> dataToko){
		this.dataToko = dataToko;
	}

	public List<DataTokoItem> getDataToko(){
		return dataToko;
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
			"ResponseDataToko{" + 
			"pesan = '" + pesan + '\'' + 
			",dataToko = '" + dataToko + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}