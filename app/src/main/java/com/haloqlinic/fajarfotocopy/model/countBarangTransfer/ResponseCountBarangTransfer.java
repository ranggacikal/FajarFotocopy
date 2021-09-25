package com.haloqlinic.fajarfotocopy.model.countBarangTransfer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCountBarangTransfer{

	@SerializedName("countBarangTransfer")
	private List<CountBarangTransferItem> countBarangTransfer;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public void setCountBarangTransfer(List<CountBarangTransferItem> countBarangTransfer){
		this.countBarangTransfer = countBarangTransfer;
	}

	public List<CountBarangTransferItem> getCountBarangTransfer(){
		return countBarangTransfer;
	}

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
			"ResponseCountBarangTransfer{" + 
			"countBarangTransfer = '" + countBarangTransfer + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}