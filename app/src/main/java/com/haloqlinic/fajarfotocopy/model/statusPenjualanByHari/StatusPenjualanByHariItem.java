package com.haloqlinic.fajarfotocopy.model.statusPenjualanByHari;

import com.google.gson.annotations.SerializedName;

public class StatusPenjualanByHariItem{

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("id_status_penjualan")
	private String idStatusPenjualan;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("metode_bayar")
	private String metodeBayar;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("jumlah_diskon")
	private String jumlahDiskon;

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setIdStatusPenjualan(String idStatusPenjualan){
		this.idStatusPenjualan = idStatusPenjualan;
	}

	public String getIdStatusPenjualan(){
		return idStatusPenjualan;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	public void setMetodeBayar(String metodeBayar){
		this.metodeBayar = metodeBayar;
	}

	public String getMetodeBayar(){
		return metodeBayar;
	}

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
	}

	public void setJumlahDiskon(String jumlahDiskon){
		this.jumlahDiskon = jumlahDiskon;
	}

	public String getJumlahDiskon(){
		return jumlahDiskon;
	}

	@Override
 	public String toString(){
		return 
			"StatusPenjualanByHariItem{" + 
			"tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",id_status_penjualan = '" + idStatusPenjualan + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",metode_bayar = '" + metodeBayar + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",jumlah_diskon = '" + jumlahDiskon + '\'' + 
			"}";
		}
}