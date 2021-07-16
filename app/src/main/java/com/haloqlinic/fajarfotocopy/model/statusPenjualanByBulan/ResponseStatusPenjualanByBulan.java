package com.haloqlinic.fajarfotocopy.model.statusPenjualanByBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPenjualanByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPenjualanByBulan")
	private List<StatusPenjualanByBulanItem> statusPenjualanByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPenjualanByBulan(List<StatusPenjualanByBulanItem> statusPenjualanByBulan){
		this.statusPenjualanByBulan = statusPenjualanByBulan;
	}

	public List<StatusPenjualanByBulanItem> getStatusPenjualanByBulan(){
		return statusPenjualanByBulan;
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
			"ResponseStatusPenjualanByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPenjualanByBulan = '" + statusPenjualanByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}