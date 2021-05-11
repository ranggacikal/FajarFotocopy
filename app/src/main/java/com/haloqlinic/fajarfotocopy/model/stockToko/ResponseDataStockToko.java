package com.haloqlinic.fajarfotocopy.model.stockToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataStockToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getStockByToko")
	private List<GetStockByTokoItem> getStockByToko;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetStockByToko(List<GetStockByTokoItem> getStockByToko){
		this.getStockByToko = getStockByToko;
	}

	public List<GetStockByTokoItem> getGetStockByToko(){
		return getStockByToko;
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
			"ResponseDataStockToko{" + 
			"pesan = '" + pesan + '\'' + 
			",getStockByToko = '" + getStockByToko + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}