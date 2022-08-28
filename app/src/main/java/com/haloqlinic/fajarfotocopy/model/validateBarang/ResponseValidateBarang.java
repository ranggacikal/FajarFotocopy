package com.haloqlinic.fajarfotocopy.model.validateBarang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseValidateBarang{

	@SerializedName("dataValidateBarang")
	private List<DataValidateBarangItem> dataValidateBarang;

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("status")
	private int status;

	public void setDataValidateBarang(List<DataValidateBarangItem> dataValidateBarang){
		this.dataValidateBarang = dataValidateBarang;
	}

	public List<DataValidateBarangItem> getDataValidateBarang(){
		return dataValidateBarang;
	}

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
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
			"ResponseValidateBarang{" + 
			"dataValidateBarang = '" + dataValidateBarang + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}