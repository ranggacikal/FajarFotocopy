package com.haloqlinic.fajarfotocopy.model.validateBarang;

import com.google.gson.annotations.SerializedName;

public class DataValidateBarangItem{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
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
			"DataValidateBarangItem{" + 
			"pesan = '" + pesan + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}