package com.PDI.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class FuntionStatic{
	private static int contador = 1;
	private static int X = 0;
	private static int Y = 0;
		
	public static int generarId(){
		return contador++;
	}

	public static int[] position(){
		X = X + 5;
		Y = Y + 5;
		return new int[]{X, Y};
	}

	public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Creando un BufferedImage con el mismo ancho, alto y tipo que la imagen original
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Dibujando la imagen en el BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

	public static  pixel[][] extraPixel(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		pixel[][] matriz = new pixel[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);

				matriz[y][x] = new pixel();
				// Extraer componentes ARGB
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;

				matriz[y][x].setRed(red);	
				matriz[y][x].setGreen(green);	
				matriz[y][x].setBlue(blue);	

			}
		}
		return matriz;
	}

	public static Image convertImage(int[][] imageTemporal){
		int alto = imageTemporal.length;
		int ancho = imageTemporal[0].length;
		Image image;
		BufferedImage buffer = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                // Asumiendo que imageTemporal contiene valores ARGB completos.
                buffer.setRGB(x, y, imageTemporal[y][x]);
			}
        }
		image = buffer;
		return image;
	}
	
	public static int[][] matriztoARGB(int[][] matrizPixel){
		int alto = matrizPixel.length;
		int ancho = matrizPixel[0].length;
		int[][] matrizARGB = new int[alto][ancho];
		int pixel = 0;
		int color;
		for(int y = 0;y < alto;y++){
			for(int x = 0;x<ancho;x++){
				pixel = matrizPixel[y][x];
				
				color = (255 << 24) | (pixel << 16) | (pixel << 8) | pixel;
				
				matrizARGB[y][x] = color;
			}
		}

		return matrizARGB;
	}

	public static String getFirstWord(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        int firstSpaceIndex = str.indexOf(' ');
        if (firstSpaceIndex == -1) {
            return str; // No hay espacios, la cadena completa es una palabra
        }
        return str.substring(0, firstSpaceIndex);
    }

	public static double validar(double valor){
		if (valor < 0) return 0;
		if (valor > 255) return 255;
		return valor;
	}

	public static double redondear(double valor){
		return Math.round(valor);
	}

	public static double validarCero(double valor){
		return valor = (valor == 0) ? 0.00000000001 : valor; 
	}

	public static double minDeTres(double a, double b, double c) {
		return Math.min(Math.min(a, b), c);
	}

	public static double maxDeTres(double a,double b, double c){
		return Math.max(Math.max(a,b),c);
	}

	public static double redondear(double valor,int decimales){
		double factor = Math.pow(10, decimales);
        return Math.round(valor * factor) / factor;
	}
}
