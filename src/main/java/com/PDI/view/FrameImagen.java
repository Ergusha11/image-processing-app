package com.PDI.view;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.Image;
import java.awt.BorderLayout;

import com.PDI.controller.lectorPlanos;

public class FrameImagen extends JInternalFrame{
	private PanelImagen panel;
	private lectorPlanos lector;
	private String nombreImage;
	private String tipo;
	private int id;
	private int idReference;
	private String modelColor;

	private static int x = 0;
	private static int y = 0;

	public FrameImagen(lectorPlanos lector) {
        super(lector.getNombreArchivo());
        this.lector = lector;
		this.tipo = "Color";
		this.modelColor = "RGB";
		this.idReference = -1;
        //this.bufferedImagen = lector.getBufferedImagen();
        initComponents();
    }
    
    public FrameImagen(Image image) {
        super("Visor de imagen procesada");
        initComponents(image);
        
    }
    
    private void initComponents() {
        panel = new PanelImagen(lector.getImagen());
        JScrollPane scroll = new JScrollPane(panel);
        
        // Establecer el tamaño del JInternalFrame basado en el tamaño de la imagen o un máximo deseado
        int ancho = Math.min(lector.getAncho(),580);
        int alto = Math.min(lector.getAlto(), 580);
        setSize(ancho+10, alto+20);
        
        if(lector.getAncho() > ancho || lector.getAlto() > alto) {
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            // Si no deseas que aparezcan las barras de desplazamiento cuando la imagen es más pequeña que los límites
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        
        
        getContentPane().add(scroll, BorderLayout.CENTER);
        
        //pack();
        // Configuración de las propiedades del JInternalFrame
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);

        // Hacer el JInternalFrame visible
        setVisible(true);
    }
    
    private void initComponents(Image image) {
        panel = new PanelImagen(image);
        JScrollPane scroll = new JScrollPane(panel);
        
		int ancho = Math.min(image.getWidth(null),580);
        int alto = Math.min(image.getHeight(null), 580);
        
		setSize(ancho+10, alto+20);
        getContentPane().setLayout(new BorderLayout());

        if(alto > 580 || ancho > 580) {
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            // Si no deseas que aparezcan las barras de desplazamiento cuando la imagen es más pequeña que los límites
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        getContentPane().add(scroll, BorderLayout.CENTER); // Usar JScrollPane para manejar imágenes grandes
        //setSize(image.getWidth(null)+10, image.getHeight(null)+20);
        
        //Extraer buffer a la imagen
        //this.bufferedImagen = convertirATipoBufferedImage(image);
        this.lector =  new lectorPlanos(image);
		//this.lector.extraerPixeles();
        //this.bufferedImagen = lector.getBufferedImagen();
        
        // Configuración de las propiedades del JInternalFrame
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        
        setVisible(true);
    }

	public PanelImagen getPanel() {
		return panel;
	}
	
	public int getIdReference() {
		return idReference;
	}

	public void setIdReference(int idReference) {
		this.idReference = idReference;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public lectorPlanos getLector() {
		return lector;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModelColor() {
		return modelColor;
	}

	public void setModelColor(String modelColor) {
		this.modelColor = modelColor;
	}
}
