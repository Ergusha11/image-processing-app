package com.PDI.controller.ruido;

public class AplicarRuido {
    private interfaceRuido estrategia;

    public void setEstrategia(interfaceRuido estrategia) {
        this.estrategia = estrategia;
    }

    public int[][] aplicarRuido(int[][] imagen) {
        if (estrategia == null) {
            throw new IllegalStateException("La estrategia no ha sido establecida.");
        }
        return estrategia.aplicarRuido(imagen);
    }
}

