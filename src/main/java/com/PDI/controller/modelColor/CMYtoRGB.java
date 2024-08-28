package com.PDI.controller.modelColor;

import java.awt.Color;

import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class CMYtoRGB implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String nombre) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		final int vector = 255;
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		double R,G,B;
		pixel [][] infoImagen = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		for(int y = 0; y < alto;y++){
			for(int x = 0; x < ancho;x++){
				infoImagen[y][x] = new pixel();
				R = vector - matrizPixel[y][x].getRed();
				G = vector - matrizPixel[y][x].getGreen();
				B = vector - matrizPixel[y][x].getBlue();
				
				infoImagen[y][x].setRed(R);
				infoImagen[y][x].setGreen(G);
				infoImagen[y][x].setBlue(B);
				
				Color color = new Color((int)R,(int)G,(int)B);
				imagenTemporal[y][x] = color.getRGB();
			}
		}

		refImg.addImage(ID,infoImagen);
		return imagenTemporal;
	}

}
