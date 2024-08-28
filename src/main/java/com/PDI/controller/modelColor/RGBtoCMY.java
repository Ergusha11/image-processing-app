package com.PDI.controller.modelColor;

import java.awt.Color;

import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class RGBtoCMY implements interfaceColor{
	
	@Override
	public int[][] convertModelColor(pixel[][] matrixPixel,int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		final int vector = 255;
		int alto = matrixPixel.length;
		int ancho = matrixPixel[0].length;
		double C,M,Y;
		pixel [][] infoImagen = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho ;x++){
				infoImagen[y][x] = new pixel();
				C = vector - matrixPixel[y][x].getRed();
				M = vector - matrixPixel[y][x].getGreen();
				Y = vector - matrixPixel[y][x].getBlue();
				infoImagen[y][x].setRed(C);
				infoImagen[y][x].setGreen(M);
				infoImagen[y][x].setBlue(Y);
				Color color = new Color((int)C,(int)M,(int)Y);
				imagenTemporal[y][x] = color.getRGB();
				
			}
		}
		refImg.addImage(ID,infoImagen);
		return imagenTemporal;
	}
}
