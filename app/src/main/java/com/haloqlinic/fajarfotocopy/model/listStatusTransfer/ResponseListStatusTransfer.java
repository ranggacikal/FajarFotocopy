package com.haloqlinic.fajarfotocopy.model.listStatusTransfer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListStatusTransfer{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("listStatusTransfer")
	private List<ListStatusTransferItem> listStatusTransfer;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setListStatusTransfer(List<ListStatusTransferItem> listStatusTransfer){
		this.listStatusTransfer = listStatusTransfer;
	}

	public List<ListStatusTransferItem> getListStatusTransfer(){
		return listStatusTransfer;
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
			"ResponseListStatusTransfer{" + 
			"pesan = '" + pesan + '\'' + 
			",listStatusTransfer = '" + listStatusTransfer + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}