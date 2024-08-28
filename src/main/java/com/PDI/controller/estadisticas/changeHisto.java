package com.PDI.controller.estadisticas;

import com.PDI.model.FuntionStatic;

public class changeHisto {
	private double[] probaHistoAcu;
	private int[][] matrizImage;

	public changeHisto(int[][] matrizImage, double[] probaHistoAcu){
		this.probaHistoAcu = probaHistoAcu;
		this.matrizImage = matrizImage;
	}

	public int[][] changeImageHisto(){
		int alto = this.matrizImage.length;
		int ancho = matrizImage[0].length;
		int [][] resulImageChange = new int[alto][ancho];
		int pixel = 0;
		for(int y = 0; y < alto;y++){
			for(int x = 0; x < ancho;x++){
				pixel = matrizImage[y][x];
				pixel = (int)FuntionStatic.validar(FuntionStatic.redondear(probaHistoAcu[pixel] * 255));
				resulImageChange[y][x] = pixel;
			}
		}

		return resulImageChange;
	}
}
