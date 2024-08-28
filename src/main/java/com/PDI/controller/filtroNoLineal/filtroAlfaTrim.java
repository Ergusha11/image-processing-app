package com.PDI.controller.filtroNoLineal;

import java.util.Arrays;

public class filtroAlfaTrim implements interfaceNoLi {
	private int d;
	
	public filtroAlfaTrim(int d){
		this.d = d;
	}

	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularAlfaTrimmed(imagen, i, j, tamMascara, width, height, offset);
            }
        }
        return imagenFiltrada;
	}

	private int calcularAlfaTrimmed(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset) {
        int[] ventana = new int[tamanoMascara * tamanoMascara];
        int contador = 0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = (x + i + width) % width;
                int yVecino = (y + j + height) % height;
                ventana[contador++] = imagen[xVecino][yVecino];
            }
        }

        Arrays.sort(ventana);
        int sum = 0;
        for (int k = d / 2; k < ventana.length - d / 2; k++) {
            sum += ventana[k];
        }

        return sum / (ventana.length - d);
    }
}
