package com.PDI.controller.ruido;

import java.security.SecureRandom;

public class RuidoExponencialNegativo implements interfaceRuido {
    private double media;
    private double varianza;
    private SecureRandom random;

    public RuidoExponencialNegativo(double media, double varianza) {
        if (varianza <= 0) {
            throw new IllegalArgumentException("La varianza debe ser mayor que 0.");
        }
        this.media = media;
        this.varianza = varianza;
        this.random = new SecureRandom();
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenConRuido = new int[width][height];

        double A = Math.sqrt(varianza) / 2.0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dRuido = Math.sqrt(-2 * A * Math.log(1.0 - random.nextDouble()));
                double theta = random.nextDouble() * 2 * Math.PI;
                double Rx = dRuido * Math.cos(theta);
                double Ry = dRuido * Math.sin(theta) + media;
                dRuido = Rx * Rx + Ry * Ry;
                int valor = (int) Math.round(imagen[i][j] + dRuido);
                imagenConRuido[i][j] = Math.max(0, Math.min(255, valor));
            }
        }

        return imagenConRuido;
    }
}

