package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;

public class PlanesYIQ implements InterfacePlane{
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		double Y,I,Q;
		for(int y = 0; y < alto ;y++){
			for(int x = 0;x < ancho ;x++){
				Y = matrizPixel[y][x].getRed();
				I = matrizPixel[y][x].getGreen();
				Q = matrizPixel[y][x].getBlue();

				Y = FuntionStatic.validar(Y*255);
				I = FuntionStatic.validar((I + 0.59)*255);
				Q = FuntionStatic.validar((Q + 0.52)*255);

				planes[0][y][x] = (255 << 24) |	(((int)Y) << 16) | (((int)Y) << 8) | ((int)Y);
				planes[1][y][x] = (255 << 24) |	(((int)I) << 16) | (((int)I) << 8) | ((int)I);
				planes[2][y][x] = (255 << 24) |	(((int)Q) << 16) | (((int)Q) << 8) | ((int)Q);
			}
		}
		return planes;
	}
}
