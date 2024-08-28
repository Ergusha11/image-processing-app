package com.PDI.controller.estadisticas;

import com.PDI.model.pixel;

import java.util.Arrays;

public class DatosEstadisticos{
	private double probabilidadPixel[][] = new double[3][256];
	private double probaAcumPixel[][] = new double[3][256];
    private double valorMax[] = new double[3];
	private double valorMaxGray;
	private pixel [][] matrizPixel;
	private int[][] matrizGrey;

	public DatosEstadisticos(pixel [][] matrizPixel) {
		this.matrizPixel = matrizPixel;
	}

	public DatosEstadisticos(int[][] matrizPixel){
		this.matrizGrey = matrizPixel;
	}

	public void frecuencias() {
        if (matrizPixel != null) {
            calcularFrecuenciasColor();
        } else if (matrizGrey != null) {
            calcularFrecuenciasGris();
        } else {
            System.err.println("No hay datos para calcular frecuencias");
        }
    }

	private void calcularFrecuenciasGris() {
        int alto = matrizGrey.length;
        int ancho = matrizGrey[0].length;
        int areaImage = alto * ancho;
        int pixelFrecuencias[] = new int[256];

        Arrays.fill(pixelFrecuencias, 0);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int grayValue = matrizGrey[y][x];
                pixelFrecuencias[grayValue]++;
            }
        }
		
        for (int i = 0; i < 256; i++) {
            probabilidadPixel[0][i] = (double) pixelFrecuencias[i] / areaImage;
        }

		probaAcumPixel[0][0] = probabilidadPixel[0][0];
		for(int i = 1; i < 256; i++){
			probaAcumPixel[0][i] = probabilidadPixel[0][i] + probaAcumPixel[0][i-1];
		}

		this.valorMax[0] = Arrays.stream(probabilidadPixel[0]).max().getAsDouble();
        //System.out.println("Valor máximo de probabilidad en escala de grises: " + valorMaxGray);
		//System.out.println("Valor del acumulado" + probaAcumPixel[0][255]);
    }

	private void calcularFrecuenciasColor(){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int areaImage = alto * ancho;
		int pixelFrecuencias[][] = new int[3][256];
		pixel pixel = null;	
        //Inicializar la matriz de frecuencias con 0 con un for-each
		for (int[] pixelFrecuencia : pixelFrecuencias) {
			Arrays.fill(pixelFrecuencia, 0);
        }

		if (matrizPixel == null) {
            System.err.println("NO Hay pixel");
            //return;
        }
        
        
        for(int y=0; y < alto; y++) {
            for(int x=0; x < ancho; x++) {
                pixel = matrizPixel[y][x];
                pixelFrecuencias[0][(int)pixel.getRed()]++;
                pixelFrecuencias[1][(int)pixel.getGreen()]++;
                pixelFrecuencias[2][(int)pixel.getBlue()]++;
            }
        }
            
        for(int i = 0; i < 256 ;i++){
            probabilidadPixel[0][i] = (double) pixelFrecuencias[0][i] / areaImage;
            probabilidadPixel[1][i] = (double) pixelFrecuencias[1][i] / areaImage;
            probabilidadPixel[2][i] = (double) pixelFrecuencias[2][i] / areaImage;
        
        }

        for (int i = 0; i < 3; i++) {
            this.valorMax[i] = Arrays.stream(this.probabilidadPixel[i])
                             .max().getAsDouble(); // Cambiado para trabajar con double
            System.out.println(this.valorMax[i]);
        }

		probaAcumPixel[0][0] = probabilidadPixel[0][0];
		probaAcumPixel[1][0] = probabilidadPixel[1][0];
		probaAcumPixel[2][0] = probabilidadPixel[2][0];
		for(int i = 1; i < 256; i++){
			probaAcumPixel[0][i] = probabilidadPixel[0][i] + probaAcumPixel[0][i-1];
			probaAcumPixel[1][i] = probabilidadPixel[1][i] + probaAcumPixel[1][i-1];
			probaAcumPixel[2][i] = probabilidadPixel[2][i] + probaAcumPixel[2][i-1];
		}
	}

	public double[][] getProbaAcumPixel() {
		return probaAcumPixel;
	}

	public double[][] getProbabilidadPixel() {
		return probabilidadPixel;
	}

	public double[] getValorMax() {
		return valorMax;
	}
}
