package com.haloqlinic.fajarfotocopy.model.transaksiByBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTransaksiByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("transaksiByBulan")
	private List<TransaksiByBulanItem> transaksiByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setTransaksiByBulan(List<TransaksiByBulanItem> transaksiByBulan){
		this.transaksiByBulan = transaksiByBulan;
	}

	public List<TransaksiByBulanItem> getTransaksiByBulan(){
		return transaksiByBulan;
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
			"ResponseTransaksiByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",transaksiByBulan = '" + transaksiByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}