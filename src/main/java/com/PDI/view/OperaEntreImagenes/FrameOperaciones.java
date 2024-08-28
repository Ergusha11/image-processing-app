package com.PDI.view.OperaEntreImagenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.PDI.controller.OperaImage.*;
import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.PanelImagen;
import com.PDI.view.Histograma.PanelHistograma;

public class FrameOperaciones extends JInternalFrame{
	private FrameMain main;
	private JComboBox<String> listOne;
	private JComboBox<String> listTwo;
	private JComboBox<String> listOpc;
	private JPanel panelNorth;	
	private JPanel panelCenter;
	private JPanel panelSouth;
	private PanelImagen panelImageFirst;
	private PanelImagen panelImageSecond;
	private PanelImagen panelImageResuld;
	private JPanel panelHisto1;
	private JPanel panelHisto2;
	private JPanel panelHistoResuld;
	private JButton btnProcesar;
	private JButton btnAceptar;
	private final int width = 400;
	private final int heigth = 400;
	private int[][] resultd = null;
	greyImagesReferences imageGray = greyImagesReferences.getInstance();
	
	public FrameOperaciones(FrameMain main){
		super("Operaciones entre imagenes");
		this.main = main;
		initComponents();
		setSize(1180,680);
		setClosable(true);
		setVisible(true);
	}
	

	private void initComponents(){
		setLayout(new BorderLayout());
		
		listOne = new JComboBox<>();
		listTwo = new JComboBox<>();
		listOpc = new JComboBox<>();

		listOne.setPreferredSize(new Dimension(160,26));
		listTwo.setPreferredSize(new Dimension(160,26));

        panelNorth = new JPanel();
		panelCenter = new JPanel();
		panelSouth = new JPanel();

		JPanel panelImage1 = new JPanel(); 
		JPanel panelImage2 = new JPanel(); 
		JPanel panelImage3 = new JPanel();

		panelHisto1 = new JPanel(null);
		panelHisto2 = new JPanel(null);
		panelHistoResuld = new JPanel(null);
		panelHisto1.setSize(390,210);
		panelHisto2.setSize(390,210);

		btnAceptar = new JButton("Aceptar");
		btnProcesar = new JButton("Procesar");
		
		panelImage1.setBorder(new BevelBorder(BevelBorder.RAISED));
		panelImage2.setBorder(new BevelBorder(BevelBorder.RAISED));
		panelImage3.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		panelCenter.setLayout(new GridLayout(1,3));
		panelSouth.setLayout(new GridLayout(1,3));
		panelSouth.setPreferredSize(new Dimension(1180,210));

		panelImageFirst = new PanelImagen();
		panelImageSecond = new PanelImagen();
		panelImageResuld = new PanelImagen();

		panelImageFirst.setSize(width,heigth);

		panelImage1.add(panelImageFirst);
		panelImage2.add(panelImageSecond);
		panelImage3.add(panelImageResuld);

		panelCenter.add(panelImage1);
		panelCenter.add(panelImage2);
		panelCenter.add(panelImage3);

		panelSouth.add(panelHisto1);
		panelSouth.add(panelHisto2);
		panelSouth.add(panelHistoResuld);

		panelNorth.add(listOne);
		panelNorth.add(listTwo);
		panelNorth.add(listOpc);
		panelNorth.add(btnProcesar);
		panelNorth.add(btnAceptar);

		populateComboBox();
		
		int id = Integer.parseInt(
			FuntionStatic.getFirstWord((String)listOne.getSelectedItem()));
		panelImageFirst.setImage(addImageList(id),width - 25,heigth - 25);
				
		PanelHistograma panel = addHistogram(id);
		panelHisto1.add(panel);
		panelHisto1.revalidate();
		panelHisto1.repaint();

		id = Integer.parseInt(
			FuntionStatic.getFirstWord((String)listTwo.getSelectedItem()));
		panelImageSecond.setImage(addImageList(id),width - 25,heigth - 25);

		panel = addHistogram(id);
		panelHisto2.add(panel);
		panelHisto2.revalidate();
		panelHisto2.repaint();

		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter,BorderLayout.CENTER);
		add(panelSouth,BorderLayout.SOUTH);
		

		listener();

	}

	private void populateComboBox() {
        HashMap<Integer, String> map = imageGray.getImageNames();
		String valueName; 
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
			valueName = entry.getKey() + " - " + entry.getValue();
            listOne.addItem(valueName);
			listTwo.addItem(valueName);
        }

		listOpc.addItem("Suma");
		listOpc.addItem("Resta");
		listOpc.addItem("Multiplicacion");
		listOpc.addItem("Division");

		listOpc.addItem("And");
		listOpc.addItem("Or");
		listOpc.addItem("Not");

		listOpc.addItem("<");
		listOpc.addItem("<=");
		listOpc.addItem(">");
		listOpc.addItem(">=");
		listOpc.addItem("==");
		listOpc.addItem("!=");
	}

	private Image addImageList(int id){
		int[][] matrizImage = imageGray.getImage().get(id);
		matrizImage = FuntionStatic.matriztoARGB(matrizImage);
		return FuntionStatic.convertImage(matrizImage);
	}

	private Image addImageList(int[][] matrizImage){
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

	private PanelHistograma addHistogram(int id){
		int[][] matrizImage = imageGray.getImage().get(id);
		String nameTitle = "Histograma de la imagen " + id;

		DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
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

	private void listener(){
		listOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listOne.getSelectedItem()));
				
				panelImageFirst.setImage(addImageList(id),width - 25,heigth - 25);

				panelHisto1.removeAll();
				PanelHistograma panel = addHistogram(id);
				panelHisto1.add(panel);
				panelHisto1.revalidate();
				panelHisto1.repaint();
				
				revalidate();
				repaint();
			}
		});	

		listTwo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listTwo.getSelectedItem()));

				panelImageSecond.setImage(addImageList(id),width - 25,heigth - 25);


				panelHisto2.removeAll();
				PanelHistograma panel = addHistogram(id);
				panelHisto2.add(panel);
				panelHisto2.revalidate();
				panelHisto2.repaint();

				revalidate();
				repaint();
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valueName = (String)listOpc.getSelectedItem();
				String model = "Gris";
				int id = FuntionStatic.generarId();
				System.out.println("El id de la operacion es: " + id);
				FrameImagen frameImagen = new FrameImagen(
					FuntionStatic.convertImage(FuntionStatic.matriztoARGB(resultd)));
				frameImagen.setId(id);
				frameImagen.setTipo(model);
				frameImagen.setModelColor(model);
				frameImagen.setTitle(model + valueName);
				imageGray.addImage(id,resultd,model+valueName);
				main.seleccionarFrameImagen(frameImagen);	
				main.addInternalFrame(frameImagen);
			}
		});

		btnProcesar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				operationImage operation = new operationImage(null);
				String opc = (String)listOpc.getSelectedItem();
				int[][] image1,image2,matrizOperation;

				int id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listOne.getSelectedItem()));

				image1 = imageGray.getImage().get(id);

				id = Integer.parseInt(
					FuntionStatic.getFirstWord((String)listTwo.getSelectedItem()));

				image2 = imageGray.getImage().get(id);

				switch(opc){
					case "Suma" ->	operation.setStrategy(new operationAdd());
					case "Resta" -> operation.setStrategy(new operationSub());
					case "Multiplicacion" -> operation.setStrategy(new operationMulti());
					case "Division" -> operation.setStrategy(new operationDiv());
					case "And" -> operation.setStrategy(new operationAnd());
					case "Or" -> operation.setStrategy(new operationOr());
					case "Not" -> operation.setStrategy(new operationNot());
					case "<" -> operation.setStrategy(new operationSmaller());
					case "<=" -> operation.setStrategy(new operationLessEqual());
					case ">" -> operation.setStrategy(new operationGreater());
					case ">=" -> operation.setStrategy(new operationGreaterEqual());
					case "==" -> operation.setStrategy(new operationEquals());
					case "!=" -> operation.setStrategy(new operationDiffe());
					default -> {
						JOptionPane.showMessageDialog(null,"Problema al realizar la operacion", "Error de operacion", JOptionPane.ERROR_MESSAGE);
						operation = null;
					}			
				}

				matrizOperation = operation.applyOperation(image1,image2);

				resultd = matrizOperation;

				panelImageResuld.setImage(addImageList(matrizOperation),width - 25,heigth - 25);
				
				panelHistoResuld.removeAll();
				PanelHistograma panel = addHistogram(matrizOperation);
				panelHistoResuld.add(panel);
				panelHistoResuld.revalidate();
				panelHistoResuld.repaint();
			}
		});
	}
}
