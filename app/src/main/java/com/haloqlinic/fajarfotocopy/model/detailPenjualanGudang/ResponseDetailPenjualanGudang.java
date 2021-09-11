package com.haloqlinic.fajarfotocopy.model.detailPenjualanGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailPenjualanGudang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("detailPenjualanGudang")
	private List<DetailPenjualanGudangItem> detailPenjualanGudang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDetailPenjualanGudang(List<DetailPenjualanGudangItem> detailPenjualanGudang){
		this.detailPenjualanGudang = detailPenjualanGudang;
	}

	public List<DetailPenjualanGudangItem> getDetailPenjualanGudang(){
		return detailPenjualanGudang;
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
			"ResponseDetailPenjualanGudang{" + 
			"pesan = '" + pesan + '\'' + 
			",detailPenjualanGudang = '" + detailPenjualanGudang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}