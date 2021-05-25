package com.haloqlinic.fajarfotocopy.model.getLastIdStatusPengiriman;

import com.google.gson.annotations.SerializedName;

public class ResponseLastIdStatusPengiriamn{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("DataIdStatusPengiriman")
	private DataIdStatusPengiriman dataIdStatusPengiriman;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataIdStatusPengiriman(DataIdStatusPengiriman dataIdStatusPengiriman){
		this.dataIdStatusPengiriman = dataIdStatusPengiriman;
	}

	public DataIdStatusPengiriman getDataIdStatusPengiriman(){
		return dataIdStatusPengiriman;
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
			"ResponseLastIdStatusPengiriamn{" + 
			"pesan = '" + pesan + '\'' + 
			",dataIdStatusPengiriman = '" + dataIdStatusPengiriman + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}