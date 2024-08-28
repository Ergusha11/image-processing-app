package com.PDI.controller.modelColor;


import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class RGBtoYIQ implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel,int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		double Y,I,Q;
		double R,G,B;
		int color;
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho ;x++){
				infoImage[y][x] = new pixel();
				R = matrizPixel[y][x].getRed()/255;
				G = matrizPixel[y][x].getGreen()/255;
				B = matrizPixel[y][x].getBlue()/255;

				Y = (0.299f * R) + (0.587f * G) + (0.114f * B);
				I =  (0.596f * R) + (-0.274f * G) + (-0.322f * B);
				Q = (0.211f * R) + (-0.523f * G) + (0.312f * B);

				infoImage[y][x].setRed(Y);
				infoImage[y][x].setGreen(I);
				infoImage[y][x].setBlue(Q);
				
				Y = FuntionStatic.validar(Y*255);
				I = FuntionStatic.validar((I + 0.59)*255);
				Q = FuntionStatic.validar((Q + 0.52)*255);

				color = (255<<24) | (((int)Y) << 16) | (((int)I) << 8) | ((int)Q);
				
				imagenTemporal[y][x] = color;
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}
}
