package com.haloqlinic.fajarfotocopy.model.dataUserByLevel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataUserByLevel{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataUserByLevel")
	private List<DataUserByLevelItem> dataUserByLevel;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataUserByLevel(List<DataUserByLevelItem> dataUserByLevel){
		this.dataUserByLevel = dataUserByLevel;
	}

	public List<DataUserByLevelItem> getDataUserByLevel(){
		return dataUserByLevel;
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
			"ResponseDataUserByLevel{" + 
			"pesan = '" + pesan + '\'' + 
			",dataUserByLevel = '" + dataUserByLevel + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}