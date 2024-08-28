package com.PDI.controller.ruido;

public class RuidoGamma implements interfaceRuido {
    private double alpha;
    private double beta;

    public RuidoGamma(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenConRuido = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double ruido = generateGammaNoise(alpha, beta);
                int valor = (int) Math.round(imagen[i][j] + ruido);
                imagenConRuido[i][j] = Math.max(0, Math.min(255, valor));
            }
        }

        return imagenConRuido;
    }

    private double generateGammaNoise(double alpha, double beta) {
        double noise = 0.0;
        for (int i = 0; i < alpha; i++) {
            noise += -Math.log(Math.random());
        }
        return noise / beta;
    }
}

