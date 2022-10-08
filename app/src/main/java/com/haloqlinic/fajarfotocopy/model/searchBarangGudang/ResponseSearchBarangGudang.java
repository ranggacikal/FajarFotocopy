package com.haloqlinic.fajarfotocopy.model.searchBarangGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchBarangGudang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangGudang")
	private List<SearchBarangGudangItem> searchBarangGudang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangGudang(List<SearchBarangGudangItem> searchBarangGudang){
		this.searchBarangGudang = searchBarangGudang;
	}

	public List<SearchBarangGudangItem> getSearchBarangGudang(){
		return searchBarangGudang;
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
			"ResponseSearchBarangGudang{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangGudang = '" + searchBarangGudang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}