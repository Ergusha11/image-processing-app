package com.PDI.view.menu.modelAction;

import javax.swing.JOptionPane;

import com.PDI.controller.modelColor.*;
import com.PDI.controller.modelColor.planeExtractor.*;
import com.PDI.model.*;
import com.PDI.view.FrameImagen;
import com.PDI.view.FrameMain;

public class acctionModel{
	private FrameMain main;
	private ImageConverter modelConvert;
	private ExtractPlanes modelPlanes; 
	private final referenciaImagen refImg;

	public acctionModel(FrameMain main,referenciaImagen refImg){
		this.main = main;
		this.refImg = refImg;
	}

	public void extraerPlanos(){
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		String modelColor = main.getFrameActivo().getModelColor();
		//System.out.println(modelColor);
		modelPlanes = new ExtractPlanes(null);	
		
		switch (modelColor){
			case "RGB" -> modelPlanes.setPlanes(new PlanesRGB());
			case "CMY" -> modelPlanes.setPlanes(new PlanesCMY());
			case "YIQ" -> modelPlanes.setPlanes(new PlanesYIQ());
			case "HSI" -> modelPlanes.setPlanes(new PlanesHSI());
			case "HSV" -> modelPlanes.setPlanes(new PlanesHSV());
			case "Lab" -> modelPlanes.setPlanes(new PlanesCieLab());
			default -> {
				JOptionPane.showMessageDialog(null,"Tipo de extracion no soportado: " + modelColor, "Error de extracion de planos", JOptionPane.ERROR_MESSAGE);
				modelConvert = null;
			}			
		}

		int[][][] planes = modelPlanes.extractPlane(matrixPixel);
		for(int i = 0; i < 3;i++){
			FrameImagen frameImagen = new FrameImagen(FuntionStatic.convertImage(planes[i]));
			frameImagen.getLector().extraerPixeles();
			frameImagen.setTipo("Plano " + (i+1));
			frameImagen.setIdReference(id);
			id = FuntionStatic.generarId();
			main.seleccionarFrameImagen(frameImagen);
			frameImagen.setId(id);
			frameImagen.setModelColor(modelColor);
			frameImagen.setTitle(modelColor+" "+frameImagen.getTipo());
			refImg.addImage(id,frameImagen.getLector().getPixelRGB());
			main.addInternalFrame(frameImagen);
		}
	}

	public void escalaGrises(){
		String model = "Gris";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		escalaGrises modelConvert = new escalaGrises();	
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertModelColor(matrixPixel,id,(nombre+" "+model))
		));
		frameImagen.setModelColor(model);
		frameImagen.setTitle(model + " " + nombre);
		frameImagen.setTipo(model);
		frameImagen.setId(id);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
		//System.out.println("Nombre: " + nombre);
	}

	public void RGBtoCMY(){
		String model = "CMY";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		
		//Calcular el Id del nuevo frame
		id = FuntionStatic.generarId(); 

		modelConvert = new ImageConverter(new RGBtoCMY());
		
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertImage(matrixPixel,id,(nombre+" "+model))
		));
		//Asignamos los valores al frame
		frameImagen.setModelColor(model);
		frameImagen.setTipo("Color");
		frameImagen.setId(id);
		frameImagen.setTitle(model+" "+nombre);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
	}

	public void RGBtoYIQ() {
		String model = "YIQ";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		modelConvert = new ImageConverter(new RGBtoYIQ());
		id = FuntionStatic.generarId();
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertImage(matrixPixel,id,(nombre+" "+model))
		));
		frameImagen.setModelColor(model);
		frameImagen.setTitle(model+" "+nombre);
		frameImagen.setTipo("Color");
		frameImagen.setId(id);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
	}
	
	public void RGBtoHSI(){
		String model = "HSI";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		modelConvert = new ImageConverter(new RGBtoHSI());
		id = FuntionStatic.generarId();
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertImage(matrixPixel,id,(nombre+" "+model))
		));
		frameImagen.setModelColor(model);
		frameImagen.setTitle(model+" "+nombre);
		frameImagen.setTipo("Color");
		frameImagen.setId(id);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
	}

	public void RGBtoHSV(){
		String model = "HSV";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		modelConvert = new ImageConverter(new RGBtoHSV());
		id = FuntionStatic.generarId();
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertImage(matrixPixel,id,(nombre+" "+model))
		));
		frameImagen.setModelColor(model);
		frameImagen.setTitle(model+" "+nombre);
		frameImagen.setTipo("Color");
		frameImagen.setId(id);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
	}

	public void RGBtoLab(){
		String model = "Lab";
		String nombre = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		pixel[][] matrixPixel = refImg.getImage().get(id);
		modelConvert = new ImageConverter(new RGBtoCieLab());
		id = FuntionStatic.generarId();
		FrameImagen frameImagen = new FrameImagen(
			FuntionStatic.convertImage(modelConvert.convertImage(matrixPixel,id,(nombre+" "+model))
		));
		frameImagen.setModelColor(model);
		frameImagen.setTitle(model+" "+nombre);
		frameImagen.setTipo("Color");
		frameImagen.setId(id);
		main.seleccionarFrameImagen(frameImagen);
		main.addInternalFrame(frameImagen);
	}

	public void modelColortoRGB(){
		String name = main.getFrameActivo().getTitle();
		int id = main.getFrameActivo().getId();
		String tipo = main.getFrameActivo().getModelColor();
		pixel[][] matrizPixel = refImg.getImage().get(id);
		modelConvert = new ImageConverter(null);
		
		switch(tipo){
			case "CMY" -> modelConvert.setStrategy(new CMYtoRGB());
			case "YIQ" -> modelConvert.setStrategy(new YIQtoRGB());
			case "HSI" -> modelConvert.setStrategy(new HSItoRGB());
			case "HSV" -> modelConvert.setStrategy(new HSVtoRGB());
			case "Lab" -> modelConvert.setStrategy(new CieLabtoRGB());
			default -> {
				JOptionPane.showMessageDialog(null,"Tipo de conversión no soportado: " + tipo, "Error de Conversión", JOptionPane.ERROR_MESSAGE);
				modelConvert = null;
			}			
		}

		if(modelConvert != null){
			id = FuntionStatic.generarId();
			name = FuntionStatic.getFirstWord(name);
			FrameImagen frameImagen = new FrameImagen(
				FuntionStatic.convertImage(modelConvert.convertImage(matrizPixel,id,name)
			));
			frameImagen.setModelColor("RGB");
			frameImagen.setTipo("Color");
			frameImagen.setId(id);
			main.seleccionarFrameImagen(frameImagen);
			main.addInternalFrame(frameImagen);
		}
	}
}
