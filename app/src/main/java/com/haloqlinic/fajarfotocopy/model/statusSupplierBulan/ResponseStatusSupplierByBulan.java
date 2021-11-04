package com.haloqlinic.fajarfotocopy.model.statusSupplierBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusSupplierByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPenjualanGudangByBulan")
	private List<StatusPenjualanGudangByBulanItem> statusPenjualanGudangByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPenjualanGudangByBulan(List<StatusPenjualanGudangByBulanItem> statusPenjualanGudangByBulan){
		this.statusPenjualanGudangByBulan = statusPenjualanGudangByBulan;
	}

	public List<StatusPenjualanGudangByBulanItem> getStatusPenjualanGudangByBulan(){
		return statusPenjualanGudangByBulan;
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
			"ResponseStatusSupplierByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPenjualanGudangByBulan = '" + statusPenjualanGudangByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}