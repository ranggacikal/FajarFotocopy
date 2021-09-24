package com.haloqlinic.fajarfotocopy.model.listPenjualanGudangByIdStatus;

import com.google.gson.annotations.SerializedName;

public class ListPenjualanGudangByIdStatusItem{

	@SerializedName("id_status_penjualan_gudang")
	private String idStatusPenjualanGudang;

	@SerializedName("id_penjualan_gudang")
	private String idPenjualanGudang;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("harga_jual_toko_pack")
	private String hargaJualTokoPack;

	@SerializedName("jumlah_barang")
	private String jumlahBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("harga_jual_toko")
	private String hargaJualToko;

	public void setIdStatusPenjualanGudang(String idStatusPenjualanGudang){
		this.idStatusPenjualanGudang = idStatusPenjualanGudang;
	}

	public String getIdStatusPenjualanGudang(){
		return idStatusPenjualanGudang;
	}

	public void setIdPenjualanGudang(String idPenjualanGudang){
		this.idPenjualanGudang = idPenjualanGudang;
	}

	public String getIdPenjualanGudang(){
		return idPenjualanGudang;
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

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setStatusPengiriman(String statusPengiriman){
		this.statusPengiriman = statusPengiriman;
	}

	public String getStatusPengiriman(){
		return statusPengiriman;
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

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setNamaUser(String namaUser){
		this.namaUser = namaUser;
	}

	public String getNamaUser(){
		return namaUser;
	}

	public void setHargaJualToko(String hargaJualToko){
		this.hargaJualToko = hargaJualToko;
	}

	public String getHargaJualToko(){
		return hargaJualToko;
	}

	@Override
 	public String toString(){
		return 
			"ListPenjualanGudangByIdStatusItem{" + 
			"id_status_penjualan_gudang = '" + idStatusPenjualanGudang + '\'' + 
			",id_penjualan_gudang = '" + idPenjualanGudang + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",harga_jual_toko_pack = '" + hargaJualTokoPack + '\'' + 
			",jumlah_barang = '" + jumlahBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_user = '" + namaUser + '\'' + 
			",harga_jual_toko = '" + hargaJualToko + '\'' + 
			"}";
		}
}