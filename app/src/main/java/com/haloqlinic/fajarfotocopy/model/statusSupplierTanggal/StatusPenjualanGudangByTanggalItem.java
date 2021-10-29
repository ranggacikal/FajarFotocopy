package com.haloqlinic.fajarfotocopy.model.statusSupplierTanggal;

import com.google.gson.annotations.SerializedName;

public class StatusPenjualanGudangByTanggalItem{

	@SerializedName("jumlah_kurang")
	private String jumlahKurang;

	@SerializedName("image_bukti_tf")
	private String imageBuktiTf;

	@SerializedName("driver_id")
	private String driverId;

	@SerializedName("id_status_penjualan_gudang")
	private String idStatusPenjualanGudang;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("metode_bayar")
	private String metodeBayar;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("pilihan_kirim")
	private String pilihanKirim;

	@SerializedName("alamat_tujuan")
	private String alamatTujuan;

	@SerializedName("no_status_penjualan_gudang")
	private String noStatusPenjualanGudang;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("status_pengiriman")
	private String statusPengiriman;

	@SerializedName("jumlah_bayar")
	private String jumlahBayar;

	public void setJumlahKurang(String jumlahKurang){
		this.jumlahKurang = jumlahKurang;
	}

	public String getJumlahKurang(){
		return jumlahKurang;
	}

	public void setImageBuktiTf(String imageBuktiTf){
		this.imageBuktiTf = imageBuktiTf;
	}

	public String getImageBuktiTf(){
		return imageBuktiTf;
	}

	public void setDriverId(String driverId){
		this.driverId = driverId;
	}

	public String getDriverId(){
		return driverId;
	}

	public void setIdStatusPenjualanGudang(String idStatusPenjualanGudang){
		this.idStatusPenjualanGudang = idStatusPenjualanGudang;
	}

	public String getIdStatusPenjualanGudang(){
		return idStatusPenjualanGudang;
	}

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
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

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setPilihanKirim(String pilihanKirim){
		this.pilihanKirim = pilihanKirim;
	}

	public String getPilihanKirim(){
		return pilihanKirim;
	}

	public void setAlamatTujuan(String alamatTujuan){
		this.alamatTujuan = alamatTujuan;
	}

	public String getAlamatTujuan(){
		return alamatTujuan;
	}

	public void setNoStatusPenjualanGudang(String noStatusPenjualanGudang){
		this.noStatusPenjualanGudang = noStatusPenjualanGudang;
	}

	public String getNoStatusPenjualanGudang(){
		return noStatusPenjualanGudang;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setStatusPengiriman(String statusPengiriman){
		this.statusPengiriman = statusPengiriman;
	}

	public String getStatusPengiriman(){
		return statusPengiriman;
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
			"StatusPenjualanGudangByTanggalItem{" + 
			"jumlah_kurang = '" + jumlahKurang + '\'' + 
			",image_bukti_tf = '" + imageBuktiTf + '\'' + 
			",driver_id = '" + driverId + '\'' + 
			",id_status_penjualan_gudang = '" + idStatusPenjualanGudang + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",metode_bayar = '" + metodeBayar + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",pilihan_kirim = '" + pilihanKirim + '\'' + 
			",alamat_tujuan = '" + alamatTujuan + '\'' + 
			",no_status_penjualan_gudang = '" + noStatusPenjualanGudang + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",status_pengiriman = '" + statusPengiriman + '\'' + 
			",jumlah_bayar = '" + jumlahBayar + '\'' + 
			"}";
		}
}