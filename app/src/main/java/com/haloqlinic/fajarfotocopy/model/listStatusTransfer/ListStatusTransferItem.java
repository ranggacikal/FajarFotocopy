package com.haloqlinic.fajarfotocopy.model.listStatusTransfer;

import com.google.gson.annotations.SerializedName;

public class ListStatusTransferItem{

	@SerializedName("id_status_transfer")
	private String idStatusTransfer;

	@SerializedName("outlet_penerima")
	private String outletPenerima;

	@SerializedName("tanggal_transfer")
	private String tanggalTransfer;

	@SerializedName("id_outlet_penerima")
	private String idOutletPenerima;

	@SerializedName("id_outlet_pengirim")
	private String idOutletPengirim;

	@SerializedName("status_transfer")
	private String statusTransfer;

	@SerializedName("outlet_pengirim")
	private String outletPengirim;

	public void setIdStatusTransfer(String idStatusTransfer){
		this.idStatusTransfer = idStatusTransfer;
	}

	public String getIdStatusTransfer(){
		return idStatusTransfer;
	}

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

	public void setIdOutletPenerima(String idOutletPenerima){
		this.idOutletPenerima = idOutletPenerima;
	}

	public String getIdOutletPenerima(){
		return idOutletPenerima;
	}

	public void setIdOutletPengirim(String idOutletPengirim){
		this.idOutletPengirim = idOutletPengirim;
	}

	public String getIdOutletPengirim(){
		return idOutletPengirim;
	}

	public void setStatusTransfer(String statusTransfer){
		this.statusTransfer = statusTransfer;
	}

	public String getStatusTransfer(){
		return statusTransfer;
	}

	public void setOutletPengirim(String outletPengirim){
		this.outletPengirim = outletPengirim;
	}

	public String getOutletPengirim(){
		return outletPengirim;
	}

	@Override
 	public String toString(){
		return 
			"ListStatusTransferItem{" + 
			"id_status_transfer = '" + idStatusTransfer + '\'' + 
			",outlet_penerima = '" + outletPenerima + '\'' + 
			",tanggal_transfer = '" + tanggalTransfer + '\'' + 
			",id_outlet_penerima = '" + idOutletPenerima + '\'' + 
			",id_outlet_pengirim = '" + idOutletPengirim + '\'' + 
			",status_transfer = '" + statusTransfer + '\'' + 
			",outlet_pengirim = '" + outletPengirim + '\'' + 
			"}";
		}
}