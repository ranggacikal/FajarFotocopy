package com.haloqlinic.fajarfotocopy.model.statusPengirimanByToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusPengirimanByToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("listPengirimanByOutlet")
	private List<ListPengirimanByOutletItem> listPengirimanByOutlet;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setListPengirimanByOutlet(List<ListPengirimanByOutletItem> listPengirimanByOutlet){
		this.listPengirimanByOutlet = listPengirimanByOutlet;
	}

	public List<ListPengirimanByOutletItem> getListPengirimanByOutlet(){
		return listPengirimanByOutlet;
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
			"ResponseStatusPengirimanByToko{" + 
			"pesan = '" + pesan + '\'' + 
			",listPengirimanByOutlet = '" + listPengirimanByOutlet + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}