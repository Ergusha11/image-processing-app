package com.PDI.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 *
 * @author Saul
 */
public class PanelImagen extends JPanel {
    private Image imagen;
    
	public PanelImagen(){
		this.imagen = null;
	}

	public PanelImagen(Image imagen) {
        super();
        this.imagen = imagen;
        //this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setPreferredSize(new Dimension(imagen.getWidth(null), imagen.getHeight(null)));
    }
    
    public void addImagen(Image imagen) {
        this.imagen = imagen;
        this.setPreferredSize(new Dimension(imagen.getWidth(null), imagen.getHeight(null)));
        revalidate(); 
        repaint();
    }
	
	public void setImage(Image image,int width, int height){
		this.imagen = resizeImage(image,width,height);
		this.setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
	}

	private Image resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bufferedImage;
    }

    /**
     * Dibuja la imagen BMP en el panel del Frame interno.
     * 
     * @param g el componente grafico !=null
     */
    @Override
    public void paintComponent( Graphics g ) {
    	super.paintComponent( g ); //pinta el fondo
    	//Pone la imagen en su tamanio normal.
    	if( imagen!=null ){
            g.drawImage( imagen, 0, 0, this );
        }
    }
    
    public Image getImagen() {
        return imagen;
    }
}
