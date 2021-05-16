package com.haloqlinic.fajarfotocopy.model.cariToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCariToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataCariToko")
	private List<DataCariTokoItem> dataCariToko;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataCariToko(List<DataCariTokoItem> dataCariToko){
		this.dataCariToko = dataCariToko;
	}

	public List<DataCariTokoItem> getDataCariToko(){
		return dataCariToko;
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
			"ResponseCariToko{" + 
			"pesan = '" + pesan + '\'' + 
			",dataCariToko = '" + dataCariToko + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}