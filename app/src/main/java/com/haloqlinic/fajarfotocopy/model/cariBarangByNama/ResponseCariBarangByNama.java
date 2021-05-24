package com.haloqlinic.fajarfotocopy.model.cariBarangByNama;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCariBarangByNama{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangByNama")
	private List<SearchBarangByNamaItem> searchBarangByNama;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangByNama(List<SearchBarangByNamaItem> searchBarangByNama){
		this.searchBarangByNama = searchBarangByNama;
	}

	public List<SearchBarangByNamaItem> getSearchBarangByNama(){
		return searchBarangByNama;
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
			"ResponseCariBarangByNama{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangByNama = '" + searchBarangByNama + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}