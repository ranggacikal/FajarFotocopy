package com.haloqlinic.fajarfotocopy.model.sumTransaksiBulan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSumTransaksiBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("sumTransaksiByBulan")
	private List<SumTransaksiByBulanItem> sumTransaksiByBulan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSumTransaksiByBulan(List<SumTransaksiByBulanItem> sumTransaksiByBulan){
		this.sumTransaksiByBulan = sumTransaksiByBulan;
	}

	public List<SumTransaksiByBulanItem> getSumTransaksiByBulan(){
		return sumTransaksiByBulan;
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
			"ResponseSumTransaksiBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",sumTransaksiByBulan = '" + sumTransaksiByBulan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}