package com.PDI.controller.OperaImage;

import com.PDI.model.FuntionStatic;

public class operationMulti implements interOperaImage{
	@Override
	public int[][] operationImage(int[][] image1, int[][] image2) {
		int altoImage1 = image1.length;
		int anchoImage1 = image1.length;
		int altoImage2 = image2.length;
		int anchoImage2 = image2.length;
		int altoPrima,anchoPrima;
		double pixel,min,max;
		
		altoPrima = Math.abs(altoImage1 - altoImage2);
		anchoPrima = Math.abs(anchoImage1 - anchoImage2);

		altoPrima = (altoImage1 > altoImage2) ? (altoImage1 - altoPrima) : (altoImage2 -altoPrima);
		anchoPrima = (anchoImage1 > anchoImage2) ? (anchoImage1 - anchoPrima) :(anchoImage2 - anchoPrima);

		int[][] imageOperation = new int[altoPrima][anchoPrima];
		min = 260;
		max = 0;
		for(int y = 0; y < altoPrima; y++){
			for(int x = 0; x < anchoPrima; x++){
				pixel = image1[y][x]*image2[y][x];
				if(pixel < min){
					min = pixel;
				}
				if(pixel > max){
					max = pixel;
				}
				imageOperation[y][x] = (int)pixel;
			}
		}
		double aux = max - min;
		for(int y = 0; y < altoPrima; y++){
			for(int x = 0; x < anchoPrima; x++){
				if(aux != 0){
					pixel = imageOperation[y][x];
					pixel = pixel - min;
					pixel = (pixel/aux)*255;
					pixel = FuntionStatic.validar(FuntionStatic.redondear(pixel));
				}
				else{
					pixel = 255;
				}
				imageOperation[y][x] = (int)pixel;
			}
		}

		return imageOperation;
	}
}
