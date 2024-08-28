package com.PDI.controller.ruido;

public class RuidoDeSalYPimienta implements interfaceRuido {
    private double probabilidad;

    public RuidoDeSalYPimienta(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenConRuido = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double rand = Math.random();
                if (rand < probabilidad / 2) {
                    imagenConRuido[i][j] = 0; // Sal
                } else if (rand < probabilidad) {
                    imagenConRuido[i][j] = 255; // Pimienta
                } else {
                    imagenConRuido[i][j] = imagen[i][j];
                }
            }
        }

        return imagenConRuido;
    }
}

