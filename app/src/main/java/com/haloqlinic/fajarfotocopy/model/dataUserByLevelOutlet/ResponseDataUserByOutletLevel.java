package com.haloqlinic.fajarfotocopy.model.dataUserByLevelOutlet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataUserByOutletLevel{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataUserByOutletDanLevel")
	private List<DataUserByOutletDanLevelItem> dataUserByOutletDanLevel;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataUserByOutletDanLevel(List<DataUserByOutletDanLevelItem> dataUserByOutletDanLevel){
		this.dataUserByOutletDanLevel = dataUserByOutletDanLevel;
	}

	public List<DataUserByOutletDanLevelItem> getDataUserByOutletDanLevel(){
		return dataUserByOutletDanLevel;
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
			"ResponseDataUserByOutletLevel{" + 
			"pesan = '" + pesan + '\'' + 
			",dataUserByOutletDanLevel = '" + dataUserByOutletDanLevel + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}