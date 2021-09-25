package com.haloqlinic.fajarfotocopy.model.countBarangTransfer;

import com.google.gson.annotations.SerializedName;

public class CountBarangTransferItem{

	@SerializedName("id_status_transfer")
	private String idStatusTransfer;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("status_barang")
	private String statusBarang;

	@SerializedName("id_barang_outlet_pengirim")
	private String idBarangOutletPengirim;

	@SerializedName("id_transfer_barang")
	private String idTransferBarang;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

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

	public void setIdBarangOutletPengirim(String idBarangOutletPengirim){
		this.idBarangOutletPengirim = idBarangOutletPengirim;
	}

	public String getIdBarangOutletPengirim(){
		return idBarangOutletPengirim;
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

	@Override
 	public String toString(){
		return 
			"CountBarangTransferItem{" + 
			"id_status_transfer = '" + idStatusTransfer + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",status_barang = '" + statusBarang + '\'' + 
			",id_barang_outlet_pengirim = '" + idBarangOutletPengirim + '\'' + 
			",id_transfer_barang = '" + idTransferBarang + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			"}";
		}
}