package com.haloqlinic.fajarfotocopy.model.getIdBarangOutlet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetIdBarangOutlet{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("idBarangOutlet")
	private List<IdBarangOutletItem> idBarangOutlet;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setIdBarangOutlet(List<IdBarangOutletItem> idBarangOutlet){
		this.idBarangOutlet = idBarangOutlet;
	}

	public List<IdBarangOutletItem> getIdBarangOutlet(){
		return idBarangOutlet;
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
			"ResponseGetIdBarangOutlet{" + 
			"pesan = '" + pesan + '\'' + 
			",idBarangOutlet = '" + idBarangOutlet + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}