package com.haloqlinic.fajarfotocopy.model.statusPenjualanGudangSelesai;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPenjualanGudangSelesai{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPenjualanGudangSelesai")
	private List<StatusPenjualanGudangSelesaiItem> statusPenjualanGudangSelesai;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPenjualanGudangSelesai(List<StatusPenjualanGudangSelesaiItem> statusPenjualanGudangSelesai){
		this.statusPenjualanGudangSelesai = statusPenjualanGudangSelesai;
	}

	public List<StatusPenjualanGudangSelesaiItem> getStatusPenjualanGudangSelesai(){
		return statusPenjualanGudangSelesai;
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
			"ResponseStatusPenjualanGudangSelesai{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPenjualanGudangSelesai = '" + statusPenjualanGudangSelesai + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}