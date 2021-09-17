package com.haloqlinic.fajarfotocopy.model.listTransfer;

import com.google.gson.annotations.SerializedName;

public class ListTransferBarangItem{

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("id_transfer_barang")
	private String idTransferBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("id_status_transfer")
	private String idStatusTransfer;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("diskon_pack")
	private String diskonPack;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("status_barang")
	private String statusBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_barang_outlet_pengirim")
	private String idBarangOutletPengirim;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("diskon")
	private String diskon;

	public void setHargaJualPack(String hargaJualPack){
		this.hargaJualPack = hargaJualPack;
	}

	public String getHargaJualPack(){
		return hargaJualPack;
	}

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
	}

	public void setIdTransferBarang(String idTransferBarang){
		this.idTransferBarang = idTransferBarang;
	}

	public String getIdTransferBarang(){
		return idTransferBarang;
	}

	public void setJumlahPack(String jumlahPack){
		this.jumlahPack = jumlahPack;
	}

	public String getJumlahPack(){
		return jumlahPack;
	}

	public void setIdStatusTransfer(String idStatusTransfer){
		this.idStatusTransfer = idStatusTransfer;
	}

	public String getIdStatusTransfer(){
		return idStatusTransfer;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setDiskonPack(String diskonPack){
		this.diskonPack = diskonPack;
	}

	public String getDiskonPack(){
		return diskonPack;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setStatusBarang(String statusBarang){
		this.statusBarang = statusBarang;
	}

	public String getStatusBarang(){
		return statusBarang;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setIdBarangOutletPengirim(String idBarangOutletPengirim){
		this.idBarangOutletPengirim = idBarangOutletPengirim;
	}

	public String getIdBarangOutletPengirim(){
		return idBarangOutletPengirim;
	}

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	public void setDiskon(String diskon){
		this.diskon = diskon;
	}

	public String getDiskon(){
		return diskon;
	}

	@Override
 	public String toString(){
		return 
			"ListTransferBarangItem{" + 
			"harga_jual_pack = '" + hargaJualPack + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",id_transfer_barang = '" + idTransferBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",id_status_transfer = '" + idStatusTransfer + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",diskon_pack = '" + diskonPack + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_barang = '" + statusBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",id_barang_outlet_pengirim = '" + idBarangOutletPengirim + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",diskon = '" + diskon + '\'' + 
			"}";
		}
}