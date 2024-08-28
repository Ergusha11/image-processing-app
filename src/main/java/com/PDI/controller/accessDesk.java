package com.PDI.controller;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * La clase {@link accessDesk} proporciona una funcion para
 * poder cargar imagenes de tipo png,git,jpeg y jpg para 
 * ser procesadas.
 * 
 * @author Gustavo Hernandez
 * @version 1.0
 */
public class accessDesk extends JFrame{
	private File image;
	/*
	 * Constructor para crear una instancia de accessDesk 
	 * que ejecuta un metodo para aceder y cargar la imagen
	 *
	 * @param path Es la ruta donde se encuentra el archivo 
	*/
	public accessDesk(){
		this.image = null;
	}

	public accessDesk(String path){
		openFiles(path);
	}

	private void openFiles(String path){
		JFileChooser file;
		if(path!=null){
			file = new JFileChooser(path);
		}
		else{
			file = new JFileChooser();
		}
		file.setAcceptAllFileFilterUsed(false);
		file.addChoosableFileFilter(new FileNameExtensionFilter("Imagenes", "png", "gif", "jpeg","jpg","bmp"));
		int resultado = file.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
				this.image = file.getSelectedFile();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + ex.getMessage());
            }
        }
	}

	public void saveFiles(BufferedImage bufferedImage){
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar imagen");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagenes PNG", "png"));
        
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }
			try {
				ImageIO.write(bufferedImage, "png", fileToSave);
				System.out.println("Imagen guardada en: " + fileToSave.getAbsolutePath());
			} catch (Exception e){
                JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + e.getMessage());
			}
        }
	}
	
	public File getPath() {
		return image;
	}
}
