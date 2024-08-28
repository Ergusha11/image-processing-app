package com.PDI.view.menu;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.PDI.controller.accessDesk;
import com.PDI.controller.lectorPlanos;
import com.PDI.model.FuntionStatic;
import com.PDI.model.greyImagesReferences;
import com.PDI.model.referenciaImagen;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;
import com.PDI.view.Binarizacion.FrameBina;
import com.PDI.view.Histograma.FrameChangeHisto;
import com.PDI.view.Histograma.FrameEqualization;
import com.PDI.view.Histograma.FrameHistograma;
import com.PDI.view.OperaEntreImagenes.FrameOperaciones;
import com.PDI.view.Ruido.FrameRuido;
import com.PDI.view.Filtros.FrameFiltrosLineales;
import com.PDI.view.FiltrosNoLineales.FrameNoLineales;
import com.PDI.view.menu.modelAction.acctionModel;

public class actionMenu{
	private FrameMain main;
	private acctionModel model;
	private final referenciaImagen refImg;
    greyImagesReferences imageGray = greyImagesReferences.getInstance();

	public actionMenu(FrameMain main){
		this.main = main;
		this.refImg = referenciaImagen.getInstance();
	}

    public void loadImage(){
		//System.out.println("Imagen cargada");
		int id;
		lectorPlanos imagen = new lectorPlanos();
		imagen.buscarImagen(main.getPath());
		if(imagen.getPath()!=null){
			imagen.leerBufferedImagen();
			imagen.extraerPixeles();
			id = FuntionStatic.generarId(); 
			refImg.addImage(id,imagen.getPixelRGB());
			FrameImagen frameImagen = new FrameImagen(imagen);
			main.seleccionarFrameImagen(frameImagen);
			frameImagen.setId(id);
			main.addInternalFrame(frameImagen);
			main.setPath(imagen.getPath());
		}
    }

	public void saveImage(){
		FrameImagen ref = main.getFrameActivo();
		accessDesk save = new accessDesk();
		Image image = ref.getPanel().getImagen();
		BufferedImage buffered = FuntionStatic.toBufferedImage(image);
		save.saveFiles(buffered);
	}

	public void histogram(){
		FrameImagen ref = main.getFrameActivo();
		System.out.println(ref.getId());
		FrameHistograma imageEsta = new FrameHistograma(
			ref.getLector(),ref.getTipo(),ref.getModelColor(),ref.getId(),ref.getIdReference()
		);
		main.addInternalFrame(imageEsta);
	}

	public void binarizacion(){
		try {
			FrameImagen image = main.getFrameActivo();
			if (image == null) {
				JOptionPane.showMessageDialog(null,"No hay imagen seleccionada","ERROR",JOptionPane.ERROR_MESSAGE);
				return;
			}

			String tipo = image.getModelColor();
			if (tipo.equals("Gris")) {
				FrameBina binarizacion = new FrameBina(image.getLector(), image.getId(),this.main,image.getTitle());
				main.addInternalFrame(binarizacion);
			} else {
				JOptionPane.showMessageDialog(null,"Poner solo imagenes en escala de grises","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Ocurrió un error al procesar la imagen","ERROR",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void operacionesImagenes(){
		if(!imageGray.getImage().isEmpty()){
			FrameOperaciones operaciones = new FrameOperaciones(this.main);
			main.addInternalFrame(operaciones);
		}
		else {
			JOptionPane.showMessageDialog(null,"No hay imagenes en escala de grises","ERROR",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void ChangeHisto(){
		if(!imageGray.getImage().isEmpty()){
			FrameChangeHisto changeHisto = new FrameChangeHisto(this.main);
			main.addInternalFrame(changeHisto);
		}
		else {
			JOptionPane.showMessageDialog(null,"No hay imagenes en escala de grises","ERROR",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void equealHisto(){
		FrameEqualization equalization = new FrameEqualization(this.main);
		main.addInternalFrame(equalization);
	}

	public void filtrosLineales(){
		FrameFiltrosLineales filtros = new FrameFiltrosLineales();
		main.addInternalFrame(filtros);
	}

	public void filtrosNoLineales(){
		FrameNoLineales noLineales = new FrameNoLineales(this.main);
		main.addInternalFrame(noLineales);
	}

	public void aplicarRuido(){
		FrameRuido ruido = new FrameRuido(this.main);
		main.addInternalFrame(ruido);
	}

	public acctionModel getModel() {
		if (model == null) {
			if(this.main.getFrameActivo()!=null){
				model = new acctionModel(this.main,this.refImg); // Nuevamente, asumiendo un constructor sin argumentos
			}else{
				JOptionPane.showMessageDialog(null,"No hay imagen seleccionada","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
		return model;
	}

}
