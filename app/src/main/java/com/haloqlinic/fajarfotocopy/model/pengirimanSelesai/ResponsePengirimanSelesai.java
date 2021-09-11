package com.haloqlinic.fajarfotocopy.model.pengirimanSelesai;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePengirimanSelesai{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusPengirimanSelesai")
	private List<StatusPengirimanSelesaiItem> statusPengirimanSelesai;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusPengirimanSelesai(List<StatusPengirimanSelesaiItem> statusPengirimanSelesai){
		this.statusPengirimanSelesai = statusPengirimanSelesai;
	}

	public List<StatusPengirimanSelesaiItem> getStatusPengirimanSelesai(){
		return statusPengirimanSelesai;
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
			"ResponsePengirimanSelesai{" + 
			"pesan = '" + pesan + '\'' + 
			",statusPengirimanSelesai = '" + statusPengirimanSelesai + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}