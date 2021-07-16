package com.haloqlinic.fajarfotocopy.model.transaksiByHari;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTransaksiByHari{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("transaksiByHari")
	private List<TransaksiByHariItem> transaksiByHari;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setTransaksiByHari(List<TransaksiByHariItem> transaksiByHari){
		this.transaksiByHari = transaksiByHari;
	}

	public List<TransaksiByHariItem> getTransaksiByHari(){
		return transaksiByHari;
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
			"ResponseTransaksiByHari{" + 
			"pesan = '" + pesan + '\'' + 
			",transaksiByHari = '" + transaksiByHari + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}