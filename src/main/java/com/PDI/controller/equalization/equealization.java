package com.PDI.controller.equalization;

import com.PDI.model.FuntionStatic;

public class equealization {
	private interfaceEquealization strategy;

	public equealization(interfaceEquealization strategy){
		this.strategy = strategy;
	}

	public void setStrategy(interfaceEquealization strategy) {
		this.strategy = strategy;
	}

	public double[] applyEquealization(int fMin,int fMax,double[] histoAcu,double... params){
		return strategy.generarLUT(fMin,fMax,histoAcu,params);
	}

	public int[][] applyLut(int[][] matrizPixel, double[] lut){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][] matrizOperation = new int[alto][ancho]; 
		for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int pixel = matrizPixel[i][j];
                matrizOperation[i][j] = (int)FuntionStatic.validar(FuntionStatic.redondear(lut[pixel]));
            }
        }

		return matrizOperation;
	}
}
