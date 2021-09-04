package com.haloqlinic.fajarfotocopy.model.hapusMintaBarang;

import com.google.gson.annotations.SerializedName;

public class ResponseHapusMintaBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

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
			"ResponseHapusMintaBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}