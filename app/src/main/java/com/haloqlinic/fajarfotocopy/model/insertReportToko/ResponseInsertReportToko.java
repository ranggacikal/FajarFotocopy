package com.haloqlinic.fajarfotocopy.model.insertReportToko;

import com.google.gson.annotations.SerializedName;

public class ResponseInsertReportToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("sukses")
	private boolean sukses;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
	}

	@Override
 	public String toString(){
		return 
			"ResponseInsertReportToko{" + 
			"pesan = '" + pesan + '\'' + 
			",sukses = '" + sukses + '\'' + 
			"}";
		}
}