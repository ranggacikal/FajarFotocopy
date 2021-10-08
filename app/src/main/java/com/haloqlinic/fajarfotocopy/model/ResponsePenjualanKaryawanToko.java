package com.haloqlinic.fajarfotocopy.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePenjualanKaryawanToko{

	@SerializedName("dataPenjualanKaryawanToko")
	private List<DataPenjualanKaryawanTokoItem> dataPenjualanKaryawanToko;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public void setDataPenjualanKaryawanToko(List<DataPenjualanKaryawanTokoItem> dataPenjualanKaryawanToko){
		this.dataPenjualanKaryawanToko = dataPenjualanKaryawanToko;
	}

	public List<DataPenjualanKaryawanTokoItem> getDataPenjualanKaryawanToko(){
		return dataPenjualanKaryawanToko;
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
			"ResponsePenjualanKaryawanToko{" + 
			"dataPenjualanKaryawanToko = '" + dataPenjualanKaryawanToko + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}