package com.haloqlinic.fajarfotocopy.model.sumPenjualanGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSumPenjualanGudang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSumPenjualanGudang")
	private List<DataSumPenjualanGudangItem> dataSumPenjualanGudang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSumPenjualanGudang(List<DataSumPenjualanGudangItem> dataSumPenjualanGudang){
		this.dataSumPenjualanGudang = dataSumPenjualanGudang;
	}

	public List<DataSumPenjualanGudangItem> getDataSumPenjualanGudang(){
		return dataSumPenjualanGudang;
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
			"ResponseSumPenjualanGudang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSumPenjualanGudang = '" + dataSumPenjualanGudang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}