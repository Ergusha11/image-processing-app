package com.PDI.controller.filtroNoLineal;

public class filtroGeometrico implements interfaceNoLi {
	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularGeometrico(imagen, i, j, tamMascara, width, height, offset);
            }
        }
        return imagenFiltrada;
	}

	private int calcularGeometrico(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset) {
        double producto = 1.0;
        int numPixeles = 0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = (x + i + width) % width;
                int yVecino = (y + j + height) % height;
                producto *= imagen[xVecino][yVecino] & 0xFF;
                numPixeles++;
            }
        }

        return (int) Math.pow(producto, 1.0 / numPixeles);
    }
}
