package com.haloqlinic.fajarfotocopy.model.stockByIdBarang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseStockByIdBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("getStockByIdBarang")
	private List<GetStockByIdBarangItem> getStockByIdBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setGetStockByIdBarang(List<GetStockByIdBarangItem> getStockByIdBarang){
		this.getStockByIdBarang = getStockByIdBarang;
	}

	public List<GetStockByIdBarangItem> getGetStockByIdBarang(){
		return getStockByIdBarang;
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
			"ResponseStockByIdBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",getStockByIdBarang = '" + getStockByIdBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}