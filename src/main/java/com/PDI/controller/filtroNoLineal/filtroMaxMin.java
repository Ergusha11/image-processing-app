package com.PDI.controller.filtroNoLineal;

import com.PDI.model.FuntionStatic;

public class filtroMaxMin implements interfaceNoLi {
	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularMaxMin(imagen, i, j, tamMascara, width, height, offset);
            }
        }
        return imagenFiltrada;
	}

	private int calcularMaxMin(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
		int result;
        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = (x + i + width) % width;
                int yVecino = (y + j + height) % height;
                int pixelValue = imagen[xVecino][yVecino] & 0xFF;

                if (pixelValue > max) {
                    max = pixelValue;
                }

                if (pixelValue < min) {
                    min = pixelValue;
                }
            }
        }
		result = (int)FuntionStatic.validar((double)(max - min));
        return result;
    }
}
