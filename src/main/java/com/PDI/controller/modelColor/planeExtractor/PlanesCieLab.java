package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;

public class PlanesCieLab implements InterfacePlane {
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		double L,a,b;
		for(int y = 0; y < alto; y++){
			for(int x = 0; x < ancho; x++){
				L = matrizPixel[y][x].getRed();
				a = matrizPixel[y][x].getGreen();
				b = matrizPixel[y][x].getBlue();

				L = FuntionStatic.validar(FuntionStatic.redondear((L*255)/100));
				a = FuntionStatic.validar(FuntionStatic.redondear(a + 127));
				b = FuntionStatic.validar(FuntionStatic.redondear(b + 127));

				planes[0][y][x] = (255 << 24) |	(((int)L) << 16) | (((int)L) << 8) | ((int)L);
				planes[1][y][x] = (255 << 24) |	(((int)a) << 16) | (((int)a) << 8) | ((int)a);
				planes[2][y][x] = (255 << 24) |	(((int)b) << 16) | (((int)b) << 8) | ((int)b);

			}
		}
		return planes;
	}
}
