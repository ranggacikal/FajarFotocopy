package com.haloqlinic.fajarfotocopy.model.detailStatusSupplier;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailStatusSupplierItem{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("id_status_penjualan_gudang")
	private String idStatusPenjualanGudang;

	@SerializedName("detailStatusSupplier")
	private List<DetailStatusSupplierItem> detailStatusSupplier;

	@SerializedName("status")
	private int status;

	@SerializedName("jumlah_kurang")
	private String jumlahKurang;

	@SerializedName("harga_modal_gudang")
	private String hargaModalGudang;

	@SerializedName("image_bukti_tf")
	private String imageBuktiTf;

	@SerializedName("id_penjualan_gudang")
	private String idPenjualanGudang;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("metode_bayar")
	private String metodeBayar;

	@SerializedName("jumlah_pack")
	private String jumlahPack;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("jumlah_barang")
	private String jumlahBarang;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("jumlah_bayar")
	private String jumlahBayar;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setIdStatusPenjualanGudang(String idStatusPenjualanGudang){
		this.idStatusPenjualanGudang = idStatusPenjualanGudang;
	}

	public String getIdStatusPenjualanGudang(){
		return idStatusPenjualanGudang;
	}

	public void setDetailStatusSupplier(List<DetailStatusSupplierItem> detailStatusSupplier){
		this.detailStatusSupplier = detailStatusSupplier;
	}

	public List<DetailStatusSupplierItem> getDetailStatusSupplier(){
		return detailStatusSupplier;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setJumlahKurang(String jumlahKurang){
		this.jumlahKurang = jumlahKurang;
	}

	public String getJumlahKurang(){
		return jumlahKurang;
	}

	public void setHargaModalGudang(String hargaModalGudang){
		this.hargaModalGudang = hargaModalGudang;
	}

	public String getHargaModalGudang(){
		return hargaModalGudang;
	}

	public void setImageBuktiTf(String imageBuktiTf){
		this.imageBuktiTf = imageBuktiTf;
	}

	public String getImageBuktiTf(){
		return imageBuktiTf;
	}

	public void setIdPenjualanGudang(String idPenjualanGudang){
		this.idPenjualanGudang = idPenjualanGudang;
	}

	public String getIdPenjualanGudang(){
		return idPenjualanGudang;
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

	public void setJumlahBayar(String jumlahBayar){
		this.jumlahBayar = jumlahBayar;
	}

	public String getJumlahBayar(){
		return jumlahBayar;
	}

	@Override
 	public String toString(){
		return 
			"DetailStatusSupplierItem{" + 
			"pesan = '" + pesan + '\'' + 
			",id_status_penjualan_gudang = '" + idStatusPenjualanGudang + '\'' + 
			",detailStatusSupplier = '" + detailStatusSupplier + '\'' + 
			",status = '" + status + '\'' + 
			",jumlah_kurang = '" + jumlahKurang + '\'' + 
			",harga_modal_gudang = '" + hargaModalGudang + '\'' + 
			",image_bukti_tf = '" + imageBuktiTf + '\'' + 
			",id_penjualan_gudang = '" + idPenjualanGudang + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",metode_bayar = '" + metodeBayar + '\'' + 
			",jumlah_pack = '" + jumlahPack + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",jumlah_barang = '" + jumlahBarang + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",nama_user = '" + namaUser + '\'' + 
			",jumlah_bayar = '" + jumlahBayar + '\'' + 
			"}";
		}
}