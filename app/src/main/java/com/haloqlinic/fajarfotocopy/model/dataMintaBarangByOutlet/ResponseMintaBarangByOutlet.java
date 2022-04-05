package com.haloqlinic.fajarfotocopy.model.dataMintaBarangByOutlet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMintaBarangByOutlet{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataPermintaanBarang")
	private List<DataPermintaanBarangItem> dataPermintaanBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataPermintaanBarang(List<DataPermintaanBarangItem> dataPermintaanBarang){
		this.dataPermintaanBarang = dataPermintaanBarang;
	}

	public List<DataPermintaanBarangItem> getDataPermintaanBarang(){
		return dataPermintaanBarang;
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
			"ResponseMintaBarangByOutlet{" + 
			"pesan = '" + pesan + '\'' + 
			",dataPermintaanBarang = '" + dataPermintaanBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}