package com.PDI.controller.Convolucion;

import com.PDI.model.FuntionStatic;

public class convolution {
	 /**
     * Aplica la convolución a una matriz de imagen con una máscara dada, considerando que la imagen está envuelta.
     *
     * @param image La matriz de la imagen.
     * @param mask La máscara de convolución.
     * @return La matriz de la imagen resultante.
     */
	public static int[][] applyConvolution(int[][] image, double[][] mask, double maskSum) {
        int width = image.length;
        int height = image[0].length;
        int maskSize = mask.length;
        int offset = maskSize / 2;
        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sum = 0;
                for (int k = -offset; k <= offset; k++) {
                    for (int l = -offset; l <= offset; l++) {
                        int x = (i + k + width) % width;
                        int y = (j + l + height) % height;
                        sum += image[x][y] * mask[k + offset][l + offset] * maskSum;
                    }
                }
                result[i][j] = (int)FuntionStatic.validar(FuntionStatic.redondear(sum));
            }
        }
        return result;
    }

	public static double[][] maskAverageFilter() {
        double[][] averageMask = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        return averageMask;
    }

	public static double[][] maskGaussianFilter() {
        double[][] gaussianMask = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        return gaussianMask;
    }

	public static double[][] getKernelHF0() {
        return new double[][] {
            { 1, 0, 0 },
            { 0, -1, 0 },
            { 0, 0, 0 }
        };
    }

    public static double[][] getKernelHC0() {
        return new double[][] {
            { 0, 1, 0 },
            { -1, 0, 0 },
            { 0, 0, 0 }
        };
    }

    public static double[][] getPrewittKernelHF() {
        return new double[][] {
            { -1, 0, 1 },
            { -1, 0, 1 },
            { -1, 0, 1 }
        };
    }

    public static double[][] getPrewittKernelHC() {
        return new double[][] {
            { -1, -1, -1 },
            { 0, 0, 0 },
            { 1, 1, 1 }
        };
    }

    public static double[][] getSobelKernelHF() {
        return new double[][] {
            { -1, 0, 1 },
            { -2, 0, 2 },
            { -1, 0, 1 }
        };
    }

    public static double[][] getSobelKernelHC() {
        return new double[][] {
            { -1, -2, -1 },
            { 0, 0, 0 },
            { 1, 2, 1 }
        };
    }

    public static double[][] getFreiChenKernelHF() {
        return new double[][] {
            { -1, 0, 1 },
            { -Math.sqrt(2), 0, Math.sqrt(2) },
            { -1, 0, 1 }
        };
    }

    public static double[][] getFreiChenKernelHC() {
        return new double[][] {
            { -1, -Math.sqrt(2), -1 },
            { 0, 0, 0 },
            { 1, Math.sqrt(2), 1 }
        };
    }
}
