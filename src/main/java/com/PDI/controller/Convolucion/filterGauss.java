package com.PDI.controller.Convolucion;

public class filterGauss implements interfaceFiltro{
	@Override
	public int[][] applyFilter(int[][] matrizPixel, double[][] kernel) {
	
		int[][] result = convolution.applyConvolution(matrizPixel,kernel,1.0/16.0);
		return result;
	}
}
