package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.pixel;

public class PlanesHSV implements InterfacePlane {
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		double H,S,V;
		for(int y = 0; y < alto ;y++){
			for(int x = 0; x < ancho ;x++){
				H = matrizPixel[y][x].getRed();
				S = matrizPixel[y][x].getGreen();
				V = matrizPixel[y][x].getBlue();

				H = H*255;
				S = S*255;
				V = V*255;

				planes[0][y][x] = (255 << 24) |	(((int)H) << 16) | (((int)H) << 8) | ((int)H);
				planes[1][y][x] = (255 << 24) |	(((int)S) << 16) | (((int)S) << 8) | ((int)S);
				planes[2][y][x] = (255 << 24) |	(((int)V) << 16) | (((int)V) << 8) | ((int)V);
			}
		}
		return planes;
	}
}
