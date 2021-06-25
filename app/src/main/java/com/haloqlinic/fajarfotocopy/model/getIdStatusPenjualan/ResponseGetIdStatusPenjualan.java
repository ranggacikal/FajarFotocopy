package com.haloqlinic.fajarfotocopy.model.getIdStatusPenjualan;

import com.google.gson.annotations.SerializedName;

public class ResponseGetIdStatusPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("DataIdStatusPenjualan")
	private DataIdStatusPenjualan dataIdStatusPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataIdStatusPenjualan(DataIdStatusPenjualan dataIdStatusPenjualan){
		this.dataIdStatusPenjualan = dataIdStatusPenjualan;
	}

	public DataIdStatusPenjualan getDataIdStatusPenjualan(){
		return dataIdStatusPenjualan;
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
			"ResponseGetIdStatusPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",dataIdStatusPenjualan = '" + dataIdStatusPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}