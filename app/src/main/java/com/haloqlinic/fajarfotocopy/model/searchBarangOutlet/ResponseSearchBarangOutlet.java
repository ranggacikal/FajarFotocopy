package com.haloqlinic.fajarfotocopy.model.searchBarangOutlet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchBarangOutlet{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangOutlet")
	private List<SearchBarangOutletItem> searchBarangOutlet;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangOutlet(List<SearchBarangOutletItem> searchBarangOutlet){
		this.searchBarangOutlet = searchBarangOutlet;
	}

	public List<SearchBarangOutletItem> getSearchBarangOutlet(){
		return searchBarangOutlet;
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
			"ResponseSearchBarangOutlet{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangOutlet = '" + searchBarangOutlet + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}