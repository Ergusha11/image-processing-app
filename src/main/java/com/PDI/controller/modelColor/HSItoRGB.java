package com.PDI.controller.modelColor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class HSItoRGB implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		int color;
		double H,S,I;
		double R,G,B;
		double alfa,beta;
		alfa = (2*Math.PI)/3; //120 grados
		beta = alfa*2; //240 grados
		for(int y = 0; y < alto;y++){
			for(int x = 0; x < ancho;x++){
				infoImage[y][x] = new pixel();
				H = matrizPixel[y][x].getRed();
				S = matrizPixel[y][x].getGreen();
				I = matrizPixel[y][x].getBlue();
				//RG sector
				if(H >=0 && H < alfa){
					B = I*(1 - S);
					R = I*(1 + ((S*Math.cos(H))/Math.cos((Math.PI/3) - H)));
					G = 3*I - (R + B);
				}
				//GB sector
				else if(H >= alfa && H < beta){
					H = H - alfa;
					R = I*(1-S);
					G = I*(1 + ((S*Math.cos(H))/Math.cos((Math.PI/3) - H)));
					B = 3*I - (R + G);

				}
				//BR sector
				else{
					H = H - beta;
					G = I*(1 - S);
					B = I*(1 + ((S*Math.cos(H))/Math.cos((Math.PI/3) - H)));
					R = 3*I - (G+B); 

				}
				
				R = R*255;
				G = G*255;
				B = B*255;
				
				R = FuntionStatic.validar(FuntionStatic.redondear(R));
				G = FuntionStatic.validar(FuntionStatic.redondear(G));
				B = FuntionStatic.validar(FuntionStatic.redondear(B));

				infoImage[y][x].setRed(R);
				infoImage[y][x].setGreen(G);
				infoImage[y][x].setBlue(B);

				color = (255<<24) | ((int)(R) << 16) | (((int)G) << 8) | ((int)B);
				imagenTemporal[y][x] = color;
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}
}
