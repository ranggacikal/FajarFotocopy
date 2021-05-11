package com.haloqlinic.fajarfotocopy.model.searchStockTokoGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchStockTokoGudang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("searchStockByToko")
	private List<SearchStockByTokoItem> searchStockByToko;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSearchStockByToko(List<SearchStockByTokoItem> searchStockByToko){
		this.searchStockByToko = searchStockByToko;
	}

	public List<SearchStockByTokoItem> getSearchStockByToko(){
		return searchStockByToko;
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
			"ResponseSearchStockTokoGudang{" + 
			"pesan = '" + pesan + '\'' + 
			",searchStockByToko = '" + searchStockByToko + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}