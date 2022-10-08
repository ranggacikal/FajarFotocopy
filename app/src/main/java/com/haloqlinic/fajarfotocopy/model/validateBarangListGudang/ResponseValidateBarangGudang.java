package com.haloqlinic.fajarfotocopy.model.validateBarangListGudang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseValidateBarangGudang{

	@SerializedName("dataValidateBarangGudang")
	private List<DataValidateBarangGudangItem> dataValidateBarangGudang;

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("status")
	private int status;

	public void setDataValidateBarangGudang(List<DataValidateBarangGudangItem> dataValidateBarangGudang){
		this.dataValidateBarangGudang = dataValidateBarangGudang;
	}

	public List<DataValidateBarangGudangItem> getDataValidateBarangGudang(){
		return dataValidateBarangGudang;
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
			"ResponseValidateBarangGudang{" + 
			"dataValidateBarangGudang = '" + dataValidateBarangGudang + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}