package com.haloqlinic.fajarfotocopy.model.listPengiriman;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListPengiriman{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getListPengiriman")
	private List<GetListPengirimanItem> getListPengiriman;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetListPengiriman(List<GetListPengirimanItem> getListPengiriman){
		this.getListPengiriman = getListPengiriman;
	}

	public List<GetListPengirimanItem> getGetListPengiriman(){
		return getListPengiriman;
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
			"ResponseListPengiriman{" + 
			"pesan = '" + pesan + '\'' + 
			",getListPengiriman = '" + getListPengiriman + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}