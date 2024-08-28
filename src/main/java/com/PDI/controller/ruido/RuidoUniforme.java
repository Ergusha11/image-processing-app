package com.PDI.controller.ruido;

public class RuidoUniforme implements interfaceRuido {
    private double a;
    private double b;

    public RuidoUniforme(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int[][] aplicarRuido(int[][] imagen) {
        int width = imagen.length;
        int height = imagen[0].length;
        int[][] imagenConRuido = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double ruido = a + (b - a) * Math.random();
                int valor = (int) Math.round(imagen[i][j] + ruido);
                imagenConRuido[i][j] = Math.max(0, Math.min(255, valor));
            }
        }

        return imagenConRuido;
    }
}

