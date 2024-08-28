package com.PDI.controller.filtroNoLineal;

import java.util.Arrays;

public class filtroMediana implements interfaceNoLi {
	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularMediana(imagen, i, j, tamMascara, width, height, offset);
            }
        }
        return imagenFiltrada;
	}

	private int calcularMediana(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset) {
        int[] ventana = new int[tamanoMascara * tamanoMascara];
        int contador = 0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = x + i;
                int yVecino = y + j;

                if (xVecino >= 0 && xVecino < width && yVecino >= 0 && yVecino < height) {
                    ventana[contador++] = imagen[xVecino][yVecino];
                }
            }
        }

        Arrays.sort(ventana, 0, contador);
        return ventana[contador / 2];
    }
}
