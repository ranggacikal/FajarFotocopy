package com.haloqlinic.fajarfotocopy.model.dataUserByToko;

import com.google.gson.annotations.SerializedName;

public class DataUserByOutletItem{

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

	public String getPassword(){
		return password;
	}

	public String getFoto(){
		return foto;
	}

	public String getLevel(){
		return level;
	}

	public String getNamaLengkap(){
		return namaLengkap;
	}

	public String getIdOutlet(){
		return idOutlet;
	}

	public String getIdUser(){
		return idUser;
	}

	public String getNamaOutlet(){
		return namaOutlet;
	}

	public String getUsername(){
		return username;
	}
}