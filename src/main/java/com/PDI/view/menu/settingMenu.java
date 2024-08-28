package com.PDI.view.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class settingMenu{
    private final actionMenu actions;

    public settingMenu(actionMenu actions){
		this.actions = actions;
    }

    public void buildMenu(JMenuBar menuBar){
		menuBar.add(createFile());
		menuBar.add(histogram());
		menuBar.add(changeModels());
		menuBar.add(Binarizacion());
		menuBar.add(Filtros());
    }

    private JMenu createFile(){
		JMenu menuFile = new JMenu("Archivo");
		
		JMenuItem menuItemCargar = new JMenuItem("Cargar Imagen");
		menuItemCargar.addActionListener(e -> actions.loadImage());
		menuFile.add(menuItemCargar);

		JMenuItem menuItemGuardar = new JMenuItem("Guardar Imagen");
		menuItemGuardar.addActionListener(e -> actions.saveImage());
		menuFile.add(menuItemGuardar);
		return menuFile;
    }

	private JMenu changeModels(){
		JMenu menuModel = new JMenu("Modelos de color");

		JMenuItem menuItemPlanos = new JMenuItem("Extraer Planos");
		menuItemPlanos.addActionListener(e -> actions.getModel().extraerPlanos());
		menuModel.add(menuItemPlanos);

		JMenuItem menuItemGrises = new JMenuItem("Escala de grises");
		menuItemGrises.addActionListener(e -> actions.getModel().escalaGrises());
		menuModel.add(menuItemGrises);
		
		JMenuItem menuItemCMY = new JMenuItem("RGB a CMY");
		menuItemCMY.addActionListener(e -> actions.getModel().RGBtoCMY());
		menuModel.add(menuItemCMY);

		JMenuItem menuItemYIQ = new JMenuItem("RGB to YIQ");
		menuItemYIQ.addActionListener(e -> actions.getModel().RGBtoYIQ());
		menuModel.add(menuItemYIQ);

		JMenuItem menuItemHSI = new JMenuItem("RGB to HSI");
		menuItemHSI.addActionListener(e -> actions.getModel().RGBtoHSI());
		menuModel.add(menuItemHSI);
		
		JMenuItem menuItemHSV = new JMenuItem("RGB to HSV");
		menuItemHSV.addActionListener(e -> actions.getModel().RGBtoHSV());
		menuModel.add(menuItemHSV);

		JMenuItem menuItemLab = new JMenuItem("RGB to CieLab");
		menuItemLab.addActionListener(e -> actions.getModel().RGBtoLab());
		menuModel.add(menuItemLab);
		
		JMenuItem menuItemToRGB = new JMenuItem("ModelColor to RGB");
		menuItemToRGB.addActionListener(e -> actions.getModel().modelColortoRGB());
		menuModel.add(menuItemToRGB);
		
		return menuModel;
	}

	private JMenu histogram(){
		JMenu menuHisto = new JMenu("Estadisticas de la imagen");

		JMenuItem menuItemHisto = new JMenuItem("Histograma");
		menuItemHisto.addActionListener(e -> actions.histogram());
		menuHisto.add(menuItemHisto);

		JMenuItem menuItemChangeHisto = new JMenuItem("Modificacion del histograma");
		menuItemChangeHisto.addActionListener(e -> actions.ChangeHisto());
		menuHisto.add(menuItemChangeHisto);

		JMenuItem menuItemEquel = new JMenuItem("Equealizacion");
		menuItemEquel.addActionListener(e -> actions.equealHisto());
		menuHisto.add(menuItemEquel);

		return menuHisto;

	}

	private JMenu Binarizacion(){
		JMenu menuBina = new JMenu("Binarizacion de Imagen");

		JMenuItem menuItemBina = new JMenuItem("Biarizacion por umbral");
		menuItemBina.addActionListener(e -> actions.binarizacion());
		menuBina.add(menuItemBina);

		JMenuItem menuItemOpe = new JMenuItem("Operaciones entre Imagenes");
		menuItemOpe.addActionListener(e -> actions.operacionesImagenes());
		menuBina.add(menuItemOpe);

		return menuBina;
	}

	private JMenu Filtros(){
		JMenu menuFiltros = new JMenu("Filtros");

		JMenuItem menuItemLineales = new JMenuItem("Filtros Lineales");
		menuItemLineales.addActionListener(e -> actions.filtrosLineales());
		menuFiltros.add(menuItemLineales);

		JMenuItem menuItemNoLineales = new JMenuItem("Filtros no Lineales");
		menuItemNoLineales.addActionListener(e -> actions.filtrosNoLineales());
		menuFiltros.add(menuItemNoLineales);

		JMenuItem menuItemRuido = new JMenuItem("Ruido");
		menuItemRuido.addActionListener(e -> actions.aplicarRuido());
		menuFiltros.add(menuItemRuido);

		return menuFiltros;
	}

}
