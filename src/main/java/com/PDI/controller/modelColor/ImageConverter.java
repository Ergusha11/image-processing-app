package com.PDI.controller.modelColor;

import com.PDI.model.pixel;

public class ImageConverter{
	private interfaceColor strategy;

    public ImageConverter(interfaceColor strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(interfaceColor strategy) {
        this.strategy = strategy;
    }

    public int[][] convertImage(pixel[][] pixelMatrix,int ID,String nombre) {
        return strategy.convertModelColor(pixelMatrix,ID,nombre);
    }
}
