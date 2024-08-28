package com.PDI.controller.ruido;

import com.PDI.model.FuntionStatic;

public class RuidoDeGauss implements interfaceRuido {
    private double mean;
    private double stdDev;

    public RuidoDeGauss(double mean, double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenConRuido = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double ruido = mean + stdDev * Math.random();
                int valor = (int) Math.round(imagen[i][j] + ruido);
                imagenConRuido[i][j] = (int)FuntionStatic.validar(valor);
            }
        }


        return imagenConRuido;
    }
}

