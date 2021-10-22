package com.haloqlinic.fajarfotocopy.model.statusPengirimanByBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPengirimanByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getStatusPengirimanByBulan")
	private List<GetStatusPengirimanByBulanItem> getStatusPengirimanByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetStatusPengirimanByBulan(List<GetStatusPengirimanByBulanItem> getStatusPengirimanByBulan){
		this.getStatusPengirimanByBulan = getStatusPengirimanByBulan;
	}

	public List<GetStatusPengirimanByBulanItem> getGetStatusPengirimanByBulan(){
		return getStatusPengirimanByBulan;
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
			"ResponseStatusPengirimanByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",getStatusPengirimanByBulan = '" + getStatusPengirimanByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}