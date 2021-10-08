package com.haloqlinic.fajarfotocopy.model;

import com.google.gson.annotations.SerializedName;

public class DataPenjualanKaryawanTokoItem{

	@SerializedName("total")
	private String total;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	@Override
 	public String toString(){
		return 
			"DataPenjualanKaryawanTokoItem{" + 
			"total = '" + total + '\'' + 
			"}";
		}
}