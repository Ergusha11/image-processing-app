package com.PDI.controller.modelColor;

import java.awt.Color;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class YIQtoRGB implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		double Y,I,Q;
		double R,G,B;
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho ;x++){
				infoImage[y][x] = new pixel();
				Y = matrizPixel[y][x].getRed();
				I = matrizPixel[y][x].getGreen();
				Q = matrizPixel[y][x].getBlue();
				//System.out.println("El valor de YIQ " + Y + " " + I + " "+ Q);
				R = (1.0f * Y) + (0.956f * I) + (0.114f * Q);
				G = (1.0f * Y) + (-0.272f * I) + (-0.647f * Q);
				B = (1.0f * Y) + (-1.106f * I) + (1.703f * Q);

				//System.out.println("El valor de RGB " + R + " " + G + " "+ B);
				R = R*255;
				G = G*255;
				B = B*255;
				
				R = FuntionStatic.validar(FuntionStatic.redondear(R));
				G = FuntionStatic.validar(FuntionStatic.redondear(G));
				B = FuntionStatic.validar(FuntionStatic.redondear(B));

				infoImage[y][x].setRed(R);
				infoImage[y][x].setGreen(G);
				infoImage[y][x].setBlue(B);
				
				Color color = new Color((int)R,(int)G,(int)B);
				imagenTemporal[y][x] = color.getRGB();
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}
}
