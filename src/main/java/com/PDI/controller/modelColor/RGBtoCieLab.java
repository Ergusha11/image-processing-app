package com.PDI.controller.modelColor;

import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class RGBtoCieLab implements interfaceColor{
	final double xPrima = 0.95047;
	final double yPrima = 1.0;
	final double zPrima = 1.08883;
	
	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int colorRGB = 0;
		double X,Y,Z;
		double L,A,B;
		double valorY;
		pixel aux = null;
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		for(int  y = 0; y < alto; y++){
			for(int x = 0;x < ancho ; x++){
				infoImage[y][x] = new pixel();
				aux = RGBtoXYZ(matrizPixel[y][x].getRed(),matrizPixel[y][x].getGreen(),matrizPixel[y][x].getBlue());
				X = aux.getRed();
				Y = aux.getGreen();
				Z = aux.getBlue();

				//System.out.println("Valor de X: " + X);
				//System.out.println("Valor de Y: " + Y);
				//System.out.println("Valor de Z: " + Z);
				
				valorY = funtionPrima((Y/yPrima));
				//System.out.println("Valor de y prima: " + valorY);
				L = (116 * valorY) - 16;
				A = 500*(funtionPrima(X/xPrima) - valorY);
				B = 200*(valorY - funtionPrima(Z/zPrima));
				//System.out.println("Valor de L: " + L);
				//System.out.println("Valor de A: " + A);
				//System.out.println("Valor de B: " + B);
				infoImage[y][x].setRed(L);
				infoImage[y][x].setGreen(A);
				infoImage[y][x].setBlue(B);
				
				colorRGB = (255<<24) | (int)((L*255)/100) << 16 | (int)(A + 127) << 8 | (int)(B + 127);

				imagenTemporal[y][x] = colorRGB;
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}

	private pixel RGBtoXYZ(double R,double G,double B){
		double X,Y,Z;
		pixel aux = new pixel();
		R = R/255;
		G = G/255;
		B = B/255;
				
		//System.out.println("Valor de R: " + R);
		//System.out.println("Valor de G: " + G);
		//System.out.println("Valor de B: " + B);
				
		X = (R * 0.412453) + (G * 0.357580) + (B * 0.180423);
		Y = (R * 0.212671) + (G * 0.715160) + (B * 0.072169);
		Z = (R * 0.019334) + (G * 0.119193) + (B * 0.950227);
		aux.setRed(X);
		aux.setGreen(Y);
		aux.setBlue(Z);

		//System.out.println("Valor de X: " + X);
		//System.out.println("Valor de Y: " + Y);
		//System.out.println("Valor de Z: " + Z);
		
		return aux;
	}

	private double funtionPrima(double C){
		final double delta = 0.0088564;
		final double K = 7.78703;
		
		return (C > delta) ? Math.pow(C,1.0/3) : (K*C) + 4.0/29; 
	}
}

