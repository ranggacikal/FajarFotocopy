package com.haloqlinic.fajarfotocopy.model.dataPermintaanBarang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataPermintaanBarangItem{

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("dataBarang")
	private List<DataBarangItem> dataBarang;

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
	}

	public void setDataBarang(List<DataBarangItem> dataBarang){
		this.dataBarang = dataBarang;
	}

	public List<DataBarangItem> getDataBarang(){
		return dataBarang;
	}

	@Override
 	public String toString(){
		return 
			"DataPermintaanBarangItem{" + 
			"id_outlet = '" + idOutlet + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",dataBarang = '" + dataBarang + '\'' + 
			"}";
		}
}