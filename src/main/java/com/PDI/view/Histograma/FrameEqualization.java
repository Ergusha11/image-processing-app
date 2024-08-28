package com.PDI.view.Histograma;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import com.PDI.controller.OperaImage.*;
import com.PDI.controller.equalization.*;
import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.Histograma.PanelHistograma;
import com.PDI.view.PanelImagen;

public class FrameEqualization extends JInternalFrame {
    private FrameMain main;
    private JComboBox<String> listOne;
    private JComboBox<String> listTwo;
    private JComboBox<String> listOpc;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private PanelImagen panelImageFirst;
    private PanelImagen panelImagenSecond;
    private JPanel panelHisto1;
    private JPanel panelHistoAcum1;
    private JPanel panelHistoAcumResuld;
	private JTextField textFieldAlfa;
	private JTextField textFieldPot;
	private JTextField textField1;
	private JTextField textField2;
    private JButton btnProcesar;
    private JButton btnAceptar;
    private final int width = 400;
    private final int height = 400;
    private int[][] resultd = null;
	private double[] probabilidadAcum = null;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();

    public FrameEqualization(FrameMain main) {
        super("Equalización del histograma");
        this.main = main;
        initComponents();
        setSize(1180, 680);
        setClosable(true);
        setVisible(true);
        try {
            setSelected(true);
            toFront();
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        listOne = new JComboBox<>();
        listTwo = new JComboBox<>();
        listOpc = new JComboBox<>();

        listOne.setPreferredSize(new Dimension(160, 26));
        listTwo.setPreferredSize(new Dimension(160, 26));

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        JPanel panelImage1 = new JPanel();
        JPanel panelOpciones = new JPanel();

        panelHisto1 = new JPanel(null);
        panelHistoAcum1 = new JPanel(null);
        panelHistoAcumResuld = new JPanel(null);
        panelHisto1.setSize(390, 210);
        panelHistoAcum1.setSize(390, 210);
        panelHistoAcumResuld.setSize(390, 210);

        btnAceptar = new JButton("Aceptar");
        btnProcesar = new JButton("Procesar");

        panelImage1.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelOpciones.setBorder(new BevelBorder(BevelBorder.RAISED));

        panelImage1.setPreferredSize(new Dimension(width, height));

        panelCenter.setLayout(new GridLayout(1, 2));
        panelSouth.setLayout(new GridLayout(1, 3));
        panelSouth.setPreferredSize(new Dimension(1180, 210));

        panelImageFirst = new PanelImagen();

        panelImageFirst.setSize(width, height);

        panelImage1.add(panelImageFirst);

        panelCenter.add(panelImage1);
        panelCenter.add(panelOpciones);

        panelSouth.add(panelHisto1);
        panelSouth.add(panelHistoAcum1);
        panelSouth.add(panelHistoAcumResuld);

        panelNorth.add(listOne);
        panelNorth.add(listTwo);
        panelNorth.add(listOpc);
        panelNorth.add(btnProcesar);
        panelNorth.add(btnAceptar);

        populateComboBox();

        int id = Integer.parseInt(FuntionStatic.getFirstWord((String) listOne.getSelectedItem()));
        panelImageFirst.setImage(addImageList(id), width - 25, height - 25);

        PanelHistograma panel = addHistogram(id);
        panelHisto1.add(panel);
        panelHisto1.revalidate();
        panelHisto1.repaint();

        PanelHistograma panelAcum = addHistogramAcum(id,false);
        panelHistoAcum1.add(panelAcum);
        panelHistoAcum1.revalidate();
        panelHistoAcum1.repaint();

		id = Integer.parseInt(FuntionStatic.getFirstWord((String) listTwo.getSelectedItem()));
		panelAcum = addHistogramAcum(id,true);
		panelHistoAcumResuld.add(panelAcum);
		panelHistoAcumResuld.revalidate();
		panelHistoAcumResuld.repaint();

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        listener();
        optEqual(panelOpciones);
    }

    private void populateComboBox() {
        HashMap<Integer, String> map = imageGray.getImageNames();
        String valueName;
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            valueName = entry.getKey() + " - " + entry.getValue();
            listOne.addItem(valueName);
            listTwo.addItem(valueName);
        }

        listOpc.addItem("Uniforme");
        listOpc.addItem("Exponencial");
        listOpc.addItem("Rayleigh");
        listOpc.addItem("Hiperbólica Raices");
        listOpc.addItem("Hiperbólica Logarítmica");
    }

	private void optEqual(JPanel panelOpciones) {
		panelOpciones.setLayout(new BorderLayout());
		
		// Crear y añadir el título centrado
		JLabel titleLabel = new JLabel("Parámetros de ecualización");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelOpciones.add(titleLabel, BorderLayout.NORTH);
		
		JPanel imagePanel = new JPanel();
		JPanel optionsPanel = new JPanel(new GridBagLayout());
		imagePanel.setPreferredSize(new Dimension(250, 250));

		// Añadir la imagen pequeña
		panelImagenSecond = new PanelImagen();
		panelImagenSecond.setPreferredSize(new Dimension(250, 250));
		int id = Integer.parseInt(FuntionStatic.getFirstWord((String) listTwo.getSelectedItem()));
		panelImagenSecond.setImage(addImageList(id), 250 - 25, 250 - 25);
		imagePanel.add(panelImagenSecond);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// Componentes para las barras de desplazamiento
		JLabel labelSlider1 = new JLabel("F min:");
		JSlider slider1 = new JSlider(0, 255);
		textField1 = new JTextField(3);
		textField1.setEditable(true);
		textField1.addActionListener(e -> {
			int value = Integer.parseInt(textField1.getText());
			if (value >= 0 && value <= 255) {
				slider1.setValue(value);
			} else {
				JOptionPane.showMessageDialog(panelOpciones, "Ingrese un valor entre 0 y 255");
			}
		});
		slider1.addChangeListener(e -> textField1.setText(String.valueOf(slider1.getValue())));

		JLabel labelSlider2 = new JLabel("F max:");
		JSlider slider2 = new JSlider(0, 255);
		textField2 = new JTextField(3);
		textField2.setEditable(true);
		textField2.addActionListener(e -> {
			int value = Integer.parseInt(textField2.getText());
			if (value >= 0 && value <= 255) {
				slider2.setValue(value);
			} else {
				JOptionPane.showMessageDialog(panelOpciones, "Ingrese un valor entre 0 y 255");
			}
		});
		slider2.addChangeListener(e -> textField2.setText(String.valueOf(slider2.getValue())));

		// Componentes para alfa y pot
		JLabel labelAlfa = new JLabel("Alfa:");
		textFieldAlfa = new JTextField(5);
		
		JLabel labelPot = new JLabel("Pot:");
		textFieldPot = new JTextField(5);

		textField1.setText("127");
		textField2.setText("127");
		textFieldAlfa.setText("0.0");
		textFieldPot.setText("0.0");
		
		// Añadir componentes al panel de opciones usando GridBagLayout
		gbc.gridx = 0;
		gbc.gridy = 0;
		optionsPanel.add(labelSlider1, gbc);
		gbc.gridx = 1;
		optionsPanel.add(slider1, gbc);
		gbc.gridx = 2;
		optionsPanel.add(textField1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		optionsPanel.add(labelSlider2, gbc);
		gbc.gridx = 1;
		optionsPanel.add(slider2, gbc);
		gbc.gridx = 2;
		optionsPanel.add(textField2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		optionsPanel.add(labelAlfa, gbc);
		gbc.gridx = 1;
		optionsPanel.add(textFieldAlfa, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		optionsPanel.add(labelPot, gbc);
		gbc.gridx = 1;
		optionsPanel.add(textFieldPot, gbc);

		// Añadir ambos paneles al panel de opciones
		panelOpciones.add(imagePanel, BorderLayout.CENTER);
		panelOpciones.add(optionsPanel, BorderLayout.SOUTH);
	}

    private Image addImageList(int id) {
        int[][] matrizImage = imageGray.getImage().get(id);
        matrizImage = FuntionStatic.matriztoARGB(matrizImage);
        return FuntionStatic.convertImage(matrizImage);
    }

    private Image addImageList(int[][] matrizImage) {
        matrizImage = FuntionStatic.matriztoARGB(matrizImage);
        return FuntionStatic.convertImage(matrizImage);
    }

    private PanelHistograma addHistogram(int[][] matrizResuld) {
        String nameTitle = "Histograma de la imagen resultante";
        DatosEstadisticos histogram = new DatosEstadisticos(matrizResuld);
        histogram.frecuencias();

        double frecuencias[][] = histogram.getProbabilidadPixel();
        double max[] = histogram.getValorMax();

        PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0], max[0], Color.GRAY);
        panelHistograma.setBounds(20, 0, 350, 210);
        panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

        Border lineBorder = new LineBorder(Color.BLUE, 1);
        Border title = BorderFactory.createTitledBorder(lineBorder, nameTitle);
        ((TitledBorder) title).setTitleColor(Color.RED);

        panelHistograma.setBorder(title);

        return panelHistograma;
    }

    private PanelHistograma addHistogram(int id) {
        int[][] matrizImage = imageGray.getImage().get(id);
        String nameTitle = "Histograma de la imagen " + id;

        DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
        histogram.frecuencias();

        double frecuencias[][] = histogram.getProbabilidadPixel();
        double max[] = histogram.getValorMax();

        PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0], max[0], Color.GRAY);
        panelHistograma.setBounds(20, 0, 350, 210);
        panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

        Border lineBorder = new LineBorder(Color.BLUE, 1);
        Border title = BorderFactory.createTitledBorder(lineBorder, nameTitle);
        ((TitledBorder) title).setTitleColor(Color.RED);

        panelHistograma.setBorder(title);

        return panelHistograma;
    }

    private PanelHistograma addHistogramAcum(int id,boolean bandera) {
        int[][] matrizImage = imageGray.getImage().get(id);
        String nameTitle = "Histograma acumulado de la imagen " + id;

        DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
        histogram.frecuencias();

        double frecuencias[][] = histogram.getProbaAcumPixel();

		if(bandera){
			probabilidadAcum = frecuencias[0];
		}

        PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0], 1, Color.GRAY);
        panelHistograma.setBounds(20, 0, 350, 210);
        panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

        Border lineBorder = new LineBorder(Color.BLUE, 1);
        Border title = BorderFactory.createTitledBorder(lineBorder, nameTitle);
        ((TitledBorder) title).setTitleColor(Color.RED);

        panelHistograma.setBorder(title);

        return panelHistograma;
    }

	private PanelHistograma addHistogramAcum(int[][] matrizImage){
        String nameTitle = "Histograma acumulado de la imagen resultante";
        DatosEstadisticos histogram = new DatosEstadisticos(matrizImage);
        histogram.frecuencias();

        double frecuencias[][] = histogram.getProbaAcumPixel();

        PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0], 1, Color.GRAY);
        panelHistograma.setBounds(20, 0, 350, 210);
        panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

        Border lineBorder = new LineBorder(Color.BLUE, 1);
        Border title = BorderFactory.createTitledBorder(lineBorder, nameTitle);
        ((TitledBorder) title).setTitleColor(Color.RED);

        panelHistograma.setBorder(title);

        return panelHistograma;
	}

    private void listener() {
        listOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(FuntionStatic.getFirstWord((String) listOne.getSelectedItem()));

                panelImageFirst.setImage(addImageList(id), width - 25, height - 25);

                panelHisto1.removeAll();
                PanelHistograma panel = addHistogram(id);
                panelHisto1.add(panel);
                panelHisto1.revalidate();
                panelHisto1.repaint();

                panelHistoAcum1.removeAll();
                PanelHistograma panelAcum = addHistogramAcum(id,false);
                panelHistoAcum1.add(panelAcum);
                panelHistoAcum1.revalidate();
                panelHistoAcum1.repaint();

                revalidate();
                repaint();
            }
        });

        listTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(FuntionStatic.getFirstWord((String) listTwo.getSelectedItem()));

                panelImagenSecond.setImage(addImageList(id), 250 - 25, 250 - 25);

                panelHistoAcumResuld.removeAll();
                PanelHistograma panelAcumResuld = addHistogramAcum(id,true);
                panelHistoAcumResuld.add(panelAcumResuld);
                panelHistoAcumResuld.revalidate();
                panelHistoAcumResuld.repaint();

                revalidate();
                repaint();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valueName = (String) listOpc.getSelectedItem();
                String model = "Gris";
                int id = FuntionStatic.generarId();
                System.out.println("El id de la operacion es: " + id);
                FrameImagen frameImagen = new FrameImagen(FuntionStatic.convertImage(FuntionStatic.matriztoARGB(resultd)));
                frameImagen.setId(id);
                frameImagen.setTipo(model);
                frameImagen.setModelColor(model);
                frameImagen.setTitle(model + valueName);
                imageGray.addImage(id, resultd, model + valueName);
                main.seleccionarFrameImagen(frameImagen);
                main.addInternalFrame(frameImagen);
            }
        });

        btnProcesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				equealization equealization = new equealization(null);
                String opc = (String) listOpc.getSelectedItem();

                int id = Integer.parseInt(FuntionStatic.getFirstWord((String) listOne.getSelectedItem()));

                resultd = imageGray.getImage().get(id);
				double[] params = new double[]{};
				int fMin;
				int fMax;
				try {
					fMin = Integer.parseInt(textField1.getText());
					fMax = Integer.parseInt(textField2.getText());

					if (opc.equals("Exponencial") || opc.equals("Rayleigh")) {
						double alpha = Double.parseDouble(textFieldAlfa.getText());
						params = new double[]{alpha};
					} else if (opc.equals("Hiperbólica Raices")) {
						double pot = Double.parseDouble(textFieldPot.getText());
						params = new double[]{pot};
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
					return;
				}

                switch (opc) {
                    case "Uniforme" -> equealization.setStrategy(new ecualizacionUniforme());
                    case "Exponencial" -> equealization.setStrategy(new ecualizacionExponencial());
                    case "Rayleigh" -> equealization.setStrategy(new ecualizacionRayleigh());
                    case "Hiperbólica Raices" -> equealization.setStrategy(new ecualizacionHiperbolicaRaices());
                    case "Hiperbólica Logarítmica" -> equealization.setStrategy(new ecualizacionHiperbolicaLog());
                }

				if (equealization != null) {
					double[] lut = equealization.applyEquealization(fMin,fMax,probabilidadAcum,params);
					resultd = equealization.applyLut(resultd,lut);
				}

                panelImageFirst.setImage(addImageList(resultd), width - 25, height - 25);

                panelHisto1.removeAll();
                PanelHistograma panel = addHistogram(resultd);
                panelHisto1.add(panel);
                panelHisto1.revalidate();
                panelHisto1.repaint();

                panelHistoAcum1.removeAll();
                PanelHistograma panelAcum = addHistogramAcum(resultd);
                panelHistoAcum1.add(panelAcum);
                panelHistoAcum1.revalidate();
                panelHistoAcum1.repaint();

                revalidate();
                repaint();
            }
        });
    }
}

