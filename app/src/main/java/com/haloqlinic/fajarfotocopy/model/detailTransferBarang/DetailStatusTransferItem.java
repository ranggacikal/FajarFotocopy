package com.haloqlinic.fajarfotocopy.model.detailTransferBarang;

import com.google.gson.annotations.SerializedName;

public class DetailStatusTransferItem{

	@SerializedName("outlet_penerima")
	private String outletPenerima;

	@SerializedName("tanggal_transfer")
	private String tanggalTransfer;

	@SerializedName("harga_jual_pack")
	private String hargaJualPack;

	@SerializedName("id_transfer_barang")
	private String idTransferBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("outlet_pengirim")
	private String outletPengirim;

	@SerializedName("id_status_transfer")
	private String idStatusTransfer;

	@SerializedName("jumlah")
	private String jumlah;

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

	public void setOutletPenerima(String outletPenerima){
		this.outletPenerima = outletPenerima;
	}

	public String getOutletPenerima(){
		return outletPenerima;
	}

	public void setTanggalTransfer(String tanggalTransfer){
		this.tanggalTransfer = tanggalTransfer;
	}

	public String getTanggalTransfer(){
		return tanggalTransfer;
	}

	public void setHargaJualPack(String hargaJualPack){
		this.hargaJualPack = hargaJualPack;
	}

	public String getHargaJualPack(){
		return hargaJualPack;
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

	public void setOutletPengirim(String outletPengirim){
		this.outletPengirim = outletPengirim;
	}

	public String getOutletPengirim(){
		return outletPengirim;
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

	@Override
 	public String toString(){
		return 
			"DetailStatusTransferItem{" + 
			"outlet_penerima = '" + outletPenerima + '\'' + 
			",tanggal_transfer = '" + tanggalTransfer + '\'' + 
			",harga_jual_pack = '" + hargaJualPack + '\'' + 
			",id_transfer_barang = '" + idTransferBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",outlet_pengirim = '" + outletPengirim + '\'' + 
			",id_status_transfer = '" + idStatusTransfer + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_barang = '" + statusBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",id_barang_outlet_pengirim = '" + idBarangOutletPengirim + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			"}";
		}
}