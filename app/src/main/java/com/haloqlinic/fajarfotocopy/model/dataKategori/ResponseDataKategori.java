package com.haloqlinic.fajarfotocopy.model.dataKategori;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataKategori{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataKategori")
	private List<DataKategoriItem> dataKategori;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataKategori(List<DataKategoriItem> dataKategori){
		this.dataKategori = dataKategori;
	}

	public List<DataKategoriItem> getDataKategori(){
		return dataKategori;
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
			"ResponseDataKategori{" + 
			"pesan = '" + pesan + '\'' + 
			",dataKategori = '" + dataKategori + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}