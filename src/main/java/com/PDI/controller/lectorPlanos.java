package com.PDI.controller;

import java.awt.Image;

import com.PDI.model.pixel;

public class lectorPlanos extends lectorImagen{
	private pixel [][] pixelRGB;
	
	public lectorPlanos(){
		super();
	}
	
	public lectorPlanos(Image image){
		super(image);
	}
	
	public void extraerPixeles(){
		int ancho = bufferedImagen.getWidth();
		int alto = bufferedImagen.getHeight();
		pixelRGB = new pixel[alto][ancho];
		int pixel = 0;
		for(int y = 0;y < alto;y++){
			for(int x = 0; x < ancho; x++){
				pixel = bufferedImagen.getRGB(x,y);
				int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
				this.pixelRGB[y][x] = new pixel();
                this.pixelRGB[y][x].setRed(rojo);
                this.pixelRGB[y][x].setGreen(verde);
                this.pixelRGB[y][x].setBlue(azul);
			}
		}
	}
	
	public pixel[][] getPixelRGB() {
		return pixelRGB;
	}

}
