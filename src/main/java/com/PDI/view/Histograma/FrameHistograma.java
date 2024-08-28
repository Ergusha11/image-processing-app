package com.PDI.view.Histograma;

import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.PDI.controller.lectorPlanos;
import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.model.pixel;
import com.PDI.model.referenciaImagen;
import com.PDI.view.PanelImagen;

public class FrameHistograma extends JInternalFrame{
	private JPanel panelHistograma;
	private PanelImagen panel;
	private lectorPlanos imagen;
	private String tipoImagen;
	private int idImage;
	private int idReference;
	private String modelColor;

	public FrameHistograma(lectorPlanos imagen,String tipoImagen,String modelColor,int idImage,int idReferences) {
        super("Estadisticas de la imagen",true);
        this.imagen = imagen;
        this.tipoImagen = tipoImagen;
		this.modelColor = modelColor;
		this.idImage = idImage;
		this.idReference = idReferences;
        iniinitComponents();
        agregarHistograma();
    }
    
    public void iniinitComponents(){
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setSize(1200, 400);
        
        panel = new PanelImagen(imagen.getImagen());
        panel.setBounds(20, 20, 300, 200);
        
        // Configuración del panelHistograma si es necesario
        panelHistograma = new JPanel(null);
        
        if(imagen.getAncho() >= 350 || imagen.getAlto()>= 350){
            JScrollPane scroll = new JScrollPane(panel,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setBounds(20, 20, 350, 350);
            panelHistograma.add(scroll);
        }
        else{
            // Agregar el panel de la imagen al panelHistograma
            panelHistograma.add(panel);
        }
        //panelHistograma.setLayout(new BorderLayout());

        // Configurar el contenido del JInternalFrame
        setContentPane(panelHistograma);
        
        setVisible(true);
    }

    public void agregarHistograma(){
		referenciaImagen refImage = referenciaImagen.getInstance();
		greyImagesReferences refGrey = greyImagesReferences.getInstance();
		DatosEstadisticos imagenEstadisticas = null;
		System.out.println(refImage.getImage().get(this.idImage));

		if (!tipoImagen.equals("Gris")) {
            pixel[][] matrizPixel = refImage.getImage().get(idImage);

			if(!modelColor.equals("RGB") && !modelColor.equals("CMY")){
				matrizPixel = FuntionStatic.extraPixel(FuntionStatic.toBufferedImage(imagen.getImagen()));
			}
			else if (!tipoImagen.equals("Color")){
				matrizPixel = refImage.getImage().get(idReference);
			}

            imagenEstadisticas = new DatosEstadisticos(matrizPixel);
        } else if (tipoImagen.equals("Gris")) {
            int[][] matrizPixel = refGrey.getImage().get(idImage);
            imagenEstadisticas = new DatosEstadisticos(matrizPixel);
        } else {
			JOptionPane.showMessageDialog(null,"Error al procesar la image" );
            return;
        }

        imagenEstadisticas.frecuencias();
        double frecuencias[][] = imagenEstadisticas.getProbabilidadPixel();
		double frecuAcumulativa[][] = imagenEstadisticas.getProbaAcumPixel();
        double max[] = imagenEstadisticas.getValorMax();
        
       switch (tipoImagen) {
            case "Color":
				// Rojo
                PanelHistograma histogramaRojo = new PanelHistograma(frecuencias[0], max[0], Color.RED);
                PanelHistograma histoAcumulativoRojo = new PanelHistograma(frecuAcumulativa[0], 1, Color.RED);
                histogramaRojo.setBounds(400, 20, 350, 220);
                histoAcumulativoRojo.setBounds(800, 20, 350, 220);

                // Verde
                PanelHistograma histogramaVerde = new PanelHistograma(frecuencias[1], max[1], Color.GREEN);
                PanelHistograma histoAcumulativoVerde = new PanelHistograma(frecuAcumulativa[1], 1, Color.GREEN);
                histogramaVerde.setBounds(400, 210, 350, 220);
                histoAcumulativoVerde.setBounds(800, 210, 350, 220);

                // Azul
                PanelHistograma histogramaAzul = new PanelHistograma(frecuencias[2], max[2], Color.BLUE);
                PanelHistograma histoAcumulativoAzul = new PanelHistograma(frecuAcumulativa[2], 1, Color.BLUE);
                histogramaAzul.setBounds(400, 410, 350, 220);
                histoAcumulativoAzul.setBounds(800, 410, 350, 220);

                panelHistograma.add(histogramaRojo);
                panelHistograma.add(histoAcumulativoRojo);
                panelHistograma.add(histogramaVerde);
                panelHistograma.add(histoAcumulativoVerde);
                panelHistograma.add(histogramaAzul);
                panelHistograma.add(histoAcumulativoAzul);
                break;

            case "Plano 1":
                PanelHistograma histogramaPlano1 = new PanelHistograma(frecuencias[0], max[0], Color.RED);
                PanelHistograma histoAcumulativoPlano1 = new PanelHistograma(frecuAcumulativa[0], 1, Color.RED);
                histogramaPlano1.setBounds(400, 20, 350, 220);
                histoAcumulativoPlano1.setBounds(800, 20, 350, 220);
                panelHistograma.add(histogramaPlano1);
                panelHistograma.add(histoAcumulativoPlano1);
				break;

            case "Plano 2":
				PanelHistograma histogramaPlano2 = new PanelHistograma(frecuencias[1], max[1], Color.GREEN);
                PanelHistograma histoAcumulativoPlano2 = new PanelHistograma(frecuAcumulativa[1], 1, Color.GREEN);
                histogramaPlano2.setBounds(400, 20, 350, 220);
                histoAcumulativoPlano2.setBounds(800, 20, 350, 220);
                panelHistograma.add(histogramaPlano2);
                panelHistograma.add(histoAcumulativoPlano2);
                break;

            case "Plano 3":
				PanelHistograma histogramaPlano3 = new PanelHistograma(frecuencias[2], max[2], Color.BLUE);
                PanelHistograma histoAcumulativoPlano3 = new PanelHistograma(frecuAcumulativa[2], 1, Color.BLUE);
                histogramaPlano3.setBounds(400, 20, 350, 220);
                histoAcumulativoPlano3.setBounds(800, 20, 350, 220);
                panelHistograma.add(histogramaPlano3);
                panelHistograma.add(histoAcumulativoPlano3);
                break;

            case "Gris":
				PanelHistograma histogramaGris = new PanelHistograma(frecuencias[0], max[0], Color.GRAY);
                PanelHistograma histoAcumulativoGris = new PanelHistograma(frecuAcumulativa[0], 1, Color.GRAY);
                histogramaGris.setBounds(400, 20, 350, 220);
                histoAcumulativoGris.setBounds(800, 20, 350, 220);
                panelHistograma.add(histogramaGris);
                panelHistograma.add(histoAcumulativoGris);
                break;

            default:
                throw new AssertionError();
        }
        panelHistograma.revalidate();
        panelHistograma.repaint();
	}

}
