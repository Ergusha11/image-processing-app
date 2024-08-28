package com.PDI.controller.modelColor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class RGBtoHSV implements interfaceColor{

	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		pixel [][]  infoImagen = new pixel[alto][ancho];
		int [][] ImagenTemporal = new int[alto][ancho];
		double R,G,B;
		double H,S,V,max,min,Crng,Rprima,Gprima,Bprima;
		for(int y = 0;y<alto;y++){
			for(int x = 0;x < ancho;x++){
				infoImagen[y][x] = new pixel();
				R = matrizPixel[y][x].getRed()/255;
				G = matrizPixel[y][x].getGreen()/255;
				B = matrizPixel[y][x].getBlue()/255;
				max = FuntionStatic.maxDeTres(R,G,B);
				min = FuntionStatic.minDeTres(R,G,B);
				Crng = max - min;

				V = max/1;
				S = max > 0 ? Crng/max : 0;
				H = 0;
				if(S != 0){
					Rprima = (max-R)/Crng;
					Gprima = (max-G)/Crng;
					Bprima = (max-B)/Crng;

					if(R == max){
						H = Bprima - Gprima;
					}
					else if(G == max){
						H = Rprima - Bprima + 2;
					}
					else{
						H = Gprima - Rprima + 4;
					}

					H = (H < 0) ? (H + 6)/6: H/6; 
				}

				infoImagen[y][x].setRed(H);
				infoImagen[y][x].setGreen(S);
				infoImagen[y][x].setBlue(V);
				

				int colorRGB = (255<<24) | ((int)(H*255)) << 16 | ((int)(S*255)) << 8 | ((int)V*255);
				ImagenTemporal[y][x] = colorRGB;
			}
		}
		refImg.addImage(ID,infoImagen);
		return ImagenTemporal;
	}
} 
