package com.haloqlinic.fajarfotocopy.model.validateBarangListGudang;

import com.google.gson.annotations.SerializedName;

public class DataValidateBarangGudangItem{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("sukses")
	private boolean sukses;

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

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataValidateBarangGudangItem{" + 
			"pesan = '" + pesan + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}