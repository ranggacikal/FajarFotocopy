package com.haloqlinic.fajarfotocopy.model.getBarangPenjualan;

import com.google.gson.annotations.SerializedName;

public class BarangPenjualanItem{

	@SerializedName("nama_kasir")
	private String namaKasir;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("id_kategori_barang")
	private String idKategoriBarang;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan_barang")
	private String statusPenjualanBarang;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("jenis_satuan")
	private String jenisSatuan;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("id_status_penjualan")
	private String idStatusPenjualan;

	@SerializedName("harga_jual_toko_pack")
	private String hargaJualTokoPack;

	@SerializedName("jumlah_barang")
	private String jumlahBarang;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("harga_jual_toko")
	private String hargaJualToko;

	@SerializedName("id_barang_outlet")
	private String idBarangOutlet;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setNamaKasir(String namaKasir){
		this.namaKasir = namaKasir;
	}

	public String getNamaKasir(){
		return namaKasir;
	}

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
	}

	public void setJumlahPack(String jumlahPack){
		this.jumlahPack = jumlahPack;
	}

	public String getJumlahPack(){
		return jumlahPack;
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

	public void setIdKategoriBarang(String idKategoriBarang){
		this.idKategoriBarang = idKategoriBarang;
	}

	public String getIdKategoriBarang(){
		return idKategoriBarang;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setStatusPenjualanBarang(String statusPenjualanBarang){
		this.statusPenjualanBarang = statusPenjualanBarang;
	}

	public String getStatusPenjualanBarang(){
		return statusPenjualanBarang;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setJenisSatuan(String jenisSatuan){
		this.jenisSatuan = jenisSatuan;
	}

	public String getJenisSatuan(){
		return jenisSatuan;
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

	public void setHargaJualTokoPack(String hargaJualTokoPack){
		this.hargaJualTokoPack = hargaJualTokoPack;
	}

	public String getHargaJualTokoPack(){
		return hargaJualTokoPack;
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

	public void setHargaJualToko(String hargaJualToko){
		this.hargaJualToko = hargaJualToko;
	}

	public String getHargaJualToko(){
		return hargaJualToko;
	}

	public void setIdBarangOutlet(String idBarangOutlet){
		this.idBarangOutlet = idBarangOutlet;
	}

	public String getIdBarangOutlet(){
		return idBarangOutlet;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}

	@Override
 	public String toString(){
		return 
			"BarangPenjualanItem{" + 
			"nama_kasir = '" + namaKasir + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",id_penjualan = '" + idPenjualan + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",id_kategori_barang = '" + idKategoriBarang + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan_barang = '" + statusPenjualanBarang + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",jenis_satuan = '" + jenisSatuan + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",id_status_penjualan = '" + idStatusPenjualan + '\'' + 
			",harga_jual_toko_pack = '" + hargaJualTokoPack + '\'' + 
			",jumlah_barang = '" + jumlahBarang + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			",id_barang_outlet = '" + idBarangOutlet + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}