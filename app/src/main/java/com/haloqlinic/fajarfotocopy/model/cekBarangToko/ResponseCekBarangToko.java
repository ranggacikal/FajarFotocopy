package com.haloqlinic.fajarfotocopy.model.cekBarangToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCekBarangToko{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	@SerializedName("cekBarangToko")
	private List<CekBarangTokoItem> cekBarangToko;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setCekBarangToko(List<CekBarangTokoItem> cekBarangToko){
		this.cekBarangToko = cekBarangToko;
	}

	public List<CekBarangTokoItem> getCekBarangToko(){
		return cekBarangToko;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCekBarangToko{" + 
			"pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			",cekBarangToko = '" + cekBarangToko + '\'' + 
			"}";
		}
}