package com.PDI.controller.ruido;

import java.util.Random;

import com.PDI.model.FuntionStatic;

public class RuidoDeRayleigh implements interfaceRuido {
    private double escala;

    public RuidoDeRayleigh(double escala) {
        this.escala = escala;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenRuidosa = new int[width][height];
        Random random = new Random();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double r = escala * Math.sqrt(-2 * Math.log(1 - random.nextDouble()));
                imagenRuidosa[i][j] = imagen[i][j] + (int) r;
                imagenRuidosa[i][j] = (int)FuntionStatic.validar(imagenRuidosa[i][j]); // aseguramos que los valores estén entre 0 y 255
            }
        }
        return imagenRuidosa;
    }
}

