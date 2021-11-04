package com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusSupplierTanggal{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPenjualanGudangByTanggal")
	private List<StatusPenjualanGudangByTanggalItem> statusPenjualanGudangByTanggal;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPenjualanGudangByTanggal(List<StatusPenjualanGudangByTanggalItem> statusPenjualanGudangByTanggal){
		this.statusPenjualanGudangByTanggal = statusPenjualanGudangByTanggal;
	}

	public List<StatusPenjualanGudangByTanggalItem> getStatusPenjualanGudangByTanggal(){
		return statusPenjualanGudangByTanggal;
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
			"ResponseStatusSupplierTanggal{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPenjualanGudangByTanggal = '" + statusPenjualanGudangByTanggal + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}