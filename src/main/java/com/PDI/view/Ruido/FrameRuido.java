package com.PDI.view.Ruido;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.PDI.controller.ruido.*;
import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.PanelImagen;
import com.PDI.model.FuntionStatic;

public class FrameRuido extends JInternalFrame {
	private FrameMain main;
    private JComboBox<String> imageComboBox;
    private JComboBox<String> ruidoComboBox;
    private JTextField param1Field;
    private JTextField param2Field;
    private JButton btnProcesar;
    private JButton btnAceptar;
    private PanelImagen panelImageOriginal;
    private PanelImagen panelImageNoisy;
    private final int width = 400;
    private final int height = 400;
    private int[][] resultImage = null;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();
    AplicarRuido aplicarRuido = new AplicarRuido();

    public FrameRuido(FrameMain main) {
        super("Añadir Ruido");
		this.main = main;
        initComponents();
        setSize(1000, 600);
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

        imageComboBox = new JComboBox<>();
        ruidoComboBox = new JComboBox<>();
        param1Field = new JTextField(10);
        param2Field = new JTextField(10);
        btnProcesar = new JButton("Procesar");
        btnAceptar = new JButton("Aceptar");

        populateImageComboBox();

        ruidoComboBox.addItem("Ruido Coherente");
        ruidoComboBox.addItem("Ruido Gaussiano");
        ruidoComboBox.addItem("Ruido Rayleigh");
		ruidoComboBox.addItem("Ruido De Sal y Pimienta");
        ruidoComboBox.addItem("Ruido Uniforme");
        ruidoComboBox.addItem("Ruido Exponencial Negativo");
        ruidoComboBox.addItem("Ruido Gamma");


        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel(new GridLayout(1, 2));
        JPanel panelEast = new JPanel(new GridLayout(4, 2, 5, 5));

        JPanel panelImage1 = new JPanel();
        JPanel panelImage2 = new JPanel();

        panelImage1.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImage2.setBorder(new BevelBorder(BevelBorder.RAISED));

        panelImageOriginal = new PanelImagen();
        panelImageNoisy = new PanelImagen();

        panelImageOriginal.setSize(width, height);
        panelImageNoisy.setSize(width, height);

        panelImage1.add(panelImageOriginal);
        panelImage2.add(panelImageNoisy);

        panelCenter.add(panelImage1);
        panelCenter.add(panelImage2);

        panelEast.add(new JLabel("Parámetro 1:"));
        panelEast.add(param1Field);
        panelEast.add(new JLabel("Parámetro 2:"));
        panelEast.add(param2Field);

        panelNorth.add(imageComboBox);
        panelNorth.add(ruidoComboBox);
        panelNorth.add(btnProcesar);
        panelNorth.add(btnAceptar);

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelEast, BorderLayout.EAST);

        listener();
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
                    panelImageOriginal.repaint();
                }
            }
        });

        btnProcesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imageComboBox.getSelectedItem() == null || ruidoComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen y un tipo de ruido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				int id = Integer.parseInt(FuntionStatic.getFirstWord((String) imageComboBox.getSelectedItem()));
				int[][] imagen = imageGray.getImage().get(id);

				String ruido = (String) ruidoComboBox.getSelectedItem();
				double param1, param2;

				try {
					param1 = Double.parseDouble(param1Field.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El valor del parámetro 1 no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					param2 = Double.parseDouble(param2Field.getText());
				} catch (NumberFormatException ex) {
					if (!ruido.equals("Ruido Coherente") && !ruido.equals("Ruido Rayleigh")) {
						JOptionPane.showMessageDialog(null, "El valor del parámetro 2 no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					param2 = 0; // Valor por defecto para casos donde no se necesita param2
				}

				AplicarRuido aplicarRuido = new AplicarRuido();
				switch (ruido) {
					case "Ruido Coherente" -> aplicarRuido.setEstrategia(new RuidoCoherente(param1));
					case "Ruido Gaussiano" -> aplicarRuido.setEstrategia(new RuidoDeGauss(param1, param2));
					case "Ruido Rayleigh" -> aplicarRuido.setEstrategia(new RuidoDeRayleigh(param1));
					case "Ruido De Sal y Pimienta" -> aplicarRuido.setEstrategia(new RuidoDeSalYPimienta(param1));
					case "Ruido Uniforme" -> aplicarRuido.setEstrategia(new RuidoUniforme(param1, param2));
					case "Ruido Exponencial Negativo" -> aplicarRuido.setEstrategia(new RuidoExponencialNegativo(param1,param2));
					case "Ruido Gamma" -> aplicarRuido.setEstrategia(new RuidoGamma(param1, param2));
					default -> {
						JOptionPane.showMessageDialog(null, "Tipo de ruido no soportado.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				resultImage = aplicarRuido.aplicarRuido(imagen);
				panelImageNoisy.setImage(addImageList(resultImage), width, height);
				panelImageNoisy.repaint();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultImage != null) {
					String valueName = (String)ruidoComboBox.getSelectedItem();
					String model = "Gris";
					int id = FuntionStatic.generarId();
					FrameImagen frameImagen = new FrameImagen(addImageList(resultImage));
					frameImagen.setId(id);
					frameImagen.setTipo(model);
					frameImagen.setModelColor(model);
					frameImagen.setTitle(model + valueName);
					imageGray.addImage(id,resultImage,model+valueName);
					main.seleccionarFrameImagen(frameImagen);	
					main.addInternalFrame(frameImagen);
                    imageGray.addImage(id, resultImage, valueName);
                    JOptionPane.showMessageDialog(null, "Imagen con ruido guardada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha procesado ninguna imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
}

