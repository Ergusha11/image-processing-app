package com.PDI.controller.modelColor;


import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class RGBtoHSI implements interfaceColor{
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		double theta,denominador,numerador,aux, R,G,B;
		double H,S,I;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		for(int y = 0; y < alto;y++){
			for(int x = 0;x < ancho ;x++){
				infoImage[y][x] = new pixel();
				R = matrizPixel[y][x].getRed()/255;
				G = matrizPixel[y][x].getGreen()/255;
				B = matrizPixel[y][x].getBlue()/255;
				//System.out.println("El valor RGB es: " + R +" "+G+" "+B);
				//System.out.println("El valor del numerador es: " + numerador);
				//System.out.println("El valor del denominador es: " + denominador);
				aux = R+G+B;
				S = ((R==0)&&(R==G)&&(G==B)) ? 0 : (1-(3/aux)*FuntionStatic.minDeTres(R,G,B));
				I = aux/3;

				H = 0;
				if (S != 0) {
					numerador = 0.5f * ((R - G) + (R - B));
					denominador = (float)Math.sqrt((R - G) * (R - G) + (R - B) * (G - B));
					if (denominador != 0) {
						theta = Math.acos(numerador / denominador);
						if (B <= G) {
							H = theta;
						} else {
							H = ((2*Math.PI) - theta);
						}
					}
				}

				//System.out.println("El valor de H es: " + H);
				infoImage[y][x].setRed(H);
				infoImage[y][x].setGreen(S);
				infoImage[y][x].setBlue(I);

				int valorEscalaGrises = (int) ((H / (2 * Math.PI)) * 255);

				S = FuntionStatic.validar(255*S);
				I = FuntionStatic.validar(255*I);

				int colorRGB = (255 << 24) | (valorEscalaGrises << 16) | ((int)S << 8) | (int)I;
                // Crear un color en escala de grises con ese valor
				imagenTemporal[y][x] = colorRGB;
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}	
}
