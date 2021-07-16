package com.haloqlinic.fajarfotocopy.model.transaksiByBulan;

import com.google.gson.annotations.SerializedName;

public class TransaksiByBulanItem{

	@SerializedName("nama_kasir")
	private String namaKasir;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("metode_bayar")
	private String metodeBayar;

	@SerializedName("jumlah_diskon")
	private String jumlahDiskon;

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("id_status_penjualan")
	private String idStatusPenjualan;

	@SerializedName("jumlah_barang")
	private String jumlahBarang;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_barang_outlet")
	private String idBarangOutlet;

	public void setNamaKasir(String namaKasir){
		this.namaKasir = namaKasir;
	}

	public String getNamaKasir(){
		return namaKasir;
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

	public void setJumlahDiskon(String jumlahDiskon){
		this.jumlahDiskon = jumlahDiskon;
	}

	public String getJumlahDiskon(){
		return jumlahDiskon;
	}

	public void setIdPenjualan(String idPenjualan){
		this.idPenjualan = idPenjualan;
	}

	public String getIdPenjualan(){
		return idPenjualan;
	}

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setIdStatusPenjualan(String idStatusPenjualan){
		this.idStatusPenjualan = idStatusPenjualan;
	}

	public String getIdStatusPenjualan(){
		return idStatusPenjualan;
	}

	public void setJumlahBarang(String jumlahBarang){
		this.jumlahBarang = jumlahBarang;
	}

	public String getJumlahBarang(){
		return jumlahBarang;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setIdBarangOutlet(String idBarangOutlet){
		this.idBarangOutlet = idBarangOutlet;
	}

	public String getIdBarangOutlet(){
		return idBarangOutlet;
	}

	@Override
 	public String toString(){
		return 
			"TransaksiByBulanItem{" + 
			"nama_kasir = '" + namaKasir + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",metode_bayar = '" + metodeBayar + '\'' + 
			",jumlah_diskon = '" + jumlahDiskon + '\'' + 
			",id_penjualan = '" + idPenjualan + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",id_status_penjualan = '" + idStatusPenjualan + '\'' + 
			",jumlah_barang = '" + jumlahBarang + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			"}";
		}
}