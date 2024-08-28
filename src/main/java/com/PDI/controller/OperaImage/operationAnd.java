package com.PDI.controller.OperaImage;

import com.PDI.model.FuntionStatic;

public class operationAnd implements interOperaImage{
	@Override
	public int[][] operationImage(int[][] image1, int[][] image2) {
		int altoImage1 = image1.length;
		int anchoImage1 = image1.length;
		int altoImage2 = image2.length;
		int anchoImage2 = image2.length;
		int altoPrima,anchoPrima;
		int pixel;
		
		altoPrima = Math.abs(altoImage1 - altoImage2);
		anchoPrima = Math.abs(anchoImage1 - anchoImage2);

		altoPrima = (altoImage1 > altoImage2) ? (altoImage1 - altoPrima) : (altoImage2 -altoPrima);
		anchoPrima = (anchoImage1 > anchoImage2) ? (anchoImage1 - anchoPrima) :(anchoImage2 - anchoPrima);

		int[][] imageOperation = new int[altoPrima][anchoPrima];

		for(int y = 0; y < altoPrima; y++){
			for(int x = 0; x < anchoPrima; x++){
				pixel = image1[y][x] & image2[y][x];
				imageOperation[y][x] = pixel;
			}
		}
		return imageOperation;
	}
}