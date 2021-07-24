package com.haloqlinic.fajarfotocopy.model.statusTransferByBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusTransferByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("statusTransferByBulan")
	private List<StatusTransferByBulanItem> statusTransferByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatusTransferByBulan(List<StatusTransferByBulanItem> statusTransferByBulan){
		this.statusTransferByBulan = statusTransferByBulan;
	}

	public List<StatusTransferByBulanItem> getStatusTransferByBulan(){
		return statusTransferByBulan;
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
			"ResponseStatusTransferByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",statusTransferByBulan = '" + statusTransferByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}