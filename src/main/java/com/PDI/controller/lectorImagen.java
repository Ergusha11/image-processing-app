package com.PDI.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class lectorImagen{
	protected Image imagen;
	protected String path;
	protected String nombreArchivo;
	protected BufferedImage bufferedImagen;

	public lectorImagen(){
		imagen = null;
		this.path = null;
		this.nombreArchivo = null;
	}

	public lectorImagen(Image imagen){
		this.imagen = imagen;
        this.bufferedImagen = new BufferedImage(imagen.getWidth(null),
                imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Dibujar el Image en el BufferedImage
        Graphics2D bGr = bufferedImagen.createGraphics();
        bGr.drawImage(imagen, 0, 0, null);
        bGr.dispose();
	}
	
	/*
	 * Se obtienen el buffer de la imagen que se cargue
	*/
	public void leerBufferedImagen() {
        bufferedImagen = null;
        try {
            String acceso = this.path;
            //DEBUG
            //System.out.println("Acceso: " + acceso);
            FileInputStream input = 
                        new FileInputStream(
                            new File(acceso));
            bufferedImagen = ImageIO.read(input);
            //tipo = bufferedImagen.getType();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
        int ancho = bufferedImagen.getWidth();
        int alto = bufferedImagen.getHeight();
        imagen = bufferedImagen.getScaledInstance(ancho, alto, 
                BufferedImage.SCALE_DEFAULT);
    }
	
	/*
	 * Se crea un objeto de tipo FileChooser para buscar la imagen en el directorio
	*/
	public void buscarImagen(String path){
		accessDesk image = new accessDesk(path);
		if(image.getPath() != null){
			this.path = image.getPath().getAbsolutePath();
			this.nombreArchivo = image.getPath().getName();
		}	
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public BufferedImage getBufferedImagen() {
		return bufferedImagen;
	}

	public void setBufferedImagen(BufferedImage bufferedImagen) {
		this.bufferedImagen = bufferedImagen;
	}

	public String getPath() {
		return path;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public int getAncho() {
        return imagen.getWidth(null);
    }

    public int getAlto() {
        return imagen.getHeight(null);
    }

    public int getAnchoBufferedImage() {
        return bufferedImagen.getWidth();
    }

    public int getAltoBufferedImage() {
        return bufferedImagen.getHeight();
    }
}
