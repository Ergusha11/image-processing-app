package com.PDI.view.Filtros;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.PDI.model.greyImagesReferences;
import com.PDI.view.PanelImagen;
import com.PDI.view.Histograma.PanelHistograma;
import com.PDI.controller.Convolucion.*;
import com.PDI.controller.OperaImage.operationAdd;
import com.PDI.controller.OperaImage.operationImage;
import com.PDI.controller.estadisticas.DatosEstadisticos;
import com.PDI.model.FuntionStatic;

public class FrameFiltrosLineales extends JInternalFrame {
    private JComboBox<String> filterComboBox;
    private JComboBox<String> imageComboBox;
    private JComboBox<String> maskComboBox;
    private JButton btnProcesar;
    private JButton btnAceptar;
    private JPanel panelMascara;
	private JPanel panelMascaraAlta;
    private PanelImagen panelImageOriginal;
    private PanelImagen panelImageFiltered;
	private JPanel panelHistoImage;
	private JPanel panelHistoResult;
    private JTextField[][] textFieldsMascara;
    private JTextField[][] textFields;
    private final int width = 400;
    private final int height = 400;
    private int[][] resultImage = null;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();

    public FrameFiltrosLineales() {
        super("Filtros Lineales");
        initComponents();
        setSize(1200, 700);
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

        filterComboBox = new JComboBox<>();
        imageComboBox = new JComboBox<>();
        maskComboBox = new JComboBox<>();
        btnProcesar = new JButton("Procesar");
        btnAceptar = new JButton("Aceptar");

        filterComboBox.addItem("Pasa Bajas");
        filterComboBox.addItem("Pasa Altas");
        filterComboBox.addItem("Canny");	
		
		maskComboBox.addItem("Filtro Promedio");
		maskComboBox.addItem("Filtro Gaussiano");

        populateImageComboBox();

        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelEast = new JPanel();
		JPanel panelSout = new JPanel();

        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));

        JPanel panelImage1 = new JPanel();
        JPanel panelImage2 = new JPanel();

		panelHistoImage = new JPanel(null);
		panelHistoResult = new JPanel(null);
		JPanel extra = new JPanel(null);

        panelImage1.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImage2.setBorder(new BevelBorder(BevelBorder.RAISED));

        panelImageOriginal = new PanelImagen();
        panelImageFiltered = new PanelImagen();

        panelImageOriginal.setSize(width, height);
        panelImageFiltered.setSize(width, height);
		
		panelHistoImage.setSize(360,220);
		panelHistoResult.setSize(360,220);
		extra.setSize(360,100);

        panelImage1.add(panelImageOriginal);
        panelImage2.add(panelImageFiltered);

        panelSout.setLayout(new GridLayout(1, 3));
		panelSout.setPreferredSize(new Dimension(1200,210));
		panelSout.add(panelHistoImage);
		panelSout.add(panelHistoResult);
		panelSout.add(extra);

        panelCenter.add(panelImage1, BorderLayout.WEST);
        panelCenter.add(panelImage2, BorderLayout.EAST);
		
		panelMascara = new JPanel(new GridLayout(3, 3, 5, 5));
        panelMascara.setMaximumSize(new Dimension(150, 150));
        textFieldsMascara = new JTextField[3][3];

        panelMascaraAlta = new JPanel(new GridLayout(3, 3, 5, 5));
        panelMascaraAlta.setMaximumSize(new Dimension(150, 150));
        textFields = new JTextField[3][3];

        initializeMaskFields();

        maskComboBox.setMaximumSize(new Dimension(150, 25));

		panelEast.add(maskComboBox);
        panelEast.add(Box.createVerticalStrut(10));
        panelEast.add(panelMascara);
        panelEast.add(Box.createVerticalStrut(10));
        panelEast.add(panelMascaraAlta);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCenter, panelEast);
        splitPane.setResizeWeight(0.66); // Ajusta el peso del panel izquierdo (0.85) y derecho (0.15)
        splitPane.setDividerLocation(900); // Ajusta el tamaño del divisor

        panelNorth.add(imageComboBox);
        panelNorth.add(filterComboBox);
        panelNorth.add(btnProcesar);
        panelNorth.add(btnAceptar);

        add(panelNorth, BorderLayout.NORTH);
		add(panelSout, BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);

        listener();
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
		panelHistograma.setBounds(20,0,345,200);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

	private PanelHistograma addHistogram(int[][] matrizResuld){
		String nameTitle = "Histograma de la imagen resultante";
		DatosEstadisticos histogram = new DatosEstadisticos(matrizResuld);
		histogram.frecuencias();

        double frecuencias[][] = histogram.getProbabilidadPixel();
        double max[] = histogram.getValorMax();

		PanelHistograma panelHistograma = new PanelHistograma(frecuencias[0],max[0], Color.GRAY);
		panelHistograma.setBounds(42,0,345,200);
		panelHistograma.setBorder(new BevelBorder(BevelBorder.RAISED));

		Border lineBorder = new LineBorder(Color.BLUE,1);
		Border title = BorderFactory.createTitledBorder(lineBorder,nameTitle);
		((TitledBorder) title).setTitleColor(Color.RED);

		panelHistograma.setBorder(title);

		return panelHistograma;
	}

    private Image addImageList(int id) {
        int[][] matrizImage = imageGray.getImage().get(id);
        matrizImage = FuntionStatic.matriztoARGB(matrizImage);
        return FuntionStatic.convertImage(matrizImage);
    }

	private Image addImageList(int[][] matrizImage){
        matrizImage = FuntionStatic.matriztoARGB(matrizImage);
        return FuntionStatic.convertImage(matrizImage);
	}

    private void listener() {
        imageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imageComboBox.getSelectedItem() != null) {
                    int id = Integer.parseInt(FuntionStatic.getFirstWord((String) imageComboBox.getSelectedItem()));
                    panelImageOriginal.setImage(addImageList(id), width, height);
					panelHistoImage.removeAll();

					PanelHistograma panel = addHistogram(id);
					panelHistoImage.add(panel);
					panelHistoImage.repaint();
                    
					panelHistoImage.revalidate();
					panelImageOriginal.repaint();
                }
            }
        });

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) filterComboBox.getSelectedItem();
                if ("Pasa Bajas".equals(selectedFilter)) {
                    for (JTextField[] textFields : textFieldsMascara) {
                        for (JTextField textField : textFields) {
                            textField.setEditable(true);
                        }
                    }


                } else {
                    for (JTextField[] textFields : textFieldsMascara) {
                        for (JTextField textField : textFields) {
                            textField.setEditable(false);
                        }
                    }
                }
				addItem(selectedFilter);
            }
        });

        maskComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar el código para cambiar la máscara según la selección del usuario
                String selectedMask = (String) maskComboBox.getSelectedItem();
                updateMaskFields(selectedMask);
            }
        });

        btnProcesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMask = (String) filterComboBox.getSelectedItem();
                int id = Integer.parseInt(FuntionStatic.getFirstWord((String) imageComboBox.getSelectedItem()));
				applyFilter filter = new applyFilter(null);
				double[][] mask = null;
				double[][] maskTwo = null;
				int[][] aux = null;
				int[][] matrizImage = imageGray.getImage().get(id);

				if(selectedMask.equals("Pasa Bajas")){
					selectedMask = (String) maskComboBox.getSelectedItem();
					if(selectedMask.equals("Filtro Promedio")){
						mask = getMask();
						filter.setStrategy(new filterProme());
					}else if(selectedMask.equals("Filtro Gaussiano")){
						mask = getMask();
						filter.setStrategy(new filterGauss());
					}else {
						mask = getMask();
						filter.setStrategy(new filterPersonalizado());
					}
				} else if(selectedMask.equals("Pasa Altas")){
					//selectedMask = (String) maskComboBox.getSelectedItem();
					//if(selectedMask.equals("Roberts")){
						mask = getMask();
						maskTwo = getTwoMask();
					//}
					filter.setStrategy(new filterAlta());
				}else {
					resultImage = algorithCanny.applyCanny(matrizImage);	
				}

				if(!selectedMask.equals("Canny")){
					if (selectedMask.equals("Pasa Altas") && maskTwo != null) {
						aux = filter.filter(matrizImage, mask);
						resultImage = filter.filter(matrizImage, maskTwo);
						operationImage plus = new operationImage(new operationAdd());
						resultImage = plus.applyOperation(aux, resultImage);
					} else if (mask != null) {
						resultImage = filter.filter(matrizImage, mask);
					} else {
						// Manejar el caso en que la máscara no se haya establecido correctamente
						JOptionPane.showMessageDialog(null, "La máscara no se ha establecido correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				panelImageFiltered.setImage(addImageList(resultImage),width,height);
				panelHistoResult.removeAll();

				PanelHistograma panel = addHistogram(resultImage);
				panelHistoResult.add(panel);

				panelHistoResult.repaint();
				panelImageFiltered.repaint();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes añadir el código para guardar la imagen o realizar alguna otra acción
                JOptionPane.showMessageDialog(null, "Imagen procesada y aceptada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void populateImageComboBox() {
        HashMap<Integer, String> map = imageGray.getImageNames();
        String valueName;
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            valueName = entry.getKey() + " - " + entry.getValue();
            imageComboBox.addItem(valueName);
        }
    }

	private void addItem(String tipo){
		maskComboBox.removeAllItems();
		
		if(tipo.equals("Pasa Bajas")){
			maskComboBox.addItem("Filtro Promedio");
			maskComboBox.addItem("Filtro Gaussiano");
			maskComboBox.addItem("Personalizado");
		}
		else if(tipo.equals("Pasa Altas")){
			maskComboBox.addItem("Roberts");
			maskComboBox.addItem("Prewitt");
			maskComboBox.addItem("Sobel");
			maskComboBox.addItem("Frei-Chen");
		}
		else {
			maskComboBox.setEditable(false);
		}
	}

    private void initializeMaskFields() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
				textFieldsMascara[i][j] = new JTextField(5);
                textFieldsMascara[i][j].setHorizontalAlignment(JTextField.CENTER);
                textFieldsMascara[i][j].setEditable(true);
                panelMascara.add(textFieldsMascara[i][j]);

                textFields[i][j] = new JTextField(5);
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                textFields[i][j].setEditable(false);
                panelMascaraAlta.add(textFields[i][j]);

            }
        }
    }
	
	private void updateMaskFields(String mask) {
		double[][] filter = null;
		double[][] filterTwo = null;

		if ("Filtro Promedio".equals(mask)) {
			filter = convolution.maskAverageFilter(); // Cambiar según tu filtro promedio
		} else if ("Filtro Gaussiano".equals(mask)) {
			filter = convolution.maskGaussianFilter();
		} else if ("Personalizado".equals(mask)) {
			filter = new double[][] {
				{3, 3, 3},
				{3, 3, 3},
				{3, 3, 3}
			};
		} else if ("Roberts".equals(mask)) {
			filter = convolution.getKernelHF0();
			filterTwo = convolution.getKernelHC0();
		} else if ("Prewitt".equals(mask)) {
			filter = convolution.getPrewittKernelHF();
			filterTwo = convolution.getPrewittKernelHC();
		} else if ("Sobel".equals(mask)) {
			filter = convolution.getSobelKernelHF();
			filterTwo = convolution.getSobelKernelHC();
		} else if ("Frei-Chen".equals(mask)) {
			filter = convolution.getFreiChenKernelHF();
			filterTwo = convolution.getFreiChenKernelHC();
		} else {
			return; // Si el filtro no es reconocido, salir del método
		}

		// Establecer los valores en los JTextField para panelMascara
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				textFieldsMascara[i][j].setText(String.valueOf(filter[i][j]));
			}
		}

		// Establecer los valores en los JTextField para panelMascaraAlta si filterTwo no es nulo
		if (filterTwo != null) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					textFields[i][j].setText(String.valueOf(filterTwo[i][j]));
				}
			}
		} else {
			// Si filterTwo es nulo, limpiar los campos de texto en panelMascaraAlta
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					textFields[i][j].setText("");
				}
			}
		}
	}

	private double[][] getMask(){
        double[][] filter = new double[3][3];    
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
				String num = textFieldsMascara[i][j].getText();  // Ejemplo: Establecer todos los valores en 1
				filter[i][j] = Double.parseDouble(num);  // Ejemplo: Establecer todos los valores en 1
			}
		}

		return filter;
	}

	private double[][] getTwoMask(){
        double[][] filter = new double[3][3];    
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
				String num = textFields[i][j].getText();  // Ejemplo: Establecer todos los valores en 1
				filter[i][j] = Double.parseDouble(num);  // Ejemplo: Establecer todos los valores en 1
			}
		}
		return filter;
	}
}

