package com.haloqlinic.fajarfotocopy.model.searchBarangOutletByNama;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBarangOutletByNama{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchBarangOutletByNama")
	private List<SearchBarangOutletByNamaItem> searchBarangOutletByNama;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchBarangOutletByNama(List<SearchBarangOutletByNamaItem> searchBarangOutletByNama){
		this.searchBarangOutletByNama = searchBarangOutletByNama;
	}

	public List<SearchBarangOutletByNamaItem> getSearchBarangOutletByNama(){
		return searchBarangOutletByNama;
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
			"ResponseBarangOutletByNama{" + 
			"pesan = '" + pesan + '\'' + 
			",searchBarangOutletByNama = '" + searchBarangOutletByNama + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}