package com.PDI.view.Histograma;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.controller.estadisticas.changeHisto;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.PanelImagen;

public class FrameChangeHisto extends JInternalFrame{
	private FrameMain main;
    private PanelImagen panelImagen;
    private PanelImagen panelImagen2;
    private JButton aceptar;
    private JButton procesar;
    private JComboBox<String> listImage;
    private JComboBox<String> listImage2;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JPanel panelHistograma1;
	private JPanel panelHistoAcum1;
    private JPanel panelHistograma2;
	private double[] imageHistoAcum = null;
	private int[][] resultImage;
	private final int width = 400;
	private final int heigth = 400;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();

    public FrameChangeHisto(FrameMain main) {
        super("Operaciones entre imagenes");
        this.main = main;
        initComponents();
        setSize(1180, 680);
        setClosable(true);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Initialize components

		JPanel image1 = new JPanel(); 
		JPanel image2 = new JPanel(); 
        
		panelImagen = new PanelImagen();
        panelImagen2 = new PanelImagen();

        aceptar = new JButton("Aceptar");
        procesar = new JButton("Aplicar");
        
		listImage = new JComboBox<>();
        listImage2 = new JComboBox<>();
        
		panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        
		panelHistograma1 = new JPanel(null);
		panelHistoAcum1 = new JPanel(null);
        panelHistograma2 = new JPanel(null);
		panelHistograma1.setSize(360,210);
		panelHistoAcum1.setSize(360,210);
		panelHistograma2.setSize(360,210);

        // Set layout and add components
		image1.setBorder(new BevelBorder(BevelBorder.RAISED));
		image2.setBorder(new BevelBorder(BevelBorder.RAISED));
		image1.add(panelImagen);
		image2.add(panelImagen2);

        panelNorth.setLayout(new GridLayout(1, 5));
        panelNorth.add(listImage);
        panelNorth.add(listImage2);
        panelNorth.add(procesar);
        panelNorth.add(aceptar);

        panelCenter.setLayout(new GridLayout(1, 2));
        panelCenter.add(image1);
        panelCenter.add(image2);

        panelSouth.setLayout(new GridLayout(1, 3));
		panelSouth.setPreferredSize(new Dimension(1180,210));
        panelSouth.add(panelHistograma1);
		panelSouth.add(panelHistoAcum1);
        panelSouth.add(panelHistograma2);

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        populateComboBox();

		int id = Integer.parseInt(
			FuntionStatic.getFirstWord((String)listImage.getSelectedItem()));
		panelImagen.setImage(addImageList(id),width - 25,heigth - 25);

		PanelHistograma panel = addHistogram(id);
		PanelHistograma histoAcum = addHistogramAcum(id,true);
		
		panelHistograma1.add(panel);
		panelHistoAcum1.add(histoAcum);

		panelHistoAcum1.revalidate();
		panelHistoAcum1.repaint();
		panelHistograma1.revalidate();
		panelHistograma1.repaint();

		id = Integer.parseInt(
			FuntionStatic.getFirstWord((String)listImage2.getSelectedItem()));
		panelImagen2.setImage(addImageList(id),width - 25,heigth - 25);

		panel = addHistogramAcum(id,false);
		panelHistograma2.add(panel);
		panelHistograma2.revalidate();
		panelHistograma2.repaint();
		listener();
    }

    private void populateComboBox() {
        HashMap<Integer, String> map = imageGray.getImageNames();
        String valueName;
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            valueName = entry.getKey() + " - " + entry.getValue();
            listImage.addItem(valueName);
            listImage2.addItem(valueName);
        }
    }

    private Image addImageList(int id) {
        int[][] matrizImage = imageGray.getImage().get(id);
        matrizImage = FuntionStatic.matriztoARGB(matrizImage);
        return FuntionStatic.convertImage(matrizImage);
    }

	private PanelHistograma addHistogram(int id){
		int[][] matrizImage = imageGray.getImage().get(id);
		String nameTitle = "Histograma de la imagen" + id;

		DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
		histogram.frecuencias();

        double frecuencias[][] = histogram.getProbabilidadPixel();
        double max[] = histogram.getValorMax();

		//System.out.println("Max: " + max[0]);

		PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0],max[0], Color.GRAY);
		panelHistograma.setBounds(10,0,350,210);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

	private PanelHistograma addHistogramAcum(int id,boolean bandera){
		int[][] matrizImage = imageGray.getImage().get(id);
		String nameTitle = "Histograma acumulativo de la imagen" + id;

		DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
		histogram.frecuencias();
		
        double frecuencias[][] = histogram.getProbaAcumPixel();
        double max[] = {1};

		if(!bandera){
			this.imageHistoAcum = frecuencias[0];
		}
		//System.out.println("Max: " + max[0]);

		PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0],max[0], Color.GRAY);
		panelHistograma.setBounds(10,0,350,210);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

	private Image addImage(int[][] matrizImage){
		matrizImage = FuntionStatic.matriztoARGB(matrizImage);
		return FuntionStatic.convertImage(matrizImage);
	}

	private PanelHistograma addHistogram(int[][] matrizResuld){
		String nameTitle = "Histograma de la imagen resultante";
		DatosEstadisticos histogram = new DatosEstadisticos(matrizResuld);
		histogram.frecuencias();

        double frecuencias[][] = histogram.getProbabilidadPixel();
        double max[] = histogram.getValorMax();

		//System.out.println("Max: " + max[0]);

		PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0],max[0], Color.GRAY);
		panelHistograma.setBounds(20,0,350,210);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

	private PanelHistograma addHistogramAcum(int[][] matrizImage){
		String nameTitle = "Histograma acumulativo de la imagen resultante";

		DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
		histogram.frecuencias();
		
        double frecuencias[][] = histogram.getProbaAcumPixel();
        double max[] = {1};

		//System.out.println("Max: " + max[0]);

		PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0],max[0], Color.GRAY);
		panelHistograma.setBounds(10,0,350,210);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

	private void listener(){
		listImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(
				FuntionStatic.getFirstWord((String)listImage.getSelectedItem()));
				panelImagen.setImage(addImageList(id),width - 25,heigth - 25);

				panelHistograma1.removeAll();
				panelHistoAcum1.removeAll();

				PanelHistograma panel = addHistogram(id);
				PanelHistograma histoAcum = addHistogramAcum(id,true);
				
				panelHistograma1.add(panel);
				panelHistoAcum1.add(histoAcum);

				panelHistoAcum1.revalidate();
				panelHistoAcum1.repaint();
				panelHistograma1.revalidate();
				panelHistograma1.repaint();

				revalidate();
				repaint();
			}
		});

		listImage2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listImage2.getSelectedItem()));

				panelImagen2.setImage(addImageList(id),width - 25,heigth - 25);
				panelHistograma2.removeAll();
				PanelHistograma panel = addHistogramAcum(id,false);
				panelHistograma2.add(panel);
				panelHistograma2.revalidate();
				panelHistograma2.repaint();

				revalidate();
				repaint();
				
			}
		});

		procesar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listImage.getSelectedItem()));

				int[][] matrizImage = imageGray.getImage().get(id);
				changeHisto changeImage = new changeHisto(matrizImage,imageHistoAcum);
				resultImage = changeImage.changeImageHisto();
				panelImagen.setImage(addImage(resultImage), width - 25,heigth - 25);

				panelHistograma1.removeAll();
				panelHistoAcum1.removeAll();

				PanelHistograma histoAcum = addHistogramAcum(resultImage);
				PanelHistograma panel = addHistogram(resultImage);
				panelHistograma1.add(panel);
				panelHistoAcum1.add(histoAcum);
				
				panelHistoAcum1.revalidate();
				panelHistoAcum1.repaint();
				panelHistograma1.revalidate();
				panelHistograma1.repaint();

				revalidate();
				repaint();
			}
		});

		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String model = "Gris";
				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listImage.getSelectedItem()));
				HashMap<Integer, String> map = imageGray.getImageNames();
				String valueName = map.get(id);
				id = FuntionStatic.generarId();
				FrameImagen frameImagen = new FrameImagen(
					FuntionStatic.convertImage(FuntionStatic.matriztoARGB(resultImage)));
				frameImagen.setId(id);
				frameImagen.setTipo(model);
				frameImagen.setModelColor(model);

				model = "ModifiacionHisto ";

				frameImagen.setTitle(model + valueName);
				imageGray.addImage(id,resultImage,model+valueName);
				main.seleccionarFrameImagen(frameImagen);	
				main.addInternalFrame(frameImagen);
			}
		});
	}
}
