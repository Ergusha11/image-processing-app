package com.PDI.controller.Binarizacion;

public class umbralizacion {

	public int[][] binarizacion(int[][] matrizPixel,int umbral){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int [][] binaImage = new int[alto][ancho];
		int pixel = 0;

		for(int y = 0; y < alto; y++){
			for(int x = 0; x < ancho; x++){
				pixel = matrizPixel[y][x];
				if(pixel <= umbral){
					pixel = 0;	
				}
				else{
					pixel = 255;
				}

				binaImage[y][x] = pixel;
			}
		}

		return binaImage;
	}

	public int[][] binarizacion(int[][] matrizPixel,int umbralUno, int umbralDos){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int [][] binaImage = new int[alto][ancho];
		int pixel = 0;

		for(int y = 0; y < alto; y++){
			for(int x = 0; x < ancho; x++){
				pixel = matrizPixel[y][x];
				if(pixel <= umbralUno){
					pixel = 0;	
				}
				else if(pixel > umbralUno && pixel <= umbralDos){
					pixel = 127;
				}
				else{
					pixel = 255;
				}

				binaImage[y][x] = pixel;
			}
		}

		return binaImage;
	}
	
	public int[][] negativo(int[][] matrizPixel){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int [][] binaImage = new int[alto][ancho];
		int pixel = 0;
		int negativo = 255;

		for(int y = 0; y < alto; y++){
			for(int x = 0; x < ancho; x++){
				pixel = matrizPixel[y][x];
				pixel = negativo - pixel;
				binaImage[y][x] = pixel;
			}
		}

		return binaImage;
	}
}
