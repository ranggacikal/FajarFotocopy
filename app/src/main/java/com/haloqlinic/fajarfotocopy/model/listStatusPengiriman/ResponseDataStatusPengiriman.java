package com.haloqlinic.fajarfotocopy.model.listStatusPengiriman;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataStatusPengiriman{

	@SerializedName("dataStatusPengiriman")
	private List<DataStatusPengirimanItem> dataStatusPengiriman;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public void setDataStatusPengiriman(List<DataStatusPengirimanItem> dataStatusPengiriman){
		this.dataStatusPengiriman = dataStatusPengiriman;
	}

	public List<DataStatusPengirimanItem> getDataStatusPengiriman(){
		return dataStatusPengiriman;
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
			"ResponseDataStatusPengiriman{" + 
			"dataStatusPengiriman = '" + dataStatusPengiriman + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}