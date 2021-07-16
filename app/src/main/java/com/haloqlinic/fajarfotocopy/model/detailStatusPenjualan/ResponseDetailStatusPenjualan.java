package com.haloqlinic.fajarfotocopy.model.detailStatusPenjualan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailStatusPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("detailStatusPenjualan")
	private List<DetailStatusPenjualanItem> detailStatusPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDetailStatusPenjualan(List<DetailStatusPenjualanItem> detailStatusPenjualan){
		this.detailStatusPenjualan = detailStatusPenjualan;
	}

	public List<DetailStatusPenjualanItem> getDetailStatusPenjualan(){
		return detailStatusPenjualan;
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
			"ResponseDetailStatusPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",detailStatusPenjualan = '" + detailStatusPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}