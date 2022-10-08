package com.haloqlinic.fajarfotocopy.model.listBarangGudangValidate;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListBarangGudangValidate{

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("dataBarangGudangList")
	private List<DataBarangGudangListItem> dataBarangGudangList;

	@SerializedName("status")
	private int status;

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
	}

	public void setDataBarangGudangList(List<DataBarangGudangListItem> dataBarangGudangList){
		this.dataBarangGudangList = dataBarangGudangList;
	}

	public List<DataBarangGudangListItem> getDataBarangGudangList(){
		return dataBarangGudangList;
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
			"ResponseListBarangGudangValidate{" + 
			"sukses = '" + sukses + '\'' + 
			",dataBarangGudangList = '" + dataBarangGudangList + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}