package com.haloqlinic.fajarfotocopy.model.searchBarangOutletById;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBarangOutletById{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangOutletById")
	private List<SearchBarangOutletByIdItem> searchBarangOutletById;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangOutletById(List<SearchBarangOutletByIdItem> searchBarangOutletById){
		this.searchBarangOutletById = searchBarangOutletById;
	}

	public List<SearchBarangOutletByIdItem> getSearchBarangOutletById(){
		return searchBarangOutletById;
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
			"ResponseBarangOutletById{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangOutletById = '" + searchBarangOutletById + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}