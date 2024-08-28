package com.PDI.controller.filtroNoLineal;

public class filtroContraArmonico implements interfaceNoLi {
	private double Q;

	public filtroContraArmonico(double Q){
		this.Q = Q;
	}

	@Override
	public int[][] aplicarFiltro(int[][] imagen, int tamMascara) {
		int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenFiltrada = new int[width][height];
        int offset = tamMascara / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenFiltrada[i][j] = calcularContraArmonico(imagen, i, j, tamMascara, width, height, offset, Q);
            }
        }
        return imagenFiltrada;
	}

	private int calcularContraArmonico(int[][] imagen, int x, int y, int tamanoMascara, int width, int height, int offset, double Q) {
        double num = 0.0;
        double denom = 0.0;

        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                int xVecino = (x + i + width) % width;
                int yVecino = (y + j + height) % height;
                double pixelValue = imagen[xVecino][yVecino] & 0xFF; // Asegura el valor de 8 bits

                num += Math.pow(pixelValue, Q + 1);
                denom += Math.pow(pixelValue, Q);
            }
        }

        return denom == 0 ? 0 : (int) (num / denom);
    }
}
