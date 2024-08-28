package com.PDI.controller.modelColor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class HSVtoRGB implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		int color,c1;
		double H,S,V,hPrima,c2,X,Y,Z;
		double R,G,B;
		R = 0;
		G = 0;
		B = 0;
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho;x++){
				infoImage[y][x] = new pixel();
				H = matrizPixel[y][x].getRed();
				S = matrizPixel[y][x].getGreen();
				V = matrizPixel[y][x].getBlue();

				hPrima = (6*H)%6;
				c1 = (int)hPrima;
				c2 = hPrima - c1;

				X = (1 - S)*V;
				Y = (1 - (S*c2))*V;
				Z = (1 - (S*(1 - c2)))*V;

				switch(c1){
					case 0: R = V; G = Z; B = X; break;
					case 1: R = Y; G = V; B = X; break;
					case 2: R = X; G = V; B = Z; break;
					case 3: R = X; G = Y; B = V; break;
					case 4: R = Z; G = X; B = V; break;
					case 5: R = V; G = X; B = Y; break;
				}

				R =(int)FuntionStatic.validar(R*255);
				G =(int)FuntionStatic.validar(G*255);
				B =(int)FuntionStatic.validar(B*255);

				infoImage[y][x].setRed(R);
				infoImage[y][x].setGreen(G);
				infoImage[y][x].setBlue(B);

				color = (255 << 24) | (((int)R) << 16) | (((int)G) << 8) | ((int)B);

				imagenTemporal[y][x] = color;

			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}
}
