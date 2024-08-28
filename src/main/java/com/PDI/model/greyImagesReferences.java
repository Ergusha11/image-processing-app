package com.PDI.model;

import java.util.HashMap;

public class greyImagesReferences{
	private HashMap<Integer,int[][]> greyImages;
	private static final greyImagesReferences images = new greyImagesReferences();
	private HashMap<Integer, String> imageNames;

	private greyImagesReferences(){
		greyImages = new HashMap<>();
		imageNames = new HashMap<>();
	}

	public static greyImagesReferences getInstance(){
		return images;  
	}

	public HashMap<Integer,int[][]> getImage(){
		return greyImages;
	}

	public HashMap<Integer, String> getImageNames() {
        return imageNames;
    }

	public void addImage(int Id,int[][] matriz,String imageName){
		this.greyImages.put(Id,matriz);
		this.imageNames.put(Id,imageName);
	}

	public void removeImage(int Id){
		this.greyImages.remove(Id);
		this.imageNames.remove(Id);
	}

	public String getImageName(int id) {
        return this.imageNames.get(id);
    }
}
