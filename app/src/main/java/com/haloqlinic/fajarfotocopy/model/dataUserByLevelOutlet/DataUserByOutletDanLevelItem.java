package com.haloqlinic.fajarfotocopy.model.dataUserByLevelOutlet;

import com.google.gson.annotations.SerializedName;

public class DataUserByOutletDanLevelItem{

	@SerializedName("password")
	private String password;

	@SerializedName("foto")
	private String foto;

	@SerializedName("level")
	private String level;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("id_outlet")
	private String idOutlet;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("nama_outlet")
	private String namaOutlet;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
	}

	public void setIdOutlet(String idOutlet){
		this.idOutlet = idOutlet;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setNamaOutlet(String namaOutlet){
		this.namaOutlet = namaOutlet;
	}

	public String getNamaOutlet(){
		return namaOutlet;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DataUserByOutletDanLevelItem{" + 
			"password = '" + password + '\'' + 
			",foto = '" + foto + '\'' + 
			",level = '" + level + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",nama_outlet = '" + namaOutlet + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}