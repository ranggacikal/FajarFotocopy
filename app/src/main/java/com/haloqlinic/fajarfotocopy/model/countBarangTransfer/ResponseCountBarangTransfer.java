package com.haloqlinic.fajarfotocopy.model.countBarangTransfer;

public class ResponseCountBarangTransfer{
	private int countBarangTransfer;
	private String pesan;
	private int status;

	public void setCountBarangTransfer(int countBarangTransfer){
		this.countBarangTransfer = countBarangTransfer;
	}

	public int getCountBarangTransfer(){
		return countBarangTransfer;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCountBarangTransfer{" + 
			"countBarangTransfer = '" + countBarangTransfer + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
