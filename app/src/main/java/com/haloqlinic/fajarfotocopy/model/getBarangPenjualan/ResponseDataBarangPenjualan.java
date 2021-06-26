package com.haloqlinic.fajarfotocopy.model.getBarangPenjualan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataBarangPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("barangPenjualan")
	private List<BarangPenjualanItem> barangPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setBarangPenjualan(List<BarangPenjualanItem> barangPenjualan){
		this.barangPenjualan = barangPenjualan;
	}

	public List<BarangPenjualanItem> getBarangPenjualan(){
		return barangPenjualan;
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
			"ResponseDataBarangPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",barangPenjualan = '" + barangPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}