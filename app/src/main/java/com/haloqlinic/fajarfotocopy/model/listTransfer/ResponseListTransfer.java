package com.haloqlinic.fajarfotocopy.model.listTransfer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListTransfer{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("listTransferBarang")
	private List<ListTransferBarangItem> listTransferBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setListTransferBarang(List<ListTransferBarangItem> listTransferBarang){
		this.listTransferBarang = listTransferBarang;
	}

	public List<ListTransferBarangItem> getListTransferBarang(){
		return listTransferBarang;
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
			"ResponseListTransfer{" + 
			"pesan = '" + pesan + '\'' + 
			",listTransferBarang = '" + listTransferBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}