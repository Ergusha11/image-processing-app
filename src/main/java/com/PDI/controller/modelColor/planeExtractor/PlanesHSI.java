package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;

public class PlanesHSI implements InterfacePlane{
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		double H,S,I;
		for(int y = 0; y < alto ;y++){
			for(int x = 0; x < ancho; x++){
				H = matrizPixel[y][x].getRed();
				S = matrizPixel[y][x].getGreen();
				I = matrizPixel[y][x].getBlue();

				H = (int) ((H / (2 * Math.PI)) * 255);
				S = S * 255;
				I = I * 255;

				H = FuntionStatic.validar(H);
				S = FuntionStatic.validar(S);
				I = FuntionStatic.validar(I);

				planes[0][y][x] = (255 << 24) |	(((int)H) << 16) | (((int)H) << 8) | ((int)H);
				planes[1][y][x] = (255 << 24) |	(((int)S) << 16) | (((int)S) << 8) | ((int)S);
				planes[2][y][x] = (255 << 24) |	(((int)I) << 16) | (((int)I) << 8) | ((int)I);

			}
		}
		return planes;
	}
}
