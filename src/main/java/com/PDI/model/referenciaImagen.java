package com.PDI.model;

import java.util.HashMap;

public class referenciaImagen{
	private HashMap<Integer,pixel[][]> reference; 
	private static final referenciaImagen instance = new referenciaImagen();
	
	private referenciaImagen(){
		reference = new HashMap<>();
	}

	public static referenciaImagen getInstance(){
		return instance;
	}
	
	public HashMap<Integer,pixel[][]> getImage(){
		return reference;
	}

	public void addImage(int Id,pixel[][] matriz){
		this.reference.put(Id,matriz);
	}

	public void removeImage(int Id){
		this.reference.remove(Id);
	}
}
