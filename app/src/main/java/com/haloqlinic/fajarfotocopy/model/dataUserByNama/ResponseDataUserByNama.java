package com.haloqlinic.fajarfotocopy.model.dataUserByNama;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataUserByNama{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataUserByNama")
	private List<DataUserByNamaItem> dataUserByNama;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataUserByNama(List<DataUserByNamaItem> dataUserByNama){
		this.dataUserByNama = dataUserByNama;
	}

	public List<DataUserByNamaItem> getDataUserByNama(){
		return dataUserByNama;
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
			"ResponseDataUserByNama{" + 
			"pesan = '" + pesan + '\'' + 
			",dataUserByNama = '" + dataUserByNama + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}