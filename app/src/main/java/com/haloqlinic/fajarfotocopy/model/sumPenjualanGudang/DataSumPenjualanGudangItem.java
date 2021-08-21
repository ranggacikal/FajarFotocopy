package com.haloqlinic.fajarfotocopy.model.sumPenjualanGudang;

import com.google.gson.annotations.SerializedName;

public class DataSumPenjualanGudangItem{

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
			"DataSumPenjualanGudangItem{" + 
			"total = '" + total + '\'' + 
			"}";
		}
}