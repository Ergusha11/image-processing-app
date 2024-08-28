package com.PDI.controller.modelColor.planeExtractor;

import java.awt.Color;

import com.PDI.model.pixel;

public class PlanesRGB implements InterfacePlane{
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		//int pixel;
		for(int y = 0;y < alto;y++){
			for(int x = 0;x < ancho; x++){
			int red = (int)matrizPixel[y][x].getRed();
            int green = (int)matrizPixel[y][x].getGreen();
            int blue = (int)matrizPixel[y][x].getBlue();
            
            // Plano Rojo en formato ARGB, solo el canal rojo tiene el valor, los otros son ceros
            planes[0][y][x] = (255 << 24) | (red << 16) | (0 << 8) | 0;
            // Plano Verde en formato ARGB, solo el canal verde tiene el valor, los otros son ceros
            planes[1][y][x] = (255 << 24) | (0 << 16) | (green << 8) | 0;
            // Plano Azul en formato ARGB, solo el canal azul tiene el valor, los otros son ceros
            planes[2][y][x] = (255 << 24) | (0 << 16) | (0 << 8) | blue;
			}
		}
		return planes;
	}
}
