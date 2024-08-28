package com.PDI.controller.Convolucion;

public class algorithCanny{

	public static int[][] applyCanny(int[][] image){
		applyFilter filterGaus = new applyFilter(new filterGauss());
		int[][] result = filterGaus.filter(image,convolution.maskGaussianFilter());
		double[][][] gradients = computeGradients(result);
		double[][] magnitude = gradients[0];
        double[][] direction = gradients[1];

		int[][] suppressed = nonMaximumSuppression(magnitude, direction);
		int[][] edges = doubleThreshold(suppressed, 85, 170);
		return edges;
	}

	public static double[][][] computeGradients(int[][] image) {
        int width = image.length;
        int height = image[0].length;
        double[][] magnitude = new double[width][height];
        double[][] direction = new double[width][height];

        int[][] sobelX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] sobelY = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
        };

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                int gx = 0, gy = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gx += sobelX[i][j] * image[x + i - 1][y + j - 1];
                        gy += sobelY[i][j] * image[x + i - 1][y + j - 1];
                    }
                }
                magnitude[x][y] = Math.sqrt(gx * gx + gy * gy);
                direction[x][y] = Math.atan2(gy, gx);
            }
        }

        return new double[][][] {magnitude, direction};
    }
	
	 public static int[][] nonMaximumSuppression(double[][] magnitude, double[][] direction) {
        int width = magnitude.length;
        int height = magnitude[0].length;
        int[][] result = new int[width][height];

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                double angle = direction[x][y];
                double mag = magnitude[x][y];

                int q = 255, r = 255;

                // Check the direction and compare with neighbors
                if ((angle >= -Math.PI / 8 && angle < Math.PI / 8) || 
                    (angle >= 7 * Math.PI / 8 || angle < -7 * Math.PI / 8)) {
                    q = (int) magnitude[x + 1][y];
                    r = (int) magnitude[x - 1][y];
                } else if ((angle >= Math.PI / 8 && angle < 3 * Math.PI / 8) || 
                           (angle >= -7 * Math.PI / 8 && angle < -5 * Math.PI / 8)) {
                    q = (int) magnitude[x + 1][y + 1];
                    r = (int) magnitude[x - 1][y - 1];
                } else if ((angle >= 3 * Math.PI / 8 && angle < 5 * Math.PI / 8) || 
                           (angle >= -5 * Math.PI / 8 && angle < -3 * Math.PI / 8)) {
                    q = (int) magnitude[x][y + 1];
                    r = (int) magnitude[x][y - 1];
                } else if ((angle >= 5 * Math.PI / 8 && angle < 7 * Math.PI / 8) || 
                           (angle >= -3 * Math.PI / 8 && angle < -Math.PI / 8)) {
                    q = (int) magnitude[x - 1][y + 1];
                    r = (int) magnitude[x + 1][y - 1];
                }

                if (mag >= q && mag >= r) {
                    result[x][y] = (int) mag;
                } else {
                    result[x][y] = 0;
                }
            }
        }
        return result;
    }

	public static int[][] doubleThreshold(int[][] image, int lowThreshold, int highThreshold) {
        int width = image.length;
        int height = image[0].length;
        int[][] result = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int value = image[x][y];
                if (value >= highThreshold) {
                    result[x][y] = 255;
                } else if (value >= lowThreshold) {
                    result[x][y] = 128;
                } else {
                    result[x][y] = 0;
                }
            }
        }

        // Hysteresis: link weak edges (128) to strong edges (255)
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (result[x][y] == 128) {
                    if ((result[x + 1][y] == 255) || (result[x - 1][y] == 255) ||
                        (result[x][y + 1] == 255) || (result[x][y - 1] == 255) ||
                        (result[x + 1][y + 1] == 255) || (result[x - 1][y - 1] == 255) ||
                        (result[x + 1][y - 1] == 255) || (result[x - 1][y + 1] == 255)) {
                        result[x][y] = 255;
                    } else {
                        result[x][y] = 0;
                    }
                }
            }
        }

        return result;
    }
}
