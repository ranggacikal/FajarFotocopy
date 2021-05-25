package com.haloqlinic.fajarfotocopy.model.dataUserByToko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataUserByOutlet{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataUserByOutlet")
	private List<DataUserByOutletItem> dataUserByOutlet;

	@SerializedName("status")
	private int status;

	public String getPesan(){
		return pesan;
	}

	public List<DataUserByOutletItem> getDataUserByOutlet(){
		return dataUserByOutlet;
	}

	public int getStatus(){
		return status;
	}
}