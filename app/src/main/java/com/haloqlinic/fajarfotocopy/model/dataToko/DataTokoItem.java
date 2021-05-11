package com.haloqlinic.fajarfotocopy.model.dataToko;

import com.google.gson.annotations.SerializedName;

public class DataTokoItem{

	@SerializedName("kota")
	private String kota;

	@SerializedName("jumlah_anggota")
	private String jumlahAnggota;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("gaji")
	private String gaji;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("persentase")
	private String persentase;

	@SerializedName("alamat")
	private String alamat;

	public void setKota(String kota){
		this.kota = kota;
	}

	public String getKota(){
		return kota;
	}

	public void setJumlahAnggota(String jumlahAnggota){
		this.jumlahAnggota = jumlahAnggota;
	}

	public String getJumlahAnggota(){
		return jumlahAnggota;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setGaji(String gaji){
		this.gaji = gaji;
	}

	public String getGaji(){
		return gaji;
	}

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
	}

	public void setPersentase(String persentase){
		this.persentase = persentase;
	}

	public String getPersentase(){
		return persentase;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"DataTokoItem{" + 
			"kota = '" + kota + '\'' + 
			",jumlah_anggota = '" + jumlahAnggota + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",gaji = '" + gaji + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",persentase = '" + persentase + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}