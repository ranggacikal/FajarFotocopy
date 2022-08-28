package com.haloqlinic.fajarfotocopy.model.dataBarangOutletList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBarangOutletList{

	@SerializedName("dataBarangOutletList")
	private List<DataBarangOutletListItem> dataBarangOutletList;

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("status")
	private int status;

	public void setDataBarangOutletList(List<DataBarangOutletListItem> dataBarangOutletList){
		this.dataBarangOutletList = dataBarangOutletList;
	}

	public List<DataBarangOutletListItem> getDataBarangOutletList(){
		return dataBarangOutletList;
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
			"ResponseBarangOutletList{" + 
			"dataBarangOutletList = '" + dataBarangOutletList + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}