package com.haloqlinic.fajarfotocopy.adapter.kasir.model;

public class DataPenjualan {

    public String id_barang_outlet;
    public String jumlah_barang;

    public DataPenjualan(String id_barang_outlet, String jumlah_barang) {
        this.id_barang_outlet = id_barang_outlet;
        this.jumlah_barang = jumlah_barang;
    }

    public String getId_barang_outlet() {
        return id_barang_outlet;
    }

    public void setId_barang_outlet(String id_barang_outlet) {
        this.id_barang_outlet = id_barang_outlet;
    }

    public String getJumlah_barang() {
        return jumlah_barang;
    }

    public void setJumlah_barang(String jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }
}
