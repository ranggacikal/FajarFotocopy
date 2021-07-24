package com.haloqlinic.fajarfotocopy.model.detailTransferBarang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailTransferBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("detailStatusTransfer")
	private List<DetailStatusTransferItem> detailStatusTransfer;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDetailStatusTransfer(List<DetailStatusTransferItem> detailStatusTransfer){
		this.detailStatusTransfer = detailStatusTransfer;
	}

	public List<DetailStatusTransferItem> getDetailStatusTransfer(){
		return detailStatusTransfer;
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
			"ResponseDetailTransferBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",detailStatusTransfer = '" + detailStatusTransfer + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}