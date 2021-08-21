package com.haloqlinic.fajarfotocopy.model.getBarangPenjualanGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBarangPenjualanGudang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("barangPenjualanGudang")
	private List<BarangPenjualanGudangItem> barangPenjualanGudang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setBarangPenjualanGudang(List<BarangPenjualanGudangItem> barangPenjualanGudang){
		this.barangPenjualanGudang = barangPenjualanGudang;
	}

	public List<BarangPenjualanGudangItem> getBarangPenjualanGudang(){
		return barangPenjualanGudang;
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
			"ResponseBarangPenjualanGudang{" + 
			"pesan = '" + pesan + '\'' + 
			",barangPenjualanGudang = '" + barangPenjualanGudang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}