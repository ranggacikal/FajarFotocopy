package com.haloqlinic.fajarfotocopy.model.dataKategoriDesc;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataKategoriDesc{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataKategoriDesc")
	private List<DataKategoriDescItem> dataKategoriDesc;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataKategoriDesc(List<DataKategoriDescItem> dataKategoriDesc){
		this.dataKategoriDesc = dataKategoriDesc;
	}

	public List<DataKategoriDescItem> getDataKategoriDesc(){
		return dataKategoriDesc;
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
			"ResponseDataKategoriDesc{" + 
			"pesan = '" + pesan + '\'' + 
			",dataKategoriDesc = '" + dataKategoriDesc + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}