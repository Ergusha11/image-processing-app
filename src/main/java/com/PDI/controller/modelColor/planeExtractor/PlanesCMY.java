package com.PDI.controller.modelColor.planeExtractor;

import com.PDI.model.pixel;

public class PlanesCMY implements InterfacePlane{
	@Override
	public int[][][] extractPlanes(pixel[][] matrizPixel) {
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][][] planes = new int[3][alto][ancho];
		//int pixel;
		for(int y = 0;y < alto;y++){
			for(int x = 0;x < ancho; x++){
			int cian = (int)matrizPixel[y][x].getRed();
            int magenta = (int)matrizPixel[y][x].getGreen();
            int amarillo = (int)matrizPixel[y][x].getBlue();
            
			 // Para visualizar Cian a color en un sistema RGB:
            planes[0][y][x] = (255 << 24) | (0 << 16) | (cian << 8) | (cian);
            // Para visualizar Magenta a color en un sistema RGB:
            planes[1][y][x] = (255 << 24) | (magenta << 16) | (0 << 8) | (magenta);
            // Para visualizar Amarillo a color en un sistema RGB:
            planes[2][y][x] = (255 << 24) | (amarillo << 16) | (amarillo << 8) | 0;
			
			}
		}
		return planes;
	}
}
