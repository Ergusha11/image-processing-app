package com.PDI.controller.modelColor;

import java.awt.Color;

import com.PDI.model.greyImagesReferences;
import com.PDI.model.pixel;

public class escalaGrises implements interfaceColor{
	public int [][] convertModelColor(pixel[][] matrixPixel,int ID,String nombre) {
		greyImagesReferences refImg = greyImagesReferences.getInstance();
		int alto = matrixPixel.length;
		int ancho = matrixPixel[0].length;
		int [][] imageGrey = new int[alto][ancho]; 
		int [][] imagenTemporal = new int[alto][ancho];
		int pixel = 0;
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho ;x++){
				pixel = (int)(matrixPixel[y][x].getRed() + 
                        matrixPixel[y][x].getGreen()+ 
                        matrixPixel[y][x].getBlue())/3;
				
				imageGrey[y][x] = pixel;

				Color color = new Color(pixel,pixel,pixel);
				imagenTemporal[y][x] = color.getRGB();
			}
		}
		
		refImg.addImage(ID,imageGrey,nombre);

		return imagenTemporal;
	}	
}
