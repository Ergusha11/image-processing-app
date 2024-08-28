package com.PDI.view.FiltrosNoLineales;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.PDI.model.greyImagesReferences;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.PanelImagen;
import com.PDI.controller.filtroNoLineal.*;
import com.PDI.model.FuntionStatic;

public class FrameNoLineales extends JInternalFrame {
    private JComboBox<String> imageComboBox;
    private JComboBox<String> filterComboBox;
    private JButton btnProcesar;
    private JButton btnAceptar;
    private PanelImagen panelImageOriginal;
    private PanelImagen panelImageFiltered;
	private FrameMain main;
    private final int width = 400;
    private final int height = 400;
	private int[][] resultd = null;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();

    public FrameNoLineales(FrameMain main) {
        super("Filtros No Lineales");
		this.main = main; 
        initComponents();
        setSize(1000, 690);
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

        // Inicializar componentes
        imageComboBox = new JComboBox<>();
        filterComboBox = new JComboBox<>();
        btnProcesar = new JButton("Procesar");
        btnAceptar = new JButton("Aceptar");

        // Añadir filtros al combobox
        filterComboBox.addItem("Alfa-Trimmed");
        filterComboBox.addItem("Mediana");
        filterComboBox.addItem("Máximo");
        filterComboBox.addItem("Mínimo");
        filterComboBox.addItem("Punto Medio");
        filterComboBox.addItem("Armónico");
        filterComboBox.addItem("Contra Armónico");
        filterComboBox.addItem("Geométrico");
        filterComboBox.addItem("Aritmetico");
        filterComboBox.addItem("Maximo-Minimo");

        populateImageComboBox();

        // Crear paneles
        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel(new GridLayout(1, 2));
        JPanel panelImage1 = new JPanel();
        JPanel panelImage2 = new JPanel();

        panelImage1.setBorder(new BevelBorder(BevelBorder.RAISED));
        panelImage2.setBorder(new BevelBorder(BevelBorder.RAISED));

        panelImageOriginal = new PanelImagen();
        panelImageFiltered = new PanelImagen();

        panelImageOriginal.setSize(width, height);
        panelImageFiltered.setSize(width, height);

        panelImage1.add(panelImageOriginal);
        panelImage2.add(panelImageFiltered);

        panelCenter.add(panelImage1);
        panelCenter.add(panelImage2);

        // Añadir componentes al panel norte
        panelNorth.add(imageComboBox);
        panelNorth.add(filterComboBox);
        panelNorth.add(btnProcesar);
        panelNorth.add(btnAceptar);

        // Añadir paneles al frame
        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);

        listener();
    }

    private void populateImageComboBox() {
        HashMap<Integer, String> map = imageGray.getImageNames();
        String valueName;
        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            valueName = entry.getKey() + " - " + entry.getValue();
            imageComboBox.addItem(valueName);
        }
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
				String selectedFilter = (String) filterComboBox.getSelectedItem();
                int id = Integer.parseInt(FuntionStatic.getFirstWord((String) imageComboBox.getSelectedItem()));
				int[][] matrizImage = imageGray.getImage().get(id);
				int maskSize = 3;
				applyFilter nolineal = new applyFilter();

				switch (selectedFilter){
					case "Alfa-Trimmed" -> nolineal.setStrategy(new filtroAlfaTrim(4));
					case "Mediana" -> nolineal.setStrategy(new filtroMediana());
					case "Máximo" -> nolineal.setStrategy(new filtroDelMaximo());
					case "Mínimo" -> nolineal.setStrategy(new filtroMinimo());
					case "Punto Medio" -> nolineal.setStrategy(new filtroPuntoMedio());
					case "Armónico" -> nolineal.setStrategy(new filtroInfeArmonico());
					case "Contra Armónico" -> nolineal.setStrategy(new filtroContraArmonico(1.5));
					case "Geométrico" -> nolineal.setStrategy(new filtroGeometrico());
					case "Aritmetico" -> nolineal.setStrategy(new filtroMediaAritmetica());
					case "Maximo-Minimo" -> nolineal.setStrategy(new filtroMaxMin());
				}

				resultd = nolineal.aplicarFiltro(matrizImage,maskSize);

				panelImageFiltered.setImage(addImageList(resultd),width,height);
				panelImageFiltered.repaint();
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultd != null) {
					String valueName = (String) filterComboBox.getSelectedItem();
					String model = "Gris";
					int id = FuntionStatic.generarId();
					FrameImagen frameImagen = new FrameImagen(addImageList(resultd));
					frameImagen.setId(id);
					frameImagen.setTipo(model);
					frameImagen.setModelColor(model);
					frameImagen.setTitle(model + valueName);
					imageGray.addImage(id,resultd,model+valueName);
					main.seleccionarFrameImagen(frameImagen);	
					main.addInternalFrame(frameImagen);
                    imageGray.addImage(id, resultd, valueName);
                    JOptionPane.showMessageDialog(null, "Imagen filtrada guardada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha procesado ninguna imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}

