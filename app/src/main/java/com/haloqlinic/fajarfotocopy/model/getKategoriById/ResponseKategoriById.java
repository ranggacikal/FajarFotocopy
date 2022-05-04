package com.haloqlinic.fajarfotocopy.model.getKategoriById;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKategoriById{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataKategoriById")
	private List<DataKategoriByIdItem> dataKategoriById;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataKategoriById(List<DataKategoriByIdItem> dataKategoriById){
		this.dataKategoriById = dataKategoriById;
	}

	public List<DataKategoriByIdItem> getDataKategoriById(){
		return dataKategoriById;
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
			"ResponseKategoriById{" + 
			"pesan = '" + pesan + '\'' + 
			",dataKategoriById = '" + dataKategoriById + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}