package com.PDI.view.Binarizacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.PDI.controller.lectorPlanos;
import com.PDI.controller.Binarizacion.umbralizacion;
import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.PanelImagen;
import com.PDI.view.Histograma.PanelHistograma;

public class FrameBina extends JInternalFrame{
	private lectorPlanos image;
	private Scrollbar umbral;
	private TextField valor;
	private Scrollbar secondUmbral; 
	private TextField secondValor;
	private JButton btnProcesar;
	private JButton btnNegativo;
	private JButton btnAceptar;
	private JPanel panelCenter;
	private JPanel panelWest; 
	private JPanel secondUmbralPanel;
	private JComboBox<String> listOption; 
	private boolean isSecondUmbralVisible = false;
	private FrameMain main;
	private String title;
	private int[][] matrizGrey;
	private int[][] resuld = null;
	private int id;
	greyImagesReferences imagesGrey = greyImagesReferences.getInstance();

	public FrameBina(lectorPlanos image,int id,FrameMain main,String title){
		super("Binarizacion",true);
		this.image = image;
		this.title = title;
		matrizGrey = imagesGrey.getImage().get(id);
		this.main = main;
		initComponents();
		listener();
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// Limpiar recursos o manejar el cierre
				dispose();
			}
		});
	}

	private void initComponents(){
		int extent = 50;
		int width = 350;
		int heigth = 260;
		setLayout(new BorderLayout());
		setVisible(true);
		JPanel settings = new JPanel();
		panelWest = new JPanel(null);
		panelWest.setBackground(Color.GRAY);
		panelWest.setPreferredSize(new Dimension(400,0));
		
		PanelImagen panelImage = new PanelImagen(image.getImagen());
		panelImage.setImage(image.getImagen(),width,heigth);
        panelImage.setBounds(25, 20, width, heigth);
		
		listOption = new JComboBox<String>();
		listOption.setBounds(150,300,100,20);

		listOption.addItem("1 umbral");
		listOption.addItem("2 umbrales");

		umbral = new Scrollbar(Scrollbar.HORIZONTAL,0,extent,0,255 + extent);
		umbral.setBounds(75,330,250,16);
		umbral.setBackground(Color.white);
	
		settings.setLayout(new GridLayout(1,2));
		settings.setBounds(110,350,180,20);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(140,380,90,24);
		btnProcesar.setBackground(Color.white);
	
		btnNegativo = new JButton("Negativo");
		btnNegativo.setBounds(140,420,90,24);
		btnNegativo.setBackground(Color.WHITE);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(140,460,90,24);
		btnAceptar.setBackground(Color.white);

		valor = new TextField("0",4);
		Label etiqueta = new Label( "Umbral (0-255)" );
		settings.add(valor);
		settings.add(etiqueta);

		panelWest.add(panelImage);
		panelWest.add(umbral);
		panelWest.add(listOption);
		panelWest.add(settings);
		panelWest.add(btnProcesar);
		panelWest.add(btnNegativo);
		panelWest.add(btnAceptar);

		panelCenter = new JPanel();

		setClosable(true);
		setMaximizable(true);
		setSize(1300, 600);
		add(panelWest,BorderLayout.WEST);
	}

	private void binaImage(int umbral,int umbralDos,boolean bandera){
		umbralizacion bina = new umbralizacion();
		
		String selectedItem = (String) listOption.getSelectedItem();
		
		if(bandera){
			if(resuld!=null){
				resuld = bina.negativo(resuld);
			}else {
				JOptionPane.showMessageDialog(null,"Error la imagen no a sido binarizada","ERROR",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else{
			if (selectedItem.equals("2 umbrales")) {
				this.resuld = bina.binarizacion(matrizGrey,umbral,umbralDos);
			} else {
				this.resuld = bina.binarizacion(matrizGrey,umbral);
			}
		}
		

		DatosEstadisticos estadisticas = new DatosEstadisticos(resuld);
		estadisticas.frecuencias();
		double [][] probaGrey = estadisticas.getProbabilidadPixel();
		double [] max = estadisticas.getValorMax();
		

		panelCenter.removeAll();
		

		PanelImagen panelImage = new PanelImagen(
			FuntionStatic.convertImage(FuntionStatic.matriztoARGB(resuld)));

		PanelHistograma panelHistograma = new PanelHistograma(probaGrey[0],max[0],Color.GRAY);
		
		int width = matrizGrey.length;
		int height = matrizGrey[0].length;
		//System.out.println("El width es: " + width);
		//System.out.println("El height" + height);

		if(width >= 580 || height >= 580 ){
            JScrollPane scroll = new JScrollPane(panelImage,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setPreferredSize(new Dimension(450,300));
			panelCenter.add(scroll);
			//System.out.println("Entre a la condicion");
		}
		else{
			panelCenter.add(panelImage);
		}
		panelCenter.add(panelHistograma);
		//panelImage.setPreferredSize(new Dimension(350,250));
		panelHistograma.setPreferredSize(new Dimension(350,220));
		
		add(panelCenter,BorderLayout.CENTER);
		
		revalidate();
        repaint();
	}

	private void addSecondScrool(){
		if (!isSecondUmbralVisible) {
			secondUmbralPanel = new JPanel(null);
			int extent = 50;

			secondUmbral = new Scrollbar(Scrollbar.HORIZONTAL, 0, extent, 0, 255 + extent);
			secondUmbral.setBounds(75, 390, 250, 16);
			secondUmbral.setBackground(Color.white);
			
			secondUmbralPanel.setLayout(new GridLayout(1,2));
			secondUmbralPanel.setBounds(95,410,205,20);
			
			btnProcesar.setLocation(140,440);
			btnNegativo.setLocation(140,480);
			btnAceptar.setLocation(140,520);
			//btnProcesar.setBounds(140,380,90,30);
			secondValor = new TextField("0", 4);
			Label etiqueta = new Label("2 umbrales (0-255)");

			secondUmbralPanel.add(secondValor);
			secondUmbralPanel.add(etiqueta);
			
			panelWest.add(secondUmbral);
			panelWest.add(secondUmbralPanel);
			
			isSecondUmbralVisible = true;
			
			if(secondUmbralPanel != null){
				System.out.println("Si entre a la condicion");
				secondUmbral.addAdjustmentListener(e -> {
						secondValor.setText(String.valueOf(secondUmbral.getValue()));
				});
			}
			
			revalidate();
			repaint();
		}
	}
    
	private void removeSecondScroll() {
        if (isSecondUmbralVisible) {
			panelWest.remove(secondUmbral);
            panelWest.remove(secondUmbralPanel);
			btnProcesar.setLocation(140,380);
			btnNegativo.setLocation(140,420);
			btnAceptar.setLocation(140,460);
            secondUmbralPanel = null; 
			isSecondUmbralVisible = false;
            revalidate();
            repaint();
        }
    }

	private void listener() {
		umbral.addAdjustmentListener(e -> {
			valor.setText(String.valueOf(umbral.getValue()));
		});

		btnProcesar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int umbralValue = umbral.getValue();
				int umbralDos = 0;
				if(secondUmbral != null){
					umbralDos = secondUmbral.getValue();
				}
				binaImage(umbralValue,umbralDos,false);
				//System.out.println(umbralValue);
			}
		});

		btnNegativo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int umbralValue = umbral.getValue();
				int umbralDos = 0;
				if(secondUmbral != null){
					umbralDos = secondUmbral.getValue();
				}
				binaImage(umbralValue,umbralDos,true);
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				greyImagesReferences imagesGrey = greyImagesReferences.getInstance();
				String model = "Gris";
				int id = FuntionStatic.generarId();
				//System.out.println("El id de la operacion es: " + id);
				FrameImagen frameImagen = new FrameImagen(
					FuntionStatic.convertImage(FuntionStatic.matriztoARGB(resuld)));
				frameImagen.setId(id);
				frameImagen.setTipo(model);
				frameImagen.setModelColor(model);
				frameImagen.setTitle("Binarizado " + title);
				imagesGrey.addImage(id,resuld,model);
				main.seleccionarFrameImagen(frameImagen);	
				main.addInternalFrame(frameImagen);
			}
		});

		listOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				String selectedItem = (String) listOption.getSelectedItem();
				//System.out.println(selectedItem);
				if (selectedItem.equals("2 umbrales")) {
					addSecondScrool();
				} else {
					removeSecondScroll();
				}
			}
		});
	}

}
