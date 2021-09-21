package com.haloqlinic.fajarfotocopy.model.sumTransaksiHari;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSumTransaksiHari{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("sumTransaksiByHari")
	private List<SumTransaksiByHariItem> sumTransaksiByHari;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSumTransaksiByHari(List<SumTransaksiByHariItem> sumTransaksiByHari){
		this.sumTransaksiByHari = sumTransaksiByHari;
	}

	public List<SumTransaksiByHariItem> getSumTransaksiByHari(){
		return sumTransaksiByHari;
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
			"ResponseSumTransaksiHari{" + 
			"pesan = '" + pesan + '\'' + 
			",sumTransaksiByHari = '" + sumTransaksiByHari + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}