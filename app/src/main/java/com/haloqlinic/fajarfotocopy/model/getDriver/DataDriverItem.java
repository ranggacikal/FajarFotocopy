package com.haloqlinic.fajarfotocopy.model.getDriver;

import com.google.gson.annotations.SerializedName;

public class DataDriverItem{

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

	@SerializedName("firebase_token")
	private String firebaseToken;

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

	public void setFirebaseToken(String firebaseToken){
		this.firebaseToken = firebaseToken;
	}

	public String getFirebaseToken(){
		return firebaseToken;
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
			"DataDriverItem{" + 
			"password = '" + password + '\'' + 
			",foto = '" + foto + '\'' + 
			",level = '" + level + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",id_outlet = '" + idOutlet + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",firebase_token = '" + firebaseToken + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}