package com.PDI.controller.modelColor;

import com.PDI.model.FuntionStatic;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;

public class CieLabtoRGB implements interfaceColor{
	final double xPrima = 0.95047;
	final double yPrima = 1.0;
	final double zPrima = 1.08883;

	@Override
	public int[][] convertModelColor(pixel[][] matrizPixel, int ID,String name) {
		referenciaImagen refImg = referenciaImagen.getInstance();
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int colorRGB = 0;
		double L,a,b;
		double R,G,B;
		double XYZ[];
		pixel [][] infoImage = new pixel[alto][ancho];
		int [][] imagenTemporal = new int[alto][ancho];
		for(int y = 0; y < alto;y++){
			for(int x = 0; x < ancho ;x++){
				infoImage[y][x] = new pixel();
				L = matrizPixel[y][x].getRed();
				a = matrizPixel[y][x].getGreen();
				b = matrizPixel[y][x].getBlue();
				XYZ = LabtoXYZ(L,a,b);
				R = (3.240479*XYZ[0]) + (-1.537150*XYZ[1]) + (-0.498535*XYZ[2]);
				G = (-0.969256*XYZ[0]) + (1.875992*XYZ[1]) + (0.041556*XYZ[2]);
				B = (0.055648*XYZ[0]) + (-0.204043*XYZ[1]) + (1.057311*XYZ[2]);

				R = FuntionStatic.validar(FuntionStatic.redondear(R*255));
				G = FuntionStatic.validar(FuntionStatic.redondear(G*255));
				B = FuntionStatic.validar(FuntionStatic.redondear(B*255));

				infoImage[y][x].setRed(R);
				infoImage[y][x].setGreen(G);
				infoImage[y][x].setBlue(B);
				
				colorRGB = (255 << 24) | (((int)R) << 16) | (((int)G) << 8) | ((int)B);
				imagenTemporal[y][x] = colorRGB;
			}
		}
		refImg.addImage(ID,infoImage);
		return imagenTemporal;
	}

	private double [] LabtoXYZ(double L, double a, double b){
		double X,Y,Z,lPrima;
		lPrima = (L + 16)/116;
		X = xPrima * funtionPrima(lPrima + (a/500));
		Y = yPrima * funtionPrima(lPrima);
		Z = zPrima * funtionPrima(lPrima - (b/200));
		return new double[] {X,Y,Z};
	}
	
	private double funtionPrima(double c){
		final double delta = 0.0088564;
		final double K = 7.78703;

		return (Math.pow(c,3) > delta) ? Math.pow(c,3): ((c - (16.0/116))/K);
	}
}
