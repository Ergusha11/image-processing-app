package com.PDI.controller.filtroNoLineal;

public class filtroMediaAritmetica implements interfaceNoLi {
	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularMediaAritmetica(imagen, i, j, tamMascara, width, height, offset);
            }
        }
        return imagenFiltrada;
	}

	private int calcularMediaAritmetica(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset) {
        int suma = 0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = (x + i + width) % width;
                int yVecino = (y + j + height) % height;
                suma += imagen[xVecino][yVecino];
            }
        }

        return suma / (tamanoMascara * tamanoMascara);
    }
}
