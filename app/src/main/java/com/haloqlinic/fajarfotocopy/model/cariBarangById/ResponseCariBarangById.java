package com.haloqlinic.fajarfotocopy.model.cariBarangById;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCariBarangById{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangById")
	private List<SearchBarangByIdItem> searchBarangById;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangById(List<SearchBarangByIdItem> searchBarangById){
		this.searchBarangById = searchBarangById;
	}

	public List<SearchBarangByIdItem> getSearchBarangById(){
		return searchBarangById;
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
			"ResponseCariBarangById{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangById = '" + searchBarangById + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}