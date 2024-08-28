package com.PDI.controller.ruido;

import java.util.Random;

import com.PDI.model.FuntionStatic;

public class RuidoCoherente implements interfaceRuido {
    private double frecuencia;

    public RuidoCoherente(double frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenRuidosa = new int[width][height];
        Random random = new Random();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imagenRuidosa[i][j] = imagen[i][j] + (int) (frecuencia * random.nextGaussian());
                imagenRuidosa[i][j] = (int)FuntionStatic.validar(imagenRuidosa[i][j]); // aseguramos que los valores estén entre 0 y 255
            }
        }
        return imagenRuidosa;
    }
}
